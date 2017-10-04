package de.flecktec.shippinglabeler;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by jonas on 19.08.17.
 */
public abstract class LabelCreator {

    final static float mm = 72 / 25.4f;
    final static float dpi = 300;

    public abstract void createPDF(File path, File source);

    protected static int mmToPixels(double mm) {
        return (int)(mm / 25.4 * dpi);
    }
    protected static BufferedImage cropImage(BufferedImage src, Rectangle rect) {
        BufferedImage dest = src.getSubimage(rect.x, rect.y, rect.width, rect.height);
        return dest;
    }
    public static BufferedImage rotateImage(BufferedImage image, double angle) {

        //  Determine the size of the rotated image
        double theta = Math.toRadians(angle);
        double cos = Math.abs(Math.cos(theta));
        double sin = Math.abs(Math.sin(theta));
        double width  = image.getWidth();
        double height = image.getHeight();
        int w = (int)(width * cos + height * sin);
        int h = (int)(width * sin + height * cos);

        BufferedImage out = new BufferedImage(w, h, image.getType());
        Graphics2D g2 = out.createGraphics();
        g2.fillRect(0,0,w,h);
        double x = w/2;
        double y = h/2;
        AffineTransform at = AffineTransform.getRotateInstance(theta, x, y);
        x = (w - width)/2;
        y = (h - height)/2;
        at.translate(x, y);
        g2.drawRenderedImage(image, at);
        g2.dispose();
        return out;
    }

}
