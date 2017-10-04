package de.flecktec.shippinglabeler;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Jonas Fleck on 13.08.17.
 */
public class PDFMain
{

    public static void main (String[] args) throws IOException {
        while(true) {
            try {
                menue();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

    private static void menue() throws IOException {
        File pdfPath = new File(System.getProperty("java.io.tmpdir") + "/shippingLabel.pdf");
        System.out.print("Please enter full path of original label (macOS Drag&Drop into Terminal:");
        Scanner sc = new Scanner(System.in);
        File sourePath = new File(sc.next());
        System.out.println("Please select your label type:");
        System.out.println("1: DHL-Private");
        System.out.println("2: GLS-Private");
        System.out.println("3: DPD-Private (62 x 209)");
        System.out.println("4: AmazonDHL-Retoure (Gif File)");
        System.out.println("5: DHL-Retoure (62 x 209)");
        LabelCreator creator = LabelCreatorFactory.buildCreator(LabelType.get(sc.nextInt()));
        creator.createPDF(pdfPath, sourePath);
        Desktop.getDesktop().open(pdfPath);

    }
}

