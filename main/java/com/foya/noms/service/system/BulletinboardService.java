package com.foya.noms.service.system;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbSysBulletinboard;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.noms.dao.system.BulletinboardDao;
import com.foya.noms.dao.system.LookupDao;
import com.foya.noms.dto.system.BulletinboardDTO;

@Service
public class BulletinboardService {
	@Autowired
	private BulletinboardDao sysBulletinboardDao;
	
	@Autowired
	private LookupDao sysLookupDao;
	
	//根據登入Date查詢公佈欄
	public List<BulletinboardDTO> getBulletinboardByLoginDate(Timestamp loginDate) {
		return sysBulletinboardDao.selectBulletinboardByDate(loginDate);
	}	
	
	//根據公佈欄ID查詢詳細資料
	public BulletinboardDTO getBulletinboardById(BigDecimal bulletinId) {
		return sysBulletinboardDao.selectBulletinboardById(bulletinId);
	}
		
	//根據查詢條件查詢公佈欄
	public List<BulletinboardDTO> getBulletinboardByCond(Map<String, String> record) {
		return sysBulletinboardDao.selectBulletinboardByCond(record);
	}
	
	//公佈欄新增
	public int addBulletinboard(TbSysBulletinboard bulletinRecod) {
		return sysBulletinboardDao.addBulletinboard(bulletinRecod);
	}
	
	//公佈欄修改
	public void editBulletinboard(TbSysBulletinboard bulletinRecod) {
		sysBulletinboardDao.editBulletinboard(bulletinRecod);
	}
	
	//公佈欄刪除
	@Transactional
	public void deleteBulletinboard(String[] bulletinIdArray) {
		for (String bId : bulletinIdArray) {
			BigDecimal bulletinId = BigDecimal.valueOf(Long.parseLong(bId));
			sysBulletinboardDao.deleteBulletinboard(bulletinId);
		}		
	}
	
	//取得公佈類型
	public List<TbSysLookup> getBulletinType() {
		return sysLookupDao.selectBulletinType();
	}
	

}
