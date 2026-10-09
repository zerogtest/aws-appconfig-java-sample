package com.amazonaws.samples.appconfig.utils;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class SecurityTest {

    @Test
    public void testGetCertificateThrowsForNonExistentFile() {
        Security security = new Security();
        File nonExistentFile = new File("/nonexistent/path/cert.pem");
        try {
            security.getCertificate(nonExistentFile);
            fail("Expected RuntimeException for non-existent file");
        } catch (RuntimeException e) {
            assertNotNull(e.getMessage());
        } catch (Exception e) {
            // CertificateExpiredException or CertificateNotYetValidException
            fail("Expected RuntimeException but got: " + e.getClass().getName());
        }
    }

    @Test
    public void testGetCertificateThrowsForNullFile() {
        Security security = new Security();
        try {
            security.getCertificate(null);
            fail("Expected exception for null file");
        } catch (Exception e) {
            // Expected - NullPointerException or RuntimeException
            assertNotNull(e);
        }
    }

    @Test
    public void testSecurityInstantiation() {
        Security security = new Security();
        assertNotNull(security);
    }
}
