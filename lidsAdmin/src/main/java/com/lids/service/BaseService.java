package com.lids.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service基类
 */
@Transactional(readOnly = false)
public abstract  class BaseService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

}
