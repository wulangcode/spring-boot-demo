package com.wulang.batis.admin.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Order(-1)  设为负数，是为了加载顺序提前，保证在@Transactional之前执行
 *
 * @author wulang
 * @create 2019/12/10/17:25
 */
@Component
@Slf4j
@Aspect
@Order(-1)
public class DataSourceAspect {
    /**
     * Pointcut：切入点
     *
     * java动态代理--利用反射机制生成一个实现代理接口的匿名类，在调用具体方法前调用InvokeHandler来处理。（基于接口）
     *
     * cglib动态代理--利用asm开源包，对代理对象类的class文件加载进来，通过修改其字节码生成子类来处理。（基于继承）
     *
     * 对比：cglib代理比jdk代理快
     *
     * @within和@annotation的区别：
     *
     * @within 对象级别 
     *
     * @annotation 方法级别
     */
    @Pointcut("@within(com.wulang.batis.admin.config.DataSource) || @annotation(com.wulang.batis.admin.config.DataSource)")
    public void pointCut(){

    }

    @Before("pointCut() && @annotation(dataSource)")
    public void doBefore(DataSource dataSource){
        log.info("选择数据源---"+dataSource.value().getValue());
        DataSourceContextHolder.setDataSource(dataSource.value().getValue());
    }

    @After("pointCut()")
    public void doAfter(){
        DataSourceContextHolder.clear();
    }
}
