package com.afb.virementsRec.jpa.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ResponseDataBkeacvr extends ResponseBase{
	
	@JsonIgnore
	private static final long serialVersionUID = 1L;
	
	private List<Bkbeacrv> data = new ArrayList<Bkbeacrv>();
	
	public ResponseDataBkeacvr() {
		super();
	}

	/**
	 * @return the datas
	 */
	public List<Bkbeacrv> getData() {
		return data;
	}

	/**
	 * @param datas the datas to set
	 */
	public void setData(List<Bkbeacrv> datas) {
		this.data = datas;
	}
}
