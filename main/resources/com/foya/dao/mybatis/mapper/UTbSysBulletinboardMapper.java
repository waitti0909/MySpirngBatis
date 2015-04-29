package com.foya.dao.mybatis.mapper;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.foya.noms.dto.system.BulletinboardDTO;

public interface UTbSysBulletinboardMapper {
	
	/**
	 * By登入日期查詢公佈欄
	 * @return
	 */
	List<BulletinboardDTO> searchBulletinboardByDate(@Param("loginDate") Timestamp loginDate);
	
	/**
	 * By公佈欄ID查詢詳細資訊
	 * @return
	 */
	BulletinboardDTO searchBulletinboardById(@Param("bulletinId") BigDecimal bulletinId);
	
	/**
	 * 根據查詢條件查詢公佈欄
	 * @return
	 */
	List<BulletinboardDTO> searchBulletinboardByCond(Map<String, String> record);

}
