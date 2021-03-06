package model;

/**
 * Aceasta clasa defineste toate detaliile despre client Clasa contine un
 * constructor, si gettere si settere
 *
 */
public class Client {
	private int idC;
	private String lName;
	private String fName;
	private String city;

	public Client(int idC, String lName, String fName, String city) {
		this.setIdC(idC);
		this.setlName(lName);
		this.setfName(fName);
		this.setCity(city);

	}

	public int getIdC() {
		return idC;
	}

	public void setIdC(int idC) {
		this.idC = idC;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}
