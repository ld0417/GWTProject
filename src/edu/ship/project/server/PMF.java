package edu.ship.project.server;

import javax.jdo.PersistenceManager;

public class PMF {
	
	private static PersistenceManager pm;
	
	public static PersistenceManager get() {
		return pm = (PersistenceManager) new PMF();
	}
	
	public PersistenceManager getPersistenceManager(){
		return pm;
	}
}
