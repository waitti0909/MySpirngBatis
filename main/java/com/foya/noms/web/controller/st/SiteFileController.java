package com.foya.noms.web.controller.st;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.noms.dto.common.FileDtlDTO;
import com.foya.noms.service.st.SiteFileService;
import com.foya.noms.util.JqGridData;
import com.foya.noms.web.controller.BaseController;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Controller
public class SiteFileController extends BaseController {

	@Autowired
	private SiteFileService siteFileService;
		
	/**
	 * 基站附件下載頁
	 * @param typePath
	 * @param fileDoc
	 * @param fileType
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/st/file/initDownload")
	public String initSiteFileDownloadPage(@RequestParam(value="siteId") String siteId, @RequestParam(value="btsSiteId") String btsSiteId,
			Map<String, Object> map) {
		map.put("btsSiteId", btsSiteId);
		map.put("siteId", siteId);
		map.put("works", siteFileService.getWorksForSite(siteId));
		return "/ajaxPage/st/SiteFileDownload";
	}
	
	/**
	 * 取得已上傳檔案列表
	 * @param typePath
	 * @param fileDoc
	 * @param fileType
	 * @return
	 */
	@RequestMapping(value = "/st/file/getList")
	@ResponseBody
	public JqGridData<FileDtlDTO> getSiteFileList(@RequestParam(value="siteId") String siteId, 
			@RequestParam(value="docNo") String docNo, @RequestParam(value="sort") String sort, @RequestParam(value="order") String order) {
		// load file list that has been uploaded
		List<String> fileDoc = new ArrayList<>();
		if (StringUtils.isEmpty(docNo)) {
			Map<String, String> fileDocMap = siteFileService.getWorksForSite(siteId);
			if (fileDocMap.isEmpty()) {
				// 若該基站還沒產生作業類型的附件，就需另找從單檔功能上傳的附件
				fileDocMap.put("S"+siteId, "站台附件");
			}
			fileDoc.addAll(fileDocMap.keySet());
		} else {
			fileDoc.add(docNo);
		}
		List<FileDtlDTO> rows = siteFileService.getSiteFileList(fileDoc, sort, order);
		PageList<FileDtlDTO> page= (PageList<FileDtlDTO>) rows;		
		return new JqGridData<>(page.getPaginator().getTotalCount(), rows);
	}
}
