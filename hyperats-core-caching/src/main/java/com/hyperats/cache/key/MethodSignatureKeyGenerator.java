/**
 * 
 */
package com.hyperats.cache.key;

/**
 * @author weiht
 * 
 */
public interface MethodSignatureKeyGenerator extends KeyGenerator {
	Key generateKey(final Object target, final String targetMethod,
			final Object[] arguments);
}