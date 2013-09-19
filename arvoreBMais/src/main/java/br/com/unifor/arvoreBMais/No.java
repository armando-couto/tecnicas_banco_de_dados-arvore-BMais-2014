package br.com.unifor.arvoreBMais;

import java.util.Vector;

/**
 * @author armandocouto
 * @email coutoarmando@gmail.com
 * @date 18/09/2013
 */
public class No {

	private boolean folha; // diz se o nó é folha ou não
	private Vector<Registro> listaInterna; // registros internos do nó
	private No proximo; // próxima folha
	private No anterior; // folha anterior
	private No pai; // pai do nó
	private int tamanho; // tamanho do nó
	private int tamanhoContador; // menor espaço do nó
	private int menorEspaco = Integer.MAX_VALUE;
	private int peso; // quantos registros pertencem ao nó

	// inicializa o nó com o tamanho máximo de t
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

	public No(boolean folha, int tamanho) { // inicializa o nó considerando se é
											// uma folha ou não
		this.folha = folha;
		if (this.folha) {
			this.listaInterna = new Vector<Registro>(); // quando o nó é uma
														// folha, o vetor possui
														// registros
			proximo = null;
		} else {
			this.listaInterna = new Vector<Registro>(); // quando o nó não é uma
														// folha, o vetor possui
														// chaves
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
		if (isEmpty()) { // o nó está vazio, apenas é inserido
			listaInterna.insertElementAt(x, 0);
			tamanhoContador++; // ajusta o tamanho
		} else {
			boolean inserted = false;
			int i = -1;
			while (!inserted) { // Continuar até que o registro seja inserido
				i++;
				if (i >= tamanhoContador) { // fim do vetor
					listaInterna.insertElementAt(x, i);
					tamanhoContador++; // ajusta o tamanho
					inserted = true; // inseriu
				} else if ((get(i) != null) && (x.getNumero() < get(i).getNumero())) { // lugar
																						// certo
					listaInterna.insertElementAt(x, i);
					if (!isFolha()) {
						((Chave) get(i + 1)).setEsquerda(((Chave) get(i)).getDireita());
					}
					inserted = true; // inseriu
					tamanhoContador++; // ajusta o tamanho
				}
			}

			// ajustar a distância mínima, se necessário
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
		// Ajuste de peso
		if (folha) {
			this.pesoIncrementa();
			No curr = (No) pai;
			while (curr != null) {
				curr.pesoIncrementa();
				curr = curr.getPai();
			}
		}
		if (isFull())
			Split(); // O nó é agora completo, a divisão tem que acontecer
	}

	/**
	 * 
	 * @param i
	 * @return Registro
	 */
	public Registro remove(int i) { // remove um registro específico
		tamanhoContador--; // ajusta o tamanho
		return listaInterna.remove(i);
	}

	private void Split() { // só acontece em uma folha cheia!
		No b = new No(folha, tamanho); // irmão
		int i;

		if (tamanhoLista() % 2 == 0) // o tamanho é um número duplo, os cortar
										// ao meio
			i = tamanhoLista() / 2;
		else
			// o tamanho é um número ímpar, a maioria dos registros permanecem
			// no nó antigo
			i = tamanhoLista() / 2 + 1;

		while (i != tamanhoLista()) {
			if (!folha)
				setPeso(peso - ((Chave) get(i)).getDireita().getPeso()); // ajusta
																			// o
																			// peso
																			// de
																			// uma
																			// chave
			else
				this.pesoDecrementa(); // ajusta o peso de um registro
			b.inserir(this.remove(i)); // remove do antigo nó e insere no novo
										// nó
		}
		// Após este loop, 'a' é cortado a metade e 'b' tem a outra metade
		Chave k = new Chave(listaInterna.lastElement().getNumero()); // cria
																		// nova
																		// chave
																		// depois
																		// de
																		// seu
																		// sucessor;
		k.setEsquerda(this); // seta o filho esquerdo da chave
		k.setDireita(b); // seta o filho direito da chave
		int w = 0; // ajustando o peso de b
		i = 0;

		// cuidando de b
		if (!b.isFolha()) {
			while (i != b.tamanhoLista()) {
				((Chave) b.get(i)).getEsquerda().setPai(b); // Atribui um novo
															// pai para o filho
															// esquerdo
				w += ((Chave) b.get(i)).getEsquerda().getPeso(); // Conta o peso
				i++;
			}
			((Chave) b.ultimo()).getDireita().setPai(b); // Atribui um novo pai
															// para o filho
															// direito
			w += ((Chave) b.ultimo()).getDireita().getPeso(); // conta o peso
			b.setPeso(w); // seta o peso de b
		}

		if (folha) { // Se b é uma folha ajustar suas folhas seguintes e
						// anteriores
			b.setProximo(this.getProximo());
			setProximo(b);
			b.setAnterior(this);
		} else { // o nó é interno, é preciso remover a chave
			setPeso(peso - ((Chave) ultimo()).getDireita().getPeso()); // ajusta
																		// o
																		// peso
			listaInterna.remove(listaInterna.lastElement()); // remove a última
																// chave
			tamanhoContador--; // ajusta o tamanho
		}

		if (pai == null) { // não há pai para este nó, provavelmente é a raiz
			No f = new No(false, tamanho); // cria um novo nó para ser o pai
			f.inserir(k); // insere a chave para o novo pai, faz divisão, se
							// necessário
			setPai(f); // seta o pai do nó
			b.setPai(f); // mesmo pai para o irmão
			pai.setPeso(this.peso + b.peso);
		} else { // já é um pai
			b.setPai(pai);
			pai.inserir(k); // recursivo se pai está cheio
		}
	}

	// imprime os registros internos
	public String toString() {
		String ans = "";

		for (int i = 0; i < listaInterna.size(); i++) {
			if (get(i) != null)
				ans = ans + "-" + get(i).getNumero();
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