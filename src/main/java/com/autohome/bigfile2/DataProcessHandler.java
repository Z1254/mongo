package com.autohome.bigfile2;

import java.util.concurrent.BlockingDeque;

/**
 * @author zzn
 * @date 2020/4/30 1:16
 */
public interface DataProcessHandler {
    void process(BlockingDeque<String> queue, byte[] data);
}
