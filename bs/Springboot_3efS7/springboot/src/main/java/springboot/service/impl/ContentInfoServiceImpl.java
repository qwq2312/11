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
import springboot.common.recommend.RecommendService;
import springboot.common.utils.DateUtils;
import springboot.common.utils.ExcelUtils;
import springboot.convert.ContentInfoConvert;
import springboot.dao.ContentInfoDao;
import springboot.entity.ContentInfoEntity;
import springboot.query.ContentInfoQuery;
import springboot.service.ContentInfoService;
import springboot.vo.ContentInfoVO;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 新闻表
 */
@Service
@AllArgsConstructor
public class ContentInfoServiceImpl extends BaseServiceImpl<ContentInfoDao, ContentInfoEntity> implements ContentInfoService {
	private final RecommendService recommendService;

	@Override
	public PageResult<ContentInfoVO> page(ContentInfoQuery query) {
		IPage<ContentInfoEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
		return new PageResult<>(ContentInfoConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
	}

	@Override
	public List<ContentInfoVO> queryList(ContentInfoQuery query) {
		return ContentInfoConvert.INSTANCE.convertList(baseMapper.selectList(getWrapper(query)));
	}

	private LambdaQueryWrapper<ContentInfoEntity> getWrapper(ContentInfoQuery query) {
		LambdaQueryWrapper<ContentInfoEntity> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(null != query.getId(), ContentInfoEntity::getId, query.getId());
		wrapper.eq(StringUtils.isNotEmpty(query.getTitle()), ContentInfoEntity::getTitle, query.getTitle());
		wrapper.eq(StringUtils.isNotEmpty(query.getPhotoUrl()), ContentInfoEntity::getPhotoUrl, query.getPhotoUrl());
		wrapper.between(ArrayUtils.isNotEmpty(query.getCreateTime()), ContentInfoEntity::getCreateTime, ArrayUtils.isNotEmpty(query.getCreateTime()) ? query.getCreateTime()[0] : null, ArrayUtils.isNotEmpty(query.getCreateTime()) ? query.getCreateTime()[1] : null);
		wrapper.eq(StringUtils.isNotEmpty(query.getSource()), ContentInfoEntity::getSource, query.getSource());
		wrapper.eq(null != query.getCommentCount(), ContentInfoEntity::getCommentCount, query.getCommentCount());
		wrapper.eq(null != query.getEnable(), ContentInfoEntity::getEnable, query.getEnable());
		wrapper.eq(null != query.getSort(), ContentInfoEntity::getSort, query.getSort());
		wrapper.eq(null != query.getCategoryId(), ContentInfoEntity::getCategoryId, query.getCategoryId());
		wrapper.eq(StringUtils.isNotEmpty(query.getContent()), ContentInfoEntity::getContent, query.getContent());
		wrapper.eq(StringUtils.isNotEmpty(query.getDesinfo()), ContentInfoEntity::getDesinfo, query.getDesinfo());
		wrapper.eq(StringUtils.isNotEmpty(query.getSourceUrl()), ContentInfoEntity::getSourceUrl, query.getSourceUrl());
		wrapper.eq(null != query.getLikeCount(), ContentInfoEntity::getLikeCount, query.getLikeCount());
		wrapper.eq(null != query.getSlider(), ContentInfoEntity::getSlider, query.getSlider());
		wrapper.eq(null != query.getUserId(), ContentInfoEntity::getUserId, query.getUserId());
		wrapper.eq(null != query.getAfterProcess(), ContentInfoEntity::getAfterProcess, query.getAfterProcess());
		wrapper.orderByDesc(ContentInfoEntity::getProcessingTime);
		return wrapper;
	}

	@Override
	public void save(ContentInfoVO vo) {
		ContentInfoEntity entity = ContentInfoConvert.INSTANCE.convert(vo);
		baseMapper.insert(entity);
	}

	@Override
	public void update(ContentInfoVO vo) {
		ContentInfoEntity entity = ContentInfoConvert.INSTANCE.convert(vo);
		updateById(entity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(List<Long> idList) {
		removeByIds(idList);
	}

	@Override
	@SneakyThrows
	public void export(ContentInfoQuery query) {
		List<ContentInfoVO> list = ContentInfoConvert.INSTANCE.convertList(baseMapper.selectList(getWrapper(query)));
		// 写到浏览器打开
		ExcelUtils.excelExport(ContentInfoVO.class, "新闻表" + DateUtils.format(new Date()), null, list);
	}

	@Override
	public List<ContentInfoVO> recommend(Long userId) {
		LambdaQueryWrapper<ContentInfoEntity> wrapper = Wrappers.lambdaQuery();
		wrapper.orderByDesc(ContentInfoEntity::getReadcount);
		List<Long> uIds = baseMapper.selectList(wrapper).stream().map(ContentInfoEntity::getId).collect(Collectors.toList());
		List<Long> ids = recommendService.UserCFRecommend(userId, uIds);
		List<ContentInfoEntity> query = baseMapper.selectBatchIds(ids);
		return ContentInfoConvert.INSTANCE.convertList(query);
	}

	@Override
	public List<Map<String, Object>> selectValue(Map<String, Object> params, LambdaQueryWrapper<ContentInfoEntity> wrapper) {
		return baseMapper.selectValue(params, wrapper);
	}

	@Override
	public List<Map<String, Object>> selectTimeStatValue(Map<String, Object> params, LambdaQueryWrapper<ContentInfoEntity> wrapper) {
		return baseMapper.selectTimeStatValue(params, wrapper);
	}

	@Override
	public List<Map<String, Object>> selectGroup(Map<String, Object> params, LambdaQueryWrapper<ContentInfoEntity> wrapper) {
		return baseMapper.selectGroup(params, wrapper);
	}
}