package com.wulang.security.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wulang.security.admin.entity.collection.GranMonitorPara;
import com.wulang.security.admin.dao.GranMonitorParaDao;
import com.wulang.security.admin.service.GranMonitorParaService;
import org.springframework.stereotype.Service;


/**
 * (GranMonitorPara)表服务实现类
 *
 * @author makejava
 * @since 2019-11-19 10:44:11
 */
@Service("granMonitorParaService")
public class GranMonitorParaServiceImpl extends ServiceImpl<GranMonitorParaDao, GranMonitorPara> implements GranMonitorParaService {
}