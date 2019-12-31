package com.wulang.security.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wulang.security.admin.dao.TitleIconDao;
import com.wulang.security.admin.entity.monitor.TbTitleIcon;
import com.wulang.security.admin.service.TitleIconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: wulang
 * @date: 2019/11/29
 * @description:
 */
@Service
public class TitleIconServiceImpl extends ServiceImpl<TitleIconDao, TbTitleIcon> implements TitleIconService {

    @Autowired
    private TitleIconDao titleIconDao;

    @Override
    public Boolean updateByTitle(String title, Double value) {
        return titleIconDao.updateByTitle(title, value);
    }
}
