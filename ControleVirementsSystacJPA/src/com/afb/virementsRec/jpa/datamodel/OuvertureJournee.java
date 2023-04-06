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
@Table(name = "VIR_SYSTAC_OUVERTURE_JRNEE")
public class OuvertureJournee implements Serializable{


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
	private Date dateOuverture;
	
	private String heure;
	
	@Enumerated(EnumType.STRING)
	private TypeProcess typeProcess;
	
	@Column(length=1000000)
	private String fichiersRepEntree;
	
	@Column(length=1000000)
	private String fichiersRepSortie;
	
	@Transient
	private List<OuvertureJournee> items;
	

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

	public String getHeure() {
		return heure;
	}

	public void setHeure(String heure) {
		this.heure = heure;
	}


	public TypeProcess getTypeProcess() {
		return typeProcess;
	}

	public void setTypeProcess(TypeProcess typeProcess) {
		this.typeProcess = typeProcess;
	}

	public Date getDateOuverture() {
		return dateOuverture;
	}

	public void setDateOuverture(Date dateOuverture) {
		this.dateOuverture = dateOuverture;
	}

	public String getFichiersRepEntree() {
		return fichiersRepEntree;
	}

	public void setFichiersRepEntree(String fichiersRepEntree) {
		this.fichiersRepEntree = fichiersRepEntree;
	}

	public String getFichiersRepSortie() {
		return fichiersRepSortie;
	}

	public void setFichiersRepSortie(String fichiersRepSortie) {
		this.fichiersRepSortie = fichiersRepSortie;
	}

	public List<OuvertureJournee> getItems() {
		return items;
	}

	public void setItems(List<OuvertureJournee> items) {
		this.items = items;
	}

}

