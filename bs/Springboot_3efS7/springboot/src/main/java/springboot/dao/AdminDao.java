package springboot.dao;

import springboot.common.utils.BaseDao;
import springboot.entity.AdminEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 后台管理用户表
*
*/
@Mapper
public interface AdminDao extends BaseDao<AdminEntity> {

}