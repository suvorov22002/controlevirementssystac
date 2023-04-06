/**
 * 
 */
package com.afb.virementsRec.dao.parameter.shared;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Local;

import com.afb.virementsRec.jpa.datamodel.ContenuFichier;
import com.afb.virementsRec.jpa.datamodel.Traitement;
import com.yashiro.persistence.utils.dao.IJPAGenericDAO;


/**
 * Interface DAO Locale de gestion des Parametrages generaux
 * @author Stéphane Mouafo
 * @version 1.0
 */
@Local
public interface IVirementsRecManagerDAOLocal extends IJPAGenericDAO {
	
	/**
	 * Nom du Service DAO de gestion des Parametrages generaux
	 */
	public static final String SERVICE_NAME = "ManagerDAO";
	
	public void deletePointDeContact(Long id);
	
	public void deleteStatutsName(Long id);
	
	public void deleteStatuts(Long id);
	
	public void deletePassationService(Long id);

	public List<String>filterUsersOfModule(String moduleCode);
	
	public void deleteEmailEnCopie(Long id);
	
	public Long countVirementsInDB(Date dateDebut, Date dateFin);
	
	public Long countVirementsTraitesDoublonsInDB(Date dateDebut, Date dateFin, Boolean traite);
	
	public Long countVirementsTraitesIncoherencesInDB(Date dateDebut, Date dateFin, Boolean traite);
	
	public List<Traitement> filterVirementsDoublonsInDB(Date dateDebut, Date dateFin, Boolean traite, int tailleParLot);
	
	public List<Traitement> filterVirementsIncoherencesInDB(Date dateDebut, Date dateFin, Boolean traite, int tailleParLot);
	
	public List<Traitement> filterVirementsInDB(Date dateDebut, Date dateFin, Boolean traite, int tailleParLot,int mont);
	
	public byte[] getReportPDFBytes(String reportName, HashMap<String, Object> map, Collection<?> maCollection) throws Exception;
	
	public Long findMaxTraitementId();
	
	public List<Traitement> findTraitements(Long minId, Long maxId);
	
	public void traitementInDB();
	
	public List<ContenuFichier> findDoublonsEntreFic(ContenuFichier c);

	

}
