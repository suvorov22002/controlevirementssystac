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
@Table(name = "VIR_SYSTAC_PARAM_CARAC_SPEC")
public class ParametragesCaracteresSpeciaux implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public ParametragesCaracteresSpeciaux() {
		super();
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PARAM_ID")
	private Long id;
	
	private String caractereSpecial;
	
	@ManyToOne
	@JoinColumn(name = "PARAM_TELECOMPENSE")
	private ParametragesGenTeleCompense param;
	
	@Column(name = "VALIDE")
	private Boolean valide = Boolean.TRUE;

	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getCaractereSpecial() {
		return caractereSpecial;
	}


	public void setCaractereSpecial(String caractereSpecial) {
		this.caractereSpecial = caractereSpecial;
	}


	public ParametragesGenTeleCompense getParam() {
		return param;
	}


	public void setParam(ParametragesGenTeleCompense param) {
		this.param = param;
	}


	public Boolean getValide() {
		return valide;
	}


	public void setValide(Boolean valide) {
		this.valide = valide;
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
