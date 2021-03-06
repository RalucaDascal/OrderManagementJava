package dataAccess;

import java.sql.*;
import model.*;
import java.lang.reflect.Field;

public class OrderDAO {
	/**
	 * aceasta metoda realizeaza si executa secventa SQL pentru adaugarea unei
	 * comenzi in tabel
	 * 
	 * @param Data-valorile pe care le introducem in tabel
	 */
	public void insertOrder(String Data) {
		Connection conn = ConnectionFactory.getConnection();
		Field[] atribut = Order.class.getDeclaredFields();
		int i = 0;
		String secv = "(";
		for (i = 0; i < atribut.length - 1; i++) {

			secv = secv + "`" + atribut[i].getName() + "`" + ", ";
		}
		secv = secv + "`" + atribut[atribut.length - 1].getName() + "`" + ")";
		Statement st;
		try {
			st = conn.createStatement();
			String query = "INSERT INTO `order`.`" + Order.class.getSimpleName() + "`  " + secv + " VALUES " + Data;
			st.executeUpdate(query);
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	/**
	 * aceasta metoda va realiza si executa o instructiune SQL care va determina
	 * identificator ultimei comenzi din tabel
	 * 
	 * @return identificatorul ultimei comenzi adaugat
	 */
	public int nrRowsOrder() {
		Connection conn = ConnectionFactory.getConnection();
		try {
			Statement st = conn.createStatement();
			String query = "SELECT * FROM order.order";
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
	 * liniilor din tabel Order
	 * 
	 * @return numarul de linii din tabelul Order, adica numarul de comenzi
	 *         existente la un moment dat in tabel
	 */

	public int countRowsOrder() {
		Connection conn = ConnectionFactory.getConnection();
		try {
			Statement st = conn.createStatement();
			String query = "SELECT Count(*) FROM order.order";
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
	 * tuturor liniilor si a coloanelor din tabelul Order
	 * 
	 * @return o matrice de variabile String care va contine toate detaliile gasite
	 *         in tabelul Order
	 */
	public String[][] selectAllOrders() {
		String[][] table = new String[100][100];
		Field[] atribut = Product.class.getDeclaredFields();
		Connection conn = ConnectionFactory.getConnection();
		String query = "SELECT * from `order`.`" + Order.class.getSimpleName() + "`";
		Statement st;
		int i = 0;
		try {
			st = conn.createStatement();
			ResultSet r = st.executeQuery(query);
			while (r.next()) {
				table[i][0] = Integer.toString(r.getInt(1));
				table[i][1] = r.getString(2);
				table[i][2] = r.getString(3);
				table[i][3] = r.getString(4);
				table[i][4] = Integer.toString(r.getInt(5));
				i++;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return table;
	}

}
