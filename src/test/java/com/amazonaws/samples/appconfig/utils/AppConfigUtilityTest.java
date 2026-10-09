package com.amazonaws.samples.appconfig.utils;

import com.amazonaws.samples.appconfig.cache.ConfigurationCache;
import com.amazonaws.samples.appconfig.cache.ConfigurationCacheItem;
import com.amazonaws.samples.appconfig.model.ConfigurationKey;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.appconfig.AppConfigClient;
import software.amazon.awssdk.services.appconfig.model.GetConfigurationRequest;
import software.amazon.awssdk.services.appconfig.model.GetConfigurationResponse;

import java.time.Duration;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AppConfigUtilityTest {

    @Mock
    private AppConfigClient mockClient;

    @Mock
    private ConfigurationCache mockCache;

    private AppConfigUtility utility;
    private ConfigurationKey testKey;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        utility = new AppConfigUtility(mockClient, mockCache, Duration.ofSeconds(30), "test-client-id");
        testKey = new ConfigurationKey("testApp", "testEnv", "testConfig");
    }

    @Test
    public void testGetConfigurationWhenCacheMiss() {
        when(mockCache.get(testKey)).thenReturn(null);

        GetConfigurationResponse mockResponse = GetConfigurationResponse.builder()
                .content(SdkBytes.fromUtf8String("{\"key\":\"value\"}"))
                .configurationVersion("1")
                .build();
        when(mockClient.getConfiguration(any(GetConfigurationRequest.class))).thenReturn(mockResponse);

        GetConfigurationResponse result = utility.getConfiguration(testKey);

        assertNotNull(result);
        verify(mockClient).getConfiguration(any(GetConfigurationRequest.class));
        verify(mockCache).put(eq(testKey), any(ConfigurationCacheItem.class));
    }

    @Test
    public void testGetConfigurationWhenCacheHitAndRefreshNotNeeded() {
        ConfigurationCacheItem<GetConfigurationResponse> cachedItem = mock(ConfigurationCacheItem.class);
        GetConfigurationResponse cachedResponse = GetConfigurationResponse.builder()
                .content(SdkBytes.fromUtf8String("{\"cached\":true}"))
                .configurationVersion("1")
                .build();
        when(cachedItem.isRefreshNeeded()).thenReturn(false);
        when(cachedItem.getValue()).thenReturn(cachedResponse);
        when(mockCache.get(testKey)).thenReturn(cachedItem);

        GetConfigurationResponse result = utility.getConfiguration(testKey);

        assertNotNull(result);
        assertEquals(cachedResponse, result);
        verify(mockClient, never()).getConfiguration(any(GetConfigurationRequest.class));
    }

    @Test
    public void testGetConfigurationWhenCacheHitAndRefreshNeeded() {
        ConfigurationCacheItem<GetConfigurationResponse> cachedItem = mock(ConfigurationCacheItem.class);
        GetConfigurationResponse cachedResponse = GetConfigurationResponse.builder()
                .content(SdkBytes.fromUtf8String("{\"cached\":true}"))
                .configurationVersion("1")
                .build();
        when(cachedItem.isRefreshNeeded()).thenReturn(true);
        when(cachedItem.getValue()).thenReturn(cachedResponse);
        when(mockCache.get(testKey)).thenReturn(cachedItem);

        GetConfigurationResponse freshResponse = GetConfigurationResponse.builder()
                .content(SdkBytes.fromUtf8String("{\"fresh\":true}"))
                .configurationVersion("2")
                .build();
        when(mockClient.getConfiguration(any(GetConfigurationRequest.class))).thenReturn(freshResponse);

        GetConfigurationResponse result = utility.getConfiguration(testKey);

        assertNotNull(result);
        verify(mockClient).getConfiguration(any(GetConfigurationRequest.class));
    }

    @Test(expected = RuntimeException.class)
    public void testGetConfigurationThrowsWhenValueNullAndExceptionPresent() {
        when(mockCache.get(testKey)).thenReturn(null);
        when(mockClient.getConfiguration(any(GetConfigurationRequest.class)))
                .thenThrow(new RuntimeException("API error"));

        utility.getConfiguration(testKey);
    }

    @Test
    public void testGetConfigurationFromApiAndApplyCacheWithNullExistingItem() {
        GetConfigurationResponse mockResponse = GetConfigurationResponse.builder()
                .content(SdkBytes.fromUtf8String("{\"key\":\"value\"}"))
                .configurationVersion("1")
                .build();
        when(mockClient.getConfiguration(any(GetConfigurationRequest.class))).thenReturn(mockResponse);

        ConfigurationCacheItem<GetConfigurationResponse> result =
                utility.getConfigurationFromApiAndApplyToCache(testKey, null, null);

        assertNotNull(result);
        assertNotNull(result.getValue());
        verify(mockCache).put(eq(testKey), eq(result));
    }

    @Test
    public void testGetConfigurationFromApiAndApplyCacheWithExceptionAndNullExistingItem() {
        when(mockClient.getConfiguration(any(GetConfigurationRequest.class)))
                .thenThrow(new RuntimeException("connection error"));

        ConfigurationCacheItem<GetConfigurationResponse> result =
                utility.getConfigurationFromApiAndApplyToCache(testKey, null, null);

        assertNotNull(result);
        assertNull(result.getValue());
        assertNotNull(result.getException());
        verify(mockCache).put(eq(testKey), eq(result));
    }

    @Test(expected = RuntimeException.class)
    public void testUpdateConfigurationThrowsOnError() {
        when(mockClient.updateConfigurationProfile(any(software.amazon.awssdk.services.appconfig.model.UpdateConfigurationProfileRequest.class)))
                .thenThrow(new RuntimeException("update failed"));

        utility.updateConfiguration(testKey, "content");
    }
}
