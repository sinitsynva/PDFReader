import java.io.*;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import org.jetbrains.annotations.NotNull;

public class PdfParser {
    private final File file;
    private PdfDocument document;
    public PdfParser(String path) throws IOException {
        this.file = new File(path);
    }

    public PdfParser(@NotNull File file){
        this.file = file;

    }
    public void parse() throws IOException {
        InputStream inputStream = new FileInputStream(file);
        PdfReader reader = new PdfReader(inputStream);
        document = new PdfDocument(reader);
        inputStream.close();
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

}
