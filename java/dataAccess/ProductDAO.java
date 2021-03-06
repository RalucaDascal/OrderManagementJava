package dataAccess;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Client;
import model.Product;

public class ProductDAO {
	private Connection conn = ConnectionFactory.getConnection();

	/**
	 * aceasta metoda realizeaza si executa secventa SQL pentru adaugarea unui
	 * produs in tabel
	 * 
	 * @param Data-valorile pe care le introducem in tabel
	 */
	public void insertProduct(String Data) {
		Connection conn = ConnectionFactory.getConnection();
		Field[] atribut = Product.class.getDeclaredFields();
		int i = 0;
		String secv = "(";
		for (i = 0; i < atribut.length - 1; i++) {

			secv = secv + "`" + atribut[i].getName() + "`" + ", ";
		}
		secv = secv + "`" + atribut[atribut.length - 1].getName() + "`" + ")";
		Statement st;
		try {
			st = conn.createStatement();
			String query = "INSERT INTO `order`.`" + Product.class.getSimpleName() + "`  " + secv + " VALUES " + Data;
			st.executeUpdate(query);
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	/**
	 * aceasta metoda realizeaza si executa secventa SQL pentru cautarea unui produs
	 * stiind denumirea acestuia
	 * 
	 * @param name- denumirea produsului cautat
	 * @return identificatorul unic al produsului cu denumirea data
	 */
	public int findIDBYNameProduct(String name) {
		Connection conn = ConnectionFactory.getConnection();
		Statement st;
		try {
			st = conn.createStatement();
			String query = "SELECT * FROM order." + Product.class.getSimpleName() + " WHERE name=\"" + name + "\"";
			ResultSet r = st.executeQuery(query);
			int idC = 0;
			while (r.next()) {
				idC = r.getInt(1);
			}
			return idC;
		} catch (SQLException se) {
			se.printStackTrace();
		}
		return 0;
	}

	/**
	 * aceasta metoda realizeaza si executa secventa SQL pentru cautarea unui produs
	 * stiind denumirea acestuia
	 * 
	 * @param name- denumirea produsului cautat
	 * @return cantitatea pe care o avem in stoc a produsului cu denumirea data
	 */

	public int findQBYNameProduct(String name) {
		Connection conn = ConnectionFactory.getConnection();
		Statement st;
		try {
			st = conn.createStatement();
			String query = "SELECT * FROM order." + Product.class.getSimpleName() + " WHERE name=\"" + name + "\"";
			ResultSet r = st.executeQuery(query);
			int q = 0;
			while (r.next()) {
				q = r.getInt(3);
			}
			return q;
		} catch (SQLException se) {
			se.printStackTrace();
		}
		return 0;
	}

	/**
	 * aceasta metoda realizeaza si executa secventa SQL pentru cautarea unui produs
	 * stiind denumirea acestuia
	 * 
	 * @param name- denumirea produsului cautat
	 * @return pretul produsului cu denumirea data
	 */

	public float findPBYNameProduct(String name) {
		Connection conn = ConnectionFactory.getConnection();
		Statement st;
		try {
			st = conn.createStatement();
			String query = "SELECT * FROM order." + Product.class.getSimpleName() + " WHERE name=\"" + name + "\"";
			ResultSet r = st.executeQuery(query);
			float q = 0;
			while (r.next()) {
				q = r.getFloat(4);
			}
			return q;
		} catch (SQLException se) {
			se.printStackTrace();
		}
		return 0;
	}

	/**
	 * aceasta metoda realizeaza si executa secventa SQL pentru actualizarea
	 * cantitatii unui produs in cazul in care acesta se mai adauga in stoc sau este
	 * comandat
	 * 
	 * @param name- denumirea produsului actualizat
	 * @param quan- este cantitatea pe care trebuie sa o adaugam sau sa o scadem din
	 *              cantitatea actuala in functie de cazul in care ne aflam
	 */

	public void updateProduct(String name, int quan) {
		Connection conn = ConnectionFactory.getConnection();
		int idP = findIDBYNameProduct(name);
		int q = findQBYNameProduct(name);
		q = q + quan;
		Statement st;
		try {
			st = conn.createStatement();
			String query = "UPDATE `order`.`" + Product.class.getSimpleName() + "` SET quantity=  " + q + " WHERE idP= "
					+ idP;
			st.executeUpdate(query);
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	/**
	 * aceasta metoda realizeaza si executa secventa SQL pentru stergerea unui
	 * produs din tabel
	 * 
	 * @param name- denumirea produsului pe care dorim sa-l stergem
	 * 
	 */

	public void deleteProduct(String name) {
		Connection conn = ConnectionFactory.getConnection();
		Statement st;
		try {
			st = conn.createStatement();
			String query = "Delete FROM order." + Product.class.getSimpleName() + " WHERE name=\"" + name + "\"";
			st.executeUpdate(query);
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	/**
	 * aceasta metoda va realiza si executa o instructiune SQL care va determina
	 * identificator ultimului produs din tabel
	 * 
	 * @return identificatorul ultimului produs adaugat
	 */

	public int nrRowsProduct() {
		try {
			Statement st = conn.createStatement();
			String query = "SELECT * FROM PRODUCT";
			ResultSet r = st.executeQuery(query);
			int idaux = 0;
			while (r.next()) {
				idaux = r.getInt(1);
			}
			return idaux;
		} catch (SQLException se) {
			se.printStackTrace();
		}
		return 0;
	}

	/**
	 * aceasta metoda va realiza si executa o instructiune SQL pentru numararea
	 * liniilor din tabel Product
	 * 
	 * @return numarul de linii din tabelul Product, adica numarul de produse
	 *         existente la un moment dat in tabel
	 */

	public int countRowsProduct() {
		Connection conn = ConnectionFactory.getConnection();
		try {
			Statement st = conn.createStatement();
			String query = "SELECT Count(*) FROM order.product";
			ResultSet r = st.executeQuery(query);
			int nr = 0;
			while (r.next()) {
				nr = r.getInt(1);
			}
			return nr;
		} catch (SQLException se) {
			se.printStackTrace();
		}
		return 0;
	}

	/**
	 * aceasta metoda va realiza si executa o instructiune SQL pentru selectarea
	 * tuturor liniilor si a coloanelor din tabelul Product
	 * 
	 * @return o matrice de variabile String care va contine toate detaliile gasite
	 *         in tabelul Product
	 */
	public String[][] selectAllProducts() {
		String[][] table = new String[100][100];
		Field[] atribut = Product.class.getDeclaredFields();
		Connection conn = ConnectionFactory.getConnection();
		String query = "SELECT * from `order`.`" + Product.class.getSimpleName() + "`";
		Statement st;
		int i = 0;
		try {
			st = conn.createStatement();
			ResultSet r = st.executeQuery(query);
			while (r.next()) {
				table[i][0] = Integer.toString(r.getInt(1));
				table[i][1] = r.getString(2);
				table[i][2] = Integer.toString(r.getInt(3));
				table[i][3] = Float.toString(r.getFloat(4));
				i++;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return table;
	}

}
