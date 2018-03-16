/*
 * <br>Created on 2005-10-26
 * Architect Addler Huang
 * Copyright Addler Organization 2003-2005
 * ����: ����������������Ȩ���͹�ʹ�Լ�ı�����δ����Ȩ���Ը��ƻ�ɢ��������Ĳ��ֻ�ȫ����
 * ���������������º����´���������֪��Υ���߽����跨�ɷ�Χ�ڵ�ȫ���Ʋá�
 */
package com.utils;

import java.io.*;
import java.util.Arrays;
import java.util.Random;

public class ByteArrayHelper {
    final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C',
            'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z' };

    public static String byteArrayToHexString(byte[] byteArrays) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteArrays.length; i++) {
            sb.append(toUnsignedString(byteArrays[i] & 0x00FF, 4, 2));
        }
        return sb.toString();
    }

    public static int byteArrayToInt(byte[] bytes) {
        // byte[] buf = new byte[4];
        int i = 0;
        int intValue = 0;
        // int radix = 1 << 8;
        // int mask = 0xFF;
        do {
            intValue = intValue | (bytes[i] & 0xff) << ((3 - i) * 8);
            i++;
        } while (i <= 3);
        return intValue;
    }

    public static int byteArrayToInt(byte[] bytes, int off, int length) {
        int i = off;
        int intValue = 0;
        do {
            intValue = intValue | (bytes[i] & 0xff) << ((3 - i + off) * 8);
            i++;
        } while (i < length + off);
        return intValue;
    }

    public static long byteArrayToLong(byte[] bytes) {
        // byte[] buf = new byte[4];
        int i = 0;
        long intValue = 0;
        // int radix = 1 << 8;
        // int mask = 0xFF;
        do {
            intValue = intValue | (bytes[i] & (long) 0xff) << ((7 - i) * 8);
            i++;
        } while (i <= 7);
        return intValue;
    }

    public static byte[] hexStringToByteArray(String hexString) throws NumberFormatException {
        if (hexString == null) {
            return null;
        }
        int length = hexString.length() / 2;
        byte[] ret = new byte[length];
        char[] upperString = hexString.toUpperCase().toCharArray();
        for (int i = 0; i < length; i++) {
            byte bh = hexCharToByte(upperString[i * 2]);
            byte bl = hexCharToByte(upperString[i * 2 + 1]);
            ret[i] = (byte) ((0xff & (bh << 4)) | bl);
        }
        return ret;
    }

    public static byte[] intToByteArray(int intValue) {
        byte[] buf = new byte[4];
        int i = 4;
        // int radix = 1 << 8;
        int mask = 0xFF;
        do {
            buf[--i] = (byte) (intValue & mask);
            intValue >>>= 8;
        } while (i != 0);
        return buf;
    }

    public static byte[] longToByteArray(long intValue) {
        byte[] buf = new byte[8];
        int i = 8;
        // int radix = 1 << 8;
        int mask = 0xFF;
        do {
            buf[--i] = (byte) (intValue & mask);
            intValue >>>= 8;
        } while (i != 0);
        return buf;
    }

    public static void main(String[] args) {
        Random r = new Random();
        for (int i = 0; i < 100; i++) {
            long l = r.nextLong();
            byte[] bs = longToByteArray(l);
            long ll = byteArrayToLong(bs);
            if (l != ll) {
                System.out.println("l:" + l + "[" + byteArrayToHexString(bs) + "]" + ll);
            } else {
                System.out.println(l);
            }
        }

    }

    private static byte hexCharToByte(char hexChar) throws NumberFormatException {
        if ((hexChar >= '0') && (hexChar <= '9')) {
            return (byte) (hexChar - '0');
        }
        if ((hexChar >= 'A') && (hexChar <= 'F')) {
            return (byte) (hexChar - 'A' + 10);
        }
        if ((hexChar >= 'a') && (hexChar <= 'f')) {
            return (byte) (hexChar - 'a' + 10);
        }
        throw new NumberFormatException(Character.toString(hexChar));
    }

    private static String toUnsignedString(int i, int shift, int width) {
        char[] buf = new char[8];
        Arrays.fill(buf, digits[0]);
        int charPos = 8;
        int radix = 1 << shift;
        int mask = radix - 1;
        do {
            buf[--charPos] = digits[i & mask];
            i >>>= shift;
        } while (i != 0);

        return new String(buf, Math.min(charPos, 8 - width), Math.max(8 - charPos, width));
    }

    public static Object byte2Object(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            ObjectInputStream inputStream;
            inputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
            Object obj = inputStream.readObject();
            return obj;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] object2Bytes(Object value) {
        if (value == null) {
            return null;
        }
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream outputStream = null;
        try {
            outputStream = new ObjectOutputStream(arrayOutputStream);
            outputStream.writeObject(value);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                arrayOutputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return arrayOutputStream.toByteArray();
    }
}
