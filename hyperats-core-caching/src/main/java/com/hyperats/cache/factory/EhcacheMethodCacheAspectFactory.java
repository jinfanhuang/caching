package com.hyperats.cache.factory;

import java.util.ArrayList;
import java.util.List;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import com.hyperats.cache.MethodResultCachingAspect;
import com.hyperats.cache.directive.CacheDirective;
import com.hyperats.cache.directive.CacheDirectiveImpl;
import com.hyperats.cache.key.StringMethodSignatureKeyGenerator;
import com.hyperats.cache.provider.EhCacheProvider;

public class EhcacheMethodCacheAspectFactory {
	private EhCacheCacheManager ehcacheCacheManager;
	private StringMethodSignatureKeyGenerator methodResultCacheKeyGenerator;
	
	public MethodResultCachingAspect getAspect(String name) {
		MethodResultCachingAspect aspect = new MethodResultCachingAspect();
		initAspect(aspect, name);
		return aspect;
	}
	
	private void initAspect(MethodResultCachingAspect aspect, String name) {
		Cache cache = ehcacheCacheManager.getCache(name);
		EhCacheProvider provider = new EhCacheProvider((net.sf.ehcache.Cache) cache.getNativeCache());
		
		CacheDirectiveImpl cacheDirective = new CacheDirectiveImpl();
		cacheDirective.setKeyGenerator(methodResultCacheKeyGenerator);
		cacheDirective.setProvider(provider);
		
		List<CacheDirective> cacheDirectives = new ArrayList<CacheDirective>();
		cacheDirectives.add(cacheDirective);
		
		aspect.setCacheDirectives(cacheDirectives);
	}

	public void setEhcacheCacheManager(EhCacheCacheManager ehcacheCacheManager) {
		this.ehcacheCacheManager = ehcacheCacheManager;
	}

	public void setMethodResultCacheKeyGenerator(
			StringMethodSignatureKeyGenerator methodResultCacheKeyGenerator) {
		this.methodResultCacheKeyGenerator = methodResultCacheKeyGenerator;
	}
	
}
