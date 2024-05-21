package springboot.dao;

import org.apache.ibatis.annotations.Mapper;
import springboot.common.utils.BaseDao;
import springboot.entity.PostcategoriesEntity;

/**
 * 帖子话题分类
 */
@Mapper
public interface PostcategoriesDao extends BaseDao<PostcategoriesEntity> {

}