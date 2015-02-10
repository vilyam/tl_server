package com.c17.yyh.util;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonValue;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class PairSet {
	private Multimap<Integer, Integer> values = ArrayListMultimap.create();
	
	@JsonValue 
	@Override
	public String toString() {
		return SetHelper.toString(values);
	};
	
	public static PairSet fromString(String s) {
		PairSet set = new PairSet();
		SetHelper.parse(s, set.values);
		return set;
	}

	public Collection<Integer> keySet() {
		return values.keySet();
	}

	public Collection<Integer> get(Integer key) {
		return values.get(key);
	}
}
