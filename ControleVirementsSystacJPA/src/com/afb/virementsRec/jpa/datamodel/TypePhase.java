package com.afb.virementsRec.jpa.datamodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Type énuméré pour la gestion des types de traitements
 * @author stephane_mouafo
 *
 */
public enum TypePhase {

	PHASE_ALLER("PHASE ALLER"),
	PHASE_RETOUR("PHASE RETOUR");
	
	
	/**
	 * La valeur du type de retenue
	 */
	private final String value;
	

	/**
	 * Liste de types de retenues
	 */
	private static final List<TypePhase> types = new ArrayList<TypePhase>();
	
	/**
	 * constructeur
	 */
	static{
	 
	 types.add(PHASE_ALLER);
	 types.add(PHASE_RETOUR);
	 
	}
	
	/**
	 * 
	 * @param value the value to set
	 */
	private TypePhase(String value){
		
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
	public static List<TypePhase> types(){
		
		return types;
		
	}
	
	
}
