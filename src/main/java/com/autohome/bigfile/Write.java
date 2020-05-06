package com.autohome.bigfile;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * @author zzn
 * @date 2020/4/29 17:21
 */
public class Write {
    public static void main(String[] args) throws Exception{
        System.out.println("开始执行....");
        FileWriter fileWriter = new FileWriter("E:\\c.txt");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for (int i = 0; i < 1000000; i++) {
            System.out.println(i);
            //bufferedWriter.write(i+"\t1911010747047554131\tdlczjkps\t1012818\t13680677011\ttp://athm.cn/cYuS5nc，祝您成功购车。非本人操作回1，退订TDFC【汽车之家】\t2019-11-01  07:47:04.000\t2019-11-01  07:47:10.000\t1\n");
            //bufferedWriter.write(i+"\t1911010747047554131\tdlczjkps\t1012818\t13680677011\t【汽车之家】您的询价需求已提交，请留意车主来电。二手车之家提醒：看车前请谨慎付定金、押金等费用！请实地看车，查验购买车辆相关证件！（点击免费领出行意外险：athm.cn/CCaAZgc）\t2019-11-01 07:47:04.000 \t2019-11-01 07:47:10.000\t1\n");
            bufferedWriter.write(i+"\t1911010747047554131\tdlczjkps\t1012818\t13680677011\t【汽车之家】[云监控] URL状态码 状态: 告警 告警Id: 95972 级别: 警告 应用/环境: chejiahao-m-git/gitbeta 告警对象: http://chejiahao.m.autohome.com.cn/authors/27... IP: 192.168.223.161 告警说明: url返回码不为200 当前值：404 发生时间: 2020-04-22 20:01:00 持续时间: 6天12小时57分钟 PC查看详情: http://athm.cn/xcy3pqc 汽车人查看详情: http://athm.cn/tdy3pqc\t2019-11-01 07:47:04.000\t2019-11-01 07:47:10.000\t1\n");
        }
        bufferedWriter.close();
        fileWriter.close();
        System.out.println("执行完成....");
    }
}
