package junit_activities;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Activity1 {
     List list = new ArrayList<String>();
    @BeforeEach
    public void setUp(){

        list.add("alpha");
        list.add("beta");

    }

    @Test
    public  void insertTest() throws Exception{
        assertEquals(2,list.size());
        list.add("gamma");
        assertEquals(3,list.size());
        assertEquals("alpha",list.get(0));
        assertEquals("beta",list.get(1));
        assertEquals("gamma",list.get(2));


    }

    @Test
    public void replaceTest() throws Exception{
    assertEquals(2,list.size());
    list.add("niki");
    assertEquals(3,list.size());
        list.set(1,"rumi");
        assertEquals("alpha",list.get(0));
        assertEquals("beta",list.get(1));

    }

}
