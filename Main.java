package co.edu.uniquindio.parcial2infra;

import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception {
		System.out.println("\n-------------------------- BIENVENIDO ---------------------------");
		displayMenu();
	}

	public static void displayMenu() throws Exception{
		String opcion;
		boolean flag = true;
		@SuppressWarnings("resource")
		Scanner leer = new Scanner(System.in);

		while (flag == true) {
			
			System.out.println("\nDigite uno de los siguientes numeros para seleccionar una funcion del programa"
					+ "\n\nConversiones entre sistemas numericos:"
					+ "\n 1. Convertir un numero decimal a binario y hexadecimal "
					+ "\n 2. Convertir un numero binario a decimal y hexadecimal"
					+ "\n 3. Convertir un numero hexadecimal a binario y decimal"
					+ "\n\nFunciones de redes:"
					+ "\n 4. Hallar la direccion de red y broadcast de una red dada la direccion IP y mascara de subred"
					+ "\n 5. Hallar la cantidad de direcciones IP asignables dada la direccion IP y mascara de subred"
					+ "\n 6. Hallar la lista de direcciones IP asignables dada la direccion IP y mascara de subred"
					+ "\n 7. Hallar la mascara de subred que puede usarse con el desperdicio minimo de direcciones dada la cantidad de hosts necesarios para una red"
					+ "\n\nFunciones de subredes:"
					+ "\n 8. Hallar la cantidad de subredes utilizables dada una direccion de red, mascara de subred y mascara de subred adaptada"
					+ "\n 9. Hallar la cantidad de direcciones IP asignables dada una direccion de red, mascara de subred y mascara de subred adaptada"
					+ "\n 10. Hallar el rango de direcciones IP asignables dada una direccion de red, mascara de subred y mascara de subred adaptada"
					+ "\n 11. Hallar la direccion de una subred dada"
					+ "\n 12. Hallar la direccion de broadcast de una subred dada");

			System.out.println("\nSeleccione una opcion");
			opcion = leer.nextLine();

			flag = menu(opcion);
		}

		System.out.println("\nAdios");
	}

	public static boolean menu(String opcion){
		Redes red = new Redes();
		Operaciones operacion = new Operaciones();
		@SuppressWarnings("resource")
		Scanner leer = new Scanner(System.in);
		String confirmacion, dato, dato2, dato3, dato4;

		if(opcion.equals("1")){
			System.out.println("\nEscriba un numero decimal (no mayor a 255)");
			dato = leer.nextLine();

			System.out.println("\nEl numero decimal convertido a binario es " + operacion.convertirDecimalBinario(dato) + " y a hexadecimal es " + operacion.convertirDecimalHexadecimal(dato));
		}

		else if(opcion.equals("2")){
			System.out.println("\nEscriba un numero binario (no mayor a 8 bits)");
			dato = leer.nextLine();

			System.out.println("\nEl numero binario convertido a decimal es " + operacion.convertirBinarioDecimal(dato) + " y a hexadecimal es " + operacion.convertirBinarioHexadecimal(dato));
		}

		else if(opcion.equals("3")){
			System.out.println("\nEscriba un numero hexadecimal (no mayor a FF)");
			dato = leer.nextLine();

			System.out.println("\nEl numero hexadecimal convertido a binario es " + operacion.convertirHexadecimalBinario(dato) + " y a decimal es " + operacion.convertirHexaDecimal(dato));
		}

		else if(opcion.equals("4")){
			System.out.println("\nEscriba la direccion IP del host en formato decimal con puntos");
			dato = leer.nextLine();

			System.out.println("\nEscriba la mascara de subred en formato decimal con puntos o en formato abreviado (incluya el /)");
			dato2 = leer.nextLine();

			String[] resultado = red.encontrarDireccionRedYBroadcast(dato, dato2);
			System.out.println("\nLa direccion de la red es " + resultado[0] + " y su direccion de broadcast " + resultado[1]);
		}

		else if(opcion.equals("5")){
			System.out.println("\nEscriba la mascara de subred en formato decimal con puntos o en formato abreviado (incluya el /)");
			dato = leer.nextLine();

			System.out.println("\nLa cantidad de direcciones IP que se pueden asignar a hosts en esta red es de " + red.calcularCantidadDireccionesAsignables(dato));

		}

		else if(opcion.equals("6")){
			System.out.println("\nEscriba la direccion IP del host en formato decimal con puntos");
			dato = leer.nextLine();

			System.out.println("\nEscriba la mascara de subred en formato decimal con puntos o en formato abreviado (incluya el /)");
			dato2 = leer.nextLine();

			List<String> lista = red.encontrarDireccionesAsignables(dato, dato2);
			System.out.println("La lista de direcciones asignables a los hosts es:");
			System.out.println( lista.toString() );
			System.out.println( "(" + lista.size() + " direcciones)" );
		}

		else if(opcion.equals("7")){
			System.out.println("\nEscriba la cantidad de hosts que necesita para la red");
			dato = leer.nextLine();

			System.out.println("La mascara de subred que puede ser utilizada con el desperdicio minimo de direcciones es: "+ red.determinarMascaraParaHosts(dato));
		}

		else if(opcion.equals("8")){
			System.out.println("\nEscriba la direccion de la red en formato decimal con puntos");
			dato = leer.nextLine();

			System.out.println("\nEscriba la mascara de subred para la red en formato decimal con puntos o abreviada (incluya el /)");
			dato2 = leer.nextLine();

			System.out.println("\nEscriba la mascara de subred adaptada en formato decimal con puntos o abreviada (incluya el /)");
			dato3 = leer.nextLine();

			System.out.println("La cantidad de subredes que se pueden utilizar es de "+ red.determinarCantidadSubredes(dato, dato2, dato3));
		}

		else if(opcion.equals("9")){
			System.out.println("\nEscriba la direccion de la red en formato decimal con puntos");
			dato = leer.nextLine();

			System.out.println("\nEscriba la mascara de subred para la red en formato decimal con puntos o abreviada (incluya el /)");
			dato2 = leer.nextLine();

			System.out.println("\nEscriba la mascara de subred adaptada en formato decimal con puntos o abreviada (incluya el /)");
			dato3 = leer.nextLine();

			System.out.println("La cantidad de direcciones asignables para hosts es de "+ red.determinarCantidadDireccionesAsignablesHostsSubred(dato, dato2, dato3));
		}

		else if(opcion.equals("10")){
			System.out.println("\nEscriba la direccion de la red en formato decimal con puntos");
			dato = leer.nextLine();

			System.out.println("\nEscriba la mascara de subred para la red en formato decimal con puntos o abreviada (incluya el /)");
			dato2 = leer.nextLine();

			System.out.println("\nEscriba la mascara de subred adaptada en formato decimal con puntos o abreviada (incluya el /)");
			dato3 = leer.nextLine();
			
			List< List<String> > rangosDirecciones = red.determinarRangoDireccionesAsignablesHosts(dato, dato2, dato3);
			
			for(int i = 1; i <= rangosDirecciones.size(); i++) {
				System.out.println("Subred " + i + ":" + rangosDirecciones.get(i-1).toString() );
			}
		}

		else if(opcion.equals("11")){
			System.out.println("\nEscriba la direccion de la red en formato decimal con puntos");
			dato = leer.nextLine();

			System.out.println("\nEscriba la mascara de subred para la red en formato decimal con puntos o abreviada (incluya el /)");
			dato2 = leer.nextLine();

			System.out.println("\nEscriba la mascara de subred adaptada en formato decimal con puntos o abreviada (incluya el /)");
			dato3 = leer.nextLine();
			
			System.out.println("\n¿De cual subred desea conocer la direccion? (numero)");
			dato4 = leer.nextLine();
			
			System.out.println("\nLa direccion de la subred " + dato4 + " en la red es: " + red.encontrarDireccionSubredYBroadcast(dato, dato2, dato3, dato4)[0]);
		}

		else if(opcion.equals("12")){
			System.out.println("\nEscriba la direccion de la red en formato decimal con puntos");
			dato = leer.nextLine();

			System.out.println("\nEscriba la mascara de subred para la red en formato decimal con puntos o abreviada (incluya el /)");
			dato2 = leer.nextLine();

			System.out.println("\nEscriba la mascara de subred adaptada en formato decimal con puntos o abreviada (incluya el /)");
			dato3 = leer.nextLine();
			
			System.out.println("\n¿De cual subred desea conocer la direccion de broadcast? (numero)");
			dato4 = leer.nextLine();
			
			System.out.println("\nLa direccion de broadcast de la subred " + dato4 + " en la red es: " + red.encontrarDireccionSubredYBroadcast(dato, dato2, dato3, dato4)[1]);
		}

		else {
			System.out.println("\nNumero invalido");
		}

		System.out.println("\nSeleccione 1 si desea volver a escoger una opcion, cualquier otra tecla para salir del programa");
		confirmacion = leer.nextLine();

		if(confirmacion.equals("1")){
			return true;
		}
		else{
			return false;
		}
	}
}