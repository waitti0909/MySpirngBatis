package com.foya.noms.dao;

import javax.inject.Named;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import com.foya.noms.resources.AppConstants;
import com.foya.noms.util.PagerContext;
import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

public abstract class BaseDao {

	protected Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	@Named("sqlSession") 
	protected SqlSession sqlSession;

	
	protected PageBounds getPageBounds(){
		PageBounds pageBounds = new PageBounds(PagerContext.getPage()==null?AppConstants.DEFAULTPAGENUMER:PagerContext.getPage(),
				PagerContext.getSize()==null?AppConstants.DEFAULTDATASIZEPERPAGE:PagerContext.getSize());
		
		return pageBounds;
	}
	protected PageBounds getPageBounds(String orderbyString){
		PageBounds pageBounds = new PageBounds(PagerContext.getPage()==null?AppConstants.DEFAULTPAGENUMER:PagerContext.getPage(),PagerContext.getSize()==null?AppConstants.DEFAULTDATASIZEPERPAGE:PagerContext.getSize(),Order.formString(orderbyString));
		
		return pageBounds;
	}
	
	
}
