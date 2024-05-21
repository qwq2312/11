package springboot.service;

import springboot.common.page.PageResult;
import springboot.common.utils.BaseService;
import springboot.entity.PostcategoriesEntity;
import springboot.query.PostcategoriesQuery;
import springboot.vo.PostcategoriesVO;

import java.util.List;

/**
 * 帖子话题分类
 */
public interface PostcategoriesService extends BaseService<PostcategoriesEntity> {

	PageResult<PostcategoriesVO> page(PostcategoriesQuery query);

	List<PostcategoriesVO> queryList(PostcategoriesQuery query);

	void save(PostcategoriesVO vo);

	void update(PostcategoriesVO vo);

	void delete(List<Long> idList);

	void export(PostcategoriesQuery query);
}