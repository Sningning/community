package sningning.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import sningning.community.util.RedisKeyUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author: Song Ningning
 * @date: 2020-08-24 21:00
 */
@Service
public class DataService {

    @Autowired
    private RedisTemplate redisTemplate;

    private SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");

    /**
     * 将指定的IP计入UV
     * @param ip
     */
    public void recordUV(String ip) {
        String redisKey = RedisKeyUtil.getUVKey(sdf.format(new Date()));
        redisTemplate.opsForHyperLogLog().add(redisKey, ip);
    }

    /**
     * 计算指定日期区间的UV
     * @param start
     * @param end
     * @return
     */
    public long calculateUV(Date start, Date end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("参数不能为空！");
        }

        // 整理该日期范围内的key
        List<String> keyList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        while (!calendar.getTime().after(end)) {
            String key = RedisKeyUtil.getUVKey(sdf.format(calendar.getTime()));
            keyList.add(key);
            calendar.add(Calendar.DATE, 1);
        }
        // 合并数据
        String redisKey = RedisKeyUtil.getUVKey(sdf.format(start), sdf.format(end));
        redisTemplate.opsForHyperLogLog().union(redisKey, keyList.toArray());

        // 返回统计的结果
        return redisTemplate.opsForHyperLogLog().size(redisKey);
    }

    /**
     * 将指定的用户计入DAU
     * @param userId
     */
    public void recordDAU(int userId) {
        String redisKey = RedisKeyUtil.getDAUKey(sdf.format(new Date()));
        redisTemplate.opsForValue().setBit(redisKey, userId, true);
    }

    /**
     * 计算指定日期区间的DAU
     * @param start
     * @param end
     * @return
     */
    public long calculateDAU(Date start, Date end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("参数不能为空！");
        }

        // 整理该日期范围内的key
        List<byte[]> keyList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        while (!calendar.getTime().after(end)) {
            String key = RedisKeyUtil.getDAUKey(sdf.format(calendar.getTime()));
            keyList.add(key.getBytes());
            calendar.add(Calendar.DATE, 1);
        }
        // 进行 OR 运算
        return (long) redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                String redisKey = RedisKeyUtil.getDAUKey(sdf.format(start), sdf.format(end));
                connection.bitOp(RedisStringCommands.BitOperation.OR,
                        redisKey.getBytes(), keyList.toArray(new byte[0][0]));
                return connection.bitCount(redisKey.getBytes());
            }
        });
    }
}
