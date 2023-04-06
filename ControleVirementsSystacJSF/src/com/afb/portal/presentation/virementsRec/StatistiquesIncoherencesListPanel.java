package com.afb.portal.presentation.virementsRec;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import com.afb.portal.presentation.models.AbstractPanel;
import com.afb.portal.presentation.models.reportViewer.ReportViewer;
import com.afb.portal.presentation.tools.ExceptionHelper;

import java.util.HashMap;
import java.util.Map;

import com.afb.portal.presentation.tools.ViewHelper;
import com.afb.virementsRec.jpa.datamodel.Doublons;
import com.afb.virementsRec.jpa.datamodel.Incoherences;
import com.afb.virementsRec.jpa.datamodel.Parametrages;
import com.afb.virementsRec.jpa.datamodel.Traitement;

import java.text.SimpleDateFormat;



public class StatistiquesIncoherencesListPanel extends AbstractPanel {

	private Traitement currentObject = new Traitement();

	//private TraitementDialog entiteDialog = new TraitementDialog();

	private Date dateDebut;

	private Date dateFin;

	private int index = 0;

	private int count;
	
	private int countInco;

	//private List<Traitement> listTraitement = new ArrayList<Traitement>();

	List<Parametrages> params = new ArrayList<Parametrages>();

	private List<SelectItem> items = new ArrayList<SelectItem>();

	private Map<String, Traitement> mapVirements = new HashMap<>();

	private Map<String, Doublons> mapDoublons = new HashMap<>();

	private Map<String, Incoherences> mapIncoherences = new HashMap<>();

	private List<Doublons> listDoublons = new ArrayList<Doublons>();

	private List<Incoherences> listIncoherences = new ArrayList<Incoherences>();

	private ReportViewer reportViewer;

	private DoublonsDialog doublonsDialog = new DoublonsDialog();

	private IncoherencesDialog incoherencesDialog = new IncoherencesDialog();




	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "StatistiquesIncoherencesListPanel";
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Statistiques des contrôles des incohérences des virements reçus de SYSTAC";
	}

	@Override
	public String getFileDefinition() {
		// TODO Auto-generated method stub
		return "/views/virementsRec/StatistiquesIncoherencesListPanel.xhtml";
	}

	@Override
	public String getChildDialogDefinition() {

		//return (this.doublonsDialog != null && this.doublonsDialog.isOpen()) ? doublonsDialog.getFileDefinition() : "/views/home/EmptyPage.xhtml";
		return null;
	}

	@Override
	public String getSecondChildDefinition() {

		//return (this.incoherencesDialog != null && this.incoherencesDialog.isOpen()) ? incoherencesDialog.getFileDefinition() : "/views/home/EmptyPage.xhtml";

		return null;
	}

	@Override
	protected void initComponents(){

		dateDebut = Calendar.getInstance().getTime();
		dateFin = Calendar.getInstance().getTime();
	}


	@Override
	protected void disposeResourceOnClose(){

		super.disposeResourceOnClose();


		count=0;
		listDoublons.clear();
		mapVirements.clear();
		listIncoherences.clear();

	}


	public Traitement getCurrentObject() {
		return currentObject;
	}

	public void setCurrentObject(Traitement currentObject) {
		this.currentObject = currentObject;
	}


	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}



	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
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

	public List<SelectItem> getItems() {
		return items;
	}

	public void setItems(List<SelectItem> items) {
		this.items = items;
	}


	public List<Parametrages> getParams() {
		return params;
	}

	public void setParams(List<Parametrages> params) {
		this.params = params;
	}

	public Map<String, Traitement> getMapVirements() {
		return mapVirements;
	}

	public void setMapVirements(Map<String, Traitement> mapVirements) {
		this.mapVirements = mapVirements;
	}

	public List<Doublons> getListDoublons() {
		return listDoublons;
	}

	public void setListDoublons(List<Doublons> listDoublons) {
		this.listDoublons = listDoublons;
	}

	public List<Incoherences> getListIncoherences() {
		return listIncoherences;
	}

	public void setListIncoherences(List<Incoherences> listIncoherences) {
		this.listIncoherences = listIncoherences;
	}

	public ReportViewer getReportViewer() {
		return reportViewer;
	}

	public void setReportViewer(ReportViewer reportViewer) {
		this.reportViewer = reportViewer;
	}

	public DoublonsDialog getDoublonsDialog() {
		return doublonsDialog;
	}

	public void setDoublonsDialog(DoublonsDialog doublonsDialog) {
		this.doublonsDialog = doublonsDialog;
	}

	public IncoherencesDialog getIncoherencesDialog() {
		return incoherencesDialog;
	}

	public void setIncoherencesDialog(IncoherencesDialog incoherencesDialog) {
		this.incoherencesDialog = incoherencesDialog;
	}

	/**
	 * Methode de recherche des contrôles d'incohérences
	 */
	public void processRecherche(){

		listIncoherences = ViewHelper.virementsRecManager.filterIncoherences(dateDebut, dateFin);
		countInco = listIncoherences.size();
		

		Long countTotalVirements = ViewHelper.virementsRecManager.countVirementsInDB(dateDebut, dateFin);
		count = countTotalVirements.intValue();


	}



	

	public void openReportIncoherences(){

		try{

			listDoublons.clear();
			if(listIncoherences == null || listIncoherences.isEmpty()) throw new Exception("Aucune incohérence selectionnée dans la Liste!");

			HashMap<String, Object> param = new HashMap<String, Object>();

			// Affichage du rapport de traitement 
			List<Incoherences> repports = new ArrayList<Incoherences>();

			Incoherences raport = new Incoherences();
			raport.setItems(listIncoherences);
			repports.add(raport);


			param.put("uti", ViewHelper.getSessionUser().getName());
			param.put("periode", new SimpleDateFormat("dd/MM/yyyy").format(dateDebut) + "  -  " + new SimpleDateFormat("dd/MM/yyyy").format(dateFin));
			param.put("nombreVir", countInco); //count
			param.put("total", countInco);
			int nbrIncoherencesVal = 0;
			int nbrIncoherencesIgn = 0;
			for(Incoherences doub: listIncoherences){
				if(doub.getValide().equals(Boolean.TRUE)){ //doublons validés
					nbrIncoherencesVal++;
				}else{
					nbrIncoherencesIgn++;
				}
			}
			param.put("nbrIncoherencesVal", nbrIncoherencesVal);
			param.put("nbrIncoherencesIgn", nbrIncoherencesIgn);

			System.out.println("*****repports.size()******" + repports.size());

			//param.put("items", statistiques);
			System.out.println("****Before Report Viewer****");
			reportViewer = new ReportViewer(listIncoherences,"rapportIncoherences.jasper", param, "afb.statistique.title", this,"/views/repport/ReportStatIncoherences.xhtml");
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
}

/*****
 * 
 * 
 * 	<h:outputText value="Total: " />
			<h:inputText id="totalVir" style="color:blue;"
				value="#{statistiquesIncoherencesListPanel.count}"
				reRender="#{clientArea.ID}" disabled="true" />
 */
