package springboot.dao;

import springboot.common.utils.BaseDao;
import springboot.entity.PostsEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 帖子详情
*
*/
@Mapper
public interface PostsDao extends BaseDao<PostsEntity> {

}