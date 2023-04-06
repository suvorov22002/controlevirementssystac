/**
 * 
 */
package com.afb.portal.presentation.models;

import java.text.ParseException;
import java.util.TimeZone;

import com.afb.portal.presentation.tools.ExceptionHelper;
import com.afb.portal.presentation.tools.ViewHelper;

/**
 * Classe representant la base des modeles des boites de dialogues
 * 
 * @author Francis K
 * @version 1.0
 */
public abstract class AbstractDialog implements IDialog {

	/**
	 * Etat d'inclusion de la boite de dialogue d'erreur
	 */
	protected boolean error = false;

	/**
	 * Etat d'inclusion de la boite de dialogue d'information
	 */
	protected boolean information = false;

	/**
	 * Etat d'inclusion de la boite de dialogue d'impression
	 */
	protected boolean print = false;

	/**
	 * Valeur de la recherche par defaut
	 */
	protected String defaultValue = "NO_SPECIFIED_VALUE";

	/**
	 * Composant Parent
	 */
	protected IViewComponent parent = null;

	/**
	 * Mode d'ouverture de la boite
	 */
	protected DialogFormMode mode = DialogFormMode.READ;

	/**
	 * Type de fermeture de la boite
	 */
	protected boolean wellClose = false;

	/**
	 * Etat de fermeture ou d'ouverture de la boite
	 */
	public boolean open = false;

	/**
	 * Obtention du Mode d'ouverture du formulaire
	 * 
	 * @return Mode d'ouverture du formulaire
	 */
	public DialogFormMode getMode() {
		return mode;
	}

	/**
	 * Mise a jour du Mode d'ouverture du formulaire
	 * 
	 * @param mode
	 *            Mode d'ouverture du formulaire
	 */
	public void setMode(DialogFormMode mode) {

		this.mode = mode;
	}

	/**
	 * Methode d'obtention de la Valeur par defaut
	 * 
	 * @return Valeur de la recherche par defaut
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * Methode de mise a jour de la Valeur de la recherche par defaut
	 * 
	 * @param defaultValue
	 *            Valeur de la recherche par defaut
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * @param open
	 *            the open to set
	 */
	public void setOpen(boolean open) {
		this.open = open;
	}

	/**
	 * 
	 * @return
	 */
	public TimeZone getTimeZone() {
		return TimeZone.getDefault();
	}

	@Override
	public void open() {
		this.open = true;
	}

	@Override
	public void cancel() {

		// Liberation des Ressources
		disposeResourceOnClose();

		// On positionne l'etat a false
		this.open = false;

		// On positionne l'etat de sortie
		this.wellClose = false;
	}

	@Override
	public void close() {

		try {

			// Si le Contrôle de l'Objet Courant echoue
			if (!this.checkBuildedCurrentObject()) {

				// On positionne l'etat d'info
				this.information = true;

				// On sort
				return;
			}

			// Construction de l'Objet Courant
			buildCurrentObject();

			// Validation
			validate();

			// open = false;

			// Information
			information = true;

			// Message d'information
			ExceptionHelper.showInformation("abstractdialog.operation.ok", this);

		} catch (Exception e) {

			// Mise en place de l'etat d'inclusion du fichier d'erreur
			this.error = true;

			// Traitement de l'exception
			ExceptionHelper.threatException(e);
		}
	}

	/**
	 * 
	 */
	@Override
	public String getIcon() {

		// Chemin de l'Image
		return ViewHelper.getSessionSkinURLStatic()
				+ "/Images/CountryListPanelLogo.png";
	}

	@Override
	public boolean isOpen() {
		return open;
	}

	@Override
	public boolean isConsultation() {

		boolean val = false;

		if (mode.equals(DialogFormMode.READ)) {
			val = true;
		}

		// On retourne le resultat
		return val;
	}
	
	@Override
	public boolean isUpdateAndConsultation() {

		boolean val = false;

		if (mode.equals(DialogFormMode.UPDATE)||mode.equals(DialogFormMode.READ)) {
			val = true;
		}

		// On retourne le resultat
		return val;
	}
	
	
	@Override
	public boolean isCreateAndConsultation() {

		boolean val = false;

		if (mode.equals(DialogFormMode.CREATE)||mode.equals(DialogFormMode.READ)) {
			val = true;
		}

		// On retourne le resultat
		return val;
	}

	@Override
	public boolean isCreate() {

		// On retourne le resultat
		return mode.equals(DialogFormMode.CREATE);
	}

	@Override
	public boolean isUpdate() {

		// On retourne le resultat
		return mode.equals(DialogFormMode.UPDATE);
	}

	/**
	 * Obtention de l'Etat de fermeture de la fenetre
	 * 
	 * @return Etat de fermeture de la fenetre
	 */
	public boolean isWellClose() {
		return wellClose;
	}

	/**
	 * Mise a jour de l'Etat de fermeture de la fenetre
	 * 
	 * @param wellClose
	 *            Etat de fermeture de la fenetre
	 */
	public void setWellClose(boolean wellClose) {
		this.wellClose = wellClose;
	}

	/**
	 * Methode d'obtention du Composant Parent
	 * 
	 * @return Composant Parent
	 */
	public IViewComponent getParent() {
		return parent;
	}

	/**
	 * Methode de mise a jour du Composant Parent
	 * 
	 * @return Composant Parent
	 */
	public void setParent(IViewComponent parent) {
		this.parent = parent;
	}

	/**
	 * Methode d'obtention du nom de la queue de requete
	 * 
	 * @return queue de requete
	 */
	public String getRequestQueue() {

		// On retourne le nom de la queue
		return "DialogRequestQueue";
	}

	@Override
	public void validateSubDialogClosure(IDialog child, DialogFormMode mode,
			boolean wellClose) {
	}

	/**
	 * Methode d'initialisation de la boite
	 */
	public abstract void initComponents();

	/**
	 * Methode de construction de l'Objet Courant
	 * 
	 * @throws ParseException
	 */
	protected abstract void buildCurrentObject() throws ParseException;

	/**
	 * Methode de validation
	 */
	protected abstract void validate();

	/**
	 * Methode de liberation des ressources lors de la fermeture
	 */
	protected abstract void disposeResourceOnClose();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.afrikbrain.pme.genezis.presentation.models.IPanel#
	 * getErrorDialogDefinition()
	 */
	public String getErrorDialogDefinition() {

		// Le Chemin
		String path = error ? "/views/home/ErrorDialog.xhtml"
				: "/views/home/EmptyPage.xhtml";

		// On annule
		error = false;

		// On retourne le chemin
		return path;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.afrikbrain.pme.genezis.presentation.models.IPanel#
	 * getInformationDialogDefinition()
	 */
	public String getInformationDialogDefinition() {

		// Le Chemin
		String path = information ? "/views/home/InformationDialog.xhtml"
				: "/views/home/EmptyPage.xhtml";

		// On annule
		information = false;

		// On retourne le chemin
		return path;

	}

	/**
	 * Methode d'obtention du chemin vers le fichier de definition de la boite
	 * de dialogue d'attente
	 * 
	 * @return Boite de dialogue d'attente
	 */
	public String getWaitDialogDefinition() {

		// On retourne le chemin du fichier
		return open ? "/views/home/WaitDialog.xhtml"
				: "/views/home/EmptyPage.xhtml";
	}

	@Override
	public void actionOnInformationDialogClose() {

		// Liberation des Ressources
		disposeResourceOnClose();

		// On positionne l'etat a false
		this.open = false;

		// On positionne l'etat de sortie
		this.wellClose = true;
		
		if(this.parent == null) return;

		// Validation de la fermeture
		this.parent.validateSubDialogClosure(this, mode, wellClose);
	}
	
	

	@Override
	public String getViewAreaToRerender() {

		// La zone Cliente
		return ClientArea.staticGetID();
	}

	@Override
	public String getJSScriptOnComplete() {

		// Un Script Vide
		return "";
	}

	@Override
	public void validateConfirmationDialog() {
	}

}
