package springboot.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import springboot.entity.SysAttachmentEntity;
import springboot.vo.SysAttachmentVO;

import java.util.List;

/**
 * 附件管理
 */
@Mapper
public interface SysAttachmentConvert {
	SysAttachmentConvert INSTANCE = Mappers.getMapper(SysAttachmentConvert.class);

	SysAttachmentEntity convert(SysAttachmentVO vo);

	SysAttachmentVO convert(SysAttachmentEntity entity);

	List<SysAttachmentVO> convertList(List<SysAttachmentEntity> list);

}