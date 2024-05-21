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
import springboot.convert.AboutusConvert;
import springboot.dao.AboutusDao;
import springboot.entity.AboutusEntity;
import springboot.query.AboutusQuery;
import springboot.service.AboutusService;
import springboot.vo.AboutusVO;

import java.util.Date;
import java.util.List;

/**
 * 关于我们
 */
@Service
@AllArgsConstructor
public class AboutusServiceImpl extends BaseServiceImpl<AboutusDao, AboutusEntity> implements AboutusService {

	@Override
	public PageResult<AboutusVO> page(AboutusQuery query) {
		IPage<AboutusEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
		return new PageResult<>(AboutusConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
	}

	@Override
	public List<AboutusVO> queryList(AboutusQuery query) {
		return AboutusConvert.INSTANCE.convertList(baseMapper.selectList(getWrapper(query)));
	}

	private LambdaQueryWrapper<AboutusEntity> getWrapper(AboutusQuery query) {
		LambdaQueryWrapper<AboutusEntity> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(null != query.getId(), AboutusEntity::getId, query.getId());
		wrapper.between(ArrayUtils.isNotEmpty(query.getAddtime()), AboutusEntity::getAddtime, ArrayUtils.isNotEmpty(query.getAddtime()) ? query.getAddtime()[0] : null, ArrayUtils.isNotEmpty(query.getAddtime()) ? query.getAddtime()[1] : null);
		wrapper.eq(StringUtils.isNotEmpty(query.getTitle()), AboutusEntity::getTitle, query.getTitle());
		wrapper.eq(StringUtils.isNotEmpty(query.getSubtitle()), AboutusEntity::getSubtitle, query.getSubtitle());
		wrapper.eq(StringUtils.isNotEmpty(query.getContent()), AboutusEntity::getContent, query.getContent());
		wrapper.eq(StringUtils.isNotEmpty(query.getPicture1()), AboutusEntity::getPicture1, query.getPicture1());
		wrapper.eq(StringUtils.isNotEmpty(query.getPicture2()), AboutusEntity::getPicture2, query.getPicture2());
		wrapper.eq(StringUtils.isNotEmpty(query.getPicture3()), AboutusEntity::getPicture3, query.getPicture3());
		return wrapper;
	}

	@Override
	public void save(AboutusVO vo) {
		AboutusEntity entity = AboutusConvert.INSTANCE.convert(vo);
		baseMapper.insert(entity);
	}

	@Override
	public void update(AboutusVO vo) {
		AboutusEntity entity = AboutusConvert.INSTANCE.convert(vo);
		updateById(entity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(List<Long> idList) {
		removeByIds(idList);
	}

	@Override
	@SneakyThrows
	public void export(AboutusQuery query) {
		List<AboutusVO> list = AboutusConvert.INSTANCE.convertList(baseMapper.selectList(getWrapper(query)));
		// 写到浏览器打开
		ExcelUtils.excelExport(AboutusVO.class, "关于我们" + DateUtils.format(new Date()), null, list);
	}
}