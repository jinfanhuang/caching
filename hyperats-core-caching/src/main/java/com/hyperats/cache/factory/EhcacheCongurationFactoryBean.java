package com.hyperats.cache.factory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class EhcacheCongurationFactoryBean implements FactoryBean<Resource>, InitializingBean {
	
	public static final String PATTERN_EHCACHE_PROPERTIES = "classpath*:META-INF/*-ehcache.properties";
	public static final String EHCACHE_DEFAULT_PROPERTIES = "/META-INF/ehcache-default.properties";
	public static final String EHCACHE_TEMPLATE = "/META-INF/ehcache-template.xml";
	
	private String cacheManagerName = "defaultCacheManager";
	private String diskStorePath = System.getProperty("java.io.tmpdir") + File.separator + "defaultCacheManager";
	private String renderedConfigXml;
	
	private void renderConfigurations() throws IOException {
		InputStream defaultis = getClass().getResourceAsStream(EHCACHE_DEFAULT_PROPERTIES);
		Properties defaultProperties = new Properties();
		defaultProperties.load(defaultis);
		
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		List<Properties> lst = new ArrayList<Properties>();
		List<String> cacheNames = new ArrayList<String>();
		for (Resource res : resolver.getResources(PATTERN_EHCACHE_PROPERTIES)) {
			Properties props = new Properties(defaultProperties);
			props.load(res.getInputStream());
			String cacheName = props.getProperty("name");
			if(cacheName != null){
				checkProperties(props);
				lst.add(props);
				cacheNames.add(cacheName);
			}
		}
		//检查是否有重复的cacheName;
		int hashSetSize = new HashSet<String>(cacheNames).size();
		if(cacheNames.size() != hashSetSize){
			throw new RuntimeException();
		}
		render(lst);
	}

	private void checkProperties(Properties props) {
		try {
			String maxElementsInMemory = props.getProperty("maxElementsInMemory");
			String eternal = props.getProperty("eternal");
			String timeToIdleSeconds = props.getProperty("timeToIdleSeconds");
			String timeToLiveSeconds = props.getProperty("timeToLiveSeconds");
			String overflowToDisk = props.getProperty("overflowToDisk");
			String maxElementsOnDisk = props.getProperty("maxElementsOnDisk");
		    String diskPersistent = props.getProperty("diskPersistent");
		    String diskExpiryThreadIntervalSeconds = props.getProperty("diskExpiryThreadIntervalSeconds");
		    String memoryStoreEvictionPolicy = props.getProperty("memoryStoreEvictionPolicy");
			if(maxElementsInMemory != null && !maxElementsInMemory.isEmpty()){
				Integer.parseInt(maxElementsInMemory);
			}
			if(eternal != null && !eternal.isEmpty()){
				if(!(eternal.equals("true") || eternal.equals("false"))){
					throw new RuntimeException();
				}
			}
			if(timeToIdleSeconds != null && !timeToIdleSeconds.isEmpty()){
				Integer.parseInt(timeToIdleSeconds);
			}
			if(timeToLiveSeconds != null && !timeToLiveSeconds.isEmpty()){
				Integer.parseInt(timeToLiveSeconds);				
			}
			if(overflowToDisk != null && !overflowToDisk.isEmpty()){
				if(!(overflowToDisk.equals("true") || overflowToDisk.equals("false"))){
					throw new RuntimeException();
				}
			}
			if(maxElementsOnDisk != null && !maxElementsOnDisk.isEmpty()){
				Integer.parseInt(maxElementsOnDisk);
			}
			if(diskPersistent != null && !diskPersistent.isEmpty()){
				if(!(diskPersistent.equals("true") || diskPersistent.equals("false"))){
					throw new RuntimeException();
				}
			}
			if(diskExpiryThreadIntervalSeconds != null && !diskExpiryThreadIntervalSeconds.isEmpty()){
				Integer.parseInt(diskExpiryThreadIntervalSeconds);
			}
			if(memoryStoreEvictionPolicy != null && !memoryStoreEvictionPolicy.isEmpty()){
				if(!(memoryStoreEvictionPolicy.equals("LRU") || memoryStoreEvictionPolicy.equals("FIFO") || memoryStoreEvictionPolicy.equals("LFU"))){
					throw new RuntimeException();
				}
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
		
		
	}

	private void render(List<Properties> configurations) throws IOException {
		Context ctx = new VelocityContext();
		ctx.put("configurations", configurations);
		ctx.put("cacheManagerName", cacheManagerName);
		ctx.put("diskStorePath", diskStorePath);
		VelocityEngine engine = new VelocityEngine();
		StringWriter w = new StringWriter();
		InputStreamReader r = new InputStreamReader(getClass().getResourceAsStream(
				EHCACHE_TEMPLATE));
		try {
			engine.evaluate(ctx, w, cacheManagerName, r);
			renderedConfigXml = w.toString();
		} finally {
			r.close();
			w.close();
		}
	}

	public void setCacheManagerName(String cacheManagerName) {
		if(cacheManagerName != null && !cacheManagerName.isEmpty()){
			this.cacheManagerName = cacheManagerName;			
		}
	}

	public void setDiskStorePath(String diskStorePath) {
		if(diskStorePath != null && !diskStorePath.isEmpty()){
			File f = new File(diskStorePath);
			if(f.exists()){
				this.diskStorePath = diskStorePath;				
			}
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		renderConfigurations();
	}

	@Override
	public Resource getObject() throws Exception {
		return new ByteArrayResource(renderedConfigXml.getBytes());
	}

	@Override
	public Class<?> getObjectType() {
		return ByteArrayResource.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
