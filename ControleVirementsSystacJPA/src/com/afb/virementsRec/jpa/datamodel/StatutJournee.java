package com.afb.virementsRec.jpa.datamodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Type énuméré pour la gestion des types de traitements
 * @author stephane_mouafo
 *
 */
public enum StatutJournee {

	OUVERTURE("OUVERTURE"),
	FERMETURE("FERMETURE");
	
	/**
	 * La valeur du type de retenue
	 */
	private final String value;
	

	/**
	 * Liste de types de retenues
	 */
	private static final List<StatutJournee> types = new ArrayList<StatutJournee>();
	
	/**
	 * constructeur
	 */
	static{
	 
	 types.add(OUVERTURE);
	 types.add(FERMETURE);
	
	 
	}
	
	/**
	 * 
	 * @param value the value to set
	 */
	private StatutJournee(String value){
		
		this.value = value;
	}
	
    /**
     * 
     * @return the value
     */
	public String value() {
        return value;
    }

	/**
	 * 
	 * @return the value
	 */
	public String getValue() {
        return value;
    }
	
	/**
	 * 
	 * @return the types
	 */
	public static List<StatutJournee> types(){
		
		return types;
		
	}
	
	
}
