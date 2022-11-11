import java.io.*;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

public class PdfWorker {
    private final  String pathToPDF;
    private String text;
    public PdfWorker(String pathToPDF){
        this.pathToPDF = pathToPDF;
    }

    public String getText() throws IOException {
        if (text == null){
            File initialFile = new File(pathToPDF);
            InputStream inputStream = new FileInputStream(initialFile);
            PdfReader reader = new PdfReader(inputStream);
            PdfDocument document = new PdfDocument(reader);
            for(int i=1; i<=document.getNumberOfPages(); i++){
                PdfPage page = document.getPage(i);
                text = text + PdfTextExtractor.getTextFromPage(page);
            }
        }
        return text;
    }


}
