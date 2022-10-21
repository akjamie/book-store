package org.akj.springboot.authorization.security;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class RandomValueStringGenerator {

    private static final char[] DEFAULT_CODEC = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-_"
            .toCharArray();

    private final static Random RANDOM = new SecureRandom();

    public String generate(@NonNull Integer length) {
        byte[] verifierBytes = new byte[length];
        RANDOM.nextBytes(verifierBytes);
        return getAuthorizationCodeString(verifierBytes);
    }

    /**
     * Convert these random bytes to a verifier string. The length of the byte array can be
     *
     * @param verifierBytes The bytes.
     * @return The string.
     */
    protected String getAuthorizationCodeString(byte[] verifierBytes) {
        char[] chars = new char[verifierBytes.length];
        for (int i = 0; i < verifierBytes.length; i++) {
            chars[i] = DEFAULT_CODEC[((verifierBytes[i] & 0xFF) % DEFAULT_CODEC.length)];
        }
        return new String(chars);
    }

}