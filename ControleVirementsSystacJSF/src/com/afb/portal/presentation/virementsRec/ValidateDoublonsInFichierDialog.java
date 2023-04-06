package com.afb.portal.presentation.virementsRec;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang3.time.DateFormatUtils;
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
import com.afb.virementsRec.jpa.datamodel.ParamCompensateurCentrale;
import com.afb.virementsRec.jpa.datamodel.Parametrages;
import com.afb.virementsRec.jpa.datamodel.ParametragesGenTeleCompense;
import com.afb.virementsRec.jpa.datamodel.SortTraitement;
import com.afb.virementsRec.jpa.datamodel.TourCompensation;
import com.afb.virementsRec.jpa.datamodel.Traitement;
import com.afb.virementsRec.jpa.datamodel.TraitementTourCompensation;
import com.afb.virementsRec.jpa.datamodel.TypeProcess;
import com.afb.virementsRec.jpa.datamodel.TypeTraitement;
import com.afb.virementsRec.jpa.datamodel.ValidateDoublonsInFichier;
import com.yashiro.persistence.utils.dao.tools.OrderContainer;
import com.yashiro.persistence.utils.dao.tools.RestrictionsContainer;



public class ValidateDoublonsInFichierDialog extends AbstractDialog {

	private ValidateDoublonsInFichier currentObject = new ValidateDoublonsInFichier();

	private int count;

	private int countDoub;

	private int totalDoublons;

	private List<ValidateDoublonsInFichier> listDoublons = new ArrayList<ValidateDoublonsInFichier>();

	private List<ValidateDoublonsInFichier> listDoublonsAValider = new ArrayList<ValidateDoublonsInFichier>();

	private List<ValidateDoublonsInFichier> listDoublonsANePasValider = new ArrayList<ValidateDoublonsInFichier>();

	private ParametragesGenTeleCompense parametragesGenTeleCompense;

	private List<Traitement>listTraitement = new ArrayList<Traitement>();

	private Selection selection;

	private ReportViewer reportViewer;

	private Boolean check = Boolean.FALSE;

	private ValidateDoublonsInFichier doublonObject = new ValidateDoublonsInFichier();

	private Date dateDebut;

	private Date dateFin;

	private boolean isInBkeveOrBkheve;

	private TypeProcess typeProcess;

	private TypeTraitement typeTraitement;

	private Integer tour;

	int nbrFichCopie=0;

	private List<SelectItem> typeProcessItems = new ArrayList<SelectItem>();

	private List<SelectItem> typeTraitementItems = new ArrayList<SelectItem>();

	private boolean disable = false;


	/**
	 * Constructeur par défaut
	 */
	public ValidateDoublonsInFichierDialog() {
		super();
	}

	/**
	 * Constructeur du Modele
	 * @param mode	Mode d'ouverture de la Boite
	 * @param currentObject	Objet Courant de la Boite
	 * @param parent	Composant Parent
	 */
	public ValidateDoublonsInFichierDialog(DialogFormMode mode, ValidateDoublonsInFichier currentObject,List<ValidateDoublonsInFichier>doublons, List<Traitement>traitements, int count, IViewComponent parent, Date dateDebut, Date dateFin, boolean isInBkeveOrBkheve) {

		// Appel Parent
		super();

		// Initialisation des Parametres
		this.mode = mode;
		this.currentObject = currentObject;
		//this.param = param;
		System.out.println("***doublons.size()****" +doublons.size());
		this.listDoublons.addAll(doublons);
		System.out.println("*******traitements.size()******" +traitements.size());
		this.listTraitement.addAll(traitements);
		this.count = count;
		this.dateDebut=dateDebut;
		this.dateFin=dateFin;
		this.parent = parent;
		this.isInBkeveOrBkheve = isInBkeveOrBkheve;

		// Initialisation des Composants
		initComponents();
		// this.open = true;

	}

	public void openValidateDoublonsInFichierDialog(){

		try {
			//this.mode = DialogFormMode.UPDATE;
			initComponents();
			this.open();
		}catch (Exception e) {

			// Etat d'erreur
			this.error = true;
			// Traitement de l'exception
			ExceptionHelper.threatException(e);
		}
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Validation de l'intégration des Doublons dans les fichiers";
	}

	@Override
	public String getFileDefinition() {
		// TODO Auto-generated method stub
		return "/views/virementsRec/ValidateDoublonsInFichierDialog.xhtml";
	}

	@SuppressWarnings("unchecked")
	@Override
	public ValidateDoublonsInFichier getCurrentObject() {
		// TODO Auto-generated method stub
		return this.currentObject;
	}

	@Override
	public boolean checkBuildedCurrentObject() {

		try{



		}
		catch(Exception e){

			e.printStackTrace();
		}

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

	public ReportViewer getReportViewer() {
		return reportViewer;
	}

	public void setReportViewer(ReportViewer reportViewer) {
		this.reportViewer = reportViewer;
	}


	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<ValidateDoublonsInFichier> getListDoublons() {
		return listDoublons;
	}

	public void setListDoublons(List<ValidateDoublonsInFichier> listDoublons) {
		this.listDoublons = listDoublons;
	}

	public List<ValidateDoublonsInFichier> getListDoublonsAValider() {
		return listDoublonsAValider;
	}

	public void setListDoublonsAValider(List<ValidateDoublonsInFichier> listDoublonsAValider) {
		this.listDoublonsAValider = listDoublonsAValider;
	}

	public List<ValidateDoublonsInFichier> getListDoublonsANePasValider() {
		return listDoublonsANePasValider;
	}

	public void setListDoublonsANePasValider(
			List<ValidateDoublonsInFichier> listDoublonsANePasValider) {
		this.listDoublonsANePasValider = listDoublonsANePasValider;
	}

	public List<Traitement> getListTraitement() {
		return listTraitement;
	}

	public void setListTraitement(List<Traitement> listTraitement) {
		this.listTraitement = listTraitement;
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

	public boolean isInBkeveOrBkheve() {
		return isInBkeveOrBkheve;
	}

	public void setInBkeveOrBkheve(boolean isInBkeveOrBkheve) {
		this.isInBkeveOrBkheve = isInBkeveOrBkheve;
	}

	public void setCurrentObject(ValidateDoublonsInFichier currentObject) {
		this.currentObject = currentObject;
	}

	public Selection getSelection() {
		return selection;
	}

	public void setSelection(Selection selection) {
		this.selection = selection;
	}

	public int getTotalDoublons() {
		return totalDoublons;
	}

	public void setTotalDoublons(int totalDoublons) {
		this.totalDoublons = totalDoublons;
	}

	public Boolean getCheck() {
		return check;
	}

	public void setCheck(Boolean check) {
		this.check = check;
	}

	public ValidateDoublonsInFichier getDoublonObject() {
		return doublonObject;
	}

	public void setDoublonObject(ValidateDoublonsInFichier doublonObject) {
		this.doublonObject = doublonObject;
	}

	public ParametragesGenTeleCompense getParametragesGenTeleCompense() {
		return parametragesGenTeleCompense;
	}

	public void setParametragesGenTeleCompense(
			ParametragesGenTeleCompense parametragesGenTeleCompense) {
		this.parametragesGenTeleCompense = parametragesGenTeleCompense;
	}

	public TypeProcess getTypeProcess() {
		return typeProcess;
	}

	public void setTypeProcess(TypeProcess typeProcess) {
		this.typeProcess = typeProcess;
	}

	public TypeTraitement getTypeTraitement() {
		return typeTraitement;
	}

	public void setTypeTraitement(TypeTraitement typeTraitement) {
		this.typeTraitement = typeTraitement;
	}

	public List<SelectItem> getTypeTraitementItems() {
		return typeTraitementItems;
	}

	public void setTypeTraitementItems(List<SelectItem> typeTraitementItems) {
		this.typeTraitementItems = typeTraitementItems;
	}

	public Integer getTour() {
		return tour;
	}

	public void setTour(Integer tour) {
		this.tour = tour;
	}

	public int getNbrFichCopie() {
		return nbrFichCopie;
	}

	public void setNbrFichCopie(int nbrFichCopie) {
		this.nbrFichCopie = nbrFichCopie;
	}

	public List<SelectItem> getTypeProcessItems() {
		return typeProcessItems;
	}

	public void setTypeProcessItems(List<SelectItem> typeProcessItems) {
		this.typeProcessItems = typeProcessItems;
	}

	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}

	@Override
	public void initComponents() {

		try{

			dateDebut = new SimpleDateFormat("dd/MM/yyyy").parse(DateFormatUtils.format(Calendar.getInstance().getTime(), "dd/MM/yyyy"));  //new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());

			typeProcessItems.add(new SelectItem(null, "---Choisir---"));
			typeProcessItems.add(new SelectItem(TypeProcess.FICHIER_COMPTA_ALLER, TypeProcess.FICHIER_COMPTA_ALLER.value()));
			typeProcessItems.add(new SelectItem(TypeProcess.FICHIER_COMPTA_RETOUR, TypeProcess.FICHIER_COMPTA_RETOUR.value()));

			/*for (TypeProcess val : TypeProcess.types()){
				typeProcessItems.add(new SelectItem(val.name(), val.getValue()));
			}*/

			/*typeTraitementItems.add(new SelectItem(null, "---Choisir---"));
			for (TypeTraitement val : TypeTraitement.types()){
				typeTraitementItems.add(new SelectItem(val.name(), val.getValue()));
			}*/


			List<ParametragesGenTeleCompense> listParams = ViewHelper.virementsRecManager.filterParamGen();
			if(listParams!=null && !listParams.isEmpty()){

				//currentObject = listParams.get(0);
				parametragesGenTeleCompense = listParams.get(0);
			}


		}
		catch(Exception e){

			this.error = true;
			e.printStackTrace();
			ExceptionHelper.threatException(e);
		}
	}



	public void processRecherche(){

		List<ParamCompensateurCentrale> paramCompensateurCentrales = ViewHelper.virementsRecManagerDAOLocal.filter(ParamCompensateurCentrale.class, null, null, OrderContainer.getInstance().add(Order.desc("id")), null, 0, -1);

		if(paramCompensateurCentrales!=null&& !paramCompensateurCentrales.isEmpty()){

			tour = paramCompensateurCentrales.get(0).getTourActuel();

		}else{

			this.error = true;
			ExceptionHelper.showError("Le paramétrage de la compensation n'est pas effectuée !!!  Contactez le compensateur centrale !!!", this);
			return;
		}

		if(typeProcess==null){
			this.error=true;
			this.information=true;
			ExceptionHelper.showError("Veuillez choisir le type de Process !!!", this);
			return;
		}
		listDoublons = ViewHelper.virementsRecManager.filterDoublons(dateDebut, dateFin, typeProcess);

		for(ValidateDoublonsInFichier dd: listDoublons){
			if(dd.getValide().equals(Boolean.TRUE)){
				dd.setDisable(Boolean.TRUE);
				dd.setSelect(Boolean.TRUE);
			}
			else
				count++;
		}


		int cnt = 0;
		//Récupération des doublons à disable
		for(ValidateDoublonsInFichier c : listDoublons){

			if(c.getValide().equals(Boolean.TRUE) && c.getDisable().equals(Boolean.TRUE)){

				cnt++;
			}

		}

		if(cnt == listDoublons.size()){

			disable = true;

		}
	}

	public void processPrint(){

		try{


			if(listDoublons == null || listDoublons.isEmpty()) throw new Exception("Aucun Doublon selectionné dans la Liste!");


			HashMap<String, Object> param = new HashMap<String, Object>();


			param.put("uti", ViewHelper.getSessionUser().getName());
			param.put("periode", new SimpleDateFormat("dd/MM/yyyy").format(dateDebut));  //+ "  -  " + new SimpleDateFormat("dd/MM/yyyy").format(dateFin));
			param.put("nombreVir", countDoub);
			param.put("total", count);
			int nbrDoublonsVal = 0;
			int nbrDoublonsIgn = 0;
			for(ValidateDoublonsInFichier doub: listDoublons){
				if(doub.getDisable().equals(Boolean.FALSE)){
					if(doub.getValide().equals(Boolean.TRUE)){ //doublons validés
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
			reportViewer = new ReportViewer(listDoublons,"rapportDoublons.jasper", param, "afb.statistique.title", this,"/views/repport/ReportStatDoublons.xhtml");
			reportViewer.viewReport();
			System.out.println("****After Report Viewer****");


			return;


			/**Affichage du rapport de traitement 
			List<ValidateDoublonsInFichier> repports = new ArrayList<ValidateDoublonsInFichier>();

			ValidateDoublonsInFichier subReportDoublonsDansFichier = new ValidateDoublonsInFichier();
			subReportDoublonsDansFichier.setSubReportDoublonsDansFichiers(listDoublons);
			//subReportDoublonsDansFichier.setFichiersSupprimeACauseDeValeursEnDoublonsDansAutreFichiers(fList);

			System.out.println("*****repports.size()******" + repports.size());

			//param.put("items", statistiques);
			System.out.println("****Before Report Viewer****");***
			reportViewer = new ReportViewer(repports,"transmissions.jasper", param, "afb.statistique.title", this,"/views/repport/ReportValidateDoublons.xhtml");
			reportViewer.viewReport();
			System.out.println("****After Report Viewer****");


			return;*////



		}catch(Exception e){

			e.printStackTrace();
			// Etat d'erreur
			this.error = true;
			// Traitement de l'exception
			ExceptionHelper.threatException(e);

		}
	}

	@Override
	protected void buildCurrentObject() throws ParseException {

		if(mode.equals(DialogFormMode.CREATE)){


		}

		else if(mode.equals(DialogFormMode.UPDATE)){
		}
	}




	@Override
	protected void validate() {


	}

	public boolean copieFichiersDeDoublonsValides(List<ValidateDoublonsInFichier> listDoublonsAValider){

		/**List<TourCompensation> listTourCompense = ViewHelper.virementsRecManager.filterTourCompensation(new Date(), ViewHelper.getSessionUser().getLogin(), tour, typeProcess);
		if(listTourCompense==null || listTourCompense.isEmpty()){

			System.out.println("****Ce tour de compensation " + tour + " n'a pas encore été effectué pour ce type de process: " + typeProcess + ". Veuillez lancer d'abord le traitement normale!!!");

			this.error=true;
			this.information = true;
			ExceptionHelper.showError("Ce tour de compensation " + tour + " n'a pas encore été effectué pour ce type de process: " + typeProcess + "Veuillez lancer d'abord le traitement normale!!!" , this);

			return false;
		}*/


		TourCompensation tourCompensation = new TourCompensation();
		List<TourCompensation>toursCompensation =   ViewHelper.virementsRecManagerDAOLocal.filter(TourCompensation.class, null, RestrictionsContainer.getInstance().add(Restrictions.eq("dateTraitement", new Date())), OrderContainer.getInstance().add(Order.desc("id")), null, 0, -1);        //ViewHelper.virementsRecManager.filterTourCompensation(new Date(), null, tour, typeProcess);
		if(toursCompensation!=null &&!toursCompensation.isEmpty()){
			tourCompensation = toursCompensation.get(0);
		}


		Boolean bool = false;
		String repEntree;
		String repDestination;

		File folderEntree; 
		File folderDest;

		if(typeProcess.equals(TypeProcess.FICHIER_COMPTA_ALLER)){

			repEntree = parametragesGenTeleCompense.getRepertoireEntreeFichierComptabilisationAller();
			repDestination = parametragesGenTeleCompense.getRepertoireDestinationFichierComptabilisationAller();

			folderEntree = new File(repEntree);
			folderDest = new File(repDestination);


			File[] listOfFiles = folderEntree.listFiles();
			if(listOfFiles==null || listOfFiles.length==0){

				System.out.println("Aucun fichier trouvé dans le répertoire d'entrée !!!");
				this.error=true;
				this.information=true;
				ExceptionHelper.showError("Aucun fichier trouvé dans le répertoire d'entrée !!!", this);

			}else{


				for(ValidateDoublonsInFichier v: listDoublonsAValider){

					File file = new File(v.getFichier());

					String fichierEntree = repEntree + File.separator + v.getFichier();

					String fichierDest = repDestination + File.separator + v.getFichier();


					Path pathEntree = Paths.get(fichierEntree);

					Path pathDestination = Paths.get(fichierDest);

					Path p = null;
					try {
						p = Files.move(pathEntree, pathDestination, StandardCopyOption.REPLACE_EXISTING);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(p!=null){
						System.out.println("***Files copied to ***" + p.toString());
						nbrFichCopie++;
					}


					TraitementTourCompensation traitementTourCompense = new TraitementTourCompensation();
					traitementTourCompense.setUtiTraitement(ViewHelper.getSessionUser().getLogin());
					traitementTourCompense.setTypeTraitement(TypeTraitement.VALIDATION_MANUELLE);
					traitementTourCompense.setDateTraitement(new Date());
					traitementTourCompense.setHeure(new SimpleDateFormat("HH:mm:ss").format(new Date()));
					traitementTourCompense.setFichiersTraite(v.getFichier());

					//if(tourCompensation.getNumeroTour()!=null)
					traitementTourCompense.setTourCompensation(null);


					traitementTourCompense.setSortTraitement(SortTraitement.SUCCES);
					ViewHelper.virementsRecManagerDAOLocal.save(traitementTourCompense);
					bool = true;

				}
			}

		}else if(typeProcess.equals(TypeProcess.FICHIER_COMPTA_RETOUR)){

			repEntree = parametragesGenTeleCompense.getRepertoireEntreeFichierComptabilisationRetour();
			repDestination = parametragesGenTeleCompense.getRepertoireDestinationFichierComptabilisationRetour();

			folderEntree = new File(repEntree);
			folderDest = new File(repDestination);

			File[] listOfFiles = folderEntree.listFiles();
			if(listOfFiles==null || listOfFiles.length==0){

				System.out.println("Aucun fichier trouvé dans le répertoire d'entrée !!!");
				this.error=true;
				this.information=true;
				ExceptionHelper.showError("Aucun fichier trouvé dans le répertoire d'entrée !!!", this);

			}else{

				for(ValidateDoublonsInFichier v: listDoublonsAValider){

					File file = new File(v.getFichier());

					String fichierEntree = repEntree + File.separator + v.getFichier();

					String fichierDest = repDestination + File.separator + v.getFichier();


					Path pathEntree = Paths.get(fichierEntree);

					Path pathDestination = Paths.get(fichierDest);

					Path p=null;
					try {
						p = Files.move(pathEntree, pathDestination, StandardCopyOption.REPLACE_EXISTING);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(p!=null){
						System.out.println("***Files copied to ***" + p.toString());
						nbrFichCopie++;
					}

					TraitementTourCompensation traitementTourCompense = new TraitementTourCompensation();
					traitementTourCompense.setUtiTraitement(ViewHelper.getSessionUser().getLogin());
					traitementTourCompense.setTypeTraitement(TypeTraitement.VALIDATION_MANUELLE);
					traitementTourCompense.setDateTraitement(new Date());
					traitementTourCompense.setHeure(new SimpleDateFormat("HH:mm:ss").format(new Date()));
					traitementTourCompense.setFichiersTraite(v.getFichier());

					if(tourCompensation.getNumeroTour()!=null)
						traitementTourCompense.setTourCompensation(tourCompensation);

					traitementTourCompense.setSortTraitement(SortTraitement.SUCCES);
					ViewHelper.virementsRecManagerDAOLocal.save(traitementTourCompense);
					bool = true;

				}
			}

		}


		return bool;
	}


	public void save(){

		//Récupération des doublons à valider
		for(ValidateDoublonsInFichier c : listDoublons){
			//c.setSelect(check);
			if(new SimpleDateFormat("dd/MM/yyyy").format(c.getDateTraitement()).equals(new SimpleDateFormat("dd/MM/yyyy").format(new Date()))){
				if(c.getDisable().equals(Boolean.FALSE)){
					if(c.getSelect().equals(Boolean.TRUE)){
						c.setValide(Boolean.TRUE);
						c.setUti(ViewHelper.getSessionUser().getLogin());
						listDoublonsAValider.add(c);
						countDoub++;
					}
					else{
						c.setValide(Boolean.FALSE);
						listDoublonsANePasValider.add(c);
					}
				}
			}
		}



		//Boolean okay = ViewHelper.virementsRecManager.setDoublonsEnAttente(listDoublonsAValider,listDoublonsANePasValider,isInBkeveOrBkheve);

		Boolean okay = copieFichiersDeDoublonsValides(listDoublonsAValider); //copie des fichiers de doublons validés


		System.out.println("***okay**"+ okay);

		if(okay){

			for(ValidateDoublonsInFichier c : listDoublons){

				ViewHelper.virementsRecManagerDAOLocal.update(c);
			}

			processPrint();

			this.information=true;
			ExceptionHelper.showInformation(nbrFichCopie + " copiés dans le répertoire de destination !!!",this);
			return;

		}else{

			this.error=true;
			this.information=true;
			ExceptionHelper.showError(" Impossible d'intégrer les fichiers.  Contactez l'administrateur de l'application !!!",this);
			return;
		}

		//if(okay.equals(Boolean.TRUE)){
		/*******Affichage du rapport de contrôle de doublons*****/
		/***HashMap<String, Object> parameter = new HashMap<String, Object>();


		parameter.put("uti", ViewHelper.getSessionUser().getName());
		parameter.put("periode", new SimpleDateFormat("dd/MM/yyyy").format(dateDebut) + "-" + new SimpleDateFormat("dd/MM/yyyy").format(dateFin));

		//parameter.put("nombreVir", listTraitement.size());
		//parameter.put("total", listDoublons.size());

		int nbrDoublonsVal = 0;
		int nbrDoublonsIgn = 0;
		for(ValidateDoublonsInFichier doub: listDoublons){
			if(doub.getValide().equals(Boolean.TRUE)){ //doublons validés
				nbrDoublonsVal++;
				//doub.setEtat("AT");
			}else{
				//doub.setEtat("VA");
				nbrDoublonsIgn++;
			}
		}
		parameter.put("nbrDoublonsVal", nbrDoublonsVal);
		parameter.put("nbrDoublonsIgn", nbrDoublonsIgn);

		reportViewer = new ReportViewer(listDoublons,"rapportDoublons.jasper", parameter, "afb.statistique.title", this,"/views/repport/ReportDoublons.xhtml");
		reportViewer.viewReport();*/

	}

	@Override
	protected void disposeResourceOnClose() {
		typeProcessItems.clear();
		typeTraitementItems.clear();
		listDoublons.clear();
		count=0;
		if(!listDoublonsAValider.isEmpty())listDoublonsAValider.clear();
		if(!listDoublonsANePasValider.isEmpty())listDoublonsANePasValider.clear();
		nbrFichCopie=0;
	}

	public void processCheckAll(ActionEvent event){

		// if(Boolean.FALSE.equals(check)) check = Boolean.TRUE;
		// else check = Boolean.FALSE;
		int count = 0;
		for(ValidateDoublonsInFichier c : listDoublons){
			if(new SimpleDateFormat("dd/MM/yyyy").format(c.getDateTraitement()).equals(new SimpleDateFormat("dd/MM/yyyy").format(new Date()))){
				c.setSelect(check);
				if(check.equals(Boolean.TRUE)){
					if(count==0)
						totalDoublons=0;
					totalDoublons+=1;
					count++;
				}
				else{
					if(totalDoublons!=0)
						totalDoublons-=1;
				}
			}else{

			}
		}

	}

}


/****
 * 
 * 	<td><h:outputText value="Type de Traitement"
								styleClass="labelStyle" /></td>
						<td><h:selectOneMenu
								value="#{validateDoublonsInFichierDialog.typeTraitement}"
								styleClass="text ui-widget-content ui-corner-all">
								<f:selectItems
									value="#{validateDoublonsInFichierDialog.typeTraitementItems}" />
							</h:selectOneMenu></td>

 * 
 */
