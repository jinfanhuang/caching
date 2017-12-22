/**
 * 
 */
package com.hyperats.cache.key;

import org.apache.commons.lang.StringUtils;

/**
 * @author weiht
 * 
 */
public class StringKey implements Key {
	private static final long serialVersionUID = -5587092232276637031L;

	private final String key;

	public StringKey(final String key) {
		if (key == null) {
			throw new NullPointerException();
		}

		this.key = key;
	}

	public String getKey() {
		return key;
	}

	@Override
	public boolean equals(final Object object) {
		if (object == null
				|| !object.getClass().isAssignableFrom(StringKey.class)) {
			return false;
		}

		return StringUtils.equals(this.key, ((StringKey) object).getKey());
	}

	@Override
	public int hashCode() {
		return key.hashCode();
	}
}