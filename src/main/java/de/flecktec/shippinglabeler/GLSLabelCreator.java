package de.flecktec.shippinglabeler;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by jonas on 20.08.17.
 */
public class GLSLabelCreator extends LabelCreator{

    public void createPDF(File path, File source) {
        System.out.println("Creating PDF");
        try (PDDocument sourceDocument = PDDocument.load(source); final PDDocument targetdocument = new PDDocument())
        {
            PDPageTree pdPages = sourceDocument.getDocumentCatalog().getPages();
            PDFRenderer pdfRenderer = new PDFRenderer(sourceDocument);
                System.out.println("Rendering page ");
                BufferedImage bim = pdfRenderer.renderImageWithDPI(0, dpi, ImageType.RGB);
                System.out.println(bim.getWidth());
                System.out.println(mmToPixels(297));
                System.out.println("Creating new page");
                final PDPage singlePage = new PDPage(new PDRectangle(177f * mm, 56f * mm));
                targetdocument.addPage(singlePage);
                final PDPageContentStream contentStream = new PDPageContentStream(targetdocument, singlePage);
                BufferedImage machineLabel = cropImage(bim, new Rectangle(mmToPixels(7) , mmToPixels(36), mmToPixels(92) ,mmToPixels(53)));
                contentStream.drawImage(JPEGFactory.createFromImage(targetdocument, machineLabel), 0*mm, 2*mm, 92*mm, 53 * mm);
                BufferedImage sender = cropImage(bim, new Rectangle(mmToPixels(186.5) , mmToPixels(19), mmToPixels(17) ,mmToPixels(80)));
                sender = rotateImage(sender, -90);
                contentStream.drawImage(JPEGFactory.createFromImage(targetdocument, sender), 94*mm, 41*mm, 71*mm, 15 * mm);
                BufferedImage receiver = cropImage(bim, new Rectangle(mmToPixels(112) , mmToPixels(56), mmToPixels(75) ,mmToPixels(43)));
                contentStream.drawImage(JPEGFactory.createFromImage(targetdocument, receiver), 94*mm, 0*mm, 79*mm, 42 * mm);
                contentStream.close();



            targetdocument.save(path);


        }
        catch (IOException ioEx)
        {
            ioEx.printStackTrace();
        }
    }
}
