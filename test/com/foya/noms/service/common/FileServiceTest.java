package com.foya.noms.service.common;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import com.foya.noms.GenericTest;
import com.foya.noms.dao.common.FileDtlDao;
import com.foya.noms.dto.auth.UserDTO;
import com.foya.noms.dto.common.FileDtlDTO;

public class FileServiceTest extends GenericTest {

	@Autowired
	private FileService service;
	@Autowired
	private FileDtlDao dao;
	@Test
	public void testGetFileList() {
		List<FileDtlDTO> fileList =service.getFileList("BULLETIN", "bbb", "TYPE1","file_TYPE_NAME","desc");
		Assert.assertNotNull(fileList);
		Assert.assertTrue(fileList.size()>0);
	}
	
	@Test
	public void testUploadFile(){
		boolean result = false;
		UserDTO loginUser = new UserDTO();
		loginUser.setUsername("junitTest");
		MockMultipartHttpServletRequest request = new MockMultipartHttpServletRequest();
		request.addFile(new MockMultipartFile("junit","junit.txt","contentType","myContent1".getBytes()));
		MultipartFile file1 = request.getFile("junit");
		try {
			result = service.uploadFile("BULLETIN", "test2", "TYPE2", file1, loginUser, "127.0.0.1");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(result);
	}
	
	@Test
	public void testGetFilePath() {
		List<FileDtlDTO> fileList = service.getFileList("BULLETIN", "junit", "TYPE1","file_TYPE_NAME","desc");
		Assert.assertNotNull(fileList);
		Assert.assertTrue(fileList.size()>0);
		
		Assert.assertEquals(fileList.get(0).getDOC_NO(), "junit");
		Assert.assertEquals(fileList.get(0).getTYPE_PATH(), "BULLETIN");
		Assert.assertEquals(fileList.get(0).getFILE_TYPE(), "TYPE1");
	} 
	
	@Test
	public void testGetDownloadPath() {
		UserDTO loginUser = new UserDTO();
		loginUser.setUsername("junit");
		String remoteIp ="127.0.0.1";
		String result = service.getDownloadPath(BigDecimal.valueOf(445), loginUser, remoteIp);
		log.debug("result : "+result);
		Assert.assertNotNull(result);
		Assert.assertTrue(result.length()>0);
	}

}
