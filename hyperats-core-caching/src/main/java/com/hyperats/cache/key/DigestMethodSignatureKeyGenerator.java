package com.hyperats.cache.key;

import org.apache.commons.lang.StringUtils;

public class DigestMethodSignatureKeyGenerator extends
		AbstractMethodSignatureKeyGenerator {
	private String algorithm;

	@Override
	protected Key createKey(final String keyValue) {
		try {
			if (StringUtils.isEmpty(algorithm)) {
				return new DigestKey(keyValue);
			} else {
				return new DigestKey(keyValue, algorithm);
			}
		} catch (final Exception exception) {
			throw new KeyGeneratorException(exception);
		}
	}

	public void setAlgorithm(final String algorithm) {
		this.algorithm = algorithm;
	}
}