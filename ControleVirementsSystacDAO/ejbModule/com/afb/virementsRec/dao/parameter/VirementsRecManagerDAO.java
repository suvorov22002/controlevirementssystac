/**
 * 
 */
package com.afb.virementsRec.dao.parameter;


import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.fop.render.rtf.ListAttributesConverter;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import afb.dsi.dpd.portal.jpa.entities.User;

import com.afb.virementsRec.dao.parameter.shared.IVirementsRecManagerDAOLocal;
import com.afb.virementsRec.jpa.datamodel.CaracteristiquesVir;
import com.afb.virementsRec.jpa.datamodel.CaracteristiquesVirItem;
import com.afb.virementsRec.jpa.datamodel.ContenuFichier;
import com.afb.virementsRec.jpa.datamodel.Fichier;
import com.afb.virementsRec.jpa.datamodel.Traitement;
import com.yashiro.persistence.utils.dao.JPAGenericDAORulesBased;
import com.yashiro.persistence.utils.dao.tools.OrderContainer;
import com.yashiro.persistence.utils.dao.tools.RestrictionsContainer;


/**
 * Implementation du service DAO Local de gestion des Parametrages generaux
 * @author Stéphane Mouafo
 * @version 1.0
 */
@Stateless(name = IVirementsRecManagerDAOLocal.SERVICE_NAME, mappedName = IVirementsRecManagerDAOLocal.SERVICE_NAME, description = "Service DAO Local de gestion des Parametrages generaux")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class VirementsRecManagerDAO extends JPAGenericDAORulesBased implements IVirementsRecManagerDAOLocal{

	/**
	 * Le gestionnaire d'entites
	 */
	@PersistenceContext(unitName = "ControleVirementsSystacEAR")
	private EntityManager entityManager;

	/**
	 * Le Logger
	 */
	private static Log logger = LogFactory.getLog(VirementsRecManagerDAO.class);

	/*
	 * (non-Javadoc)
	 * @see com.yashiro.persistence.utils.dao.IJPAGenericDAO#getEntityManager()
	 */
	@Override
	public EntityManager getEntityManager() {

		// Un log
		logger.trace("[getEntityManager]");

		// On retourne le gestionnaire d'entites
		return entityManager;
	}


	@Override
	public void deletePointDeContact(Long id) {


		Query q = entityManager.createNativeQuery("delete from MONIT_REPORT_POINTCONTACT where POINT_CONTACT_ID = '"+id+"'");

		q.executeUpdate();

	}


	public void deleteStatutsName(Long id){

		Query q = entityManager.createNativeQuery("delete from MONIT_REPORT_STATUTSNAME where STATUTNAME_ID = '"+id+"'");

		q.executeUpdate();
	}

	public void deleteStatuts(Long id){

		Query q = entityManager.createNativeQuery("delete from MONIT_REPORT_STATUT where STATUTNAME_ID = '"+id+"'");

		q.executeUpdate();
	}

	public void deletePassationService(Long id){

		Query q = entityManager.createNativeQuery("delete from MONIT_REPORT_PASS_SERVICE where PassationServ_ID = '"+id+"'");

		q.executeUpdate();

	}

	@SuppressWarnings("unchecked")
	public List<String>filterUsersOfModule(String moduleCode){

		Query q = entityManager.createNativeQuery("Select id from core_module where code = '"+moduleCode+"'");
		System.out.println("******" + q.toString() + "*****");

		String idModule = q.getSingleResult().toString();

		//String idModule = q.getResultList().toString();
		//Long idModule = Long.parseLong(list);

		System.out.println("******" + idModule + "*****");

		Query q2 = entityManager.createNativeQuery("select user_id from core_user_module where module_id = '"+idModule+"' ");
		System.out.println("******" + q2.toString() + "*****");

		List<BigDecimal> list2 = q2.getResultList();
		System.out.println("****list2***" + list2 + "*****");

		List<Long>idUsers = new ArrayList<Long>();

		for(BigDecimal result:list2){
			idUsers.add(result.longValue());
			System.out.println("******" + result + "*****");
		}

		//Query q3 = entityManager.createNativeQuery("select name from core_user where id in ('"+list2+"')");

		RestrictionsContainer rc = RestrictionsContainer.getInstance();
		rc.add(Restrictions.in("id", idUsers));

		List<User> users = filter(User.class, null, rc, OrderContainer.getInstance().add(Order.asc("name")), null, 0, -1);

		List<String> nameUsers = new ArrayList<String>();

		for(User u:users){

			if(u.getName()!=null&&!u.getName().trim().isEmpty()){
				nameUsers.add(u.getName());
				System.out.println("******" + u.getName() + "*****");
			}
		}

		System.out.println("*****nameUsers size****" + nameUsers.size());

		return nameUsers;
		//return null;
	}

	public void deleteEmailEnCopie(Long id){

		Query q = entityManager.createNativeQuery("delete from MONIT_REPORT_EMAIL_ENCOPIE where ID_EMAIL_ENCOPIE = '"+id+"'");

		q.executeUpdate();
	}

	public Long countVirementsInDB(Date dateDebut, Date dateFin){

		Query q = entityManager.createQuery("select count(*) from Traitement t where dsai between '"+dateDebut+"' and '"+dateFin+"' ");

		return (Long)q.getSingleResult();
	}

	public Long countVirementsTraitesDoublonsInDB(Date dateDebut, Date dateFin, Boolean traite){

		Query q = entityManager.createQuery("select count(*) from Traitement t where dsai between '"+dateDebut+"' and '"+dateFin+"' and t.traiteDoublons =:traiteD ");

		q.setParameter("traiteD", traite);
		//q.setParameter("traiteI", traite);

		return (Long)q.getSingleResult();
	}

	public Long countVirementsTraitesIncoherencesInDB(Date dateDebut, Date dateFin, Boolean traite){

		Query q = entityManager.createQuery("select count(*) from Traitement t where dsai between '"+dateDebut+"' and '"+dateFin+"' and t.traiteIncoherences =:traiteI ");

		//q.setParameter("traiteD", traite);
		q.setParameter("traiteI", traite);

		return (Long)q.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<Traitement> filterVirementsDoublonsInDB(Date dateDebut, Date dateFin, Boolean traite, int tailleParLot){

		Query q = entityManager.createQuery("select t from Traitement t where t.dsai between '"+dateDebut+"' and '"+dateFin+"' and t.traiteDoublons =:traiteD order by t.nomBeneficiaire, t.ncp asc");
		q.setParameter("traiteD", traite);
		//q.setParameter("traiteI", traite);
		int firstResult = 0;
		int pageSize = tailleParLot;
		q.setFirstResult(firstResult);
		q.setMaxResults(pageSize);
		List<Traitement>virements = q.getResultList();
		System.out.println("***virements size****"+ virements.size());


		boolean done = false;

		while(!done){

			if(virements.size()<=pageSize){
				done = true;
			}

			firstResult = firstResult + pageSize;
			q.setFirstResult(firstResult);

			System.out.println("***firstResult****"+ firstResult);
			System.out.println("***pageSize****"+ pageSize);


			if(!done){

				virements = q.getResultList();
				System.out.println("***virements size   ****"+ virements.size());
			}
		}
		//entityManager.close();

		return virements;
	}

	@SuppressWarnings("unchecked")
	public List<Traitement> filterVirementsIncoherencesInDB(Date dateToday, Date dateFin, Boolean traite, int tailleParLot){

		Query q = entityManager.createQuery("select t from Traitement t where t.drec = '"+dateToday+"' and t.traiteIncoherences =:traiteI order by t.nomBeneficiaire, t.ncp asc");


		//Query q = entityManager.createQuery("select t from Traitement t where t.dsai between '"+dateDebut+"' and '"+dateFin+"' and t.traiteIncoherences =:traiteI order by t.nomBeneficiaire, t.ncp asc");
		//q.setParameter("traiteD", traite);
		/**q.setParameter("traiteI", traite);
		int firstResult = 0;
		int pageSize = tailleParLot;
		q.setFirstResult(firstResult);
		q.setMaxResults(pageSize);
		List<Traitement>virements = q.getResultList();
		System.out.println("***virements size****"+ virements.size());


		boolean done = false;

		while(!done){

			if(virements.size()<=pageSize){
				done = true;
			}

			firstResult = firstResult + pageSize;
			q.setFirstResult(firstResult);

			System.out.println("***firstResult****"+ firstResult);
			System.out.println("***pageSize****"+ pageSize);


			if(!done){

				virements = q.getResultList();
				System.out.println("***virements size   ****"+ virements.size());
			}
		}
		return virements;
		 */
		//entityManager.close();

		return q.getResultList();
	}



	@SuppressWarnings("unchecked")
	public List<Traitement> filterVirementsInDB(Date dateToday, Date dateFin, Boolean traite, int tailleParLot, int mont){

		Query q = entityManager.createQuery("select t from Traitement t where t.drec = '"+dateToday+"' and t.montant >= '"+mont+"' order by t.agence, t.ncp, t.nomBeneficiaire asc");
		//q.setParameter("traiteD", traite);
		//q.setParameter("traiteI", traite);

		//Query q = entityManager.createQuery("select t from Traitement t where t.dsai between '"+dateDebut+"' and '"+dateFin+"'  order by t.nomBeneficiaire, t.ncp asc");


		/**int firstResult = 0;
		int pageSize = tailleParLot;
		q.setFirstResult(firstResult);
		q.setMaxResults(pageSize);
		List<Traitement>virements = q.getResultList();
		System.out.println("***virements size****"+ virements.size());


		boolean done = false;

		while(!done){

			if(virements.size()<=pageSize){
				done = true;
			}

			firstResult = firstResult + pageSize;
			q.setFirstResult(firstResult);

			System.out.println("***firstResult****"+ firstResult);
			System.out.println("***pageSize****"+ pageSize);


			if(!done){

				virements = q.getResultList();
				System.out.println("***virements size   ****"+ virements.size());
			}
		}

		return virements

		 */
		//entityManager.close();

		return q.getResultList();
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

	public Long findMaxTraitementId(){

		Query q = entityManager.createQuery("select max(id) from Traitement t");

		return (Long)q.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<Traitement> findTraitements(Long minId, Long maxId){

		Query q = entityManager.createQuery("select t from Traitement t where id between '"+minId+"' and '"+maxId+"' ");

		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<ContenuFichier>  findDoublonsEntreFic(ContenuFichier c){

		String cle = constructLinesOfKeysFromContenu(c);
		System.out.println("********CLE******" + cle);

		String whereKey = constructQueryWhereKey(cle, c);
		System.out.println("********WHEREKEY******" + whereKey);

		Query q = entityManager.createNativeQuery("SELECT * FROM VIR_SYSTAC_Contenu_Fichier where  "+whereKey+ " order by id desc, heureCreation desc ", ContenuFichier.class);

		return  q.getResultList();
	}


	@SuppressWarnings("unchecked")
	public void traitementInDB(){

		List<Fichier> listFichier =  new ArrayList<Fichier>();
		List<ContenuFichier> listContenus = new ArrayList<ContenuFichier>();

		System.out.println("*********************DOUBLONS NOMS FICHIERS QUERY*********************************************************");
		Query q = entityManager.createNativeQuery("SELECT a.* from (select f.nomFichier from VIR_SYSTAC_FICHIER f WHERE f.dateCreation =:dateNow " +
				"GROUP BY f.nomFichier " +
				"having count(f.nomFichier) > 1) b " +
				"JOIN VIR_SYSTAC_Fichier a " +
				"on a.nomfichier = b.nomfichier " +
				"order by nomfichier desc, heurecreation desc, id desc", Fichier.class);

		System.out.println("*****************QUERY***********************************************************************"+q.toString());

		try{
			Date dateNow  = new SimpleDateFormat("dd/MM/yyyy").parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
			q.setParameter("dateNow", dateNow);
			System.out.println("***********DATE NOW**********" + dateNow);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Fichier fic = new Fichier();
		if(q.getResultList()!=null && !q.getResultList().isEmpty()){
			for(Object o: q.getResultList() ){
				fic = (Fichier)o;
				listFichier.add(fic);
			}
		}
		//listNomsFichier = q.getResultList();
		if(listFichier!=null && !listFichier.isEmpty()){
			
			System.out.println("*****************LISTE FICHIERS DOUBLONS NOT EMPTY***********************************"+listFichier.size());

			for(Fichier f: listFichier){
				listContenus = f.getListContenus();
				for(ContenuFichier c: listContenus){
					c.setIsDoublonNomFic(Boolean.TRUE);
					update(c);
				}
			}
			listContenus.clear();
		}else{
			System.out.println("*****************LISTE FICHIERS DOUBLONS EMPTY*****************************************"+listFichier.size());
		}

		System.out.println("*****************CONSTRUCTING SELECT QUERY ELEMENTS***********************************************************");
		String cle = constructSelectQueryElements();
		System.out.println("*******CLE**********************************************************************************************" + cle);

		System.out.println("*****************CONSTRUCTING ATTRIBUTE KEY ELEMENTS**********************************************************");
		String attributeKey = constructAttributeKey(cle);
		System.out.println("*******ATTRIBUTE KEY**********" + attributeKey);


		System.out.println("********************DOUBLONS ENTRE ET DANS FICHIER QUERY****************************************************************");
		
		q = entityManager.createNativeQuery("SELECT a.* FROM  (SELECT "+cle+", count(*) " +
				"FROM VIR_SYSTAC_Contenu_Fichier c  "+
				"GROUP BY "+cle+" " +
				"HAVING COUNT(*) > 1) b " +
				"JOIN VIR_SYSTAC_Contenu_Fichier a  "+
				"on "+attributeKey+" " +
				"order by a.id desc, heurecreation desc", ContenuFichier.class);

		System.out.println("*****************QUERY*****************************************************************************"+q.toString());


		listContenus = q.getResultList();
		if(listContenus!=null && !listContenus.isEmpty()){
			
			System.out.println("*****************LISTE CONTENUS DOUBLONS NOT EMPTY****************************************"+listContenus.size());

			String key1 = "";
			String key2 = "";

			/***FILTRER LES CONTENUS PUIS LES METTRE DANS UNE LISTE; ENSUITE LA PARCOURIR ET VERIFIER CHAQUE COUPLE DE LIGNES POUR VERIFIER SI CE SONT DES DOUBLONS
			DES QUE DEUX LIGNES SONT DOUBLONS, IL FAUT IDENTIFIER SI CES LIGNES SONT DANS LE MEME FICHIER OU PAS, PUIS METTRE A JOUR LE ISDOUBLON DANS LA TABLE***************/
			//We traverse the list comparing one element and its subsequent elements
			for(int i=0; i<listContenus.size();i++){

				key1 = constructLinesOfKeysFromContenu((ContenuFichier)listContenus.get(i)).trim();
				//listContenus.get(i).setIsTraite(Boolean.TRUE);

				for(int j=1; j<listContenus.size();j++){

					key2 = constructLinesOfKeysFromContenu((ContenuFichier)listContenus.get(j)).trim();
					//listContenus.get(j).setIsTraite(Boolean.TRUE);

					if(key1.equals(key2)){  //même clé
						//Si les doublons sont dans le même fichier
						if(listContenus.get(i).getFichier().getId().equals(listContenus.get(j).getFichier().getId())){

							System.out.println("*****************DOUBLONS DANS FIC***********************"+listContenus.get(i).getId());
							listContenus.get(i).setIsDoublonDansFic(Boolean.TRUE);
							update(listContenus.get(i));
							listContenus.get(j).setIsDoublonDansFic(Boolean.TRUE);
							update(listContenus.get(j));

						}else if(!listContenus.get(i).getFichier().getId().equals(listContenus.get(j).getFichier().getId())){
							//les doublons sont dans des fichiers différents

							System.out.println("*****************DOUBLONS ENTRE FIC***********************"+listContenus.get(i).getId());
							listContenus.get(i).setIsDoublonEntreFic(Boolean.TRUE);
							update(listContenus.get(i));
							listContenus.get(j).setIsDoublonEntreFic(Boolean.TRUE);
							update(listContenus.get(j));

						}
					}	
				}
			}
		}else{
			System.out.println("*****************LISTE CONTENUS KO***********************"+listContenus.size());
		}

	}



	public String constructLinesOfKeysFromContenu(ContenuFichier contenu){

		//List<String> listKeys = new ArrayList<String>();

		String cle = "";

		List<CaracteristiquesVir> caracteristiquesDoublons =  filter(CaracteristiquesVir.class, null, RestrictionsContainer.getInstance().add(Restrictions.eq("valide", Boolean.TRUE)), null, null, 0, -1);
		if(caracteristiquesDoublons!=null && !caracteristiquesDoublons.isEmpty()){

			for(CaracteristiquesVir c: caracteristiquesDoublons){

				if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.Evenement)){

					if(cle.equals(""))
						cle = contenu.getNumeroVirement();
					else
						cle = cle + "," + contenu.getNumeroVirement();
				}
				if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.montant)){

					if(cle.equals(""))
						cle = String.valueOf(contenu.getMontantVirement());
					else
						cle = cle + "," + String.valueOf(contenu.getMontantVirement());
				}
				if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.ncp)){
					if(cle.equals(""))
						cle = contenu.getRIBBeneficiaire();
					else
						cle = cle + "," + contenu.getRIBBeneficiaire(); //substring(135, 158).substring(11,22)
				}
				if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.ncp_donneur_ordre)){
					if(cle.equals(""))
						cle =  contenu.getRIBDonneurOrdre();
					else
						cle = cle + "," + contenu.getRIBDonneurOrdre(); //substring(75, 98).substring(11,22)
				}
				if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.nom_benef)){
					if(cle.equals(""))
						cle = contenu.getNomEtPrenomBeneficiaire();
					else
						cle = cle + "," + contenu.getNomEtPrenomBeneficiaire();
				}
				if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.nom_donneur_ordre)){
					if(cle.equals(""))
						cle = contenu.getNomEtPrenomDonneurOrdre();
					else
						cle = cle + "," + contenu.getNomEtPrenomDonneurOrdre();
				}
			}

			//listKeys.add(cle);

		}
		return cle;
	}


	public String constructSelectQueryElements(){
		String cle="";
		List<CaracteristiquesVir> caracteristiquesDoublons =  filter(CaracteristiquesVir.class, null, RestrictionsContainer.getInstance().add(Restrictions.eq("valide", Boolean.TRUE)), null, null, 0, -1);
		if(caracteristiquesDoublons!=null && !caracteristiquesDoublons.isEmpty()){

			for(CaracteristiquesVir c: caracteristiquesDoublons){
				if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.Evenement)){

					if(cle.equals(""))
						cle = "numeroVirement";
					else
						cle = cle + ", " + "numeroVirement";
				}
				if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.montant)){

					if(cle.equals(""))
						cle = "montantVirement";
					else
						cle = cle + ", " + "montantVirement";
				}
				if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.ncp)){
					if(cle.equals(""))
						cle = "RIBBeneficiaire";
					else
						cle = cle + ", " + "RIBBeneficiaire"; //substring(135, 158).substring(11,22)
				}
				if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.ncp_donneur_ordre)){
					if(cle.equals(""))
						cle =  "RIBDonneurOrdre";
					else
						cle = cle + ", " + "RIBDonneurOrdre"; //substring(75, 98).substring(11,22)
				}
				if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.nom_benef)){
					if(cle.equals(""))
						cle = "nomEtPrenomBeneficiaire";
					else
						cle = cle + ", " + "nomEtPrenomBeneficiaire";
				}
				if(c.getCaracteristiquesItems().equals(CaracteristiquesVirItem.nom_donneur_ordre)){
					if(cle.equals(""))
						cle = "nomEtPrenomDonneurOrdre";
					else
						cle = cle + ", " + "nomEtPrenomDonneurOrdre";
				}
			}
		}
		return cle;
	}

	public String constructAttributeKey(String cle){

		String attributeKey = "";
		String [] cleArray = cle.split(",");
		for(String key: cleArray){
			if(attributeKey.isEmpty())
				attributeKey = "a."+key.trim() + " = " + "b."+key.trim();
			else
				attributeKey = attributeKey + " AND " + "a."+key.trim() + " = " + "b."+key.trim();
		}

		return attributeKey;
	}


	public String constructQueryWhereKey(String cle, ContenuFichier contenu){

		String whereKey = "";
		String whereKeyValue="";
		String [] cleArray = cle.split(",");
		System.out.println("*****cleArray length*****" + cleArray.length);
		for(String key: cleArray){

			if(whereKey.equals("")){

				if(key.equals(contenu.getNumeroVirement())){
					whereKey = "numeroVirement";
				}
				else if(key.equals(String.valueOf(contenu.getMontantVirement()))){
					whereKey = "montantVirement";
				}
				else if(key.equals(contenu.getRIBBeneficiaire())){
					whereKey = "RIBBeneficiaire";
				}
				else if(key.equals(contenu.getRIBDonneurOrdre())){
					whereKey = "RIBDonneurOrdre";
				}
				else if(key.equals(contenu.getNomEtPrenomBeneficiaire())){
					whereKey = "nomEtPrenomBeneficiaire";
				}
				else if(key.equals(contenu.getNomEtPrenomDonneurOrdre())){
					whereKey = "nomEtPrenomDonneurOrdre";
				}

				//whereKey =  key.equals(contenu.getNumeroVirement())?"numeroVirement":key.equals(contenu.getMontantVirement())?"montantVirement":key.equals(contenu.getRIBBeneficiaire())?"RIBBeneficiaire":key.equals(contenu.getRIBDonneurOrdre())?"RIBDonneurOrdre":key.equals(contenu.getNomEtPrenomBeneficiaire())?"nomEtPrenomBeneficiaire":key.equals(contenu.getNomEtPrenomDonneurOrdre())?"nomEtPrenomDonneurOrdre":"";
				whereKey = whereKey + " = ";

				if(key.equals(contenu.getNumeroVirement())){
					whereKeyValue = "'" + contenu.getNumeroVirement() + "'";
				}
				else if(key.equals(String.valueOf(contenu.getMontantVirement()))){
					whereKeyValue = "'" + String.valueOf(contenu.getMontantVirement()) + "'";
				}
				else if(key.equals(contenu.getRIBBeneficiaire())){
					whereKeyValue = "'" + contenu.getRIBBeneficiaire() + "'";
				}
				else if(key.equals(contenu.getRIBDonneurOrdre())){
					whereKeyValue = "'" + contenu.getRIBDonneurOrdre() + "'";
				}
				else if(key.equals(contenu.getNomEtPrenomBeneficiaire())){
					whereKeyValue = "'" + contenu.getNomEtPrenomBeneficiaire() + "'";
				}
				else if(key.equals(contenu.getNomEtPrenomDonneurOrdre())){
					whereKeyValue = "'" + contenu.getNomEtPrenomDonneurOrdre() + "'";
				}

				//whereKeyValue = key.equals(contenu.getNumeroVirement())?contenu.getNumeroVirement():key.equals(contenu.getMontantVirement())?String.valueOf(contenu.getMontantVirement()):key.equals(contenu.getRIBBeneficiaire())?contenu.getRIBBeneficiaire():key.equals(contenu.getRIBDonneurOrdre())?contenu.getRIBDonneurOrdre():key.equals(contenu.getNomEtPrenomBeneficiaire())?contenu.getNomEtPrenomBeneficiaire():key.equals(contenu.getNomEtPrenomDonneurOrdre())?contenu.getNomEtPrenomDonneurOrdre():"";

				whereKey = whereKey + whereKeyValue;

				System.out.println("***whereKey*****" + whereKey);
			}
			else{

				whereKey = whereKey + " AND " ;

				if(key.equals(contenu.getNumeroVirement())){
					whereKey += "numeroVirement";
				}
				else if(key.equals(String.valueOf(contenu.getMontantVirement()))){
					whereKey += "montantVirement";
				}
				else if(key.equals(contenu.getRIBBeneficiaire())){
					whereKey += "RIBBeneficiaire";
				}
				else if(key.equals(contenu.getRIBDonneurOrdre())){
					whereKey += "RIBDonneurOrdre";
				}
				else if(key.equals(contenu.getNomEtPrenomBeneficiaire())){
					whereKey += "nomEtPrenomBeneficiaire";
				}
				else if(key.equals(contenu.getNomEtPrenomDonneurOrdre())){
					whereKey += "nomEtPrenomDonneurOrdre";
				}

				//whereKey =  key.equals(contenu.getNumeroVirement())?"numeroVirement":key.equals(contenu.getMontantVirement())?"montantVirement":key.equals(contenu.getRIBBeneficiaire())?"RIBBeneficiaire":key.equals(contenu.getRIBDonneurOrdre())?"RIBDonneurOrdre":key.equals(contenu.getNomEtPrenomBeneficiaire())?"nomEtPrenomBeneficiaire":key.equals(contenu.getNomEtPrenomDonneurOrdre())?"nomEtPrenomDonneurOrdre":"";

				whereKey = whereKey + " = ";


				if(key.equals(contenu.getNumeroVirement())){
					whereKeyValue = "'" + contenu.getNumeroVirement() + "'";
				}
				else if(key.equals(String.valueOf(contenu.getMontantVirement()))){
					whereKeyValue = "'" + String.valueOf(contenu.getMontantVirement()) + "'";
				}
				else if(key.equals(contenu.getRIBBeneficiaire())){
					whereKeyValue = "'" + contenu.getRIBBeneficiaire() + "'";
				}
				else if(key.equals(contenu.getRIBDonneurOrdre())){
					whereKeyValue = "'" + contenu.getRIBDonneurOrdre() + "'";
				}
				else if(key.equals(contenu.getNomEtPrenomBeneficiaire())){
					whereKeyValue = "'" + contenu.getNomEtPrenomBeneficiaire() + "'";
				}
				else if(key.equals(contenu.getNomEtPrenomDonneurOrdre())){
					whereKeyValue = "'" + contenu.getNomEtPrenomDonneurOrdre() + "'";
				}
				//whereKeyValue = key.equals(contenu.getNumeroVirement())?contenu.getNumeroVirement():key.equals(contenu.getMontantVirement())?String.valueOf(contenu.getMontantVirement()):key.equals(contenu.getRIBBeneficiaire())?contenu.getRIBBeneficiaire():key.equals(contenu.getRIBDonneurOrdre())?contenu.getRIBDonneurOrdre():key.equals(contenu.getNomEtPrenomBeneficiaire())?contenu.getNomEtPrenomBeneficiaire():key.equals(contenu.getNomEtPrenomDonneurOrdre())?contenu.getNomEtPrenomDonneurOrdre():"";

				whereKey = whereKey + whereKeyValue;

				System.out.println("***whereKey 2*****" + whereKey);
			}
		}

		return whereKey;
	}


}