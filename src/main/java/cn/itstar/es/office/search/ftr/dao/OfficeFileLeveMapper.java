package cn.itstar.es.office.search.ftr.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import cn.itstar.es.office.search.common.dao.BaseMapper;
import cn.itstar.es.office.search.ftr.entity.SysRoleFileLeveEntity;

/**
 * 文件与文件级别的关系
 */
@MapperScan
public interface OfficeFileLeveMapper extends BaseMapper<SysRoleFileLeveEntity> {

	/**
	 * 根据文件级别 ID 查询出所有与之相关的文件
	 * @param fileLevelId
	 * @return
	 */
	List<Long> listFileId(Long fileLevelId);
	
	int batchRemoveByFileLevelId(Long[] id);
	
	int batchRemoveByfileId(Long[] id);
	
}
