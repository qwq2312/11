package springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.common.impl.BaseServiceImpl;
import springboot.common.page.PageResult;
import springboot.common.utils.DateUtils;
import springboot.common.utils.ExcelUtils;
import springboot.common.utils.GloabUtils;
import springboot.convert.StoreupConvert;
import springboot.dao.StoreupDao;
import springboot.entity.StoreupEntity;
import springboot.query.StoreupQuery;
import springboot.service.StoreupService;
import springboot.vo.StoreupVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 收藏表
 */
@Service
@AllArgsConstructor
public class StoreupServiceImpl extends BaseServiceImpl<StoreupDao, StoreupEntity> implements StoreupService {

	@Override
	public PageResult<StoreupVO> page(StoreupQuery query) {
		IPage<StoreupEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
		return new PageResult<>(StoreupConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
	}

	@Override
	public List<StoreupVO> queryList(StoreupQuery query) {
		return StoreupConvert.INSTANCE.convertList(baseMapper.selectList(getWrapper(query)));
	}

	private LambdaQueryWrapper<StoreupEntity> getWrapper(StoreupQuery query) {
		LambdaQueryWrapper<StoreupEntity> wrapper = Wrappers.lambdaQuery();
		if (GloabUtils.isNotAdmin()) {
			query.setUserid(GloabUtils.getUserId());
		}
		wrapper.eq(null != query.getId(), StoreupEntity::getId, query.getId());
		wrapper.between(ArrayUtils.isNotEmpty(query.getAddtime()), StoreupEntity::getAddtime, ArrayUtils.isNotEmpty(query.getAddtime()) ? query.getAddtime()[0] : null, ArrayUtils.isNotEmpty(query.getAddtime()) ? query.getAddtime()[1] : null);
		wrapper.eq(null != query.getUserid(), StoreupEntity::getUserid, query.getUserid());
		wrapper.eq(null != query.getRefid(), StoreupEntity::getRefid, query.getRefid());
		wrapper.eq(StringUtils.isNotEmpty(query.getTablename()), StoreupEntity::getTablename, query.getTablename());
		wrapper.eq(StringUtils.isNotEmpty(query.getName()), StoreupEntity::getName, query.getName());
		wrapper.eq(StringUtils.isNotEmpty(query.getPicture()), StoreupEntity::getPicture, query.getPicture());
		wrapper.eq(null != query.getType(), StoreupEntity::getType, query.getType());
		wrapper.eq(StringUtils.isNotEmpty(query.getInteltype()), StoreupEntity::getInteltype, query.getInteltype());
		wrapper.eq(StringUtils.isNotEmpty(query.getRemark()), StoreupEntity::getRemark, query.getRemark());
		return wrapper;
	}

	@Override
	public void save(StoreupVO vo) {
		StoreupEntity entity = StoreupConvert.INSTANCE.convert(vo);
		baseMapper.insert(entity);
	}

	@Override
	public void update(StoreupVO vo) {
		StoreupEntity entity = StoreupConvert.INSTANCE.convert(vo);
		updateById(entity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(List<Long> idList) {
		removeByIds(idList);
	}

	@Override
	@SneakyThrows
	public void export(StoreupQuery query) {
		List<StoreupVO> list = StoreupConvert.INSTANCE.convertList(baseMapper.selectList(getWrapper(query)));
		// 写到浏览器打开
		ExcelUtils.excelExport(StoreupVO.class, "收藏表" + DateUtils.format(new Date()), null, list);
	}

	@Override
	public List<Map<String, Object>> selectValue(Map<String, Object> params, LambdaQueryWrapper<StoreupEntity> wrapper) {
		return baseMapper.selectValue(params, wrapper);
	}

	@Override
	public List<Map<String, Object>> selectTimeStatValue(Map<String, Object> params, LambdaQueryWrapper<StoreupEntity> wrapper) {
		return baseMapper.selectTimeStatValue(params, wrapper);
	}

	@Override
	public List<Map<String, Object>> selectGroup(Map<String, Object> params, LambdaQueryWrapper<StoreupEntity> wrapper) {
		return baseMapper.selectGroup(params, wrapper);
	}
}