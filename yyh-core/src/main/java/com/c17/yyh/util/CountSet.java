package com.c17.yyh.util;

import java.util.Collection;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonValue;

import com.google.common.collect.Maps;

public class CountSet {
	
	private Map<Integer, Integer> values = Maps.newHashMap();
	
	@JsonValue 
	@Override
	public String toString() {
		return SetHelper.toString(values);
	};
	
	public static CountSet fromString(String s) {
		CountSet set = new CountSet();
		SetHelper.parse(s, set.values);
		return set;
	}

	public static CountSet fromInt(int key, int count) {
		CountSet set = new CountSet();
		set.values.put(key, count);
		return set;
	}
	
	public void add(Integer key) {
		if (values.containsKey(key)) {
			values.put(key, values.get(key) + 1);
		} else {
			values.put(key, 1);
		}
	}
	
	public void sub(Integer key) {
		if (values.containsKey(key)) {
			values.put(key, values.get(key) - 1);
		}
	}

	public int count() {
		int count = 0;
		for (Integer key: values.keySet()) {
			count += values.get(key);
		}
		return count;
	}
	
	public boolean contains(Integer id) {
		return values.containsKey(id);
	}
	
	public Collection<Integer> keySet() {
		return values.keySet();
	}
	
	public Integer get(Integer key) {
		return values.get(key);
	}
}
