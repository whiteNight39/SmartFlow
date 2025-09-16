package com.whitenight.smartflow.utils;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

public class TokenGenerator {

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding();

    // Strong secure token
    public static String generateSecureToken(int byteLength) {
        byte[] tokenBytes = new byte[byteLength];
        secureRandom.nextBytes(tokenBytes);
        return base64Encoder.encodeToString(tokenBytes);
    }

    // Simpler UUID token
    public static String generateUUIDToken() {
        return UUID.randomUUID().toString();
    }
}