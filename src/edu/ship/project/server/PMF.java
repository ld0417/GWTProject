package edu.ship.project.server;

import javax.jdo.PersistenceManager;

public class PMF {
	
	private static PersistenceManager pm;
	
	public static PMF get() {
		return new PMF();
	}
	
	public PersistenceManager getPersistenceManager(){
		return pm;
	}
}
