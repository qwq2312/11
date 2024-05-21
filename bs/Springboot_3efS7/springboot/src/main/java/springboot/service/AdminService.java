package springboot.service;

import org.springframework.web.multipart.MultipartFile;
import springboot.common.page.PageResult;
import springboot.common.utils.BaseService;
import springboot.entity.AdminEntity;
import springboot.query.AdminQuery;
import springboot.vo.AdminVO;

import java.util.List;

/**
 * 后台管理用户表
 */
public interface AdminService extends BaseService<AdminEntity> {

	PageResult<AdminVO> page(AdminQuery query);

	List<AdminVO> queryList(AdminQuery query);

	void save(AdminVO vo);

	void update(AdminVO vo);

	void delete(List<Long> idList);

	void export(AdminQuery query);

	AdminEntity getByUsername(String username);

	void importAdmin(MultipartFile file, String encrypt);
}