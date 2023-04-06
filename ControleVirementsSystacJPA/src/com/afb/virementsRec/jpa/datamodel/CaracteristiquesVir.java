package com.afb.virementsRec.jpa.datamodel;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: CaracteristiquesVir
 * Classe pour l'entité CaracteristiquesVir
 * @author Stéphane Mouafo
 *
 */
@Entity
@Table(name = "VIR_SYSTAC_CARACT_VIR")
public class CaracteristiquesVir implements Serializable, Comparable<CaracteristiquesVir>{

	
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Identifiant
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PARAM_CARAC_ID")
	private Long id;
	
	
	@Column(name = "RANG")
	private Integer rang;    //used for the purpose of ordering the message parameters //e.g. name before surname, surname before date of birth etc.
	
	
	@Column(name = "CARACTERISTIQUES_VIR")
	@Enumerated(EnumType.STRING)
	private CaracteristiquesVirItem caracteristiquesItems;
	
	
	@ManyToOne
	@JoinColumn(name = "PARAM_ID")
	private Parametrages param;
	
	@Column(name = "VALIDE")
	private Boolean valide = Boolean.TRUE;

	
	
	public CaracteristiquesVir() {
		super();
	}
	
	


    /**
     *
     * @param rang
     * @param messageItems
     * @param message
     */
	public CaracteristiquesVir(Integer rang,
			CaracteristiquesVirItem caracteristiquesItems) {
		super();
		this.rang = rang;
		this.caracteristiquesItems = caracteristiquesItems;
	}




    /**
     * 
     * @return the id_param
     */
	public Long getId() {
		return id;
	}

    /**
     * 
     * @param id_param the id_param to set
     */
	public void setId(Long id) {
		this.id = id;
	}


    /**
     * 
     * @return the messageItems
     */
	public CaracteristiquesVirItem getCaracteristiquesItems() {
		return caracteristiquesItems;
	}


    /**
     * 
     * @param messageItems the messageItems
     */
	public void setCaracteristiquesItems(CaracteristiquesVirItem caracteristiquesItems) {
		this.caracteristiquesItems = caracteristiquesItems;
	}



   


    public Parametrages getParam() {
		return param;
	}




	public void setParam(Parametrages param) {
		this.param = param;
	}




	/**
     * 
     * @return the rang
     */
	public Integer getRang() {
		return rang;
	}


    /**
     * 
     * @param rang the rang to set
     */
	public void setRang(Integer rang) {
		this.rang = rang;
	}

	
	    /**
	     * 
	     * @return the valide
	     */
		public Boolean getValide() {
		return valide;
	}



    /**
     * 
     * @param valide the valide to set
     */
	public void setValide(Boolean valide) {
		this.valide = valide;
	}





		@Override
		public int compareTo(CaracteristiquesVir o) {
			// TODO Auto-generated method stub
			return this.getRang().compareTo(o.getRang());
		}




		@Override
		public String toString() {
			return "CaracteristiquesVir [rang=" + rang + ", caracteristiquesItems="
					+ caracteristiquesItems + ", param=" + param + ", valide="
					+ valide + "]";
		}




		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime
					* result
					+ ((caracteristiquesItems == null) ? 0
							: caracteristiquesItems.hashCode());
			return result;
		}




		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CaracteristiquesVir other = (CaracteristiquesVir) obj;
			if (caracteristiquesItems.getValue()  == null) {
				if (other.caracteristiquesItems.getValue()  != null)
					return false;
			} else if (!caracteristiquesItems.getValue().equals(other.caracteristiquesItems.getValue() ))
				return false;
			return true;
		}


		
		
	}

	