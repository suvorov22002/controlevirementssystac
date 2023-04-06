package com.afb.portal.presentation.virementsRec;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.mail.MessagingException;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.richfaces.model.selection.Selection;

import com.afb.dpd.smsbanking.jpa.tools.CurrencyCode;
import com.afb.dsi.alertes.AfrilandSendMail;
import com.afb.portal.presentation.models.AbstractDialog;
import com.afb.portal.presentation.models.DialogFormMode;
import com.afb.portal.presentation.tools.ExceptionHelper;
import com.afb.portal.presentation.tools.ViewHelper;
import com.afb.virementsRec.jpa.datamodel.OuvertureJournee;
import com.afb.virementsRec.jpa.datamodel.ParamCompensateurCentrale;
import com.afb.virementsRec.jpa.datamodel.ParamEmail;
import com.afb.virementsRec.jpa.datamodel.ParamEmailAuto;
import com.afb.virementsRec.jpa.datamodel.ParametragesCaracteresSpeciaux;
import com.afb.virementsRec.jpa.datamodel.ParametragesGenTeleCompense;
import com.afb.virementsRec.jpa.datamodel.SendMail;
import com.afb.virementsRec.jpa.datamodel.StatutJournee;
import com.afb.virementsRec.jpa.datamodel.TourCompensation;
import com.afb.virementsRec.jpa.datamodel.ToursAller;
import com.afb.virementsRec.jpa.datamodel.ToursRetour;
import com.afb.virementsRec.jpa.datamodel.TypePhase;
import com.afb.virementsRec.jpa.datamodel.TypeProcess;
import com.afb.virementsRec.jpa.datamodel.TypeTraitement;
import com.yashiro.persistence.utils.dao.tools.OrderContainer;
import com.yashiro.persistence.utils.dao.tools.RestrictionsContainer;
import com.yashiro.persistence.utils.dao.tools.encrypter.Encrypter;


public class ParamCompensateurCentrauxDialog extends AbstractDialog {


	private ParamCompensateurCentrale currentObject = new ParamCompensateurCentrale();

	private TourCompensation currentObject2 = new TourCompensation();

	//private ToursRetour currentObject3 = new ToursRetour();

	ParamCompensateurCentrale dbObject;

	private List<ParamCompensateurCentrale> params = new ArrayList<ParamCompensateurCentrale>();

	private Date dateJournee;

	private Integer tourMax;

	private Integer tourActuel;

	private StatutJournee statutJournee;

	private List<SelectItem> statutJourneeItems = new ArrayList<SelectItem>();

	private ParametragesGenTeleCompense parametragesGenTeleCompense;

	private String heureTour;

	private TypePhase typePhase;

	private List<SelectItem> typePhaseItems = new ArrayList<SelectItem>();

	//private String heureToursRetour;

	private List<TourCompensation> listTours = new ArrayList<TourCompensation>();
	private List<TourCompensation> listToursToRemove = new ArrayList<TourCompensation>();

	/*private List<ToursRetour> listToursRetour = new ArrayList<ToursRetour>();
	private List<ToursRetour> listToursRetourToRemove = new ArrayList<ToursRetour>();*/

	private Selection selection;

	private TourCompensation deleUser = new TourCompensation();

	//private ToursRetour deleUser2 = new ToursRetour();



	public ParamCompensateurCentrauxDialog() {

		//this.mode = DialogFormMode.UPDATE;
		//initComponents();
	}

	public void openParamCompensateurCentrauxDialog(){

		try {

			System.out.println("----opening param dialog-----");

			//initializeComponents();

			initComponents();

			// Ouverture
			this.open();

		} catch (Exception e) {

			// Etat d'erreur
			this.error = true;
			// Traitement de l'exception
			ExceptionHelper.threatException(e);
		}
	}



	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Parametrages des Compensateurs Centraux";
	}

	@Override
	public String getFileDefinition() {
		// TODO Auto-generated method stub
		return "/views/virementsRec/ParamCompensateurCentrauxDialog.xhtml";
	}

	@SuppressWarnings("unchecked")
	@Override
	public ParamCompensateurCentrale getCurrentObject() {
		// TODO Auto-generated method stub
		return this.currentObject;
	}


	public List<ParamCompensateurCentrale> getParams() {
		return params;
	}

	public void setParams(List<ParamCompensateurCentrale> params) {
		this.params = params;
	}

	public Date getDateJournee() {
		return dateJournee;
	}

	public void setDateJournee(Date dateJournee) {
		this.dateJournee = dateJournee;
	}

	public Integer getTourMax() {
		return tourMax;
	}

	public void setTourMax(Integer tourMax) {
		this.tourMax = tourMax;
	}

	public StatutJournee getStatutJournee() {
		return statutJournee;
	}

	public void setStatutJournee(StatutJournee statutJournee) {
		this.statutJournee = statutJournee;
	}

	public void setCurrentObject(ParamCompensateurCentrale currentObject) {
		this.currentObject = currentObject;
	}

	public List<SelectItem> getStatutJourneeItems() {
		return statutJourneeItems;
	}

	public void setStatutJourneeItems(List<SelectItem> statutJourneeItems) {
		this.statutJourneeItems = statutJourneeItems;
	}

	public ParamCompensateurCentrale getDbObject() {
		return dbObject;
	}

	public void setDbObject(ParamCompensateurCentrale dbObject) {
		this.dbObject = dbObject;
	}

	public ParametragesGenTeleCompense getParametragesGenTeleCompense() {
		return parametragesGenTeleCompense;
	}

	public void setParametragesGenTeleCompense(
			ParametragesGenTeleCompense parametragesGenTeleCompense) {
		this.parametragesGenTeleCompense = parametragesGenTeleCompense;
	}

	public TourCompensation getCurrentObject2() {
		return currentObject2;
	}

	public void setCurrentObject2(TourCompensation currentObject2) {
		this.currentObject2 = currentObject2;
	}

	public String getHeureTour() {
		return heureTour;
	}

	public void setHeureTour(String heureTour) {
		this.heureTour = heureTour;
	}

	public List<TourCompensation> getListTours() {
		return listTours;
	}

	public void setListTours(List<TourCompensation> listTours) {
		this.listTours = listTours;
	}

	public List<TourCompensation> getListToursToRemove() {
		return listToursToRemove;
	}

	public void setListToursToRemove(List<TourCompensation> listToursToRemove) {
		this.listToursToRemove = listToursToRemove;
	}

	public Selection getSelection() {
		return selection;
	}

	public void setSelection(Selection selection) {
		this.selection = selection;
	}

	public TypePhase getTypePhase() {
		return typePhase;
	}

	public void setTypePhase(TypePhase typePhase) {
		this.typePhase = typePhase;
	}

	public List<SelectItem> getTypePhaseItems() {
		return typePhaseItems;
	}

	public void setTypePhaseItems(List<SelectItem> typePhaseItems) {
		this.typePhaseItems = typePhaseItems;
	}
	
	public Integer getTourActuel() {
		return tourActuel;
	}

	public void setTourActuel(Integer tourActuel) {
		this.tourActuel = tourActuel;
	}

	@Override
	public boolean checkBuildedCurrentObject() {

		try{

			/***if(listTours==null||listTours.isEmpty()){
				this.error = true;
				ExceptionHelper.showError("La liste des tours de compensation ne peut être vide !!!", this);
				return false;
			}*/


			if(currentObject.getDateJournee()==null || currentObject.getStatutJournee()==null || currentObject.getTourMax()==null){

				ExceptionHelper.showError("Tous les champs ne sont pas remplis !!!", this);
				return false;

			}

			if(currentObject.getId()!=null){

				if(currentObject.getDateJournee().before(new SimpleDateFormat("dd/MM/yyyy").parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date())))){

					if(currentObject.getStatutJournee().equals(StatutJournee.OUVERTURE)){
						ExceptionHelper.showError("La date de la journée de compensation ne peut être inférieure à la date d'aujourd'hui !!!", this);
						return false;
					}else{

						if(currentObject.getStatutJourneeEnCours().equals(StatutJournee.FERMETURE)){

							ExceptionHelper.showError("Cette journée est déjà fermée !!!", this);
							return false;

						}else{
							ExceptionHelper.showInformation("Fermeture de la journée du " + new SimpleDateFormat("dd/MM/yyyy").format(currentObject.getDateJournee()), this);

							buildCurrentObject();

							dbObject =  new ParamCompensateurCentrale();
							dbObject.setDateJournee(currentObject.getDateJournee());
							dbObject.setDateJourneeEnCours(currentObject.getDateJourneeEnCours());
							dbObject.setStatutJournee(currentObject.getStatutJournee());
							dbObject.setStatutJourneeEnCours(currentObject.getStatutJourneeEnCours());
							dbObject.setTourActuel(currentObject.getTourActuel());
							dbObject.setTourMax(currentObject.getTourMax());
							dbObject.setUtiTraitement(currentObject.getUtiTraitement());
							ViewHelper.virementsRecManagerDAOLocal.save(dbObject);
							return false;
						}
					}

				}

				if(currentObject.getDateJournee().after(new SimpleDateFormat("dd/MM/yyyy").parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date())))){

					ExceptionHelper.showError("La date de la journée de compensation ne peut être supérieure à la date d'aujourd'hui !!!", this);
					return false;

				}

				if(currentObject.getTourMax()==0){

					ExceptionHelper.showError("Le nombre de tours maximum doit être au moins 1 !!!", this);
					return false;

				}

				if(currentObject.getTourActuel()==0){

					ExceptionHelper.showError("Le tour actuel ne peut être 0 !!!", this);
					return false;

				}

				if(currentObject.getStatutJournee().equals(StatutJournee.FERMETURE)){

					dbObject = ViewHelper.virementsRecManagerDAOLocal.findByPrimaryKey(ParamCompensateurCentrale.class, currentObject.getId(), null);
					//filter(ParamCompensateurCentrale.class, null, RestrictionsContainer.getInstance().add(Restrictions.eq(propertyName, value)), arg3, arg4, arg5, arg6)
					if(dbObject==null){

					}
					else if(currentObject.getDateJournee().equals(dbObject.getDateJournee())){

						if(dbObject.getStatutJournee().equals(StatutJournee.OUVERTURE)){
							/////OK
						}else{
							ExceptionHelper.showError("Cette journée a déjà été fermée !!!", this);
							return false;
						}

					}else if(currentObject.getDateJournee().after(dbObject.getDateJournee())){

						if(dbObject.getStatutJournee().equals(StatutJournee.OUVERTURE)){

							ExceptionHelper.showError("L'ancienne journée n'a pas encore été fermée !!!", this);
							return false;
						}else{

							ExceptionHelper.showError("Cette journée ne peux pas encore être fermée !!!", this);
							return false;

						}

					}else{

						ExceptionHelper.showError("La date de la journée ne peut pas être inférieure à la plus récente !!!" + new SimpleDateFormat("dd/MM/yyyy").format(dbObject.getDateJournee()), this);
						return false;
					}
				}

				if(currentObject.getStatutJournee().equals(StatutJournee.OUVERTURE)){

					ParamCompensateurCentrale dbObject = ViewHelper.virementsRecManagerDAOLocal.findByPrimaryKey(ParamCompensateurCentrale.class, currentObject.getId(), null);


					if(dbObject==null){

						//currentObject.setDateJournee(dateJournee);
					}

					else if(currentObject.getDateJournee().equals(dbObject.getDateJournee())){

						if(dbObject.getStatutJournee().equals(StatutJournee.OUVERTURE) && (currentObject.getTourActuel()==dbObject.getTourActuel())){
							ExceptionHelper.showError("Cette journée est déjà ouverte !!!", this);
							return false;
						}else if(dbObject.getStatutJournee().equals(StatutJournee.FERMETURE)){
							ExceptionHelper.showError("Cette journée a déjà été fermée !!!", this);
							return false;
						}

					}
					else if(currentObject.getDateJournee().after(dbObject.getDateJournee())){

						if(dbObject.getStatutJournee().equals(StatutJournee.OUVERTURE)){

							ExceptionHelper.showError("L'ancienne journée n'a pas encore été fermée !!!", this);
							return false;

						}else{
							//////OK
							
							//currentObject.setTourActuel(1);
							tourActuel = 1;

							buildCurrentObject();

							dbObject =  new ParamCompensateurCentrale();
							dbObject.setDateJournee(currentObject.getDateJournee());
							dbObject.setDateJourneeEnCours(currentObject.getDateJourneeEnCours());
							dbObject.setStatutJournee(currentObject.getStatutJournee());
							dbObject.setStatutJourneeEnCours(currentObject.getStatutJourneeEnCours());
							dbObject.setTourActuel(currentObject.getTourActuel());
							dbObject.setTourMax(currentObject.getTourMax());
							dbObject.setUtiTraitement(currentObject.getUtiTraitement());
							ViewHelper.virementsRecManagerDAOLocal.save(dbObject);

							this.information = true;
							ExceptionHelper.showInformation(" Ouverture de la journée !!! Lancement des archives des répertoires d'entrées et de destination !!!", this);

							System.out.println("***Lancer suppression des répertoires entrée et archives des répertoires sorties***");

							/***Lancer suppression des répertoires entrée et archives des répertoires sorties*****/

							suppressionEntreeEtArchiveSorties();

							return false;
						}
					}
					else{


					}
				}

			} 

		}catch(Exception e){
			e.printStackTrace();
		}


		return true;
	}


	public void suppressionEntreeEtArchiveSorties(){

		System.out.println("***IN suppressionEntreeEtArchiveSorties****");

		List<ParametragesGenTeleCompense> listParams = ViewHelper.virementsRecManager.filterParamGen();

		if(listParams!=null && !listParams.isEmpty()){
			parametragesGenTeleCompense = listParams.get(0);
		}

		String entree, sortie, archive, archive_entree;

		List<OuvertureJournee> OJ = new ArrayList<OuvertureJournee>();

		OuvertureJournee ouvertureJournee = new OuvertureJournee();

		for (TypeProcess val : TypeProcess.types()){

			if(val.name().equals(TypeProcess.RAPATRIEMENT_IMG_ALLER.toString())){
				entree = parametragesGenTeleCompense.getRepertoireEntreeCollecteImageAller();
				sortie = parametragesGenTeleCompense.getRepertoireDestinationCollecteImageAller();
				archive = parametragesGenTeleCompense.getRepertoireArchivageCollecteImageAller();
				archive_entree = parametragesGenTeleCompense.getRepertoireArchiveEntreeCollecteImageAller();
				ouvertureJournee = viderRepertoiresPourNouvelleJrnee(entree, sortie, archive,archive_entree,TypeProcess.RAPATRIEMENT_IMG_ALLER);
				if(ouvertureJournee!=null){
					OJ.add(ouvertureJournee);
				}
			}
			if(val.name().equals(TypeProcess.FICHIER_COMPTA_ALLER.toString())){
				entree = parametragesGenTeleCompense.getRepertoireEntreeFichierComptabilisationAller();
				sortie = parametragesGenTeleCompense.getRepertoireDestinationFichierComptabilisationAller();
				archive = parametragesGenTeleCompense.getRepertoireArchivageFichierComptabilisationAller();
				archive_entree = parametragesGenTeleCompense.getRepertoireArchiveEntreeFichierComptabilisationAller();
				ouvertureJournee = viderRepertoiresPourNouvelleJrnee(entree, sortie, archive, archive_entree, TypeProcess.FICHIER_COMPTA_ALLER);
				if(ouvertureJournee!=null){
					OJ.add(ouvertureJournee);
				}
			}
			if(val.name().equals(TypeProcess.RAPATRIEMENT_IMG_RETOUR.toString())){
				entree = parametragesGenTeleCompense.getRepertoireEntreeCollecteImageRetour();
				sortie = parametragesGenTeleCompense.getRepertoireDestinationCollecteImageRetour();
				archive = parametragesGenTeleCompense.getRepertoireArchivageCollecteImageRetour();
				archive_entree = parametragesGenTeleCompense.getRepertoireArchiveEntreeCollecteImageRetour();
				ouvertureJournee = viderRepertoiresPourNouvelleJrnee(entree, sortie, archive, archive_entree, TypeProcess.RAPATRIEMENT_IMG_RETOUR);
				if(ouvertureJournee!=null){
					OJ.add(ouvertureJournee);
				}
			}
			if(val.name().equals(TypeProcess.FICHIER_COMPTA_RETOUR.toString())){
				entree = parametragesGenTeleCompense.getRepertoireEntreeFichierComptabilisationRetour();
				sortie = parametragesGenTeleCompense.getRepertoireDestinationFichierComptabilisationRetour();
				archive = parametragesGenTeleCompense.getRepertoireArchivageFichierComptabilisationRetour();
				archive_entree = parametragesGenTeleCompense.getRepertoireArchiveEntreeFichierComptabilisationRetour();
				ouvertureJournee = viderRepertoiresPourNouvelleJrnee(entree, sortie, archive, archive_entree, TypeProcess.FICHIER_COMPTA_RETOUR);
				if(ouvertureJournee!=null){
					OJ.add(ouvertureJournee);
				}
			}
		}

		/**********************ENVOI EMAIL****************************************/

		String title="Rapport ouverture de Journée de Compensation, le " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()) + " à " + new SimpleDateFormat("HH:mm:ss").format(new Date());
		String msg = "Bonjour, " + "\t" + "\n" + "\t\t" + "Veuillez trouver ci-dessous le rapport des fichiers archivés des répertoires d'entrées et de destination lors de l'ouverture de la journée de compensation de ce jour : " + "\n\n";

		String [] fichEntr;
		String [] fichSortie;
		List<String> fE = new ArrayList<String>();
		List<String> fS = new ArrayList<String>();
		for(OuvertureJournee oj: OJ){
			if(oj.getFichiersRepEntree()!=null && !oj.getFichiersRepEntree().isEmpty()){
				fichEntr =  oj.getFichiersRepEntree().split(";");
				for(String s: fichEntr){
					if(!s.isEmpty())
						fE.add(s);
				}
			}
			if(oj.getFichiersRepSortie()!=null && !oj.getFichiersRepSortie().isEmpty()){
				fichSortie = oj.getFichiersRepSortie().split(";");
				for(String s: fichSortie){
					if(!s.isEmpty())
						fS.add(s);
				}
			}
		}


		if(!fE.isEmpty())
			msg = msg + "Nombre de fichiers archivés dans les répertoires d'archives entreés, venant des répertoires d'entrées: " + fE.size() + "\n\n"; 

		if(!fS.isEmpty())
			msg = msg + "Nombre de fichers archivés dans les répertoires d'archives, venant des répertoires de destination: " + fS.size() + "\n\n";


		if(!fE.isEmpty() || !fS.isEmpty()){

			List<String>mailsTo = new ArrayList<String>();

			if(parametragesGenTeleCompense.getListEmails()!=null && !parametragesGenTeleCompense.getListEmails().isEmpty()){

				for(ParamEmail pe: parametragesGenTeleCompense.getListEmails()){
					if(pe.getValide().equals(Boolean.TRUE))
						mailsTo.add(pe.getEmail());
				}
			}

			List<String>mailsCC = new ArrayList<String>();

			List<String>mailsBCC = new ArrayList<String>();

			String ip="";
			String email="";
			String pass="";
			Integer port=22;
			List<ParamEmailAuto>paramsEmailAuto=ViewHelper.virementsRecManager.filterParamEmailAuto();
			if(paramsEmailAuto!=null && !paramsEmailAuto.isEmpty()){
				for(ParamEmailAuto p: paramsEmailAuto){
					ip = p.getIp();
					email = p.getEmail();
					pass = Encrypter.getInstance().decryptText(p.getPass());
					port = p.getPort();
				}
			}

			/*try {
				SendMail.sendMail(null, null, mailsTo, mailsCC, mailsBCC, title, msg, ip, email, pass, port);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			try {
				List<ParamEmailAuto> parametres = ViewHelper.virementsRecManagerDAOLocal.filter(ParamEmailAuto.class, null, null, null, null, 0, -1);
				if(parametres!=null&&!parametres.isEmpty()){
					AfrilandSendMail.sendMail(null, null, mailsTo, mailsCC, title, msg, email, pass, email + "@afrilandfirstbank.com", "AFRILAND FIRST BANK", ip, String.valueOf(port));
				}
			} catch (Exception ex) {
				// TODO Auto-generated catch block
			}

		}
		/**********************************************/
	}


	public OuvertureJournee viderRepertoiresPourNouvelleJrnee(String entree, String sortie, String archive, String archive_entree, TypeProcess typeProcess){

		OuvertureJournee ouvertureJournee = null;

		try{

			System.out.println("***IN viderRepertoiresPourNouvelleJrnee****");

			String filesEntree="";
			String filesDest="";

			ouvertureJournee = new OuvertureJournee();
			ouvertureJournee.setUtiTraitement(ViewHelper.getSessionUser().getLogin());
			ouvertureJournee.setDateOuverture(new Date());
			ouvertureJournee.setHeure(new SimpleDateFormat("HH:mm:ss").format(new Date()));
			ouvertureJournee.setTypeProcess(typeProcess);


			File folderEntree = new File(entree);
			//Si le répertoire n'existe pas, il faut le créer
			if(!folderEntree.exists()){
				if(folderEntree.mkdirs()){
					System.out.println("*****Les répertoires parents sont crées avec succès!******");
				} else {
					System.out.println("******Echec de création des répertoires parents!*****");
				}
			}
			System.out.println("****folderEntree*****" + folderEntree);


			File folderDest = new File(sortie);
			//Si le répertoire n'existe pas, il faut le créer
			if(!folderDest.exists()){
				if(folderDest.mkdirs()){
					System.out.println("*****Les répertoires parents sont crées avec succès!******");
				} else {
					System.out.println("******Echec de création des répertoires parents!*****");
				}
			}
			System.out.println("****folderDest*****" + folderDest);


			File folderArchiveEntree = new File(archive_entree);
			//Si le répertoire n'existe pas, il faut le créer
			if(!folderArchiveEntree.exists()){
				if(folderArchiveEntree.mkdirs()){
					System.out.println("*****Les répertoires parents sont crées avec succès!******");
				} else {
					System.out.println("******Echec de création des répertoires parents!*****");
				}
			}
			System.out.println("****folderArchiveEntree*****" + folderArchiveEntree);

			/*****************************Suppression des fichiers dans le répertoire d'entrée (archivage dans le repertoire archive_entree)**********************************************************/
			File[] listOfFiles = folderEntree.listFiles();
			if(listOfFiles==null || listOfFiles.length==0){

				System.out.println("Aucun fichier trouvé dans le répertoire d'entrée !!!");
				/*this.error=true;
			this.information=true;
			ExceptionHelper.showError("Aucun fichier trouvé dans le répertoire d'entrée !!!", this);
			return;*/
			}else if(listOfFiles!=null && listOfFiles.length>0){

				//lancer la suppression des fichiers dans ce répertoire d'entrée

				for (int i = 0; i < listOfFiles.length; i++) {
					if (listOfFiles[i].isFile()) {

						System.out.println("listOfFiles[i] " + listOfFiles[i]);
						System.out.println("listOfFiles[i].getName() " + listOfFiles[i].getName());

						String fichierEntree = entree + File.separator + listOfFiles[i].getName();

						String fichierArchEntree = archive_entree + File.separator + listOfFiles[i].getName();

						Path pathentree = Paths.get(fichierEntree);

						Path pathArchiveEntree = Paths.get(fichierArchEntree);

						if(filesEntree.isEmpty())
							filesEntree = listOfFiles[i].getName();
						else
							filesEntree = filesEntree + ";" + listOfFiles[i].getName();

						//listOfFiles[i].delete();
						//System.out.println("Successfully deleted file " + listOfFiles[i].getName());

						Path p = Files.move(pathentree, pathArchiveEntree, StandardCopyOption.REPLACE_EXISTING);
						System.out.println("***Files copied to ***" + p.toString());
					}
				}

				ouvertureJournee.setFichiersRepEntree(filesEntree);

			}

			/***********************************************************************************************************************************************/

			/*****************************Archivage des fichiers du répertoire de destination**********************************************************/

			File[] listFiles = folderDest.listFiles();
			if(listFiles==null || listFiles.length==0){
				System.out.println("Aucun fichier trouvé dans le répertoire de destination !!!");
			}
			else if(listFiles!=null && listFiles.length>0){
				//Le répertoire de destination n'est pas vide; lancer l'archivage des fichiers contenus si c'est l'ouverture de la journée

				for (int i = 0; i < listFiles.length; i++){
					if (listFiles[i].isFile()) {

						String fichierDest = sortie + File.separator + listFiles[i].getName();

						String fichierArch = archive + File.separator + listFiles[i].getName();

						Path pathDestination = Paths.get(fichierDest);

						Path pathArchive = Paths.get(fichierArch);

						if(filesDest.isEmpty())
							filesDest = listFiles[i].getName();
						else
							filesDest = filesDest + ";" + listFiles[i].getName();

						Path p = Files.move(pathDestination, pathArchive, StandardCopyOption.REPLACE_EXISTING);
						System.out.println("***Files copied to ***" + p.toString());
					}
				}
				ouvertureJournee.setFichiersRepSortie(filesDest);
			}

			ViewHelper.virementsRecManagerDAOLocal.save(ouvertureJournee);


			/***********************************************************************************************************************************************/

		}catch(Exception e){

			e.printStackTrace();
		}

		return ouvertureJournee;
	}

	@Override
	public String getChildDialogDefinition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSecondChildDefinition() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void initComponents() {

		statutJourneeItems.add(new SelectItem(null, "---Choisir---"));
		for(StatutJournee val : StatutJournee.types())statutJourneeItems.add( new SelectItem(val.name(),val.value()) );

		typePhaseItems.add(new SelectItem(null, "---Choisir---"));
		for (TypePhase val : TypePhase.types()){
			typePhaseItems.add(new SelectItem(val.name(), val.getValue()));
		}

		params =  ViewHelper.virementsRecManagerDAOLocal.filter(ParamCompensateurCentrale.class, null, null, OrderContainer.getInstance().add(Order.desc("id")), null, 0, -1);
		System.out.println("-----params size-----" + params.size());

		if(params!=null&&!params.isEmpty()){
			currentObject = params.get(0);
			tourActuel = currentObject.getTourActuel();
		}

	}

	@Override
	protected void buildCurrentObject() throws ParseException {

		if(currentObject.getTourActuel()==null){
			currentObject.setTourActuel(1);
		}else{
			currentObject.setTourActuel(tourActuel);
		}

		currentObject.setDateJourneeEnCours(currentObject.getDateJournee());

		currentObject.setStatutJourneeEnCours(currentObject.getStatutJournee());

		currentObject.setUtiTraitement(ViewHelper.getSessionUser().getLogin());
	}

	@Override
	protected void validate() {

		ViewHelper.virementsRecManagerDAOLocal.save(currentObject);
	}

	@Override
	protected void disposeResourceOnClose() {
		statutJourneeItems.clear();
		listTours.clear();
		listToursToRemove.clear();
	}

	public void passProTour(){

		//int tourActuel = currentObject.getTourActuel();

		if(currentObject.getStatutJourneeEnCours().equals(StatutJournee.OUVERTURE)){

			if(tourActuel<currentObject.getTourMax()){

				if(tourActuel-currentObject.getTourActuel()==0 && tourActuel+1 <currentObject.getTourMax()){
					tourActuel +=1;
				}
				else{
					this.error = true;
					this.information = true;
					ExceptionHelper.showError("Le tour ", tourActuel + " n'a pas encore été exécuté. Veuillez d'abord l'exécuter !!!", this);
					return;
				}
				//currentObject.setTourActuel(tourActuel+1);

			}else{

				this.error = true;
				this.information = true;
				ExceptionHelper.showError("Vous ne pouvez pas aller au dela de ", currentObject.getTourMax() + " tours!!!", this);
				return;
			}
		}else{

			System.out.println("Vous ne pouvez pas passer au prochain tour pour une journée fermée");

			ExceptionHelper.showError("Vous ne pouvez pas passer au prochain tour pour une journée fermée !!!", this);
			this.error = true;
			this.information = true;
			return;

		}

	}


	public TourCompensation getDeleUser() {
		return deleUser;
	}

	public void setDeleUser(TourCompensation deleUser) {
		this.deleUser = deleUser;

		try {

			if(deleUser != null){

				System.out.println("email " +deleUser.getNumeroTour());

				//deleUser = getElement();

				listToursToRemove.add(deleUser);

				listTours.remove(deleUser);


				//System.out.println("----emails size after remove----" + emails.size());

				//deleUser.setValide(Boolean.FALSE);

				//caracVir.add(deleUser);

				//System.out.println("email " +deleUser.getEmail() + " enlevé avec succes!");

			}

		} catch (Exception e) {

			// Traitement de l'exception
			ExceptionHelper.threatException(e);
		}
	}


	/*public ToursRetour getDeleUser2() {
		return deleUser2;
	}

	public void setDeleUser2(ToursRetour deleUser2) {
		this.deleUser2 = deleUser2;

		try {

			if(deleUser != null){

				//System.out.println("CaractereSpecial " +deleUser2.getCaractereSpecial());

				listToursRetourToRemove.add(deleUser2);

				listToursRetour.remove(deleUser2);

			}

		} catch (Exception e) {

			// Traitement de l'exception
			ExceptionHelper.threatException(e);
		}
	}*/


	public void ajouterParam(){

		if(heureTour!=null && !heureTour.isEmpty()){

			Integer tour = 1;

			int size = 0;

			currentObject2 = new TourCompensation();
			currentObject2.setHeure(heureTour.trim());
			currentObject2.setTypePhase(typePhase);
			currentObject2.setUtiTraitement(ViewHelper.getSessionUser().getLogin());
			currentObject2.setDateTraitement(new Date());


			if (this.listTours == null) {

				this.listTours = new ArrayList<TourCompensation>();

				tour = 1;

			} else if (this.listTours != null && this.listTours.size() > 0) {

				size = this.listTours.size();

				tour = size + 1;
			}


			currentObject2.setNumeroTour(tour);
			currentObject2.setParamCompensateurCentrale(currentObject);


			listTours.add(currentObject2);

			heureTour = "";

		}
	}

	/*public void ajouterParam2(){

		if(heureToursRetour!=null && !heureToursRetour.isEmpty()){

			Integer tour = 1;

			int size = 0;

			currentObject3 = new ToursRetour();
			currentObject3.setHeure(heureToursRetour);

			if (this.listToursRetour == null) {

				this.listToursRetour = new ArrayList<ToursRetour>();

				tour = 1;

			} else if (this.listToursRetour != null && this.listToursRetour.size() > 0) {

				size = this.listToursRetour.size();

				tour = size + 1;
			}

			currentObject3.setNumeroTour(tour);
			currentObject3.setParamCompensateurCentrale(currentObject);


			listToursRetour.add(currentObject3);

			heureToursRetour = "";

		}
	}*/

	/******
	 * 
	 * 
	 * 	<!-- <tr>
									<td width="25%" align="left">
										<fieldset class="fieldSetStyle">
											<legend>
												<h:outputText value="Paramétrages Tours Compensation"
													styleClass="labelStyle" />
											</legend>

											<table>
												<tr>
													<td><h:outputText value="Heure"
															styleClass="labelStyle" /></td>
													<td><h:inputText
															value="#{paramCompensateurCentrauxDialog.heureTour}"
															styleClass="text ui-widget-content ui-corner-all"
															maxlength="255" /></td>

													<td><h:outputText value="Phase"
															styleClass="labelStyle" /></td>
													<td><h:selectOneMenu
															value="#{paramCompensateurCentrauxDialog.typePhase}"
															required="true" requiredMessage="Entrer le type de phase"
															styleClass="text ui-widget-content ui-corner-all">
															<f:selectItems
																value="#{paramCompensateurCentrauxDialog.typePhaseItems}" />
														</h:selectOneMenu></td>


													<td><h:panelGrid columns="5">
															<rich:spacer width="3px" />
															<a4j:commandButton value="Ajouter"
																action="#{paramCompensateurCentrauxDialog.ajouterParam}"
																reRender="#{clientArea.ID}" limitToList="true"
																styleClass="ui-button ui-widget ui-state-default ui-corner-all"
																onclick="startWaitInStyle();"
																oncomplete="stoptWaitInStyle();" />
															<rich:spacer width="3px" />
															<rich:spacer width="5px" />
														</h:panelGrid></td>

												</tr>
												<tr>

													<td><rich:extendedDataTable id="Table" rendered="true"
															var="var" rowKeyVar="row" width="200" height="100px"
															value="#{paramCompensateurCentrauxDialog.listTours}"
															selection="#{paramCompensateurCentrauxDialog.selection}"
															rowClasses="row-style-impair, row-style-pair"
															cellpadding="0" selectionMode="multi"
															selectedClass="row-style-selected"
															noDataLabel="#{messages['table.nodata']}">

															<rich:column width="30%">
																<f:facet name="header">
																	<h:panelGroup>
																		<h:outputText value="Phase" />
																	</h:panelGroup>
																</f:facet>
																<h:outputText value="#{var.typePhase}" />
															</rich:column>

															<rich:column width="10%">
																<f:facet name="header">
																	<h:panelGroup>
																		<h:outputText value="Tour" />
																	</h:panelGroup>
																</f:facet>
																<h:outputText value="#{var.numeroTour}" />
															</rich:column>


															<rich:column width="30%">
																<f:facet name="header">
																	<h:panelGroup>
																		<h:outputText value="Heure" />
																	</h:panelGroup>
																</f:facet>
																<h:outputText value="#{var.heure}" />
															</rich:column>

															<rich:column width="20%">
																<f:facet name="header">Supprimer</f:facet>
																<h:graphicImage
																	value="#{viewHelper.sessionSkinURL}/Images/DeleteRed16x.png"
																	title="Supprimer" style="cursor:pointer">
																	<a4j:support event="onclick"
																		reRender="#{clientArea.ID}" ajaxSingle="true"
																		onsubmit="startWaitInStyle();"
																		oncomplete="stoptWaitInStyle();">
																		<f:setPropertyActionListener
																			target="#{paramCompensateurCentrauxDialog.deleUser}"
																			value="#{var}" />
																	</a4j:support>
																</h:graphicImage>
															</rich:column>

														</rich:extendedDataTable></td>
												</tr>
											</table>


										</fieldset>

									</td>

								</tr>

								<tr>

									<td><h:outputText value="Heure Début Journée" styleClass="labelStyle" /></td>
									<td><h:inputText
											value="#{paramCompensateurCentrauxDialog.currentObject.heureStartJournee}"
											styleClass="text ui-widget-content ui-corner-all"
											maxlength="255" /></td>


									<td><h:outputText value="Heure Fin Journée" styleClass="labelStyle" /></td>
									<td><h:inputText
											value="#{paramCompensateurCentrauxDialog.currentObject.heureFinJournee}"
											styleClass="text ui-widget-content ui-corner-all"
											maxlength="255" /></td>


								</tr>






				----OK-----









				-->

	 * 
	 */


}
