package com.wulang.security.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wulang.security.admin.dao.CollectionDeviceDataDao;
import com.wulang.security.admin.service.CollectionDeviceDataService;
import com.wulang.security.serialPort.model.Com;
import org.springframework.stereotype.Service;

/**
 * 船舶发动机数据(TbShipDeviceData)表服务实现类
 *
 * @author makejava
 * @since 2019-11-29 10:52:56
 */
@Service("tbShipDeviceDataService")
public class CollectionDeviceDataServiceIMpl extends ServiceImpl<CollectionDeviceDataDao, Com> implements CollectionDeviceDataService {
}