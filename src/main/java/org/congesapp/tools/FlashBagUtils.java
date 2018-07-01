package org.congesapp.tools;

import static org.congesapp.tools.FlashLevel.FLASH_DANGER;
import static org.congesapp.tools.FlashLevel.FLASH_INFO;
import static org.congesapp.tools.FlashLevel.FLASH_SUCCESS;
import static org.congesapp.tools.FlashLevel.FLASH_WARNING;

import javax.servlet.http.HttpServletRequest;

public class FlashBagUtils {
	public static final String ATTR_FLASH_MESSAGE = "flashMessage";
	public static final String ATTR_FLASH_LEVEL = "flashLevel";	
	public static final String ATTR_FLASH_DISPLAY = "flashDisplay";	
	
	public static void flashError(HttpServletRequest req, String message) {
		flash(req,message,FLASH_DANGER);		
	}
	
	public static void flashWarning(HttpServletRequest req, String message) {
		flash(req,message,FLASH_WARNING);		
	}
	
	public static void flashNotice(HttpServletRequest req, String message) {
		flash(req,message,FLASH_INFO);		
	}
	
	public static void flashSuccess(HttpServletRequest req, String message) {
		flash(req,message,FLASH_SUCCESS);		
	}
	
	private static void flash(HttpServletRequest req, String message,FlashLevel level) {
		req.getSession().setAttribute(ATTR_FLASH_MESSAGE, message);
		req.getSession().setAttribute(ATTR_FLASH_LEVEL, level.getLevel());	
		req.getSession().setAttribute(ATTR_FLASH_DISPLAY, level.getDisplay());		
	}
	
	public static void cleanFlash(HttpServletRequest req) {
		req.getSession().removeAttribute(ATTR_FLASH_MESSAGE);
		req.getSession().removeAttribute(ATTR_FLASH_LEVEL);
		req.getSession().removeAttribute(ATTR_FLASH_DISPLAY);		
	}
}
