package com.c17.yyh.util;

public abstract class LazyBean<T> {
	private volatile T t;
	
	public T get() { 
		if (t == null) {
			synchronized (this) {
				if (t == null) {
					t = init();
				}
			}
		}
		return t; 
	}
	
	protected abstract T init();
}
