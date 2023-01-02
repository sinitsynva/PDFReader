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
    Label label1,label2,label3;
    TextField text1,text2,text3;
    Button button1,button2,button3;
    public ConverterGUI()
    {
        label1 = new Label("Path to input pdf");
        label2 = new Label("Path to output file");
        label3 = new Label("Extracting pages separated by commas");
        text1 = new TextField(50);
        text2 = new TextField(50);
        text3 = new TextField(50);
        button1 = new Button("Convert text");
        button3 = new Button("Extract pages");
        button2 = new Button("Close");
        add(label1);
        add(text1);
        add(label2);
        add(text2);
        add(label3);
        add(text3);
        add(button1);
        add(button3);
        add(button2);
        setSize(500,210);
        setTitle("PDF features");
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
        {   PdfWorker parser;
            String[] pagesStr =  text3.getText().split(",");
            int[] pages = new int[pagesStr.length];
            for(int i=0; i<pages.length; i++){
                try {
                    System.out.println(pagesStr[i]);
                    pages[i] = Integer.parseInt(pagesStr[i]);
                } catch (NullPointerException | NumberFormatException e) {
                    text3.setText("Invalid format pages");
                    return;
                }

            }
            try {
                parser = new PdfWorker(text1.getText());
                parser.parse();
            }
            catch (FileNotFoundException e) {
                text1.setText("Invalid path entered");
                return;
            }  catch (IOException e) {
                text1.setText("OOOOPs, something wrong...");
                return;
            }
            try {
                parser.extractPages(pages, text2.getText());
            } catch (IOException e) {
                text3.setText("OOOOPs, something wrong...");
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

