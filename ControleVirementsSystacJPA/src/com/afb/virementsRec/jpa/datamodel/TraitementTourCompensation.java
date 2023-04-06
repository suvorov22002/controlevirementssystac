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
@Table(name = "VIR_SYSTAC_TRAITE_TOUR_COMPENSATION")
public class TraitementTourCompensation implements Serializable{


	private static final long serialVersionUID = 1L;


	/**
	 * Identifiant
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	
	private String utiTraitement;
	
	@Temporal(TemporalType.DATE)
	private Date dateTraitement;
	
	private String heure;
	
	@Enumerated(EnumType.STRING)
	private TypeTraitement typeTraitement;
	
	@Enumerated(EnumType.STRING)
	private SortTraitement sortTraitement;
	
	@Column(length = 1000000)
	private String fichiersTraite;
	
	private String valeurTraite;
	
	@Column(length = 1000000)
	private String fichiersSupprime;
	
	@ManyToOne
	@JoinColumn(name = "ID_TOUR_COMPENSATION")
	private TourCompensation tourCompensation;
	
	@Transient
	private List<TraitementTourCompensation> items;
	

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

	public Date getDateTraitement() {
		return dateTraitement;
	}

	public void setDateTraitement(Date dateTraitement) {
		this.dateTraitement = dateTraitement;
	}

	public String getHeure() {
		return heure;
	}

	public void setHeure(String heure) {
		this.heure = heure;
	}

	public TypeTraitement getTypeTraitement() {
		return typeTraitement;
	}

	public void setTypeTraitement(TypeTraitement typeTraitement) {
		this.typeTraitement = typeTraitement;
	}

	public SortTraitement getSortTraitement() {
		return sortTraitement;
	}

	public void setSortTraitement(SortTraitement sortTraitement) {
		this.sortTraitement = sortTraitement;
	}

	public String getFichiersTraite() {
		return fichiersTraite;
	}

	public void setFichiersTraite(String fichiersTraite) {
		this.fichiersTraite = fichiersTraite;
	}

	public TourCompensation getTourCompensation() {
		return tourCompensation;
	}

	public void setTourCompensation(TourCompensation tourCompensation) {
		this.tourCompensation = tourCompensation;
	}

	public List<TraitementTourCompensation> getItems() {
		return items;
	}

	public void setItems(List<TraitementTourCompensation> items) {
		this.items = items;
	}

	public String getFichiersSupprime() {
		return fichiersSupprime;
	}

	public void setFichiersSupprime(String fichiersSupprime) {
		this.fichiersSupprime = fichiersSupprime;
	}

	public String getValeurTraite() {
		return valeurTraite;
	}

	public void setValeurTraite(String valeurTraite) {
		this.valeurTraite = valeurTraite;
	}
	

}

