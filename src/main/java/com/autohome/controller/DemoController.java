package com.autohome.controller;


import com.autohome.bigfile2.FileLineDataHandler;
import com.autohome.bigfile2.FileReader;
import com.autohome.dao.domain.SmsBill;
import com.autohome.dao.mapper.SmsBillMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zzn
 * @date 2020/5/5 1:09
 */
@RestController
public class DemoController {
    @Autowired
    private SmsBillMapper smsBillMapper;

    @GetMapping("/api/file/test")
    public void test() {
        FileReader fileReader = new FileReader("E:\\c.txt", 1024, 10, 100);
        fileReader.registerHandler(new FileLineDataHandler());
        fileReader.startRead();
        fileReader.startWriter();
    }

    @GetMapping("/api/file/test2")
    public void test2() {
        //SmsBill{msgId='1911010747047554131', accountId='dlczjkps', appendId='1012818', mobile='13680677011', content='tp://athm.cn/cYuS5nc，祝您成功购车。非本人操作回1，退订TDFC【汽车之家】', pushTime=Fri Nov 01 07:47:04 CST 2019, reportTime=Fri Nov 01 07:47:10 CST 2019, status='1'}
        List<SmsBill> list = new ArrayList<>();
        SmsBill smsBill = new SmsBill();
        smsBill.setMsgId("1911010747047554131");
        smsBill.setAccountId("dlczjkps");
        smsBill.setAppendId("1012818");
        smsBill.setMobile("13680677011");
        smsBill.setContent("哈哈");
        smsBill.setPushTime(new Date());
        smsBill.setReportTime(new Date());
        smsBill.setStatus("1");
        list.add(smsBill);
        list.add(smsBill);
        this.smsBillMapper.insert(list);
    }
}
