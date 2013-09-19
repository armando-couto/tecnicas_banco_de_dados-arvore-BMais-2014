package br.com.unifor.arvoreBMais;

/**
 * @author armandocouto
 * @email coutoarmando@gmail.com
 * @date 18/09/2013
 */
public class Ramo {

	private boolean[] flags; // array de booleanos que representas as flags
	private int a; // radom de 1 a 100 usado para as funções hash
	private int b; // radom de 1 a 100 usado para as funções hash

	public Ramo(int n) {
		// inicializa o tamanho do array com um tamanho de 4*n
		flags = new boolean[4 * n]; 
		for (int i = 0; i < flags.length; i++) {
			// inicializa os campos com valor false
			flags[i] = false;
		}
		a = (int) (Math.random() * 100); // 1-100
		b = (int) (Math.random() * 100); // 1-100
	}

	// funções hash
	private int[] functions(int x) { 
		int[] ans = new int[4];
		ans[0] = ((a * x + b) % 29) % flags.length;
		ans[1] = ((a * x + b) % 311) % flags.length;
		ans[2] = ((a * x + b) % 571) % flags.length;
		ans[3] = ((a * x + b) % 739) % flags.length;
		// retorna 4 índices que estavam implícitos nas funções hash
		return ans;
	}

	// add valor ao ramo
	public void add(int x) {
		// obtem os indices
		int[] index = functions(x);
		for (int i = 0; i < index.length; i++) {
			// muda a flag para true
			flags[index[i]] = true;
		}
	}

	public boolean contains(int x) {
		boolean ans = true;
		int[] index = functions(x); // obtem os índices
		for (int i = 0; i < index.length; i++) {
			ans = ans && flags[index[i]];
		}
		// false se não existir, true se existir(probabilidade muito elevada)
		return ans; 
	}
}