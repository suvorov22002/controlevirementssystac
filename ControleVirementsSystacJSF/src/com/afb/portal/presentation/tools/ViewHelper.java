/**
 * 
 */
package com.afb.portal.presentation.tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import afb.dsi.dpd.portal.business.facade.IFacadeManagerRemote;
import afb.dsi.dpd.portal.jpa.entities.User;
import afb.dsi.dpd.portal.jpa.tools.PortalHelper;

import com.afb.portal.presentation.models.ClientArea;
import com.afb.virementsRec.jpa.parameter.configuration.PlatformConfiguration;
import com.afb.virementsRec.business.parameter.shared.IVirementsRecManager;
import com.afb.virementsRec.dao.parameter.shared.IVirementsRecManagerDAOLocal;

/**
 * Classe contenant toutes donnees d'aide de l'application de presentation
 * @author Francis K, Stéphane M
 * @version 1.0
 */
public class ViewHelper {

	/**
	 * Service de gestion des Parametrages generaux
	 */
	public static IVirementsRecManager virementsRecManager = null;
	
	public static IVirementsRecManagerDAOLocal virementsRecManagerDAOLocal = null;


	public static IFacadeManagerRemote facadeManager;

	/**
	 * Nom du parametre contenant la Locale de la session Utilisateur
	 */
	public static final String SESSIONLOCALE_PARAMETER_NAME = "USER_LOCALE";

	/**
	 * Reertoire racine des donnees de EBuisnessPortal
	 */
	public static String ROOT_DATAS_DIR = null;

	/**
	 * Libelle de connexion
	 */
	public static String libuserId = "";

	/**
	 * Libelle de connexion
	 */
	public static String userId = null;

	/**
	 * Login de connexion
	 */
	public static String login = null;

	/**
	 * Utilisateur connexte
	 */
	public static User user = null;

	/**
	 * Nom de l'EAR parente
	 */
	public static final String EAR_APPLICATION = PlatformConfiguration.APPLICATION_EAR;

	/**
	 * Nom du repertoire de base des donnees de ATFF
	 */
	public static final String ATFF_ROOT_DATAS_DIR = "ControleVirementsSystacJSF";

	/**
	 * Nom du repertoire des donnees de la couche de presentation
	 */
	public static final String ATFF_RESOURCES_DATAS_DIR = "Resources";

	/**
	 * Nom du repertoire des Etats de ATFF
	 */
	public static final String ATFF_REPORTS_DATAS_DIR = "Reports";

	public static final String TRANSMISSIONS = "Transmissions";

	/**
	 * Nom du repertoire des skins
	 */
	public static final String ATFF_SKINS_DIR = "Skins";

	/**
	 * Nom du skin par defaut
	 */
	public static final String ATFF_DEFAULT_SKIN = "e-Gold";

	/**
	 * Nom de l'attribut de session portant la valeur du Skin de l'Utilisateur
	 */
	public static final String SKIN_ATTRIBUTE_NAME = "UserSkin";

	/**
	 * Constructeur
	 */
	public ViewHelper() {

		// Initialisation
		initialize();

	}

	/**
	 * methode pour convertier la date
	 * @return
	 */
	public TimeZone getTimeZone() {
		return TimeZone.getDefault();
	}

	/**
	 * Methode d'initialisation
	 */
	private void initialize() {}

	/**
	 * Methode permettant d'obtenir le contexte de l'application
	 * @return	Contexte de l'application
	 */
	public static String getApplicationContext() {

		// Contexte de la servlet
		String servletContextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestServletPath();

		// Requete entiere
		String requestURL = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURL().toString().replace(servletContextPath, "");

		// On retourne le contest
		return requestURL;
	}

	/**
	 * Methode permettant de retouver le skin d'une session
	 * @return	Skin de la session
	 */
	public String getSessionSkin() {

		try {

			// Recherche de l'attribut
			String skin = (String) ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute(SKIN_ATTRIBUTE_NAME);

			// Si le skin n'existe pas
			if(skin == null || skin.trim().length() == 0) return ATFF_DEFAULT_SKIN;

			// On retourne la valeur
			return skin;

		} catch (Exception e) {

			// On retourne le skin par defaut
			return ATFF_DEFAULT_SKIN;
		}
	}

	/**
	 * Methode permettant de retouver le skin d'une session
	 * @return	Skin de la session
	 */
	public static String getSessionSkinStatic() {

		try {

			// Recherche de l'attribut
			String skin = (String) ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute(SKIN_ATTRIBUTE_NAME);

			// Si le skin n'existe pas
			if(skin == null || skin.trim().length() == 0) return ATFF_DEFAULT_SKIN;

			// On retourne la valeur
			return skin;

		} catch (Exception e) {

			// On retourne le skin par defaut
			return ATFF_DEFAULT_SKIN;
		}
	}

	/**
	 * Methode permettant d'obtenir l'URL du repertoire de Skin de la session en cours
	 * @return	URL du repertoire de skin
	 */
	public String getSessionSkinURL() {

		// Contexte de la servlet
		String servletContextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestServletPath();

		// Requete entiere
		String requestURL = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURL().toString().replace(servletContextPath, "");

		// URI du repertoire de Skin
		String skinURI = ATFF_RESOURCES_DATAS_DIR.concat("/").concat(ATFF_SKINS_DIR).concat("/").concat(getSessionSkin());

		// On retourne l'URL
		return requestURL + "/" + skinURI;

		//return System.getProperty("jboss.server.data.dir", ".") + File.separator + "PortalResources"+ File.separator +"Skins"+ File.separator +"e-Gold";

	}



	/**
	 * Methode permettant d'obtenir l'URL du repertoire de Skin de la session en cours
	 * @return	URL du repertoire de skin
	 */
	public String getSessionSkinURLPortal(){

		// Contexte de la servlet
		String servletContextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestServletPath();

		// Requete entiere
		String requestURL = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURL().toString().replace(servletContextPath, "");

		String skinURI = PortalHelper.PORTAL_RESOURCES_DATA_DIR.concat("/").concat(PortalHelper.PORTAL_GRAPHIC_UI_DIR);

		// On retourne l'URL
		return requestURL.concat("/").concat(skinURI);

	}

	/**
	 * Methode permettant d'obtenir l'URL du repertoire de Skin de la session en cours
	 * @return	URL du repertoire de skin
	 */
	public static String getSessionSkinURLStatic() {

		/**
		 * Contexte de la servlet
		 */
		String servletContextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestServletPath();

		// Requete entiere
		String requestURL = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURL().toString().replace(servletContextPath, "");

		// URI du repertoire de Skin
		String skinURI = ATFF_RESOURCES_DATAS_DIR.concat("/").concat(ATFF_SKINS_DIR).concat("/").concat(getSessionSkinStatic());

		// On retourne l'URL
		return requestURL + "/" + skinURI;

	}

	/**
	 * Retourne le chemin du repertoire des etats de E-HR
	 * @return
	 */
	public static String getReportsDir(){

		// Repertoire des etats
		return ROOT_DATAS_DIR + File.separator + ATFF_ROOT_DATAS_DIR + File.separator + ATFF_RESOURCES_DATAS_DIR + File.separator + ATFF_REPORTS_DATAS_DIR;

	}
	
	public static String getReportsDir2(){

		// Repertoire des etats
		return ROOT_DATAS_DIR + File.separator + ATFF_ROOT_DATAS_DIR + File.separator + ATFF_RESOURCES_DATAS_DIR + File.separator + ATFF_REPORTS_DATAS_DIR + File.separator;

	}
	
	public static String getPiecesJointesDir() {
    	return ROOT_DATAS_DIR + File.separator + ATFF_ROOT_DATAS_DIR + File.separator + ATFF_RESOURCES_DATAS_DIR + File.separator + ATFF_REPORTS_DATAS_DIR + File.separator + "PJ" + File.separator;
    }
	


	public static String getTransmissionsDir(){

		// Repertoire des etats
		return ROOT_DATAS_DIR + File.separator + ATFF_ROOT_DATAS_DIR + File.separator + ATFF_RESOURCES_DATAS_DIR + File.separator + TRANSMISSIONS;

	}

	/**
	 * Methode permettant d'obtenir l'URI du repertoire de Skin de la session en cours
	 * @return	URI du repertoire de skin
	 */
	public String getSessionSkinURI() {

		// URI du repertoire de Skin
		String skinURI = "/" + ATFF_RESOURCES_DATAS_DIR.concat("/").concat(ATFF_SKINS_DIR).concat("/").concat(getSessionSkin());

		// On retourne l'URI
		return skinURI;

	}

	/**
	 * Methode permettant d'obtenir l'URL du repertoire des Etats
	 * @return	URL du repertoire des Etats
	 */
	public String getReportsURL(){

		// Contexte de la servlet
		String servletContextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestServletPath();

		// Requete entiere
		String requestURL = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURL().toString().replace(servletContextPath, "");

		// URI du repertoire des Etats
		String skinURI = ATFF_RESOURCES_DATAS_DIR.concat("/").concat(ATFF_REPORTS_DATAS_DIR);

		// On retourne l'URL
		return requestURL + "/" + skinURI;

	}


	public String getTransmissionsURL(){

		/// Contexte de la servlet
		String servletContextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestServletPath();

		// Requete entiere
		String requestURL = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURL().toString().replace(servletContextPath, "");

		// URI du repertoire des Etats
		String skinURI = ATFF_RESOURCES_DATAS_DIR.concat("/").concat(TRANSMISSIONS);

		// On retourne l'URL
		return requestURL + "/" + skinURI;

	}

	/**
	 * Methode permettant d'obtenir l'URL du repertoire des Etats
	 * @return	URL du repertoire des Etats
	 */
	public static String getReportsURLStatic() {

		// Contexte de la servlet
		String servletContextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestServletPath();

		// Requete entiere
		String requestURL = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURL().toString().replace(servletContextPath, "");

		// URI du repertoire des Etats
		String skinURI = ATFF_RESOURCES_DATAS_DIR.concat("/").concat(ATFF_REPORTS_DATAS_DIR);

		// On retourne l'URL
		return requestURL + "/" + skinURI;
	}

	/**
	 * Methode permettant d'obtenir l'URI du repertoire des Etats
	 * @return	URI du repertoire des Etats
	 */
	public String getReportsURI() {

		// URI du repertoire de Skin
		String skinURI = "/" + ATFF_ROOT_DATAS_DIR + "/" + ATFF_RESOURCES_DATAS_DIR.concat("/").concat(ATFF_REPORTS_DATAS_DIR);

		// On retourne l'URI
		return skinURI;
	}



	/**
	 * @return the libuserId
	 */
	public  String getLibuserId() {
		return libuserId;
	}

	/**
	 * @param libuserId the libuserId to set
	 */
	public  void setLibuserId(String libuserId) {
		ViewHelper.libuserId = libuserId;
	}

	/**
	 * Methode permettant d'obtenir l'URI du repertoire des Etats
	 * @return	URI du repertoire des Etats
	 */
	public static String getReportsURIStatic() {

		// URI du repertoire de Skin
		String skinURI = "/" + ATFF_ROOT_DATAS_DIR + "/" + ATFF_RESOURCES_DATAS_DIR.concat("/").concat(ATFF_REPORTS_DATAS_DIR);

		// On retourne l'URI
		return skinURI;
	}

	/**
	 * Methode permettant d'obtenir la liste des Skins disponibles
	 * @return	liste des Skins disponibles
	 */
	public static List<String> getSkinsList() {

		// Chemin vers le repertoires des donnees de Genezis

		// Un File sur la Racine des donnees
		File rootDataFile = new File(ViewHelper.ROOT_DATAS_DIR);

		// Un File sur le repertoire des Resources de Genezis
		File genezisRootDataFile = new File(rootDataFile, ATFF_ROOT_DATAS_DIR.concat(File.separator).concat(ATFF_RESOURCES_DATAS_DIR));

		// Un File sur le repertoire des Skins
		File genezisSkinsRootDataFile = new File(genezisRootDataFile, ATFF_SKINS_DIR);

		// Liste des Chemisn
		File[] contentsFile = genezisSkinsRootDataFile.listFiles();

		// Liste des Skins
		List<String> skins = new ArrayList<String>();

		// Parcours
		for (File file : contentsFile) {

			// Si c'est un repertoire
			if(file.isDirectory()) skins.add(file.getName());

		}

		// On retourne la liste
		return skins;
	}

	/**
	 * Methode permettant d'obtenir le Bean Manage ClientArea
	 * @return	Bean Manage ClientArea
	 */
	public static ClientArea getSessionClientAreaManagedBean() {

		// On retourne le Bean
		return (ClientArea) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("clientArea");
	}

	/**
	 * Methode de mise a jour d'un Parametre dans la portee Application
	 * @param parameterName	Nom du Parametre
	 * @param parameterValue	Valeur du Parametre
	 */
	public static void setApplicationScopeParameter(String parameterName, Object parameterValue) {

		// Le FacesContext
		FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put(parameterName, parameterValue);
	}

	/**
	 * Methode de mise a jour d'un Parametre dans la portee Application
	 * @param parameterName	Nom du Parametre
	 * @return Valeur du Parametre
	 */
	public static Object getApplicationScopeParameter(String parameterName) {

		// Le FacesContext
		return FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get(parameterName);
	}

	/**
	 * Methode de mise a jour d'un Parametre dans la portee Session
	 * @param parameterName	Nom du Parametre
	 * @param parameterValue Valeur du Parametre
	 */
	public static void setSessionScopeParameter(String parameterName, Object parameterValue) {

		// Le FacesContext
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(parameterName, parameterValue);
	}

	/**
	 * Methode de mise a jour d'un Parametre dans la portee Session
	 * @param parameterName	Nom du Parametre
	 * @return Valeur du Parametre
	 */
	public static Object getSessionScopeParameter(String parameterName) {

		// Le FacesContext
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(parameterName);
	}


	/**
	 * Methode permettant d'obtenir le Bean Manage ClientArea
	 * @return	Bean Manage ClientArea
	 */
	public static Object getSessionManagedBean( String managedBeanName ) {

		// On retourne le Bean
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(managedBeanName);
	}


	/**
	 * Methode permettant d'obtenir le Bean Manage ClientArea
	 * @return	Bean Manage ClientArea
	 */
	public static void setSessionManagedBean( String managedBeanName, Object bean ) {

		// On retourne le Bean
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(managedBeanName, bean);
	}

	/**
	 * 
	 * @return
	 */
	public static User getSessionUser() {
		return (User) ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute(PortalHelper.CONNECTED_USER_SESSION_NAME);
	}

	/**
	 * Retourne la liste des mois de l'année
	 * @return months
	 */
	public static List<String> getMonths(){

		// Initialisation de la liste des mois a retourner
		List<String> months = new ArrayList<String>();

		// Ajout des mois
		for(int i=1; i<=12; i++) months.add(Multilangue.getMessage( "ebp.month." + ( i<10 ? "0" + i : i ) ) );

		// Retourne la liste des mois
		return months;
	}

	
	public String getPiecesJointesURL(){

		/// Contexte de la servlet
		String servletContextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestServletPath();

		// Requete entiere
		String requestURL = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURL().toString().replace(servletContextPath, "");

		// URI du repertoire des Etats
		String skinURI = ATFF_RESOURCES_DATAS_DIR.concat("/").concat(ATFF_REPORTS_DATAS_DIR).concat("/").concat("PJ");
		

		// On retourne l'URL
		return requestURL + "/" + skinURI;

	}



}