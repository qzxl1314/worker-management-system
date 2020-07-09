package com.example.appqzx;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private  int Code;
    @Test
    public void addition_isCorrect() {
    float [][] a=new float[2][2];
    Random f=new Random(1);
    System.out.println(Arrays.deepToString(a));
        for (int item=0;item< a.length;item++) {
            float [] d={f.nextFloat(),f.nextFloat()};
            a[item]=d;
        }
    System.out.println(Arrays.deepToString(a));

    }

}