package com.afb.portal.presentation.virementsRec;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.afb.portal.presentation.models.AbstractDialog;
import com.afb.portal.presentation.models.DialogFormMode;
import com.afb.portal.presentation.tools.ExceptionHelper;

import com.afb.portal.presentation.tools.ViewHelper;
import com.afb.virementsRec.jpa.datamodel.ParamEmailAuto;
import com.yashiro.persistence.utils.dao.tools.encrypter.Encrypter;


public class ParamEmailAutoDialog extends AbstractDialog {


	private ParamEmailAuto currentObject = new ParamEmailAuto();

	private List<ParamEmailAuto> params = new ArrayList<ParamEmailAuto>();
	
	


	public ParamEmailAutoDialog() {

		this.mode = DialogFormMode.UPDATE;
		//initComponents();
	}

	public void openParamEmailAutoDialog(){

		try {

			System.out.println("----opening param dialog-----");

			//initializeComponents();

			initComponents();

			// Ouverture
			this.open();

		} catch (Exception e) {

			// Etat d'erreur
			this.error = true;
			// Traitement de l'exception
			ExceptionHelper.threatException(e);
		}
	}
	


	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Parametrages de l'email utilisé pour l'envoi des emails";
	}

	@Override
	public String getFileDefinition() {
		// TODO Auto-generated method stub
		return "/views/virementsRec/ParamEmailAutoDialog.xhtml";
	}

	@SuppressWarnings("unchecked")
	@Override
	public ParamEmailAuto getCurrentObject() {
		// TODO Auto-generated method stub
		return this.currentObject;
	}



	@Override
	public boolean checkBuildedCurrentObject() {
		// TODO Auto-generated method stub
		
		if(currentObject.getEmail().isEmpty()||currentObject.getIp().isEmpty()||currentObject.getPass().isEmpty()){
			
			ExceptionHelper.showError("Il y a au moins un champ non-remplis dans ce formulaire !!!", this);
			this.error = true;
			return false;
		}
		if(currentObject.getPort()==0){
			ExceptionHelper.showError("Le port ne peut pas être zéro (0) !!!", this);
			this.error = true;
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


		params =  ViewHelper.virementsRecManager.filterParamEmailAuto();
		System.out.println("-----params size-----" + params.size());
		if(params!=null&&!params.isEmpty()){

			currentObject = params.get(0);
		}else{
			currentObject.setPort(0);
		}


	}

	@Override
	protected void buildCurrentObject() throws ParseException {
		// TODO Auto-generated method stub
	}

	@Override
	protected void validate() {

		System.out.println("----mode---" + mode);

		currentObject.setPass(Encrypter.getInstance().encryptText(currentObject.getPass()));

		if(params!=null&&!params.isEmpty()){


			this.currentObject = ViewHelper.virementsRecManager.updateParamEmailAuto(currentObject);

		}else{


			this.currentObject = ViewHelper.virementsRecManager.saveParamEmailAuto(currentObject);
		}

	}

	@Override
	protected void disposeResourceOnClose() {

		currentObject.setEmail("");
		currentObject.setIp("");
		currentObject.setPass("");
		currentObject.setPort(0);
	}



}
