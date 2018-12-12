package lab12;

import org.xml.sax.InputSource;

import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        (new MyFrame()).setVisible(true);
        XMLDecoder d=new XMLDecoder(new InputSource(new FileReader(new File("input.xml"))));
        System.out.println(d.readObject());
    }
}
