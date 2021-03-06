package businessLogic;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import dataAccess.*;
import model.*;

public class Bll {
	private ClientDAO c = new ClientDAO();
	public ProductDAO p = new ProductDAO();
	private OrderDAO o = new OrderDAO();
	ArrayList<Client> clist = new ArrayList<Client>();
	ArrayList<Product> plist = new ArrayList<Product>();
	ArrayList<Order> olist = new ArrayList<Order>();
	private int counterbill = 0;
	private int counterunderstock = 0;
	private int counterreportC = 0;
	private int counterreportP = 0;
	private int counterreportO = 0;

	/**
	 * defineste toate valorile pe care dorim sa le introduce si le adauga atat in
	 * tabel cu ajutorul metodei definite in clasa ClientDAO cat si intr-un
	 * ArrayList
	 * 
	 * @param data contine toate detaliile despre valorile pe care trebuie sa le
	 *             introducem in tabelul Client
	 */
	public void insertClientBll(String data) {
		int row = c.nrRowsClient() + 1;
		String[] sir = new String[10];
		sir = data.split(" ");
		Client c1 = new Client(row, sir[1].substring(0, sir[1].length() - 1), sir[0], sir[2]);
		clist.add(c1);
		String newclient = "";
		newclient = "('" + Integer.toString(row) + "','" + sir[1].substring(0, sir[1].length() - 1) + "','" + sir[0]
				+ "','" + sir[2] + "')";
		c.insertClient(newclient);
	}

	/**
	 * defineste toate valorile pe care dorim sa le introduce si le adauga atat in
	 * tabel cu ajutorul metodei definite in clasa ProductDAO cat si intr-un
	 * ArrayList
	 * 
	 * @param data contine toate detaliile despre valorile pe care trebuie sa le
	 *             introducem in tabelul Product
	 */

	public void insertProductBll(String data) {
		int row = p.nrRowsProduct() + 1;
		String[] sir = new String[10];
		sir = data.split(" ");
		Product p1 = new Product(row, sir[0].substring(0, sir[0].length() - 1),
				Integer.parseInt(sir[1].substring(0, sir[1].length() - 1)), Float.parseFloat(sir[2]));
		plist.add(p1);
		String newproduct = "";
		newproduct = "('" + Integer.toString(row) + "','" + sir[0].substring(0, sir[0].length() - 1) + "','"
				+ sir[1].substring(0, sir[1].length() - 1) + "','" + sir[2] + "')";
		int einstoc = p.findIDBYNameProduct(sir[0].substring(0, sir[0].length() - 1));
		if (einstoc == 0) {
			p.insertProduct(newproduct);
		} else {
			p.updateProduct(sir[0].substring(0, sir[0].length() - 1),
					Integer.parseInt(sir[1].substring(0, sir[1].length() - 1)));
		}
	}

	/**
	 * realizeaza stergerea atat din tabel cu ajutorul metodei definite in clasa
	 * ClientDAO cat si din ArrayList
	 * 
	 * @param data -detaliile clientului pe care dorim sa-l stergem
	 */

	public void deleteClientBll(String data) {
		String[] sir = new String[10];
		sir = data.split(" ");
		for (Client item : clist) {
			if (item.getlName() == sir[1]) {
				if (item.getfName() == sir[0]) {
					clist.remove(item);
				}
			}

		}
		sir[1] = sir[1].substring(0, sir[1].length() - 1);
		c.deleteClient(sir[1], sir[0]);
	}

	/**
	 * realizeaza stergerea atat din tabel cu ajutorul metodei definite in clasa
	 * ProductDAO cat si din ArrayList
	 * 
	 * @param data -detaliile produsului pe care dorim sa-l stergem
	 */
	public void deleteProductBll(String data) {
		for (Product item : plist) {
			if (item.getName() == data) {
				plist.remove(item);

			}

		}
		p.deleteProduct(data);
	}

	/**
	 * realizeaza adaugarea unei comenzi atat in tabel prin intermediul metodei din
	 * OrderDAO cat si intr-un ArrayList. definirea mai multor documente pdf care
	 * contin detaliile despre comenzile facute precum
	 * cumparatorul,produsul,cantitatea si suma totala sau in cazul in care stocul
	 * este mai mic decat cantitatea ceruta in documentul pdf va aparea un mesaj
	 * corespunzator.
	 * 
	 * @param data -valorile pe care trebuie sa le introducem
	 * @throws IOException       exceptie generala
	 * @throws DocumentException exceptie legata de folosirea documentelor
	 */
	public void orderBll(String data) throws IOException, DocumentException {
		int row = o.nrRowsOrder() + 1;
		Document doc = new Document();
		String[] sir = data.split(" ");
		Order o1 = new Order(row, sir[1].substring(0, sir[1].length() - 1), sir[0],
				sir[2].substring(0, sir[2].length() - 1), Integer.parseInt(sir[3]));
		olist.add(o1);
		String neworder = "('" + Integer.toString(row) + "','" + sir[1].substring(0, sir[1].length() - 1) + "','"
				+ sir[0] + "','" + sir[2].substring(0, sir[2].length() - 1) + "','" + sir[3] + "')";
		if (p.findQBYNameProduct(sir[2].substring(0, sir[2].length() - 1)) >= Integer.parseInt(sir[3])) {
			int cantitate = 0 - Integer.parseInt(sir[3]);
			float price = p.findPBYNameProduct(sir[2].substring(0, sir[2].length() - 1));
			price = price * (0 - cantitate);
			p.updateProduct(sir[2].substring(0, sir[2].length() - 1), cantitate);
			o.insertOrder(neworder);
			counterbill++;
			String namebill = "bill" + counterbill + ".pdf";
			PdfWriter.getInstance(doc, new FileOutputStream(namebill));
			doc.open();
			Chunk message = new Chunk(sir[1].substring(0, sir[1].length() - 1) + " " + sir[0] + " a comandat" + " "
					+ sir[3] + " " + sir[2].substring(0, sir[2].length() - 1) + "-Total= " + price + " RON");
			doc.add(message);
			doc.close();
		} else {
			counterunderstock++;
			String nameunderstock = "understock" + counterunderstock + ".pdf";
			PdfWriter.getInstance(doc, new FileOutputStream(nameunderstock));
			doc.open();
			Chunk message = new Chunk(sir[1].substring(0, sir[1].length() - 1) + " " + sir[0] + " a comandat" + " "
					+ sir[3] + " " + sir[2].substring(0, sir[2].length() - 1) + " -Stoc epuizat");
			doc.add(message);
			doc.close();
		}
	}

	/**
	 * se creeaza documente pdf care contin intreg tabelul Client
	 * 
	 * @throws FileNotFoundException exceptie legata de negasirea fisierului
	 * @throws DocumentException     exceptie legata de folosirea documentelor
	 */
	public void reportClient() throws FileNotFoundException, DocumentException {
		Document document = new Document();
		String[][] tClient = c.selectAllClients();
		int rows = c.countRowsClient();
		counterreportC++;
		String nameR = "ReportClient" + counterreportC + ".pdf";
		PdfWriter.getInstance(document, new FileOutputStream(nameR));
		document.open();
		PdfPTable table = new PdfPTable(4);
		addTableHeaderClient(table);
		for (int i = 0; i < rows; i++) {
			table.addCell(tClient[i][0]);
			table.addCell(tClient[i][1]);
			table.addCell(tClient[i][2]);
			table.addCell(tClient[i][3]);
		}
		document.add(table);
		document.close();
	}

	/**
	 * se creeaza documente pdf care contin intreg tabelul Product
	 * 
	 * @throws FileNotFoundException exceptie legata de negasirea fisierului
	 * @throws DocumentException     exceptie legata de folosirea documentelor
	 */

	public void reportProduct() throws FileNotFoundException, DocumentException {
		Document document = new Document();
		String[][] tProduct = p.selectAllProducts();
		int rows = p.countRowsProduct();
		counterreportP++;
		String nameR = "ReportProduct" + counterreportP + ".pdf";
		PdfWriter.getInstance(document, new FileOutputStream(nameR));
		document.open();
		PdfPTable table = new PdfPTable(4);
		addTableHeaderProduct(table);
		for (int i = 0; i < rows; i++) {
			table.addCell(tProduct[i][0]);
			table.addCell(tProduct[i][1]);
			table.addCell(tProduct[i][2]);
			table.addCell(tProduct[i][3]);
		}
		document.add(table);
		document.close();

	}

	/**
	 * se creeaza documente pdf care contin intreg tabelul Order
	 * 
	 * @throws FileNotFoundException xceptie legata de negasirea fisierului
	 * @throws DocumentException     exceptie legata de folosirea documentelor
	 */

	public void reportOrder() throws FileNotFoundException, DocumentException {
		Document document = new Document();
		String[][] tOrder = o.selectAllOrders();
		int rows = o.countRowsOrder();
		counterreportO++;
		String nameR = "ReportOrder" + counterreportO + ".pdf";
		PdfWriter.getInstance(document, new FileOutputStream(nameR));
		document.open();
		PdfPTable table = new PdfPTable(4);
		addTableHeaderProduct(table);
		for (int i = 0; i < rows; i++) {
			table.addCell(tOrder[i][0]);
			table.addCell(tOrder[i][1]);
			table.addCell(tOrder[i][2]);
			table.addCell(tOrder[i][3]);
			table.addCell(tOrder[i][4]);
		}
		document.add(table);
		document.close();

	}

	/**
	 * adaugarea in tabel a tuturor coloanelor si liniilor din tabelul Client
	 * 
	 * @param table tabelul nou creat ce se va afisa in documentul pdf
	 */

	private void addTableHeaderClient(PdfPTable table) {
		String[] title = new String[10];
		title[0] = "idC";
		title[1] = "lName";
		title[2] = "fName";
		title[3] = "city";
		for (int i = 0; i < title.length - 2; i++) {
			PdfPCell header = new PdfPCell();
			header.setBackgroundColor(BaseColor.GRAY);
			header.setBorderWidth(2);
			header.setPhrase(new Phrase(title[i]));
			table.addCell(header);
		}
	}

	/**
	 * adaugarea in tabel a tuturor coloanelor si liniilor din tabelul Product
	 * 
	 * @param table tabelul nou creat ce se va afisa in documentul pdf
	 */

	private void addTableHeaderProduct(PdfPTable table) {
		String[] title = new String[10];
		title[0] = "idP";
		title[1] = "name";
		title[2] = "quantity";
		title[3] = "price";
		for (int i = 0; i < title.length - 2; i++) {
			PdfPCell header = new PdfPCell();
			header.setBackgroundColor(BaseColor.GRAY);
			header.setBorderWidth(2);
			header.setPhrase(new Phrase(title[i]));
			table.addCell(header);
		}
	}

	/**
	 * adaugarea in tabel a tuturor coloanelor si liniilor din tabelul Order
	 * 
	 * @param table tabelul nou creat ce se va afisa in documentul pdf
	 */
	private void addTableHeaderOrder(PdfPTable table) {
		String[] title = new String[10];
		title[0] = "idO";
		title[1] = "lName";
		title[2] = "fName";
		title[3] = "product";
		title[3] = "quantity";
		for (int i = 0; i < title.length - 2; i++) {
			PdfPCell header = new PdfPCell();
			header.setBackgroundColor(BaseColor.GRAY);
			header.setBorderWidth(2);
			header.setPhrase(new Phrase(title[i]));
			table.addCell(header);
		}
	}

}
