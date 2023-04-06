package com.afb.virementsRec.jpa.datamodel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * Entity implementation class for Entity: Parametrages
 *
 */
@Entity
@Table(name = "VIR_SYSTAC_PARAM_IMPOTS")
public class ParametragesImpots implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public ParametragesImpots() {
		super();
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PARAM_ID")
	private Long id;
	
	/**@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade=CascadeType.ALL, mappedBy="param",fetch=FetchType.LAZY)
	private List<CaracteristiquesVir> typeCarac = new ArrayList<CaracteristiquesVir>();*/

	@Column(name = "REP_ENTREE")
	private String repertoireEntree;
	
	@Column(name = "REP_ARCH_ENTREE")
	private String repertoireArchiveEntree;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date tempsArchivage;

	/**
	 * Agence de saisie
	 */
	@Column(name = "AGSA")
	private String agsa = "00001";
	
	/**
	 * Code opération
	 */
	@Column(name = "OPE_SYSTAC")
	private String opeSYSTAC  = "137";
	
	/**
	 * Code opération
	 */
	@Column(name = "OPE_SYGMA")
	private String opeSYGMA = "437";
	
	/**
	 * Type rattaché
	 */
	@Column(name = "TYP_SYSTAC")
	private String typSYSTAC = "100";
	
	/**
	 * Type rattaché
	 */
	@Column(name = "TYP_SYGMA")
	private String typSYGMA = "400";
	
	/**
	 * Devise compte 1
	 */
	@Column(name = "DEV1")
	private String dev1 = "001"; 
	
	/**
	 * Indisponible hors SBF du compte 1 avant opération
	 */
	@Column(name = "INDH1")
	private Double indh1 = 0.0000;
	
	/**
	 * Indisponible SBF du compte 1 avant opération
	 */
	@Column(name = "INDS1")
	private Double inds1 = 0.0000;
	
	/**
	 * Devise transaction
	 */
	@Column(name = "DEV")
	private String dev = "001"; 
	

	/**
	 * Nature de la transaction
	 */
	@Column(name = "NAT")
	private String nat = "VIRBAN"; 


	/**
	 * Référence de lettrage
	 */
	@Column(name = "RLET")
	private String rlet = "18991231"; ////seulment pour Systac
	

	/**
	 * Devise du compte frais
	 */
	@Column(name = "DEVF")
	private String devf = "001"; 

	/**
	 * Pourcentage retenue de garantie
	 */
	@Column(name = "PRGA")
	private Double prga = 0.0000000; 
	
	@Column(name = "MONTANT_FRAIS_SYSTAC")
	private Integer montantFraisSystac = 4000;
	
	@Column(name = "TAUX_TAX")
	private Double tauxTax = 0.1925; //19.25;
	
	@Column(name = "MNT_PLAF_SYSTAC")
	private Double montantPlafondSystac = 5000000d;
	
	@Column(name = "MNT_PLAF_SYGMA")
	private Double montantPlafondSYGMA = 5000000d;
	
	@Column(name = "MONTANT_FRAIS_SYGMA")
	private Integer montantFraisSYGMA = 30000;
	
	private Integer montantMaxSYSTAC = 100000000;
	
	private String libelleDesaccordDebit="B/DT";
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade=CascadeType.ALL, mappedBy="param",fetch=FetchType.LAZY)
	private List<MotifsDeRejet> motifsDeRejet = new ArrayList<MotifsDeRejet>();
	
	private Integer palierMinSystac = 500;
	
	private Integer palierMaxSystac = 10000;
	
	private Integer palierMinSygma = 500;
	
	private Integer palierMaxSygma = 10000;
	
	private Double pourcentageFraisImpots = 0.1;
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getRepertoireEntree() {
		return repertoireEntree;
	}


	public void setRepertoireEntree(String repertoireEntree) {
		this.repertoireEntree = repertoireEntree;
	}


	public String getRepertoireArchiveEntree() {
		return repertoireArchiveEntree;
	}


	public void setRepertoireArchiveEntree(String repertoireArchiveEntree) {
		this.repertoireArchiveEntree = repertoireArchiveEntree;
	}


	public Date getTempsArchivage() {
		return tempsArchivage;
	}


	public void setTempsArchivage(Date tempsArchivage) {
		this.tempsArchivage = tempsArchivage;
	}


	public String getAgsa() {
		return agsa;
	}


	public void setAgsa(String agsa) {
		this.agsa = agsa;
	}



	public String getOpeSYSTAC() {
		return opeSYSTAC;
	}


	public void setOpeSYSTAC(String opeSYSTAC) {
		this.opeSYSTAC = opeSYSTAC;
	}


	public String getOpeSYGMA() {
		return opeSYGMA;
	}


	public void setOpeSYGMA(String opeSYGMA) {
		this.opeSYGMA = opeSYGMA;
	}


	public String getTypSYSTAC() {
		return typSYSTAC;
	}


	public void setTypSYSTAC(String typSYSTAC) {
		this.typSYSTAC = typSYSTAC;
	}


	public String getTypSYGMA() {
		return typSYGMA;
	}


	public void setTypSYGMA(String typSYGMA) {
		this.typSYGMA = typSYGMA;
	}


	public Integer getMontantFraisSYGMA() {
		return montantFraisSYGMA;
	}


	public void setMontantFraisSYGMA(Integer montantFraisSYGMA) {
		this.montantFraisSYGMA = montantFraisSYGMA;
	}


	public String getDev1() {
		return dev1;
	}


	public void setDev1(String dev1) {
		this.dev1 = dev1;
	}


	public Double getIndh1() {
		return indh1;
	}


	public void setIndh1(Double indh1) {
		this.indh1 = indh1;
	}


	public Double getInds1() {
		return inds1;
	}


	public void setInds1(Double inds1) {
		this.inds1 = inds1;
	}


	public String getDev() {
		return dev;
	}


	public void setDev(String dev) {
		this.dev = dev;
	}


	public String getNat() {
		return nat;
	}


	public void setNat(String nat) {
		this.nat = nat;
	}


	public String getRlet() {
		return rlet;
	}


	public void setRlet(String rlet) {
		this.rlet = rlet;
	}


	public String getDevf() {
		return devf;
	}


	public void setDevf(String devf) {
		this.devf = devf;
	}


	public Double getPrga() {
		return prga;
	}


	public void setPrga(Double prga) {
		this.prga = prga;
	}


	public Integer getMontantFraisSystac() {
		return montantFraisSystac;
	}


	public void setMontantFraisSystac(Integer montantFraisSystac) {
		this.montantFraisSystac = montantFraisSystac;
	}


	public Double getTauxTax() {
		return tauxTax;
	}


	public void setTauxTax(Double tauxTax) {
		this.tauxTax = tauxTax;
	}


	public Double getMontantPlafondSystac() {
		return montantPlafondSystac;
	}


	public void setMontantPlafondSystac(Double montantPlafondSystac) {
		this.montantPlafondSystac = montantPlafondSystac;
	}


	public Double getMontantPlafondSYGMA() {
		return montantPlafondSYGMA;
	}


	public void setMontantPlafondSYGMA(Double montantPlafondSYGMA) {
		this.montantPlafondSYGMA = montantPlafondSYGMA;
	}


	public String getLibelleDesaccordDebit() {
		return libelleDesaccordDebit;
	}


	public void setLibelleDesaccordDebit(String libelleDesaccordDebit) {
		this.libelleDesaccordDebit = libelleDesaccordDebit;
	}


	public List<MotifsDeRejet> getMotifsDeRejet() {
		return motifsDeRejet;
	}


	public void setMotifsDeRejet(List<MotifsDeRejet> motifsDeRejet) {
		this.motifsDeRejet = motifsDeRejet;
	}


	public Integer getMontantMaxSYSTAC() {
		return montantMaxSYSTAC;
	}


	public void setMontantMaxSYSTAC(Integer montantMaxSYSTAC) {
		this.montantMaxSYSTAC = montantMaxSYSTAC;
	}


	public Integer getPalierMinSystac() {
		return palierMinSystac;
	}


	public void setPalierMinSystac(Integer palierMinSystac) {
		this.palierMinSystac = palierMinSystac;
	}


	public Integer getPalierMaxSystac() {
		return palierMaxSystac;
	}


	public void setPalierMaxSystac(Integer palierMaxSystac) {
		this.palierMaxSystac = palierMaxSystac;
	}


	public Integer getPalierMinSygma() {
		return palierMinSygma;
	}


	public void setPalierMinSygma(Integer palierMinSygma) {
		this.palierMinSygma = palierMinSygma;
	}


	public Integer getPalierMaxSygma() {
		return palierMaxSygma;
	}


	public void setPalierMaxSygma(Integer palierMaxSygma) {
		this.palierMaxSygma = palierMaxSygma;
	}


	public Double getPourcentageFraisImpots() {
		return pourcentageFraisImpots;
	}


	public void setPourcentageFraisImpots(Double pourcentageFraisImpots) {
		this.pourcentageFraisImpots = pourcentageFraisImpots;
	}


	
}
