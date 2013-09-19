package br.com.unifor.arvoreBMais;

/**
 * @author armandocouto
 * @email coutoarmando@gmail.com
 * @date 18/09/2013
 */
public class Arvore {

	private No raiz; // raiz da árvore
	private No primeiro; // primeira folha
	private int menorEspaco; // menor espaço na árvore
	private Ramo ramo; // ramo da árvore

	public Arvore() {
		init(0);
	}

	public Arvore(int t, int ramoTamanho) {
		init(t);
		// inicializando o ramo
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
		// add o registro o registro p/ o ramo também
		this.adicionarNovoRamo(x);
		No curr = raiz;
		int i = -1;
		// percorre a árvore até chegar a uma folha
		while (!curr.isFolha()) {
			i++;
			// fim da folha
			if (i >= curr.getTamanho()) {
				curr = ((Chave) curr.ultimo()).getDireita();
				i = -1;
			} else if (x.getNumero() <= curr.get(i).getNumero()) { 
				// encontramos a chave a direita
				curr = ((Chave) curr.get(i)).getEsquerda();
				i = -1;
			}
		}
		// insere x na folha, se divide, se necessário
		curr.inserir(x);

		// ajusta MenorEspaco, se preciso
		if (curr.getMenorEspaco() < menorEspaco)
			setMenorEspaco(curr.getMenorEspaco());

		// ajusta o nó, se preciso
		if (raiz.getPai() != null)
			raiz = raiz.getPai();
	}

	public boolean pesquisar(Registro x) {
		// checa o ramo para este registro
		if (!contains(x)) {
			return false;
		} else {
			// assegurando que a árvore não contem x
			No curr = raiz;
			int i = -1;
			// percorrendo a árvore até chegar a folha
			while (!curr.isFolha()) {
				i++;
				// fim do nó
				if (i >= curr.getTamanho()) {
					curr = ((Chave) curr.ultimo()).getDireita();
					i = -1;
				} else if (x.getNumero() <= curr.get(i).getNumero()) {
					// encontramos a chave à direita
					curr = ((Chave) curr.get(i)).getEsquerda();
					i = -1;
				}
			}
			// retorna true, se o nó contém x
			return curr.pesquisar(x);
		}
	}

	public int order(Registro x) {
		int counter = 0;
		int i = 0;
		No curr = raiz;

		// percorre a árvore até chegar a uma das folhas
		while (!curr.isFolha()) {
			if ((i != curr.getTamanho()) && (x.getNumero() > curr.get(i).getNumero())) {
				counter += ((Chave) curr.get(i)).getEsquerda().getPeso();
			} else if (i == curr.getTamanho()) { // fim do nó
				// addiciona todo o peso do filho esquerdo
				curr = ((Chave) curr.ultimo()).getDireita();
				i = -1;
			} else {
				// chave à direita é encontrada
				curr = ((Chave) curr.get(i)).getEsquerda();
				i = -1;
			}
			i++;
		}

		i = 0;
		while ((i != curr.getTamanho()) && (x.getNumero() >= curr.get(i).getNumero())) {
			// estamos dentro da folha direita, contar os registros até chegar x
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