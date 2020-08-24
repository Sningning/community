package sningning.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @author: Song Ningning
 * @date: 2020-08-11 22:02
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class RedisTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testString() {
        String redisKey = "test:count";

        redisTemplate.opsForValue().set(redisKey, 1);

        System.out.println(redisTemplate.opsForValue().get(redisKey));
        System.out.println(redisTemplate.opsForValue().increment(redisKey));
        System.out.println(redisTemplate.opsForValue().decrement(redisKey));
    }

    @Test
    public void testHash() {
        String redisKey = "test:user";

        redisTemplate.opsForHash().put(redisKey, "name", "zhangsan");
        redisTemplate.opsForHash().put(redisKey, "age", 25);

        System.out.println(redisTemplate.opsForHash().get(redisKey, "name"));
        System.out.println(redisTemplate.opsForHash().get(redisKey, "age"));
    }

    @Test
    public void testList() {
        String redisKey = "test:list";

        redisTemplate.opsForList().leftPush(redisKey, "aaa");
        redisTemplate.opsForList().leftPush(redisKey, "bbb");
        redisTemplate.opsForList().leftPush(redisKey, "ccc");

        System.out.println(redisTemplate.opsForList().range(redisKey, 0, -1));
        System.out.println(redisTemplate.opsForList().rightPop(redisKey));
        System.out.println(redisTemplate.opsForList().range(redisKey, 0, -1));
        System.out.println(redisTemplate.opsForList().index(redisKey, 0));
    }

    @Test
    public void testSet() {
        String redisKey = "test:set";

        redisTemplate.opsForSet().add(redisKey, "aaa", "bbb", "ccc");

        System.out.println(redisTemplate.opsForSet().members(redisKey));
        System.out.println(redisTemplate.opsForSet().members(redisKey));
        System.out.println(redisTemplate.opsForSet().isMember(redisKey, "aaa"));
        System.out.println(redisTemplate.opsForSet().isMember(redisKey, "ddd"));
    }

    @Test
    public void testSortedSet() {
        String redisKey = "test:students";

        redisTemplate.opsForZSet().add(redisKey, "小王", 80);
        redisTemplate.opsForZSet().add(redisKey, "小张", 60);
        redisTemplate.opsForZSet().add(redisKey, "小李", 70);
        redisTemplate.opsForZSet().add(redisKey, "小宋", 90);

        System.out.println(redisTemplate.opsForZSet().zCard(redisKey));
        System.out.println(redisTemplate.opsForZSet().range(redisKey, 0, -1));
        System.out.println(redisTemplate.opsForZSet().reverseRange(redisKey, 0, -1));
        System.out.println(redisTemplate.opsForZSet().score(redisKey, "小宋"));
    }

    @Test
    public void testKeys() {
        redisTemplate.delete("test:user");
        System.out.println(redisTemplate.hasKey("test:user"));
        redisTemplate.expire("test:students", 10, TimeUnit.SECONDS);
    }

    /**
     * 多次访问一个key
     */
    @Test
    public void testBoundOperation() {
        String redisKey = "test:count";
        BoundValueOperations operations = redisTemplate.boundValueOps(redisKey);
        System.out.println(operations.increment());
        System.out.println(operations.increment());
        System.out.println(operations.get());
    }

    /**
     * 编程式事务
     */
    @Test
    public void testTransaction() {
        Object obj = redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                String redisKey = "test:tx";
                // 启用事务
                redisOperations.multi();

                redisOperations.opsForSet().add(redisKey, "张三");
                redisOperations.opsForSet().add(redisKey, "李四");
                redisOperations.opsForSet().add(redisKey, "王五");

                System.out.println(redisOperations.opsForSet().members(redisKey));

                // 提交事务
                return redisOperations.exec();
            }
        });
        System.out.println(obj);
    }

    /**
     * 统计20w个重复数据的独立总数
     */
    @Test
    public void testHyperLogLog() {
        String redisKey = "test:hll:01";
        for (int i = 1; i <= 100000; i++) {
            redisTemplate.opsForHyperLogLog().add(redisKey, i);
        }

        for (int i = 1; i <= 100000; i++) {
            int r = (int) (Math.random() * 100000 + 1);
            redisTemplate.opsForHyperLogLog().add(redisKey, r);
        }

        long size = redisTemplate.opsForHyperLogLog().size(redisKey);
        System.out.println(size);
    }

    /**
     * 将3组数据合并，再统计合并后的数据的独立总数
     */
    @Test
    public void testHyperLogLogUnion() {
        String redisKey2 = "test:hll:02";
        for (int i = 1; i <= 10000; i++) {
            redisTemplate.opsForHyperLogLog().add(redisKey2, i);
        }

        String redisKey3 = "test:hll:03";
        for (int i = 5001; i <= 15000; i++) {
            redisTemplate.opsForHyperLogLog().add(redisKey3, i);
        }

        String redisKey4 = "test:hll:04";
        for (int i = 10001; i <= 20000; i++) {
            redisTemplate.opsForHyperLogLog().add(redisKey4, i);
        }

        String unionKey = "test:hll:union";
        redisTemplate.opsForHyperLogLog().union(unionKey,redisKey2, redisKey3, redisKey4);

        long size = redisTemplate.opsForHyperLogLog().size(unionKey);
        System.out.println(size);
    }

    /**
     * 统计一组数据的布尔值
     */
    @Test
    public void testBitMap() {
        String redisKey = "test:bm:01";

        // 记录
        redisTemplate.opsForValue().setBit(redisKey, 1, true);
        redisTemplate.opsForValue().setBit(redisKey, 4, true);
        redisTemplate.opsForValue().setBit(redisKey, 7, true);

        // 查询
        System.out.println(redisTemplate.opsForValue().getBit(redisKey, 0));
        System.out.println(redisTemplate.opsForValue().getBit(redisKey, 1));
        System.out.println(redisTemplate.opsForValue().getBit(redisKey, 2));
        System.out.println(redisTemplate.opsForValue().getBit(redisKey, 4));

        // 统计
        Object obj = redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.bitCount(redisKey.getBytes());
            }
        });

        System.out.println(obj);
    }

    /**
     * 统计3组数据的布尔值，并对这3组数据做OR运算
     */
    @Test
    public void testBitMapOperation() {
        String redisKey2 = "test:bm:02";
        // 记录
        redisTemplate.opsForValue().setBit(redisKey2, 0, true);
        redisTemplate.opsForValue().setBit(redisKey2, 1, true);
        redisTemplate.opsForValue().setBit(redisKey2, 2, true);

        String redisKey3 = "test:bm:03";
        // 记录
        redisTemplate.opsForValue().setBit(redisKey3, 2, true);
        redisTemplate.opsForValue().setBit(redisKey3, 3, true);
        redisTemplate.opsForValue().setBit(redisKey3, 4, true);

        String redisKey4 = "test:bm:04";
        // 记录
        redisTemplate.opsForValue().setBit(redisKey4, 4, true);
        redisTemplate.opsForValue().setBit(redisKey4, 5, true);
        redisTemplate.opsForValue().setBit(redisKey4, 6, true);

        String redisKey = "test:bm:or";
        Object obj = redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.bitOp(RedisStringCommands.BitOperation.OR,
                        redisKey.getBytes(), redisKey2.getBytes(), redisKey3.getBytes(), redisKey4.getBytes());
                return connection.bitCount(redisKey.getBytes());
            }
        });

        System.out.println(obj);
        System.out.println(redisTemplate.opsForValue().getBit(redisKey, 0));
        System.out.println(redisTemplate.opsForValue().getBit(redisKey, 1));
        System.out.println(redisTemplate.opsForValue().getBit(redisKey, 2));
        System.out.println(redisTemplate.opsForValue().getBit(redisKey, 3));
        System.out.println(redisTemplate.opsForValue().getBit(redisKey, 4));
        System.out.println(redisTemplate.opsForValue().getBit(redisKey, 5));
        System.out.println(redisTemplate.opsForValue().getBit(redisKey, 6));

    }
}
