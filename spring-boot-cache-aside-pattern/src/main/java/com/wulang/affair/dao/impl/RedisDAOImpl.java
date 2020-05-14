package com.wulang.affair.dao.impl;

import com.wulang.affair.dao.RedisDAO;
import com.wulang.affair.utils.RedisClusterUtils;
import org.springframework.stereotype.Repository;


@Repository("redisDAO")
public class RedisDAOImpl implements RedisDAO {

	@Override
	public void set(String key, String value) {
        RedisClusterUtils.setString(key,value);
	}

	@Override
	public String get(String key) {
        return RedisClusterUtils.getString(key);
	}

    @Override
    public void delete(String key) {
        RedisClusterUtils.delString(key);
    }

}
