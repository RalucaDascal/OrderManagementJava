package model;

/**
 * Aceasta clasa defineste toate detaliile despre comanda Clasa contine un
 * constructor, gettere si settere
 * 
 *
 */
public class Order {
	private int idO;
	private String lName;
	private String fName;
	private String pName;
	private int quantity;

	public Order(int id, String lName, String fName, String pName, int quantity) {
		this.idO = id;
		this.lName = lName;
		this.fName = fName;
		this.quantity = quantity;
	}

	public int getIdO() {
		return idO;
	}

	public void setIdO(int idO) {
		this.idO = idO;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
