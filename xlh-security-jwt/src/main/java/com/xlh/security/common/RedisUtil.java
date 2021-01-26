package com.xlh.security.common;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author xulihua
 * @date 2021/1/25 17:06
 */
@Component
public class RedisUtil {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    /**
     * 获取普通值
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }
    /**
     * set-string
     *
     * @param str
     * @param obj
     */
    public boolean set(String str, Object obj) {
        if (ObjectUtil.isNull(str)) {
            return false;
        }
        redisTemplate.opsForValue().set(str, obj);
        return true;
    }
    /**
     * 设置某个key的超时时间
     *
     * @param key
     * @param time
     * @return
     */
    public boolean setExpire(String key, long time) {
        boolean empty = ObjectUtil.isNull(key) || time < 0;
        if (!empty) {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
            return true;
        }
        return false;
    }
    /**
     * 获取某个key的超时时间
     *
     * @param key
     * @return
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }
    /**
     * 根据key的名称判断某个key是否存在
     *
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }
    /**
     * 根据key来删除缓存
     *
     * @param keys
     * @return
     */
    public boolean del(String... keys) {
        List<String> keyList = Arrays.asList(keys);
        boolean empty = CollUtil.isNotEmpty(keyList);
        if (!empty) {
            redisTemplate.delete(keyList);
            return true;
        }
        return false;
    }
    /**
     * 添加k-v普通缓存(可设置超时时间)
     *
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean set(String key, String value, Long time) {
        if (ObjectUtil.isEmpty(key)) {
            return false;
        }
        if (!ObjectUtil.isEmpty(time) || time > 0) {
            redisTemplate.opsForValue().set(key, value, time);
        }
        return true;
    }
    /**
     * k-v类型递增
     *
     * @param key
     * @param data
     * @return
     */
    public Long incr(String key, Long data) {
        return redisTemplate.opsForValue().increment(key, data);
    }
    /**
     * 获取hash
     *
     * @param key
     * @param item
     * @return
     */
    public Object hGet(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }
    /**
     * 获取hashKey对应的所有值
     *
     * @param key
     * @return
     */
    public Map<Object, Object> hAllGet(String key) {
        return redisTemplate.opsForHash().entries(key);
    }
    /**
     * 添加hash
     *
     * @param key 键
     * @param map map值
     * @return
     */
    public boolean hmPull(String key, Map<String, Object> map) {
        // 若缓存中已经存在同名的key会报错
        if (!ObjectUtil.isEmpty(key) && !hasKey(key)) {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        }
        return false;
    }
    /**
     * 添加hash,设置超时时间
     *
     * @param key
     * @param map
     * @param time
     * @return
     */
    public boolean hmPull(String key, Map<String, Object> map, Long time) {
        if (!ObjectUtil.isEmpty(time) && time > 0) {
            hmPull(key, map);
            return redisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
        return false;
    }

    /**
     * 删除某个key下的指定map-key的hash值
     * @param key
     * @param mapKey
     * @return
     */
    public Long hdel(String key,String mapKey){
        return redisTemplate.opsForHash().delete(key, mapKey);
    }
    /**
     * hash递增/递减(负数则为减) 可以做浏览量,访问量,点赞量的业务
     *
     * @param key
     * @param item
     * @param data
     * @return
     */
    public Long hIncr(String key, String item, Long data) {
        return redisTemplate.opsForHash().increment(key, item, data);
    }
    /**
     * set添加
     *
     * @param key
     * @param values
     * @return
     */
    public Long setS(String key, Object... values) {
        return redisTemplate.opsForSet().add(key, values);
    }
    /**
     * 根据key获取Set里面的值
     *
     * @param key
     * @return
     */
    public Set<Object> getSet(String key) {
        return redisTemplate.opsForSet().members(key);
    }
    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long setRemove(String key, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    // ===============================list=================================
    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     * @return
     */
    public List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                setExpire(key, time);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean lSet(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean lSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                setExpire(key, time);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}

