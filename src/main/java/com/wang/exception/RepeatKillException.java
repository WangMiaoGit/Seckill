package com.wang.exception;

/**
 * Created by 2017063001 on 2018/9/4.
 * 项目名称：Seckill
 * 类描述  ：
 * 创建人  ：MaxWang
 * 创建时间：2018/9/4 12:08
 * 修改人  ：2017063001
 * 修改时间：2018/9/4
 * 修改备注：
 */

/**
 * 重复秒杀异常(运行期间异常)
 */
public class RepeatKillException extends SeckillException{

    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
