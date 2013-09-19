package br.com.unifor.arvoreBMais;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author armandocouto
 * @email coutoarmando@gmail.com
 * @date 18/09/2013
 */
public class Principal {

	public static void main(String[] args) {

		if (args.length == 4) {
			String tInputFileName = args[0];
			int t = Integer.parseInt(args[1]);
			int oNum = Integer.parseInt(args[2]);
			Registro oRegistro = new Registro();

			String tOutputFileName = args[3];
			
			String inputFile = readFromFile(tInputFileName).trim();
			
			String[] numbers = inputFile.split(" ");
			
			Registro[] Registros = new Registro[numbers.length];
			
			Arvore arvore = new Arvore(t, Registros.length);
			for (int i = 0; i < numbers.length; i++) {
				Registros[i] = new Registro(Integer.parseInt(numbers[i]));
				if (oNum == Registros[i].getNumero())
					oRegistro = Registros[i];
				arvore.inserir(Registros[i]);
			}
			String Content = "" + arvore.toString() + "\n" + arvore.getMenorEspaco() + "\n" + arvore.order(oRegistro);
			System.out.println(arvore.toString());
			System.out.println(arvore.getMenorEspaco());
			System.out.println(arvore.order(oRegistro));

			writeToFile(tOutputFileName, Content);
		} else {
			System.out.println("No args were given , or more than four args");
		}
	}

	public static String readFromFile(String fileName) {
		String tContent = "";
		try {

			File tFile = new File(fileName);

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

	public static void writeToFile(String fileName, String Content) {
		try {
			File tFile = new File(fileName);

			if (!tFile.exists()) {
				tFile.createNewFile();
			}

			FileWriter fw = new FileWriter(tFile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(Content);

			bw.close();
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
}