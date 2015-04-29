package com.foya.dao.mybatis.mapper;

import java.util.HashMap;
import java.util.List;

import com.foya.noms.dto.inv.TbInvMaterialOptDTO;

public interface UTbInvMaterialOptMapper {
    
    List<TbInvMaterialOptDTO> search(HashMap<String,Object> dataObj);
    
    List<TbInvMaterialOptDTO> searchJoinMrD(HashMap<String,Object> dataObj);
    
    List<TbInvMaterialOptDTO> searchRTN(HashMap<String,Object> dataObj);
    
    List<TbInvMaterialOptDTO> searchTodo(HashMap<String,Object> dataObj);
    
    List<TbInvMaterialOptDTO>  selectMaterialOptByCondistions(TbInvMaterialOptDTO tbInvMaterialOptDTO);
    
    List<TbInvMaterialOptDTO>  selectForExportMaterialExeclTitle(TbInvMaterialOptDTO tbInvMaterialOptDTO);
    
    List<TbInvMaterialOptDTO>  selectTroDetail(String optId);
    
    TbInvMaterialOptDTO  selectTroHistory(String optId);
    
    Integer selectInsItemOnSiteForOrder(String orderId);
}