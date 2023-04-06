package com.afb.virementsRec.jpa.datamodel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/*****
 * 
 * @author stephane_mouafo
 *
 */

@Entity
@Table(name = "VIR_SYSTAC_VALIDATE_DOUB")
public class ValidateDoublonsInFichier implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public ValidateDoublonsInFichier() {
		super();
	}
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "FICHIER")
	private String fichier;
	
	
	@Column(name = "DOUBLONS", length = 100000)
	private String doublons;
	
	@Column(name = "COLOR")
	private String color;
	
	@Transient
	private List<ValidateDoublonsInFichier> subReportDoublonsDansFichiers;
	
	@Transient
	private List<FichiersSupprimeACauseDeValeursEnDoublonsDansAutreFichier> fichiersSupprimeACauseDeValeursEnDoublonsDansAutreFichiers;
	
	@Transient
	private List<FichiersSupprimeACauseDeDoublonsNomsFichiers> fichiersSupprimeACauseDeDoublonsNomsFichiers;
	
	@Transient
	private Boolean select = Boolean.FALSE;
	
	@Column(name = "VALIDE")
	private Boolean valide = Boolean.FALSE;
	
	@Column(name = "DATE_TRAITEMENT")
	private Date dateTraitement;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE_PROCESS")
	private TypeProcess typeProcess;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE_TRAITEMENT")
	private TypeTraitement typeTraitement;
	
	@Column(name = "DISABLE")
	private Boolean disable = Boolean.FALSE;
	
	@Column(name = "UTI")
	private String uti = "";
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFichier() {
		return fichier;
	}

	public void setFichier(String fichier) {
		this.fichier = fichier;
	}

	public String getDoublons() {
		return doublons;
	}

	public void setDoublons(String doublons) {
		this.doublons = doublons;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public List<ValidateDoublonsInFichier> getSubReportDoublonsDansFichiers() {
		return subReportDoublonsDansFichiers;
	}

	public void setSubReportDoublonsDansFichiers(
			List<ValidateDoublonsInFichier> subReportDoublonsDansFichiers) {
		this.subReportDoublonsDansFichiers = subReportDoublonsDansFichiers;
	}

	public Boolean getSelect() {
		return select;
	}

	public void setSelect(Boolean select) {
		this.select = select;
	}

	public Boolean getValide() {
		return valide;
	}

	public void setValide(Boolean valide) {
		this.valide = valide;
	}

	public Date getDateTraitement() {
		return dateTraitement;
	}

	public void setDateTraitement(Date dateTraitement) {
		this.dateTraitement = dateTraitement;
	}

	public TypeProcess getTypeProcess() {
		return typeProcess;
	}

	public void setTypeProcess(TypeProcess typeProcess) {
		this.typeProcess = typeProcess;
	}

	public List<FichiersSupprimeACauseDeValeursEnDoublonsDansAutreFichier> getFichiersSupprimeACauseDeValeursEnDoublonsDansAutreFichiers() {
		return fichiersSupprimeACauseDeValeursEnDoublonsDansAutreFichiers;
	}

	public void setFichiersSupprimeACauseDeValeursEnDoublonsDansAutreFichiers(
			List<FichiersSupprimeACauseDeValeursEnDoublonsDansAutreFichier> fichiersSupprimeACauseDeValeursEnDoublonsDansAutreFichiers) {
		this.fichiersSupprimeACauseDeValeursEnDoublonsDansAutreFichiers = fichiersSupprimeACauseDeValeursEnDoublonsDansAutreFichiers;
	}

	public List<FichiersSupprimeACauseDeDoublonsNomsFichiers> getFichiersSupprimeACauseDeDoublonsNomsFichiers() {
		return fichiersSupprimeACauseDeDoublonsNomsFichiers;
	}

	public void setFichiersSupprimeACauseDeDoublonsNomsFichiers(
			List<FichiersSupprimeACauseDeDoublonsNomsFichiers> fichiersSupprimeACauseDeDoublonsNomsFichiers) {
		this.fichiersSupprimeACauseDeDoublonsNomsFichiers = fichiersSupprimeACauseDeDoublonsNomsFichiers;
	}

	public Boolean getDisable() {
		return disable;
	}

	public void setDisable(Boolean disable) {
		this.disable = disable;
	}

	public TypeTraitement getTypeTraitement() {
		return typeTraitement;
	}

	public void setTypeTraitement(TypeTraitement typeTraitement) {
		this.typeTraitement = typeTraitement;
	}

	public String getUti() {
		return uti;
	}

	public void setUti(String uti) {
		this.uti = uti;
	}


}
