package br.com.unifor.arvoreBMais;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.swing.JFileChooser;

/**
 * @author armandocouto
 * @email coutoarmando@gmail.com
 * @date 18/09/2013
 */
public class Principal {

	public static void main(String[] args) {

		JFileChooser chooser = new JFileChooser();
		// Diretório raiz.
		chooser.setCurrentDirectory(new File("C:" + File.separator));
		// restringe a amostra a diretorios apenas.
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		if (chooser.showOpenDialog(null) == 0) {
			
		}

		int t = 4; // Tamanho
		int oNum = 3;
		Registro oRegistro = new Registro();

		String inputFile = readFromFile(chooser.getSelectedFile());

		String[] numbers = inputFile.split(" ");

		Registro[] Registros = new Registro[numbers.length];

		Arvore arvore = new Arvore(t, Registros.length);
		for (int i = 0; i < numbers.length; i++) {
			Registros[i] = new Registro(Integer.parseInt(numbers[i]));
			if (oNum == Registros[i].getNumero())
				oRegistro = Registros[i];
			arvore.inserir(Registros[i]);
		}
		System.out.println(arvore.toString());
		System.out.println(arvore.getMenorEspaco());
		System.out.println(arvore.order(oRegistro));
	}

	public static String readFromFile(File tFile) {
		String tContent = "";
		try {

			FileInputStream fstream = new FileInputStream(tFile);
			DataInputStream in = new DataInputStream(fstream);

			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			while ((strLine = br.readLine()) != null) {
				tContent = tContent + strLine + " ";
			}

			in.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		return tContent;
	}
}