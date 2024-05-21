package springboot.dao;

import springboot.common.utils.BaseDao;
import springboot.entity.SystemintroEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 关于我们
 *
 * @since 1.0.0 2023-11-04
 */
@Mapper
public interface SystemintroDao extends BaseDao<SystemintroEntity> {

}