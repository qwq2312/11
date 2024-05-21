package springboot.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import springboot.entity.TextContentEntity;
import springboot.vo.TextContentVO;

import java.util.List;

/**
 *
 */
@Mapper
public interface TextContentConvert {
	TextContentConvert INSTANCE = Mappers.getMapper(TextContentConvert.class);

	TextContentEntity convert(TextContentVO vo);

	TextContentVO convert(TextContentEntity entity);

	List<TextContentVO> convertList(List<TextContentEntity> list);

}