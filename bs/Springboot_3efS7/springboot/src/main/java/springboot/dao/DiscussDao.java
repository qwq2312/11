package springboot.dao;


import org.apache.ibatis.annotations.Mapper;
import springboot.common.utils.BaseDao;
import springboot.entity.DiscussEntity;

/**
 * 评论表
 *
 * @since 1.0.0 2023-11-07
 */
@Mapper
public interface DiscussDao extends BaseDao<DiscussEntity> {

	void like(Long id);
}