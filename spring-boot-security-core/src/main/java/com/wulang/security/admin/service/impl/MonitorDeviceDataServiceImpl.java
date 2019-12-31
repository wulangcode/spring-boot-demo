package com.wulang.security.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wulang.security.admin.dao.MonitorDeviceDataDao;
import com.wulang.security.admin.entity.monitor.MonitorDeviceData;
import com.wulang.security.admin.service.MonitorDeviceDataService;
import org.springframework.stereotype.Service;

/**
 * 船舶发动机数据(TbShipDeviceData)表服务实现类
 *
 * @author makejava
 * @since 2019-11-29 10:24:10
 */
@Service
public class MonitorDeviceDataServiceImpl extends ServiceImpl<MonitorDeviceDataDao, MonitorDeviceData> implements MonitorDeviceDataService {

    @Override
    public Boolean saveData(MonitorDeviceData monitorDeviceData) {
        return super.save(monitorDeviceData);
    }
}