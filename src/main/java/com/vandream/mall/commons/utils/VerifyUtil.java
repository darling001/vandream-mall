package com.vandream.mall.commons.utils;

import com.vandream.mall.business.vo.jigsaw.CutResultData;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Random;
import javax.imageio.ImageIO;
import sun.misc.BASE64Encoder;

public class VerifyUtil {

    private static double LEN_PER = 0.17;

    private static CutData getBlockData(Integer x, Integer y) {

        int[][] data = new int[x][y];
        CutData cd = new CutData();
        // 截图宽
        double tarWidth = Math.ceil(y * LEN_PER);
        double radius = Math.ceil(tarWidth / 6);

        // 截图正方形顶点坐标 ***/
        double tarTopX = Math.ceil((Math.random() + 0.1) * (x - tarWidth * 2));
        double tarTopY = Math.ceil((Math.random() + 0.1) * (y - tarWidth * 2));

        // 上突出或左突出 >0 上突出 =0左突出
        Random r = new Random();
        int tOrL = r.nextInt(2);

        double topX = tOrL == 0 ? tarTopX - radius : tarTopX;
        double topY = tOrL == 0 ? tarTopY : tarTopY - radius;
        double cutWidth = tOrL == 0 ? tarWidth + radius + 1 : tarWidth + 1;
        double cutHeight = tOrL == 0 ? tarWidth + 1 : tarWidth + radius + 1;

        cd.setCutPoin(new CutPoin((int) topX, (int) topY));
        cd.setCutWidth((int) cutWidth);
        cd.setCutHeight((int) cutHeight);

        // 左边圆圆点左标 ***/
        double lh = tarTopX;
        double lw = tarTopY + tarWidth / 2;


        // 上边圆圆点左标 ***/
        double th = tarTopX + tarWidth / 2;
        double tw = tarTopY;


        for (int cx = 0; cx < x; cx++) {
            for (int cy = 0; cy < y; cy++) {

                double llen = Math.sqrt(Math.abs((cx - lh) * (cx - lh) + (cy - lw) * (cy - lw)));
                double tlen = Math.sqrt(Math.abs((cx - th) * (cx - th) + (cy - tw) * (cy - tw)));
                if (cx >= tarTopX && cx <= (tarTopX + tarWidth) && cy >= tarTopY && cy <=
                        (tarTopY + tarWidth)) {
                    data[cx][cy] = 1;
                }

                if (tlen <= radius + 0.3) {
                    data[cx][cy] = tOrL == 0 ? 0 : 1;
                }

                if (llen <= radius + 0.3) {
                    data[cx][cy] = tOrL == 0 ? 1 : 0;
                }
            }
        }
        cd.setData(data);
        return cd;

    }


    private static BufferedImage cutByTemplate(BufferedImage oriImage, BufferedImage targetImage,
                                               int[][] templateImage, int x, int y) {

        int oriX = oriImage.getWidth();
        int oriY = oriImage.getHeight();

        BufferedImage newImage = new BufferedImage(oriX, oriY, oriImage.getType());
        for (int i = 0; i < oriX; i++) {
            for (int j = 0; j < oriY; j++) {
                int oriRGB = oriImage.getRGB(i, j);
                int rgb = templateImage[i][j];
                // 原图中对应位置变色处理
//                try {
                if (rgb == 1) {
                        targetImage.setRGB(i - x, j - y, oriRGB);
                    if (templateImage[i - 1][j] == 0 || templateImage[i + 1][j] == 0 ||
                            templateImage[i][j - 1] == 0 || templateImage[i][j + 1] == 0) {
                        newImage.setRGB(i, j, oriRGB);
                    } else {
                        //int r = (0xff & oriRGB);
                        //int g = (0xff & (oriRGB >> 8));
                        //int b = (0xff & (oriRGB >> 16));
                        int r = 50;
                        int g = 50;
                        int b = 50;
                        newImage.setRGB(i, j, r + (g << 8) + (b << 16) + (200 << 24));
                    }
                } else {
                    newImage.setRGB(i, j, oriRGB);
                }


            }
        }
        return newImage;
    }

    private static String bufferImgTOBase64(BufferedImage bimg) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bimg, "png", outputStream);
        BASE64Encoder encoder = new BASE64Encoder();
        String base64Img = encoder.encode(outputStream.toByteArray());
        return base64Img;
    }

    public static CutResultData drawImgVerify(InputStream imageStream) throws Exception {

        BufferedImage srcImage = ImageIO.read(imageStream);
        CutData cd = VerifyUtil.getBlockData(srcImage.getWidth(), srcImage.getHeight());
        BufferedImage drawImage = new BufferedImage(cd.getCutWidth(), cd.getCutHeight(), srcImage
                .getType());

        System.out.println("width:" + cd.cutWidth + " height:" + cd.getCutHeight() + " X: " + cd
                .getCutPoin().getX() + " Y:" + cd.getCutPoin().getY());


        BufferedImage targetImg = VerifyUtil.cutByTemplate(srcImage, drawImage, cd.getData(), cd
                .getCutPoin().getX(), cd.getCutPoin().getY());

        Graphics2D graphics2d = drawImage.createGraphics();
        graphics2d.getDeviceConfiguration().createCompatibleImage(drawImage.getWidth(), drawImage
                .getHeight(), Transparency.TRANSLUCENT);
        graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints
                .VALUE_ANTIALIAS_ON);
        graphics2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);
        graphics2d.drawImage(drawImage.getScaledInstance(drawImage.getWidth() + 1, drawImage
                .getHeight() + 1, Image.SCALE_SMOOTH), 0, 0, null);

        graphics2d.dispose();
        CutResultData crd = new CutResultData();


        crd.setDrawImg(bufferImgTOBase64(drawImage));
        crd.setTarImg(bufferImgTOBase64(targetImg));
        crd.setPointX(cd.getCutPoin().getX());
        crd.setPointY(cd.getCutPoin().getY());
        crd.setSrcWidth(cd.getSrcX());
        crd.setSrcHeight(cd.getSrcY());

        return crd;
    }

    static class CutData {


        /**
         * 原图高
         */
        private int srcY;
        /**
         * 原图宽
         */
        private int srcX;
        /**
         * 切图宽度
         */
        private int cutWidth;
        /**
         * 切图高度
         */
        private int cutHeight;
        /**
         * 切图顶点位置
         */
        private CutPoin cutPoin;
        /**
         * 截图数据
         */
        private int[][] data;


        public void setCutWidth(int cutWidth) {
            this.cutWidth = cutWidth;
        }

        public int getCutWidth() {
            return this.cutWidth;
        }

        public int getSrcY() {
            return srcY;
        }

        public void setSrcY(int srcY) {
            this.srcY = srcY;
        }

        public int getSrcX() {
            return srcX;
        }

        public void setSrcX(int srcX) {
            this.srcX = srcX;
        }

        public CutPoin getCutPoin() {
            return cutPoin;
        }

        public void setCutPoin(CutPoin cutPoin) {
            this.cutPoin = cutPoin;
        }

        public int[][] getData() {
            return data;
        }

        public void setData(int[][] data) {
            this.data = data;
        }

        public int getCutHeight() {
            return cutHeight;
        }

        public void setCutHeight(int cutHeight) {
            this.cutHeight = cutHeight;
        }
    }

    static class CutPoin {

        private int y;


        private int x;

        public CutPoin(int x, int y) {
            this.y = y;
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }


    }


}
