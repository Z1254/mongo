package com.autohome.bigfile;

import org.springframework.util.StopWatch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author zzn
 * @date 2020/4/29 23:23
 */
public class ReadBig {
    public static String file = "E:\\b.txt";

    public static void main(String[] args) throws Exception {
        StopWatch stopWatch = new StopWatch();
        final int BUFFER_SIZE = 0x300000;
        File f = new File(file);
        MappedByteBuffer inputBuffer = new RandomAccessFile(f, "r")
                .getChannel().map(FileChannel.MapMode.READ_ONLY, 0, f.length());
        byte[] dst = new byte[BUFFER_SIZE];
        stopWatch.start();
        for (int offset = 0; offset < inputBuffer.capacity(); offset += BUFFER_SIZE) {
            if (inputBuffer.capacity() - offset >= BUFFER_SIZE) {
                for (int i = 0; i < BUFFER_SIZE; i++) {
                    dst[i] = inputBuffer.get(offset + i);
                }
            } else {
                for (int i = 0; i < inputBuffer.capacity() - offset; i++) {
                    dst[i] = inputBuffer.get(offset + i);
                }
            }
            int length = (inputBuffer.capacity() % BUFFER_SIZE == 0) ? BUFFER_SIZE : inputBuffer.capacity() % BUFFER_SIZE;
            System.out.println(new String(dst, 0, length));
        }
        stopWatch.stop();
        System.out.println("读取文件花费的时间:" + stopWatch.getTotalTimeSeconds());
    }
}
