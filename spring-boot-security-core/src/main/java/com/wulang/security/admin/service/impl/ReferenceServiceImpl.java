package com.wulang.security.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wulang.security.admin.dao.ReferenceDao;
import com.wulang.security.admin.entity.collection.ReferenceValue;
import com.wulang.security.admin.service.ReferenceService;
import org.springframework.stereotype.Service;

/**
 * (FerroPara)表服务实现类
 *
 * @author makejava
 * @since 2019-11-19 10:43:23
 */
@Service
public class ReferenceServiceImpl extends ServiceImpl<ReferenceDao, ReferenceValue> implements ReferenceService {
}