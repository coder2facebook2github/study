package com.utils;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 提供高精度的运算支持. 所以函数以double为参数类型，兼容int与float.
 * 
 */
public class NumberUtils {

    // 默认除法运算精度
    private static final int DEF_DIV_SCALE = 2;
    // 默认乘法倍数,1元 = 100;
    private static final float DEF_MULTIPLICAND_DIVIDEND = (float) 100;

    /**
     * 精确的加法运算.
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }


    /**
     * 除法运算,钱精确到分
     *
     * @param l
     *
     * @return double
     */
    public static double divide2Double(long l){
        return divide(l,DEF_MULTIPLICAND_DIVIDEND);
    }

    /**
     * 提供（相对）精确的除法运算.
     * 
     * @see #divide(double, double, int)
     */
    public static double divide(double v1, double v2) {
        return divide(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算.
     *
     * @see #divide(double, double, int)
     */
    public static double divide(long v1, long v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }

        BigDecimal b1 = new BigDecimal(Long.toString(v1));
        BigDecimal b2 = new BigDecimal(Long.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算. 由scale参数指定精度，以后的数字四舍五入.
     * 
     * @param v1
     *            被除数
     * @param v2
     *            除数
     * @param scale
     *            表示表示需要精确到小数点以后几位
     */
    public static double divide(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }

        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 除法运算,钱精确到分
     * 
     * @param l
     * @return float
     */
    public static float divide(long l) {
        return divide(l, DEF_MULTIPLICAND_DIVIDEND, DEF_DIV_SCALE);
    }

    public static float divide(long v1, float v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Long.toString(v1));
        BigDecimal b2 = new BigDecimal(Float.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * 
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。舍入模式采用ROUND_HALF_EVEN
     * 
     * @param v1
     * @param v2
     * @param scale
     *            表示需要精确到小数点以后几位
     * @return 以字符串格式返回
     */
    public static String divide(String v1, String v2, int scale) {
        return divide(v1, v2, scale, BigDecimal.ROUND_HALF_EVEN);
    }

    public static String divide(String v1, String v2, int scale, int round_mode) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }

        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, scale, round_mode).toString();
    }

    /**
     * 提供精确的乘法运算.返回double类型值
     * 
     * @return double
     */
    public static double multiply(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算，并对运算结果截位.
     * 
     * @param scale
     *            运算结果小数后精确的位数
     */
    public static double multiply(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 乘法运算:float 的 10^6 值
     * 
     * @param f1
     * @return
     */
    public static long multiply(float f1) {
        return (long) multiply(f1, DEF_MULTIPLICAND_DIVIDEND);
    }

    /**
     * 提供精确的乘法运算.返回float类型值
     * 
     * @return double
     */
    public static double multiply(float f1, float f2) {
        BigDecimal b1 = new BigDecimal(Float.toString(f1));
        BigDecimal b2 = new BigDecimal(Float.toString(f2));
        return b1.multiply(b2).doubleValue();
    }

    public static float parseFloat(Object val, float defultValue) {
        if (val == null || StringUtils.isBlank(val.toString())) {
            return defultValue;
        }
        try {
            return Float.parseFloat(val.toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return defultValue;
        }
    }

    public static int parseInt(Object val, int defultValue) {
        if (val == null || StringUtils.isBlank(val.toString())) {
            return defultValue;
        }
        try {
            return Integer.parseInt(val.toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return defultValue;
        }
    }

    public static long parseLong(Object val) {
        return parseLong(val, 0);
    }

    public static long parseLong(Object val, long defultValue) {
        if (val == null || StringUtils.isBlank(val.toString())) {
            return defultValue;
        }
        try {
            return Long.parseLong(val.toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return defultValue;
        }
    }

    /**
     * 提供精确的小数位四舍五入处理.
     * 
     * @param v
     *            需要四舍五入的数字
     * @param scale
     *            小数点后保留几位
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(v);
        return b.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 
     * 精确的减法运算.
     * 
     * @param v1
     *            被减数
     * @param v2
     *            减数
     */
    public static double subtract(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    public static float subtract(float f1, float f2) {
        BigDecimal b1 = new BigDecimal(Float.toString(f1));
        BigDecimal b2 = new BigDecimal(Float.toString(f2));
        BigDecimal result = b1.subtract(b2);
        return result.floatValue();
    }

    /**
     * 保留小数点后某几位小数
     * 
     * @param f
     * @param digitalNum
     *            小数点位数
     * @return
     */
    public static String toDigitalFloat(float f, int digitalNum) {
        StringBuffer format = new StringBuffer("0.");
        for (int i = 0; i < digitalNum; i++) {
            format.append("0");
        }
        DecimalFormat df = new DecimalFormat(format.toString());
        return df.format(f);
    }

    /**
     * 计算比率
     * @param d1 分子
     * @param d2 分母
     * @param scale 保留位数
     * @return
     */
    public static String rate(double d1,double d2,int scale){
        if(d2 > 0){
            return round((d1/d2)*100,scale)+"%";
        }
        return "0%";
    }


    /**
     * 计算比率,默认两位
     * @param d1 分子
     * @param d2 分母
     * @return
     */
    public static String rate(double d1,double d2){
        return rate(d1,d2,DEF_DIV_SCALE);
    }

    /**
     * @param maxRange 随机数最大放大倍数
     * @return
     */
    public static int random(int maxRange) { return (int)(Math.random()*maxRange); }

    /**
     * 提供解决精度问题，去掉小数点最后边的0
     * @param f
     * @return 返回格式化后的字符串值
     */
    public static String stripTrailZeros(Float f){
        return new BigDecimal(String.valueOf(f)).stripTrailingZeros().toPlainString();
    }

    public static String stripTrailZeros(String s){
        return new BigDecimal(s).stripTrailingZeros().toPlainString();
    }

    private NumberUtils() {

    }
}
