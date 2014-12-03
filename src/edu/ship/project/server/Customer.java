package edu.ship.project.server;

import javax.jdo.annotations.*;

@PersistenceCapable
public class Customer {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	private String name;

	public Customer(String name) {
		super();
		this.name = name;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Customer [key=" + key + ", name=" + name + "]";
	}	
}
