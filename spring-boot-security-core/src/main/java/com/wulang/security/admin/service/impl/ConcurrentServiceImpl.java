package com.wulang.security.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wulang.security.admin.dao.ConcurretDao;
import com.wulang.security.admin.entity.collection.Concurrent;
import com.wulang.security.admin.service.ConcurrentService;
import org.springframework.stereotype.Service;

/**
 * @author: wulang
 * @date: 2019/12/9
 * @description:
 */
@Service
public class ConcurrentServiceImpl extends ServiceImpl<ConcurretDao, Concurrent> implements ConcurrentService {
}
