package com.foya.dao.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.noms.dto.org.DeptDTO;

public interface UTbOrgDeptMapper {
	
    /**
     * 取得部門Tree
     * @return
     */
    List<DeptDTO> getDeptTree();
    
    /**
     * 查詢所有dept
     * @param example
     * @return
     */
    List<DeptDTO> searchDeptByIdName(String deptId, String deptName);
    
    /**
	 * 用部門ID查詢相對應的職務
	 * @param deptId
	 * @return
	 */
	List<DeptDTO> searchPositionByDeptId(String deptId);
	
	/**
	 * 更新部門主管
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(@Param("deptId")String deptId,@Param("dpetPosId")String dpetPosId);
	
	/**
	 * 根據某點尋找所有父節點
	 * @param deptId
	 * @return
	 */
	List<DeptDTO> searchParentByChild(String deptId);
	
	/**
	 * @固定網路服務事業部組織清單
	 * @return
	 */
	List<DeptDTO> searchDeptByIdLike();
	/**
	 * @根據ID搜尋資料
	 * @return
	 */
	DeptDTO searchDeptById(@Param("deptId")String deptId);
	
	/**
	 * 查詢 接工單位
	 * @param deptList
	 * @return
	 */
	List<DeptDTO> selectDeptAllList(List<String> deptIdList);
	
	/**
	 * 查詢Domain下的部分
	 * @param deptList
	 * @return
	 */
	List<TbOrgDept> selectDeptByDomainList(List<String> domainList);
	
	
	/**
	 * 根據DEPTID查詢POS_TYPE='N'的職稱
	 * @param deptId
	 * @return
	 */
	List<DeptDTO> selecPosByDept(String deptId);
	
}