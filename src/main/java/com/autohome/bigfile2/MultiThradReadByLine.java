package com.autohome.bigfile2;

import org.springframework.util.StopWatch;

/**
 * @author zzn
 * @date 2020/4/30 1:21
 */
public class MultiThradReadByLine {

    public static void main(String[] args) {
        FileReader fileReader = new FileReader("E:\\a.txt",1024,100,50);
        fileReader.registerHandler(new FileLineDataHandler());
        fileReader.startRead();
        fileReader.startWriter();
    }
}
