package com.foya.noms.dto.ls;

import java.util.List;

import com.foya.dao.mybatis.model.TbLsLocationAdded;
import com.foya.dao.mybatis.model.TbLsMainAdded;
import com.foya.noms.dto.pay.TbLsLocElecDTO;
import com.foya.noms.dto.pay.TbLsSiteDTO;

public class TbLsLocElecLocPaymentDTO {

	private List<TbLsLocPaymentDTO> tbLsLocPaymentDTOList;

	private List<TbLsLocElecDTO> tbLsLocElecDTOList;
	
	private List<TbLsLocElecDTO> oldTbLsLocElecAddedList;
	
	private List<TbLsLocElecDTO> newTbLsLocElecAddedList;
	
	private List<TbLsLocPaymentDTO> oldTbLsLocPaymentAddedList;
	
	private List<TbLsLocPaymentDTO> newTbLsLocPaymentAddedList;
	
	private  List<TbLsSiteDTO> tbLsSiteDTOList; 
	
	private TbLsMainAdded tbLsMainAdded;
	
	private TbLsLocationAdded tbLsLocationAdded;

	public List<TbLsLocPaymentDTO> getTbLsLocPaymentDTOList() {
		return tbLsLocPaymentDTOList;
	}

	public void setTbLsLocPaymentDTOList(List<TbLsLocPaymentDTO> tbLsLocPaymentDTOList) {
		this.tbLsLocPaymentDTOList = tbLsLocPaymentDTOList;
	}

	public List<TbLsLocElecDTO> getTbLsLocElecDTOList() {
		return tbLsLocElecDTOList;
	}

	public void setTbLsLocElecDTOList(List<TbLsLocElecDTO> tbLsLocElecDTOList) {
		this.tbLsLocElecDTOList = tbLsLocElecDTOList;
	}

	public List<TbLsLocElecDTO> getOldTbLsLocElecAddedList() {
		return oldTbLsLocElecAddedList;
	}

	public void setOldTbLsLocElecAddedList(List<TbLsLocElecDTO> oldTbLsLocElecAddedList) {
		this.oldTbLsLocElecAddedList = oldTbLsLocElecAddedList;
	}

	public List<TbLsLocElecDTO> getNewTbLsLocElecAddedList() {
		return newTbLsLocElecAddedList;
	}

	public void setNewTbLsLocElecAddedList(List<TbLsLocElecDTO> newTbLsLocElecAddedList) {
		this.newTbLsLocElecAddedList = newTbLsLocElecAddedList;
	}

	public List<TbLsLocPaymentDTO> getOldTbLsLocPaymentAddedList() {
		return oldTbLsLocPaymentAddedList;
	}

	public void setOldTbLsLocPaymentAddedList(List<TbLsLocPaymentDTO> oldTbLsLocPaymentAddedList) {
		this.oldTbLsLocPaymentAddedList = oldTbLsLocPaymentAddedList;
	}

	public List<TbLsLocPaymentDTO> getNewTbLsLocPaymentAddedList() {
		return newTbLsLocPaymentAddedList;
	}

	public void setNewTbLsLocPaymentAddedList(List<TbLsLocPaymentDTO> newTbLsLocPaymentAddedList) {
		this.newTbLsLocPaymentAddedList = newTbLsLocPaymentAddedList;
	}

	public TbLsMainAdded getTbLsMainAdded() {
		return tbLsMainAdded;
	}

	public void setTbLsMainAdded(TbLsMainAdded tbLsMainAdded) {
		this.tbLsMainAdded = tbLsMainAdded;
	}

	public TbLsLocationAdded getTbLsLocationAdded() {
		return tbLsLocationAdded;
	}

	public void setTbLsLocationAdded(TbLsLocationAdded tbLsLocationAdded) {
		this.tbLsLocationAdded = tbLsLocationAdded;
	}

	public List<TbLsSiteDTO> getTbLsSiteDTOList() {
		return tbLsSiteDTOList;
	}

	public void setTbLsSiteDTOList(List<TbLsSiteDTO> tbLsSiteDTOList) {
		this.tbLsSiteDTOList = tbLsSiteDTOList;
	}

}
