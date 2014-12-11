package edu.ship.project.server;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class InventoryElement {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
    private int sku;
	
	@Persistent
	private String description;

	@Persistent
	private String url;
	
	@Persistent
	private int price;
	
	@Persistent
	private int num;

	public InventoryElement(int sku, String description, String url, int price,
			int num) {
		super();
		this.sku = sku;
		this.description = description;
		this.url = url;
		this.price = price;
		this.num = num;
	}

	public Key getKey() {
		return key;
	}
	
	public int getSku() {
		return sku;
	}

	public void setSku(int sku) {
		this.sku = sku;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	@Override
	public String toString() {
		//return "InventoryElement [key=" + key + ", name=" + name + "]";
		return null;
	}	
}