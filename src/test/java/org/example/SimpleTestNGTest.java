package org.example;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SimpleTestNGTest {

    @Test
    public void sampleTest(){
        int a = 2 + 2;
        Assert.assertEquals(a, 4, "La suma debe ser 4");
    }
}

