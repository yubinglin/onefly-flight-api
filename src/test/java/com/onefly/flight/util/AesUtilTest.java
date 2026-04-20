package com.onefly.flight.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AesUtilTest {

    private static final String TEST_KEY = "UQVKPI5ATFNQQIA5";

    @Test
    void encrypt_andDecrypt_shouldReturnOriginal() {
        String original = "abcdefghigklmnopqrstuvwxyz0123456789";
        String encrypted = AesUtil.encrypt(original, TEST_KEY);
        assertNotNull(encrypted);
        assertNotEquals(original, encrypted);

        String decrypted = AesUtil.decrypt(encrypted, TEST_KEY);
        assertEquals(original, decrypted);
    }

    @Test
    void encrypt_withKnownSample_shouldMatch() {
        String original = "abcdefghigklmnopqrstuvwxyz0123456789";
        String encrypted = AesUtil.encrypt(original, TEST_KEY);
        String expected = "ltCT+VhBg9eTVaGW4Z6sy3+jDlhW+BxO5c6hMbl049AATwmoda6OimDgtwnCdTn5";
        assertEquals(expected, encrypted);
    }

    @Test
    void decrypt_withKnownSample_shouldMatch() {
        String encrypted = "ltCT+VhBg9eTVaGW4Z6sy3+jDlhW+BxO5c6hMbl049AATwmoda6OimDgtwnCdTn5";
        String decrypted = AesUtil.decrypt(encrypted, TEST_KEY);
        assertEquals("abcdefghigklmnopqrstuvwxyz0123456789", decrypted);
    }

    @Test
    void encrypt_withInvalidKeyLength_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () ->
                AesUtil.encrypt("test", "short"));
    }

    @Test
    void decrypt_withInvalidKeyLength_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () ->
                AesUtil.decrypt("test", "short"));
    }

    @Test
    void encrypt_withEmptyString_shouldWork() {
        String encrypted = AesUtil.encrypt("", TEST_KEY);
        assertNotNull(encrypted);
        String decrypted = AesUtil.decrypt(encrypted, TEST_KEY);
        assertEquals("", decrypted);
    }

    @Test
    void encrypt_withJsonContent_shouldWork() {
        String json = "{\"cid\":\"test\",\"tripType\":\"1\",\"adultNum\":1}";
        String encrypted = AesUtil.encrypt(json, TEST_KEY);
        String decrypted = AesUtil.decrypt(encrypted, TEST_KEY);
        assertEquals(json, decrypted);
    }
}
