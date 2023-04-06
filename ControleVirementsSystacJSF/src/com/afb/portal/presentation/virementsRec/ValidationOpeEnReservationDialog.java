package com.afb.portal.presentation.virementsRec;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.model.SelectItem;

import org.hibernate.criterion.Restrictions;
import org.richfaces.model.selection.Selection;
import org.richfaces.model.selection.SimpleSelection;

import afb.dsi.dpd.portal.jpa.entities.User;

import com.afb.portal.presentation.models.AbstractDialog;
import com.afb.portal.presentation.models.DialogFormMode;
import com.afb.portal.presentation.tools.ExceptionHelper;
import com.afb.portal.presentation.tools.Multilangue;
import com.afb.portal.presentation.tools.ViewHelper;
import com.afb.virementsRec.jpa.datamodel.CaracteristiquesVir;
import com.afb.virementsRec.jpa.datamodel.CaracteristiquesVirItem;
import com.afb.virementsRec.jpa.datamodel.ParamCompensateurCentrale;
import com.afb.virementsRec.jpa.datamodel.ParamEmail;
import com.afb.virementsRec.jpa.datamodel.Parametrages;
import com.afb.virementsRec.jpa.datamodel.ParametragesCaracteresSpeciaux;
import com.afb.virementsRec.jpa.datamodel.ParametragesGenTeleCompense;
import com.afb.virementsRec.jpa.datamodel.TraitementImpots;
import com.yashiro.persistence.utils.dao.tools.RestrictionsContainer;


public class ValidationOpeEnReservationDialog extends AbstractDialog{

	private TraitementImpots currentObject = new TraitementImpots();

	private List<TraitementImpots> listOpe = new ArrayList<TraitementImpots>();

	private Selection selection;

	private int index = 0;

	private String caractereSpecial;

	private String caractereDeRemplacement;

	private User user;

	private String ope, uti, ncp1;

	private Date dsai;
	
	TraitementImpots trt = new TraitementImpots(); 




	public void openValidationOpeEnReservationDialog(){

		this.mode = DialogFormMode.UPDATE;
		initComponents();
		this.open();
	}


	@Override
	public String getTitle() { 
		// TODO Auto-generated method stub
		return "Validation des Opérations en réservation";
	}

	@Override
	public String getFileDefinition() {
		// TODO Auto-generated method stub
		return "/views/virementsRec/ValidationOpeEnReservationDialog.xhtml";
	}


	public int getIndex() {
		return index;
	}


	public void setIndex(int index) {
		this.index = index;
	}


	public Selection getSelection() {
		return selection;
	}


	public void setSelection(Selection selection) {
		this.selection = selection;
	}


	public List<TraitementImpots> getListOpe() {
		return listOpe;
	}

	public void setListOpe(List<TraitementImpots> listOpe) {
		this.listOpe = listOpe;
	}

	public String getOpe() {
		return ope;
	}

	public void setOpe(String ope) {
		this.ope = ope;
	}

	public String getUti() {
		return uti;
	}

	public void setUti(String uti) {
		this.uti = uti;
	}

	public String getNcp1() {
		return ncp1;
	}

	public void setNcp1(String ncp1) {
		this.ncp1 = ncp1;
	}

	public Date getDsai() {
		return dsai;
	}

	public void setDsai(Date dsai) {
		this.dsai = dsai;
	}

	public void setCurrentObject(TraitementImpots currentObject) {
		this.currentObject = currentObject;
	}

	public String getCaractereSpecial() {
		return caractereSpecial;
	}

	public void setCaractereSpecial(String caractereSpecial) {
		this.caractereSpecial = caractereSpecial;
	}

	public String getCaractereDeRemplacement() {
		return caractereDeRemplacement;
	}

	public void setCaractereDeRemplacement(String caractereDeRemplacement) {
		this.caractereDeRemplacement = caractereDeRemplacement;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public TraitementImpots getCurrentObject() {
		// TODO Auto-generated method stub
		return this.currentObject;
	}

	@Override
	public boolean checkBuildedCurrentObject() {
		// TODO Auto-generated method stub

		System.out.println("---listOpe---" + listOpe.size());

		if(listOpe==null||listOpe.isEmpty()){
			this.information = true;
			ExceptionHelper.showError("Aucune opération dans la Liste!",  this);
			return false;
		}


		//Récupération de l'élément selectionné
		trt = getElement();
		if(trt == null ){
			this.information = true;
			ExceptionHelper.showError("Aucune opération selectionnée dans la Liste!",  this);
			return false;
		}


		return true;
	}

	@Override
	public String getChildDialogDefinition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSecondChildDefinition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initComponents() {
		// TODO Auto-generated method stub

		user = ViewHelper.getSessionUser();
	}

	@Override
	protected void buildCurrentObject() throws ParseException {

		//this.currentObject.setTypeCarac(this.caracVir);

	}



	public TraitementImpots getElement(){

		TraitementImpots trt = null;

		try {

			// Si la selection est nulle
			if(selection == null){
				if(!this.listOpe.isEmpty()){
					return this.listOpe.get(0);
				}else{
					// On sort
					return null;
				}
			}

			// Obtention des lignes selectionnees
			Iterator<Object> iterator = selection.getKeys();

			// Index de la ligne selectionnee
			int index = 0;

			// Si l'iterateur est null
			if(iterator == null){
				if(!this.listOpe.isEmpty()){
					return this.listOpe.get(0);
				}else{
					// On sort
					return null;
				}
			}

			// Si l'iterateur a un element
			if(iterator.hasNext()) {

				// Index selectionné
				index = (Integer) iterator.next();

			}

			// Si l'index n'est pas dans l'intervalle du modele
			if(index < 0 || index >= this.listOpe.size()) {

				if(!this.listOpe.isEmpty()){
					return this.listOpe.get(0);
				}else{
					// On sort
					return null;
				}

			}

			// Le credit selectionne
			trt = this.listOpe.get(index);

			trt = ViewHelper.virementsRecManager.findByPrimaryKey(TraitementImpots.class, trt.getId(), null);

		} catch (Exception e) {

			// Etat d'erreur
			this.error = true;
			// Traitement de l'exception
			ExceptionHelper.threatException(e);
		}

		return trt;

	}



	@Override
	protected void validate() {

		System.out.println("----In validate---- ");
		//Exécution Update
		boolean executed = ViewHelper.virementsRecManager.updateOpeReserveeOTP_DGI(trt.getOpe(), trt.getUti(), trt.getNcp1(), trt.getDsai(), user.getLogin());
		System.out.println("------executed------ : " + executed);

	}


	public void processRecherche(){
		// Exécution Recherche
		System.out.println(" 0 ope: "+ ope + " ncp1: "+ncp1 + " uti: " + uti + " dsai: " + dsai);
		List<TraitementImpots> trts = ViewHelper.virementsRecManager.findOpeReserveeOTP_DGI(ope, uti, ncp1, dsai);
		if(trts!=null&&!trts.isEmpty()){

			listOpe.addAll(trts);
		}
	}

	@Override
	protected void disposeResourceOnClose() {

		ope="";
		uti="";
		ncp1="";
		dsai=null;
		listOpe.clear();

	}

	/**public void ajouterParam(){

		if(ope!=null && !ope.isEmpty() && uti!=null && !uti.isEmpty() && ncp1!=null && !ncp1.isEmpty() && dsai!=null){

			currentObject = new TraitementImpots();
			currentObject.setOpe(ope.trim());
			currentObject.setUti(uti.trim());
			currentObject.setNcp1(ncp1.trim());
			currentObject.setDsai(dsai);

			listOpe.add(currentObject);

			ope = "";
			uti = "";
			ncp1="";
			dsai=null;

		}
	}*/


	/**@Override
	public void actionOnInformationDialogClose(){

		// Liberation des Ressources
		//disposeResourceOnClose();

		// On positionne l'etat a false
		this.open = true;

		// On positionne l'etat de sortie
		this.wellClose = false;

		if(this.parent == null) return;

		// Validation de la fermeture
		this.parent.validateSubDialogClosure(this, mode, wellClose);

	}*/




}
