package com.wang.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.wang.entity.SecKill;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import static com.sun.xml.internal.stream.Entity.ScannedEntity.DEFAULT_BUFFER_SIZE;

/**
 * Created by 2017063001 on 2018/9/18.
 * 项目名称：Seckill
 * 类描述  ：
 * 创建人  ：MaxWang
 * 创建时间：2018/9/18 16:32
 * 修改人  ：2017063001
 * 修改时间：2018/9/18
 * 修改备注：
 */
public class RedisDao {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JedisPool jedisPool;

    public RedisDao(String ip, int port) {
        jedisPool = new JedisPool(ip, port);
    }

    //序列化
    private RuntimeSchema<SecKill> schema = RuntimeSchema.createFrom(SecKill.class);

    public SecKill getSeckilkll(long seckillId) {
        //redis操作的逻辑
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:" + seckillId;
                //并没有实现内部序列化操作
                //get-->byte[]  -->反序列化-->Object(Seckill)
                //采用自定义序列化
                //protostuff : pojo.
                byte[] bytes = jedis.get(key.getBytes());
                //缓存中获取到了
                if (bytes != null) {
                    //空对象
                    SecKill secKill = schema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes, secKill, schema);
                    //seckill被反序列化
                    return secKill;
                }

                jedis.get(key);
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public String putSeckill(SecKill secKill) {

        //set  Object(SecKill) -->  序列化 -->byte[]
        try {
            Jedis jedis = jedisPool.getResource();

            try {
                String key = "seckill:" + secKill.getSeckillId();
                byte[] bytes = ProtostuffIOUtil.toByteArray(secKill, schema,
                        LinkedBuffer.allocate(DEFAULT_BUFFER_SIZE));
                //超时缓存
                int timeout = 60 * 60;//1H
                String result = jedis.setex(key.getBytes(), timeout, bytes);
                return result;

            } finally {
                jedis.close();
            }

        } catch (Exception e) {

        }
        return null;
    }
}
