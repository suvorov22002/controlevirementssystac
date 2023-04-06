package com.afb.portal.presentation.models.reportViewer;

import java.io.File;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import javax.faces.context.FacesContext;

import com.afb.portal.presentation.models.AbstractDialog;
import com.afb.portal.presentation.models.IViewComponent;
import com.afb.portal.presentation.tools.ExceptionHelper;
import com.afb.portal.presentation.tools.Multilangue;
import com.afb.portal.presentation.tools.ViewHelper;

/**
 * Classe representant le modele du visualisateur d'etats 
 * @author Francis Konchou, Stéphane M
 * @version 1.0
 */
public class ReportViewer extends AbstractDialog {

	/**
	 * Model de données de l'etat
	 */
	private ControlReportViewerModel reportData = new ControlReportViewerModel();
	
	
	/**
	 * 
	 */
	private String fileDefinition;
	
	/**
	 * Titre de l'etat
	 */
	private String reportTitle = "";
	
	/**
	 * Type de mime PDF
	 */
	public static final String MIME_PDF = "application/pdf";
	
	/**
	 * Type de mime HTML
	 */
	public static final String MIME_HTML = "text/html";
	
	public static final String MIME_WORD = "application/msword";
	
	public static final String MIME_EXCEL = "application/vnd.ms-excel";
	
	public static final String MIME_CSV = "text/csv";
	
	public static final String MIME_TXT = "text/plain";

	
	/**
	 * Type de mime de l'etat
	 */
	private String mimeType = MIME_PDF;
	
	private String creditType = "scolaires";
	
	/**
	 * Donnees du flux du fichier
	 */
	private byte[] streamData = null;
	
		
	/**
	 * Constructeur par defaut
	 */
	public ReportViewer(){}

	/**
	 * Constructeur avec initialisation des parametres
	 * @param dataSource Source de donnees de l'etat
	 * @param reportFileName Nom du fichier .jasper de l'etat
	 * @param parameters Map des parametres a passer a l'etat
	 * @param Titre de l'etat
	 */
	public ReportViewer(Collection<?> dataSource, String reportFileName, Map<String, Object> parameters, String reportTitle , IViewComponent parent,String fileDefinition){

		this.fileDefinition = fileDefinition;
		
		// Initialisation du modele de donnees de l'etat
		reportData = new ControlReportViewerModel();
		
		// Lecture de la source de donnees
		reportData.setPrincipalDataSource(dataSource);
		
		// Lecture du fichier de l'etat
		reportData.setReportFileName(reportFileName);
		
		this.parent = parent;
		
		// Lecture du Titre de l'etat
		this.reportTitle = reportTitle;
		
		System.out.println("********parameters.size()********" + parameters.size());
		 
		if(parameters != null && !parameters.isEmpty()) for(Entry<String, Object> entry : parameters.entrySet()) reportData.getParameters().put(entry.getKey(), entry.getValue()); //reportData.getParameters().putAll(parameters);
		
		System.out.println("********reportData.getParameters().size()********" + reportData.getParameters().size());
		
		// Construction de l'etat
		buildReport("");
		
		this.open = true;
	}

	public ReportViewer(Collection<?> dataSource, String reportFileName, Map<String, Object> parameters, String reportTitle, String mime,String fileDefinition, String nameFile){

		this.fileDefinition = fileDefinition;
		
		// Initialisation du modele de donnees de l'etat
		reportData = new ControlReportViewerModel();
		
		// Lecture de la source de donnees
		reportData.setPrincipalDataSource(dataSource);
		
		// Lecture du fichier de l'etat
		reportData.setReportFileName(reportFileName);
		 
		//if(parameters != null) reportData.getParameters().putAll(parameters);
		
		System.out.println("********parameters.size()********" + parameters.size());
		
		if(parameters != null && !parameters.isEmpty()) for(Entry<String, Object> entry : parameters.entrySet()) reportData.getParameters().put(entry.getKey(), entry.getValue()); //reportData.getParameters().putAll(parameters);
		
		System.out.println("********reportData.getParameters().size()********" + reportData.getParameters().size());
		
		// Lecture du Titre de l'etat
		this.reportTitle = reportTitle;
		
		// Lecture du type mime
		if(mime != null) this.mimeType = mime;
		
		//if(creditType != null) this.creditType = creditType;
		
		// Construction de l'etat
		//System.out.println("ss la");
		//buildReport();
		buildReport(nameFile);

		
	}
	
	/**
	 * Methode de construction de l'etat
	 */
	private void buildReport(String nameFile){
		//System.out.println("ss entré");
		//System.out.println(mimeType);
		
		try{
			
			// Construction de l'etat
			//streamData = mimeType.equals(MIME_HTML) ? ReportBuilder.getReportHtmlBytes(reportData.getReportFileName(), new HashMap<Object, Object>(reportData.getParameters()), reportData.getPrincipalDataSource()) : ReportBuilder.getReportPDFBytes(reportData.getReportFileName(), new HashMap<Object, Object>(reportData.getParameters()), reportData.getPrincipalDataSource());

			if(mimeType.equals(MIME_HTML))
			{
				streamData = ReportBuilder.getReportHtmlBytes(reportData.getReportFileName(), new HashMap<Object, Object>(reportData.getParameters()), reportData.getPrincipalDataSource());
			}
			else if(mimeType.equals(MIME_PDF))
			{   System.out.println("******IN MIME TYPE PDF*****");
				streamData = ReportBuilder.getReportPDFBytes(reportData.getReportFileName(), new HashMap<Object, Object>(reportData.getParameters()), reportData.getPrincipalDataSource());
			}
			else if(mimeType.equals(MIME_WORD)&&creditType.equals("scolaires"))
			{
				//System.out.println("ss ici oooo");
				
				streamData = ReportBuilder.exportReportToWordFile(reportData.getReportFileName(), new HashMap<Object, Object>(reportData.getParameters()), reportData.getPrincipalDataSource());
			    //ReportBuilder.exportReportToWordFile(reportData.getReportFileName(), new HashMap<Object, Object>(reportData.getParameters()), reportData.getPrincipalDataSource(),"report.docx");
			}
			else if(mimeType.equals(MIME_WORD)&&creditType.equals("fin_annee"))
			{
				//System.out.println("ss ici oooo");
				streamData = ReportBuilder.exportReportToWordFile2(reportData.getReportFileName(), new HashMap<Object, Object>(reportData.getParameters()), reportData.getPrincipalDataSource());
			    //ReportBuilder.exportReportToWordFile(reportData.getReportFileName(), new HashMap<Object, Object>(reportData.getParameters()), reportData.getPrincipalDataSource(),"report.docx");
			}
			else if(mimeType.equals(MIME_WORD)&&creditType.equals("classiques"))
			{
				//System.out.println("ss ici oooo");
				streamData = ReportBuilder.exportReportToWordFile3(reportData.getReportFileName(), new HashMap<Object, Object>(reportData.getParameters()), reportData.getPrincipalDataSource());
			    //ReportBuilder.exportReportToWordFile(reportData.getReportFileName(), new HashMap<Object, Object>(reportData.getParameters()), reportData.getPrincipalDataSource(),"report.docx");
			}
			else if(mimeType.equals(MIME_WORD)&&creditType.equals("religieuses"))
			{
				//System.out.println("ss ici oooo");
				streamData = ReportBuilder.exportReportToWordFile4(reportData.getReportFileName(), new HashMap<Object, Object>(reportData.getParameters()), reportData.getPrincipalDataSource());
			    //ReportBuilder.exportReportToWordFile(reportData.getReportFileName(), new HashMap<Object, Object>(reportData.getParameters()), reportData.getPrincipalDataSource(),"report.docx");
			}
			else if(mimeType.equals(MIME_EXCEL))
			{
				streamData = ReportBuilder.getReportExcelBytes(reportData.getReportFileName(), new HashMap<Object, Object>(reportData.getParameters()), reportData.getPrincipalDataSource(), nameFile);
			}
			else if(mimeType.equals(MIME_CSV)){
				
				streamData = ReportBuilder.getReportCSVBytes(reportData.getReportFileName(), new HashMap<Object, Object>(reportData.getParameters()), reportData.getPrincipalDataSource(), nameFile);
			
			}else if(mimeType.equals(MIME_TXT)){
				
				streamData = ReportBuilder.getReportTextBytes(reportData.getReportFileName(), new HashMap<Object, Object>(reportData.getParameters()), reportData.getPrincipalDataSource(), nameFile);

			}
				
				
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Retourne les donnes de l'etat
	 * @return reportData
	 */
	public ControlReportViewerModel getReportData() {
		return reportData;
	}

	@Override
	public String getIcon() {

		// Chemin de l'Image
		return ViewHelper.getSessionSkinURLStatic() + "/Images/CountryListPanelLogo.png";
	}
	
	/**
	 * Titre de l'etat
	 * @return reportTitle
	 */
	public String getReportTitle() {
		return reportTitle;
	}

	/*
	 * (non-Javadoc)
	 * @see com.afrikbrain.pme.ehr.presentation.models.IPanel#getFileDefinition()
	 */
	@Override
	public String getFileDefinition(){
		// Fichier de definition du visualisateur
		return this.fileDefinition;
	}
	
	/**
	 * @param fileDefinition the fileDefinition to set
	 */
	public void setFileDefinition(String fileDefinition){
		this.fileDefinition = fileDefinition;
	}

	/*
	 * (non-Javadoc)
	 * @see com.afrikbrain.pme.ehr.presentation.models.IPanel#getName()
	 */
	public String getName() {
		
		// Nom du visualisateur
		return "ControlReportViewer";
	}

	/*
	 * (non-Javadoc)
	 * @see com.afrikbrain.pme.ehr.presentation.models.IPanel#getTitle()
	 */
	@Override
	public String getTitle() {
		
		// Titre du visualisateur
		return "afb.reportviewer.title";
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.afrikbrain.pme.ehr.presentation.models.IViewComponent#getChildDialogDefinition()
	 */
	@Override
	public String getChildDialogDefinition() {
		
		// Panneau vide
		return "/views/home/EmptyPage.xhtml";
	}
	
	/**
	 * Affichage du visualisateur d'etat
	 */
	public void viewReport() {
		
		// Ouverture du viewer
		this.open();
	}

	
	/**
	 * Affichage du visualisateur d'etat
	 */
	public void closeReport() {
		
		try{
			
			// Fermeture du viewer
			this.cancel();
			
			// Annulation du ReportData
			reportData = null;
			
			// Annulation des donnees de l'etat
			streamData = null;
			
								
		}catch(Exception e){
			
		}
		
		
	}
	
	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	/**
	 * Creation du fichier envoye a l'indice object
	 * @param stream
	 * @param object
	 */
    public void paint(OutputStream stream, Object object) {
    	
    	try{
    		
    		// Si le tableau de données n'est pas vide
    		if(streamData != null && streamData.length>0){
    			
    			// Affichage du flux
    			stream.write(streamData);
    			//System.out.println("------------------" + stream);
    		}
    		
    	} catch(Exception e) {
    		
			// Etat d'erreur
			this.error = true;
			
			// Traitement de l'exception
			ExceptionHelper.threatException(e);
    	}
    }
    
    public long getTimeStamp(){
    	
    	// Retourne la duree
        return System.currentTimeMillis();
    }
    
    public MediaData getMediaData(){
    	return new MediaData();
    }

	@Override
	protected void buildCurrentObject() {
	}

	@Override
	protected void disposeResourceOnClose() {
	}

	@Override
	public void initComponents() {
	}
	
	
	@Override
	protected void validate() {
	}

	@Override
	public boolean checkBuildedCurrentObject() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ControlReportViewerModel getCurrentObject() {
		// TODO Auto-generated method stub
		return reportData;
	}


	@Override
	public String getSecondChildDefinition() {
		// TODO Auto-generated method stub
		return null;
	}
	


	
}


/**
 * Classe representant le modele de données du visualisateur d'etat
 * @author Francis L
 * @version 1.0
 */
class ControlReportViewerModel extends AbstractReportViewerModel {

	/*
	 * (non-Javadoc)
	 * @see com.afrikbrain.pme.genezis.reportviewer.tools.managedbean.AbstractGenezisReportViewerModel#reloadReport()
	 */
	@Override
	public void reloadReport() {}
	
	/**
	 * Constructeur
	 */
	public ControlReportViewerModel() {
		
		// Lecture du repertoire des etats
		this.reportRootDiretory = ViewHelper.getReportsDir() + File.separator;
		
		// initialisation des parametres de l'etat
		this.parameters = new HashMap<Object, Object>();
		
		// Initialisation des parametres de base de l'etat
		this.parameters.put("REPORT_RESOURCE_BUNDLE", ResourceBundle.getBundle(FacesContext.getCurrentInstance().getApplication().getMessageBundle(), Multilangue.getLocale(FacesContext.getCurrentInstance()),Multilangue.getClassLoader() ));
		this.parameters.put("REPORT_LOCALE", Multilangue.getLocale(FacesContext.getCurrentInstance()));
		//this.parameters.put("SUBREPORT_DIR", this.reportRootDiretory);
		this.parameters.put("SUBREPORT_DIR", ViewHelper.getReportsDir()+File.separator);
		this.parameters.put("image", ViewHelper.getReportsDir()+File.separator+"image.png");
	}

	/**
	 * Retourne la map des parametres de l'etat
	 */
	@Override
	public Map<Object, Object> getParameters() {
		
		// Retourne les parametres de l'etat 
		return this.parameters;
	}
}