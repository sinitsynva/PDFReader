import marytts.LocalMaryInterface;
import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.io.IOException;

public class Converter {
    public static void main(String[] args) throws IOException, MaryConfigurationException, SynthesisException {
        PdfWorker worker = new PdfWorker("/Users/viktorsinicyn/Downloads/OTUS.pdf");
        System.out.println(worker.getText());

        File file = new File("/Users/viktorsinicyn/Downloads/OTUS.wave");
        LocalMaryInterface marry = new LocalMaryInterface();
        AudioInputStream stream = marry.generateAudio(worker.getText());
        AudioSystem.write(stream, AudioFileFormat.Type.WAVE, file);



    }
}
