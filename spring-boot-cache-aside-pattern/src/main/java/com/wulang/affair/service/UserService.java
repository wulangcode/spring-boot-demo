package com.wulang.affair.service;

import com.wulang.affair.model.User;

/**
 * 用户Service接口
 * @author Administrator
 *
 */
public interface UserService {

	/**
	 * 查询用户信息
	 * @return 用户信息
	 */
	public User findUserInfo();

	/**
	 * 查询redis中缓存的用户信息
	 * @return
	 */
	public User getCachedUserInfo();

}
