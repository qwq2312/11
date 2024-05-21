package springboot.service;

import org.springframework.web.multipart.MultipartFile;
import springboot.common.page.PageResult;
import springboot.common.utils.BaseService;
import springboot.entity.ConfigEntity;
import springboot.query.ConfigQuery;
import springboot.vo.ConfigVO;

import java.util.List;

/**
 * 配置文件
 */
public interface ConfigService extends BaseService<ConfigEntity> {

	PageResult<ConfigVO> page(ConfigQuery query);

	List<ConfigVO> queryList(ConfigQuery query);

	void save(ConfigVO vo);

	void update(ConfigVO vo);

	void delete(List<Long> idList);

	void export(ConfigQuery query);

	void importExcel(MultipartFile file);
}