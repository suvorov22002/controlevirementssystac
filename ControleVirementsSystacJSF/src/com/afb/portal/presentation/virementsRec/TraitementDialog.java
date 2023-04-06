package com.afb.portal.presentation.virementsRec;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.mail.MessagingException;
import javax.persistence.Query;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.richfaces.model.selection.Selection;

import afb.dsi.dpd.portal.jpa.entities.User;

import com.afb.dsi.alertes.AfrilandSendMail;
import com.afb.portal.presentation.models.AbstractDialog;
import com.afb.portal.presentation.models.DialogFormMode;
import com.afb.portal.presentation.models.reportViewer.ReportViewer;
import com.afb.portal.presentation.tools.ExceptionHelper;
import com.afb.portal.presentation.tools.Multilangue;
import com.afb.portal.presentation.tools.ViewHelper;
import com.afb.virementsRec.jpa.datamodel.CaracteristiquesVir;
import com.afb.virementsRec.jpa.datamodel.CaracteristiquesVirItem;
import com.afb.virementsRec.jpa.datamodel.ContenuFichier;
import com.afb.virementsRec.jpa.datamodel.Fichier;
import com.afb.virementsRec.jpa.datamodel.FichiersSupprimeACauseDeDoublonsNomsFichiers;
import com.afb.virementsRec.jpa.datamodel.FichiersSupprimeACauseDeValeursEnDoublonsDansAutreFichier;
import com.afb.virementsRec.jpa.datamodel.ParamCompensateurCentrale;
import com.afb.virementsRec.jpa.datamodel.ParamEmail;
import com.afb.virementsRec.jpa.datamodel.ParamEmailAuto;
import com.afb.virementsRec.jpa.datamodel.Parametrages;
import com.afb.virementsRec.jpa.datamodel.ParametragesCaracteresSpeciaux;
import com.afb.virementsRec.jpa.datamodel.ParametragesGenTeleCompense;
import com.afb.virementsRec.jpa.datamodel.RapatriementImagesAller;
import com.afb.virementsRec.jpa.datamodel.RapatriementImagesRetour;
import com.afb.virementsRec.jpa.datamodel.FichiersComptabilisationAller;
import com.afb.virementsRec.jpa.datamodel.FichiersComptabilisationRetour;
import com.afb.virementsRec.jpa.datamodel.SendMail;
import com.afb.virementsRec.jpa.datamodel.SortTraitement;
import com.afb.virementsRec.jpa.datamodel.StatistiqueRapports;
import com.afb.virementsRec.jpa.datamodel.StatutJournee;
import com.afb.virementsRec.jpa.datamodel.TourCompensation;
import com.afb.virementsRec.jpa.datamodel.TraitementTourCompensation;
import com.afb.virementsRec.jpa.datamodel.TypeProcess;
import com.afb.virementsRec.jpa.datamodel.TypeTraitement;
import com.afb.virementsRec.jpa.datamodel.ValidateDoublonsInFichier;
import com.ibm.icu.text.SimpleDateFormat;
import com.yashiro.persistence.utils.dao.tools.OrderContainer;
import com.yashiro.persistence.utils.dao.tools.RestrictionsContainer;
import com.yashiro.persistence.utils.dao.tools.encrypter.Encrypter;



public class TraitementDialog extends AbstractDialog{

	//private ParametragesGenTeleCompense currentObject = new ParametragesGenTeleCompense();

	private static TimerTask task3;

	private static Timer timer3;

	private TypeProcess typeProcess;

	private TypeTraitement typeTraitement;

	private List<SelectItem> typeProcessItems = new ArrayList<SelectItem>();

	private List<SelectItem> tourItems = new ArrayList<SelectItem>();

	private List<ParamEmail> emails = new ArrayList<ParamEmail>();

	private Selection selection;

	private ParamEmail deleUser = new ParamEmail();

	private int index = 0;

	private Integer tour;

	private String msgFinTraitement;

	private boolean statutTraitement = false;

	private User user;

	private Integer nbrFichACopierImg;

	private Integer nbrFichCopieImg;

	private Integer nbrDoublonsImg;

	private Integer nbrFichAArchiver;

	private Integer nbrFichArchive;

	private Integer nbrFichACopierCompta;

	private Integer nbrFichATraiteComptaTotal;

	private Integer nbrFichCopieCompta;

	private Integer nbrDoublonsCompta; //nbr de fichiers en doubles

	//private Integer nbrDoublonsComptaTotal;


	/*private Integer nbrFichAArchiverComptaAller;

	private Integer nbrFichArchiveComptaAller;*/

	private Integer nbrValeursEnDoubleCompta;
	//private Integer nbrValeursEnDoubleComptaTotal;
	private Integer nbrValeursDeposeesCompta;
	//private Integer nbrValeursDeposeesComptaTotal;
	private Integer nbrFichContenantDoublonsCompta;
	//private Integer nbrFichContenantDoublonsComptaTotal;
	private Integer nbrFichDoublonsEntreFichiers;
	//private Integer nbrFichDoublonsEntreFichiersTotal;

	private Map<String, List<String>> mapFichierEtValEnDoubleComptaAllerTraite1 = new HashMap<>();

	//private Map<Fichier, String> mapFichierEtContenu = new HashMap<>();
	private Map<Fichier, List<String>> mapFichierEtContenu = new HashMap<>();

	private List<String> listFichierSupprimePourDoublonsEntreFichiers = new ArrayList<>();

	private List<String> listFichierContenantDoublonsEntreFichiers = new ArrayList<>();

	private List<String> listFichierACopierPourDoublonsEntreFichiers = new ArrayList<>(); //les fichiers potentiels a copier lorsqu'ils sont en double dans le même répertoire en termes de contenus; il faudra à la fin choisir un seul de ces fichiers
	private List<String> listFichierANePasCopierPourDoublonsEntreFichiers = new ArrayList<>(); 
	private Map<String, Map<String,Integer>> mapFichiersContenuDansFichiers = new HashMap<>();


	/**private Integer nbrFichACopierComptaRetour;

	private Integer nbrFichCopieComptaRetour;

	private Integer nbrDoublonsComptaRetour; //nbr de fichiers en doubles */

	/*private Integer nbrFichAArchiverComptaRetour;

	private Integer nbrFichArchiveComptaRetour;*/

	/**private Integer nbrValeursEnDoubleComptaRetour;
	private Integer nbrValeursDeposeesComptaRetour;
	private Integer nbrFichContenantDoublonsComptaRetour;*/

	private Map<String, List<String>> mapFichierEtValEnDoubleComptaRetourTraite1 = new HashMap<>();



	/**private Integer nbrFichACopierImgRetour;

	private Integer nbrFichCopieImgRetour;

	private Integer nbrDoublonsImgRetour;*/


	/*private Integer nbrFichAArchiverImgRetour;

	private Integer nbrFichArchiveImgRetour;*/


	private List<String> listFichierEtValEnDouble = new ArrayList<>();

	private Parametrages parametrages = new Parametrages();

	private ParametragesGenTeleCompense parametragesGenTeleCompense;

	private List<ParametragesCaracteresSpeciaux> parametragesCaracteresSpeciauxs;

	private List<String>listFileNamesWithDoublons = new ArrayList<String>();

	private boolean nomFichierDoublon;

	final static Charset ENCODING = StandardCharsets.UTF_8;

	private ReportViewer reportViewer;

	private List<String> filesNames = new ArrayList<String>();

	private List<String> filesPath = new ArrayList<String>();

	private List<String>mailsTo = new ArrayList<String>();

	private List<String>mailsCC = new ArrayList<String>();

	private List<String>mailsBCC = new ArrayList<String>();

	private String ip="";

	private String email="";

	private String pass="";

	private Integer port=22;

	private String title = "";

	private String msg = "";



	//private static final long serialVersionUID = -314414475508376585L;
	private boolean buttonRendered = true;
	private boolean enabled = false;
	private int currentValue;

	public String startProcess() {
		setEnabled(true);
		setButtonRendered(false);
		setCurrentValue(0);

		//ViewHelper.virementsRecManager.checkAndMountDiskForCompensation();

		return null;
	}

	public void increment(ActionEvent ae) {
		System.out.println("***In increment****");
		if (isEnabled() && currentValue < 100) {
			currentValue += 2;
			if (currentValue >= 100) {
				setButtonRendered(true);
				System.out.println("***currentValue***" + currentValue);
			}
		}
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isButtonRendered() {
		return buttonRendered;
	}

	public void setButtonRendered(boolean buttonRendered) {
		this.buttonRendered = buttonRendered;
	}

	public TypeTraitement getTypeTraitement() {
		return typeTraitement;
	}

	public void setTypeTraitement(TypeTraitement typeTraitement) {
		this.typeTraitement = typeTraitement;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/*public Integer getNbrFichACopierImgAller() {
		return nbrFichACopierImgAller;
	}

	public void setNbrFichACopierImgAller(Integer nbrFichACopierImgAller) {
		this.nbrFichACopierImgAller = nbrFichACopierImgAller;
	}

	public Integer getNbrFichCopieImgAller() {
		return nbrFichCopieImgAller;
	}

	public void setNbrFichCopieImgAller(Integer nbrFichCopieImgAller) {
		this.nbrFichCopieImgAller = nbrFichCopieImgAller;
	}

	public Integer getNbrDoublonsImgAller() {
		return nbrDoublonsImgAller;
	}

	public void setNbrDoublonsImgAller(Integer nbrDoublonsImgAller) {
		this.nbrDoublonsImgAller = nbrDoublonsImgAller;
	}*/

	/*public Integer getNbrFichAArchiverImgAller() {
		return nbrFichAArchiverImgAller;
	}

	public void setNbrFichAArchiverImgAller(Integer nbrFichAArchiverImgAller) {
		this.nbrFichAArchiverImgAller = nbrFichAArchiverImgAller;
	}

	public Integer getNbrFichArchiveImgAller() {
		return nbrFichArchiveImgAller;
	}

	public void setNbrFichArchiveImgAller(Integer nbrFichArchiveImgAller) {
		this.nbrFichArchiveImgAller = nbrFichArchiveImgAller;
	}*/

	/*public Integer getNbrFichACopierImgRetour() {
		return nbrFichACopierImgRetour;
	}

	public void setNbrFichACopierImgRetour(Integer nbrFichACopierImgRetour) {
		this.nbrFichACopierImgRetour = nbrFichACopierImgRetour;
	}

	public Integer getNbrFichCopieImgRetour() {
		return nbrFichCopieImgRetour;
	}

	public void setNbrFichCopieImgRetour(Integer nbrFichCopieImgRetour) {
		this.nbrFichCopieImgRetour = nbrFichCopieImgRetour;
	}

	public Integer getNbrDoublonsImgRetour() {
		return nbrDoublonsImgRetour;
	}

	public void setNbrDoublonsImgRetour(Integer nbrDoublonsImgRetour) {
		this.nbrDoublonsImgRetour = nbrDoublonsImgRetour;
	}*/

	/*public Integer getNbrFichAArchiverImgRetour() {
		return nbrFichAArchiverImgRetour;
	}

	public void setNbrFichAArchiverImgRetour(Integer nbrFichAArchiverImgRetour) {
		this.nbrFichAArchiverImgRetour = nbrFichAArchiverImgRetour;
	}

	public Integer getNbrFichArchiveImgRetour() {
		return nbrFichArchiveImgRetour;
	}

	public void setNbrFichArchiveImgRetour(Integer nbrFichArchiveImgRetour) {
		this.nbrFichArchiveImgRetour = nbrFichArchiveImgRetour;
	}*/

	public ParametragesGenTeleCompense getParametragesGenTeleCompense() {
		return parametragesGenTeleCompense;
	}

	public void setParametragesGenTeleCompense(
			ParametragesGenTeleCompense parametragesGenTeleCompense) {
		this.parametragesGenTeleCompense = parametragesGenTeleCompense;
	}

	public ReportViewer getReportViewer() {
		return reportViewer;
	}

	public void setReportViewer(ReportViewer reportViewer) {
		this.reportViewer = reportViewer;
	}

	public int getCurrentValue() {
		/*System.out.println("***In getCurrentValue****");
		if (!isEnabled()) {
			return -1;
		}
		else if (isEnabled() && currentValue < 100) {
			currentValue += 2;
			if (currentValue >= 100) {
				setButtonRendered(true);
				System.out.println("***currentValue***" + currentValue);
			}
		}*/
		return currentValue;
	}

	public void setCurrentValue(int currentValue) {
		this.currentValue = currentValue;
	}


	public List<String> readAndScanFile(Path pathEntree){

		List<String> fileLines = new ArrayList<String>();

		if(pathEntree!=null){

			Scanner scanner = null;
			try{
				scanner =  new Scanner(pathEntree);
			}
			catch(Exception e){

				System.out.println(e.getMessage());
				e.printStackTrace();
			}


			String scLine = "";

			int j=0;

			if(scanner!=null){

				while(scanner.hasNextLine()){

					scLine = scanner.nextLine();

					if(!scLine.isEmpty()&&!scLine.equals("")){

						fileLines.add(scLine.trim());

					}
					j++;
					//System.out.println("***READ AND GOT LINE "+ j);
				}

				scanner.close();
			}
		}
		return fileLines;
	}

	

	
	

	public void traitementInDB(){
		System.out.println("******************TRAITEMENT EN BD*****************************");
		ViewHelper.virementsRecManager.traitementInDB();
	}

	public boolean doTraitement(TypeProcess typeProcess, String repEntree,String repSortieOK, String repSortieKO, String repArchiveEntree, Integer aCopier, Integer Copier, Integer Doublons, Integer fichContenantDoublons,
			Integer valeursEnDouble, Integer valeursDeposees, List<String>listFileNamesWithDoublons, List<String> listFichierEtValEnDouble, List<String> listFichierSupprimePourDoublonsEntreFichiers, 
			Map<String, List<String>> mapFichierEtValEnDouble, List<String> listFichierContenantDoublonsEntreFichiers, Map<String, List<String>> mapFichiersEntreeEtContenus, Map<String, List<String>> mapFichiersDestEtContenus, boolean fichiersDestination, String fichiersTraite, String jasper, String reportFile ){

		try {

			File folderEntree = new File(repEntree);
			//Si le répertoire n'existe pas, il faut le créer
			if(!folderEntree.exists()){
				if(folderEntree.mkdirs()){
					System.out.println("*****Les répertoires parents sont crées avec succès!******");
				} else {
					System.out.println("******Echec de création des répertoires parents!*****");
				}
			}
			System.out.println("****folderEntree*****" + folderEntree);


			File folderDest = new File(repSortieOK);
			//Si le répertoire n'existe pas, il faut le créer
			if(!folderDest.exists()){
				if(folderDest.mkdirs()){
					System.out.println("*****Les répertoires parents sont crées avec succès!******");
				} else {
					System.out.println("******Echec de création des répertoires parents!*****");
				}
			}
			System.out.println("****folderDest*****" + folderDest);


			File folderDestKO = new File(repSortieKO);
			//Si le répertoire n'existe pas, il faut le créer
			if(!folderDestKO.exists()){
				if(folderDestKO.mkdirs()){
					System.out.println("*****Les répertoires parents sont crées avec succès!******");
				} else {
					System.out.println("******Echec de création des répertoires parents!*****");
				}
			}
			System.out.println("****folderDestKO*****" + folderDestKO);


			File folderArchiveEntree = new File(repArchiveEntree);
			//Si le répertoire n'existe pas, il faut le créer
			if(!folderArchiveEntree.exists()){
				if(folderArchiveEntree.mkdirs()){
					System.out.println("*****Les répertoires parents sont crées avec succès!******");
				} else {
					System.out.println("******Echec de création des répertoires parents!*****");
				}
			}
			System.out.println("****folderArchiveEntree*****" + folderArchiveEntree);


			TourCompensation tourCompensation = new TourCompensation();
			tourCompensation.setUtiTraitement(user.getLogin());
			tourCompensation.setNumeroTour(tour);
			tourCompensation.setDateTraitement(new Date());
			tourCompensation.setTypeProcess(typeProcess);
			ViewHelper.virementsRecManagerDAOLocal.save(tourCompensation);


			Date dateTourCompense = new SimpleDateFormat("dd/MM/yyyy").parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

			File[] listOfFiles = folderEntree.listFiles();
			if(listOfFiles==null || listOfFiles.length==0){
				System.out.println("Aucun fichier trouvé dans le répertoire d'entrée !!!");
				this.error=true;
				this.information=true;
				ExceptionHelper.showError("Aucun fichier trouvé dans le répertoire d'entrée !!!", this);

				List<TourCompensation>toursCompensation = ViewHelper.virementsRecManager.filterTourCompensation(dateTourCompense, user.getLogin(), tour, typeProcess); //new Date()
				if(toursCompensation!=null &&!toursCompensation.isEmpty()){
					tourCompensation = toursCompensation.get(0);
					ViewHelper.virementsRecManagerDAOLocal.delete(tourCompensation);
				}
				return false;
			}

			/**Liste des fichiers dans le répertoire d'entrée***/
			File[] listFiles = folderDest.listFiles();
			if(listFiles!=null && listFiles.length>0){
				fichiersDestination = true; 
			}

			if(listOfFiles!=null&&listOfFiles.length>0){

				dateTourCompense = new SimpleDateFormat("dd/MM/yyyy").parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
				List<TourCompensation>toursCompensation = ViewHelper.virementsRecManager.filterTourCompensation(dateTourCompense, user.getLogin(), tour, typeProcess);
				if(toursCompensation!=null &&!toursCompensation.isEmpty()){
					tourCompensation = toursCompensation.get(0);
				}

				if(typeProcess.equals(TypeProcess.RAPATRIEMENT_IMG_ALLER) || typeProcess.equals(TypeProcess.RAPATRIEMENT_IMG_RETOUR) ){

					typeTraitement = TypeTraitement.DOUBLONS_NOM_FICHIER;

					/**Parcours du répertoire d'entrée***/
					for (int i = 0; i < listOfFiles.length; i++) {
						if (listOfFiles[i].isFile()) {

							nbrFichACopierImg++;

							System.out.println("File " + listOfFiles[i].getName());

							String fichierEntree = repEntree + File.separator + listOfFiles[i].getName();

							String fichierDestOK = repSortieOK + File.separator + listOfFiles[i].getName();
							
							String fichierDestKO = repSortieKO + File.separator + listOfFiles[i].getName();

							String fichierArchiveEntree = repArchiveEntree + File.separator + listOfFiles[i].getName();

							System.out.println("****fichierEntree*****" + fichierEntree);

							System.out.println("****fichierDestOK*****" + fichierDestOK);
							System.out.println("****fichierDestKO*****" + fichierDestKO);
							System.out.println("****fichierArchiveEntree*****" + fichierArchiveEntree);

							Path pathEntree = Paths.get(fichierEntree);

							Path pathDestinationOK = Paths.get(fichierDestOK);
							
							Path pathDestinationKO = Paths.get(fichierDestKO);

							Path pathArchiveEntree = Paths.get(fichierArchiveEntree);

							if(traitementEtCopieFichiers(typeProcess, folderDest.listFiles(), listOfFiles[i].getName(), pathEntree, pathDestinationOK, pathDestinationKO, pathArchiveEntree, tourCompensation, fichiersDestination, aCopier)){
								if(fichiersTraite.isEmpty())fichiersTraite = listOfFiles[i].getName();
								else
									fichiersTraite = fichiersTraite + ";" + listOfFiles[i].getName();
							}

						}
					}

					/** Sauvegarder le traitement***/
					saveTraitement(typeProcess, typeTraitement, fichiersTraite, tourCompensation);


					/***Affichage Rapport de traitement + envoi mail****/
					HashMap<String, Object> param = new HashMap<String, Object>();

					List<RapatriementImagesAller> repports = new ArrayList<RapatriementImagesAller>();

					if(typeProcess.equals(TypeProcess.RAPATRIEMENT_IMG_ALLER)){

						param.put("uti", user.getLogin());
						param.put("nbrFichACopierImgAller", nbrFichACopierImg);
						param.put("nbrFichCopieImgAller", nbrFichCopieImg);
						param.put("nbrDoublonsImgAller", nbrDoublonsImg);

						title="Rapport traitement (copie) des fichiers images phase Aller, le " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()) + " à " + new SimpleDateFormat("HH:mm:ss").format(new Date());
						msg = "Bonjour, " + "\t" + "\n" + "\t\t" + "Veuillez trouver ci-dessous le rapport des fichiers images de la phase Aller copiés ce jour : " + "\n\n";

						msg = msg + "Nombre de fichiers à copier: " + nbrFichACopierImg + "\n\n";
						msg = msg + "Nombre de fichers copiés dans le répertoire de destination: " + nbrFichCopieImg + "\n\n";
						msg = msg + "Nombre de doublons détectés: " + nbrDoublonsImg + "\n\n";


						filesNames = new ArrayList<String>();
						filesPath = new ArrayList<String>();

						String fName = new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date());
						FileOutputStream fileOuputStream = new FileOutputStream(ViewHelper.getPiecesJointesDir() + "Rapport_Copie_Img_Aller_" + fName+".pdf");
						fileOuputStream.write(ViewHelper.virementsRecManager.getReportPDFBytes(ViewHelper.getReportsDir2().concat("rapportCopieImgAller.jasper"), param, repports));
						fileOuputStream.close();

						filesNames.add("Rapport_Copie_Img_Aller_" + fName+".pdf");
						filesPath.add(ViewHelper.getPiecesJointesDir() + "Rapport_Copie_Img_Aller_" + fName+".pdf");

						StatistiqueRapports stat = new StatistiqueRapports();
						stat.setRapport("Rapport_Copie_Img_Aller_" + fName+".pdf");
						stat.setTypeProcess(TypeProcess.RAPATRIEMENT_IMG_ALLER);
						ViewHelper.virementsRecManagerDAOLocal.save(stat);

					}else{  //RETOUR

						param.put("uti", user.getLogin());
						param.put("nbrFichACopierImgRetour", nbrFichACopierImg);
						param.put("nbrFichCopieImgRetour", nbrFichCopieImg);
						param.put("nbrDoublonsImgRetour", nbrDoublonsImg);


						title="Rapport traitement (copie) des fichiers images phase Retour, le " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()) + " à " + new SimpleDateFormat("HH:mm:ss").format(new Date());
						msg = "Bonjour, " + "\t" + "\n" + "\t\t" + "Veuillez trouver ci-dessous le rapport des fichiers images de la phase Retour copiés ce jour : " + "\n\n";

						msg = msg +"Nombre de fichiers à copier: " + nbrFichACopierImg + "\n\n";
						msg = msg +"Nombre de fichers copiés dans le répertoire de destination: " + nbrFichCopieImg + "\n\n";
						msg = msg +"Nombre de doublons détectés: " + nbrDoublonsImg + "\n\n";


						filesNames = new ArrayList<String>();
						filesPath = new ArrayList<String>();

						String fName = new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date());
						FileOutputStream fileOuputStream = new FileOutputStream(ViewHelper.getPiecesJointesDir() + "Rapport_Copie_Img_Retour_" + fName+".pdf");
						fileOuputStream.write(ViewHelper.virementsRecManager.getReportPDFBytes(ViewHelper.getReportsDir2().concat("rapportCopieImgRetour.jasper"), param, repports));
						fileOuputStream.close();

						filesNames.add("Rapport_Copie_Img_Retour_" + fName+".pdf");
						filesPath.add(ViewHelper.getPiecesJointesDir() + "Rapport_Copie_Img_Retour_" + fName+".pdf");

						StatistiqueRapports stat = new StatistiqueRapports();
						stat.setRapport("Rapport_Copie_Img_Retour_" + fName+".pdf");
						stat.setTypeProcess(TypeProcess.RAPATRIEMENT_IMG_RETOUR);
						ViewHelper.virementsRecManagerDAOLocal.save(stat);

					}


					/***Rapport***/
					reportViewer = new ReportViewer(repports,jasper, param, "afb.statistique.title", this,"/views/repport/" + reportFile);
					reportViewer.viewReport();


					/*********************Envoi mail rapport traitement******************/
					//SendMail.sendMail(filesNames, filesPath, mailsTo, mailsCC, mailsBCC, title, msg, ip, email, pass, port);
					try {
						List<ParamEmailAuto> parametres = ViewHelper.virementsRecManagerDAOLocal.filter(ParamEmailAuto.class, null, null, null, null, 0, -1);
						if(parametres!=null&&!parametres.isEmpty()){
							AfrilandSendMail.sendMail(filesNames, filesPath, mailsTo, mailsCC, title, msg, email, pass, email + "@afrilandfirstbank.com", "AFRILAND FIRST BANK", ip, String.valueOf(port));
						}
					} catch (Exception ex) {
						// TODO Auto-generated catch block
					}

					return true;
					/*******************************************/

				}else if(typeProcess.equals(TypeProcess.FICHIER_COMPTA_ALLER) || typeProcess.equals(TypeProcess.FICHIER_COMPTA_RETOUR)){

					/*********Nouveau traitement - Nouvelle Logique**************/

					initValues(); //initialisation des valeurs du tour concerné, pour le rapport

					String fileName;
					Fichier fichier;

					ContenuFichier contenu;
					//List<Fichier>listFichiers=new ArrayList<Fichier>();
					List<ContenuFichier>listContenus=new ArrayList<ContenuFichier>();
					List<String> fileLines = new ArrayList<String>();

					Path pathEntree;
					Path pathArchiveEntree;
					Path pathDestinationOK;
					Path pathDestinationKO;

					//Parcours du répertoire d'entrée pour charger le contenu en BD
					for (int i = 0; i < listOfFiles.length; i++) {

						if (listOfFiles[i].isFile()) {

							listContenus.clear();

							String fichierEntree = repEntree + File.separator + listOfFiles[i].getName();
							//String fichierArchiveEntree = repArchiveEntree + File.separator + listOfFiles[i].getName();

							pathEntree = Paths.get(fichierEntree);

							fileName = listOfFiles[i].getName().trim();

							//Création du fichier pour sauvegarde en BD
							fichier = new Fichier();
							fichier.setNomFichier(fileName);
							fichier.setDateCreation(new SimpleDateFormat("dd/MM/yyyy").parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date())));
							fichier.setHeureCreation(new SimpleDateFormat("HH:mm:ss").format(new Date()));
							fichier.setUtiTraitement(ViewHelper.getSessionUser().getLogin());
							fichier.setTourCompensation(tourCompensation);


							//Création du contenu du fichier pour sauvegarde en BD
							if(pathEntree!=null)
								fileLines = readAndScanFile(pathEntree);

							int lineNumber = 2;
							for(String line: fileLines){

								//On exclu la première ligne car elle contient les informations sur le fichier et non sur un virement précis
								if(!line.equals(fileLines.get(0).toString())){

									contenu = new ContenuFichier();
									contenu.setNumeroLigne(lineNumber);
									contenu.setFichier(fichier);
									contenu.setUtiTraitement(ViewHelper.getSessionUser().getLogin());
									contenu.setDateCreation(new SimpleDateFormat("dd/MM/yyyy").parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date())));
									contenu.setHeureCreation(new SimpleDateFormat("HH:mm:ss").format(new Date()));

									contenu.setSens(line.substring(0, 1));
									contenu.setCodeCentreCompensation(line.substring(1, 3));
									contenu.setCodePaysEmetteur(line.substring(3, 5));
									contenu.setDateGeneration(line.substring(5, 13));
									contenu.setHeureGeneration(line.substring(13, 19));
									contenu.setCodeValeur(line.substring(19, 21));
									contenu.setCodeParticipantRemettant(line.substring(21, 26));
									contenu.setDatePresentation(line.substring(26, 34));
									contenu.setDatePresentationAppliquee(line.substring(34, 42));
									contenu.setNumeroDeRemise(line.substring(42, 46));
									contenu.setCodeEnregistrement(line.substring(46, 48));
									contenu.setCodeDevise(line.substring(48, 51));
									contenu.setRang(line.substring(51, 53));

									String montantVirStr = line.substring(53, 68);
									double montantVir = 0.0;
									try{
										montantVir = Double.parseDouble(montantVirStr);
									}catch(Exception e){
										montantVir = 0.0;
									}
									contenu.setMontantVirement(montantVir);

									contenu.setNumeroVirement(line.substring(68, 75));
									contenu.setRIBDonneurOrdre(line.substring(75, 98));
									contenu.setNomEtPrenomDonneurOrdre(line.substring(98, 128));
									contenu.setCodeParticipantDestinataire(line.substring(128, 133));
									contenu.setCodePaysDestinataire(line.substring(133, 135));
									contenu.setRIBBeneficiaire(line.substring(135, 158));
									contenu.setNomEtPrenomBeneficiaire(line.substring(158, 188));
									contenu.setNumeroReference(line.substring(188, 208));
									contenu.setNombreEnregistrementsComp(line.substring(208, 210));
									contenu.setMotifOperation(line.substring(210, 255));
									contenu.setDateReglement(line.substring(255, 263));
									contenu.setMotifRejet(line.substring(263, 271));
									contenu.setZoneLibre(line.substring(271));

									///contenu.setIsTraite(Boolean.TRUE);
									contenu.setCleEnCours(constructLinesOfKeysFromContenu(contenu));

									listContenus.add(contenu);

									lineNumber++;
								}
							}

							fichier.setListContenus(listContenus);
							System.out.println("****************************PERSISTANCE DES DONNEES************************************************************");
							ViewHelper.virementsRecManagerDAOLocal.save(fichier);

						}
					}

					//tourCompensation.setListFichier(listFichiers);
					//ViewHelper.virementsRecManagerDAOLocal.update(tourCompensation);
					//listFichiers.clear();


					/******DEPLACEMENT DES FICHIERS DANS UN REPERTOIRE D'ARCHIVES**********************************/
					for (int i = 0; i < listOfFiles.length; i++) {

						if (listOfFiles[i].isFile()) {
							String fichierEntree = repEntree + File.separator + listOfFiles[i].getName();
							String fichierArchiveEntree = repArchiveEntree + File.separator + listOfFiles[i].getName();

							pathEntree = Paths.get(fichierEntree);
							pathArchiveEntree = Paths.get(fichierArchiveEntree);

							if(pathEntree!=null && pathArchiveEntree!=null){

								File file = new File(fichierArchiveEntree);

								//Si le répertoire n'existe pas, il faut le créer
								if(!file.exists()){
									System.out.println("****folderEntree*****" + folderEntree);
									Path p = Files.move(pathEntree, pathArchiveEntree, StandardCopyOption.REPLACE_EXISTING);
									System.out.println("***Files copied to ***" + p.toString());
								}else{//Le fichier existe déjà dans le rep. de dest., donc on le renomme pour le copier dans le rep. de dest. avec la date de la copie ajoutée comme suffix du fichier
									String nameFile = listOfFiles[i].getName()  + "_" + new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss").format(new Date());

									fichierArchiveEntree = repArchiveEntree + File.separator + nameFile;

									//file = new File(fichierEntree);
									//boolean succeeded = file.renameTo(file);

									//if(succeeded){
									pathArchiveEntree = Paths.get(fichierArchiveEntree);
									Path p = Files.move(pathEntree, pathArchiveEntree, StandardCopyOption.REPLACE_EXISTING);
									System.out.println("***Files copied to ***" + p.toString());
									//}else{
									//on ne fait rien
									System.out.println("***Rename operation failed! On ne fait rien ***");

									//}
								}

							}
						}
					}


					/***********************TRAITEMENT*******************************************************/
					traitementInDB();


					/***************DEPLACEMENT DES FICHIERS DANS LES REPERTOIRES DE DESTINATION OK ET KO*************************/
					List<String>fichiersOK = new ArrayList<String>();
					List<String>fichiersKO = new ArrayList<String>();
					List<String>listContenusPourMap = new ArrayList<String>();
					boolean ok;
					//List<Fichier> listFicCopier = new ArrayList<Fichier>();
					Date dateCreation = new SimpleDateFormat("dd/MM/yyyy").parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
					List<Fichier> listFic = ViewHelper.virementsRecManagerDAOLocal.filter(Fichier.class, null, RestrictionsContainer.getInstance().add(Restrictions.eq("dateCreation", dateCreation)), null, null, 0, -1);
					System.out.println("****dateCreation****" + dateCreation);
					if(listFic!=null && !listFic.isEmpty()){
						System.out.println("******In !listFic.isEmpty()*****");
						nbrFichATraiteComptaTotal = listFic.size();
						for(Fichier f: listFic){
							System.out.println(" f.getTourCompensation().getId()" + f.getTourCompensation().getId() + "tourCompensation.getId() " + tourCompensation.getId());
							//Si ce sont les fichiers d'un même tour de compensation
							if(!f.getIsTraite() && f.getTourCompensation().getId().equals(tourCompensation.getId())){ //Si le fichier n'est pas encore traité et fait partie du tour courrent de compensation
								nbrFichACopierCompta++;
								//f.setIsTraite(Boolean.TRUE);
								//ViewHelper.virementsRecManagerDAOLocal.update(f);
							}
						}
					}else{
						System.out.println("******In listFic.isEmpty()*****");
					}



					List<ContenuFichier> listContenusFic = ViewHelper.virementsRecManagerDAOLocal.filter(ContenuFichier.class, null, RestrictionsContainer.getInstance().add(Restrictions.eq("dateCreation", new SimpleDateFormat("dd/MM/yyyy").parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date())))), null, null, 0, -1);
					for(ContenuFichier c: listContenusFic){

						ok = true;

						TraitementTourCompensation traitementTourCompense = new TraitementTourCompensation();
						traitementTourCompense.setUtiTraitement(user.getLogin());
						//traitementTourCompense.setTypeTraitement(typeTraitement);
						traitementTourCompense.setDateTraitement(new Date());
						traitementTourCompense.setHeure(new SimpleDateFormat("HH:mm:ss").format(new Date()));
						traitementTourCompense.setFichiersTraite(c.getFichier().getNomFichier());
						traitementTourCompense.setValeurTraite(c.getCleEnCours());
						traitementTourCompense.setTourCompensation(tourCompensation);


						String fichierEntree = repEntree + File.separator + c.getFichier().getNomFichier();
						String fichierDestinationOK = repSortieOK + File.separator + c.getFichier().getNomFichier();
						String fichierDestinationKO = repSortieKO + File.separator + c.getFichier().getNomFichier();

						pathEntree = Paths.get(fichierEntree);
						pathDestinationOK = Paths.get(fichierDestinationOK);
						pathDestinationKO = Paths.get(fichierDestinationKO);


						if(c.getIsDoublonNomFic()){ //plusieurs fichiers avec le même nom

							if(!fichiersOK.contains(c.getFichier().getNomFichier()) && !fichiersKO.contains(c.getFichier().getNomFichier()) && !c.getIsTraite() && c.getFichier().getTourCompensation().getId().equals(tourCompensation.getId())){

								///On récupère la date et l'heure de création du fichier dans la BD; et on intègre le fichier le plus récent, les autres on rejette
								List<Fichier> fichiers = ViewHelper.virementsRecManagerDAOLocal.filter(Fichier.class, null, RestrictionsContainer.getInstance().add(Restrictions.eq("dateCreation", new SimpleDateFormat("dd/MM/yyyy").parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date())))).add(Restrictions.eq("nomFichier", c.getFichier().getNomFichier())), OrderContainer.getInstance().add(Order.desc("nomFichier")).add(Order.desc("heureCreation")).add(Order.desc("id")), null, 0, -1);
								if(fichiers!=null&&!fichiers.isEmpty()){

									//Ajout du fichiers dans la liste des fichiers à intégrer
									if(!fichiers.get(0).getIsTraite()){
										fichiersOK.add(fichiers.get(0).getNomFichier());

										//On enlève le fichier que l'on vient d'ajouter, de la liste des fichiers
										fichiers.remove(fichiers.get(0));
									}

									//Ajout dans la liste des doublons noms fichiers
									listFileNamesWithDoublons.add(fichiers.get(0).getNomFichier());

									//Rejet des autres fichiers
									for(Fichier ff:fichiers){
										if(!fichiersKO.contains(ff.getNomFichier())){

											//Ajout du fichier dans les liste des fichiers à rejeté
											if(!ff.getIsTraite() && ff.getTourCompensation().getId().equals(tourCompensation.getId())){
												fichiersKO.add(ff.getNomFichier());
											}

											System.out.println("***Doublons Nom Fichier trouvé******" + ff.getNomFichier());

											nbrDoublonsCompta++;

											ok = false;

											//Ajout du fichier rejeté dans la liste des doublons noms fichiers
											listFileNamesWithDoublons.add(ff.getNomFichier());

											traitementTourCompense.setSortTraitement(SortTraitement.ECHEC);

											ViewHelper.virementsRecManagerDAOLocal.save(traitementTourCompense);

										}
									}
								}
							}
						}

						if(c.getIsDoublonDansFic()){  //plusieurs lignes dans le même fichier

							//Si le fichier était déjà considéré comme bon à cause du succès lors du premier traitement de doublons noms fichier, on l'enlève
							if(fichiersOK.contains(c.getFichier().getNomFichier()) && !c.getIsTraite() && c.getFichier().getTourCompensation().getId().equals(tourCompensation.getId())){
								fichiersOK.remove(c.getFichier().getNomFichier());
							}

							//On n'intègre pas le fichier; si le fichier n'est pas encore dans la liste des fichiers à rejeter, alors on l'ajoute
							if(!fichiersKO.contains(c.getFichier().getNomFichier())){

								if(!c.getIsTraite() && c.getFichier().getTourCompensation().getId().equals(tourCompensation.getId())){
									fichiersKO.add(c.getFichier().getNomFichier());
								}

								nbrFichContenantDoublonsCompta++;

								nbrValeursEnDoubleCompta++;

								ok = false;

								if(mapFichierEtContenu.containsKey(c.getFichier())){
									listContenusPourMap = mapFichierEtContenu.get(c.getFichier());
								}
								listContenusPourMap.add(constructLinesOfKeysFromContenu(c));

								//mapFichierEtContenu.put(c.getFichier(), constructLinesOfKeysFromContenu(c));
								mapFichierEtContenu.put(c.getFichier(), listContenusPourMap);

								System.out.println("***Doublons dans fichier trouvé******" + c.getFichier().getNomFichier());

								traitementTourCompense.setSortTraitement(SortTraitement.ECHEC);

								ViewHelper.virementsRecManagerDAOLocal.save(traitementTourCompense);
							}
						}

						if(c.getIsDoublonEntreFic()){

							///On récupère tous les fichiers contenant cette ligne (un filter sur les attribut de la clé where les idFic sont différents)
							///On récupère le fichier avec la date et l'heure la plus récente et on intègre; les autres on rejette
							List<ContenuFichier> contents = ViewHelper.virementsRecManagerDAOLocal.findDoublonsEntreFic(c);

							if(contents!=null&&!contents.isEmpty()){
								//Intégration du fichier le plus récent
								if(!fichiersOK.contains(c.getFichier().getNomFichier()) && !fichiersKO.contains(c.getFichier().getNomFichier()) && !c.getIsTraite() && c.getFichier().getTourCompensation().getId().equals(tourCompensation.getId())){
									fichiersOK.add(contents.get(0).getFichier().getNomFichier());
									contents.remove(contents.get(0));
								}

								///contents.remove(contents.get(0));

								//Rejet des autres fichiers
								for(ContenuFichier cc:contents){
									if(!fichiersKO.contains(cc.getFichier().getNomFichier())){
										if(!cc.getFichier().getIsTraite() && cc.getFichier().getTourCompensation().getId().equals(tourCompensation.getId())){
											fichiersKO.add(cc.getFichier().getNomFichier());
										}
										ok = false;
										System.out.println("***Doublons entre fichiers trouvé******" + cc.getFichier().getNomFichier());
										nbrFichDoublonsEntreFichiers++;
										listFichierSupprimePourDoublonsEntreFichiers.add(cc.getFichier().getNomFichier());
										traitementTourCompense.setSortTraitement(SortTraitement.ECHEC);
										ViewHelper.virementsRecManagerDAOLocal.save(traitementTourCompense);
									}
								}
							}
						}

						if(ok){

							System.out.println("*****c.getFichier().getTourCompensation()*****" + c.getFichier().getTourCompensation().getId());
							System.out.println("*****tourCompensation*******" + tourCompensation.getId());
							System.out.println("******c.getIsTraite()*******************" + c.getIsTraite());
							if(!c.getIsTraite() && c.getFichier().getTourCompensation().getId().equals(tourCompensation.getId())){
								traitementTourCompense.setSortTraitement(SortTraitement.SUCCES);
								ViewHelper.virementsRecManagerDAOLocal.save(traitementTourCompense);
								nbrValeursDeposeesCompta++;
								System.out.println("***Valeurs sures à copier******" + constructLinesOfKeysFromContenu(c));
								if(!fichiersOK.contains(c.getFichier().getNomFichier())){
									fichiersOK.add(c.getFichier().getNomFichier());
									//listFicCopier.add(c.getFichier());
									System.out.println("***Fichier sur à copier******" + c.getFichier().getNomFichier());
									nbrFichCopieCompta++;
								}
							}
						}

						c.setIsTraite(Boolean.TRUE);
						ViewHelper.virementsRecManagerDAOLocal.update(c);

						Fichier fic = ViewHelper.virementsRecManagerDAOLocal.findByPrimaryKey(Fichier.class, c.getFichier().getId(), null);
						fic.setIsTraite(Boolean.TRUE);
						ViewHelper.virementsRecManagerDAOLocal.update(fic);

					}

					System.out.println("***fichiersOK.size()*****" + fichiersOK.size());
					System.out.println("***fichiersKO.size()*****" + fichiersKO.size());

					for(String fic: fichiersOK){

						System.out.println("***fichiersOK.size()*****+++" + fic);

						//String fichierEntree = repEntree + File.separator + fic;
						String fichierArchiveEntree = repArchiveEntree + File.separator + fic;
						String fichierDestinationOK = repSortieOK + File.separator + fic;
						//String fichierDestinationKO = repSortieKO + File.separator + fic;

						//pathEntree = Paths.get(fichierEntree);
						pathArchiveEntree = Paths.get(fichierArchiveEntree);
						pathDestinationOK = Paths.get(fichierDestinationOK);
						//pathDestinationKO = Paths.get(fichierDestinationKO);

						Path p = Files.copy(pathArchiveEntree, pathDestinationOK, StandardCopyOption.REPLACE_EXISTING);
						System.out.println("*******REP OK*******Files copied to ***" + p.toString());
					}

					for(String fic: fichiersKO){

						System.out.println("***fichiersKO.size()*****---" + fic);

						//String fichierEntree = repEntree + File.separator + fic;
						String fichierArchiveEntree = repArchiveEntree + File.separator + fic;
						//String fichierDestinationOK = repSortieOK + File.separator + fic;
						String fichierDestinationKO = repSortieKO + File.separator + fic;

						//pathEntree = Paths.get(fichierEntree);
						pathArchiveEntree = Paths.get(fichierArchiveEntree);
						//pathDestinationOK = Paths.get(fichierDestinationOK);
						pathDestinationKO = Paths.get(fichierDestinationKO);

						Path p = Files.copy(pathArchiveEntree, pathDestinationKO, StandardCopyOption.REPLACE_EXISTING);
						System.out.println("*********REP KO********Files copied to ***" + p.toString());
					}

					fichiersOK.clear();
					fichiersKO.clear();


					/******int j = 0;

					nomFichierDoublon = false;

					/**Parcours du répertoire d'entrée pour collecter les contenus*
					List<String> fileLines1 = new ArrayList<String>();
					for(int k=0; k<listOfFiles.length;k++){
						fileLines1 = readAndScanFile(Paths.get(repEntree + File.separator + listOfFiles[k].getName()));
						if(!fileLines1.isEmpty())
							mapFichiersEntreeEtContenus.put(listOfFiles[k].getName(), fileLines1);
					}

					/**Parcours du répertoire de destination pour collecter les contenus*
					List<String> fileLines = new ArrayList<String>();
					for(int k=0; k<listFiles.length;k++){
						fileLines = readAndScanFile(Paths.get(repSortie + File.separator + listFiles[k].getName()));
						if(!fileLines.isEmpty())
							mapFichiersDestEtContenus.put(listFiles[k].getName(), fileLines);
					}

					while (j<3){ // Nous avons trois traitements à effectuer

						fichiersTraite = "";

						/**Parcours du répertoire d'entrée**
						for (int i = 0; i < listOfFiles.length; i++) {


							if (listOfFiles[i].isFile()) {

								if(j==2) //pour ne pas dupliquer ce chiffre lors du deuxième et troisième traitement
									nbrFichACopierCompta++;

								System.out.println("File " + listOfFiles[i].getName());

								String fichierEntree = repEntree + File.separator + listOfFiles[i].getName();
								String fichierDest = repSortie + File.separator + listOfFiles[i].getName();
								String fichierArchiveEntree = repArchiveEntree + File.separator + listOfFiles[i].getName();

								System.out.println("****fichierEntree*****" + fichierEntree);
								System.out.println("****fichierDest*****" + fichierDest);


								Path pathEntree = Paths.get(fichierEntree);
								Path pathDestination = Paths.get(fichierDest);
								Path pathArchiveEntree = Paths.get(fichierArchiveEntree);


								if(j==0){

									/**Ne pas oublier de sauvegarder les nbr des doublons et autre en BD***

									typeTraitement = TypeTraitement.DOUBLONS_NOM_FICHIER;
									if(traitementEtCopieFichiersCompta(folderDest.listFiles(), listOfFiles[i].getName(), pathEntree, pathDestination, pathArchiveEntree, tourCompensation, typeTraitement, parametragesCaracteresSpeciauxs,fichiersDestination,mapFichiersEntreeEtContenus,mapFichiersDestEtContenus, aCopier, typeProcess)){
										if(fichiersTraite.isEmpty())fichiersTraite = listOfFiles[i].getName();
										else
											fichiersTraite = fichiersTraite + ";" + listOfFiles[i].getName();
									}

								}else if(j==1){

									typeTraitement = TypeTraitement.DOUBLONS_ENTRE_FICHIER;
									if(traitementEtCopieFichiersCompta(folderDest.listFiles(), listOfFiles[i].getName(), pathEntree, pathDestination, pathArchiveEntree, tourCompensation, typeTraitement, parametragesCaracteresSpeciauxs, fichiersDestination,mapFichiersEntreeEtContenus,mapFichiersDestEtContenus, aCopier, typeProcess)){
										if(fichiersTraite.isEmpty())fichiersTraite = listOfFiles[i].getName();
										else
											fichiersTraite = fichiersTraite + ";" + listOfFiles[i].getName();
									}

								}
								else if(j==2){
									typeTraitement = TypeTraitement.DOUBLONS_DANS_FICHIER;
									if(traitementEtCopieFichiersCompta(folderDest.listFiles(), listOfFiles[i].getName(), pathEntree, pathDestination, pathArchiveEntree, tourCompensation, typeTraitement, parametragesCaracteresSpeciauxs, fichiersDestination,mapFichiersEntreeEtContenus,mapFichiersDestEtContenus, aCopier, typeProcess)){
										if(fichiersTraite.isEmpty())fichiersTraite = listOfFiles[i].getName();
										else
											fichiersTraite = fichiersTraite + ";" + listOfFiles[i].getName();

										List<String> listFVD = new ArrayList<>();
										if(this.listFichierEtValEnDouble.size()>1){
											listFVD = new ArrayList<>(listFichierEtValEnDouble);
											mapFichierEtValEnDoubleComptaAllerTraite1.put(listFVD.get(0), listFVD.subList(1, listFVD.size()));
										}
										System.out.println("----mapFichierEtValEnDoubleComptaAllerTraite1.size()----" + mapFichierEtValEnDoubleComptaAllerTraite1.size());
									}

								}


							}
						}

						j++;
					}*/

					/** Sauvegarder le traitement**/
					saveTraitement(typeProcess, typeTraitement, fichiersTraite, tourCompensation);

					/************************Tableau 1 du rapport*********************************/
					//List<String> listString = new ArrayList<String>();
					for(Fichier key1:mapFichierEtContenu.keySet()){
						/**for(Fichier key2:mapFichierEtContenu.keySet()){
							if(key2.getId()==key1.getId()){
								if(!listString.contains(mapFichierEtContenu.get(key2)))
									listString.add(mapFichierEtContenu.get(key2));
							}
						}*/
						mapFichierEtValEnDoubleComptaAllerTraite1.put(key1.getNomFichier(), mapFichierEtContenu.get(key1));
					}

					List<ValidateDoublonsInFichier> subs = new ArrayList<ValidateDoublonsInFichier>();
					ValidateDoublonsInFichier sub;
					String color="";
					String previousColor="";
					List<String> mapString = new ArrayList<String>(); 
					for(String key:mapFichierEtValEnDoubleComptaAllerTraite1.keySet()){

						sub = new ValidateDoublonsInFichier();
						sub.setFichier(key);

						String doublons="";
						mapString.clear();
						mapString.addAll(mapFichierEtValEnDoubleComptaAllerTraite1.get(key));
						for(String s: mapString){
							if(!doublons.equals(""))
								doublons = doublons + ";" + s;
							else
								doublons = s;
						}

						if(!previousColor.equals("blue") || previousColor.isEmpty())
							color = "blue";
						else
							color = "green";

						sub.setColor(color);
						sub.setDoublons(doublons);
						sub.setDateTraitement(new Date());
						sub.setTypeProcess(typeProcess);

						subs.add(sub);

						previousColor = color;
					}


					for(ValidateDoublonsInFichier v: subs){
						ViewHelper.virementsRecManagerDAOLocal.save(v);
					}


					/***********************Tableau 2 du rapport***********************************/

					FichiersSupprimeACauseDeValeursEnDoublonsDansAutreFichier  f = new FichiersSupprimeACauseDeValeursEnDoublonsDansAutreFichier();
					List<FichiersSupprimeACauseDeValeursEnDoublonsDansAutreFichier> fList = new ArrayList<FichiersSupprimeACauseDeValeursEnDoublonsDansAutreFichier>();
					for(int i=0; i<this.listFichierSupprimePourDoublonsEntreFichiers.size();i++){
						f = new FichiersSupprimeACauseDeValeursEnDoublonsDansAutreFichier();
						/**if(i%2 == 0){
							f.setFichierSupprime(this.listFichierSupprimePourDoublonsEntreFichiers.get(i));
							if(i<this.listFichierSupprimePourDoublonsEntreFichiers.size()-1)
								f.setFichierConserve(this.listFichierSupprimePourDoublonsEntreFichiers.get(i+1));

						}*/
						f.setFichierSupprime(this.listFichierSupprimePourDoublonsEntreFichiers.get(i));

						if(f.getFichierSupprime()!=null && !f.getFichierSupprime().isEmpty())fList.add(f);
					}



					/***********************Tableau 3 du rapport****************************************/
					FichiersSupprimeACauseDeDoublonsNomsFichiers fSDNF = new FichiersSupprimeACauseDeDoublonsNomsFichiers();
					List<FichiersSupprimeACauseDeDoublonsNomsFichiers> listFSDNF = new ArrayList<FichiersSupprimeACauseDeDoublonsNomsFichiers>();
					for(String s: this.listFileNamesWithDoublons){
						fSDNF = new FichiersSupprimeACauseDeDoublonsNomsFichiers();
						fSDNF.setFichier(s);
						listFSDNF.add(fSDNF);
					}


					List<ValidateDoublonsInFichier> repports = new ArrayList<ValidateDoublonsInFichier>();

					ValidateDoublonsInFichier subReportDoublonsDansFichier = new ValidateDoublonsInFichier();
					subReportDoublonsDansFichier.setSubReportDoublonsDansFichiers(subs);
					subReportDoublonsDansFichier.setFichiersSupprimeACauseDeValeursEnDoublonsDansAutreFichiers(fList);
					subReportDoublonsDansFichier.setFichiersSupprimeACauseDeDoublonsNomsFichiers(listFSDNF);

					repports.add(subReportDoublonsDansFichier);


					/***Affichage Rapport de traitement + envoi mail***/
					HashMap<String, Object> param = new HashMap<String, Object>();


					if(!subs.isEmpty()){
						param.put("libelleTabAnnexe", "Liste des doublons apparus en terme de contenus dans un même fichier:");
					}
					if(!fList.isEmpty()){
						param.put("libelleSupprime",  "Liste des fichiers dont leur contenu est contenus dans un autre fichier:");
					}
					if(!listFSDNF.isEmpty()){
						param.put("libelleDoubNomsFic", "Liste des fichiers qui ont des doublons en termes de noms avec d'autres fichiers:");
					}

					if(typeProcess.equals(TypeProcess.FICHIER_COMPTA_ALLER)){

						nbrFichDoublonsEntreFichiers = listFichierANePasCopierPourDoublonsEntreFichiers.size();

						param.put("uti", user.getLogin());
						param.put("nbrFichACopierComptaAller", nbrFichACopierCompta);      
						param.put("nbrFichCopieComptaAller", nbrFichCopieCompta);
						param.put("nbrDoublonsComptaAller", nbrDoublonsCompta);
						param.put("nbrFichContenantDoublonsComptaAller", nbrFichContenantDoublonsCompta);
						param.put("nbrValeursDeposeesComptaAller", nbrValeursDeposeesCompta);
						param.put("nbrValeursEnDoubleComptaAller", nbrValeursEnDoubleCompta);
						param.put("nbrFichDoublonsEntreFichiers", nbrFichDoublonsEntreFichiers);

						title="Rapport traitement (copie) des fichiers de comptabilisation phase Aller, le " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()) + " à " + new SimpleDateFormat("HH:mm:ss").format(new Date());
						msg = "Bonjour, " + "\t" + "\n" + "\t\t" + "Veuillez trouver ci-dessous le rapport des fichiers de comptabilisation de la phase Aller copiés ce jour : " + "\n\n";

						msg = msg + "Nombre de fichiers à copier: " + nbrFichACopierCompta + "\n\n";
						msg = msg + "Nombre de fichers copiés dans le répertoire de destination: " + nbrFichCopieCompta + "\n\n";
						msg = msg + "\t" + "Types de Doublons:" + "\n\n";
						msg = msg + "\t\t" + "Cas 1:";
						msg = msg + "Nombre de fichiers en double détectés " + nbrDoublonsCompta +"\n\n";
						msg = msg + "\t\t" + "Cas 2:";
						msg = msg + "Nombre de fichiers avec contenu inclus dans d'autres fichiers " + nbrFichDoublonsEntreFichiers +"\n\n";
						msg = msg + "\t\t" + "Cas 3:";
						msg = msg + "Nombre de fichiers contenant des doublons: " + nbrFichContenantDoublonsCompta + "\n\n";
						msg = msg + "Nombre de valeurs en double détectés " + nbrValeursEnDoubleCompta + "\n\n";
						msg = msg + "Nombre de valeurs déposées " + nbrValeursDeposeesCompta + "\n\n";


						filesNames = new ArrayList<String>();
						filesPath = new ArrayList<String>();

						String fName = new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date());
						FileOutputStream fileOuputStream = new FileOutputStream(ViewHelper.getPiecesJointesDir() + "Rapport_Copie_Fichier_Compta_Aller_" + fName+".pdf");
						fileOuputStream.write(ViewHelper.virementsRecManager.getReportPDFBytes(ViewHelper.getReportsDir2().concat("rapportCopieComptaAller.jasper"), param, repports));
						fileOuputStream.close();

						filesNames.add("Rapport_Copie_Fichier_Compta_Aller_" + fName+".pdf");
						filesPath.add(ViewHelper.getPiecesJointesDir() + "Rapport_Copie_Fichier_Compta_Aller_" + fName+".pdf");

						StatistiqueRapports stat = new StatistiqueRapports();
						stat.setRapport("Rapport_Copie_Fichier_Compta_Aller_" + fName+".pdf");
						stat.setTypeProcess(TypeProcess.FICHIER_COMPTA_ALLER);
						ViewHelper.virementsRecManagerDAOLocal.save(stat);

					}else{  //RETOUR

						nbrFichDoublonsEntreFichiers = listFichierANePasCopierPourDoublonsEntreFichiers.size();

						param.put("uti", user.getLogin());
						param.put("nbrFichACopierComptaRetour", nbrFichACopierCompta);      
						param.put("nbrFichCopieComptaRetour", nbrFichCopieCompta);
						param.put("nbrDoublonsComptaRetour", nbrDoublonsCompta);
						param.put("nbrFichContenantDoublonsComptaRetour", nbrFichContenantDoublonsCompta);
						param.put("nbrValeursDeposeesComptaRetour", nbrValeursDeposeesCompta);
						param.put("nbrValeursEnDoubleComptaRetour", nbrValeursEnDoubleCompta);
						param.put("nbrFichDoublonsEntreFichiers", nbrFichDoublonsEntreFichiers );


						title="Rapport traitement (copie) des fichiers de comptabilisation phase Retour, le " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()) + " à " + new SimpleDateFormat("HH:mm:ss").format(new Date());
						msg = "Bonjour, " + "\t" + "\n" + "\t\t" + "Veuillez trouver ci-dessous le rapport des fichiers de comptabilisation de la phase Retour copiés ce jour : " + "\n\n";

						msg = msg + "Nombre de fichiers à copier: " + nbrFichACopierCompta + "\n\n";
						msg = msg + "Nombre de fichers copiés dans le répertoire de destination: " + nbrFichCopieCompta + "\n\n";
						msg = msg + "\t" + "Types de Doublons:" + "\n\n";
						msg = msg + "\t\t" + "Cas 1:";
						msg = msg + "Nombre de fichiers en double détectés " + nbrDoublonsCompta +"\n\n";
						msg = msg + "\t\t" + "Cas 2:";
						msg = msg + "Nombre de fichiers avec contenu inclus dans d'autres fichiers " + nbrFichDoublonsEntreFichiers +"\n\n";
						msg = msg + "\t\t" + "Cas 3:";
						msg = msg + "Nombre de fichiers contenant des doublons: " + nbrFichContenantDoublonsCompta + "\n\n";
						msg = msg + "Nombre de valeurs en double détectés " + nbrValeursEnDoubleCompta + "\n\n";
						msg = msg + "Nombre de valeurs déposées " + nbrValeursDeposeesCompta + "\n\n";

						filesNames = new ArrayList<String>();
						filesPath = new ArrayList<String>();

						String fName = new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date());
						FileOutputStream fileOuputStream = new FileOutputStream(ViewHelper.getPiecesJointesDir() + "Rapport_Copie_Fichier_Compta_Retour_" + fName+".pdf");
						fileOuputStream.write(ViewHelper.virementsRecManager.getReportPDFBytes(ViewHelper.getReportsDir2().concat("rapportCopieComptaRetour.jasper"), param, repports));
						fileOuputStream.close();

						filesNames.add("Rapport_Copie_Fichier_Compta_Retour_" + fName+".pdf");
						filesPath.add(ViewHelper.getPiecesJointesDir() + "Rapport_Copie_Fichier_Compta_Retour_" + fName+".pdf");

						StatistiqueRapports stat = new StatistiqueRapports();
						stat.setRapport("Rapport_Copie_Fichier_Compta_Retour_" + fName+".pdf");
						stat.setTypeProcess(TypeProcess.FICHIER_COMPTA_RETOUR);
						ViewHelper.virementsRecManagerDAOLocal.save(stat);

					}


					/***Rapport*/
					reportViewer = new ReportViewer(repports,jasper, param, "afb.statistique.title", this,"/views/repport/" + reportFile);
					reportViewer.viewReport();


					/*********************Envoi mail rapport traitement****************/
					//SendMail.sendMail(filesNames, filesPath, mailsTo, mailsCC, mailsBCC, title, msg, ip, email, pass, port);
					try {
						List<ParamEmailAuto> parametres = ViewHelper.virementsRecManagerDAOLocal.filter(ParamEmailAuto.class, null, null, null, null, 0, -1);
						if(parametres!=null&&!parametres.isEmpty()){
							AfrilandSendMail.sendMail(filesNames, filesPath, mailsTo, mailsCC, title, msg, email, pass, email + "@afrilandfirstbank.com", "AFRILAND FIRST BANK", ip, String.valueOf(port));
						}
					} catch (Exception ex) {
						// TODO Auto-generated catch block
					}

				}

			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}


	public String constructLinesOfKeysFromContenu(ContenuFichier contenu){

		//List<String> listKeys = new ArrayList<String>();

		String cle = "";

		List<CaracteristiquesVir> caracteristiquesDoublons =  ViewHelper.virementsRecManagerDAOLocal.filter(CaracteristiquesVir.class, null, RestrictionsContainer.getInstance().add(Restrictions.eq("valide", Boolean.TRUE)), null, null, 0, -1);
		if(caracteristiquesDoublons!=null && !caracteristiquesDoublons.isEmpty()){

			for(CaracteristiquesVir c: caracteristiquesDoublons){

				if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.Evenement)){

					if(cle.equals(""))
						cle = contenu.getNumeroVirement();
					else
						cle = cle + "-" + contenu.getNumeroVirement();
				}
				if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.montant)){

					if(cle.equals(""))
						cle = String.valueOf(contenu.getMontantVirement());
					else
						cle = cle + "-" + String.valueOf(contenu.getMontantVirement());
				}
				if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.ncp)){
					if(cle.equals(""))
						cle = contenu.getRIBBeneficiaire();
					else
						cle = cle + "-" + contenu.getRIBBeneficiaire(); //substring(135, 158).substring(11,22)
				}
				if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.ncp_donneur_ordre)){
					if(cle.equals(""))
						cle =  contenu.getRIBDonneurOrdre();
					else
						cle = cle + "-" + contenu.getRIBDonneurOrdre(); //substring(75, 98).substring(11,22)
				}
				if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.nom_benef)){
					if(cle.equals(""))
						cle = contenu.getNomEtPrenomBeneficiaire();
					else
						cle = cle + "-" + contenu.getNomEtPrenomBeneficiaire();
				}
				if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.nom_donneur_ordre)){
					if(cle.equals(""))
						cle = contenu.getNomEtPrenomDonneurOrdre();
					else
						cle = cle + "-" + contenu.getNomEtPrenomDonneurOrdre();
				}
			}

			//listKeys.add(cle);

		}
		return cle;
	}



	public void traitement(){

		try{

			boolean traitementOK = false;

			/**************************************Vérification de la journée en cours************************************************/
			ParamCompensateurCentrale paramCompensateurCentrale = ViewHelper.virementsRecManager.filterLastParamCompensateurCentrale();
			if(paramCompensateurCentrale!=null){

				if(!paramCompensateurCentrale.getDateJourneeEnCours().equals(new SimpleDateFormat("dd/MM/yyyy").parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date())))){

					System.out.println("Cette journée n'est pas encore ouverte !!! Contactez le Compensateur Centrale");
					this.error=true;
					this.information=true;
					ExceptionHelper.showError("Cette journée n'est pas encore ouverte !!! Contactez le Compensateur Centrale", this);
					return;

				}else if(paramCompensateurCentrale.getDateJourneeEnCours().equals(new SimpleDateFormat("dd/MM/yyyy").parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date())))){

					if(paramCompensateurCentrale.getStatutJourneeEnCours().equals(StatutJournee.FERMETURE)){
						System.out.println("Cette journée est déjà fermée !!! Contactez le Compensateur Centrale");
						this.error=true;
						this.information=true;
						ExceptionHelper.showError("Cette journée est déjà fermée !!! Contactez le Compensateur Centrale", this);
						return;
					}
				}
			}else if(paramCompensateurCentrale==null){

				System.out.println("Le paramétrage de la compensation n'est pas encore effectué !!! Contactez le Compensateur Centrale");
				this.error=true;
				this.information=true;
				ExceptionHelper.showError("Le paramétrage de la compensation n'est pas encore effectué !!! Contactez le Compensateur Centrale", this);

				return;

			}

			/*******************On s'assure d'abord que le numéro du tour entré à l'interface n'a pas déjà été traité**************************************/
			List<TourCompensation> listTourCompense = ViewHelper.virementsRecManager.filterTourCompensation(new Date(), user.getLogin(), tour, typeProcess);
			if(listTourCompense!=null && !listTourCompense.isEmpty()){

				System.out.println("****Ce tour de compensation " + tour + " a déjà été effectué pour ce type de process: " + typeProcess);

				this.error=true;
				this.information = true;
				ExceptionHelper.showError("Ce tour de compensation " + tour + " a déjà été effectué pour ce type de process: " + typeProcess , this);

				return;
			}

			if(typeProcess.equals(TypeProcess.RAPATRIEMENT_IMG_ALLER)){

				String fichiersTraite = "";
				nbrFichACopierImg = 0;
				nbrFichCopieImg = 0;
				nbrDoublonsImg = 0;

				boolean fichiersDestination = false;

				/*******Récupération des différents répertoires*********/
				String repEntreeImageAller = parametragesGenTeleCompense.getRepertoireEntreeCollecteImageAller();
				String repDestinationImageAller = parametragesGenTeleCompense.getRepertoireDestinationCollecteImageAller();
				String repArchiveEntreeImageAller = parametragesGenTeleCompense.getRepertoireArchiveEntreeCollecteImageAller();
				String repDestinationImageAllerKO = parametragesGenTeleCompense.getRepertoireDestinationCollecteImageAllerKO();


				/*********Exécution du Traitement********/
				traitementOK = doTraitement(typeProcess, repEntreeImageAller, repDestinationImageAller, repDestinationImageAllerKO, repArchiveEntreeImageAller, nbrFichACopierImg, nbrFichCopieImg, nbrDoublonsImg, 0, 0, 0, listFileNamesWithDoublons, listFichierEtValEnDouble, listFichierSupprimePourDoublonsEntreFichiers, null, listFichierContenantDoublonsEntreFichiers, null,null, fichiersDestination,fichiersTraite, "rapportCopieImgAller.jasper", "ReportCopieImgAller.xhtml" );

			}else if(typeProcess.equals(TypeProcess.FICHIER_COMPTA_ALLER)){

				String fichiersTraite = "";

				listFileNamesWithDoublons.clear();

				listFichierEtValEnDouble.clear(); 

				listFichierSupprimePourDoublonsEntreFichiers.clear();

				mapFichierEtValEnDoubleComptaAllerTraite1.clear();

				listFichierContenantDoublonsEntreFichiers.clear();

				listFichierACopierPourDoublonsEntreFichiers.clear();

				listFichierANePasCopierPourDoublonsEntreFichiers.clear();

				mapFichiersContenuDansFichiers.clear();

				Map<String, List<String>> mapFichiersEntreeEtContenus = new HashMap<>();

				Map<String, List<String>> mapFichiersDestEtContenus = new HashMap<>();


				nbrFichACopierCompta = 0;
				nbrFichCopieCompta = 0;
				nbrDoublonsCompta =0;
				nbrFichContenantDoublonsCompta=0;
				nbrValeursEnDoubleCompta=0;
				nbrValeursDeposeesCompta=0;
				nbrFichDoublonsEntreFichiers=0;

				boolean fichiersDestination = false;


				/*******Récupération des différents répertoires*********/
				String repEntreeFichCompteAller = parametragesGenTeleCompense.getRepertoireEntreeFichierComptabilisationAller();
				String repDestinationFicheComptaAller = parametragesGenTeleCompense.getRepertoireDestinationFichierComptabilisationAller();
				String repArchiveEntreeFicheComptaAller = parametragesGenTeleCompense.getRepertoireArchiveEntreeFichierComptabilisationAller();
				String repDestinationFicheComptaAllerKO = parametragesGenTeleCompense.getRepertoireDestinationFichierComptabilisationAllerKO();

				/*********Exécution du Traitement********/
				traitementOK = doTraitement(typeProcess, repEntreeFichCompteAller, repDestinationFicheComptaAller, repDestinationFicheComptaAllerKO, repArchiveEntreeFicheComptaAller, nbrFichACopierCompta, nbrFichCopieCompta, nbrDoublonsCompta, nbrFichContenantDoublonsCompta, nbrValeursEnDoubleCompta, nbrValeursDeposeesCompta, listFileNamesWithDoublons, listFichierEtValEnDouble, listFichierSupprimePourDoublonsEntreFichiers, mapFichierEtValEnDoubleComptaAllerTraite1, listFichierContenantDoublonsEntreFichiers,  mapFichiersEntreeEtContenus,mapFichiersDestEtContenus, fichiersDestination,fichiersTraite, "rapportCopieComptaAller.jasper", "ReportCopieComptaAller.xhtml" );
			}

			else if(typeProcess.equals(TypeProcess.RAPATRIEMENT_IMG_RETOUR)){

				//copie du rep entrée retour vers rep destination retour, ensuite archivage du rep destination retour vers rep archivage retour

				String fichiersTraite = "";
				nbrFichACopierImg = 0;
				nbrFichCopieImg = 0;
				nbrDoublonsImg = 0;

				boolean fichiersDestination = false;

				/**Récupération des différents répertoires**/
				String repEntreeImageRetour = parametragesGenTeleCompense.getRepertoireEntreeCollecteImageRetour();
				String repDestinationImageRetour = parametragesGenTeleCompense.getRepertoireDestinationCollecteImageRetour();
				String repArchiveEntreeImageRetour = parametragesGenTeleCompense.getRepertoireArchiveEntreeCollecteImageRetour();
				String repDestinationImageRetourKO = parametragesGenTeleCompense.getRepertoireDestinationCollecteImageRetourKO();

				/*********Exécution du Traitement********/
				traitementOK = doTraitement(typeProcess, repEntreeImageRetour, repDestinationImageRetour, repDestinationImageRetourKO, repArchiveEntreeImageRetour, nbrFichACopierImg, nbrFichCopieImg, nbrDoublonsImg, 0, 0, 0, listFileNamesWithDoublons, listFichierEtValEnDouble, listFichierSupprimePourDoublonsEntreFichiers, null, listFichierContenantDoublonsEntreFichiers, null,null, fichiersDestination,fichiersTraite, "rapportCopieImgRetour.jasper", "ReportCopieImgRetour.xhtml");
			}
			else if(typeProcess.equals(TypeProcess.FICHIER_COMPTA_RETOUR)){

				String fichiersTraite = "";

				listFileNamesWithDoublons.clear();

				listFichierEtValEnDouble.clear();

				listFichierSupprimePourDoublonsEntreFichiers.clear();

				mapFichierEtValEnDoubleComptaAllerTraite1.clear();

				//mapFichierEtValEnDoubleComptaRetourTraite1.clear();

				listFichierContenantDoublonsEntreFichiers.clear();

				listFichierACopierPourDoublonsEntreFichiers.clear();

				listFichierANePasCopierPourDoublonsEntreFichiers.clear();

				mapFichiersContenuDansFichiers.clear();

				Map<String, List<String>> mapFichiersEntreeEtContenus = new HashMap<>();

				Map<String, List<String>> mapFichiersDestEtContenus = new HashMap<>();

				nbrFichACopierCompta = 0;
				nbrFichCopieCompta = 0;
				nbrDoublonsCompta =0;
				nbrFichContenantDoublonsCompta=0;
				nbrValeursEnDoubleCompta=0;
				nbrValeursDeposeesCompta=0;
				nbrFichDoublonsEntreFichiers=0;

				boolean fichiersDestination = false;

				/*******Récupération des différents répertoires*********/
				String repEntreeFichCompteRetour = parametragesGenTeleCompense.getRepertoireEntreeFichierComptabilisationRetour();
				String repDestinationFicheComptaRetour = parametragesGenTeleCompense.getRepertoireDestinationFichierComptabilisationRetour();
				String repArchiveEntreeFicheComptaRetour = parametragesGenTeleCompense.getRepertoireArchiveEntreeFichierComptabilisationRetour();
				String repDestinationFicheComptaRetourKO = parametragesGenTeleCompense.getRepertoireDestinationFichierComptabilisationRetourKO();

				/*********Exécution du Traitement********/
				traitementOK = doTraitement(typeProcess, repEntreeFichCompteRetour, repDestinationFicheComptaRetour, repDestinationFicheComptaRetourKO, repArchiveEntreeFicheComptaRetour, nbrFichACopierCompta, nbrFichCopieCompta, nbrDoublonsCompta, nbrFichContenantDoublonsCompta, nbrValeursEnDoubleCompta, nbrValeursDeposeesCompta, listFileNamesWithDoublons, listFichierEtValEnDouble, listFichierSupprimePourDoublonsEntreFichiers, mapFichierEtValEnDoubleComptaAllerTraite1, listFichierContenantDoublonsEntreFichiers, mapFichiersEntreeEtContenus, mapFichiersDestEtContenus, fichiersDestination,fichiersTraite, "rapportCopieComptaRetour.jasper", "ReportCopieComptaRetour.xhtml" );
			}

			if(traitementOK){
				msgFinTraitement = "Traitement Terminé. Veuillez lancer l'archivage";

				statutTraitement = true;
			}else{

				//On vide les traitementsTourCompense, conteusFichiers, Fichiers, et tourCompensation parce qu'on a une erreur

				Date dateTourCompense = new SimpleDateFormat("dd/MM/yyyy").parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

				List<TourCompensation>toursCompensation = ViewHelper.virementsRecManager.filterTourCompensation(dateTourCompense, user.getLogin(), tour, typeProcess); //new Date()
				if(toursCompensation!=null &&!toursCompensation.isEmpty()){

					TourCompensation tourCompensation = toursCompensation.get(0);

					List<Fichier> listFic = ViewHelper.virementsRecManagerDAOLocal.filter(Fichier.class, null, RestrictionsContainer.getInstance().add(Restrictions.eq("dateCreation", new SimpleDateFormat("dd/MM/yyyy").parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date())))).add(Restrictions.eq("tourCompensation", tourCompensation)), null, null, 0, -1);
					if(listFic!=null && !listFic.isEmpty()){

						List<ContenuFichier> listContenusFic = ViewHelper.virementsRecManagerDAOLocal.filter(ContenuFichier.class, null, RestrictionsContainer.getInstance().add(Restrictions.eq("dateCreation", new SimpleDateFormat("dd/MM/yyyy").parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date())))).add(Restrictions.in("fichier", listFic)), null, null, 0, -1);
						for(ContenuFichier c: listContenusFic){
							ViewHelper.virementsRecManagerDAOLocal.delete(c);
						}
						listContenusFic.clear();

						for(Fichier f: listFic){
							ViewHelper.virementsRecManagerDAOLocal.delete(f);
						}
						listFic.clear();

						List<TraitementTourCompensation> traitementTourCompensations = ViewHelper.virementsRecManager.filterTraitementTourCompensation(null,tourCompensation,dateTourCompense);
						if(traitementTourCompensations!=null&&!traitementTourCompensations.isEmpty()){

							for(TraitementTourCompensation t: traitementTourCompensations){
								ViewHelper.virementsRecManagerDAOLocal.delete(t);
							}
							traitementTourCompensations.clear();
						}


						ViewHelper.virementsRecManagerDAOLocal.delete(tourCompensation);
						toursCompensation.clear();
					}

				}

				msgFinTraitement = "Erreur dans le traitement!!!!!!!!!";

				statutTraitement = true;
			}

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	//Copier les fichiers d'un répertoire vers un autre
	public boolean traitementEtCopieFichiers(TypeProcess typeProcess,File[] listFiles, String fichierTraite, Path pathEntree, Path pathDestinationOK, Path pathDestinationKO, Path pathArchiveEntree, TourCompensation tourCompensation, Boolean fichiersDestination, Integer aCopier){
		try {

			/*Integer copier = 0 ;

			Integer doublons = 0;*/

			TraitementTourCompensation traitementTourCompense = new TraitementTourCompensation();
			traitementTourCompense.setUtiTraitement(user.getLogin());
			traitementTourCompense.setTypeTraitement(typeTraitement);
			traitementTourCompense.setDateTraitement(new Date());
			traitementTourCompense.setHeure(new SimpleDateFormat("HH:mm:ss").format(new Date()));
			traitementTourCompense.setFichiersTraite(fichierTraite);
			traitementTourCompense.setTourCompensation(tourCompensation);

			boolean doublonFound = false;


			//Il y avait des doublons dans la destination au début du traitement; malgré que nous les ayons archivés, on ne copie plus
			if(fichiersDestination==true){

				String newFileName = pathEntree.getFileName().toString();

				for(File f: listFiles){

					if(f.getName().equals(newFileName)){
						System.out.println("***Doublons trouvé******" + f.getName());
						nbrDoublonsImg++;
						traitementTourCompense.setSortTraitement(SortTraitement.ECHEC);
						ViewHelper.virementsRecManagerDAOLocal.save(traitementTourCompense);
						doublonFound=true;
					}

				}

				/******Arriver ici (y a pas eu de doublons) on copie*****/
				if(!doublonFound){

					Path p = Files.copy(pathEntree, pathArchiveEntree, StandardCopyOption.REPLACE_EXISTING);
					System.out.println("***Files copied to ***" + p.toString());

					p = Files.move(pathEntree, pathDestinationOK, StandardCopyOption.REPLACE_EXISTING);
					System.out.println("***Files copied to ***" + p.toString());
					nbrFichCopieImg++;

					traitementTourCompense.setSortTraitement(SortTraitement.SUCCES);
					ViewHelper.virementsRecManagerDAOLocal.save(traitementTourCompense);
					
				}else{
					
					//S'il y a doublon
					
					Path p = Files.copy(pathEntree, pathArchiveEntree, StandardCopyOption.REPLACE_EXISTING);
					System.out.println("***Files copied to ***" + p.toString());

					p = Files.move(pathEntree, pathDestinationKO, StandardCopyOption.REPLACE_EXISTING);
					System.out.println("***Files copied to ***" + p.toString());
					//nbrFichCopieImg++;

					traitementTourCompense.setSortTraitement(SortTraitement.ECHEC);
					ViewHelper.virementsRecManagerDAOLocal.save(traitementTourCompense);
				}


			}else{

				Path p = Files.copy(pathEntree, pathArchiveEntree, StandardCopyOption.REPLACE_EXISTING);
				System.out.println("***Files copied to ***" + p.toString());

				p = Files.move(pathEntree, pathDestinationOK, StandardCopyOption.REPLACE_EXISTING);
				System.out.println("***Files copied to ***" + p.toString());
				nbrFichCopieImg++;

				traitementTourCompense.setSortTraitement(SortTraitement.SUCCES);
				ViewHelper.virementsRecManagerDAOLocal.save(traitementTourCompense);
			}


			/***if(typeProcess.equals(TypeProcess.RAPATRIEMENT_IMG_ALLER)){
				nbrFichACopierImgAller = aCopier;
				nbrFichCopieImgAller = copier;
				nbrDoublonsImgAller = doublons;

			}else if(typeProcess.equals(TypeProcess.RAPATRIEMENT_IMG_RETOUR)){
				nbrFichACopierImgRetour = aCopier;
				nbrFichCopieImgRetour = copier;
				nbrDoublonsImgRetour = doublons;  
			}*/

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;

	}



	/***Copier les fichiers d'un répertoire vers un autre
	public boolean traitementEtCopieFichiersRetour(File[] listFiles, String fichierTraite, Path pathEntree, Path pathDestination, TourCompensation tourCompensation, Boolean fichiersDestination){
		try {

			TraitementTourCompensation traitementTourCompense = new TraitementTourCompensation();
			traitementTourCompense.setUtiTraitement(user.getLogin());
			traitementTourCompense.setTypeTraitement(typeTraitement);
			traitementTourCompense.setDateTraitement(new Date());
			traitementTourCompense.setHeure(new SimpleDateFormat("HH:mm:ss").format(new Date()));
			traitementTourCompense.setFichiersTraite(fichierTraite);
			traitementTourCompense.setTourCompensation(tourCompensation);

			//Il y avait des doublons dans la destination au début du traitement; malgré que nous les ayons archivés, on ne copie plus
			if(fichiersDestination==true){

				String newFileName = pathEntree.getFileName().toString();

				for(File f: listFiles){

					if(f.getName().equals(newFileName)){
						System.out.println("***Doublons trouvé******" + f.getName());
						nbrDoublonsImgRetour++;
						traitementTourCompense.setSortTraitement(SortTraitement.ECHEC);
						ViewHelper.virementsRecManagerDAOLocal.save(traitementTourCompense);
						return false;
					}

				}

				/******Arriver ici (y a pas eu de doublons) on copie*****

				Path p = Files.move(pathEntree, pathDestination, StandardCopyOption.REPLACE_EXISTING);
				System.out.println("***Files copied to ***" + p.toString());
				nbrFichCopieImgRetour++;

				traitementTourCompense.setSortTraitement(SortTraitement.SUCCES);
				ViewHelper.virementsRecManagerDAOLocal.save(traitementTourCompense);

				return true;

			}else{


				Path p = Files.move(pathEntree, pathDestination, StandardCopyOption.REPLACE_EXISTING);
				System.out.println("***Files copied to ***" + p.toString());
				nbrFichCopieImgRetour++;

				traitementTourCompense.setSortTraitement(SortTraitement.SUCCES);
				ViewHelper.virementsRecManagerDAOLocal.save(traitementTourCompense);
			}



		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;

	}*/

	public List<String> constructLinesOfKeysFromFileLines(List<String> fileLines){

		String cle;

		List<String> listKeys = new ArrayList<String>();

		if(parametrages.getTypeCarac()!=null &&!parametrages.getTypeCarac().isEmpty() ){

			for(String line: fileLines){

				cle = "";

				for(CaracteristiquesVir c: parametrages.getTypeCarac()){

					if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.Evenement)){

						if(cle.equals(""))
							cle = line.substring(68, 75);
						else
							cle = cle + "-" + line.substring(68, 75);
					}
					if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.montant)){

						if(cle.equals(""))
							cle = line.substring(53, 68);
						else
							cle = cle + "-" + line.substring(53, 68);
					}
					if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.ncp)){
						if(cle.equals(""))
							cle = line.substring(145, 156);
						else
							cle = cle + "-" + line.substring(145, 156); //substring(135, 158).substring(11,22)
					}
					if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.ncp_donneur_ordre)){
						if(cle.equals(""))
							cle =  line.substring(85, 96);
						else
							cle = cle + "-" + line.substring(85, 96); //substring(75, 98).substring(11,22)
					}
					if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.nom_benef)){
						if(cle.equals(""))
							cle = line.substring(158, 188);
						else
							cle = cle + "-" + line.substring(158, 188);
					}
					if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.nom_donneur_ordre)){
						if(cle.equals(""))
							cle = line.substring(98, 128);
						else
							cle = cle + "-" + line.substring(98, 128);
					}
				}

				listKeys.add(cle);
			}	
		}
		return listKeys;
	}


	public boolean traitementEtCopieFichiersCompta(File[] listFiles, String fichierTraite, Path pathEntree, Path pathDestination, Path pathArchiveEntree,  TourCompensation tourCompensation, TypeTraitement typeTraitement, List<ParametragesCaracteresSpeciaux>parametragesCaracteresSpeciauxs, Boolean fichiersDestination, Map<String, List<String>> mapFichiersEntreeEtContenus, Map<String, List<String>> mapFichiersDestEtContenus, Integer aCopier, TypeProcess typeProcess  ){
		try {

			TraitementTourCompensation traitementTourCompense = new TraitementTourCompensation();
			traitementTourCompense.setUtiTraitement(user.getLogin());
			traitementTourCompense.setTypeTraitement(typeTraitement);
			traitementTourCompense.setDateTraitement(new Date());
			traitementTourCompense.setHeure(new SimpleDateFormat("HH:mm:ss").format(new Date()));
			traitementTourCompense.setFichiersTraite(fichierTraite);
			traitementTourCompense.setTourCompensation(tourCompensation);


			String newFileName = pathEntree.getFileName().toString();

			if(typeTraitement.equals(TypeTraitement.DOUBLONS_NOM_FICHIER)){
				/**Controler les doublons entre fichiers du rep de destination et fichiers du rep d'entrée**/
				//Il y avait des doublons dans la destination au début du traitement; malgré que nous les ayons archivés, on ne copie plus
				if(fichiersDestination==true){
					for(File f: listFiles){
						if(f.getName().equals(newFileName)){
							System.out.println("***Doublons Nom trouvé******" + f.getName());
							nomFichierDoublon = true;
							nbrDoublonsCompta++;
							listFileNamesWithDoublons.add(newFileName);
							traitementTourCompense.setSortTraitement(SortTraitement.ECHEC);
							ViewHelper.virementsRecManagerDAOLocal.save(traitementTourCompense);
						}
					}

					if(listFileNamesWithDoublons.isEmpty()){
						traitementTourCompense.setSortTraitement(SortTraitement.SUCCES);
						ViewHelper.virementsRecManagerDAOLocal.save(traitementTourCompense);
					}
				}else{
					/**On va copier à la deuxième itération j=1*/
					traitementTourCompense.setSortTraitement(SortTraitement.SUCCES);
					ViewHelper.virementsRecManagerDAOLocal.save(traitementTourCompense);
				}

			}
			else if(typeTraitement.equals(TypeTraitement.DOUBLONS_ENTRE_FICHIER)){

				List<String> fileLines = new ArrayList<String>();
				List<String> fileLinesFormatted = new ArrayList<String>();
				List<String> listKeys = new ArrayList<String>();
				List<String> listKeys2 = new ArrayList<String>();

				int count1=0;
				//boolean doublonsEntreFichierDansEntree=false;

				//List<String> listFichiersQuiContiennentFichier = new ArrayList<String>();
				Map<String,Integer> mapFichiersQuiContiennentFichier = new HashMap<>();

				for(String key:mapFichiersEntreeEtContenus.keySet()){

					System.out.println("*****fichierTraite*****" + fichierTraite);
					System.out.println("*****key*****" + key);


					listKeys.clear();listKeys2.clear();

					if(!key.equals(fichierTraite)){ //Pour ne pas comparer un fichier avec lui-même

						String typeFicTraite="";
						String typeKey="";
						String part1="";
						String cenr="";
						if(typeProcess.equals(TypeProcess.FICHIER_COMPTA_ALLER)){
							String [] typeFicTraiteArray =  fichierTraite.split("ENV");   
							if(typeFicTraiteArray!=null && typeFicTraiteArray.length!=0){
								part1 = typeFicTraiteArray[0];
								if(part1!=null && part1.length()!=0){
									typeFicTraite = part1.substring(part1.length()-10, part1.length()-5); //String typeFicTraite  =   fichierTraite.substring(34, 39);
								}
							}
							String [] typeKeyArray =  key.split("ENV");  
							if(typeKeyArray!=null && typeKeyArray.length!=0){
								part1 = typeKeyArray[0];
								if(part1!=null && part1.length()!=0){
									typeKey = part1.substring(part1.length()-10, part1.length()-5);  //typeKey = key.substring(34, 39);
								}
							}
						}else if(typeProcess.equals(TypeProcess.FICHIER_COMPTA_RETOUR)){
							String [] typeFicTraiteArray =  fichierTraite.split("RCP");   
							if(typeFicTraiteArray!=null && typeFicTraiteArray.length!=0){
								part1 = typeFicTraiteArray[0];
								if(part1!=null && part1.length()!=0){
									typeFicTraite = part1.substring(part1.length()-10, part1.length()-5); //String typeFicTraite  =   fichierTraite.substring(34, 39);
								}
							}
							String [] typeKeyArray =  key.split("RCP");  
							if(typeKeyArray!=null && typeKeyArray.length!=0){
								part1 = typeKeyArray[0];
								if(part1!=null && part1.length()!=0){
									typeKey = part1.substring(part1.length()-10, part1.length()-5);  //typeKey = key.substring(34, 39);
								}
							}
						}
						System.out.println("****typeFicTraite****" + typeFicTraite + "***typeKey****" + typeKey );

						if(typeFicTraite.equals(typeKey)){ //Si même type, i.e. e.g. 10-21 = 10-21

							if(pathEntree!=null)
								fileLines = readAndScanFile(pathEntree);


							/**String cle;

							if(parametrages.getTypeCarac()!=null &&!parametrages.getTypeCarac().isEmpty() ){

								for(String line: fileLines){

									cle = "";

									for(CaracteristiquesVir c: parametrages.getTypeCarac()){

										if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.Evenement)){

											if(cle.equals(""))
												cle = line.substring(68, 75);
											else
												cle = cle + "-" + line.substring(68, 75);
										}
										if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.montant)){

											if(cle.equals(""))
												cle = line.substring(53, 68);
											else
												cle = cle + "-" + line.substring(53, 68);
										}
										if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.ncp)){
											if(cle.equals(""))
												cle = line.substring(145, 156);
											else
												cle = cle + "-" + line.substring(145, 156); //substring(135, 158).substring(11,22)
										}
										if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.ncp_donneur_ordre)){
											if(cle.equals(""))
												cle =  line.substring(85, 96);
											else
												cle = cle + "-" + line.substring(85, 96); //substring(75, 98).substring(11,22)
										}
										if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.nom_benef)){
											if(cle.equals(""))
												cle = line.substring(158, 188);
											else
												cle = cle + "-" + line.substring(158, 188);
										}
										if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.nom_donneur_ordre)){
											if(cle.equals(""))
												cle = line.substring(98, 128);
											else
												cle = cle + "-" + line.substring(98, 128);
										}

										///System.out.println("**key***"+key);
									}

									listKeys.add(cle);
								}	
							}*/

							listKeys = constructLinesOfKeysFromFileLines(fileLines);


							for(String s: listKeys){  //fileLines

								fileLinesFormatted.add(s.trim());
							}
							if(!fileLinesFormatted.isEmpty()){

								List<String> listContent = mapFichiersEntreeEtContenus.get(key);

								/**if(parametrages.getTypeCarac()!=null &&!parametrages.getTypeCarac().isEmpty() ){

									for(String line: listContent){

										cle = "";

										for(CaracteristiquesVir c: parametrages.getTypeCarac()){

											if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.Evenement)){

												if(cle.equals(""))
													cle = line.substring(68, 75);
												else
													cle = cle + "-" + line.substring(68, 75);
											}
											if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.montant)){

												if(cle.equals(""))
													cle = line.substring(53, 68);
												else
													cle = cle + "-" + line.substring(53, 68);
											}
											if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.ncp)){
												if(cle.equals(""))
													cle = line.substring(145, 156);
												else
													cle = cle + "-" + line.substring(145, 156); //substring(135, 158).substring(11,22)
											}
											if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.ncp_donneur_ordre)){
												if(cle.equals(""))
													cle =  line.substring(85, 96);
												else
													cle = cle + "-" + line.substring(85, 96); //substring(75, 98).substring(11,22)
											}
											if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.nom_benef)){
												if(cle.equals(""))
													cle = line.substring(158, 188);
												else
													cle = cle + "-" + line.substring(158, 188);
											}
											if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.nom_donneur_ordre)){
												if(cle.equals(""))
													cle = line.substring(98, 128);
												else
													cle = cle + "-" + line.substring(98, 128);
											}

											///System.out.println("**key***"+key);
										}

										listKeys2.add(cle);
									}	
								}*/

								listKeys2 = constructLinesOfKeysFromFileLines(listContent);

								List<String> listContentFormatted = new ArrayList<String>();
								for(String m: listKeys2){  //listContent
									listContentFormatted.add(m.trim());
								}


								//Si le contenu du fichier du repertoire entrée est contenu dans le fichier parcouru du répertoire de destination
								if(listContentFormatted.containsAll(fileLinesFormatted) ){

									/********
									nbrFichDoublonsEntreFichiers++;

									if(!mapFichiersQuiContiennentFichier.containsKey(fichierTraite))
										mapFichiersQuiContiennentFichier.put(fichierTraite,  mapFichiersEntreeEtContenus.get(fichierTraite).size());
									mapFichiersQuiContiennentFichier.put(key, mapFichiersEntreeEtContenus.get(key).size());

									//listFichiersQuiContiennentFichier.add(key);

									//doublonsEntreFichierDansEntree = true;*/

									//count1++;
									/**
									 * 
									 * List<String> tempListFichierANePasCopierPourDoublonsEntreFichiers = new ArrayList<>(); 
									List<String> tempListFichierACopierPourDoublonsEntreFichiers = new ArrayList<>(); 
									 * nbrFichDoublonsEntreFichiers++;

									if(!listFichierANePasCopierPourDoublonsEntreFichiers.contains(fichierTraite)){
										listFichierANePasCopierPourDoublonsEntreFichiers.add(fichierTraite);
									}

									fileLines = new ArrayList<String>();
									listKeys = new ArrayList<String>();
									List<String> linesFormatted = new ArrayList<String>();
									if(!listFichierACopierPourDoublonsEntreFichiers.isEmpty()){
										for(String file: listFichierACopierPourDoublonsEntreFichiers){
											String fichier = pathEntree.getParent() + File.separator + file;
											Path path = Paths.get(fichier);
											fileLines = readAndScanFile(path);
											listKeys = constructLinesOfKeysFromFileLines(fileLines);
											for(String s: listKeys){ //fileLines
												linesFormatted.add(s.trim());
											}
											if(listContentFormatted.containsAll(linesFormatted)){
												tempListFichierANePasCopierPourDoublonsEntreFichiers.add(file);
												tempListFichierACopierPourDoublonsEntreFichiers.add(key);
												//listFichierACopierPourDoublonsEntreFichiers.add(fichierTraite);
											}
										}

										for(String s: tempListFichierANePasCopierPourDoublonsEntreFichiers){
											listFichierACopierPourDoublonsEntreFichiers.remove(s);
										}
										tempListFichierANePasCopierPourDoublonsEntreFichiers.clear();

										for(String s: tempListFichierACopierPourDoublonsEntreFichiers){
											listFichierACopierPourDoublonsEntreFichiers.add(s);
										}
										tempListFichierACopierPourDoublonsEntreFichiers.clear();

									}else{

										listFichierACopierPourDoublonsEntreFichiers.add(key);
									}*/

									//count1++;
									nbrFichDoublonsEntreFichiers++;

									if(!listFichierANePasCopierPourDoublonsEntreFichiers.contains(fichierTraite)){
										listFichierANePasCopierPourDoublonsEntreFichiers.add(fichierTraite);
									}

									listFichierContenantDoublonsEntreFichiers.add(fichierTraite);
									System.out.println(" -----------listFichierContenantDoublonsEntreFichiers.size------------------" + listFichierContenantDoublonsEntreFichiers.size());
									if(!listFichierContenantDoublonsEntreFichiers.isEmpty())System.out.println(" -----------listFichierContenantDoublonsEntreFichiers.get(0)------------------" + listFichierContenantDoublonsEntreFichiers.get(0));

									System.out.println("************Le fichier******" + fichierTraite + " est ajoutée dans listFichierContenantDoublonsEntreFichiers ");

									/**listFichierSupprimePourDoublonsEntreFichiers.add(fichierTraite); //mauvais fichier
									listFichierSupprimePourDoublonsEntreFichiers.add(key); //le bon fichier pour garder la trace dans les rapports*/

									if(!listFichierSupprimePourDoublonsEntreFichiers.contains(fichierTraite))listFichierSupprimePourDoublonsEntreFichiers.add(fichierTraite); //mauvais fichier
									//if(!listFichierSupprimePourDoublonsEntreFichiers.contains(key))listFichierSupprimePourDoublonsEntreFichiers.add(key); //le bon fichier pour garder la trace dans les rapports

									//listFichierACopierPourDoublonsEntreFichiers.add(key);


								}
								//listContentFormatted.clear();
							}
						}

					}

				}
				fileLines.clear(); fileLinesFormatted.clear();
				System.out.println("*****mapFichiersDestEtContenus size*****" + mapFichiersDestEtContenus.size());

				/**mapFichiersContenuDansFichiers.put(fichierTraite, mapFichiersQuiContiennentFichier);

				List<String> filesInAscendingOrder = new ArrayList<String>();
				List<Integer> integersInAscendingOrder = new ArrayList<Integer>();
				for(String fichier:mapFichiersContenuDansFichiers.get(fichierTraite).keySet()){
					int numberOfLines = mapFichiersContenuDansFichiers.get(fichierTraite).get(fichier);
					integersInAscendingOrder.add(numberOfLines);
				}
				Collections.sort(integersInAscendingOrder);


				for(String fichier:mapFichiersContenuDansFichiers.get(fichierTraite).keySet()){
					if(!listFichierANePasCopierPourDoublonsEntreFichiers.contains(fichier))
						listFichierANePasCopierPourDoublonsEntreFichiers.add(fichier);
				}

				System.out.println("*****mapFichiersContenuDansFichiers size*****" + mapFichiersContenuDansFichiers.size());

				int maxValueOfIntegersList=0;

				if(integersInAscendingOrder.size()>0){

					maxValueOfIntegersList = integersInAscendingOrder.get(integersInAscendingOrder.size()-1);
					System.out.println("*****listFichierACopierPourDoublonsEntreFichiers size*****" + listFichierACopierPourDoublonsEntreFichiers.size());
					System.out.println("*****listFichierANePasCopierPourDoublonsEntreFichiers size*****" + listFichierANePasCopierPourDoublonsEntreFichiers.size());

					for(String fichier:mapFichiersContenuDansFichiers.get(fichierTraite).keySet()){
						//boolean found = false;
						if(mapFichiersContenuDansFichiers.get(fichierTraite).get(fichier)==maxValueOfIntegersList){

							if(listFichierACopierPourDoublonsEntreFichiers.isEmpty()){
								listFichierACopierPourDoublonsEntreFichiers.add(fichier);
								if(listFichierANePasCopierPourDoublonsEntreFichiers.contains(fichier))
									listFichierANePasCopierPourDoublonsEntreFichiers.remove(fichier);
							}else{
								listFichierACopierPourDoublonsEntreFichiers.add(fichier);
							}
							//listFichierANePasCopierPourDoublonsEntreFichiers.remove(fichier);

							break;
						}
					}
				}*/


				for(String key:mapFichiersDestEtContenus.keySet()){

					listKeys.clear();listKeys2.clear();

					System.out.println("***Key is****" + key);

					String typeFicTraite="";
					String typeKey="";
					String part1="";
					String cenr="";
					if(typeProcess.equals(TypeProcess.FICHIER_COMPTA_ALLER)){
						String [] typeFicTraiteArray =  fichierTraite.split("ENV");   
						if(typeFicTraiteArray!=null && typeFicTraiteArray.length!=0){
							part1 = typeFicTraiteArray[0];
							if(part1!=null && part1.length()!=0){
								typeFicTraite = part1.substring(part1.length()-10, part1.length()-5); //String typeFicTraite  =   fichierTraite.substring(34, 39);
							}
						}
						String [] typeKeyArray =  key.split("ENV");  
						if(typeKeyArray!=null && typeKeyArray.length!=0){
							part1 = typeKeyArray[0];
							if(part1!=null && part1.length()!=0){
								typeKey = part1.substring(part1.length()-10, part1.length()-5);  //typeKey = key.substring(34, 39);
							}
						}
					}else if(typeProcess.equals(TypeProcess.FICHIER_COMPTA_RETOUR)){
						String [] typeFicTraiteArray =  fichierTraite.split("RCP");   
						if(typeFicTraiteArray!=null && typeFicTraiteArray.length!=0){
							part1 = typeFicTraiteArray[0];
							if(part1!=null && part1.length()!=0){
								typeFicTraite = part1.substring(part1.length()-10, part1.length()-5); //String typeFicTraite  =   fichierTraite.substring(34, 39);
							}
						}
						String [] typeKeyArray =  key.split("RCP");  
						if(typeKeyArray!=null && typeKeyArray.length!=0){
							part1 = typeKeyArray[0];
							if(part1!=null && part1.length()!=0){
								typeKey = part1.substring(part1.length()-10, part1.length()-5);  //typeKey = key.substring(34, 39);
							}
						}
					}
					System.out.println("****typeFicTraite****" + typeFicTraite + "***typeKey****" + typeKey );

					if(typeFicTraite.equals(typeKey)){ //Si même type, i.e. e.g. 10-21 = 10-21

						if(pathEntree!=null)
							fileLines = readAndScanFile(pathEntree);
						/**String cle;

						if(parametrages.getTypeCarac()!=null &&!parametrages.getTypeCarac().isEmpty() ){

							for(String line: fileLines){

								cle = "";

								for(CaracteristiquesVir c: parametrages.getTypeCarac()){

									if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.Evenement)){

										if(cle.equals(""))
											cle = line.substring(68, 75);
										else
											cle = cle + "-" + line.substring(68, 75);
									}
									if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.montant)){

										if(cle.equals(""))
											cle = line.substring(53, 68);
										else
											cle = cle + "-" + line.substring(53, 68);
									}
									if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.ncp)){
										if(cle.equals(""))
											cle = line.substring(145, 156);
										else
											cle = cle + "-" + line.substring(145, 156); //substring(135, 158).substring(11,22)
									}
									if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.ncp_donneur_ordre)){
										if(cle.equals(""))
											cle =  line.substring(85, 96);
										else
											cle = cle + "-" + line.substring(85, 96); //substring(75, 98).substring(11,22)
									}
									if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.nom_benef)){
										if(cle.equals(""))
											cle = line.substring(158, 188);
										else
											cle = cle + "-" + line.substring(158, 188);
									}
									if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.nom_donneur_ordre)){
										if(cle.equals(""))
											cle = line.substring(98, 128);
										else
											cle = cle + "-" + line.substring(98, 128);
									}

									///System.out.println("**key***"+key);
								}

								listKeys.add(cle);
							}	
						}*/

						listKeys = constructLinesOfKeysFromFileLines(fileLines);

						for(String s: listKeys){ //fileLines
							fileLinesFormatted.add(s.trim());
						}
						if(!fileLinesFormatted.isEmpty()){

							List<String> listContent = mapFichiersDestEtContenus.get(key);

							/**if(parametrages.getTypeCarac()!=null &&!parametrages.getTypeCarac().isEmpty() ){

								for(String line: listContent){

									cle = "";

									for(CaracteristiquesVir c: parametrages.getTypeCarac()){

										if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.Evenement)){

											if(cle.equals(""))
												cle = line.substring(68, 75);
											else
												cle = cle + "-" + line.substring(68, 75);
										}
										if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.montant)){

											if(cle.equals(""))
												cle = line.substring(53, 68);
											else
												cle = cle + "-" + line.substring(53, 68);
										}
										if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.ncp)){
											if(cle.equals(""))
												cle = line.substring(145, 156);
											else
												cle = cle + "-" + line.substring(145, 156); //substring(135, 158).substring(11,22)
										}
										if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.ncp_donneur_ordre)){
											if(cle.equals(""))
												cle =  line.substring(85, 96);
											else
												cle = cle + "-" + line.substring(85, 96); //substring(75, 98).substring(11,22)
										}
										if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.nom_benef)){
											if(cle.equals(""))
												cle = line.substring(158, 188);
											else
												cle = cle + "-" + line.substring(158, 188);
										}
										if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.nom_donneur_ordre)){
											if(cle.equals(""))
												cle = line.substring(98, 128);
											else
												cle = cle + "-" + line.substring(98, 128);
										}

										///System.out.println("**key***"+key);
									}

									listKeys2.add(cle);
								}	
							}*/

							listKeys2 = constructLinesOfKeysFromFileLines(listContent);

							List<String> listContentFormatted = new ArrayList<String>();
							for(String m: listKeys2){ //listContent
								listContentFormatted.add(m.trim());
							}
							//Si le contenu du fichier du repertoire entrée est contenu dans le fichier parcouru du répertoire de destination
							if(listContentFormatted.containsAll(fileLinesFormatted) ){

								//count1++;
								nbrFichDoublonsEntreFichiers++; 

								if(!listFichierANePasCopierPourDoublonsEntreFichiers.contains(fichierTraite)){
									listFichierANePasCopierPourDoublonsEntreFichiers.add(fichierTraite);
								}

								listFichierContenantDoublonsEntreFichiers.add(fichierTraite);
								System.out.println(" -----------listFichierContenantDoublonsEntreFichiers.size------------------" + listFichierContenantDoublonsEntreFichiers.size());
								if(!listFichierContenantDoublonsEntreFichiers.isEmpty())System.out.println(" -----------listFichierContenantDoublonsEntreFichiers.get(0)------------------" + listFichierContenantDoublonsEntreFichiers.get(0));

								System.out.println("************Le fichier******" + fichierTraite + " est ajoutée dans listFichierContenantDoublonsEntreFichiers ");

								/**listFichierSupprimePourDoublonsEntreFichiers.add(fichierTraite); //mauvais fichier
								listFichierSupprimePourDoublonsEntreFichiers.add(key); //le bon fichier pour garder la trace dans les rapports*/

								if(!listFichierSupprimePourDoublonsEntreFichiers.contains(fichierTraite))listFichierSupprimePourDoublonsEntreFichiers.add(fichierTraite); //mauvais fichier
								//if(!listFichierSupprimePourDoublonsEntreFichiers.contains(key))listFichierSupprimePourDoublonsEntreFichiers.add(key); //le bon fichier pour garder la trace dans les rapports

								//listFichierACopierPourDoublonsEntreFichiers.add(key);
							}
						}
					}

				}
				fileLines.clear(); fileLinesFormatted.clear(); 



				System.out.println(" -----------listFichierContenantDoublonsEntreFichiers.size------xxxxxxxxxx-----------" + listFichierContenantDoublonsEntreFichiers.size());
				if(!listFichierContenantDoublonsEntreFichiers.isEmpty())System.out.println(" -----------listFichierContenantDoublonsEntreFichiers.get(0)------xxxxxxxxxx-----------" + listFichierContenantDoublonsEntreFichiers.get(0));


				if(count1>0){

					//nbrFichDoublonsEntreFichiers = count1;

					traitementTourCompense.setSortTraitement(SortTraitement.ECHEC);
					ViewHelper.virementsRecManagerDAOLocal.save(traitementTourCompense);

				}else{

					traitementTourCompense.setSortTraitement(SortTraitement.SUCCES);
					ViewHelper.virementsRecManagerDAOLocal.save(traitementTourCompense);
				}


				System.out.println("*****listFichierSupprimePourDoublonsEntreFichiers size*****" + listFichierSupprimePourDoublonsEntreFichiers.size());
				if(!listFichierSupprimePourDoublonsEntreFichiers.isEmpty()){
					for(String s: listFichierSupprimePourDoublonsEntreFichiers){
						System.out.println("*******s******" + s);
					}
				}

			}
			else if(typeTraitement.equals(TypeTraitement.DOUBLONS_DANS_FICHIER)){

				System.out.println(" -----------listFichierContenantDoublonsEntreFichiers.size--------++++++----------" + listFichierContenantDoublonsEntreFichiers.size());
				if(!listFichierContenantDoublonsEntreFichiers.isEmpty())System.out.println(" -----------listFichierContenantDoublonsEntreFichiers.get(0)--------++++++----------" + listFichierContenantDoublonsEntreFichiers.get(0));

				/***Vérification des doublons en termes de valeurs contenus dans le fichier de comptabilisation ENV***/
				System.out.println("Fichier Traité " + fichierTraite);

				boolean charSpeciauxFound = false;

				listFichierEtValEnDouble.clear();

				List<String> fileLines = new ArrayList<String>();

				List<String> listKeys = new ArrayList<String>();

				List<String> aLines =  new ArrayList<String>();


				String key;
				if(pathEntree!=null){
					//Récupération des lignes du fichier
					fileLines = readAndScanFile(pathEntree);

					//Parcours des caractéristiques de doublons dans les virements; on forme un clé de ces valeurs pour chaque ligne du fichier
					if(parametrages.getTypeCarac()!=null &&!parametrages.getTypeCarac().isEmpty() ){

						for(String line: fileLines){

							key = "";

							for(CaracteristiquesVir c: parametrages.getTypeCarac()){

								if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.Evenement)){

									if(key.equals(""))
										key = line.substring(68, 75);
									else
										key = key + "-" + line.substring(68, 75);
								}
								if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.montant)){

									if(key.equals(""))
										key = line.substring(53, 68);
									else
										key = key + "-" + line.substring(53, 68);
								}
								if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.ncp)){
									if(key.equals(""))
										key = line.substring(145, 156);
									else
										key = key + "-" + line.substring(145, 156); //substring(135, 158).substring(11,22)
								}
								if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.ncp_donneur_ordre)){
									if(key.equals(""))
										key =  line.substring(85, 96);
									else
										key = key + "-" + line.substring(85, 96); //substring(75, 98).substring(11,22)
								}
								if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.nom_benef)){
									if(key.equals(""))
										key = line.substring(158, 188);
									else
										key = key + "-" + line.substring(158, 188);
								}
								if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.nom_donneur_ordre)){
									if(key.equals(""))
										key = line.substring(98, 128);
									else
										key = key + "-" + line.substring(98, 128);
								}

								///System.out.println("**key***"+key);
							}

							listKeys.add(key);
						}	
					}
				}


				int l;
				int nbrValeursEnDoubleComptaAllerLocal=0;
				int nbrValeursDeposeesComptaAllerLocal=0;

				//Le prermier élément dans la liste est le nom du fichier traité
				listFichierEtValEnDouble.add(fichierTraite);

				//Si la liste des clés n'est pas vide on la parcours pour vérifier qu'il n'y a pas deux clés/lignes qui se ressemble, dans quel cas nous constatons un doublon
				if(!listKeys.isEmpty()){

					for(int k=0; k<listKeys.size();k++){
						l=k+1;
						while(l<listKeys.size()){
							if(listKeys.get(k).equals(listKeys.get(l))){
								/***Noter les valeurs en double et le nom du fichier contenant ces valeurs***/

								nbrValeursEnDoubleCompta++;
								nbrValeursEnDoubleComptaAllerLocal++;

								//On ajoute la valeur en double
								listFichierEtValEnDouble.add(fileLines.get(l));

								////System.out.println("***Le doublon c'est la ligne " + l);
							}
							l++;
						}
					}

				}else{

					//Si la liste des clés est vide, mais la liste des ligne n'est pas vide, alors aucune clé n'a été paramétré dans le module, alors nous allons par défaut considérer la totalité de chaque ligne comme clé de doublon
					if(!fileLines.isEmpty()){

						for(int k=0; k<fileLines.size(); k++){

							l=k+1;

							//On compare chaque ligne k avec toutes les autres lignes de k+1 en montant pour vérifier s'il y a un ou plusieurs doublons selon les critères de doublons paramétrés
							while(l<fileLines.size()){

								if(fileLines.get(k).equals(fileLines.get(l))){

									/***Noter le valeurs en double et le nom du fichier contenant ces valeurs***/

									nbrValeursEnDoubleCompta++;
									nbrValeursEnDoubleComptaAllerLocal++;

									//On ajoute la valeur en double
									listFichierEtValEnDouble.add(fileLines.get(l));

									////System.out.println("***Le doublon c'est la ligne " + l);

								}

								l++;
							}

						}
					}
				}


				/***************************************Traitement des caractères spéciaux************************************************************/
				for(int k=0; k<fileLines.size(); k++){

					aLines.add(fileLines.get(k));
					String newLine="";
					for(ParametragesCaracteresSpeciaux p: parametragesCaracteresSpeciauxs){
						if(p.getValide().equals(Boolean.TRUE)){
							if(fileLines.get(k).contains(p.getCaractereSpecial().trim())){ //Si la ligne contient le caractère spécial

								if(!newLine.isEmpty())
									aLines.remove(newLine);
								else
									aLines.remove(fileLines.get(k));

								/****On remplace le caractère spécial par le caractère de remplacement**/
								if(newLine.isEmpty())
									newLine = fileLines.get(k).replace(p.getCaractereSpecial().trim(), parametragesGenTeleCompense.getCaracterePourRemplacerCaractereSpecial().trim());
								else{
									newLine = newLine.replace(p.getCaractereSpecial().trim(), parametragesGenTeleCompense.getCaracterePourRemplacerCaractereSpecial().trim());
								}

								System.out.println("******************newLine**************" + newLine);


								aLines.add(newLine);

								charSpeciauxFound=true;

							}
						}

					}

				}
				/*********************************************Traitement des caractères spéciaux************************************************/

				if(charSpeciauxFound==true)
					createNewFileWithNewLines(pathEntree, aLines);


				System.out.println("***listFichierEtValEnDouble size***" + listFichierEtValEnDouble.size());


				//S'il y avait au moins un doublon
				if(listFichierEtValEnDouble.size()>1){

					nbrFichContenantDoublonsCompta++;

					traitementTourCompense.setSortTraitement(SortTraitement.ECHEC);
					ViewHelper.virementsRecManagerDAOLocal.save(traitementTourCompense);

				}
				else{

					if(!listFileNamesWithDoublons.contains(newFileName) && !listFichierSupprimePourDoublonsEntreFichiers.contains(newFileName) && !listFichierContenantDoublonsEntreFichiers.contains(newFileName) ){

						Path p = Files.copy(pathEntree, pathArchiveEntree, StandardCopyOption.REPLACE_EXISTING);
						System.out.println("***Files copied to ***" + p.toString());

						p = Files.move(pathEntree, pathDestination, StandardCopyOption.REPLACE_EXISTING);
						System.out.println("***Files copied to ***" + p.toString());

						nbrFichCopieCompta++;	

						nbrValeursDeposeesComptaAllerLocal = fileLines.size() - 1;

						nbrValeursDeposeesCompta = nbrValeursDeposeesCompta + nbrValeursDeposeesComptaAllerLocal;

						traitementTourCompense.setSortTraitement(SortTraitement.SUCCES);
						ViewHelper.virementsRecManagerDAOLocal.save(traitementTourCompense);

					}

					//listFichierSupprimePourDoublonsEntreFichiers.contains(newFileName) || listFileNamesWithDoublons.contains(newFileName)
					//if(listFichierContenantDoublonsEntreFichiers.contains(newFileName) || listFileNamesWithDoublons.contains(newFileName) || listFichierACopierPourDoublonsEntreFichiers.contains(newFileName)){
					/**boolean copiedToArchiveEntree;
						for(String s: listFichierContenantDoublonsEntreFichiers){

							if(!listFileNamesWithDoublons.contains(s) && !listFichierACopierPourDoublonsEntreFichiers.contains(s) ){

								String file = pathEntree.getParent() + File.separator + s;

								System.out.println("***File contenant Doublons Entre Fichier***" +file);


								Path p;


								copiedToArchiveEntree = false;
								if(pathEntree.getFileName().toString().equals(s)){
									if(!listFichierACopierPourDoublonsEntreFichiers.contains(newFileName)){
										p = Files.move(pathEntree, pathArchiveEntree, StandardCopyOption.REPLACE_EXISTING);
										System.out.println("***Files copied to ***" + p.toString());
										copiedToArchiveEntree=true;
									}
								}else{
									//Ca veut dire qu'on avait déjà copier le mauvais fichier dans archive_entrée, et donc ce fichier actuel est le bon et doit être copier dans repDest. Et ce cas arrivera seulement si le bon fichier était aussi dans le repEntree

									//On vérifie d'abord que le fichier est dans archive_entrée
									if(pathArchiveEntree.getFileName().toString().equals(s)){  //Si c'est le cas on copie plutôt dans Dest

										p = Files.copy(pathEntree, pathArchiveEntree, StandardCopyOption.REPLACE_EXISTING);
										System.out.println("***Files copied to ***" + p.toString());

										p = Files.move(pathEntree, pathDestination, StandardCopyOption.REPLACE_EXISTING);
										System.out.println("***Files copied to ***" + p.toString());

										nbrFichCopieCompta++;	

										nbrValeursDeposeesComptaAllerLocal = fileLines.size() - 1;

										nbrValeursDeposeesCompta = nbrValeursDeposeesCompta + nbrValeursDeposeesComptaAllerLocal;

									}
									else{ //Sinon on copie dans ArchiveEntrée
										if(!listFichierACopierPourDoublonsEntreFichiers.contains(newFileName)){
											p = Files.move(pathEntree, pathArchiveEntree, StandardCopyOption.REPLACE_EXISTING);
											System.out.println("***Files copied to ***" + p.toString());
										}
									}
								}
								//}

								traitementTourCompense.setFichiersSupprime(s);
								ViewHelper.virementsRecManagerDAOLocal.update(traitementTourCompense);

							}
						}*/

					for(String t:listFileNamesWithDoublons){
						if(pathEntree.getFileName().toString().equals(t)){
							Path p = Files.move(pathEntree, pathArchiveEntree, StandardCopyOption.REPLACE_EXISTING);
							System.out.println("***Files copied to ***" + p.toString());
						}
					}

					for(String s: listFichierACopierPourDoublonsEntreFichiers){
						if(pathEntree.getFileName().toString().equals(s)){ 

							Path p = Files.copy(pathEntree, pathArchiveEntree, StandardCopyOption.REPLACE_EXISTING);
							System.out.println("***Files copied to ***" + p.toString());

							p = Files.move(pathEntree, pathDestination, StandardCopyOption.REPLACE_EXISTING); //pathEntree
							System.out.println("***Files copied to ***" + p.toString());

							nbrFichCopieCompta++;	

							nbrValeursDeposeesComptaAllerLocal = fileLines.size() - 1;

							nbrValeursDeposeesCompta = nbrValeursDeposeesCompta + nbrValeursDeposeesComptaAllerLocal;
						}
					}

					for(String s: listFichierANePasCopierPourDoublonsEntreFichiers){
						if(pathEntree.getFileName().toString().equals(s)){

							Path p = Files.move(pathEntree, pathArchiveEntree, StandardCopyOption.REPLACE_EXISTING);
							System.out.println("***Files copied to ***" + p.toString());

							/**p = Files.move(pathEntree, pathDestination, StandardCopyOption.REPLACE_EXISTING); //pathEntree
								System.out.println("***Files copied to ***" + p.toString());

								nbrFichCopieCompta++;	

								nbrValeursDeposeesComptaAllerLocal = fileLines.size() - 1;

								nbrValeursDeposeesCompta = nbrValeursDeposeesCompta + nbrValeursDeposeesComptaAllerLocal;*/
						}
					}

					//}
				}

				fileLines.clear();

				System.out.println(" -----------listFichierContenantDoublonsEntreFichiers.size--------zzzzzzz---------" + listFichierContenantDoublonsEntreFichiers.size());
				if(!listFichierContenantDoublonsEntreFichiers.isEmpty())System.out.println(" -----------listFichierContenantDoublonsEntreFichiers.get(0)--------zzzzzz----------" + listFichierContenantDoublonsEntreFichiers.get(0));

			}




			/**if(typeProcess.equals(TypeProcess.RAPATRIEMENT_IMG_ALLER)){
				nbrFichACopierComptaAller = aCopier;
				nbrFichCopieComptaAller = copier;
				nbrDoublonsComptaAller = doublons;
				nbrFichContenantDoublonsComptaAller = fichContenantDoublons;
				nbrValeursEnDoubleComptaAller =  valeursEnDouble;
				nbrValeursDeposeesComptaAller = valeursDeposees;

			}else if(typeProcess.equals(TypeProcess.RAPATRIEMENT_IMG_RETOUR)){
				nbrFichACopierComptaRetour = aCopier;
				nbrFichCopieComptaRetour = copier;
				nbrDoublonsComptaRetour = doublons;
				nbrFichContenantDoublonsComptaRetour = fichContenantDoublons ;
				nbrValeursEnDoubleComptaRetour =  valeursEnDouble;
				nbrValeursDeposeesComptaRetour = valeursDeposees;
			}*/


		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;

	}





	/***public boolean traitementEtCopieFichiersComptaRetour(File[] listFiles, String fichierTraite, Path pathEntree, Path pathDestination, Path pathArchiveEntree, TourCompensation tourCompensation, TypeTraitement typeTraitement, List<ParametragesCaracteresSpeciaux>parametragesCaracteresSpeciauxs, Boolean fichiersDestination, Map<String, List<String>> mapFichiersDestEtContenus){
		try {

			TraitementTourCompensation traitementTourCompense = new TraitementTourCompensation();
			traitementTourCompense.setUtiTraitement(user.getLogin());
			traitementTourCompense.setTypeTraitement(typeTraitement);
			traitementTourCompense.setDateTraitement(new Date());
			traitementTourCompense.setHeure(new SimpleDateFormat("HH:mm:ss").format(new Date()));
			traitementTourCompense.setFichiersTraite(fichierTraite);
			traitementTourCompense.setTourCompensation(tourCompensation);

			String newFileName = pathEntree.getFileName().toString(); 

			if(typeTraitement.equals(TypeTraitement.DOUBLONS_NOM_FICHIER)){

				/**Controler les doublons entre fichiers du rep de destination et fichiers du rep d'entrée**
				if(fichiersDestination==true){

					for(File f: listFiles){

						if(f.getName().equals(newFileName)){
							System.out.println("***Doublons trouvé******" + f.getName());
							nomFichierDoublon = true;
							nbrDoublonsComptaRetour++;
							listFileNamesWithDoublons.add(newFileName);
							traitementTourCompense.setSortTraitement(SortTraitement.ECHEC);
							ViewHelper.virementsRecManagerDAOLocal.save(traitementTourCompense);

						}

					}


					if(listFileNamesWithDoublons.isEmpty()){
						traitementTourCompense.setSortTraitement(SortTraitement.SUCCES);
						ViewHelper.virementsRecManagerDAOLocal.save(traitementTourCompense);
					}


				}else{


					traitementTourCompense.setSortTraitement(SortTraitement.SUCCES);
					ViewHelper.virementsRecManagerDAOLocal.save(traitementTourCompense);

				}


			}else if(typeTraitement.equals(TypeTraitement.DOUBLONS_ENTRE_FICHIER)){

				List<String> fileLines = new ArrayList<String>();
				List<String> fileLinesFormatted = new ArrayList<String>();

				int count1=0;
				int count2=0;
				for(String key:mapFichiersDestEtContenus.keySet()){

					fileLines = readAndScanFile(pathEntree);
					for(String s: fileLines){
						fileLinesFormatted.add(s.trim());
					}
					if(!fileLinesFormatted.isEmpty()){

						List<String> mapContent = mapFichiersDestEtContenus.get(key);
						List<String> mapContentFormatted = new ArrayList<String>();
						for(String m: mapContent){
							mapContentFormatted.add(m.trim());
						}
						System.out.println("mapContentFormatted " + mapContentFormatted + "size " + mapContentFormatted.size());
						//Si le contenu du fichier du repertoire entrée est contenu dans le fichier parcouru du répertoire de destination
						if(mapContentFormatted.containsAll(fileLinesFormatted) ){


							count1++;

							listFichierContenantDoublonsEntreFichiers.add(fichierTraite);

							listFichierSupprimePourDoublonsEntreFichiers.add(fichierTraite); //mauvais fichier
							listFichierSupprimePourDoublonsEntreFichiers.add(key); //le bon fichier pour garder la trace dans les rapports



						}

						/*****else if(fileLines.containsAll(mapFichiersDestEtContenus.get(key))){ //le contenu du fichier du répertoire de destination est contenu dans le fichier d'entrée

							count2++;

							File f = new File(pathDestination.toString());
							f.delete(); //on supprime le fichier du répertoire de destination

							traitementTourCompense.setFichiersSupprime(key);

							listFichierSupprimePourDoublonsEntreFichiers.add(key); //mauvais fichier
							listFichierSupprimePourDoublonsEntreFichiers.add(fichierTraite); //le bon fichier pour garder la trace dans les rapports
						}***
					}

				}

				if(count1>0 || count2>0){

					traitementTourCompense.setSortTraitement(SortTraitement.ECHEC);
					ViewHelper.virementsRecManagerDAOLocal.save(traitementTourCompense);

				}else{

					traitementTourCompense.setSortTraitement(SortTraitement.SUCCES);
					ViewHelper.virementsRecManagerDAOLocal.save(traitementTourCompense);
				}

			}else if(typeTraitement.equals(TypeTraitement.DOUBLONS_DANS_FICHIER)){

				/***Vérification des doublons en termes de valeurs contenus dans le fichier de comptabilisation ENV***
				System.out.println("Fichier Traité " + fichierTraite);

				listFichierEtValEnDouble.clear();

				boolean charSpeciauxFound = false;


				if(pathEntree!=null){

					Scanner scanner = null;
					try{
						scanner =  new Scanner(pathEntree);
					}
					catch(Exception e){

						System.out.println(e.getMessage());
						e.printStackTrace();
					}

					List<String> fileLines = new ArrayList<String>();

					String scLine = "";

					int j=0;

					if(scanner!=null){

						while(scanner.hasNextLine()){

							scLine = scanner.nextLine();

							if(!scLine.isEmpty()&&!scLine.equals("")){

								fileLines.add(scLine);

							}
							j++;
							System.out.println("***READ AND GOT LINE "+ j);
						}

						scanner.close();

						int l;
						int nbrValeursEnDoubleComptaRetourLocal=0;
						int nbrValeursDeposeesComptaRetourLocal=0;

						//Le prermier élément dans la liste est le nom du fichier traité
						listFichierEtValEnDouble.add(fichierTraite);

						List<String> aLines =  new ArrayList<String>();

						for(int k=0; k<fileLines.size(); k++){

							l=k+1;	

							//On compare chaque ligne k avec toutes les autres lignes de k+1 en montant pour vérifier s'il y a un ou plusieurs doublons
							while(l<fileLines.size()){

								if(fileLines.get(k).equals(fileLines.get(l))){

									/***Noter le valeurs en double et le nom du fichier contenant ces valeurs**

									nbrValeursEnDoubleComptaRetour++;
									nbrValeursEnDoubleComptaRetourLocal++;

									//On ajoute la valeur en double
									listFichierEtValEnDouble.add(fileLines.get(l));

									System.out.println("***Le doublon c'est la ligne " + l);

								}

								l++;
							}

							/*******Traitement des caractères spéciaux******
							aLines.add(fileLines.get(k));
							String newLine="";
							for(ParametragesCaracteresSpeciaux p: parametragesCaracteresSpeciauxs){
								if(p.getValide().equals(Boolean.TRUE)){
									if(fileLines.get(k).contains(p.getCaractereSpecial().trim())){ //Si la ligne contient le caractère spécial

										if(!newLine.isEmpty())
											aLines.remove(newLine);
										else
											aLines.remove(fileLines.get(k));

										/****On remplace le caractère spécial par le caractère de remplacement****
										if(newLine.isEmpty())
											newLine = fileLines.get(k).replace(p.getCaractereSpecial().trim(), parametragesGenTeleCompense.getCaracterePourRemplacerCaractereSpecial().trim());
										else{
											newLine = newLine.replace(p.getCaractereSpecial().trim(), parametragesGenTeleCompense.getCaracterePourRemplacerCaractereSpecial().trim());
										}

										System.out.println("******************newLine**************" + newLine);


										aLines.add(newLine);

										charSpeciauxFound=true;

									}


								}

							}
							/*******Traitement des caractères spéciaux****

						}

						if(charSpeciauxFound==true)
							createNewFileWithNewLines(pathEntree, aLines);



						System.out.println("***listFichierEtValEnDouble size***" + listFichierEtValEnDouble.size());


						//S'il y avait au moins un doublon
						if(listFichierEtValEnDouble.size()>1){

							//on ajoute le nombre de valeurs en double
							////listFichierEtValEnDouble.add(String.valueOf(nbrValeursEnDoubleComptaRetourLocal));								


							//on ajoute le nombre de valeurs déposées
							///listFichierEtValEnDouble.add(String.valueOf(nbrValeursDeposeesComptaRetourLocal)); //les valeurs avec local à la fin sont pour le fichier genre le nombre de doublons pour un fichier particulier


							nbrFichContenantDoublonsComptaRetour++;

							traitementTourCompense.setSortTraitement(SortTraitement.ECHEC);
							ViewHelper.virementsRecManagerDAOLocal.save(traitementTourCompense);


						}
						else{

							if(!listFileNamesWithDoublons.contains(newFileName) && !listFichierSupprimePourDoublonsEntreFichiers.contains(newFileName)){
								Path p = Files.move(pathEntree, pathDestination, StandardCopyOption.REPLACE_EXISTING);
								System.out.println("***Files copied to ***" + p.toString());

								nbrFichCopieComptaRetour++;

								nbrValeursDeposeesComptaRetourLocal = fileLines.size() - 1;
								nbrValeursDeposeesComptaRetour = nbrValeursDeposeesComptaRetour + nbrValeursDeposeesComptaRetourLocal;
							}

							traitementTourCompense.setSortTraitement(SortTraitement.SUCCES);
							ViewHelper.virementsRecManagerDAOLocal.save(traitementTourCompense);


						}

						if(listFichierSupprimePourDoublonsEntreFichiers.contains(newFileName) && listFichierContenantDoublonsEntreFichiers.contains(newFileName)){

							for(String s: listFichierContenantDoublonsEntreFichiers){

								String file = pathEntree.getParent() + File.separator + s;

								/*File f = new File(file);
								f.delete(); //on supprime le fichier du répertoire d'entrée****

								Path p = Files.move(pathEntree, pathArchiveEntree, StandardCopyOption.REPLACE_EXISTING);
								System.out.println("***Files copied to ***" + p.toString());


								traitementTourCompense.setFichiersSupprime(s);
								ViewHelper.virementsRecManagerDAOLocal.update(traitementTourCompense);

							}

						}

						fileLines.clear();

					}
				}

			}
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;

	}*/


	public void createNewFileWithNewLines(Path path, List<String> aLines){

		/*File f = new File(aFileName);
		System.out.println("***file length before*****" + f.length());

		Path path = null;
		try{
			path = Paths.get(aFileName);
		}catch(InvalidPathException ipe){
			System.out.println(ipe.getMessage());
			ipe.printStackTrace();
		}*/

		File file = new File(path.toString());
		file.delete(); //On supprime d'abord l'existant avant de le recréer

		if(path!=null){
			try(BufferedWriter writer = Files.newBufferedWriter(path, ENCODING)){

				for(String line : aLines){

					writer.write(line);
					//System.out.println(line);
					writer.newLine();
				}

				writer.close();

				System.out.println("***WRITTEN IN FILE***");

			}catch(Exception e){

				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}


	public void archiveFichiers(String fichierTraite, Path pathDestination, Path pathArchive, TourCompensation tourCompensation){
		try {

			TraitementTourCompensation traitementTourCompense = new TraitementTourCompensation();
			traitementTourCompense.setUtiTraitement(user.getLogin());
			traitementTourCompense.setTypeTraitement(TypeTraitement.ARCHIVE);
			traitementTourCompense.setDateTraitement(new Date());
			traitementTourCompense.setHeure(new SimpleDateFormat("HH:mm:ss").format(new Date()));
			traitementTourCompense.setFichiersTraite(fichierTraite);
			traitementTourCompense.setTourCompensation(tourCompensation);


			Path p = Files.copy(pathDestination, pathArchive, StandardCopyOption.REPLACE_EXISTING); //move(
			System.out.println("***Files copied to ***" + p.toString());


			traitementTourCompense.setSortTraitement(SortTraitement.SUCCES);
			ViewHelper.virementsRecManagerDAOLocal.save(traitementTourCompense);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void saveTraitement(TypeProcess typeProcess, TypeTraitement typeTraitement, String fichiersTraite, TourCompensation tourCompensation){

		if(typeProcess.equals(TypeProcess.RAPATRIEMENT_IMG_ALLER)){

			//Date dateTraite = new Date();

			/**RapatriementImagesAller rapatriementImagesAller = new RapatriementImagesAller();
			rapatriementImagesAller.setDateTraitement(dateTraite);
			rapatriementImagesAller.setUtiTraitement(user.getLogin());
			ViewHelper.virementsRecManagerDAOLocal.save(rapatriementImagesAller);

			List<RapatriementImagesAller> rapatriementImagesAllers = ViewHelper.virementsRecManager.filterRapatriementImageAller(dateTraite, user.getLogin());
			rapatriementImagesAller = rapatriementImagesAllers.get(0);*/
			//if(rapatriementImagesAllers!=null&&!rapatriementImagesAllers.isEmpty()){

			tourCompensation.setHeure(new SimpleDateFormat("HH:mm:ss").format(new Date()));
			tourCompensation.setFichiersTraite(fichiersTraite);
			//tourCompensation.setRapatriementImagesAller(rapatriementImagesAller);
			tourCompensation.setNbrFichiersACopiers(nbrFichACopierImg);
			tourCompensation.setNbrFichiersCopies(nbrFichCopieImg);
			tourCompensation.setNbrDoublons(nbrDoublonsImg);

			ViewHelper.virementsRecManagerDAOLocal.update(tourCompensation);

			//}

		}


		if(typeProcess.equals(TypeProcess.RAPATRIEMENT_IMG_RETOUR)){

			//Date dateTraite = new Date();

			/**RapatriementImagesRetour rapatriementImagesRetour = new RapatriementImagesRetour();
			rapatriementImagesRetour.setDateTraitement(dateTraite);
			rapatriementImagesRetour.setUtiTraitement(user.getLogin());

			ViewHelper.virementsRecManagerDAOLocal.save(rapatriementImagesRetour);


			List<RapatriementImagesRetour> rapatriementImagesRetours = ViewHelper.virementsRecManager.filterRapatriementImageRetour(dateTraite, user.getLogin());
			rapatriementImagesRetour = rapatriementImagesRetours.get(0);*/
			//if(rapatriementImagesRetours!=null&&!rapatriementImagesRetours.isEmpty()){

			tourCompensation.setHeure(new SimpleDateFormat("HH:mm:ss").format(new Date()));
			tourCompensation.setFichiersTraite(fichiersTraite);
			//tourCompensation.setRapatriementImagesRetour(rapatriementImagesRetour);
			tourCompensation.setNbrFichiersACopiers(nbrFichACopierImg);
			tourCompensation.setNbrFichiersCopies(nbrFichCopieImg);
			tourCompensation.setNbrDoublons(nbrDoublonsImg);

			ViewHelper.virementsRecManagerDAOLocal.update(tourCompensation);
			//}

		}



		if(typeProcess.equals(TypeProcess.FICHIER_COMPTA_ALLER)){

			/**Date dateTraite = new Date();

			FichiersComptabilisationAller fichiersComptaAller = new FichiersComptabilisationAller();
			fichiersComptaAller.setDateTraitement(dateTraite);
			fichiersComptaAller.setUtiTraitement(user.getLogin());
			ViewHelper.virementsRecManagerDAOLocal.save(fichiersComptaAller);


			List<FichiersComptabilisationAller> fichiersComptaAllers = ViewHelper.virementsRecManager.filterFichierComptaAller(dateTraite, user.getLogin());
			fichiersComptaAller = fichiersComptaAllers.get(0);*/
			//if(fichiersComptaAllers!=null&&!fichiersComptaAllers.isEmpty()){

			tourCompensation.setHeure(new SimpleDateFormat("HH:mm:ss").format(new Date()));
			tourCompensation.setFichiersTraite(fichiersTraite);
			//tourCompensation.setFichiersComptabilisationAller(fichiersComptaAller);
			tourCompensation.setNbrFichiersACopiers(nbrFichACopierCompta);
			tourCompensation.setNbrFichiersCopies(nbrFichCopieCompta);
			tourCompensation.setNbrDoublons(nbrDoublonsCompta);
			tourCompensation.setNbrFichContenantDoublons(nbrFichContenantDoublonsCompta);
			tourCompensation.setNbrValeursDeposees(nbrValeursDeposeesCompta);
			tourCompensation.setNbrValeursEnDouble(nbrValeursEnDoubleCompta);


			ViewHelper.virementsRecManagerDAOLocal.update(tourCompensation);

			//}

		}


		if(typeProcess.equals(TypeProcess.FICHIER_COMPTA_RETOUR)){

			/**Date dateTraite = new Date();

			FichiersComptabilisationRetour fichiersComptaRetour = new FichiersComptabilisationRetour();
			fichiersComptaRetour.setDateTraitement(dateTraite);
			fichiersComptaRetour.setUtiTraitement(user.getLogin());
			ViewHelper.virementsRecManagerDAOLocal.save(fichiersComptaRetour);


			List<FichiersComptabilisationRetour> fichiersComptaRetours = ViewHelper.virementsRecManager.filterFichierComptaRetour(dateTraite, user.getLogin());
			fichiersComptaRetour = fichiersComptaRetours.get(0);*/
			//if(fichiersComptaRetours!=null&&!fichiersComptaRetours.isEmpty()){

			tourCompensation.setHeure(new SimpleDateFormat("HH:mm:ss").format(new Date()));
			tourCompensation.setFichiersTraite(fichiersTraite);
			//tourCompensation.setFichiersComptabilisationRetour(fichiersComptaRetour);
			tourCompensation.setNbrFichiersACopiers(nbrFichACopierCompta);
			tourCompensation.setNbrFichiersCopies(nbrFichCopieCompta);
			tourCompensation.setNbrDoublons(nbrDoublonsCompta);
			tourCompensation.setNbrFichContenantDoublons(nbrFichContenantDoublonsCompta);
			tourCompensation.setNbrValeursDeposees(nbrValeursDeposeesCompta);
			tourCompensation.setNbrValeursEnDouble(nbrValeursEnDoubleCompta);

			ViewHelper.virementsRecManagerDAOLocal.update(tourCompensation);

			//}

		}

	}


	public void doArchivage(String repDestination, String repArchive, TourCompensation tourCompensation, TypeProcess typeProcess){

		try{

			File folderDest = new File(repDestination);
			if(!folderDest.exists()){
				if(folderDest.mkdirs()){
					System.out.println("*****Les répertoires parents sont crées avec succès!******");
				} else {
					System.out.println("******Echec de création des répertoires parents!*****");
				}
			}
			System.out.println("****folderDest*****" + folderDest);


			File folderArch = new File(repArchive);
			if(!folderArch.exists()){
				if(folderArch.mkdirs()){
					System.out.println("*****Les répertoires parents sont crées avec succès!******");
				} else {
					System.out.println("******Echec de création des répertoires parents!*****");
				}
			}
			System.out.println("****folderArch*****" + folderArch);

			/**Parcours du rep de destination***/
			File[] listFiles = folderDest.listFiles();
			if(listFiles==null || listFiles.length==0){

				System.out.println("Aucun fichier trouvé dans le répertoire de destination !!!");
				this.error=true;
				this.information=true;
				ExceptionHelper.showError("Aucun fichier trouvé dans le répertoire de destination ! !!!", this);

				return;
			}


			File [] listFiles2 = folderArch.listFiles();
			System.out.println("listFiles2.length  " + listFiles2.length);
			if(listFiles2==null || listFiles2.length>0){
				//le rep d'archive est vide; on ne fait rien
				System.out.println("le rep d'archive est vide; on ne fait rien");
			}else{
				//le rep d'archive n'est pas vide; on appelle le process de suppression manuelle
				/******processSuppression();***/
			}


			for (int i = 0; i < listFiles.length; i++) {
				if (listFiles[i].isFile()) {

					nbrFichAArchiver++;

					System.out.println("File " + listFiles[i].getName());

					String fichierDest = repDestination + File.separator + listFiles[i].getName();

					String fichierArch = repArchive + File.separator + listFiles[i].getName();

					System.out.println("****fichierDest*****" + fichierDest);
					System.out.println("****fichierArch*****" + fichierArch);


					Path pathDestination = Paths.get(fichierDest);

					Path pathArchive = Paths.get(fichierArch);

					/**On récupère l'élément le plus récent du rép de destination**/


					archiveFichiers(listFiles[i].getName(), pathDestination, pathArchive, tourCompensation);
					nbrFichArchive++;

				}
				else
					System.out.println("***File is not normal***");
			}

			//Il y a eu archive
			if(nbrFichArchive>0){ 

				this.information=true;
				ExceptionHelper.showInformation("Archivage terminé avec succès !!! Copie des fichiers du répertoire de destination vers le repertoire d'archives !!!", this);

			}

			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("uti", user.getLogin());
			param.put("nbrFichAArchiver", nbrFichAArchiver);
			param.put("nbrFichArchives", nbrFichArchive);
			param.put("TotalArchives", listFiles.length);


			if(typeProcess.equals(TypeProcess.RAPATRIEMENT_IMG_ALLER)){

				title="Rapport archivage des fichiers images phase Aller, le " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()) + " à " + new SimpleDateFormat("HH:mm:ss").format(new Date());
				msg = "Bonjour, " + "\t" + "\n" + "\t\t" + "Veuillez trouver ci-dessous le rapport des fichiers images de la phase Aller archivés ce jour : " + "\n\n";

				msg = msg + "Nombre de fichiers à archiver: " + nbrFichAArchiver + "\n\n"; 
				msg = msg + "Nombre de fichers archivées dans le répertoire d'archive: " + nbrFichArchive + "\n\n";
				msg = msg + "Un total de " + listFiles.length + " fichiers ont été archivés." + "\n\n";


				param.put("TypeProcess", "Images ALLER");
				List<RapatriementImagesAller> repports = new ArrayList<RapatriementImagesAller>();

				filesNames = new ArrayList<String>();
				filesPath = new ArrayList<String>();

				String fName = new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date());
				FileOutputStream fileOuputStream = new FileOutputStream(ViewHelper.getPiecesJointesDir() + "Rapport_Archive_Img_Aller_" + fName+".pdf");
				fileOuputStream.write(ViewHelper.virementsRecManager.getReportPDFBytes(ViewHelper.getReportsDir2().concat("rapportArchive.jasper"), param, repports));
				fileOuputStream.close();

				filesNames.add("Rapport_Archive_Img_Aller_" + fName+".pdf");
				filesPath.add(ViewHelper.getPiecesJointesDir() + "Rapport_Archive_Img_Aller_" + fName+".pdf");

				StatistiqueRapports stat = new StatistiqueRapports();
				stat.setRapport("Rapport_Archive_Img_Aller_" + fName+".pdf");
				stat.setTypeProcess(TypeProcess.RAPATRIEMENT_IMG_ALLER);
				ViewHelper.virementsRecManagerDAOLocal.save(stat);


				//SendMail.sendMail(filesNames, filesPath, mailsTo, mailsCC, mailsBCC, title, msg, ip, email, pass, port);
				try {
					List<ParamEmailAuto> parametres = ViewHelper.virementsRecManagerDAOLocal.filter(ParamEmailAuto.class, null, null, null, null, 0, -1);
					if(parametres!=null&&!parametres.isEmpty()){
						AfrilandSendMail.sendMail(filesNames, filesPath, mailsTo, mailsCC, title, msg, email, pass, email + "@afrilandfirstbank.com", "AFRILAND FIRST BANK", ip, String.valueOf(port));
					}
				} catch (Exception ex) {
					// TODO Auto-generated catch block
				}

			}if(typeProcess.equals(TypeProcess.FICHIER_COMPTA_ALLER)){

				title="Rapport archivage des fichiers de comptabilisation .ENV phase Aller, le " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()) + " à " + new SimpleDateFormat("HH:mm:ss").format(new Date());
				msg = "Bonjour, " + "\t" + "\n" + "\t\t" + "Veuillez trouver ci-dessous le rapport des fichiers de comptabilisation .ENV de la phase Aller archivés ce jour : " + "\n\n";

				msg = msg + "Nombre de fichiers à archiver: " + nbrFichAArchiver + "\n\n"; 
				msg = msg + "Nombre de fichers archivées dans le répertoire d'archive: " + nbrFichArchive + "\n\n";
				msg = msg + "Un total de " + listFiles.length + " fichiers ont été archivés." + "\n\n";


				param.put("TypeProcess", "Fichiers de Comptabilisation ALLER");

				List<FichiersComptabilisationAller> repports = new ArrayList<FichiersComptabilisationAller>();

				filesNames = new ArrayList<String>();
				filesPath = new ArrayList<String>();

				String fName = new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date());
				FileOutputStream fileOuputStream = new FileOutputStream(ViewHelper.getPiecesJointesDir() + "Rapport_Archive_Compta_Aller_" + fName+".pdf");
				fileOuputStream.write(ViewHelper.virementsRecManager.getReportPDFBytes(ViewHelper.getReportsDir2().concat("rapportArchive.jasper"), param, repports));
				fileOuputStream.close();

				filesNames.add("Rapport_Archive_Compta_Aller_" + fName+".pdf");
				filesPath.add(ViewHelper.getPiecesJointesDir() + "Rapport_Archive_Compta_Aller_" + fName+".pdf");

				StatistiqueRapports stat = new StatistiqueRapports();
				stat.setRapport("Rapport_Archive_Compta_Aller_" + fName+".pdf");
				stat.setTypeProcess(TypeProcess.FICHIER_COMPTA_ALLER);
				ViewHelper.virementsRecManagerDAOLocal.save(stat);


				//SendMail.sendMail(filesNames, filesPath, mailsTo, mailsCC, mailsBCC, title, msg, ip, email, pass, port);
				try {
					List<ParamEmailAuto> parametres = ViewHelper.virementsRecManagerDAOLocal.filter(ParamEmailAuto.class, null, null, null, null, 0, -1);
					if(parametres!=null&&!parametres.isEmpty()){
						AfrilandSendMail.sendMail(filesNames, filesPath, mailsTo, mailsCC, title, msg, email, pass, email + "@afrilandfirstbank.com", "AFRILAND FIRST BANK", ip, String.valueOf(port));
					}
				} catch (Exception ex) {
					// TODO Auto-generated catch block
				}

			}if(typeProcess.equals(TypeProcess.RAPATRIEMENT_IMG_RETOUR)){

				title="Rapport archivage des fichiers images phase Retour, le " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()) + " à " + new SimpleDateFormat("HH:mm:ss").format(new Date());
				msg = "Bonjour, " + "\t" + "\n" + "\t\t" + "Veuillez trouver ci-dessous le rapport des fichiers images de la phase Retour archivés ce jour : " + "\n\n";

				msg = msg +"Nombre de fichiers à archiver: " + nbrFichAArchiver + "\n\n"; 
				msg = msg +"Nombre de fichers archivées dans le répertoire d'archive: " + nbrFichArchive + "\n\n";
				msg = msg + "Un total de " + listFiles.length + " fichiers ont été archivés." + "\n\n";

				param.put("TypeProcess", "Images RETOUR");

				List<RapatriementImagesRetour> repports = new ArrayList<RapatriementImagesRetour>();

				filesNames = new ArrayList<String>();
				filesPath = new ArrayList<String>();

				String fName = new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date());
				FileOutputStream fileOuputStream = new FileOutputStream(ViewHelper.getPiecesJointesDir() + "Rapport_Archive_Img_Retour_" + fName+".pdf");
				fileOuputStream.write(ViewHelper.virementsRecManager.getReportPDFBytes(ViewHelper.getReportsDir2().concat("rapportArchive.jasper"), param, repports));
				fileOuputStream.close();

				filesNames.add("Rapport_Archive_Img_Retour_" + fName+".pdf");
				filesPath.add(ViewHelper.getPiecesJointesDir() + "Rapport_Archive_Img_Retour_" + fName+".pdf");

				StatistiqueRapports stat = new StatistiqueRapports();
				stat.setRapport("Rapport_Archive_Img_Retour_" + fName+".pdf");
				stat.setTypeProcess(TypeProcess.RAPATRIEMENT_IMG_RETOUR);
				ViewHelper.virementsRecManagerDAOLocal.save(stat);


				//SendMail.sendMail(filesNames, filesPath, mailsTo, mailsCC, mailsBCC, title, msg, ip, email, pass, port);
				try {
					List<ParamEmailAuto> parametres = ViewHelper.virementsRecManagerDAOLocal.filter(ParamEmailAuto.class, null, null, null, null, 0, -1);
					if(parametres!=null&&!parametres.isEmpty()){
						AfrilandSendMail.sendMail(filesNames, filesPath, mailsTo, mailsCC, title, msg, email, pass, email + "@afrilandfirstbank.com", "AFRILAND FIRST BANK", ip, String.valueOf(port));
					}
				} catch (Exception ex) {
					// TODO Auto-generated catch block
				}

			}if(typeProcess.equals(TypeProcess.FICHIER_COMPTA_RETOUR)){

				title="Rapport archivage des fichiers de comptabilisation .RCP phase Retour, le " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()) + " à " + new SimpleDateFormat("HH:mm:ss").format(new Date());
				msg = "Bonjour, " + "\t" + "\n" + "\t\t" + "Veuillez trouver ci-dessous le rapport des fichiers de comptabilisation .RCP de la phase Retour archivés ce jour : " + "\n\n";

				msg = msg + "Nombre de fichiers à archiver: " + nbrFichAArchiver + "\n\n"; 
				msg = msg + "Nombre de fichers archivées dans le répertoire d'archive: " + nbrFichArchive + "\n\n";
				msg = msg + "Un total de " + listFiles.length + " fichiers ont été archivés." + "\n\n";

				param.put("TypeProcess", "Fichiers de Comptabilisation RETOUR");

				List<FichiersComptabilisationRetour> repports = new ArrayList<FichiersComptabilisationRetour>();


				filesNames = new ArrayList<String>();
				filesPath = new ArrayList<String>();

				String fName = new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date());
				FileOutputStream fileOuputStream = new FileOutputStream(ViewHelper.getPiecesJointesDir() + "Rapport_Archive_Compta_Retour_" + fName+".pdf");
				fileOuputStream.write(ViewHelper.virementsRecManager.getReportPDFBytes(ViewHelper.getReportsDir2().concat("rapportArchive.jasper"), param, repports));
				fileOuputStream.close();

				filesNames.add("Rapport_Archive_Compta_Retour_" + fName+".pdf");
				filesPath.add(ViewHelper.getPiecesJointesDir() + "Rapport_Archive_Compta_Retour_" + fName+".pdf");

				StatistiqueRapports stat = new StatistiqueRapports();
				stat.setRapport("Rapport_Archive_Compta_Retour_" + fName+".pdf");
				stat.setTypeProcess(TypeProcess.FICHIER_COMPTA_RETOUR);
				ViewHelper.virementsRecManagerDAOLocal.save(stat);


				//SendMail.sendMail(filesNames, filesPath, mailsTo, mailsCC, mailsBCC, title, msg, ip, email, pass, port);
				try {
					List<ParamEmailAuto> parametres = ViewHelper.virementsRecManagerDAOLocal.filter(ParamEmailAuto.class, null, null, null, null, 0, -1);
					if(parametres!=null&&!parametres.isEmpty()){
						AfrilandSendMail.sendMail(filesNames, filesPath, mailsTo, mailsCC, title, msg, email, pass, email + "@afrilandfirstbank.com", "AFRILAND FIRST BANK", ip, String.valueOf(port));
					}
				} catch (Exception ex) {
					// TODO Auto-generated catch block
				}
			}

		}catch(Exception e){

			e.printStackTrace();
		}


	}

	public void archivage(){

		List<TourCompensation> listTourCompense = ViewHelper.virementsRecManager.filterTourCompensation(null, null, tour, typeProcess);
		if(listTourCompense!=null && !listTourCompense.isEmpty()){

			List<TraitementTourCompensation> traitementTourCompensations = ViewHelper.virementsRecManager.filterTraitementTourCompensation(TypeTraitement.ARCHIVE,listTourCompense.get(0),null);
			if(traitementTourCompensations!=null&&!traitementTourCompensations.isEmpty()){

				ExceptionHelper.showError("L'archivage pour ce tour de compensation " + tour + " a déjà été effectué pour ce type de process: " + typeProcess , this);
				//this.open = false;
				this.error = true;
				this.information = true;
				System.out.println("****L'archivage pour ce tour de compensation " + tour + " a déjà été effectué pour ce type de process: " + typeProcess);
				return;
			}

		}

		TourCompensation tourCompensation =  new TourCompensation();
		List<TourCompensation>toursCompensation = ViewHelper.virementsRecManager.filterTourCompensation(new Date(), null, tour, typeProcess);

		if(toursCompensation!=null &&!toursCompensation.isEmpty()){
			tourCompensation = toursCompensation.get(0);
			System.out.println("***Tour compensation***" + tourCompensation.getId());
		}else{
			this.error=true;
			this.information=true;
			ExceptionHelper.showError("La copie des fichiers pour ce type de process n'a pas encore été effectué!!! Bien vouloir lancer le traitement!!!", this);
			return;
		}

		if(typeProcess.equals(TypeProcess.RAPATRIEMENT_IMG_ALLER)){


			nbrFichAArchiver = 0;
			nbrFichArchive = 0;


			/**Récupération des différents répertoires**/
			String repDestinationImageAller = parametragesGenTeleCompense.getRepertoireDestinationCollecteImageAller();
			String repArchiveImageAller = parametragesGenTeleCompense.getRepertoireArchivageCollecteImageAller();


			doArchivage(repDestinationImageAller, repArchiveImageAller, tourCompensation, typeProcess);

		}


		if(typeProcess.equals(TypeProcess.FICHIER_COMPTA_ALLER)){

			nbrFichAArchiver = 0;
			nbrFichArchive = 0;

			/**Récupération des différents répertoires**/
			String repDestinationFichCompteAller  = parametragesGenTeleCompense.getRepertoireDestinationFichierComptabilisationAller();
			String repArchiveFichCompteAller  = parametragesGenTeleCompense.getRepertoireArchivageFichierComptabilisationAller();

			doArchivage(repDestinationFichCompteAller, repArchiveFichCompteAller, tourCompensation, typeProcess);

		}


		if(typeProcess.equals(TypeProcess.RAPATRIEMENT_IMG_RETOUR)){

			nbrFichAArchiver = 0;
			nbrFichArchive = 0;


			/**Récupération des différents répertoires**/
			String repDestinationImageRetour = parametragesGenTeleCompense.getRepertoireDestinationCollecteImageRetour();
			String repArchiveImageRetour = parametragesGenTeleCompense.getRepertoireArchivageCollecteImageRetour();

			doArchivage(repDestinationImageRetour, repArchiveImageRetour, tourCompensation, typeProcess);


		}else if(typeProcess.equals(TypeProcess.FICHIER_COMPTA_RETOUR)){

			nbrFichAArchiver = 0;
			nbrFichArchive = 0;


			/**Récupération des différents répertoires**/
			String repDestinationFichCompteRetour  = parametragesGenTeleCompense.getRepertoireDestinationFichierComptabilisationRetour();
			String repArchiveFichCompteRetour  = parametragesGenTeleCompense.getRepertoireArchivageFichierComptabilisationRetour();

			doArchivage(repDestinationFichCompteRetour, repArchiveFichCompteRetour, tourCompensation, typeProcess);

		}

	}


	public void openTraitementDialog(){

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
		return "Traitements";
	}

	@Override
	public String getFileDefinition() {
		// TODO Auto-generated method stub
		return "/views/virementsRec/TraitementDialog.xhtml";
	}


	public String getMsgFinTraitement() {
		return msgFinTraitement;
	}

	public void setMsgFinTraitement(String msgFinTraitement) {
		this.msgFinTraitement = msgFinTraitement;
	}

	public boolean isStatutTraitement() {
		return statutTraitement;
	}

	public void setStatutTraitement(boolean statutTraitement) {
		this.statutTraitement = statutTraitement;
	}

	public List<ParamEmail> getEmails() {
		return emails;
	}


	public void setEmails(List<ParamEmail> emails) {
		this.emails = emails;
	}


	public int getIndex() {
		return index;
	}


	public void setIndex(int index) {
		this.index = index;
	}


	public void setDeleUser(ParamEmail deleUser) {
		this.deleUser = deleUser;
	} 


	public List<SelectItem> getTypeProcessItems() {
		return typeProcessItems;
	}


	public void setTypeProcessItems(List<SelectItem> typeProcessItems) {
		this.typeProcessItems = typeProcessItems;
	}


	public List<SelectItem> getTourItems() {
		return tourItems;
	}


	public void setTourItems(List<SelectItem> tourItems) {
		this.tourItems = tourItems;
	}


	public Integer getTour() {
		return tour;
	}


	public void setTour(Integer tour) {
		this.tour = tour;
	}


	public Selection getSelection() {
		return selection;
	}


	public void setSelection(Selection selection) {
		this.selection = selection;
	}


	public TypeProcess getTypeProcess() {
		return typeProcess;
	}


	public void setTypeProcess(TypeProcess typeProcess) {
		this.typeProcess = typeProcess;
	}


	public ParamEmail getDeleUser() {
		return deleUser;
	}


	@SuppressWarnings("unchecked")
	@Override
	public ParametragesGenTeleCompense getCurrentObject() {
		// TODO Auto-generated method stub
		return null; //this.currentObject;
	}

	@Override
	public boolean checkBuildedCurrentObject() {
		// TODO Auto-generated method stub


		return true;
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

	public void initValues(){

		///nbrFichACopierComptaTotal = 0;  //On n'a pas besoin de réinitialiser ceci, durant toute la session

		nbrFichACopierCompta = 0;

		nbrFichCopieCompta = 0;

		nbrDoublonsCompta = 0;

		nbrFichDoublonsEntreFichiers = 0;

		nbrValeursEnDoubleCompta = 0;

		nbrValeursDeposeesCompta = 0;

		nbrFichContenantDoublonsCompta = 0;

	}

	@Override
	public void initComponents() {
		// TODO Auto-generated method stub

		System.out.println("**Chargement des types de Process**");

		typeProcessItems.add(new SelectItem(null, "---Choisir---"));
		for (TypeProcess val : TypeProcess.types()){
			typeProcessItems.add(new SelectItem(val.name(), val.getValue()));
		}


		user = ViewHelper.getSessionUser();

		List<ParametragesGenTeleCompense> listParams = ViewHelper.virementsRecManager.filterParamGen();
		if(listParams!=null && !listParams.isEmpty()){

			//currentObject = listParams.get(0);
			parametragesGenTeleCompense = listParams.get(0);

			if(parametragesGenTeleCompense.getListEmails()!=null && !parametragesGenTeleCompense.getListEmails().isEmpty()){

				for(ParamEmail pe: parametragesGenTeleCompense.getListEmails()){
					if(pe.getValide().equals(Boolean.TRUE))
						mailsTo.add(pe.getEmail());
				}
			}

			mailsCC = new ArrayList<String>();

			mailsBCC = new ArrayList<String>();


			if(ViewHelper.virementsRecManager.filterParamEmailAuto()!=null && !ViewHelper.virementsRecManager.filterParamEmailAuto().isEmpty()){
				for(ParamEmailAuto p: ViewHelper.virementsRecManager.filterParamEmailAuto()){
					ip = p.getIp();
					email = p.getEmail();
					pass = Encrypter.getInstance().decryptText(p.getPass());
					port = p.getPort();
				}
			}
		}

		List<Parametrages> listParams2 = ViewHelper.virementsRecManager.filterParams();
		if(listParams2!=null && !listParams2.isEmpty()){
			parametrages = listParams2.get(0);
		}

		emails = ViewHelper.virementsRecManager.filterEmails();

		List<ParamCompensateurCentrale> paramCompensateurCentrales = ViewHelper.virementsRecManagerDAOLocal.filter(ParamCompensateurCentrale.class, null, null, OrderContainer.getInstance().add(Order.desc("id")), null, 0, -1);

		if(paramCompensateurCentrales!=null&& !paramCompensateurCentrales.isEmpty()){

			tour = paramCompensateurCentrales.get(0).getTourActuel();

		}else{

			this.error = true;
			ExceptionHelper.showError("Le paramétrage de la compensation n'est pas effectuée !!!  Contactez le compensateur centrale !!!", this);
			return;
		}

		parametragesCaracteresSpeciauxs = ViewHelper.virementsRecManagerDAOLocal.filter(ParametragesCaracteresSpeciaux.class, null, null, OrderContainer.getInstance().add(Order.desc("id")), null, 0, -1);





	}


	@Override
	protected void buildCurrentObject() throws ParseException {

		//this.currentObject.setTypeCarac(this.caracVir);

	}



	@Override
	protected void validate() {

		/*this.currentObject = ViewHelper.virementsRecManager.updateParametrages(currentObject);


		for(CaracteristiquesVir c: caracVirToRemove){

			List<CaracteristiquesVir> listCar = ViewHelper.virementsRecManager.filterCaracteristiquesVir(null,c,null);
			if(listCar!=null&&!listCar.isEmpty()){
				listCar.get(0).setValide(Boolean.FALSE);
				ViewHelper.virementsRecManager.updateCaracteristiquesVir(listCar.get(0));
				System.out.println("Suppression de " + listCar.get(0).getCaracteristiquesItems() + " effectuée avec succes!");
			}
		}*/

	}

	@Override
	protected void disposeResourceOnClose() {

		typeProcessItems.clear();
		setEnabled(false);
		setButtonRendered(true);
		setCurrentValue(0);
		msgFinTraitement="";
		statutTraitement=false;

		listFileNamesWithDoublons.clear();

		listFichierEtValEnDouble.clear(); 

		listFichierSupprimePourDoublonsEntreFichiers.clear();
		
		mapFichierEtContenu.clear();

		mapFichierEtValEnDoubleComptaAllerTraite1.clear();

		listFichierContenantDoublonsEntreFichiers.clear();
		

		mailsTo.clear();
		mailsCC.clear();
		mailsBCC.clear();


	}

	public static void robotTraitementCompensation(){
		try{

			if(task3 != null )task3.cancel();
			if(timer3 != null )timer3.cancel();

			task3 = new TimerTask(){
				@Override
				public void run(){
					try{

						traitementAuto();

					}catch(Exception e){e.printStackTrace();}
				}	
			};

			timer3 = new java.util.Timer(true);

			int sec = 60 ;int min = 30;

			timer3.schedule(task3,DateUtils.addMinutes(new Date(), 1), min*sec*1000); //chaque 30 minutes

		}catch(Exception e){
			e.printStackTrace();
		}
	}


	public static void traitementAuto(){

		ParamCompensateurCentrale paramCompensateurCentrale = ViewHelper.virementsRecManager.filterLastParamCompensateurCentrale();

		if(paramCompensateurCentrale!=null){

			List<TourCompensation> listToursCompense =  paramCompensateurCentrale.getListToursCompensation();
			if(listToursCompense!=null){
				Collections.sort(listToursCompense);
			}

			if(listToursCompense!=null && !listToursCompense.isEmpty()){
				for(TourCompensation t: listToursCompense){

					//On suppose qu'en ouvrant une journée, les différents tours sont aussi sauvegardés avec leurs heures correspondantes

					//Si l'heure d'un tour de compensation est arrivée
					if(t.getHeure().equals(new SimpleDateFormat("HH:mm").format(new Date()))){

						int tourActuel = paramCompensateurCentrale.getTourActuel();

						if(paramCompensateurCentrale.getStatutJourneeEnCours().equals(StatutJournee.OUVERTURE)){

							if(tourActuel<paramCompensateurCentrale.getTourMax()){

								paramCompensateurCentrale.setTourActuel(tourActuel+1);

								paramCompensateurCentrale.setDateJourneeEnCours(paramCompensateurCentrale.getDateJournee());

								paramCompensateurCentrale.setStatutJourneeEnCours(paramCompensateurCentrale.getStatutJournee());

								paramCompensateurCentrale.setUtiTraitement("AUTO");

								ViewHelper.virementsRecManagerDAOLocal.update(paramCompensateurCentrale);

								//Exécution du traitement
								ViewHelper.virementsRecManager.traitement(ViewHelper.getPiecesJointesDir(), ViewHelper.getReportsDir2() );

							}else{
								System.err.println("*****Le nombre de tours autorisés est dépassé******");
							}
						}
					}
				}
			}
		}
	}





	/*public CaracteristiquesVir getDeleUser() {
		return deleUser;
	}

	public void setDeleUser(CaracteristiquesVir deleUser) {
		this.deleUser = deleUser;

		try {

			if(deleUser != null){

				System.out.println("caracVir " +deleUser.getCaracteristiquesItems());

				System.out.println("----caracVir size before remove----" + caracVir.size());

				//deleUser = getElement();

				System.out.println("----caracVir size to remove----" + deleUser.getCaracteristiquesItems());

				caracVir.remove(deleUser);

				caracVirToRemove.add(deleUser);

				System.out.println("----caracVir size after remove----" + caracVir.size());

				deleUser.setValide(Boolean.FALSE);

				//caracVir.add(deleUser);

				System.out.println("caracVir " +deleUser.getCaracteristiquesItems() + " enlevé avec succes!");

			}

		} catch (Exception e) {

			// Traitement de l'exception
			ExceptionHelper.threatException(e);
		}
	}*/


	/*public void ajouterParam() {

		try {

			Integer rang = 1;

			int size = 0;

			CaracteristiquesVir param = new CaracteristiquesVir();

			//List<CaracteristiquesVir>carVirs = ViewHelper.virementsRecManager.filterCaracteristiquesVir(null, null,typeParam);

			param.setCaracteristiquesItems(typeParam);

			boolean found = false;
			for(CaracteristiquesVir c: caracVir){

				if(c.getCaracteristiquesItems().getValue().equals(param.getCaracteristiquesItems().getValue())){
					found = true;
				}
			}


			if(found==false){

				if (this.caracVir == null) {

					this.caracVir = new ArrayList<CaracteristiquesVir>();

					rang = 1;

				} else if (this.caracVir != null && this.caracVir.size() > 0) {

					size = this.caracVir.size();

					rang = size + 1;
				}

				param.setRang(rang);

				param.setParam(currentObject);


				this.caracVir.add(param);
			}


			System.out.println("-----Rang is " + rang);

		} catch (Exception e) {

			// Etat d'erreur
			this.error = true;

			// Traitement de l'exception
			ExceptionHelper.threatException(e);
		}

	}
	 */


	/***
	 * 	<!-- <a4j:ajaxListener  event="begin" listener="#{traitementDialog.increment}" /> -->
	 * 
	 * 
	 * 
							<!-- Update the progress for each AJAX update -->

							<a4j:support actionListener="#{traitementDialog.increment}"
								event="onchange" ajaxSingle="false" immediate="true" />








									<h:form>
						<a4j:outputPanel id="progressPanel">
							<rich:progressBar mode="ajax" id="pp"
								value="#{traitementDialog.currentValue}" interval="500" 
								enabled="#{traitementDialog.enabled}" minValue="0"
								maxValue="100" reRender="pp">
								<f:facet name="initial">
									<h:panelGroup>
										<h:outputText value="Process hasn't started yet" />
										<a4j:commandButton action="#{traitementDialog.startProcess}"
											styleClass="ui-button ui-widget ui-state-default ui-corner-all"
											value="Démarrer Process" reRender="pp"
											rendered="#{traitementDialog.buttonRendered}"
											style="margin: 9px 0px 5px;" />
									</h:panelGroup>
								</f:facet>
								<f:facet name="finish">
									<h:panelGroup>
										<h:outputText value="Process Done" />
										<a4j:commandButton action="#{traitementDialog.startProcess}"
											styleClass="ui-button ui-widget ui-state-default ui-corner-all"
											value="Restart Process" reRender="pp"
											rendered="#{traitementDialog.buttonRendered}"
											style="margin: 9px 0px 5px;" />
									</h:panelGroup>
								</f:facet>
								<h:outputText value="#{traitementDialog.currentValue} %" />


							</rich:progressBar>
						</a4j:outputPanel>
					</h:form>








					<h:form>
						<a4j:outputPanel id="progressPanel2">
							<rich:progressBar mode="ajax" id="pp2"
								value="#{traitementDialog.currentValue}" interval="500" 
								enabled="#{traitementDialog.enabled}" minValue="0"
								maxValue="100" reRender="pp2">
								<f:facet name="initial2">
									<h:panelGroup>
										<h:outputText value="Process hasn't started yet" />
										<a4j:commandButton action="#{traitementDialog.startProcess}"
											styleClass="ui-button ui-widget ui-state-default ui-corner-all"
											value="Démarrer Process" reRender="pp2"
											rendered="#{traitementDialog.buttonRendered}"
											style="margin: 9px 0px 5px;" />
									</h:panelGroup>
								</f:facet>
								<f:facet name="finish2">
									<h:panelGroup>
										<h:outputText value="Process Done" />
										<a4j:commandButton action="#{traitementDialog.startProcess}"
											value="Restart Process" reRender="pp2"
											rendered="#{traitementDialog.buttonRendered}"
											styleClass="ui-button ui-widget ui-state-default ui-corner-all"
											style="margin: 9px 0px 5px;" />
									</h:panelGroup>
								</f:facet>
								<h:outputText value="#{traitementDialog.currentValue} %" />


							</rich:progressBar>
						</a4j:outputPanel>
					</h:form>




					<td><h:outputText value="Tour" styleClass="labelStyle" /></td>
							<td><h:selectOneMenu value="#{traitementDialog.tour}" required="true" requiredMessage="Entrer le tour"
									styleClass="text ui-widget-content ui-corner-all">
									<f:selectItems value="#{traitementDialog.tourItems}" />
								</h:selectOneMenu></td>

	 */


}
