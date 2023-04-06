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

public class FichiersSupprimeACauseDeDoublonsNomsFichiers implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public FichiersSupprimeACauseDeDoublonsNomsFichiers() {
		super();
	}
	
	
	private Long id;
	
	
	private String fichier;
	
	

	@Transient
	private Boolean select = Boolean.FALSE;

	
	private Date dateTraitement = new Date();
	
	
	private TypeProcess typeProcess;
	
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

	public Boolean getSelect() {
		return select;
	}

	public void setSelect(Boolean select) {
		this.select = select;
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

	
}
