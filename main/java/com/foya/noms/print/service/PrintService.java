package com.foya.noms.print.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import com.foya.dao.mybatis.model.TbLsAppForm;
import com.foya.exception.NomsException;
import com.lowagie.text.DocumentException;

public interface PrintService {

	public ByteArrayOutputStream makePdfs(TbLsAppForm pdf, Map<String, String> map) throws IOException, DocumentException, NomsException;
}
