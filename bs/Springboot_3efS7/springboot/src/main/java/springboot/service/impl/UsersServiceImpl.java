package springboot.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.common.impl.BaseServiceImpl;
import springboot.common.page.PageResult;
import springboot.dao.UsersDao;
import springboot.entity.UsersEntity;
import springboot.query.UsersQuery;
import springboot.service.UsersService;

import java.util.List;

/**
 * 系统用户
 */
@Service("usersService")
@AllArgsConstructor
public class UsersServiceImpl extends BaseServiceImpl<UsersDao, UsersEntity> implements UsersService {

	@Override
	public PageResult<UsersEntity> queryPage(UsersQuery user) {
		IPage<UsersEntity> page = baseMapper.selectPage(getPage(user), getWrapper(user));
		return new PageResult<>(page.getRecords(), page.getTotal());
	}

	private Wrapper<UsersEntity> getWrapper(UsersQuery query) {
		LambdaQueryWrapper<UsersEntity> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(null != query.getUsername(), UsersEntity::getUsername, query.getUsername());
		wrapper.eq(null != query.getRole(), UsersEntity::getRole, query.getRole());
		wrapper.eq(null != query.getId(), UsersEntity::getId, query.getId());
		wrapper.orderByDesc(UsersEntity::getAddtime);
		return wrapper;
	}

	@Override
	public List<UsersEntity> selectListView(Wrapper<UsersEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public UsersEntity getByUsername(String username) {
		return baseMapper.selectOne(new QueryWrapper<UsersEntity>().eq("username", username));
	}
}
