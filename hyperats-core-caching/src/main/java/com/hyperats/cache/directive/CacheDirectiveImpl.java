package com.hyperats.cache.directive;

import com.hyperats.cache.key.KeyGenerator;
import com.hyperats.cache.provider.Provider;
 
public class CacheDirectiveImpl implements CacheDirective {
 
  private Provider provider;
 
  private KeyGenerator keyGenerator;
 
  public Provider getProvider() {
    return provider;
  }
 
  public void setProvider(final Provider provider) {
    this.provider = provider;
  }
 
  public KeyGenerator getKeyGenerator() {
    return keyGenerator;
  }
 
  public void setKeyGenerator(final KeyGenerator keyGenerator) {
    this.keyGenerator = keyGenerator;
  }
}