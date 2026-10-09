package com.amazonaws.samples.appconfig.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConfigurationKeyTest {

    @Test
    public void testConstructorAndGetters() {
        ConfigurationKey key = new ConfigurationKey("myApp", "prod", "myConfig");
        assertEquals("myApp", key.getApplication());
        assertEquals("prod", key.getEnvironment());
        assertEquals("myConfig", key.getConfiguration());
    }

    @Test
    public void testToStringReturnsCompositeKey() {
        ConfigurationKey key = new ConfigurationKey("myApp", "prod", "myConfig");
        assertEquals("myApp::prod::myConfig", key.toString());
    }

    @Test
    public void testEqualsWithSameObject() {
        ConfigurationKey key = new ConfigurationKey("app", "env", "config");
        assertTrue(key.equals(key));
    }

    @Test
    public void testEqualsWithEqualObjects() {
        ConfigurationKey key1 = new ConfigurationKey("app", "env", "config");
        ConfigurationKey key2 = new ConfigurationKey("app", "env", "config");
        assertTrue(key1.equals(key2));
        assertTrue(key2.equals(key1));
    }

    @Test
    public void testEqualsWithDifferentApplication() {
        ConfigurationKey key1 = new ConfigurationKey("app1", "env", "config");
        ConfigurationKey key2 = new ConfigurationKey("app2", "env", "config");
        assertFalse(key1.equals(key2));
    }

    @Test
    public void testEqualsWithDifferentEnvironment() {
        ConfigurationKey key1 = new ConfigurationKey("app", "env1", "config");
        ConfigurationKey key2 = new ConfigurationKey("app", "env2", "config");
        assertFalse(key1.equals(key2));
    }

    @Test
    public void testEqualsWithDifferentConfiguration() {
        ConfigurationKey key1 = new ConfigurationKey("app", "env", "config1");
        ConfigurationKey key2 = new ConfigurationKey("app", "env", "config2");
        assertFalse(key1.equals(key2));
    }

    @Test
    public void testEqualsWithNull() {
        ConfigurationKey key = new ConfigurationKey("app", "env", "config");
        assertFalse(key.equals(null));
    }

    @Test
    public void testEqualsWithDifferentClass() {
        ConfigurationKey key = new ConfigurationKey("app", "env", "config");
        assertFalse(key.equals("not a key"));
    }

    @Test
    public void testHashCodeConsistency() {
        ConfigurationKey key = new ConfigurationKey("app", "env", "config");
        int hash1 = key.hashCode();
        int hash2 = key.hashCode();
        assertEquals(hash1, hash2);
    }

    @Test
    public void testHashCodeEqualForEqualObjects() {
        ConfigurationKey key1 = new ConfigurationKey("app", "env", "config");
        ConfigurationKey key2 = new ConfigurationKey("app", "env", "config");
        assertEquals(key1.hashCode(), key2.hashCode());
    }
}
