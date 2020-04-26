package image;

import java.awt.*;
import java.awt.image.BufferedImage;


public class ImageTool {

    public static BufferedImage grayImage(BufferedImage bufferedImage){
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        BufferedImage grayBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                final int color = bufferedImage.getRGB(x, y);
                int gray = getGray(color);
                int newPixel = colorToRGB(255, gray, gray, gray);
                grayBufferedImage.setRGB(x, y, newPixel);
            }
        }
        return grayBufferedImage;
    }

    public static BufferedImage binarzing(BufferedImage bufferedImage, int threshold) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int gray = getGray(bufferedImage.getRGB(x, y));
                if (gray < threshold) {
                    bufferedImage.setRGB(x, y, colorToRGB(255,0,0,0));
                } else {
                    bufferedImage.setRGB(x, y, colorToRGB(255,255,255,255));
                }
            }
        }
        return bufferedImage;
    }

    public static BufferedImage clearLine(BufferedImage bufferedImage, int keyx, int keyy) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        for (int x = 0; x < width; x++) {
            int blackNum = 0;
            int cutPoint = 0;
            for (int y = 0; y < height; y++) {
                int color = getGray(bufferedImage.getRGB(x, y));
                if (color == 0) {
                    if (blackNum == 0) {
                        cutPoint = y;
                    }
                    blackNum++;
                }
                if (color == 255) {
                    if (blackNum < keyx) {
                        for (int i = cutPoint; i < cutPoint + blackNum; i++) {
                            bufferedImage.setRGB(x, i, colorToRGB(255,255,255,255));
                        }
                    }
                    blackNum = 0;
                    cutPoint = 0;
                }
            }
        }

        for (int y = 0; y < height; y++) {
            int blackNum = 0;
            int cutPoint = 0;
            for (int x = 0; x < width; x++) {
                int color = getGray(bufferedImage.getRGB(x, y));
                if (color == 0) {
                    if (blackNum == 0) {
                        cutPoint = x;
                    }
                    blackNum++;
                }
                if (color == 255) {
                    if (blackNum < keyy) {
                        for (int i = cutPoint; i < cutPoint + blackNum; i++) {
                            bufferedImage.setRGB(i, y, colorToRGB(255,255,255,255));
                        }
                    }
                    blackNum = 0;
                    cutPoint = 0;
                }

            }
        }
        return bufferedImage;
    }

    public static BufferedImage grayAndBin(BufferedImage image) {
        image = grayImage(image);
        return binarzing(image, 127);
    }

    public static BufferedImage grayBinClearLine(BufferedImage image, int keyx, int keyy) {
        image = grayImage(image);
        return clearLine(binarzing(image, 127), keyx, keyy);
    }

    private static int colorToRGB(int alpha, int red, int green, int blue) {
        int newPixel = 0;
        newPixel += alpha;
        newPixel = newPixel << 8;
        newPixel += red;
        newPixel = newPixel << 8;
        newPixel += green;
        newPixel = newPixel << 8;
        newPixel += blue;

        return newPixel;
    }

    private static int getGray(int color) {
        Color c = new Color(color);
        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();
        return (r + g + b)/3;
    }


}
