package com.c17.yyh.util;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Multimap;

public final class SetHelper {
	private static final Pattern regex = Pattern.compile("(\\d+):(\\d+)");
	private static final String DELIMITER = ",";

	private static final Logger logger = LoggerFactory.getLogger(SetHelper.class);
	
	public static <T extends Map<Integer, Integer>> String toString(T map) {
		StringBuilder sb = new StringBuilder();
		for (Integer key: map.keySet()) {
			if (sb.length() > 0) {
				sb.append(DELIMITER);
			}
			sb.append(key).append(":").append(map.get(key));
		}
		return sb.toString();
	}
	
	public static <T extends Multimap<Integer, Integer>> String toString(T map) {
		StringBuilder sb = new StringBuilder();
		for (Integer key: map.keySet()) {
			for (Integer value: map.get(key)) {
				if (sb.length() > 0) {
					sb.append(DELIMITER);
				}
				sb.append(key).append(":").append(value);
			}
		}
		return sb.toString();
	}
	
	public static <T extends Multimap<Integer, Integer>> T parse(String s, T set) {
		if (s.isEmpty())
			return set;
		
		for (String token: s.split(DELIMITER)) {
			Matcher matcher = regex.matcher(token);
			if (matcher.matches()) {
				int key = Integer.valueOf(matcher.group(1));
				int value = Integer.valueOf(matcher.group(2));
				set.put(key, value);
			} else {
				logger.warn("Cannot parse '" + token + "'");
			}
		}
		return set;
	}
	
	public static <T extends Map<Integer, Integer>> T parse(String s, T set) {
		if (s.isEmpty())
			return set;

		for (String token: s.split(DELIMITER)) {
			if (s.isEmpty()) continue;
			Matcher matcher = regex.matcher(token);
			if (matcher.matches()) {
				int key = Integer.valueOf(matcher.group(1));
				int value = Integer.valueOf(matcher.group(2));
				set.put(key, value);
			} else {
				logger.warn("Cannot parse '" + token + "'");
			}
		}
		return set;
	}	
}
