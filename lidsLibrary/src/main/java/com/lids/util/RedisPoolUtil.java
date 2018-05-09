package com.lids.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public final class RedisPoolUtil {

    private static JedisPool jedisPool;
    private static Integer MAX_TOTAL = 1024;
    private static Integer MAX_IDLE = 200;
    private static Integer MAX_WAIT_MILLIS = 10000;
    private static Integer TIMEOUT = 10000;
    private static Boolean TEST_ON_BORROW = true;
    private static String serverIP = "120.77.39.33";
    private static String serverPassword = "123456";

    static{
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_TOTAL);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT_MILLIS);
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new JedisPool(config,serverIP,6379,TIMEOUT,serverPassword);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public synchronized static Jedis getJedis(){
        try {
            if(jedisPool != null){
                Jedis jedis = jedisPool.getResource();
                return jedis;
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void returnResource(final Jedis jedis){
        //方法参数被声明为final，表示它是只读的。
        if(jedis!=null){
//            jedisPool.returnResource(jedis);
            //取代jedisPool.returnResource(jedis)方法将3.0版本开始
            jedis.close();
        }
    }

}
