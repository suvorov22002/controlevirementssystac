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
import com.afb.virementsRec.jpa.datamodel.MotsClesEtCharSpeciaux;
import com.afb.virementsRec.jpa.datamodel.ParamEmail;
import com.afb.virementsRec.jpa.datamodel.Parametrages;
import com.yashiro.persistence.utils.dao.tools.RestrictionsContainer;


public class ParamDialog extends AbstractDialog{

	private Parametrages currentObject = new Parametrages();

	private MotsClesEtCharSpeciaux currentObject2 = new MotsClesEtCharSpeciaux();

	private CaracteristiquesVirItem typeParam;

	private String motCle;

	private List<SelectItem> selectItems2 = new ArrayList<SelectItem>();

	private List<CaracteristiquesVir> caracVir = new ArrayList<CaracteristiquesVir>();

	private List<CaracteristiquesVir> caracVirToRemove = new ArrayList<CaracteristiquesVir>();

	private Selection selection;

	private CaracteristiquesVir deleUser = new CaracteristiquesVir();

	private MotsClesEtCharSpeciaux deleUser2 = new MotsClesEtCharSpeciaux();

	private int index = 0;

	private Integer montant;

	private List<MotsClesEtCharSpeciaux> listMotsCles = new ArrayList<MotsClesEtCharSpeciaux>();

	private List<MotsClesEtCharSpeciaux> listMotsClesToRemove = new ArrayList<MotsClesEtCharSpeciaux>();

	private String regex1 = "[0-9]+";

	private String regex2 = "[0-9, /,]+";




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
		return "/views/virementsRec/ParamDialog.xhtml";
	}


	public CaracteristiquesVirItem getTypeParam() {
		return typeParam;
	}


	public void setTypeParam(CaracteristiquesVirItem typeParam) {
		this.typeParam = typeParam;
	}


	public List<SelectItem> getSelectItems2() {
		return selectItems2;
	}


	public void setSelectItems2(List<SelectItem> selectItems2) {
		this.selectItems2 = selectItems2;
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


	public void setCurrentObject(Parametrages currentObject) {
		this.currentObject = currentObject;
	}


	public Integer getMontant() {
		return montant;
	}


	public void setMontant(Integer montant) {
		this.montant = montant;
	}


	public String getMotCle() {
		return motCle;
	}


	public void setMotCle(String motCle) {
		this.motCle = motCle;
	}


	public List<MotsClesEtCharSpeciaux> getListMotsCles() {
		return listMotsCles;
	}


	public void setListMotsCles(List<MotsClesEtCharSpeciaux> listMotsCles) {
		this.listMotsCles = listMotsCles;
	}


	@SuppressWarnings("unchecked")
	@Override
	public Parametrages getCurrentObject() {
		// TODO Auto-generated method stub
		return this.currentObject;
	}

	@Override
	public boolean checkBuildedCurrentObject() {
		// TODO Auto-generated method stub
		////||currentObject.getNature().isEmpty() || currentObject.getTope().isEmpty() || currentObject.getCodeEnreg().isEmpty()
		if(String.valueOf(currentObject.getMontant()).isEmpty() || currentObject.getOpeSystac().isEmpty() || currentObject.getOpp().isEmpty() || String.valueOf(currentObject.getSeuil()).isEmpty()){
			this.error=true;
			ExceptionHelper.showError("Veuillez remplir tous les champs obligatoires!", this);
			return false;
		}

		/**if(!currentObject.getTope().matches(regex1)){

			if(!currentObject.getTope().matches(regex2)){
				this.error=true;
				ExceptionHelper.showError("Vérifiez le format du type d'opérations! Séparez les par les virgules s'il y en a plusieurs!", this);
				return false;
			}
		}*/

		/**if(!currentObject.getCodeEnreg().matches(regex1)){

			if(!currentObject.getCodeEnreg().matches(regex2)){
				this.error=true;
				ExceptionHelper.showError("Vérifiez le format du code enregistrement! Séparez les par les virgules s'il y en a plusieurs!", this);
				return false;
			}
		}*/

		if(!currentObject.getChapitres().isEmpty()){
			if(!currentObject.getChapitres().matches(regex1)){
				if(!currentObject.getChapitres().matches(regex2)){
					this.error=true;
					ExceptionHelper.showError("Vérifiez le format du chapitre à exclure! Séparez les par les virgules s'il y en a plusieurs!", this);
					return false;
				}
			}
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

		List<Parametrages> listParams = ViewHelper.virementsRecManager.filterParams();
		if(listParams!=null && !listParams.isEmpty()){

			currentObject = listParams.get(0);

			if(currentObject!=null){

				montant = currentObject.getMontant().intValue();

				if(currentObject.getTypeCarac()!=null){

					System.out.println(currentObject.getTypeCarac().isEmpty());

					caracVir = ViewHelper.virementsRecManager.filterCaracteristiquesVir(currentObject, null,null);

					listMotsCles = ViewHelper.virementsRecManagerDAOLocal.filter(MotsClesEtCharSpeciaux.class, null, RestrictionsContainer.getInstance().add(Restrictions.eq("param", currentObject)).add(Restrictions.eq("valide", Boolean.TRUE)), null, null, 0, -1);
				}
			}
		}

		selectItems2.add(new SelectItem(null, "---Choisir---"));
		for (CaracteristiquesVirItem val : CaracteristiquesVirItem.typeParam()){
			selectItems2.add(new SelectItem(val.name(), Multilangue.getMessage(val.value(), this)));
		}

	}

	@Override
	protected void buildCurrentObject() throws ParseException {

		this.currentObject.setTypeCarac(this.caracVir);
		this.currentObject.setMontant(Double.valueOf(montant));
		this.currentObject.setMotsClesEtCharSpeciauxs(listMotsCles);

	}



	@Override
	protected void validate() {

		this.currentObject = ViewHelper.virementsRecManager.updateParametrages(currentObject);


		for(CaracteristiquesVir c: caracVirToRemove){

			List<CaracteristiquesVir> listCar = ViewHelper.virementsRecManager.filterCaracteristiquesVir(null,c,null);
			if(listCar!=null&&!listCar.isEmpty()){
				listCar.get(0).setValide(Boolean.FALSE);
				ViewHelper.virementsRecManager.updateCaracteristiquesVir(listCar.get(0));
				System.out.println("Suppression de " + listCar.get(0).getCaracteristiquesItems() + " effectuée avec succes!");
			}
		}

		for(MotsClesEtCharSpeciaux c: listMotsClesToRemove){

			List<MotsClesEtCharSpeciaux> listMC = ViewHelper.virementsRecManager.filterMotsCles(null,c);
			if(listMC!=null&&!listMC.isEmpty()){
				listMC.get(0).setValide(Boolean.FALSE);
				ViewHelper.virementsRecManagerDAOLocal.update(listMC.get(0));
				System.out.println("Suppression de " + listMC.get(0).getMotCles() + " effectuée avec succes!");
			}
		}

	}

	@Override
	protected void disposeResourceOnClose() {

		selectItems2.clear();
		caracVir.clear();
		caracVirToRemove.clear();
		motCle="";

	}



	public CaracteristiquesVir getDeleUser() {
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
	}


	public MotsClesEtCharSpeciaux getDeleUser2() {
		return deleUser2;
	}

	public void setDeleUser2(MotsClesEtCharSpeciaux deleUser2) {
		this.deleUser2 = deleUser2;

		try {

			if(deleUser2 != null){


				listMotsCles.remove(deleUser2);

				listMotsClesToRemove.add(deleUser2);

				deleUser2.setValide(Boolean.FALSE);

				System.out.println("Mot Clé " +deleUser2.getMotCles() + " enlevé avec succes!");

			}

		} catch (Exception e) {

			// Traitement de l'exception
			ExceptionHelper.threatException(e);
		}
	}


	public void ajouterParam() {

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

	}

	public void ajouterParam2() {
		if(motCle!=null && !motCle.isEmpty() ){

			currentObject2 = new MotsClesEtCharSpeciaux();
			currentObject2.setMotCles(motCle);
			currentObject2.setParam(currentObject);

			listMotsCles.add(currentObject2);
			motCle = "";

		}
	}


	/**
	 * recuperation de l element de la liste
	 * @return
	 */
	public CaracteristiquesVir getElement(){

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

	}

}


/*****
 * 
 * 
 * 
 * 	<table width="100%" align="center" cellpadding="0" cellspacing="0"
				border="0">
				<tr>

				</tr>
			</table>
 * 
 * 
 * 
 * 	<td><h:outputText value="Nature" styleClass="labelStyle" /></td>
							<td><h:inputText value="#{paramDialog.currentObject.nature}"
									styleClass="text ui-widget-content ui-corner-all"
									maxlength="20">
								</h:inputText></td>

							<td><h:outputText value="Type Opération"
									styleClass="labelStyle" /></td>
							<td><h:inputText value="#{paramDialog.currentObject.tope}"
									styleClass="text ui-widget-content ui-corner-all"
									maxlength="20">
								</h:inputText></td>



							<td><h:outputText value="Code Enregistrement"
									styleClass="labelStyle" /></td>
							<td><h:inputText
									value="#{paramDialog.currentObject.codeEnreg}"
									styleClass="text ui-widget-content ui-corner-all"
									maxlength="20">
								</h:inputText></td>

 */
