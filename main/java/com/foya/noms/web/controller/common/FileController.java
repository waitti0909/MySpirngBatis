package com.foya.noms.web.controller.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.portable.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.foya.dao.mybatis.model.TbComFile;
import com.foya.dao.mybatis.model.TbComFileDtl;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.noms.dto.common.FileDtlDTO;
import com.foya.noms.service.common.FileService;
import com.foya.noms.service.system.LookupService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.JqGridData;
import com.foya.noms.web.controller.BaseController;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
/**
 * 檔案上下載處理Controller
 * 
 * <table>
 * <tr>
 * <th>版本</th>
 * <th>日期</th>
 * <th>詳細說明</th>
 * <th>modifier</th>
 * </tr>
 * <tr>
 * <td>1.0</td>
 * <td>2014/9/3</td>
 * <td>檔案上下載處理Controller</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 */
@Controller
public class FileController extends BaseController {
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private LookupService lookupService;
	
	/**
	 * 檔案處理頁
	 * @param typePath
	 * @param fileDoc
	 * @param fileType
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/common/file/initLoad")
	public String initUploadPage(@RequestParam(value="typePath") String typePath, @RequestParam(value="fileDoc") String fileDoc, 
			@RequestParam(value="fileType", required = false) String fileType, Map<String, Object> modelMap) {
		TbSysLookup model = lookupService.getByTypeAndCode("UPLOADFILE", typePath);
		modelMap.put("typePath", typePath);
		modelMap.put("fileDoc", fileDoc);
		modelMap.put("fileType", fileType);		
		modelMap.put("fileTypeMap", lookupService.getFileTypeKeyValues("FILETYPE", typePath));
		if (model != null) modelMap.put("typePathName", model.getNAME());
		return "/ajaxPage/common/fileM";
	}
	
	/**
	 * 取得已上傳檔案列表
	 * @param typePath
	 * @param fileDoc
	 * @param fileType
	 * @return
	 */
	@RequestMapping(value = "/common/file/getList")
	@ResponseBody
	public JqGridData<FileDtlDTO> getFileList(@RequestParam(value="typePath") String typePath, @RequestParam(value="fileDoc") String fileDoc,
			@RequestParam(value="fileType") String fileType, @RequestParam(value="sort") String sort, @RequestParam(value="order") String order) {
		// load file list that has been uploaded
		List<FileDtlDTO> rows = fileService.getFileList(typePath, fileDoc, fileType, sort, order);
		PageList<FileDtlDTO> page= (PageList<FileDtlDTO>) rows;		
		return new JqGridData<>(page.getPaginator().getTotalCount(), rows);
	}

	/**
	 * 上傳
	 * @param file
	 * @param model
	 * @return
	 * @throws IOException 
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/common/file/upload", method = RequestMethod.POST)
	@ResponseBody
	public BaseJasonObject<TbComFile> handleUploadProcess(MultipartHttpServletRequest mutipartRequest, 
			@RequestParam(value="typePath") String typePath, @RequestParam(value="fileDoc") String fileDoc,
			@RequestParam(value="fileType") String fileType) throws IOException {
		boolean isSuccess = false;
		for (Iterator<String> itr = mutipartRequest.getFileNames(); itr.hasNext();) { 			
			MultipartFile file = mutipartRequest.getFile(itr.next());
			
			isSuccess = fileService.uploadFile(typePath, fileDoc, fileType, file, getLoginUser(), mutipartRequest.getRemoteAddr());
			log.debug("typePath : " + typePath);
			log.debug("fileDoc : " + fileDoc);
			log.debug("fileType : " + fileType);
			log.debug("contentType : " + file.getContentType());
			log.debug("originalFileName : " + file.getOriginalFilename());
			log.debug("size : " + file.getSize());
		}
		return new BaseJasonObject<>(isSuccess, isSuccess ? AJAX_SUCCESS : AJAX_FAILED);
	}
	
	/**
	 * 共用下載頁
	 * @param typePath
	 * @param fileDoc
	 * @param fileType
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/common/file/initDownload")
	public String initDownloadPage(@RequestParam(value="typePath") String typePath, @RequestParam(value="fileDoc") String fileDoc, 
			@RequestParam(value="fileType") String fileType, Map<String, String> map) {
		log.debug("prepared to download list fileDoc::::" + fileDoc);
		TbSysLookup model = lookupService.getByTypeAndCode("UPLOADFILE", typePath);
		map.put("typePath", typePath);
		map.put("fileDoc", fileDoc);
		map.put("fileType", fileType);
		if (model != null) map.put("typePathName", model.getNAME());
		return "/ajaxPage/common/download";
	}
	
	/**
	 * 下載前檢查檔案存在
	 * @param fileId
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/common/file/checkExist")
	@ResponseBody
	public BaseJasonObject<String> handleDownloadProcess(@RequestParam(value="fileId") BigDecimal fileId) 
			throws IOException {
		
		String fullPath = fileService.getDownloadPath(fileId, getLoginUser(), getUserIp()); // get file full path by file id
        File downloadFile = new File(fullPath);
        //if (!downloadFile.exists()) throw new FileNotFoundException("檔案不存在！下載失敗");
        
		return new BaseJasonObject<>(downloadFile.exists(), downloadFile.exists() ? "" : "檔案不存在！下載失敗" );
	}
	
	/**
	 * 下載
	 * @param fileId
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/common/file/download")
	public void handleDownloadProcess(@RequestParam(value="fileId") BigDecimal fileId, HttpServletRequest request, HttpServletResponse response) 
			throws IOException {
		
		response.setCharacterEncoding("UTF-8");
		String fullPath = fileService.getDownloadPath(fileId, getLoginUser(), getUserIp()); // get file full path by file id
        File downloadFile = new File(fullPath);
        if (!downloadFile.exists()) throw new FileNotFoundException("檔案不存在，下載失敗！");
        FileInputStream is = new FileInputStream(downloadFile);
         
        // get MIME type of the file
        String mimeType = request.getServletContext().getMimeType(fullPath);
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }
        log.debug("MIME type: " + mimeType);
        
        String fileName = new String(downloadFile.getName().getBytes(), "ISO8859-1");
		response.setContentType(mimeType);
        response.setHeader( "Content-Disposition", "attachment; filename=\"" + fileName + "\"" );
        OutputStream os = response.getOutputStream();
        
        byte[] buffer = new byte[2048];
        int bytesRead = -1;
 
        // write bytes read from the input stream into the output stream
        while ((bytesRead = is.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        
		os.flush();
		is.close();
		os.close();
		
	}
	
	/**
	 * 刪除
	 * @param fileId
	 * @param request
	 * @return
	 * @throws FileNotFoundException 
	 */
	@RequestMapping(value = "/common/file/delete")
	@ResponseBody
	public BaseJasonObject<TbComFileDtl> handleDeleteFile(@RequestParam(value="fileId") BigDecimal fileId, HttpServletRequest request) throws FileNotFoundException {
		boolean isSuccess = fileService.deleteFile(fileId, getLoginUser(), getUserIp());
		return new BaseJasonObject<>(isSuccess, isSuccess ? AJAX_SUCCESS : "檔案不存在，刪除失敗！");
	}
}
