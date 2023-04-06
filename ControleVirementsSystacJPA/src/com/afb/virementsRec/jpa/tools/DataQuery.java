package com.afb.virementsRec.jpa.tools;

import java.io.Serializable;

/**
 * 
 * @author Owner
 *
 */
public class DataQuery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private String tittle;
	private String query;
	private String value;
	
	/**
	 * 
	 */
	public DataQuery() {
		super();
	}
	
	
	
	/**
	 * @param code
	 * @param tittle
	 * @param querry
	 * @param value
	 */
	public DataQuery(String code, String tittle, String querry, String value) {
		super();
		this.code = code;
		this.tittle = tittle;
		this.query = querry;
		this.value = value;
	}



	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the tittle
	 */
	public String getTittle() {
		return tittle;
	}
	/**
	 * @param tittle the tittle to set
	 */
	public void setTittle(String tittle) {
		this.tittle = tittle;
	}
	
	
	
	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}



	/**
	 * @param query the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}



	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}
