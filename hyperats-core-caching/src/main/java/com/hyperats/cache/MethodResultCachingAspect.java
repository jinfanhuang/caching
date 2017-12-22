package com.hyperats.cache;

import java.util.ArrayList;
import java.util.List;

import com.hyperats.cache.directive.CacheDirective;
import com.hyperats.cache.directive.CacheRemovalDirective;
import com.hyperats.cache.key.Key;
import com.hyperats.cache.key.MethodSignatureKeyGenerator;
import com.hyperats.cache.provider.Provider;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;

public class MethodResultCachingAspect {
	private static final Logger logger = LogManager.getLogger(MethodResultCachingAspect.class);
	
	private List<CacheDirective> cacheDirectives
		= new ArrayList<CacheDirective>();
	private List<CacheRemovalDirective> cacheRemovalDirectives
		= new ArrayList<CacheRemovalDirective>();

	public Object around(final ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {
		logger.debug("Method result cache started...");
		final Object target = proceedingJoinPoint.getTarget();
		final String targetMethod = proceedingJoinPoint.getSignature()
				.getName();
		final Object[] arguments = proceedingJoinPoint.getArgs();

		Object methodResult = retrieveCachedMethodResult(target, targetMethod,
				arguments);
		if (methodResult == null) {
			methodResult = proceedingJoinPoint.proceed();
			cacheMethodResult(target, targetMethod, arguments, methodResult);
		}

		//performCacheRemoval(target, targetMethod, arguments);

		logger.debug("Method result cache finished.");
		return methodResult;
	}

	private Object retrieveCachedMethodResult(final Object target,
			final String targetMethod, final Object[] arguments) {
		if (logger.isDebugEnabled()) {
			logger.debug("Retrieving cached result:" + target.getClass()
					+ "." + targetMethod + "(" + arguments + ")");
		}
		for (final CacheDirective cacheDirective : cacheDirectives) {
			final Provider cacheProvider = cacheDirective.getProvider();
			final MethodSignatureKeyGenerator keyGenerator = (MethodSignatureKeyGenerator) cacheDirective
					.getKeyGenerator();
			final Key key = keyGenerator.generateKey(target, targetMethod,
					arguments);
			final Object cacheEntry = cacheProvider.get(key);
			if (cacheEntry != null) {
				if (logger.isDebugEnabled()) {
					logger.debug("Method cache hit:" + target.getClass()
							+ "." + targetMethod + "(" + arguments + ") = "
							+ cacheEntry);
				}
				return cacheEntry;
			}
		}
		return null;
	}

	private void cacheMethodResult(final Object target,
			final String targetMethod, final Object[] arguments,
			final Object methodResult) {
		logger.debug("Caching method result...");
		for (final CacheDirective cacheDirective : cacheDirectives) {
			final Provider cacheProvider = cacheDirective.getProvider();
			final MethodSignatureKeyGenerator keyGenerator = (MethodSignatureKeyGenerator) cacheDirective
					.getKeyGenerator();
			final Key key = keyGenerator.generateKey(target, targetMethod,
					arguments);
			cacheProvider.put(key, methodResult);
			if (logger.isDebugEnabled()) {
				logger.debug("Result cached: " + key + " = " + methodResult);
			}
		}
	}

	@SuppressWarnings("unused")
	private void performCacheRemoval(final Object target,
			final String targetMethod, final Object[] arguments) {
		logger.debug("Removing cache...");
		for (final CacheRemovalDirective cacheRemovalDirective : cacheRemovalDirectives) {
			final Provider cacheProvider = cacheRemovalDirective.getProvider();
			final MethodSignatureKeyGenerator keyGenerator = (MethodSignatureKeyGenerator) cacheRemovalDirective
					.getKeyGenerator();
			final Key key = keyGenerator.generateKey(target, targetMethod,
					arguments);
			cacheProvider.remove(key);
			logger.debug("Cache removed.");
		}
	}

	public void setCacheDirectives(final List<CacheDirective> cacheDirectives) {
		this.cacheDirectives = cacheDirectives;
	}

	public void setCacheRemovalDirectives(
			final List<CacheRemovalDirective> cacheRemovalDirectives) {
		this.cacheRemovalDirectives = cacheRemovalDirectives;
	}
}