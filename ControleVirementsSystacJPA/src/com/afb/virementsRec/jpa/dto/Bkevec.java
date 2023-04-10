package com.afb.virementsRec.jpa.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Bkevec implements Serializable{
	
	/**
	 * 
	 */
	@JsonIgnore
	private static final long serialVersionUID = 1L;
	
	private String age;
	private String ope;
	private String eve;
	private String nat;
	private String iden;
	private String typc;
	private String devr;
	private String mcomr;
	private String txref;
	private String mcomc;
	private String mcomn;
	private String mcomt;
	private String tax;
	private String tcom;
	
	
	public Bkevec() {
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
	public String getNat() {
		return nat;
	}
	public void setNat(String nat) {
		this.nat = nat;
	}
	public String getIden() {
		return iden;
	}
	public void setIden(String iden) {
		this.iden = iden;
	}
	public String getTypc() {
		return typc;
	}
	public void setTypc(String typc) {
		this.typc = typc;
	}
	public String getDevr() {
		return devr;
	}
	public void setDevr(String devr) {
		this.devr = devr;
	}
	public String getMcomr() {
		return mcomr;
	}
	public void setMcomr(String mcomr) {
		this.mcomr = mcomr;
	}
	public String getTxref() {
		return txref;
	}
	public void setTxref(String txref) {
		this.txref = txref;
	}
	public String getMcomc() {
		return mcomc;
	}
	public void setMcomc(String mcomc) {
		this.mcomc = mcomc;
	}
	public String getMcomn() {
		return mcomn;
	}
	public void setMcomn(String mcomn) {
		this.mcomn = mcomn;
	}
	public String getMcomt() {
		return mcomt;
	}
	public void setMcomt(String mcomt) {
		this.mcomt = mcomt;
	}
	public String getTax() {
		return tax;
	}
	public void setTax(String tax) {
		this.tax = tax;
	}
	public String getTcom() {
		return tcom;
	}
	public void setTcom(String tcom) {
		this.tcom = tcom;
	}
	
	

}
