package de.flecktec.shippinglabeler;

import de.flecktec.shippinglabeler.LabelCreator;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by jonas on 13.08.17.
 */
public class DHLRetoureLabelCreator extends LabelCreator {

    public void createPDF(File path, File source) {
        System.out.println("Creating PDF");
        try (PDDocument sourceDocument = PDDocument.load(source); final PDDocument targetdocument = new PDDocument())
        {
            PDFRenderer pdfRenderer = new PDFRenderer(sourceDocument);
            for (int page = 0; page < sourceDocument.getNumberOfPages(); ++page)
            {
                System.out.println("Rendering page "+ page);
                sourceDocument.getPage(page).setRotation(90);
                BufferedImage bim = pdfRenderer.renderImageWithDPI(page, dpi, ImageType.RGB);
                System.out.println(bim.getWidth());
                System.out.println(mmToPixels(297));
                System.out.println("Creating new page");
                final PDPage singlePage = new PDPage(new PDRectangle(202f * mm, 56f * mm));
                targetdocument.addPage(singlePage);
                final PDPageContentStream contentStream = new PDPageContentStream(targetdocument, singlePage);

                BufferedImage adressLabel = cropImage(bim, new Rectangle(mmToPixels(165) , mmToPixels(16), mmToPixels(106) ,mmToPixels(53)));
                contentStream.drawImage(JPEGFactory.createFromImage(targetdocument, adressLabel), 0*mm, 0*mm, 113*mm, 56.08f * mm);

                BufferedImage barcode1 = cropImage(bim, new Rectangle(mmToPixels(190) , mmToPixels(134), mmToPixels(55) ,mmToPixels(25)));
                contentStream.drawImage(JPEGFactory.createFromImage(targetdocument, barcode1), 116*mm, 30*mm, 55*mm, 25 * mm);

                BufferedImage barcode2 = cropImage(bim, new Rectangle(mmToPixels(190) , mmToPixels(167), mmToPixels(55) ,mmToPixels(25)));
                contentStream.drawImage(JPEGFactory.createFromImage(targetdocument, barcode2), 116*mm, 0*mm, 55*mm, 25 * mm);

                BufferedImage barcode3 = cropImage(bim, new Rectangle(mmToPixels(200) , mmToPixels(90), mmToPixels(35) ,mmToPixels(21)));
                barcode3 = rotateImage(barcode3, -90);
                contentStream.drawImage(JPEGFactory.createFromImage(targetdocument, barcode3), 181*mm, 10*mm, 21*mm, 35 * mm);

                contentStream.close();


            }

            targetdocument.save(path);
            /*for(Smarthomeitem item : smarthomeitems) {
                final PDPage singlePage = new PDPage(new PDRectangle(62 * mm, 25 * mm));
                document.addPage(singlePage);
                final PDPageContentStream contentStream = new PDPageContentStream(document, singlePage);
                String json = new Gson().toJson(item);
                BufferedImage qr = createQRImage(json, 250);
                contentStream.drawImage(JPEGFactory.createFromImage(document, qr), 2*mm, 3*mm, 19*mm, 19 * mm);
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER_BOLD, 8);
                contentStream.newLineAtOffset(22* mm, 14* mm);
                contentStream.showText(item.getName());
                contentStream.endText();
                contentStream.close();
            }
            document.save(path);
            System.out.println("Finished"); */

        }
        catch (IOException ioEx)
        {
            ioEx.printStackTrace();
        }
    }


}
