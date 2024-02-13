package de.paulsenik;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.text.PDFTextStripperByArea;

public class Main {

  public static void main(String[] args) throws IOException {
    String path = "/home/paulsenik/Documents/EBook/writinginteractivemusicforvideogames.pdf";
    PDDocument document = Loader.loadPDF(new File(path));

    PDRectangle mediaBox = document.getPage(0).getMediaBox();
    System.out.println("Width: " + mediaBox.getWidth());
    System.out.println("Height: " + mediaBox.getHeight());

    String extractedText = fetchTextByRegion(document, 34);
    System.out.println(extractedText);
  }

  public static String fetchTextByRegion(PDDocument document, int pageNumber) throws IOException {
    Rectangle2D region = new Rectangle2D.Double(30,  0,  420,  630); // Adjust these values based on your PDF layout
    String regionName = "content";

    PDFTextStripperByArea stripper = new PDFTextStripperByArea();
    stripper.addRegion(regionName, region);
    PDPage page = document.getPage(pageNumber);
    stripper.extractRegions(page);
    String text = stripper.getTextForRegion(regionName);
    document.close();
    return text;
  }
}