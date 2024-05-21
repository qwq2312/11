package springboot.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import springboot.entity.ConfigEntity;
import springboot.vo.ConfigVO;

import java.util.List;

/**
 * 配置文件
 */
@Mapper
public interface ConfigConvert {
	ConfigConvert INSTANCE = Mappers.getMapper(ConfigConvert.class);

	ConfigEntity convert(ConfigVO vo);

	ConfigVO convert(ConfigEntity entity);

	List<ConfigVO> convertList(List<ConfigEntity> list);

	List<ConfigEntity> convertEntityList(List<ConfigVO> result);
}