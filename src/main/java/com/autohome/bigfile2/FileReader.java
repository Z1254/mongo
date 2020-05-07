package com.autohome.bigfile2;

import com.autohome.config.BeanContext;
import com.autohome.dao.domain.SmsBill;
import com.autohome.dao.mapper.SmsBillMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * @author zzn
 * 多线程按行读取文件工具类
 * @date 2020/4/30 0:22
 */
public class FileReader {
    //线程数 默认是3
    private int readThreadNum = 3;
    private int writeThreadNum = 3;
    //文件路径
    private String filePath;
    //缓冲区大小  默认1024
    private int bufSize = 1024;
    //数据处理接口
    private DataProcessHandler dataProcessHandler;
    //线程池
    private ExecutorService readThreadPool;
    private ExecutorService writeThreadPool;
    private StopWatch stopWatch = new StopWatch();

    BlockingDeque<String> queue = new LinkedBlockingDeque<>();

    public FileReader(String filePath, int bufSize, int readThreadNum, int writeThreadNum) {
        this.readThreadNum = readThreadNum;
        this.writeThreadNum = writeThreadNum;
        this.bufSize = bufSize;
        this.filePath = filePath;
        this.readThreadPool = Executors.newFixedThreadPool(readThreadNum);
        this.writeThreadPool = Executors.newFixedThreadPool(writeThreadNum);
    }

    /**
     * description: 启动多线程读取文件
     *
     * @param
     * @return void
     */
    public void startRead() {
        FileChannel infile = null;
        try {
            RandomAccessFile raf = new RandomAccessFile(filePath, "r");
            infile = raf.getChannel();
            long size = infile.size();
            long subSize = size / readThreadNum;
            for (int i = 0; i < readThreadNum; i++) {
                long startIndex = i * subSize;
                if (size % readThreadNum > 0 && i == readThreadNum - 1) {
                    subSize += size % readThreadNum;
                }
                RandomAccessFile accessFile = new RandomAccessFile(filePath, "r");
                FileChannel inch = accessFile.getChannel();
                readThreadPool.execute(new MultiThreadReader(inch, startIndex, subSize));
            }
            readThreadPool.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (infile != null) {
                    infile.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * description: 启动多线程写
     *
     * @param
     * @return void
     */
    public void startWriter() {
        stopWatch.start();
        for (int i = 0; i < writeThreadNum; i++) {
            writeThreadPool.execute(new MultiThreadWriter());
        }
        writeThreadPool.shutdown();
        while (true) {
            if (writeThreadPool.isTerminated()) {
                stopWatch.stop();
                break;
            }
        }
        System.out.println(stopWatch.getTotalTimeSeconds());
    }

    public void registerHandler(DataProcessHandler dataProcessHandler) {
        this.dataProcessHandler = dataProcessHandler;
    }


    public class MultiThreadWriter implements Runnable {

        private SmsBillMapper smsBillMapper;

        @Override
        public void run() {
            boolean isRunning = true;
            try {
                List<SmsBill> list = new ArrayList<>();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                while (isRunning) {
                    String poll = queue.poll(2, TimeUnit.SECONDS);
                    if (null != poll) {
                        try {
                            SmsBill smsBill = new SmsBill();
                            String[] split = poll.split("\\t");
                            smsBill.setMsgId(UUID.randomUUID().toString().replace("-",""));
                            smsBill.setAccountId(split[2].trim());
                            smsBill.setAppendId(split[3].trim());
                            smsBill.setMobile(split[4].trim());
                            smsBill.setContent(split[5].trim());
                            smsBill.setPushTime(simpleDateFormat.parse(split[6].trim()));
                            smsBill.setReportTime(simpleDateFormat.parse(split[7].trim()));
                            smsBill.setStatus(split[8].trim());
                            list.add(smsBill);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } else {
                        isRunning = false;
                    }
                    if (list.size() > 200 || (list.size() > 0 && isRunning == false)) {
                        //TODO: 写数据库
                        this.smsBillMapper = BeanContext.getApplicationContext().getBean(SmsBillMapper.class);
                        long l = System.currentTimeMillis();
                        this.smsBillMapper.insert(list);
                        long l1 = System.currentTimeMillis();
                        System.out.println("insert 所用时长： " + (l1 - l));
                        list.clear();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            } finally {
                System.out.println("退出线程");
            }
        }
    }


    public class MultiThreadReader implements Runnable {
        private FileChannel channel;
        private long startIndex;
        private long rSize;

        public MultiThreadReader(FileChannel channel, long startIndex, long rSize) {
            this.channel = channel;
            this.startIndex = startIndex > 0 ? startIndex - 1 : startIndex;
            this.rSize = rSize;
        }

        @Override
        public void run() {
            readByLine();
        }

        public void readByLine() {
            try {
                ByteBuffer rbuf = ByteBuffer.allocate(bufSize);
                //设置读取文件的起始位置
                channel.position(startIndex);
                //读取文件数据的结束位置
                long endIndex = startIndex + rSize;
                //用来缓存上次读取剩下的部分
                byte[] temp = new byte[0];
                //换行符
                int LF = "\n".getBytes()[0];
                //用户判断文件是否读完
                boolean isEnd = false;
                //用于判断第一行读取到的是否是完成的一行
                boolean isWholeLine = false;
                //行数统计
                long lineCount = 0;
                //当前处理字节所在的位置
                long endLineIndex = startIndex;
                while (channel.read(rbuf) != -1 && !isEnd) {
                    int position = rbuf.position();
                    byte[] rbyte = new byte[position];
                    rbuf.flip();
                    rbuf.get(rbyte);
                    //每一行的起始位置的下标，相当于当前所读取到的byte数组
                    int startnum = 0;
                    //判断是否有换行符
                    //如果读取到最后一行不是完整的一行时，则继续往后读取直至读取到完整的一行才结束
                    for (int i = 0; i < rbyte.length; i++) {
                        endLineIndex++;
                        //若存在换行符
                        if (rbyte[i] == LF) {
                            //若该数据片段第一个字节为换行符，说明第一行读取到的是完整的一行
                            if (channel.position() == startIndex) {
                                isWholeLine = true;
                                startnum = i + 1;
                            } else {
                                byte[] line = new byte[temp.length + i - startnum + 1];
                                System.arraycopy(temp, 0, line, 0, temp.length);
                                System.arraycopy(rbyte, startnum, line, temp.length, i - startnum + 1);
                                startnum = i + 1;
                                lineCount++;
                                temp = new byte[0];
                                //处理数据
                                //如果不是第一个数据段
                                if (startIndex != 0) {
                                    if (lineCount == 1) {
                                        //当且仅当第一行为完整行时才处理
                                        if (isWholeLine) {
                                            dataProcessHandler.process(queue, line);
                                        }
                                    } else {
                                        dataProcessHandler.process(queue, line);
                                    }
                                } else {
                                    dataProcessHandler.process(queue, line);
                                }
                                //结束读取的判断
                                if (endLineIndex >= endIndex) {
                                    isEnd = true;
                                    break;
                                }
                            }
                        }
                    }
                    //说明rbyte最后一行还剩不完整的一行
                    if (!isEnd && startnum < rbyte.length) {
                        byte[] temp2 = new byte[temp.length + rbyte.length - startnum];
                        System.arraycopy(temp, 0, temp2, 0, temp.length);
                        System.arraycopy(rbyte, startnum, temp2, temp.length, rbyte.length - startnum);
                        temp = temp2;
                    }
                    rbuf.clear();
                }
                //兼容最后一行没有换行的情况
                if (temp.length > 0) {
                    if (dataProcessHandler != null) {
                        dataProcessHandler.process(queue, temp);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    channel.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public int getReadThreadNum() {
        return readThreadNum;
    }

    public int getWriteThreadNum() {
        return writeThreadNum;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getBufSize() {
        return bufSize;
    }

    public ExecutorService getReadThreadPool() {
        return readThreadPool;
    }

    public ExecutorService getWriteThreadPool() {
        return writeThreadPool;
    }
}
