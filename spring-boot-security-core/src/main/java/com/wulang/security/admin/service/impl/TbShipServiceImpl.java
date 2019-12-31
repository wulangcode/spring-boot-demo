package com.wulang.security.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wulang.security.admin.dao.TbShipDao;
import com.wulang.security.admin.entity.monitor.TbShip;
import com.wulang.security.admin.service.TbShipService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 船舶(TbShip)表服务实现类
 *
 * @author makejava
 * @since 2019-11-29 10:24:10
 */
@Service("tbShipService")
public class TbShipServiceImpl extends ServiceImpl<TbShipDao, TbShip> implements TbShipService {

    @Override
    public List<TbShip> getList() {
        return super.list();
    }
}