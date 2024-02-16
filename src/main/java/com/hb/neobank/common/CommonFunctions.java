package com.hb.neobank.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.WordUtils;
import org.springframework.stereotype.Service;

@Service
public class CommonFunctions {

	public static Date convertDateStringToDate(String dateStr, String format) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			date = sdf.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("unable to parse date " + e.getMessage());
		}
		return date;
	}

	public static String toTitleCase(String str) {
		final char[] delimiters = { ' ', '_' };
		return WordUtils.capitalizeFully(str, delimiters);
	}
}
