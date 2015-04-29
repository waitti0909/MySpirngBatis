package com.foya.noms.service.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.foya.dao.mybatis.model.TbComFile;
import com.foya.dao.mybatis.model.TbComFileDtl;
import com.foya.dao.mybatis.model.TbComFileDtlActionLog;
import com.foya.dao.mybatis.model.TbComFileDtlExample;
import com.foya.dao.mybatis.model.TbComFileExample;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.dao.mybatis.model.TbSysLookupExample;
import com.foya.noms.dao.common.FileDao;
import com.foya.noms.dao.common.FileDtlActionLogDao;
import com.foya.noms.dao.common.FileDtlDao;
import com.foya.noms.dao.system.LookupDao;
import com.foya.noms.dto.auth.UserDTO;
import com.foya.noms.dto.common.FileDtlDTO;
import com.foya.noms.resources.AppConstants;
import com.foya.noms.service.BaseService;
import com.foya.noms.util.DateUtils;

/**
 * 檔案處理SERVICE
 * <table>
 * <tr>
 * <th>版本</th>
 * <th>日期</th>
 * <th>詳細說明</th>
 * <th>modifier</th>
 * </tr>
 * <tr>
 * <td>1.0</td>
 * <td>2014/9/4</td>
 * <td>新建檔案</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 */
@Service
public class FileService extends BaseService {
	
	private final String localTestFolder = "C:/testUpload/";

	@Autowired
	private FileDao fileDao;
	
	@Autowired
	private FileDtlDao fileDtlDao;
	
	@Autowired
	private FileDtlActionLogDao fileDtlActionLogDao;
	
	@Autowired
	private LookupDao lookupDao;
	
	/**
	 * 取得已上傳檔案明細
	 * @param typePath
	 * @param fileDoc
	 * @param fileType
	 * @param sort
	 * @param order
	 * @return
	 * @author Charlie Woo
	 */
	public List<FileDtlDTO> getFileList(String typePath, String fileDoc, String fileType, String sort, String order) {
//		fileType = StringUtils.isEmpty(fileType) ? "NOTYPE" : fileType;	// 沒有傳檔案分類：預設不分類
		HashMap<String, String> condition = new HashMap<String, String>();
		condition.put("typePath", typePath);
		condition.put("fileDoc", fileDoc);
		condition.put("sort", sort);
		condition.put("order", order);
		if (StringUtils.isNotEmpty(fileType)) condition.put("fileType", fileType);
		return fileDtlDao.findByMapCondition(condition);
	}
	
	/**
	 * 檔案上傳
	 * @param typePath
	 * @param fileDoc
	 * @param mutipartFile
	 * @param loginUser
	 * @param remoteIp
	 * @return
	 * @throws IOException 
	 */
	@Transactional
	public boolean uploadFile(String typePath, String fileDoc, String fileType, 
			MultipartFile mutipartFile, UserDTO loginUser, String remoteIp) throws IOException {
		
		boolean alreadyUploaded = false, replaceFile = false;
		Date now = DateUtils.today();
		String fileTypePath = null, folder = null;
		String fileName = mutipartFile.getOriginalFilename();
		String action = AppConstants.FILE_UPLOAD_ACTION;
		TbComFileDtl detail = null;
		
		// find file path by typePath in sys_look_up setting
		fileTypePath = getFilePath(typePath);
		
		if (StringUtils.isNotEmpty(fileTypePath)) {
			// search by table 
			folder = fileTypePath + File.separator + fileDoc + File.separator + fileType;
			
			// check TbComFile
			alreadyUploaded = checkHaveMainRecord(typePath, fileDoc);
			
			// check TbComFileDtl
			detail = getFileDtlSeqId(typePath, fileDoc, fileType, fileName);
			if (detail != null) {	// have been uploaded
				replaceFile = true;
				action = AppConstants.FILE_REPLACE_ACTION;
			}
		} else {
			// syslookup沒有設定
			folder = localTestFolder;
		}
		
		// do save file to physical location
		File newFilePath = new File(folder);
		log.debug("Prepare to upload to folder : " + newFilePath.getAbsolutePath());		
		if (!newFilePath.exists()) newFilePath.mkdirs();
		
		File newFile = new File(folder + File.separator + fileName);
		log.debug("Prepare to upload file : " + newFile.getAbsolutePath());
		if (!newFile.exists()) newFile.createNewFile();
		
		FileOutputStream fos = new FileOutputStream(newFile);
		InputStream is = mutipartFile.getInputStream();
		try {
			byte[] data = new byte[512];
			int length = -1;
			
			while((length = is.read(data)) != -1) { 
				fos.write(data, 0, length);
				fos.flush();
			} 
			
		} catch (IOException e) {
			throw e;
		} finally {
			fos.close();
			is.close();
		}		
		
		// insert main
		if (!alreadyUploaded) {			
			TbComFile file = new TbComFile();
			file.setDOC_NO(fileDoc);
			file.setTYPE_PATH(typePath);
			fileDao.insert(file);
		}
		
		// insert or update detail
		if (replaceFile) {		
			detail.setCR_TIME(now);
			detail.setCR_USER(loginUser.getUsername());
			fileDtlDao.update(detail);
		} else {			
			TbComFileDtl fileD = new TbComFileDtl();
			fileD.setTYPE_PATH(typePath);
			fileD.setDOC_NO(fileDoc);
			fileD.setFILE_NAME(fileName);
			fileD.setFILE_TYPE(fileType);
			fileD.setCR_USER(loginUser.getUsername());
			fileD.setCR_TIME(now);
			fileDtlDao.insert(fileD);
		}
		
		// process file log
		insertActionLog(loginUser.getUsername(), action, remoteIp, typePath, fileDoc, fileType, fileName);
		
		return true;
	}
	
	/**
	 * 檔案下載
	 * @param fileId
	 * @param loginUser
	 * @param remoteIp
	 * @return
	 */
	@Transactional
	public String getDownloadPath(BigDecimal fileId, UserDTO loginUser, String remoteIp) {
		// record download action log
		TbComFileDtl model = fileDtlDao.findByPk(fileId);		
		insertActionLog(loginUser.getUsername(), AppConstants.FILE_DOWNLOAD_ACTION, remoteIp, model.getTYPE_PATH(), model.getDOC_NO(), model.getFILE_TYPE(), model.getFILE_NAME());
		
		return fileDtlDao.findFullPathByFileId(fileId);
	}
	
	/**
	 * 刪除檔案
	 * @param fileId
	 * @param loginUser
	 * @param remoteIp
	 * @return
	 * @throws FileNotFoundException 
	 */
	@Transactional
	public boolean deleteFile(BigDecimal fileId, UserDTO loginUser, String remoteIp) throws FileNotFoundException {
		
		TbComFileDtl model = fileDtlDao.findByPk(fileId);
		
		// delete physical file
		String filePath = fileDtlDao.findFullPathByFileId(fileId);
		log.debug("delete file path :: " + filePath);
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		} else {
			// if file not exist, throws FileNotFoundException
			// throw new FileNotFoundException("檔案不存在! 刪除失敗");
			return false;
		}
		
		// delete tb_com_file_dtl
		fileDtlDao.delete(fileId);
		
		// insert action log
		insertActionLog(loginUser.getUsername(), AppConstants.FILE_DELETE_ACTION, remoteIp, model.getTYPE_PATH(), model.getDOC_NO(), model.getFILE_TYPE(), model.getFILE_NAME());
		
		return true;
	}
	
	/**
	 * 寫入檔案操作LOG
	 * @param userName
	 * @param action
	 * @param remoteIp
	 * @param typePath
	 * @param fileDoc
	 * @param fileType
	 * @param fileName
	 * @return
	 */
	private int insertActionLog(String userName, String action, String remoteIp, String typePath, String fileDoc, String fileType, String fileName) {
		TbComFileDtlActionLog fileLog = new TbComFileDtlActionLog();
		fileLog.setACTION_USER(userName);
		fileLog.setACTION_TIME(DateUtils.today());
		fileLog.setACTION(action);
		fileLog.setDOC_NO(fileDoc);
		fileLog.setTYPE_PATH(typePath);
		fileLog.setREMOTE_IP(remoteIp);
		fileLog.setFILE_NAME(fileName);
		fileLog.setFILE_TYPE(fileType);
		log.debug(fileLog.toString());
		return fileDtlActionLogDao.insert(fileLog);
	}
	
	/**
	 * 取得檔案種類路徑
	 * @param typePath
	 * @return
	 */
	private String getFilePath(String typePath) {
		TbSysLookupExample example = new TbSysLookupExample();
		example.createCriteria().andTYPEEqualTo("UPLOADFILE").andCODEEqualTo(typePath);
		List<TbSysLookup> lookups = lookupDao.selectByExample(example);
		if (!lookups.isEmpty()) {
			return lookups.get(0).getVALUE1();	// 條件下應只會有一筆
		}
		return null;
	}
	
	/**
	 * 檢查主檔是否已有上傳記錄
	 * @param typePath
	 * @param fileDoc
	 * @return
	 */
	private boolean checkHaveMainRecord(String typePath, String fileDoc) {
		TbComFileExample tbComFileExample = new TbComFileExample();
		tbComFileExample.createCriteria().andTYPE_PATHEqualTo(typePath).andDOC_NOEqualTo(fileDoc);
		List<TbComFile> models = fileDao.findByCondition(tbComFileExample);
		
		return !models.isEmpty();
	}
	
	private TbComFileDtl getFileDtlSeqId(String typePath, String fileDoc, String fileType, String fileName) {
		TbComFileDtlExample example = new TbComFileDtlExample();
		example.createCriteria().andTYPE_PATHEqualTo(typePath).andDOC_NOEqualTo(fileDoc)
			.andFILE_TYPEEqualTo(fileType).andFILE_NAMEEqualTo(fileName);
		
		List<TbComFileDtl> models = fileDtlDao.findByCondition(example);
		if (!models.isEmpty()) {
			return models.get(0);
		}		
		return null;
	}
	
	public TbComFileDtl getLatestFileDtlSeqId(String typePath, String docNo) {
		TbComFileDtlExample example = new TbComFileDtlExample();
		example.createCriteria().andTYPE_PATHEqualTo(typePath).andDOC_NOEqualTo(docNo);
		example.setOrderByClause(" cr_time desc ");	
		List<TbComFileDtl> models = fileDtlDao.findByCondition(example);
		if (!models.isEmpty()) {
			return models.get(0);
		}		
		return null;
	}
	
	
}
