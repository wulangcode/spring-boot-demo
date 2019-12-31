package com.wulang.security.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wulang.security.admin.entity.collection.FerroPara;
import com.wulang.security.admin.dao.FerroParaDao;
import com.wulang.security.admin.service.FerroParaService;
import org.springframework.stereotype.Service;


/**
 * (FerroPara)表服务实现类
 *
 * @author makejava
 * @since 2019-11-19 10:43:23
 */
@Service("ferroParaService")
public class FerroParaServiceImpl extends ServiceImpl<FerroParaDao, FerroPara> implements FerroParaService{
}