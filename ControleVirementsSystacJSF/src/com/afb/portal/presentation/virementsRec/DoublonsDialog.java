package com.afb.portal.presentation.virementsRec;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.richfaces.model.selection.Selection;

import com.afb.portal.presentation.models.AbstractDialog;
import com.afb.portal.presentation.models.DialogFormMode;
import com.afb.portal.presentation.models.IViewComponent;
import com.afb.portal.presentation.models.reportViewer.ReportViewer;
import com.afb.portal.presentation.tools.ExceptionHelper;
import com.afb.portal.presentation.tools.ViewHelper;
import com.afb.virementsRec.jpa.datamodel.Doublons;
import com.afb.virementsRec.jpa.datamodel.Parametrages;
import com.afb.virementsRec.jpa.datamodel.Traitement;



public class DoublonsDialog extends AbstractDialog {


	private Doublons currentObject = new Doublons();

	private int count;

	private int totalDoublons;

	private Parametrages param = new Parametrages();

	private List<Doublons> listDoublons = new ArrayList<Doublons>();

	private List<Doublons> listDoublonsAValider = new ArrayList<Doublons>();

	private List<Doublons> listDoublonsANePasValider = new ArrayList<Doublons>();


	private List<Traitement>listTraitement = new ArrayList<Traitement>();

	private Selection selection;

	private ReportViewer reportViewer;

	private Boolean check = Boolean.FALSE;

	private Doublons doublonObject = new Doublons();

	private Date dateDebut;

	private Date dateFin;

	private boolean isInBkeveOrBkheve;

	/**
	 * Constructeur par défaut
	 */
	public DoublonsDialog() {
		super();
	}

	/**
	 * Constructeur du Modele
	 * @param mode	Mode d'ouverture de la Boite
	 * @param currentObject	Objet Courant de la Boite
	 * @param parent	Composant Parent
	 */
	public DoublonsDialog(DialogFormMode mode, Doublons currentObject, Parametrages param, List<Doublons>doublons, List<Traitement>traitements, int count, IViewComponent parent, Date dateDebut, Date dateFin, boolean isInBkeveOrBkheve) {

		// Appel Parent
		super();

		// Initialisation des Parametres
		this.mode = mode;
		this.currentObject = currentObject;
		this.param = param;
		System.out.println("***doublons.size()****" +doublons.size());
		this.listDoublons.addAll(doublons);
		System.out.println("*******traitements.size()******" +traitements.size());
		this.listTraitement.addAll(traitements);
		this.count = count;
		this.dateDebut=dateDebut;
		this.dateFin=dateFin;
		this.parent = parent;
		this.isInBkeveOrBkheve = isInBkeveOrBkheve;

		// Initialisation des Composants
		initComponents();
		// this.open = true;

	}


	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Traitement des Doublons";
	}

	@Override
	public String getFileDefinition() {
		// TODO Auto-generated method stub
		return "/views/virementsRec/DoublonsDialog.xhtml";
	}

	@SuppressWarnings("unchecked")
	@Override
	public Doublons getCurrentObject() {
		// TODO Auto-generated method stub
		return this.currentObject;
	}

	@Override
	public boolean checkBuildedCurrentObject() {

		try{



		}
		catch(Exception e){

			e.printStackTrace();
		}

		return true;
	}

	@Override
	public String getChildDialogDefinition() {

		return null;
	}

	@Override
	public String getSecondChildDefinition() {

		return null;
	}

	public ReportViewer getReportViewer() {
		return reportViewer;
	}

	public void setReportViewer(ReportViewer reportViewer) {
		this.reportViewer = reportViewer;
	}

	public void setCurrentObject(Doublons currentObject) {
		this.currentObject = currentObject;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<Doublons> getListDoublons() {
		return listDoublons;
	}

	public void setListDoublons(List<Doublons> listDoublons) {
		this.listDoublons = listDoublons;
	}

	public Selection getSelection() {
		return selection;
	}

	public void setSelection(Selection selection) {
		this.selection = selection;
	}

	public int getTotalDoublons() {
		return totalDoublons;
	}

	public void setTotalDoublons(int totalDoublons) {
		this.totalDoublons = totalDoublons;
	}

	public Boolean getCheck() {
		return check;
	}

	public void setCheck(Boolean check) {
		this.check = check;
	}

	public Doublons getDoublonObject() {

		return doublonObject;
	}

	public void setDoublonObject(Doublons doublonObject) {
		this.doublonObject = doublonObject;
	}


	@Override
	public void initComponents() {

		try{

			if(mode.equals(DialogFormMode.CREATE)){

			}

			if(mode.equals(DialogFormMode.UPDATE) || mode.equals(DialogFormMode.READ)){


			}

		}
		catch(Exception e){

			this.error = true;
			e.printStackTrace();
			ExceptionHelper.threatException(e);
		}
	}



	@Override
	protected void buildCurrentObject() throws ParseException {

		if(mode.equals(DialogFormMode.CREATE)){


		}

		else if(mode.equals(DialogFormMode.UPDATE)){
		}
	}




	@Override
	protected void validate() {


	}


	public void save(){


		//Récupération des doublons à valider
		for(Doublons c : listDoublons){
			//c.setSelect(check);
			if(new SimpleDateFormat("dd/MM/yyyy").format(c.getDsai()).equals(new SimpleDateFormat("dd/MM/yyyy").format(new Date()))){
				if(c.getSelect().equals(Boolean.TRUE)){
					c.setValide(Boolean.TRUE);
					//c.setEtat("AT");
					listDoublonsAValider.add(c);
				}
				else{
					c.setValide(Boolean.FALSE);
					//c.setEtat("VA");
					listDoublonsANePasValider.add(c);
				}
			}
		}

		Boolean okay = ViewHelper.virementsRecManager.setDoublonsEnAttente(listDoublonsAValider,listDoublonsANePasValider,isInBkeveOrBkheve);

		System.out.println("***okay**"+ okay);

		//if(okay.equals(Boolean.TRUE)){
		/*******Affichage du rapport de contrôle de doublons*****/
		HashMap<String, Object> parameter = new HashMap<String, Object>();


		parameter.put("uti", ViewHelper.getSessionUser().getName());
		parameter.put("periode", new SimpleDateFormat("dd/MM/yyyy").format(dateDebut) + "-" + new SimpleDateFormat("dd/MM/yyyy").format(dateFin));
		System.out.println("****listDoublons.size()*****"+listDoublons.size());
		parameter.put("nombreVir", listTraitement.size());
		parameter.put("total", listDoublons.size());
		int nbrDoublonsVal = 0;
		int nbrDoublonsIgn = 0;
		for(Doublons doub: listDoublons){
			if(doub.getValide().equals(Boolean.TRUE)){ //doublons validés
				nbrDoublonsVal++;
				doub.setEtat("AT");
			}else{
				doub.setEtat("VA");
				nbrDoublonsIgn++;
			}
		}
		parameter.put("nbrDoublonsVal", nbrDoublonsVal);
		parameter.put("nbrDoublonsIgn", nbrDoublonsIgn);

		reportViewer = new ReportViewer(listDoublons,"rapportDoublons.jasper", parameter, "afb.statistique.title", this,"/views/repport/ReportDoublons.xhtml");
		reportViewer.viewReport();


		for(Doublons d: listDoublons){
			ViewHelper.virementsRecManager.saveDoublons(d);
			System.out.println("Saving Doublons");
		}


		for(Traitement t: listTraitement){

			/*if(t.equals(listTraitement.get(listTraitement.size()-1))){
					param.setLastElementOfLotDoublons(t.getId());
					ViewHelper.virementsRecManager.updateParametrages(param);
				}*/

			if(t.equals(listTraitement.get(listTraitement.size()-1))){

				if(param.getId()!=null){

					param = ViewHelper.virementsRecManager.findByPrimaryKey(Parametrages.class, param.getId(), null);

					param.setLastElementOfLotDoublons(t.getId());

					ViewHelper.virementsRecManager.updateParametrages(param);
				}
			}


			ViewHelper.virementsRecManager.updateTraitement(t);
		}

		//}else{

		/*System.out.println("Les doublons n'ont pas pu être mis en attente! Veuillez ré-essayer; en cas d'échec contacter la DSI!!!");
			this.information = false;
			this.error = true;
			this.open=false;
			ExceptionHelper.showError("Les doublons n'ont pas pu être mis en attente! Veuillez ré-essayer; en cas d'échec contacter la DSI!!!", this);
		 */
		//}

		if(!listDoublonsAValider.isEmpty())listDoublonsAValider.clear();
		if(!listDoublonsANePasValider.isEmpty())listDoublonsANePasValider.clear();

	}

	@Override
	protected void disposeResourceOnClose() {

		listDoublons.clear();
		count=0;
	}

	public void processCheckAll(ActionEvent event){

		// if(Boolean.FALSE.equals(check)) check = Boolean.TRUE;
		// else check = Boolean.FALSE;
		int count = 0;
		for(Doublons c : listDoublons){
			if(new SimpleDateFormat("dd/MM/yyyy").format(c.getDsai()).equals(new SimpleDateFormat("dd/MM/yyyy").format(new Date()))){
				c.setSelect(check);
				if(check.equals(Boolean.TRUE)){
					if(count==0)
						totalDoublons=0;
					totalDoublons+=1;
					count++;
				}
				else{
					if(totalDoublons!=0)
						totalDoublons-=1;
				}
			}else{

			}
		}

	}




}
