package com.wulang.security.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wulang.security.admin.dao.TbDeviceTypeDao;
import com.wulang.security.admin.entity.collection.TbDeviceType;
import com.wulang.security.admin.service.TbDeviceTypeService;
import org.springframework.stereotype.Service;

/**
 * (TbDeviceType)表服务实现类
 *
 * @author makejava
 * @since 2019-11-29 10:24:10
 */
@Service("tbDeviceTypeService")
public class TbDeviceTypeServiceImpl extends ServiceImpl<TbDeviceTypeDao, TbDeviceType> implements TbDeviceTypeService {
}