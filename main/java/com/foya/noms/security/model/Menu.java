package com.foya.noms.security.model;

import org.apache.commons.lang.builder.CompareToBuilder;

public class Menu implements Comparable<Menu>{

	private String id;
	private String url;
	private String name;
	private String parentId;
	private String isFolder;

	// **************************
		public Menu() {
			
		}
		public Menu(String url, String name, String parentId, String isFolder,
				String desc, String sort) {
			this.url = url;
			this.name = name;
			this.parentId = parentId;
			this.isFolder = isFolder;
			this.desc = desc;
			this.sort = sort;
		}
		private String desc;
		private String sort;
		private String crID;
		private String crTM;
		private String mdyTSTMP;
		private String mdyPSNID;
		private Integer level;
		private String used;
		
		
		public String getMdyPSNID() {
			return mdyPSNID;
		}
		public void setMdyPSNID(String mdyPSNID) {
			this.mdyPSNID = mdyPSNID;
		}
		
		public String getMdyTSTMP() {
			return mdyTSTMP;
		}
		public void setMdyTSTMP(String mdyTSTMP) {
			this.mdyTSTMP = mdyTSTMP;
		}
		public String getCrID() {
			return crID;
		}

		public void setCrID(String crID) {
			this.crID = crID;
		}

		public String getCrTM() {
			return crTM;
		}

		public void setCrTM(String crTM) {
			this.crTM = crTM;
		}

		public String getSort() {
			return sort;
		}

		public void setSort(String sort) {
			this.sort = sort;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		// **********************************

	public String getIsFolder() {
		return isFolder;
	}

	public void setIsFolder(String isFolder) {
		this.isFolder = isFolder;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((parentId == null) ? 0 : parentId.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Menu other = (Menu) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parentId == null) {
			if (other.parentId != null)
				return false;
		} else if (!parentId.equals(other.parentId))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	@Override
	public int compareTo(Menu o) {
		return CompareToBuilder.reflectionCompare(this, o);
	}

	
	public String getParent(){
		return (parentId==null||parentId.isEmpty())?"#":parentId;
	}
	
	public String getText(){
		return name;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getUsed() {
		return used;
	}
	public void setUsed(String used) {
		this.used = used;
	}
	
	
}
