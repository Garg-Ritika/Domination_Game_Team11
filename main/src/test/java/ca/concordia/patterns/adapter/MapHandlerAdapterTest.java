package ca.concordia.patterns.adapter;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import static junit.framework.TestCase.assertEquals;

public class MapHandlerAdapterTest {

    MapHandlerAdapter d_mha;
    ConquestMapHandler d_cmh;

    File p_MapFile = new File("sample1");
    File p_MapFile2 = new File("sample2");


    @Before
    public void before() {
        d_mha = new MapHandlerAdapter(d_cmh);
    }


    @Test
    public void readMapFile() throws IOException {
        if (p_MapFile.exists()) {
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));

            d_mha.readMapFile(p_MapFile);
            String l_Ex = ">reading .map file from ";
            assertEquals(true, outContent.toString().startsWith(l_Ex));
        }

    }

    @Test
    public void writeMapFile() {

        if (p_MapFile2.exists()) {
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));

            d_mha.writeMapFile(p_MapFile2);
            String l_Ex = "writing .map file to ";
            assertEquals(true, outContent.toString().startsWith(l_Ex));
        }
    }
}
