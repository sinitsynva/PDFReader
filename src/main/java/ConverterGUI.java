import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

class ConverterGUI extends Frame implements ActionListener
{
    Label label1,label2;
    TextField text1,text2;
    Button button1,button2;
    public ConverterGUI()
    {
        label1 = new Label("Path to input pdf");
        label2 = new Label("Path to output file");
        text1 = new TextField(10);
        text2 = new TextField(10);
        button1 = new Button("Convert");
        button2 = new Button("Close");
        add(label1);
        add(text1);
        add(label2);
        add(text2);
        add(button1);
        add(button2);
        setSize(200,200);
        setTitle("Convert text to speech");
        setLayout(new FlowLayout());
        button1.addActionListener(this);
        button2.addActionListener(this);
    }
    public void actionPerformed(ActionEvent action) {
        if(action.getSource()==button1)
        {
            try {
            PdfWorker worker = new PdfWorker(text1.getText());
            System.out.println(worker.getText());
            AudioWorker audioWorker = new AudioWorker(worker.getText());
            audioWorker.save(text2.getText());
            }
            catch (FileNotFoundException e) {
                text1.setText("Invalid input entered");
            }  catch (SynthesisException | MaryConfigurationException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        if(action.getSource() == button2)
        {
            System.exit(0);
        }
    }
    public static void main(String[] args)
    {
        ConverterGUI calC = new ConverterGUI();
        calC.setVisible(true);
        calC.setLocation(300,300);
    }
}

