package com.hyperats.cache.provider;

public interface Provider {
	/**
	 * 获取缓存。
	 * 
	 * @param key 缓存的键
	 * @return 缓存的值
	 */
	Object get(Object key);
	/**
	 * 存入缓存。
	 * 
	 * @param key 缓存的键
	 * @param entry 要缓存的对象
	 */
	void put(Object key, Object entry);
	/**
	 * 移除缓存。
	 * 
	 * @param key 缓存的键
	 */
	void remove(Object key);
}