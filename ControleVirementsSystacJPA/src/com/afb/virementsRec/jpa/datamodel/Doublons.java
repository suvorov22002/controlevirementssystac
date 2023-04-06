package com.afb.virementsRec.jpa.datamodel;

import java.io.Serializable;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Entity implementation class for Entity: Doublons
 * Classe pour l'entité Doublons
 * @author Stéphane Mouafo
 *
 */
@Entity
@Table(name = "VIR_SYSTAC_DOUBLONS")
public class Doublons implements Serializable{


	private static final long serialVersionUID = 1L;


	/**
	 * Identifiant
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DOUBLON_ID")
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
	private Boolean valide = Boolean.FALSE;
	
	/*@Column(name = "TRAITE_DOUBLONS")
	private Boolean traiteDoublons = Boolean.FALSE;
	
	@Column(name = "TRAITE_INCOHERENCES")
	private Boolean traiteIncoherences = Boolean.FALSE;*/
	
	@Column(name = "DATE_TRAITEMENT")
	private Date dateTraitement;
	
	@Transient
	private List<Doublons> items = new ArrayList<Doublons>();

	@Transient
	private Boolean select = Boolean.FALSE;
	
	@Column(name = "COLOR")
	private String color;
	
	@Column(name = "DISABLE")
	private Boolean disable = Boolean.FALSE;
	
	
	


	public Doublons() {
		super();
	}





	public Doublons(String agence, String ncp, String cle, String devise,
			String nomBeneficiaire, String nomBeneficiaireAmplitude,Date dsai, Date dco, Date dva, String ope,String eve,
			Double montant, String lib, String etat, String uti,
			String codeEtabDonneurOrdre, String codeGuicherDonneurOrdre,
			String ncpDonneurOrdre, String cleDonneurOrdre,
			String nomDonneurOrdre,
			Date dateTraitement, String color, String utiPortal) {
		super();
		this.agence = agence;
		this.ncp = ncp;
		this.cle = cle;
		this.devise = devise;
		this.nomBeneficiaire = nomBeneficiaire;
		this.nomBeneficiaireAmplitude=nomBeneficiaireAmplitude;
		this.dsai=dsai;
		this.dco = dco;
		this.dva = dva;
		this.ope = ope;
		this.eve=eve;
		this.montant = montant;
		this.lib = lib;
		this.etat = etat;
		this.uti = uti;
		this.codeEtabDonneurOrdre = codeEtabDonneurOrdre;
		this.codeGuicherDonneurOrdre = codeGuicherDonneurOrdre;
		this.ncpDonneurOrdre = ncpDonneurOrdre;
		this.cleDonneurOrdre = cleDonneurOrdre;
		this.nomDonneurOrdre = nomDonneurOrdre;
		this.dateTraitement = dateTraitement;
		this.color = color;
		this.utiPortal=utiPortal;
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


	public List<Doublons> getItems() {
		return items;
	}





	public void setItems(List<Doublons> items) {
		this.items = items;
	}





	public Boolean getSelect() {
		return select;
	}





	public void setSelect(Boolean select) {
		this.select = select;
	}





	public String getColor() {
		return color;
	}





	public void setColor(String color) {
		this.color = color;
	}





	public Boolean getDisable() {
		return disable;
	}





	public void setDisable(Boolean disable) {
		this.disable = disable;
	}



	public String getUtiPortal() {
		return utiPortal;
	}





	public void setUtiPortal(String utiPortal) {
		this.utiPortal = utiPortal;
	}
	
	



}

