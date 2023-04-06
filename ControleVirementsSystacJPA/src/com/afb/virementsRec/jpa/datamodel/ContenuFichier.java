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
@Table(name = "VIR_SYSTAC_CONTENU_FICHIER")
public class ContenuFichier implements Serializable{


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
	private Date dateCreation;
	
	private String heureCreation;
	
	private Integer numeroLigne;
	
	private String cleEnCours;

	@ManyToOne
	@JoinColumn(name = "ID_FICHIER")
	private Fichier fichier;
	
	private String sens;
	
	private String codeCentreCompensation;
	
	private String codePaysEmetteur;
	
	private String dateGeneration;
	
	private String heureGeneration;
	
	private String codeValeur;
	
	private String codeParticipantRemettant;
	
	private String datePresentation;
	
	private String datePresentationAppliquee;
	
	private String numeroDeRemise;
	
	private String codeEnregistrement;
	
	private String codeDevise;
	
	private String rang;
	
	private Double montantVirement;
	
	private String numeroVirement;
	
	private String RIBDonneurOrdre;
	
	private String nomEtPrenomDonneurOrdre;  //ou raison sociale
	
	private String codeParticipantDestinataire;
	
	private String codePaysDestinataire;
	
	private String RIBBeneficiaire;
	
	private String nomEtPrenomBeneficiaire;
	
	private String numeroReference;
	
	private String nombreEnregistrementsComp;
	
	private String motifOperation;
	
	private String dateReglement;
	
	private String motifRejet;
	
	private String zoneLibre;
	
	private Boolean isDoublonNomFic = Boolean.FALSE;
	
    private Boolean isDoublonDansFic = Boolean.FALSE;
    
    private Boolean isDoublonEntreFic = Boolean.FALSE;
    
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

	public Fichier getFichier() {
		return fichier;
	}

	public void setFichier(Fichier fichier) {
		this.fichier = fichier;
	}

	public String getSens() {
		return sens;
	}

	public void setSens(String sens) {
		this.sens = sens;
	}

	public String getCodeCentreCompensation() {
		return codeCentreCompensation;
	}

	public void setCodeCentreCompensation(String codeCentreCompensation) {
		this.codeCentreCompensation = codeCentreCompensation;
	}

	public String getCodePaysEmetteur() {
		return codePaysEmetteur;
	}

	public void setCodePaysEmetteur(String codePaysEmetteur) {
		this.codePaysEmetteur = codePaysEmetteur;
	}

	public String getDateGeneration() {
		return dateGeneration;
	}

	public void setDateGeneration(String dateGeneration) {
		this.dateGeneration = dateGeneration;
	}

	public String getHeureGeneration() {
		return heureGeneration;
	}

	public void setHeureGeneration(String heureGeneration) {
		this.heureGeneration = heureGeneration;
	}

	public String getCodeValeur() {
		return codeValeur;
	}

	public void setCodeValeur(String codeValeur) {
		this.codeValeur = codeValeur;
	}

	public String getCodeParticipantRemettant() {
		return codeParticipantRemettant;
	}

	public void setCodeParticipantRemettant(String codeParticipantRemettant) {
		this.codeParticipantRemettant = codeParticipantRemettant;
	}

	public String getDatePresentation() {
		return datePresentation;
	}

	public void setDatePresentation(String datePresentation) {
		this.datePresentation = datePresentation;
	}

	public String getDatePresentationAppliquee() {
		return datePresentationAppliquee;
	}

	public void setDatePresentationAppliquee(String datePresentationAppliquee) {
		this.datePresentationAppliquee = datePresentationAppliquee;
	}

	public String getNumeroDeRemise() {
		return numeroDeRemise;
	}

	public void setNumeroDeRemise(String numeroDeRemise) {
		this.numeroDeRemise = numeroDeRemise;
	}

	public String getCodeEnregistrement() {
		return codeEnregistrement;
	}

	public void setCodeEnregistrement(String codeEnregistrement) {
		this.codeEnregistrement = codeEnregistrement;
	}

	public String getCodeDevise() {
		return codeDevise;
	}

	public void setCodeDevise(String codeDevise) {
		this.codeDevise = codeDevise;
	}

	public String getRang() {
		return rang;
	}

	public void setRang(String rang) {
		this.rang = rang;
	}

	public Double getMontantVirement() {
		return montantVirement;
	}

	public void setMontantVirement(Double montantVirement) {
		this.montantVirement = montantVirement;
	}

	public String getNumeroVirement() {
		return numeroVirement;
	}

	public void setNumeroVirement(String numeroVirement) {
		this.numeroVirement = numeroVirement;
	}

	public String getRIBDonneurOrdre() {
		return RIBDonneurOrdre;
	}

	public void setRIBDonneurOrdre(String rIBDonneurOrdre) {
		RIBDonneurOrdre = rIBDonneurOrdre;
	}

	public String getNomEtPrenomDonneurOrdre() {
		return nomEtPrenomDonneurOrdre;
	}

	public void setNomEtPrenomDonneurOrdre(String nomEtPrenomDonneurOrdre) {
		this.nomEtPrenomDonneurOrdre = nomEtPrenomDonneurOrdre;
	}

	public String getCodeParticipantDestinataire() {
		return codeParticipantDestinataire;
	}

	public void setCodeParticipantDestinataire(String codeParticipantDestinataire) {
		this.codeParticipantDestinataire = codeParticipantDestinataire;
	}

	public String getCodePaysDestinataire() {
		return codePaysDestinataire;
	}

	public void setCodePaysDestinataire(String codePaysDestinataire) {
		this.codePaysDestinataire = codePaysDestinataire;
	}

	public String getRIBBeneficiaire() {
		return RIBBeneficiaire;
	}

	public void setRIBBeneficiaire(String rIBBeneficiaire) {
		RIBBeneficiaire = rIBBeneficiaire;
	}

	public String getNomEtPrenomBeneficiaire() {
		return nomEtPrenomBeneficiaire;
	}

	public void setNomEtPrenomBeneficiaire(String nomEtPrenomBeneficiaire) {
		this.nomEtPrenomBeneficiaire = nomEtPrenomBeneficiaire;
	}

	public String getNumeroReference() {
		return numeroReference;
	}

	public void setNumeroReference(String numeroReference) {
		this.numeroReference = numeroReference;
	}

	public String getNombreEnregistrementsComp() {
		return nombreEnregistrementsComp;
	}

	public void setNombreEnregistrementsComp(String nombreEnregistrementsComp) {
		this.nombreEnregistrementsComp = nombreEnregistrementsComp;
	}

	public String getMotifOperation() {
		return motifOperation;
	}

	public void setMotifOperation(String motifOperation) {
		this.motifOperation = motifOperation;
	}

	public String getDateReglement() {
		return dateReglement;
	}

	public void setDateReglement(String dateReglement) {
		this.dateReglement = dateReglement;
	}

	public String getMotifRejet() {
		return motifRejet;
	}

	public void setMotifRejet(String motifRejet) {
		this.motifRejet = motifRejet;
	}

	public String getZoneLibre() {
		return zoneLibre;
	}

	public void setZoneLibre(String zoneLibre) {
		this.zoneLibre = zoneLibre;
	}

	public Boolean getIsDoublonNomFic() {
		return isDoublonNomFic;
	}

	public void setIsDoublonNomFic(Boolean isDoublonNomFic) {
		this.isDoublonNomFic = isDoublonNomFic;
	}

	public Boolean getIsDoublonDansFic() {
		return isDoublonDansFic;
	}
	

	public void setIsDoublonDansFic(Boolean isDoublonDansFic) {
		this.isDoublonDansFic = isDoublonDansFic;
	}

	public Boolean getIsDoublonEntreFic() {
		return isDoublonEntreFic;
	}

	public void setIsDoublonEntreFic(Boolean isDoublonEntreFic) {
		this.isDoublonEntreFic = isDoublonEntreFic;
	}

	public Integer getNumeroLigne() {
		return numeroLigne;
	}

	public void setNumeroLigne(Integer numeroLigne) {
		this.numeroLigne = numeroLigne;
	}

	public Boolean getIsTraite() {
		return isTraite;
	}

	public void setIsTraite(Boolean isTraite) {
		this.isTraite = isTraite;
	}

	public String getCleEnCours() {
		return cleEnCours;
	}

	public void setCleEnCours(String cleEnCours) {
		this.cleEnCours = cleEnCours;
	}
	
}

