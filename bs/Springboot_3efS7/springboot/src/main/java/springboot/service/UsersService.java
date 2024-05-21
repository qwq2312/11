package springboot.service;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import springboot.common.page.PageResult;
import springboot.common.utils.BaseService;
import springboot.entity.UsersEntity;
import springboot.query.UsersQuery;

import java.util.List;


/**
 * 系统用户
 */
public interface UsersService extends BaseService<UsersEntity> {
	PageResult<UsersEntity> queryPage(UsersQuery user);

	List<UsersEntity> selectListView(Wrapper<UsersEntity> wrapper);

	UsersEntity getByUsername(String username);
}
