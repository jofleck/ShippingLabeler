package de.flecktec.shippinglabeler;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by jonas on 01.09.17.
 */
public class AmazonRetourLabelCreator extends LabelCreator {

    @Override
    public void createPDF(File path, File source) {
        try ( final PDDocument targetdocument = new PDDocument()) {
            BufferedImage img = ImageIO.read(source);
            img = rotateImage(img, 90);
            final PDPage singlePage = new PDPage(new PDRectangle(177f * mm, 56f * mm));
            targetdocument.addPage(singlePage);
            final PDPageContentStream contentStream = new PDPageContentStream(targetdocument, singlePage);

            BufferedImage senderLabel = cropImage(img, new Rectangle(mmToPixels(12) , mmToPixels(6), mmToPixels(50) ,mmToPixels(22)));
            contentStream.drawImage(JPEGFactory.createFromImage(targetdocument, senderLabel), 0*mm, 34*mm, 50*mm, 22 * mm);

            BufferedImage receiverLabel = cropImage(img, new Rectangle(mmToPixels(6) , mmToPixels(60), mmToPixels(50) ,mmToPixels(28)));
            contentStream.drawImage(JPEGFactory.createFromImage(targetdocument, receiverLabel), 0*mm, 0*mm, 50*mm, 28 * mm);

            BufferedImage packetNumberLabel = cropImage(img, new Rectangle(mmToPixels(26) , mmToPixels(38), mmToPixels(46), mmToPixels(20)));
            contentStream.drawImage(JPEGFactory.createFromImage(targetdocument, packetNumberLabel), 52*mm, 30*mm, 60*mm, 26 * mm);

            BufferedImage routingCodeLabel = cropImage(img, new Rectangle(mmToPixels(3) , mmToPixels(105), mmToPixels(53) ,mmToPixels(20)));
            contentStream.drawImage(JPEGFactory.createFromImage(targetdocument, routingCodeLabel), 113*mm, 30*mm, 69*mm, 26 * mm);

            BufferedImage rmaCodeLabel = cropImage(img, new Rectangle(mmToPixels(15) , mmToPixels(127), mmToPixels(72) ,mmToPixels(19)));
            contentStream.drawImage(JPEGFactory.createFromImage(targetdocument, rmaCodeLabel), 62*mm, 0*mm, 93*mm, 25 * mm);


            contentStream.close();
            targetdocument.save(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
