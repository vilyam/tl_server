package com.c17.yyh.core;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import com.c17.yyh.db.entities.UserBase;

@Service("cacheService")
public class CacheService {

	@CachePut(value="users", key="#user.snId")
	public UserBase updateUserInCache(UserBase user) {
		return user;
	}
	
	@CacheEvict(value="users", allEntries = false, key = "#user.snId")
	public void clearCacheForUser(UserBase user) {
	}
	
	@CacheEvict(value="users", allEntries = false, key = "#p0")
	public void clearCacheForUser(String userSnId) {
	}
	
	@CacheEvict(value="users", allEntries = true)
	public void clearCache() {
	}
}
