package com.wulang.batis.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wulang.batis.admin.config.DataSource;
import com.wulang.batis.admin.config.DataSourceEnum;
import com.wulang.batis.admin.dao.TwoMapper;
import com.wulang.batis.admin.entity.Two;
import com.wulang.batis.admin.service.TwoService;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2019-12-10
 */
@Service
public class TwoServiceImpl extends ServiceImpl<TwoMapper, Two> implements TwoService {

    /**
     * 这里为设置第二个数据源
     * @param id
     * @return
     */
    @Override
    @DataSource(DataSourceEnum.DB2)
    public Two getById(Serializable id) {
        return super.getById(id);
    }
}
