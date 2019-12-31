package com.wulang.batis.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wulang.batis.admin.config.DataSource;
import com.wulang.batis.admin.config.DataSourceEnum;
import com.wulang.batis.admin.dao.TestMapper;
import com.wulang.batis.admin.entity.Test;
import com.wulang.batis.admin.service.TestService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2019-12-10
 */
@Service
@DataSource(DataSourceEnum.DB1)
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements TestService {

}
