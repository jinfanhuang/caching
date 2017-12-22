package com.hyperats.cache.manager;

import java.util.Collection;
import org.springframework.cache.Cache;

public interface CacheService {

	/**
	 * 获取所有缓存名字列表
	 * @return
	 */
	public Collection<String> findCacheNames();
	
	/**
	 * 根据名称获取缓存对象
	 * @param cacheName
	 * @return
	 */
	public Cache findCacheByName(String cacheName);
	
	/**
	 * 清除某个缓存
	 * @param cacheName
	 */
	public void clearCache(String cacheName); 
}
