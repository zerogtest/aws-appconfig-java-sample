package com.amazonaws.samples.appconfig.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class EncoderTest {

    @Test
    public void testEncoderInstantiation() {
        Encoder encoder = new Encoder();
        assertNotNull(encoder);
    }

    @Test
    public void testDefaultDateIsSet() {
        Encoder encoder = new Encoder();
        assertNotNull(encoder.defaultDate);
    }

    @Test
    public void testBytesArrayIsInitialized() {
        Encoder encoder = new Encoder();
        assertNotNull(encoder.bytes);
        assertEquals(57, encoder.bytes.length);
    }

    @Test
    public void testLoggerIsInitialized() {
        Encoder encoder = new Encoder();
        assertNotNull(encoder.logger);
    }
}
