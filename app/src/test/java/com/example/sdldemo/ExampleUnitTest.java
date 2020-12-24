package com.example.sdldemo;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
//        assertEquals(4, 2 + 2);
        System.out.println(test());
    }

    private String test() {

        String result = "程序开始";
        try {
            openText();
        } catch (Exception e) {
            result = "异常补货";
        }

        return result;

    }

    private void openText() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                throw new RuntimeException("线程中抛出异常");
            }
        }).start();
    }


}