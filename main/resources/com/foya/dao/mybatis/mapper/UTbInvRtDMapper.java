package com.foya.dao.mybatis.mapper;

import java.util.HashMap;
import java.util.List;

import com.foya.noms.dto.inv.TbInvRtDDTO;
import com.foya.noms.dto.st.MeterialRtnDTO;

public interface UTbInvRtDMapper {
    //15L.jsp 主檔資料
    List<TbInvRtDDTO> searchDtl(HashMap<String,Object> dataObj);
    //收料功能
    List<TbInvRtDDTO> searchFs(HashMap<String,Object> dataObj);
    
    List<TbInvRtDDTO> searchRtDOptId(HashMap<String,Object>  dataObj);
    
    List<MeterialRtnDTO> selectRtntrItemByOptId(HashMap<String,Object>  dataObj);
    
    TbInvRtDDTO searchRtDSumQty(HashMap<String,Object> dataObj);
}