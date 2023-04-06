package com.afb.portal.presentation.virementsRec;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
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

import org.apache.commons.io.FilenameUtils;
import org.hibernate.criterion.Restrictions;
import org.richfaces.model.selection.Selection;

import afb.dsi.dpd.portal.jpa.entities.User;

import com.afb.portal.presentation.models.AbstractDialog;
import com.afb.portal.presentation.models.reportViewer.ReportViewer;
import com.afb.portal.presentation.tools.ExceptionHelper;
import com.afb.portal.presentation.tools.ViewHelper;
import com.afb.virementsRec.jpa.datamodel.MotifsDeRejet;
import com.afb.virementsRec.jpa.datamodel.ParametragesImpots;
import com.afb.virementsRec.jpa.datamodel.RapatriementImagesAller;
import com.afb.virementsRec.jpa.datamodel.Rejet;
import com.afb.virementsRec.jpa.datamodel.SortTraitement;
import com.afb.virementsRec.jpa.datamodel.TourCompensation;
import com.afb.virementsRec.jpa.datamodel.TraitementImpots;
import com.afb.virementsRec.jpa.datamodel.TraitementTourCompensation;
import com.afb.virementsRec.jpa.datamodel.TypeRejet;
import com.afb.virementsRec.jpa.datamodel.TypeTraitement;
import com.ibm.icu.text.SimpleDateFormat;
import com.yashiro.persistence.utils.dao.tools.RestrictionsContainer;



public class TraitementImpotsDialog extends AbstractDialog{

	//private ParametragesImpots currentObject = new ParametragesImpots();

	//private List<ParamEmail> emails = new ArrayList<ParamEmail>();

	//private Selection selection;

	//private ParamEmail deleUser = new ParamEmail();

	//private int index = 0;

	private String msgFinTraitement;

	private boolean statutTraitement = false;

	private User user;

	private Integer nbrFichACopier;

	private Integer nbrFichCopie;

	private ParametragesImpots parametragesImpots;

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


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ParametragesImpots getParametragesImpots() {
		return parametragesImpots;
	}

	public void setParametragesImpots(
			ParametragesImpots parametragesImpots) {
		this.parametragesImpots = parametragesImpots;
	}

	public ReportViewer getReportViewer() {
		return reportViewer;
	}

	public void setReportViewer(ReportViewer reportViewer) {
		this.reportViewer = reportViewer;
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

						fileLines.add(scLine);

					}
					j++;
					//System.out.println("***READ AND GOT LINE "+ j);
				}

				scanner.close();
			}
		}
		return fileLines;
	}


	public void doTraitement(String repEntree, String repArchiveEntree, Integer aCopier, Integer Copier, String fichiersTraite, String jasper, String reportFile ){

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


			/**File folderDest = new File(repSortie);
			//Si le répertoire n'existe pas, il faut le créer
			if(!folderDest.exists()){
				if(folderDest.mkdirs()){
					System.out.println("*****Les répertoires parents sont crées avec succès!******");
				} else {
					System.out.println("******Echec de création des répertoires parents!*****");
				}
			}
			System.out.println("****folderDest*****" + folderDest);*/


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


			File[] listOfFiles = folderEntree.listFiles();
			if(listOfFiles==null || listOfFiles.length==0){
				System.out.println("Aucun fichier trouvé dans le répertoire d'entrée !!!");
				this.error=true;
				this.information=true;
				ExceptionHelper.showError("Aucun fichier trouvé dans le répertoire d'entrée !!!", this);
				return;
			}


			if(listOfFiles!=null&&listOfFiles.length>0){

				/**Parcours du répertoire d'entrée***/
				for (int i = 0; i < listOfFiles.length; i++) {

					if (listOfFiles[i].isFile()) {

						nbrFichACopier++;

						System.out.println("File " + listOfFiles[i].getName());

						String fichierEntree = repEntree + File.separator + listOfFiles[i].getName();

						String prefix = FilenameUtils.getBaseName(listOfFiles[i].getName());
						String suffix = FilenameUtils.getExtension(listOfFiles[i].getName());
						SimpleDateFormat df = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");

						String newFileName = prefix + "_" + df.format(new Date()) +  "." + suffix;

						//String fichierArchiveEntree = repArchiveEntree + File.separator + listOfFiles[i].getName();
						String fichierArchiveEntree = repArchiveEntree + File.separator + newFileName;


						System.out.println("****fichierEntree*****" + fichierEntree);

						System.out.println("****fichierArchiveEntree*****" + fichierArchiveEntree);

						Path pathEntree = Paths.get(fichierEntree);

						Path pathArchiveEntree = Paths.get(fichierArchiveEntree);

						String ext = FilenameUtils.getExtension(fichierEntree); 


						if(ext.equals("csv")|| ext.equals("CSV")){

							if(traitementEtCopieFichiers(listOfFiles[i].getName(), pathEntree, pathArchiveEntree, aCopier)){
								if(fichiersTraite.isEmpty())
									fichiersTraite = listOfFiles[i].getName();
								else
									fichiersTraite = fichiersTraite + ";" + listOfFiles[i].getName();
							}
						}

					}
				}

				/** Sauvegarder le traitement***/
				saveTraitement(fichiersTraite);


				/********************Affichage Rapport de traitement + envoi mail***********************************/
				HashMap<String, Object> param = new HashMap<String, Object>();

				List<RapatriementImagesAller> repports = new ArrayList<RapatriementImagesAller>();


				param.put("uti", user.getLogin());
				param.put("nbrFichACopierImgAller", nbrFichACopier);
				param.put("nbrFichCopieImgAller", nbrFichCopie);

				/***Rapport***/
				//reportViewer = new ReportViewer(repports,jasper, param, "afb.statistique.title", this,"/views/repport/" + reportFile);
				//reportViewer.viewReport();

			}


			/***title="Rapport traitement (copie) des fichiers images phase Aller, le " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()) + " à " + new SimpleDateFormat("HH:mm:ss").format(new Date());
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


					SendMail.sendMail(filesNames, filesPath, mailsTo, mailsCC, mailsBCC, title, msg, ip, email, pass, port);



				}
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/ 
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void traitement(){

		try{

			String fichiersTraite = "";
			nbrFichACopier = 0;
			nbrFichCopie = 0;


			boolean fichiersDestination = false;


			/*******Récupération des différents répertoires*********/
			String repEntree = parametragesImpots.getRepertoireEntree();
			String repArchiveEntree = parametragesImpots.getRepertoireArchiveEntree();



			/*********Exécution du Traitement********/
			doTraitement(repEntree, repArchiveEntree, nbrFichACopier, nbrFichCopie,fichiersTraite, "rapportTraitementImpots.jasper", "ReportTraitementImpots.xhtml");


			msgFinTraitement = "Traitement Terminé!!!";

			statutTraitement = true;

		}catch(Exception e){
			e.printStackTrace();
		}

	}

	//Copier les fichiers d'un répertoire vers un autre
	public boolean traitementEtCopieFichiers(String fichierTraite, Path pathEntree, Path pathArchiveEntree, Integer aCopier){

		try {

			boolean isSystac = false;
			boolean isSygma = false;	
			boolean toTraite = true;

			List<TraitementImpots> listTraitementImpots = new ArrayList<TraitementImpots>();
			List<TraitementImpots> listTraitementSygmaPlus = new ArrayList<TraitementImpots>();
			List<TraitementImpots> listTotalTraitementImpots = new ArrayList<TraitementImpots>();
			List<Rejet> listRejet = new ArrayList<Rejet>();


			//File SYSTAC
			List<String> fileLines = readAndScanFile(pathEntree);

			if(fileLines!=null && !fileLines.isEmpty()){

				int j = 0;
				int position = 0;
				String lastOpe = "";
				int lastPositionIncremented = 0;
				Map<String, String> mapOpeLastEve = new HashMap<>();

				for(String line: fileLines){

					toTraite = true;

					TraitementImpots traitementImpots = new TraitementImpots();
					traitementImpots.setUtiPortal(user.getLogin());

					//Récupération des valeurs paramétrés dans le module (param généraux pour SYSTAC et SYGMA)
					traitementImpots.setAgsa(parametragesImpots.getAgsa());

					traitementImpots.setDev1(parametragesImpots.getDev1());
					traitementImpots.setIndh1(parametragesImpots.getIndh1());
					traitementImpots.setInds1(parametragesImpots.getInds1());
					traitementImpots.setDev(parametragesImpots.getDev());
					traitementImpots.setNat(parametragesImpots.getNat());

					traitementImpots.setDevf(parametragesImpots.getDevf());
					traitementImpots.setPrga(parametragesImpots.getPrga());


					String [] readLineArray = line.split("\\|"); 
					if(readLineArray!=null && readLineArray.length>0){

						int lengthArray = readLineArray.length;

						int i = 0;

						Double mht1=0.0;
						Date dou=null;
						Date dsai=null;
						//Récupération des valeurs dans le fichier de la DGI
						while(i<lengthArray){

							if(i==0){
								/**************identification fichier SYSTAC*******************/
								if(readLineArray[0].equals("1")){
									isSystac =  true;
									isSygma =  false;
									traitementImpots.setOpe(parametragesImpots.getOpeSYSTAC());
									traitementImpots.setTyp(parametragesImpots.getTypSYSTAC());
									traitementImpots.setRlet(parametragesImpots.getRlet());  
								}else if(readLineArray[0].equals("2")){				
									/**************identification fichier SYGMA*******************/
									isSygma = true;
									isSystac =  false;
									traitementImpots.setOpe(parametragesImpots.getOpeSYGMA());
									traitementImpots.setTyp(parametragesImpots.getTypSYGMA());
									//traitementImpots.setRlet("");  
									//already set
								}
							}

							if(i==1){
								traitementImpots.setAge(readLineArray[1]);
								traitementImpots.setAge1(readLineArray[1]);
							}
							else if(i==2)
								traitementImpots.setNcp1(readLineArray[2]);
							else if(i==3)
								traitementImpots.setClc1(readLineArray[3]);
							else if(i==4)
								traitementImpots.setCli1(readLineArray[4]);
							else if(i==5){
								mht1 = Double.parseDouble(readLineArray[5]);
								traitementImpots.setMht1(mht1);
							}
							/*else if(i==6){
								traitementImpots.setEtab(readLineArray[6]);
							}
							else if(i==7){
								traitementImpots.setGuib(readLineArray[7]);
							}
							else if(i==8){
								traitementImpots.setNome(readLineArray[8]);
							}
							else if(i==9){
								traitementImpots.setDomi(readLineArray[9]);
							}*/
							else if(i==10){
								traitementImpots.setEtabr(readLineArray[10]);
							}
							else if(i==11){
								traitementImpots.setGuibr(readLineArray[11]);
							}
							else if(i==12){
								traitementImpots.setComb(readLineArray[12]);
							}
							else if(i==13){
								traitementImpots.setCleb(readLineArray[13]);
							}
							else if(i==14){
								traitementImpots.setNomb(readLineArray[14]);
							}
							else if(i==15){
								dou = new SimpleDateFormat("dd/MM/yyyy").parse(readLineArray[15]);
								traitementImpots.setDou(dou);
							}
							else if(i==16){
								traitementImpots.setLib1(readLineArray[16]);
							}
							else if(i==17){
								dsai = new SimpleDateFormat("dd/MM/yyyy").parse(readLineArray[17]);
								traitementImpots.setDsai(dsai);
							}
							else if(i==18){
								traitementImpots.setHsai(readLineArray[18]);
							}
							else if(i==19){
								traitementImpots.setLib2(readLineArray[19]);
							}
							else if(i==20){
								traitementImpots.setLib3(readLineArray[20]);
							}

							i++;
						}

						TypeRejet motifDeRejet=null;
						/*****************Récupération du compte Impôts*********************************/
						List<String> recupCompteImpots = ViewHelper.virementsRecManager.recuperationCompteImpots(traitementImpots.getEtabr(), traitementImpots.getGuibr(), traitementImpots.getAge());
						if(recupCompteImpots!=null && !recupCompteImpots.isEmpty()){
							traitementImpots.setEtab(recupCompteImpots.get(0));
							traitementImpots.setGuib(recupCompteImpots.get(1));
							traitementImpots.setNome(recupCompteImpots.get(2));
							traitementImpots.setDomi(recupCompteImpots.get(3));
						}else{
							//RIB BENEFICIAIRE INCORRECT
							if(parametragesImpots.getMotifsDeRejet()!=null&&!parametragesImpots.getMotifsDeRejet().isEmpty()){
								for(MotifsDeRejet m: parametragesImpots.getMotifsDeRejet()){
									if(m.getTypeRejet().equals(TypeRejet.RIB_BENEFICIAIRE_INCORRECT)){
										motifDeRejet = m.getTypeRejet();
									}
								}
							}
							//Construction du fichier de Rejet
							Rejet rejet = new Rejet(traitementImpots.getLib3(), traitementImpots.getLib2(), traitementImpots.getNcp1(), traitementImpots.getMht1(), traitementImpots.getLib1(), new SimpleDateFormat("dd/MM/yyyy").parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date())), new SimpleDateFormat("HH:mm:ss").format(new Date()), motifDeRejet, motifDeRejet.value(), new SimpleDateFormat("dd/MM/yyyy").parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date())));
							listRejet.add(rejet);
							toTraite=false;
						}

						/************************Calcule des autres valeurs pour insertion dans bkeve****************************/
						String eta = "";
						String desa1 = "";
						Double mon1=0d;
						if(isSystac){
							if(mht1>parametragesImpots.getMontantPlafondSystac()){  //5000000
								eta = "AT"; //FO
								desa1 = "PLAF";
							}else{
								eta = "VA";
							}
							
							if(mht1>=parametragesImpots.getMontantMaxSYSTAC()){ //Rejet pour montant supérieur ou égale à 100 M
								if(parametragesImpots.getMotifsDeRejet()!=null&&!parametragesImpots.getMotifsDeRejet().isEmpty()){
									for(MotifsDeRejet m: parametragesImpots.getMotifsDeRejet()){
										if(m.getTypeRejet().equals(TypeRejet.OPPOSITION_DEBIT)){
											motifDeRejet = m.getTypeRejet();
										}
									}
								}
								
								//Construction du fichier de Rejet
								Rejet rejet = new Rejet(traitementImpots.getLib3(), traitementImpots.getLib2(), traitementImpots.getNcp1(), traitementImpots.getMht1(), traitementImpots.getLib1(), new SimpleDateFormat("dd/MM/yyyy").parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date())), new SimpleDateFormat("HH:mm:ss").format(new Date()), motifDeRejet, motifDeRejet.value(), new SimpleDateFormat("dd/MM/yyyy").parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date())));
								listRejet.add(rejet);
								toTraite=false;
							}

							//mon1 = mht1 + (parametragesImpots.getMontantFraisSystac() + (parametragesImpots.getTauxTax() * parametragesImpots.getMontantFraisSystac()));
							double fraisImpots = parametragesImpots.getPourcentageFraisImpots() * mht1;
							if(fraisImpots<=parametragesImpots.getPalierMinSystac()){
								fraisImpots = parametragesImpots.getPalierMinSystac();
							}
							else if(parametragesImpots.getPalierMinSystac()<fraisImpots && fraisImpots<parametragesImpots.getPalierMaxSystac()){
								fraisImpots = parametragesImpots.getPourcentageFraisImpots() * mht1;							
							}
							else if(fraisImpots>=parametragesImpots.getPalierMaxSystac()){
								fraisImpots = parametragesImpots.getPalierMaxSystac();
							}
							mon1 = mht1 + (fraisImpots + (parametragesImpots.getTauxTax() * fraisImpots)) + (parametragesImpots.getMontantFraisSystac() + (parametragesImpots.getTauxTax() * parametragesImpots.getMontantFraisSystac()));

						}else if(isSygma){ //SYGMA
							eta = "AT";

							if(mht1>parametragesImpots.getMontantPlafondSYGMA()){  //5000000
								desa1 = "PLAF";
							}

							traitementImpots.setOrig("S");
							traitementImpots.setCeb("N");
							traitementImpots.setTenv("P");
							traitementImpots.setCfra("0");
							traitementImpots.setCsp4("0001");
							traitementImpots.setCsp5("RTG");
							traitementImpots.setForc("");
							traitementImpots.setCsp3("");
							Date dret  = dsai;
							traitementImpots.setDret(dret);

							//mon1 = mht1 + (parametragesImpots.getMontantFraisSYGMA() + (parametragesImpots.getTauxTax() * parametragesImpots.getMontantFraisSYGMA()));
							
							double fraisImpots = parametragesImpots.getPourcentageFraisImpots() * mht1;
							if(fraisImpots<=parametragesImpots.getPalierMinSygma()){
								fraisImpots = parametragesImpots.getPalierMinSygma();
							}
							else if(parametragesImpots.getPalierMinSygma()<fraisImpots && fraisImpots<parametragesImpots.getPalierMaxSygma()){
								fraisImpots = parametragesImpots.getPourcentageFraisImpots() * mht1;							
							}
							else if(fraisImpots>=parametragesImpots.getPalierMaxSygma()){
								fraisImpots = parametragesImpots.getPalierMaxSygma();
							}
							
							mon1 = mht1 +  (fraisImpots +  (parametragesImpots.getTauxTax() * fraisImpots))  + (parametragesImpots.getMontantFraisSYGMA() + (parametragesImpots.getTauxTax() * parametragesImpots.getMontantFraisSYGMA()));

						}
						traitementImpots.setEta(eta);
						traitementImpots.setDesa1(desa1);
						traitementImpots.setMon1(mon1);

						////String nome; //bkbqe*
						Double mban = mht1;
						traitementImpots.setMban(mban);
						Date dvab=null;
						if(dou!=null)
							dvab = dou;
						traitementImpots.setDvab(dvab);
						Double mht = mht1;
						traitementImpots.setMht(mht);
						Double mnat = mht;
						traitementImpots.setMnat(mnat);
						Double mht2 = mht1;
						traitementImpots.setMht2(mht2);
						Double mon2 = mht2;
						traitementImpots.setMon2(mon2);
						Date dco = dou;
						traitementImpots.setDco(dco);
						Double mnt1 = mht1;
						traitementImpots.setMnt1(mnt1);


						String cco = "1";

						Calendar cal = Calendar.getInstance();
						if(dsai!=null)cal.setTime(dsai);
						if(isSystac){
							/**if( (cal.get(Calendar.DAY_OF_WEEK)>=Calendar.MONDAY) && (cal.get(Calendar.DAY_OF_WEEK)<=Calendar.FRIDAY)){
								cco = "1";
							}else{
								cco = "0";
							}*/
							cco = "0";
						}else{
							cco = "2";
						}
						traitementImpots.setCco(cco);

						//tenv = T
						//cco = 2
						//cfra = 0
						//csp4=0001
						//csp5=RTG
						//forc = ""
						//csp3=""
						//dsa1 = "PLAF" pr mont sup 5millions
						//dret  = dsai;


						String nom1;
						String ges1;
						Date dva1;  //J-1 bkfer
						Double sol1;  //bkcom
						String uti=user.getLogin();



						//Aller dans Amplitude récupérer les valeurs restantes
						List<String> recupElementsCB = ViewHelper.virementsRecManager.recuperationsValCB(uti, traitementImpots.getCli1(), traitementImpots.getAge(), traitementImpots.getNcp1(), traitementImpots.getClc1(), parametragesImpots.getDev1(), dsai);
						if(recupElementsCB!=null && !recupElementsCB.isEmpty()){

							if(recupElementsCB.get(0).equals("COMPTE FERME OU EN INSTANCE DE FERMETURE")){
								System.out.println("/*****************REJET POUR COMPTE FERME***********************/");
								/*****************REJET POUR COMPTE FERME***********************/
								if(parametragesImpots.getMotifsDeRejet()!=null&&!parametragesImpots.getMotifsDeRejet().isEmpty()){
									for(MotifsDeRejet m: parametragesImpots.getMotifsDeRejet()){
										if(m.getTypeRejet().equals(TypeRejet.COMPTE_FERME)){
											motifDeRejet = m.getTypeRejet();
										}
									}
								}
								//Construction du fichier de Rejet
								Rejet rejet = new Rejet(traitementImpots.getLib3(), traitementImpots.getLib2(), traitementImpots.getNcp1(), traitementImpots.getMht1(), traitementImpots.getLib1(), new SimpleDateFormat("dd/MM/yyyy").parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date())), new SimpleDateFormat("HH:mm:ss").format(new Date()), motifDeRejet, motifDeRejet.value(), new SimpleDateFormat("dd/MM/yyyy").parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date())));
								listRejet.add(rejet);
								toTraite=false;
							}

							if(recupElementsCB.get(0).equals("NCP NOT FOUND")){
								System.out.println("/*****************REJET POUR COMPTE INEXISTANT***********************/");
								/*****************REJET POUR COMPTE INEXISTANT***********************/
								if(parametragesImpots.getMotifsDeRejet()!=null&&!parametragesImpots.getMotifsDeRejet().isEmpty()){
									for(MotifsDeRejet m: parametragesImpots.getMotifsDeRejet()){
										if(m.getTypeRejet().equals(TypeRejet.COMPTE_INEXISTANT)){
											motifDeRejet = m.getTypeRejet();
										}
									}
								}
								//Construction du fichier de Rejet
								Rejet rejet = new Rejet(traitementImpots.getLib3(), traitementImpots.getLib2(), traitementImpots.getNcp1(), traitementImpots.getMht1(), traitementImpots.getLib1(), new SimpleDateFormat("dd/MM/yyyy").parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date())), new SimpleDateFormat("HH:mm:ss").format(new Date()), motifDeRejet, motifDeRejet.value(), new SimpleDateFormat("dd/MM/yyyy").parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date())));
								listRejet.add(rejet);
								toTraite=false;
							}else{

								uti = recupElementsCB.get(0);
								if(uti.equals("")||uti.isEmpty()){
									uti = user.getLogin();
								}
								traitementImpots.setUti(uti);


								if(recupElementsCB.size()>1){
									sol1 = Double.parseDouble(recupElementsCB.get(1));
									if(sol1<mht1){
										/******REJET POUR SOLDE (INCLUANT DECOUVERT) INSUFFISANT******/
										if(parametragesImpots.getMotifsDeRejet()!=null&&!parametragesImpots.getMotifsDeRejet().isEmpty()){
											System.out.println("/******REJET POUR SOLDE (INCLUANT DECOUVERT) INSUFFISANT******/");
											for(MotifsDeRejet m: parametragesImpots.getMotifsDeRejet()){
												if(sol1>0.0){
													if(m.getTypeRejet().equals(TypeRejet.PROVISION_INSUFFISENTE)){
														motifDeRejet = m.getTypeRejet();
													}
												}else if(sol1<=0.0){ //sol1=0
													if(m.getTypeRejet().equals(TypeRejet.ABSENCE_DE_PROVISION)){
														motifDeRejet = m.getTypeRejet();
													}
												}
											}
										}
										System.out.println("--sol1--" + sol1);
										System.out.println("------------motifDeRejet--------------" + motifDeRejet + "-----" + motifDeRejet.value());
										//Construction du fichier de Rejet
										Rejet rejet = new Rejet(traitementImpots.getLib3(), traitementImpots.getLib2(), traitementImpots.getNcp1(), traitementImpots.getMht1(), traitementImpots.getLib1(), new SimpleDateFormat("dd/MM/yyyy").parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date())), new SimpleDateFormat("HH:mm:ss").format(new Date()), motifDeRejet, motifDeRejet.value(), new SimpleDateFormat("dd/MM/yyyy").parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date())));
										listRejet.add(rejet);
										toTraite=false;
									}else{
										traitementImpots.setSol1(sol1);
										nom1 = recupElementsCB.get(2);
										traitementImpots.setNom1(nom1);
										ges1  = recupElementsCB.get(3);
										traitementImpots.setGes1(ges1);

										String dva1S = recupElementsCB.get(4);
										dva1 = new SimpleDateFormat("yyyy-MM-dd").parse(dva1S);
										String dva1S2 = new SimpleDateFormat("dd/MM/yyyy").format(dva1);
										Date dva12 = new SimpleDateFormat("dd/MM/yyyy").parse(dva1S2);
										traitementImpots.setDva1(dva12);
										
										
										List<String> listOppositions = ViewHelper.virementsRecManager.recuperationsOppositionDebit(traitementImpots.getAge(), traitementImpots.getCli1(), traitementImpots.getNcp1());
										if(listOppositions!=null && !listOppositions.isEmpty()){
											System.out.println("/**********REJET POUR OPPOSITION DEBIT ******************");  //OPPOSITION DEBIT 
											if(parametragesImpots.getMotifsDeRejet()!=null&&!parametragesImpots.getMotifsDeRejet().isEmpty()){
												for(MotifsDeRejet m: parametragesImpots.getMotifsDeRejet()){
													if(m.getTypeRejet().equals(TypeRejet.OPPOSITION_DEBIT)){
														motifDeRejet = m.getTypeRejet();
													}
												}
											}
											/**********REJET POUR DESACCORDS OPPOSITION DEBIT****************/
											//Construction du fichier de Rejet
											Rejet rejet = new Rejet(traitementImpots.getLib3(), traitementImpots.getLib2(), traitementImpots.getNcp1(), traitementImpots.getMht1(), traitementImpots.getLib1(), new SimpleDateFormat("dd/MM/yyyy").parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date())), new SimpleDateFormat("HH:mm:ss").format(new Date()), motifDeRejet, motifDeRejet.value(), new SimpleDateFormat("dd/MM/yyyy").parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date())));
											listRejet.add(rejet);
											toTraite=false;
										}
										

										List<String> listDesaccords = ViewHelper.virementsRecManager.recuperationsListeDesaccords(traitementImpots.getAge(), traitementImpots.getCli1(), traitementImpots.getNcp1());
										if(listDesaccords!=null && !listDesaccords.isEmpty()){

											/*******INSERTION DES DESACCORDS **************/
											if(listDesaccords!=null && !listDesaccords.isEmpty()){
												System.out.println("/*******INSERTION DES DESACCORDS **************/");
												int c=0;
												while(c<listDesaccords.size()){
													if(c==0){
														if(traitementImpots.getDesa1().trim().isEmpty())
															traitementImpots.setDesa1(listDesaccords.get(0)); //desa1
														else
															traitementImpots.setDesa2(listDesaccords.get(0)); //desa2
													}
													if(c==1){
														if(traitementImpots.getDesa2().trim().isEmpty())
															traitementImpots.setDesa2(listDesaccords.get(1)); //desa2
														else
															traitementImpots.setDesa3(listDesaccords.get(1)); //desa2
													}
													if(c==2){
														if(traitementImpots.getDesa3().trim().isEmpty())
															traitementImpots.setDesa3(listDesaccords.get(2)); //desa3
														else{
															traitementImpots.setDesa4(listDesaccords.get(2)); //
														}
													}
													if(c==3){
														if(traitementImpots.getDesa4().trim().isEmpty())
															traitementImpots.setDesa4(listDesaccords.get(3)); //desa4
														else
															traitementImpots.setDesa5(listDesaccords.get(3)); 
													}
													if(c==4){
														if(traitementImpots.getDesa5().trim().isEmpty())
															traitementImpots.setDesa5(listDesaccords.get(4)); //desa5
													}
													c++;
												}
											}

											//if(listDesaccords.contains(parametragesImpots.getLibelleDesaccordDebit().trim())){

											/**if(!listDesaccords.isEmpty()){
												System.out.println("/**********REJET POUR DESACCORDS ******************");  //OPPOSITION DEBIT 
												if(parametragesImpots.getMotifsDeRejet()!=null&&!parametragesImpots.getMotifsDeRejet().isEmpty()){
													for(MotifsDeRejet m: parametragesImpots.getMotifsDeRejet()){
														if(m.getTypeRejet().equals(TypeRejet.DESACCORDS_SUR_COMPTE_OU_CLIENT)){
															motifDeRejet = m.getTypeRejet();
														}
													}
												}
												/**********REJET POUR DESACCORDS OPPOSITION DEBIT****************
												//Construction du fichier de Rejet
												Rejet rejet = new Rejet(traitementImpots.getLib3(), traitementImpots.getLib2(), traitementImpots.getNcp1(), traitementImpots.getMht1(), traitementImpots.getLib1(), new SimpleDateFormat("dd/MM/yyyy").parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date())), new SimpleDateFormat("HH:mm:ss").format(new Date()), motifDeRejet, motifDeRejet.value(), new SimpleDateFormat("dd/MM/yyyy").parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date())));
												listRejet.add(rejet);
												toTraite=false;
											}*/

										}
									}
								}

							}

						}

						traitementImpots.setDateTraitement(new Date());

						lastOpe = traitementImpots.getOpe();

					}

					System.out.println("*****value of j****" + j);

					//Créer l'évènement
					/*if(lastOpe.equals(traitementImpots.getOpe())){
							position = j++;
							lastPositionIncremented = position;
						}else{

							position = lastPositionIncremented;
						}*/

					String eve="";
					if(mapOpeLastEve!=null && !mapOpeLastEve.isEmpty()){
						if(mapOpeLastEve.get(traitementImpots.getOpe())==null){
							eve=ViewHelper.virementsRecManager.findMaxEveOfBkeve(traitementImpots.getOpe().trim(), 1);
							System.out.println("***eve***" + eve);

						}else{
							String lastEve = mapOpeLastEve.get(traitementImpots.getOpe().trim()); //dernier eve pour l'ope courrante
							Long le = Long.parseLong(lastEve); //last eve
							System.out.println("***le***" + le);

							Long ne = le + 1; //new eve

							System.out.println("***ne***" + ne);

							eve = String.valueOf(ne); //new eve as String
							System.out.println("***eve***" + eve);
						}

					}else{
						eve=ViewHelper.virementsRecManager.findMaxEveOfBkeve(traitementImpots.getOpe().trim(), 1); //++j
						System.out.println("***eve***" + eve);

					}


					//long eveLong = Long.parseLong(eve);
					//eveLong = eveLong + 1;
					//eve = String.valueOf(eveLong);

					mapOpeLastEve.put(traitementImpots.getOpe(), eve);

					traitementImpots.setEve(eve);
					System.out.println("***eve***" + eve);
					System.out.println("***Eve Impôts****" + traitementImpots.getEve());

					if(toTraite){ //Si ce virement est OK
						listTraitementImpots.add(traitementImpots);
					}
					listTotalTraitementImpots.add(traitementImpots);


					if(isSygma && toTraite){

						listTraitementSygmaPlus.add(traitementImpots);
					}
				}

				mapOpeLastEve.clear();

			}

			System.out.println("-------Going to insert in bkeve------" + listTraitementImpots.size() + " virements");
			/********Insertion dans bkeve******/
			ViewHelper.virementsRecManager.insertIntoBkeve(listTraitementImpots);  //Pour les Systac et Sygma

			boolean error = false;
			try{
				if(listTraitementSygmaPlus!=null && !listTraitementSygmaPlus.isEmpty()){ //Pour les virements Sygma uniquement on insert aussi dans bkevec et dans bkrtgseve
					ViewHelper.virementsRecManager.insertIntoBkeveC(listTraitementSygmaPlus, parametragesImpots);
					ViewHelper.virementsRecManager.insertIntoBkrtgsEve(listTraitementSygmaPlus, parametragesImpots);
				}
			}catch(Exception e){

				error = true;
				this.information = true;
				ExceptionHelper.showInformation("Erreur dans l'intégration des Sygma !!!", this);
				return false;
			}


			boolean rejet = false;
			/*********************Fabrication du fichier de rejet*************************/
			if(listRejet!=null&&!listRejet.isEmpty()){
				rejet = genererFichierRejet(listRejet, listTotalTraitementImpots.size());
			}

			if(!rejet && !error){
				this.information = true;
				ExceptionHelper.showInformation("Intégration terminée avec succès !!!", this);
			}
			else if(rejet && !error){
				this.information = true;
				ExceptionHelper.showInformation("Intégration terminée avec succès !!! Cliquez sur le lien de téléchargement dans le menu 'Fichiers de Rejets' pour télécharger votre fichier de rejet sous format excel dans le repertoire C://Utilisateurs/Téléchargement de votre machine.", this);
			}

			listRejet.clear();

			/********Archive du fichier traité******/
			Path p = Files.move(pathEntree, pathArchiveEntree, StandardCopyOption.REPLACE_EXISTING);
			System.out.println("***Files copied to ***" + p.toString());


			/**p = Files.move(pathEntree, pathDestination, StandardCopyOption.REPLACE_EXISTING);
				System.out.println("***Files copied to ***" + p.toString());
				nbrFichCopieImg++;*/

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;

	}



	public boolean genererFichierRejet(List<Rejet> listRejets, int sizeVirements){

		String date = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss").format(new Date());

		String fileName = "Liste_Rejets_OTP_DGI_DU_" + date + ".CSV"; //xlsx txt csv

		System.out.println("*****SAVING REJETS***********");
		for(Rejet r: listRejets){
			r.setRejetFileName(fileName);
			ViewHelper.virementsRecManagerDAOLocal.save(r);
		}

		/**System.out.println("*****FILTERING REJETS***********");
			List<Rejet> rejetsListe = ViewHelper.virementsRecManagerDAOLocal.filter(Rejet.class, null, RestrictionsContainer.getInstance().add(Restrictions.eq("dateCreation", new Date())), null, null, 0, -1);
			System.out.println("*****rejetsListe size***********" + rejetsListe.size());*/

		/**************FABRICATION ET DEPÔT DU FICHIER DE REJET*****************/
		if(listRejets!=null && !listRejets.isEmpty()){

			/**HashMap<String, Object> param = new HashMap<String, Object>();

			// Affichage du rapport de traitement 
			List<Rejet> repports = new ArrayList<Rejet>();

			//Rejet raport = new Rejet();
			//raport.setItems(rejetsListe);
			//repports.add(raport);

			param.put("uti", ViewHelper.getSessionUser().getName());
			param.put("countTotalVir", sizeVirements);
			param.put("countTotalVirRejet", listRejets.size());
			reportViewer = new ReportViewer(rejetsListe,"RejetOTPDGI.jasper", param, "afb.statistique.title", "text/csv" ,"/views/repport/ReportStat2.xhtml", fileName);
			//reportViewer.viewReport();*/


			try{    

				String reportsDir = ViewHelper.getReportsDir();
				BufferedWriter bw = new BufferedWriter(new FileWriter(reportsDir + File.separator + fileName));
				//FileWriter fw = new FileWriter(reportsDir + File.separator + fileName + ".txt");
				//bw.write("AFRILAND FIRST BANK CAMEROUN");
				//bw.newLine();
				//bw.newLine();
				//bw.write("FICHIER DE REJETS DU " + new java.text.SimpleDateFormat("dd/MM/YYYY hh:mm:ss").format(new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss").parse(date)));
				//bw.newLine();
				//bw.newLine();

				/*bw.write("Numéro de l'AI ou de l'AMR");
				bw.append('|');
				bw.write("Numéro unique du contribuable(NIU)");
				bw.append('|');
				bw.write("RIB du contribuable");
				bw.append('|');
				bw.write("Montant nominal en devise");
				bw.append('|');
				bw.write("Motif de l'ordre de virement");
				bw.append('|');
				bw.write("Date de rejet");
				bw.append('|');
				bw.write("Heure de rejet");
				bw.append('|');
				bw.write("Motif de rejet");
				bw.newLine();*/

				for(int i=0; i<listRejets.size(); i++){

					bw.write(listRejets.get(i).toString());
					if(i!=listRejets.size()-1)
						bw.newLine();
				}
				//fw.write("Welcome to javaTpoint.");    
				bw.close();    

				System.out.println("Success...");   

			}catch(Exception e){

				System.out.println(e);
				e.printStackTrace();

				System.out.println("Failure...");   

			}    


			return true;

		}


		return false;

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


			Path p = Files.move(pathDestination, pathArchive, StandardCopyOption.REPLACE_EXISTING);
			System.out.println("***Files copied to ***" + p.toString());


			traitementTourCompense.setSortTraitement(SortTraitement.SUCCES);
			ViewHelper.virementsRecManagerDAOLocal.save(traitementTourCompense);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void saveTraitement(String fichiersTraite){

		/**Date dateTraite = new Date();

		TraitementImpots traitement = new TraitementImpots();
		traitement.setDateTraitement(dateTraite);
		traitement.setUtiPortal(user.getLogin());*/

		//ViewHelper.virementsRecManagerDAOLocal.save(rapatriementImagesRetour);

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
		return "/views/virementsRec/TraitementImpotsDialog.xhtml";
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


	@SuppressWarnings("unchecked")
	@Override
	public ParametragesImpots getCurrentObject() {
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

	@Override
	public void initComponents() {
		// TODO Auto-generated method stub

		user = ViewHelper.getSessionUser();

		List<ParametragesImpots> listParams = ViewHelper.virementsRecManagerDAOLocal.filter(ParametragesImpots.class, null, null, null, null, 0, -1);
		if(listParams!=null && !listParams.isEmpty()){

			//currentObject = listParams.get(0);
			parametragesImpots = listParams.get(0);

			/**if(parametragesImpots.getListEmails()!=null && !parametragesImpots.getListEmails().isEmpty()){

				for(ParamEmail pe: parametragesImpots.getListEmails()){
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
			}*/
		}else{
			this.error=true;
			ExceptionHelper.showError("Veuillez d'abord renseigner les paramétrages!", this);
		}

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

		msgFinTraitement="";

		statutTraitement=false;

	}

	/**public static void robotTraitementCompensation(){
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
	}*/


	/**public static void traitementAuto(){

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
	}*/





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
