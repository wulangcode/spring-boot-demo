package com.wulang.multievel.mapper;

import com.wulang.multievel.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

	public User findUserInfo();

}
