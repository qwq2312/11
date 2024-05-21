package springboot.dao;

import springboot.common.utils.BaseDao;
import springboot.entity.CategoryInfoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 新闻分类
*
*/
@Mapper
public interface CategoryInfoDao extends BaseDao<CategoryInfoEntity> {

}