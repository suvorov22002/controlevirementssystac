package com.afb.portal.presentation.virementsRec;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.model.SelectItem;

import org.richfaces.model.selection.Selection;

import com.afb.portal.presentation.models.AbstractDialog;
import com.afb.portal.presentation.models.DialogFormMode;
import com.afb.portal.presentation.models.IViewComponent;
import com.afb.portal.presentation.models.reportViewer.ReportViewer;
import com.afb.portal.presentation.tools.ExceptionHelper;
import com.afb.portal.presentation.tools.ViewHelper;
import com.afb.virementsRec.jpa.datamodel.Doublons;
import com.afb.virementsRec.jpa.datamodel.SortTraitement;
import com.afb.virementsRec.jpa.datamodel.TourCompensation;
import com.afb.virementsRec.jpa.datamodel.TraitementTourCompensation;
import com.afb.virementsRec.jpa.datamodel.TypeTraitement;



public class StatistiquesDialog extends AbstractDialog {

	private TraitementTourCompensation currentObject = new TraitementTourCompensation();

	public boolean disable;

	private List<SelectItem> typeTraitementItems = new ArrayList<SelectItem>();

	private List<SelectItem> sortTraitementItems = new ArrayList<SelectItem>();

	private TourCompensation tourCompensation = new TourCompensation();

	private Date dateDebut;

	private Date dateFin;

	private String heure;

	private String utilisateur;

	private TypeTraitement typeTraitement;

	private SortTraitement sortTraitement;

	private ReportViewer reportViewer;

	private Selection selection;

	private String message;

	private boolean disabled = true;

	private List<TraitementTourCompensation> listTraitementTourCompensations = new ArrayList<TraitementTourCompensation>();

	private int count;

	private int countDoub;

	/**
	 * Constructeur par défaut
	 */
	public StatistiquesDialog() {
		super();
	}

	/**
	 * Constructeur du Modele
	 * @param mode	Mode d'ouverture de la Boite
	 * @param trans	Objet Courant de la Boite
	 * @param parent	Composant Parent
	 */
	public StatistiquesDialog(DialogFormMode mode, TraitementTourCompensation traitementTourCompensation, IViewComponent parent) {

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

	public void openStatistiquesDialog(){

		this.mode = DialogFormMode.UPDATE;
		initComponents();
		this.open();
	}


	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Statistiques";
	}

	@Override
	public String getFileDefinition() {
		// TODO Auto-generated method stub
		return "/views/virementsRec/StatistiquesDialog.xhtml";
	}

	@SuppressWarnings("unchecked")
	@Override
	public TraitementTourCompensation getCurrentObject() {
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

		typeTraitementItems.add(new SelectItem(null, "---Choisir---"));
		for(TypeTraitement val : TypeTraitement.types())typeTraitementItems.add( new SelectItem(val.name(),val.value()) );

		sortTraitementItems.add(new SelectItem(null, "---Choisir---"));
		for(SortTraitement val : SortTraitement.types())sortTraitementItems.add( new SelectItem(val.name(),val.value()) );

		//listTraitementTourCompensations = ViewHelper.virementsRecManagerDAOLocal.filter(TraitementTourCompensation.class, null, null, null, null, 0, -1);

		dateDebut = Calendar.getInstance().getTime();
		dateFin = Calendar.getInstance().getTime();
	}

	public void processRecherche(){

		listTraitementTourCompensations = ViewHelper.virementsRecManager.filterTraitementTourCompensation2(dateDebut, dateFin, heure, utilisateur, typeTraitement, sortTraitement);
		count = listTraitementTourCompensations.size();

		for(TraitementTourCompensation t: listTraitementTourCompensations){

			if(t.getSortTraitement()!=null && t.getSortTraitement().equals(SortTraitement.ECHEC)){
				countDoub++;
			}
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

		typeTraitementItems.clear();

		sortTraitementItems.clear();

	}

	public void setCurrentObject(TraitementTourCompensation currentObject) {
		this.currentObject = currentObject;
	}

	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}

	public List<SelectItem> getTypeTraitementItems() {
		return typeTraitementItems;
	}

	public void setTypeTraitementItems(List<SelectItem> typeTraitementItems) {
		this.typeTraitementItems = typeTraitementItems;
	}

	public List<SelectItem> getSortTraitementItems() {
		return sortTraitementItems;
	}

	public void setSortTraitementItems(List<SelectItem> sortTraitementItems) {
		this.sortTraitementItems = sortTraitementItems;
	}

	public String getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(String utilisateur) {
		this.utilisateur = utilisateur;
	}

	public TypeTraitement getTypeTraitement() {
		return typeTraitement;
	}

	public void setTypeTraitement(TypeTraitement typeTraitement) {
		this.typeTraitement = typeTraitement;
	}

	public SortTraitement getSortTraitement() {
		return sortTraitement;
	}

	public void setSortTraitement(SortTraitement sortTraitement) {
		this.sortTraitement = sortTraitement;
	}

	public ReportViewer getReportViewer() {
		return reportViewer;
	}

	public void setReportViewer(ReportViewer reportViewer) {
		this.reportViewer = reportViewer;
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



	public TourCompensation getTourCompensation() {
		return tourCompensation;
	}

	public void setTourCompensation(TourCompensation tourCompensation) {
		this.tourCompensation = tourCompensation;
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

	public String getHeure() {
		return heure;
	}

	public void setHeure(String heure) {
		this.heure = heure;
	}

	public List<TraitementTourCompensation> getListTraitementTourCompensations() {
		return listTraitementTourCompensations;
	}

	public void setListTraitementTourCompensations(
			List<TraitementTourCompensation> listTraitementTourCompensations) {
		this.listTraitementTourCompensations = listTraitementTourCompensations;
	}

	/**public void openReportViewer(){

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
			/*ReportBuilder reportBuilder = new ReportBuilder();
			reportBuilder.complete();



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

	public void openReportDoublons(){

		try{

			if(listTraitementTourCompensations == null || listTraitementTourCompensations.isEmpty()) throw new Exception("Aucun Doublon selectionné dans la Liste!");

			HashMap<String, Object> param = new HashMap<String, Object>();

			param.put("uti", ViewHelper.getSessionUser().getName());
			param.put("periode", new SimpleDateFormat("dd/MM/yyyy").format(dateDebut)+ "  -  " + new SimpleDateFormat("dd/MM/yyyy").format(dateFin));
			param.put("nombreVir", count); //countDoub
			param.put("total", count);
			int nbrDoublonsVal = 0;
			int nbrDoublonsIgn = 0;
			for(TraitementTourCompensation doub: listTraitementTourCompensations){
				if(doub.getSortTraitement()!=null){
					if(doub.getSortTraitement().equals(SortTraitement.ECHEC)){ //doublons validés
						nbrDoublonsVal++;
					}else{
						nbrDoublonsIgn++;
					}
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


	}


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


