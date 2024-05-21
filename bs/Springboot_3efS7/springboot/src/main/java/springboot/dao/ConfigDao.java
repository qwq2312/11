package springboot.dao;

import springboot.common.utils.BaseDao;
import springboot.entity.ConfigEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 配置文件
*
*/
@Mapper
public interface ConfigDao extends BaseDao<ConfigEntity> {

}