package com.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CodeHelper {
    private static char[] password = "uLi4/f4+Pb39.T19".toCharArray();

    private static int ITERATIONS = 1000;
    private static Cipher sealEncryptCipher = null;
    private static Cipher sealDecryptCipher = null;
    private static SecretKey sealKey = null;
    private static MessageDigest md5 = null;
    private static byte[] salt = { 29, 14, -23, 56, 78, 19, -1, 78 };

    private static final String PROVIDER = "BC";

    private static final String SEAL_ALGORITHM = "PBEWITHMD5ANDDES";

    public static String fromBase64(String s) {
        if (s == null) {
            return null;
        }
        return new String(Base64.decodeBase64(s));
    }

    public static String getURLContent(String url) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        java.io.InputStream l_urlStream;

        try {
            java.net.URL l_url = new java.net.URL(url);
            java.net.HttpURLConnection l_connection = (java.net.HttpURLConnection) l_url
                    .openConnection();
            l_connection.connect();
            l_urlStream = l_connection.getInputStream();
            byte[] bs = new byte[1024];
            int size = 0;
            while ((size = l_urlStream.read(bs, 0, 1024)) >= 0) {
                baos.write(bs, 0, size);
            }
            l_urlStream.close();
            baos.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new String(baos.toByteArray());
    }

    public static void initSeal() throws Exception {
        PBEKeySpec keySpec = new PBEKeySpec(password);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(SEAL_ALGORITHM, PROVIDER);
        sealKey = keyFactory.generateSecret(keySpec);
        PBEParameterSpec paramSpec = new PBEParameterSpec(salt, ITERATIONS);
        sealEncryptCipher = Cipher.getInstance(SEAL_ALGORITHM, PROVIDER);
        sealEncryptCipher.init(Cipher.ENCRYPT_MODE, sealKey, paramSpec);
        sealDecryptCipher = Cipher.getInstance(SEAL_ALGORITHM, PROVIDER);
        sealDecryptCipher.init(Cipher.DECRYPT_MODE, sealKey, paramSpec);
    }


    public synchronized static String md5(byte[] in) {
        try {
            if (md5 == null) {
                md5 = MessageDigest.getInstance("MD5");
            }
            md5.update(in);
            byte[] theDigest = md5.digest();
            if (theDigest == null) {
                return null;
            }
            return ByteArrayHelper.byteArrayToHexString(theDigest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String toBase64(String s) {
        if (s == null) {
            return null;
        }
        return new String(Base64.encodeBase64URLSafe(s.getBytes()));
    }

}
