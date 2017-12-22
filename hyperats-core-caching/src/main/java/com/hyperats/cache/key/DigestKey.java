/**
 * 
 */
package com.hyperats.cache.key;

/**
 * @author weiht
 *
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class DigestKey implements Key {
	private static final long serialVersionUID = 282656784835633189L;

	private static final String DEFAULT_ALGORITHM = "SHA-1";

	private final byte[] digest;

	public DigestKey(final String key, final String algorithm)
			throws NoSuchAlgorithmException {
		if (key == null) {
			throw new NullPointerException();
		}

		final MessageDigest messageDigest = MessageDigest
				.getInstance(algorithm);
		digest = messageDigest.digest(key.getBytes());
	}

	public DigestKey(final String key) throws NoSuchAlgorithmException {
		this(key, DEFAULT_ALGORITHM);
	}

	public byte[] getKey() {
		return digest;
	}

	@Override
	public boolean equals(final Object object) {
		if (object == null
				|| !object.getClass().isAssignableFrom(DigestKey.class)) {
			return false;
		}

		return ArrayUtils.isEquals(this.digest, ((DigestKey) object).getKey());
	}

	@Override
	public int hashCode() {
		final HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();

		for (final byte keyElement : getKey()) {
			hashCodeBuilder.append(keyElement);
		}

		return hashCodeBuilder.toHashCode();
	}
}