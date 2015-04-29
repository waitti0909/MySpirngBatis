package com.foya.dao.mybatis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.foya.noms.dto.inv.TbInvMrDDTO;
import com.foya.noms.dto.st.SiteMtDetailImportDTO;

public interface UTbInvMrDMapper {
	List<TbInvMrDDTO> searchMrDWithSumQty(HashMap<String,Object>  dataObj);
	
	List<TbInvMrDDTO> searchMrD(HashMap<String,Object>  dataObj);
	
	List<TbInvMrDDTO> searchMrDOptId(HashMap<String,Object>  dataObj);
	
	List<TbInvMrDDTO> selectItemMainQuery(Map<String, String>  dataObj);
	
	
	List<SiteMtDetailImportDTO> searchMeterialApplyExcel(Map<String,String> dataObj);
	
	List<SiteMtDetailImportDTO> selectItemQuery(Map<String, String>  dataObj);
	
	TbInvMrDDTO searchMrDSumQty(Map<String, Object>  dataObj);
}