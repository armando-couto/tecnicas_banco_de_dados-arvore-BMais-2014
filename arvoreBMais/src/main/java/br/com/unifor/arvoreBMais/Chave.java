package br.com.unifor.arvoreBMais;

/**
 * @author armandocouto
 * @email coutoarmando@gmail.com
 * @date 18/09/2013
 */
public class Chave extends Registro {

	private No direita;
	private No esquerda;

	public Chave() {
		super();
		direita = null;
		esquerda = null;
	}
	
	public Chave(int numero) {
		super(numero);
		direita = null;
		esquerda = null;
	}
	
	public No getDireita() {
		return direita;
	}
	public void setDireita(No direita) {
		this.direita = direita;
	}
	public No getEsquerda() {
		return esquerda;
	}
	public void setEsquerda(No esquerda) {
		this.esquerda = esquerda;
	}
}