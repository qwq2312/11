package springboot.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import springboot.entity.ImageContentEntity;
import springboot.vo.ImageContentVO;

import java.util.List;

/**
 *
 */
@Mapper
public interface ImageContentConvert {
	ImageContentConvert INSTANCE = Mappers.getMapper(ImageContentConvert.class);

	ImageContentEntity convert(ImageContentVO vo);

	ImageContentVO convert(ImageContentEntity entity);

	List<ImageContentVO> convertList(List<ImageContentEntity> list);

}