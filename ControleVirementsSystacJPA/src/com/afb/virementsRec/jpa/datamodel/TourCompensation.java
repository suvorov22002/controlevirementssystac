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
 * @author Stéphane Mouafo
 *
 */
@Entity
@Table(name = "VIR_SYSTAC_TOUR_COMPENSATION")
public class TourCompensation implements Serializable, Comparable<TourCompensation>{


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

	private Integer numeroTour;

	@Column(length = 1000000)
	private String fichiersTraite;

	@Enumerated(EnumType.STRING)
	private TypeProcess typeProcess;

	@Enumerated(EnumType.STRING)
	private TypePhase typePhase;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade=CascadeType.ALL, mappedBy="tourCompensation",fetch=FetchType.LAZY)
	private List<TraitementTourCompensation> listTraitementTour = new ArrayList<TraitementTourCompensation>();
	
	@OneToMany(mappedBy = "tourCompensation", cascade = {CascadeType.ALL})
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Fichier> listFichier;

	/**@ManyToOne
	@JoinColumn(name = "ID_RAPATRIEMENT_ALLER")
	private RapatriementImagesAller rapatriementImagesAller;

	@ManyToOne
	@JoinColumn(name = "ID_FICHIERS_COMPTA_ALLER")
	private FichiersComptabilisationAller fichiersComptabilisationAller;

	@ManyToOne
	@JoinColumn(name = "ID_FICHIERS_COMPTA_RETOUR")
	private FichiersComptabilisationRetour fichiersComptabilisationRetour;

	@ManyToOne
	@JoinColumn(name = "ID_RAPATRIEMENT_RETOUR")
	private RapatriementImagesRetour rapatriementImagesRetour;*/

	@ManyToOne
	@JoinColumn(name = "ID_PARAM_COMPENSE_CENTR")
	private ParamCompensateurCentrale paramCompensateurCentrale;

	private Integer nbrFichiersACopiers;

	private Integer nbrFichiersCopies;

	private Integer nbrDoublons; //doublons nom fic

	private Integer nbrFichContenantDoublons;

	private Integer nbrValeursEnDouble; //doublons dans et entre fic

	private Integer nbrValeursDeposees;

	private Boolean valide = Boolean.TRUE;


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

	public List<TraitementTourCompensation> getListTraitementTour() {
		return listTraitementTour;
	}

	public void setListTraitementTour(
			List<TraitementTourCompensation> listTraitementTour) {
		this.listTraitementTour = listTraitementTour;
	}

	public Integer getNumeroTour() {
		return numeroTour;
	}

	public void setNumeroTour(Integer numeroTour) {
		this.numeroTour = numeroTour;
	}

	/**
	 public RapatriementImagesAller getRapatriementImagesAller() {
		return rapatriementImagesAller;
	}

	public void setRapatriementImagesAller(
			RapatriementImagesAller rapatriementImagesAller) {
		this.rapatriementImagesAller = rapatriementImagesAller;
	}

	 public FichiersComptabilisationAller getFichiersComptabilisationAller() {
		return fichiersComptabilisationAller;
	}

	public void setFichiersComptabilisationAller(
			FichiersComptabilisationAller fichiersComptabilisationAller) {
		this.fichiersComptabilisationAller = fichiersComptabilisationAller;
	}

	public FichiersComptabilisationRetour getFichiersComptabilisationRetour() {
		return fichiersComptabilisationRetour;
	}

	public void setFichiersComptabilisationRetour(
			FichiersComptabilisationRetour fichiersComptabilisationRetour) {
		this.fichiersComptabilisationRetour = fichiersComptabilisationRetour;
	}

	public RapatriementImagesRetour getRapatriementImagesRetour() {
		return rapatriementImagesRetour;
	}

	public void setRapatriementImagesRetour(
			RapatriementImagesRetour rapatriementImagesRetour) {
		this.rapatriementImagesRetour = rapatriementImagesRetour;
	}*/

	public String getFichiersTraite() {
		return fichiersTraite;
	}

	public void setFichiersTraite(String fichiersTraite) {
		this.fichiersTraite = fichiersTraite;
	}

	public TypeProcess getTypeProcess() {
		return typeProcess;
	}

	public void setTypeProcess(TypeProcess typeProcess) {
		this.typeProcess = typeProcess;
	}

	public Integer getNbrFichiersACopiers() {
		return nbrFichiersACopiers;
	}

	public void setNbrFichiersACopiers(Integer nbrFichiersACopiers) {
		this.nbrFichiersACopiers = nbrFichiersACopiers;
	}

	public Integer getNbrFichiersCopies() {
		return nbrFichiersCopies;
	}

	public void setNbrFichiersCopies(Integer nbrFichiersCopies) {
		this.nbrFichiersCopies = nbrFichiersCopies;
	}

	public Integer getNbrDoublons() {
		return nbrDoublons;
	}

	public void setNbrDoublons(Integer nbrDoublons) {
		this.nbrDoublons = nbrDoublons;
	}

	public Integer getNbrFichContenantDoublons() {
		return nbrFichContenantDoublons;
	}

	public void setNbrFichContenantDoublons(Integer nbrFichContenantDoublons) {
		this.nbrFichContenantDoublons = nbrFichContenantDoublons;
	}

	public Integer getNbrValeursEnDouble() {
		return nbrValeursEnDouble;
	}

	public void setNbrValeursEnDouble(Integer nbrValeursEnDouble) {
		this.nbrValeursEnDouble = nbrValeursEnDouble;
	}

	public Integer getNbrValeursDeposees() {
		return nbrValeursDeposees;
	}

	public void setNbrValeursDeposees(Integer nbrValeursDeposees) {
		this.nbrValeursDeposees = nbrValeursDeposees;
	}

	public TypePhase getTypePhase() {
		return typePhase;
	}

	public void setTypePhase(TypePhase typePhase) {
		this.typePhase = typePhase;
	}

	public ParamCompensateurCentrale getParamCompensateurCentrale() {
		return paramCompensateurCentrale;
	}

	public void setParamCompensateurCentrale(
			ParamCompensateurCentrale paramCompensateurCentrale) {
		this.paramCompensateurCentrale = paramCompensateurCentrale;
	}

	public Boolean getValide() {
		return valide;
	}

	public void setValide(Boolean valide) {
		this.valide = valide;
	}

	public List<Fichier> getListFichier() {
		return listFichier;
	}

	public void setListFichier(List<Fichier> listFichier) {
		this.listFichier = listFichier;
	}

	@Override
	public int compareTo(TourCompensation o) {
		// TODO Auto-generated method stub
		return this.getNumeroTour().compareTo(o.getNumeroTour());
	}
}

