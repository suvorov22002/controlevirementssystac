/**
 * 
 */
package com.afb.virementsRec.jpa.parameter.configuration;

import java.io.File;

/**
 * Configuration 
 * @author Francis
 * @version 1.0
 */
public class PlatformConfiguration {
		
	/**
	 * Nom de l'EAR
	 */
	public static final String APPLICATION_EAR = "ControleVirementsSystacEAR";
	
	/**
	 * Code de la configuration
	 */
	public static final String CONFIGURATION_CODE = "ControleVirementsSystacEAR";
		
	/**
	 * Repertoire racine des donnees du portail
	 */
	private String rootDataDirectory = System.getProperty("jboss.server.data.dir", ".") + File.separator + "PortalDatas";
		
	
	/**
	 * Constructeur par defaut
	 */
	public PlatformConfiguration() {}

	/**
	 * Methode d'obtention du Repertoire racine de stockage des donnees de l'application
	 * @return Repertoire racine de stockage des donnees de l'application
	 */
	public String getRootDataDirectory() {
		
		// On recalcule
		rootDataDirectory = System.getProperty("jboss.server.data.dir", ".") + File.separator + "PortalDatas";
		
		// On retourne
		return rootDataDirectory;
	}

	/**
	 * Methode de mise a jour du Repertoire racine de stockage des donnees de l'application
	 * @param rootDataDirectory Repertoire racine de stockage des donnees de l'application
	 */
	public void setRootDataDirectory(String rootDataDirectory) {
		this.rootDataDirectory = rootDataDirectory;
	}

}
