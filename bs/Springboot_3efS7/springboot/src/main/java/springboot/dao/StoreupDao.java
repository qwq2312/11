package springboot.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import springboot.common.utils.BaseDao;
import springboot.entity.StoreupEntity;
import springboot.vo.StoreupVO;

import java.util.List;
import java.util.Map;

/**
 * 收藏表
 */
@Mapper
public interface StoreupDao extends BaseDao<StoreupEntity> {

	List<StoreupVO> selectAll();

	List<Map<String, Object>> selectValue(Map<String, Object> params, LambdaQueryWrapper<StoreupEntity> wrapper);

	List<Map<String, Object>> selectTimeStatValue(Map<String, Object> params, LambdaQueryWrapper<StoreupEntity> wrapper);

	List<Map<String, Object>> selectGroup(Map<String, Object> params, LambdaQueryWrapper<StoreupEntity> wrapper);
}