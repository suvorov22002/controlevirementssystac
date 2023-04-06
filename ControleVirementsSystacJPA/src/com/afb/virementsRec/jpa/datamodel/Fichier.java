package com.afb.virementsRec.jpa.datamodel;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.Date;
import java.util.List;

/**
 * Entity implementation class for Entity: Fichier
 * Classe pour l'entité Fichier
 * @author Stéphane Mouafo
 *
 */
@Entity
@Table(name = "VIR_SYSTAC_FICHIER")
public class Fichier implements Serializable{


	private static final long serialVersionUID = 1L;


	/**
	 * Identifiant
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	
	@Column(length = 1000000)
	private String nomFichier;
	
	@Temporal(TemporalType.DATE)
	private Date dateCreation;
	
	private String heureCreation;

	private String utiTraitement;

	@OneToMany(mappedBy = "fichier", cascade = {CascadeType.ALL})
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<ContenuFichier> listContenus;
	
	@ManyToOne
	@JoinColumn(name = "ID_TOUR_COMPENSATION")
	private TourCompensation tourCompensation;
	
    private Boolean isTraite = Boolean.FALSE;

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUtiTraitement() {
		return utiTraitement;
	}

	public void setUtiTraitement(String utiTraitement) {
		this.utiTraitement = utiTraitement;
	}

	public String getNomFichier() {
		return nomFichier;
	}

	public void setNomFichier(String nomFichier) {
		this.nomFichier = nomFichier;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getHeureCreation() {
		return heureCreation;
	}

	public void setHeureCreation(String heureCreation) {
		this.heureCreation = heureCreation;
	}

	public List<ContenuFichier> getListContenus() {
		return listContenus;
	}

	public void setListContenus(List<ContenuFichier> listContenus) {
		this.listContenus = listContenus;
	}

	public Boolean getIsTraite() {
		return isTraite;
	}

	public void setIsTraite(Boolean isTraite) {
		this.isTraite = isTraite;
	}

	public TourCompensation getTourCompensation() {
		return tourCompensation;
	}

	public void setTourCompensation(TourCompensation tourCompensation) {
		this.tourCompensation = tourCompensation;
	}

	
}

