package co.edu.uniquindio.parcial2infra;

import java.util.ArrayList;
import java.util.List;

public class Redes {
	
	private final Operaciones operaciones = new Operaciones();
	
	// --------------------- Dada la dirección IP de un host y la máscara de subred -----------------------------
	
	/**
	 * 
	 * @param direccionHost
	 * @param mascara
	 * @return [0]: direccion de red, [1]: direccion de broadcast
	 */
	public String[] encontrarDireccionRedYBroadcast(String direccionHost, String mascara) {
		String bitsRed = "";
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
	 * 
	 * @param direccionHost
	 * @param mascara
	 * @return
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
	
	public List<String> encontrarDireccionesAsignables(String direccionHost, String mascara) {
		List<String> listaDireccionesAsignables = new ArrayList<>();
		
		// Si la mascara tiene tres o menos caracteres, entonces esta en formato abreviado
		if(mascara.length() <= 3) {
			mascara = calcularMascara(mascara);
		}
		
		String[] direccionesLimite = encontrarDireccionRedYBroadcast(direccionHost, mascara);
		String[] octetosRed = direccionesLimite[0].split("\\.");
		String[] octetosBroadcast = direccionesLimite[1].split("\\.");
		int cantidadDireccionesAsignables = calcularCantidadDireccionesAsignables(mascara);
		
		int indiceOctetos = octetosRed.length - 1;
		String direccionAnterior = direccionesLimite[0];
		
		while(cantidadDireccionesAsignables > 0) {
			listaDireccionesAsignables.add( incrementarDireccion( direccionAnterior ) );
			direccionAnterior = incrementarDireccion( direccionAnterior );
			cantidadDireccionesAsignables--;
		}
		
		return listaDireccionesAsignables;
	}

	// ----------------------------------------- Utilidades -----------------------------------------------------
	
	


	public String calcularMascara(String mascaraAbreviada) {
		int cantidadUnosMascara = Integer.parseInt( mascaraAbreviada.substring(1) );
		String mascaraBinaria = calcularMascaraBinaria(cantidadUnosMascara);
		
		return calcularDireccionDecimal(mascaraBinaria);
	}
	
	private String calcularMascaraBinaria(int cantidadUnosMascara) {
		String mascaraBinaria = "";
		
		for(int i = 1; i <= 32; i++) {
			if(i <= cantidadUnosMascara) mascaraBinaria += "1";
			else mascaraBinaria += "0";
			
			if(i % 8 == 0 && i != 32) mascaraBinaria += ".";
		}
		
		return mascaraBinaria;
	}
	
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
	
	private int encontrarCantidadUnosMascara(String mascaraDecimal) {
		String mascaraBinaria = calcularDireccionBinaria(mascaraDecimal);
		int contadorUnos = 0;
		
		for(int i = 0; i < mascaraBinaria.length(); i++) 
			if(mascaraBinaria.charAt(i) == '1') contadorUnos++;
		
		return contadorUnos;
	}
	
	private String completarCerosRed(String direccionRedBinaria) {
		
		for(int i = direccionRedBinaria.length(); i <= 32; i++) {
			if(i % 8 == 0 && i != 32) 
				direccionRedBinaria += ".";
			direccionRedBinaria += "0";
		}
		
		return direccionRedBinaria;
	}
	
	private String completarUnosRed(String direccionRedBinaria) {
		
		for(int i = direccionRedBinaria.length(); i <= 32; i++) {
			if(i % 8 == 0 && i != 32) 
				direccionRedBinaria += ".";
			direccionRedBinaria += "1";
		}
		
		return direccionRedBinaria;
	}
	
	private String incrementarDireccion(String direccionDecimal) {
		String[] octetos = direccionDecimal.split("\\.");
		int valor;
		int indice = octetos.length - 1;
		String[] bufferOctetos = new String[octetos.length];
		String nuevaDireccion = "";
		
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
	
}
