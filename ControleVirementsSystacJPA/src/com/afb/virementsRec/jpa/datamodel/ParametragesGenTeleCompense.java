package com.afb.virementsRec.jpa.datamodel;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * Entity implementation class for Entity: ParametragesGenTeleCompense
 *
 */
@Entity
@Table(name = "VIR_SYSTAC_PARAM_TELECOMP")
public class ParametragesGenTeleCompense implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public ParametragesGenTeleCompense() {
		super();
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PARAM_ID")
	private Long id;
	
	private String repertoireEntreeCollecteImageAller="";
	private String repertoireDestinationCollecteImageAller="";
	private String repertoireDestinationCollecteImageAllerKO="";
	private String repertoireArchivageCollecteImageAller="";
	private String repertoireArchiveEntreeCollecteImageAller="";
	@Temporal(TemporalType.TIMESTAMP)
	private Date tempsArchivageCollecteImageAller;
	
	private String repertoireEntreeFichierComptabilisationAller="";
	private String repertoireDestinationFichierComptabilisationAller="";
	private String repertoireDestinationFichierComptabilisationAllerKO="";
	private String repertoireArchivageFichierComptabilisationAller="";
	private String repertoireArchiveEntreeFichierComptabilisationAller="";
	@Temporal(TemporalType.TIMESTAMP)
	private Date tempsArchivageFichierComptabilisationAller;
	
	private String repertoireEntreeFichierComptabilisationRetour="";
	private String repertoireDestinationFichierComptabilisationRetour="";
	private String repertoireDestinationFichierComptabilisationRetourKO="";
	private String repertoireArchivageFichierComptabilisationRetour="";
	private String repertoireArchiveEntreeFichierComptabilisationRetour="";
	@Temporal(TemporalType.TIMESTAMP)
	private Date tempsArchivageFichierComptabilisationRetour;
	
	private String repertoireEntreeCollecteImageRetour="";
	private String repertoireDestinationCollecteImageRetour="";
	private String repertoireDestinationCollecteImageRetourKO="";
	private String repertoireArchivageCollecteImageRetour="";
	private String repertoireArchiveEntreeCollecteImageRetour="";
	@Temporal(TemporalType.TIMESTAMP)
	private Date tempsArchivageCollecteImageRetour;
	
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade=CascadeType.ALL, mappedBy="param",fetch=FetchType.LAZY)
	private List<ParamEmail> listEmails = new ArrayList<ParamEmail>();
	
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade=CascadeType.ALL, mappedBy="param",fetch=FetchType.LAZY)
	private List<ParametragesCaracteresSpeciaux> listCaracteresSpeciaux = new ArrayList<ParametragesCaracteresSpeciaux>();
	
	private String caracterePourRemplacerCaractereSpecial;
	

	private String utiParam;

	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getRepertoireEntreeCollecteImageAller() {
		return repertoireEntreeCollecteImageAller;
	}


	public void setRepertoireEntreeCollecteImageAller(
			String repertoireEntreeCollecteImageAller) {
		this.repertoireEntreeCollecteImageAller = repertoireEntreeCollecteImageAller;
	}


	public String getRepertoireDestinationCollecteImageAller() {
		return repertoireDestinationCollecteImageAller;
	}


	public void setRepertoireDestinationCollecteImageAller(
			String repertoireDestinationCollecteImageAller) {
		this.repertoireDestinationCollecteImageAller = repertoireDestinationCollecteImageAller;
	}


	public String getRepertoireArchivageCollecteImageAller() {
		return repertoireArchivageCollecteImageAller;
	}


	public void setRepertoireArchivageCollecteImageAller(
			String repertoireArchivageCollecteImageAller) {
		this.repertoireArchivageCollecteImageAller = repertoireArchivageCollecteImageAller;
	}


	public String getRepertoireEntreeFichierComptabilisationAller() {
		return repertoireEntreeFichierComptabilisationAller;
	}


	public void setRepertoireEntreeFichierComptabilisationAller(
			String repertoireEntreeFichierComptabilisationAller) {
		this.repertoireEntreeFichierComptabilisationAller = repertoireEntreeFichierComptabilisationAller;
	}


	public String getRepertoireDestinationFichierComptabilisationAller() {
		return repertoireDestinationFichierComptabilisationAller;
	}


	public void setRepertoireDestinationFichierComptabilisationAller(
			String repertoireDestinationFichierComptabilisationAller) {
		this.repertoireDestinationFichierComptabilisationAller = repertoireDestinationFichierComptabilisationAller;
	}


	public String getRepertoireArchivageFichierComptabilisationAller() {
		return repertoireArchivageFichierComptabilisationAller;
	}


	public void setRepertoireArchivageFichierComptabilisationAller(
			String repertoireArchivageFichierComptabilisationAller) {
		this.repertoireArchivageFichierComptabilisationAller = repertoireArchivageFichierComptabilisationAller;
	}


	
	public String getRepertoireEntreeFichierComptabilisationRetour() {
		return repertoireEntreeFichierComptabilisationRetour;
	}


	public void setRepertoireEntreeFichierComptabilisationRetour(
			String repertoireEntreeFichierComptabilisationRetour) {
		this.repertoireEntreeFichierComptabilisationRetour = repertoireEntreeFichierComptabilisationRetour;
	}


	public String getRepertoireDestinationFichierComptabilisationRetour() {
		return repertoireDestinationFichierComptabilisationRetour;
	}


	public void setRepertoireDestinationFichierComptabilisationRetour(
			String repertoireDestinationFichierComptabilisationRetour) {
		this.repertoireDestinationFichierComptabilisationRetour = repertoireDestinationFichierComptabilisationRetour;
	}


	public String getRepertoireArchivageFichierComptabilisationRetour() {
		return repertoireArchivageFichierComptabilisationRetour;
	}


	public void setRepertoireArchivageFichierComptabilisationRetour(
			String repertoireArchivageFichierComptabilisationRetour) {
		this.repertoireArchivageFichierComptabilisationRetour = repertoireArchivageFichierComptabilisationRetour;
	}


	

	public String getRepertoireEntreeCollecteImageRetour() {
		return repertoireEntreeCollecteImageRetour;
	}


	public void setRepertoireEntreeCollecteImageRetour(
			String repertoireEntreeCollecteImageRetour) {
		this.repertoireEntreeCollecteImageRetour = repertoireEntreeCollecteImageRetour;
	}


	public String getRepertoireDestinationCollecteImageRetour() {
		return repertoireDestinationCollecteImageRetour;
	}


	public void setRepertoireDestinationCollecteImageRetour(
			String repertoireDestinationCollecteImageRetour) {
		this.repertoireDestinationCollecteImageRetour = repertoireDestinationCollecteImageRetour;
	}


	public String getRepertoireArchivageCollecteImageRetour() {
		return repertoireArchivageCollecteImageRetour;
	}


	public void setRepertoireArchivageCollecteImageRetour(
			String repertoireArchivageCollecteImageRetour) {
		this.repertoireArchivageCollecteImageRetour = repertoireArchivageCollecteImageRetour;
	}


	

	public List<ParamEmail> getListEmails() {
		return listEmails;
	}


	public void setListEmails(List<ParamEmail> listEmails) {
		this.listEmails = listEmails;
	}


	public Date getTempsArchivageCollecteImageAller() {
		return tempsArchivageCollecteImageAller;
	}


	public void setTempsArchivageCollecteImageAller(
			Date tempsArchivageCollecteImageAller) {
		this.tempsArchivageCollecteImageAller = tempsArchivageCollecteImageAller;
	}


	public Date getTempsArchivageFichierComptabilisationAller() {
		return tempsArchivageFichierComptabilisationAller;
	}


	public void setTempsArchivageFichierComptabilisationAller(
			Date tempsArchivageFichierComptabilisationAller) {
		this.tempsArchivageFichierComptabilisationAller = tempsArchivageFichierComptabilisationAller;
	}


	public Date getTempsArchivageFichierComptabilisationRetour() {
		return tempsArchivageFichierComptabilisationRetour;
	}


	public void setTempsArchivageFichierComptabilisationRetour(
			Date tempsArchivageFichierComptabilisationRetour) {
		this.tempsArchivageFichierComptabilisationRetour = tempsArchivageFichierComptabilisationRetour;
	}


	public Date getTempsArchivageCollecteImageRetour() {
		return tempsArchivageCollecteImageRetour;
	}


	public void setTempsArchivageCollecteImageRetour(
			Date tempsArchivageCollecteImageRetour) {
		this.tempsArchivageCollecteImageRetour = tempsArchivageCollecteImageRetour;
	}


	public String getUtiParam() {
		return utiParam;
	}


	public void setUtiParam(String utiParam) {
		this.utiParam = utiParam;
	}


	public List<ParametragesCaracteresSpeciaux> getListCaracteresSpeciaux() {
		return listCaracteresSpeciaux;
	}


	public void setListCaracteresSpeciaux(
			List<ParametragesCaracteresSpeciaux> listCaracteresSpeciaux) {
		this.listCaracteresSpeciaux = listCaracteresSpeciaux;
	}


	public String getCaracterePourRemplacerCaractereSpecial() {
		return caracterePourRemplacerCaractereSpecial;
	}


	public void setCaracterePourRemplacerCaractereSpecial(
			String caracterePourRemplacerCaractereSpecial) {
		this.caracterePourRemplacerCaractereSpecial = caracterePourRemplacerCaractereSpecial;
	}


	public String getRepertoireArchiveEntreeCollecteImageAller() {
		return repertoireArchiveEntreeCollecteImageAller;
	}


	public void setRepertoireArchiveEntreeCollecteImageAller(
			String repertoireArchiveEntreeCollecteImageAller) {
		this.repertoireArchiveEntreeCollecteImageAller = repertoireArchiveEntreeCollecteImageAller;
	}


	public String getRepertoireArchiveEntreeFichierComptabilisationAller() {
		return repertoireArchiveEntreeFichierComptabilisationAller;
	}


	public void setRepertoireArchiveEntreeFichierComptabilisationAller(
			String repertoireArchiveEntreeFichierComptabilisationAller) {
		this.repertoireArchiveEntreeFichierComptabilisationAller = repertoireArchiveEntreeFichierComptabilisationAller;
	}


	public String getRepertoireArchiveEntreeFichierComptabilisationRetour() {
		return repertoireArchiveEntreeFichierComptabilisationRetour;
	}


	public void setRepertoireArchiveEntreeFichierComptabilisationRetour(
			String repertoireArchiveEntreeFichierComptabilisationRetour) {
		this.repertoireArchiveEntreeFichierComptabilisationRetour = repertoireArchiveEntreeFichierComptabilisationRetour;
	}


	public String getRepertoireArchiveEntreeCollecteImageRetour() {
		return repertoireArchiveEntreeCollecteImageRetour;
	}


	public void setRepertoireArchiveEntreeCollecteImageRetour(
			String repertoireArchiveEntreeCollecteImageRetour) {
		this.repertoireArchiveEntreeCollecteImageRetour = repertoireArchiveEntreeCollecteImageRetour;
	}


	public String getRepertoireDestinationCollecteImageAllerKO() {
		return repertoireDestinationCollecteImageAllerKO;
	}


	public void setRepertoireDestinationCollecteImageAllerKO(
			String repertoireDestinationCollecteImageAllerKO) {
		this.repertoireDestinationCollecteImageAllerKO = repertoireDestinationCollecteImageAllerKO;
	}


	

	public String getRepertoireDestinationFichierComptabilisationAllerKO() {
		return repertoireDestinationFichierComptabilisationAllerKO;
	}


	public void setRepertoireDestinationFichierComptabilisationAllerKO(
			String repertoireDestinationFichierComptabilisationAllerKO) {
		this.repertoireDestinationFichierComptabilisationAllerKO = repertoireDestinationFichierComptabilisationAllerKO;
	}


	public String getRepertoireDestinationFichierComptabilisationRetourKO() {
		return repertoireDestinationFichierComptabilisationRetourKO;
	}


	public void setRepertoireDestinationFichierComptabilisationRetourKO(
			String repertoireDestinationFichierComptabilisationRetourKO) {
		this.repertoireDestinationFichierComptabilisationRetourKO = repertoireDestinationFichierComptabilisationRetourKO;
	}


	public String getRepertoireDestinationCollecteImageRetourKO() {
		return repertoireDestinationCollecteImageRetourKO;
	}


	public void setRepertoireDestinationCollecteImageRetourKO(
			String repertoireDestinationCollecteImageRetourKO) {
		this.repertoireDestinationCollecteImageRetourKO = repertoireDestinationCollecteImageRetourKO;
	}
	
	/****
	 * 
	 * 	<!-- <table width="100%" align="center" cellpadding="0" cellspacing="0"
				border="0">
				<tr>
					<td width="100%" align="right"><h:panelGrid columns="5">
							<rich:spacer width="5px" />
							<a4j:commandButton value="#{messages['dialog.btnvalider']}"
								action="#{parameterGenDialog.close}" reRender="#{clientArea.ID}"
								styleClass="ui-button ui-widget ui-state-default ui-corner-all"
								onclick="startWaitInStyle();" oncomplete="stoptWaitInStyle();" />
							<rich:spacer width="5px" />
							<a4j:commandButton value="#{messages['dialog.btnannuler']}"
								action="#{parameterGenDialog.cancel}"
								onclick="Richfaces.hideModalPanel('ParameterGenDialog');"
								styleClass="ui-button ui-widget ui-state-default ui-corner-all"
								immediate="true" />
							<rich:spacer width="5px" />
						</h:panelGrid></td>
				</tr>
			</table>-->
			
			****/


   
}
