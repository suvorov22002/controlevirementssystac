package com.afb.virementsRec.jpa.datamodel;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

/**
 * Entity implementation class for Entity: Rapatriement Aller
 * Classe pour l'entité Doublons
 * @author Stéphane Mouafo
 *
 */
@Entity
@Table(name = "VIR_SYSTAC_TOURS_ALLER")
public class ToursAller implements Serializable{


	private static final long serialVersionUID = 1L;


	/**
	 * Identifiant
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	
	private Integer numeroTour;
	
	
	private String heure;
	
	@ManyToOne
	@JoinColumn(name = "ID_PARAM_COMPENSE_CENTR")
	private ParamCompensateurCentrale paramCompensateurCentrale;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getHeure() {
		return heure;
	}

	public void setHeure(String heure) {
		this.heure = heure;
	}

	public Integer getNumeroTour() {
		return numeroTour;
	}

	public void setNumeroTour(Integer numeroTour) {
		this.numeroTour = numeroTour;
	}

	public ParamCompensateurCentrale getParamCompensateurCentrale() {
		return paramCompensateurCentrale;
	}

	public void setParamCompensateurCentrale(
			ParamCompensateurCentrale paramCompensateurCentrale) {
		this.paramCompensateurCentrale = paramCompensateurCentrale;
	}

}

