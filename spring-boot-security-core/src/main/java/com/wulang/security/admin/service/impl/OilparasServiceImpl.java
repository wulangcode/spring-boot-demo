package com.wulang.security.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wulang.security.admin.entity.collection.Oilparas;
import com.wulang.security.admin.dao.OilparasDao;
import com.wulang.security.admin.service.OilparasService;
import org.springframework.stereotype.Service;


/**
 * (Oilparas)表服务实现类
 *
 * @author makejava
 * @since 2019-11-19 10:44:11
 */
@Service("oilparasService")
public class OilparasServiceImpl extends ServiceImpl<OilparasDao, Oilparas> implements OilparasService {
}