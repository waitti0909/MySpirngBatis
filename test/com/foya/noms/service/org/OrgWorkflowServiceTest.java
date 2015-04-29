package com.foya.noms.service.org;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.noms.GenericTest;
import com.foya.noms.dto.org.DeptDTO;
import com.foya.noms.dto.org.DeptPosDTO;

public class OrgWorkflowServiceTest extends GenericTest {
	@Autowired
	private OrgWorkflowService orgWorkflowService;

	@Test
	public void testDeptTree() {
		List<DeptDTO> deptDTOs = orgWorkflowService.searchParentByChild("240000");
		Assert.assertNotNull(deptDTOs);
		Assert.assertTrue(deptDTOs.size() > 0);
	}

	@Test
	public void testDeptPos() {
		List<DeptPosDTO> deptPosDTOs = orgWorkflowService.getDeptPosDtoByDeptId("240000");
		Assert.assertNotNull(deptPosDTOs);
		Assert.assertTrue(deptPosDTOs.size() > 0);
	}

	@Test
	public void testDeptAndPos() {
		DeptPosDTO deptPosDTO = orgWorkflowService.getDeptPosDtoByDeptIdAndPosId("480100",
				"WF_DEPT_MANAGER");
		Assert.assertNotNull(deptPosDTO);
	}

}
