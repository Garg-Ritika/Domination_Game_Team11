package ca.concordia.patterns.state.edit;

import ca.concordia.gameengine.GameEngine;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static junit.framework.TestCase.assertEquals;


/**
 * This class implements the methods of PostLoad class
 * and checks whether expected output is equals to actual output or not.
 */
public class PostLoadTest {
    GameEngine d_ge = new GameEngine();
    String[] d_str = {"savemap"};
    String[] d_str1 = {"-add asia 1"};
    String[] d_str2 = {"editmap"};
    String[] d_str3 = {"validatemap"};


    /**
     * This is the test method that checks editContinent method's expected and actual output
     */
    @Test
    public void editContinent() {
        System.out.println("PostLoad Test : editcontinent");
        ByteArrayOutputStream l_OutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(l_OutContent));

        PostLoad l_Po = new PostLoad(d_ge);
        l_Po.saveMap(d_str1);
        String l_Ex = "INCOMPLETE COMMAND";
        assertEquals(true, l_OutContent.toString().contains(l_Ex));

    }

    /**
     * This is the test method that checks saveMap method's expected and actual output
     */
    @Test
    public void saveMap() {
        System.out.println("PostLoad Test : savemap");
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        PostLoad l_Po = new PostLoad(d_ge);
        l_Po.saveMap(d_str);
        String l_Ex = "INCOMPLETE COMMAND" + System.lineSeparator();
        assertEquals(true, outContent.toString().startsWith(l_Ex));

    }

    /**
     * This test method asserts editMap method in PostLoad class
     */
    @Test
    public void editMap() {
        System.out.println("PostLoad Test : editmap");
        ByteArrayOutputStream l_OutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(l_OutContent));

        PostLoad l_Po = new PostLoad(d_ge);

        InputStream stdin = System.in;
        try {
            ByteArrayInputStream bin = new ByteArrayInputStream("1".getBytes());
            System.setIn(bin);

            l_Po.editMap(d_str2);
        } finally {
            System.setIn(stdin);
            System.out.println("finally ");
        }

        String l_Ex = "INCOMPLETE COMMAND, create an in-memory map file from scratch";
        assertEquals(true, l_OutContent.toString().startsWith(l_Ex));

    }

    /**
     * This  method checks whether validateMap method's(in PostLoad class) expected and actual output is equal or not
     */
    @Test
    public void validateMap() {
        System.out.println("PostLoad Test : validatemap");
        ByteArrayOutputStream l_OutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(l_OutContent));

        PostLoad l_Po = new PostLoad(d_ge);
        l_Po.validateMap(d_str3);

        String l_Ex = "validatemap command received ...";

        assertEquals(true, l_OutContent.toString().startsWith(l_Ex));
    }

}