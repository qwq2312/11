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
import org.springframework.web.multipart.MultipartFile;
import springboot.common.excel.ExcelFinishCallBack;
import springboot.common.impl.BaseServiceImpl;
import springboot.common.page.PageResult;
import springboot.common.utils.DateUtils;
import springboot.common.utils.ExcelUtils;
import springboot.convert.ConfigConvert;
import springboot.dao.ConfigDao;
import springboot.entity.ConfigEntity;
import springboot.query.ConfigQuery;
import springboot.service.ConfigService;
import springboot.vo.ConfigVO;

import java.util.Date;
import java.util.List;

/**
 * 配置文件
 */
@Service
@AllArgsConstructor
public class ConfigServiceImpl extends BaseServiceImpl<ConfigDao, ConfigEntity> implements ConfigService {

	@Override
	public PageResult<ConfigVO> page(ConfigQuery query) {
		IPage<ConfigEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
		return new PageResult<>(ConfigConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
	}

	@Override
	public List<ConfigVO> queryList(ConfigQuery query) {
		return ConfigConvert.INSTANCE.convertList(baseMapper.selectList(getWrapper(query)));
	}

	private LambdaQueryWrapper<ConfigEntity> getWrapper(ConfigQuery query) {
		LambdaQueryWrapper<ConfigEntity> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(null != query.getId(), ConfigEntity::getId, query.getId());
		wrapper.eq(StringUtils.isNotEmpty(query.getName()), ConfigEntity::getName, query.getName());
		wrapper.eq(StringUtils.isNotEmpty(query.getValue()), ConfigEntity::getValue, query.getValue());
		wrapper.between(ArrayUtils.isNotEmpty(query.getAddtime()), ConfigEntity::getAddtime, ArrayUtils.isNotEmpty(query.getAddtime()) ? query.getAddtime()[0] : null, ArrayUtils.isNotEmpty(query.getAddtime()) ? query.getAddtime()[1] : null);
		return wrapper;
	}

	@Override
	public void save(ConfigVO vo) {
		ConfigEntity entity = ConfigConvert.INSTANCE.convert(vo);
		baseMapper.insert(entity);
	}

	@Override
	public void update(ConfigVO vo) {
		ConfigEntity entity = ConfigConvert.INSTANCE.convert(vo);
		updateById(entity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(List<Long> idList) {
		removeByIds(idList);
	}

	@Override
	@SneakyThrows
	public void export(ConfigQuery query) {
		List<ConfigVO> list = ConfigConvert.INSTANCE.convertList(baseMapper.selectList(getWrapper(query)));
		// 写到浏览器打开
		ExcelUtils.excelExport(ConfigVO.class, "配置文件" + DateUtils.format(new Date()), null, list);
	}

	@Override
	@Transactional
	public void importExcel(MultipartFile file) {
		ExcelUtils.readAnalysis(file, ConfigVO.class, new ExcelFinishCallBack<ConfigVO>() {
			@Override
			public void doAfterAllAnalysed(List<ConfigVO> result) {
				saveConfigs(result);
			}

			@Override
			public void doSaveBatch(List<ConfigVO> result) {
				saveConfigs(result);
			}

			private void saveConfigs(List<ConfigVO> result) {
				List<ConfigEntity> configEntities = ConfigConvert.INSTANCE.convertEntityList(result);
				saveBatch(configEntities);
			}
		});
	}
}