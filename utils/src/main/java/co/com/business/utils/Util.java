package co.com.business.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
	
	public static String formatDate(String format, Date date){
		
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		return sdf.format(date);
		
	}
	
}