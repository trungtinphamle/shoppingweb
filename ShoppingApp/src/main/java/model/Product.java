package model;

public class Product {
	private int id;
	private String name;
	private String description;
	private float price;
	private String src; // location
	private String type;
	private String brand; // category
	private int number;
	
	public Product(int id, String name, String description, float price, String src, String type, String brand) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.src = src;
		this.type = type;
		this.brand = brand;
		this.number = 1;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public float getPrice() {
		return price;
	}

	public String getSrc() {
		return src;
	}

	public String getType() {
		return type;
	}

	public String getBrand() {
		return brand;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
}
