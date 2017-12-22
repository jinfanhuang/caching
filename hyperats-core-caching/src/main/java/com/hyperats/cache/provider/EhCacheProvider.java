/**
 * 
 */
package com.hyperats.cache.provider;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

/**
 * @author weiht
 * 
 */
public class EhCacheProvider implements Provider {
	private final Cache cache;

	public EhCacheProvider(final Cache cache) {
		this.cache = cache;
	}

	public Object get(final Object key) {
		final Element element = cache.get(key);
		if (element == null) {
			return null;
		}

		return element.getObjectValue();
	}

	public void put(final Object key, final Object entry) {
		cache.put(new Element(key, entry));
	}

	public void remove(final Object key) {
		cache.remove(key);
	}
}