package springboot.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import springboot.common.page.PageResult;
import springboot.common.utils.BaseService;
import springboot.entity.ContentInfoEntity;
import springboot.query.ContentInfoQuery;
import springboot.vo.ContentInfoVO;

import java.util.List;
import java.util.Map;

/**
 * 新闻表
 */
public interface ContentInfoService extends BaseService<ContentInfoEntity> {

	PageResult<ContentInfoVO> page(ContentInfoQuery query);

	List<ContentInfoVO> queryList(ContentInfoQuery query);

	void save(ContentInfoVO vo);

	void update(ContentInfoVO vo);

	void delete(List<Long> idList);

	void export(ContentInfoQuery query);

	List<ContentInfoVO> recommend(Long userId);

	List<Map<String, Object>> selectGroup(Map<String, Object> params, LambdaQueryWrapper<ContentInfoEntity> wrapper);

	List<Map<String, Object>> selectTimeStatValue(Map<String, Object> params, LambdaQueryWrapper<ContentInfoEntity> wrapper);

	List<Map<String, Object>> selectValue(Map<String, Object> params, LambdaQueryWrapper<ContentInfoEntity> wrapper);
}