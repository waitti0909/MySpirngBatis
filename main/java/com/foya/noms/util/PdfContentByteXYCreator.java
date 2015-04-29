package com.foya.noms.util;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

/**
 * 
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
 * <td>2014/12/26</td>
 * <td>產生標準格線的PDF</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 */
public class PdfContentByteXYCreator {
	
	String pdfPath = "D:/abc.pdf";	// 讀取的PDF檔案
	int printY = 0;
	int printX = 290;
	int totalPage = 1;
	
	public ByteArrayOutputStream create(boolean isShowAll){
		ByteArrayOutputStream baosPDF = new ByteArrayOutputStream();
		
		Rectangle rt = PageSize.A4;
		
		 // step 1
        Document document = new Document(rt, 50, 50, 50, 50);
        document = new Document();
        
		try {
			// step 2: we set the ContentType and create an instance of the
			// corresponding Writer
			PdfWriter writer = PdfWriter.getInstance(document, baosPDF);

			// step 3
			document.open();
			// step 4
//			 add content
            PdfContentByte cb = writer.getDirectContent();
            cb.beginText();
            
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            cb.setColorFill(Color.BLACK);
            
            if(isShowAll){
	            cb.setFontAndSize(bf,2);            
	            for(int x=0;x<rt.getWidth();x+=10){
	            	for(int y=0;y<rt.getHeight();y+=10){
	            	cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
	                		x+","+y, x, y, 0);    
	            	}
	            }
            }else{
            	cb.setLineWidth(0.3f);
            	cb.setFontAndSize(bf,6);
	            for (int x = 0; x < rt.getWidth(); x += 10) {
	            	if(x > 99){
	            		cb.showTextAligned(PdfContentByte.ALIGN_LEFT
							, "" + x/10, x, printY,0);
	            	}else{
	            		cb.showTextAligned(PdfContentByte.ALIGN_LEFT
								, "" + x, x, printY,0);
	            	}
				}
	            	            
	            for (int y = 0; y < rt.getHeight(); y += 10) {
					cb.showTextAligned(PdfContentByte.ALIGN_LEFT
							, "" + y, printX, y ,0);
					
					cb.moveTo(0, y);
	                cb.lineTo(rt.getWidth(), y);
	                cb.stroke();
				}	            
	         }
          
            if(!isShowAll){
            	for (int x = 0; x < rt.getWidth(); x += 10) {	            	
	            	cb.moveTo(x, 0);
	                cb.lineTo(x, rt.getHeight());
	                cb.stroke();
            	}
            	
            	for (int y = 0; y < rt.getHeight(); y += 10) {					
					cb.moveTo(0, y);
	                cb.lineTo(rt.getWidth(), y);
	                cb.stroke();
				}	
            }
            
            
            cb.endText();

            
		} catch (DocumentException e) {
			
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
        
// step 5: we close the document (the outputstream is also closed internally)
        document.close();
        
		return baosPDF;
	}
		

	public ByteArrayOutputStream create2(boolean isShowAll){
		ByteArrayOutputStream baosPDF = new ByteArrayOutputStream();
		
		Rectangle rt = PageSize.A4.rotate();
		
		 // step 1
        Document document = new Document(rt, 50, 50, 50, 50);
        
		try {
			// step 2: we set the ContentType and create an instance of the
			// corresponding Writer
			PdfWriter writer = PdfWriter.getInstance(document, baosPDF);

			// step 3
			document.open();
			// step 4
//			 add content
            PdfContentByte cb = writer.getDirectContent();
            cb.beginText();
            
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            cb.setColorFill(Color.BLACK);
            
            if(isShowAll){
	            cb.setFontAndSize(bf,2);            
	            for(int x=0;x<rt.getWidth();x+=10){
	            	for(int y=0;y<rt.getHeight();y+=10){
	            	cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
	                		x+","+y, x, y, 0);    
	            	}
	            }
            }else{
            	cb.setLineWidth(0.3f);
            	cb.setFontAndSize(bf,6);
	            for (int x = 0; x < rt.getWidth(); x += 10) {
	            	if(x > 99){
	            		cb.showTextAligned(PdfContentByte.ALIGN_LEFT
							, "" + x /10, x, printY,0);
	            	}else{
	            		cb.showTextAligned(PdfContentByte.ALIGN_LEFT
								, "" + x, x, printY,0);
	            	}
				}
	            	            
	            for (int y = 0; y < rt.getHeight(); y += 10) {
					cb.showTextAligned(PdfContentByte.ALIGN_LEFT
							, "" + y, printX, y ,0);
					
					cb.moveTo(0, y);
	                cb.lineTo(rt.getWidth(), y);
	                cb.stroke();
				}	            
	         }
          
            if(!isShowAll){
            	for (int x = 0; x < rt.getWidth(); x += 10) {	            	
	            	cb.moveTo(x, 0);
	                cb.lineTo(x, rt.getHeight());
	                cb.stroke();
            	}
            	
            	for (int y = 0; y < rt.getHeight(); y += 10) {					
					cb.moveTo(0, y);
	                cb.lineTo(rt.getWidth(), y);
	                cb.stroke();
				}	
            }
            
            
            cb.endText();

            
		} catch (DocumentException e) {
			
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
        
// step 5: we close the document (the outputstream is also closed internally)
        document.close();
        
		return baosPDF;
	}
		
	

	public ByteArrayOutputStream create3(boolean isShowAll){
		ByteArrayOutputStream baosPDF = new ByteArrayOutputStream();
		
		Rectangle rt = PageSize.A4;
		
		 // step 1
        Document document = new Document(rt, 50, 50, 50, 50);
        
		try {
			// step 2: we set the ContentType and create an instance of the
			// corresponding Writer
			PdfWriter writer = PdfWriter.getInstance(document, baosPDF);

			// step 3
			document.open();
			// step 4
//			 add content
            PdfContentByte cb = writer.getDirectContent();
            

			PdfReader reader = new PdfReader(pdfPath);
			
			for (int i = 1; i <= totalPage; i++) {
				document.newPage();
			    PdfImportedPage page1 = writer.getImportedPage(reader, i);
			    cb.addTemplate(page1,0,0);
			    
			    
	            
	            
	            cb.beginText();
	            
	            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
	            
	            // 浮水格線
	            PdfGState gstate = new PdfGState();
	            gstate.setFillOpacity(0.5f);
	            gstate.setStrokeOpacity(0.5f);
	            cb.setGState(gstate);
	            
	            cb.setColorFill(Color.BLACK);
	            
	            if(isShowAll){
		            cb.setFontAndSize(bf,2);            
		            for(int x=0;x<rt.getWidth();x+=10){
		            	for(int y=0;y<rt.getHeight();y+=10){
		            	cb.showTextAligned(PdfContentByte.ALIGN_LEFT,
		                		x+","+y, x, y, 0);    
		            	}
		            }
	            }else{
	            	cb.setLineWidth(0.3f);
	            	cb.setFontAndSize(bf,6);
	            	for (; printY < rt.getHeight(); printY += 150) {
			            for (int x = 0; x < rt.getWidth(); x += 10) {
			            	if(x > 99){
			            		cb.showTextAligned(PdfContentByte.ALIGN_LEFT
									, "" + x /10, x, printY,0);
			            	}else{
			            		cb.showTextAligned(PdfContentByte.ALIGN_LEFT
										, "" + x, x, printY,0);
			            	}
						}
	            	}
		            	            
		            for (int y = 0; y < rt.getHeight(); y += 10) {
						cb.showTextAligned(PdfContentByte.ALIGN_LEFT
								, "" + y, printX, y ,0);
						
						cb.moveTo(0, y);
		                cb.lineTo(rt.getWidth(), y);
		                cb.stroke();
					}	            
		         }
	          
	            if(!isShowAll){
	            	for (int x = 0; x < rt.getWidth(); x += 10) {	            	
		            	cb.moveTo(x, 0);
		                cb.lineTo(x, rt.getHeight());
		                cb.stroke();
	            	}
	            	
	            	for (int y = 0; y < rt.getHeight(); y += 10) {					
						cb.moveTo(0, y);
		                cb.lineTo(rt.getWidth(), y);
		                cb.stroke();
					}	
	            }
	            
	            
	            cb.endText();

	            
			}
			
		} catch (DocumentException e) {
			
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
        
// step 5: we close the document (the outputstream is also closed internally)
        document.close();
        
		return baosPDF;
	}
		
	public ByteArrayOutputStream create4(boolean isShowAll){
		ByteArrayOutputStream baosPDF = new ByteArrayOutputStream();
		
		Rectangle rt = PageSize.A4.rotate();
		
		 // step 1
        Document document = new Document(rt, 50, 50, 50, 50);
        
		try {
			// step 2: we set the ContentType and create an instance of the
			// corresponding Writer
			PdfWriter writer = PdfWriter.getInstance(document, baosPDF);

			// step 3
			document.open();
			// step 4
//			 add content
            PdfContentByte cb = writer.getDirectContent();
            

			PdfReader reader = new PdfReader(pdfPath);
			
			for (int i = 1; i <= totalPage; i++) {
				document.newPage();
			    PdfImportedPage page1 = writer.getImportedPage(reader, i);

				int rotation = reader.getPageRotation(1);
				// landscape mode
				if (rotation == 90 || rotation == 270) {
					cb.addTemplate(page1, 0, -1f, 1f, 0, 0, reader.getPageSizeWithRotation(1).getHeight());
				} else {
					cb.addTemplate(page1, 0, 0);
				}
			    
	            cb.beginText();
	            
	            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
	            cb.setColorFill(Color.BLACK);
	            
	            	cb.setLineWidth(0.3f);
	            	cb.setFontAndSize(bf,6);
		            for (int x = 0; x < rt.getWidth(); x += 10) {
		            	if(x > 99){
		            		cb.showTextAligned(PdfContentByte.ALIGN_LEFT
								, "" + x /10, x, printY,0);
		            	}else{
		            		cb.showTextAligned(PdfContentByte.ALIGN_LEFT
									, "" + x, x, printY,0);
		            	}
					}
		            	            
		            for (int y = 0; y < rt.getHeight(); y += 10) {
						cb.showTextAligned(PdfContentByte.ALIGN_LEFT
								, "" + y, printX, y ,0);
						
						cb.moveTo(0, y);
		                cb.lineTo(rt.getWidth(), y);
		                cb.stroke();
					}	 

	            	for (int x = 0; x < rt.getWidth(); x += 10) {	            	
		            	cb.moveTo(x, 0);
		                cb.lineTo(x, rt.getHeight());
		                cb.stroke();
	            	}
	            	

	            	for (int x = 0; x < rt.getWidth(); x ++) {	            	
		            	cb.moveTo(x, printY);
		                cb.lineTo(x, printY+2);
		                cb.stroke();
	            	}
	            	
	            	
	            	for (int y = 0; y < rt.getHeight(); y ++) {					
						cb.moveTo(printX, y);
		                cb.lineTo(printX+2, y);
		                cb.stroke();
					}	
	            
	            cb.endText();
	            
		            
		  }
	          
		} catch (DocumentException e) {
			
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
        
// step 5: we close the document (the outputstream is also closed internally)
        document.close();
        
		return baosPDF;
	}
	
	public static void main(String[] args) throws IOException {
		PdfContentByteXYCreator pb = new PdfContentByteXYCreator();
		String path = "D:/Doc2.pdf";
		ByteArrayOutputStream baos = pb.create3(false);
		
		OutputStream os = new FileOutputStream(path);
        
		os.write(baos.toByteArray());
		os.flush();
		os.close();
		
	}
}
