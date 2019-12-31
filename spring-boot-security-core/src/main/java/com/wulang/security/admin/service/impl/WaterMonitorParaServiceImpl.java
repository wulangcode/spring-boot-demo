package com.wulang.security.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wulang.security.admin.entity.collection.WaterMonitorPara;
import com.wulang.security.admin.dao.WaterMonitorParaDao;
import com.wulang.security.admin.service.WaterMonitorParaService;
import org.springframework.stereotype.Service;


/**
 * (WaterMonitorPara)表服务实现类
 *
 * @author makejava
 * @since 2019-11-19 10:44:11
 */
@Service("waterMonitorParaService")
public class WaterMonitorParaServiceImpl extends ServiceImpl<WaterMonitorParaDao, WaterMonitorPara> implements WaterMonitorParaService {
}