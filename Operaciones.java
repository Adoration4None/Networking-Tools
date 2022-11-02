package co.edu.uniquindio.parcial2infra;

public class Operaciones {
	
	// ---------------------------------------- Entrada decimal -----------------------------------------------
	
	/**
	 * 
	 * @param decimal
	 * @return
	 */
	public String convertirDecimalBinario(String decimal) {
		// Como el resultado tiene que ser de 8 bits, las potencias solo llegan hasta 2^7
		int[] potencias = {128, 64, 32, 16, 8, 4, 2, 1};
		
		String binario = "";
		int valorDecimal = Integer.parseInt(decimal);
		
		for(int i = 0; i < potencias.length; i++) {
			if(valorDecimal >= potencias[i]) {
				binario += "1";
				valorDecimal -= potencias[i];
			}
			else binario += "0";
		}
		
		return binario;
	}
	
	/**
	 * 
	 * @param decimal
	 * @return
	 */
	public String convertirDecimalHexadecimal(String decimal) {
		// Si el resultado en binario debe ser de 8 bits, entonces en hexadecimal no supera los 2 digitos
		int[] potencias = {16, 1};
		
		String hexadecimal = "";
		int valorDecimal = Integer.parseInt(decimal);
		int digito;
		
		for(int i = 0; i < potencias.length; i++) {
			digito = valorDecimal / potencias[i];
			hexadecimal += completarHexadecimal(digito);
			valorDecimal -= potencias[i] * digito;
		}
		
		return hexadecimal;
	}
	
	// ------------------------------------------ Entrada binaria ---------------------------------------------
	
	/**
	 * 
	 * @param binario
	 * @return
	 */
	public String convertirBinarioDecimal(String binario) {
		// Como los binarios son de 8 bits, las potencias solo llegan hasta 2^7
		int[] potencias = {128, 64, 32, 16, 8, 4, 2, 1};
		
		int decimal = 0;
		
		if(binario.length() < 8) binario = completarCeros(binario);
		
		for(int i = 0; i < binario.length(); i++) {
			if( binario.charAt(i) == '1' ) decimal += potencias[i];
		}
		
		return Integer.toString(decimal);
	}

	/**
	 * 
	 * @param binario
	 * @return
	 */
	public String convertirBinarioHexadecimal(String binario) {
		// Como los binarios son de 8 bits, entonces en hexadecimal los numeros no superan los 2 digitos
		
		if(binario.length() < 8) binario = completarCeros(binario);
		
		String mitad1 = binario.substring(0, 4);
		String mitad2 = binario.substring(4, 8);
		
		String digito1 = convertirBinarioDecimal(mitad1);
		String digito2 = convertirBinarioDecimal(mitad2);
		
		return completarHexadecimal(Integer.parseInt(digito1)) + completarHexadecimal(Integer.parseInt(digito2));
	}
	
	// -------------------------------------- Entrada hexadecimal ----------------------------------------------
	
	/**
	 * 
	 * @param hexadecimal
	 * @return
	 */
	public String convertirHexaDecimal(String hexadecimal) {
		// Si el resultado en binario debe ser de 8 bits, entonces en hexadecimal no supera los 2 digitos
		int[] potencias = {16, 1};
		
		int decimal = 0;
		
		for(int i = 0; i < hexadecimal.length(); i++) {
			int digito = invertirHexadecimal(hexadecimal.charAt(i));
			decimal += (digito * potencias[i]);
		}
		
		return String.valueOf(decimal);
	}
	
	
	public String convertirHexadecimalBinario(String hexadecimal) {
		// Como los binarios son de 8 bits, entonces en hexadecimal los numeros no superan los 2 digitos
		
		int digito1 = invertirHexadecimal( hexadecimal.charAt(0) );
		int digito2 = invertirHexadecimal( hexadecimal.charAt(1) );
		
		String binarioMitad1 = convertirDecimalBinario( Integer.toString(digito1) );
		String binarioMitad2 = convertirDecimalBinario( Integer.toString(digito2) );
		
		return completarCeros( quitarCerosIzquierda(binarioMitad1) + quitarCerosIzquierda(binarioMitad2) );
	}


	// ------------------------------------------ Utilidades ---------------------------------------------------
	
	private String completarHexadecimal(int numero) {
		switch(numero) {
		case 10: return "A";
		case 11: return "B";
		case 12: return "C";
		case 13: return "D";
		case 14: return "E";
		case 15: return "F";
		default: return Integer.toString(numero);
		}
	}
	
	private int invertirHexadecimal(char digito) {
		switch( (digito+"").toUpperCase() ) {
		case "A": return 10;
		case "B": return 11;
		case "C": return 12;
		case "D": return 13;
		case "E": return 14;
		case "F": return 15;
		default: return Integer.parseInt( ""+digito);
		}
	}
	
	private String completarCeros(String binario) {
		for(int i = binario.length(); i < 8; i++ )
			binario = "0" + binario;
		
		return binario;
	}
	
	private String quitarCerosIzquierda(String binario) {
		int indicePrimerUno = encontrarPrimerUno(binario, 0);
		
		if(indicePrimerUno == -1) return "";
		return binario.substring(indicePrimerUno);
	}
	
	public String reducirBinario(String binario, int numeroBits) {
		int indicePartida = (binario.length() - numeroBits);
		
		return binario.substring(indicePartida);
	}

	private int encontrarPrimerUno(String binario, int i) {
		if(binario.charAt(i) == '1') return i;
		
		if(i+1 < binario.length()) return encontrarPrimerUno(binario, ++i);
		return -1;
	}

}
