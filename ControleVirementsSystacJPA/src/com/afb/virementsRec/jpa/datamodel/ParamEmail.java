package com.afb.virementsRec.jpa.datamodel;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: CodesBIC
 *
 */
@Entity
@Table(name = "VIR_SYSTAC_PARAM_EMAIL")
public class ParamEmail implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public ParamEmail() {
		super();
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "NOM")
	private String nom;
	
	@Column(name = "EMAIL")
	private String email;
	
	@ManyToOne
	@JoinColumn(name = "PARAM_TELECOMPENSE")
	private ParametragesGenTeleCompense param;
	
	@Column(name = "VALIDE")
	private Boolean valide = Boolean.TRUE;
	
	private Date dateCreation = new Date();
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public ParametragesGenTeleCompense getParam() {
		return param;
	}

	public void setParam(ParametragesGenTeleCompense param) {
		this.param = param;
	}

	public Boolean getValide() {
		return valide;
	}

	public void setValide(Boolean valide) {
		this.valide = valide;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	
}
