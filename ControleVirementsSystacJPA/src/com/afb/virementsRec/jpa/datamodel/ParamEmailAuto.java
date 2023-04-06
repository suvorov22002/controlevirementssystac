package com.afb.virementsRec.jpa.datamodel;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: ParamEmailAuto
 *
 */
@Entity
@Table(name = "VIR_SYSTAC_PARAM_EMAIL_AUTO")
public class ParamEmailAuto implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public ParamEmailAuto() {
		super();
	}
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_PARAM")
	private Long id;
	
	@Column(name = "IP")
	private String ip; //domain
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "PASS")
	private String pass;
	
	@Column(name = "PORT")
	private Integer port;
	
		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}
}
