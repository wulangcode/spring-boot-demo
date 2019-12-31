package com.wulang.security.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wulang.security.admin.entity.monitor.MonitorDeviceData;

/**
 * 船舶发动机数据(TbShipDeviceData)表服务接口
 *
 * @author makejava
 * @since 2019-11-29 10:24:10
 */
public interface MonitorDeviceDataService extends IService<MonitorDeviceData> {

    Boolean saveData(MonitorDeviceData monitorDeviceData);
}