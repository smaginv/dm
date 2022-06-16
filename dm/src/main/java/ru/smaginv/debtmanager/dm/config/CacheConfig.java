package ru.smaginv.debtmanager.dm.config;

import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final Long entries;
    private final Long heapSize;
    private final Long duration;
    private final List<String> values;

    @Autowired
    public CacheConfig(PropertiesConfig propertiesConfig) {
        this.entries = propertiesConfig.cache().getEntries();
        this.heapSize = propertiesConfig.cache().getHeapSize();
        this.duration = propertiesConfig.cache().getDuration();
        this.values = propertiesConfig.cache().getValues();
    }

    @Bean
    public CacheManager ehCacheManager() {
        CachingProvider cachingProvider = Caching.getCachingProvider();
        CacheManager cacheManager = cachingProvider.getCacheManager();
        CacheConfiguration<String, Object> cacheConfiguration = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(
                        String.class,
                        Object.class,
                        ResourcePoolsBuilder.heap(entries).offheap(heapSize, MemoryUnit.MB)
                )
                .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofSeconds(duration)))
                .build();
        values.forEach(cache -> cacheManager.createCache(
                cache, Eh107Configuration.fromEhcacheCacheConfiguration(cacheConfiguration))
        );
        return cacheManager;
    }
}
