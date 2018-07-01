package org.congesapp.tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Tools {
	
	public static final int ERROR_CODE_INT =-1;
	public static final Long ERROR_CODE_LONG =-1L;
	
	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public static boolean isLong(String s) {
		try {
			Long.parseLong(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public static Long parseSecuredLong(String str) {
		if(Tools.isLong(str)) {
			return Long.parseLong(str);
		}
		return ERROR_CODE_LONG;		
	}
	
	public static int parseSecuredInt(String str) {
		if(Tools.isInteger(str)) {
			return Integer.parseInt(str);
		}
		return ERROR_CODE_INT;		
	}
	
	public static Date randomDate() {	
		
		//Time between epoch and 31/12/2000
		long time = (long) (Math.random()*978217200000L);
		return new Date(time);
	}
	
	public static Date strToDate(String str) throws ParseException {
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
		Date date = format.parse(str);
		
		return date;
	}
	
	public static Date addDays(Calendar cal,Integer days) {
		
		Calendar cal2 = (Calendar) cal.clone();
		cal2.add(Calendar.DAY_OF_MONTH, days);
		return cal2.getTime();		
	}
	

	
}
