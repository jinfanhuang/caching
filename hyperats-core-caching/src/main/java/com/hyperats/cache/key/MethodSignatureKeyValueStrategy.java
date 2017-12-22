package com.hyperats.cache.key;

public interface MethodSignatureKeyValueStrategy {
	String generateKeyValue(final Object target, final String targetMethod,
			final Object[] arguments);
}