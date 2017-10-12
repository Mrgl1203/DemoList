package com.zxing.encoding;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.Hashtable;

/**
 * @author Ryan Tang
 */
public final class EncodingHandler {
    private static final int BLACK = 0xff000000;

    public static Bitmap createQRCode(String str, int widthAndHeight) throws WriterException {
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix matrix = new MultiFormatWriter().encode(str,
                BarcodeFormat.QR_CODE, widthAndHeight, widthAndHeight);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = BLACK;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    /**
     * 生成中心带头像的二维码图形：
     * 1. 向上方一样正常处理图形的生成
     * 2. 需要判断，xy坐标点所处的位置，如果位置是在中心头像意外，
     * 那么就让对应的点上显示黑色或者蓝色等颜色
     * 3. 如果所处的位置是在中心头像区域内，那么就让对应的点上
     * 显示头像图片中的颜色值
     */
    public static Bitmap createQRCode(String str, int widthAndHeight, Bitmap icon, int iconWidth) throws WriterException {
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

        BitMatrix matrix = new MultiFormatWriter().encode(str,
                BarcodeFormat.QR_CODE, widthAndHeight, widthAndHeight);

        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];

        //处理中心图片
        Bitmap logo = Bitmap.createScaledBitmap(icon, iconWidth, iconWidth, true);
        //获取到头像图片的宽高的1/2
        int bitWidth = logo.getWidth() / 2;
        int bitHeight = logo.getHeight() / 2;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if ((x > width / 2 - bitWidth && x < width / 2 + bitWidth) &&
                        (y > height / 2 - bitHeight && y < height / 2 + bitHeight)) {  //作用：判断当前的x，y点的范围是否位于头像范围内
                    //当前x,y点位于头像范围内
                    pixels[y * width + x] = logo.getPixel(x - (width / 2 - bitWidth), y - (height / 2 - bitHeight));

                } else {
                    if (matrix.get(x, y)) {
                        pixels[y * width + x] = BLACK;
                    }
                }


            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}
