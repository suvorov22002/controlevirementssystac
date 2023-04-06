package com.afb.virementsRec.jpa.datamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * Entity implementation class for Entity: ParamEmailAuto
 *
 */
@Entity
@Table(name = "VIR_SYSTAC_PARAM_COMPENSATEUR_CENTR")
public class ParamCompensateurCentrale implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public ParamCompensateurCentrale() {
		super();
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_PARAM")
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUT_JOURNEE")
	private StatutJournee statutJournee;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_JOURNEE")
	private Date dateJournee;
	
	@Column(name = "TOUR_MAX")
	private Integer tourMax;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUT_JOURNEE_EN_COURS")
	private StatutJournee statutJourneeEnCours;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_JOURNEE_EN_COURS")
	private Date dateJourneeEnCours;
	
	@Column(name = "TOUR_ACTUEL")
	private Integer tourActuel;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_ENREGISTREMENT")
	private Date dateEnregistrement = new Date();
	
	private String utiTraitement;
	
	//private String heureStartJournee;
	
	//private String heureFinJournee;
	

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade=CascadeType.ALL, mappedBy="paramCompensateurCentrale",fetch=FetchType.LAZY)
	private List<TourCompensation> listToursCompensation = new ArrayList<TourCompensation>();
	

	/*@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade=CascadeType.ALL, mappedBy="paramCompensateurCentrale",fetch=FetchType.LAZY)
	private List<ToursRetour> listToursRetour = new ArrayList<ToursRetour>();*/
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StatutJournee getStatutJournee() {
		return statutJournee;
	}

	public void setStatutJournee(StatutJournee statutJournee) {
		this.statutJournee = statutJournee;
	}

	public Date getDateJournee() {
		return dateJournee;
	}

	public void setDateJournee(Date dateJournee) {
		this.dateJournee = dateJournee;
	}

	public Integer getTourMax() {
		return tourMax;
	}

	public void setTourMax(Integer tourMax) {
		this.tourMax = tourMax;
	}

	public StatutJournee getStatutJourneeEnCours() {
		return statutJourneeEnCours;
	}

	public void setStatutJourneeEnCours(StatutJournee statutJourneeEnCours) {
		this.statutJourneeEnCours = statutJourneeEnCours;
	}

	public Date getDateJourneeEnCours() {
		return dateJourneeEnCours;
	}

	public void setDateJourneeEnCours(Date dateJourneeEnCours) {
		this.dateJourneeEnCours = dateJourneeEnCours;
	}

	public Integer getTourActuel() {
		return tourActuel;
	}

	public void setTourActuel(Integer tourActuel) {
		this.tourActuel = tourActuel;
	}

	public Date getDateEnregistrement() {
		return dateEnregistrement;
	}

	public void setDateEnregistrement(Date dateEnregistrement) {
		this.dateEnregistrement = dateEnregistrement;
	}

	public String getUtiTraitement() {
		return utiTraitement;
	}

	public void setUtiTraitement(String utiTraitement) {
		this.utiTraitement = utiTraitement;
	}

	/*public String getHeureStartJournee() {
		return heureStartJournee;
	}

	public void setHeureStartJournee(String heureStartJournee) {
		this.heureStartJournee = heureStartJournee;
	}

	public String getHeureFinJournee() {
		return heureFinJournee;
	}

	public void setHeureFinJournee(String heureFinJournee) {
		this.heureFinJournee = heureFinJournee;
	}*/

	public List<TourCompensation> getListToursCompensation() {
		return listToursCompensation;
	}

	public void setListToursCompensation(
			List<TourCompensation> listToursCompensation) {
		this.listToursCompensation = listToursCompensation;
	}
	
}
