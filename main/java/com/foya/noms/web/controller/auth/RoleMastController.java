package com.foya.noms.web.controller.auth;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbAuthRoleMast;
import com.foya.noms.dto.auth.RoleDTO;
import com.foya.noms.dto.auth.RoleMastDTO;
import com.foya.noms.service.auth.PersonnelService;
import com.foya.noms.service.auth.PsnRoleService;
import com.foya.noms.service.auth.RoleMastService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.JqGridData;
import com.foya.noms.web.controller.BaseController;
import com.github.miemiedev.mybatis.paginator.domain.PageList;



@Controller
@RequestMapping(value = "/auth")
public class RoleMastController extends BaseController {
	
	@Autowired
	private RoleMastService roleService;
	
	@Autowired
	private PsnRoleService userRoleService;
	
	@Autowired
	private PersonnelService userService;

	/**
	 * 初始頁面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/role/list")
	public String roleMastInit(HttpServletRequest request,
			Map<String, Object> model) {
		model.put("allRoles", roleService.getAll());
		return "ajaxPage/auth/roleMast";
	}

	/**
	 * 系統角色查詢
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/searchRole/")
	public @ResponseBody JqGridData<RoleMastDTO> searchRoleDao(@RequestParam("roleId") Integer roleID) {	
		List<RoleMastDTO> rsList = null;		
		rsList = roleService.getById(roleID);
		PageList<RoleMastDTO> page= (PageList<RoleMastDTO>) rsList;		
		return new JqGridData<>(page.getPaginator().getTotalCount(), rsList);
	}
	
	/**
	 * 刪除系統角色
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/roleDelete/")
	public @ResponseBody BaseJasonObject<RoleDTO> roleDelete(@RequestParam("roleID") String roleID) {
		  try {
			  String[] roleIdArray = roleID.split(","); 			  
			  roleService.delete(roleIdArray);
			  
			  List<RoleDTO> listRole=roleService.getAll();	  
		      return new BaseJasonObject<>(listRole,getMessageDetail("msg.delete.success"),"無資料");
			  
		} catch (Exception e) {
			 log.error(ExceptionUtils.getFullStackTrace(e));
			 return new BaseJasonObject<>(false,getMessageDetail("msg.delete.fail"));
		}
	}
	
	/**
	 * 新增系統角色
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/roleAdd/")
	public String roleAdd() {
		return "ajaxPage/auth/roleMastA";
	}

	/**
	 * 儲存新增系統角色
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/roleAddSave/")
	public @ResponseBody List<RoleDTO> roleAddSave(HttpServletRequest request) {		
		String currentUserName = getLoginUser().getUsername();
		TbAuthRoleMast role = new TbAuthRoleMast();
		
		String rolename = (request.getParameter("roleName"));
		if((request.getParameter("roleDesc"))!=null && !(request.getParameter("roleDesc")).equals("")){
			String roledesc = (request.getParameter("roleDesc"));
			role.setROLE_DESC(roledesc);
		}
		
		role.setROLE_NAME(rolename);
		role.setMD_USER(currentUserName);
		role.setMD_TIME(new Date());
		roleService.addRole(role);		
		List<RoleDTO> listRole = roleService.getAll();
		return listRole;
	}


	/**
	 * 設定編輯角色使用者
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/searchUserRole/")
	public String searchUser(@RequestParam("btnType") String btnType,@RequestParam("roleID") Integer roleID,Map<String, Object> model) {
		//角色資訊
		model.put("role", roleService.getById(roleID));
		//取所有使用者
		model.put("allPsn", userService.getAllPsn());
		//根據角色ID取已選取之使用者
		model.put("getUserByRoleId", userRoleService.getUserByRole(new BigDecimal(roleID)));
		//呈現類型
		model.put("showType", btnType);
		return "ajaxPage/auth/roleMastE";
	}
	
	/**
	 * 儲存角色使用者設定
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/userRoleSave/")
	public @ResponseBody
	List<RoleMastDTO> roleUserSave(HttpServletRequest request) {	
		BigDecimal roleId = BigDecimal.valueOf(Long.parseLong(request.getParameter("roleID")));
		String roleDesc = request.getParameter("roleDESC");
		String currentUserName = getLoginUser().getUsername();
		
		TbAuthRoleMast role = new TbAuthRoleMast();
		role.setROLE_ID(roleId);
		role.setROLE_DESC(roleDesc);
		role.setMD_USER(currentUserName);

		String userIdArr = request.getParameter("userIDArr");
		String[] userIdArray = userIdArr.split(",");
		userRoleService.addPsnRole(role,userIdArray,currentUserName);
		
		List<RoleMastDTO> roleraw = roleService.getById(Integer.parseInt(request.getParameter("roleID")));
		return roleraw;
	}
}