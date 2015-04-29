package com.foya.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

import com.foya.dao.mybatis.model.TbComPoItem;
import com.foya.noms.dto.basic.PoItemDTO;

public interface UTbComPoItemMapper {
    
    
    List<PoItemDTO> selectTotailAccount(Map<String, String> map);
    
    List<PoItemDTO> selectItemIdByOsId(Map<String, String> map);
    
    List<TbComPoItem> selectItemForDelete(Map<String, String> map);
    
    List<PoItemDTO> getPoitemByPoIdAndUsedBySite(String poId);
    
    List<String> selectFaCategoryByCat(Map<String, String> map);
    
    
}