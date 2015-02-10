package com.c17.yyh.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public final class HashHelper {
	
	public static String messagePrepareToHash(Object obj) throws IllegalArgumentException, IllegalAccessException{
		String strToHash = "";
		Class<?>  oClass = obj.getClass();
		
		Field[] fields = oClass.getFields();
		List<String> listToSort= new LinkedList<String> ();
		for (int i=0;i<fields.length;i++){
			Object value = fields[i].get(obj);
			if (value != null) {
				listToSort.add(fields[i].getName() + "=" + value);
			}
		}
		
        String[] sorted = listToSort.toArray(new String[0]);
        Arrays.sort(sorted, String.CASE_INSENSITIVE_ORDER);
		
		for (int i = 0; i < sorted.length; i++) {
			strToHash += sorted[i];
		}
		return strToHash;
	}
}
