package com.hyperats.cache;

import com.hyperats.cache.manager.CacheService;
import com.hyperats.cache.test.MethodCallBean;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:META-INF/*-resourceconfig.xml")
public class ResourceConfigMethodCachingTest extends MethodCachingTest {
}
