package htmltopdf;

import org.junit.jupiter.api.Test;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HtmlToPdfConverterTest {

    @Test
    public void testParseHtml() throws IOException {
        String htmlContent = "<html><body>Hello, World!</body></html>";

        // Create a temporary HTML file
        File tempFile = File.createTempFile("temp", ".html");

        // Write the HTML content to the file
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(htmlContent);
        }

        // Parse the HTML file and verify the result
        Document document = HtmlToPdfConverter.parseHtml(tempFile.getAbsolutePath());
        assertTrue(document.body().hasText());

        // Delete the temporary file
        tempFile.delete();
    }

    @Test
    public void testGetBaseUrl() throws MalformedURLException {
        String baseUrl = HtmlToPdfConverter.getBaseUrl();
        assertTrue(baseUrl.startsWith("file:"));
    }

    @Test
    public void testRenderPdf() throws IOException {
        String htmlContent = "<html><body>Hello, World!</body></html>";
        Document document = Jsoup.parse(htmlContent);

        // Create a temporary file to store the PDF output
        File tempFile = File.createTempFile("temp", ".pdf");

        HtmlToPdfConverter.renderPdf(document, tempFile.getAbsolutePath());

        // Read the contents of the temporary file and verify the PDF output
        try (InputStream inputStream = new FileInputStream(tempFile)) {
            byte[] pdfData = new byte[inputStream.available()];
            inputStream.read(pdfData);
            assertTrue(pdfData.length > 0);
        } finally {
            tempFile.delete(); // Delete the temporary file
        }
    }
}