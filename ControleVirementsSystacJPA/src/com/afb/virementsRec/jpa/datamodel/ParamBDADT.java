package com.afb.virementsRec.jpa.datamodel;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: CodesBIC
 *
 */
//@Entity
//@Table(name = "VIR_SYSTAC_PARAM_BD_ADT")
public class ParamBDADT implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public ParamBDADT() {
		super();
	}
	
	//@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
	//@Column(name = "ID")
	private Long id;
	
	//@Column(name = "UTILISATEUR")
	private String user;
	
	//@Column(name = "PASSW")
	private String password;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

	
}
