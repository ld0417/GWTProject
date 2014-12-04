package edu.ship.project.server;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

public class PMF {
	
//	private static final PersistenceManagerFactory PMF = JDOHelper.getPersistenceManagerFactory("jdo.properties");
//	private static final ThreadLocal<PersistenceManager> PM = new ThreadLocal<PersistenceManager>();
//	
//	public static PersistenceManager get() {
//		return PM.get();
//	}
//	
//	public PersistenceManager getPersistenceManager(){
//		PersistenceManager pm = PM.get();
//	    if (pm == null) {
//	      pm = PMF.getPersistenceManager();
//	      PM.set(pm);
//	    }
//	    return pm;
//	}
}
