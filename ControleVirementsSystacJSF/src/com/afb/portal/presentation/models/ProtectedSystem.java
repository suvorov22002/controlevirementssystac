package com.afb.portal.presentation.models;

import java.io.Serializable;



/**
 * classe de protection des menu
 * @author Stéphane Mouafo
 * @version 1.0
 */
public class ProtectedSystem implements Serializable{

	/**
	 * ID 
	 */
	private static final long serialVersionUID = 1L;


	public static Boolean param = Boolean.FALSE;
	
	public static Boolean paramCompenseCentrale = Boolean.FALSE;

	public static Boolean filterTraitement = Boolean.FALSE;
	
	public static Boolean findDoublons = Boolean.FALSE;
	
	public static Boolean findIncoherences = Boolean.FALSE;
	
	public static Boolean traitement = Boolean.FALSE;
	
	public static Boolean traitementDoublons = Boolean.FALSE;

	public static Boolean traitementIncoherences = Boolean.FALSE;
	
	public static Boolean traitementValidationDoublonsDansFichier = Boolean.FALSE;


	
	public static Boolean statistiques = Boolean.FALSE;
	
	public static Boolean statistiquesDoublons = Boolean.FALSE;

	
	public static Boolean statistiquesIncoherences = Boolean.FALSE;

	
	public static Boolean statistiquesRapports = Boolean.FALSE;

	

	public static Boolean paramImpots = Boolean.FALSE;
	
	public static Boolean traitementImpots = Boolean.FALSE;
	
	public static Boolean validationOpeReservees = Boolean.FALSE;


	/**
	 * Constructeur par défaut
	 */
	public ProtectedSystem() {
		super();
	}



	public  Boolean getParam() {
		return param;
	}





	public  void setParam(Boolean param) {
		ProtectedSystem.param = param;
	}





	public  Boolean getFilterTraitement() {
		return filterTraitement;
	}





	public  void setFilterTraitement(Boolean filterTraitement) {
		ProtectedSystem.filterTraitement = filterTraitement;
	}





	public  Boolean getFindDoublons() {
		return findDoublons;
	}



	public  void setFindDoublons(Boolean findDoublons) {
		ProtectedSystem.findDoublons = findDoublons;
	}



	public  Boolean getFindIncoherences() {
		return findIncoherences;
	}



	public  void setFindIncoherences(Boolean findIncoherences) {
		ProtectedSystem.findIncoherences = findIncoherences;
	}



	public  Boolean getTraitement() {
		return traitement;
	}



	public  void setTraitement(Boolean traitement) {
		ProtectedSystem.traitement = traitement;
	}



	public  Boolean getStatistiques() {
		return statistiques;
	}



	public  void setStatistiques(Boolean statistiques) {
		ProtectedSystem.statistiques = statistiques;
	}



	public  Boolean getParamCompenseCentrale() {
		return paramCompenseCentrale;
	}



	public  void setParamCompenseCentrale(Boolean paramCompenseCentrale) {
		ProtectedSystem.paramCompenseCentrale = paramCompenseCentrale;
	}



	public  Boolean getParamImpots() {
		return paramImpots;
	}



	public  void setParamImpots(Boolean paramImpots) {
		ProtectedSystem.paramImpots = paramImpots;
	}



	public  Boolean getTraitementImpots() {
		return traitementImpots;
	}



	public  void setTraitementImpots(Boolean traitementImpots) {
		ProtectedSystem.traitementImpots = traitementImpots;
	}



	public  Boolean getTraitementDoublons() {
		return traitementDoublons;
	}



	public  void setTraitementDoublons(Boolean traitementDoublons) {
		ProtectedSystem.traitementDoublons = traitementDoublons;
	}



	public  Boolean getTraitementIncoherences() {
		return traitementIncoherences;
	}



	public  void setTraitementIncoherences(Boolean traitementIncoherences) {
		ProtectedSystem.traitementIncoherences = traitementIncoherences;
	}



	public  Boolean getTraitementValidationDoublonsDansFichier() {
		return traitementValidationDoublonsDansFichier;
	}



	public  void setTraitementValidationDoublonsDansFichier(
			Boolean traitementValidationDoublonsDansFichier) {
		ProtectedSystem.traitementValidationDoublonsDansFichier = traitementValidationDoublonsDansFichier;
	}



	public  Boolean getStatistiquesDoublons() {
		return statistiquesDoublons;
	}



	public  void setStatistiquesDoublons(Boolean statistiquesDoublons) {
		ProtectedSystem.statistiquesDoublons = statistiquesDoublons;
	}



	public  Boolean getStatistiquesIncoherences() {
		return statistiquesIncoherences;
	}



	public  void setStatistiquesIncoherences(Boolean statistiquesIncoherences) {
		ProtectedSystem.statistiquesIncoherences = statistiquesIncoherences;
	}



	public  Boolean getStatistiquesRapports() {
		return statistiquesRapports;
	}



	public  void setStatistiquesRapports(Boolean statistiquesRapports) {
		ProtectedSystem.statistiquesRapports = statistiquesRapports;
	}



	public  Boolean getValidationOpeReservees() {
		return validationOpeReservees;
	}



	public  void setValidationOpeReservees(Boolean validationOpeReservees) {
		ProtectedSystem.validationOpeReservees = validationOpeReservees;
	}


	
	






}


