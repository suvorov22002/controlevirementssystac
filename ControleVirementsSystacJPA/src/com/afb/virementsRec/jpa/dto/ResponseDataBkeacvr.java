package com.afb.virementsRec.jpa.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ResponseDataBkeacvr extends ResponseBase{
	
	@JsonIgnore
	private static final long serialVersionUID = 1L;
	
	private List<Bkbeacrv> datas = new ArrayList<Bkbeacrv>();
	
	public ResponseDataBkeacvr() {
		super();
	}

	/**
	 * @return the datas
	 */
	public List<Bkbeacrv> getDatas() {
		return datas;
	}

	/**
	 * @param datas the datas to set
	 */
	public void setDatas(List<Bkbeacrv> datas) {
		this.datas = datas;
	}
}
