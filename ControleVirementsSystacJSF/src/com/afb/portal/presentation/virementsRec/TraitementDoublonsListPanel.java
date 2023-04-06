package com.afb.portal.presentation.virementsRec;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import afb.dsi.dpd.portal.jpa.entities.User;

import com.afb.portal.presentation.models.AbstractPanel;
import com.afb.portal.presentation.models.DialogFormMode;
import com.afb.portal.presentation.models.reportViewer.ReportViewer;
import com.afb.portal.presentation.tools.ExceptionHelper;

import java.util.HashMap;
import java.util.Map;

import com.afb.portal.presentation.tools.ViewHelper;
import com.afb.virementsRec.jpa.datamodel.CaracteristiquesVir;
import com.afb.virementsRec.jpa.datamodel.CaracteristiquesVirItem;
import com.afb.virementsRec.jpa.datamodel.Doublons;
import com.afb.virementsRec.jpa.datamodel.Incoherences;
import com.afb.virementsRec.jpa.datamodel.Parametrages;
import com.afb.virementsRec.jpa.datamodel.Traitement;



public class TraitementDoublonsListPanel extends AbstractPanel {

	private Traitement currentObject = new Traitement();

	//private TraitementDialog entiteDialog = new TraitementDialog();

	private Date dateDebut;

	private Date dateFin;

	private Date oldDateDebut;

	private Date oldDateFin;

	private int index = 0;

	private int nombreVirementTotal;

	private List<Traitement> listTraitement = new ArrayList<Traitement>();

	private Parametrages param =  new Parametrages();

	List<Parametrages> params = new ArrayList<Parametrages>();

	private List<SelectItem> items = new ArrayList<SelectItem>();

	private Map<String, Traitement> mapVirements = new HashMap<>();

	private Map<String, Doublons> mapDoublons = new HashMap<>();

	private Map<Integer, List<Traitement>> mapLots = new HashMap<>();

	private List<Integer>elementsParcourus = new ArrayList<Integer>();

	private List<Doublons> listDoublons = new ArrayList<Doublons>();

	//private List<Incoherences> listIncoherences = new ArrayList<Incoherences>();

	private ReportViewer reportViewer;

	private DoublonsDialog doublonsDialog = new DoublonsDialog();

	//private IncoherencesDialog incoherencesDialog = new IncoherencesDialog();

	private int tailleParLot = 1000;

	private int nombreVirementsRestantsATraiter;

	private int nombreVirementTraite;

	private boolean changedSearch = false;

	private boolean isInBkeveOrBkheve;

	private User u;

	private String user = "";


	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "TraitementDoublonsListPanel";
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Traitement des doublons dans les virements reçus de SYSTAC";
	}

	@Override
	public String getFileDefinition() {
		// TODO Auto-generated method stub
		return "/views/virementsRec/TraitementDoublonsListPanel.xhtml";
	}

	@Override
	public String getChildDialogDefinition() {
		//return null;
		return (this.doublonsDialog != null && this.doublonsDialog.isOpen()) ? doublonsDialog.getFileDefinition() : "/views/home/EmptyPage.xhtml";
	}

	@Override
	public String getSecondChildDefinition() {
		// TODO Auto-generated method stub
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

		listTraitement.clear();
		nombreVirementTotal=0;
		nombreVirementTraite=0;
		nombreVirementsRestantsATraiter=0;
		listDoublons.clear();
		mapVirements.clear();
		tailleParLot=1000;

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

	public int getNombreVirementTotal() {
		return nombreVirementTotal;
	}

	public void setNombreVirementTotal(int nombreVirementTotal) {
		this.nombreVirementTotal = nombreVirementTotal;
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

	public List<Traitement> getListTraitement() {
		return listTraitement;
	}

	public void setListTraitement(List<Traitement> listTraitement) {
		this.listTraitement = listTraitement;
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


	public Map<String, Doublons> getMapDoublons() {
		return mapDoublons;
	}

	public void setMapDoublons(Map<String, Doublons> mapDoublons) {
		this.mapDoublons = mapDoublons;
	}

	public Map<Integer, List<Traitement>> getMapLots() {
		return mapLots;
	}

	public void setMapLots(Map<Integer, List<Traitement>> mapLots) {
		this.mapLots = mapLots;
	}

	public List<Integer> getElementsParcourus() {
		return elementsParcourus;
	}

	public void setElementsParcourus(List<Integer> elementsParcourus) {
		this.elementsParcourus = elementsParcourus;
	}

	public int getTailleParLot() {
		return tailleParLot;
	}

	public void setTailleParLot(int tailleParLot) {
		this.tailleParLot = tailleParLot;
	}

	public int getNombreVirementTraite() {
		return nombreVirementTraite;
	}

	public void setNombreVirementTraite(int nombreVirementTraite) {
		this.nombreVirementTraite = nombreVirementTraite;
	}

	public int getNombreVirementsRestantsATraiter() {
		return nombreVirementsRestantsATraiter;
	}

	public void setNombreVirementsRestantsATraiter(
			int nombreVirementsRestantsATraiter) {
		this.nombreVirementsRestantsATraiter = nombreVirementsRestantsATraiter;
	}

	/**
	 * Methode de recherche d'un virement
	 */
	public void processRecherche(){

		try{

			u = ViewHelper.getSessionUser();

			if(user!=null)
				user = u.getLogin();
			else{
				this.error = true;
				ExceptionHelper.showError("Veuillez vous reconnecter!!!!!", this);
				return;
			}

			oldDateDebut = dateDebut;
			oldDateFin = dateFin;

			Long countTotalVirements = ViewHelper.virementsRecManager.countVirementsInDB(dateDebut, dateFin);
			nombreVirementTotal = countTotalVirements.intValue();
			Long countTotalVirementsTraites = ViewHelper.virementsRecManager.countVirementsTraitesDoublonsInDB(dateDebut, dateFin, Boolean.TRUE);
			nombreVirementTraite = countTotalVirementsTraites.intValue();

			if(!dateDebut.equals(oldDateDebut) || !dateFin.equals(oldDateFin)) 
				changedSearch = true;  //on a changé la période de recherche
			System.out.println("****count virements****"+nombreVirementTotal);
			System.out.println("****count virements traité****"+nombreVirementTraite);


			//Important pour savoir où faire les updates dans le cas des doublons et incohérences validés
			int i = ViewHelper.virementsRecManager.checkIfEveInBkEveOrBkheve(dateDebut, dateFin);
			if(i==1){
				isInBkeveOrBkheve = true; //bkeve
			}
			else if(i==2){
				isInBkeveOrBkheve = false; //bkheve
			}


			if(!changedSearch){

				listTraitement = ViewHelper.virementsRecManager.filterVirementsDoublons(dateDebut, dateFin, Boolean.FALSE, tailleParLot,user);
				//nombreVirementTraite = nombreVirementTraite + listTraitement.size();
				if(nombreVirementTotal==0&&nombreVirementTraite==0){
					countTotalVirements = ViewHelper.virementsRecManager.countVirementsInDB(dateDebut, dateFin);
					nombreVirementTotal = countTotalVirements.intValue();
					countTotalVirementsTraites = ViewHelper.virementsRecManager.countVirementsTraitesDoublonsInDB(dateDebut, dateFin, Boolean.TRUE);
					nombreVirementTraite = countTotalVirementsTraites.intValue();
				}
				nombreVirementsRestantsATraiter = nombreVirementTotal - nombreVirementTraite;
			}else{

				nombreVirementTraite=0;
				nombreVirementsRestantsATraiter=0;
				listTraitement = ViewHelper.virementsRecManager.filterVirementsDoublons(dateDebut, dateFin, Boolean.FALSE, tailleParLot,user);
				//nombreVirementTraite = nombreVirementTraite + listTraitement.size();
				nombreVirementsRestantsATraiter = nombreVirementTotal - nombreVirementTraite;

				changedSearch=false;
			}



		}catch(Exception e){

			e.printStackTrace();
			// Etat d'erreur
			this.error = true;
			// Traitement de l'exception
			ExceptionHelper.threatException(e);

		}


	}

	public Traitement findLastElementDoublons(){

		params = ViewHelper.virementsRecManager.filterParams();

		Traitement trait = null;

		if(params!=null&&!params.isEmpty()){

			//On récupère le paramétrage enregistré
			param = params.get(0);

			Long lastElementOfLastLotDoublons = param.getLastElementOfLotDoublons()!=null? param.getLastElementOfLotDoublons():null;
			trait = ViewHelper.virementsRecManager.getLastElementOfLastLotDoublons(lastElementOfLastLotDoublons);
		}

		return trait;

	}

	public boolean comparerVirements(Traitement t1, Traitement t2){

		boolean result=false;

		List<Traitement>listVir = new ArrayList<Traitement>();
		listVir.add(t1);
		listVir.add(t2);

		System.out.println("listVir.size()" +listVir.size());

		String key = "";
		String firstKey="";

		params = ViewHelper.virementsRecManager.filterParams();
		List<CaracteristiquesVir> caracteristiquesDoublons=new ArrayList<CaracteristiquesVir>();

		if(params!=null&&!params.isEmpty()){

			//On récupère le paramétrage enregistré
			param = params.get(0);

			//lastElementOfLastLotDoublons = param.getLastElementOfLotDoublons()!=null? param.getLastElementOfLotDoublons():null;

			//lastElementOfLastLotIncoherences = param.getLastElementOfLotIncoherences()!=null?param.getLastElementOfLotIncoherences():null;

			//Récupération de la liste des caracteristiques pris en compte pour la vérification des doublons dans les virements
			for(CaracteristiquesVir cv:param.getTypeCarac()){

				if(cv.getValide().equals(Boolean.TRUE)){

					caracteristiquesDoublons.add(cv);
					System.out.println("**caracteristique***"+ cv.getCaracteristiquesItems());
				}
			}

			//Récupération de la liste des caracteristiques pris en compte pour la vérification des doublons dans les virements 
			//List<CaracteristiquesVir> caracteristiquesDoublons =  param.getTypeCarac();


			if(caracteristiquesDoublons!=null&&!caracteristiquesDoublons.isEmpty()){

				int counter = 0;
				for(Traitement t:listVir){
					key="";
					//Pour chaque virement, on récupère ses caracteristiques de doublons
					for(CaracteristiquesVir c: caracteristiquesDoublons){

						/**if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.agence)){

							key = key + "" + t.getAgence();
						}
						if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.dco)){

							key = key + "" + t.getDco();
						}
						if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.lib)){

							key = key + "" + t.getLib();
						}*/
						if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.montant)){

							key = key + "" + t.getMontant();
						}
						if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.ncp)){

							key = key + "" + t.getNcp();
						}
						if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.ncp_donneur_ordre)){

							key = key + "" + t.getNcpDonneurOrdre();
						}
						if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.nom_benef)){

							key = key + "" + t.getNomBeneficiaire();
						}
						if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.nom_donneur_ordre)){

							key = key + "" + t.getNomDonneurOrdre();
						}


						System.out.println("**key***"+key);
					}

					counter++;

					if(counter!=listVir.size()){
						System.out.println("**counter    ***"+counter);
						firstKey = key;
						System.out.println("**first  OUT key***"+firstKey);
					}
				}

				System.out.println("**counter out***"+counter);

				System.out.println("**first key***"+firstKey);
				System.out.println("**key***"+key);

				if(key.equals(firstKey) && !key.isEmpty() &&!firstKey.isEmpty()){
					result = true;
				}else
					result = false;
			}	
		}else{

			this.error = true;
			ExceptionHelper.showError("Veuillez d'abord enregistrer les paramètres", this);
		}

		return result;
	}



	/**
	 * Méthode de vérification des doublons
	 */
	public void processFindDoublons(){

		try {

			if(listTraitement == null || listTraitement.isEmpty()) throw new Exception("Aucun virement selectionné dans la Liste!");

			/**Process de vérification des doublons*/
			if(listTraitement!=null&&!listTraitement.isEmpty()){

				listDoublons.clear();


				System.out.println("**Nous sommes dans la liste des virements***");


				Traitement trait = findLastElementDoublons();

				System.out.println("**Nous sortons dans la liste des virements***");


				String color="";
				String previousColor="";
				List<Traitement>list=new ArrayList<Traitement>();

				if(trait!=null){

					boolean verifier = false;

					verifier = comparerVirements(trait, listTraitement.get(0));
					if(verifier){
						list=new ArrayList<Traitement>();
						list.add(listTraitement.get(0));
						list.add(trait);
						mapLots.put(0,list);

						elementsParcourus.add(0);
					}

				}

				//On parcours la liste des virements en vue de détecter des doublons
				for(int t=1; t<listTraitement.size(); t++){

					if(listTraitement.get(t-1).getNcp().equals(listTraitement.get(t).getNcp())){

						boolean verifier = false;

						for(Integer k: mapLots.keySet()){

							verifier = comparerVirements(listTraitement.get(k), listTraitement.get(t));
							if(verifier){

								mapLots.get(k).add(listTraitement.get(t));

								break;
							}else{

							}
						}


						if(verifier==false){

							for(int j=t-1; j>=t-1 ;j--){

								if(!elementsParcourus.contains(j)){

									boolean check = comparerVirements(listTraitement.get(j), listTraitement.get(t));
									if(check){
										list=new ArrayList<Traitement>();
										list.add(listTraitement.get(t));
										list.add(listTraitement.get(j));
										mapLots.put(j,list);

										elementsParcourus.add(t);
										elementsParcourus.add(j);

										j=-1;
									}else{

									}
								}
								else{

								}
							}

						}
						else{

						}


					}

					listTraitement.get(t).setTraiteDoublons(Boolean.TRUE);

				}
				listTraitement.get(0).setTraiteDoublons(Boolean.TRUE);


				System.out.println("**AFTER BIG FOR**");


				//List<Traitement>trait=new ArrayList<Traitement>();
				Map<String, List<Traitement>> mapLotsColeurs = new HashMap<>();

				if(mapLots.isEmpty()){
					//Aucun doublon;
					System.out.println("**MAP LOTS EMPTY***");

				}else{
					Doublons doublon = new Doublons();

					for(int k: mapLots.keySet()){

						if(!previousColor.equals("blue") || previousColor.isEmpty())
							color = "blue";
						else
							color = "green";
						mapLotsColeurs.put(color, mapLots.get(k));

						for(Traitement t:mapLots.get(k)){

							doublon = new Doublons(t.getAgence(), t.getNcp(), t.getCle(), t.getDevise(), t.getNomBeneficiaire(), t.getNomBeneficiaireAmplitude(), t.getDsai(),t.getDco(), t.getDva(), t.getOpe(), t.getEve(), t.getMontant(), t.getLib(), t.getEtat(), t.getUti(), t.getCodeEtabDonneurOrdre(), t.getCodeGuicherDonneurOrdre(), t.getNcpDonneurOrdre(), t.getCleDonneurOrdre(), t.getNomDonneurOrdre(), t.getDateTraitement(),color,user);
							listDoublons.add(doublon);
							System.out.println("****listDoublons size****"+listDoublons.size());

						}


						previousColor = color;

					}

					mapLots.clear();
					elementsParcourus.clear();

				}


				if(listDoublons!=null&&!listDoublons.isEmpty()){

					for(Doublons dd:listDoublons){
						if(!new SimpleDateFormat("dd/MM/yyyy").format(dd.getDsai()).equals(new SimpleDateFormat("dd/MM/yyyy").format(new Date()))){
							dd.setDisable(Boolean.TRUE);
						}
					}
					//Ouverture du Panneau de Validation des soublons 
					/**Passer la liste des traitements en paramètres*/
					doublonsDialog = new DoublonsDialog(DialogFormMode.UPDATE, new Doublons(), param, listDoublons,listTraitement, nombreVirementTotal, this, dateDebut, dateFin, isInBkeveOrBkheve);
					System.out.println("**NEW DOUBLON***");
					//Ouverture
					doublonsDialog.open();

					//listDoublons.clear();

				}else{

					for(Traitement t: listTraitement){

						if(t.equals(listTraitement.get(listTraitement.size()-1))){

							if(param.getId()!=null){
								param = ViewHelper.virementsRecManager.findByPrimaryKey(Parametrages.class, param.getId(), null);

								param.setLastElementOfLotDoublons(t.getId());

								ViewHelper.virementsRecManager.updateParametrages(param);
							}
						}
						ViewHelper.virementsRecManager.updateTraitement(t);

					}

					this.information = true;

					ExceptionHelper.showInformation("Il n'y a pas eu de doublons pour la période spécifiée", this);
				}
				listTraitement.clear();





				///String key = "";
				///String oldKey="";
				//List<String>keys = new ArrayList<String>();
				//Doublons doublon = new Doublons();




				//Si l'élément courant est égale à l'élément précédent, nous constatons des doublons
				///if(key.equals(oldKey) && !key.isEmpty() &&!oldKey.isEmpty()){

				/**On ajoute les doublons dans la liste des doublons*/
				///listDoublons.add(mapDoublons.get(oldKey));
				///listDoublons.add(mapDoublons.get(key));


				//

				////}else{ //si l'élément courant est différent de l'élément précédent

				//On parcours la liste des clés déjà parcourus
				///for(String kk:keys){
				////if(kk.equals(key)){ //Si une clé dans la liste est égale à la clé courante, alors celà fait l'objet d'un doublon

				/**On ajoute les doublons dans la liste des doublons*/
				///	listDoublons.add(mapDoublons.get(kk));
				///listDoublons.add(mapDoublons.get(key));
				///}
				///}

				//La clé courante devient l'ancienne clé
				///oldKey = key;
				///keys.add(oldKey);  //On ajoute la clé courante devenue ancienne clé dans la liste des clés
				///}

				///doublon = new Doublons(t.getAgence(), t.getNcp(), t.getCle(), t.getDevise(), t.getNomBeneficiaire(), t.getDco(), t.getDva(), t.getOpe(), t.getMontant(), t.getLib(), t.getEtat(), t.getUti(), t.getCodeEtabDonneurOrdre(), t.getCodeGuicherDonneurOrdre(), t.getNcpDonneurOrdre(), t.getCleDonneurOrdre(), t.getNomDonneurOrdre(), t.getDateTraitement());
				///mapDoublons.put(key, doublon); //On ajoute la clé courante dans la map des clé-virements

				///}


				////if(listDoublons!=null&&!listDoublons.isEmpty()){
				// Ouverture du Panneau de Validation des soublons 
				////doublonsDialog = new DoublonsDialog(DialogFormMode.UPDATE, new Doublons(), listDoublons, count, this, dateDebut, dateFin);

				// Ouverture
				///doublonsDialog.open();
				///}else{

				///this.information = true;
				////ExceptionHelper.showInformation("Il n'y a pas eu de doublons pour la période spécifiée", this);
				////}

			}



		} catch (Exception e) {

			// Etat d'erreur
			this.error = true;
			// Traitement de l'exception
			ExceptionHelper.threatException(e);
		}


	}


	public boolean countBeneficiaireSubTokens(String [] benefC, String [] benefS,  int benefCBLength, int  benefSystacLength){

		boolean flag = false;

		int count = 0;

		//Si la taille du benef corebanking est supérieur à taille benef systac divisé par 2 plus 1 et inversement
		if( (benefCBLength >= (benefSystacLength/2) + 1)  || (benefSystacLength >= (benefCBLength/2) + 1 ) ){

			for(int i=0; i<benefC.length;i++){

				for(int j=0; j<benefS.length; j++){

					if(benefC[i].equalsIgnoreCase(benefS[j])){

						count++;

					}

				}
			}
		}else
			count = 0;

		if(benefCBLength==benefSystacLength && (benefCBLength == 1 || benefCBLength ==2)){

			count = benefCBLength;

		}else{

			int max = Math.max(benefCBLength, benefSystacLength);
			if(count>=max){

				flag = false;  //pas incohérences
			}
			else
				flag = true; //incohérences

		}

		return flag;

	}



	public Traitement getElement(){

		Traitement entite = null;

		try {

			// Si la selection est nulle
			if(selection == null){
				if(!this.listTraitement.isEmpty()){
					return this.listTraitement.get(0);
				}else{
					// On sort
					return null;
				}
			}

			// Obtention des lignes selectionnees
			Iterator<Object> iterator = selection.getKeys();


			// Si l'iterateur est null
			if(iterator == null){
				if(!this.listTraitement.isEmpty()){
					return this.listTraitement.get(0);
				}else{
					// On sort
					return null;
				}
			}

			// Si l'iterateur a un element
			if(iterator.hasNext()) {

				// Index selectionné
				index = (Integer) iterator.next();

			}

			// Si l'index n'est pas dans l'intervalle du modele
			if(index < 0 || index >= this.listTraitement.size()) {

				if(!this.listTraitement.isEmpty()){
					return this.listTraitement.get(0);
				}else{
					// On sort
					return null;
				}

			}

			System.out.println("------getElement " + index);
			// L'facture selectionne
			entite = this.listTraitement.get(index);

			entite = ViewHelper.virementsRecManager.findByPrimaryKey(Traitement.class, entite.getId(), null);


		} catch (Exception e) {

			// Etat d'erreur
			this.error = true;
			// Traitement de l'exception
			ExceptionHelper.threatException(e);
		}

		return entite;

	}



	public void processConsultation() {

		try {



		} catch (Exception e) {
			e.printStackTrace();
			// Etat d'erreur
			this.error = true;

			// Traitement de l'exception
			ExceptionHelper.threatException(e);
		}
	}



	public void processAnnuler() {

		try {



		}catch(Exception e){
			e.printStackTrace();
			// Etat d'erreur
			this.error = true;

			// Traitement de l'exception
			ExceptionHelper.threatException(e);
		}
	}



	public void processInitialiser() {

		try {


		}catch(Exception e){
			e.printStackTrace();
			// Etat d'erreur
			this.error = true;

			// Traitement de l'exception
			ExceptionHelper.threatException(e);
		}
	}


}
