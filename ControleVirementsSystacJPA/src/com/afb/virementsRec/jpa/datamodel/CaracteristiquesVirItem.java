package com.afb.virementsRec.jpa.datamodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Type énuméré pour la gestion des caracteristiques des doublons
 * @author stephane_mouafo
 *
 */
public enum CaracteristiquesVirItem {


	/*RIB_BENEF("RIB Beneficiaire"),

	RIB_DONNEUR_ORDRE("RIB Donneur d ordre"),*/
	
	///lib("Libelle"),
	
	///agence("Agence"),
	
	ncp("Numéro de compte bénéficiare"), //rib benef    substring(135, 158).substring(11,22)
	
	///dco("Date Comptable"),
	
	ncp_donneur_ordre("Numéro Compte Donneur d'ordre"), //substring(75, 98).substring(11,22)
	
	nom_benef("Nom Bénéficiaire"), //substring(158, 188)
	
	nom_donneur_ordre("Nom Donneur d'odre"),  //substring(98, 128)

	montant("Montant"),   //substring(53, 68)
	
	Evenement("Evenement"); //substring(68, 75)

	


	private String value;

	private static final List<CaracteristiquesVirItem> typeParam = new ArrayList<CaracteristiquesVirItem>();


	static {

		//typeParam.add(lib);
		//typeParam.add(agence);
		typeParam.add(ncp);
		//typeParam.add(dco);
		typeParam.add(ncp_donneur_ordre);
		typeParam.add(nom_benef);
		typeParam.add(nom_donneur_ordre);
		typeParam.add(montant);
		typeParam.add(Evenement);
	}

	private CaracteristiquesVirItem(String value){

		this.value = value;
	}


	public String value() {
		return value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value){
		
		this.value = value;
	}


	public static List<CaracteristiquesVirItem> typeParam() {

		// On retourne la liste
		return typeParam;

	}

	
	
}










