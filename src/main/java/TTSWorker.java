import marytts.LocalMaryInterface;
import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.io.IOException;

public class TTSWorker {
    public static void convertAndSave(String path, String text) throws SynthesisException, MaryConfigurationException, IOException {
        File file = new File(path);
        LocalMaryInterface marry = new LocalMaryInterface();
        AudioInputStream stream = marry.generateAudio(text);
        System.out.println("write start;");
        AudioSystem.write(stream, AudioFileFormat.Type.WAVE, file);
        System.out.println("write end;");
    }
}
