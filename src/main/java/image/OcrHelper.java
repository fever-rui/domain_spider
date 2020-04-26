package image;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.awt.image.BufferedImage;

public class OcrHelper {

    private final static String numWhiteList = "0123456789";

    private final static String wordAndNumWhiteList = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String ocrNum(BufferedImage image) {
        String ocrResult = null;
        try {
            ITesseract instance = new Tesseract();
            instance.setDatapath("C:\\Program Files (x86)\\Tesseract-OCR");
            instance.setLanguage("eng");
            instance.setTessVariable("tessedit_char_whitelist", numWhiteList);
            ocrResult = instance.doOCR(image).replaceAll("[^0-9]", "");
            System.out.println("ocr:" + ocrResult);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        return ocrResult;
    }

    public static String ocrWordAndNum(BufferedImage image) {
        String ocrResult = null;
        try {
            ITesseract instance = new Tesseract();
            instance.setDatapath("C:\\Program Files (x86)\\Tesseract-OCR");
            instance.setLanguage("eng");
            instance.setTessVariable("tessedit_char_whitelist", wordAndNumWhiteList);
            ocrResult = instance.doOCR(image).replaceAll("[^0-9A-Z]", "");
            System.out.println("ocr:" + ocrResult);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        return ocrResult;
    }
}
