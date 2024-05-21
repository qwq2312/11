package springboot.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import springboot.entity.AdminEntity;
import springboot.vo.AdminVO;

import java.util.List;

/**
 * 后台管理用户表
 */
@Mapper
public interface AdminConvert {
	AdminConvert INSTANCE = Mappers.getMapper(AdminConvert.class);

	AdminEntity convert(AdminVO vo);

	AdminVO convert(AdminEntity entity);

	List<AdminVO> convertList(List<AdminEntity> list);

	List<AdminEntity> convertListEntity(List<AdminVO> result);
}