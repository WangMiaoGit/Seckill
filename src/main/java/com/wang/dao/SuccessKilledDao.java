package com.wang.dao;

import com.wang.entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;

/**
 * Created by 2017063001 on 2018/9/3.
 * 项目名称：Seckill
 * 类描述  ：
 * 创建人  ：MaxWang
 * 创建时间：2018/9/3 15:41
 * 修改人  ：2017063001
 * 修改时间：2018/9/3
 * 修改备注：
 */
public interface SuccessKilledDao {
    /**
     * 插入购买明细，可过滤重复  联合唯一主键
     * @param seckillId
     * @param userPhone
     * @return  插入的行数
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone")long userPhone);

    /**
     * 根据id查询SuccessKilled并携带秒杀产品对象实体
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId")long seckillId,@Param("userPhone")long userPhone);


}
