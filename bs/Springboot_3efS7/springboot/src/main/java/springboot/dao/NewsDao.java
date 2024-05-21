package springboot.dao;

import springboot.common.utils.BaseDao;
import springboot.entity.NewsEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 公告信息
*
*/
@Mapper
public interface NewsDao extends BaseDao<NewsEntity> {

}