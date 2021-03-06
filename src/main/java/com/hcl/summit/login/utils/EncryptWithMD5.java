package com.hcl.summit.login.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptWithMD5 {
	private static MessageDigest md;

	public static String encode(String pass) throws NoSuchAlgorithmException {
		try {
			md = MessageDigest.getInstance("MD5");
			byte[] passBytes = pass.getBytes();
			md.reset();
			byte[] digested = md.digest(passBytes);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < digested.length; i++) {
				sb.append(Integer.toHexString(0xff & digested[i]));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException ex) {
			System.out.println("Exception in encoding " + ex.getMessage());
			throw ex;
		}

	}
}
