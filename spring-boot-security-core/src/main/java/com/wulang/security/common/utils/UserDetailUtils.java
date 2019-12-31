package com.wulang.security.common.utils;

import com.wulang.security.admin.entity.collection.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author: wulang
 * @date: 2019/11/22
 * @description:
 */
public class UserDetailUtils {
    public static User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
