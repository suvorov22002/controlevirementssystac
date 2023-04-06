/**
 * 
 */
package com.afb.portal.presentation.models.reportViewer;

import java.awt.Image;
import java.util.Collection;

import org.jfree.data.general.AbstractDataset;

/**
 * Classe de base permettant de creer un Graphique pour l'Etat
 * @author Francis K
 * @version 1.0
 */
public abstract class AbstractChartBuilder {
	
	/**
	 * DataSet de G�n�ration de l'Image du Chart
	 */
	protected AbstractDataset chartDataSet = null;
	
	/**
	 * Source de donn�e Principale de l'Etat
	 */
	protected Collection<?> principalDataSource;
		
	/**
	 * Methode permettant d'obtenir la Source de donn�e Principale de l'Etat
	 * @return Source de donn�e Principale de l'Etat
	 */
	public Collection<?> getPrincipalDataSource() {
		return principalDataSource;
	}

	/**
	 * Methode permettant de mettre a jour la Source de donn�e Principale de l'Etat
	 * @param principalDataSource Source de donn�e Principale de l'Etat
	 */
	public void setPrincipalDataSource(Collection<?> principalDataSource) {
		this.principalDataSource = principalDataSource;
	}
	
	/**
	 * Methode de creation de l'Image du Chart
	 * @return	Image du Chart
	 */
	public Image buidChart() {

		// Si la source de donn�e principale est vide
		if(principalDataSource == null || principalDataSource.size() == 0) return null;
		
		// On Construit la source de donn�es du Chart
		this.chartDataSet = buildChartDataSet();
		
		// Si le DataSet est null
		if(chartDataSet == null) return null;
		
		// On retourne l'Image
		return generateImage(this.chartDataSet);
	}
	
	/**
	 * Methode permettant de construire la source de donn�es du graphique
	 * @return	DataSet du Chart
	 */
	protected abstract AbstractDataset buildChartDataSet();
	
	/**
	 * Methode de generation de l'Image du Chart
	 * @param	chartDataSet	Dataset du Chart
	 * @return	Image du Chart
	 */
	protected abstract Image generateImage(AbstractDataset chartDataSet);
}
