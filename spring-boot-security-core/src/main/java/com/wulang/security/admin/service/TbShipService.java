package com.wulang.security.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wulang.security.admin.entity.monitor.TbShip;

import java.util.List;

/**
 * 船舶(TbShip)表服务接口
 *
 * @author makejava
 * @since 2019-11-29 10:24:10
 */
public interface TbShipService extends IService<TbShip> {

    List<TbShip>  getList();
}