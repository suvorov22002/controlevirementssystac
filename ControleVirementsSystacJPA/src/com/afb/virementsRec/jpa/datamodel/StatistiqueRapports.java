package com.afb.virementsRec.jpa.datamodel;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: CodesBIC
 *
 */
@Entity
@Table(name = "VIR_SYSTAC_STAT_RAPPORT")
public class StatistiqueRapports implements Serializable {


	private static final long serialVersionUID = 1L;

	public StatistiqueRapports() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "RAPPORT")
	private String rapport;

	private Date dateRapport = new Date();
	
	@Enumerated(EnumType.STRING)
	private TypeProcess typeProcess;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRapport() {
		return rapport;
	}

	public void setRapport(String rapport) {
		this.rapport = rapport;
	}

	public Date getDateRapport() {
		return dateRapport;
	}

	public void setDateRapport(Date dateRapport) {
		this.dateRapport = dateRapport;
	}

	public TypeProcess getTypeProcess() {
		return typeProcess;
	}

	public void setTypeProcess(TypeProcess typeProcess) {
		this.typeProcess = typeProcess;
	}
	

}
