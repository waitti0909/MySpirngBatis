package com.foya.noms.service.org;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.dao.mybatis.model.TbOrgDomain;
import com.foya.noms.dao.org.DomainDao;
import com.foya.noms.service.BaseService;

@Service
public class DomainService extends BaseService {
	
	
	public static final String ROOTDOMAIN="HQ"; 
	public static final String ROOTDOMAIN_PARENT="A"; 
	
	@Autowired
	private DomainDao domainDao;
	
	/**
	 *取得標準第二層(全區的下一層) Domain清單  (北一區,北二區,中區,南區)
	 * 
	 * @return
	 */
	public List<TbOrgDomain> getStandardDomainList() {
		
		return domainDao.getStandardDomain();
	}
	
	
	/**
	 *取得標準第二層(全區的下一層)跟全區 Domain清單  (全區,北一區,北二區,中區,南區)
	 * 
	 * @return List<TbOrgDomain>
	 */
	public List<TbOrgDomain> getStandardAndTopDomain() {
		
		return domainDao.getStandardAndTopDomain();
	}
	
	
	/**
	 *取得可使用的區域清單
	 *@input 使用者部門所屬區域
	 * 
	 * @return List<TbOrgDomain>
	 */
	public List<TbOrgDomain> getAccessDomainByUser(TbOrgDomain ownedDomain){
		
		//如果要全區請 getAccessDomainByUser(ownedDomain, true);
		return getAccessDomainByUser(ownedDomain, false);
		
	}
	/**
	 *取得可使用的區域清單
	 *@input 使用者部門所屬區域
	 * 
	 * @return List<TbOrgDomain>
	 */
	public List<TbOrgDomain> getAccessDomainByUser(TbOrgDomain ownedDomain,boolean includeHQDomain){
		
		List<TbOrgDomain> standardList =includeHQDomain?getStandardAndTopDomain(): getStandardDomainList();
		if(ownedDomain==null){
			return null;
		}else if(ownedDomain.getID().equalsIgnoreCase(ROOTDOMAIN) || ownedDomain.getPARENT().equalsIgnoreCase(ROOTDOMAIN_PARENT)){
			//代表是全區的人access
			return standardList;
		}
		List<TbOrgDomain> returnList = new ArrayList<>();
		
		//目前先支援 上一層是北一區 或是 本身是北一區
		for (TbOrgDomain tbOrgDomain : standardList) {
			if(tbOrgDomain.getID().equalsIgnoreCase(ROOTDOMAIN)){
				continue;
			}else if(tbOrgDomain.getID().equalsIgnoreCase(ownedDomain.getID()) || (tbOrgDomain.getID().equalsIgnoreCase(ownedDomain.getPARENT()))){
					returnList.add(tbOrgDomain);
			}
		}
		return returnList;
		
	}
	
	
	/**
	 *根據所選擇的標準四區找出底下的子區域
	 *@input 標準四區區域ID
	 * 
	 * @return List<TbOrgDomain>
	 */
	public List<TbOrgDomain> getDomainTreeByStandardDomain(String standardDomainId){
		return domainDao.getDomainIdTreeByStandard(standardDomainId);
	}
	
	/**
	 *根據所屬的自區域找出所屬標準區域
	 *@input 區域ID
	 * 
	 * 
	 */
	public String getTopDomainId(String domainId) {
		if (StringUtils.equals(domainId, "HQ")) {
			return domainId;
		} else {
			return StringUtils.substring(domainId, 0, 1);
		}
	}
	
	
}
