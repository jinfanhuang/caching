package com.hyperats.cache.manager;

import java.util.Collection;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;

public class CacheServiceImpl implements CacheService {

	private EhCacheCacheManager ehcacheCacheManager;
	
	public void setEhcacheCacheManager(EhCacheCacheManager ehcacheCacheManager) {
		this.ehcacheCacheManager = ehcacheCacheManager;
	}

	@Override
	public Collection<String> findCacheNames() {
		return ehcacheCacheManager.getCacheNames();
	}

	@Override
	public Cache findCacheByName(String cacheName) {
		return ehcacheCacheManager.getCache(cacheName);
	}

	@Override
	public void clearCache(String cacheName) {
		Cache cache = findCacheByName(cacheName);
		cache.clear();
	}

}
