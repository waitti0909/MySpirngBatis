package com.foya.noms.web.controller.common;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.foya.dao.mybatis.model.TbComFileDtl;
import com.foya.noms.service.common.FileService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.web.controller.BaseController;

@Controller
public class PIC001Controller extends BaseController {

	@Autowired
	private FileService fileService;
	
	/**
	 * 圖資上傳初始頁
	 * @return
	 */
	@RequestMapping(value = "/common/PIC001/inital")
	public String pIC001Init() {
		return "ajaxPage/common/PIC001";
	}
	
	/**
	 * 上傳頁面
	 * @param zip
	 * @param typePath
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/common/PIC001/search")
	public String searchSignalMap(@RequestParam(value="zip") String zip,@RequestParam(value="typePath") String typePath,
			final RedirectAttributes redirectAttributes) {
		redirectAttributes.addAttribute("typePath", typePath);
		redirectAttributes.addAttribute("fileDoc", zip);
//		redirectAttributes.addFlashAttribute("fileType", "Successfully added..");
		return "redirect:/common/file/initLoad";	
	}
	/**
	 * 上傳頁面
	 * @param zip
	 * @param typePath
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/common/PIC001/downloadSignalMap")
	@ResponseBody
	public BaseJasonObject<String>  downloadSignalMap(@RequestParam(value="zip") String zip,
			final RedirectAttributes redirectAttributes) {
		TbComFileDtl f =fileService.getLatestFileDtlSeqId("SIGNALMAP", zip);
		if(f==null){
			 return  new BaseJasonObject<>(false,"檔案不存在！") ;
		}
		String fullPath = fileService.getDownloadPath(f.getFILE_DTL_SEQ_ID(), getLoginUser(), getUserIp()); // get file full path by file id
        File downloadFile = new File(fullPath);
        
        if(!downloadFile.exists()){
			 return new BaseJasonObject<>(false,"檔案不存在！") ;
		}
		return new BaseJasonObject<>(""+f.getFILE_DTL_SEQ_ID(),"Success") ;	
	}

	
}
