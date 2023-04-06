package com.afb.virementsRec.jpa.datamodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Type énuméré pour la gestion des types de traitements
 * @author stephane_mouafo
 *
 */
public enum SortTraitement {

	SUCCES("SUCCES"),
	ECHEC("ECHEC"),
	PARTIEL("PARTIEL");
	
	/**
	 * La valeur du type de retenue
	 */
	private final String value;
	

	/**
	 * Liste de types de retenues
	 */
	private static final List<SortTraitement> types = new ArrayList<SortTraitement>();
	
	/**
	 * constructeur
	 */
	static{
	 
	 types.add(SUCCES);
	 types.add(ECHEC);
	 types.add(PARTIEL);
	 
	}
	
	/**
	 * 
	 * @param value the value to set
	 */
	private SortTraitement(String value){
		
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
	public static List<SortTraitement> types(){
		
		return types;
		
	}
	
	
}
