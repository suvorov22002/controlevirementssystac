package com.afb.virementsRec.jpa.datamodel;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * Entity implementation class for Entity: Traitement
 * Classe pour l'entité Traitement
 * @author Stéphane Mouafo
 *
 */
@Entity
@Table(name = "VIR_SYSTAC_TRAITEMENT")
public class Traitement implements Serializable{


	private static final long serialVersionUID = 1L;


	/**
	 * Identifiant
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TRAITEMENT_ID")
	private Long id;


	@Column(name = "AGENCE")
	private String agence;


	@Column(name = "NUM_CPT")
	private String ncp;    

	@Column(name = "CLE")
	private String cle;

	@Column(name = "DEVISE")
	private String devise;


	@Column(name = "NOM_BENEF")
	private String nomBeneficiaire;


	@Column(name = "NOM_BENEF_AMPLI")
	private String nomBeneficiaireAmplitude;
	
	@Column(name = "SIGLE")
	private String sigle;

	@Column(name = "DATE_SAISIE")
	private Date dsai;



	@Column(name = "DATE_COMPTABLE")
	private Date dco;


	@Column(name = "DATE_VALEUR")
	private Date dva;


	@Column(name = "CODE_OPE")
	private String ope;

	@Column(name = "EVE")
	private String eve;

	@Column(name = "MONTANT")
	private Double montant;

	@Column(name = "LIBELLE")
	private String lib;

	@Column(name = "ETAT")
	private String etat;

	@Column(name = "UTI")
	private String uti;

	@Column(name = "UTI_PORTAL")
	private String utiPortal;

	@Column(name = "CODE_ETAB_DONNEUR_ORDRE")
	private String codeEtabDonneurOrdre;

	@Column(name = "CODE_GUICH_DONNEUR_ORDRE")
	private String codeGuicherDonneurOrdre;

	@Column(name = "NCP_DONNEUR_ORDRE")
	private String ncpDonneurOrdre;

	@Column(name = "CLE_DONNEUR_ORDRE")
	private String cleDonneurOrdre;

	@Column(name = "NOM_DONNEUR_ORDRE")
	private String nomDonneurOrdre;

	@Column(name = "VALIDE")
	private Boolean valide = Boolean.TRUE;

	@Column(name = "TRAITE_DOUBLONS")
	private Boolean traiteDoublons = Boolean.FALSE;  //traité

	@Column(name = "TRAITE_INCOHERENCES")
	private Boolean traiteIncoherences = Boolean.FALSE;

	@Column(name = "DATE_TRAITEMENT")
	private Date dateTraitement;

	@Transient
	private List<Traitement> items = new ArrayList<Traitement>();
	
	//@Transient
	private Integer countTotalVirements;
	
	//@Transient
	private Integer countTotalVirementsATraiter;
	
	//@Transient
	private Integer nbrBelowPlancher;
	
	//@Transient
	private Integer nbrEMF;
	

	private String nat;  //nature du fichier

	private String tope;  // type d'opération

	private String cenr ;  //code enregistrement

	private Date drec ; // date réception

	private Date dreg ;  // date règlement

	private Date dsor ; //date du sort

	private String ndoc; // numéro du document

	private Date dcom ; // date de compensation

	@Column(length=100000)
	private String zone; //contenu du fichier

	/**@OneToOne(mappedBy="traitement")
	private Incoherences incoherences;*/




	public Traitement() {
		super();
	}



	public Traitement(String agence, String ncp, String cle, String devise,
			String nomBeneficiaire, String nomBeneficiaireAmplitude,String sigle, Date drec, Date dco, Date dcom, String tope, String cenr, String ndoc, String ope,String eve,
			Double montant, String zone, String uti,
			String codeEtabDonneurOrdre, String codeGuicherDonneurOrdre,
			String ncpDonneurOrdre, String cleDonneurOrdre,
			String nomDonneurOrdre,
			Date dateTraitement, String utiPortal, Integer countTotalVirements, Integer countTotalVirementsATraiter, Integer nbrBelowSeuil, Integer nbrEMF) {
		super();
		this.agence = agence;
		this.ncp = ncp;
		this.cle = cle;
		this.devise = devise;
		this.nomBeneficiaire = nomBeneficiaire;
		this.nomBeneficiaireAmplitude=nomBeneficiaireAmplitude;
		this.sigle = sigle;
		this.drec=drec;
		this.dco = dco;
		this.dcom = dcom;
		this.tope = tope;
		this.cenr = cenr;
		this.ndoc = ndoc;
		this.ope = ope;
		this.eve=eve;
		this.montant = montant;
		this.zone = zone;
		this.uti = uti;
		this.codeEtabDonneurOrdre = codeEtabDonneurOrdre;
		this.codeGuicherDonneurOrdre = codeGuicherDonneurOrdre;
		this.ncpDonneurOrdre = ncpDonneurOrdre;
		this.cleDonneurOrdre = cleDonneurOrdre;
		this.nomDonneurOrdre = nomDonneurOrdre;
		this.dateTraitement = dateTraitement;
		this.utiPortal=utiPortal;
		this.countTotalVirements = countTotalVirements;
		this.countTotalVirementsATraiter = countTotalVirementsATraiter;
		this.nbrBelowPlancher = nbrBelowSeuil;
		this.nbrEMF = nbrEMF;
	}





	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getAgence() {
		return agence;
	}



	public void setAgence(String agence) {
		this.agence = agence;
	}



	public String getNcp() {
		return ncp;
	}



	public void setNcp(String ncp) {
		this.ncp = ncp;
	}



	public String getCle() {
		return cle;
	}



	public void setCle(String cle) {
		this.cle = cle;
	}





	public String getDevise() {
		return devise;
	}



	public void setDevise(String devise) {
		this.devise = devise;
	}



	public Date getDsai() {
		return dsai;
	}





	public void setDsai(Date dsai) {
		this.dsai = dsai;
	}





	public Date getDco() {
		return dco;
	}



	public void setDco(Date dco) {
		this.dco = dco;
	}



	public Date getDva() {
		return dva;
	}



	public void setDva(Date dva) {
		this.dva = dva;
	}



	public String getOpe() {
		return ope;
	}



	public void setOpe(String ope) {
		this.ope = ope;
	}



	public String getEve() {
		return eve;
	}





	public void setEve(String eve) {
		this.eve = eve;
	}





	public Double getMontant() {
		return montant;
	}



	public void setMontant(Double montant) {
		this.montant = montant;
	}





	public String getNomBeneficiaire() {
		return nomBeneficiaire;
	}





	public void setNomBeneficiaire(String nomBeneficiaire) {
		this.nomBeneficiaire = nomBeneficiaire;
	}





	public String getNomBeneficiaireAmplitude() {
		return nomBeneficiaireAmplitude;
	}





	public void setNomBeneficiaireAmplitude(String nomBeneficiaireAmplitude) {
		this.nomBeneficiaireAmplitude = nomBeneficiaireAmplitude;
	}





	public String getCodeEtabDonneurOrdre() {
		return codeEtabDonneurOrdre;
	}





	public void setCodeEtabDonneurOrdre(String codeEtabDonneurOrdre) {
		this.codeEtabDonneurOrdre = codeEtabDonneurOrdre;
	}





	public String getCodeGuicherDonneurOrdre() {
		return codeGuicherDonneurOrdre;
	}





	public void setCodeGuicherDonneurOrdre(String codeGuicherDonneurOrdre) {
		this.codeGuicherDonneurOrdre = codeGuicherDonneurOrdre;
	}





	public String getNcpDonneurOrdre() {
		return ncpDonneurOrdre;
	}





	public void setNcpDonneurOrdre(String ncpDonneurOrdre) {
		this.ncpDonneurOrdre = ncpDonneurOrdre;
	}





	public String getCleDonneurOrdre() {
		return cleDonneurOrdre;
	}





	public void setCleDonneurOrdre(String cleDonneurOrdre) {
		this.cleDonneurOrdre = cleDonneurOrdre;
	}





	public String getNomDonneurOrdre() {
		return nomDonneurOrdre;
	}





	public void setNomDonneurOrdre(String nomDonneurOrdre) {
		this.nomDonneurOrdre = nomDonneurOrdre;
	}





	public String getLib() {
		return lib;
	}



	public void setLib(String lib) {
		this.lib = lib;
	}



	public String getEtat() {
		return etat;
	}



	public void setEtat(String etat) {
		this.etat = etat;
	}



	public String getUti() {
		return uti;
	}



	public void setUti(String uti) {
		this.uti = uti;
	}



	public Boolean getValide() {
		return valide;
	}



	public void setValide(Boolean valide) {
		this.valide = valide;
	}



	public Date getDateTraitement() {
		return dateTraitement;
	}



	public void setDateTraitement(Date dateTraitement) {
		this.dateTraitement = dateTraitement;
	}



	public Boolean getTraiteDoublons() {
		return traiteDoublons;
	}





	public void setTraiteDoublons(Boolean traiteDoublons) {
		this.traiteDoublons = traiteDoublons;
	}





	public Boolean getTraiteIncoherences() {
		return traiteIncoherences;
	}





	public void setTraiteIncoherences(Boolean traiteIncoherences) {
		this.traiteIncoherences = traiteIncoherences;
	}





	public List<Traitement> getItems() {
		return items;
	}





	public void setItems(List<Traitement> items) {
		this.items = items;
	}





	public String getUtiPortal() {
		return utiPortal;
	}





	public void setUtiPortal(String utiPortal) {
		this.utiPortal = utiPortal;
	}



	public String getNat() {
		return nat;
	}



	public void setNat(String nat) {
		this.nat = nat;
	}



	public String getTope() {
		return tope;
	}



	public void setTope(String tope) {
		this.tope = tope;
	}



	public String getCenr() {
		return cenr;
	}



	public void setCenr(String cenr) {
		this.cenr = cenr;
	}



	public Date getDrec() {
		return drec;
	}



	public void setDrec(Date drec) {
		this.drec = drec;
	}



	public Date getDreg() {
		return dreg;
	}



	public void setDreg(Date dreg) {
		this.dreg = dreg;
	}



	public Date getDsor() {
		return dsor;
	}



	public void setDsor(Date dsor) {
		this.dsor = dsor;
	}



	public String getNdoc() {
		return ndoc;
	}



	public void setNdoc(String ndoc) {
		this.ndoc = ndoc;
	}



	public Date getDcom() {
		return dcom;
	}



	public void setDcom(Date dcom) {
		this.dcom = dcom;
	}



	public String getZone() {
		return zone;
	}



	public void setZone(String zone) {
		this.zone = zone;
	}



	public String getSigle() {
		return sigle;
	}



	public void setSigle(String sigle) {
		this.sigle = sigle;
	}



	public Integer getCountTotalVirements() {
		return countTotalVirements;
	}



	public void setCountTotalVirements(Integer countTotalVirements) {
		this.countTotalVirements = countTotalVirements;
	}



	public Integer getCountTotalVirementsATraiter() {
		return countTotalVirementsATraiter;
	}



	public void setCountTotalVirementsATraiter(Integer countTotalVirementsATraiter) {
		this.countTotalVirementsATraiter = countTotalVirementsATraiter;
	}



	public Integer getNbrBelowPlancher() {
		return nbrBelowPlancher;
	}



	public void setNbrBelowPlancher(Integer nbrBelowPlancher) {
		this.nbrBelowPlancher = nbrBelowPlancher;
	}



	public Integer getNbrEMF() {
		return nbrEMF;
	}



	public void setNbrEMF(Integer nbrEMF) {
		this.nbrEMF = nbrEMF;
	}



	/***public Incoherences getIncoherences() {
		return incoherences;
	}



	public void setIncoherences(Incoherences incoherences) {
		this.incoherences = incoherences;
	}*/



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agence == null) ? 0 : agence.hashCode());
		result = prime * result + ((cenr == null) ? 0 : cenr.hashCode());
		result = prime * result + ((cle == null) ? 0 : cle.hashCode());
		result = prime * result
				+ ((cleDonneurOrdre == null) ? 0 : cleDonneurOrdre.hashCode());
		result = prime
				* result
				+ ((codeEtabDonneurOrdre == null) ? 0 : codeEtabDonneurOrdre
						.hashCode());
		result = prime
				* result
				+ ((codeGuicherDonneurOrdre == null) ? 0
						: codeGuicherDonneurOrdre.hashCode());
		result = prime * result
				+ ((dateTraitement == null) ? 0 : dateTraitement.hashCode());
		result = prime * result + ((dco == null) ? 0 : dco.hashCode());
		result = prime * result + ((dcom == null) ? 0 : dcom.hashCode());
		result = prime * result + ((devise == null) ? 0 : devise.hashCode());
		result = prime * result + ((drec == null) ? 0 : drec.hashCode());
		result = prime * result + ((dreg == null) ? 0 : dreg.hashCode());
		result = prime * result + ((dsai == null) ? 0 : dsai.hashCode());
		result = prime * result + ((dsor == null) ? 0 : dsor.hashCode());
		result = prime * result + ((dva == null) ? 0 : dva.hashCode());
		result = prime * result + ((etat == null) ? 0 : etat.hashCode());
		result = prime * result + ((eve == null) ? 0 : eve.hashCode());
		result = prime * result + ((lib == null) ? 0 : lib.hashCode());
		result = prime * result + ((montant == null) ? 0 : montant.hashCode());
		result = prime * result + ((nat == null) ? 0 : nat.hashCode());
		result = prime * result + ((ncp == null) ? 0 : ncp.hashCode());
		result = prime * result
				+ ((ncpDonneurOrdre == null) ? 0 : ncpDonneurOrdre.hashCode());
		result = prime * result + ((ndoc == null) ? 0 : ndoc.hashCode());
		result = prime * result
				+ ((nomBeneficiaire == null) ? 0 : nomBeneficiaire.hashCode());
		result = prime
				* result
				+ ((nomBeneficiaireAmplitude == null) ? 0
						: nomBeneficiaireAmplitude.hashCode());
		result = prime * result
				+ ((nomDonneurOrdre == null) ? 0 : nomDonneurOrdre.hashCode());
		result = prime * result + ((ope == null) ? 0 : ope.hashCode());
		result = prime * result + ((tope == null) ? 0 : tope.hashCode());
		result = prime * result + ((zone == null) ? 0 : zone.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		//System.out.println("----------EQUALS-----");
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Traitement other = (Traitement) obj;
		//System.out.println("----------EQUALS  2-----");
		if (agence == null) {
			if (other.agence != null)
				return false;
		} else if (!agence.equals(other.agence))
			return false;
		if (cenr == null) {
			if (other.cenr != null)
				return false;
		} else if (!cenr.equals(other.cenr))
			return false;
		if (cle == null) {
			if (other.cle != null)
				return false;
		} else if (!cle.equals(other.cle))
			return false;
		if (cleDonneurOrdre == null) {
			if (other.cleDonneurOrdre != null)
				return false;
		} else if (!cleDonneurOrdre.equals(other.cleDonneurOrdre))
			return false;
		if (codeEtabDonneurOrdre == null) {
			if (other.codeEtabDonneurOrdre != null)
				return false;
		} else if (!codeEtabDonneurOrdre.equals(other.codeEtabDonneurOrdre))
			return false;
		if (codeGuicherDonneurOrdre == null) {
			if (other.codeGuicherDonneurOrdre != null)
				return false;
		} else if (!codeGuicherDonneurOrdre
				.equals(other.codeGuicherDonneurOrdre))
			return false;
		if (dco == null) {
			if (other.dco != null)
				return false;
		} else if (!dco.equals(other.dco))
			return false;
		if (dcom == null) {
			if (other.dcom != null)
				return false;
		} else if (!dcom.equals(other.dcom))
			return false;
		if (devise == null) {
			if (other.devise != null)
				return false;
		} else if (!devise.equals(other.devise))
			return false;
		if (drec == null) {
			if (other.drec != null)
				return false;
		} else if (!drec.equals(other.drec))
			return false;
		if (dreg == null) {
			if (other.dreg != null)
				return false;
		} else if (!dreg.equals(other.dreg))
			return false;
		if (dsai == null) {
			if (other.dsai != null)
				return false;
		} else if (!dsai.equals(other.dsai))
			return false;
		if (dsor == null) {
			if (other.dsor != null)
				return false;
		} else if (!dsor.equals(other.dsor))
			return false;
		if (dva == null) {
			if (other.dva != null)
				return false;
		} else if (!dva.equals(other.dva))
			return false;
		if (eve == null) {
			if (other.eve != null)
				return false;
		} else if (!eve.equals(other.eve))
			return false;
		if (lib == null) {
			if (other.lib != null)
				return false;
		} else if (!lib.equals(other.lib))
			return false;
		if (montant == null) {
			if (other.montant != null)
				return false;
		} else if (!montant.equals(other.montant))
			return false;
		if (nat == null) {
			if (other.nat != null)
				return false;
		} else if (!nat.equals(other.nat))
			return false;
		if (ncp == null) {
			if (other.ncp != null)
				return false;
		} else if (!ncp.equals(other.ncp))
			return false;
		if (ncpDonneurOrdre == null) {
			if (other.ncpDonneurOrdre != null)
				return false;
		} else if (!ncpDonneurOrdre.equals(other.ncpDonneurOrdre))
			return false;
		if (ndoc == null) {
			if (other.ndoc != null)
				return false;
		} else if (!ndoc.equals(other.ndoc))
			return false;
		if (nomBeneficiaire == null) {
			if (other.nomBeneficiaire != null)
				return false;
		} else if (!nomBeneficiaire.equals(other.nomBeneficiaire))
			return false;
		if (nomBeneficiaireAmplitude == null) {
			if (other.nomBeneficiaireAmplitude != null)
				return false;
		} else if (!nomBeneficiaireAmplitude
				.equals(other.nomBeneficiaireAmplitude))
			return false;
		if (nomDonneurOrdre == null) {
			if (other.nomDonneurOrdre != null)
				return false;
		} else if (!nomDonneurOrdre.equals(other.nomDonneurOrdre))
			return false;
		if (ope == null) {
			if (other.ope != null)
				return false;
		} else if (!ope.equals(other.ope))
			return false;
		if (tope == null) {
			if (other.tope != null)
				return false;
		} else if (!tope.equals(other.tope))
			return false;
		if (zone == null) {
			if (other.zone != null)
				return false;
		} else if (!zone.equals(other.zone))
			return false;
		return true;
	}






}

