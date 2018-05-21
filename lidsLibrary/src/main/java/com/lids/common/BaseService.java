package com.lids.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service基类
 *
 * @author yaoyou
 */
public abstract class BaseService{

    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

}