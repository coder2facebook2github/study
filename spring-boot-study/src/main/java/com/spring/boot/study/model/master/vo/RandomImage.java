package com.spring.boot.study.model.master.vo;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

public class RandomImage {
    private String randomCode;// 验证码
    private BufferedImage image;
    private ThreadLocalRandom random = ThreadLocalRandom.current();
    private int codeLength = 5;
    private int width = 130;
    private int height = 36;

    private static String[] signs = new String[]{"++", "+-", "+*", "*+", "*-"};
    private static char[] codeSource = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            '2', '3', '4', '5', '6', '7', '8', '9'};

    public String getRandomCode() {
        return randomCode;
    }

    public BufferedImage getImage() {
        return image;
    }

    public RandomImage() {
        createImage();
    }

    private void createImage() {
        // 在内存中创建图象
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        // 获取图形上下文
        Graphics2D g2 = image.createGraphics();
        // 设定背景色
        g2.setColor(getRandColor(200, 250));
        g2.fillRect(0, 0, width, height);
        // 设定字体
        g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, (int) (height * 0.8)));
        // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
        g2.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g2.drawLine(x, y, x + xl, y + yl);
        }

        // 添加噪点
        float yawpRate = 0.05f;// 噪声率
        int area = (int) (yawpRate * width * height);
        for (int i = 0; i < area; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int rgb = getRandomIntColor();
            image.setRGB(x, y, rgb);
        }

        // 取随机产生的认证码
        char[] randomCode = createRandomCode();
        float x = 5F;
        float y = (float) (height * 0.8);
        for (int i = 0; i < codeLength; i++) {
            // 将认证码显示到图象中
            g2.setColor(getRandomColor());
            g2.drawString(String.valueOf(randomCode[i]), x, y);
            x += (width / codeLength) * (random.nextDouble() * 0.3 + 0.8);
        }
        // 图象生效
        g2.dispose();

        // 赋值验证码
        this.image = image;
    }


    private Color getRandomColor() {
        return new Color(20 + random.nextInt(110), 20 + random
                .nextInt(110), 20 + random.nextInt(110));
    }

    private int getRandomIntColor() {
        int[] rgb = getRandomRgb();
        int color = 0;
        for (int c : rgb) {
            color = color << 8;
            color = color | c;
        }
        return color;
    }

    private int[] getRandomRgb() {
        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++) {
            rgb[i] = random.nextInt(255);
        }
        return rgb;
    }

    /*
     * 给定范围获得随机颜色
     */
    private Color getRandColor(int fc, int bc) {
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    private char[] createRandomCode(int len) {
        char[] chars = new char[len];
        for (int i = 0; i < len; i++) {
            chars[i] = codeSource[random.nextInt(codeSource.length)];
        }
        randomCode = new String(chars);
        return chars;
    }

    private char[] createRandomCode() {
        char[] chars = new char[5];
        String sign = signs[random.nextInt(signs.length)];
        char sign1 = sign.charAt(0);
        char sign2 = sign.charAt(1);
        chars[1] = sign1;
        chars[3] = sign2;
        int a = random.nextInt(9) + 1;
        int b = random.nextInt(9) + 1;
        chars[0] = (char) (a + 48);
        chars[2] = (char) (b + 48);

        int c;
        switch (sign) {
            case "++":
                c = random.nextInt(9) + 1;
                chars[4] = (char) (c + 48);
                randomCode = String.valueOf(a + b + c);
                System.out.println("=============randomCode: " + randomCode);
                break;
            case "+*":
                c = random.nextInt(9) + 1;
                chars[4] = (char) (c + 48);
                randomCode = String.valueOf(a + b * c);
                System.out.println("=============randomCode: " + randomCode);
                break;
            case "+-":
                if ((a + b) >= 9) {
                    c = random.nextInt(9) + 1;
                } else {
                    c = random.nextInt(a + b) + 1;
                }
                chars[4] = (char) (c + 48);
                randomCode = String.valueOf(a + b - c);
                System.out.println("=============randomCode: " + randomCode);
                break;
            case "*+":
                c = random.nextInt(9) + 1;
                chars[4] = (char) (c + 48);
                randomCode = String.valueOf(a * b + c);
                System.out.println("=============randomCode: " + randomCode);
                break;
            case "*-":
                if ((a * b) >= 9) {
                    c = random.nextInt(9) + 1;
                } else {
                    c = random.nextInt(a * b) + 1;
                }
                chars[4] = (char) (c + 48);
                randomCode = String.valueOf(a * b - c);
                System.out.println("=============randomCode: " + randomCode);
                break;
        }
        return chars;
    }
}
