package com.afb.virementsRec.jpa.datamodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Type énuméré pour la gestion des types de traitements
 * @author stephane_mouafo
 *
 */
public enum TypeTraitement {

	DOUBLONS_NOM_FICHIER("DOUBLONS_NOM_FICHIER"),
	DOUBLONS_ENTRE_FICHIER("DOUBLONS_ENTRE_FICHIER"),
	DOUBLONS_DANS_FICHIER("DOUBLONS_DANS_FICHIER"),
	ARCHIVE("ARCHIVE"),
	VALIDATION_MANUELLE("VALIDATION_MANUELLE");
	
	/**
	 * La valeur du type de retenue
	 */
	private final String value;
	

	/**
	 * Liste de types de retenues
	 */
	private static final List<TypeTraitement> types = new ArrayList<TypeTraitement>();
	
	/**
	 * constructeur
	 */
	static{
	 
	 types.add(DOUBLONS_NOM_FICHIER);
	 types.add(DOUBLONS_ENTRE_FICHIER);
	 types.add(DOUBLONS_DANS_FICHIER);
	 types.add(ARCHIVE);
	 types.add(VALIDATION_MANUELLE);
	 
	}
	
	/**
	 * 
	 * @param value the value to set
	 */
	private TypeTraitement(String value){
		
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
	public static List<TypeTraitement> types(){
		
		return types;
		
	}
	
	
}
