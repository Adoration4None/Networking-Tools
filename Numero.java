package co.edu.uniquindio.parcial2infra;

import java.util.ArrayList;

public class Numero {

	/*
	 *
	 */
	public ArrayList<String> convertirDecimal(String numero){
		String valor;
		ArrayList<String> lista = new ArrayList<>();

		//para convertir a binario
		valor = Integer.toBinaryString(Integer.parseInt(numero));
		lista.add(valor);

		//para convertir a hexadecimal
		valor = Integer.toHexString(Integer.parseInt(numero));
		lista.add(valor);

		return (lista);
	}

	/*
	 *
	 */
	public ArrayList<String> convertirBinario(String numero){
		String valor = "";
		ArrayList<String> lista = new ArrayList<>();

		//para convertir a decimal
		valor = valor+Integer.parseInt(numero, 2);
		lista.add(valor);

		//para convertir a hexadecimal
		valor = Integer.toHexString(Integer.parseInt(numero, 2));
		lista.add(valor);

		return (lista);
	}

	/*
	 *
	 */
	public ArrayList<String> convertirHexadecimal(String numero){
		String valor = "";
		ArrayList<String> lista = new ArrayList<>();

		//para convertir a decimal
		valor = valor+Integer.parseInt(numero, 16);
		lista.add(valor);

		//para convertir a binario
		valor = Integer.toBinaryString(Integer.parseInt(numero, 16));
		lista.add(valor);

		return (lista);
	}

	//------------------------------------------------------------------------------------------------DADA LA DIRECCION IP DE UN HOST Y MASCARA DE SUBRED---------------------------------------------------------------------------------------------------------

	/*
	 *
	 */
	public ArrayList<String> obtenerDireccionRed_Broadcast(String numero){
		return null;
	}

	/*
	 *
	 */
	public String obtenerCantidadDireccionesAsignables(String numero){
		return null;
	}

	/*
	 *
	 */
	public ArrayList<String> obtenerListaDireccionesAsignables(String numero){
		return null;
	}

	//----------------------------------------------------------------------------------------------------DADA LA CANTIDAD DE HOSTS QUE NECESITA UNA RED-------------------------------------------------------------------------------------------------------

	/*
	 * Metodo para obtener una mascara de subred para un numero de direcciones de hosts con el desperdicio minimo
	 */
	public String ObtenerMascaraSubred(String numero){
		return null;
	}

	//--------------------------------------------------------------------------------------------DADA UNA DIRECCION DE RED, MASCARA DE SUBRED Y MASCARA DE SUBRED ADAPTADA--------------------------------------------------------------------------------------------

	/*
	 *
	 */
	public String obtenerCantidadSubredesUtilizables(String numero){
		return null;
	}

	/*
	 *
	 */
	public String obtenerCantidadDireccionesAsignables2(String numero){
		return null;
	}

	/*
	 *
	 */
	public String obtenerRangoDireccionesAsignables(String numero){
		return null;
	}

	/*
	 *
	 */
	public String obtenerDireccionSubredDada(String numero){
		return null;
	}

	/*
	 *
	 */
	public String obtenerDireccionBroadcastSubredDada(String numero){
		return null;
	}

	//----------------------------------------------------------------------------------------------------------RESTO DE METODOS----------------------------------------------------------------------------------------------------------------------
}
