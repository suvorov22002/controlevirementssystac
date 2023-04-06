package com.afb.virementsRec.jpa.datamodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Type énuméré pour la gestion des types de traitements
 * @author stephane_mouafo
 *
 */
public enum TypeRejet {

	PROVISION_INSUFFISENTE("01"),
	ABSENCE_DE_PROVISION("02"),
	COMPTE_FERME("03"),
	//OPPOSITION_DEBIT("OPPOSITION DEBIT"),
	//COMPTE_BLOQUE("04"),
	//DESACCORDS_SUR_COMPTE_OU_CLIENT("04"),
	OPPOSITION_DEBIT("04"),
	COMPTE_INEXISTANT("05"),
	RIB_BENEFICIAIRE_INCORRECT("06"),
	AUTORISATION_PRELEVEMENT_NONVALIDE("07"),
	STRUCTURE_LIGNE_INCORRECTE("10");
	
	/**
	 * La valeur du type de retenue
	 */
	private final String value;
	

	/**
	 * Liste de types de retenues
	 */
	private static final List<TypeRejet> types = new ArrayList<TypeRejet>();
	
	/**
	 * constructeur
	 */
	static{
	 
	 types.add(PROVISION_INSUFFISENTE);
	 types.add(ABSENCE_DE_PROVISION);
	 types.add(COMPTE_FERME);
	 types.add(OPPOSITION_DEBIT);
	 types.add(COMPTE_INEXISTANT);
	 types.add(RIB_BENEFICIAIRE_INCORRECT);
	 types.add(AUTORISATION_PRELEVEMENT_NONVALIDE);
	 types.add(STRUCTURE_LIGNE_INCORRECTE);
	 
	}
	
	/**
	 * 
	 * @param value the value to set
	 */
	private TypeRejet(String value){
		
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
	public static List<TypeRejet> types(){
		
		return types;
		
	}
	
	
}
