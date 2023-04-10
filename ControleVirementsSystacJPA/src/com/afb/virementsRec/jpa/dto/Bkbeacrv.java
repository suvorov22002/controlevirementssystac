package com.afb.virementsRec.jpa.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Bkbeacrv implements Serializable {
	/**
	 * 
	 */
	@JsonIgnore
	private static final long serialVersionUID = 1L;
	
	private String agec;
	private String age;
	private String ncp; 
	private String clc; 
	private String dev;
	private String zone;
	private String nat; 
	private String tope; 
	private String cenr; 
	private Date drec; 
	private Date dreg; 
	private Date dsort; 
	private String ope; 
	private String ndoc;
	private Double mon; 
	private Date dco; 
	private Date dcom;
	private String eve; 
	private String utrt;
	private String etabr; 
	private String guibr;
	private String comr; 
	private String cler;
	
	
	public Bkbeacrv() {
		super();
	}


	public Bkbeacrv(String agec, String age, String ncp, String clc, String dev, String zone, String nat, String tope,
			String cenr, Date drec, Date dreg, Date dsort, String ope, String ndoc, Double mon, Date dco, Date dcom,
			String eve, String utrt, String etabr, String guibr, String comr, String cler) {
		
		this.agec = agec;
		this.age = age;
		this.ncp = ncp;
		this.clc = clc;
		this.dev = dev;
		this.zone = zone;
		this.nat = nat;
		this.tope = tope;
		this.cenr = cenr;
		this.drec = drec;
		this.dreg = dreg;
		this.dsort = dsort;
		this.ope = ope;
		this.ndoc = ndoc;
		this.mon = mon;
		this.dco = dco;
		this.dcom = dcom;
		this.eve = eve;
		this.utrt = utrt;
		this.etabr = etabr;
		this.guibr = guibr;
		this.comr = comr;
		this.cler = cler;
	}


	public String getAgec() {
		return agec;
	}


	public void setAgec(String agec) {
		this.agec = agec;
	}


	public String getAge() {
		return age;
	}


	public void setAge(String age) {
		this.age = age;
	}


	public String getNcp() {
		return ncp;
	}


	public void setNcp(String ncp) {
		this.ncp = ncp;
	}


	public String getClc() {
		return clc;
	}


	public void setClc(String clc) {
		this.clc = clc;
	}


	public String getDev() {
		return dev;
	}


	public void setDev(String dev) {
		this.dev = dev;
	}


	public String getZone() {
		return zone;
	}


	public void setZone(String zone) {
		this.zone = zone;
	}


	public String getNat() {
		return nat;
	}


	public void setNat(String nat) {
		this.nat = nat;
	}


	public String getTope() {
		return tope;
	}


	public void setTope(String tope) {
		this.tope = tope;
	}


	public String getCenr() {
		return cenr;
	}


	public void setCenr(String cenr) {
		this.cenr = cenr;
	}


	public Date getDrec() {
		return drec;
	}


	public void setDrec(Date drec) {
		this.drec = drec;
	}


	public Date getDreg() {
		return dreg;
	}


	public void setDreg(Date dreg) {
		this.dreg = dreg;
	}


	public Date getDsort() {
		return dsort;
	}


	public void setDsort(Date dsort) {
		this.dsort = dsort;
	}


	public String getOpe() {
		return ope;
	}


	public void setOpe(String ope) {
		this.ope = ope;
	}


	public String getNdoc() {
		return ndoc;
	}


	public void setNdoc(String ndoc) {
		this.ndoc = ndoc;
	}


	public Double getMon() {
		return mon;
	}


	public void setMon(Double mon) {
		this.mon = mon;
	}


	public Date getDco() {
		return dco;
	}


	public void setDco(Date dco) {
		this.dco = dco;
	}


	public Date getDcom() {
		return dcom;
	}


	public void setDcom(Date dcom) {
		this.dcom = dcom;
	}


	public String getEve() {
		return eve;
	}


	public void setEve(String eve) {
		this.eve = eve;
	}


	public String getUtrt() {
		return utrt;
	}


	public void setUtrt(String utrt) {
		this.utrt = utrt;
	}


	public String getEtabr() {
		return etabr;
	}


	public void setEtabr(String etabr) {
		this.etabr = etabr;
	}


	public String getGuibr() {
		return guibr;
	}


	public void setGuibr(String guibr) {
		this.guibr = guibr;
	}


	public String getComr() {
		return comr;
	}


	public void setComr(String comr) {
		this.comr = comr;
	}


	public String getCler() {
		return cler;
	}


	public void setCler(String cler) {
		this.cler = cler;
	}

}
