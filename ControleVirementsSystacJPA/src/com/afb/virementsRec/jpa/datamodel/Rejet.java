package com.afb.virementsRec.jpa.datamodel;

import java.io.Serializable;

import javax.persistence.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Entity implementation class for Entity: Rejet
 * Classe pour l'entité Rejet
 * @author Stéphane Mouafo
 *
 */
@Entity
@Table(name = "VIR_SYSTAC_REJET")
public class Rejet implements Serializable{


	private static final long serialVersionUID = 1L;


	/**
	 * Identifiant
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "REJET_ID")
	private Long id;

	
	@Column(name = "AVIS_IMPOSITION")
	private String avisImposition;
	
	@Column(name = "NUMERO_CONTRIBUABLE")
	private String numeroContribuable;
	
	@Column(name = "NCP_CONTRIBUABLE")
	private String ncpCtr;    

	@Column(name = "MONTANT_NOMINAL")
	private Double montantNom;
	
	@Column(name = "MOTIF_ORDRE_VIR")
	private String motifOrdreVirement;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_REJET")
	private Date dateRejet;

	@Column(name = "HEURE_REJET")
	private String heureRejet;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "MOTIF_REJET")
	private TypeRejet motifRejet;
	
	@Column(name = "CODE_MOTIF")
	private String codeModif;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_CREATION")
	private Date dateCreation =  new Date();

	@Column(name = "VALIDE")
	private Boolean valide = Boolean.TRUE;
	
	@Column(name = "REJET_FILE_NAME")
	private String rejetFileName;
	
	@Transient
	private List<Rejet> items = new ArrayList<Rejet>();
	

	public Rejet() {
		super();
	}


	public Rejet(String avisImposition, String numeroContribuable,
			String ncpCtr, Double montantNom, String motifOrdreVirement,
			Date dateRejet, String heureRejet, TypeRejet motifRejet, String codeMotif,
			Date dateCreation) {
		super();
		this.avisImposition = avisImposition;
		this.numeroContribuable = numeroContribuable;
		this.ncpCtr = ncpCtr;
		this.montantNom = montantNom;
		this.motifOrdreVirement = motifOrdreVirement;
		this.dateRejet = dateRejet;
		this.heureRejet = heureRejet;
		this.motifRejet = motifRejet;
		this.codeModif = codeMotif;
		this.dateCreation = dateCreation;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getAvisImposition() {
		return avisImposition;
	}


	public void setAvisImposition(String avisImposition) {
		this.avisImposition = avisImposition;
	}


	public String getNumeroContribuable() {
		return numeroContribuable;
	}


	public void setNumeroContribuable(String numeroContribuable) {
		this.numeroContribuable = numeroContribuable;
	}


	public String getNcpCtr() {
		return ncpCtr;
	}


	public void setNcpCtr(String ncpCtr) {
		this.ncpCtr = ncpCtr;
	}


	public Double getMontantNom() {
		return montantNom;
	}


	public void setMontantNom(Double montantNom) {
		this.montantNom = montantNom;
	}


	public String getMotifOrdreVirement() {
		return motifOrdreVirement;
	}


	public void setMotifOrdreVirement(String motifOrdreVirement) {
		this.motifOrdreVirement = motifOrdreVirement;
	}


	public Date getDateRejet() {
		return dateRejet;
	}


	public void setDateRejet(Date dateRejet) {
		this.dateRejet = dateRejet;
	}


	public String getHeureRejet() {
		return heureRejet;
	}


	public void setHeureRejet(String heureRejet) {
		this.heureRejet = heureRejet;
	}


	public TypeRejet getMotifRejet() {
		return motifRejet;
	}


	public void setMotifRejet(TypeRejet motifRejet) {
		this.motifRejet = motifRejet;
	}


	public String getCodeModif() {
		return codeModif;
	}


	public void setCodeModif(String codeModif) {
		this.codeModif = codeModif;
	}


	public Date getDateCreation() {
		return dateCreation;
	}


	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}


	public Boolean getValide() {
		return valide;
	}


	public void setValide(Boolean valide) {
		this.valide = valide;
	}


	public String getRejetFileName() {
		return rejetFileName;
	}


	public void setRejetFileName(String rejetFileName) {
		this.rejetFileName = rejetFileName;
	}


	public List<Rejet> getItems() {
		return items;
	}


	public void setItems(List<Rejet> items) {
		this.items = items;
	}


	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(10);
        System.out.println(df.format(montantNom));
        
		StringBuilder sb = new StringBuilder();
		sb.append(avisImposition).append('|').append(numeroContribuable).append('|').append(ncpCtr).append('|').append(df.format(montantNom)).append('|').append(motifOrdreVirement).append('|').append(new java.text.SimpleDateFormat("dd/MM/yyyy").format(dateRejet)).append('|').append(heureRejet).append('|').append(codeModif);
		return sb.toString();
	}
	
	
}

