package tech.kerok.portfolio.rps.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashService {
    public static String createMD5Hash(String input) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(input.getBytes());
        return convertToHex(messageDigest);
    }

    private static String convertToHex(byte[] messageDigest) {
        BigInteger bigInteger = new BigInteger(1, messageDigest);
        String hexText = bigInteger.toString(16);
        while (hexText.length() < 32) {
            hexText = "0".concat(hexText);
        }
        return hexText;
    }

    public static boolean verify(String hash, String finalHash) {
        return hash.contentEquals(finalHash);
    }
}
