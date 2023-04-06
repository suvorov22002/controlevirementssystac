/**
 * 
 */
package com.afb.portal.presentation.models;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import afb.dsi.dpd.portal.business.facade.IFacadeManagerRemote;
import afb.dsi.dpd.portal.jpa.entities.Role;
import afb.dsi.dpd.portal.jpa.entities.User;
import afb.dsi.dpd.portal.jpa.tools.PortalHelper;

import com.afb.portal.presentation.tools.ViewHelper;
import com.afb.portal.presentation.virementsRec.StatistiquesDoublonsListPanel;
import com.afb.portal.presentation.virementsRec.StatistiquesIncoherencesListPanel;
import com.afb.portal.presentation.virementsRec.TraitementDoublonsListPanel;
import com.afb.portal.presentation.virementsRec.TraitementIncoherencesListPanel;


/**
 * Classe representant le modele de la zone cliente
 * @author Francis K
 * @version 2.0
 */ 
public class ClientArea {

	/**
	 * ID de l'accordeon
	 */
	public static final String ACCORDEON_FORM_ID = "AccordeonArea";

	/**
	 * Locale de l'Utilisateur
	 */
	private Locale userLocale = Locale.getDefault();

	/**
	 * Modele de la boite de dialogue de gestion des exceptions
	 */
	private ErrorDialogModel errorDialogModel = new ErrorDialogModel();

	/**
	 * Modele de la boite de dialogue d'informations
	 */
	private InformationDialogModel informationDialogModel = new InformationDialogModel();

	/**
	 * Modele de la boite de dialogue d'informations
	 */
	private ConfirmationDialogModel confirmationDialogModel = new ConfirmationDialogModel();

	/**
	 * Paneau en cours
	 */
	private IPanel currentPanel = null;

	/**
	 * Nom du Panneau Par defaut
	 */
	private String currentPanelName = "";

	/**
	 * Libelle de connexion
	 */
	public  String libuserId = "";

	/**
	 * Login de connexion
	 */
	public static String login = null;

	/**
	 * Utilisateur connexte
	 */
	public User user = null;

	/**
	 * Liste des panneaux possibles
	 */
	private Map<String, IPanel> panels = new HashMap<String, IPanel>();
	
	private Map<String, IDialog> dialogs = new HashMap<String, IDialog>();

	/**
	 * 
	 */
	private  ProtectedSystem system = new ProtectedSystem();

	/**
	 * Constructeur par defaut
	 */
	public ClientArea() {

		// On positionne le panneau par defaut
		this.currentPanel = new DefaultPanel();
		this.panels.put(this.currentPanel.getName(), this.currentPanel);
		this.setCurrentPanelName(this.currentPanel.getName());
		system = new ProtectedSystem();
		//System.out.println("---------------" + currentPanelName);
		// Initialisations autres
		initialize();
		libuserId = loadConnectedUser();
		initialiseparameter();

	}

	/**
	 * Initialisation des parametres de la vue
	 */
	public void initialiseparameter(){

		// Chargement des roles des utilisateurs connectes 
		List<Role> roles = new ArrayList<Role>();	
		
		if(this.user == null){
			Long uid = ViewHelper.userId == null ? null : Long.valueOf(ViewHelper.userId);
			// Si le service Facade du portail est demarre
			if( uid != null ){
				// Recherche de l'utilisateur connecte
				 user = ViewHelper.virementsRecManager.findByPrimaryKey(User.class, uid, null);
			}	 
		}
		
		if(this.user == null){
			this.user = ViewHelper.user;
		}

		if( this.user != null){

			try{	
				//User user = (User) ViewHelper.getSessionScopeParameter("user");
				User user = this.user;
				user = ViewHelper.virementsRecManager.findByPrimaryKey(User.class, user.getId(), null);
				if(ViewHelper.facadeManager == null )ViewHelper.facadeManager = (IFacadeManagerRemote) new InitialContext().lookup(PortalHelper.APPLICATION_EAR + "/" + IFacadeManagerRemote.SERVICE_NAME + "/remote");
				roles = ViewHelper.facadeManager.filterUserRoleFromModule(user.getId(),"VIR_SYSTAC");
				if(roles == null) roles = new ArrayList<Role>();

			}catch (Exception e){
				e.printStackTrace();								
			}

			// voir tout
			// ProtectedSystem.seeAllCredit = Boolean.FALSE;
			
			// Affichage des roles
			if(roles == null) return;
			
			   reset();  //on réinitialise les valeurs du ProtectedSystem
			// parcour des roles
			for(Role role : roles){
				

				if(role.getName().equalsIgnoreCase("param"))ProtectedSystem.param = Boolean.TRUE;
				if(role.getName().equalsIgnoreCase("filterTraitementIncoherences"))ProtectedSystem.filterTraitement = Boolean.TRUE;
				if(role.getName().equalsIgnoreCase("findDoublons"))ProtectedSystem.findDoublons = Boolean.TRUE;
				if(role.getName().equalsIgnoreCase("findIncoherences"))ProtectedSystem.findIncoherences = Boolean.TRUE;
				
				if(role.getName().equalsIgnoreCase("traitementDoublons"))ProtectedSystem.traitementDoublons = Boolean.TRUE;
				if(role.getName().equalsIgnoreCase("traitementIncoherences"))ProtectedSystem.traitementIncoherences = Boolean.TRUE;
				if(role.getName().equalsIgnoreCase("traitementValidationDoubDansFic"))ProtectedSystem.traitementValidationDoublonsDansFichier = Boolean.TRUE;

				if(role.getName().equalsIgnoreCase("statistiquesDoublons"))ProtectedSystem.statistiquesDoublons = Boolean.TRUE;
				if(role.getName().equalsIgnoreCase("statistiquesIncoherences"))ProtectedSystem.statistiquesIncoherences = Boolean.TRUE;
				if(role.getName().equalsIgnoreCase("statistiquesRapports"))ProtectedSystem.statistiquesRapports = Boolean.TRUE;

				
				if(role.getName().equalsIgnoreCase("paramCompenseCentrale"))ProtectedSystem.paramCompenseCentrale = Boolean.TRUE;
				
				if(role.getName().equalsIgnoreCase("paramImpots"))ProtectedSystem.paramImpots = Boolean.TRUE;
				if(role.getName().equalsIgnoreCase("traitementImpots"))ProtectedSystem.traitementImpots = Boolean.TRUE;

				if(role.getName().equalsIgnoreCase("validateOpeReserv"))ProtectedSystem.validationOpeReservees = Boolean.TRUE;
				
			
			}


		}

	}
	
	
	public void reset(){
		
		ProtectedSystem.param= Boolean.FALSE;
		ProtectedSystem.filterTraitement= Boolean.FALSE;
		ProtectedSystem.findDoublons= Boolean.FALSE;
		ProtectedSystem.findIncoherences= Boolean.FALSE;
		ProtectedSystem.traitementDoublons= Boolean.FALSE;
		ProtectedSystem.traitementIncoherences= Boolean.FALSE;
		ProtectedSystem.traitementValidationDoublonsDansFichier= Boolean.FALSE;
		ProtectedSystem.statistiquesDoublons= Boolean.FALSE;
		ProtectedSystem.statistiquesIncoherences= Boolean.FALSE;
		ProtectedSystem.statistiquesRapports = Boolean.FALSE;

		ProtectedSystem.paramCompenseCentrale= Boolean.FALSE;
		
		ProtectedSystem.paramImpots= Boolean.FALSE;
		ProtectedSystem.traitementImpots= Boolean.FALSE;
		
		ProtectedSystem.validationOpeReservees = Boolean.FALSE;
		
	}

	/**
	 * Methode d'initialisation du gestionnaire de la zone client
	 */
	private void initialize() {

		// Chargement de l'Utilisateur
		reloadUser();

		// Ajout panneau
		TraitementDoublonsListPanel traitementDoublonsListPanel = new TraitementDoublonsListPanel();
		this.panels.put(traitementDoublonsListPanel.getName(), traitementDoublonsListPanel);
		
		TraitementIncoherencesListPanel traitementIncoherencesListPanel = new TraitementIncoherencesListPanel();
		this.panels.put(traitementIncoherencesListPanel.getName(), traitementIncoherencesListPanel);
		
		StatistiquesDoublonsListPanel statistiquesDoublonsListPanel = new StatistiquesDoublonsListPanel();
		this.panels.put(statistiquesDoublonsListPanel.getName(), statistiquesDoublonsListPanel);
		
		StatistiquesIncoherencesListPanel statistiquesIncoherencesListPanel = new StatistiquesIncoherencesListPanel();
		this.panels.put(statistiquesIncoherencesListPanel.getName(), statistiquesIncoherencesListPanel);
		
		
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("traitementDoublonsListPanel",traitementDoublonsListPanel);
		
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("traitementIncoherencesListPanel",traitementIncoherencesListPanel);
		
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("statistiquesDoublonsListPanel",statistiquesDoublonsListPanel);
		
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("statistiquesIncoherencesListPanel",statistiquesIncoherencesListPanel);


	}

	/**
	 * Methode d'obtention du Paneau en cours
	 * @return Paneau en cours
	 */
	public IPanel getCurrentPanel() {
		return currentPanel;
	}



	/**
	 * @return the system
	 */
	public ProtectedSystem getSystem() {
		return system;
	}

	/**
	 * @param system the system to set
	 */
	public void setSystem(ProtectedSystem system) {
		this.system = system;
	}

	/**
	 * @return the libuserId
	 */
	public String getLibuserId() {
		return loadConnectedUser();
	}

	/**
	 * @return the user
	 */
	public User getUser() {

		if(user == null) return new User();
		return user;
	}

	/**
	 * Methode de mise a jour du Paneau en cours
	 * @param currentPanel Paneau en cours
	 */
	public void setCurrentPanel(IPanel currentPanel) {
		this.currentPanel = currentPanel;
	}

	/**
	 * Methode d'obtention du Nom du Panneau Par defaut
	 * @return Nom du Panneau Par defaut
	 */
	public String getCurrentPanelName() {
		return currentPanelName;
	}

	/**
	 * Methode de mise a jour du Nom du Panneau Par defaut
	 * @param currentPanelName Nom du Panneau Par defaut
	 */
	public void setCurrentPanelName(String currentPanelName) {
		this.currentPanelName = currentPanelName;
	}

	/**
	 * Methode d'obtention de la Liste des panneaux possibles
	 * @return Liste des panneaux possibles
	 */
	public Map<String, IPanel> getPanels() {
		return Collections.unmodifiableMap(panels);
	}

	/**
	 * Methode d'Ajout d'un Paneau
	 * @param panels Liste des panneaux possibles
	 */
	public void addPanels(Collection<IPanel> panels) {

		// Si la liste des Panels a ajouter est vide
		if(panels == null || panels.size() == 0) return;

		// arcours
		for (IPanel panel : panels) {

			// Ajout
			this.addPanel(panel);
		}
	}

	/**
	 * Methode d'ajout d'un Panel
	 * @param panel	Panel a ajouter
	 */
	public void addPanel(IPanel panel) {

		// Si le Panel est null
		if(panel == null) return;

		// Si le panel n'a pas de nom
		if(panel.getName() == null || panel.getName().trim().length() == 0) return;

		// Si le Panel existe deja
		if(panels.containsKey(panel.getName().trim())) return;

		// Ajout
		panels.put(panel.getName().trim(), panel);
	}

	
	/**
	 * 
	 * @return
	 */
	public String loadConnectedUser(){

		User user = ViewHelper.getSessionUser();
		String message ="";

		// Si l'utilisateur a ete retrouve
		if( user != null ){
			message = "Bienvenue "+user.getName()+" à  Contrôle automatique des virements reçus de Systac";
			ViewHelper.user = user;
			ViewHelper.login = user.getLogin();
			ViewHelper.libuserId = message;
			ClientArea.login = user.getLogin();
			// Id User
			ViewHelper.setSessionScopeParameter("user", user);
			// Id User
			ViewHelper.setSessionScopeParameter("login", user.getLogin());
			// Id User
			ViewHelper.setSessionScopeParameter(PortalHelper.CONNECTED_USER_SESSION_NAME, user);
			// On Positionne l'Utilisateur
			initialiseparameter();
		}	

		return message;

	}	
	
	
	public void deconnexion() throws Throwable {

		// On invalide la session
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

		// Si la session n'est pas nulle
		session.invalidate();

		// Reponse Http
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

		// On Vide le Buffer
		response.flushBuffer();

		// On redirect
		response.sendRedirect(ViewHelper.getApplicationContext().concat("/views/jsp/blank.jsp")); 

		// On rend une fois la vue
		FacesContext.getCurrentInstance().responseComplete();
		
	}
	

	/**
	 * Methode d'agffichage du Pannel Courant
	 */
	public void showCurrentPanel() {

		// init
		// loadConnectedUser();
		initialiseparameter();

		// Obtention du Panel Courant (Ancien)
		IPanel oldCurrent = this.currentPanel;

		// Si le nom du Nouveau est le même que celui de l'ancien
		if(this.currentPanelName == null || this.currentPanelName.equals(this.currentPanel.getName())) return;

		// Mise en place du nouveau
		this.currentPanel = panels.get(this.currentPanelName); 

		// Si le nouveau n'existe pas
		if(this.currentPanel == null){

			// On replace l'ancien
			this.currentPanel = oldCurrent;
			this.currentPanelName = oldCurrent.getName();
			// On sort
			return;

		} 

		// Sinon
		else {
			// On positionne le nom
			this.currentPanelName = this.currentPanel.getName();
		}

		// Fermeture de l'ancien Panel
		oldCurrent.close();

		// Ouverture du Nouveau
		this.currentPanel.open();

	}

	/**
	 * Methode d'obtention du Modele de la boite de dialogue de gestion des exceptions
	 * @return Modele de la boite de dialogue de gestion des exceptions
	 */
	public ErrorDialogModel getErrorDialogModel() {
		return errorDialogModel;
	}

	/**
	 * Methode de mise a jour du Modele de la boite de dialogue de gestion des exceptions
	 * @param errorDialogModel Modele de la boite de dialogue de gestion des exceptions
	 */
	public void setErrorDialogModel(ErrorDialogModel errorDialogModel) {
		this.errorDialogModel = errorDialogModel;
	}

	/**
	 * Methode d'obtention du Modele de la boite de dialogue d'informations
	 * @return Modele de la boite de dialogue d'informations
	 */
	public InformationDialogModel getInformationDialogModel() {
		return informationDialogModel;
	}

	/**
	 * Methode d'obtention du nom de la queue de requete
	 * @return queue de requete
	 */
	public String getRequestQueue() {

		// On retourne le nom de la queue
		return "ClientAreaRequestQueue";
	}

	/**
	 * @return the confirmationDialogModel
	 */
	public ConfirmationDialogModel getConfirmationDialogModel() {
		return confirmationDialogModel;
	}


	/**
	 * @param confirmationDialogModel the confirmationDialogModel to set
	 */
	public void setConfirmationDialogModel(
			ConfirmationDialogModel confirmationDialogModel) {
		this.confirmationDialogModel = confirmationDialogModel;
	}


	/**
	 * Methode permettant d'obtenir le nom du Fichier de definition de la boite d'infos
	 * @return	Nom du Fichier de definition de la boite d'infos
	 */
	public String getInformationDialogDefinition() {

		// On retourne le chemin
		return this.informationDialogModel != null ? this.informationDialogModel.getFileDefinition() : "/views/home/EmptyPage.xhtml";
	}

	/**
	 * Methode permettant d'obtenir le nom du Fichier de definition de la boite de confirmation
	 * @return	Nom du Fichier de definition de la boite d'infos
	 */
	public String getConfirmationDialogDefinition() {

		// On retourne le chemin
		return this.confirmationDialogModel != null ? this.confirmationDialogModel.getFileDefinition() : "/views/home/EmptyPage.xhtml";

	}

	/**
	 * Methode permettant d'obtenir l'ID de la zone d'affichage Client
	 * @return	ID de la zone d'affichage Client
	 */
	public String getID() {

		// On retourne l'ID
		return "ClientAreaContainer";
	}

	/**
	 * Methode statique permettant d'obtenir l'ID de la zone d'affichage Client
	 * @return	ID de la zone d'affichage Client
	 */
	public static String staticGetID() {

		// On retourne l'ID
		return "ClientAreaContainer";
	}

	/**
	 * Methode d'obtention du chemin vers le fichier de definition de la boite de dialogue d'attente
	 * @return Boite de dialogue d'attente
	 */
	public String getWaitDialogDefinition() {

		// On retourne le chemin du fichier
		return "/views/home/WaitDialog.xhtml";
	}

	/**
	 * Methode de rechargement de l'Utilisateur
	 */
	public void reloadUser() {

		// Utilisateur de la session
		//loadConnectedUser();

		ViewHelper.ROOT_DATAS_DIR = System.getProperty("jboss.server.data.dir", ".") + File.separator + "PortalDatas";

		// On positionne le skin
		ViewHelper.setSessionScopeParameter(ViewHelper.SKIN_ATTRIBUTE_NAME, "e-Gold");

		// On Positionne la locale
		ViewHelper.setSessionScopeParameter(ViewHelper.SESSIONLOCALE_PARAMETER_NAME, userLocale);

		// On Positionne la Locale
		FacesContext.getCurrentInstance().getViewRoot().setLocale(userLocale);

	}

	/**
	 * Methode permettant d'obtenir la Locale de l'Utilisateur
	 * @return Locale de l'Utilisateur
	 */
	public Locale getLocale() {

		// Chargement de l'Utilisateur
		reloadUser();

		// On retourne la Locale
		return userLocale;
	}

	/**
	 * Methode d'obtention de l'ID du formulaire de l'accordeon
	 * @return	ID du formulaire de l'accordeon
	 */
	public String getAccordeonFormID() {

		// On retourne l'ID
		return ACCORDEON_FORM_ID;
	}

	

}
