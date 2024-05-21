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
import springboot.convert.NewsConvert;
import springboot.dao.NewsDao;
import springboot.entity.NewsEntity;
import springboot.query.NewsQuery;
import springboot.service.NewsService;
import springboot.vo.NewsVO;

import java.util.Date;
import java.util.List;

/**
 * 公告信息
 */
@Service
@AllArgsConstructor
public class NewsServiceImpl extends BaseServiceImpl<NewsDao, NewsEntity> implements NewsService {

	@Override
	public PageResult<NewsVO> page(NewsQuery query) {
		IPage<NewsEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
		return new PageResult<>(NewsConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
	}

	@Override
	public List<NewsVO> queryList(NewsQuery query) {
		return NewsConvert.INSTANCE.convertList(baseMapper.selectList(getWrapper(query)));
	}

	private LambdaQueryWrapper<NewsEntity> getWrapper(NewsQuery query) {
		LambdaQueryWrapper<NewsEntity> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(null != query.getId(), NewsEntity::getId, query.getId());
		wrapper.between(ArrayUtils.isNotEmpty(query.getAddtime()), NewsEntity::getAddtime, ArrayUtils.isNotEmpty(query.getAddtime()) ? query.getAddtime()[0] : null, ArrayUtils.isNotEmpty(query.getAddtime()) ? query.getAddtime()[1] : null);
		wrapper.like(StringUtils.isNotEmpty(query.getTitle()), NewsEntity::getTitle, query.getTitle());
		wrapper.eq(StringUtils.isNotEmpty(query.getIntroduction()), NewsEntity::getIntroduction, query.getIntroduction());
		wrapper.eq(StringUtils.isNotEmpty(query.getPicture()), NewsEntity::getPicture, query.getPicture());
		wrapper.eq(StringUtils.isNotEmpty(query.getContent()), NewsEntity::getContent, query.getContent());
		return wrapper;
	}

	@Override
	public void save(NewsVO vo) {
		NewsEntity entity = NewsConvert.INSTANCE.convert(vo);
		baseMapper.insert(entity);
	}

	@Override
	public void update(NewsVO vo) {
		NewsEntity entity = NewsConvert.INSTANCE.convert(vo);
		updateById(entity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(List<Long> idList) {
		removeByIds(idList);
	}

	@Override
	@SneakyThrows
	public void export(NewsQuery query) {
		List<NewsVO> list = NewsConvert.INSTANCE.convertList(baseMapper.selectList(getWrapper(query)));
		// 写到浏览器打开
		ExcelUtils.excelExport(NewsVO.class, "公告信息" + DateUtils.format(new Date()), null, list);
	}
}