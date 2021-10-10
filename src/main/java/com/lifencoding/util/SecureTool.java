package com.lifencoding.util;

import java.security.MessageDigest;
import java.security.SecureRandom;

public class SecureTool {
	private static final int STRETCH_SIZE = 7;
	private static final int SALT_SIZE = 8;

	private String byteToString(byte[] digest) {
        StringBuilder sb = new StringBuilder();

        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

	public String encrypt(String password,String salt) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-256");

		for (int i = 0; i < STRETCH_SIZE; i++) {
			String temp = password + salt;
			md.update(temp.getBytes());
			password = byteToString(md.digest());
		}

		return password;
	}

	public String createSalt() {
		return makeRandomString(SALT_SIZE);
	}

	public String makeRandomString(int length) {
		SecureRandom secureRandom = new SecureRandom();
		byte[] temp = new byte[length];

		secureRandom.nextBytes(temp);

		return byteToString(temp);
	}
}
