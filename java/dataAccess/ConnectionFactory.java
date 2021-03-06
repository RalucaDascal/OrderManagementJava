package dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionFactory {
	private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DBURL = "jdbc:mysql://localhost:3306/order";
	private static final String USER = "root";
	private static final String PASS = "root";

	private static ConnectionFactory singleInstance = new ConnectionFactory();
/**
 *  aceasta metoda defineste driver-ul bazei de data
 */
	private ConnectionFactory() {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * realizeaza conexiunea dintre aplicatie si baza de date  
	 * @return conexiunea cu baza de date
	 */
	
	private Connection createConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DBURL, USER, PASS);
		} catch (SQLException se) {
		    LOGGER.log(Level.WARNING, "An errot occured while trying to connect to the DB");
			se.printStackTrace();
		}
		return conn;
	};
/**
 * 
 * @return conexiunea creata
 */
	public static Connection getConnection() {
		return singleInstance.createConnection();
	};
	/**
	 * inchiderea conexiunii date
	 * @param conn connexiunea pe care dorim sa o inchidem
	 */

	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, "An error occured while trying to close  the connection");
				e.printStackTrace();
			}
		}
	};
  /**
   *  inchiderea instructiunii SQL
   * @param statement instructiunea SQL
   */
	public static void close(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, "An error occured while trying to close the statement");
				e.printStackTrace();
			}
		}
	};
/**
 *  inchiderea rezultatului instructiunii SQL
 * @param resultSet rezultatul instructiunii SQL
 */
	public static void close(ResultSet resultSet) {
	if (resultSet !=null)
	{
		try {
			resultSet.close();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "An error occured while trying to close the result");
			e.printStackTrace();
		}
	}
	};

}
