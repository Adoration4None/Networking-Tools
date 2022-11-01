package co.edu.uniquindio.parcial2infra;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) throws Exception {
		String valor;
		
		Operaciones operaciones = new Operaciones();
		Redes redes = new Redes();
		
		System.out.println( redes.encontrarDireccionRedYBroadcast("172.16.243.128", "/16")[0] );
		System.out.println("Direcciones asignables: " + redes.calcularCantidadDireccionesAsignables("/16"));
		
		List<String> direccionesAsignables = redes.encontrarDireccionesAsignables("172.16.243.128", "/16");
		
		System.out.println("\nDirecciones asignables:");
		for( String d : direccionesAsignables ) {
			System.out.println(d);
		}
		
		System.out.println(direccionesAsignables.size());
		
//		Scanner leer = new Scanner(System.in);
//		
//		System.out.println("Ingreseun numero en bnario");
//		String bin = leer.nextLine();
//		
//		System.out.println(Integer.parseInt(bin, 2));
//		displayMenu();

//		System.out.println(opcion);
//		menu(opcion);
	}

	public static void displayMenu() throws Exception{
		String opcion;
		boolean flag = true;

		while (flag == true) {
			JOptionPane.showMessageDialog(null, "Digite uno de los siguientes n�meros para seleccionar una funci�n del programa:"
					+ "\n 1. Convertir un numero decimal a binario y hexadecimal "
					+ "\n 2. Convertir un numero binario a decimal y hexadecimal"
					+ "\n 3. Convertir un numero hexadecimal a binario y decimal"
					+ "\n 4. Hallar la direccion de red y broadcast de una red dada la direccion IP y mascara de subred"
					+ "\n 5. Hallar la cantidad de direcciones IP asignables dada la direccion IP y mascara de subred"
					+ "\n 6. Hallar la lista de direcciones IP asignables dada la direccion IP y mascara de subred"
					+ "\n 7. Hallar la mascara de subred que puede usarse con el desperdicio minimo de direcciones dada la cantidad de hosts necesarios para una red"
					+ "\n 8. Hallar la cantidad de subredes utilizables dada una direccion de red, mascara de subred y mascara de subred adaptada"
					+ "\n 9. Hallar la cantidad de direcciones IP asignables dada una direccion de red, mascara de subred y mascara de subred adaptada"
					+ "\n 10. Hallar el rango de direcciones IP asignables dada una direccion de red, mascara de subred y mascara de subred adaptada"
					+ "\n 11. Hallar la direccion de una (dada?) dada una direccion de red, mascara de subred y mascara de subred adaptada"
					+ "\n 12. Hallar la direccion de broadcast de una subred (dada?) dada una direccion de red, mascara de subred y mascara de subred adaptada"
					+ "\n \n Presione en aceptar para seleccionar una de las opciones dadas");

			opcion = JOptionPane.showInputDialog("Seleccione una opcion");

			flag = menu(opcion);
		}

		JOptionPane.showMessageDialog(null, "SEE YOU LATER!");
	}

	public static boolean menu(String opcion){
		//instancia de la clase Numero que tiene todos los metodos
		Numero numero = new Numero();
		String confirmacion, dato, mensaje;
		ArrayList<String> mensaje2 = new ArrayList<String>();

		if(opcion.equals("1")){
			dato = JOptionPane.showInputDialog("Escriba un numero decimal");
			mensaje2.addAll(numero.convertirDecimal(dato));
			JOptionPane.showMessageDialog(null, "El numero decimal convertido a binario es " + mensaje2.get(0) + " y a hexadecimal es " + mensaje2.get(1));
		}
		else if(opcion.equals("2")){
			dato = JOptionPane.showInputDialog("Escriba un numero binario");
			mensaje2.addAll(numero.convertirBinario(dato));
			JOptionPane.showMessageDialog(null, "El numero binario convertido a decimal es " + mensaje2.get(0) + " y a hexadecimal es " + mensaje2.get(1));
		}
		else if(opcion.equals("3")){
			dato = JOptionPane.showInputDialog("Escriba un numero hexadecimal");
			mensaje2.addAll(numero.convertirHexadecimal(dato));
			JOptionPane.showMessageDialog(null, "El numero hexadecimal convertido a decimal es " + mensaje2.get(0) + " y a binario es " + mensaje2.get(1));
		}
		else if(opcion.equals("4")){
			dato = JOptionPane.showInputDialog("Escriba la direccion IP y mascara de subred en decimal separados por una espacio");
			mensaje2 = numero.obtenerDireccionRed_Broadcast(dato);
		}
		else if(opcion.equals("5")){
			dato = JOptionPane.showInputDialog("Escriba la direccion IP y mascara de subred en decimal separados por una espacio");
			mensaje = numero.obtenerCantidadDireccionesAsignables(dato);
		}
		else if(opcion.equals("6")){
			dato = JOptionPane.showInputDialog("Escriba la direccion IP y mascara de subred en decimal separados por una espacio");
			mensaje2 = numero.obtenerListaDireccionesAsignables(dato);
		}
		else if(opcion.equals("7")){
			dato = JOptionPane.showInputDialog("Escriba la cantidad de hosts necesarios para la red");
			mensaje = numero.ObtenerMascaraSubred(dato);
		}
		else if(opcion.equals("8")){
			dato = JOptionPane.showInputDialog("Escriba un numero decimal");
			mensaje = numero.obtenerCantidadSubredesUtilizables(dato);
		}
		else if(opcion.equals("9")){
			dato = JOptionPane.showInputDialog("Escriba un numero decimal");
			mensaje = numero.obtenerCantidadDireccionesAsignables2(dato);
		}
		else if(opcion.equals("10")){
			dato = JOptionPane.showInputDialog("Escriba un numero decimal");
			mensaje = numero.obtenerRangoDireccionesAsignables(dato);
		}
		else if(opcion.equals("11")){
			dato = JOptionPane.showInputDialog("Escriba un numero decimal");
			mensaje = numero.obtenerDireccionSubredDada(dato);
		}
		else if(opcion.equals("12")){
			dato = JOptionPane.showInputDialog("Escriba un numero decimal");
			mensaje = numero.obtenerDireccionBroadcastSubredDada(dato);
		}
		else {
			JOptionPane.showMessageDialog(null, "Numero invalido");
		}

		confirmacion = JOptionPane.showInputDialog("Seleccione 1 si desea volver a escoger una opcion, cualquier otra tecla para salir del programa");

		if(confirmacion.equals("1")){
			return true;
		}
		else{
			return false;
		}
	}
}