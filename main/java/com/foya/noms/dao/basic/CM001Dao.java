package com.foya.noms.dao.basic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbAuthPersonnelMapper;
import com.foya.dao.mybatis.mapper.TbComCompanyMapper;
import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.dao.mybatis.model.TbComCompany;
import com.foya.dao.mybatis.model.TbComCompanyExample;
import com.foya.noms.dao.BaseDao;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Repository
public class CM001Dao extends BaseDao {
	
	@Autowired
	private TbComCompanyMapper tbComCompanyMapper;
	
	@Autowired
	private TbAuthPersonnelMapper tbAuthPersonnelMapper;

	/**
	 * 根據條件查詢廠商(分頁)
	 * 
	 * @param
	 * @return
	 */
	public List<TbComCompany> searchCompanyByVatName(String coUbnNo, String name,String[] type) {
		PageBounds pageBounds = getPageBounds();		
		TbComCompanyExample example = new TbComCompanyExample();
		TbComCompanyExample.Criteria criteria = example.createCriteria();
		
		if(coUbnNo!=null && !(coUbnNo.equals(""))){
			criteria.andCO_UBN_NOEqualTo(coUbnNo);
		}		
		if(name!=null && !(name.equals(""))){
			criteria.andCO_NAMELike("%"+name+"%");
		}		
		
		//達到 vatNo and name and (eng or equ) 查詢條件
		  List<String> typeYN = new ArrayList<String>();
		  typeYN.add("Y");
		  typeYN.add("N");
		
		if(type.length!=0){
			if(type[0].equals("Y") && type[1].equals("Y") && type[2].equals("Y")){
				criteria.andTYPE_ENGIn(typeYN);
				criteria.andTYPE_EQUIn(typeYN);
				criteria.andTYPE_WHIn(typeYN);
			}else if(type[0].equals("Y") && type[1].equals("N") && type[2].equals("N")){
				criteria.andTYPE_ENGEqualTo(type[0]);
			}else if(type[0].equals("N") && type[1].equals("Y") && type[2].equals("N")){
				criteria.andTYPE_EQUEqualTo(type[1]);
			}else if(type[0].equals("N") && type[1].equals("N") && type[2].equals("Y")){
				criteria.andTYPE_WHEqualTo(type[2]);
			}else if(type[0].equals("Y") && type[1].equals("Y") && type[2].equals("N")){
				criteria.andTYPE_ENGEqualTo(type[0]);
				criteria.andTYPE_EQUEqualTo(type[1]);
			}else if(type[0].equals("Y") && type[1].equals("N") && type[2].equals("Y")){
				criteria.andTYPE_ENGEqualTo(type[0]);
				criteria.andTYPE_WHEqualTo(type[2]);
			}else if(type[0].equals("N") && type[1].equals("Y") && type[2].equals("Y")){
				criteria.andTYPE_EQUEqualTo(type[1]);
				criteria.andTYPE_WHEqualTo(type[2]);
			}		
		}

		
		List<TbComCompany> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.TbComCompanyMapper.selectByExample",example, pageBounds);

		PageList<TbComCompany> pageList = (PageList<TbComCompany>) list;
		log.debug("totaql=" + pageList.getPaginator().getTotalCount());
		return pageList;
	}
	
	/**
	 * 查詢預新增之資料是否已存於DB中
	 * @param vatNo
	 * @return
	 */
	public TbComCompany selectByPrimaryKey(String vatNo){
		return tbComCompanyMapper.selectByPrimaryKey(vatNo);
	}
	
	/**
	 * 查詢廠商
	 * @param example
	 * @return
	 */
	public List<TbComCompany> selectByExample(TbComCompanyExample example){
		return tbComCompanyMapper.selectByExample(example);
	}
	/**
	 * 新增
	 * @param record
	 */
	public void saveNewCompany(TbComCompany company){
		tbComCompanyMapper.insertSelective(company);
	}
	/**
	 * 根據廠商編號取CompanyDetail
	 * @param vatNo
	 * @return
	 */
	public TbComCompany getCompanyDetailByID(String vatNo){
		return tbComCompanyMapper.selectByPrimaryKey(vatNo);
	}
	
	/**
	 * 修改
	 * @param company
	 */
	public void saveUpdateCompany(TbComCompany company){
		tbComCompanyMapper.updateByPrimaryKey(company);
	}
	
	/**
	 * 廠商帳號新增
	 * @param record
	 */
	public void saveNewCompanyAccount(TbAuthPersonnel record){
		tbAuthPersonnelMapper.insertSelective(record);
	}
	
	/**
	 * 廠商帳號編輯
	 * @param record
	 */
	public void saveUpdateCompanyAccount(TbAuthPersonnel record){
		tbAuthPersonnelMapper.updateByPrimaryKeySelective(record);
	}
	
	
}
