package springboot.service;


import springboot.common.page.PageResult;
import springboot.common.utils.BaseService;
import springboot.entity.SystemintroEntity;
import springboot.query.SystemintroQuery;
import springboot.vo.SystemintroVO;

import java.util.List;

/**
 * 关于我们
 *
 * @since 1.0.0 2023-11-04
 */
public interface SystemintroService extends BaseService
		<SystemintroEntity> {

	PageResult<SystemintroVO> page(SystemintroQuery query);

	void save(SystemintroVO vo);

	void update(SystemintroVO vo);

	void delete(List
			            <Long> idList);
}