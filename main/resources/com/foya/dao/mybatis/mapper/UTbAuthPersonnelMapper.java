package com.foya.dao.mybatis.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.noms.dto.auth.PersonnelDTO;
import com.foya.noms.dto.auth.UserDTO;

public interface UTbAuthPersonnelMapper {

	/**
	 * 用psn_ID找到部門名稱
	 * 
	 * @param psnId
	 * @return
	 */
	PersonnelDTO getPsnDeptById(Integer psnId);

	/**
	 * 用psn no, chnName, email 查詢
	 * 
	 * @param psnNo
	 * @param chnName
	 * @param email
	 * @return
	 */
	List<TbAuthPersonnel> searchPsnByNoChnNameEmail(@Param(value = "psnNo") String psnNo,
			@Param(value = "chnName") String chnName, @Param(value = "email") String email);

	/**
	 * 用psn no查詢ChnNM
	 * 
	 * @param psnNo
	 * @return list of TbAuthPersonnel
	 */
	List<TbAuthPersonnel> searchPsnsByPsnNo(String psnNo);

	/**
	 * 根據 psn no 查詢用戶
	 * 
	 * @param psnNo
	 * @return
	 */
	UserDTO getUserDtoByPsnNo(@Param(value = "psnNo") String psnNo);

	/**
	 * 根據 list of psn no 查詢用戶
	 * 
	 * @param psnNo
	 * @return list of UserDTO
	 */
	List<UserDTO> getUserDtoByPsnNos(@Param("psnNos") List<String> psnNos);

	/**
	 * 根據 dept pos id 查詢用戶
	 * 
	 * @param deptPosId
	 * @return list of UserDTO
	 */
	List<UserDTO> getUserDtoByDeptPosId(@Param("deptPosId") String deptPosId);

	/**
	 * 根據登入且到職生效帳號查詢用戶
	 * 
	 * @param psnNo 帳號
	 * @return UserDTO
	 */
	UserDTO selectLoginUserByPsnNo(@Param(value = "psnNo") String psnNo,
			@Param(value = "sysdate") Date sysdate);

	/**
	 * 根據DeptPosId更新PsnPrimaryJob
	 * 
	 * @param oldDeptPosId
	 * @param newDeptPosId
	 */
	int updatePsnPrimaryJobByDeptPosId(@Param(value = "oldDeptPosId") String oldDeptPosId,
			@Param(value = "newDeptPosId") String newDeptPosId);
	
	/**
	 * 根據DeptPosId,posNo更新PsnPrimaryJob
	 * 
	 * @param deptPosId
	 * @param posNo
	 * @return
	 */
	int updatePrimaryJobByDeptPosIdPSNNo(@Param(value = "deptId") String deptId,@Param(value = "dpetPosId") String deptPosId,
			@Param(value = "psnNo") String psnNo,@Param(value = "jobTitle") String jobTitle,
			@Param(value = "primaryDeptPosId") String primaryDeptPosId);

	/**
	 * 根據dept更新Psn的部門
	 * 
	 * @param mdUser
	 * @param mdTime
	 * @param deptId
	 * @param primaryDeptPosId
	 * @param editdeptID
	 * @return
	 */
	int updatePsnDeptByDeptId(@Param(value = "mdUser") String mdUser,
			@Param(value = "mdTime") Date mdTime, @Param(value = "deptId") String deptId,
			@Param(value = "primaryDeptPosId") String primaryDeptPosId,
			@Param(value = "editdeptID") String editdeptID);

	/**
	 * 根據帳號查詢部門帳號
	 * 
	 * @param account 帳號
	 * @return
	 */
	UserDTO selectByPsn(@Param(value = "username") String username);

	/**
	 * 根據部門帳號查帳號
	 * 
	 * @return
	 */
	List<TbAuthPersonnel> selectByDeptIdLike(@Param(value = "deptId") String deptId);

	/**
	 * 根據部門帳號查帳號姓名
	 * 
	 * @return
	 */
	List<TbAuthPersonnel> selectByDeptId(@Param(value = "dept") String dept);

	/**
	 * 根據維運區查帳號姓名
	 * 
	 * @return
	 */
	List<TbAuthPersonnel> selectAppUserByDomain(@Param(value = "domain") String domain);
	
	
	
	/**
	 * 根據維運區查帳號姓名
	 * 
	 * @return
	 */
	List<TbAuthPersonnel> selectOnJobUserByDomain(@Param("list") List<String> domainList);
	
	
	
	
	/**
	 * 根據廠商編號查詢personnel
	 * @param vatNo
	 * @return
	 */
	List<PersonnelDTO> searchPsnByVatNo(@Param(value="vatNo")String vatNo);
	
	/**
	 * 根據登入且到職生效帳號查詢用戶
	 * @param account 帳號
	 * @return
	 */
	UserDTO selectLoginUserByEName(@Param(value="eName") String eName,@Param(value="sysdate") Date sysdate);
	
	/**
	 * 根據查詢條件查詢Personnel(使用者維護)
	 * @param psnNo
	 * @param chnName
	 * @param email
	 * @return
	 */
	List<PersonnelDTO> searchPsnByCondition(@Param(value = "psnNO") String psnNO,
			@Param(value = "engNM") String engNM, @Param(value = "email") String email);
	
	/**
	 * 取得部門下所有職稱之人員
	 * @param deptID
	 * @return
	 */
	List<PersonnelDTO> getDeptPersonnels(@Param(value="deptID")String deptID);
	
}
