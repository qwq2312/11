package springboot.service;


import springboot.common.page.PageResult;
import springboot.common.utils.BaseService;
import springboot.entity.DiscussEntity;
import springboot.query.DiscussQuery;
import springboot.vo.DiscussVO;

import java.util.List;

/**
 * 评论表
 *
 * @since 1.0.0 2023-11-07
 */
public interface DiscussService extends BaseService<DiscussEntity> {
	PageResult<DiscussVO> page(DiscussQuery query);

	void save(DiscussVO vo);

	void update(DiscussVO vo);

	void delete(List<Long> idList);

	List<DiscussVO> queryList(DiscussQuery query);

	void like(Long id);
}