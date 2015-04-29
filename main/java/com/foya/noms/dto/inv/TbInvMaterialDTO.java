package com.foya.noms.dto.inv;

import com.foya.dao.mybatis.model.TbInvMaterial;

public class TbInvMaterialDTO extends TbInvMaterial {

	private String ctrl_type_dscr;
	private String is_asset_dscr;
	//private String asset_type_dscr;
    private String catg1_name;
    private String catg2_name;
    private String catg3_name;

	public String getCtrl_type_dscr() {
		return ctrl_type_dscr;
	}

	public void setCtrl_type_dscr(String ctrl_type_dscr) {
		this.ctrl_type_dscr = ctrl_type_dscr;
	}

	public String getIs_asset_dscr() {
		return is_asset_dscr;
	}

	public void setIs_asset_dscr(String is_asset_dscr) {
		this.is_asset_dscr = is_asset_dscr;
	}
	/*
	public String getAsset_type_dscr() {
		return asset_type_dscr;
	}

	public void setAsset_type_dscr(String asset_type_dscr) {
		this.asset_type_dscr = asset_type_dscr;
	}*/

    public String getCatg1_name() {
		return catg1_name;
	}

	public void setCatg1_name(String catg1_name) {
		this.catg1_name = catg1_name == null ? null : catg1_name.trim();
	}

	public String getCatg2_name() {
		return catg2_name;
	}

	public void setCatg2_name(String catg2_name) {
		this.catg2_name = catg2_name == null ? null : catg2_name.trim();
	}

	public String getCatg3_name() {
		return catg3_name;
	}

	public void setCatg3_name(String catg3_name) {
		this.catg3_name = catg3_name == null ? null : catg3_name.trim();
	}
}
