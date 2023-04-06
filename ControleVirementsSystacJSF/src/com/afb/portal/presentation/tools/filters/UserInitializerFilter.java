/**
 * 
 */
package com.afb.portal.presentation.tools.filters;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import afb.dsi.dpd.portal.business.facade.IFacadeManagerRemote;
import afb.dsi.dpd.portal.jpa.entities.Role;
import afb.dsi.dpd.portal.jpa.entities.User;
import afb.dsi.dpd.portal.jpa.tools.PortalHelper;

import com.afb.portal.presentation.models.ProtectedSystem;
import com.afb.portal.presentation.tools.ViewHelper;

/**
 * Filtre d'initialisation des Parametres Utilisateurs apres connexion
 * @author Francis K
 * @version 1.0
 */
public class UserInitializerFilter implements Filter {

	/**
	 * Etat permettant de savoir que l'Utilisateur a deja ete charge
	 */
	public static final String USER_DATA_ALREADY_LOADED = "USER_DATA_ALREADY_LOADED";


	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

		// On caste la requete
		HttpServletRequest request = (HttpServletRequest) servletRequest;

		// La Session
		HttpSession session = request.getSession(true);
		
		// Si l'Utilisateur a deja ete charge
		if(session.getAttribute(USER_DATA_ALREADY_LOADED) != null){
			// On filtre
			chain.doFilter(servletRequest, servletResponse);
			// On sort
			return;	
		}
		
		// Adresse IP du Client
		String clientIPAdress = request.getRemoteAddr();

		Long uid = request.getParameter("uid") == null ? null : Long.valueOf(request.getParameter("uid")) ;

		// Si l'id de l'utilisateur n'est pas null
		if(uid != null) {
			try{
				if ( ViewHelper.facadeManager == null ) ViewHelper.facadeManager = (IFacadeManagerRemote) new InitialContext().lookup( PortalHelper.APPLICATION_EAR.concat("/").concat( IFacadeManagerRemote.SERVICE_NAME ).concat("/remote") );
			}catch(Exception e){}
			// Si le service Facade du portail est demarre
			if ( ViewHelper.facadeManager != null ) {
				// Recherche de l'utilisateur connecte
				User user = (User) ViewHelper.facadeManager.findByProperty(User.class, "id", uid);
				// Si l'utilisateur a ete retrouve
				if( user != null ) {
					// Lecture de l'adresse ip du poste utilisateur
					user.setIpAddress(clientIPAdress);
					// On Positionne l'Utilisateur
					session.setAttribute(PortalHelper.CONNECTED_USER_SESSION_NAME, user );	
				}		
			}
		}

		// Chemin de base des Ressources
		ViewHelper.ROOT_DATAS_DIR = getRootDataDirectory();

		// On positionne l'etat de chargement
		session.setAttribute(USER_DATA_ALREADY_LOADED, USER_DATA_ALREADY_LOADED);

		// On filtre
		chain.doFilter(servletRequest, servletResponse);


	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {}

	/**
	 * Methode d'obtention du Repertoire racine de stockage des donnees de l'application
	 * @return Repertoire racine de stockage des donnees de l'application
	 */
	public String getRootDataDirectory() {

		// On recalcule
		String rootDataDirectory = System.getProperty("jboss.server.data.dir", ".") + File.separator + "PortalDatas";

		// On retourne
		return rootDataDirectory;
	}
	
	/**
	 * Initialisation des parametres de la vue
	 */
	public void initialiseparameter(){

		// Chargement des roles des utilisateurs connectes 
		List<Role> roles = new ArrayList<Role>();	

		if(ViewHelper.user != null){

			try{	

				//User user = (User) ViewHelper.getSessionScopeParameter("user");
				User user = ViewHelper.user;
				IFacadeManagerRemote facade = (IFacadeManagerRemote) new InitialContext().lookup(PortalHelper.APPLICATION_EAR + "/" + IFacadeManagerRemote.SERVICE_NAME + "/remote");
				roles = facade.filterUserRoleFromModule(user.getId(),"VIR_SYSTAC");
				if(roles == null) roles = new ArrayList<Role>();

			}catch (Exception e){
				e.printStackTrace();								
			}

			// Affichage des roles
			if(roles == null) return;
			
			reset();  //on réinitialise les valeurs du ProtectedSystem
			
			// parcour des roles
			for(Role role : roles){ 
				
				/**if(role.getName().equalsIgnoreCase("param"))ProtectedSystem.param = Boolean.TRUE;
				if(role.getName().equalsIgnoreCase("filterTraitementIncoherences"))ProtectedSystem.filterTraitement = Boolean.TRUE;
				if(role.getName().equalsIgnoreCase("findDoublons"))ProtectedSystem.findDoublons = Boolean.TRUE;
				if(role.getName().equalsIgnoreCase("findIncoherences"))ProtectedSystem.findIncoherences = Boolean.TRUE;
				if(role.getName().equalsIgnoreCase("traitement"))ProtectedSystem.traitement = Boolean.TRUE;
				if(role.getName().equalsIgnoreCase("statistiques"))ProtectedSystem.statistiques = Boolean.TRUE;
				if(role.getName().equalsIgnoreCase("paramCompenseCentrale"))ProtectedSystem.paramCompenseCentrale = Boolean.TRUE;
				
				if(role.getName().equalsIgnoreCase("paramImpots"))ProtectedSystem.paramImpots = Boolean.TRUE;
				if(role.getName().equalsIgnoreCase("traitementImpots"))ProtectedSystem.traitementImpots = Boolean.TRUE;*/
				
				

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

	
	
}
