package com.shangde.service;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.google.common.io.BaseEncoding;

public class EncryptUtil {

    private final static String DEFAULT_CHARSET = "UTF-8";

    private final static byte[] IV_PARAMETER = {
            0x30, 0x31, 0x30, 0x32, 0x30, 0x33, 0x30, 0x34,
            0x30, 0x35, 0x30, 0x36, 0x30, 0x37, 0x30, 0x38};

    public static String encrypt(String content, String key)
            throws UnsupportedEncodingException, NoSuchPaddingException,
            InvalidKeyException, NoSuchAlgorithmException,
            IllegalBlockSizeException, BadPaddingException,
            InvalidAlgorithmParameterException {
        return base64Encode(aes(content.getBytes(DEFAULT_CHARSET), key.getBytes(DEFAULT_CHARSET), Cipher.ENCRYPT_MODE));
    }

    public static String decrypt(String content, String key)
            throws UnsupportedEncodingException, NoSuchPaddingException,
            InvalidKeyException, NoSuchAlgorithmException,
            IllegalBlockSizeException, BadPaddingException,
            InvalidAlgorithmParameterException {
        return new String(aes(base64Decode(content), key.getBytes(DEFAULT_CHARSET), Cipher.DECRYPT_MODE), DEFAULT_CHARSET);
    }

    private static byte[] aes(byte[] content, byte[] key, int mode)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
    	IvParameterSpec ivParameterSpec = new IvParameterSpec(IV_PARAMETER);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        cipher.init(mode, keySpec, ivParameterSpec);
        return cipher.doFinal(content);
    }

    private static String base64Encode(byte[] content) throws UnsupportedEncodingException {
        return BaseEncoding.base64Url().omitPadding().encode(content);
    }

    private static byte[] base64Decode(String content) throws UnsupportedEncodingException {
        return BaseEncoding.base64Url().omitPadding().decode(content);
    }

    public static void main(String[] args) throws Exception{}

}
