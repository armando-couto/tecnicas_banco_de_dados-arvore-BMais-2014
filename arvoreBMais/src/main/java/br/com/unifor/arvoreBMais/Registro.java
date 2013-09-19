package br.com.unifor.arvoreBMais;

/**
 * @author armandocouto
 * @email coutoarmando@gmail.com
 * @date 18/09/2013
 */
public class Registro {

	private int numero;
	
	public Registro() {
		numero = 0;
	}
	
	public Registro(int numero) {
		this.numero = numero;
	}
	
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}
}