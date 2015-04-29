package com.foya.noms.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class PagerContextFilter implements Filter{

	public static Integer size;

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		Integer page = 0;
		try {
			//jqgrid
			//size=10&page=1&sort=&order=asc
			
			page = Integer.parseInt(req.getParameter("page"));
			
			if(req.getParameter("size")!=null && !req.getParameter("size").isEmpty()){
				size = Integer.parseInt(req.getParameter("size"));
			}
		} catch (NumberFormatException e) {
			
		}
		try {
			PagerContext.setOrder(req.getParameter("order"));
			PagerContext.setSort(req.getParameter("sort"));
			PagerContext.setPage(page);
			PagerContext.setSize(size);
			chain.doFilter(req,resp);
		} finally {
			PagerContext.removeOrder();
			PagerContext.removeSort();
			PagerContext.removePage();
			PagerContext.removeSize();
		}
	}

	@Override
	public void init(FilterConfig cfg) throws ServletException {
		try {
			size = Integer.parseInt(cfg.getInitParameter("size"));
		} catch (NumberFormatException e) {
			size = 10;
		}
	}

}
