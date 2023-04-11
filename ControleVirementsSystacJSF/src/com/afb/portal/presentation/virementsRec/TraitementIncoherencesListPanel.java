package com.afb.portal.presentation.virementsRec;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import org.jboss.ejb.plugins.cmp.jdbc2.FindByPrimaryKeyCommand;

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
import com.afb.virementsRec.jpa.datamodel.MotsClesEtCharSpeciaux;
import com.afb.virementsRec.jpa.datamodel.Parametrages;
import com.afb.virementsRec.jpa.datamodel.Traitement;

import org.apache.commons.text.similarity.LevenshteinDistance;


public class TraitementIncoherencesListPanel extends AbstractPanel {

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

	private Map<Long, Traitement> mapLots = new HashMap<>();

	private List<Integer>elementsParcourus = new ArrayList<Integer>();

	//private List<Doublons> listDoublons = new ArrayList<Doublons>();

	private List<Incoherences> listIncoherences = new ArrayList<Incoherences>();

	private ReportViewer reportViewer;

	//private DoublonsDialog doublonsDialog = new DoublonsDialog();

	private IncoherencesDialog incoherencesDialog = new IncoherencesDialog();

	private int tailleParLot = 1000;

	private int nombreVirementsRestantsATraiter;

	private int nombreVirementTraite;

	private boolean changedSearch = false;

	private boolean isInBkeveOrBkheve;

	private User u;

	private String user = "";

	List<Parametrages> listParams = new ArrayList<Parametrages>();

	private List<MotsClesEtCharSpeciaux> listMotsCles = new ArrayList<MotsClesEtCharSpeciaux>();





	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "TraitementIncoherencesListPanel";
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Traitement des incohérences dans les virements reçus de SYSTAC";
	}

	@Override
	public String getFileDefinition() {
		// TODO Auto-generated method stub
		return "/views/virementsRec/TraitementIncoherencesListPanel.xhtml";
	}

	@Override
	public String getChildDialogDefinition() {
		return null;
		//return (this.doublonsDialog != null && this.doublonsDialog.isOpen()) ? doublonsDialog.getFileDefinition() : "/views/home/EmptyPage.xhtml";
	}

	@Override
	public String getSecondChildDefinition() {
		// TODO Auto-generated method stub
		return (this.incoherencesDialog != null && this.incoherencesDialog.isOpen()) ? incoherencesDialog.getFileDefinition() : "/views/home/EmptyPage.xhtml";
	}

	@Override
	protected void initComponents(){

		dateDebut = Calendar.getInstance().getTime();
		//dateFin = Calendar.getInstance().getTime();

		u = ViewHelper.getSessionUser();

		if(user!=null)
			user = u.getLogin();
		else{
			this.error = true;
			ExceptionHelper.showError("Veuillez vous reconnecter!!!!!", this);
			return;
		}

		listParams = ViewHelper.virementsRecManager.filterParams();
		if(listParams!=null && !listParams.isEmpty()){
			listMotsCles = listParams.get(0).getMotsClesEtCharSpeciauxs();
		}

	}


	@Override
	protected void disposeResourceOnClose(){

		super.disposeResourceOnClose();

		listTraitement.clear();
		nombreVirementTotal=0;
		nombreVirementTraite=0;
		nombreVirementsRestantsATraiter=0;
		mapVirements.clear();
		listIncoherences.clear();
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

	public IncoherencesDialog getIncoherencesDialog() {
		return incoherencesDialog;
	}

	public void setIncoherencesDialog(IncoherencesDialog incoherencesDialog) {
		this.incoherencesDialog = incoherencesDialog;
	}

	public Map<String, Doublons> getMapDoublons() {
		return mapDoublons;
	}

	public void setMapDoublons(Map<String, Doublons> mapDoublons) {
		this.mapDoublons = mapDoublons;
	}

	public Map<Long, Traitement> getMapLots() {
		return mapLots;
	}

	public void setMapLots(Map<Long, Traitement> mapLots) {
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
	 * Methode de recherche d'une facture valide
	 */
	public void processRecherche(){

		try{

			/**oldDateDebut = dateDebut;
			oldDateFin = dateFin;

			Long countTotalVirements = ViewHelper.virementsRecManager.countVirementsInDB(dateDebut, dateFin);
			nombreVirementTotal = countTotalVirements.intValue();
			Long countTotalVirementsTraites = ViewHelper.virementsRecManager.countVirementsTraitesIncoherencesInDB(dateDebut, dateFin, Boolean.TRUE);
			nombreVirementTraite = countTotalVirementsTraites.intValue();

			if(!dateDebut.equals(oldDateDebut) || !dateFin.equals(oldDateFin)) 
				changedSearch = true;  //on a changé la période de recherche
			System.out.println("****count virements****"+nombreVirementTotal);


			//Important pour savoir où faire les updates dans le cas des doublons et incohérences validés
			int i = ViewHelper.virementsRecManager.checkIfEveInBkEveOrBkheve(dateDebut, dateFin);
			if(i==1){
				isInBkeveOrBkheve = true; //bkeve
			}
			else if(i==2){

				isInBkeveOrBkheve = false; //bkheve
			}


			if(!changedSearch){

				listTraitement = ViewHelper.virementsRecManager.filterVirementsIncoherences(dateDebut, dateFin, Boolean.FALSE, tailleParLot, user);
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
				listTraitement = ViewHelper.virementsRecManager.filterVirementsIncoherences(dateDebut, dateFin, Boolean.FALSE, tailleParLot, user);
				//nombreVirementTraite = nombreVirementTraite + listTraitement.size();
				nombreVirementsRestantsATraiter = nombreVirementTotal - nombreVirementTraite;

				changedSearch=false;
			}*/



		}catch(Exception e){

			e.printStackTrace();
			// Etat d'erreur
			this.error = true;
			// Traitement de l'exception
			ExceptionHelper.threatException(e);

		}


	}


	public Traitement findLastElementIncoherences(){

		params = ViewHelper.virementsRecManager.filterParams();

		Traitement trait = null;

		if(params!=null&&!params.isEmpty()){

			//On récupère le paramétrage enregistré
			param = params.get(0);

			Long lastElementOfLastLotIncoherences = param.getLastElementOfLotIncoherences()!=null? param.getLastElementOfLotIncoherences():null;
			trait = ViewHelper.virementsRecManager.getLastElementOfLastLotIncoherences(lastElementOfLastLotIncoherences);
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

			//lastElementOfLastLotDoublons = param.getLastElementOfLotDoublons();

			//lastElementOfLastLotIncoherences = param.getLastElementOfLotIncoherences();

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



	public boolean countBeneficiaireSubTokens(String [] benefC, String [] benefS){

		int benefCBLength = benefC.length;
		int  benefSystacLength = benefS.length;

		boolean flag = false;

		int count = 0;

		//Si la taille du benef corebanking est supérieur à taille benef systac divisé par 2 plus 1 et inversement
		if( (benefCBLength >= (benefSystacLength/2) + 1)  || (benefSystacLength >= (benefCBLength/2) + 1 ) ){

			System.out.println("****In countBeneficiaireSubTokens if*****");

			for(int i=0; i<benefC.length;i++){

				for(int j=0; j<benefS.length; j++){

					if(benefC[i].equalsIgnoreCase(benefS[j])){

						count++;
						System.out.println("***count****" + count);

					}

				}
			}
		}else
			count = 0;

		if(benefCBLength==benefSystacLength && (benefCBLength == 1 || benefCBLength ==2)){

			count = benefCBLength;
			flag = false;

			System.out.println("***first case****" + count);

		}else{

			int max = (int)(Math.max(benefCBLength, benefSystacLength)/2)+1;
			System.out.println("*****count***"+ count);
			System.out.println("*****max***"+ max);
			System.out.println("*****benefCBLength***"+ benefCBLength);
			System.out.println("*****benefSystacLength***"+ benefSystacLength);
			if(count>=max){

				flag = false;  //pas incohérences

				System.out.println("***second case****" + count);
			}
			else
				flag = true; //incohérences

			System.out.println("***third case****" + count);

		}

		return flag;

	}


	public boolean countBeneficiaireSubTokensWithLevenshteinAlgo(String [] benefC, String [] benefS){

		int benefCBLength = benefC.length;
		int  benefSystacLength = benefS.length;

		boolean flag = false;

		int count = 0;

		//Si la taille du benef corebanking est supérieur à taille benef systac divisé par 2 plus 1 et inversement
		if( (benefCBLength >= (benefSystacLength/2) + 1)  || (benefSystacLength >= (benefCBLength/2) + 1 ) ){

			System.out.println("****In countBeneficiaireSubTokens if*****");

			for(int i=0; i<benefC.length;i++){

				for(int j=0; j<benefS.length; j++){

					if(calculLevenshtein(benefS[j].toUpperCase(), benefC[i].toUpperCase())>=listParams.get(0).getSeuil()){ //On applique l'algo de Levenshtein
						count++;
						System.out.println("***count****" + count);
					}
				}
			}
		}else
			count = 0;

		if(benefCBLength==benefSystacLength && (benefCBLength == 1 || benefCBLength ==2)){

			count = benefCBLength;
			flag = false;

			System.out.println("***first case****" + count);

		}else{

			int max = (int)(Math.max(benefCBLength, benefSystacLength)/2)+1;
			System.out.println("*****count***"+ count);
			System.out.println("*****max***"+ max);
			System.out.println("*****benefCBLength***"+ benefCBLength);
			System.out.println("*****benefSystacLength***"+ benefSystacLength);
			if(count>=max){

				flag = false;  //pas incohérences

				System.out.println("***second case****" + count);
			}
			else
				flag = true; //incohérences

			System.out.println("***third case****" + count);

		}

		return flag;

	}


	public int traitementSigle(String benefSystac, String benefCB, String sigle){

		//Si le nom systac est égale au nom CB
		if(benefSystac.trim().equalsIgnoreCase(benefCB.trim())){
			return 0;
		}

		//Si le nom systac est contenu dans le nom CB et inversement
		if((benefSystac.trim().contains(benefCB.trim())) || (benefCB.trim().contains(benefSystac.trim()))){
			return 1;
		}

		if(sigle!=null && !sigle.isEmpty()){
			//Si le nom systac est égale au sigle
			if(benefSystac.trim().equalsIgnoreCase(sigle.trim())){
				return 0;
			}

			//Si le nom systac est contenu dans le sigle et inversement

			if(benefSystac.trim().contains(sigle.trim()) || sigle.trim().contains(benefSystac.trim())){
				return 2;
			}
		}

		return 3;
	}

	public double calculLevenshtein(String benefSystac, String benefCB){

		LevenshteinDistance distance = LevenshteinDistance.getDefaultInstance();

		int nb = distance.apply(benefSystac, benefCB);

		int taille = Math.max(benefSystac.length(), benefCB.length());

		double incoherence = ((double)nb)/taille;

		double similitude = 1 - incoherence;

		return similitude;
	}

	/**
	 * Méthode de vérification des incohérences
	 */
	public void processFindIncoherences() {

		try {

			//if(!listTraitement.isEmpty())listTraitement.clear();
			listTraitement = ViewHelper.virementsRecManager.filterVirementsIncoherences(dateDebut, dateFin, Boolean.FALSE, 0, user);  //boolean et 0 ne servent à rien

			initComponents();

			if(listTraitement == null){
				throw new Exception("Vérifier vos paramétrages ou la connexion au Core Banking!");
			}
			if(listTraitement.isEmpty()){
				//throw new Exception("Aucune incohérence encore détectée aujourd'hui!");
			}

			/*****New Traitement*******/
			if(listTraitement!=null && !listTraitement.isEmpty()){
				listIncoherences.clear();

				String benefSystac = "";
				String benefCB = "";
				String sigle = "";
				String color="";
				String previousColor="";

				boolean isGestionSigle=false;
				boolean isGestionAlgo2=false;

				//On vérifie les Sigle (si le mode gestion sigle est activé)
				if(listParams!=null && !listParams.isEmpty()){
					if(listParams.get(0).getGestionSigle()!=null && listParams.get(0).getGestionSigle().equals(Boolean.TRUE)){
						isGestionSigle = true;
					}
					if(listParams.get(0).getGestionAlgo2()!=null && listParams.get(0).getGestionAlgo2().equals(Boolean.TRUE)){
						isGestionAlgo2 = true;
					}
				}

				for(Traitement t: listTraitement){

					benefSystac = t.getNomBeneficiaire();
					benefCB = t.getNomBeneficiaireAmplitude();
					sigle = t.getSigle();

					if(benefCB!=null&&!benefCB.isEmpty() && benefSystac!=null&&!benefSystac.isEmpty()){

						int result=0;
						//----------------Si le mode gestion sigle est activé----------------------------------
						if(isGestionSigle){
							result = traitementSigle(benefSystac,benefCB,sigle);
							if(result==2){ //Si le sigle et systac ont une relation où un est contenu dans l'autre, alors on affecte le sigle au benef Amplitude pour faire la comparaison par la suite dans Levenshtein
								benefCB = sigle;
							}
						}else{
							result=3;
						}
						//-------------------------------------------------------------------------------------

						//-----------------------Si étape 1 pas OK, on enlève les mots clés--------------------
						if(result==1||result==2||result==3){ //si le result!=0, donc pas d'égalité entre systac et CB ou systac et sigle

							String [] resultArraySystac = benefSystac.trim().split("\\s+"); //on split sur l'espace
							String [] resultArrayCB = benefCB.trim().split("\\s+"); //on split sur l'espace

							List<String> listSystacTokens = new ArrayList<String>();
							List<String> listCBTokens = new ArrayList<String>();
							List<String> listSystacTokensDeleted = new ArrayList<String>();
							List<String> listCBTokensDeleted = new ArrayList<String>();

							for(String token : resultArraySystac){
								listSystacTokens.add(token.trim());
							}
							for(String token2 : resultArrayCB){
								listCBTokens.add(token2.trim());
							}

							for(MotsClesEtCharSpeciaux cle:listMotsCles){ //parcours de la liste des mots clés
								for(String token : listSystacTokens){ //parcours des tokens du nom systac
									if(token.equalsIgnoreCase(cle.getMotCles().trim())){
										listSystacTokensDeleted.add(token);
									}
								}
								for(String token2 : listCBTokens){ //parcours des token du nom CB
									if(token2.equalsIgnoreCase(cle.getMotCles().trim())){ 
										listCBTokensDeleted.add(token2);
									}
								}
							}

							listSystacTokens.removeAll(listSystacTokensDeleted);
							listCBTokens.removeAll(listCBTokensDeleted);

							listSystacTokensDeleted.clear();
							listCBTokensDeleted.clear();

							//Gestion des ponctuations
							//******Si listSystacTokens/listCBTokens contient les ponctuations (en dur) on les enlève*****///
							//****On les mets dans un tableau et on parcours
							List<String> ponctuations = new ArrayList<String>();
							ponctuations.add(",");
							ponctuations.add(";");
							ponctuations.add(":");
							ponctuations.add(".");
							ponctuations.add("'");
							ponctuations.add("\"");
							ponctuations.add("<");
							ponctuations.add(">");
							ponctuations.add("(");
							ponctuations.add(")");
							ponctuations.add("[");
							ponctuations.add("]");
							ponctuations.add("-");
							ponctuations.add("_");
							ponctuations.add("|");
							ponctuations.add("/");
							ponctuations.add("\\");
							ponctuations.add("?");
							ponctuations.add("!");

							for(String s: ponctuations){
								if(listSystacTokens.contains(s.trim())){
									listSystacTokensDeleted.add(s);
								}
								if(listCBTokens.contains(s.trim())){
									listCBTokensDeleted.add(s);
								}
							}


							listSystacTokens.removeAll(listSystacTokensDeleted);
							listCBTokens.removeAll(listCBTokensDeleted);

							listSystacTokensDeleted.clear();
							listCBTokensDeleted.clear();


							benefSystac="";
							for(String token : listSystacTokens){
								if(benefSystac.isEmpty())
									benefSystac = token;
								else{
									benefSystac = benefSystac + " " + token;
								}
							}
							benefCB="";
							for(String token2 : listCBTokens){
								if(benefCB.isEmpty())
									benefCB = token2;
								else{
									benefCB = benefCB + " " + token2;
								}
							}

						}

						//Ensuite On applique l'algo de Levenshtein
						if(result!=0){ //y a pas eu correspondence d'égalité entre systac et CB ou systac et sigle
							//s'il y a correspondance de contenance entre benef systac et benef CB on traite dans Levenshtein


							/********On va trimé chaque token de chacune des chaines et re-concatener avec un espace entre chaque mots, pour éviter
							 * les incohérences comme MOUAFO STEPHANE et MOUAFO       STEPHANE*************/

							//On refait le split sur les nouvelles valeurs sans les mots clés
							String [] arraySystac = benefSystac.split("\\s+"); //on split sur l'espace
							String [] arrayCB = benefCB.split("\\s+"); //on split sur l'espace
							String tokenSystac="";
							for(String s: arraySystac ){
								if(tokenSystac.isEmpty())tokenSystac = s.trim();
								else
									tokenSystac = tokenSystac + s.trim();
							}
							String tokenCB="";
							for(String s: arrayCB ){
								if(tokenCB.isEmpty())tokenCB = s.trim();
								else
									tokenCB = tokenCB + s.trim();
							}


							/*******************************************************************************/

							double similitude = calculLevenshtein(tokenSystac.toUpperCase(), tokenCB.toUpperCase());

							//double similitude = calculLevenshtein(benefSystac.toUpperCase(), benefCB.toUpperCase());
							//if(similitude>=listParams.get(0).getSeuil()) alors on considère pas d'incohérences

							if(similitude<listParams.get(0).getSeuil()){
								
								if(isGestionAlgo2){
									//Si étape 3 pas OK, on applique méthode normal + Levenshtein entre les mots splittés

									//On refait le split sur les nouvelles valeurs sans les mots clés
									String [] resultArraySystac = benefSystac.split("\\s+"); //on split sur l'espace
									String [] resultArrayCB = benefCB.split("\\s+"); //on split sur l'espace


									boolean results = countBeneficiaireSubTokensWithLevenshteinAlgo(resultArrayCB, resultArraySystac);
									if(results==false){
										//Aucune incohérence
										System.out.println("***Aucune incohérence****");

									}else{

										///incoherenceDetectee(color, previousColor, t);
										System.out.println("***Incohérence détectée****");

										mapLots.put(t.getId(),t);

										Map<String, Traitement> mapLotsColeurs = new HashMap<>();

										if(mapLots.isEmpty()){
											//Aucun doublons;
											System.out.println("**MAP LOTS EMPTY***");

										}else{

											Incoherences incoherence = new Incoherences();

											for(Long k: mapLots.keySet()){

												if(!previousColor.equals("blue"))
													color = "blue";
												else
													color = "green";
												mapLotsColeurs.put(color, mapLots.get(k));

												Traitement tt = mapLots.get(k);

												System.out.println("----------------------------tt.getId()--------------------" + tt.getId());
												incoherence = new Incoherences(tt.getAgence(), tt.getNcp(), tt.getCle(), tt.getDevise(), tt.getNomBeneficiaire(), tt.getNomBeneficiaireAmplitude(), tt.getDrec(),tt.getDco(), tt.getDva(), tt.getOpe(), tt.getEve(), tt.getMontant(), tt.getLib(), tt.getEtat(), tt.getUti(), tt.getCodeEtabDonneurOrdre(), tt.getCodeGuicherDonneurOrdre(), tt.getNcpDonneurOrdre(), tt.getCleDonneurOrdre(), tt.getNomDonneurOrdre(), tt.getDateTraitement(),color,user, tt.getValide(), tt.getId());

												/**if(t.getId()!=null){
											t = ViewHelper.virementsRecManager.findByPrimaryKey(Traitement.class, t.getId(), null);
											incoherence.setTraitement(t);
										    }****/

												listIncoherences.add(incoherence);
												//t.setIncoherences(incoherence);

												System.out.println("****listIncoherences size****"+listIncoherences.size());

												previousColor = color;

											}

											mapLots.clear();
											elementsParcourus.clear();

										}

									}

								}else{
									//Si la gestion du deuxième algo n'est pas activée
									//incoherenceDetectee(color, previousColor, t);
									System.out.println("***Incohérence détectée****");

									mapLots.put(t.getId(),t);

									Map<String, Traitement> mapLotsColeurs = new HashMap<>();

									if(mapLots.isEmpty()){
										//Aucun doublons;
										System.out.println("**MAP LOTS EMPTY***");

									}else{

										Incoherences incoherence = new Incoherences();

										for(Long k: mapLots.keySet()){

											if(!previousColor.equals("blue"))
												color = "blue";
											else
												color = "green";
											mapLotsColeurs.put(color, mapLots.get(k));

											Traitement tt = mapLots.get(k);

											System.out.println("----------------------------tt.getId()--------------------" + tt.getId());
											incoherence = new Incoherences(tt.getAgence(), tt.getNcp(), tt.getCle(), tt.getDevise(), tt.getNomBeneficiaire(), tt.getNomBeneficiaireAmplitude(), tt.getDrec(),tt.getDco(), tt.getDva(), tt.getOpe(), tt.getEve(), tt.getMontant(), tt.getLib(), tt.getEtat(), tt.getUti(), tt.getCodeEtabDonneurOrdre(), tt.getCodeGuicherDonneurOrdre(), tt.getNcpDonneurOrdre(), tt.getCleDonneurOrdre(), tt.getNomDonneurOrdre(), tt.getDateTraitement(),color,user, tt.getValide(), tt.getId());

											/**if(t.getId()!=null){
										t = ViewHelper.virementsRecManager.findByPrimaryKey(Traitement.class, t.getId(), null);
										incoherence.setTraitement(t);
									    }*/

											listIncoherences.add(incoherence);
											//t.setIncoherences(incoherence);

											System.out.println("****listIncoherences size****"+listIncoherences.size());

											previousColor = color;

										}

										mapLots.clear();
										elementsParcourus.clear();

									}
								}
							}
						}

					}
					t.setTraiteIncoherences(Boolean.TRUE);
					System.out.println("*****t.getTraiteIncoherences()******" + t.getTraiteIncoherences());
				}

				if(listIncoherences!=null&&!listIncoherences.isEmpty()){

					System.out.println("***Incohérence****");

					for(Incoherences dd:listIncoherences){
						if(!new SimpleDateFormat("dd/MM/yyyy").format(dd.getDrec()).equals(new SimpleDateFormat("dd/MM/yyyy").format(new Date()))){
							//dd.setDisable(Boolean.TRUE);
						}else{

						}

						System.out.println("*****++++++++++++*****READY TO SET DISABLE TO TRUE*****");
						if(dd.getEtat()!=null && !dd.getEtat().isEmpty() && dd.getEtat().equals("AT")){
							dd.setDisable(Boolean.TRUE);
							//dd.setSelect(Boolean.TRUE); //Par défaut il est true
							System.out.println("*****++++++++++++*****DISABLE*****"+ dd.getDisable() + "****************+++++++++");
							System.out.println("*****++++++++++++*****SET DISABLE TO TRUE SUCCESSFULLY*****");
						}

						System.out.println("*****++++++++++++*****DISABLE*****"+ dd.getDisable());
					}

					/**Mettre opposition sur incohérences (useless)*/
					String mettreOpposition =  ""; ///putOpposition(listIncoherences);

					// Ouverture du Panneau de Validation des incohérences 
					incoherencesDialog = new IncoherencesDialog(DialogFormMode.UPDATE, new Incoherences(), listParams, listIncoherences, listTraitement, nombreVirementTotal, this, dateDebut, dateFin, isInBkeveOrBkheve, mettreOpposition, user);

					// Ouverture
					incoherencesDialog.open();
					//incoherencesDialog.open=false;
					//listIncoherences.clear();	

				}else{

					System.out.println("***Aucune incohérence donc on sort****");

					for(Traitement t: listTraitement){
						ViewHelper.virementsRecManager.updateTraitement(t);	
					}

					System.out.println("Il n'y a pas eu d'incohérences dans les noms des bénéficiaires pour la période spécifiée");
					this.information = true;
					ExceptionHelper.showInformation("Il n'y a pas eu d'incohérences dans les noms des bénéficiaires pour la période spécifiée", this);
				}
				//listTraitement.clear();
			}

			/**On parcours la liste des virements en vue de détecter des incohérences
			if(listTraitement!=null&&!listTraitement.isEmpty()){

				listIncoherences.clear();

				String benefSystac = "";
				String benefCB = "";
				String color="";
				String previousColor="";

				//On parcours la liste des virements

				for(Traitement t: listTraitement){

					benefSystac = t.getNomBeneficiaire();

					System.out.println("*****t.getNomBeneficiaire()******"+ t.getNomBeneficiaire());

					benefCB = t.getNomBeneficiaireAmplitude();

					System.out.println("*****t.getNomBeneficiaireAmplitude()******"+ t.getNomBeneficiaireAmplitude());

					if(benefCB!=null&&!benefCB.isEmpty()){

						/**Algo comparaison pour trouver incohérences*
						int benefSystacLength = benefSystac.length();
						System.out.println("*****benefSystacLength******"+ benefSystacLength);

						int benefCBLength = benefCB.length();
						System.out.println("*****benefCBLength******"+ benefCBLength);

						String benefS [] = benefSystac.split("\\s+");
						String benefC [] = benefCB.split("\\s+");

						if(!benefSystac.equalsIgnoreCase(benefCB)){

							boolean result = countBeneficiaireSubTokens(benefC, benefS);
							if(result==false){
								//Aucune incohérence
								System.out.println("***Aucune incohérence****");

							}else{

								System.out.println("***Incohérence détectée****");

								mapLots.put(t.getId(),t);

								Map<String, Traitement> mapLotsColeurs = new HashMap<>();

								if(mapLots.isEmpty()){
									//Aucun doublons;
									System.out.println("**MAP LOTS EMPTY***");

								}else{

									Incoherences incoherence = new Incoherences();

									for(Long k: mapLots.keySet()){

										if(!previousColor.equals("blue"))
											color = "blue";
										else
											color = "green";
										mapLotsColeurs.put(color, mapLots.get(k));

										Traitement tt = mapLots.get(k);

										System.out.println("----------------------------tt.getId()--------------------" + tt.getId());
										incoherence = new Incoherences(tt.getAgence(), tt.getNcp(), tt.getCle(), tt.getDevise(), tt.getNomBeneficiaire(), tt.getNomBeneficiaireAmplitude(), tt.getDrec(),tt.getDco(), tt.getDva(), tt.getOpe(), tt.getEve(), tt.getMontant(), tt.getLib(), tt.getEtat(), tt.getUti(), tt.getCodeEtabDonneurOrdre(), tt.getCodeGuicherDonneurOrdre(), tt.getNcpDonneurOrdre(), tt.getCleDonneurOrdre(), tt.getNomDonneurOrdre(), tt.getDateTraitement(),color,user, tt.getValide(), tt.getId());



										listIncoherences.add(incoherence);
										//t.setIncoherences(incoherence);

										System.out.println("****listIncoherences size****"+listIncoherences.size());

										previousColor = color;

									}

									mapLots.clear();
									elementsParcourus.clear();

								}

							}

						}
					}
					t.setTraiteIncoherences(Boolean.TRUE);
					System.out.println("*****t.getTraiteIncoherences()******" + t.getTraiteIncoherences());
				}


				if(listIncoherences!=null&&!listIncoherences.isEmpty()){

					System.out.println("***Incohérence****");

					for(Incoherences dd:listIncoherences){
						if(!new SimpleDateFormat("dd/MM/yyyy").format(dd.getDrec()).equals(new SimpleDateFormat("dd/MM/yyyy").format(new Date()))){
							//dd.setDisable(Boolean.TRUE);
						}else{

						}

						System.out.println("*****++++++++++++*****READY TO SET DISABLE TO TRUE*****");
						if(dd.getEtat()!=null && !dd.getEtat().isEmpty() && dd.getEtat().equals("AT")){
							dd.setDisable(Boolean.TRUE);
							//dd.setSelect(Boolean.TRUE); //Par défaut il est true
							System.out.println("*****++++++++++++*****DISABLE*****"+ dd.getDisable() + "****************+++++++++");
							System.out.println("*****++++++++++++*****SET DISABLE TO TRUE SUCCESSFULLY*****");
						}

						System.out.println("*****++++++++++++*****DISABLE*****"+ dd.getDisable());
					}

					/**Mettre opposition sur incohérences (useless)*
					String mettreOpposition =  ""; ///putOpposition(listIncoherences);

					// Ouverture du Panneau de Validation des incohérences 
					incoherencesDialog = new IncoherencesDialog(DialogFormMode.UPDATE, new Incoherences(), listIncoherences, listTraitement, nombreVirementTotal, this, dateDebut, dateFin, isInBkeveOrBkheve, mettreOpposition);

					// Ouverture
					incoherencesDialog.open();
					//incoherencesDialog.open=false;
					//listIncoherences.clear();	

				}else{

					System.out.println("***Aucune incohérence donc on sort****");

					for(Traitement t: listTraitement){
						ViewHelper.virementsRecManager.updateTraitement(t);	
					}

					System.out.println("Il n'y a pas eu d'incohérences dans les noms des bénéficiaires pour la période spécifiée");
					this.information = true;
					ExceptionHelper.showInformation("Il n'y a pas eu d'incohérences dans les noms des bénéficiaires pour la période spécifiée", this);
				}
				//listTraitement.clear();
			}*/

		} catch (Exception e) {
			e.printStackTrace();
			// Etat d'erreur
			this.error = true;
			// Traitement de l'exception
			ExceptionHelper.threatException(e);
		}
	}
	
	
	public void incoherenceDetectee(String color, String previousColor, Traitement t){
		System.out.println("***Incohérence détectée****");

		mapLots.put(t.getId(),t);

		Map<String, Traitement> mapLotsColeurs = new HashMap<>();

		if(mapLots.isEmpty()){
			//Aucun doublons;
			System.out.println("**MAP LOTS EMPTY***");

		}else{

			Incoherences incoherence = new Incoherences();

			for(Long k: mapLots.keySet()){

				if(!previousColor.equals("blue"))
					color = "blue";
				else
					color = "green";
				mapLotsColeurs.put(color, mapLots.get(k));

				Traitement tt = mapLots.get(k);

				System.out.println("----------------------------tt.getId()--------------------" + tt.getId());
				incoherence = new Incoherences(tt.getAgence(), tt.getNcp(), tt.getCle(), tt.getDevise(), tt.getNomBeneficiaire(), tt.getNomBeneficiaireAmplitude(), tt.getDrec(),tt.getDco(), tt.getDva(), tt.getOpe(), tt.getEve(), tt.getMontant(), tt.getLib(), tt.getEtat(), tt.getUti(), tt.getCodeEtabDonneurOrdre(), tt.getCodeGuicherDonneurOrdre(), tt.getNcpDonneurOrdre(), tt.getCleDonneurOrdre(), tt.getNomDonneurOrdre(), tt.getDateTraitement(),color,user, tt.getValide(), tt.getId());

				/**if(t.getId()!=null){
			t = ViewHelper.virementsRecManager.findByPrimaryKey(Traitement.class, t.getId(), null);
			incoherence.setTraitement(t);
		    }*/

				listIncoherences.add(incoherence);
				//t.setIncoherences(incoherence);

				System.out.println("****listIncoherences size****"+listIncoherences.size());

				previousColor = color;

			}

			mapLots.clear();
			elementsParcourus.clear();

		}

	}



//	public String putOpposition(List<Incoherences> listIncoherences){
//
//		String okay = ViewHelper.virementsRecManager.setIncoherencesEnAttente(listIncoherences, null,isInBkeveOrBkheve,user);
//
//		return okay;
//	}


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
