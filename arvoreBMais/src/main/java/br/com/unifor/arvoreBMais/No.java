package br.com.unifor.arvoreBMais;

import java.util.Vector;

/**
 * @author armandocouto
 * @email coutoarmando@gmail.com
 * @date 18/09/2013
 */
public class No {

	private boolean folha;
	private Vector<Registro> listaInterna;
	private No proximo;
	private No anterior;
	private No pai;
	private int tamanho;
	private int tamanhoContador;
	private int menorEspaco = Integer.MAX_VALUE;
	private int peso;

	public No(int tamanho) {
		this.folha = true;
		this.listaInterna = new Vector<Registro>();
		this.tamanho = tamanho;
		this.proximo = null;
		this.anterior = null;
		this.tamanhoContador = 0;
		this.pai = null;
		this.peso = 0;
	}

	public No(boolean folha, int tamanho) {
		this.folha = folha;
		if (this.folha) {
			this.listaInterna = new Vector<Registro>();
			proximo = null;
		} else {
			this.listaInterna = new Vector<Registro>();
		}
		this.tamanho = tamanho;
		this.tamanhoContador = 0;
		this.anterior = null;
		this.pai = null;
		this.peso = 0;
	}

	public boolean isEmpty() {
		return listaInterna.isEmpty();
	}

	public boolean isFull() {
		return tamanhoContador == tamanho;
	}

	public boolean pesquisar(Registro x) {
		return listaInterna.contains(x);
	}

	public Registro get(int i) {
		return listaInterna.get(i);
	}

	public Registro primeiro() {
		return listaInterna.firstElement();
	}

	public Registro ultimo() {
		return listaInterna.lastElement();
	}

	public void pesoIncrementa() {
		this.peso++;
	}

	public void pesoDecrementa() {
		this.peso--;
	}

	public int tamanhoLista() {
		return listaInterna.size();
	}

	/**
	 * 
	 * @param x
	 */
	public void inserir(Registro x) {
		if (isEmpty()) {
			listaInterna.insertElementAt(x, 0);
			tamanhoContador++;
		} else {
			boolean inserted = false;
			int i = -1;
			while (!inserted) {
				i++;
				if (i >= tamanhoContador) {
					listaInterna.insertElementAt(x, i);
					tamanhoContador++;
					inserted = true;
				} else if ((get(i) != null) && (x.getNumero() < get(i).getNumero())) {
					listaInterna.insertElementAt(x, i);
					if (!isFolha()) {
						((Chave) get(i + 1)).setEsquerda(((Chave) get(i)).getDireita());
					}
					inserted = true;
					tamanhoContador++;
				}
			}

			if (folha) {
				int esquerdaMin = Integer.MAX_VALUE;
				int direitaMin = Integer.MAX_VALUE;
				if (primeiro() != x) {
					esquerdaMin = Math.abs(get(i - 1).getNumero() - x.getNumero());
				}

				else if (anterior != null) {
					esquerdaMin = Math.abs(x.getNumero() - anterior.ultimo().getNumero());
				}

				if (ultimo() != x) {
					direitaMin = Math.abs(get(i + 1).getNumero() - x.getNumero());
				}

				else if (proximo != null) {
					direitaMin = Math.abs(x.getNumero() - proximo.primeiro().getNumero());
				}
				menorEspaco = Math.min(menorEspaco, Math.min(esquerdaMin, direitaMin));
			}
		}
		if (folha) {
			this.pesoIncrementa();
			No curr = (No) pai;
			while (curr != null) {
				curr.pesoIncrementa();
				curr = curr.getPai();
			}
		}
		if (isFull())
			Split();
	}

	/**
	 * 
	 * @param i
	 * @return Registro
	 */
	public Registro remove(int i) {
		tamanhoContador--;
		return listaInterna.remove(i);
	}

	private void Split() {
		No b = new No(folha, tamanho);
		int i;

		if (tamanhoLista() % 2 == 0)
			i = tamanhoLista() / 2;
		else
			i = tamanhoLista() / 2 + 1;

		while (i != tamanhoLista()) {
			if (!folha)
				setPeso(peso - ((Chave) get(i)).getDireita().getPeso());
			else
				this.pesoDecrementa();
			b.inserir(this.remove(i));
		}

		Chave k = new Chave(listaInterna.lastElement().getNumero());
		k.setEsquerda(this);
		k.setDireita(b);
		int w = 0;
		i = 0;

		if (!b.isFolha()) {
			while (i != b.tamanhoLista()) {
				((Chave) b.get(i)).getEsquerda().setPai(b);
				w += ((Chave) b.get(i)).getEsquerda().getPeso();
				i++;
			}
			((Chave) b.ultimo()).getDireita().setPai(b);
			w += ((Chave) b.ultimo()).getDireita().getPeso();
			b.setPeso(w);
		}

		if (folha) {
			b.setProximo(this.getProximo());
			setProximo(b);
			b.setAnterior(this);
		} else {
			setPeso(peso - ((Chave)ultimo()).getDireita().getPeso());
			listaInterna.remove(listaInterna.lastElement());
			tamanhoContador--;
		}

		if (pai == null) { 
			No f = new No(false, tamanho);
			f.inserir(k);
			setPai(f); 
			b.setPai(f);
			pai.setPeso(this.peso + b.peso);
		} else {
			b.setPai(pai);
			pai.inserir(k);
		}
	}

	public String toString() {
		String ans = "";

		for (int i = 0; i < listaInterna.size(); i++) {
			if (get(i) != null)
				ans = ans + "," + get(i).toString();
		}
		ans = ans.substring(1);

		return ans;
	}

	public boolean isFolha() {
		return folha;
	}

	public void setFolha(boolean isFolha) {
		this.folha = isFolha;
	}

	public Vector<Registro> getListaInterna() {
		return listaInterna;
	}

	public void setListaInterna(Vector<Registro> listaInterna) {
		this.listaInterna = listaInterna;
	}

	public No getProximo() {
		return proximo;
	}

	public void setProximo(No proximo) {
		this.proximo = proximo;
	}

	public No getAnterior() {
		return anterior;
	}

	public void setAnterior(No anterior) {
		this.anterior = anterior;
	}

	public No getPai() {
		return pai;
	}

	public void setPai(No pai) {
		this.pai = pai;
	}

	public int getTamanho() {
		return tamanho;
	}

	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}

	public int getTamanhoContador() {
		return tamanhoContador;
	}

	public void setTamanhoContador(int tamanhoContador) {
		this.tamanhoContador = tamanhoContador;
	}

	public int getMenorEspaco() {
		return menorEspaco;
	}

	public void setMenorEspaco(int menorEspaco) {
		this.menorEspaco = menorEspaco;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}
}