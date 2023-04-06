package com.afb.virementsRec.jpa.datamodel;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Entity implementation class for Entity: Rapatriement Aller
 * Classe pour l'entité Doublons
 * //@author Stéphane Mouafo
 *
 */
//@Entity
//@Table(name = "VIR_SYSTAC_RAPATRIEMENT_ALLER")
public class RapatriementImagesAller implements Serializable{


	private static final long serialVersionUID = 1L;


	/**
	 * Identifiant
	 */
	//@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@Column(name = "ID")
	private Long id;

	//@OneToMany(mappedBy = "rapatriementImagesAller", cascade = {CascadeType.ALL})
	//@LazyCollection(LazyCollectionOption.FALSE)
	private List<TourCompensation> tourCompensation;
	
	//@Temporal(TemporalType.TIMESTAMP)
	private Date dateTraitement;
	
	private String utiTraitement;
	

	public String getUtiTraitement() {
		return utiTraitement;
	}

	public void setUtiTraitement(String utiTraitement) {
		this.utiTraitement = utiTraitement;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<TourCompensation> getTourCompensation() {
		return tourCompensation;
	}

	public void setTourCompensation(List<TourCompensation> tourCompensation) {
		this.tourCompensation = tourCompensation;
	}

	public Date getDateTraitement() {
		return dateTraitement;
	}

	public void setDateTraitement(Date dateTraitement) {
		this.dateTraitement = dateTraitement;
	}

}

