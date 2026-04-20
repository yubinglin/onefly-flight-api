package com.onefly.flight.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class AesUtil {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final byte[] ZERO_IV = new byte[16];

    private AesUtil() {
    }

    public static String encrypt(String content, String key) {
        try {
            byte[] raw = key.getBytes(StandardCharsets.UTF_8);
            if (raw.length != 16) {
                throw new IllegalArgumentException(
                        "Invalid key size. Key '" + key + "', token length is not 16 bytes");
            }
            SecretKeySpec keySpec = new SecretKeySpec(raw, ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(ZERO_IV));
            byte[] encrypted = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("AES encryption failed", e);
        }
    }

    public static String decrypt(String content, String key) {
        try {
            byte[] raw = key.getBytes(StandardCharsets.UTF_8);
            if (raw.length != 16) {
                throw new IllegalArgumentException(
                        "Invalid key size. Key '" + key + "', token length is not 16 bytes");
            }
            SecretKeySpec keySpec = new SecretKeySpec(raw, ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(ZERO_IV));
            byte[] toDecrypt = Base64.getDecoder().decode(content);
            byte[] original = cipher.doFinal(toDecrypt);
            return new String(original, StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("AES decryption failed", e);
        }
    }
}
