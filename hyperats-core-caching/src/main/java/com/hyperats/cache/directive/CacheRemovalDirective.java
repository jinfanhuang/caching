package com.hyperats.cache.directive;

import com.hyperats.cache.key.KeyGenerator;
import com.hyperats.cache.provider.Provider;
 
public interface CacheRemovalDirective {
 
  Provider getProvider();
 
  KeyGenerator getKeyGenerator();
}