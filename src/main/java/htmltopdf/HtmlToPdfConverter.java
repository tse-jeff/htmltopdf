package htmltopdf;

import java.io.File;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.FileSystems;

public class HtmlToPdfConverter {
    /**
     * Parses an HTML file into a Jsoup Document object.
     *
     * @param htmlPath The path to the HTML file.
     * @return The parsed Document object.
     * @throws IOException If an error occurs while parsing the file.
     */
    public static Document parseHtml(String htmlPath) throws IOException {
        File htmlFile = new File(htmlPath);
        Document document = Jsoup.parse(htmlFile, "UTF-8");
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        return document;
    }

    /**
     * Gets the base URL for the current directory.
     *
     * @return The base URL.
     * @throws MalformedURLException If an error occurs while creating the URL.
     */
    public static String getBaseUrl() throws MalformedURLException {
        return FileSystems.getDefault().getPath(".").toUri().toURL().toString();
    }

    /**
     * Renders a Document object as a PDF file.
     *
     * @param document The Document object to render.
     * @param pdfFilePath The path to the output PDF file.
     * @throws IOException If an error occurs while writing the PDF file.
     */
    public static void renderPdf(Document document, String pdfFilePath) throws IOException {
        try (OutputStream os = new FileOutputStream(pdfFilePath)) {
            ITextRenderer renderer = new ITextRenderer();
            String baseUrl = getBaseUrl();
            renderer.setDocumentFromString(document.html(), baseUrl);
            renderer.layout();
            renderer.createPDF(os);
            System.out.println("done");
        }
    }

    /**
     * The main entry point for the application.
     *
     * @param args The command-line arguments.
     * @throws IOException If an error occurs during the conversion process.
     */
    public static void main(String[] args) throws IOException {
        String htmlPath = "./example.html";
        String pdfFilePath = "./output.pdf";
        Document document = parseHtml(htmlPath);
        renderPdf(document, pdfFilePath);
    }
}
