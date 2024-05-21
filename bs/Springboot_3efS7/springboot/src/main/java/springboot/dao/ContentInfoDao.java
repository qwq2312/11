package springboot.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import springboot.common.utils.BaseDao;
import springboot.entity.ContentInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * 新闻表
 */
@Mapper
public interface ContentInfoDao extends BaseDao<ContentInfoEntity> {

	List<Map<String, Object>> selectValue(@Param("params") Map<String, Object> params, @Param(Constants.WRAPPER) LambdaQueryWrapper<ContentInfoEntity> wrapper);

	List<Map<String, Object>> selectTimeStatValue(@Param("params") Map<String, Object> params, @Param(Constants.WRAPPER) LambdaQueryWrapper<ContentInfoEntity> wrapper);

	List<Map<String, Object>> selectGroup(@Param("params") Map<String, Object> params, @Param(Constants.WRAPPER) LambdaQueryWrapper<ContentInfoEntity> wrapper);
}