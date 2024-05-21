package springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import springboot.common.impl.BaseServiceImpl;
import springboot.common.page.PageResult;
import springboot.convert.SystemintroConvert;
import springboot.dao.SystemintroDao;
import springboot.entity.SystemintroEntity;
import springboot.query.SystemintroQuery;
import springboot.service.SystemintroService;
import springboot.vo.SystemintroVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 关于我们
 *
 * @since 1.0.0 2023-11-04
 */
@Service
@AllArgsConstructor
public class SystemintroServiceImpl extends BaseServiceImpl
		<SystemintroDao, SystemintroEntity> implements SystemintroService {

	@Override
	public PageResult<SystemintroVO> page(SystemintroQuery query) {
		IPage<SystemintroEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

		return new PageResult<>(SystemintroConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
	}

	private LambdaQueryWrapper<SystemintroEntity> getWrapper(SystemintroQuery query) {
		LambdaQueryWrapper<SystemintroEntity> wrapper = Wrappers.lambdaQuery();
		return wrapper;
	}

	@Override
	public void save(SystemintroVO vo) {
		SystemintroEntity entity = SystemintroConvert.INSTANCE.convert(vo);

		baseMapper.insert(entity);
	}

	@Override
	public void update(SystemintroVO vo) {
		SystemintroEntity entity = SystemintroConvert.INSTANCE.convert(vo);

		updateById(entity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(List
			                   <Long> idList) {
		removeByIds(idList);
	}

}