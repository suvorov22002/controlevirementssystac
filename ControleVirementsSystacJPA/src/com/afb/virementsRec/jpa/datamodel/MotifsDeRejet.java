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
@Table(name = "VIR_SYSTAC_PARAM_MOTIF_REJET")
public class MotifsDeRejet implements Serializable {


	private static final long serialVersionUID = 1L;

	public MotifsDeRejet() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PARAM_ID")
	private Long id;

	@Enumerated(EnumType.STRING)
	private TypeRejet typeRejet;

	//private String motifRejet;

	@ManyToOne
	@JoinColumn(name = "PARAM_IMPOTS")
	private ParametragesImpots param;

	@Column(name = "VALIDE")
	private Boolean valide = Boolean.TRUE;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	/**public String getMotifRejet() {
		return motifRejet;
	}


	public void setMotifRejet(String motifRejet) {
		this.motifRejet = motifRejet;
	}*/


	public ParametragesImpots getParam() {
		return param;
	}


	public void setParam(ParametragesImpots param) {
		this.param = param;
	}


	public Boolean getValide() {
		return valide;
	}


	public void setValide(Boolean valide) {
		this.valide = valide;
	}


	public TypeRejet getTypeRejet() {
		return typeRejet;
	}


	public void setTypeRejet(TypeRejet typeRejet) {
		this.typeRejet = typeRejet;
	}


}
