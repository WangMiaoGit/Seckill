package com.wang.dao;

import com.wang.entity.SecKill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 2017063001 on 2018/9/3.
 * 项目名称：Seckill
 * 类描述  ：
 * 创建人  ：MaxWang
 * 创建时间：2018/9/3 15:38
 * 修改人  ：2017063001
 * 修改时间：2018/9/3
 * 修改备注：
 */
public interface SeckillDao {
    /**
     * 减库存
     * @param seckillId
     * @param killTime
     * @return 如果影响行数》1，表示更新的记录行数
     */
    int reduceNumber (@Param("seckillId") long seckillId,@Param("killTime") Date killTime);

    /**
     * 根据id查询秒杀对象
     * @param seckillId
     * @return
     */
    SecKill queryById(long seckillId);

    /**
     * 根据偏移量查询秒杀商品列表
     * @param offset
     * @param limit
     * @return
     */
    List<SecKill> queryAll(@Param("offset") int offset,@Param("limit") int limit);

    /**
     * 使用存储过程执行秒杀
     * @param paramMap
     */
    void killByProcedure(Map<String,Object> paramMap);
}
