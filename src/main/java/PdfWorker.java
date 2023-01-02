import java.io.*;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.layout.Document;
import org.jetbrains.annotations.NotNull;

public class PdfWorker {
    private final File file;
    private PdfDocument document;
    public PdfWorker(String path) throws IOException {
        this.file = new File(path);
    }

    public PdfWorker(@NotNull File file){
        this.file = file;

    }
    public void parse() throws IOException {
        InputStream inputStream = new FileInputStream(file);
        PdfReader reader = new PdfReader(inputStream);
        document = new PdfDocument(reader);
        inputStream.close();
        System.out.println("parse: " + document.getNumberOfPages());
    }

    public String getTextFromPage(int pageNumber){
        if (pageNumber <= 0 && document.getNumberOfPages() < pageNumber){
           return "";
        }
        return PdfTextExtractor.getTextFromPage(document.getPage(pageNumber));
    }

    public String getTextFromPages(int @NotNull [] pageNumbers){
        StringBuilder result = new StringBuilder();
        for (int pageNumber : pageNumbers) {
            result.append(getTextFromPage(pageNumber));
        }
        return result.toString();
    }

    public String getTextFromAllPages(){
        int[] pages = new int[document.getNumberOfPages()];
        for(int i = 0; i < pages.length; i++){
            pages[i] = i+1;
        }
        return getTextFromPages(pages);
    }

    private PdfPage getPage(int pageNumber) {
        System.out.println("getPage: " + pageNumber);
        return document.getPage(pageNumber);
    }

    public void extractPages(int[] pageNumbers, String path) throws FileNotFoundException {
        File file = new File(path);
        PdfDocument newDocument = new PdfDocument(new PdfWriter(file));
        for (int pageNumber : pageNumbers) {
            System.out.println("extractPages: copy pageNumber " + pageNumber);
            document.copyPagesTo(pageNumber, pageNumber, newDocument);
            //newDocument.addPage(getPage(pageNumber));
        }
        Document document = new Document(newDocument);
        document.close();
    }

}
