package com.afb.portal.presentation.virementsRec;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.richfaces.model.selection.Selection;

import com.afb.portal.presentation.models.AbstractDialog;
import com.afb.portal.presentation.models.DialogFormMode;
import com.afb.portal.presentation.models.IViewComponent;
import com.afb.portal.presentation.models.reportViewer.ReportViewer;
import com.afb.portal.presentation.tools.ExceptionHelper;
import com.afb.portal.presentation.tools.ViewHelper;
import com.afb.virementsRec.jpa.datamodel.Doublons;
import com.afb.virementsRec.jpa.datamodel.SortTraitement;
import com.afb.virementsRec.jpa.datamodel.Rejet;
import com.afb.virementsRec.jpa.datamodel.TourCompensation;
import com.afb.virementsRec.jpa.datamodel.TraitementTourCompensation;
import com.afb.virementsRec.jpa.datamodel.TypeTraitement;
import com.yashiro.persistence.utils.dao.tools.OrderContainer;
import com.yashiro.persistence.utils.dao.tools.RestrictionsContainer;



public class TelechargeFichierRejetDialog extends AbstractDialog {

	private Rejet currentObject = new Rejet();

	public boolean disable;

	private Date dateDebut;

	private Date dateFin;

	private Selection selection;

	private String message;

	private boolean disabled = true;

	private List<Rejet> listStatRejets = new ArrayList<Rejet>();


	/**
	 * Constructeur par défaut
	 */
	public TelechargeFichierRejetDialog() {
		super();
	}

	/**
	 * Constructeur du Modele
	 * @param mode	Mode d'ouverture de la Boite
	 * @param trans	Objet Courant de la Boite
	 * @param parent	Composant Parent
	 */
	public TelechargeFichierRejetDialog(DialogFormMode mode, Rejet traitementTourCompensation, IViewComponent parent) {

		// Appel Parent
		super();

		// Initialisation des Parametres
		this.mode = mode;
		this.currentObject = traitementTourCompensation;
		this.parent = parent;

		// Initialisation des Composants
		initComponents();
		// this.open = true;

	}

	public void openTelechargeFichierRejetDialog(){

		this.mode = DialogFormMode.UPDATE;
		initComponents();
		this.open();
	}


	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Liste des Rejets";
	}

	@Override
	public String getFileDefinition() {
		// TODO Auto-generated method stub
		return "/views/virementsRec/TelechargeFichierRejetDialog.xhtml";
	}

	@SuppressWarnings("unchecked")
	@Override
	public Rejet getCurrentObject() {
		// TODO Auto-generated method stub
		return this.currentObject;
	}

	@Override
	public boolean checkBuildedCurrentObject() {



		return true;
	}

	@Override
	public String getChildDialogDefinition() {

		return null;
	}

	@Override
	public String getSecondChildDefinition() {

		return null;
	}

	@Override
	public void initComponents() {

		/*typeTraitementItems.add(new SelectItem(null, "---Choisir---"));
		for(TypeTraitement val : TypeTraitement.types())typeTraitementItems.add( new SelectItem(val.name(),val.value()) );

		sortTraitementItems.add(new SelectItem(null, "---Choisir---"));
		for(SortTraitement val : SortTraitement.types())sortTraitementItems.add( new SelectItem(val.name(),val.value()) );*/

		//listTraitementTourCompensations = ViewHelper.virementsRecManagerDAOLocal.filter(TraitementTourCompensation.class, null, null, null, null, 0, -1);

		dateDebut = Calendar.getInstance().getTime();
		dateFin = DateUtils.addDays(Calendar.getInstance().getTime(), 1);
	}

	public void processRecherche(){
		if(dateDebut!=null && dateFin!=null){
			listStatRejets = ViewHelper.virementsRecManagerDAOLocal.filter(Rejet.class, null, RestrictionsContainer.getInstance().add(Restrictions.between("dateRejet", dateDebut, dateFin)), OrderContainer.getInstance().add(Order.desc("dateRejet")).add(Order.desc("heureRejet")), null, 0, -1);
		}
		else{
			this.error = true;
			this.information = true;
			ExceptionHelper.showError("Veuillez renseigner une intervalle de dates s'il vous plaît !!!", this);
		}
	}

	
	@Override
	protected void buildCurrentObject() throws ParseException {


	}

	@Override
	protected void validate() {

	}

	@Override
	protected void disposeResourceOnClose() {

		listStatRejets.clear();

	}

	public void setCurrentObject(Rejet currentObject) {
		this.currentObject = currentObject;
	}

	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}


	public Selection getSelection() {
		return selection;
	}

	public void setSelection(Selection selection) {
		this.selection = selection;
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	/*public void reset(ActionEvent event)
	{

		for (Entite ent :listEmettrices) {
			ent.setCheck(set);  
		}
	}

	public void reset2(ActionEvent event)
	{

		for (Reporting ent :listReporting) {
			ent.setCheck(set2);  
		}
	}*/

	public List<Rejet> getListStatRejets() {
		return listStatRejets;
	}

	public void setListStatRejets(List<Rejet> listStatRejets) {
		this.listStatRejets = listStatRejets;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}



	/*public void openReportViewer(){

		try{

			//listTauxConformite = ViewHelper.monitoringIEManager.filterTauxConformite(listEmettrices, listResponsables, nature, tauxDeConformite, dateDelaiDebut, dateDelaiFin);

			if(listTraitementTourCompensations == null || listTraitementTourCompensations.isEmpty()) throw new Exception("Aucune Transmission selectionnée dans la Liste!");

			HashMap<String, Object> param = new HashMap<String, Object>();

			//param.put("tauxConformiteGlobal", leTauxDeConformite);

			//System.out.println("*******param size*******" + param.size());

			// Affichage du rapport de traitement 
			List<TraitementTourCompensation> lisTraitementTourCompensations = new ArrayList<TraitementTourCompensation>();

			TraitementTourCompensation traiteTourComp = new TraitementTourCompensation();
			traiteTourComp.setItems(listTraitementTourCompensations);
			lisTraitementTourCompensations.add(traiteTourComp);

			System.out.println("*****lisTraitementTourCompensations.size()******" + lisTraitementTourCompensations.size());

			//param.put("items", statistiques);
			System.out.println("****Before Report Viewer****");
			//reportViewer = new ReportViewer(tauxConformites,"tauxConformite.jasper", param, "Taux De Conformité", this,"/views/repport/ReportStat2.xhtml");
			//reportViewer.viewReport();

			reportViewer = new ReportViewer(lisTraitementTourCompensations, "tauxConformite.jasper",param, "Taux De Conformité", "application/vnd.ms-excel","/views/repport/ReportStat2.xhtml");

			disabled = false;
			message = "Opération Effectuée avec Succès. Cliquez sur le lien de téléchargement pour télécharger vos statistiques en fichier excel nommé statistiques_taux_de_conformite.xlsx dans le repertoire C://Utilisateurs/Téléchargement de votre machine.";


			System.out.println("****After Report Viewer****");


			return;

		}catch(Exception e){

			e.printStackTrace();
			// Etat d'erreur
			this.error = true;
			// Traitement de l'exception
			ExceptionHelper.threatException(e);

		}

	}*/

	/*public void openReportDoublons(){

		try{

			if(listTraitementTourCompensations == null || listTraitementTourCompensations.isEmpty()) throw new Exception("Aucun Doublon selectionné dans la Liste!");

			HashMap<String, Object> param = new HashMap<String, Object>();

			param.put("uti", ViewHelper.getSessionUser().getName());
			param.put("periode", new SimpleDateFormat("dd/MM/yyyy").format(dateDebut)+ "  -  " + new SimpleDateFormat("dd/MM/yyyy").format(dateFin));
			param.put("nombreVir", countDoub);
			param.put("total", count);
			int nbrDoublonsVal = 0;
			int nbrDoublonsIgn = 0;
			for(TraitementTourCompensation doub: listTraitementTourCompensations){
				if(doub.getSortTraitement().equals(SortTraitement.ECHEC)){ //doublons validés
					nbrDoublonsVal++;
				}else{
					nbrDoublonsIgn++;
				}
			}
			param.put("nbrDoublonsVal", nbrDoublonsVal);
			param.put("nbrDoublonsIgn", nbrDoublonsIgn);

			//System.out.println("*****repports.size()******" + repports.size());

			//param.put("items", statistiques);
			System.out.println("****Before Report Viewer****");
			reportViewer = new ReportViewer(listTraitementTourCompensations,"rapportDoublons.jasper", param, "afb.statistique.title", this,"/views/repport/ReportStat.xhtml");
			reportViewer.viewReport();
			System.out.println("****After Report Viewer****");


			return;

		}catch(Exception e){

			e.printStackTrace();
			// Etat d'erreur
			this.error = true;
			// Traitement de l'exception
			ExceptionHelper.threatException(e);

		}


	}*/


	/*@Override
	public void actionOnInformationDialogClose(){

		// Liberation des Ressources
		//disposeResourceOnClose();

		// On positionne l'etat a false
		this.open = true;

		// On positionne l'etat de sortie
		this.wellClose = false;

		if(this.parent == null) return;

		// Validation de la fermeture
		this.parent.validateSubDialogClosure(this, mode, wellClose);

	}*/



}


