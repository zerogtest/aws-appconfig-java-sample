package com.amazonaws.samples.appconfig.cache;

import org.junit.Test;
import software.amazon.awssdk.services.appconfig.model.BadRequestException;
import software.amazon.awssdk.services.appconfig.model.ResourceNotFoundException;

import java.time.Duration;

import static org.junit.Assert.*;

public class ConfigurationCacheItemTest {

    @Test
    public void testConstructorSetsTtl() {
        Duration ttl = Duration.ofSeconds(30);
        ConfigurationCacheItem<String> item = new ConfigurationCacheItem<>(ttl);
        assertEquals(ttl, item.getTtl());
    }

    @Test
    public void testSetAndGetValue() {
        ConfigurationCacheItem<String> item = new ConfigurationCacheItem<>(Duration.ofSeconds(30));
        assertNull(item.getValue());
        item.setValue("testValue");
        assertEquals("testValue", item.getValue());
    }

    @Test
    public void testSetAndGetException() {
        ConfigurationCacheItem<String> item = new ConfigurationCacheItem<>(Duration.ofSeconds(30));
        assertNull(item.getException());
        RuntimeException ex = new RuntimeException("test error");
        item.setException(ex);
        assertEquals(ex, item.getException());
    }

    @Test
    public void testSetAndGetRefreshTime() {
        ConfigurationCacheItem<String> item = new ConfigurationCacheItem<>(Duration.ofSeconds(30));
        long refreshTime = 123456789L;
        item.setRefreshTime(refreshTime);
        assertEquals(refreshTime, item.getRefreshTime());
    }

    @Test
    public void testIsRefreshNeededWhenTimeHasPassed() {
        ConfigurationCacheItem<String> item = new ConfigurationCacheItem<>(Duration.ofSeconds(30));
        item.setRefreshTime(0L);
        assertTrue(item.isRefreshNeeded());
    }

    @Test
    public void testIsRefreshNeededWhenTimeHasNotPassed() {
        ConfigurationCacheItem<String> item = new ConfigurationCacheItem<>(Duration.ofSeconds(30));
        item.setRefreshTime(Long.MAX_VALUE);
        assertFalse(item.isRefreshNeeded());
    }

    @Test
    public void testIsCacheableExceptionTypeWithResourceNotFoundException() {
        ResourceNotFoundException ex = ResourceNotFoundException.builder().build();
        assertTrue(ConfigurationCacheItem.isCacheableExceptionType(ex));
    }

    @Test
    public void testIsCacheableExceptionTypeWithBadRequestException() {
        BadRequestException ex = BadRequestException.builder().build();
        assertTrue(ConfigurationCacheItem.isCacheableExceptionType(ex));
    }

    @Test
    public void testIsCacheableExceptionTypeWithOtherException() {
        RuntimeException ex = new RuntimeException("other");
        assertFalse(ConfigurationCacheItem.isCacheableExceptionType(ex));
    }

    @Test
    public void testCalculateAndSetRefreshTimeWithNonCacheableException() {
        ConfigurationCacheItem<String> item = new ConfigurationCacheItem<>(Duration.ofSeconds(60));
        item.setException(new RuntimeException("non-cacheable"));
        long beforeRefresh = item.getRefreshTime();
        item.calculateAndSetRefreshTime();
        // Should set refresh time to now (immediate refresh)
        assertTrue(item.getRefreshTime() <= System.currentTimeMillis());
    }

    @Test
    public void testCalculateAndSetRefreshTimeWithCacheableException() {
        ConfigurationCacheItem<String> item = new ConfigurationCacheItem<>(Duration.ofSeconds(60));
        item.setException(ResourceNotFoundException.builder().build());
        item.calculateAndSetRefreshTime();
        // Should set refresh time to now + ttl
        assertTrue(item.getRefreshTime() > System.currentTimeMillis());
    }

    @Test
    public void testCalculateAndSetRefreshTimeWithNoException() {
        ConfigurationCacheItem<String> item = new ConfigurationCacheItem<>(Duration.ofSeconds(60));
        item.calculateAndSetRefreshTime();
        // No exception means refresh time = now + ttl
        assertTrue(item.getRefreshTime() > System.currentTimeMillis());
    }

    @Test
    public void testEqualsWithSameObject() {
        ConfigurationCacheItem<String> item = new ConfigurationCacheItem<>(Duration.ofSeconds(30));
        assertTrue(item.equals(item));
    }

    @Test
    public void testEqualsWithNull() {
        ConfigurationCacheItem<String> item = new ConfigurationCacheItem<>(Duration.ofSeconds(30));
        assertFalse(item.equals(null));
    }

    @Test
    public void testEqualsWithDifferentClass() {
        ConfigurationCacheItem<String> item = new ConfigurationCacheItem<>(Duration.ofSeconds(30));
        assertFalse(item.equals("a string"));
    }

    @Test
    public void testHashCodeConsistency() {
        ConfigurationCacheItem<String> item = new ConfigurationCacheItem<>(Duration.ofSeconds(30));
        item.setValue("test");
        int hash1 = item.hashCode();
        int hash2 = item.hashCode();
        assertEquals(hash1, hash2);
    }

    @Test
    public void testToString() {
        ConfigurationCacheItem<String> item = new ConfigurationCacheItem<>(Duration.ofSeconds(30));
        item.setValue("testVal");
        String result = item.toString();
        assertTrue(result.contains("ConfigurationCacheItem"));
        assertTrue(result.contains("testVal"));
    }
}
