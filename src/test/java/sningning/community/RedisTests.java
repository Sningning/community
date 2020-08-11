package sningning.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
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
}
