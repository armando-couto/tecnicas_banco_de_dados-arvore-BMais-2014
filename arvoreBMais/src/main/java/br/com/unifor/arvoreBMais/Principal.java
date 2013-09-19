package br.com.unifor.arvoreBMais;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * @author armandocouto
 * @email coutoarmando@gmail.com
 * @date 18/09/2013
 */
public class Principal {

	public static void main(String[] args) {

		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("C:" + File.separator));
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		if (chooser.showOpenDialog(null) == 0) {
			// Tamanho máximo dos nós da árvore.
			int tamanho = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite o tamanho da Árvore?"));
			// O número que deve ser ordenado.
			int numeroOrdenador = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite o número ordenador?"));
			Registro registro = new Registro();
			
			String arquivo = leituraArquivo(chooser.getSelectedFile());
			
			// array de strings que possuem os números
			String[] numerosDaArvore = arquivo.split(" ");
			
			Registro[] registros = new Registro[numerosDaArvore.length];
			
			Arvore arvore = new Arvore(tamanho, registros.length);
			// inicializa os registro e insere na árvore
			for (int i = 0; i < numerosDaArvore.length; i++) {
				registros[i] = new Registro(Integer.parseInt(numerosDaArvore[i]));
				if (numeroOrdenador == registros[i].getNumero()) {
					// o registro é encontrado e salvo
					registro = registros[i];
				}
				arvore.inserir(registros[i]);
			}
			System.out.println(arvore.toString());
			System.out.println(arvore.getMenorEspaco());
			System.out.println(arvore.order(registro));
		}
	}

	public static String leituraArquivo(File file) {
		String resultado = "";
		try {
			FileInputStream fis = new FileInputStream(file);
			DataInputStream dis = new DataInputStream(fis);
			BufferedReader br = new BufferedReader(new InputStreamReader(dis));
			
			String linha;
			// lendo arquivo linha a linha
			while ((linha = br.readLine()) != null) {
				resultado = resultado + linha + " ";
			}

			dis.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		return resultado;
	}
}