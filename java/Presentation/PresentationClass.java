package Presentation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

import businessLogic.Bll;
import dataAccess.ClientDAO;
import dataAccess.OrderDAO;
import dataAccess.ProductDAO;

public class PresentationClass {
	private static Bll b = new Bll();

	/**
	 * realizeaza crearea unui fisier de citire si apeleaza o metoda in care se
	 * realizeaza aceasta
	 * 
	 * @param args-parametrii primiti in linia de comanda
	 * @throws IOException       exceptie generala
	 * @throws DocumentException exceptie legata de folosirea documentelor
	 */
	public static void main(String args[]) throws IOException, DocumentException {
		File file = new File(args[0]);
		readData(file);
	}

	/**
	 * citirea intregului continut al fisierului si apelerea unor metode
	 * corespunzatoare unor cuvinte cheie citite
	 * 
	 * @param file fisierul din care se citeste
	 * @throws IOException       exceptie generala
	 * @throws DocumentException exceptie legata de folosirea documentelor
	 */
	public static void readData(File file) throws IOException, DocumentException {
		Scanner scan = new Scanner(file);
		while (scan.hasNextLine()) {
			String data = scan.nextLine();
			String[] sir = data.split(" ");
			if (sir[0].compareTo("Insert") == 0) {
				verifyQInsert(sir[0], sir[1], sir[2] + " " + sir[3] + " " + sir[4]);
			}
			if (sir[0].compareTo("Delete") == 0 && sir[1].compareTo("client:") == 00) {
				verifyQDelete(sir[0], sir[1], sir[2] + " " + sir[3] + " " + sir[4]);
			}
			if (sir[0].compareTo("Delete") == 0 && sir[1].compareTo("Product:") == 00) {
				verifyQDelete(sir[0], sir[1], sir[2]);
			}
			if (sir[0].compareTo("Order:") == 0) {
				verifyQOrder(sir[1] + " " + sir[2] + " " + sir[3] + " " + sir[4]);
			}
			if (sir[0].compareTo("Report") == 0) {
				verifyQReport(sir[1]);
			}
		}
		scan.close();
	}

	/**
	 * apeleaza metodele de inserarea a produselor si ale clientilor
	 * 
	 * @param w1 -element care ajuta la alegerea metodei apelate
	 * @param w2 -element care ajuta la alegerea metodei apelate
	 * @param w3 -valorile ce trebuie introduse in tabele
	 */

	public static void verifyQInsert(String w1, String w2, String w3) {

		if (w1.compareTo("Insert") == 0) {
			if (w2.compareTo("client:") == 0) {
				b.insertClientBll(w3);
			} else {
				b.insertProductBll(w3);
			}
		}

	}

	/**
	 * apeleaza metodele de stergere a produselor si ale clientilor
	 * 
	 * @param w1 -element care ajuta la alegerea metodei apelate
	 * @param w2 -element care ajuta la alegerea metodei apelate
	 * @param w3 -valorile ce arata care produs/client trebuie sa-l stergem
	 */
	public static void verifyQDelete(String w1, String w2, String w3) {

		if (w1.compareTo("Delete") == 0) {
			if (w2.compareTo("client:") == 0) {
				b.deleteClientBll(w3);
			} else {
				b.deleteProductBll(w3);
			}
		}

	}

	/**
	 * 
	 * @param w1-valorile ce trebuie introduse in tabelul Order
	 * @throws IOException       exceptie generala
	 * @throws DocumentException exceptie legata de folosirea documentelor
	 */
	public static void verifyQOrder(String w1) throws IOException, DocumentException {
		b.orderBll(w1);
	}

	/**
	 * 
	 * @param w1 sugereaza care dintre cele 3 tabele trebuie sa fie adaugate in
	 *           documentul pdf
	 * @throws FileNotFoundException exceptie legata de negasirea fisierului
	 * @throws DocumentException     exceptie legata de folosirea documentelor
	 */

	public static void verifyQReport(String w1) throws FileNotFoundException, DocumentException {
		if (w1.compareTo("client") == 0) {
			b.reportClient();
		}
		if (w1.compareTo("product") == 0) {
			b.reportProduct();
		}
		if (w1.compareTo("order") == 0) {
			b.reportOrder();
		}
	}

}
