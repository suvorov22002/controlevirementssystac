package com.afb.virementsRec.jpa.datamodel;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * Entity implementation class for Entity: ParametragesGenTeleCompense
 *
 */
@Entity
@Table(name = "VIR_SYSTAC_PARAM_MOTS_CLE")
public class MotsClesEtCharSpeciaux implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public MotsClesEtCharSpeciaux() {
		super();
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PARAM_ID")
	private Long id;
	
	private String motCles;
	
	@ManyToOne
	@JoinColumn(name = "PARAM")
	private Parametrages param;
	
	@Column(name = "VALIDE")
	private Boolean valide = Boolean.TRUE;

	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	


	public String getMotCles() {
		return motCles;
	}


	public void setMotCles(String motCles) {
		this.motCles = motCles;
	}


	public Parametrages getParam() {
		return param;
	}


	public void setParam(Parametrages param) {
		this.param = param;
	}


	public Boolean getValide() {
		return valide;
	}


	public void setValide(Boolean valide) {
		this.valide = valide;
	}

	


   
}
