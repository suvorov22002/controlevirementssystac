/**
 * 
 */
package com.afb.portal.presentation.tools.listeners;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.PatternLayout;

import afb.dsi.dpd.portal.jpa.tools.PortalHelper;

import com.afb.portal.presentation.tools.ViewHelper;
import com.afb.portal.presentation.virementsRec.TraitementDialog;
import com.afb.virementsRec.business.parameter.shared.IVirementsRecManager;
import com.afb.virementsRec.business.scanner.IScanner;
import com.afb.virementsRec.dao.parameter.shared.IVirementsRecManagerDAOLocal;
import com.afb.virementsRec.jpa.parameter.configuration.PlatformConfiguration;


/**
 * Listener du chargement du Contexte de l'application
 * @author Francis K, Stéphane M
 * @version 2.0
 */
public class ContextLoaderListener implements ServletContextListener {

	/**
	 *  Contexte JNDI
	 */
	private Context ctx = null;

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent event) {

		try {

			// Si le contexte n'est pas null
			if(ctx != null) ctx.close();

		} catch (Exception e) {

			// On relance l'exception
			throw new RuntimeException("Erreur lors de la fermeture du Contexte JNDI", e);

		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {

		try {

			// Initialisation du contexte JNDI
			ctx = new InitialContext(); 

		} catch (Exception e) {

			// On relance l'exception
			throw new RuntimeException("Erreur lors de l'initialisation du Contexte JNDI", e);

		}

		try{

			// Chargement du service de gestion des Utilisateurs
			ViewHelper.virementsRecManager = (IVirementsRecManager) ctx.lookup(PlatformConfiguration.APPLICATION_EAR + "/" + IVirementsRecManager.SERVICE_NAME + "/remote");
			
			ViewHelper.virementsRecManagerDAOLocal = (IVirementsRecManagerDAOLocal) ctx.lookup(PlatformConfiguration.APPLICATION_EAR + "/" + IVirementsRecManagerDAOLocal.SERVICE_NAME + "/local");

			
			// Chargement du service de gestion des Utilisateurs
			IScanner scanner  = (IScanner) ctx.lookup(PlatformConfiguration.APPLICATION_EAR + "/" + IScanner.SERVICE_NAME + "/remote");
			scanner.scanAndInitialiseModule(PortalHelper.JBOSS_DEPLOY_DIR + File.separator+PlatformConfiguration.APPLICATION_EAR+".ear");

		}catch (Exception e){
			// On relance l'exception
			throw new RuntimeException("Erreur lors du chargement des Services Métiers", e);
		}

		try{
		
			ViewHelper.virementsRecManager.robotSuppressionArchives();
			
			//ViewHelper.virementsRecManager.robotOuvertureFermetureJournee();
			
			//TraitementDialog.robotTraitementCompensation();		
			
			//ViewHelper.virementsRecManager.testSMS();
			
			/*//Robot pour lancer les alertes et les demandes d'explication
			ViewHelper.monitoringIEManager.lancerAlertes();
			
			//Robot pour mettre à jour les délais
			ViewHelper.monitoringIEManager.checkAndSetDelais();
			
			//Robot pour mettre à jour le statut d'une transmission à DE
			ViewHelper.monitoringIEManager.MAJStatuts();*/
			
			
			
			
		}catch(Exception e){
			// On relance l'exception
			//throw new RuntimeException("Erreur lors de l'extraction des fournisseurs",e);
			e.printStackTrace();
		}

	}
	
	

}
