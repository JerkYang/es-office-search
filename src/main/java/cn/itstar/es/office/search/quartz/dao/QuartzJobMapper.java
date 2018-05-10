package cn.itstar.es.office.search.quartz.dao;

import org.mybatis.spring.annotation.MapperScan;

import cn.itstar.es.office.search.common.dao.BaseMapper;
import cn.itstar.es.office.search.quartz.entity.QuartzJobEntity;

/**
 * 定时任务
 *
 */
@MapperScan
public interface QuartzJobMapper extends BaseMapper<QuartzJobEntity> {

}
