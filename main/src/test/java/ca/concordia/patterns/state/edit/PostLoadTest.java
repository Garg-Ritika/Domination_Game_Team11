package ca.concordia.patterns.state.edit;

import ca.concordia.gameengine.GameEngine;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.TestCase.assertEquals;


/**
 * This class implements the methods of PostLoad class
 * and checks whether expected output is equals to actual output or not.
 */
public class PostLoadTest {
    GameEngine d_ge= new GameEngine();
    String[] d_str = {"savemap"};
    String[] d_str1={"-add asia 1"};
    String[] d_str2 = {"editmap"};
    String[] d_str3 = {"validatemap"};


    /**
     * This is the test method that checks editContinent method's expected and actual output
     */
    @Test
    public void editContinent() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        PostLoad po= new PostLoad(d_ge);
        po.saveMap(d_str1);
        String ex="INCOMPLETE COMMAND"+ System.lineSeparator() +
                "INCOMPLETE COMMAND" + System.lineSeparator();
        assertEquals(ex,outContent.toString());

    }

    /**
     * This is the test method that checks saveMap method's expected and actual output
     */
    @Test
    public void saveMap () {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        PostLoad po= new PostLoad(d_ge);
        po.saveMap(d_str);
        String ex="INCOMPLETE COMMAND"+ System.lineSeparator() +
                "INCOMPLETE COMMAND" + System.lineSeparator();
        assertEquals(ex,outContent.toString());
    }

    /**
     * This test method asserts editMap method in PostLoad class
     */
    @Test
    public void editMap() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        PostLoad po= new PostLoad(d_ge);
        po.editMap(d_str2);

        String ex="INCOMPLETE COMMAND, create an in-memory map file from scratch"+ System.lineSeparator() +
                " No continents available in the map " + System.lineSeparator();
        assertEquals(ex,outContent.toString());

    }

    /**
     * This  method checks whether validateMap method's(in PostLoad class) expected and actual output is equal or not
     */
    @Test
    public void validateMap() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        PostLoad po= new PostLoad(d_ge);
        po.validateMap(d_str3);

        String ex="validatemap command received ..." + System.lineSeparator() +
                " No continents available in the map " + System.lineSeparator();
        assertEquals(ex,outContent.toString());
    }


}