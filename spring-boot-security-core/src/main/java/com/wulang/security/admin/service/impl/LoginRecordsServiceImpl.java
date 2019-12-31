package com.wulang.security.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wulang.security.admin.dao.LoginRecordDao;
import com.wulang.security.admin.entity.collection.LoginRecord;
import com.wulang.security.admin.service.LoginRecordsService;
import org.springframework.stereotype.Service;

/**
 * @author: wulang
 * @date: 2019/11/27
 * @description:
 */
@Service
public class LoginRecordsServiceImpl extends ServiceImpl<LoginRecordDao, LoginRecord> implements LoginRecordsService {
}
