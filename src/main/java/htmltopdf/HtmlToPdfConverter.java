package htmltopdf;

import java.io.File;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;

public class HtmlToPdfConverter {
    public static void main(String[] args) throws IOException {
        String htmlPath = "./example.html";
        String pdfFilePath = "./output.pdf";

        File htmlFile = new File(htmlPath);
        Document document = Jsoup.parse(htmlFile, "UTF-8");
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);

        try (OutputStream os = new FileOutputStream(pdfFilePath)) {
            ITextRenderer renderer = new ITextRenderer();
            SharedContext context = renderer.getSharedContext();
            context.setPrint(true);
            context.setInteractive(false);
            String baseUrl = FileSystems.getDefault().getPath(".")
                                .toUri().toURL().toString();
            renderer.setDocumentFromString(document.html(), baseUrl);
            renderer.layout();
            renderer.createPDF(os);
            System.out.println("done");
        }

    }
}
