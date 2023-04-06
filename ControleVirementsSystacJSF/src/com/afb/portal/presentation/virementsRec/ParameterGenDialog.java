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
import com.yashiro.persistence.utils.dao.tools.RestrictionsContainer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ParameterGenDialog extends AbstractDialog{

	private ParametragesGenTeleCompense currentObject = new ParametragesGenTeleCompense();

	private ParamEmail currentObject2 = new ParamEmail();

	private ParametragesCaracteresSpeciaux currentObject3 = new ParametragesCaracteresSpeciaux();

	private List<SelectItem> selectItems2 = new ArrayList<SelectItem>();

	private List<ParamEmail> emails = new ArrayList<ParamEmail>();

	private List<ParamEmail> emailsToRemove = new ArrayList<ParamEmail>();

	private List<ParametragesCaracteresSpeciaux> listCaracteresSpeciauxs = new ArrayList<ParametragesCaracteresSpeciaux>();

	private List<ParametragesCaracteresSpeciaux> listCaracteresSpeciauxsToRemove = new ArrayList<ParametragesCaracteresSpeciaux>();


	private Selection selection;

	private ParamEmail deleUser = new ParamEmail();

	private ParametragesCaracteresSpeciaux deleUser2 = new ParametragesCaracteresSpeciaux();

	private int index = 0;

	private Pattern pattern;

	private Matcher matcher;

	private String nom;

	private String email;

	private String caractereSpecial;

	private String caractereDeRemplacement;

	private User user;


	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";



	public void openParamDialog(){

		this.mode = DialogFormMode.UPDATE;

		initComponents();

		pattern = Pattern.compile(EMAIL_PATTERN);

		this.open();
	}

	public boolean valide(final String hex) {

		System.out.println("*****ENTERED HERE*******");

		matcher = pattern.matcher(hex);

		System.out.println("*****matcher*******" + matcher);

		return matcher.matches();

	}

	@Override
	public String getTitle() { 
		// TODO Auto-generated method stub
		return "Paramétrages Généraux";
	}

	@Override
	public String getFileDefinition() {
		// TODO Auto-generated method stub
		return "/views/virementsRec/ParameterGenDialog.xhtml";
	}


	public List<ParamEmail> getEmails() {
		return emails;
	}


	public void setEmails(List<ParamEmail> emails) {
		this.emails = emails;
	}


	public ParamEmail getCurrentObject2() {
		return currentObject2;
	}


	public void setCurrentObject2(ParamEmail currentObject2) {
		this.currentObject2 = currentObject2;
	}


	public int getIndex() {
		return index;
	}


	public void setIndex(int index) {
		this.index = index;
	}



	public List<SelectItem> getSelectItems2() {
		return selectItems2;
	}


	public void setSelectItems2(List<SelectItem> selectItems2) {
		this.selectItems2 = selectItems2;
	}


	public Selection getSelection() {
		return selection;
	}


	public void setSelection(Selection selection) {
		this.selection = selection;
	}


	public List<ParamEmail> getEmailsToRemove() {
		return emailsToRemove;
	}

	public void setEmailsToRemove(List<ParamEmail> emailsToRemove) {
		this.emailsToRemove = emailsToRemove;
	}

	public Pattern getPattern() {
		return pattern;
	}

	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}

	public Matcher getMatcher() {
		return matcher;
	}

	public void setMatcher(Matcher matcher) {
		this.matcher = matcher;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setCurrentObject(ParametragesGenTeleCompense currentObject) {
		this.currentObject = currentObject;
	}


	public ParametragesCaracteresSpeciaux getCurrentObject3() {
		return currentObject3;
	}

	public void setCurrentObject3(ParametragesCaracteresSpeciaux currentObject3) {
		this.currentObject3 = currentObject3;
	}

	public List<ParametragesCaracteresSpeciaux> getListCaracteresSpeciauxs() {
		return listCaracteresSpeciauxs;
	}

	public void setListCaracteresSpeciauxs(
			List<ParametragesCaracteresSpeciaux> listCaracteresSpeciauxs) {
		this.listCaracteresSpeciauxs = listCaracteresSpeciauxs;
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
	public ParametragesGenTeleCompense getCurrentObject() {
		// TODO Auto-generated method stub
		return this.currentObject;
	}

	@Override
	public boolean checkBuildedCurrentObject() {
		// TODO Auto-generated method stub

		if(currentObject.getRepertoireEntreeCollecteImageAller().trim().isEmpty()||currentObject.getRepertoireEntreeCollecteImageRetour().trim().isEmpty()||currentObject.getRepertoireEntreeFichierComptabilisationAller().trim().isEmpty()||currentObject.getRepertoireEntreeFichierComptabilisationRetour().trim().isEmpty()||currentObject.getRepertoireDestinationCollecteImageAller().trim().isEmpty()
				||currentObject.getRepertoireDestinationCollecteImageRetour().trim().isEmpty()||currentObject.getRepertoireDestinationFichierComptabilisationAller().trim().isEmpty()||currentObject.getRepertoireDestinationFichierComptabilisationRetour().trim().isEmpty()||currentObject.getRepertoireArchivageCollecteImageAller().trim().isEmpty()||currentObject.getRepertoireArchivageCollecteImageRetour().trim().isEmpty()||
				currentObject.getRepertoireArchivageFichierComptabilisationAller().trim().isEmpty()||currentObject.getRepertoireArchivageFichierComptabilisationRetour().trim().isEmpty()||currentObject.getTempsArchivageCollecteImageAller()==null||currentObject.getTempsArchivageCollecteImageRetour()==null||currentObject.getTempsArchivageFichierComptabilisationAller()==null||currentObject.getTempsArchivageFichierComptabilisationRetour()==null
				||currentObject.getRepertoireArchiveEntreeCollecteImageAller().trim().isEmpty()||currentObject.getRepertoireArchiveEntreeCollecteImageRetour().trim().isEmpty()||currentObject.getRepertoireArchiveEntreeFichierComptabilisationAller().trim().isEmpty()||currentObject.getRepertoireArchiveEntreeFichierComptabilisationRetour().trim().isEmpty()){

			ExceptionHelper.showError("Il y a au moins un champ non-remplis dans le formulaire !!!", this);
			this.error=true;
			return false;
		}

		if(emails.isEmpty()){
			ExceptionHelper.showError("La liste des emails des superviseurs est vide !!!", this);
			this.error = true;
			return false;
		}

		List<String> repertoires = new ArrayList<String>();
		repertoires.add(currentObject.getRepertoireEntreeCollecteImageAller().trim());
		repertoires.add(currentObject.getRepertoireEntreeCollecteImageRetour().trim());
		repertoires.add(currentObject.getRepertoireEntreeFichierComptabilisationAller().trim());
		repertoires.add(currentObject.getRepertoireEntreeFichierComptabilisationRetour().trim());
		repertoires.add(currentObject.getRepertoireDestinationCollecteImageAller().trim());
		repertoires.add(currentObject.getRepertoireDestinationCollecteImageRetour().trim());
		repertoires.add(currentObject.getRepertoireDestinationFichierComptabilisationAller().trim());
		repertoires.add(currentObject.getRepertoireDestinationFichierComptabilisationRetour().trim());
		repertoires.add(currentObject.getRepertoireArchivageCollecteImageAller().trim());
		repertoires.add(currentObject.getRepertoireArchivageCollecteImageRetour().trim());
		repertoires.add(currentObject.getRepertoireArchivageFichierComptabilisationAller().trim());
		repertoires.add(currentObject.getRepertoireArchivageFichierComptabilisationRetour().trim());
		
		repertoires.add(currentObject.getRepertoireArchiveEntreeCollecteImageAller());
		repertoires.add(currentObject.getRepertoireArchiveEntreeCollecteImageRetour());
		repertoires.add(currentObject.getRepertoireArchiveEntreeFichierComptabilisationAller());
		repertoires.add(currentObject.getRepertoireArchiveEntreeFichierComptabilisationRetour());
		


		/**List<Date> datesArchives = new ArrayList<Date>();
		datesArchives.add(currentObject.getTempsArchivageCollecteImageAller());
		datesArchives.add(currentObject.getTempsArchivageCollecteImageRetour());
		datesArchives.add(currentObject.getTempsArchivageFichierComptabilisationAller());
		datesArchives.add(currentObject.getTempsArchivageFichierComptabilisationRetour());*/


		int l;
		for(int k=0; k<repertoires.size(); k++){

			l=k+1;

			//On compare chaque ligne k avec toutes les autres lignes de k+1 en montant pour vérifier s'il y a un ou plusieurs doublons
			while(l<repertoires.size()){

				if(repertoires.get(k).equals(repertoires.get(l))){

					ExceptionHelper.showError("Chaque répertoire doit être unique !!! Revoyez votre paramétrage !!!", this);
					this.error=true;
					return false;
				}

				l++;
			}

		}

		/**int n;
		for(int m=0; m<datesArchives.size(); m++){

			n=m+1;

			//On compare chaque ligne k avec toutes les autres lignes de k+1 en montant pour vérifier s'il y a un ou plusieurs doublons
			while(n<datesArchives.size()){

				if(datesArchives.get(m).equals(datesArchives.get(n))){

					ExceptionHelper.showError("Chaque date de suppression d'archives doit être unique !!! Revoyez votre paramétrage !!!", this);
					this.error=true;
					return false;
				}

				if(datesArchives.get(m).before(new Date())){

					ExceptionHelper.showError("Les dates de suppression d'archives doivent être supérieure à la date d'aujourd'hui !!! Revoyez votre paramétrage !!!", this);
					this.error=true;
					return false;
				}

				n++;
			}

		}*/

		if(emails!=null&&!emails.isEmpty()){
			for(ParamEmail email: emails){
				if(email!=null&&!email.getEmail().isEmpty()){
					if(valide(email.getEmail())==false){
						this.error = true;
						ExceptionHelper.showError("Mauvais format d'adresse email!!! Veuillez suivre le format tttttt@yyyy.zzz", this);
						return false;
					}
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

		user = ViewHelper.getSessionUser();

		List<ParametragesGenTeleCompense> listParams = ViewHelper.virementsRecManager.filterParamGen();
		if(listParams!=null && !listParams.isEmpty()){

			currentObject = listParams.get(0);
		}else{
			currentObject.setTempsArchivageCollecteImageAller(null);
			currentObject.setTempsArchivageCollecteImageRetour(null);
			currentObject.setTempsArchivageFichierComptabilisationAller(null);
			currentObject.setTempsArchivageFichierComptabilisationRetour(null);
		}

		emails = ViewHelper.virementsRecManager.filterEmails();

		listCaracteresSpeciauxs = ViewHelper.virementsRecManagerDAOLocal.filter(ParametragesCaracteresSpeciaux.class, null, RestrictionsContainer.getInstance().add(Restrictions.eq("valide", Boolean.TRUE)), null, null, 0, -1);

		caractereDeRemplacement = currentObject.getCaracterePourRemplacerCaractereSpecial();

		selectItems2.add(new SelectItem(null, "---Choisir---"));
		for (CaracteristiquesVirItem val : CaracteristiquesVirItem.typeParam()){
			selectItems2.add(new SelectItem(val.name(), Multilangue.getMessage(val.value(), this)));
		}

	}

	@Override
	protected void buildCurrentObject() throws ParseException {

		//this.currentObject.setTypeCarac(this.caracVir);

	}



	@Override
	protected void validate() {

		System.out.println("----emails size----" + emails.size());

		for(ParamEmail e: emails){
			/*List<ParamEmail> p = ViewHelper.virementsRecManager.filterEmails(e.getEmail().trim());
			if(p==null||p.isEmpty()){
				ViewHelper.virementsRecManager.saveParamEmail(e);
			}else{
				e.setParam(currentObject);
				ViewHelper.virementsRecManager.updateParamEmail(e);
			}*/
			e.setParam(currentObject);
		}
		currentObject.setListEmails(emails);


		for(ParamEmail e: emailsToRemove){
			if(e.getId()!=null){
				e = ViewHelper.virementsRecManagerDAOLocal.findByPrimaryKey(ParamEmail.class, e.getId(), null);
				e.setValide(Boolean.FALSE);
				System.out.println("+++++" + e.getEmail() + "+++++" + e.getValide());
				ViewHelper.virementsRecManager.updateParamEmail(e);	
			}
		}



		for(ParametragesCaracteresSpeciaux p: listCaracteresSpeciauxs){
			p.setParam(currentObject);
			//ViewHelper.virementsRecManagerDAOLocal.update(p);
		}
		currentObject.setListCaracteresSpeciaux(listCaracteresSpeciauxs);


		for(ParametragesCaracteresSpeciaux p: listCaracteresSpeciauxsToRemove){
			if(p.getId()!=null){
				p = ViewHelper.virementsRecManagerDAOLocal.findByPrimaryKey(ParametragesCaracteresSpeciaux.class, p.getId(), null);
				p.setValide(Boolean.FALSE);
				System.out.println("+++++" + p.getCaractereSpecial() + "+++++" + p.getValide());
				ViewHelper.virementsRecManagerDAOLocal.update(p);
			}
		}


		currentObject.setUtiParam(user.getLogin());
		currentObject.setCaracterePourRemplacerCaractereSpecial(caractereDeRemplacement);

		this.currentObject = ViewHelper.virementsRecManager.updateParametragesGenTeleCompense(currentObject);

		initComponents();

		ViewHelper.virementsRecManager.robotSuppressionArchives();

	}

	@Override
	protected void disposeResourceOnClose() {

		nom = "";
		email = "";
		selectItems2.clear();
		emails.clear();
		emailsToRemove.clear();
		listCaracteresSpeciauxs.clear();
		listCaracteresSpeciauxsToRemove.clear();

		currentObject.setRepertoireEntreeCollecteImageAller("");
		currentObject.setRepertoireEntreeCollecteImageRetour("");
		currentObject.setRepertoireEntreeFichierComptabilisationAller("");
		currentObject.setRepertoireEntreeFichierComptabilisationRetour("");

		currentObject.setRepertoireDestinationCollecteImageAller("");
		currentObject.setRepertoireEntreeCollecteImageRetour("");
		currentObject.setRepertoireEntreeFichierComptabilisationAller("");
		currentObject.setRepertoireEntreeFichierComptabilisationRetour("");

		currentObject.setRepertoireArchivageCollecteImageAller("");
		currentObject.setRepertoireArchivageCollecteImageRetour("");
		currentObject.setRepertoireArchivageFichierComptabilisationAller("");
		currentObject.setRepertoireArchivageFichierComptabilisationRetour("");

		currentObject.setTempsArchivageCollecteImageAller(null);
		currentObject.setTempsArchivageCollecteImageRetour(null);
		currentObject.setTempsArchivageFichierComptabilisationAller(null);
		currentObject.setTempsArchivageFichierComptabilisationRetour(null);


	}

	public void ajouterParam(){

		if(nom!=null && !nom.isEmpty() && email!=null && !email.isEmpty()){

			currentObject2 = new ParamEmail();
			currentObject2.setNom(nom.trim());
			currentObject2.setEmail(email.trim());

			emails.add(currentObject2);

			nom = "";
			email = "";

		}
	}

	public void ajouterParam2(){

		if(caractereSpecial!=null && !caractereSpecial.isEmpty() && caractereDeRemplacement!=null && !caractereDeRemplacement.isEmpty()){

			currentObject3 = new ParametragesCaracteresSpeciaux();
			currentObject3.setCaractereSpecial(caractereSpecial);

			listCaracteresSpeciauxs.add(currentObject3);

			caractereSpecial = "";

		}
	}


	public ParamEmail getDeleUser() {
		return deleUser;
	}

	public void setDeleUser(ParamEmail deleUser) {
		this.deleUser = deleUser;

		try {

			if(deleUser != null){

				System.out.println("email " +deleUser.getEmail());

				System.out.println("----emails size before remove----" + emails.size());

				//deleUser = getElement();

				emailsToRemove.add(deleUser);

				emails.remove(deleUser);


				System.out.println("----emails size after remove----" + emails.size());

				//deleUser.setValide(Boolean.FALSE);

				//caracVir.add(deleUser);

				System.out.println("email " +deleUser.getEmail() + " enlevé avec succes!");

			}

		} catch (Exception e) {

			// Traitement de l'exception
			ExceptionHelper.threatException(e);
		}
	}


	public ParametragesCaracteresSpeciaux getDeleUser2() {
		return deleUser2;
	}

	public void setDeleUser2(ParametragesCaracteresSpeciaux deleUser2) {
		this.deleUser2 = deleUser2;

		try {

			if(deleUser != null){

				System.out.println("CaractereSpecial " +deleUser2.getCaractereSpecial());

				listCaracteresSpeciauxsToRemove.add(deleUser2);

				listCaracteresSpeciauxs.remove(deleUser2);

			}

		} catch (Exception e) {

			// Traitement de l'exception
			ExceptionHelper.threatException(e);
		}
	}


	@Override
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

	}



	/*public void ajouterParam() {

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
	 */




}
