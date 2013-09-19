package br.com.unifor.arvoreBMais;

/**
 * @author armandocouto
 * @email coutoarmando@gmail.com
 * @date 18/09/2013
 */
public class Ramo {

	private boolean[] flags;
	private int a;
	private int b;

	public Ramo(int n) {
		flags = new boolean[4 * n];
		for (int i = 0; i < flags.length; i++) {
			flags[i] = false;
		}
		a = (int) (Math.random() * 100); // 1-100
		b = (int) (Math.random() * 100);// 1-100
	}

	private int[] functions(int x) {
		int[] ans = new int[4];
		ans[0] = ((a * x + b) % 29) % flags.length;
		ans[1] = ((a * x + b) % 311) % flags.length;
		ans[2] = ((a * x + b) % 571) % flags.length;
		ans[3] = ((a * x + b) % 739) % flags.length;
		return ans;
	}

	public void add(int x) {
		int[] index = functions(x);
		for (int i = 0; i < index.length; i++)
			flags[index[i]] = true;
	}

	public boolean contains(int x) {
		boolean ans = true;
		int[] index = functions(x);
		for (int i = 0; i < index.length; i++)
			ans = ans && flags[index[i]];
		return ans;
	}
}