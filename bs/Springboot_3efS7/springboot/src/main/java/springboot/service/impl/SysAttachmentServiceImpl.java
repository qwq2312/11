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
import springboot.convert.SysAttachmentConvert;
import springboot.dao.SysAttachmentDao;
import springboot.entity.SysAttachmentEntity;
import springboot.query.SysAttachmentQuery;
import springboot.service.SysAttachmentService;
import springboot.vo.SysAttachmentVO;

import java.util.Date;
import java.util.List;

/**
 * 附件管理
 *
 * @since 1.0.0 2023-11-16
 */
@Service
@AllArgsConstructor
public class SysAttachmentServiceImpl extends BaseServiceImpl<SysAttachmentDao, SysAttachmentEntity> implements SysAttachmentService {

	@Override
	public PageResult<SysAttachmentVO> page(SysAttachmentQuery query) {
		IPage<SysAttachmentEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
		return new PageResult<>(SysAttachmentConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
	}

	@Override
	public List<SysAttachmentVO> queryList(SysAttachmentQuery query) {
		return SysAttachmentConvert.INSTANCE.convertList(baseMapper.selectList(getWrapper(query)));
	}

	private LambdaQueryWrapper<SysAttachmentEntity> getWrapper(SysAttachmentQuery query) {
		LambdaQueryWrapper<SysAttachmentEntity> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(null != query.getId(), SysAttachmentEntity::getId, query.getId());
		wrapper.eq(StringUtils.isNotEmpty(query.getName()), SysAttachmentEntity::getName, query.getName());
		wrapper.eq(StringUtils.isNotEmpty(query.getUrl()), SysAttachmentEntity::getUrl, query.getUrl());
		wrapper.eq(null != query.getSize(), SysAttachmentEntity::getSize, query.getSize());
		wrapper.eq(StringUtils.isNotEmpty(query.getPlatform()), SysAttachmentEntity::getPlatform, query.getPlatform());
		wrapper.eq(null != query.getCreator(), SysAttachmentEntity::getCreator, query.getCreator());
		wrapper.between(ArrayUtils.isNotEmpty(query.getCreateTime()), SysAttachmentEntity::getCreateTime, ArrayUtils.isNotEmpty(query.getCreateTime()) ? query.getCreateTime()[0] : null, ArrayUtils.isNotEmpty(query.getCreateTime()) ? query.getCreateTime()[1] : null);
		return wrapper;
	}

	@Override
	public void save(SysAttachmentVO vo) {
		SysAttachmentEntity entity = SysAttachmentConvert.INSTANCE.convert(vo);
		baseMapper.insert(entity);
		vo.setId(entity.getId());
	}

	@Override
	public void update(SysAttachmentVO vo) {
		SysAttachmentEntity entity = SysAttachmentConvert.INSTANCE.convert(vo);
		updateById(entity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(List<Long> idList) {
		removeByIds(idList);
	}

	@Override
	@SneakyThrows
	public void export(SysAttachmentQuery query) {
		List<SysAttachmentVO> list = SysAttachmentConvert.INSTANCE.convertList(baseMapper.selectList(getWrapper(query)));
		// 写到浏览器打开
		ExcelUtils.excelExport(SysAttachmentVO.class, "附件管理" + DateUtils.format(new Date()), null, list);
	}
}