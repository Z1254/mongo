package com.autohome.bigfile2;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.BlockingDeque;

/**
 * @author zzn
 * @date 2020/4/30 1:18
 */
public class FileLineDataHandler implements DataProcessHandler {
    private String encode = "UTF-8";
    @Override
    public void process(BlockingDeque<String> queue, byte[] data) {
        try {
            //System.out.println(new String(data,encode).toString());
            queue.put(new String(data,encode).toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
