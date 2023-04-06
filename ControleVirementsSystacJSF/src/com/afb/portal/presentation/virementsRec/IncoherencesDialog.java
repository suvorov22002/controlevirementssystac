package com.afb.portal.presentation.virementsRec;

import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.richfaces.model.selection.Selection;

import com.afb.dsi.alertes.AfrilandSendMail;
import com.afb.portal.presentation.models.AbstractDialog;
import com.afb.portal.presentation.models.DialogFormMode;
import com.afb.portal.presentation.models.IViewComponent;
import com.afb.portal.presentation.models.reportViewer.ReportViewer;
import com.afb.portal.presentation.tools.ExceptionHelper;
import com.afb.portal.presentation.tools.ViewHelper;
import com.afb.virementsRec.jpa.datamodel.Doublons;
import com.afb.virementsRec.jpa.datamodel.Incoherences;
import com.afb.virementsRec.jpa.datamodel.ParamEmail;
import com.afb.virementsRec.jpa.datamodel.ParamEmailAuto;
import com.afb.virementsRec.jpa.datamodel.Parametrages;
import com.afb.virementsRec.jpa.datamodel.ParametragesGenTeleCompense;
import com.afb.virementsRec.jpa.datamodel.SendMail;
import com.afb.virementsRec.jpa.datamodel.StatistiqueRapports;
import com.afb.virementsRec.jpa.datamodel.Traitement;
import com.afb.virementsRec.jpa.datamodel.TypeProcess;
import com.yashiro.persistence.utils.dao.tools.encrypter.Encrypter;



public class IncoherencesDialog extends AbstractDialog {


	private Incoherences currentObject = new Incoherences();

	//private Parametrages param = new Parametrages();

	private int count;

	private List<Incoherences> listIncoherences = new ArrayList<Incoherences>();
	
	private List<Parametrages> listParams = new ArrayList<Parametrages>();

	private List<Traitement> listTraitement = new ArrayList<Traitement>();

	private Selection selection;

	private ReportViewer reportViewer;

	private Boolean check = Boolean.FALSE;

	private Incoherences incoherenceObject = new Incoherences();

	private int totalIncoherences;

	private List<Incoherences> listIncoherencesAValider = new ArrayList<Incoherences>();

	private List<Incoherences> listIncoherencesANePasValider = new ArrayList<Incoherences>();

	private Date dateDebut;

	private Date dateFin;

	private boolean isInBkeveOrBkheve;

	private List<String> filesNames = new ArrayList<String>();

	private List<String> filesPath = new ArrayList<String>();

	private List<String>mailsTo = new ArrayList<String>();

	private List<String>mailsCC = new ArrayList<String>();

	private List<String>mailsBCC = new ArrayList<String>();

	private String ip="";

	private String email="";

	private String pass="";

	private Integer port=22;

	private String subject = "";

	private String messageCorps = "";

	private String mettreOpposition;
	
	private String user = "";

	/**
	 * Constructeur par défaut
	 */
	public IncoherencesDialog() {
		super();
	}

	/**
	 * Constructeur du Modele
	 * @param mode	Mode d'ouverture de la Boite
	 * @param currentObject	Objet Courant de la Boite
	 * @param parent	Composant Parent
	 */
	public IncoherencesDialog(DialogFormMode mode, Incoherences currentObject, List<Parametrages> listParams, List<Incoherences> incoherences, List<Traitement>traitements, int count, IViewComponent parent, Date dateDebut, Date dateFin, boolean isInBkeveOrBkheve, String mettreOpposition, String user) {

		// Appel Parent
		super();

		// Initialisation des Parametres
		this.mode = mode;
		this.currentObject = currentObject;
		//this.param = param;
		//this.listIncoherences=incoherences;
		//this.listTraitement=traitements;

		this.listParams=listParams;

		System.out.println("***incoherences.size()****" +incoherences.size());
		this.listIncoherences.addAll(incoherences);
		System.out.println("*******traitements.size()******" +traitements.size());
		this.listTraitement.addAll(traitements);

		this.count = count;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.parent = parent;
		this.isInBkeveOrBkheve=isInBkeveOrBkheve;

		this.mettreOpposition=mettreOpposition;
		this.user = user;
		// Initialisation des Composants
		initComponents();
		// this.open = true;

	}


	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Traitement des incohérences";
	}

	@Override
	public String getFileDefinition() {
		// TODO Auto-generated method stub
		return "/views/virementsRec/IncoherencesDialog.xhtml";
	}

	@SuppressWarnings("unchecked")
	@Override
	public Incoherences getCurrentObject() {
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



	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<Incoherences> getListIncoherences() {
		return listIncoherences;
	}

	public void setListIncoherences(List<Incoherences> listIncoherences) {
		this.listIncoherences = listIncoherences;
	}

	public Selection getSelection() {
		return selection;
	}

	public void setSelection(Selection selection) {
		this.selection = selection;
	}

	public ReportViewer getReportViewer() {
		return reportViewer;
	}

	public void setReportViewer(ReportViewer reportViewer) {
		this.reportViewer = reportViewer;
	}

	public void setCurrentObject(Incoherences currentObject) {
		this.currentObject = currentObject;
	}

	public Boolean getCheck() {
		return check;
	}

	public void setCheck(Boolean check) {
		this.check = check;
	}

	public Incoherences getIncoherenceObject() {
		return incoherenceObject;
	}

	public void setIncoherenceObject(Incoherences incoherenceObject) {
		this.incoherenceObject = incoherenceObject;
	}

	public int getTotalIncoherences() {
		return totalIncoherences;
	}

	public void setTotalIncoherences(int totalIncoherences) {
		this.totalIncoherences = totalIncoherences;
	}

	@Override
	public void initComponents() {

		try{

			if(mode.equals(DialogFormMode.CREATE)){
			}

			if(mode.equals(DialogFormMode.UPDATE) || mode.equals(DialogFormMode.READ)){
			}

			List<ParametragesGenTeleCompense> parametragesGenTeleCompenses =  ViewHelper.virementsRecManager.filterParamGen();
			ParametragesGenTeleCompense parametragesGenTeleCompense  = new ParametragesGenTeleCompense();
			if(parametragesGenTeleCompenses!=null && !parametragesGenTeleCompenses.isEmpty()){
				parametragesGenTeleCompense = parametragesGenTeleCompenses.get(0);
			}
			if(parametragesGenTeleCompense.getListEmails()!=null && !parametragesGenTeleCompense.getListEmails().isEmpty()){

				for(ParamEmail pe: parametragesGenTeleCompense.getListEmails()){
					if(pe.getValide().equals(Boolean.TRUE))
						mailsTo.add(pe.getEmail());
				}
			}

			if(ViewHelper.virementsRecManager.filterParamEmailAuto()!=null && !ViewHelper.virementsRecManager.filterParamEmailAuto().isEmpty()){
				for(ParamEmailAuto p: ViewHelper.virementsRecManager.filterParamEmailAuto()){
					ip = p.getIp();
					email = p.getEmail();
					pass = Encrypter.getInstance().decryptText(p.getPass());
					port = p.getPort();
				}
			}

			/////List<Incoherences> newListIncoherences = ViewHelper.virementsRecManager.findExistingIncoherencesAndMarkThem(listIncoherences);

			/****for(Incoherences dd : listIncoherences){
				//c.setSelect(check);
				if(new SimpleDateFormat("dd/MM/yyyy").format(dd.getDrec()).equals(new SimpleDateFormat("dd/MM/yyyy").format(new Date()))){

					if(dd.getEtat().equals("AT"))
						dd.setDisable(Boolean.TRUE);

				}
			}****/

			/**if(newListIncoherences!=null){
				for (Incoherences i:listIncoherences){

					for(Incoherences dd : newListIncoherences){

						if(i.equals(dd)){
							if(i.getEtat().equals("AT")){
								i.setDisable(Boolean.TRUE);
								System.out.println("*****++++++++++++*****SET DISABLE TO TRUE*****");
							}
						}
					}
				}
			}****/


		}
		catch(Exception e){

			this.error = true;
			e.printStackTrace();
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

	public void save(){

		try{
			
			List<Traitement> traiteList= new ArrayList<Traitement>();
			
			Collections.sort(listIncoherences);

			//Récupération des incohérences à décocher (lever l'opposition)
			for(Incoherences c : listIncoherences){
				//if(new SimpleDateFormat("dd/MM/yyyy").format(c.getDrec()).equals(new SimpleDateFormat("dd/MM/yyyy").format(new Date()))){
				if(c.getDisable().equals(Boolean.FALSE)){
					if(c.getSelect().equals(Boolean.TRUE)){
						c.setValide(Boolean.TRUE);
						//c.setEtat("AT");
						listIncoherencesAValider.add(c);
					}
					else{
						c.setValide(Boolean.FALSE);
						//c.setEtat("VA");
						listIncoherencesANePasValider.add(c);
					}
				}
				//}
			}

			String okay = ViewHelper.virementsRecManager.setIncoherencesEnAttente(listIncoherencesAValider, listIncoherencesANePasValider,isInBkeveOrBkheve, user);

			System.out.println("***okay**"+ okay); //Les oppositions ont été placées sur les comptes suspects et les transactions correspondantes passeront en attente

			if(okay.equals("ERROR_PARAM")){

				this.information = false;
				this.error = true;
				ExceptionHelper.showError("BV revoir le paramétrage du code opposition et code opération dans ce module !!!", this);
				return;

			}
			else if(okay.equals("ERROR_CONNECT")){

				this.information = false;
				this.error = true;
				ExceptionHelper.showError("Echec de connexion au module Amplitude !!!", this);
				return;

			}else if(okay.equals("KO")){

				this.information = false;
				this.error = true;
				ExceptionHelper.showError("Echec d'exécution de la requête dans Amplitude !!!", this);
				return;

			}else if(okay.equals("EMPTY")){
				//this.open = false;
				this.information = true;
				this.error = true;
				ExceptionHelper.showError("Ces valeurs ont déjà été mis en opposition et passerons en attente dans Amplitude !!!", this);
				return;
			}
			else if(okay.equals("OK")){
				/*******Affichage du rapport de contrôle de incohérences*****/
				HashMap<String, Object> parameter = new HashMap<String, Object>();

				parameter.put("uti", ViewHelper.getSessionUser().getName());
				parameter.put("periode", new SimpleDateFormat("dd/MM/yyyy").format(dateDebut)); //new Date())
				//parameter.put("periode", new SimpleDateFormat("dd/MM/yyyy").format(dateDebut) + "-" + new SimpleDateFormat("dd/MM/yyyy").format(dateFin));
				System.out.println("****listIncoherences.size()*****"+listIncoherences.size());
			
				
				int nbrIncoherencesVal = 0;
				int nbrIncoherencesIgn = 0;
				for(Incoherences doub: listIncoherences){
					if(doub.getDisable().equals(Boolean.FALSE)){
						if(doub.getValide().equals(Boolean.TRUE)){ //doublons validés
							nbrIncoherencesVal++;
							doub.setEtat("AT");
						}else{
							doub.setEtat("VA");
							nbrIncoherencesIgn++;
						}
					}
				}
				
				parameter.put("countTotalVir", listTraitement.get(0).getCountTotalVirements());
				parameter.put("countTotalVirATraite", listTraitement.get(0).getCountTotalVirementsATraiter());
				int virDejaTraite = listTraitement.get(0).getCountTotalVirements() - listTraitement.get(0).getCountTotalVirementsATraiter();
				parameter.put("countTotalVirDejaTraite", virDejaTraite);
				parameter.put("countBelowPlancher", listTraitement.get(0).getNbrBelowPlancher());
				parameter.put("countEMF", listTraitement.get(0).getNbrEMF());
				
				
				parameter.put("nbrIncoherencesVal", nbrIncoherencesVal);
				parameter.put("nbrIncoherencesIgn", nbrIncoherencesIgn);

				
				int sumIncoherences = nbrIncoherencesVal + nbrIncoherencesIgn;
				
				parameter.put("nombreVir", listTraitement.size()); //listIncoherences.size()
				parameter.put("totalInco", sumIncoherences);  // listIncoherences.size()
			
				double pourcentageIncoAvTraite = ((double)sumIncoherences/listTraitement.size())*100;
				double pourcentageIncoAprTraite = ((double)nbrIncoherencesVal/listTraitement.size())*100;
				
				DecimalFormat df = new DecimalFormat("#.##");
				
				String strPourcentageIncoAvTraite = df.format(pourcentageIncoAvTraite)+"%";
				String strPourcentageIncoAprTraite = df.format(pourcentageIncoAprTraite)+"%";
				
				//System.out.println("*****strPourcentageIncoAvTraite*****" + strPourcentageIncoAvTraite);
				//System.out.println("*****strPourcentageIncoAprTraite*****" + strPourcentageIncoAprTraite);

				parameter.put("pourcentageRejetAvantVal", strPourcentageIncoAvTraite);
				parameter.put("pourcentageRejetApresVal", strPourcentageIncoAprTraite);
				
				double seuil = listParams.get(0).getSeuil();
				seuil = seuil*100;
				String strSeuil = df.format(seuil)+"%";
				parameter.put("pourcentageSeuil", strSeuil);
				
				parameter.put("plancher", Separator(listParams.get(0).getMontant())+" FCFA");
				
				String gestionSigle = "";
				String gestionAlgoEuclidien = "";
				if(listParams.get(0).getGestionSigle().equals(Boolean.TRUE)){
					gestionSigle = "OUI";
				}else{
					gestionSigle = "NON";
				}
				if(listParams.get(0).getGestionAlgo2().equals(Boolean.TRUE)){
					gestionAlgoEuclidien = "OUI";
				}else{
					gestionAlgoEuclidien = "NON";
				}
				parameter.put("gestionSigle", gestionSigle);
				parameter.put("gestionAlgoEuclidien", gestionAlgoEuclidien);


				//reportViewer = new ReportViewer(listIncoherences,"rapportIncoherences.jasper", parameter, "afb.statistique.title", this,"/views/repport/ReportIncoherences.xhtml");
				reportViewer = new ReportViewer(listIncoherencesAValider,"rapportIncoherences.jasper", parameter, "afb.statistique.title", this,"/views/repport/ReportIncoherences.xhtml");
				reportViewer.viewReport();
				
		

				for(Incoherences d: listIncoherences){

					List<Incoherences> listI = ViewHelper.virementsRecManager.findExistingIncoherences(d);  //findByPrimaryKey(Incoherences.class, d.getId(), null);
					if(listI!=null && !listI.isEmpty()){
						Incoherences incoBD = listI.get(0); 
						incoBD.setEtat(d.getEtat());
						incoBD.setValide(d.getValide());
						incoBD.setUtiPortal(ViewHelper.getSessionUser().getLogin());
						ViewHelper.virementsRecManagerDAOLocal.update(incoBD);
						System.out.println("**********incoBD.getTraitementID()********" + incoBD.getTraitementID());
						if(incoBD.getTraitementID()!=null){
							System.out.println("++++++++++++++++++incoBD.getTraitementID()********" + incoBD.getTraitementID());
							Traitement traite = ViewHelper.virementsRecManager.findByPrimaryKey(Traitement.class, incoBD.getTraitementID(), null);
							traite.setEtat(incoBD.getEtat());
							if(traite!=null)traiteList.add(traite);
						}
						
					}else{
						if(d.getTraitementID()!=null){
							System.out.println("++++++++++++++++++incoBD.getTraitementID()********" + d.getTraitementID());
							Traitement traite = ViewHelper.virementsRecManager.findByPrimaryKey(Traitement.class, d.getTraitementID(), null);
							traite.setEtat(d.getEtat());
							if(traite!=null)traiteList.add(traite);
						}
						ViewHelper.virementsRecManager.saveIncoherences(d);
					}

				}

				/************************************************ENVOI EMAIL*************************************************************************************************/
				try{

					System.out.println("******SEND EMAIL********");

					String fName = new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date());
					FileOutputStream fileOuputStream = new FileOutputStream(ViewHelper.getPiecesJointesDir() +  "Rapport_Incoherences_Intitules_" + fName+".pdf");
					fileOuputStream.write(ViewHelper.virementsRecManager.getReportPDFBytes(ViewHelper.getReportsDir2().concat("rapportIncoherences.jasper"), parameter, listIncoherencesAValider));
					fileOuputStream.close();

					filesNames.clear();filesPath.clear();
					filesNames.add("Rapport Contrôle des Intitulés de Comptes Sur Virements RECUS de SYSTAC_" + fName+".pdf");
					filesPath.add(ViewHelper.getPiecesJointesDir() + "Rapport_Incoherences_Intitules_" + fName+".pdf");
					subject = "Rapport Contrôle des Intitulés de Comptes Sur Virements RECUS de SYSTAC";
					messageCorps = "Bonjour " + "\n" + "Ci-joint le rapport du contrôle des incohérences sur intitulés de comptes sur Virements reçus de Systac." + "\n\n"+
							"Bonne Reception !!!";

					
					
					StatistiqueRapports stat = new StatistiqueRapports();
					stat.setRapport("Rapport_Incoherences_Intitules_" + fName+".pdf");
					//stat.setTypeProcess("");
					ViewHelper.virementsRecManagerDAOLocal.save(stat);

					
					//SendMail.sendMail(filesNames, filesPath, mailsTo, mailsCC, mailsBCC, subject, messageCorps, ip, email, pass, port);
					try {
						List<ParamEmailAuto> parametres = ViewHelper.virementsRecManagerDAOLocal.filter(ParamEmailAuto.class, null, null, null, null, 0, -1);
						if(parametres!=null&&!parametres.isEmpty()){
							AfrilandSendMail.sendMail(filesNames, filesPath, mailsTo, mailsCC, subject, messageCorps, email, pass, email + "@afrilandfirstbank.com", "AFRILAND FIRST BANK", ip, String.valueOf(port));
						}
					} catch (Exception ex) {
						// TODO Auto-generated catch block
					}

					System.out.println("******SENDING EMAIL********");

				}
				catch(Exception e){

					e.printStackTrace();
				}

				/***********************************************************************************************************************************************************/

			}else{   //PAS DE PARAMETRAGE EFFECTUE SUR LE CONTROLE DE L'OPPOSITION SUR LE CODE OPE 144 (LE DEX DOIT LE FAIRE)

				this.information = false;
				this.error = true;
				ExceptionHelper.showError(okay, this);

				filesPath.clear();
				filesNames.clear();
				mailsTo.clear();
				mailsCC.clear();
				mailsTo.add("charles_bakond@afrilandfirstbank.com");
				mailsTo.add("navarro_atchobi@afrilandfirstbank.com");
				mailsCC.add("stephane_mouafo@afrilandfirstbank.com");
				mailsCC.add("marc_mbida@afrilandfirstbank.com");

				subject = "ALERTE CONTROLE VIR REC SYSTAC - PARAMETRAGE AMPLITUDE REQUIS !!!";

				messageCorps = "Bonjour, " + "\n" + " Le paramétrage Amplitude autorisant le contrôle du code opposition crée pour les virements Systac, pour le code opération des virement reçus de Systac n'est pas effectif!!! " + "\n" +
						"Bien vouloir le renseigner !!!! Ceci permettra à la DOB de procéder au contrôle des incohérences sur intitulés de comptes." + "\n" + " Merci.";

				//SendMail.sendMail(filesNames, filesPath, mailsTo, mailsCC, mailsBCC, subject, messageCorps, ip, email, pass, port);
				try {
					List<ParamEmailAuto> parametres = ViewHelper.virementsRecManagerDAOLocal.filter(ParamEmailAuto.class, null, null, null, null, 0, -1);
					if(parametres!=null&&!parametres.isEmpty()){
						AfrilandSendMail.sendMail(filesNames, filesPath, mailsTo, mailsCC, subject, messageCorps, email, pass, email + "@afrilandfirstbank.com", "AFRILAND FIRST BANK", ip, String.valueOf(port));
					}
				} catch (Exception ex) {
					// TODO Auto-generated catch block
				}

				return;
			}

			if(!listIncoherencesAValider.isEmpty())listIncoherencesAValider.clear();
			if(!listIncoherencesANePasValider.isEmpty())listIncoherencesANePasValider.clear();


			/**Traitement t;
			for(Incoherences i: listIncoherences){

				t = i.getTraitement();

				if(t!=null){
					t = ViewHelper.virementsRecManager.findByPrimaryKey(Traitement.class, t.getId(), null);
					t.setEtat(i.getEtat());
					ViewHelper.virementsRecManager.updateTraitement(t);
				}
			}*/
			
			for(Traitement t: traiteList){
				System.out.println("**********updating*****" + t.getId());
				ViewHelper.virementsRecManager.updateTraitement(t);
			}
			
			//traiteList.clear();

		}catch(Exception e){

			e.printStackTrace();
		}

	}

	
	/**
	 * Separateur
	 * @param mont
	 * @return
	 */
	public static String Separator(Double mont){

		String result ="";

		String val = String.valueOf(mont.intValue());

		int i = 0;
		int j = val.length();
		int p = 0;
		while(val.length() > i){

			if(p == 3){
				result = " "+result;				
				p = 0;
			}
			result = val.substring(j-1,j)+result;	
			p++;
			i++;
			j--;
		}

		return result;
	}
	


	@Override
	protected void disposeResourceOnClose() {

		listIncoherences.clear();
		count=0;
	}


	public void processCheckAll(ActionEvent event){

		// if(Boolean.FALSE.equals(check)) check = Boolean.TRUE;
		// else check = Boolean.FALSE;
		int count = 0;
		for(Incoherences c : listIncoherences){
			//if(new SimpleDateFormat("dd/MM/yyyy").format(c.getDrec()).equals(new SimpleDateFormat("dd/MM/yyyy").format(new Date()))){
			if(c.getDisable().equals(Boolean.FALSE)){
				c.setSelect(check);
				if(check.equals(Boolean.TRUE)){
					if(count==0)
						totalIncoherences=0;
					totalIncoherences+=1;
					count++;
				}
				else{
					if(totalIncoherences!=0)
						totalIncoherences-=1;
				}
			}
			//}
		}

	}


}
