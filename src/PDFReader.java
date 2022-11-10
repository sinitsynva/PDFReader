import java.io.IOException;
import external.barcodes.PdfReader;

public class PDFReader {
    private String pathToPDF;
    private String text;

    public PDFReader(String pathToPDF) {
        this.pathToPDF = pathToPDF;
    };
    public static void main(String[] args) throws IOException {
        // считаем, что программе передается один аргумент - имя файла
        PdfReader reader = new PdfReader(args[0]);

        // не забываем, что нумерация страниц в PDF начинается с единицы.
        for (int i = 1; i <= reader.getNumberOfPages(); ++i) {
            TextExtractionStrategy strategy = new SimpleTextExtractionStrategy();
            String text = PdfTextExtractor.getTextFromPage(reader, i, strategy);
            System.out.println(text);
        }

        // убираем за собой
        reader.close();
    }

}
