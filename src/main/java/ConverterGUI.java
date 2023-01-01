import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

class ConverterGUI extends Frame implements ActionListener
{
    Label label1,label2;
    TextField text1,text2;
    Button button1,button2,button3;
    public ConverterGUI()
    {
        label1 = new Label("Path to input pdf");
        label2 = new Label("Path to output file");
        text1 = new TextField(50);
        text2 = new TextField(50);
        button1 = new Button("Convert");
        button2 = new Button("Close");
        button3 = new Button("Extract pages");
        add(label1);
        add(text1);
        add(label2);
        add(text2);
        add(button1);
        add(button2);
        add(button3);
        setSize(600,150);
        setTitle("Convert text to speech");
        setLayout(new FlowLayout());
        button1.addActionListener(this);
        button2.addActionListener(this);
        button3.addActionListener(this);
    }
    public void actionPerformed(@NotNull ActionEvent action) {
        if(action.getSource()==button1)
        {   PdfWorker parser;
            try {
                parser = new PdfWorker(text1.getText());
                parser.parse();
                System.out.println(parser.getTextFromAllPages());
            }
            catch (FileNotFoundException e) {
                text1.setText("Invalid path entered");
                return;
            }  catch (IOException e) {
                text1.setText("OOOOPs, something wrong...");
                return;
            }
            try {
                TTSWorker.convertAndSave(text2.getText(), parser.getTextFromAllPages());
            } catch (SynthesisException | MaryConfigurationException | IOException e) {
                text2.setText("OOOOPs, something wrong...");
                return;
            }

        }
        if(action.getSource() == button2)
        {
            System.exit(0);
        }
        if(action.getSource() == button3)
        {
            PdfWorker parser;
            int[] pages = new int[2];
            pages[0] = 36;
            pages[1] = 37;
            try {
                System.out.println("start parse");
                parser = new PdfWorker(text1.getText());
                parser.parse();
                System.out.println("start extract");
                parser.extractPages(pages, text2.getText());
                System.out.println("end process");
            } catch (IOException e) {
                System.out.println("exception");
                throw new RuntimeException(e);

            }
        }
    }

    public static void main(String[] args)
    {
        ConverterGUI calC = new ConverterGUI();
        calC.setVisible(true);
        calC.setLocation(300,300);
    }
}

