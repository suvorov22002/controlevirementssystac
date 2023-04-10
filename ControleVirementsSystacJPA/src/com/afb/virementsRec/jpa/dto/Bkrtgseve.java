package com.afb.virementsRec.jpa.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Bkrtgseve implements Serializable{
	
	@JsonIgnore
	private static final long serialVersionUID = 1L;
	
	private String age;
	private String ope;
	private String eve;
	private String iden;
	private String vala;
	
	public Bkrtgseve() {
		super();
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getOpe() {
		return ope;
	}

	public void setOpe(String ope) {
		this.ope = ope;
	}

	public String getEve() {
		return eve;
	}

	public void setEve(String eve) {
		this.eve = eve;
	}

	public String getIden() {
		return iden;
	}

	public void setIden(String iden) {
		this.iden = iden;
	}

	public String getVala() {
		return vala;
	}

	public void setVala(String vala) {
		this.vala = vala;
	}
	
	
	
	
}
