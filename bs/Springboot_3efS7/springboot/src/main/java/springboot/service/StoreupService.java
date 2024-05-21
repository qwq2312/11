package springboot.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import springboot.common.page.PageResult;
import springboot.common.utils.BaseService;
import springboot.entity.StoreupEntity;
import springboot.query.StoreupQuery;
import springboot.vo.StoreupVO;

import java.util.List;
import java.util.Map;

/**
 * 收藏表
 */
public interface StoreupService extends BaseService<StoreupEntity> {

	PageResult<StoreupVO> page(StoreupQuery query);

	List<StoreupVO> queryList(StoreupQuery query);

	void save(StoreupVO vo);

	void update(StoreupVO vo);

	void delete(List<Long> idList);

	void export(StoreupQuery query);

	List<Map<String, Object>> selectValue(Map<String, Object> params, LambdaQueryWrapper<StoreupEntity> wrapper);

	List<Map<String, Object>> selectTimeStatValue(Map<String, Object> params, LambdaQueryWrapper<StoreupEntity> wrapper);

	List<Map<String, Object>> selectGroup(Map<String, Object> params, LambdaQueryWrapper<StoreupEntity> wrapper);
}