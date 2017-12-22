package com.hyperats.cache;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.HashSet;

import javax.annotation.Resource;

import net.sf.ehcache.config.CacheConfiguration;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.cache.Cache;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hyperats.cache.manager.CacheService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:META_INF/*-ehcacheconfig.xml","classpath*:META-INF/*-beans.xml"})
public class ResourceConfigTest {

	private static final Logger logger = Logger.getLogger(ResourceConfigTest.class);
	@Resource(name="cacheService")
	private CacheService cacheService;
	
	@Test
	public void test(){
		logger.trace("Testing caching size with resource config");
		Collection<String> cacheNames = cacheService.findCacheNames();
		logger.debug("cache size :" + cacheNames.size());
		assertEquals(2, cacheNames.size());
		
		logger.trace("check duplicate cache name");
		int hashSetSize = new HashSet<String>(cacheNames).size();
		logger.debug("cache hashSetSize : " + hashSetSize);
		assertEquals(cacheNames.size(),hashSetSize);
		
		logger.trace("cache properties");
		for(String cn : cacheNames){
			Cache cache = cacheService.findCacheByName(cn);
			net.sf.ehcache.Cache c = (net.sf.ehcache.Cache) cache.getNativeCache();
			CacheConfiguration cc = c.getCacheConfiguration();
			logger.debug("cache "+cn+" of MaxElementsInMemory value : "+ cc.getMaxElementsInMemory());
			logger.debug("cache "+cn+" of Eternal value : "+ cc.isEternal());
			logger.debug("cache "+cn+" of IsOverflowToDisk value : "+ cc.isOverflowToDisk());
			logger.debug("cache "+cn+" of TimeToIdleSeconds value : "+ cc.getTimeToIdleSeconds());
			logger.debug("cache "+cn+" of TimeToLiveSeconds value : "+ cc.getTimeToLiveSeconds());
		}
	}
}
