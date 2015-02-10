package com.c17.yyh.util;

import java.util.List;

public final class ListHelper {

	public static int[] toIntArray(List<Integer> list){
		
		int[] ret = new int[list.size()];
		
		for(int i = 0;i < ret.length;i++){
		
			ret[i] = list.get(i);
		}
		
		return ret;
	}
	
	public static String[] toStringArray(List<String> list){
		if (list == null) return null;
		return list.toArray(new String[0]);
	}
}
