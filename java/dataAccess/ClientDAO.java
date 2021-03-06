package dataAccess;

import java.sql.*;

import model.Client;

import java.lang.reflect.Field;

public class ClientDAO {
	/**
	 * aceasta metoda realizeaza si executa secventa SQL pentru adaugarea unui
	 * client in tabel
	 * 
	 * @param Data-valorile pe care le introducem in tabel
	 */
	public void insertClient(String Data) {
		Connection conn = ConnectionFactory.getConnection();
		Field[] atribut = Client.class.getDeclaredFields();
		int i = 0;
		String secv = "(";
		for (i = 0; i < atribut.length - 1; i++) {

			secv = secv + "`" + atribut[i].getName() + "`" + ", ";
		}
		secv = secv + "`" + atribut[atribut.length - 1].getName() + "`" + ")";
		Statement st;
		try {
			st = conn.createStatement();
			String query = "INSERT INTO `order`.`" + Client.class.getSimpleName() + "`  " + secv + " VALUES " + Data;
			st.executeUpdate(query);
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	/**
	 * aceasta metoda realizeaza si executa secventa SQL pentru cautarea unui client
	 * stiind numele si prenumele
	 * 
	 * @param lName-         numele clientul cautat
	 * @param fName-prenumel clientului cautat
	 * @return identificatorul unic al clientului cu numele si prenumele dat
	 */
	public int findBYNameClient(String lName, String fName) {
		Connection conn = ConnectionFactory.getConnection();
		Statement st;
		try {
			st = conn.createStatement();
			String query = "SELECT * FROM order." + Client.class.getSimpleName() + " WHERE lName=\"" + lName
					+ "\"AND fName=" + "\"" + fName + "\"";
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
	 * aceasta metoda realizeaza si executa secventa SQL pentru stergerea unui
	 * client din tabel
	 * 
	 * @param lName- numele clientului pe care dorim sa-l stergem
	 * @param fName- prenumele clientului pe care dorim sa-l stergem
	 */
	public void deleteClient(String lName, String fName) {
		Connection conn = ConnectionFactory.getConnection();
		Statement st;
		try {
			st = conn.createStatement();
			String query = "Delete FROM order." + Client.class.getSimpleName() + " WHERE lName=\"" + lName
					+ "\"AND fName=" + "\"" + fName + "\"";
			st.executeUpdate(query);
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	/**
	 * aceasta metoda va realiza si executa o instructiune SQL care va determina
	 * identificator ultimului client din tabel
	 * 
	 * @return identificatorul ultimului client adaugat
	 */

	public int nrRowsClient() {
		Connection conn = ConnectionFactory.getConnection();
		try {
			Statement st = conn.createStatement();
			String query = "SELECT * FROM CLIENT";
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
	 * liniilor din tabel Client
	 * 
	 * @return numarul de linii din tabelul Client, adica numarul de clienti
	 *         existenti la un moment dat in tabel
	 */

	public int countRowsClient() {
		Connection conn = ConnectionFactory.getConnection();
		try {
			Statement st = conn.createStatement();
			String query = "SELECT Count(*) FROM order.client";
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
	 * tuturor liniilor si a coloanelor din tabelul Client
	 * 
	 * @return o matrice de variabile String care va contine toate detaliile gasite
	 *         in tabelul Client
	 */
	public String[][] selectAllClients() {
		String[][] table = new String[100][100];
		Field[] atribut = Client.class.getDeclaredFields();
		Connection conn = ConnectionFactory.getConnection();
		String query = "SELECT * from `order`.`" + Client.class.getSimpleName() + "`";
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
				i++;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return table;
	}

}
