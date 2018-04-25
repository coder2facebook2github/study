package com.github;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ZxingStudy {


    /**
     * 生成二维码
     * @param content
     * @param needReturn
     * @return
     */
    public BufferedImage createBarcodeImage(String content, boolean needReturn) {
        String filePath = "D:/create/image";
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "_"
                + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + ".jpg";
        int width = 300;
        int height = 300;
        Map<EncodeHintType, Object> hintTypeObjectMap = new HashMap<>();
        hintTypeObjectMap.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.displayName());
        Path path = FileSystems.getDefault().getPath(filePath, fileName);
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hintTypeObjectMap);
            if (!needReturn) {
                MatrixToImageWriter.writeToPath(bitMatrix, "jpg", path);
                return null;
            } else {
                BufferedImage image = new BufferedImage(width, height,
                        BufferedImage.TYPE_INT_RGB);
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000
                                : 0xFFFFFFFF);
                    }
                }
//                ImageIO.write(image, "jpg", path.toFile());
                return image;
            }

        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void createFile() {
        String content = "https://u.wechat.com/EJEoENXVXS1Kkg2Zt1gG7HM";
        createBarcodeImage(content, false);
    }

    /**
     * 解析二维码
     * @param file
     * @return
     */
    public String parseBarcode(File file) {
        BufferedImage image;
        String content = null;
        try {
            image = ImageIO.read(file);
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            Map<DecodeHintType, Object> hints = new HashMap<>();
            hints.put(DecodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.displayName());
            Result result = new MultiFormatReader().decode(binaryBitmap, hints);
            content = result.getText();
        } catch (IOException | NotFoundException e) {
            e.printStackTrace();
        }
        return content;
    }

    @Test
    public void readBarcode() {
        File file = new File("D:\\create\\image\\self.jpg");
//        File file = new File("D:\\create\\image\\dingding.png");
        String content = parseBarcode(file);
        System.out.println(content);
    }

    /**
     * 生成带logo二维码
     * @param logoFile
     * @param content
     * @throws IOException
     */
    public void createBarcodeWithLogo(File logoFile, String content) throws IOException {
        Image logoImage = ImageIO.read(logoFile);
        int width = 40;
        int height = 40;
        Image tempImage = logoImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = tag.createGraphics();
        graphics.drawImage(tempImage, 0, 0, null);
        graphics.dispose();

        BufferedImage barcodeImage = createBarcodeImage(content, true);
        Graphics2D graphics2D = barcodeImage.createGraphics();
        int x = (300 - width) / 2;
        int y = (300 - height) / 2;
        graphics2D.drawImage(tag, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, height, 6, 6);
        graphics2D.setStroke(new BasicStroke(3F));
        graphics2D.draw(shape);
        graphics2D.dispose();
        ImageIO.write(barcodeImage, "jpg", new File("D:\\create\\image\\" + "123_logo.jpg"));
    }

    @Test
    public void logoBarcode() throws IOException {
        File logoFile = new File("D:\\create\\image\\logo.jpg");
//        String content = "https://blog.csdn.net/shenfuli/article/details/68923393";
        String content = "https://u.wechat.com/EJEoENXVXS1Kkg2Zt1gG7HM";
        createBarcodeWithLogo(logoFile, content);
    }

}
