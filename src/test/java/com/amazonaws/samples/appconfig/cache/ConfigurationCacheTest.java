package com.amazonaws.samples.appconfig.cache;

import com.amazonaws.samples.appconfig.model.ConfigurationKey;
import org.junit.Before;
import org.junit.Test;
import software.amazon.awssdk.services.appconfig.model.GetConfigurationResponse;

import java.time.Duration;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class ConfigurationCacheTest {

    private ConfigurationCache cache;

    @Before
    public void setUp() {
        cache = new ConfigurationCache();
    }

    @Test
    public void testGetReturnsNullForMissingKey() {
        ConfigurationKey key = new ConfigurationKey("app", "env", "config");
        assertNull(cache.get(key));
    }

    @Test
    public void testPutAndGet() {
        ConfigurationKey key = new ConfigurationKey("app", "env", "config");
        ConfigurationCacheItem<GetConfigurationResponse> item = new ConfigurationCacheItem<>(Duration.ofSeconds(30));
        cache.put(key, item);
        assertSame(item, cache.get(key));
    }

    @Test
    public void testPutOverwritesExistingEntry() {
        ConfigurationKey key = new ConfigurationKey("app", "env", "config");
        ConfigurationCacheItem<GetConfigurationResponse> item1 = new ConfigurationCacheItem<>(Duration.ofSeconds(30));
        ConfigurationCacheItem<GetConfigurationResponse> item2 = new ConfigurationCacheItem<>(Duration.ofSeconds(60));
        cache.put(key, item1);
        cache.put(key, item2);
        assertSame(item2, cache.get(key));
    }

    @Test
    public void testEntrySetIsEmptyInitially() {
        Set<Map.Entry<ConfigurationKey, ConfigurationCacheItem<GetConfigurationResponse>>> entries = cache.entrySet();
        assertTrue(entries.isEmpty());
    }

    @Test
    public void testEntrySetReturnsAllEntries() {
        ConfigurationKey key1 = new ConfigurationKey("app1", "env1", "config1");
        ConfigurationKey key2 = new ConfigurationKey("app2", "env2", "config2");
        ConfigurationCacheItem<GetConfigurationResponse> item1 = new ConfigurationCacheItem<>(Duration.ofSeconds(30));
        ConfigurationCacheItem<GetConfigurationResponse> item2 = new ConfigurationCacheItem<>(Duration.ofSeconds(60));
        cache.put(key1, item1);
        cache.put(key2, item2);
        assertEquals(2, cache.entrySet().size());
    }

    @Test
    public void testGetWithDifferentKeysReturnsCorrectItems() {
        ConfigurationKey key1 = new ConfigurationKey("app1", "env1", "config1");
        ConfigurationKey key2 = new ConfigurationKey("app2", "env2", "config2");
        ConfigurationCacheItem<GetConfigurationResponse> item1 = new ConfigurationCacheItem<>(Duration.ofSeconds(30));
        ConfigurationCacheItem<GetConfigurationResponse> item2 = new ConfigurationCacheItem<>(Duration.ofSeconds(60));
        cache.put(key1, item1);
        cache.put(key2, item2);
        assertSame(item1, cache.get(key1));
        assertSame(item2, cache.get(key2));
    }
}
