package com.foya.noms.security.model;

import java.util.ArrayList;
import java.util.List;

public class Tree {
	private Menu master;
	private List<Menu> detail = new ArrayList<Menu>();

	public Tree(Menu menu) {
		this.master = menu;
	}

	public Menu getMaster() {
		return master;
	}

	public void setMaster(Menu master) {
		this.master = master;
	}

	public List<Menu> getDetail() {
		return detail;
	}

	public void setDetail(List<Menu> detail) {
		this.detail = detail;
	}
	
	public List<String> getAllurl(){
		List<String> re = new ArrayList<String>();
		re.add(master.getUrl());
		for(Menu str : detail){
			re.add(str.getUrl());
		}
		return re;
	}

}
