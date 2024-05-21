package springboot.dao;

import springboot.common.utils.BaseDao;
import springboot.entity.TextContentEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 
* @since 1.0.0 2023-11-16
*/
@Mapper
public interface TextContentDao extends BaseDao<TextContentEntity> {

}