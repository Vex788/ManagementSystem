package com.zeus.inc.afinams.configs;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Cache Configuration for Performance Optimization
 * Caches frequently accessed data like trainers, subscriptions, and clients
 */
@Configuration
@EnableCaching
public class CacheConfig {

    /**
     * Configure cache manager with specific cache names
     */
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(
                "trainers",
                "clients",
                "subscriptions",
                "schedules",
                "payment_history",
                "user_details",
                "statistics",
                "analytics"
        );
    }
}
