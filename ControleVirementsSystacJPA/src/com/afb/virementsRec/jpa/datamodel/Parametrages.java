package com.afb.virementsRec.jpa.datamodel;

import java.io.Serializable;
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
@Table(name = "VIR_SYSTAC_PARAMS")
public class Parametrages implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public Parametrages() {
		super();
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PARAM_ID")
	private Long id;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade=CascadeType.ALL, mappedBy="param",fetch=FetchType.LAZY)
	private List<CaracteristiquesVir> typeCarac = new ArrayList<CaracteristiquesVir>();
	

	@Column(name = "DERN_ELEMENT_LOT_DOUB")
	private Long lastElementOfLotDoublons;

	@Column(name = "DERN_ELEMENT_LOT_INCO")
	private Long lastElementOfLotIncoherences;
	
	@Column(name = "MONTANT_PLANCHER")
	private Double montant; //plancher pour filtrer incohérences
	
	@Column(name = "CODE_OPPO")
	private String opp; //code opposition
	
	@Column(name = "CODE_OPE_SYSTAC")
	private String opeSystac; //code ope vir reçu systac
	
	private Double seuil;
	
	private Boolean gestionSigle = Boolean.FALSE;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade=CascadeType.ALL, mappedBy="param",fetch=FetchType.LAZY)
	private List<MotsClesEtCharSpeciaux> motsClesEtCharSpeciauxs = new ArrayList<MotsClesEtCharSpeciaux>();
	
	private String nature = "RCP";
	
	private String tope = "10,13";
	
	private String codeEnreg = "21";
	
	private String chapitres = "38,54,56";
	
	private Boolean gestionAlgo2 = Boolean.FALSE;  //Algo Division Euclidienne
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public List<CaracteristiquesVir> getTypeCarac() {
		return typeCarac;
	}


	public void setTypeCarac(List<CaracteristiquesVir> typeCarac) {
		this.typeCarac = typeCarac;
	}


	public Long getLastElementOfLotDoublons() {
		return lastElementOfLotDoublons;
	}


	public void setLastElementOfLotDoublons(Long lastElementOfLotDoublons) {
		this.lastElementOfLotDoublons = lastElementOfLotDoublons;
	}


	public Long getLastElementOfLotIncoherences() {
		return lastElementOfLotIncoherences;
	}


	public void setLastElementOfLotIncoherences(Long lastElementOfLotIncoherences) {
		this.lastElementOfLotIncoherences = lastElementOfLotIncoherences;
	}


	public Double getMontant() {
		return montant;
	}


	public void setMontant(Double montant) {
		this.montant = montant;
	}


	public String getOpp() {
		return opp;
	}


	public void setOpp(String opp) {
		this.opp = opp;
	}


	public String getOpeSystac() {
		return opeSystac;
	}


	public void setOpeSystac(String opeSystac) {
		this.opeSystac = opeSystac;
	}


	public Double getSeuil() {
		return seuil;
	}


	public void setSeuil(Double seuil) {
		this.seuil = seuil;
	}


	public Boolean getGestionSigle() {
		return gestionSigle;
	}


	public void setGestionSigle(Boolean gestionSigle) {
		this.gestionSigle = gestionSigle;
	}


	public List<MotsClesEtCharSpeciaux> getMotsClesEtCharSpeciauxs() {
		return motsClesEtCharSpeciauxs;
	}


	public void setMotsClesEtCharSpeciauxs(
			List<MotsClesEtCharSpeciaux> motsClesEtCharSpeciauxs) {
		this.motsClesEtCharSpeciauxs = motsClesEtCharSpeciauxs;
	}


	public String getNature() {
		return nature;
	}


	public void setNature(String nature) {
		this.nature = nature;
	}


	public String getTope() {
		return tope;
	}


	public void setTope(String tope) {
		this.tope = tope;
	}


	public String getCodeEnreg() {
		return codeEnreg;
	}


	public void setCodeEnreg(String codeEnreg) {
		this.codeEnreg = codeEnreg;
	}


	public String getChapitres() {
		return chapitres;
	}


	public void setChapitres(String chapitres) {
		this.chapitres = chapitres;
	}


	public Boolean getGestionAlgo2() {
		return gestionAlgo2;
	}


	public void setGestionAlgo2(Boolean gestionAlgo2) {
		this.gestionAlgo2 = gestionAlgo2;
	}
}
