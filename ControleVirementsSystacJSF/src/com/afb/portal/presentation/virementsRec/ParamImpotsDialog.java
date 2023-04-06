package com.afb.portal.presentation.virementsRec;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import org.hibernate.criterion.Restrictions;
import org.richfaces.model.selection.Selection;
import org.richfaces.model.selection.SimpleSelection;

import com.afb.portal.presentation.models.AbstractDialog;
import com.afb.portal.presentation.models.DialogFormMode;
import com.afb.portal.presentation.tools.ExceptionHelper;
import com.afb.portal.presentation.tools.Multilangue;
import com.afb.portal.presentation.tools.ViewHelper;
import com.afb.virementsRec.jpa.datamodel.CaracteristiquesVir;
import com.afb.virementsRec.jpa.datamodel.CaracteristiquesVirItem;
import com.afb.virementsRec.jpa.datamodel.MotifsDeRejet;
import com.afb.virementsRec.jpa.datamodel.MotsClesEtCharSpeciaux;
import com.afb.virementsRec.jpa.datamodel.ParametragesImpots;
import com.afb.virementsRec.jpa.datamodel.TypeRejet;
import com.yashiro.persistence.utils.dao.tools.RestrictionsContainer;


public class ParamImpotsDialog extends AbstractDialog{

	private ParametragesImpots currentObject = new ParametragesImpots();

	private MotifsDeRejet currentObject2 = new MotifsDeRejet();

	private List<SelectItem> selectItems2 = new ArrayList<SelectItem>();
	/**private CaracteristiquesVirItem typeParam;
	private List<CaracteristiquesVir> caracVir = new ArrayList<CaracteristiquesVir>();

	List<CaracteristiquesVir> caracVirToRemove = new ArrayList<CaracteristiquesVir>();

	private Selection selection;

	private CaracteristiquesVir deleUser = new CaracteristiquesVir();

	private int index = 0;

	private Integer montant;*/

	private Selection selection;

	private TypeRejet motifRejet;

	private MotifsDeRejet deleUser2 = new MotifsDeRejet();

	private List<MotifsDeRejet> listMotifsRejets = new ArrayList<MotifsDeRejet>();

	private List<MotifsDeRejet> listMotifsRejetsToRemove = new ArrayList<MotifsDeRejet>();



	public void openParamDialog(){

		this.mode = DialogFormMode.UPDATE;
		initComponents();
		this.open();
	}


	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Paramétrages généraux";
	}

	@Override
	public String getFileDefinition() {
		// TODO Auto-generated method stub
		return "/views/virementsRec/ParamImpotsDialog.xhtml";
	}

	public List<SelectItem> getSelectItems2() {
		return selectItems2;
	}


	public void setSelectItems2(List<SelectItem> selectItems2) {
		this.selectItems2 = selectItems2;
	}

	/**public CaracteristiquesVirItem getTypeParam() {
		return typeParam;
	}


	public void setTypeParam(CaracteristiquesVirItem typeParam) {
		this.typeParam = typeParam;
	}


	


	public List<CaracteristiquesVir> getCaracVir() {
		return caracVir;
	}


	public void setCaracVir(List<CaracteristiquesVir> caracVir) {
		this.caracVir = caracVir;
	}


	public Selection getSelection() {
		return selection;
	}


	public void setSelection(Selection selection) {
		this.selection = selection;
	}

	public Integer getMontant() {
		return montant;
	}


	public void setMontant(Integer montant) {
		this.montant = montant;
	}

	 */


	public void setCurrentObject(ParametragesImpots currentObject) {
		this.currentObject = currentObject;
	}




	@SuppressWarnings("unchecked")
	@Override
	public ParametragesImpots getCurrentObject() {
		// TODO Auto-generated method stub
		return this.currentObject;
	}

	@Override
	public boolean checkBuildedCurrentObject() {
		// TODO Auto-generated method stub

		if(currentObject.getTauxTax() > 1 || currentObject.getTauxTax() < 0){
			
			this.error = true;
			ExceptionHelper.showError("Le Taux doit être compris entre 0 et 1", this);
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

	public MotifsDeRejet getCurrentObject2() {
		return currentObject2;
	}


	public void setCurrentObject2(MotifsDeRejet currentObject2) {
		this.currentObject2 = currentObject2;
	}


	public TypeRejet getMotifRejet() {
		return motifRejet;
	}


	public void setMotifRejet(TypeRejet motifRejet) {
		this.motifRejet = motifRejet;
	}



	public List<MotifsDeRejet> getListMotifsRejets() {
		return listMotifsRejets;
	}


	public void setListMotifsRejets(List<MotifsDeRejet> listMotifsRejets) {
		this.listMotifsRejets = listMotifsRejets;
	}


	public List<MotifsDeRejet> getListMotifsRejetsToRemove() {
		return listMotifsRejetsToRemove;
	}


	public void setListMotifsRejetsToRemove(
			List<MotifsDeRejet> listMotifsRejetsToRemove) {
		this.listMotifsRejetsToRemove = listMotifsRejetsToRemove;
	}


	public Selection getSelection() {
		return selection;
	}


	public void setSelection(Selection selection) {
		this.selection = selection;
	}


	@Override
	public void initComponents() {


		List<ParametragesImpots> params = ViewHelper.virementsRecManagerDAOLocal.filter(ParametragesImpots.class, null, null, null, null, 0, -1);
		if(params!=null && !params.isEmpty()){
			currentObject = params.get(0);
			
			listMotifsRejets = ViewHelper.virementsRecManagerDAOLocal.filter(MotifsDeRejet.class, null, RestrictionsContainer.getInstance().add(Restrictions.eq("param", currentObject)).add(Restrictions.eq("valide", Boolean.TRUE)), null, null, 0, -1);
		}


		/***List<ParametragesImpots> listParams = ViewHelper.virementsRecManager.filterParams();
		if(listParams!=null && !listParams.isEmpty()){

			currentObject = listParams.get(0);

			if(currentObject!=null){

				montant = currentObject.getMontant().intValue();

				if(currentObject.getTypeCarac()!=null){

					System.out.println(currentObject.getTypeCarac().isEmpty());

					caracVir = ViewHelper.virementsRecManager.filterCaracteristiquesVir(currentObject, null,null);
				}
			}
		}*/


		selectItems2.add(new SelectItem(null, "---Choisir---"));
		for (TypeRejet val : TypeRejet.types()){
			selectItems2.add(new SelectItem(val.name(), Multilangue.getMessage(val.value(), this)));
		}


	}

	@Override
	protected void buildCurrentObject() throws ParseException {

		/**this.currentObject.setTypeCarac(this.caracVir);
		this.currentObject.setMontant(Double.valueOf(montant));*/

		this.currentObject.setMotifsDeRejet(listMotifsRejets);


	}



	@Override
	protected void validate() {

		/**this.currentObject = ViewHelper.virementsRecManager.updateParametragesImpots(currentObject);


		for(CaracteristiquesVir c: caracVirToRemove){

			List<CaracteristiquesVir> listCar = ViewHelper.virementsRecManager.filterCaracteristiquesVir(null,c,null);
			if(listCar!=null&&!listCar.isEmpty()){
				listCar.get(0).setValide(Boolean.FALSE);
				ViewHelper.virementsRecManager.updateCaracteristiquesVir(listCar.get(0));
				System.out.println("Suppression de " + listCar.get(0).getCaracteristiquesItems() + " effectuée avec succes!");
			}
		}*/

		this.currentObject = ViewHelper.virementsRecManagerDAOLocal.update(currentObject);


		for(MotifsDeRejet c: listMotifsRejetsToRemove){

			List<MotifsDeRejet> listMR = ViewHelper.virementsRecManager.filterMotifRejet(null,c);
			if(listMR!=null&&!listMR.isEmpty()){
				listMR.get(0).setValide(Boolean.FALSE);
				ViewHelper.virementsRecManagerDAOLocal.update(listMR.get(0));
				System.out.println("Suppression de " + listMR.get(0).getTypeRejet() + " effectuée avec succes!");
			}
		}

	}

	@Override
	protected void disposeResourceOnClose() {

		selectItems2.clear();
		currentObject = new ParametragesImpots();
		/**caracVir.clear();
		caracVirToRemove.clear();*/

	}

	public void ajouterParam2() {
		if(motifRejet!=null){

			currentObject2 = new MotifsDeRejet();
			currentObject2.setTypeRejet(motifRejet);
			currentObject2.setParam(currentObject);

			listMotifsRejets.add(currentObject2);
			motifRejet = null;

		}
	}



	public MotifsDeRejet getDeleUser2() {
		return deleUser2;
	}

	public void setDeleUser2(MotifsDeRejet deleUser2) {
		this.deleUser2 = deleUser2;

		try {

			if(deleUser2 != null){


				listMotifsRejets.remove(deleUser2);

				listMotifsRejetsToRemove.add(deleUser2);

				deleUser2.setValide(Boolean.FALSE);

				System.out.println("Motif Rejet " +deleUser2.getTypeRejet() + " enlevé avec succes!");

			}

		} catch (Exception e) {

			// Traitement de l'exception
			ExceptionHelper.threatException(e);
		}
	}






	/**public CaracteristiquesVir getDeleUser() {
		return deleUser;
	}

	public void setDeleUser(CaracteristiquesVir deleUser) {
		this.deleUser = deleUser;

		try {

			if(deleUser != null){

				System.out.println("caracVir " +deleUser.getCaracteristiquesItems());

				System.out.println("----caracVir size before remove----" + caracVir.size());

				//deleUser = getElement();

				System.out.println("----caracVir size to remove----" + deleUser.getCaracteristiquesItems());

				caracVir.remove(deleUser);

				caracVirToRemove.add(deleUser);

				System.out.println("----caracVir size after remove----" + caracVir.size());

				deleUser.setValide(Boolean.FALSE);

				//caracVir.add(deleUser);

				System.out.println("caracVir " +deleUser.getCaracteristiquesItems() + " enlevé avec succes!");

			}

		} catch (Exception e) {

			// Traitement de l'exception
			ExceptionHelper.threatException(e);
		}
	}*/


	/**public void ajouterParam() {

		try {

			Integer rang = 1;

			int size = 0;

			CaracteristiquesVir param = new CaracteristiquesVir();

			//List<CaracteristiquesVir>carVirs = ViewHelper.virementsRecManager.filterCaracteristiquesVir(null, null,typeParam);

			param.setCaracteristiquesItems(typeParam);

			boolean found = false;
			for(CaracteristiquesVir c: caracVir){

				if(c.getCaracteristiquesItems().getValue().equals(param.getCaracteristiquesItems().getValue())){
					found = true;
				}
			}


			if(found==false){

				if (this.caracVir == null) {

					this.caracVir = new ArrayList<CaracteristiquesVir>();

					rang = 1;

				} else if (this.caracVir != null && this.caracVir.size() > 0) {

					size = this.caracVir.size();

					rang = size + 1;
				}

				param.setRang(rang);

				param.setParam(currentObject);


				this.caracVir.add(param);
			}


			System.out.println("-----Rang is " + rang);

		} catch (Exception e) {

			// Etat d'erreur
			this.error = true;

			// Traitement de l'exception
			ExceptionHelper.threatException(e);
		}

	}*/



	/**
	 * recuperation de l element de la liste
	 * @return
	 */
	/**public CaracteristiquesVir getElement(){

		CaracteristiquesVir caracV = null;

		try {

			// Si la selection est nulle
			if(selection == null){
				System.out.println("**selection is null***");
				if(!this.caracVir.isEmpty()){
					System.out.println("**returning caracVir.get(0)***" + caracVir.get(0));
					return this.caracVir.get(0);
				}else{
					// On sort
					return null;
				}
			}

			// Obtention des lignes selectionnees
			Iterator<Object> iterator = selection.getKeys();

			// Index de la ligne selectionnee

			// Si l'iterateur est null
			if(iterator == null){
				System.out.println("**iterator is null***");
				if(!this.caracVir.isEmpty()){
					System.out.println("**returning caracVir.get(0)***" + caracVir.get(0));
					return this.caracVir.get(0);
				}else{
					// On sort
					return null;
				}
			}

			// Si l'iterateur a un element
			if(iterator.hasNext()) {

				// Index selectionné
				System.out.println("**iterator has next***");
				index = (Integer) iterator.next();

				System.out.println("***index  i***"+index);

			}else{

				System.out.println("**iterator does not have next***");
			}

			System.out.println("***index***"+index);

			// Si l'index n'est pas dans l'intervalle du modele
			if(index < 0 || index >= this.caracVir.size()) {
				System.out.println("**index is out of bounds***");
				if(!this.caracVir.isEmpty()){
					System.out.println("**returning caracVir.get(0)***" + caracVir.get(0));
					return this.caracVir.get(0);
				}else{
					// On sort
					return null;
				}

			}

			// Le credit selectionne
			caracV = this.caracVir.get(index);
			System.out.println("**caracV***" + caracV.getCaracteristiquesItems());

			//caracV = ViewHelper.virementsRecManager.findByPrimaryKey(CaracteristiquesVir.class, caracV.getId(), null);

		} catch (Exception e) {

			// Etat d'erreur
			this.error = true;
			// Traitement de l'exception
			ExceptionHelper.threatException(e);
		}

		return caracV;

	}*/

}
