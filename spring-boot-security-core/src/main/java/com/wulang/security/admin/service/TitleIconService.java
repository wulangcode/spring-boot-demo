package com.wulang.security.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wulang.security.admin.entity.monitor.TbTitleIcon;

/**
 * @author: wulang
 * @date: 2019/11/29
 * @description:
 */
public interface TitleIconService extends IService<TbTitleIcon> {
    Boolean updateByTitle(String title, Double value);
}
