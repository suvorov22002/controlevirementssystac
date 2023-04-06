package com.afb.virementsRec.jpa.datamodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Type énuméré pour la gestion des types de traitements
 * @author stephane_mouafo
 *
 */
public enum TypeProcess {

	RAPATRIEMENT_IMG_ALLER("1.1 - RAPATRIEMENT_IMG_ALLER"),
	FICHIER_COMPTA_ALLER("1.2 - FICHIER_COMPTA_ALLER"),
	RAPATRIEMENT_IMG_RETOUR("2.1 - RAPATRIEMENT_IMG_RETOUR"),
	FICHIER_COMPTA_RETOUR("2.2 - FICHIER_COMPTA_RETOUR");
	
	/**
	 * La valeur du type de retenue
	 */
	private final String value;
	

	/**
	 * Liste de types de retenues
	 */
	private static final List<TypeProcess> types = new ArrayList<TypeProcess>();
	
	/**
	 * constructeur
	 */
	static{
	 
	 types.add(RAPATRIEMENT_IMG_ALLER);
	 types.add(FICHIER_COMPTA_ALLER);
	 types.add(RAPATRIEMENT_IMG_RETOUR);
	 types.add(FICHIER_COMPTA_RETOUR);
	 
	}
	
	/**
	 * 
	 * @param value the value to set
	 */
	private TypeProcess(String value){
		
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
	public static List<TypeProcess> types(){
		
		return types;
		
	}
	
	
}
