package com.foya.noms.service.system;

import org.apache.log4j.SimpleLayout;
import org.apache.log4j.spi.LoggingEvent;

import com.foya.noms.dto.auth.UserDTO;
import com.foya.noms.resources.AppConstants;
import com.foya.noms.util.DateUtils;

public class MailLayout extends SimpleLayout {

	@Override    
    public String getContentType() {     
        return "text/html;charset=UTF-8";  
    }  
   
    @Override  
    public String format(LoggingEvent e) {  
        StringBuffer contents = new StringBuffer();
		contents.append("<table border='1' style='width:100%'>"
				+ "<tr>"
				+ "<td align='center'>回報者</td><td>"+((UserDTO) e.getMDC("user")).getUsername()+"</td>"
				+ "<td align='center'>回報時間</td><td>"+DateUtils.formatDate(AppConstants.DATE_TIME_PATTERN)+"</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td align='center'>Action</td><td>"+e.getLocationInformation().getClassName()+"."+e.getLocationInformation().getMethodName()+"</td>"
				+ "<td align='center'>RequestURL</td><td>"+(String) e.getMDC("requestUrl")+"</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td align='center'>Exception</td>"
				+ "<td colspan='3'>" + e.getMessage() + "</td>"
				+ "</tr>"
				+ "</table>");           
        return contents.toString();  
    }

}
