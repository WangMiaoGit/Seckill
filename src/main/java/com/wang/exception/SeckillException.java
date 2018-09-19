package com.wang.exception;

/**
 * Created by 2017063001 on 2018/9/4.
 * 项目名称：Seckill
 * 类描述  ：
 * 创建人  ：MaxWang
 * 创建时间：2018/9/4 13:22
 * 修改人  ：2017063001
 * 修改时间：2018/9/4
 * 修改备注：
 */

/**
 * 秒杀业务异常
 */
public class SeckillException extends RuntimeException {

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
