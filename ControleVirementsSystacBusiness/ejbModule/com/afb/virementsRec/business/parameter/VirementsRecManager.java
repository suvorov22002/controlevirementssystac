/**
 * 
 */
package com.afb.virementsRec.business.parameter;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.model.SelectItem;
import javax.mail.MessagingException;
import javax.naming.InitialContext;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.richfaces.model.selection.Selection;

import afb.dsi.dpd.portal.business.facade.IFacadeManagerRemote;
import afb.dsi.dpd.portal.jpa.entities.DataSystem;
import afb.dsi.dpd.portal.jpa.entities.SMSWeb;
import afb.dsi.dpd.portal.jpa.entities.User;
import afb.dsi.dpd.portal.jpa.tools.PortalHelper;

import com.afb.dsi.alertes.AfrilandSendMail;
import com.afb.virementsRec.business.parameter.shared.IVirementsRecManager;
import com.afb.virementsRec.dao.parameter.shared.IVirementsRecManagerDAOLocal;
import com.afb.virementsRec.jpa.datamodel.CaracteristiquesVir;
import com.afb.virementsRec.jpa.datamodel.CaracteristiquesVirItem;
import com.afb.virementsRec.jpa.datamodel.Doublons;
import com.afb.virementsRec.jpa.datamodel.FichiersComptabilisationAller;
import com.afb.virementsRec.jpa.datamodel.FichiersComptabilisationRetour;
import com.afb.virementsRec.jpa.datamodel.FichiersSupprimeACauseDeDoublonsNomsFichiers;
import com.afb.virementsRec.jpa.datamodel.FichiersSupprimeACauseDeValeursEnDoublonsDansAutreFichier;
import com.afb.virementsRec.jpa.datamodel.Incoherences;
import com.afb.virementsRec.jpa.datamodel.MotifsDeRejet;
import com.afb.virementsRec.jpa.datamodel.MotsClesEtCharSpeciaux;
import com.afb.virementsRec.jpa.datamodel.ParamBDADT;
import com.afb.virementsRec.jpa.datamodel.ParamCompensateurCentrale;
import com.afb.virementsRec.jpa.datamodel.ParamEmail;
import com.afb.virementsRec.jpa.datamodel.ParamEmailAuto;
import com.afb.virementsRec.jpa.datamodel.Parametrages;
import com.afb.virementsRec.jpa.datamodel.ParametragesCaracteresSpeciaux;
import com.afb.virementsRec.jpa.datamodel.ParametragesGenTeleCompense;
import com.afb.virementsRec.jpa.datamodel.ParametragesImpots;
import com.afb.virementsRec.jpa.datamodel.RapatriementImagesAller;
import com.afb.virementsRec.jpa.datamodel.RapatriementImagesRetour;
import com.afb.virementsRec.jpa.datamodel.Rejet;
import com.afb.virementsRec.jpa.datamodel.SendMail;
import com.afb.virementsRec.jpa.datamodel.SortTraitement;
import com.afb.virementsRec.jpa.datamodel.StatistiqueRapports;
import com.afb.virementsRec.jpa.datamodel.StatutJournee;
import com.afb.virementsRec.jpa.datamodel.TourCompensation;
import com.afb.virementsRec.jpa.datamodel.Traitement;
import com.afb.virementsRec.jpa.datamodel.TraitementImpots;
import com.afb.virementsRec.jpa.datamodel.TraitementTourCompensation;
import com.afb.virementsRec.jpa.datamodel.TypeProcess;
import com.afb.virementsRec.jpa.datamodel.TypeTraitement;
import com.afb.virementsRec.jpa.datamodel.ValidateDoublonsInFichier;
import com.afb.virementsRec.jpa.dto.Account;
import com.afb.virementsRec.jpa.dto.Bkbeacrv;
import com.afb.virementsRec.jpa.dto.Bkevec;
import com.afb.virementsRec.jpa.dto.Bkrtgseve;
import com.afb.virementsRec.jpa.dto.Client;
import com.afb.virementsRec.jpa.dto.ResponseDataAccount;
import com.afb.virementsRec.jpa.dto.ResponseDataBkeacvr;
import com.afb.virementsRec.jpa.dto.ResponseDataClient;
import com.afb.virementsRec.jpa.dto.Shared;
import com.afb.virementsRec.jpa.dto.StoppageAccount;
import com.afb.virementsRec.jpa.dto.bkeve;
import com.afb.virementsRec.jpa.tools.DateUtil;
import com.afb.virementsRec.jpa.tools.HelperQuerry;
import com.ibm.icu.text.SimpleDateFormat;
import com.yashiro.persistence.utils.annotations.AllowedRole;
import com.yashiro.persistence.utils.annotations.ProtectedClass;
import com.yashiro.persistence.utils.dao.tools.OrderContainer;
import com.yashiro.persistence.utils.dao.tools.PropertyContainer;
import com.yashiro.persistence.utils.dao.tools.RestrictionsContainer;
import com.yashiro.persistence.utils.dao.tools.encrypter.Encrypter;




/**
 * Implementation du service Metier de gestion des Parametrages generaux
 * @author Stéphane Mouafo
 * @version 1.0
 */
@ProtectedClass(system = "MonitoringIEManager", methods = {} )
//@Interceptors(value = {AuditModuleInterceptor.class})
@Stateless(name = IVirementsRecManager.SERVICE_NAME, mappedName = IVirementsRecManager.SERVICE_NAME, description = "Implementation du service metier")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class VirementsRecManager implements IVirementsRecManager {

	/**
	 * Service DAO de gestion des Parametrages generaux
	 */
	@EJB
	private IVirementsRecManagerDAOLocal ManagerDAO;

	private TimerTask task;

	private Timer timer;

	private TimerTask task2;

	private Timer timer2;

	private TimerTask task3;

	private Timer timer3;

	private  Connection conDELTA;
	
	private DataSystem dsAIF = null;
	private DataSystem dsCbs = null;

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

	private Integer nbrFichCopieCompta;

	private Integer nbrDoublonsCompta; //nbr de fichiers en doubles


	/*private Integer nbrFichAArchiverComptaAller;

	private Integer nbrFichArchiveComptaAller;*/

	private Integer nbrValeursEnDoubleCompta;
	private Integer nbrValeursDeposeesCompta;
	private Integer nbrFichContenantDoublonsCompta;

	private Map<String, List<String>> mapFichierEtValEnDoubleComptaAllerTraite1 = new HashMap<>();

	private List<String> listFichierSupprimePourDoublonsEntreFichiers = new ArrayList<>();

	private List<String> listFichierContenantDoublonsEntreFichiers = new ArrayList<>();


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

	//private ReportViewer reportViewer;

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

	/**
	 * Un Logger
	 */
	protected Log logger = LogFactory.getLog(VirementsRecManager.class);


	/*
	 * (non-Javadoc)
	 * @see com.afb.portal.buisness.parameter.shared.IParameterManager#findByPrimaryKey(java.lang.Class, java.lang.Object, com.yashiro.persistence.utils.dao.tools.PropertyContainer)
	 */
	@Override
	public <T> T findByPrimaryKey(Class<T> entityClass, Object entityID,PropertyContainer properties) {

		// Appel de la DAO
		return ManagerDAO.findByPrimaryKey(entityClass, entityID, properties);

	}


	
	@AllowedRole(name = "validateOpeReserv", displayName = "VIR_SYSTAC.ValidationOperationReservees")
	public void validateOpeReserv(){}

	@AllowedRole(name = "param", displayName = "VIR_SYSTAC.Parametrages")
	public void param(){}

	/*@AllowedRole(name = "findDoublons", displayName = "VIR_SYSTAC.Verifier Doublons")
	public void findDoublons(){}*/


	@AllowedRole(name = "findIncoherences", displayName = "VIR_SYSTAC.Verifier Incoherences")
	public void findIncoherences(){}


	@AllowedRole(name = "traitementDoublons", displayName = "VIR_SYSTAC.Traitement des doublons")
	public void traitementD(){}

	@AllowedRole(name = "traitementIncoherences", displayName = "VIR_SYSTAC.Traitement des incohérences")
	public void traitementI(){}

	@AllowedRole(name = "traitementValidationDoubDansFic", displayName = "VIR_SYSTAC.Validation des doublons dans les fichiers")
	public void traitementV(){}


	@AllowedRole(name = "statistiquesDoublons", displayName = "VIR_SYSTAC.Statistiques des doublons")
	public void statistiquesDoublons(){}

	@AllowedRole(name = "statistiquesIncoherences", displayName = "VIR_SYSTAC.Statistiques des incohérences")
	public void statistiquesIncoherences(){}

	@AllowedRole(name = "statistiquesRapports", displayName = "VIR_SYSTAC.Statistiques des rapports")
	public void statistiquesRapports(){}


	@AllowedRole(name = "paramCompenseCentrale", displayName = "VIR_SYSTAC.ParamCompensateurCentral")
	public void compenseCentrale(){}

	//private String OUTPUT_COMPILED_FILE;

	private List<String> fileList = new ArrayList<String>();


	@AllowedRole(name = "paramImpots", displayName = "VIR_SYSTAC.Param.Impots")
	public void paramImpots(){}

	@AllowedRole(name = "traitementImpots", displayName = "VIR_SYSTAC.Traite.Impots")
	public void traitementImpots(){}



	@Override
	public List<CaracteristiquesVir> filterCaracteristiquesVir(Parametrages parametrage, CaracteristiquesVir c, CaracteristiquesVirItem cvi) {
		// TODO Auto-generated method stub
		RestrictionsContainer rc = RestrictionsContainer.getInstance();
		if(parametrage!=null)rc.add(Restrictions.eq("param", parametrage));
		if(c!=null){
			rc.add(Restrictions.eq("caracteristiquesItems", c.getCaracteristiquesItems()));
			rc.add(Restrictions.eq("rang", c.getRang()));
		}
		rc.add(Restrictions.eq("valide", Boolean.TRUE));

		return ManagerDAO.filter(CaracteristiquesVir.class, null, rc, null, null, 0, -1);
	}

	@Override
	public List<MotsClesEtCharSpeciaux> filterMotsCles(Parametrages parametrage, MotsClesEtCharSpeciaux c) {
		// TODO Auto-generated method stub
		RestrictionsContainer rc = RestrictionsContainer.getInstance();
		if(parametrage!=null)rc.add(Restrictions.eq("param", parametrage));
		if(c!=null){
			rc.add(Restrictions.eq("motCles", c.getMotCles()));
		}
		rc.add(Restrictions.eq("valide", Boolean.TRUE));

		return ManagerDAO.filter(MotsClesEtCharSpeciaux.class, null, rc, null, null, 0, -1);
	}


	@Override
	public List<MotifsDeRejet> filterMotifRejet(ParametragesImpots parametrage, MotifsDeRejet c) {
		// TODO Auto-generated method stub
		RestrictionsContainer rc = RestrictionsContainer.getInstance();
		if(parametrage!=null)rc.add(Restrictions.eq("param", parametrage));
		if(c!=null){
			rc.add(Restrictions.eq("typeRejet", c.getTypeRejet()));
		}
		rc.add(Restrictions.eq("valide", Boolean.TRUE));

		return ManagerDAO.filter(MotifsDeRejet.class, null, rc, null, null, 0, -1);
	}



	@Override
	public CaracteristiquesVir saveCaracteristiquesVir(CaracteristiquesVir c) {
		// TODO Auto-generated method stub
		return ManagerDAO.save(c);
	}



	@Override
	public CaracteristiquesVir updateCaracteristiquesVir(CaracteristiquesVir c) {
		// TODO Auto-generated method stub
		return ManagerDAO.update(c);
	}

	@Override
	public List<Parametrages> filterParams(){
		return ManagerDAO.filter(Parametrages.class, null, null, null, null, 0, -1);

	}

	public List<ParametragesGenTeleCompense> filterParamGen(){
		return ManagerDAO.filter(ParametragesGenTeleCompense.class, null, null, null, null, 0, -1);
	}

	public List<ParamEmail> filterEmails(){
		return ManagerDAO.filter(ParamEmail.class, null, RestrictionsContainer.getInstance().add(Restrictions.eq("valide", Boolean.TRUE)), null, null, 0, -1);
	}



	@Override
	public Parametrages saveParametrages(Parametrages p) {
		// TODO Auto-generated method stub
		return ManagerDAO.save(p);
	}


	@Override
	public Parametrages updateParametrages(Parametrages p) {
		// TODO Auto-generated method stub
		return ManagerDAO.update(p);
	}


	/*public List<Traitement> filterVirements(Traitement t){

		RestrictionsContainer rc = RestrictionsContainer.getInstance();

		rc.add(Restrictions.eq("agence", t.getAgence()));
		rc.add(Restrictions.eq("ncp", t.getNcp()));
		rc.add(Restrictions.eq("devise", t.getDevise()));
		rc.add(Restrictions.eq("dco", t.getDco()));
		rc.add(Restrictions.eq("dva", t.getDva()));
		rc.add(Restrictions.eq("lib", t.getLib()));
		rc.add(Restrictions.eq("montant", t.getMontant()));
		rc.add(Restrictions.eq("ope", t.getOpe()));
		rc.add(Restrictions.eq("uti", t.getUti()));


		return ManagerDAO.filter(Traitement.class, null, rc, null, null, 0, -1);
	}*/


	public int checkIfEveInBkEveOrBkheve(Date dateDebut, Date dateFin){

		int result = 0;

		try{

			Statement st = null;
			ResultSet rs = null;

			openDELTAConnection();

			if(conDELTA==null){System.out.println("----ConDelta est vide----");result = 0;}

			String dateDeb = new SimpleDateFormat("yyyy-MM-dd").format(dateDebut);
			String dateF = new SimpleDateFormat("yyyy-MM-dd").format(dateFin);

			String sql = "Select age2,ncp2,clc2,dev2,nom2,dsai,dco,dva2,etabr,guibr,comb,cleb,nomb,ope, mht, lib1,eta,bkeve.uti from bkeve left join bkcli on bkcli.cli=bkeve.cli2 where ope = '144' and dsai between '"+dateDeb+"' and '"+dateF+"'  order by nom2,ncp2" ;
			st = conDELTA.createStatement();
			rs = st.executeQuery(sql);

			if(rs.next()){
				//L'évènement est dans bkeve
				result = 1;
			}else{
				//L'évènements est dans bkheve
				result = 2;
			}

			if(rs!=null)
				rs.close();

			if(st!=null)
				st.close();

		}catch(Exception e){

			e.printStackTrace();
		}

		finally
		{
			try {

				closeDELTAConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;

	}

	//@AllowedRole(name = "filterTraitementDoublons", displayName = "VIR_SYSTAC.Rechercher Virements Doublons")
	@Override
	public List<Traitement> filterVirementsDoublons(Date dateDebut, Date dateFin, Boolean traite, int tailleParLot, String utiPortal){
		/***
		Statement st = null;
		ResultSet rs = null;

		List<Traitement> traitements = new ArrayList<Traitement>();


		try{

			String dateDeb = new SimpleDateFormat("yyyy-MM-dd").format(dateDebut);
			String dateF = new SimpleDateFormat("yyyy-MM-dd").format(dateFin);

			System.out.println("----dateDeb----" + dateDeb);
			System.out.println("----dateF----" + dateF);



			//traitements = filterVirementsDoublonsInDB(dateDebut, dateFin, traite, tailleParLot);
			traitements = filterVirementsInDB(dateDebut, dateFin, traite, tailleParLot);

			if(traitements!=null&&!traitements.isEmpty()){

				//On retourne cette liste à la fin

			}else{



				String agence; //agence bénéficiaire
				String ncp; //numéro de compte bénéficiaire
				String clc; //clé de compte bénéficiaire
				String devise ; //devise bénéficiaire
				String nom2 ; //nom du bénéficiaire
				String nomrest ; //nom du bénéficiaire Amplitude
				Date dsai ;  //date de saisie
				Date dco ; //date comptable
				Date dva ; //date de valeur
				String etabr ; //code établissement de règlement (débiteur/donneur d'ordre)
				String guibr ; //code guichet de règlement (débiteur)
				String comb ; //numéro de compte du débiteur
				String cleb ; //clé de compte du débiteur
				String nomb ; //nom de l'établissement autre banque (débiteur)
				String ope ; //code opération
				String eve; //évènement
				Double montant ; //montant de l'opération
				String lib ; //libellé de l'opération
				String eta ; //état de l'opération (VA, AT, FO)
				String uti ; //Code utilisateur ayant initié l'évènement

				Traitement traitement;



				openDELTAConnection();

				if(conDELTA==null){System.out.println("----ConDelta est vide----");return null;}


				String sql = "Select age2,ncp2,clc2,dev2,nom2,dsai,dco,dva2,etabr,guibr,comb,cleb,nomb,ope, mht, lib1,eta,bkeve.uti,bkeve.eve, bkcli.nomrest from bkeve left join bkcli on bkcli.cli=bkeve.ncp2[1,7] where ope = '144' and dsai between '"+dateDeb+"' and '"+dateF+"'  order by nom2,ncp2" ;
				st = conDELTA.createStatement();
				rs = st.executeQuery(sql);


				if(!rs.next()){
					System.out.println("********IN BKHEVE*********");
					rs=null;
					st=null;
					//L'évènements est déjà dans le bkheve
					sql = "Select age2,ncp2,clc2,dev2,nom2,dsai,dco,dva2,etabr,guibr,comb,cleb,nomb,ope, mht, lib1,eta,bkheve.uti, bkheve.eve, bkcli.nomrest from bkheve left join bkcli on bkcli.cli=bkheve.ncp2[1,7] where ope = '144' and dsai between '"+dateDeb+"' and '"+dateF+"'  order by nom2,ncp2 ";
					st = conDELTA.createStatement();
					rs = st.executeQuery(sql);


					agence = (rs.getString("age2")!=null)?rs.getString("age2").trim(): " ";  //agence bénéficiaire
					ncp = (rs.getString("ncp2")!=null)?rs.getString("ncp2").trim(): " "; //numéro de compte bénéficiaire
					clc = (rs.getString("clc2")!=null)?rs.getString("clc2").trim(): " "; //clé de compte bénéficiaire
					devise = (rs.getString("dev2")!=null)?rs.getString("dev2").trim(): " "; //devise bénéficiaire
					nom2 = (rs.getString("nom2")!=null)?rs.getString("nom2").trim(): " "; //nom du bénéficiaire
					nomrest = (rs.getString("nomrest")!=null)?rs.getString("nomrest").trim(): " "; //nom du bénéficiaire Amplitude
					dsai = (rs.getDate("dsai")!=null)?rs.getDate("dsai"): null;  //date de saisie
					dco = (rs.getDate("dco")!=null)?rs.getDate("dco"): null; //date comptable
					dva = (rs.getDate("dva2")!=null)?rs.getDate("dva2"): null; //date de valeur
					etabr = (rs.getString("etabr")!=null)?rs.getString("etabr").trim(): " "; //code établissement de règlement (débiteur/donneur d'ordre)
					guibr = (rs.getString("guibr")!=null)?rs.getString("guibr").trim(): " "; //code guichet de règlement (débiteur)
					comb = (rs.getString("comb")!=null)?rs.getString("comb").trim(): " "; //numéro de compte du débiteur
					cleb = (rs.getString("cleb")!=null)?rs.getString("cleb").trim(): " "; //clé de compte du débiteur
					nomb = (rs.getString("nomb")!=null)?rs.getString("nomb").trim(): " "; //nom de l'établissement autre banque (débiteur)
					ope = (rs.getString("ope")!=null)?rs.getString("ope").trim(): " "; //code opération
					eve = (rs.getString("eve")!=null)?rs.getString("eve").trim(): " "; //évènement
					montant = (rs.getDouble("mht")!=0.0)?rs.getDouble("mht"): 0.0; //montant de l'opération
					lib = (rs.getString("lib1")!=null)?rs.getString("lib1").trim(): " "; //libellé de l'opération
					eta = (rs.getString("eta")!=null)?rs.getString("eta").trim(): " "; //état de l'opération (VA, AT, FO)
					uti = (rs.getString("uti")!=null)?rs.getString("uti").trim(): " "; //Code utilisateur ayant initié l'évènement

					traitement = new Traitement(agence, ncp, clc, devise, nom2, nomrest, dsai, dco, dva, ope, eve, montant, lib, eta, uti, etabr, guibr, comb, cleb, nomb, new Date(),utiPortal);
					traitements.add(traitement);


				}else{

					System.out.println("********IN BKEVE*********");

					agence = (rs.getString("age2")!=null)?rs.getString("age2").trim(): " ";  //agence bénéficiaire
					ncp = (rs.getString("ncp2")!=null)?rs.getString("ncp2").trim(): " "; //numéro de compte bénéficiaire
					clc = (rs.getString("clc2")!=null)?rs.getString("clc2").trim(): " "; //clé de compte bénéficiaire
					devise = (rs.getString("dev2")!=null)?rs.getString("dev2").trim(): " "; //devise bénéficiaire
					nom2 = (rs.getString("nom2")!=null)?rs.getString("nom2").trim(): " "; //nom du bénéficiaire
					nomrest = (rs.getString("nomrest")!=null)?rs.getString("nomrest").trim(): " "; //nom du bénéficiaire Amplitude
					dsai = (rs.getDate("dsai")!=null)?rs.getDate("dsai"): null;  //date de saisie
					dco = (rs.getDate("dco")!=null)?rs.getDate("dco"): null; //date comptable
					dva = (rs.getDate("dva2")!=null)?rs.getDate("dva2"): null; //date de valeur
					etabr = (rs.getString("etabr")!=null)?rs.getString("etabr").trim(): " "; //code établissement de règlement (débiteur/donneur d'ordre)
					guibr = (rs.getString("guibr")!=null)?rs.getString("guibr").trim(): " "; //code guichet de règlement (débiteur)
					comb = (rs.getString("comb")!=null)?rs.getString("comb").trim(): " "; //numéro de compte du débiteur
					cleb = (rs.getString("cleb")!=null)?rs.getString("cleb").trim(): " "; //clé de compte du débiteur
					nomb = (rs.getString("nomb")!=null)?rs.getString("nomb").trim(): " "; //nom de l'établissement autre banque (débiteur)
					ope = (rs.getString("ope")!=null)?rs.getString("ope").trim(): " "; //code opération
					eve = (rs.getString("eve")!=null)?rs.getString("eve").trim(): " "; //évènement
					montant = (rs.getDouble("mht")!=0.0)?rs.getDouble("mht"): 0.0; //montant de l'opération
					lib = (rs.getString("lib1")!=null)?rs.getString("lib1").trim(): " "; //libellé de l'opération
					eta = (rs.getString("eta")!=null)?rs.getString("eta").trim(): " "; //état de l'opération (VA, AT, FO)
					uti = (rs.getString("uti")!=null)?rs.getString("uti").trim(): " "; //Code utilisateur ayant initié l'évènement

					traitement = new Traitement(agence, ncp, clc, devise, nom2, nomrest, dsai, dco, dva, ope, eve, montant, lib, eta, uti, etabr, guibr, comb, cleb, nomb, new Date(),utiPortal);
					traitements.add(traitement);
				}


				while(rs.next()){

					agence = (rs.getString("age2")!=null)?rs.getString("age2").trim(): " ";  //agence bénéficiaire
					ncp = (rs.getString("ncp2")!=null)?rs.getString("ncp2").trim(): " "; //numéro de compte bénéficiaire
					clc = (rs.getString("clc2")!=null)?rs.getString("clc2").trim(): " "; //clé de compte bénéficiaire
					devise = (rs.getString("dev2")!=null)?rs.getString("dev2").trim(): " "; //devise bénéficiaire
					nom2 = (rs.getString("nom2")!=null)?rs.getString("nom2").trim(): " "; //nom du bénéficiaire
					nomrest = (rs.getString("nomrest")!=null)?rs.getString("nomrest").trim(): " "; //nom du bénéficiaire Amplitude
					dsai = (rs.getDate("dsai")!=null)?rs.getDate("dsai"): null;  //date de saisie
					dco = (rs.getDate("dco")!=null)?rs.getDate("dco"): null; //date comptable
					dva = (rs.getDate("dva2")!=null)?rs.getDate("dva2"): null; //date de valeur
					etabr = (rs.getString("etabr")!=null)?rs.getString("etabr").trim(): " "; //code établissement de règlement (débiteur/donneur d'ordre)
					guibr = (rs.getString("guibr")!=null)?rs.getString("guibr").trim(): " "; //code guichet de règlement (débiteur)
					comb = (rs.getString("comb")!=null)?rs.getString("comb").trim(): " "; //numéro de compte du débiteur
					cleb = (rs.getString("cleb")!=null)?rs.getString("cleb").trim(): " "; //clé de compte du débiteur
					nomb = (rs.getString("nomb")!=null)?rs.getString("nomb").trim(): " "; //nom de l'établissement autre banque (débiteur)
					ope = (rs.getString("ope")!=null)?rs.getString("ope").trim(): " "; //code opération
					eve = (rs.getString("eve")!=null)?rs.getString("eve").trim(): " "; //évènement
					montant = (rs.getDouble("mht")!=0.0)?rs.getDouble("mht"): 0.0; //montant de l'opération
					lib = (rs.getString("lib1")!=null)?rs.getString("lib1").trim(): " "; //libellé de l'opération
					eta = (rs.getString("eta")!=null)?rs.getString("eta").trim(): " "; //état de l'opération (VA, AT, FO)
					uti = (rs.getString("uti")!=null)?rs.getString("uti").trim(): " "; //Code utilisateur ayant initié l'évènement

					traitement = new Traitement(agence, ncp, clc, devise, nom2, nomrest, dsai, dco, dva, ope, eve, montant, lib, eta, uti, etabr, guibr, comb, cleb, nomb, new Date(),utiPortal);
					traitements.add(traitement);

				}

				if(rs!=null)
					rs.close();

				if(st!=null)
					st.close();


				for(Traitement t: traitements){

					ManagerDAO.save(t);

				}
				traitements.clear();

			}
		}
		catch(Exception e){

			e.printStackTrace();
		}

		finally
		{
			try {
				//if(traitements==null||traitements.isEmpty())
				traitements = filterVirementsDoublonsInDB(dateDebut, dateFin, traite, tailleParLot);
				closeDELTAConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		return traitements;*/return null;
	}



	/***@AllowedRole(name = "filterTraitementIncoherences", displayName = "VIR_SYSTAC.Rechercher Virements Incoherences")
	@Override
	public List<Traitement> filterVirementsIncoherences(Date dateDebut, Date dateFin, Boolean traite, int tailleParLot, String utiPortal){

		Statement st = null;
		ResultSet rs = null;

		List<Traitement> traitements = new ArrayList<Traitement>();


		try{

			String dateDeb = new SimpleDateFormat("yyyy-MM-dd").format(dateDebut);
			String dateF = new SimpleDateFormat("yyyy-MM-dd").format(dateFin);

			System.out.println("----dateDeb----" + dateDeb);
			System.out.println("----dateF----" + dateF);



			//traitements = filterVirementsIncoherencesInDB(dateDebut, dateFin, traite, tailleParLot);
			traitements = filterVirementsInDB(dateDebut, dateFin, traite, tailleParLot);
			if(traitements!=null&&!traitements.isEmpty()){

				//On retourne cette liste à la fin

			}else{


				String agence; //agence bénéficiaire
				String ncp; //numéro de compte bénéficiaire
				String clc; //clé de compte bénéficiaire
				String devise ; //devise bénéficiaire
				String nom2 ; //nom du bénéficiaire
				String nomrest ; //nom du bénéficiaire Amplitude
				Date dsai ;  //date de saisie
				Date dco ; //date comptable
				Date dva ; //date de valeur
				String etabr ; //code établissement de règlement (débiteur/donneur d'ordre)
				String guibr ; //code guichet de règlement (débiteur)
				String comb ; //numéro de compte du débiteur
				String cleb ; //clé de compte du débiteur
				String nomb ; //nom de l'établissement autre banque (débiteur)
				String ope ; //code opération
				String eve; //évènement
				Double montant ; //montant de l'opération
				String lib ; //libellé de l'opération
				String eta ; //état de l'opération (VA, AT, FO)
				String uti ; //Code utilisateur ayant initié l'évènement

				Traitement traitement;


				openDELTAConnection();

				if(conDELTA==null){System.out.println("----ConDelta est vide----");return null;}



				String sql = "Select age2,ncp2,clc2,dev2,nom2,dsai,dco,dva2,etabr,guibr,comb,cleb,nomb,ope, mht, lib1,eta,bkeve.uti, bkeve.eve, bkcli.nomrest from bkeve left join bkcli on bkcli.cli=bkeve.ncp2[1,7] where ope = '144' and dsai between '"+dateDeb+"' and '"+dateF+"'  order by nom2,ncp2" ;
				st = conDELTA.createStatement();
				rs = st.executeQuery(sql);


				if(!rs.next()){
					System.out.println("********IN BKHEVE*********");
					rs=null;
					st=null;
					//L'évènements est déjà dans le bkheve
					sql = "Select age2,ncp2,clc2,dev2,nom2,dsai,dco,dva2,etabr,guibr,comb,cleb,nomb,ope, mht, lib1,eta,bkheve.uti,bkheve.eve, bkcli.nomrest from bkheve left join bkcli on bkcli.cli=bkheve.ncp2[1,7] where ope = '144' and dsai between '"+dateDeb+"' and '"+dateF+"'  order by nom2,ncp2 ";
					st = conDELTA.createStatement();
					rs = st.executeQuery(sql);


					agence = (rs.getString("age2")!=null)?rs.getString("age2").trim(): " ";  //agence bénéficiaire
					ncp = (rs.getString("ncp2")!=null)?rs.getString("ncp2").trim(): " "; //numéro de compte bénéficiaire
					clc = (rs.getString("clc2")!=null)?rs.getString("clc2").trim(): " "; //clé de compte bénéficiaire
					devise = (rs.getString("dev2")!=null)?rs.getString("dev2").trim(): " "; //devise bénéficiaire
					nom2 = (rs.getString("nom2")!=null)?rs.getString("nom2").trim(): " "; //nom du bénéficiaire
					nomrest = (rs.getString("nomrest")!=null)?rs.getString("nomrest").trim(): " "; //nom du bénéficiaire Amplitude
					dsai = (rs.getDate("dsai")!=null)?rs.getDate("dsai"): null;  //date de saisie
					dco = (rs.getDate("dco")!=null)?rs.getDate("dco"): null; //date comptable
					dva = (rs.getDate("dva2")!=null)?rs.getDate("dva2"): null; //date de valeur
					etabr = (rs.getString("etabr")!=null)?rs.getString("etabr").trim(): " "; //code établissement de règlement (débiteur/donneur d'ordre)
					guibr = (rs.getString("guibr")!=null)?rs.getString("guibr").trim(): " "; //code guichet de règlement (débiteur)
					comb = (rs.getString("comb")!=null)?rs.getString("comb").trim(): " "; //numéro de compte du débiteur
					cleb = (rs.getString("cleb")!=null)?rs.getString("cleb").trim(): " "; //clé de compte du débiteur
					nomb = (rs.getString("nomb")!=null)?rs.getString("nomb").trim(): " "; //nom de l'établissement autre banque (débiteur)
					ope = (rs.getString("ope")!=null)?rs.getString("ope").trim(): " "; //code opération
					eve = (rs.getString("eve")!=null)?rs.getString("eve").trim(): " "; //évènement
					montant = (rs.getDouble("mht")!=0.0)?rs.getDouble("mht"): 0.0; //montantde l'opération
					lib = (rs.getString("lib1")!=null)?rs.getString("lib1").trim(): " "; //libellé de l'opération
					eta = (rs.getString("eta")!=null)?rs.getString("eta").trim(): " "; //état de l'opération (VA, AT, FO)
					uti = (rs.getString("uti")!=null)?rs.getString("uti").trim(): " "; //Code utilisateur ayant initié l'évènement

					traitement = new Traitement(agence, ncp, clc, devise, nom2, nomrest, dsai, dco, dva, ope, eve, montant, lib, eta, uti, etabr, guibr, comb, cleb, nomb, new Date(),utiPortal);
					traitements.add(traitement);



				}else{

					System.out.println("********IN BKEVE*********");

					agence = (rs.getString("age2")!=null)?rs.getString("age2").trim(): " ";  //agence bénéficiaire
					ncp = (rs.getString("ncp2")!=null)?rs.getString("ncp2").trim(): " "; //numéro de compte bénéficiaire
					clc = (rs.getString("clc2")!=null)?rs.getString("clc2").trim(): " "; //clé de compte bénéficiaire
					devise = (rs.getString("dev2")!=null)?rs.getString("dev2").trim(): " "; //devise bénéficiaire
					nom2 = (rs.getString("nom2")!=null)?rs.getString("nom2").trim(): " "; //nom du bénéficiaire
					nomrest = (rs.getString("nomrest")!=null)?rs.getString("nomrest").trim(): " "; //nom du bénéficiaire Amplitude
					dsai = (rs.getDate("dsai")!=null)?rs.getDate("dsai"): null;  //date de saisie
					dco = (rs.getDate("dco")!=null)?rs.getDate("dco"): null; //date comptable
					dva = (rs.getDate("dva2")!=null)?rs.getDate("dva2"): null; //date de valeur
					etabr = (rs.getString("etabr")!=null)?rs.getString("etabr").trim(): " "; //code établissement de règlement (débiteur/donneur d'ordre)
					guibr = (rs.getString("guibr")!=null)?rs.getString("guibr").trim(): " "; //code guichet de règlement (débiteur)
					comb = (rs.getString("comb")!=null)?rs.getString("comb").trim(): " "; //numéro de compte du débiteur
					cleb = (rs.getString("cleb")!=null)?rs.getString("cleb").trim(): " "; //clé de compte du débiteur
					nomb = (rs.getString("nomb")!=null)?rs.getString("nomb").trim(): " "; //nom de l'établissement autre banque (débiteur)
					ope = (rs.getString("ope")!=null)?rs.getString("ope").trim(): " "; //code opération
					eve = (rs.getString("eve")!=null)?rs.getString("eve").trim(): " "; //évènement
					montant = (rs.getDouble("mht")!=0.0)?rs.getDouble("mht"): 0.0; //montantde l'opération
					lib = (rs.getString("lib1")!=null)?rs.getString("lib1").trim(): " "; //libellé de l'opération
					eta = (rs.getString("eta")!=null)?rs.getString("eta").trim(): " "; //état de l'opération (VA, AT, FO)
					uti = (rs.getString("uti")!=null)?rs.getString("uti").trim(): " "; //Code utilisateur ayant initié l'évènement

					traitement = new Traitement(agence, ncp, clc, devise, nom2, nomrest, dsai, dco, dva, ope, eve, montant, lib, eta, uti, etabr, guibr, comb, cleb, nomb, new Date(),utiPortal);
					traitements.add(traitement);

				}


				while(rs.next()){

					agence = (rs.getString("age2")!=null)?rs.getString("age2").trim(): " ";  //agence bénéficiaire
					ncp = (rs.getString("ncp2")!=null)?rs.getString("ncp2").trim(): " "; //numéro de compte bénéficiaire
					clc = (rs.getString("clc2")!=null)?rs.getString("clc2").trim(): " "; //clé de compte bénéficiaire
					devise = (rs.getString("dev2")!=null)?rs.getString("dev2").trim(): " "; //devise bénéficiaire
					nom2 = (rs.getString("nom2")!=null)?rs.getString("nom2").trim(): " "; //nom du bénéficiaire
					nomrest = (rs.getString("nomrest")!=null)?rs.getString("nomrest").trim(): " "; //nom du bénéficiaire Amplitude
					dsai = (rs.getDate("dsai")!=null)?rs.getDate("dsai"): null;  //date de saisie
					dco = (rs.getDate("dco")!=null)?rs.getDate("dco"): null; //date comptable
					dva = (rs.getDate("dva2")!=null)?rs.getDate("dva2"): null; //date de valeur
					etabr = (rs.getString("etabr")!=null)?rs.getString("etabr").trim(): " "; //code établissement de règlement (débiteur/donneur d'ordre)
					guibr = (rs.getString("guibr")!=null)?rs.getString("guibr").trim(): " "; //code guichet de règlement (débiteur)
					comb = (rs.getString("comb")!=null)?rs.getString("comb").trim(): " "; //numéro de compte du débiteur
					cleb = (rs.getString("cleb")!=null)?rs.getString("cleb").trim(): " "; //clé de compte du débiteur
					nomb = (rs.getString("nomb")!=null)?rs.getString("nomb").trim(): " "; //nom de l'établissement autre banque (débiteur)
					ope = (rs.getString("ope")!=null)?rs.getString("ope").trim(): " "; //code opération
					eve = (rs.getString("eve")!=null)?rs.getString("eve").trim(): " "; //évènement
					montant = (rs.getDouble("mht")!=0.0)?rs.getDouble("mht"): 0.0; //montantde l'opération
					lib = (rs.getString("lib1")!=null)?rs.getString("lib1").trim(): " "; //libellé de l'opération
					eta = (rs.getString("eta")!=null)?rs.getString("eta").trim(): " "; //état de l'opération (VA, AT, FO)
					uti = (rs.getString("uti")!=null)?rs.getString("uti").trim(): " "; //Code utilisateur ayant initié l'évènement

					traitement = new Traitement(agence, ncp, clc, devise, nom2, nomrest, dsai, dco, dva, ope, eve, montant, lib, eta, uti, etabr, guibr, comb, cleb, nomb, new Date(),utiPortal);
					traitements.add(traitement);

				}

				if(rs!=null)
					rs.close();

				if(st!=null)
					st.close();


				for(Traitement t: traitements){

					ManagerDAO.save(t);

				}
				traitements.clear();

			}
		}
		catch(Exception e){

			e.printStackTrace();
		}

		finally
		{
			try {
				//if(traitements==null||traitements.isEmpty())
				traitements = filterVirementsIncoherencesInDB(dateDebut, dateFin, traite, tailleParLot);
				closeDELTAConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		return traitements;
	}*/

	@AllowedRole(name = "filterTraitementIncoherences", displayName = "VIR_SYSTAC.Rechercher Virements Incoherences")
	@Override
	public List<Traitement> filterVirementsIncoherences(Date dateDebut, Date dateFin, Boolean traite, int tailleParLot, String utiPortal){

		Statement st = null;
		ResultSet rs = null;

		int count = 0;

		List<Traitement> traitementsBD = new ArrayList<Traitement>();

		List<Traitement> traitements = new ArrayList<Traitement>();

		List<Traitement> traitementsToSave = new ArrayList<Traitement>();

		List<Traitement> traitementsToSave2 = new ArrayList<Traitement>();

		List<Traitement> traitementsToDisplay = new ArrayList<Traitement>();

		//String dateOfToday = new SimpleDateFormat("yyyy-MM-dd").format(dateDebut);   //dateDebut
		String dateOfToday = new SimpleDateFormat("ddMMyyyy").format(dateDebut);   //dateDebut

		Date dateToday = null;

		System.out.println("----dateOfToday----" + dateOfToday);


		List<Parametrages> params = filterParams();
		Double mon = 0.0;
		Integer mont = 0;
		String nature = "";
		String typeOpe = "";
		String codeEnreg = "";
		String chap = "";
		if(params!=null && !params.isEmpty()){
			mon = params.get(0).getMontant();
			mont = mon.intValue();
			nature = params.get(0).getNature();
			typeOpe = params.get(0).getTope();
			codeEnreg = params.get(0).getCodeEnreg();
			chap = params.get(0).getChapitres();
		}

		if(mont<0 || mon<0.0){

			return null;
		}


		try{

			boolean flag = false;
			boolean notExist = false;

			openDELTAConnection();
			if(conDELTA==null){System.out.println("----ConDelta est vide----");return null;}

			int entered = 1;
			do{
dddd
				/*******************************Verif décompte total de virements pour la journée******************************/
				String queryBase = "select count(*) as count from bkbeacrv b where b.drec = '"+dateOfToday+"'"; //and agec in '('"+listAgences+"')'
				String sqlQuery = queryBase + "	and b.tope in ('10', '13')  ";
				st = conDELTA.createStatement();
				rs = st.executeQuery(sqlQuery);
				int virJournee=0;
				while(rs.next()){
					virJournee = (rs.getInt("count")!=0)?rs.getInt("count"): 0;
				}
				if(rs!=null)rs.close();

				if(virJournee==0){
					System.err.println("*****Erreur! Le decompte total des virements pour la journée ne peut pas être zero!!!!!*****");
					return null;
				}


				/*******************************Verif décompte total de virements à traiter (10-21, 13-21, RCP)******on inclus la soustraction dans le rapport pour trouver le gap************************/
				String queryForOpeToTraite= " and b.ope = '' and (b.eve = '' or b.eve = ' ' or b.eve is null) and b.dco is null ";
				String queryForVirToTraite = " and b.nat = 'RCP' and b.cenr in ('21') and b.tope in ('10', '13') ";
				String query = queryBase + queryForOpeToTraite + queryForVirToTraite;
				rs = st.executeQuery(query);
				int virATraite=0;
				while(rs.next()){
					virATraite = (rs.getInt("count")!=0)?rs.getInt("count"): 0;
				}
				if(rs!=null)rs.close();

				if(virATraite==0){
					System.err.println("*****Erreur! Le decompte total  des virements à traiter ne peut pas être zero!!!!!*****");
					return null;
				}


				/*******************************Nbr inférieur au plancher*****************************************************************/
				query = query + " and  b.mon <  '"+mont+"'  ";
				rs = st.executeQuery(query);
				int nbrBelowSeuil=0;
				while(rs.next()){
					nbrBelowSeuil = (rs.getInt("count")!=0)?rs.getInt("count"): 0;
				}
				if(rs!=null)rs.close();


				/*****************************************Nbr EMF*********************************************************************/
				int nbrEMF=0;

				if(chap!=null&&!chap.isEmpty()){

					String chaps [] = chap.split(",");
					String newChap = "";
					for(String ch: chaps){
						if(newChap.isEmpty())
							newChap = "\'" +ch.trim()+ "\'";
						else
							newChap = newChap + "," + "\'" +ch.trim()+ "\'";
					}

					String queryAdd = "select count(*) as count from bkbeacrv b left join bkcli c on c.cli=b.ncp[1,7] left join bkcom d on (b.ncp = d.ncp and b.age = d.age)  where  b.drec = '"+dateOfToday+"' " +
							queryForOpeToTraite + queryForVirToTraite + " and d.cha[1,2] in ("+newChap+")   ";  //and b.agec in ('00001', '00002', '00003', '00004', '00005')

					System.out.println("****queryAdd***" + queryAdd);
					System.out.println("****queryAdd***" + queryAdd);
					System.out.println("****queryAdd***" + queryAdd);

					rs = st.executeQuery(queryAdd);
					while(rs.next()){
						nbrEMF = (rs.getInt("count")!=0)?rs.getInt("count"): 0;
						System.out.println("****rs.getInt***" + rs.getInt("count"));
						System.out.println("****nbrEMF***" + nbrEMF);

					}
				}
				if(rs!=null)rs.close();



				dateToday = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfToday);

				traitementsBD = filterVirementsInDB(dateToday, null, traite, tailleParLot, mont);
				if(traitementsBD!=null&&!traitementsBD.isEmpty()){

				}else{
					traitementsBD = new ArrayList<Traitement>();
				}

				String agence; //agence bénéficiaire
				String ncp; //numéro de compte bénéficiaire
				String clc; //clé de compte bénéficiaire
				String devise ; //devise bénéficiaire
				String nom2 ; //nom du bénéficiaire
				String nomrest ; //nom du bénéficiaire Amplitude
				String sig; //sigle du bénéficiaire
				Date dcom ;  //date de saisie
				Date dco ; //date comptable
				Date dva ; //date de valeur
				String etabr ; //code établissement de règlement (débiteur/donneur d'ordre)
				String guibr ; //code guichet de règlement (débiteur)
				String comr ; //numéro de compte du débiteur
				String cler ; //clé de compte du débiteur
				String nomr ; //nom de l'établissement autre banque (débiteur)
				String ope ; //code opération
				String eve; //évènement
				Double montant ; //montant de l'opération
				String lib ; //libellé de l'opération
				String eta ; //état de l'opération (VA, AT, FO)
				String uti ; //Code utilisateur ayant initié l'évènement
				String ndoc;
				String zone;
				String nat;
				String tope;
				String cenr;
				Date drec;
				Date dreg;
				Date dsort;

				Traitement traitement;


				///openDELTAConnection();

				///if(conDELTA==null){System.out.println("----ConDelta est vide----");return null;}

				/**String sql = "select b.agec, b.ncp, b.clc, b.dev, b.zone, b.nat, b.tope, b.cenr, b.drec, b.dreg, b.dsort, b.ope, b.ndoc, " +
						"b.mon, b.dco, b.dcom, b.eve, b.utrt,  "+
						"b.etabr, b.guibr,b.comr, b.cler, c.nomrest, c.rso, c.sig, c.nom, c.pre "+
						"from bkbeacrv b left join bkcli c on c.cli=b.ncp[1,7] left join bkcom d on (b.ncp = d.ncp and b.age = d.age)  " +
						"where b.nat = 'RCP' and b.tope in ('10', '13') and b.cenr = '21' and b.drec = '"+dateOfToday+"' and b.ope = '' and b.mon  >= '"+mont+"' and d.cha[1,2] not in ('56','38') order by b.ncp";  //561900, 561960, 561850, 561980, 561950, 561902, 561990, 543900, 543950  ---38
				 */
				/*************************VIREMENTS TRAITES*****************************************************************************/
				String sql="";
				sql = "select b.agec, b.ncp, b.clc, b.dev, b.zone, b.nat, b.tope, b.cenr, b.drec, b.dreg, b.dsort, b.ope, b.ndoc, " +
						"b.mon, b.dco, b.dcom, b.eve, b.utrt,  "+
						"b.etabr, b.guibr,b.comr, b.cler, c.nomrest, c.rso, c.sig, c.nom, c.pre "+
						"from bkbeacrv b left join bkcli c on c.cli=b.ncp[1,7] left join bkcom d on (b.ncp = d.ncp and b.age = d.age)  " +
						"where b.drec = '"+dateOfToday+"' and b.mon  >= '"+mont+"' " + queryForOpeToTraite + queryForVirToTraite;  //561900, 561960, 561850, 561980, 561950, 561902, 561990, 543900, 543950  ---38
				//and b.agec in ('00001', '00002', '00003', '00004', '00005')
				//boolean ok = false;
				/**if(nature!=null && !nature.isEmpty()){
					sql = sql + " and";
					//ok=true;
					sql = sql + " b.nat = '"+nature+"'";
				}
				if(typeOpe!=null &&!typeOpe.isEmpty()){
					//if(ok){
					sql = sql + " and";
					sql = sql + "  b.tope in ('"+typeOpe+"')";
					/*}else{
						sql = sql + " where";
						ok=true;
						sql = sql + "  b.tope in ('"+typeOpe+"')";
					}
				}
				if(codeEnreg!=null && !codeEnreg.isEmpty()){
					//if(ok){
					sql = sql + " and";
					sql = sql + "  b.cenr in ('"+codeEnreg+"')";
					/*}else{
						sql = sql + " where";
						ok=true;
						sql = sql + "  b.cenr = '"+codeEnreg+"' ";
					}
				}*/
				if(chap!=null&&!chap.isEmpty()){

					String chaps [] = chap.split(",");
					String newChap = "";
					for(String ch: chaps){
						if(newChap.isEmpty())
							newChap = "\'" +ch.trim()+ "\'";
						else
							newChap = newChap + "," + "\'" +ch.trim()+ "\'";
					}
					//if(ok){
					sql = sql + " and";
					sql = sql + "  d.cha[1,2] not in ("+newChap+")   ";
					/*}else{
						sql = sql + " where";
						ok=true;
						sql = sql + "  d.cha[1,2] not in ('"+chap+"')  ";
					}*/

				}
				sql = sql + " order by b.agec, b.ncp"; 



				//st = conDELTA.createStatement();
				rs = st.executeQuery(sql);

				while(rs.next()){

					agence = (rs.getString("agec")!=null)?rs.getString("agec").trim(): " ";  //agence cpt bénéficiaire
					ncp = (rs.getString("ncp")!=null)?rs.getString("ncp").trim(): " "; //numéro de compte bénéficiaire
					clc = (rs.getString("clc")!=null)?rs.getString("clc").trim(): " "; //clé de compte bénéficiaire
					nat = (rs.getString("nat")!=null)?rs.getString("nat").trim(): " "; //nature du fichier
					tope = (rs.getString("tope")!=null)?rs.getString("tope").trim(): " "; //type d'opération
					cenr = (rs.getString("cenr")!=null)?rs.getString("cenr").trim(): " "; //code enregistrement
					drec = (rs.getDate("drec")!=null)?rs.getDate("drec"): null;  //date de réception
					dreg = (rs.getDate("dreg")!=null)?rs.getDate("dreg"): null;  //date de réception
					dsort = (rs.getDate("dsort")!=null)?rs.getDate("dsort"): null;  //date de réception
					ope = (rs.getString("ope")!=null)?rs.getString("ope").trim(): " "; //code opération
					ndoc = (rs.getString("ndoc")!=null)?rs.getString("ndoc").trim(): " "; //numéro du document
					montant = (rs.getDouble("mon")!=0.0)?rs.getDouble("mon"): 0.0; //montant de l'opération
					dco = (rs.getDate("dco")!=null)?rs.getDate("dco"): null; //date comptable
					dcom = (rs.getDate("dcom")!=null)?rs.getDate("dcom"): null;  //date de compensation
					eve = (rs.getString("eve")!=null)?rs.getString("eve").trim(): " "; //numéro d'évènement
					zone = (rs.getString("zone")!=null)?rs.getString("zone").trim(): " "; //contenu du fichier
					devise = (rs.getString("dev")!=null)?rs.getString("dev").trim(): " "; //devise bénéficiaire
					uti = (rs.getString("utrt")!=null)?rs.getString("utrt").trim(): " "; //Code utilisateur ayant initié l'évènement*/
					etabr = (rs.getString("etabr")!=null)?rs.getString("etabr").trim(): " "; //code établissement de remettant (débiteur/donneur d'ordre)
					guibr = (rs.getString("guibr")!=null)?rs.getString("guibr").trim(): " "; //code guichet du remettant (débiteur)
					comr = (rs.getString("comr")!=null)?rs.getString("comr").trim(): " "; //numéro de compte du débiteur
					cler = (rs.getString("cler")!=null)?rs.getString("cler").trim(): " "; //clé de compte du débiteur	
					nomrest = (rs.getString("nomrest")!=null)?rs.getString("nomrest").trim(): rs.getString("nom").trim()+' '+(rs.getString("pre") != null ? rs.getString("pre").trim() : "")+' '+(rs.getString("midname") != null ? rs.getString("midname").trim() : ""); //nom du bénéficiaire Amplitude
					//nomrest = (rs.getString("rso")!=null && !rs.getString("rso").trim().isEmpty()) ? rs.getString("rso").trim() : rs.getString("nom").trim()+' '+(rs.getString("pre") != null ? rs.getString("pre").trim() : ""); //nom du bénéficiaire Amplitude
					sig = (rs.getString("sig")!=null && !rs.getString("sig").trim().isEmpty()) ? rs.getString("sig").trim() : ""; //nom du bénéficiaire Amplitude
					nomr = zone.substring(98, 128); //nom de l'établissement autre banque (débiteur) ****/
					nom2 = zone.substring(158, 188); //nom du bénéficiaire

					//System.out.println("***eve****" + eve);
					//System.out.println("***dco****" + dco);
					//if((eve.equals(" ")||eve.isEmpty()||eve==null) && dco==null){
					traitement = new Traitement(agence, ncp, clc, devise, nom2, nomrest, sig, drec, dco, dcom, tope, cenr, ndoc, ope, eve, montant, zone, uti, etabr, guibr, comr, cler, nomr, new Date(), utiPortal, virJournee, virATraite, nbrBelowSeuil, nbrEMF);
					traitements.add(traitement);
					//}

				}

				if(rs!=null)
					rs.close();

				if(st!=null)
					st.close();


				/***Filtre pour éviter les doublons**/
				//String key1 = "";
				//String key2 = "";
				System.out.println("*******************traitementsBD size*****" + traitementsBD.size());
				System.out.println("******************traitements size*****" + traitements.size());

				if(traitementsBD!=null && !traitementsBD.isEmpty()){

					for(Traitement tt: traitements){
						if(!traitementsBD.contains(tt)){
							System.out.println("****tt****" + tt.getNomBeneficiaire());
							traitementsToSave.add(tt);  //On ajoute les nouveaux dans la BD Portal
							count++;
						}
					}
					if(count==0){
						for(Traitement t: traitementsBD){
							traitementsToDisplay.add(t); // Si tous les éléments qu'on a pris sont déjà en BD on les affiche juste
						}
					}else{ // S'il y a certains nouveaux éléments de la BD Amplitude
						for(Traitement t: traitementsBD){
							if(!traitementsToSave.contains(t)){ //On affiche le reste qui était déjà en BD Portal, parce que les nouveaux ont été enregistrés
								traitementsToDisplay.add(t);
							}
						}	
					}

					flag=true;
				}else{ //s'il n'y a encore rien en BD
					if(!traitements.isEmpty()){
						for(Traitement t: traitements){
							System.out.println("****first****" + t.getNomBeneficiaire());
							traitementsToSave.add(t);   //On enregistre en BD Portal
							//traitementsToDisplay.add(t);
						}
					}else{
						notExist = true;
					}
				}


				if(count!=0){
					traitementsToDisplay.addAll(traitementsToSave);
				}


				/***for(Traitement t: traitementsToSave){
				ManagerDAO.save(t);
			}*/

				System.out.println("*****GETTING READY TO SAVE*****");

				//traitementsToSave2.addAll(traitementsToSave);

				//if(entered==1)
				//saveTraitementsToSave(traitementsToSave2);
				//else
				saveTraitementsToSave(traitementsToSave);

				System.out.println("*****AFTER SAVE*****");

				entered++;

				/*if(count==0){
				traitementsToDisplay = filterTraitements(traitementsToSave.size());
			}*/

				/***for(Traitement t: traitements){

				ManagerDAO.save(t);

			}*/
				//traitementsToSave.clear();
				//if(!flag){
				traitementsToSave.clear();
				traitements.clear();
				traitementsBD.clear();
				//}
			}while(flag ==false && notExist==false);

			//traitementsToSave2.clear();


		}
		catch(Exception e){

			e.printStackTrace();
		}

		finally
		{
			try {
				//if(traitements==null||traitements.isEmpty())
				//traitements = filterVirementsIncoherencesInDB(dateToday, null, traite, tailleParLot);
				closeDELTAConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		return traitementsToDisplay;
	}

	public List<Traitement> filterTraitements(int size){

		List<Traitement> traitementsToDisplay = new ArrayList<Traitement>();

		Long maxId = ManagerDAO.findMaxTraitementId();

		Long minId = maxId - Long.valueOf(size);

		System.out.println("****maxId****" + maxId + "******minId***" + minId);

		traitementsToDisplay = ManagerDAO.findTraitements(minId, maxId);

		return traitementsToDisplay;
	}

	//@Asynchronous
	public boolean saveTraitementsToSave(final List<Traitement> traite ){
		//System.out.println("*****IN saveTraitementsToSave*****");
		//new Thread(new Runnable() {
		//@Override
		//public void run() {
		// TODO Auto-generated method stub
		for(Traitement t: traite){
			ManagerDAO.save(t);
			System.out.println("*****SAVING*****" + t.getNomBeneficiaire());
		}
		//}
		//}).start();

		return true;
	}


	public List<Traitement> filterVirementsDoublonsInDB(Date dateDebut, Date dateFin, Boolean traite, int tailleParLot){

		return ManagerDAO.filterVirementsDoublonsInDB(dateDebut,dateFin, traite, tailleParLot);
	}

	public List<Traitement> filterVirementsIncoherencesInDB(Date dateDebut, Date dateFin, Boolean traite, int tailleParLot){

		return ManagerDAO.filterVirementsIncoherencesInDB(dateDebut,dateFin, traite, tailleParLot);
	}

	public List<Traitement> filterVirementsInDB(Date dateDebut, Date dateFin, Boolean traite, int tailleParLot, int mont){

		return ManagerDAO.filterVirementsInDB(dateDebut, dateFin, traite, tailleParLot, mont);
	}

	public List<Doublons> filterDoublons(Date dateDebut, Date dateFin){

		RestrictionsContainer rc = RestrictionsContainer.getInstance();
		rc.add(Restrictions.between("dsai", dateDebut, dateFin));

		return ManagerDAO.filter(Doublons.class, null, rc, null, null, 0, -1);
	}


	public List<Incoherences> filterIncoherences(Date dateDebut, Date dateFin){

		RestrictionsContainer rc = RestrictionsContainer.getInstance();
		rc.add(Restrictions.between("drec", dateDebut, dateFin));

		return ManagerDAO.filter(Incoherences.class, null, rc, null, null, 0, -1);
	}



	@Override
	public Boolean setDoublonsEnAttente(List<Doublons>doublonsAValider, List<Doublons>doublonsANePasValider,boolean isInBkeveOrBkheve ){

		System.out.println("********In setDoublonsEnAttente********");
		System.out.println("********doublonsAValider.size()********" + doublonsAValider.size());
		System.out.println("********doublonsANePasValider.size()********" + doublonsANePasValider.size());

		Boolean result = Boolean.FALSE;

		try{

			Statement st = null;
			Statement st2 = null;

			openDELTAConnection();

			if(conDELTA==null){System.out.println("----ConDelta est vide----");return result;}
			else{
				System.out.println("----ConDelta n'est pas vide----");
			}

			System.out.println("****Before FOR*****");

			if(isInBkeveOrBkheve==true){
				for(Doublons d: doublonsAValider){

					System.out.println("----In doublons----" + new SimpleDateFormat("yyyy-MM-dd").format(d.getDsai())); //

					/*String sql = "Update bkeve b set b.eta = 'AT' where b.ope = '144' and b.dsai = '"+new SimpleDateFormat("yyyy-MM-dd").format(d.getDsai())+"' and b.nom2 = '"+d.getNomBeneficiaire()+"' and b.eta = '"+d.getEtat()+"' and b.mht = '"+d.getMontant()+"'"
							+ " and b.nomb = '"+d.getNomDonneurOrdre()+"' and b.ncp2 = '"+d.getNcp()+"' and b.comb = '"+d.getNcpDonneurOrdre()+"' and b.eve = '"+d.getEve()+"' ";*/

					String sql = "Update bkeve b set b.eta = 'AT' where b.ope = '144' and b.dsai = '"+new SimpleDateFormat("yyyy-MM-dd").format(d.getDsai())+"' and b.nom2 = '"+d.getNomBeneficiaire()+"'  and b.mht = "+d.getMontant()+""
							+ " and b.nomb = '"+d.getNomDonneurOrdre()+"' and b.ncp2 = '"+d.getNcp()+"' and b.comb = '"+d.getNcpDonneurOrdre()+"' and b.eve = '"+d.getEve()+"' ";

					st = conDELTA.createStatement();
					int i = st.executeUpdate(sql);
					System.out.println("----i----"+i);
					result = Boolean.TRUE;
					System.out.println("----Result is set to true----");

					if(st!=null)
						st.close();

				}


				for(Doublons d: doublonsANePasValider){

					System.out.println("----In doublons----" + new SimpleDateFormat("yyyy-MM-dd").format(d.getDsai())); //

					/*String sql = "Update bkeve b set b.eta = 'AT' where b.ope = '144' and b.dsai = '"+new SimpleDateFormat("yyyy-MM-dd").format(d.getDsai())+"' and b.nom2 = '"+d.getNomBeneficiaire()+"' and b.eta = '"+d.getEtat()+"' and b.mht = '"+d.getMontant()+"'"
							+ " and b.nomb = '"+d.getNomDonneurOrdre()+"' and b.ncp2 = '"+d.getNcp()+"' and b.comb = '"+d.getNcpDonneurOrdre()+"' and b.eve = '"+d.getEve()+"' ";*/

					String sql = "Update bkeve b set b.eta = 'VA' where b.ope = '144' and b.dsai = '"+new SimpleDateFormat("yyyy-MM-dd").format(d.getDsai())+"' and b.nom2 = '"+d.getNomBeneficiaire()+"'  and b.mht = "+d.getMontant()+""
							+ " and b.nomb = '"+d.getNomDonneurOrdre()+"' and b.ncp2 = '"+d.getNcp()+"' and b.comb = '"+d.getNcpDonneurOrdre()+"' and b.eve = '"+d.getEve()+"' ";

					st2 = conDELTA.createStatement();
					int i = st2.executeUpdate(sql);
					System.out.println("----i----"+i);
					result = Boolean.TRUE;
					System.out.println("----Result is set to true----");

					if(st2!=null)
						st2.close();

				}
			}else{

				/*   NOT USEFUL
				for(Doublons d: doublonsAValider){

					System.out.println("----In doublons 2----");

					String sql = "Update bkheve b set b.eta = 'AT' where b.ope = '144' and b.dsai = '"+new SimpleDateFormat("yyyy-MM-dd").format(d.getDsai())+"' and b.nom2 = '"+d.getNomBeneficiaire()+"'  and b.mht = "+d.getMontant()+""
							+ " and b.nomb = '"+d.getNomDonneurOrdre()+"' and b.ncp2 = '"+d.getNcp()+"' and b.comb = '"+d.getNcpDonneurOrdre()+"' and b.eve = '"+d.getEve()+"' ";

					st = conDELTA.createStatement();
					int i = st.executeUpdate(sql);
					System.out.println("----i----"+i);
					result = Boolean.TRUE;
					System.out.println("----Result is set to true----");

					if(st!=null)
						st.close();

				}

				for(Doublons d: doublonsANePasValider){

					System.out.println("----In doublons 2----");

					String sql = "Update bkheve b set b.eta = 'VA' where b.ope = '144' and b.dsai = '"+new SimpleDateFormat("yyyy-MM-dd").format(d.getDsai())+"' and b.nom2 = '"+d.getNomBeneficiaire()+"'  and b.mht = "+d.getMontant()+""
							+ " and b.nomb = '"+d.getNomDonneurOrdre()+"' and b.ncp2 = '"+d.getNcp()+"' and b.comb = '"+d.getNcpDonneurOrdre()+"' and b.eve = '"+d.getEve()+"' ";

					st2 = conDELTA.createStatement();
					int i = st2.executeUpdate(sql);
					System.out.println("----i----"+i);
					result = Boolean.TRUE;
					System.out.println("----Result is set to true----");

					if(st2!=null)
						st2.close();

				}
				 */
			}

			System.out.println("****After FOR*****");

		}
		catch(Exception e){

			e.printStackTrace();
		}

		finally
		{
			try {

				closeDELTAConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;

	}



	/***@Override
	public Boolean setIncoherencesEnAttente(List<Incoherences>incoherencesAValider, List<Incoherences>incoherencesANePasValider, boolean isInBkeveOrBkheve){


		Boolean result = Boolean.FALSE;

		try{

			Statement st = null;
			Statement st2 = null;


			openDELTAConnection();

			if(conDELTA==null){System.out.println("----ConDelta est vide----");return result;}

			if(isInBkeveOrBkheve==true){

				for(Incoherences d: incoherencesAValider){


					String sql = "Update bkeve b set b.eta = 'AT' where b.ope = '144' and b.dsai = '"+new SimpleDateFormat("yyyy-MM-dd").format(d.getDsai())+"' and b.nom2 = '"+d.getNomBeneficiaire()+"'  and b.mht = "+d.getMontant()+""
							+ " and b.nomb = '"+d.getNomDonneurOrdre()+"' and b.ncp2 = '"+d.getNcp()+"' and b.comb = '"+d.getNcpDonneurOrdre()+"' and b.eve = '"+d.getEve()+"' ";

					st = conDELTA.createStatement();
					st.executeUpdate(sql);
					result = Boolean.TRUE;

					if(st!=null)
						st.close();

				}



				for(Incoherences d: incoherencesANePasValider){
					String sql = "Update bkeve b set b.eta = 'VA' where b.ope = '144' and b.dsai = '"+new SimpleDateFormat("yyyy-MM-dd").format(d.getDsai())+"' and b.nom2 = '"+d.getNomBeneficiaire()+"'  and b.mht = "+d.getMontant()+""
							+ " and b.nomb = '"+d.getNomDonneurOrdre()+"' and b.ncp2 = '"+d.getNcp()+"' and b.comb = '"+d.getNcpDonneurOrdre()+"' and b.eve = '"+d.getEve()+"' ";

					st2 = conDELTA.createStatement();
					st2.executeUpdate(sql);
					result = Boolean.TRUE;

					if(st2!=null)
						st2.close();
				}

			}else{
				/* NOT USEFUL
				for(Incoherences d: incoherencesAValider){


					String sql = "Update bkheve b set b.eta = 'AT' where b.ope = '144' and b.dsai = '"+new SimpleDateFormat("yyyy-MM-dd").format(d.getDsai())+"' and b.nom2 = '"+d.getNomBeneficiaire()+"'  and b.mht = "+d.getMontant()+""
							+ " and b.nomb = '"+d.getNomDonneurOrdre()+"' and b.ncp2 = '"+d.getNcp()+"' and b.comb = '"+d.getNcpDonneurOrdre()+"' and b.eve = '"+d.getEve()+"' ";

					st = conDELTA.createStatement();
					st.executeUpdate(sql);
					result = Boolean.TRUE;

					if(st!=null)
						st.close();

				}

				for(Incoherences d: incoherencesANePasValider){


					String sql = "Update bkheve b set b.eta = 'VA' where b.ope = '144' and b.dsai = '"+new SimpleDateFormat("yyyy-MM-dd").format(d.getDsai())+"' and b.nom2 = '"+d.getNomBeneficiaire()+"' and b.mht = "+d.getMontant()+""
							+ " and b.nomb = '"+d.getNomDonneurOrdre()+"' and b.ncp2 = '"+d.getNcp()+"' and b.comb = '"+d.getNcpDonneurOrdre()+"' and b.eve = '"+d.getEve()+"' ";

					st2 = conDELTA.createStatement();
					st2.executeUpdate(sql);
					result = Boolean.TRUE;

					if(st2!=null)
						st2.close();

				}

			}

		}
		catch(Exception e){

			e.printStackTrace();
		}

		finally
		{
			try {

				closeDELTAConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;

	}*/

	@Override
	public String setIncoherencesEnAttente(List<Incoherences>incoherencesAValider, List<Incoherences>incoherencesANePasValider, boolean isInBkeveOrBkheve, String utiConnecte){


		String result = "KO";

		String dateOfToday = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  //valeur par défaut

		List<Parametrages> params = filterParams();
		String opp = "";
		String ope = "";
		if(params!=null && !params.isEmpty()){
			opp = params.get(0).getOpp();
			ope = params.get(0).getOpeSystac();
		}

		if(opp==null || opp.isEmpty() || ope==null || ope.isEmpty() ){

			return "ERROR_PARAM";
		}

		try{

			Statement st = null;

			ResultSet rs = null;

			openDELTAConnection();

			if(conDELTA==null){System.out.println("----ConDelta est vide----");return "ERROR_CONNECT";}

			String query = "select mnt2 from bknom where ctab='001' and cacc='00099'";
			st = conDELTA.createStatement();
			rs = st.executeQuery(query);
			String dateString = "";
			while(rs.next()){
				dateString = (rs.getString("mnt2")!=null)?rs.getString("mnt2").trim(): " ";  //code opposition

				//String day = dateString.substring(0, 2);
				//String month = dateString.substring(2, 4);
				//String year = dateString.substring(4, 8);

				String day="";
				String month="";
				String year="";	
				if(dateString.length()==8){
					day = dateString.substring(0, 2);
					month = dateString.substring(2, 4);
					year = dateString.substring(4, 8);	
				}else if(dateString.length()==7){
					day = dateString.substring(0, 1);
					month = dateString.substring(1, 3);
					year = dateString.substring(3, 7);						
				}
				else{ //POUR LES TESTS POUR CAUSE D'INDISPONIBILITE BASE 
					//day = "5";
					//month = "03";
					//year = "2019";
				}

				dateOfToday = year + "-" + month + "-" + day; //on réupère la valeur de la date d'Amplitude
				//Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateStringFormatted);
			}


			boolean found = false;

			boolean oppFound = false;

			if(incoherencesAValider==null || incoherencesAValider.isEmpty()){
				return "EMPTY";
			}

			for(Incoherences d: incoherencesAValider){

				found = false;

				oppFound = false;

				/************************On va chercher si l'opposition de blocage crédit existe déjà sur le code opération 144 pour virement reçus de Systac et pour soit l'agence centrale ou l'agence concerné********************************************************************************/
				String sql = "select copp from bkopl where (age='00099' or age = '"+d.getAgence()+"') and copp='O' and ope='"+ope+"' and opp= '"+opp+"'";
				st = conDELTA.createStatement();
				rs = st.executeQuery(sql);

				while(rs.next()){
					found = true;  //opposition blocage crédit (22) trouvée pour le code opération, avec contrôle de l'opération, et l'agence centrale ou du client , donc aucune action à faire
				}
				if(rs!=null)
					rs.close();

				if(found==false){ //Si l'opposition blocage crédit n'est trouvée sur le code opération, alors nous renvoyons l'alerte au métier de demander au DEX d'inserrer le blocage crédit pour l'opération dans Amplitude et la rendre controlable (copp='O')
					/***sql = "insert into bkopl values('00099', '144', '22', 'O') ";
					st.executeUpdate(sql);

					found = true;*///

					return "Le contrôle des incohérences ne peut se faire tant que le contrôle de l'opposition Systac n'est paramétré pour les virements reçus de Systac dans Amplitude! Contactez la DSI!!!";
				}


				/*************************On va chercher si l'opposition existe déjà sur le compte*****************************************************************/

				if(found){

					String oppRequete="";
					sql = "select opp from bkoppcom where age = '"+d.getAgence()+"' and ncp='"+d.getNcp()+"' and eta='V' and (dfin is null or dfin>='"+dateOfToday+"')"; //opposition finissant après aujourd'hui
					rs = st.executeQuery(sql);

					while(rs.next()){

						oppRequete = (rs.getString("opp")!=null)?rs.getString("opp").trim(): " ";  //code opposition
						if(oppRequete.equals(opp)){
							oppFound = true;  //l'opposition crédit est déjà sur ce compte et fini après aujourd'hui, donc aucune action à faire
						}
					}
					if(rs!=null)
						rs.close();

					if(oppFound==false){ //si l'opposition crédit n'est trouvé sur le compte du client, alors nous allons l'insérer dans Amplitude

						Date dateTomorrow = DateUtils.addDays(new SimpleDateFormat("yyyy-MM-dd").parse(dateOfToday), 1);
						String dateOfTomorrow = new SimpleDateFormat("yyyy-MM-dd").format(dateTomorrow);

						sql = "insert into bkoppcom values ('"+d.getAgence()+"', '"+d.getDevise()+"', '"+d.getNcp()+"', '', '"+opp+"', '"+dateOfToday+"', '"+dateOfToday+"', '', '', '', 'V', '"+utiConnecte+"', '"+dateOfToday+"', 'O', 'Client destinataire non reconnu');";  //Fraude probable sur intitulé VIR SYSTAC
						st.executeUpdate(sql);


						oppFound = true;
					}

				}

				if(found && oppFound){

					result = "OK";
				}

				if(st!=null)
					st.close();

			}


		}
		catch(Exception e){

			e.printStackTrace();
		}

		finally
		{
			try {

				closeDELTAConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;

	}


	public String findBeneficiareCoreBanking(Traitement t){

		String benefCB = "";

		Statement st = null;
		ResultSet rs = null;

		try{

			openDELTAConnection();

			if(conDELTA==null){System.out.println("----ConDelta est vide----");return null;}


			String cli = t.getNcp().substring(0, 7);

			String sql = "Select * from bkcli where cli = '"+cli+"' ";

			st = conDELTA.createStatement();
			rs = st.executeQuery(sql);

			if(rs.next()){


				benefCB = (rs.getString("nomrest")!=null)?rs.getString("nomrest").trim(): " ";  //nom bénéficiaire core banking

			}

			if(rs!=null)
				rs.close();

			if(st!=null)
				st.close();

		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try {

				closeDELTAConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return benefCB;
	}


	public void openDELTAConnection(){

		try{

			// System
			DataSystem system = findDataSystem("DELTA-V10");

			/*if(system == null){
				String code = "DELTA-V10";
				String name = "Amplitude V-10";
				String description = "Chaine de Connexion Amplitude";
				String providerClassName = "com.informix.jdbc.IfxDriver";
				//String dbConnectionString = "jdbc:informix-sqli://192.168.11.26:1536/recette:informixserver=ol_afbrec";
				String dbConnectionString = "jdbc:informix-sqli://172.21.60.200:1536/cerber:informixserver=ol_afbrec";
				String dbUserName = "trans";
				String dbPassword = "trans123";
				system = new DataSystem(code, name, description, providerClassName, dbConnectionString, dbUserName, dbPassword);
				system = ManagerDAO.save(system);
			}*/

			if(system != null ){
				Class.forName(system.getProviderClassName()).newInstance();
				this.conDELTA = DriverManager.getConnection(system.getDbConnectionString(), system.getDbUserName(), system.getDbPassword());
			}

		}catch (Exception e) {
			// TODO: handle exception
			this.conDELTA = null;
		}

	}


	/**
	 * Ferme la connexion a Delta
	 * @throws Exception 
	 */
	private  void closeDELTAConnection() throws Exception {
		if(this.conDELTA != null) this.conDELTA.close();
	}


	/**
	 * Trouver le système d'information
	 * @param system
	 * @return
	 */
	public DataSystem findDataSystem(String system){

		RestrictionsContainer rc = RestrictionsContainer.getInstance();
		rc.add(Restrictions.eq("code", system));
		List<DataSystem> sys = ManagerDAO.filter(DataSystem.class, null, rc, null, null, 0, -1);

		if(sys == null  || sys.isEmpty() ) return null;
		return sys.get(0);	

	}


	public Doublons saveDoublons(Doublons d){
		return ManagerDAO.save(d);
	}

	public Incoherences saveIncoherences(Incoherences i){
		return ManagerDAO.save(i);
	}

	public Traitement saveTraitement(Traitement t){

		return ManagerDAO.save(t);
	}

	public Traitement updateTraitement(Traitement t){

		return ManagerDAO.update(t);
	}

	public Long countVirementsInDB(Date dateDebut, Date dateFin){

		return ManagerDAO.countVirementsInDB(dateDebut,dateFin);
	}

	public Long countVirementsTraitesDoublonsInDB(Date dateDebut, Date dateFin, Boolean traite){
		return ManagerDAO.countVirementsTraitesDoublonsInDB(dateDebut, dateFin, traite);
	}

	public Long countVirementsTraitesIncoherencesInDB(Date dateDebut, Date dateFin, Boolean traite){
		return ManagerDAO.countVirementsTraitesIncoherencesInDB(dateDebut, dateFin, traite);
	}

	public Traitement getLastElementOfLastLotDoublons(Long lastElementOfLastLotDoublons){

		Traitement t = null;
		RestrictionsContainer rc  = RestrictionsContainer.getInstance();
		if(lastElementOfLastLotDoublons!=null){
			rc.add(Restrictions.eq("id", lastElementOfLastLotDoublons));
			List<Traitement> trait = ManagerDAO.filter(Traitement.class, null, rc, null, null, 0, -1);
			if(trait!=null&&!trait.isEmpty())
				t=trait.get(0);
		}

		return t;
	}


	public Traitement getLastElementOfLastLotIncoherences(Long lastElementOfLastLotIncoherences){

		Traitement t = null;
		RestrictionsContainer rc  = RestrictionsContainer.getInstance();
		if(lastElementOfLastLotIncoherences!=null){rc.add(Restrictions.eq("id", lastElementOfLastLotIncoherences));

		List<Traitement> trait = ManagerDAO.filter(Traitement.class, null, rc, null, null, 0, -1);
		if(trait!=null)
			t=trait.get(0);
		}

		return t;
	}


	@Override
	public void checkAndMountDiskForCompensation(){

		try {

			/***checking if the R: Disk is mounted****/
			boolean mountFound=false;
			File[] drives = File.listRoots();
			if (drives != null && drives.length > 0) {
				for (File aDrive : drives) {
					System.out.println(aDrive);
					if(aDrive.toString().contains("W:")){
						mountFound=true;
						System.out.println("****mount***" + aDrive + " found, program exiting...");
						break;
					}
				}
			}

			/***creating the mount of the disk in case the disk is not mounted***/
			if(mountFound==false){
				System.out.println("****getting ready to mount***");
				List<ParamBDADT> paramADT = filterParamBDADT();
				if(paramADT!=null&&!paramADT.isEmpty()){
					String user = paramADT.get(0).getUser();
					String pass = Encrypter.getInstance().decryptText(paramADT.get(0).getPassword());  //paramADT.get(0).getPassword();

					//String command = "c:\\windows\\system32\\net.exe use R: \\\\ADT-SYSTAC\\i /user:"+user+ " " + pass;
					//String command = "c:\\windows\\system32\\net.exe use R: \\\\172.21.240.28\\Shared /user:"+user+ " " + pass;

					String command = "cmd /C net use W: \\\\172.21.240.28\\Shared " +pass + " "+ "/user:"+user;
					System.out.println("****command***"+command);
					Process p = Runtime.getRuntime().exec(command);

					System.out.println("****mounting process executed***");
				}

			}


		} catch (IOException e) {
			e.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}

	}

	public List<ParamBDADT> filterParamBDADT(){

		return ManagerDAO.filter(ParamBDADT.class, null, null, null, null, 0, -1);
	}

	@Override
	public void saveParamADT(){

		List<ParamBDADT> paramADT = filterParamBDADT();
		//If there is no param for Systac ADT we save it
		if(paramADT==null||paramADT.isEmpty()){

			ParamBDADT paramBDADT = new ParamBDADT();
			/*paramBDADT.setUser("Administrateur");
			paramBDADT.setPassword(Encrypter.getInstance().encryptText("Systac-adt"));*/

			paramBDADT.setUser("Administrateur");
			paramBDADT.setPassword(Encrypter.getInstance().encryptText("PortalTestDPDL1!"));

			ManagerDAO.save(paramBDADT);
		}

	}

	public void deleteDiskForCompensation(){

		try {

			boolean mountFound=false;
			File[] drives = File.listRoots();
			if (drives != null && drives.length > 0) {
				for (File aDrive : drives) {
					System.out.println(aDrive);
					if(aDrive.toString().contains("W:")){
						mountFound=true;
						System.out.println("****mount***" + aDrive + " found, program exiting...");
						break;
					}
				}
			}

			/***creating the mount of the disk in case the disk is not mounted***/
			if(mountFound==true){

				String command = "cmd /C net use W: /DELETE";

				System.out.println("****command***"+command);

				Process p = Runtime.getRuntime().exec(command);

				System.out.println("****mounting process executed***");

			}

		} catch (IOException e) {
			e.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}

	}



	@Override
	public List<ParamEmail> filterEmails(String email) {
		RestrictionsContainer rc = RestrictionsContainer.getInstance();
		if(email!=null&&!email.isEmpty())rc.add(Restrictions.eq("email", email));
		rc.add(Restrictions.eq("valide", Boolean.TRUE));
		return ManagerDAO.filter(ParamEmail.class, null, null, null, null, 0, -1);
	}



	@Override
	public ParametragesGenTeleCompense saveParametragesGenTeleCompense(
			ParametragesGenTeleCompense p) {

		return ManagerDAO.save(p);
	}



	@Override
	public ParametragesGenTeleCompense updateParametragesGenTeleCompense(
			ParametragesGenTeleCompense p) {

		return ManagerDAO.update(p);
	}



	@Override
	public ParamEmail saveParamEmail(ParamEmail p) {

		return ManagerDAO.save(p);
	}



	@Override
	public ParamEmail updateParamEmail(ParamEmail p) {

		return ManagerDAO.update(p);
	}


	public List<TourCompensation> filterTourCompensation(Date date, String user, Integer tour, TypeProcess typeProcess){
		RestrictionsContainer rc = RestrictionsContainer.getInstance();
		if(date!=null)rc.add(Restrictions.eq("dateTraitement", date));
		if(user!=null && !user.trim().isEmpty())rc.add(Restrictions.eq("utiTraitement", user));
		if(tour!=0)rc.add(Restrictions.eq("numeroTour", tour));
		if(typeProcess!=null)rc.add(Restrictions.eq("typeProcess", typeProcess));

		return ManagerDAO.filter(TourCompensation.class, null, rc, null, null, 0, -1);
	}

	public List<TraitementTourCompensation> filterTraitementTourCompensation (TypeTraitement typeTraitement, TourCompensation tourCompensation, Date date ){

		RestrictionsContainer rc = RestrictionsContainer.getInstance();

		if(typeTraitement!=null)rc.add(Restrictions.eq("typeTraitement", typeTraitement));
		if(tourCompensation!=null)rc.add(Restrictions.eq("tourCompensation", tourCompensation));
		if(date!=null)rc.add(Restrictions.eq("dateTraitement", date));


		return ManagerDAO.filter(TraitementTourCompensation.class, null, rc, null, null, 0, -1);
	}

	public List<TraitementTourCompensation> filterTraitementTourCompensation2(Date dateDebut, Date dateFin, String heure, String utilisateur, TypeTraitement typeTraitement, SortTraitement sortTraitement){

		RestrictionsContainer rc = RestrictionsContainer.getInstance();

		if(dateDebut!=null)rc.add(Restrictions.ge("dateTraitement", dateDebut));
		if(dateFin!=null)rc.add(Restrictions.le("dateTraitement", dateFin));
		if(heure!=null&&!heure.isEmpty())rc.add(Restrictions.eq("heure", heure));
		if(utilisateur!=null&&!utilisateur.isEmpty())rc.add(Restrictions.eq("utiTraitement", utilisateur));
		if(typeTraitement!=null)rc.add(Restrictions.eq("typeTraitement", typeTraitement));
		if(sortTraitement!=null)rc.add(Restrictions.eq("sortTraitement", sortTraitement));

		return ManagerDAO.filter(TraitementTourCompensation.class, null, rc, null, null, 0, -1);
	}



	public List<RapatriementImagesAller> filterRapatriementImageAller(Date date, String user){

		RestrictionsContainer rc = RestrictionsContainer.getInstance();
		if(date!=null)rc.add(Restrictions.eq("dateTraitement", date));
		if(user!=null && !user.trim().isEmpty())rc.add(Restrictions.eq("utiTraitement", user));

		return ManagerDAO.filter(RapatriementImagesAller.class, null, rc, null, null, 0, -1);
	}

	public List<RapatriementImagesRetour> filterRapatriementImageRetour(Date date, String user){

		RestrictionsContainer rc = RestrictionsContainer.getInstance();
		if(date!=null)rc.add(Restrictions.eq("dateTraitement", date));
		if(user!=null && !user.trim().isEmpty())rc.add(Restrictions.eq("utiTraitement", user));

		return ManagerDAO.filter(RapatriementImagesRetour.class, null, rc, null, null, 0, -1);
	}

	public List<TraitementTourCompensation> filterTraitementTourCompensationsArchive(){
		RestrictionsContainer rc = RestrictionsContainer.getInstance();
		rc.add(Restrictions.eq("typeTraitement", TypeTraitement.ARCHIVE));

		return ManagerDAO.filter(TraitementTourCompensation.class, null, null, null, null, 0, -1);
	}

	public List<FichiersComptabilisationAller> filterFichierComptaAller(Date date, String user){

		RestrictionsContainer rc = RestrictionsContainer.getInstance();
		if(date!=null)rc.add(Restrictions.eq("dateTraitement", date));
		if(user!=null && !user.trim().isEmpty())rc.add(Restrictions.eq("utiTraitement", user));

		return ManagerDAO.filter(FichiersComptabilisationAller.class, null, rc, null, null, 0, -1);
	}

	public List<FichiersComptabilisationRetour> filterFichierComptaRetour(Date date, String user){

		RestrictionsContainer rc = RestrictionsContainer.getInstance();
		if(date!=null)rc.add(Restrictions.eq("dateTraitement", date));
		if(user!=null && !user.trim().isEmpty())rc.add(Restrictions.eq("utiTraitement", user));

		return ManagerDAO.filter(FichiersComptabilisationRetour.class, null, rc, null, null, 0, -1);
	}


	/**
	 * Robot de suppression automatique des fichiers archivés
	 */
	public void robotSuppressionArchives(){

		try{

			if(task != null )task.cancel();
			if(timer != null )timer.cancel();

			task = new TimerTask(){
				@Override
				public void run(){
					try{

						suppressionArchives();

					}catch(Exception e){e.printStackTrace();}
				}	
			};

			timer = new java.util.Timer(true);

			int sec = 60 ;int min = 60;

			timer.schedule(task,DateUtils.addMinutes(new Date(), 1), min*sec*1000); //chaque heure

		}catch(Exception e){
			e.printStackTrace();
		}
	}


	public void robotOuvertureFermetureJournee(){
		try{

			System.out.println("******IN robotOuvertureFermetureJournee()******");

			if(task2 != null )task2.cancel();
			if(timer2 != null )timer2.cancel();

			task2 = new TimerTask(){
				@Override
				public void run(){
					try{

						checkTime();

					}catch(Exception e){e.printStackTrace();}
				}	
			};

			timer2 = new java.util.Timer(true);

			int sec = 60 ;int min = 30;

			timer2.schedule(task2,DateUtils.addMinutes(new Date(), 1), min*sec*1000); //chaque 30 minutes

		}catch(Exception e){
			e.printStackTrace();
		}
	}


	public void checkTime(){

		/**try{

			ParamCompensateurCentrale paramCompensateurCentrale = filterLastParamCompensateurCentrale();

			if(paramCompensateurCentrale!=null){
				//Si la date de la base est supérieure à la date de today -- chose impossible
				if(paramCompensateurCentrale.getDateJourneeEnCours().after(new Date())){

					System.out.println("La date de la journée de compensation en base de données est supérieure à la date d'aujourd'hui!!! Ceci est une erreur. Bien vouloir contacter la DSI ");
					List<ParametragesGenTeleCompense> parametragesGenTeleCompenses =  filterParamGen();
					ParametragesGenTeleCompense parametragesGenTeleCompense  = new ParametragesGenTeleCompense();
					if(parametragesGenTeleCompenses!=null && !parametragesGenTeleCompenses.isEmpty()){
						parametragesGenTeleCompense = parametragesGenTeleCompenses.get(0);
					}
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
					if(filterParamEmailAuto()!=null && !filterParamEmailAuto().isEmpty()){
						for(ParamEmailAuto p: filterParamEmailAuto()){
							ip = p.getIp();
							email = p.getEmail();
							pass = Encrypter.getInstance().decryptText(p.getPass());
							port = p.getPort();
						}
					}
					String title="Incohérence dans la date de la journée de compense" ;
					String msg = "Bonjour, " + "\t" + "\n" + "\t\t" + "La date de la journée de compensation en base de données est supérieure à la date d'aujourd'hui!!! Ceci est une erreur. Bien vouloir contacter la DSI " + "\n\n";
					SendMail.sendMail(null, null, mailsTo, mailsCC, mailsBCC, title, msg, ip, email, pass, port);
					return;
				}

				////Si la date de la base est inférieure à la date d'aujourd'hui
				if(paramCompensateurCentrale.getDateJourneeEnCours().before(new Date())){
					// On ferme la journée précédente si elle est encore ouverte alors que nous sommes déjà le lendemain
					if(paramCompensateurCentrale.getStatutJourneeEnCours()!=null && paramCompensateurCentrale.getStatutJourneeEnCours().equals(StatutJournee.OUVERTURE)){
						ParamCompensateurCentrale dbObject =  new ParamCompensateurCentrale();
						dbObject.setDateJournee(paramCompensateurCentrale.getDateJournee());
						dbObject.setDateJourneeEnCours(paramCompensateurCentrale.getDateJourneeEnCours());
						dbObject.setStatutJournee(StatutJournee.FERMETURE);
						dbObject.setStatutJourneeEnCours(dbObject.getStatutJournee());
						dbObject.setTourActuel(paramCompensateurCentrale.getTourActuel());
						dbObject.setTourMax(paramCompensateurCentrale.getTourMax());
						dbObject.setUtiTraitement("AUTO");
						ManagerDAO.save(dbObject);
					}

					//Si la journée précédente est déjà fermée on ouvre la nouvelle journée avec la date d'aujourd'hui
					else if(paramCompensateurCentrale.getStatutJourneeEnCours()!=null && paramCompensateurCentrale.getStatutJourneeEnCours().equals(StatutJournee.FERMETURE)){
						if(paramCompensateurCentrale.getHeureStartJournee().equals(new SimpleDateFormat("HH:mm").format(new Date()))){
							ParamCompensateurCentrale dbObject =  new ParamCompensateurCentrale();
							dbObject.setDateJournee(new Date());
							dbObject.setDateJourneeEnCours(dbObject.getDateJournee());
							dbObject.setStatutJournee(StatutJournee.OUVERTURE);
							dbObject.setStatutJourneeEnCours(dbObject.getStatutJournee());
							dbObject.setTourActuel(1);
							dbObject.setTourMax(paramCompensateurCentrale.getTourMax());
							dbObject.setUtiTraitement("AUTO");
							ManagerDAO.save(dbObject);
						}
					}

					else if(paramCompensateurCentrale.getStatutJourneeEnCours()==null){
						ParamCompensateurCentrale dbObject =  new ParamCompensateurCentrale();
						dbObject.setDateJournee(paramCompensateurCentrale.getDateJournee());
						dbObject.setDateJourneeEnCours(paramCompensateurCentrale.getDateJourneeEnCours());
						dbObject.setStatutJournee(StatutJournee.FERMETURE);
						dbObject.setStatutJourneeEnCours(dbObject.getStatutJournee());
						dbObject.setTourActuel(paramCompensateurCentrale.getTourActuel());
						dbObject.setTourMax(paramCompensateurCentrale.getTourMax());
						dbObject.setUtiTraitement("AUTO");
						ManagerDAO.save(dbObject);
					}
				}

				////Si la date de la base est égale à la date d'aujourd'hui
				if(paramCompensateurCentrale.getDateJourneeEnCours().equals(new Date())){


					if(paramCompensateurCentrale.getStatutJourneeEnCours()!=null && paramCompensateurCentrale.getStatutJourneeEnCours().equals(StatutJournee.OUVERTURE)){
						if(paramCompensateurCentrale.getHeureFinJournee().equals(new SimpleDateFormat("HH:mm").format(new Date()))){
							ParamCompensateurCentrale dbObject =  new ParamCompensateurCentrale();
							dbObject.setDateJournee(paramCompensateurCentrale.getDateJournee());
							dbObject.setDateJourneeEnCours(paramCompensateurCentrale.getDateJourneeEnCours());
							dbObject.setStatutJournee(StatutJournee.FERMETURE);
							dbObject.setStatutJourneeEnCours(dbObject.getStatutJournee());
							dbObject.setTourActuel(paramCompensateurCentrale.getTourActuel());
							dbObject.setTourMax(paramCompensateurCentrale.getTourMax());
							dbObject.setUtiTraitement("AUTO");
							ManagerDAO.save(dbObject);
						}
					}
					else if(paramCompensateurCentrale.getStatutJourneeEnCours()==null){
						if(paramCompensateurCentrale.getHeureStartJournee().equals(new SimpleDateFormat("HH:mm").format(new Date()))){
							ParamCompensateurCentrale dbObject =  new ParamCompensateurCentrale();
							dbObject.setDateJournee(new Date());
							dbObject.setDateJourneeEnCours(dbObject.getDateJournee());
							dbObject.setStatutJournee(StatutJournee.OUVERTURE);
							dbObject.setStatutJourneeEnCours(dbObject.getStatutJournee());
							dbObject.setTourActuel(1);
							dbObject.setTourMax(paramCompensateurCentrale.getTourMax());
							dbObject.setUtiTraitement("AUTO");
							ManagerDAO.save(dbObject);
						}
					}
				}


			}

		}catch(Exception e){

			e.printStackTrace();
		}*/
	}


	/***public void robotTraitementCompensation(){
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


	/***public void traitementAuto(){

		ParamCompensateurCentrale paramCompensateurCentrale = filterLastParamCompensateurCentrale();

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

								ManagerDAO.update(paramCompensateurCentrale);

								//Exécution du traitement
								traitement();

							}else{
								System.err.println("*****Le nombre de tours autorisés est dépassé******");
							}
						}
					}
				}
			}
		}
	}*/


	public boolean deleteFichier(TraitementTourCompensation t, Date tempsArchive, String repertoireArchive){

		boolean result = false;

		Calendar cal = Calendar.getInstance();

		Date heureArchive = tempsArchive;

		//System.out.println("**********heureArchive********" + heureArchive);

		Calendar now = Calendar.getInstance();
		now.setTime(heureArchive);

		//	System.out.println("****now.get(Calendar.HOUR_OF_DAY)*****" + now.get(Calendar.HOUR_OF_DAY));
		//	System.out.println("***now.get(Calendar.MINUTE)****" + now.get(Calendar.MINUTE));

		cal.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.MONTH, now.get(Calendar.MONTH));
		cal.set(Calendar.YEAR, now.get(Calendar.YEAR));
		cal.set(Calendar.HOUR_OF_DAY,now.get(Calendar.HOUR_OF_DAY));
		//cal.set(Calendar.MINUTE,now.get(Calendar.MINUTE));

		Date dateToDelete = cal.getTime();
		//System.out.println("**dateToDelete***" + dateToDelete);

		Calendar actu = Calendar.getInstance();

		Date actual = actu.getTime();
		//System.out.println("**actual***" + actual);


		if((cal.get(Calendar.DAY_OF_MONTH)==actu.get(Calendar.DAY_OF_MONTH)) && (cal.get(Calendar.MONTH)==actu.get(Calendar.MONTH)) && (cal.get(Calendar.YEAR)==actu.get(Calendar.YEAR)) && (cal.get(Calendar.HOUR_OF_DAY)==actu.get(Calendar.HOUR_OF_DAY)) ){ //Si il est l'heure d'archivage   //////&& (cal.get(Calendar.MINUTE)==actu.get(Calendar.MINUTE))
			String repArchive = repertoireArchive;
			String pathRepArchive = repArchive + File.separator + t.getFichiersTraite();

			File f = new File(pathRepArchive);
			if(f.exists()){
				if(f.delete()){
					result = true;
					System.out.println("***Deletion of the file****" + pathRepArchive);
				}else{
					System.out.println("***Did not delete the file****" + pathRepArchive);
				}
			}
		}//else
		//System.out.println("**NOT STARTING***" + cal.getTime() + "****" + actu.getTime());

		return result;
	}


	public String time(){

		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH) + 1;
		int year  = cal.get(Calendar.YEAR);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);
		int sec = cal.get(Calendar.SECOND);
		int millisec = cal.get(Calendar.MILLISECOND);

		return  "" + day + month + year + hour + min + sec + millisec;
	}

	@Override
	public void suppressionArchives(){

		List<ParametragesGenTeleCompense> parametragesGenTeleCompenses =  filterParamGen();
		ParametragesGenTeleCompense parametragesGenTeleCompense  = new ParametragesGenTeleCompense();
		if(parametragesGenTeleCompenses!=null && !parametragesGenTeleCompenses.isEmpty()){
			parametragesGenTeleCompense = parametragesGenTeleCompenses.get(0);
		}

		List<String> listFichiers = new ArrayList<String>();
		boolean result = false;
		if(!filterTraitementTourCompensationsArchive().isEmpty()){

			for(TraitementTourCompensation t: filterTraitementTourCompensationsArchive()){

				///listFichiers.clear();

				if(t.getTourCompensation().getTypeProcess().equals(TypeProcess.RAPATRIEMENT_IMG_ALLER)){

					/*String repArchive = parametragesGenTeleCompense.getRepertoireArchivageCollecteImageAller();

					OUTPUT_COMPILED_FILE = repArchive + File.separator + "Archives_Img_Aller_1.1_" + time().trim() + ".zip";

					zipFiles(repArchive, OUTPUT_COMPILED_FILE);*/

					result = deleteFichier(t, parametragesGenTeleCompense.getTempsArchivageCollecteImageAller(), parametragesGenTeleCompense.getRepertoireArchivageCollecteImageAller());
					//System.out.println("**Deletion of **" + t.getFichiersTraite() + " ***is*** " + result);
					if(result)
						listFichiers.add(t.getFichiersTraite());
					else{

						//System.out.println("****listFichiers is empty***" );
					}


				}
				if(t.getTourCompensation().getTypeProcess().equals(TypeProcess.RAPATRIEMENT_IMG_RETOUR)){
					result = deleteFichier(t, parametragesGenTeleCompense.getTempsArchivageCollecteImageRetour(), parametragesGenTeleCompense.getRepertoireArchivageCollecteImageRetour());
					//System.out.println("**Deletion of **" + t.getFichiersTraite() + " ***is*** " + result);
					if(result)
						listFichiers.add(t.getFichiersTraite());


				}
				if(t.getTourCompensation().getTypeProcess().equals(TypeProcess.FICHIER_COMPTA_ALLER)){

					result = deleteFichier(t, parametragesGenTeleCompense.getTempsArchivageFichierComptabilisationAller(), parametragesGenTeleCompense.getRepertoireArchivageFichierComptabilisationAller());
					//	System.out.println("**Deletion of **" + t.getFichiersTraite() + " ***is*** " + result);
					if(result)
						listFichiers.add(t.getFichiersTraite());


				}
				if(t.getTourCompensation().getTypeProcess().equals(TypeProcess.FICHIER_COMPTA_RETOUR)){
					result = deleteFichier(t, parametragesGenTeleCompense.getTempsArchivageFichierComptabilisationRetour(), parametragesGenTeleCompense.getRepertoireArchivageFichierComptabilisationRetour());
					//System.out.println("**Deletion of **" + t.getFichiersTraite() + " ***is*** " + result);
					if(result)
						listFichiers.add(t.getFichiersTraite());


				}


			}



			/*****Envoi Rapport Suppression par email ****/
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
			if(filterParamEmailAuto()!=null && !filterParamEmailAuto().isEmpty()){
				for(ParamEmailAuto p: filterParamEmailAuto()){
					ip = p.getIp();
					email = p.getEmail();
					pass = Encrypter.getInstance().decryptText(p.getPass());
					port = p.getPort();
				}
			}

			String title="Rapport Suppression des archives le " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()) + " à " + new SimpleDateFormat("HH:mm:ss").format(new Date()) ;
			String msg = "Bonjour, " + "\t" + "\n" + "\t\t" + "Veuillez trouver ci-dessous la liste des fichiers archives supprimés ce jour : " + "\n\n";

			for(String s: listFichiers){
				msg = msg + s + "\n\n";
			}

			msg = msg + "Un total de " + listFichiers.size() + " fichiers archivés " + "ont été supprimés.";

			//System.out.println("****msg***" + msg);

			try {
				if(!listFichiers.isEmpty()){
					//SendMail.sendMail(null, null, mailsTo, mailsCC, mailsBCC, title, msg, ip, email, pass, port);
					try {
						List<ParamEmailAuto> parametres = ManagerDAO.filter(ParamEmailAuto.class, null, null, null, null, 0, -1);
						if(parametres!=null&&!parametres.isEmpty()){
							AfrilandSendMail.sendMail(filesNames, filesPath, mailsTo, mailsCC, title, msg, email, pass, email + "@afrilandfirstbank.com", "AFRILAND FIRST BANK", ip, String.valueOf(port));
						}
					} catch (Exception ex) {
						// TODO Auto-generated catch block
					}
					System.out.println("**************************************Sent email*************************************" );
				}
				else{
					System.out.println("****Did not send email***" );
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*********************************************************************************/

		}
	}

	public void zipFiles(String sourceFolder, String outputFile){

		generateFileList(sourceFolder, new File(sourceFolder));

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(outputFile);
			System.out.println("fos : " + fos);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		zipIt(sourceFolder, fos);

	}

	/**
	 * Zip it
	 * @param zipFile output ZIP file location
	 */
	public void zipIt(String sourceFolder, FileOutputStream fos){

		if(fos!=null){

			byte[] buffer = new byte[1024];

			try{

				/*File files = new File(zipFile);

			if(!files.exists()){

				if(files.mkdir()){
					System.out.println("*****Les répertoires parents sont crées avec succès!******");
				} else {
					System.out.println("******Echec de création des répertoires parents!*****");
				}
			}*/


				ZipOutputStream zos = new ZipOutputStream(fos);


				System.out.println("zos : " + zos);


				for(String file : this.fileList){

					System.out.println("File Added : " + file);
					ZipEntry ze= new ZipEntry(file);
					zos.putNextEntry(ze);

					FileInputStream in = new FileInputStream(sourceFolder + File.separator + file);

					int len;
					while ((len = in.read(buffer)) > 0) {
						System.out.println("Writing file : " + file + " to zip file");
						zos.write(buffer, 0, len);
					}

					in.close();
				}

				zos.closeEntry();
				//remember close it
				zos.close();

				System.out.println("Done");
			}catch(IOException ex){
				ex.printStackTrace();
			}

		}
		else{

			System.out.println("fos is null");
		}
	}




	/**
	 * Traverse a directory and get all files,
	 * and add the file into fileList
	 * @param node file or directory
	 */
	public void generateFileList(String sourceFolder, File node){

		//add file only
		if(node.isFile()){
			System.out.println("****node.getAbsoluteFile().toString()*****" + node.getAbsoluteFile().toString());
			fileList.add(generateZipEntry(sourceFolder,node.getAbsoluteFile().toString()));
		}

		if(node.isDirectory()){
			String[] subNote = node.list();
			for(String filename : subNote){
				System.out.println("****filename*****" + filename);
				generateFileList(sourceFolder,new File(node, filename));
			}
		}

	}


	/**
	 * Format the file path for zip
	 * @param file file path
	 * @return Formatted file path
	 */
	private String generateZipEntry(String sourceFolder, String file){
		System.out.println("****file.substring(SOURCE_FOLDER.length()+1, file.length())*****" + file.substring(sourceFolder.length()+1, file.length()));
		return file.substring(sourceFolder.length()+1, file.length());
	}

	public List<ParamEmailAuto> filterParamEmailAuto(){
		return ManagerDAO.filter(ParamEmailAuto.class, null, null, null, null, 0, -1);
	}

	public ParamEmailAuto saveParamEmailAuto(ParamEmailAuto paramEmailAuto){
		return ManagerDAO.save(paramEmailAuto);
	}

	public ParamEmailAuto updateParamEmailAuto(ParamEmailAuto paramEmailAuto){
		return ManagerDAO.update(paramEmailAuto);
	}

	public ParamCompensateurCentrale filterLastParamCompensateurCentrale(){

		ParamCompensateurCentrale returnParam;

		List<ParamCompensateurCentrale> paramCompensateurCentrales = ManagerDAO.filter(ParamCompensateurCentrale.class, null, null, OrderContainer.getInstance().add(Order.desc("id")), null, 0, -1);

		if(paramCompensateurCentrales!=null && !paramCompensateurCentrales.isEmpty())
			returnParam = paramCompensateurCentrales.get(0);
		else
			returnParam = null;

		return returnParam;
	}


	public List<ValidateDoublonsInFichier> filterDoublons(Date dateDebut, Date dateFin, TypeProcess typeProcess){

		RestrictionsContainer rc = RestrictionsContainer.getInstance();

		if(dateDebut!=null)rc.add(Restrictions.ge("dateTraitement", dateDebut));
		if(dateFin!=null)rc.add(Restrictions.le("dateTraitement", dateFin));
		if(typeProcess!=null)rc.add(Restrictions.eq("typeProcess", typeProcess));


		return ManagerDAO.filter(ValidateDoublonsInFichier.class, null, rc, OrderContainer.getInstance().add(Order.desc("id")), null, 0, -1);
	}



	public List<Incoherences> findExistingIncoherencesAndMarkThem(List<Incoherences> listIncoherences){

		List<Incoherences> list = new ArrayList<Incoherences>();

		RestrictionsContainer rc ;

		for(Incoherences i: listIncoherences){

			rc = RestrictionsContainer.getInstance();

			rc.add(Restrictions.eq("agence", i.getAgence()));
			rc.add(Restrictions.eq("ncp", i.getNcp()));
			rc.add(Restrictions.eq("cle", i.getCle()));
			rc.add(Restrictions.eq("nomBeneficiaire", i.getNomBeneficiaire()));
			rc.add(Restrictions.eq("nomBeneficiaireAmplitude", i.getNomBeneficiaireAmplitude()));
			rc.add(Restrictions.eq("drec", i.getDrec()));
			rc.add(Restrictions.eq("ope", i.getOpe()));
			rc.add(Restrictions.eq("eve", i.getEve()));
			rc.add(Restrictions.eq("montant", i.getMontant()));
			rc.add(Restrictions.eq("codeGuicherDonneurOrdre", i.getCodeGuicherDonneurOrdre()));
			rc.add(Restrictions.eq("ncpDonneurOrdre", i.getNcpDonneurOrdre()));
			rc.add(Restrictions.eq("codeEtabDonneurOrdre", i.getCodeEtabDonneurOrdre()));
			rc.add(Restrictions.eq("cleDonneurOrdre", i.getCleDonneurOrdre()));
			rc.add(Restrictions.eq("nomDonneurOrdre", i.getNomDonneurOrdre()));

			List<Incoherences> l = ManagerDAO.filter(Incoherences.class, null, rc, null, null, 0, -1);
			if(l!=null && !l.isEmpty()){
				list.add(l.get(0));
			}

		}

		System.out.println("****list size**" + list.size());

		return list;

	}

	public List<Incoherences> findExistingIncoherences(Incoherences i){

		List<Incoherences> incs = new ArrayList<Incoherences>();

		RestrictionsContainer rc = RestrictionsContainer.getInstance();

		rc.add(Restrictions.eq("agence", i.getAgence()));
		rc.add(Restrictions.eq("ncp", i.getNcp()));
		rc.add(Restrictions.eq("cle", i.getCle()));
		rc.add(Restrictions.eq("nomBeneficiaire", i.getNomBeneficiaire()));
		rc.add(Restrictions.eq("nomBeneficiaireAmplitude", i.getNomBeneficiaireAmplitude()));
		rc.add(Restrictions.eq("drec", i.getDrec()));
		rc.add(Restrictions.eq("ope", i.getOpe()));
		rc.add(Restrictions.eq("eve", i.getEve()));
		rc.add(Restrictions.eq("montant", i.getMontant()));
		rc.add(Restrictions.eq("codeGuicherDonneurOrdre", i.getCodeGuicherDonneurOrdre()));
		rc.add(Restrictions.eq("ncpDonneurOrdre", i.getNcpDonneurOrdre()));
		rc.add(Restrictions.eq("codeEtabDonneurOrdre", i.getCodeEtabDonneurOrdre()));
		rc.add(Restrictions.eq("cleDonneurOrdre", i.getCleDonneurOrdre()));
		rc.add(Restrictions.eq("nomDonneurOrdre", i.getNomDonneurOrdre()));

		incs = ManagerDAO.filter(Incoherences.class, null, rc, null, null, 0, -1);

		return incs;
	}



	public byte[] getReportPDFBytes(String reportName, HashMap<String, Object> map, Collection<?> maCollection) throws Exception {

		// Construction du JasperPrint
		JasperPrint jp = printReport(reportName, map, maCollection);

		// Construction du tableau de bytes
		return JasperExportManager.exportReportToPdf(jp);
	}


	/**
	 * Methode de construction de l'etat
	 * @param reportName
	 * @param map
	 * @param maCollection
	 * @return
	 * @throws Exception
	 */
	private static JasperPrint printReport(String reportName, HashMap<String, Object> map, Collection<?> maCollection) throws Exception {

		// Construction de la source de donnees de l'etat
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(maCollection);

		// Construction de l'etat
		return JasperFillManager.fillReport(reportName, map, dataSource);
	}


	public void traitement(String piecesJointesDir, String reportsDir){

		try{

			/***Vérification de la journée en cours****/
			ParamCompensateurCentrale paramCompensateurCentrale = filterLastParamCompensateurCentrale();
			if(paramCompensateurCentrale!=null){

				if(!paramCompensateurCentrale.getDateJourneeEnCours().equals(new SimpleDateFormat("dd/MM/yyyy").parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date())))){

					/**System.out.println("Cette journée n'est pas encore ouverte !!! Contactez le Compensateur Centrale");
					this.error=true;
					this.information=true;
					ExceptionHelper.showError("Cette journée n'est pas encore ouverte !!! Contactez le Compensateur Centrale", this);
					return;*/

					return;

				}else if(paramCompensateurCentrale.getDateJourneeEnCours().equals(new SimpleDateFormat("dd/MM/yyyy").parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date())))){

					/**if(paramCompensateurCentrale.getStatutJourneeEnCours().equals(StatutJournee.FERMETURE)){
						System.out.println("Cette journée est déjà fermée !!! Contactez le Compensateur Centrale");
						this.error=true;
						this.information=true;
						ExceptionHelper.showError("Cette journée est déjà fermée !!! Contactez le Compensateur Centrale", this);
						return;
					}*/

					return;
				}
			}else if(paramCompensateurCentrale==null){

				/**System.out.println("Le paramétrage de la compensation n'est pas encore effectué !!! Contactez le Compensateur Centrale");
				this.error=true;
				this.information=true;
				ExceptionHelper.showError("Le paramétrage de la compensation n'est pas encore effectué !!! Contactez le Compensateur Centrale", this);

				return;*/

				return;

			}

			////On s'assure d'abord que le numéro du tour entrée à l'interface n'a pas déjà été traitée
			List<TourCompensation> listTourCompense = filterTourCompensation(new Date(), user.getLogin(), tour, typeProcess);
			if(listTourCompense!=null && !listTourCompense.isEmpty()){

				System.out.println("****Ce tour de compensation " + tour + " a déjà été effectué pour ce type de process: " + typeProcess);

				/**this.error=true;
				this.information = true;
				ExceptionHelper.showError("Ce tour de compensation " + tour + " a déjà été effectué pour ce type de process: " + typeProcess , this);

				return;*/

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



				/*********Exécution du Traitement********/
				doTraitement(piecesJointesDir, reportsDir, typeProcess, repEntreeImageAller, repDestinationImageAller, repArchiveEntreeImageAller, nbrFichACopierImg, nbrFichCopieImg, nbrDoublonsImg, 0, 0, 0, listFileNamesWithDoublons, listFichierEtValEnDouble, listFichierSupprimePourDoublonsEntreFichiers, null, listFichierContenantDoublonsEntreFichiers, null,null, fichiersDestination,fichiersTraite, "rapportCopieImgAller.jasper", "ReportCopieImgAller.xhtml" );



			}else if(typeProcess.equals(TypeProcess.FICHIER_COMPTA_ALLER)){

				String fichiersTraite = "";

				listFileNamesWithDoublons.clear();

				listFichierEtValEnDouble.clear(); 

				listFichierSupprimePourDoublonsEntreFichiers.clear();

				mapFichierEtValEnDoubleComptaAllerTraite1.clear();

				listFichierContenantDoublonsEntreFichiers.clear();

				Map<String, List<String>> mapFichiersEntreeEtContenus = new HashMap<>();

				Map<String, List<String>> mapFichiersDestEtContenus = new HashMap<>();


				nbrFichACopierCompta = 0;
				nbrFichCopieCompta = 0;
				nbrDoublonsCompta =0;
				nbrFichContenantDoublonsCompta=0;
				nbrValeursEnDoubleCompta=0;
				nbrValeursDeposeesCompta=0;

				boolean fichiersDestination = false;



				/*******Récupération des différents répertoires*********/
				String repEntreeFichCompteAller = parametragesGenTeleCompense.getRepertoireEntreeFichierComptabilisationAller();
				String repDestinationFicheComptaAller = parametragesGenTeleCompense.getRepertoireDestinationFichierComptabilisationAller();
				String repArchiveEntreeFicheComptaAller = parametragesGenTeleCompense.getRepertoireArchiveEntreeFichierComptabilisationAller();


				/*********Exécution du Traitement********/
				doTraitement(piecesJointesDir, reportsDir,typeProcess, repEntreeFichCompteAller, repDestinationFicheComptaAller, repArchiveEntreeFicheComptaAller, nbrFichACopierCompta, nbrFichCopieCompta, nbrDoublonsCompta, nbrFichContenantDoublonsCompta, nbrValeursEnDoubleCompta, nbrValeursDeposeesCompta, listFileNamesWithDoublons, listFichierEtValEnDouble, listFichierSupprimePourDoublonsEntreFichiers, mapFichierEtValEnDoubleComptaAllerTraite1, listFichierContenantDoublonsEntreFichiers,  mapFichiersEntreeEtContenus,mapFichiersDestEtContenus, fichiersDestination,fichiersTraite, "rapportCopieComptaAller.jasper", "ReportCopieComptaAller.xhtml" );


				/*****
				File folderEntree = new File(repEntreeFichCompteAller);
				//Si le répertoire n'existe pas, il faut le créer
				if(!folderEntree.exists()){
					if(folderEntree.mkdirs()){
						System.out.println("*****Les répertoires parents sont crées avec succès!******");
					} else {
						System.out.println("******Echec de création des répertoires parents!*****");
					}
				}
				System.out.println("****folderEntree*****" + folderEntree);


				File folderDest = new File(repDestinationFicheComptaAller);
				//Si le répertoire n'existe pas, il faut le créer
				if(!folderDest.exists()){
					if(folderDest.mkdirs()){
						System.out.println("*****Les répertoires parents sont crées avec succès!******");
					} else {
						System.out.println("******Echec de création des répertoires parents!*****");
					}
				}
				System.out.println("****folderDest*****" + folderDest);

				TourCompensation tourCompensation = new TourCompensation();
				tourCompensation.setUtiTraitement(user.getLogin());
				tourCompensation.setNumeroTour(tour);
				tourCompensation.setDateTraitement(new Date());
				tourCompensation.setTypeProcess(TypeProcess.FICHIER_COMPTA_ALLER);
				ManagerDAO.save(tourCompensation);

				File[] listOfFiles = folderEntree.listFiles();
				if(listOfFiles==null || listOfFiles.length==0){

					System.out.println("Aucun fichier trouvé dans le répertoire d'entrée !!!");
					this.error=true;
					this.information=true;
					ExceptionHelper.showError("Aucun fichier trouvé dans le répertoire d'entrée !!!", this);

					List<TourCompensation>toursCompensation = ViewHelper.virementsRecManager.filterTourCompensation(new Date(), user.getLogin(), tour, typeProcess);
					if(toursCompensation!=null &&!toursCompensation.isEmpty()){
						tourCompensation = toursCompensation.get(0);
						ManagerDAO.delete(tourCompensation);
					}

					return;
				}

				/**Liste des fichiers dans le répertoire de sortie**
				File[] listFiles = folderDest.listFiles();
				if(listFiles!=null && listFiles.length>0){
					//Le répertoire de destination n'est pas vide; lancer l'archivage des fichiers contenus
					//archivage();  //Traitement 1
					fichiersDestination = true; 
				}



				if(listOfFiles!=null&&listOfFiles.length>0){

					List<TourCompensation>toursCompensation = ViewHelper.virementsRecManager.filterTourCompensation(new Date(), user.getLogin(), tour, typeProcess);
					if(toursCompensation!=null &&!toursCompensation.isEmpty()){
						tourCompensation = toursCompensation.get(0);
					}

					int j = 0;

					nomFichierDoublon = false;

					/**Parcours du répertoire de destination pour collecter les contenus**
					List<String> fileLines = new ArrayList<String>();
					for(int k=0; k<listFiles.length;k++){
						fileLines = readAndScanFile(Paths.get(repDestinationFicheComptaAller + File.separator + listFiles[k].getName()));
						if(!fileLines.isEmpty())
							mapFichiersDestEtContenus.put(listFiles[k].getName(), fileLines);
					}

					while (j<3){ // Nous avons trois traitements à effectuer

						fichiersTraite = "";

						/**Parcours du répertoire d'entrée**
						for (int i = 0; i < listOfFiles.length; i++) {


							if (listOfFiles[i].isFile()) {

								if(j==2) //pour ne pas dupliquer ce chiffre lors du deuxième et troisième traitement
									nbrFichACopierComptaAller++;

								System.out.println("File " + listOfFiles[i].getName());

								String fichierEntree = repEntreeFichCompteAller + File.separator + listOfFiles[i].getName();
								String fichierDest = repDestinationFicheComptaAller + File.separator + listOfFiles[i].getName();
								String fichierArchiveEntree = repArchiveEntreeFicheComptaAller + File.separator + listOfFiles[i].getName();

								System.out.println("****fichierEntree*****" + fichierEntree);
								System.out.println("****fichierDest*****" + fichierDest);


								Path pathEntree = Paths.get(fichierEntree);
								Path pathDestination = Paths.get(fichierDest);
								Path pathArchiveEntree = Paths.get(fichierArchiveEntree);


								if(j==0){

									/**Ne pas oublier de sauvegarder les nbr des doublons et autre en BD**

									typeTraitement = TypeTraitement.DOUBLONS_NOM_FICHIER;
									if(traitementEtCopieFichiersComptaAller(folderDest.listFiles(), listOfFiles[i].getName(), pathEntree, pathDestination, pathArchiveEntree, tourCompensation, typeTraitement, parametragesCaracteresSpeciauxs,fichiersDestination,mapFichiersDestEtContenus)){
										fichiersTraite = fichiersTraite + ";" + fichierEntree;
									}

								}else if(j==1){

									typeTraitement = TypeTraitement.DOUBLONS_ENTRE_FICHIER;
									if(traitementEtCopieFichiersComptaAller(folderDest.listFiles(), listOfFiles[i].getName(), pathEntree, pathDestination, pathArchiveEntree, tourCompensation, typeTraitement, parametragesCaracteresSpeciauxs, fichiersDestination,mapFichiersDestEtContenus)){
										fichiersTraite = fichiersTraite + ";" + fichierEntree;
									}

								}
								else if(j==2){
									typeTraitement = TypeTraitement.DOUBLONS_DANS_FICHIER;
									if(traitementEtCopieFichiersComptaAller(folderDest.listFiles(), listOfFiles[i].getName(), pathEntree, pathDestination, pathArchiveEntree, tourCompensation, typeTraitement, parametragesCaracteresSpeciauxs, fichiersDestination,mapFichiersDestEtContenus)){
										fichiersTraite = fichiersTraite + ";" + fichierEntree;
										//for(int p=1; p<listFichierEtValEnDouble.size();p++){ //p==0 c'est le nom du fichier

										mapFichierEtValEnDoubleComptaAllerTraite1.put(listFichierEtValEnDouble.get(0), listFichierEtValEnDouble.subList(1, listFichierEtValEnDouble.size()));
										//}
									}

								}


							}
						}

						j++;
					}



					/** Sauvegarder le traitement*
					saveTraitement(typeProcess, typeTraitement, fichiersTraite, tourCompensation); 


					/***Affichage Rapport de traitement + envoi mail***
					HashMap<String, Object> param = new HashMap<String, Object>();

					param.put("uti", user.getLogin());
					param.put("nbrFichACopierComptaAller", nbrFichACopierComptaAller);      
					param.put("nbrFichCopieComptaAller", nbrFichCopieComptaAller);
					param.put("nbrDoublonsComptaAller", nbrDoublonsComptaAller);
					param.put("nbrFichContenantDoublonsComptaAller", nbrFichContenantDoublonsComptaAller);
					param.put("nbrValeursDeposeesComptaAller", nbrValeursDeposeesComptaAller);
					param.put("nbrValeursEnDoubleComptaAller", nbrValeursEnDoubleComptaAller);



					List<ValidateDoublonsInFichier> subs = new ArrayList<ValidateDoublonsInFichier>();
					ValidateDoublonsInFichier sub;
					String color="";
					String previousColor="";
					for(String key:mapFichierEtValEnDoubleComptaAllerTraite1.keySet()){

						sub = new ValidateDoublonsInFichier();
						sub.setFichier(key);

						String doublons="";
						List<String> mapString = mapFichierEtValEnDoubleComptaAllerTraite1.get(key);
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
						sub.setTypeProcess(TypeProcess.FICHIER_COMPTA_ALLER);

						subs.add(sub);

						previousColor = color;
					}

					for(ValidateDoublonsInFichier v: subs){
						ManagerDAO.save(v);
					}

					FichiersSupprimeACauseDeValeursEnDoublonsDansAutreFichier  f = new FichiersSupprimeACauseDeValeursEnDoublonsDansAutreFichier();
					List<FichiersSupprimeACauseDeValeursEnDoublonsDansAutreFichier> fList = new ArrayList<FichiersSupprimeACauseDeValeursEnDoublonsDansAutreFichier>();
					for(int i=0; i<listFichierSupprimePourDoublonsEntreFichiers.size();i++){
						if(i%2 == 0){
							f.setFichierSupprime(listFichierSupprimePourDoublonsEntreFichiers.get(i));
							if(i<listFichierSupprimePourDoublonsEntreFichiers.size()-1)
								f.setFichierConserve(listFichierSupprimePourDoublonsEntreFichiers.get(i+1));
						}
						/*else{
							if(i<listFichierSupprimePourDoublonsEntreFichiers.size()-1)
								f.setFichierConserve(listFichierSupprimePourDoublonsEntreFichiers.get(i+1));
						}*
					}
					if(f.getFichierSupprime()!=null && !f.getFichierSupprime().isEmpty())fList.add(f);

					List<ValidateDoublonsInFichier> repports = new ArrayList<ValidateDoublonsInFichier>();

					ValidateDoublonsInFichier subReportDoublonsDansFichier = new ValidateDoublonsInFichier();
					subReportDoublonsDansFichier.setSubReportDoublonsDansFichiers(subs);
					subReportDoublonsDansFichier.setFichiersSupprimeACauseDeValeursEnDoublonsDansAutreFichiers(fList);

					repports.add(subReportDoublonsDansFichier);

					if(!subs.isEmpty()){
						param.put("libelleTabAnnexe", "Liste des doublons apparus en terme de contenus dans un même fichier:");
					}
					if(!fList.isEmpty()){
						param.put("libelleSupprime", "Liste des fichiers supprimés parce que leur contenus étaient contenus dans un autre fichier:");
					}

					/***Rapport***
					reportViewer = new ReportViewer(repports,"rapportCopieComptaAller.jasper", param, "afb.statistique.title", this,"/views/repport/ReportCopieComptaAller.xhtml");
					reportViewer.viewReport();


					/*********************Envoi mail rapport traitement*****************
					List<String>mailsTo = new ArrayList<String>();
					List<ParamEmail> listEmails = new ArrayList<ParamEmail>();
					for(ParamEmail p :parametragesGenTeleCompense.getListEmails()){

						if(p.getValide().equals(Boolean.TRUE)){
							listEmails.add(p);
						}
					}

					if(listEmails!=null && !listEmails.isEmpty()){

						for(ParamEmail pe: listEmails){
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

					String title="Rapport traitement (copie) des fichiers de comptabilisation phase Aller, le " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()) + " à " + new SimpleDateFormat("HH:mm:ss").format(new Date());
					String msg = "Bonjour, " + "\t" + "\n" + "\t\t" + "Veuillez trouver ci-dessous le rapport des fichiers de comptabilisation de la phase Aller copiés ce jour : " + "\n\n";

					msg = msg + "Nombre de fichiers à copier: " + nbrFichACopierComptaAller + "\n\n";
					msg = msg + "Nombre de fichers copiés dans le répertoire de destination: " + nbrFichCopieComptaAller + "\n\n";
					msg = msg + "Nombre de fichiers en double détectés " + nbrDoublonsComptaAller +"\n\n";
					msg = msg + "Nombre de fichiers contenant des doublons: " + nbrFichContenantDoublonsComptaAller + "\n\n";
					msg = msg + "Nombre de valeurs en double détectés " + nbrValeursEnDoubleComptaAller + "\n\n";
					msg = msg + "Nombre de valeurs déposées " + nbrValeursDeposeesComptaAller + "\n\n";


					List<String> filesNames = new ArrayList<String>();
					List<String> filesPath = new ArrayList<String>();

					String fName = new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date());
					FileOutputStream fileOuputStream = new FileOutputStream(ViewHelper.getPiecesJointesDir() + "Rapport_Copie_Fichier_Compta_Aller_" + fName+".pdf");
					fileOuputStream.write(ViewHelper.virementsRecManager.getReportPDFBytes(ViewHelper.getReportsDir2().concat("rapportCopieComptaAller.jasper"), param, repports));
					fileOuputStream.close();

					filesNames.add("Rapport_Copie_Fichier_Compta_Aller_" + fName+".pdf");
					filesPath.add(ViewHelper.getPiecesJointesDir() + "Rapport_Copie_Fichier_Compta_Aller_" + fName+".pdf");

					StatistiqueRapports stat = new StatistiqueRapports();
					stat.setRapport("Rapport_Copie_Fichier_Compta_Aller_" + fName+".pdf");
					stat.setTypeProcess(TypeProcess.FICHIER_COMPTA_ALLER);
					ManagerDAO.save(stat);

					try {
						SendMail.sendMail(filesNames, filesPath, mailsTo, mailsCC, mailsBCC, title, msg, ip, email, pass, port);
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					/*******************************************
				}else{

					this.error=true;
					this.information=true;
					ExceptionHelper.showError("Aucun fichier trouvé dans le répertoire d'entrée !!!", this);

					return;
				}*/

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


				/*********Exécution du Traitement********/
				doTraitement(piecesJointesDir, reportsDir,typeProcess, repEntreeImageRetour, repDestinationImageRetour, repArchiveEntreeImageRetour, nbrFichACopierImg, nbrFichCopieImg, nbrDoublonsImg, 0, 0, 0, listFileNamesWithDoublons, listFichierEtValEnDouble, listFichierSupprimePourDoublonsEntreFichiers, null, listFichierContenantDoublonsEntreFichiers, null,null, fichiersDestination,fichiersTraite, "rapportCopieImgRetour.jasper", "ReportCopieImgRetour.xhtml");


				/***
				File folderEntree = new File(repEntreeImageRetour);
				//Si le répertoire n'existe pas, il faut le créer
				if(!folderEntree.exists()){
					if(folderEntree.mkdirs()){
						System.out.println("*****Les répertoires parents sont crées avec succès!******");
					} else {
						System.out.println("******Echec de création des répertoires parents!*****");
					}
				}
				System.out.println("****folderEntree*****" + folderEntree);


				File folderDest = new File(repDestinationImageRetour);
				//Si le répertoire n'existe pas, il faut le créer
				if(!folderDest.exists()){
					if(folderDest.mkdirs()){
						System.out.println("*****Les répertoires parents sont crées avec succès!******");
					} else {
						System.out.println("******Echec de création des répertoires parents!*****");
					}
				}
				System.out.println("****folderDest*****" + folderDest);

				TourCompensation tourCompensation = new TourCompensation();
				tourCompensation.setUtiTraitement(user.getLogin());
				tourCompensation.setNumeroTour(tour);
				tourCompensation.setDateTraitement(new Date());
				tourCompensation.setTypeProcess(TypeProcess.RAPATRIEMENT_IMG_RETOUR);
				ManagerDAO.save(tourCompensation);

				File[] listOfFiles = folderEntree.listFiles();
				if(listOfFiles==null || listOfFiles.length==0){

					System.out.println("Aucun fichier trouvé dans le répertoire d'entrée !!!");
					this.error=true;
					this.information=true;
					ExceptionHelper.showError("Aucun fichier trouvé dans le répertoire d'entrée !!!", this);

					List<TourCompensation>toursCompensation = ViewHelper.virementsRecManager.filterTourCompensation(new Date(), user.getLogin(), tour, typeProcess);
					if(toursCompensation!=null &&!toursCompensation.isEmpty()){
						tourCompensation = toursCompensation.get(0);
						ManagerDAO.delete(tourCompensation);
					}

					return;
				}


				/**Liste des fichiers dans le répertoire d'entrée***
				File[] listFiles = folderDest.listFiles();
				if(listFiles!=null && listFiles.length>0){
					//Le répertoire de destination n'est pas vide; lancer l'archivage des fichiers contenus
					//archivage();
					fichiersDestination = true; 
				}

				typeTraitement = TypeTraitement.DOUBLONS_NOM_FICHIER;



				if(listOfFiles!=null&&listOfFiles.length>0){

					List<TourCompensation>toursCompensation = ViewHelper.virementsRecManager.filterTourCompensation(new Date(), user.getLogin(), tour, typeProcess);
					if(toursCompensation!=null &&!toursCompensation.isEmpty()){
						tourCompensation = toursCompensation.get(0);
					}

					/**Parcours du répertoire d'entrée***
					for (int i = 0; i < listOfFiles.length; i++) {
						if (listOfFiles[i].isFile()) {

							nbrFichACopierImgRetour++;

							System.out.println("File " + listOfFiles[i].getName());

							String fichierEntree = repEntreeImageRetour + File.separator + listOfFiles[i].getName();

							String fichierDest = repDestinationImageRetour + File.separator + listOfFiles[i].getName();

							System.out.println("****fichierEntree*****" + fichierEntree);
							System.out.println("****fichierDest*****" + fichierDest);


							Path pathEntree = Paths.get(fichierEntree);

							Path pathDestination = Paths.get(fichierDest);

							/**On récupère l'élément le plus récent du rép de destination*


							if(traitementEtCopieFichiersRetour(folderDest.listFiles(), listOfFiles[i].getName(), pathEntree, pathDestination, tourCompensation, fichiersDestination)){
								fichiersTraite = fichiersTraite + ";" + fichierEntree;
							}

						}
					}


					/** Sauvegarder le traitement**
					saveTraitement(typeProcess, typeTraitement, fichiersTraite, tourCompensation);


					/***Affichage Rapport de traitement + envoi mail***
					HashMap<String, Object> param = new HashMap<String, Object>();

					param.put("uti", user.getLogin());
					param.put("nbrFichACopierImgRetour", nbrFichACopierImgRetour);
					param.put("nbrFichCopieImgRetour", nbrFichCopieImgRetour);
					param.put("nbrDoublonsImgRetour", nbrDoublonsImgRetour);


					List<RapatriementImagesRetour> repports = new ArrayList<RapatriementImagesRetour>();

					//RapatriementImagesAller raport = new RapatriementImagesAller();
					//raport.setItems(listTauxConformite);
					//repports.add(raport);

					/***Rapport**
					reportViewer = new ReportViewer(repports,"rapportCopieImgRetour.jasper", param, "afb.statistique.title", this,"/views/repport/ReportCopieImgRetour.xhtml");
					reportViewer.viewReport();


					/*********************Envoi mail rapport traitement*****************
					List<String>mailsTo = new ArrayList<String>();
					List<ParamEmail> listEmails = new ArrayList<ParamEmail>();
					for(ParamEmail p :parametragesGenTeleCompense.getListEmails()){

						if(p.getValide().equals(Boolean.TRUE)){
							listEmails.add(p);
						}
					}

					if(listEmails!=null && !listEmails.isEmpty()){

						for(ParamEmail pe: listEmails){
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

					String title="Rapport traitement (copie) des fichiers images phase Retour, le " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()) + " à " + new SimpleDateFormat("HH:mm:ss").format(new Date());
					String msg = "Bonjour, " + "\t" + "\n" + "\t\t" + "Veuillez trouver ci-dessous le rapport des fichiers images de la phase Retour copiés ce jour : " + "\n\n";

					msg = msg +"Nombre de fichiers à copier: " + nbrFichACopierImgRetour + "\n\n";
					msg = msg +"Nombre de fichers copiés dans le répertoire de destination: " + nbrFichCopieImgRetour + "\n\n";
					msg = msg +"Nombre de doublons détectés: " + nbrDoublonsImgRetour + "\n\n";


					List<String> filesNames = new ArrayList<String>();
					List<String> filesPath = new ArrayList<String>();

					String fName = new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date());
					FileOutputStream fileOuputStream = new FileOutputStream(ViewHelper.getPiecesJointesDir() + "Rapport_Copie_Img_Retour_" + fName+".pdf");
					fileOuputStream.write(ViewHelper.virementsRecManager.getReportPDFBytes(ViewHelper.getReportsDir2().concat("rapportCopieImgRetour.jasper"), param, repports));
					fileOuputStream.close();

					filesNames.add("Rapport_Copie_Img_Retour_" + fName+".pdf");
					filesPath.add(ViewHelper.getPiecesJointesDir() + "Rapport_Copie_Img_Retour_" + fName+".pdf");

					StatistiqueRapports stat = new StatistiqueRapports();
					stat.setRapport("Rapport_Copie_Img_Retour_" + fName+".pdf");
					stat.setTypeProcess(TypeProcess.RAPATRIEMENT_IMG_RETOUR);
					ManagerDAO.save(stat);

					try {
						SendMail.sendMail(filesNames, filesPath, mailsTo, mailsCC, mailsBCC, title, msg, ip, email, pass, port);
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}else{

					this.error=true;
					this.information = true;
					ExceptionHelper.showError("Aucun fichier trouvé dans le répertoire d'entrée !!!", this);

					return;
				}*/

			}
			else if(typeProcess.equals(TypeProcess.FICHIER_COMPTA_RETOUR)){

				String fichiersTraite = "";

				listFileNamesWithDoublons.clear();

				listFichierEtValEnDouble.clear();

				listFichierSupprimePourDoublonsEntreFichiers.clear();

				mapFichierEtValEnDoubleComptaAllerTraite1.clear();

				//mapFichierEtValEnDoubleComptaRetourTraite1.clear();

				listFichierContenantDoublonsEntreFichiers.clear();

				Map<String, List<String>> mapFichiersEntreeEtContenus = new HashMap<>();

				Map<String, List<String>> mapFichiersDestEtContenus = new HashMap<>();

				nbrFichACopierCompta = 0;
				nbrFichCopieCompta = 0;
				nbrDoublonsCompta =0;
				nbrFichContenantDoublonsCompta=0;
				nbrValeursEnDoubleCompta=0;
				nbrValeursDeposeesCompta=0;

				boolean fichiersDestination = false;


				/*******Récupération des différents répertoires*********/
				String repEntreeFichCompteRetour = parametragesGenTeleCompense.getRepertoireEntreeFichierComptabilisationRetour();
				String repDestinationFicheComptaRetour = parametragesGenTeleCompense.getRepertoireDestinationFichierComptabilisationRetour();
				String repArchiveEntreeFicheComptaRetour = parametragesGenTeleCompense.getRepertoireArchiveEntreeFichierComptabilisationRetour();



				/*********Exécution du Traitement********/
				doTraitement(piecesJointesDir, reportsDir,typeProcess, repEntreeFichCompteRetour, repDestinationFicheComptaRetour, repArchiveEntreeFicheComptaRetour, nbrFichACopierCompta, nbrFichCopieCompta, nbrDoublonsCompta, nbrFichContenantDoublonsCompta, nbrValeursEnDoubleCompta, nbrValeursDeposeesCompta, listFileNamesWithDoublons, listFichierEtValEnDouble, listFichierSupprimePourDoublonsEntreFichiers, mapFichierEtValEnDoubleComptaAllerTraite1, listFichierContenantDoublonsEntreFichiers, mapFichiersEntreeEtContenus, mapFichiersDestEtContenus, fichiersDestination,fichiersTraite, "rapportCopieComptaRetour.jasper", "ReportCopieComptaRetour.xhtml" );




				/**		File folderEntree = new File(repEntreeFichCompteRetour);
				//Si le répertoire n'existe pas, il faut le créer
				if(!folderEntree.exists()){
					if(folderEntree.mkdirs()){
						System.out.println("*****Les répertoires parents sont crées avec succès!******");
					} else {
						System.out.println("******Echec de création des répertoires parents!*****");
					}
				}
				System.out.println("****folderEntree*****" + folderEntree);


				File folderDest = new File(repDestinationFicheComptaRetour);
				//Si le répertoire n'existe pas, il faut le créer
				if(!folderDest.exists()){
					if(folderDest.mkdirs()){
						System.out.println("*****Les répertoires parents sont crées avec succès!******");
					} else {
						System.out.println("******Echec de création des répertoires parents!*****");
					}
				}
				System.out.println("****folderDest*****" + folderDest);


				TourCompensation tourCompensation = new TourCompensation();
				tourCompensation.setUtiTraitement(user.getLogin());
				tourCompensation.setNumeroTour(tour);
				tourCompensation.setDateTraitement(new Date());
				tourCompensation.setTypeProcess(TypeProcess.FICHIER_COMPTA_RETOUR);
				ManagerDAO.save(tourCompensation);

				File[] listOfFiles = folderEntree.listFiles();
				if(listOfFiles==null || listOfFiles.length==0){

					System.out.println("Aucun fichier trouvé dans le répertoire d'entrée !!!");
					this.error=true;
					this.information=true;
					ExceptionHelper.showError("Aucun fichier trouvé dans le répertoire d'entrée !!!", this);

					List<TourCompensation>toursCompensation = ViewHelper.virementsRecManager.filterTourCompensation(new Date(), user.getLogin(), tour, typeProcess);
					if(toursCompensation!=null &&!toursCompensation.isEmpty()){
						tourCompensation = toursCompensation.get(0);
						ManagerDAO.delete(tourCompensation);
					}

					return;
				}


				/**Liste des fichiers dans le répertoire de sortie***
				File[] listFiles = folderDest.listFiles();
				if(listFiles!=null && listFiles.length>0){
					//Le répertoire de destination n'est pas vide; lancer l'archivage des fichiers contenus
					//archivage();  //Traitement 1
					fichiersDestination = true; 
				}



				if(listOfFiles!=null&&listOfFiles.length>0){

					List<TourCompensation>toursCompensation = ViewHelper.virementsRecManager.filterTourCompensation(new Date(), user.getLogin(), tour, typeProcess);
					if(toursCompensation!=null &&!toursCompensation.isEmpty()){
						tourCompensation = toursCompensation.get(0);
					}

					int j = 0;

					nomFichierDoublon = false;

					/**Parcours du répertoire de destination pour collecter les contenus**
					List<String> fileLines = new ArrayList<String>();
					for(int k=0; k<listFiles.length;k++){
						fileLines = readAndScanFile(Paths.get(repDestinationFicheComptaRetour + File.separator + listFiles[k].getName()));
						if(!fileLines.isEmpty())
							mapFichiersDestEtContenus.put(listFiles[k].getName(), fileLines);
					}

					while (j<3){ // Nous avons deux traitements à effectuer

						fichiersTraite = "";
						/**Parcours du répertoire d'entrée***
						for (int i = 0; i < listOfFiles.length; i++) {

							if (listOfFiles[i].isFile()) {

								if(j==2)
									nbrFichACopierComptaRetour++;

								System.out.println("File " + listOfFiles[i].getName());

								String fichierEntree = repEntreeFichCompteRetour + File.separator + listOfFiles[i].getName();

								String fichierDest = repDestinationFicheComptaRetour + File.separator + listOfFiles[i].getName();

								String fichierArchiveEntree = repArchiveEntreeFicheComptaRetour + File.separator + listOfFiles[i].getName();

								System.out.println("****fichierEntree*****" + fichierEntree);
								System.out.println("****fichierDest*****" + fichierDest);


								Path pathEntree = Paths.get(fichierEntree);

								Path pathDestination = Paths.get(fichierDest);

								Path pathArchiveEntree = Paths.get(fichierArchiveEntree);


								if(j==0){

									/**Ne pas oublier de sauvegarder les nbr des doublons et autre en BD**

									typeTraitement = TypeTraitement.DOUBLONS_NOM_FICHIER;
									if(traitementEtCopieFichiersComptaRetour(folderDest.listFiles(), listOfFiles[i].getName(), pathEntree, pathDestination, pathArchiveEntree, tourCompensation, typeTraitement,parametragesCaracteresSpeciauxs,fichiersDestination,mapFichiersDestEtContenus)){
										fichiersTraite = fichiersTraite + ";" + fichierEntree;
									}

								}else if(j==1){

									typeTraitement = TypeTraitement.DOUBLONS_ENTRE_FICHIER;
									if(traitementEtCopieFichiersComptaRetour(folderDest.listFiles(), listOfFiles[i].getName(), pathEntree, pathDestination, pathArchiveEntree, tourCompensation, typeTraitement, parametragesCaracteresSpeciauxs,fichiersDestination,mapFichiersDestEtContenus)){
										fichiersTraite = fichiersTraite + ";" + fichierEntree;
									}

								}
								else if(j==2){

									typeTraitement = TypeTraitement.DOUBLONS_DANS_FICHIER;
									if(traitementEtCopieFichiersComptaRetour(folderDest.listFiles(), listOfFiles[i].getName(), pathEntree, pathDestination, pathArchiveEntree, tourCompensation, typeTraitement, parametragesCaracteresSpeciauxs,fichiersDestination,mapFichiersDestEtContenus)){
										fichiersTraite = fichiersTraite + ";" + fichierEntree;
										for(int p=1; p<listFichierEtValEnDouble.size();p++){ //p==0 c'est le nom du fichier

											mapFichierEtValEnDoubleComptaRetourTraite1.put(listFichierEtValEnDouble.get(0), listFichierEtValEnDouble.subList(1, listFichierEtValEnDouble.size()));
										}
									}

								}


							}
						}

						j++;
					}


					/** Sauvegarder le traitement***
					saveTraitement(typeProcess, typeTraitement, fichiersTraite, tourCompensation);


					/***Affichage Rapport de traitement + envoi mail****
					HashMap<String, Object> param = new HashMap<String, Object>();

					param.put("uti", user.getLogin());
					param.put("nbrFichACopierComptaRetour", nbrFichACopierComptaRetour);      
					param.put("nbrFichCopieComptaRetour", nbrFichCopieComptaRetour);
					param.put("nbrDoublonsComptaRetour", nbrDoublonsComptaRetour);
					param.put("nbrFichContenantDoublonsComptaRetour", nbrFichContenantDoublonsComptaRetour);
					param.put("nbrValeursDeposeesComptaRetour", nbrValeursDeposeesComptaRetour);
					param.put("nbrValeursEnDoubleComptaRetour", nbrValeursEnDoubleComptaRetour);



					List<ValidateDoublonsInFichier> subs = new ArrayList<ValidateDoublonsInFichier>();
					ValidateDoublonsInFichier sub;
					String color="";
					String previousColor="";
					for(String key:mapFichierEtValEnDoubleComptaRetourTraite1.keySet()){

						sub = new ValidateDoublonsInFichier();
						sub.setFichier(key);

						String doublons="";
						for(String s: mapFichierEtValEnDoubleComptaRetourTraite1.get(key)){
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
						sub.setTypeProcess(TypeProcess.FICHIER_COMPTA_RETOUR);

						subs.add(sub);

						previousColor = color;
					}

					for(ValidateDoublonsInFichier v: subs){
						ManagerDAO.save(v);
					}

					FichiersSupprimeACauseDeValeursEnDoublonsDansAutreFichier  f = new FichiersSupprimeACauseDeValeursEnDoublonsDansAutreFichier();
					List<FichiersSupprimeACauseDeValeursEnDoublonsDansAutreFichier> fList = new ArrayList<FichiersSupprimeACauseDeValeursEnDoublonsDansAutreFichier>();
					for(int i=0; i<listFichierSupprimePourDoublonsEntreFichiers.size();i++){
						if(i%2 == 0){
							f.setFichierSupprime(listFichierSupprimePourDoublonsEntreFichiers.get(i));
							if(i<listFichierSupprimePourDoublonsEntreFichiers.size()-1)
								f.setFichierConserve(listFichierSupprimePourDoublonsEntreFichiers.get(i+1));
						}
						/*else{
							if(i<listFichierSupprimePourDoublonsEntreFichiers.size()-1)
								f.setFichierConserve(listFichierSupprimePourDoublonsEntreFichiers.get(i+1));
						}*
					}
					if(f.getFichierSupprime()!=null && !f.getFichierSupprime().isEmpty())fList.add(f);

					List<ValidateDoublonsInFichier> repports = new ArrayList<ValidateDoublonsInFichier>();

					ValidateDoublonsInFichier subReportDoublonsDansFichier = new ValidateDoublonsInFichier();
					subReportDoublonsDansFichier.setSubReportDoublonsDansFichiers(subs);
					subReportDoublonsDansFichier.setFichiersSupprimeACauseDeValeursEnDoublonsDansAutreFichiers(fList);

					repports.add(subReportDoublonsDansFichier);

					if(!subs.isEmpty()){
						param.put("libelleTabAnnexe", "Liste des doublons apparus en terme de contenus dans un même fichier:");
					}
					if(!fList.isEmpty()){
						param.put("libelleSupprime", "Liste des fichiers supprimés parce que leur contenus étaient contenus dans un autre fichier:");
					}

					/***Rapport***
					reportViewer = new ReportViewer(repports,"rapportCopieComptaRetour.jasper", param, "afb.statistique.title", this,"/views/repport/ReportCopieComptaRetour.xhtml");
					reportViewer.viewReport();


					/*********************Envoi mail rapport traitement******************
					List<String>mailsTo = new ArrayList<String>();
					List<ParamEmail> listEmails = new ArrayList<ParamEmail>();
					for(ParamEmail p :parametragesGenTeleCompense.getListEmails()){

						if(p.getValide().equals(Boolean.TRUE)){
							listEmails.add(p);
						}
					}

					if(listEmails!=null && !listEmails.isEmpty()){

						for(ParamEmail pe: listEmails){
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

					String title="Rapport traitement (copie) des fichiers de comptabilisation phase Retour, le " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()) + " à " + new SimpleDateFormat("HH:mm:ss").format(new Date());
					String msg = "Bonjour, " + "\t" + "\n" + "\t\t" + "Veuillez trouver ci-dessous le rapport des fichiers de comptabilisation de la phase Retour copiés ce jour : " + "\n\n";

					msg = msg + "Nombre de fichiers à copier: " + nbrFichACopierComptaRetour + "\n\n";
					msg = msg + "Nombre de fichers copiés dans le répertoire de destination: " + nbrFichCopieComptaRetour + "\n\n";
					msg = msg + "Nombre de fichiers en double détectés " + nbrDoublonsComptaRetour +"\n\n";
					msg = msg + "Nombre de fichiers contenant des doublons: " + nbrFichContenantDoublonsComptaRetour + "\n\n";
					msg = msg + "Nombre de valeurs en double détectés " + nbrValeursEnDoubleComptaRetour + "\n\n";
					msg = msg + "Nombre de valeurs déposées " + nbrValeursDeposeesComptaRetour + "\n\n";

					List<String> filesNames = new ArrayList<String>();
					List<String> filesPath = new ArrayList<String>();

					String fName = new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date());
					FileOutputStream fileOuputStream = new FileOutputStream(ViewHelper.getPiecesJointesDir() + "Rapport_Copie_Fichier_Compta_Retour_" + fName+".pdf");
					fileOuputStream.write(ViewHelper.virementsRecManager.getReportPDFBytes(ViewHelper.getReportsDir2().concat("rapportCopieComptaRetour.jasper"), param, repports));
					fileOuputStream.close();

					filesNames.add("Rapport_Copie_Fichier_Compta_Retour_" + fName+".pdf");
					filesPath.add(ViewHelper.getPiecesJointesDir() + "Rapport_Copie_Fichier_Compta_Retour_" + fName+".pdf");

					StatistiqueRapports stat = new StatistiqueRapports();
					stat.setRapport("Rapport_Copie_Fichier_Compta_Retour_" + fName+".pdf");
					stat.setTypeProcess(TypeProcess.FICHIER_COMPTA_RETOUR);
					ManagerDAO.save(stat);

					try {
						SendMail.sendMail(filesNames, filesPath, mailsTo, mailsCC, mailsBCC, title, msg, ip, email, pass, port);
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					/*******************************************
				}else{

					this.error=true;
					this.information = true;
					ExceptionHelper.showError("Aucun fichier trouvé dans le répertoire d'entrée !!!", this);

					return;
				}*/

			}


			msgFinTraitement = "Traitement Terminé. Veuillez lancer l'archivage";

			statutTraitement = true;

		}catch(Exception e){
			e.printStackTrace();
		}

	}

	public void doTraitement(String piecesJointesDir, String reportsDir, TypeProcess typeProcess, String repEntree,String repSortie, String repArchiveEntree, Integer aCopier, Integer Copier, Integer Doublons, Integer fichContenantDoublons,
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


			File folderDest = new File(repSortie);
			//Si le répertoire n'existe pas, il faut le créer
			if(!folderDest.exists()){
				if(folderDest.mkdirs()){
					System.out.println("*****Les répertoires parents sont crées avec succès!******");
				} else {
					System.out.println("******Echec de création des répertoires parents!*****");
				}
			}
			System.out.println("****folderDest*****" + folderDest);


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
			ManagerDAO.save(tourCompensation);


			File[] listOfFiles = folderEntree.listFiles();
			if(listOfFiles==null || listOfFiles.length==0){
				System.out.println("Aucun fichier trouvé dans le répertoire d'entrée !!!");
				/**this.error=true;
				this.information=true;
				ExceptionHelper.showError("Aucun fichier trouvé dans le répertoire d'entrée !!!", this);*/

				List<TourCompensation>toursCompensation = filterTourCompensation(new Date(), user.getLogin(), tour, typeProcess);
				if(toursCompensation!=null &&!toursCompensation.isEmpty()){
					tourCompensation = toursCompensation.get(0);
					ManagerDAO.delete(tourCompensation);
				}
				return;
			}


			/**Liste des fichiers dans le répertoire d'entrée***/
			File[] listFiles = folderDest.listFiles();
			if(listFiles!=null && listFiles.length>0){
				fichiersDestination = true; 
			}


			if(listOfFiles!=null&&listOfFiles.length>0){

				List<TourCompensation>toursCompensation = filterTourCompensation(new Date(), user.getLogin(), tour, typeProcess);
				if(toursCompensation!=null &&!toursCompensation.isEmpty()){
					tourCompensation = toursCompensation.get(0);
				}

				if(typeProcess.equals(TypeProcess.RAPATRIEMENT_IMG_ALLER) || typeProcess.equals(TypeProcess.RAPATRIEMENT_IMG_RETOUR) ){

					/*if(typeProcess.equals(TypeProcess.RAPATRIEMENT_IMG_ALLER)){
						nbrFichACopierImgAller = aCopier;
						nbrFichCopieImgAller = Copier;
						nbrDoublonsImgAller = Doublons;
					}else{
						nbrFichACopierImgRetour = aCopier;
						nbrFichCopieImgRetour = Copier;
						nbrDoublonsImgRetour = Doublons;
					}*/

					typeTraitement = TypeTraitement.DOUBLONS_NOM_FICHIER;

					/**Parcours du répertoire d'entrée***/
					for (int i = 0; i < listOfFiles.length; i++) {
						if (listOfFiles[i].isFile()) {

							nbrFichACopierImg++;

							System.out.println("File " + listOfFiles[i].getName());

							String fichierEntree = repEntree + File.separator + listOfFiles[i].getName();

							String fichierDest = repSortie + File.separator + listOfFiles[i].getName();

							String fichierArchiveEntree = repArchiveEntree + File.separator + listOfFiles[i].getName();

							System.out.println("****fichierEntree*****" + fichierEntree);

							System.out.println("****fichierDest*****" + fichierDest);

							System.out.println("****fichierArchiveEntree*****" + fichierArchiveEntree);

							Path pathEntree = Paths.get(fichierEntree);

							Path pathDestination = Paths.get(fichierDest);

							Path pathArchiveEntree = Paths.get(fichierArchiveEntree);

							if(traitementEtCopieFichiers(typeProcess, folderDest.listFiles(), listOfFiles[i].getName(), pathEntree, pathDestination, pathArchiveEntree, tourCompensation, fichiersDestination, aCopier)){
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
						FileOutputStream fileOuputStream = new FileOutputStream(piecesJointesDir + "Rapport_Copie_Img_Aller_" + fName+".pdf");
						fileOuputStream.write(ManagerDAO.getReportPDFBytes(reportsDir.concat("rapportCopieImgAller.jasper"), param, repports));
						fileOuputStream.close();

						filesNames.add("Rapport_Copie_Img_Aller_" + fName+".pdf");
						filesPath.add(piecesJointesDir + "Rapport_Copie_Img_Aller_" + fName+".pdf");

						StatistiqueRapports stat = new StatistiqueRapports();
						stat.setRapport("Rapport_Copie_Img_Aller_" + fName+".pdf");
						stat.setTypeProcess(TypeProcess.RAPATRIEMENT_IMG_ALLER);
						ManagerDAO.save(stat);

					}else{  //RETOUR

						/*System.out.println("***nbrFichACopierImgRetour***" + nbrFichACopierImgRetour);
						System.out.println("***nbrFichCopieImgRetour***" + nbrFichCopieImgRetour);
						System.out.println("***nbrDoublonsImgRetour***" + nbrDoublonsImgRetour);*/

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
						FileOutputStream fileOuputStream = new FileOutputStream(piecesJointesDir + "Rapport_Copie_Img_Retour_" + fName+".pdf");
						fileOuputStream.write(ManagerDAO.getReportPDFBytes(reportsDir.concat("rapportCopieImgRetour.jasper"), param, repports));
						fileOuputStream.close();

						filesNames.add("Rapport_Copie_Img_Retour_" + fName+".pdf");
						filesPath.add(piecesJointesDir + "Rapport_Copie_Img_Retour_" + fName+".pdf");

						StatistiqueRapports stat = new StatistiqueRapports();
						stat.setRapport("Rapport_Copie_Img_Retour_" + fName+".pdf");
						stat.setTypeProcess(TypeProcess.RAPATRIEMENT_IMG_RETOUR);
						ManagerDAO.save(stat);

					}


					/***Rapport***/
					/***reportViewer = new ReportViewer(repports,jasper, param, "afb.statistique.title", this,"/views/repport/" + reportFile);
					reportViewer.viewReport();*/


					/*********************Envoi mail rapport traitement******************/
					//SendMail.sendMail(filesNames, filesPath, mailsTo, mailsCC, mailsBCC, title, msg, ip, email, pass, port);
					try {
						List<ParamEmailAuto> parametres = ManagerDAO.filter(ParamEmailAuto.class, null, null, null, null, 0, -1);
						if(parametres!=null&&!parametres.isEmpty()){
							AfrilandSendMail.sendMail(filesNames, filesPath, mailsTo, mailsCC, title, msg, email, pass, email + "@afrilandfirstbank.com", "AFRILAND FIRST BANK", ip, String.valueOf(port));
						}
					} catch (Exception ex) {
						// TODO Auto-generated catch block
					}

					/*******************************************/

				}else if(typeProcess.equals(TypeProcess.FICHIER_COMPTA_ALLER) || typeProcess.equals(TypeProcess.FICHIER_COMPTA_RETOUR)){

					/**if(typeProcess.equals(TypeProcess.FICHIER_COMPTA_ALLER)){
						nbrFichACopierComptaAller = aCopier;
						nbrFichCopieComptaAller = Copier;
						nbrDoublonsComptaAller= Doublons;
						nbrFichContenantDoublonsComptaAller = fichContenantDoublons ;
						nbrValeursDeposeesComptaAller =  valeursEnDouble;
						nbrValeursEnDoubleComptaAller = valeursDeposees;
					}else{
						nbrFichACopierComptaRetour = aCopier;
						nbrFichCopieComptaRetour = Copier;
						nbrDoublonsComptaRetour = Doublons;
						nbrFichContenantDoublonsComptaRetour = fichContenantDoublons ;
						nbrValeursDeposeesComptaRetour =  valeursEnDouble;
						nbrValeursEnDoubleComptaRetour = valeursDeposees;
					}*/


					int j = 0;

					nomFichierDoublon = false;

					/**Parcours du répertoire d'entrée pour collecter les contenus**/
					List<String> fileLines1 = new ArrayList<String>();
					for(int k=0; k<listOfFiles.length;k++){
						fileLines1 = readAndScanFile(Paths.get(repEntree + File.separator + listOfFiles[k].getName()));
						if(!fileLines1.isEmpty())
							mapFichiersEntreeEtContenus.put(listOfFiles[k].getName(), fileLines1);
					}

					/**Parcours du répertoire de destination pour collecter les contenus**/
					List<String> fileLines = new ArrayList<String>();
					for(int k=0; k<listFiles.length;k++){
						fileLines = readAndScanFile(Paths.get(repSortie + File.separator + listFiles[k].getName()));
						if(!fileLines.isEmpty())
							mapFichiersDestEtContenus.put(listFiles[k].getName(), fileLines);
					}

					while (j<3){ // Nous avons trois traitements à effectuer

						fichiersTraite = "";

						/**Parcours du répertoire d'entrée***/
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

									/**Ne pas oublier de sauvegarder les nbr des doublons et autre en BD**/

									typeTraitement = TypeTraitement.DOUBLONS_NOM_FICHIER;
									if(traitementEtCopieFichiersCompta(folderDest.listFiles(), listOfFiles[i].getName(), pathEntree, pathDestination, pathArchiveEntree, tourCompensation, typeTraitement, parametragesCaracteresSpeciauxs,fichiersDestination,mapFichiersEntreeEtContenus,mapFichiersDestEtContenus, aCopier)){
										if(fichiersTraite.isEmpty())fichiersTraite = listOfFiles[i].getName();
										else
											fichiersTraite = fichiersTraite + ";" + listOfFiles[i].getName();
									}

								}else if(j==1){

									typeTraitement = TypeTraitement.DOUBLONS_ENTRE_FICHIER;
									if(traitementEtCopieFichiersCompta(folderDest.listFiles(), listOfFiles[i].getName(), pathEntree, pathDestination, pathArchiveEntree, tourCompensation, typeTraitement, parametragesCaracteresSpeciauxs, fichiersDestination,mapFichiersEntreeEtContenus,mapFichiersDestEtContenus, aCopier)){
										if(fichiersTraite.isEmpty())fichiersTraite = listOfFiles[i].getName();
										else
											fichiersTraite = fichiersTraite + ";" + listOfFiles[i].getName();
									}

								}
								else if(j==2){
									typeTraitement = TypeTraitement.DOUBLONS_DANS_FICHIER;
									if(traitementEtCopieFichiersCompta(folderDest.listFiles(), listOfFiles[i].getName(), pathEntree, pathDestination, pathArchiveEntree, tourCompensation, typeTraitement, parametragesCaracteresSpeciauxs, fichiersDestination,mapFichiersEntreeEtContenus,mapFichiersDestEtContenus, aCopier)){
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
					}

					/** Sauvegarder le traitement***/
					saveTraitement(typeProcess, typeTraitement, fichiersTraite, tourCompensation); 

					/******Tableau 1 du rapport***/

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
						ManagerDAO.save(v);
					}


					/******Tableau 2 du rapport***/

					FichiersSupprimeACauseDeValeursEnDoublonsDansAutreFichier  f = new FichiersSupprimeACauseDeValeursEnDoublonsDansAutreFichier();
					List<FichiersSupprimeACauseDeValeursEnDoublonsDansAutreFichier> fList = new ArrayList<FichiersSupprimeACauseDeValeursEnDoublonsDansAutreFichier>();
					for(int i=0; i<this.listFichierSupprimePourDoublonsEntreFichiers.size();i++){
						f = new FichiersSupprimeACauseDeValeursEnDoublonsDansAutreFichier();
						if(i%2 == 0){
							f.setFichierSupprime(this.listFichierSupprimePourDoublonsEntreFichiers.get(i));
							if(i<this.listFichierSupprimePourDoublonsEntreFichiers.size()-1)
								f.setFichierConserve(this.listFichierSupprimePourDoublonsEntreFichiers.get(i+1));

						}

						if(f.getFichierSupprime()!=null && !f.getFichierSupprime().isEmpty())fList.add(f);
						/*else{
							if(i<listFichierSupprimePourDoublonsEntreFichiers.size()-1)
								f.setFichierConserve(listFichierSupprimePourDoublonsEntreFichiers.get(i+1));
						}*/
					}




					/******Tableau 3 du rapport***/
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


					/***Affichage Rapport de traitement + envoi mail****/
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

						param.put("uti", user.getLogin());
						param.put("nbrFichACopierComptaAller", nbrFichACopierCompta);      
						param.put("nbrFichCopieComptaAller", nbrFichCopieCompta);
						param.put("nbrDoublonsComptaAller", nbrDoublonsCompta);
						param.put("nbrFichContenantDoublonsComptaAller", nbrFichContenantDoublonsCompta);
						param.put("nbrValeursDeposeesComptaAller", nbrValeursDeposeesCompta);
						param.put("nbrValeursEnDoubleComptaAller", nbrValeursEnDoubleCompta);

						title="Rapport traitement (copie) des fichiers de comptabilisation phase Aller, le " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()) + " à " + new SimpleDateFormat("HH:mm:ss").format(new Date());
						msg = "Bonjour, " + "\t" + "\n" + "\t\t" + "Veuillez trouver ci-dessous le rapport des fichiers de comptabilisation de la phase Aller copiés ce jour : " + "\n\n";

						msg = msg + "Nombre de fichiers à copier: " + nbrFichACopierCompta + "\n\n";
						msg = msg + "Nombre de fichers copiés dans le répertoire de destination: " + nbrFichCopieCompta + "\n\n";
						msg = msg + "Nombre de fichiers en double détectés " + nbrDoublonsCompta +"\n\n";
						msg = msg + "Nombre de fichiers contenant des doublons: " + nbrFichContenantDoublonsCompta + "\n\n";
						msg = msg + "Nombre de valeurs en double détectés " + nbrValeursEnDoubleCompta + "\n\n";
						msg = msg + "Nombre de valeurs déposées " + nbrValeursDeposeesCompta + "\n\n";


						filesNames = new ArrayList<String>();
						filesPath = new ArrayList<String>();

						String fName = new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date());
						FileOutputStream fileOuputStream = new FileOutputStream(piecesJointesDir + "Rapport_Copie_Fichier_Compta_Aller_" + fName+".pdf");
						fileOuputStream.write(ManagerDAO.getReportPDFBytes(reportsDir.concat("rapportCopieComptaAller.jasper"), param, repports));
						fileOuputStream.close();

						filesNames.add("Rapport_Copie_Fichier_Compta_Aller_" + fName+".pdf");
						filesPath.add(piecesJointesDir + "Rapport_Copie_Fichier_Compta_Aller_" + fName+".pdf");

						StatistiqueRapports stat = new StatistiqueRapports();
						stat.setRapport("Rapport_Copie_Fichier_Compta_Aller_" + fName+".pdf");
						stat.setTypeProcess(TypeProcess.FICHIER_COMPTA_ALLER);
						ManagerDAO.save(stat);

					}else{  //RETOUR

						param.put("uti", user.getLogin());
						param.put("nbrFichACopierComptaRetour", nbrFichACopierCompta);      
						param.put("nbrFichCopieComptaRetour", nbrFichCopieCompta);
						param.put("nbrDoublonsComptaRetour", nbrDoublonsCompta);
						param.put("nbrFichContenantDoublonsComptaRetour", nbrFichContenantDoublonsCompta);
						param.put("nbrValeursDeposeesComptaRetour", nbrValeursDeposeesCompta);
						param.put("nbrValeursEnDoubleComptaRetour", nbrValeursEnDoubleCompta);


						title="Rapport traitement (copie) des fichiers de comptabilisation phase Retour, le " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()) + " à " + new SimpleDateFormat("HH:mm:ss").format(new Date());
						msg = "Bonjour, " + "\t" + "\n" + "\t\t" + "Veuillez trouver ci-dessous le rapport des fichiers de comptabilisation de la phase Retour copiés ce jour : " + "\n\n";

						msg = msg + "Nombre de fichiers à copier: " + nbrFichACopierCompta + "\n\n";
						msg = msg + "Nombre de fichers copiés dans le répertoire de destination: " + nbrFichCopieCompta + "\n\n";
						msg = msg + "Nombre de fichiers en double détectés " + nbrDoublonsCompta +"\n\n";
						msg = msg + "Nombre de fichiers contenant des doublons: " + nbrFichContenantDoublonsCompta + "\n\n";
						msg = msg + "Nombre de valeurs en double détectés " + nbrValeursEnDoubleCompta + "\n\n";
						msg = msg + "Nombre de valeurs déposées " + nbrValeursDeposeesCompta + "\n\n";

						filesNames = new ArrayList<String>();
						filesPath = new ArrayList<String>();

						String fName = new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date());
						FileOutputStream fileOuputStream = new FileOutputStream(piecesJointesDir + "Rapport_Copie_Fichier_Compta_Retour_" + fName+".pdf");
						fileOuputStream.write(ManagerDAO.getReportPDFBytes(reportsDir.concat("rapportCopieComptaRetour.jasper"), param, repports));
						fileOuputStream.close();

						filesNames.add("Rapport_Copie_Fichier_Compta_Retour_" + fName+".pdf");
						filesPath.add(piecesJointesDir + "Rapport_Copie_Fichier_Compta_Retour_" + fName+".pdf");

						StatistiqueRapports stat = new StatistiqueRapports();
						stat.setRapport("Rapport_Copie_Fichier_Compta_Retour_" + fName+".pdf");
						stat.setTypeProcess(TypeProcess.FICHIER_COMPTA_RETOUR);
						ManagerDAO.save(stat);

					}


					/***Rapport***/
					/***reportViewer = new ReportViewer(repports,jasper, param, "afb.statistique.title", this,"/views/repport/" + reportFile);
					reportViewer.viewReport();*/


					/*********************Envoi mail rapport traitement******************/
					//SendMail.sendMail(filesNames, filesPath, mailsTo, mailsCC, mailsBCC, title, msg, ip, email, pass, port);
					try {
						List<ParamEmailAuto> parametres = ManagerDAO.filter(ParamEmailAuto.class, null, null, null, null, 0, -1);
						if(parametres!=null&&!parametres.isEmpty()){
							AfrilandSendMail.sendMail(filesNames, filesPath, mailsTo, mailsCC, title, msg, email, pass, email + "@afrilandfirstbank.com", "AFRILAND FIRST BANK", ip, String.valueOf(port));
						}
					} catch (Exception ex) {
						// TODO Auto-generated catch block
					}

					/*******************************************/

				}

			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	//Copier les fichiers d'un répertoire vers un autre
	public boolean traitementEtCopieFichiers(TypeProcess typeProcess,File[] listFiles, String fichierTraite, Path pathEntree, Path pathDestination, Path pathArchiveEntree, TourCompensation tourCompensation, Boolean fichiersDestination, Integer aCopier){
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
						ManagerDAO.save(traitementTourCompense);
						doublonFound=true;
					}

				}

				/******Arriver ici (y a pas eu de doublons) on copie*****/
				if(!doublonFound){

					Path p = Files.copy(pathEntree, pathArchiveEntree, StandardCopyOption.REPLACE_EXISTING);
					System.out.println("***Files copied to ***" + p.toString());

					p = Files.move(pathEntree, pathDestination, StandardCopyOption.REPLACE_EXISTING);
					System.out.println("***Files copied to ***" + p.toString());
					nbrFichCopieImg++;

					traitementTourCompense.setSortTraitement(SortTraitement.SUCCES);
					ManagerDAO.save(traitementTourCompense);
				}


			}else{

				Path p = Files.copy(pathEntree, pathArchiveEntree, StandardCopyOption.REPLACE_EXISTING);
				System.out.println("***Files copied to ***" + p.toString());

				p = Files.move(pathEntree, pathDestination, StandardCopyOption.REPLACE_EXISTING);
				System.out.println("***Files copied to ***" + p.toString());
				nbrFichCopieImg++;

				traitementTourCompense.setSortTraitement(SortTraitement.SUCCES);
				ManagerDAO.save(traitementTourCompense);
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

	public boolean traitementEtCopieFichiersCompta(File[] listFiles, String fichierTraite, Path pathEntree, Path pathDestination, Path pathArchiveEntree,  TourCompensation tourCompensation, TypeTraitement typeTraitement, List<ParametragesCaracteresSpeciaux>parametragesCaracteresSpeciauxs, Boolean fichiersDestination, Map<String, List<String>> mapFichiersEntreeEtContenus, Map<String, List<String>> mapFichiersDestEtContenus, Integer aCopier  ){
		try {

			/**Integer copier = 0;
				Integer doublons = 0;
				Integer fichContenantDoublons = 0;
				Integer valeursEnDouble = 0;
				Integer valeursDeposees = 0;**/

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
							System.out.println("***Doublons trouvé******" + f.getName());
							nomFichierDoublon = true;
							nbrDoublonsCompta++;
							listFileNamesWithDoublons.add(newFileName);
							traitementTourCompense.setSortTraitement(SortTraitement.ECHEC);
							ManagerDAO.save(traitementTourCompense);
						}
					}

					if(listFileNamesWithDoublons.isEmpty()){
						traitementTourCompense.setSortTraitement(SortTraitement.SUCCES);
						ManagerDAO.save(traitementTourCompense);
					}
				}else{
					/**On va copier à la deuxième itération j=1*/
					traitementTourCompense.setSortTraitement(SortTraitement.SUCCES);
					ManagerDAO.save(traitementTourCompense);
				}

			}
			else if(typeTraitement.equals(TypeTraitement.DOUBLONS_ENTRE_FICHIER)){

				List<String> fileLines = new ArrayList<String>();
				List<String> fileLinesFormatted = new ArrayList<String>();

				int count1=0;
				int count2=0;
				boolean doublonsEntreFichierDansEntree=false;
				for(String key:mapFichiersEntreeEtContenus.keySet()){

					if(!key.equals(fichierTraite)){ //Pour ne pas comparer un fichier avec lui-même

						if(pathEntree!=null)
							fileLines = readAndScanFile(pathEntree);
						for(String s: fileLines){
							fileLinesFormatted.add(s.trim());
						}
						if(!fileLinesFormatted.isEmpty()){

							List<String> listContent = mapFichiersEntreeEtContenus.get(key);
							List<String> listContentFormatted = new ArrayList<String>();
							for(String m: listContent){
								listContentFormatted.add(m.trim());
							}
							//Si le contenu du fichier du repertoire entrée est contenu dans le fichier parcouru du répertoire de destination
							if(listContentFormatted.containsAll(fileLinesFormatted) ){

								count2++;

								listFichierContenantDoublonsEntreFichiers.add(fichierTraite);
								System.out.println(" -----------listFichierContenantDoublonsEntreFichiers.size------*******************-----------" + listFichierContenantDoublonsEntreFichiers.size());
								if(!listFichierContenantDoublonsEntreFichiers.isEmpty())System.out.println(" -----------listFichierContenantDoublonsEntreFichiers.get(0)------*******************-----------" + listFichierContenantDoublonsEntreFichiers.get(0));

								System.out.println("************Le fichier xxxxxxxxxx******" + fichierTraite + " est ajoutée dans listFichierContenantDoublonsEntreFichiers "); 

								listFichierSupprimePourDoublonsEntreFichiers.add(fichierTraite); //mauvais fichier
								listFichierSupprimePourDoublonsEntreFichiers.add(key); //le bon fichier pour garder la trace dans les rapports

								doublonsEntreFichierDansEntree = true;
							}
						}
					}

				}
				fileLines.clear(); fileLinesFormatted.clear();
				System.out.println("*****mapFichiersDestEtContenus size*****" + mapFichiersDestEtContenus.size());
				for(String key:mapFichiersDestEtContenus.keySet()){

					if(pathEntree!=null)
						fileLines = readAndScanFile(pathEntree);
					for(String s: fileLines){
						fileLinesFormatted.add(s.trim());
					}
					if(!fileLinesFormatted.isEmpty()){

						List<String> listContent = mapFichiersDestEtContenus.get(key);
						List<String> listContentFormatted = new ArrayList<String>();
						for(String m: listContent){
							listContentFormatted.add(m.trim());
						}
						//Si le contenu du fichier du repertoire entrée est contenu dans le fichier parcouru du répertoire de destination
						if(listContentFormatted.containsAll(fileLinesFormatted) ){

							count1++;

							listFichierContenantDoublonsEntreFichiers.add(fichierTraite);
							System.out.println(" -----------listFichierContenantDoublonsEntreFichiers.size------------------" + listFichierContenantDoublonsEntreFichiers.size());
							if(!listFichierContenantDoublonsEntreFichiers.isEmpty())System.out.println(" -----------listFichierContenantDoublonsEntreFichiers.get(0)------------------" + listFichierContenantDoublonsEntreFichiers.get(0));

							System.out.println("************Le fichier******" + fichierTraite + " est ajoutée dans listFichierContenantDoublonsEntreFichiers ");

							listFichierSupprimePourDoublonsEntreFichiers.add(fichierTraite); //mauvais fichier
							listFichierSupprimePourDoublonsEntreFichiers.add(key); //le bon fichier pour garder la trace dans les rapports
						}
					}

				}
				fileLines.clear(); fileLinesFormatted.clear();

				System.out.println(" -----------listFichierContenantDoublonsEntreFichiers.size------xxxxxxxxxx-----------" + listFichierContenantDoublonsEntreFichiers.size());
				if(!listFichierContenantDoublonsEntreFichiers.isEmpty())System.out.println(" -----------listFichierContenantDoublonsEntreFichiers.get(0)------xxxxxxxxxx-----------" + listFichierContenantDoublonsEntreFichiers.get(0));


				if(count1>0 || count2>0){

					traitementTourCompense.setSortTraitement(SortTraitement.ECHEC);
					ManagerDAO.save(traitementTourCompense);

				}else{

					traitementTourCompense.setSortTraitement(SortTraitement.SUCCES);
					ManagerDAO.save(traitementTourCompense);
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

				//Si la liste des clés est vide on la parcours pour vérifier qu'il n'y a pas deux clés/lignes qui se ressemble, dans quel cas nous constatons un doublon
				if(!listKeys.isEmpty()){

					for(int k=0; k<listKeys.size();k++){
						l=k+1;
						while(l<listKeys.size()){
							if(listKeys.get(k).equals(listKeys.get(l))){
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
					ManagerDAO.save(traitementTourCompense);

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
						ManagerDAO.save(traitementTourCompense);

					}

					//listFichierSupprimePourDoublonsEntreFichiers.contains(newFileName) || listFileNamesWithDoublons.contains(newFileName)
					if(listFichierContenantDoublonsEntreFichiers.contains(newFileName) || listFileNamesWithDoublons.contains(newFileName)){

						for(String s: listFichierContenantDoublonsEntreFichiers){

							if(!listFileNamesWithDoublons.contains(s)){

								String file = pathEntree.getParent() + File.separator + s;

								System.out.println("***File contenant Doublons Entre Fichier***" +file);


								/*File f = new File(file);
									f.delete(); //on supprime le fichier du répertoire d'entrée*/

								Path p;
								if(pathEntree.getFileName().toString().equals(s)){
									p = Files.move(pathEntree, pathArchiveEntree, StandardCopyOption.REPLACE_EXISTING);
									System.out.println("***Files copied to ***" + p.toString());
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
										p = Files.move(pathEntree, pathArchiveEntree, StandardCopyOption.REPLACE_EXISTING);
										System.out.println("***Files copied to ***" + p.toString());
									}
								}

								traitementTourCompense.setFichiersSupprime(s);
								ManagerDAO.update(traitementTourCompense);

							}
						}

						for(String t:listFileNamesWithDoublons){
							if(pathEntree.getFileName().toString().equals(t)){
								Path p = Files.move(pathEntree, pathArchiveEntree, StandardCopyOption.REPLACE_EXISTING);
								System.out.println("***Files copied to ***" + p.toString());
							}
						}

					}
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

	public void saveTraitement(TypeProcess typeProcess, TypeTraitement typeTraitement, String fichiersTraite, TourCompensation tourCompensation){

		if(typeProcess.equals(TypeProcess.RAPATRIEMENT_IMG_ALLER)){

			Date dateTraite = new Date();

			RapatriementImagesAller rapatriementImagesAller = new RapatriementImagesAller();
			rapatriementImagesAller.setDateTraitement(dateTraite);
			rapatriementImagesAller.setUtiTraitement(user.getLogin());
			ManagerDAO.save(rapatriementImagesAller);



			List<RapatriementImagesAller> rapatriementImagesAllers = filterRapatriementImageAller(dateTraite, user.getLogin());
			rapatriementImagesAller = rapatriementImagesAllers.get(0);
			if(rapatriementImagesAllers!=null&&!rapatriementImagesAllers.isEmpty()){

				tourCompensation.setHeure(new SimpleDateFormat("HH:mm:ss").format(new Date()));
				tourCompensation.setFichiersTraite(fichiersTraite);
				//tourCompensation.setRapatriementImagesAller(rapatriementImagesAller);
				tourCompensation.setNbrFichiersACopiers(nbrFichACopierImg);
				tourCompensation.setNbrFichiersCopies(nbrFichCopieImg);
				tourCompensation.setNbrDoublons(nbrDoublonsImg);

				ManagerDAO.update(tourCompensation);

			}

		}


		if(typeProcess.equals(TypeProcess.RAPATRIEMENT_IMG_RETOUR)){

			Date dateTraite = new Date();

			RapatriementImagesRetour rapatriementImagesRetour = new RapatriementImagesRetour();
			rapatriementImagesRetour.setDateTraitement(dateTraite);
			rapatriementImagesRetour.setUtiTraitement(user.getLogin());

			ManagerDAO.save(rapatriementImagesRetour);


			List<RapatriementImagesRetour> rapatriementImagesRetours = filterRapatriementImageRetour(dateTraite, user.getLogin());
			rapatriementImagesRetour = rapatriementImagesRetours.get(0);
			if(rapatriementImagesRetours!=null&&!rapatriementImagesRetours.isEmpty()){

				tourCompensation.setHeure(new SimpleDateFormat("HH:mm:ss").format(new Date()));
				tourCompensation.setFichiersTraite(fichiersTraite);
				//tourCompensation.setRapatriementImagesRetour(rapatriementImagesRetour);
				tourCompensation.setNbrFichiersACopiers(nbrFichACopierImg);
				tourCompensation.setNbrFichiersCopies(nbrFichCopieImg);
				tourCompensation.setNbrDoublons(nbrDoublonsImg);

				ManagerDAO.update(tourCompensation);
			}

		}



		if(typeProcess.equals(TypeProcess.FICHIER_COMPTA_ALLER)){

			Date dateTraite = new Date();

			FichiersComptabilisationAller fichiersComptaAller = new FichiersComptabilisationAller();
			fichiersComptaAller.setDateTraitement(dateTraite);
			fichiersComptaAller.setUtiTraitement(user.getLogin());
			ManagerDAO.save(fichiersComptaAller);


			List<FichiersComptabilisationAller> fichiersComptaAllers = filterFichierComptaAller(dateTraite, user.getLogin());
			fichiersComptaAller = fichiersComptaAllers.get(0);
			if(fichiersComptaAllers!=null&&!fichiersComptaAllers.isEmpty()){

				tourCompensation.setHeure(new SimpleDateFormat("HH:mm:ss").format(new Date()));
				tourCompensation.setFichiersTraite(fichiersTraite);
				//tourCompensation.setFichiersComptabilisationAller(fichiersComptaAller);
				tourCompensation.setNbrFichiersACopiers(nbrFichACopierCompta);
				tourCompensation.setNbrFichiersCopies(nbrFichCopieCompta);
				tourCompensation.setNbrDoublons(nbrDoublonsCompta);
				tourCompensation.setNbrFichContenantDoublons(nbrFichContenantDoublonsCompta);
				tourCompensation.setNbrValeursDeposees(nbrValeursDeposeesCompta);
				tourCompensation.setNbrValeursEnDouble(nbrValeursEnDoubleCompta);


				ManagerDAO.update(tourCompensation);

			}

		}


		if(typeProcess.equals(TypeProcess.FICHIER_COMPTA_RETOUR)){

			Date dateTraite = new Date();

			FichiersComptabilisationRetour fichiersComptaRetour = new FichiersComptabilisationRetour();
			fichiersComptaRetour.setDateTraitement(dateTraite);
			fichiersComptaRetour.setUtiTraitement(user.getLogin());
			ManagerDAO.save(fichiersComptaRetour);


			List<FichiersComptabilisationRetour> fichiersComptaRetours = filterFichierComptaRetour(dateTraite, user.getLogin());
			fichiersComptaRetour = fichiersComptaRetours.get(0);
			if(fichiersComptaRetours!=null&&!fichiersComptaRetours.isEmpty()){

				tourCompensation.setHeure(new SimpleDateFormat("HH:mm:ss").format(new Date()));
				tourCompensation.setFichiersTraite(fichiersTraite);
				//tourCompensation.setFichiersComptabilisationRetour(fichiersComptaRetour);
				tourCompensation.setNbrFichiersACopiers(nbrFichACopierCompta);
				tourCompensation.setNbrFichiersCopies(nbrFichCopieCompta);
				tourCompensation.setNbrDoublons(nbrDoublonsCompta);
				tourCompensation.setNbrFichContenantDoublons(nbrFichContenantDoublonsCompta);
				tourCompensation.setNbrValeursDeposees(nbrValeursDeposeesCompta);
				tourCompensation.setNbrValeursEnDouble(nbrValeursEnDoubleCompta);

				ManagerDAO.update(tourCompensation);

			}

		}

	}

	public void traitementInDB(){

		ManagerDAO.traitementInDB();
	}

	public List<String> recuperationCompteImpots(String etabr, String guibr, String age){

		System.out.println("----In recuperationCompteImpots----");

		List<String> list = new ArrayList<String>();

		openDELTAConnection();

		if(conDELTA==null){System.err.println("----ConDelta est vide----");return null;}

		try{

			Statement st = null;
			ResultSet rs = null;	

			String sql = "SELECT * from bkbqe where etab = '"+etabr+"' and guib = '"+guibr+"' and age = '"+age+"' "; //compte existant et ouvert
			System.out.println("*********sql *******" + sql);
			st = conDELTA.createStatement();
			rs = st.executeQuery(sql);

			String etabr1 = "";
			String guibr1="";
			String nom1 = "";
			String domi1 = "";

			while(rs.next()){

				etabr1 = (rs.getString("etab")!=null)?rs.getString("etab").trim(): " ";
				guibr1 = (rs.getString("guib")!=null)?rs.getString("guib").trim(): " ";
				nom1 = (rs.getString("nom")!=null)?rs.getString("nom").trim(): " ";
				domi1 = (rs.getString("domi")!=null)?rs.getString("domi").trim(): " ";

			}
			if(rs!=null)rs.close();

			if(!etabr.isEmpty()&&!guibr.isEmpty()&&!nom1.isEmpty()&&!domi1.isEmpty()){
				list.add(etabr1);
				list.add(guibr1);
				list.add(nom1);
				list.add(domi1);
			}

			System.out.println("****list size****" + list.size());


		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			try {

				closeDELTAConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return list;
	}


	/**
	 * Cherche l'opération en réservation venue de la plateforme OTP-DGI et intégrée dans notre SI par ce module
	 * @return success or failure
	 */
	@Override
	public List<TraitementImpots> findOpeReserveeOTP_DGI(String ope, String uti, String ncp1, Date dsai){

		openDELTAConnection();

		if(conDELTA==null){
			System.err.println("----ConDelta est vide----");
			return null;
		}
		
		String dateSaisie = new SimpleDateFormat("yyyy-MM-dd").format(dsai); 

		TraitementImpots t = new TraitementImpots();
		List<TraitementImpots> listTrtImpots = new ArrayList<TraitementImpots>();

		try{

			//Statement st = null;
			ResultSet rs = null;			

			String sql = "select * from bkeve where ope=? and ncp1=? and uti=? and dsai = '"+dateSaisie+"'   ";

			PreparedStatement stmt=conDELTA.prepareStatement(sql);  
			stmt.setString(1,ope.trim()); 
			stmt.setString(2,ncp1.trim()); 
			stmt.setString(3,uti.trim()); 
			//stmt.setDate(4,dsai); 
			
			System.out.println(" ope: "+ ope + " ncp1: "+ncp1 + " uti: " + uti + " dsai: " + dateSaisie);

			rs = stmt.executeQuery();

			//int i=stmt.executeUpdate();  
			//System.out.println(i+" opérations insér inserted");  


			while(rs.next()){
				
				System.out.println("------In rs.next()------");
				
				t = new TraitementImpots();
				
				t.setOpe(rs.getString("ope")!=null?rs.getString("ope"): "");
				t.setNcp1(rs.getString("ncp1")!=null?rs.getString("ncp1"): "");
				t.setUti(rs.getString("uti")!=null?rs.getString("uti"): "");
				t.setDsai(rs.getDate("dsai")!=null?rs.getDate("dsai"): new Date());
				t.setMon1(rs.getDouble("mon1")!=0d?rs.getDouble("mon1"):0d);
				
				listTrtImpots.add(t);
			}

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {

				closeDELTAConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return listTrtImpots;
	}
	
	public boolean updateOpeReserveeOTP_DGI(String ope, String uti, String ncp1, Date dsai, String utif){
		
		openDELTAConnection();

		if(conDELTA==null){
			System.err.println("----ConDelta est vide----");
			return false;
		}
		
		String dateSaisie = new SimpleDateFormat("yyyy-MM-dd").format(dsai); 

		
		try{
			
			String sql = "update bkeve set dco=today, dou=today, dvab=today,dret=today, dsai=today, dva1=(today-1), eta='FO', etap='AT', utf='"+utif+"', uta='AUTO' " +
                          "where ope=? and ncp1=? and uti=? and dsai = '"+dateSaisie+"' ";

			PreparedStatement stmt=conDELTA.prepareStatement(sql);  
			stmt.setString(1,ope); 
			stmt.setString(2,ncp1); 
			stmt.setString(3,uti); 
			
			int i=stmt.executeUpdate();  
			System.out.println(i+" opérations mis à jour");  
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {

				closeDELTAConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return true;
	}

	@Override
	public List<String> recuperationsValCB(String uti, String cli, String age, String ncp, String clc, String dev, Date dsai){

		System.err.println("----In recuperationsValCB----");

		List<String> list = new ArrayList<String>();

		openDELTAConnection();

		if(conDELTA==null){System.err.println("----ConDelta est vide----");return null;}

		try{

			Statement st = null;
			ResultSet rs = null;

			String utiCB="";
			Double solde=0.0;
			String nomrest="";
			String ges="";
			String dva1S="";
			
			//String testNcpExist = "select * from bkcom a where a.ncp = '"+ncp+"' "; //compte existant et ouvert  //and a.cfe='N' and a.ife='N'
		    
            Account compte = getInfosDuCompte(age + "-" + ncp + "-" + clc);
			
			boolean found = false;
			
			if(compte != null){
				
				if(!compte.isCfe() || !compte.isIfe()){
					list.add("COMPTE FERME OU EN INSTANCE DE FERMETURE");
					return list;
				}else{
					found = true;
				}
				
			}
			
			if(found == false){
				list.add("NCP NOT FOUND");
				return list;
			}

			//S'assurer que le user traitant dans Portal a le même code user que dans Amplitude en récupérant ce dernier
			//String sql = "select cuti from evuti where cuti = '"+uti+"'";
			boolean isUser = isUserDelta(uti);

			//rs = st.executeQuery(sql);

			utiCB = isUser ? uti : " ";
			
			//if(uti.trim().equals(utiCB.trim()))
			list.add(utiCB);
			//else
			//utiCB="";
			//if(rs!=null)rs.close();

			//Récupérer le solde indicatif du compte du client incluant le découvert
			/******MAC******sql = "select a.sin-nvl(a.minds,0)+nvl(maut,0) as solde,cha from bkcom a left join bkautc b on a.age=b.age and a.dev=b.dev and a.ncp=b.ncp and eta in ('VA','VF','FO') and "
					+ "today between b.debut and b.fin where a.cfe='N' and a.ife='N' and a.age= '"+age+"' and a.clc= '"+clc+"' and a.ncp= '"+ncp+"' and a.dev= '"+dev+"' ;"; 
			 */


			/********sql = "select a.sin from bkcom a where a.age= '"+age+"' and a.clc= '"+clc+"' and a.ncp= '"+ncp+"' and a.dev= '"+dev+"'";*****/
			/**boolean executed = false;
			try{
				String preSQL = "select * from bkautc where eta in ('VA','VF','FO') and today between debut and fin into temp tmpbkautc; ";
				//rs = 
				st.executeQuery(preSQL);
				while (rs.next()){
					executed=true;
				}
				if(rs!=null)rs.close();
			}catch(Exception e){
				System.out.println("*****In Catch******");
				e.printStackTrace();
				executed = false;
			}*/


//			sql =  "select a.age,a.dev,a.ncp,a.clc,a.sin-nvl(a.minds,0)+nvl(maut,0) as solde " +  // //--2456
//					" from bkcom a left join (select * from bkautc where eta in ('VA','VF','FO') and today between debut and fin) b on a.age=b.age and a.dev=b.dev and a.ncp=b.ncp " +
//					" and eta in ('VA','VF','FO') and today between b.debut and b.fin " +
//					" where a.cfe='N' and a.ife='N' and a.age= '"+age+"' and a.clc= '"+clc+"' and a.ncp= '"+ncp+"' and a.dev= '"+dev+"';";
//			rs = st.executeQuery(sql);

			
			solde = compte != null ? compte.getSin() : 0.0; //sin
			System.out.println("********SOLDE*****" + solde);
			
			list.add(String.valueOf(solde));
			
			//if(rs!=null)rs.close();

			//Récupérer le nom du client et le code de son gestionnaire
//			sql = "select distinct a.cli, a.nomrest, N2.lib1, a.ges from  bkcli a, bkcom b, " +
//					"outer bknom N2 where a.cli = b.cli and b.cfe='N' and b.ife='N' and N2.ctab = '035' and N2.cacc = a.ges and a.cli = '"+cli+"'";
//			rs = st.executeQuery(sql);
			
			Client client = customerInfos(cli);

		    nomrest = client != null ? client.getCustomerName() : " ";
		    ges = client != null ? client.getCodeGes() : " ";
			
			list.add(nomrest);
			list.add(ges);
			
			//Récupérer la date de valeur du compte 1 et s'assurer que ce n'est ni un jour du weekend ni un jour férier

			//boolean koFerier = false;
			//boolean koWeekend = false;
			//while(!koFerier || !koWeekend){
            
			
			System.out.println("****in while(!koFerier || !koWeekend) ");
			String ssDco = DateUtil.format(dsai, DateUtil.DATE_MINUS_FORMAT_SINGLE);
			Date ddva1S = getDvaDebit(ssDco, ncp.substring(7, 10));
			dva1S = new SimpleDateFormat("yyyy-MM-dd").format(ddva1S);
			
//			Date dva1; //= dsai;
//			/*****WEEKEND****/
//			Calendar cal = Calendar.getInstance();
//			if(dsai!=null)cal.setTime(dsai);
//
//			System.out.println("****Weekend****** ");
//			if((cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY)){
//				dva1 = DateUtils.addDays(dsai, -1);
//				cal.setTime(dva1);
//			}else if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
//				dva1 = DateUtils.addDays(dsai, -2);
//				cal.setTime(dva1);
//			}else if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY){
//				dva1 = DateUtils.addDays(dsai, -3);
//				cal.setTime(dva1);
//			}else{
//				dva1 = DateUtils.addDays(dsai, -1);
//				cal.setTime(dva1);
//			}
//			if(dva1!=null) dva1S = new SimpleDateFormat("yyyy-MM-dd").format(dva1);
//
//
//			/***FERIER****/
//			System.out.println("****Ferriers****");
//			sql = "select jourfer from bkfer ";  //where jourfer = '"+dva1S+"'";
//			rs = st.executeQuery(sql);
//			Date dva;
//			List<String> listDFer = new ArrayList<String>();
//			while(rs.next()){ // si ce le j-i est férier
//				dva =  (rs.getDate("jourfer")!=null)?rs.getDate("jourfer"): null;
//				//i++;
//				dva1S = new SimpleDateFormat("yyyy-MM-dd").format(dva);
//				listDFer.add(dva1S);
//			}
//
//			dva1S = new SimpleDateFormat("yyyy-MM-dd").format(dva1);
//			while(listDFer.contains(dva1S)){
//				Calendar cal2 = Calendar.getInstance();
//				if(dva1!=null)cal2.setTime(dva1);
//				if(cal2.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY){
//					dva1 = DateUtils.addDays(dva1, -3);
//					dva1S = new SimpleDateFormat("yyyy-MM-dd").format(dva1);
//				}else{
//					dva1 = DateUtils.addDays(dva1, -1);
//					dva1S = new SimpleDateFormat("yyyy-MM-dd").format(dva1);
//				}
//				//dva1 = DateUtils.addDays(dva1, -i);
//				//dva1S = new SimpleDateFormat("yyyy-MM-dd").format(dva1);
//			}
//			//if(dva1!=null)dva1S = new SimpleDateFormat("yyyy-MM-dd").format(dva1);
//			//}
//
//			//}

			list.add(dva1S);

//			if(rs!=null)rs.close();
//
//			if(st!=null)
//				st.close();

		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			try {

				closeDELTAConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		return list;

	}

	@Override
	public List<String> recuperationsListeDesaccords(String age, String cli, String ncp){
		System.err.println("----In recuperationsValCB----");

		List<String> list = new ArrayList<String>();

		try{
			Account account = getInfosDuCompte(age + "-" + ncp);
			
			List<StoppageAccount> listStoppages;
			
			
			if(account != null) {
				String desaccord = "";
				listStoppages = account.getStoppages();
				
				if(listStoppages != null) {
					for (StoppageAccount sto : listStoppages) {
						
						if("V".equals(sto.getStatus()) && (sto.getDesignation().startsWith("DESSA") || sto.getDesignation().startsWith("DESA"))) {
							
							desaccord = sto.getDesignation();
							list.add(desaccord);
							
						}
						
					}
				}
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}

		return list;

	}


	@Override
	public List<String> recuperationsOppositionDebit(String age, String cli, String ncp){

		 System.err.println("----In recuperationsValCB----");
	     Account compte = getInfosDuCompte(age + "-" + ncp);
		
	     List<String> list = new ArrayList<String>();

		openDELTAConnection();

		if(conDELTA==null){System.err.println("----ConDelta est vide----");return null;}

		try{

//			Statement st = null;
//			ResultSet rs = null;
//
//			//String desaccord = "";
//			String oppRequete = "";
//
//			/********LISTE DES DESACCRODS*************/
//			/**String sql =  "select a.age,a.ncp,a.dev,a.opp code_opp,b.lib1 libe_desa,lib2 desa,a.dou,a.ddeb,a.dfin,a.eta,a.uti,motifo " +
//					" from bkoppcom a,bknom b "+
//					"where b.ctab='070' and b.cacc=a.opp and a.eta='V'  and a.ncp='"+ncp+"'  "  +           // ----and a.ncp='00060671051' 
//					"union "+
//					"select ' ',a.cli,' ',a.opp code_opp,b.lib1 libe_desa,lib2 desa,a.dou,a.ddeb,a.dfin,a.eta,a.uti,motifo " +
//					"from bkoppcli a,bknom b "+
//					"where b.ctab='070' and b.cacc=a.opp and a.eta='V'  and a.cli = '"+cli+"' ";    */             // -----and a.cli='0000031' 
//			String dateOfToday = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  //valeur par défaut
//
//			String sql = "select opp from bkoppcom where age = '"+age+"' and ncp='"+ncp+"' and eta='V' and (dfin is null or dfin>='"+dateOfToday+"')"; //opposition finissant après aujourd'hui
//			
			
			if(isCompteEnOpposition(compte, "01")) {
				list.add("01");
			}
			
//			st = conDELTA.createStatement();
//			rs = st.executeQuery(sql);
//
//			while(rs.next()){
//
//				oppRequete = (rs.getString("opp")!=null)?rs.getString("opp").trim(): " ";  //code opposition
//				//desaccord = (rs.getString("desa")!=null)?rs.getString("desa").trim(): " "; 
//
//				///desaccord = (rs.getString("libe_desa")!=null)?rs.getString("libe_desa").trim(): " ";
//				///list.add(desaccord);
//				if(oppRequete.equals("01")){
//					list.add(oppRequete);
//				}
//			}
//
//			if(rs!=null)rs.close();
//
//			if(st!=null)
//				st.close();


		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			try {

				closeDELTAConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		return list;

	}



	public void insertIntoBkeve(List<TraitementImpots> listTraitementImpots){

		try{

			bkeve eve = null;

			for(TraitementImpots t: listTraitementImpots){

				eve = new bkeve();
				PropertyUtils.copyProperties(eve, t);
				
				eve = registerEventToCoreBanking(eve);
				
				logger.info("Eve log OPE: " + eve.getOpe());

				// Enregistrement de l'evenement dans Amplitude
//				executeUpdateSystemQuery(dsCBS, t.getSaveQuery(), t.getQueryValues());

				// MAJ du solde indicatif du compte debiteur
//				executeUpdateSystemQuery(dsCBS, HelperQuerry.getDefaultCBSQueries().get(4).getQuery(), new Object[]{ t.getMon1(), t.getAge(), t.getNcp1(), t.getClc1() } );

				// MAJ du dernier numero d'evenement utilise pour le type operation
//				executeUpdateSystemQuery(dsCBS, HelperQuerry.getDefaultCBSQueries().get(3).getQuery(), new Object[]{ Long.valueOf(t.getEve()), t.getOpe() });
				System.out.println("****Eve Num****" + t.getEve());
				
				if (eve != null) {
					t.setValide(Boolean.TRUE);
				}
					
			}

			for(TraitementImpots t: listTraitementImpots){
				ManagerDAO.update(t);
			}

		}
		catch(Exception e){
			e.printStackTrace();
		}
	}


	public void insertIntoBkeveC(List<TraitementImpots> listTraitementImpots, ParametragesImpots parametragesImpots){

		
		try{

			
			Bkevec bkevec;
			
			for(TraitementImpots t: listTraitementImpots){

				System.out.println("***insertIntoBkeveC t.getEve()****" + t.getEve());
				
//				sql = "insert into bkevec values ('"+t.getAge()+"', '"+t.getOpe()+"', '"+t.getEve()+"', 'COMVRT', 'COM01', 'D', '001', '0,0', '1,0', '0,0', '0,0', '0,0', 'O', '0,0') ";
//				st.executeUpdate(sql);
				bkevec = mapBkevec(t.getAge(), t.getOpe(), t.getEve(), "COMVRT", "COM01", "D", "001", "0,0", "1,0", "0,0", "0,0", "0,0", "O", "0,0");
				updateBkevec(bkevec);
				
//				sql = "insert into bkevec values ('"+t.getAge()+"', '"+t.getOpe()+"', '"+t.getEve()+"', 'PRLVRT', 'PRL01', 'B', '001', '0,0', '1,0', '0,0', '0,0', '0,0', 'P', '0,0') ";
//				st.executeUpdate(sql);
				bkevec = mapBkevec(t.getAge(), t.getOpe(), t.getEve(), "PRLVRT", "PRL01", "B", "001", "0,0", "1,0", "0,0", "0,0", "0,0", "P", "0,0");
				updateBkevec(bkevec);

//				sql = "insert into bkevec values ('"+t.getAge()+"', '"+t.getOpe()+"', '"+t.getEve()+"', 'FRAVRT', 'FRA01', 'D', '001', '"+parametragesImpots.getMontantFraisSYGMA()+"', '1,0', '"+parametragesImpots.getMontantFraisSYGMA()+"', '"+parametragesImpots.getMontantFraisSYGMA()+"', '"+parametragesImpots.getMontantFraisSYGMA()+"', 'O', '0,0') ";
//				st.executeUpdate(sql);
				bkevec = mapBkevec(t.getAge(), t.getOpe(), t.getEve(), "FRAVRT", "FRA01", "D", "001", String.valueOf(parametragesImpots.getMontantFraisSYGMA()), "1,0", 
						String.valueOf(parametragesImpots.getMontantFraisSYGMA()), String.valueOf(parametragesImpots.getMontantFraisSYGMA()), 
							String.valueOf(parametragesImpots.getMontantFraisSYGMA()), "P", "0,0");
				updateBkevec(bkevec);

				Double montantTaxSyg = parametragesImpots.getMontantFraisSYGMA()*parametragesImpots.getTauxTax();
				Double tva = parametragesImpots.getTauxTax()*100;

				String montantTaxSygma = String.valueOf(montantTaxSyg.intValue());
				montantTaxSygma = montantTaxSygma.replace('.', ',');
				System.out.println(" ****montantTaxSygma *****" + montantTaxSygma);

				String TVA = String.valueOf(tva);
				TVA = TVA.replace('.', ',');
				System.out.println("******TVA******"+ TVA);

//				sql = "insert into bkevec values ('"+t.getAge()+"', '"+t.getOpe()+"', '"+t.getEve()+"', 'TAUTAX', 'TAX01', 'D','001', '"+montantTaxSygma+"', '1,0', '"+montantTaxSygma+"', '"+montantTaxSygma+"', '"+montantTaxSygma+"', 'T', '"+TVA+"') ";
//				st.executeUpdate(sql);
				bkevec = mapBkevec(t.getAge(), t.getOpe(), t.getEve(), "TAUTAX", "TAX01", "D", "001", montantTaxSygma, "1,0", 
						montantTaxSygma, montantTaxSygma, montantTaxSygma, "T", TVA);
				updateBkevec(bkevec);

				System.out.println("*****INSERTION DANS BKEVEC REUSSIE*****");
			}

			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}


	public void insertIntoBkrtgsEve(List<TraitementImpots> listTraitementImpots, ParametragesImpots parametragesImpots){
		
		try{

			Bkrtgseve bkrtgseve;
			for(TraitementImpots t: listTraitementImpots){
				System.out.println("***insertIntoBkrtgsEve t.getEve()****" + t.getEve());

//				sql = "insert into bkrtgseve values ('"+t.getAge()+"', '"+t.getOpe()+"', '"+t.getEve()+"', 'PRIOR', '30')";
//				st.executeUpdate(sql);
				bkrtgseve =  mapBkrtgseve(t.getAge(), t.getOpe(), t.getEve(), "PRIOR", "30");
				updateBkrtgseve(bkrtgseve);

//				sql = "insert into bkrtgseve values ('"+t.getAge()+"', '"+t.getOpe()+"', '"+t.getEve()+"', 'NOMBE', '"+t.getNomb()+"') ";
//				st.executeUpdate(sql);
				bkrtgseve =  mapBkrtgseve(t.getAge(), t.getOpe(), t.getEve(), "NOMBE", t.getNomb());
				updateBkrtgseve(bkrtgseve);

//				sql = "insert into bkrtgseve values ('"+t.getAge()+"', '"+t.getOpe()+"', '"+t.getEve()+"', '00504', '/CODTYPTR/') ";
//				st.executeUpdate(sql);
				bkrtgseve =  mapBkrtgseve(t.getAge(), t.getOpe(), t.getEve(), "00504", "/CODTYPTR/");
				updateBkrtgseve(bkrtgseve);

//				sql = "insert into bkrtgseve values ('"+t.getAge()+"', '"+t.getOpe()+"', '"+t.getEve()+"', '72002', '001') ";
//				st.executeUpdate(sql);
				bkrtgseve =  mapBkrtgseve(t.getAge(), t.getOpe(), t.getEve(), "72002", "001");
				updateBkrtgseve(bkrtgseve);

				System.out.println("*****INSERTION DANS BKRTGSEVE REUSSIE*****");
			}

			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	public Object[] getQueryValues(String agsa, String age, String ope, String eve,
			String typ, String ndos, String age1, String dev1, String ncp1,
			String suf1, String clc1, String cli1, String nom1, String ges1,
			String sen1, Double mht1, Double mon1, Date dva1, String exo1,
			Double sol1, Double indh1, Double inds1, String desa1,
			String desa2, String desa3, String desa4, String desa5,
			String age2, String dev2, String ncp2, String suf2, String clc2,
			String cli2, String nom2, String ges2, String sen2, Double mht2,
			Double mon2, Date dva2, Date din, String exo2, Double sol2,
			Double indh2, Double inds2, String desc1, String desc2,
			String desc3, String desc4, String desc5, String etab, String guib,
			String nome, String domi, String adb1, String adb2, String adb3,
			String vilb, String cpob, String cpay, String etabr, String guibr,
			String comb, String cleb, String nomb, Double mban, Date dvab,
			String cai1, String tyc1, String dcai1, String scai1, Double mcai1,
			Double arrc1, String cai2, String tyc2, String dcai2, String scai2,
			Double mcai2, Double arrc2, String dev, Double mht, Double mnat,
			Double mbor, String nbor, Integer nblig, Double tcai2,
			Double tcai3, String nat, String nato, String opeo, String eveo,
			String pieo, Date dou, Date dco, String eta, String etap,
			Integer nbv, Integer nval, String uti, String utf, String uta,
			Integer moa, Integer mof, String lib1, String lib2, String lib3,
			String lib4, String lib5, String lib6, String lib7, String lib8,
			String lib9, String lib10, String agec, String agep, String intr,
			String orig, String rlet, String catr, String ceb, String plb,
			Integer cco, Date dret, String natp, String nump, Date datp,
			String nomp, String ad1p, String ad2p, String delp, String serie,
			String nche, String chql, String chqc, String cab, String ncff,
			String csa, String cfra, String neff, String teff, Date dech,
			String tire, String agti, String agre, Integer nbji, String ptfc,
			String efav, String navl, String edom, String eopp, String efac,
			String moti, String envacc, String enom, String vicl, String teco,
			String tenv, String exjo, String org, String cai3, Double mcai3,
			String forc, String ocai3, Integer ncai3, String csp1, String csp2,
			String csp3, String csp4, String csp5, String ndom, String cmod,
			String devf, String ncpf, String suff, Double monf, Date dvaf,
			String exof, String agee, String deve, String ncpe, String sufe,
			String clce, String ncpi, String sufi, Double mimp, Date dvai,
			String ncpp, String sufp, Double prga, Double mrga, String term,
			String tvar, String intp, String cap, String prll, String ano,
			String etab1, String guib1, String com1b, String etab2,
			String guib2, String com2b, Double tcom1, Double mcom1,
			Double tcom2, Double mcom2, Double tcom3, Double mcom3,
			Double frai1, Double frai2, Double frai3, Double ttax1,
			Double mtax1, Double ttax2, Double mtax2, Double ttax3,
			Double mtax3, Double mnt1, Double mnt2, Double mnt3, Double mnt4,
			Double mnt5, String tyc3, String dcai3, String scai3, Double arrc3,
			Double mhtd, Double tcai4, String tope, String img, Date dsai,
			String hsai, String paysp, String pdelp, String manda,
			String refdos, Double tchfr, String nidnp, Double fraisdiff1,
			Double fraisdiff2) {

		Object[] values = new Object[236];

		values[0] = agsa;
		values[1] = age; 
		values[2] = ope; 
		values[3] = eve; 
		values[4] = typ; 
		values[5] = ndos;
		values[6] = age1;
		values[7] = dev1;
		values[8] = ncp1;
		values[9] = suf1;
		values[10] = clc1;
		values[11] = cli1;
		values[12] = nom1;
		values[13] = ges1;
		values[14] = sen1;
		values[15] = mht1;
		values[16] = mon1;
		values[17] = dva1;
		values[18] = exo1;
		values[19] = sol1;
		values[20] = indh1;
		values[21] = inds1;
		values[22] = desa1;
		values[23] = desa2;
		values[24] = desa3;
		values[25] = desa4;
		values[26] = desa5;
		values[27] = age2; 
		values[28] = dev2; 
		values[29] = ncp2; 
		values[30] = suf2;
		values[31] = clc2; 
		values[32] = cli2; 
		values[33] = nom2; 
		values[34] = ges2;
		values[35] = sen2;
		values[36] = mht2;
		values[37] = mon2 ;
		values[38] = dva2;
		values[39] = din;
		values[40] = exo2;
		values[41] = sol2;
		values[42] = indh2;
		values[43] = inds2;
		values[44] = desc1;
		values[45] = desc2;
		values[46] = desc3;
		values[47] = desc4;
		values[48] = desc5;
		values[49] = etab;
		values[50] = guib;
		values[51] = nome;
		values[52] = domi;
		values[53] = adb1;
		values[54] = adb2;
		values[55] = adb3;
		values[56] = vilb;
		values[57] = cpob;
		values[58] = cpay;
		values[59] = etabr;
		values[60] = guibr;
		values[61] = comb; 
		values[62] = cleb;
		values[63] = nomb;
		values[64] = mban;
		values[65] = dvab;
		values[66] = cai1;
		values[67] = tyc1;
		values[68] = dcai1;
		values[69] = scai1;
		values[70] = mcai1;
		values[71] = arrc1;
		values[72] = cai2;
		values[73] = tyc2;
		values[74] = dcai2;
		values[75] = scai2;
		values[76] = mcai2;
		values[77] = arrc2;
		values[78] = dev;
		values[79] = mht;
		values[80] = mnat;
		values[81] = mbor;
		values[82] = nbor;
		values[83] = nblig;
		values[84] = tcai2;
		values[85] = tcai3;
		values[86] = nat;
		values[87] = nato;
		values[88] = opeo;
		values[89] = eveo;
		values[90] = pieo;
		values[91] = dou;
		values[92] = dco;
		values[93] = eta;
		values[94] = etap;
		values[95] = nbv;
		values[96] = nval;
		values[97] = uti;
		values[98] = utf;
		values[99] = uta;
		values[100] = moa;
		values[101] = mof;
		values[102] = lib1;
		values[103] = lib2;
		values[104] = lib3;
		values[105] = lib4;
		values[106] = lib5;
		values[107] = lib6;
		values[108] = lib7;
		values[109] = lib8;
		values[110] = lib9; 
		values[111] = lib10;
		values[112] = agec;
		values[113] = agep;
		values[114] = intr;
		values[115] = orig;
		values[116] = rlet;
		values[117] = catr;
		values[118] = ceb; 
		values[119] = plb; 
		values[120] = cco; 
		values[121] = dret;
		values[122] = natp;
		values[123] = nump;
		values[124] = datp;
		values[125] = nomp;
		values[126] = ad1p;
		values[127] = ad2p;
		values[128] = delp;
		values[129] = serie;
		values[130] = nche;
		values[131] = chql;
		values[132] = chqc;
		values[133] = cab; 
		values[134] = ncff;
		values[135] = csa; 
		values[136] = dech;
		values[137] = tire;
		values[138] = agti;
		values[139] = agre;
		values[140] = nbji ;
		values[141] = ptfc;
		values[142] = efav;
		values[143] = navl;
		values[144] = edom;
		values[145] = eopp;
		values[146] = efac;
		values[147] = moti;
		values[148] = envacc;
		values[149] = enom;
		values[150] = vicl;
		values[151] = teco;
		values[152] = tenv;
		values[153] = exjo;
		values[154] = org;
		values[155] = cai3;
		values[156] = mcai3;
		values[157] = forc;
		values[158] = ocai3;
		values[159] = ncai3;
		values[160] = csp1;
		values[161] = csp2;
		values[162] = csp3;
		values[163] = csp4;
		values[164] = csp5;
		values[165] = ndom;
		values[166] = cmod;
		values[167] = devf;
		values[168] = ncpf;
		values[169] = suff;
		values[170] = monf;
		values[171] = dvaf;
		values[172] = exof;
		values[173] = agee;
		values[174] = deve;
		values[175] = ncpe;
		values[176] = sufe;
		values[177] = clce;
		values[178] = ncpi;
		values[179] = sufi;
		values[180] = mimp;
		values[181] = dvai;
		values[182] = ncpp;
		values[183] = sufp;
		values[184] = prga;
		values[185] = mrga;
		values[186] = term;
		values[187] = tvar;
		values[188] = intp;
		values[189] = cap;
		values[190] = prll;
		values[191] = ano;
		values[192] = etab1;
		values[193] = guib1;
		values[194] = com1b;
		values[195] = etab2;
		values[196] = guib2;
		values[197] = com2b;
		values[198] = tcom1;
		values[199] = mcom1;
		values[200] = tcom2;
		values[201] = mcom2;
		values[202] = tcom3;
		values[203] = mcom3;
		values[204] = frai1;
		values[205] = frai2;
		values[206] = frai3;
		values[207] = ttax1;
		values[208] = mtax1;
		values[209] = ttax2;
		values[210] = mtax2;
		values[211] = ttax3;
		values[212] = mtax3;
		values[213] = mnt1;
		values[214] = mnt2;
		values[215] = mnt3;
		values[216] = mnt4;
		values[217] = mnt5;
		values[218] = tyc3;
		values[219] = dcai3;
		values[220] = scai3;
		values[221] = arrc3;
		values[222] = mhtd;
		values[223] = tcai4;
		values[224] = tope;
		values[225] = img;
		values[226] = dsai;
		values[227] = hsai;
		values[228] = paysp;
		values[229] = pdelp;
		values[230] = manda;
		values[231] = refdos;
		values[232] = tchfr;
		values[233] = nidnp;
		values[234] = fraisdiff1;
		values[235] = fraisdiff2;

		return values;

	}


	public String findMaxEveOfBkeve(String codeOperation, int position){

		String eve="";

		try{
			
			Long res = lastNumEveOpe(codeOperation);
			Long numEve = res != null ? res : 1l;
			System.out.print("********* numEve ********** : " + numEve);
			
			eve = HelperQuerry.padText(String.valueOf(numEve), HelperQuerry.LEFT, HelperQuerry.TAILLE_CODE_EVE, "0");

		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			try {

				closeDELTAConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return eve;
	}




	public ResultSet executeFilterSystemQuery(DataSystem ds, String query, Object[] parameters) throws Exception {

		ResultSet rs = null;

		if(conDELTA == null || conDELTA.isClosed()){
			//conDELTA = getSystemConnection(ds);
			openDELTAConnection();
		}

		if(conDELTA != null){

			if(ds.getDbConnectionString().indexOf("informix") > 0) conDELTA.createStatement().executeUpdate("SET ISOLATION TO DIRTY READ");

			PreparedStatement ps = conDELTA.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			if(parameters != null && parameters.length > 0){

				int i = 1;
				for(Object o : parameters){
					if(o instanceof java.util.Date) ps.setDate(i, new java.sql.Date( ((java.util.Date)o).getTime() ) );
					else if(o instanceof java.sql.Date) ps.setDate(i, (java.sql.Date)o );
					else if(o instanceof Double) ps.setDouble(i, Double.valueOf( o.toString()) );
					else if(o instanceof Long) ps.setLong(i, Long.valueOf( o.toString()) );
					else if(o instanceof Integer) ps.setInt(i, Integer.valueOf( o.toString()) );
					else ps.setString(i, o.toString());
					i++;

				}

			}

			rs = ps.executeQuery();

		}

		return rs;

	}



	public void executeUpdateSystemQuery(DataSystem ds, String query, Object[] parameters) throws Exception {
		if(ds == null ) ds = findDataSystem("DELTA-V10");
		if(conDELTA == null || conDELTA.isClosed()){
			openDELTAConnection();
		}
		if(conDELTA != null){
			if(ds.getDbConnectionString().indexOf("informix") > 0){
				Statement statement = 	conDELTA.createStatement(); 
				statement.execute("SET ISOLATION TO DIRTY READ");
			}
			PreparedStatement ps = conDELTA.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			if(parameters != null && parameters.length > 0){
				int i = 1;
				for(Object o : parameters){
					if(o == null) ps.setNull(i, java.sql.Types.NULL);
					else if(o instanceof java.util.Date) ps.setDate(i, new java.sql.Date( ((java.util.Date)o).getTime() ) );
					else if(o instanceof java.sql.Date) ps.setDate(i, (java.sql.Date)o );
					else if(o instanceof Double) ps.setDouble(i, Double.valueOf( o.toString()) );
					else if(o instanceof Long) ps.setLong(i, Long.valueOf( o.toString()) );
					else if(o instanceof Integer) ps.setInt(i, Integer.valueOf( o.toString()) );
					else ps.setString(i, o.toString());
					i++;
				}
			}
			ps.executeUpdate();
		}
	}
	
	
	public Long lastNumEveOpe(String ope) {
		
		Long numEve = null;
		
		try {
			
			if (dsCbs == null) findCBSServicesDataSystem();
			if(dsCbs != null && StringUtils.isNotBlank(dsCbs.getDbConnectionString())) {
				System.out.println("NUMEVE: " + dsCbs.getDbConnectionString()+"/transactions/process/lastnumeroeveope/" + ope);
				HttpGet getRequest = new HttpGet(dsCbs.getDbConnectionString()+"/transactions/process/lastnumeroeveope/" + ope);
			    getRequest.setHeader("content-type", "application/json");
			    CloseableHttpResponse response = Shared.getClosableHttpClient().execute(getRequest);
			    HttpEntity entity = null;
			    entity = response.getEntity();
			    
			    if(entity != null) {
			    	
			    	 if(entity != null) {
			    		 
			    		 String content = EntityUtils.toString(entity);
						 JSONObject json = new JSONObject(content);
						 //System.out.println("JSONObject: " + json);
						 String responseCode = json.getString("code");
						 //System.out.println("responseCode: " + responseCode);
						 if ("200".equalsIgnoreCase(responseCode)) {
							 String lienSig = json.getString("data");
							 numEve = lienSig != null && !StringUtils.isBlank(lienSig) ? Long.valueOf(lienSig) + 1 : 1l;
							 //System.out.println("numEve: " + numEve);
						 }
			    		 
			    	 }
			    	
			    } 
			}
		}
		catch(Exception e) {
			return null;
		}
		
		return numEve;
	}

	private void findCBSServicesDataSystem() {
	
		try {
	
			// Demarrage du service Facade du portail
			IFacadeManagerRemote portalFacadeManager = (IFacadeManagerRemote) new InitialContext().lookup( PortalHelper.APPLICATION_EAR.concat("/").concat( IFacadeManagerRemote.SERVICE_NAME ).concat("/remote") );
	
			// Recuperation de la DS de cnx au CBS
			dsCbs = (DataSystem) portalFacadeManager.findByProperty(DataSystem.class, "code", "AFB-SERVICE-CBS");
	
		}catch(Exception e){}
	}
	
	private void findAIFDataSystem() {

		try {

			// Demarrage du service Facade du portail
			IFacadeManagerRemote portalFacadeManager = (IFacadeManagerRemote) new InitialContext().lookup( PortalHelper.APPLICATION_EAR.concat("/").concat( IFacadeManagerRemote.SERVICE_NAME ).concat("/remote") );

			// Recuperation de la DS de cnx au CBS
			dsAIF = (DataSystem) portalFacadeManager.findByProperty(DataSystem.class, "code", "AIF");

		}catch(Exception e){}
	}
	
	public bkeve registerEventToCoreBanking(bkeve eve) {
		
		bkeve _eve = null;
		
		try {
			
			String playload = Shared.mapToJsonString(eve);
			if(!Shared.isJSONValid(playload)) {
				return null;
			}
			
			if (dsCbs == null) findCBSServicesDataSystem();
			if (dsCbs != null && StringUtils.isNotBlank(dsCbs.getDbConnectionString())) {
				
				HttpPost post = new HttpPost(dsCbs.getDbConnectionString()+"/transactions/process/event");
				post.setHeader("content-type", "application/json");
				post.setEntity(new StringEntity(playload , Consts.UTF_8));
				
			    CloseableHttpResponse response = Shared.getClosableHttpClient().execute(post);
			    HttpEntity entity = null;
			    entity = response.getEntity();
			    
			    if(entity != null) {
			    	
			    	String content = EntityUtils.toString(entity);
	                JSONObject json = new JSONObject(content);
	                
	                //Verification du code reponse
	                String resp_code = json.getString("code");
	                
	                if(!resp_code.equalsIgnoreCase("200")) {
	                	return null;
	                }
	                
	                //Recuperation de l'objet renvoyÃ©
	                JSONObject eveObject = json.getJSONObject("eve");
	                
	                //Conversion de l'objet en JsonArray
	                JSONArray eveArray = new JSONArray();
	                eveArray.put(eveObject);
	      
	    	    	eve = new bkeve();
    				// CONVERT JSON ARRAY to bkeve
    			
    				int n = eveArray.length();
    				for(int i=0 ; i< n ; i++) {
    					JSONObject jo = eveArray.getJSONObject(i);
    					eve = Shared.mapToBkeve(jo);
    					//eve = Shared.mapToObject(jo, bkeve.class);
    				}
			    } 
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return _eve;
	}
	
	public Account getInfosDuCompte(String numCompte) {

		Account cpte = null;
		
		try {
			// Initialisation de DataStore d'Amplitude
			if(dsAIF == null) findAIFDataSystem();
			if(dsAIF != null && StringUtils.isNotBlank(dsAIF.getDbConnectionString())) {
				
				
				System.out.println("getDbConnectionString: " + dsAIF.getDbConnectionString()+"/account/getinfoscompte/"+ numCompte.split("-")[0] + "/001/" + numCompte.split("-")[1]);
				HttpGet getRequest = new HttpGet(dsAIF.getDbConnectionString()+"/account/getinfoscompte/" + numCompte.split("-")[0] + "/001/" + numCompte.split("-")[1]);
			    getRequest.setHeader("content-type", "application/json");
			  
			    CloseableHttpResponse response = Shared.getClosableHttpClient().execute(getRequest);
			    HttpEntity entity = null;
			    entity = response.getEntity();
			   
			    if(entity != null) {
			    	 
			    	 List<Account> listComptes = new ArrayList<>();
			    	 String content = EntityUtils.toString(entity);
			    	 //System.out.println("content: " + content);
			    	 
					 JSONObject json = new JSONObject(content);
					
					 ResponseDataAccount responseDataAcc = Shared.mapToObject(json, ResponseDataAccount.class);
					 String responseCode = responseDataAcc.getCode();
					// System.out.println("responseCode: " + responseCode);
					 if ("200".equals(responseCode)) {
						 
						 listComptes = responseDataAcc.getDatas();
						 System.out.println("responseCode listComptes: " + listComptes.size());
						 if (!listComptes.isEmpty()) {
							 
							 cpte =  listComptes.get(0);
							 
						 }
					 } 
			     }
			}

		} catch(Exception e){
			e.printStackTrace();
		}

		return cpte;
	}
	
	private Account customerAccount(String numCompte) {
    	Account acc = null;
    	
    	try {
			// Initialisation de DataStore d'Amplitude
			if(dsAIF == null) findAIFDataSystem();
			if(dsAIF != null && StringUtils.isNotBlank(dsAIF.getDbConnectionString())) {
				
				String age = numCompte.split("-")[0];
				String ncp = numCompte.split("-")[1];
				String cle = numCompte.split("-")[2];
				
				HttpGet getRequest = new HttpGet(dsAIF.getDbConnectionString()+"/account/getlistecompte/"+ncp.substring(0, 7));
			    getRequest.setHeader("content-type", "application/json");
			    CloseableHttpResponse response = Shared.getClosableHttpClient().execute(getRequest);
			    HttpEntity entity = null;
			    entity = response.getEntity();
			    
			    if(entity != null) {
			    	 
			    	 List<Account> listComptes = new ArrayList<>();
			    	 String content = EntityUtils.toString(entity);
					 JSONObject json = new JSONObject(content);
					
					 ResponseDataAccount responseDataAcc = Shared.mapToObject(json, ResponseDataAccount.class);
					 
					 String responseCode = responseDataAcc.getCode();
					 
					 if ("200".equals(responseCode)) {
						 
						 listComptes = responseDataAcc.getDatas();
						 
						 for(Account account : listComptes) {
							 
							 if (age.equalsIgnoreCase(account.getAgence()) && ncp.equalsIgnoreCase(account.getNcp()) 
									 && cle.equalsIgnoreCase(account.getCle()) ) {
								 
								 acc = account;
								 break;
							 
							 }
							 
						 }	 
					 } 
			     }
			}

		} catch(Exception e){
			return null;
		}
    	
    	return acc;
    }
	
 private Client customerInfos(String custId) {
    	
    	Client cust = null;
    	
    	try {
    		
    		if(dsAIF == null) findAIFDataSystem();
			if(dsAIF != null && StringUtils.isNotBlank(dsAIF.getDbConnectionString())) {
				
				HttpGet getRequest = new HttpGet(dsAIF.getDbConnectionString()+"/customer/customerdetails/"+custId);
			    getRequest.setHeader("content-type", "application/json");
			    CloseableHttpResponse response = Shared.getClosableHttpClient().execute(getRequest);
			    HttpEntity entity = null;
			    entity = response.getEntity();
			 
			    if(entity != null) {
			    	 
			    	 List<Client> listClient = new ArrayList<>();
			    	 String content = EntityUtils.toString(entity);
					 JSONObject json = new JSONObject(content);
					
					 ResponseDataClient responseDataClt = Shared.mapToObject(json, ResponseDataClient.class);
					 String responseCode = responseDataClt.getCode();
					
					 
					 if ("200".equals(responseCode)) {
						 
						 listClient = responseDataClt.getDatas();
						 if(!listClient.isEmpty()) {
							 cust = listClient.get(0);
						 }
					 } 
			     }
			}

		} catch(Exception e){
			return null;
		}
    	
    	return cust;
    }
	
	private boolean isUserDelta(String uti) throws Exception {

		boolean res = false;
		
		try {
			
			if (dsCbs == null) findCBSServicesDataSystem();
			if(dsCbs != null && StringUtils.isNotBlank(dsCbs.getDbConnectionString())) {
				
				HttpGet getRequest = new HttpGet(dsCbs.getDbConnectionString()+"/others/services/getinfoutilisateur/" + uti);
			    getRequest.setHeader("content-type", "application/json");
			    CloseableHttpResponse response = Shared.getClosableHttpClient().execute(getRequest);
			    HttpEntity entity = null;
			    entity = response.getEntity();
			    
			    if(entity != null) {
			    		 
		    		 String content = EntityUtils.toString(entity);
					 JSONObject json = new JSONObject(content);
					 
					 String responseCode = json.getString("code");
					 
					 if ("200".equals(responseCode)) {
						 res = true;
					 }
			    		 
			    } 
			}
		}
		catch(Exception e) {
			return false;
		}
		
		// Retourne le resultat 
		return res;
	}
	
    public Date getDvaDebit(String date, String ncp) {
		
		Date dvaDate = null;
		String pdr;
		
		try {
			
			pdr = ncp.substring(7, 10);
			
			if (dsCbs == null) findCBSServicesDataSystem();
			if(dsCbs != null && StringUtils.isNotBlank(dsCbs.getDbConnectionString())) {
				System.out.println("DVA: " + dsCbs.getDbConnectionString()+"/transactions/process/formatteddvadebit/" + date +"/" + pdr);
				HttpGet getRequest = new HttpGet(dsCbs.getDbConnectionString()+"/transactions/process/formatteddvadebit/" + date +"/" + pdr);
			    getRequest.setHeader("content-type", "application/json");
			    CloseableHttpResponse response = Shared.getClosableHttpClient().execute(getRequest);
			    HttpEntity entity = null;
			    entity = response.getEntity();
			    
			    if(entity != null) {
			    	
			    	 if(entity != null) {
			    		 
			    		 String content = EntityUtils.toString(entity);
						 JSONObject json = new JSONObject(content);
						 //System.out.println("JSONObject: " + json);
						 String responseCode = json.getString("code");
						 //System.out.println("responseCode: " + responseCode);
						 if ("200".equalsIgnoreCase(responseCode)) {
							 String datVal = json.getString("data");
							 dvaDate = DateUtil.parse(datVal, DateUtil.DATE_TIME_MINUS_FORMAT_);
						 }
			    		 
			    	 }
			    	
			    } 
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return dvaDate;
	}
    
    public Date getDvaCredit(String date, String ncp) {
		
		Date dvaDate = null;
		String pdr;
		
		try {
			
			pdr = ncp.substring(7, 10);
			
			if (dsCbs == null) findCBSServicesDataSystem();
			if(dsCbs != null && StringUtils.isNotBlank(dsCbs.getDbConnectionString())) {
				System.out.println("DVA: " + dsCbs.getDbConnectionString()+"/transactions/process/formatteddvacredit/" + date +"/" + pdr);
				HttpGet getRequest = new HttpGet(dsCbs.getDbConnectionString()+"/transactions/process/formatteddvacredit/" + date +"/" + pdr);
			    getRequest.setHeader("content-type", "application/json");
			    CloseableHttpResponse response = Shared.getClosableHttpClient().execute(getRequest);
			    HttpEntity entity = null;
			    entity = response.getEntity();
			    
			    if(entity != null) {
			    	
			    	 if(entity != null) {
			    		 
			    		 String content = EntityUtils.toString(entity);
						 JSONObject json = new JSONObject(content);
						 System.out.println("JSONObject: " + json);
						 String responseCode = json.getString("code");
						 //System.out.println("responseCode: " + responseCode);
						 if ("200".equalsIgnoreCase(responseCode)) {
							 String datVal = json.getString("data");
							 dvaDate = DateUtil.parse(datVal, DateUtil.DATE_TIME_MINUS_FORMAT_);
						 }
			    		 
			    	 }
			    	
			    } 
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return dvaDate;
	}
    
    public boolean isCompteEnOpposition(Account account, String opp){
		
		List<StoppageAccount> listStoppages;
		
		
		if(account != null) {
			
			listStoppages = account.getStoppages();
			
			if(listStoppages != null) {
				for (StoppageAccount sto : listStoppages) {
					
					if("V".equals(sto.getStatus()) && opp.equals(sto.getCode())) {
						return Boolean.TRUE;
					}
					
				}
			}
			
		}
	
		return Boolean.FALSE;
	}
    
    private boolean updateBkevec(Bkevec evec) throws Exception {

		boolean res = false;
		
		try {
			
			
			String playload = null;
			
			playload = Shared.mapToJsonString(evec);
			if(!Shared.isJSONValid(playload)) {
				return res;
			}
			
			if (dsCbs == null) findCBSServicesDataSystem();
			if(dsCbs != null && StringUtils.isNotBlank(dsCbs.getDbConnectionString())) {
				
				HttpPost post = new HttpPost(dsCbs.getDbConnectionString()+"/transactions/process/insertbkevec");
				post.setHeader("content-type", "application/json");
				post.setEntity(new StringEntity(playload , Consts.UTF_8));
				
			    CloseableHttpResponse response = Shared.getClosableHttpClient().execute(post);
			    
			    HttpEntity entity = null;
			    entity = response.getEntity();
			    
			    if(entity != null) {
			    		 
		    		 String content = EntityUtils.toString(entity);
					 JSONObject json = new JSONObject(content);
					 
					 String responseCode = json.getString("code");
					 
					 if ("200".equals(responseCode)) {
						 res = true;
					 }
			    		 
			    } 
			}
		}
		catch(Exception e) {
			return false;
		}
		
		// Retourne le resultat 
		return res;
	}
    
    private Bkevec mapBkevec(String age, String ope, String eve, String nat, 
    		String iden, String typc, String devr, String mcomr, String txref, String mcomc, String mcomn, 
    			String mcomt, String tax, String tcom) {
    	
    	Bkevec bkevec = new Bkevec();
		bkevec.setAge(age);
		bkevec.setOpe(ope);
		bkevec.setEve(eve);
		bkevec.setNat(nat);
		bkevec.setIden(iden);
		bkevec.setTypc(typc);
		bkevec.setDevr(devr);
		bkevec.setMcomr(mcomr);
		bkevec.setTxref(txref);
		bkevec.setMcomc(mcomc);
		bkevec.setMcomn(mcomn);
		bkevec.setMcomt(mcomt);
		bkevec.setTax(tax);
		bkevec.setTcom(tcom);
		
		return bkevec;
    }
    
    
    private boolean updateBkrtgseve(Bkrtgseve  eveg) throws Exception {

		boolean res = false;
		
		try {
			
			String playload = null;
			
			playload = Shared.mapToJsonString(eveg);
			if(!Shared.isJSONValid(playload)) {
				return res;
			}
			
			if (dsCbs == null) findCBSServicesDataSystem();
			if(dsCbs != null && StringUtils.isNotBlank(dsCbs.getDbConnectionString())) {
				
				HttpPost post = new HttpPost(dsCbs.getDbConnectionString()+"/transactions/process/insertbkrtgseve");
				post.setHeader("content-type", "application/json");
				post.setEntity(new StringEntity(playload , Consts.UTF_8));
				
			    CloseableHttpResponse response = Shared.getClosableHttpClient().execute(post);
				
			    HttpEntity entity = null;
			    entity = response.getEntity();
			    
			    if(entity != null) {
			    		 
		    		 String content = EntityUtils.toString(entity);
					 JSONObject json = new JSONObject(content);
					 
					 String responseCode = json.getString("code");
					 
					 if ("200".equals(responseCode)) {
						 res = true;
					 }
			    		 
			    } 
			}
		}
		catch(Exception e) {
			return false;
		}
		
		// Retourne le resultat 
		return res;
	}
    
    private Bkrtgseve mapBkrtgseve(String age, String ope, String eve, String iden, String vala) {
    	
    	Bkrtgseve bkrtgseve = new Bkrtgseve();
    	bkrtgseve.setAge(age);
    	bkrtgseve.setOpe(ope);
    	bkrtgseve.setIden(eve);
    	bkrtgseve.setIden(iden);
    	bkrtgseve.setVala(vala);
		
		return bkrtgseve;
    }
    
    public List<Bkbeacrv> getBkbeacrv(String dateOfToday) {
		
    	List<Bkbeacrv> listBkbeacrv = null;
 		
 		try {
 			//"select * from bkcom where age = ? and ncp = ? and clc = ? and cfe='N' and ife='N'"
 			if (dsCbs == null) findCBSServicesDataSystem();
 			if(dsCbs != null && StringUtils.isNotBlank(dsCbs.getDbConnectionString())) {
 				
 				HttpGet getRequest = new HttpGet(dsCbs.getDbConnectionString()+"/transactions/process/getlistebkbeacrv/" + dateOfToday);
 			    getRequest.setHeader("content-type", "application/json");
 			    CloseableHttpResponse response = Shared.getClosableHttpClient().execute(getRequest);
 			    HttpEntity entity = null;
 			    entity = response.getEntity();
 			    
 			    if(entity != null) {
 			    	
 			    	 if(entity != null) {
 			    		 
 			    		 List<Bkbeacrv> listData = new ArrayList<>();
 			    		 String content = EntityUtils.toString(entity);
 						 JSONObject json = new JSONObject(content);
 						 
 						ResponseDataBkeacvr responseData = Shared.mapToObject(json, ResponseDataBkeacvr.class);
 						String responseCode = responseData.getCode();
 						
 						 
 						 if ("200".equals(responseCode)) {
 							
 							 listBkbeacrv = responseData.getData();
 							 
 						 }
 			    		 
 			    	 }
 			    	
 			    } 
 			}
 		}
 		catch(Exception e) {
 			return null;
 		}
 		
 		return listBkbeacrv;
 	}




	/****public void testSMS(){

		/*int i = 0;

		while(i<30000){

			/*if(i<20){
				String msg = "Message Test STEPH VIR " + i;
				SMSWeb sms = new SMSWeb("VIR-SYSTAC", "Virements Systac", "MFSL", msg, "670083273");
				ManagerDAO.save(sms);
			}
			else if(i>=20 && i<=40){
				String msg = "Message Test STEPH VIR " + i;
				SMSWeb sms = new SMSWeb("VIR-SYSTAC", "Virements Systac", "MFSL", msg, "677638359");
				ManagerDAO.save(sms);
			}	
			else if(i>40){
				String msg = "Message Test STEPH VIR " + i;
				SMSWeb sms = new SMSWeb("VIR-SYSTAC", "Virements Systac", "MFSL", msg, "690343422");
				ManagerDAO.save(sms);
			}

			if(i<20000){
				String msg = "Message Test STEPH SMS " + i;
				SMSWeb sms = new SMSWeb("VIR-SYSTAC", "Virements Systac", "MFSL", msg, "694942447");
				ManagerDAO.save(sms);
			}
			else if(i>=20000 && i<30000){
				String msg = "Message Test STEPH SMS " + i;
				SMSWeb sms = new SMSWeb("VIR-SYSTAC", "Virements Systac", "MFSL", msg, "695673175");
				ManagerDAO.save(sms);
			}

			i++;
		}
	}*/

}


/**key1 = "";
key1 = tt.getAgence() + "-" + tt.getCenr() + "-" + tt.getCle() + "-" + tt.getCleDonneurOrdre() + "-" + tt.getCodeEtabDonneurOrdre() + "-" + tt.getCodeGuicherDonneurOrdre() + "-" + tt.getDevise() + "-" + tt.getEtat() +
		"-" + tt.getEve() + "-" + tt.getNcp() + "-" + tt.getNcpDonneurOrdre() + "-" + tt.getNdoc()  + "-" + tt.getNomBeneficiaire() + "-" + tt.getNomBeneficiaireAmplitude() +  "-" + tt.getNomDonneurOrdre() + "-" + tt.getOpe() +
		"-" + tt.getTope() + "-" + tt.getUti() + "-" + tt.getZone() + "-" + tt.getMontant() + "-" + tt.getDcom() + "-" + tt.getDco() + "-" + tt.getDrec();						 
 */
/**for(Traitement t: traitements){
	key2 = "";
	key2 = t.getAgence() + "-" + t.getCenr() + "-" + t.getCle() + "-" + t.getCleDonneurOrdre() + "-" + t.getCodeEtabDonneurOrdre() + "-" + t.getCodeGuicherDonneurOrdre() + "-" + t.getDevise() + "-" + t.getEtat() +
			"-" + t.getEve() + "-" + t.getNcp() + "-" + t.getNcpDonneurOrdre() + "-" + t.getNdoc()  + "-" + t.getNomBeneficiaire() + "-" + t.getNomBeneficiaireAmplitude() +  "-" + t.getNomDonneurOrdre() + "-" + t.getOpe() +
			"-" + t.getTope() + "-" + t.getUti() + "-" + t.getZone() + "-" + t.getMontant() + "-" + t.getDcom() + "-" + t.getDco() + "-" + t.getDrec();						 


	if(!key1.equals(key2) && !traitementsToSave.contains(t)){
		traitementsToSave.add(t);
	}
}*/
