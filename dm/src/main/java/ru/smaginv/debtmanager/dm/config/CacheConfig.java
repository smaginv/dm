package ru.smaginv.debtmanager.dm.config;

import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.time.Duration;
import java.util.List;

@EnableCaching
@Configuration
public class CacheConfig {

    @Bean
    public CacheManager ehCacheManager() {
        List<String> caches = List.of("accounts", "contacts", "people");
        CachingProvider cachingProvider = Caching.getCachingProvider();
        CacheManager cacheManager = cachingProvider.getCacheManager();
        CacheConfiguration<String, Object> cacheConfiguration = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(
                        String.class,
                        Object.class,
                        ResourcePoolsBuilder.heap(5000).offheap(5, MemoryUnit.MB)
                )
                .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofSeconds(3600)))
                .build();
        caches.forEach(cache -> cacheManager.createCache(
                cache, Eh107Configuration.fromEhcacheCacheConfiguration(cacheConfiguration))
        );
        return cacheManager;
    }
}
