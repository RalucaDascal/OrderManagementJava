package model;

/**
 * Aceasta clasa defineste toate detaliile despre produs Clasa contine un
 * constructor, gettere si settere
 *
 */
public class Product {
	private int idP;
	private String name;
	private int quantity;
	private float price;

	public Product(int idP, String name, int quantity, float price) {
		this.setIdP(idP);
		this.setName(name);
		this.setQuantity(quantity);
		this.setPrice(price);
	}

	public int getIdP() {
		return idP;
	}

	public void setIdP(int idP) {
		this.idP = idP;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price2) {
		this.price = price2;
	}

}
