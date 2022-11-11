import java.io.IOException;

public class Converter {
    public static void main(String[] args) throws IOException {
        PdfWorker worker = new PdfWorker("");
        System.out.println(worker.getText());
    }
}
