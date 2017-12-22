package com.hyperats.cache.directive;

import com.hyperats.cache.key.KeyGenerator;
import com.hyperats.cache.provider.Provider;

public interface CacheDirective {
	Provider getProvider();

	KeyGenerator getKeyGenerator();
}
