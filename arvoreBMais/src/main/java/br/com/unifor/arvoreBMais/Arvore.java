package br.com.unifor.arvoreBMais;

/**
 * @author armandocouto
 * @email coutoarmando@gmail.com
 * @date 18/09/2013
 */
public class Arvore {

	private No raiz;
	private No primeiro;
	private int menorEspaco;
	private Ramo ramo;

	public Arvore() {
		init(0);
	}

	public Arvore(int t, int ramoTamanho) {
		init(t);
		this.ramo = new Ramo(ramoTamanho);
	}

	public void init(int t) {
		raiz = new No(t);
		primeiro = raiz;
		menorEspaco = Integer.MAX_VALUE;
	}

	public void adicionarNovoRamo(Registro x) {
		this.ramo.add(x.getNumero());
	}

	private boolean contains(Registro x) {
		return ramo.contains(x.getNumero());
	}

	public void inserir(Registro x) {
		this.adicionarNovoRamo(x);
		No curr = raiz;
		int i = -1;
		while (!curr.isFolha()) {
			i++;
			if (i >= curr.getTamanho()) {
				curr = ((Chave) curr.ultimo()).getDireita();
				i = -1;
			} else if (x.getNumero() <= curr.get(i).getNumero()) {
				curr = ((Chave) curr.get(i)).getEsquerda();
				i = -1;
			}
		}
		curr.inserir(x);

		if (curr.getMenorEspaco() < menorEspaco)
			setMenorEspaco(curr.getMenorEspaco());

		if (raiz.getPai() != null)
			raiz = raiz.getPai();
	}

	public boolean pesquisar(Registro x) {
		if (!contains(x)) {
			return false;
		} else {
			No curr = raiz;
			int i = -1;
			while (!curr.isFolha()) {
				i++;
				if (i >= curr.getTamanho()) {
					curr = ((Chave) curr.ultimo()).getDireita();
					i = -1;
				} else if (x.getNumero() <= curr.get(i).getNumero()) {
					curr = ((Chave) curr.get(i)).getEsquerda();
					i = -1;
				}
			}
			return curr.pesquisar(x);
		}
	}

	public int order(Registro x) {
		int counter = 0;
		int i = 0;
		No curr = raiz;

		while (!curr.isFolha()) {
			if ((i != curr.getTamanho()) && (x.getNumero() > curr.get(i).getNumero())) {
				counter += ((Chave) curr.get(i)).getEsquerda().getPeso();
			} else if (i == curr.getTamanho()) {
				curr = ((Chave) curr.ultimo()).getDireita();
				i = -1;
			} else {
				curr = ((Chave) curr.get(i)).getEsquerda();
				i = -1;
			}
			i++;
		}

		i = 0;
		while ((i != curr.getTamanho()) && (x.getNumero() >= curr.get(i).getNumero())) {
			counter++;
			i++;
		}

		return counter;
	}

	public String toString() {
		String ans = "";
		No curr = primeiro;
		while (curr != null) {
			ans = ans + "#" + curr.toString();
			curr = curr.getProximo();
		}
		ans = ans.substring(1);
		return ans;
	}

	public No getRaiz() {
		return raiz;
	}

	public void setRaiz(No raiz) {
		this.raiz = raiz;
	}

	public No getPrimeiro() {
		return primeiro;
	}

	public void setPrimeiro(No primeiro) {
		this.primeiro = primeiro;
	}

	public int getMenorEspaco() {
		return menorEspaco;
	}

	public void setMenorEspaco(int menorEspaco) {
		this.menorEspaco = menorEspaco;
	}

	public Ramo getRamo() {
		return ramo;
	}

	public void setRamo(Ramo ramo) {
		this.ramo = ramo;
	}
}