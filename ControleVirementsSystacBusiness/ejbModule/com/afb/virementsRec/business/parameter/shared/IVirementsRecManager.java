/**
 * 
 */
package com.afb.virementsRec.business.parameter.shared;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Remote;

import com.afb.virementsRec.jpa.datamodel.CaracteristiquesVir;
import com.afb.virementsRec.jpa.datamodel.CaracteristiquesVirItem;
import com.afb.virementsRec.jpa.datamodel.Doublons;
import com.afb.virementsRec.jpa.datamodel.FichiersComptabilisationAller;
import com.afb.virementsRec.jpa.datamodel.FichiersComptabilisationRetour;
import com.afb.virementsRec.jpa.datamodel.Incoherences;
import com.afb.virementsRec.jpa.datamodel.MotifsDeRejet;
import com.afb.virementsRec.jpa.datamodel.MotsClesEtCharSpeciaux;
import com.afb.virementsRec.jpa.datamodel.ParamCompensateurCentrale;
import com.afb.virementsRec.jpa.datamodel.ParamEmail;
import com.afb.virementsRec.jpa.datamodel.ParamEmailAuto;
import com.afb.virementsRec.jpa.datamodel.Parametrages;
import com.afb.virementsRec.jpa.datamodel.ParametragesGenTeleCompense;
import com.afb.virementsRec.jpa.datamodel.ParametragesImpots;
import com.afb.virementsRec.jpa.datamodel.RapatriementImagesAller;
import com.afb.virementsRec.jpa.datamodel.RapatriementImagesRetour;
import com.afb.virementsRec.jpa.datamodel.Rejet;
import com.afb.virementsRec.jpa.datamodel.SortTraitement;
import com.afb.virementsRec.jpa.datamodel.TourCompensation;
import com.afb.virementsRec.jpa.datamodel.Traitement;
import com.afb.virementsRec.jpa.datamodel.TraitementImpots;
import com.afb.virementsRec.jpa.datamodel.TraitementTourCompensation;
import com.afb.virementsRec.jpa.datamodel.TypeProcess;
import com.afb.virementsRec.jpa.datamodel.TypeTraitement;
import com.afb.virementsRec.jpa.datamodel.ValidateDoublonsInFichier;
import com.yashiro.persistence.utils.dao.tools.PropertyContainer;

/**
 * Interface Metier de gestion des Parametrages generaux
 * 
 * @author Stéphane Mouafo
 * @version 1.0
 */
@Remote
public interface IVirementsRecManager {


	public static final String SERVICE_NAME = "MonitoringIEManager";

	public <T> T findByPrimaryKey(Class<T> entityClass, Object entityID,PropertyContainer properties);
	
	public List<CaracteristiquesVir> filterCaracteristiquesVir(Parametrages parametrage, CaracteristiquesVir c, CaracteristiquesVirItem cvi);
	
	public List<MotsClesEtCharSpeciaux> filterMotsCles(Parametrages parametrage, MotsClesEtCharSpeciaux c);
	
	public CaracteristiquesVir saveCaracteristiquesVir(CaracteristiquesVir c);
	
	public CaracteristiquesVir updateCaracteristiquesVir(CaracteristiquesVir c);
	
	public List<Parametrages> filterParams();

	public List<ParametragesGenTeleCompense> filterParamGen();
	
	public List<ParamEmail> filterEmails();
	
	public List<ParamEmail> filterEmails(String email);
	
	public Parametrages saveParametrages(Parametrages p);
	
	public Parametrages updateParametrages(Parametrages p);
	
	public ParametragesGenTeleCompense saveParametragesGenTeleCompense(ParametragesGenTeleCompense p);
	
	public ParametragesGenTeleCompense updateParametragesGenTeleCompense(ParametragesGenTeleCompense p);
	
	public ParamEmail saveParamEmail(ParamEmail p);
	
	public ParamEmail updateParamEmail(ParamEmail p);
	
	public List<Traitement> filterVirementsDoublons(Date dateDebut, Date dateFin, Boolean traite, int tailleParLot, String utiPortal);
	
	public List<Traitement> filterVirementsIncoherences(Date dateDebut, Date dateFin, Boolean traite, int tailleParLot, String utiPortal);
	
	public Boolean setDoublonsEnAttente(List<Doublons>doublonsAValider, List<Doublons>doublonsANePasValider, boolean isInBkeveOrBkheve);
	
	public String setIncoherencesEnAttente(List<Incoherences>incoherencesAValider, List<Incoherences>incoherencesANePasValider, boolean isInBkeveOrBkheve, String utiConnecte);
	
	public List<Doublons> filterDoublons(Date dateDebut, Date dateFin);
	
	public List<Incoherences> filterIncoherences(Date dateDebut, Date dateFin);
	
	public Doublons saveDoublons(Doublons d);

	public Incoherences saveIncoherences(Incoherences i);
	
	public String findBeneficiareCoreBanking(Traitement t);
	
	public Traitement saveTraitement(Traitement t);
	
	public Traitement updateTraitement(Traitement t);
	
	public Long countVirementsInDB(Date dateDebut, Date dateFin);
	
	public Long countVirementsTraitesDoublonsInDB(Date dateDebut, Date dateFin, Boolean traite);
	
	public Long countVirementsTraitesIncoherencesInDB(Date dateDebut, Date dateFin, Boolean traite);
	
	public int checkIfEveInBkEveOrBkheve(Date dateDebut, Date dateFin);
	
	public Traitement getLastElementOfLastLotDoublons(Long lastElementOfLastLotDoublons);
	
	public Traitement getLastElementOfLastLotIncoherences(Long lastElementOfLastLotIncoherences);
	
	public void checkAndMountDiskForCompensation();
	
	public void saveParamADT();
	
	public List<TourCompensation> filterTourCompensation(Date date, String user, Integer tour, TypeProcess typeProcess);
	
	//public List<TraitementTourCompensation> filterTraitementTourCompensation (TypeTraitement typeTraitement, TourCompensation tourCompensation );
	public List<TraitementTourCompensation> filterTraitementTourCompensation (TypeTraitement typeTraitement, TourCompensation tourCompensation, Date date);
	
	public List<TraitementTourCompensation> filterTraitementTourCompensation2(Date dateDebut, Date dateFin, String heure, String utilisateur, TypeTraitement typeTraitement, SortTraitement sortTraitement);
	
	public List<RapatriementImagesAller> filterRapatriementImageAller(Date date, String user);
	
	public List<RapatriementImagesRetour> filterRapatriementImageRetour(Date date, String user);
	
	public List<FichiersComptabilisationAller> filterFichierComptaAller(Date date, String user);
	
	public List<FichiersComptabilisationRetour> filterFichierComptaRetour(Date date, String user);
	
	public void robotSuppressionArchives();
	
	public void robotOuvertureFermetureJournee();
	
	public void suppressionArchives();
	
	public List<ParamEmailAuto> filterParamEmailAuto();
	
	public ParamEmailAuto saveParamEmailAuto(ParamEmailAuto paramEmailAuto);
	
	public ParamEmailAuto updateParamEmailAuto(ParamEmailAuto paramEmailAuto);
	
	//public void testSMS();
	
	public ParamCompensateurCentrale filterLastParamCompensateurCentrale();
	
	public List<ValidateDoublonsInFichier> filterDoublons(Date dateDebut, Date dateFin, TypeProcess typeProcess);
	
	public byte[] getReportPDFBytes(String reportName, HashMap<String, Object> map, Collection<?> maCollection) throws Exception;
	
	public List<Incoherences> findExistingIncoherencesAndMarkThem(List<Incoherences> listIncoherences);
	
	public List<Incoherences> findExistingIncoherences(Incoherences i);
	
	public void traitement(String piecesJointesDir, String reportsDir);
	
	public List<String> recuperationsValCB(String uti, String cli, String age, String ncp, String clc, String dev, Date dsai);
	
	public void ZinsertIntoBkeve(List<TraitementImpots> listTraitementImpots);
	
	//public boolean genererFichierRejet(List<Rejet> listRejets);
	
	public String findMaxEveOfBkeve(String codeOperation, int position);
	
	public List<String> recuperationsListeDesaccords(String age, String cli, String ncp);
	
	public List<String> recuperationsOppositionDebit(String age, String cli, String ncp);
	
	public List<String> recuperationCompteImpots(String etabr, String guibr, String age);
	
	public List<MotifsDeRejet> filterMotifRejet(ParametragesImpots parametrage, MotifsDeRejet c);
	
	public void traitementInDB();
	
	public void insertIntoBkeveC(List<TraitementImpots> listTraitementImpots, ParametragesImpots parametragesImpots);
	
	public void insertIntoBkrtgsEve(List<TraitementImpots> listTraitementImpots, ParametragesImpots parametragesImpots);
	
	public List<TraitementImpots> findOpeReserveeOTP_DGI(String ope, String uti, String ncp1, Date dsai);
	
	public boolean updateOpeReserveeOTP_DGI(String ope, String uti, String ncp1, Date dsai, String utif);

}

