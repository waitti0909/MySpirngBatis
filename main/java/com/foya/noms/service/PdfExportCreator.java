package com.foya.noms.service;

import java.io.ByteArrayOutputStream;

public interface PdfExportCreator {

	public ByteArrayOutputStream create(String pdfPath);
}
