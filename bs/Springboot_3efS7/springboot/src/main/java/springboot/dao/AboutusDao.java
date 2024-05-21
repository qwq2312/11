package springboot.dao;

import springboot.common.utils.BaseDao;
import springboot.entity.AboutusEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 关于我们
*
*/
@Mapper
public interface AboutusDao extends BaseDao<AboutusEntity> {

}