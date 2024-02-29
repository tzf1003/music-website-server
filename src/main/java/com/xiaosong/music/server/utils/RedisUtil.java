package com.xiaosong.music.server.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 * @author Melo
 * 
 */

@Component
public final class RedisUtil {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * 指定缓存失效时间
	 * 
	 * @param key
	 *            键
	 * @param timeout
	 *            失效时间（秒）
	 * @return
	 */
	public boolean expire(String key, long timeout) {
		try {
			if (timeout > 0) {
				redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 根据key获取过期时间
	 * 
	 * @param key
	 *            键（不能为null)
	 * @return 过期时间（秒） 返回0代表为永久有效
	 */
	public long getExpire(String key) {
		return redisTemplate.getExpire(key, TimeUnit.SECONDS);
	}

	/**
	 * 判断key是否存在
	 * 
	 * @param key
	 *            键
	 * @return true:存在 fase:不存在
	 */
	public boolean hasKey(String key) {
		try {
			return redisTemplate.hasKey(key);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 获取所有键
	 * @param pattern 通配符
	 * @return set集合
	 */
	public Set<String> keys(String pattern) {
		return redisTemplate.keys(pattern);
	}

	/**
	 * 删除缓存
	 * 
	 * @param key
	 *            键（支持一个或多个）
	 */
	@SuppressWarnings("unchecked")
	public void del(String... key) {
		if (key != null && key.length > 0) {
			if (key.length == 1) {
				redisTemplate.delete(key[0]);
			} else {
				redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(key));
			}
		}
	}

	// ============================String=============================
	/**
	 * 普通缓存获取
	 * 
	 * @param key
	 *            键
	 * @return value 值
	 */
	public Object get(String key) {
		if (key != null) {
			return redisTemplate.opsForValue().get(key);
		}
		return null;
	}

	/**
	 * 普通缓存放入
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return true:成功 false:失败
	 */
	public boolean set(String key, Object value) {
		try {
			redisTemplate.opsForValue().set(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 普通缓存放入（设置有效时间）
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param time
	 *            有效时间（秒）time<=0表示永久有效
	 * @return
	 */
	public boolean set(String key, Object value, long time) {
		try {
			if (time > 0) {
				redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
			} else {
				set(key, value);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 递增
	 * 
	 * @param key
	 *            键
	 * @param delta
	 *            递增因子（大于0）
	 * @return
	 */
	public long incr(String key, long delta) {
		if (delta < 0) {
			throw new RuntimeException("递增因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, delta);
	}

	/**
	 * 递减
	 * 
	 * @param key
	 *            键
	 * @param delta
	 *            递减因子（大于0）
	 * @return
	 */
	public long decr(String key, long delta) {
		if (delta < 0) {
			throw new RuntimeException("递减因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, -delta);
	}

	// ================================Hash=================================
	/**
	 * HashGet
	 * @param key 键（不能为null）
	 * @param item 项（不能为null）
	 * @return 值
	 */
	public Object hget(String key, String item) {
		return redisTemplate.opsForHash().get(key, item);
	}

	/**
	 * 获取hashKey对应的所有键值
	 * @param key 键
	 * @return 对应的多个键值
	 */
	public Map<Object, Object> hmget(String key) {
		return redisTemplate.opsForHash().entries(key);
	}

	/**
	 * HashSet
	 * @param key 键
	 * @param map 对应多个键值
	 * @return true 成功 false 失败
	 */
	public boolean hmset(String key, Map<String, Object> map) {
		try {
			redisTemplate.opsForHash().putAll(key, map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * HashSet 并设置时间
	 * @param key  键
	 * @param map 对应多个键值
	 * @param time 时间(秒)
	 * @return true成功 false失败
	 */
	public boolean hmset(String key, Map<String, Object> map, long time) {
		try {
			redisTemplate.opsForHash().putAll(key, map);
			if (time > 0) {
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 向一张hash表中放入数据,如果不存在将创建
	 * 
	 * @param key 键
	 * @param item 项
	 * @param value 值
	 * @return true 成功 false失败
	 */
	public boolean hset(String key, String item, Object value) {
		try {
			redisTemplate.opsForHash().put(key, item, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 向一张hash表中放入数据,如果不存在将创建
	 * 
	 * @param key 键
	 * @param item 项
	 * @param value 值
	 * @param time 时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
	 * @return true 成功 false失败
	 */
	public boolean hset(String key, String item, Object value, long time) {
		try {
			if (time > 0) {
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 删除hash表中的值
	 * 
	 * @param key 键 不能为null
	 * @param item 项 可以使多个 不能为null
	 */
	public void hdel(String key, Object... item) {
		redisTemplate.opsForHash().delete(key, item);
	}

	/**
	 * 判断hash表中是否有该项的值
	 * 
	 * @param key 键 不能为null
	 * @param item 项 不能为null
	 * @return true 存在 false不存在
	 */
	public boolean hHasKey(String key, String item) {
		return redisTemplate.opsForHash().hasKey(key, item);
	}

	/**
	 * hash递增 如果不存在,就会创建一个 并把新增后的值返回
	 * 
	 * @param key  键
	 * @param item 项
	 * @param by 要增加几(大于0)
	 * @return
	 */
	public double hincr(String key, String item, double by) {
		return redisTemplate.opsForHash().increment(key, item, by);
	}

	/**
	 * hash递减
	 * 
	 * @param key 键
	 * @param item 项
	 * @param by 要减少记(小于0)
	 * @return
	 */
	public double hdecr(String key, String item, double by) {
		return redisTemplate.opsForHash().increment(key, item, -by);
	}

	// ============================set=============================
	/**
	 * 根据key获取Set中的所有值
	 * 
	 * @param key 键
	 * @return
	 */
	public Set<Object> sGet(String key) {
		try {
			return redisTemplate.opsForSet().members(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据value从一个set中查询,是否存在
	 * 
	 * @param key 键
	 * @param value 值
	 * @return true 存在 false不存在
	 */
	public boolean sHasKey(String key, Object value) {
		try {
			return redisTemplate.opsForSet().isMember(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 将数据放入set缓存
	 * 
	 * @param key 键
	 * @param values 值 可以是多个
	 * @return 成功个数
	 */
	public long sSet(String key, Object... values) {
		try {
			return redisTemplate.opsForSet().add(key, values);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 将set数据放入缓存
	 * 
	 * @param key 键
	 * @param time 时间(秒)
	 * @param values 值 可以是多个
	 * @return 成功个数
	 */
	public long sSetAndTime(String key, long time, Object... values) {
		try {
			Long count = redisTemplate.opsForSet().add(key, values);
			if (time > 0)
				expire(key, time);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
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
	 * @param key 键
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

	/**
	 * 获取两个 set 集合的交集
	 * @param key1 set集合key1
	 * @param key2 set集合key2
	 * @return set 集合的交集
	 */
	public Set<Object> sInter(String key1,String key2) {
		SetOperations<String, Object> opsForSet = redisTemplate.opsForSet();
		Set<Object> intersect = opsForSet.intersect(key1, key2);
		return intersect;
	}

	/**
	 * 判断是否 set 集合元素
	 * @param key set集合key
	 * @param value 元素
	 * @return true：是 false：不是
	 */
	public boolean sIsMember(String key,Object value) {
		SetOperations<String, Object> opsForSet = redisTemplate.opsForSet();
		Boolean member = opsForSet.isMember(key, value);
		return member;
	}

	/**
	 * 移除并返回集合中的一个随机元素
	 * @param key set 集合key
	 * @return 随机元素
	 */
	public Object sPop(String key) {
		SetOperations<String, Object> opsForSet = redisTemplate.opsForSet();
		Object pop = opsForSet.pop(key);
		return pop;
	}

	// ===============================list=================================
	/**
	 * 获取list缓存的内容
	 * 
	 * @param key 键
	 * @param start 开始
	 * @param end 结束 0 到 -1代表所有值
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
	 * @param key 键
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
	 * @param key 键
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
	 * @param key 键
	 * @param value 值
	 * @param time 时间(秒)
	 * @return
	 */
	public boolean lSet(String key, Object value, long time) {
		try {
			redisTemplate.opsForList().rightPush(key, value);
			if (time > 0)
				expire(key, time);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 将list放入缓存
	 * 
	 * @param key 键
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
	 * @param key 键
	 * @param value 值
	 * @param time 时间(秒)
	 * @return
	 */
	public boolean lSet(String key, List<Object> value, long time) {
		try {
			redisTemplate.opsForList().rightPushAll(key, value);
			if (time > 0)
				expire(key, time);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 根据索引修改list中的某条数据
	 * 
	 * @param key 键
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
	 * @param key 键
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

	// ===============================zset=================================
	/**
	 * 将数据放入zset缓存
	 *
	 * @param key 键
	 * @param typedTupleSet 值 通过TypedTuple方式新增数据
	 * @return 成功个数
	 */
	public long zadd(String key, Set<ZSetOperations.TypedTuple<Object>> typedTupleSet ) {
		try {
			return redisTemplate.opsForZSet().add(key, typedTupleSet);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 将数据放入zset缓存
	 *
	 * @param key 键
	 * @param value 元素
	 * @param score 分值
	 * @return 是否成功
	 */
	public boolean zadd(String key, Object value, double score ) {
		try {
			return redisTemplate.opsForZSet().add(key,value,score);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 获取 zset 指定区间的元素
	 * @param key 键
	 * @param start 起始
	 * @param end  结束
	 * @return Set集合
	 */
	public Set zrange(String key,long start, long end) {
		Set zSetValue = null;
		try {
			zSetValue = redisTemplate.opsForZSet().range(key,start,end);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return zSetValue;
	}

	/**
	 * 倒序获取 zset 指定区间的元素
	 * @param key 键
	 * @param start 起始
	 * @param end  结束
	 * @return Set集合
	 */
	public Set zrevrange(String key,long start, long end) {
		Set zSetValue = null;
		try {
			zSetValue = redisTemplate.opsForZSet().reverseRange(key,start,end);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return zSetValue;
	}

	/**
	 * 根据设置的score获取zset区间值
	 * @param key 键
	 * @param min 最小值（score）
	 * @param max 最大值（score）
	 * @return set集合
	 */
	public Set zrangeByscore(String key,double min,double max) {
		Set zSetValue = null;
		try {
			zSetValue = redisTemplate.opsForZSet().rangeByScore(key,min,max);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return zSetValue;
	}

	// ===============================transaction=================================

	/**
	 * 组装事务
	 */
	public void multi() {
		redisTemplate.multi();
	}

	/**
	 * 执行事务
	 */
	public void exec() {
		redisTemplate.exec();
	}

	/**
	 * 取消事务
	 */
	public void discard() {
		redisTemplate.discard();
	}

	/**
	 * 监控事务
	 * @param key 键
	 */
	public void watch(String key) {
		redisTemplate.watch(key);
	}

	//消息队列

	/**
	 * 存放消息到队列中
	 * @param key 键
	 * @param value 值
	 * @return 是否成功
	 */
	public boolean lPushMsg(String key,Object value) {
		try{
			redisTemplate.opsForList().leftPush(key,value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 从消息队列中弹出消息
	 * @param key 键
	 * @return
	 */
	public Object rPopMsg(String key) {
		try {
			return redisTemplate.opsForList().rightPop(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 查看消息
	 * @param key 键
	 * @param start 开始位置
	 * @param end 结束位置
	 * @return 消息列表
	 */
	public List<Object> getMsg(String key, long start, long end) {
		try {
			return redisTemplate.opsForList().range(key,start,end);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

