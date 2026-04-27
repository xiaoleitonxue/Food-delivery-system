package com.sky.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;

import java.io.DataOutputStream;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

//@SpringBootTest
public class SpringDataRedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 测试RedisTemplate
     */
    @Test
    public void testRedis() {
        System.out.println(redisTemplate);
        ValueOperations valueOperations = redisTemplate.opsForValue();
        HashOperations hashOperations = redisTemplate.opsForHash();
        ListOperations listOperations = redisTemplate.opsForList();
        SetOperations setOperations = redisTemplate.opsForSet();
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
    }

    /**
     * 测试String
     */
    @Test
    public void testString() {
        //set get
        redisTemplate.opsForValue().set("name", "sky");
        String name = (String) redisTemplate.opsForValue().get("name");
        System.out.println(name);

        redisTemplate.opsForValue().set("age", 18, 30, TimeUnit.SECONDS);
        redisTemplate.opsForValue().setIfAbsent("lock", 1);
        redisTemplate.opsForValue().setIfAbsent("lock", 2);
        System.out.println(redisTemplate.opsForValue().get("lock"));
    }

    /**
     * 测试Hash
     */
    @Test
    public void testHash() {
        //put putIfAbsent delete
        redisTemplate.opsForHash().put("user", "name", "sky");
        redisTemplate.opsForHash().put("user", "age", 18);
        redisTemplate.opsForHash().put("user", "sex", "男");
        System.out.println(redisTemplate.opsForHash().get("user", "name"));
        System.out.println(redisTemplate.opsForHash().get("user", "age"));
        System.out.println(redisTemplate.opsForHash().get("user", "sex"));

        HashOperations hashOperations = redisTemplate.opsForHash();

        Set keys = hashOperations.keys("user");
        System.out.println(keys);

        List values = hashOperations.values("user");
        System.out.println(values);

        redisTemplate.opsForHash().delete("user", "name", "age");
    }

    /**
     * 测试List
     */
    @Test
    public void testList(){
        //lpush lpushx lpop rpush rpushx rpop
        ListOperations listOperations = redisTemplate.opsForList();

        listOperations.leftPushAll("mylist", "a", "b", "c");
        listOperations.leftPush("mylist","d");

        List mylist = listOperations.range("mylist", 0, -1);
        System.out.println(mylist);

        listOperations.rightPop("mylist");

        Long size = listOperations.size("mylist");
        System.out.println(size);
    }

    /**
     * 测试Set
     */
    @Test
    public void testSet(){
        //sadd smembers scard sinter sunion srem
        SetOperations setOperations = redisTemplate.opsForSet();

        setOperations.add("set1", "a", "b", "c", "d");
        setOperations.add("set2", "a", "b", "x", "y");

        Set members = setOperations.members("set1");
        System.out.println(members);

        Long size = setOperations.size("set1");
        System.out.println(size);

        Set intersect = setOperations.intersect("set1", "set2");
        System.out.println(intersect);

        Set union = setOperations.union("set1", "set2");
        System.out.println(union);

        setOperations.remove("set1", "a", "b");
    }


    /**
     * 测试Zset
     */
    @Test
    public void testZset(){
        //zadd zrange zincrby zrem
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();

        zSetOperations.add("zset1", "a", 10);
        zSetOperations.add("zset1", "b", 12);
        zSetOperations.add("zset1", "c", 9);

        Set zset1 = zSetOperations.range("zset1", 0, -1);
        System.out.println(zset1);

        zSetOperations.incrementScore("zset1", "c", 10);

        zSetOperations.remove("zset1", "a", "b");
    }


    /**
     * 测试通用方法
     */
    @Test
    public void testCommon(){
        //keys exists type del
        Set keys = redisTemplate.keys("*");
        System.out.println(keys);

        Boolean name = redisTemplate.hasKey("name");
        Boolean set1 = redisTemplate.hasKey("set1");

        for (Object key : keys) {
            DataType type = redisTemplate.type(key);
            System.out.println(type.name());
        }

        redisTemplate.delete("mylist");
    }
}
