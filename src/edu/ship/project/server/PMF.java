package edu.ship.project.server;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

/**
 * 
 * @author https://cloud.google.com/appengine/docs/java/datastore/jdo/overview-dn2
 *
 */
public class PMF {
	private static final PersistenceManagerFactory pmfInstance = JDOHelper.getPersistenceManagerFactory("transactions-optional");

	private PMF() {}

    public static PersistenceManagerFactory get() {
        return pmfInstance;
    }
}
