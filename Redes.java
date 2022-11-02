package co.edu.uniquindio.parcial2infra;

import java.util.ArrayList;
import java.util.List;

public class Redes {
	
	private final Operaciones operaciones = new Operaciones();
	
	// --------------------- Dada la dirección IP de un host y la máscara de subred -----------------------------
	
	/**
	 * Calcula la direccion de red y direccion de broadcast dada una direccion de host y una mascara de subred
	 * @param direccionHost
	 * @param mascara
	 * @return [0]: direccion de red, [1]: direccion de broadcast
	 */
	public String[] encontrarDireccionRedYBroadcast(String direccionHost, String mascara) {
		String direccionBroadcastBinaria = "";
		String direccionRedBinaria = "";
		char bit;
		
		// Si la mascara tiene tres o menos caracteres, entonces esta en formato abreviado
		if(mascara.length() <= 3) {
			mascara = calcularMascara(mascara);
		}
		
		String direccionHostBinaria = calcularDireccionBinaria(direccionHost);
		String mascaraBinaria = calcularDireccionBinaria(mascara);
		
		for(int i = 0; i < direccionHostBinaria.length(); i++) {
			// Por cada 1 que se encuentre en la mascara, se toma un bit de la direccion de host
			if(mascaraBinaria.charAt(i) == '1') {
				bit = direccionHostBinaria.charAt(i);
				direccionRedBinaria += bit;
				direccionBroadcastBinaria += bit;
			}
			// Por cada 0 en la mascara, la direccion de red toma el mismo 0, pero la de broadcast toma un 1
			else {
				bit = mascaraBinaria.charAt(i);
				direccionRedBinaria += bit;
				
				if(bit == '0') direccionBroadcastBinaria += "1";
				else direccionBroadcastBinaria += bit;
			}
		}
		
		return new String[] {calcularDireccionDecimal(direccionRedBinaria), 
							 calcularDireccionDecimal(direccionBroadcastBinaria) };
	}
	
	/**
	 * Calcula la cantidad de direcciones IP que se pueden asignar a hosts dentro de una red
	 * @param mascara
	 * @return cantidad de direcciones asignables para hosts
	 */
	public int calcularCantidadDireccionesAsignables(String mascara) {
		int numeroBitsHosts;
		int cantidadDireccionesAsignables;
		
		// Si la mascara tiene tres o menos caracteres, entonces esta en formato abreviado
		if(mascara.length() <= 3) {
			mascara = calcularMascara(mascara);
		}
		
		numeroBitsHosts = 32 - encontrarCantidadUnosMascara(mascara);
		cantidadDireccionesAsignables = (int) (Math.pow(2, numeroBitsHosts) - 2);
		
		if(cantidadDireccionesAsignables < 0) return 0;
		
		return cantidadDireccionesAsignables;
	}
	
	/**
	 * Retorna la lista de direcciones IP asignables a los hosts de una red 
	 * @param direccionHost dentro de la red
	 * @param mascara
	 * @return lista completa de direcciones asignables para hosts
	 */
	public List<String> encontrarDireccionesAsignables(String direccionHost, String mascara) {
		List<String> listaDireccionesAsignables = new ArrayList<>();
		
		// Si la mascara tiene tres o menos caracteres, entonces esta en formato abreviado
		if(mascara.length() <= 3) {
			mascara = calcularMascara(mascara);
		}
		
		int cantidadDireccionesAsignables = calcularCantidadDireccionesAsignables(mascara);
		
		// La direccion desde la que se empieza a contar es la de red (sin incluirla)
		String direccionAnterior = encontrarDireccionRedYBroadcast(direccionHost, mascara)[0];
		
		while(cantidadDireccionesAsignables > 0) {
			// Se suma 1 a la direccion anterior y se agrega a la lista
			listaDireccionesAsignables.add( incrementarDireccion( direccionAnterior ) );
			
			// Se actualiza la direccion anterior
			direccionAnterior = incrementarDireccion( direccionAnterior );
			cantidadDireccionesAsignables--;
		}
		
		return listaDireccionesAsignables;
	}
	
	// -----------------------Dada la cantidad de hosts que necesita para una red ------------------------------

	/**
	 * Encuentra la mascara que ofrezca el menor desperdicio de direcciones para una cantidad de hosts dada
	 * @param cantidadHosts
	 * @return Mascara de subred en formato abreviado
	 */
	public String determinarMascaraParaHosts(String cantidadHostsTexto) {
		String cantidadUnosMascara;
		int cantidadHosts = Integer.parseInt(cantidadHostsTexto);
		int potenciaSuficiente = 0;
		int exponente = 0;
		
		// Encontrar minima potencia de 2 util
		for(int i = 0; potenciaSuficiente < cantidadHosts; i++) {
			potenciaSuficiente = (int) Math.pow(2, i);
			exponente = i;
		}
		
		cantidadUnosMascara = Integer.toString(32 - exponente);
		
		return "/" + cantidadUnosMascara;
	}
	
	// ----- Dada una dirección de red, la máscara de subred correspondiente y la máscara de subred adaptada ----
	
	/**
	 * Calcula la cantidad de subredes que se pueden usar a partir de una direccion de red
	 * @param direccionRed
	 * @param mascara
	 * @param mascaraAdaptada
	 * @return cantidad de subredes utilizables dentro de la red
	 */
	public int determinarCantidadSubredes(String direccionRed, String mascara, String mascaraAdaptada) {
		int bitsSubred = calcularBitsSubred(mascara, mascaraAdaptada);
		return (int) (Math.pow(2, bitsSubred) - 2);
	}

	/**
	 * Calcula la cantidad de direcciones IP que se pueden asignar a los hosts de una red dividida en subredes
	 * @param direccionRed
	 * @param mascara
	 * @param mascaraAdaptada
	 * @return Cantidad de direcciones asignables para hosts
	 */
	public int determinarCantidadDireccionesAsignablesHostsSubredes(String direccionRed, String mascara, String mascaraAdaptada) {
		int cantidadSubredes = determinarCantidadSubredes(direccionRed, mascara, mascaraAdaptada);
		return calcularCantidadDireccionesAsignables(mascaraAdaptada) * cantidadSubredes;
	}
	
	/**
	 * Retorna las listas de direcciones asignables a los hosts de cada subred dentro de una red
	 * @param direccionRed
	 * @param mascara
	 * @param mascaraAdaptada
	 * @return Lista de listas de direcciones asignables para hosts en cada subred
	 */
	public List< List<String> > determinarRangoDireccionesAsignablesHosts(String direccionRed, String mascara, String mascaraAdaptada) {
		int cantidadSubredes = determinarCantidadSubredes(direccionRed, mascara, mascaraAdaptada);
		String direccionSubred;
		String primeraDireccionHostSubred;
		
		// Lista "superior" que almacenara las listas de direcciones de las subredes 
		List< List<String> > rangoDireccionesHosts = new ArrayList<>();
		
		// Lista "inferior" que almacenara la lista de direcciones de cada subred
		List<String> direccionesHostsSubred = new ArrayList<>();
		
		for(int i = 1; i <= cantidadSubredes; i++) {
			// Direccion de cada subred
			direccionSubred = encontrarDireccionSubredYBroadcast(direccionRed, mascara, mascaraAdaptada, Integer.toString(i))[0];
			
			// Primera direccion de host de cada subred
			// (necesaria para usar el metodo de encontrar las direcciones asignables)
			primeraDireccionHostSubred = incrementarDireccion(direccionSubred);
			
			// Direcciones asignables a la subred i
			direccionesHostsSubred = encontrarDireccionesAsignables(primeraDireccionHostSubred, mascaraAdaptada);
			
			// Se añade esta lista de direcciones a la lista "superior"
			rangoDireccionesHosts.add(direccionesHostsSubred);
		}

		return rangoDireccionesHosts;
	}
	
	/**
	 * Calcula la direccion de subred y direccion de broadcast de una subred dada dentro de una red 
	 * @param direccionRed
	 * @param mascara
	 * @param mascaraAdaptada
	 * @param subred
	 * @return [0]: direccion de subred, [1]: direccion de broadcast de subred
	 */
	public String[] encontrarDireccionSubredYBroadcast(String direccionRed, String mascara, String mascaraAdaptada, String subred) {
		String bitsSubred = "";
		String direccionSubredBinaria = "";
		
		// Si la mascara tiene tres o menos caracteres, entonces esta en formato abreviado
		if(mascara.length() <= 3) {
			mascara = calcularMascara(mascara);
		}
		
		// Lo mismo ocurre con la mascara adaptada
		if(mascaraAdaptada.length() <= 3) {
			mascaraAdaptada = calcularMascara(mascaraAdaptada);
		}
		
		int numeroBitsSubred = calcularBitsSubred(mascara, mascaraAdaptada);
		
		int cantidadUnosMascara = encontrarCantidadUnosMascara(mascara);
		
		String direccionRedBinaria = calcularDireccionBinaria(direccionRed);
		int indiceBits = 0;
		char bit;
		
		int contadorBitsCompletados = 0;
		
		// Se toma el numero de bits indicado por la mascara de la direccion de red 
		while(cantidadUnosMascara > 0) {
			bit = direccionRedBinaria.charAt(indiceBits);
			direccionSubredBinaria += bit;
			
			if(bit != '.' ) {
				contadorBitsCompletados++;
				cantidadUnosMascara--;
			}
			indiceBits++;
		}
		
		// Puede ser que con los bits obtenidos anteriormente, algunos octetos queden incompletos

		// Se obtienen los bits para la subred correspondiente
		bitsSubred = operaciones.reducirBinario(operaciones.convertirDecimalBinario(subred), numeroBitsSubred); 
		
		// Se añaden los bits de la subred a la direccion
		direccionSubredBinaria += bitsSubred;
		contadorBitsCompletados += bitsSubred.length();
		
		// Direccion de broadcast de la subred
		String direccionBroadcastSubred = direccionSubredBinaria;
		
		// Los bits que falten para completar 32 seran los asignados para los hosts, por lo tanto:
		
		// Para la direccion de la subred, se llenan los bits de hosts con ceros, y para la de broadcast se llenan con unos:
		for(int i = contadorBitsCompletados; i < 32; i++) {
			direccionSubredBinaria += "0";
			direccionBroadcastSubred += "1";
			contadorBitsCompletados++;
		}
		
		// Ahora hay que dividir las direcciones en octetos
		direccionSubredBinaria = dividirOctetos(direccionSubredBinaria);
		direccionBroadcastSubred = dividirOctetos(direccionBroadcastSubred);
		
		return new String[] {calcularDireccionDecimal(direccionSubredBinaria), calcularDireccionDecimal(direccionBroadcastSubred)};
	}
	
	// ----------------------------------------- Utilidades -----------------------------------------------------

	/**
	 * Convierte una mascara de red en formato abreviado a su equivalente en formato decimal con puntos
	 * @param mascaraAbreviada
	 * @return mascara en formato decimal con puntos
	 */
	private String calcularMascara(String mascaraAbreviada) {
		int cantidadUnosMascara = Integer.parseInt( mascaraAbreviada.substring(1) );
		String mascaraBinaria = calcularMascaraBinaria(cantidadUnosMascara);
		
		return calcularDireccionDecimal(mascaraBinaria);
	}
	
	/**
	 * Retorna una mascara en formato binario con puntos dada su cantidad de unos
	 * @param cantidadUnosMascara
	 * @return mascara en formato binario con puntos
	 */
	private String calcularMascaraBinaria(int cantidadUnosMascara) {
		String mascaraBinaria = "";
		
		for(int i = 1; i <= 32; i++) {
			if(i <= cantidadUnosMascara) mascaraBinaria += "1";
			else mascaraBinaria += "0";
			
			if(i % 8 == 0 && i != 32) mascaraBinaria += ".";
		}
		
		return mascaraBinaria;
	}
	
	/**
	 * Convierte una direccion IP en formato decimal con puntos a su equivalente en formato binario con puntos
	 * @param direccionDecimal con puntos
	 * @return direccion binaria con puntos
	 */
	private String calcularDireccionBinaria(String direccionDecimal) {
		// El "\\" es porque split no acepta caracteres, sino expresiones regulares
		// Entonces si pongo solo el "." no va a separar nada, porque el "." significa "cualquier caracter"
		String[] octetos = direccionDecimal.split("\\.");
		String direccionBinaria = "";
		
		for(int i = 0; i < octetos.length; i++) {
			direccionBinaria += operaciones.convertirDecimalBinario(octetos[i]);
			if(i != 3) direccionBinaria += ".";
		}
		
		return direccionBinaria;
	}
	
	/**
	 * Convierte una direccion IP en formato binario con puntos a su equivalente en formato decimal con puntos
	 * @param direccionBinaria con puntos
	 * @return direccion decimal con puntos
	 */
	private String calcularDireccionDecimal(String direccionBinaria) {
		// El "\\" es porque split no acepta caracteres, sino expresiones regulares
		// Entonces si pongo solo el "." no va a separar nada, porque el "." significa "cualquier caracter"
		String[] octetos = direccionBinaria.split("\\.");
		String direccionDecimal = "";
	
		for(int i = 0; i < octetos.length; i++) {
			direccionDecimal += operaciones.convertirBinarioDecimal(octetos[i]);
			if(i != 3) direccionDecimal += ".";
		}
		
		return direccionDecimal;
	}
	
	/**
	 * Retorna la cantidad de bits en 1 que contiene una mascara en formato decimal con puntos
	 * @param mascaraDecimal
	 * @return cantidad de bits en 1 de la mascara
	 */
	private int encontrarCantidadUnosMascara(String mascaraDecimal) {
		String mascaraBinaria = calcularDireccionBinaria(mascaraDecimal);
		int contadorUnos = 0;
		
		for(int i = 0; i < mascaraBinaria.length(); i++) 
			if(mascaraBinaria.charAt(i) == '1') contadorUnos++;
		
		return contadorUnos;
	}
	
	/**
	 * Suma 1 a una dada direccion IP en formato decimal con puntos
	 * @param direccionDecimal
	 * @return direccionDecimal + 1
	 */
	private String incrementarDireccion(String direccionDecimal) {
		String[] octetos = direccionDecimal.split("\\.");
		int indice = octetos.length - 1;
		String[] bufferOctetos = new String[octetos.length];
		String nuevaDireccion = "";
		int valor;
		
		while(indice >= 0) {
			valor = Integer.parseInt(octetos[indice]);
			
			if(valor < 255) {
				valor += 1;
				bufferOctetos[indice] = Integer.toString(valor);
				break;
			}
			else {
				bufferOctetos[indice] = "0";
				indice--;
			}
		}
		
		// Si en el buffer hay octetos vacios, estos se rellenan con los octetos iniciales de la direccion
		for(int i = 0; i < bufferOctetos.length; i++) {
			if(bufferOctetos[i] == null) {
				bufferOctetos[i] = octetos[i];
			}
			
			nuevaDireccion += bufferOctetos[i];
			if(i != 3) nuevaDireccion += ".";
		}
		
		return nuevaDireccion;
	}
	
	/**
	 * Calcula el numero de bits que pueden tener las subredes de una determinada red
	 * @param mascara
	 * @param mascaraAdaptada
	 * @return Numero de bits que se pueden usar para las subredes
	 */
	private int calcularBitsSubred(String mascara, String mascaraAdaptada) {
		
		// Si la mascara tiene tres o menos caracteres, entonces esta en formato abreviado
		if(mascara.length() <= 3) {
			mascara = calcularMascara(mascara);
		}
		
		// Lo mismo ocurre con la mascara adaptada
		if(mascaraAdaptada.length() <= 3) {
			mascaraAdaptada = calcularMascara(mascaraAdaptada);
		}
		
		int cantidadUnosMascara = encontrarCantidadUnosMascara(mascara);
		int cantidadUnosMascaraAdaptada = encontrarCantidadUnosMascara(mascaraAdaptada);
		
		return cantidadUnosMascaraAdaptada - cantidadUnosMascara;
	}
	
	/**
	 * Recibe una direccion en formato binario con algunos octetos sin separar por puntos, y la retorna con puntos
	 * @param direccionBinaria con algunos cotetos sin separar
	 * @return direccion binaria correctamente separada por puntos
	 */
	public String dividirOctetos(String direccionBinaria) {
		String nuevoOcteto = "";
		String[] octetos = direccionBinaria.split("\\.");
		
		// Las direcciones siempre van a tener 4 octetos
		String[] nuevosOctetos = new String[4]; 
		
		// i recorre los octetos anteriores
		// j recorre los nuevos octetos
		for(int i = 0, j = 0; i < octetos.length && j < nuevosOctetos.length; i++, j++) {
			
			if(octetos[i].length() == 8) {
				nuevosOctetos[i] = octetos[i];
			}
			else {
				// k recorre el "octeto" que no tenga 8 bits 
				for(int k = 1; k <= octetos[i].length(); k++) {	
					nuevoOcteto += octetos[i].charAt(k-1);
					
					if(k % 8 == 0) {
						nuevosOctetos[j] = nuevoOcteto;
						nuevoOcteto = "";
						j++;
					}
				}
				
			}
		}
		
		return nuevosOctetos[0] + "." + nuevosOctetos[1] + "." + nuevosOctetos[2] + "." + nuevosOctetos[3];
	}
	
}
