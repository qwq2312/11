package springboot.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import springboot.entity.MessageEntity;
import springboot.vo.MessageVO;

import java.util.List;

/**
 *
 */
@Mapper
public interface MessageConvert {
	MessageConvert INSTANCE = Mappers.getMapper(MessageConvert.class);

	MessageEntity convert(MessageVO vo);

	MessageVO convert(MessageEntity entity);

	List<MessageVO> convertList(List<MessageEntity> list);

}