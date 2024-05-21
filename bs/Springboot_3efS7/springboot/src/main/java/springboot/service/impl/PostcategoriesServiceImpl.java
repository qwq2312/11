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
import springboot.convert.PostcategoriesConvert;
import springboot.dao.PostcategoriesDao;
import springboot.entity.PostcategoriesEntity;
import springboot.query.PostcategoriesQuery;
import springboot.service.PostcategoriesService;
import springboot.vo.PostcategoriesVO;

import java.util.Date;
import java.util.List;

/**
 * 帖子话题分类
 */
@Service
@AllArgsConstructor
public class PostcategoriesServiceImpl extends BaseServiceImpl<PostcategoriesDao, PostcategoriesEntity> implements PostcategoriesService {

	@Override
	public PageResult<PostcategoriesVO> page(PostcategoriesQuery query) {
		IPage<PostcategoriesEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
		return new PageResult<>(PostcategoriesConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
	}

	@Override
	public List<PostcategoriesVO> queryList(PostcategoriesQuery query) {
		return PostcategoriesConvert.INSTANCE.convertList(baseMapper.selectList(getWrapper(query)));
	}

	private LambdaQueryWrapper<PostcategoriesEntity> getWrapper(PostcategoriesQuery query) {
		LambdaQueryWrapper<PostcategoriesEntity> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(null != query.getId(), PostcategoriesEntity::getId, query.getId());
		wrapper.between(ArrayUtils.isNotEmpty(query.getAddtime()), PostcategoriesEntity::getAddtime, ArrayUtils.isNotEmpty(query.getAddtime()) ? query.getAddtime()[0] : null, ArrayUtils.isNotEmpty(query.getAddtime()) ? query.getAddtime()[1] : null);
		wrapper.eq(null != query.getUserid(), PostcategoriesEntity::getUserid, query.getUserid());
		wrapper.eq(StringUtils.isNotEmpty(query.getUsername()), PostcategoriesEntity::getUsername, query.getUsername());
		wrapper.eq(null != query.getCategoryName(), PostcategoriesEntity::getCategoryName, query.getCategoryName());
		return wrapper;
	}

	@Override
	public void save(PostcategoriesVO vo) {
		PostcategoriesEntity entity = PostcategoriesConvert.INSTANCE.convert(vo);
		baseMapper.insert(entity);
	}

	@Override
	public void update(PostcategoriesVO vo) {
		PostcategoriesEntity entity = PostcategoriesConvert.INSTANCE.convert(vo);
		updateById(entity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(List<Long> idList) {
		removeByIds(idList);
	}

	@Override
	@SneakyThrows
	public void export(PostcategoriesQuery query) {
		List<PostcategoriesVO> list = PostcategoriesConvert.INSTANCE.convertList(baseMapper.selectList(getWrapper(query)));
		// 写到浏览器打开
		ExcelUtils.excelExport(PostcategoriesVO.class, "帖子话题分类" + DateUtils.format(new Date()), null, list);
	}
}