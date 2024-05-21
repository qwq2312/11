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
import springboot.convert.AdminConvert;
import springboot.dao.AdminDao;
import springboot.entity.AdminEntity;
import springboot.query.AdminQuery;
import springboot.service.AdminService;
import springboot.vo.AdminVO;

import java.util.Date;
import java.util.List;

/**
 * 后台管理用户表
 */
@Service
@AllArgsConstructor
public class AdminServiceImpl extends BaseServiceImpl<AdminDao, AdminEntity> implements AdminService {

	@Override
	public PageResult<AdminVO> page(AdminQuery query) {
		IPage<AdminEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
		return new PageResult<>(AdminConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
	}

	@Override
	public List<AdminVO> queryList(AdminQuery query) {
		return AdminConvert.INSTANCE.convertList(baseMapper.selectList(getWrapper(query)));
	}

	private LambdaQueryWrapper<AdminEntity> getWrapper(AdminQuery query) {
		LambdaQueryWrapper<AdminEntity> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(null != query.getId(), AdminEntity::getId, query.getId());
		wrapper.eq(StringUtils.isNotEmpty(query.getUsername()), AdminEntity::getUsername, query.getUsername());
		wrapper.eq(StringUtils.isNotEmpty(query.getPassword()), AdminEntity::getPassword, query.getPassword());
		wrapper.eq(StringUtils.isNotEmpty(query.getName()), AdminEntity::getName, query.getName());
		wrapper.eq(StringUtils.isNotEmpty(query.getGender()), AdminEntity::getGender, query.getGender());
		wrapper.eq(StringUtils.isNotEmpty(query.getPhone()), AdminEntity::getPhone, query.getPhone());
		wrapper.eq(StringUtils.isNotEmpty(query.getAvatarurl()), AdminEntity::getAvatarurl, query.getAvatarurl());
		wrapper.eq(StringUtils.isNotEmpty(query.getEmail()), AdminEntity::getEmail, query.getEmail());
		wrapper.eq(StringUtils.isNotEmpty(query.getRole()), AdminEntity::getRole, query.getRole());
		wrapper.between(ArrayUtils.isNotEmpty(query.getAddtime()), AdminEntity::getAddtime, ArrayUtils.isNotEmpty(query.getAddtime()) ? query.getAddtime()[0] : null, ArrayUtils.isNotEmpty(query.getAddtime()) ? query.getAddtime()[1] : null);
		return wrapper;
	}

	@Override
	public void save(AdminVO vo) {
		AdminEntity entity = AdminConvert.INSTANCE.convert(vo);
		baseMapper.insert(entity);
	}

	@Override
	public void update(AdminVO vo) {
		AdminEntity entity = AdminConvert.INSTANCE.convert(vo);
		updateById(entity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(List<Long> idList) {
		removeByIds(idList);
	}

	@Override
	@SneakyThrows
	public void export(AdminQuery query) {
		List<AdminVO> list = AdminConvert.INSTANCE.convertList(baseMapper.selectList(getWrapper(query)));
		// 写到浏览器打开
		ExcelUtils.excelExport(AdminVO.class, "后台管理用户表" + DateUtils.format(new Date()), null, list);
	}

	@Override
	public AdminEntity getByUsername(String username) {
		LambdaQueryWrapper<AdminEntity> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(StringUtils.isNotEmpty(username), AdminEntity::getUsername, username);
		return baseMapper.selectOne(wrapper);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void importAdmin(MultipartFile file, String password) {
		ExcelUtils.readAnalysis(file, AdminVO.class, new ExcelFinishCallBack<AdminVO>() {
			@Override
			public void doAfterAllAnalysed(List<AdminVO> result) {
				saveUser(result);
			}

			@Override
			public void doSaveBatch(List<AdminVO> result) {
				saveUser(result);
			}

			private void saveUser(List<AdminVO> result) {
				List<AdminEntity> sysUserEntities = AdminConvert.INSTANCE.convertListEntity(result);
				sysUserEntities.forEach(user -> user.setPassword(password));
				saveBatch(sysUserEntities);
			}
		});
	}
}