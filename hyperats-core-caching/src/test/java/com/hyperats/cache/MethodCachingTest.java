package com.hyperats.cache;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import javax.annotation.Resource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hyperats.cache.manager.CacheService;
import com.hyperats.cache.test.MethodCallBean;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:META-INF/*-beans.xml")
public class MethodCachingTest {
	private static final Logger logger = LogManager.getLogger(MethodCachingTest.class);
	
	@Resource(name="methodCallBean")
	private MethodCallBean cachedBean;
	private MethodCallBean uncachedBean;
	
	@Resource(name="cacheService")
	private CacheService cacheService;

	@Before
	public void setUp() throws Exception {
		cachedBean.setValue(MethodCallBean.DEFAULT_VALUE);
		uncachedBean = new MethodCallBean();
		uncachedBean.setValue(MethodCallBean.DEFAULT_VALUE);
	}

	@After
	public void tearDown() throws Exception {
		cacheService.clearCache("com.hyperats.methodResultCached");
	}

	@Test
	public void test() {
		logger.trace("Testing method result caching with both cached and uncached.");
		
		String valBefore, valAfter;
		
		logger.debug("Uncached:");
		valBefore = uncachedBean.getValue();
		logger.debug("Before setting: " + valBefore);
		assertEquals(MethodCallBean.DEFAULT_VALUE, valBefore);
		uncachedBean.setValue(MethodCallBean.CHANGED_VALUE);
		valAfter = uncachedBean.getValue();
		logger.debug("After setting: " + valAfter);
		assertEquals(MethodCallBean.CHANGED_VALUE, valAfter);
		
		logger.debug("Cached:");
		valBefore = cachedBean.getValue();
		logger.debug("Before setting: " + valBefore);
		assertEquals(MethodCallBean.DEFAULT_VALUE, valBefore);
		cachedBean.setValue(MethodCallBean.CHANGED_VALUE);
		valAfter = cachedBean.getValue();
		logger.debug("After setting: " + valAfter);
		assertEquals(MethodCallBean.DEFAULT_VALUE, valAfter);
	}
	@Test
	public void testRemoveCache(){
		logger.trace("Testing method result caching with remove cached");
		String valBefore, valAfter;
		valBefore = cachedBean.getValue();
		logger.debug("Before setting: " + valBefore);
		assertEquals(MethodCallBean.DEFAULT_VALUE, valBefore);
		
		logger.debug("RemoveCached:");
		cacheService.clearCache("com.hyperats.methodResultCached");
		
		cachedBean.setValue(MethodCallBean.CHANGED_VALUE);
		valAfter = cachedBean.getValue();
		logger.debug("After setting: " + valAfter);
		assertEquals(MethodCallBean.CHANGED_VALUE, valAfter);
	}
	@Test
	public void testListcacheName(){
		logger.trace("Testing method result caching with list cacheNames");
		Collection<String> cacheNames = cacheService.findCacheNames();
		assertEquals(1,cacheNames.size());
	}
}
