package com.wang.service;

/**
 * Created by 2017063001 on 2018/9/4.
 * 项目名称：Seckill
 * 类描述  ：
 * 创建人  ：MaxWang
 * 创建时间：2018/9/4 11:42
 * 修改人  ：2017063001
 * 修改时间：2018/9/4
 * 修改备注：
 */


import com.wang.dto.Exposer;
import com.wang.dto.SeckillExecution;
import com.wang.entity.SecKill;
import com.wang.exception.RepeatKillException;
import com.wang.exception.SeckillCloseException;
import com.wang.exception.SeckillException;

import java.util.List;

/**
 *  业务接口：站在“使用中”角度设计接口：
 *      1：方法定义粒度
 *      2：参数
 *      3：返回类型（return类型）
 */
public interface SecKillService {
    /**
     * 查询所有秒杀记录
     * @return
     */
    List<SecKill> getSeckillList();

    /**
     * 查阅单个秒杀记录
     * @param seckillId
     * @return
     */
    SecKill getById(long seckillId);

    /**
     * 秒杀开启是输出秒杀接口地址
     * 否则输出系统时间和秒杀时间
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 秒杀执行操作
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
        throws SeckillException,RepeatKillException,SeckillCloseException;

    /**
     * 秒杀执行操作by 存储过程
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5);

}
