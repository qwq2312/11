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
import springboot.convert.CategoryInfoConvert;
import springboot.dao.CategoryInfoDao;
import springboot.entity.CategoryInfoEntity;
import springboot.query.CategoryInfoQuery;
import springboot.service.CategoryInfoService;
import springboot.vo.CategoryInfoVO;

import java.util.Date;
import java.util.List;

/**
 * 新闻分类
 */
@Service
@AllArgsConstructor
public class CategoryInfoServiceImpl extends BaseServiceImpl<CategoryInfoDao, CategoryInfoEntity> implements CategoryInfoService {

	@Override
	public PageResult<CategoryInfoVO> page(CategoryInfoQuery query) {
		IPage<CategoryInfoEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
		return new PageResult<>(CategoryInfoConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
	}

	@Override
	public List<CategoryInfoVO> queryList(CategoryInfoQuery query) {
		return CategoryInfoConvert.INSTANCE.convertList(baseMapper.selectList(getWrapper(query)));
	}

	private LambdaQueryWrapper<CategoryInfoEntity> getWrapper(CategoryInfoQuery query) {
		LambdaQueryWrapper<CategoryInfoEntity> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(StringUtils.isNotEmpty(query.getId()), CategoryInfoEntity::getId, query.getId());
		wrapper.eq(StringUtils.isNotEmpty(query.getName()), CategoryInfoEntity::getName, query.getName());
		wrapper.eq(null != query.getPaixu(), CategoryInfoEntity::getPaixu, query.getPaixu());
		wrapper.eq(StringUtils.isNotEmpty(query.getQueryUrl()), CategoryInfoEntity::getQueryUrl, query.getQueryUrl());
		wrapper.between(ArrayUtils.isNotEmpty(query.getAddtime()), CategoryInfoEntity::getAddtime, ArrayUtils.isNotEmpty(query.getAddtime()) ? query.getAddtime()[0] : null, ArrayUtils.isNotEmpty(query.getAddtime()) ? query.getAddtime()[1] : null);
		wrapper.orderByDesc(CategoryInfoEntity::getAddtime);
		return wrapper;
	}

	@Override
	public void save(CategoryInfoVO vo) {
		CategoryInfoEntity entity = CategoryInfoConvert.INSTANCE.convert(vo);
		baseMapper.insert(entity);
	}

	@Override
	public void update(CategoryInfoVO vo) {
		CategoryInfoEntity entity = CategoryInfoConvert.INSTANCE.convert(vo);
		updateById(entity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(List<Long> idList) {
		removeByIds(idList);
	}

	@Override
	@SneakyThrows
	public void export(CategoryInfoQuery query) {
		List<CategoryInfoVO> list = CategoryInfoConvert.INSTANCE.convertList(baseMapper.selectList(getWrapper(query)));
		// 写到浏览器打开
		ExcelUtils.excelExport(CategoryInfoVO.class, "新闻分类" + DateUtils.format(new Date()), null, list);
	}
}