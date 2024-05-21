package springboot.convert;

import springboot.entity.ContentInfoEntity;
import springboot.vo.ContentInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 新闻表
*
*/
@Mapper
public interface ContentInfoConvert {
    ContentInfoConvert INSTANCE = Mappers.getMapper(ContentInfoConvert.class);

    ContentInfoEntity convert(ContentInfoVO vo);

    ContentInfoVO convert(ContentInfoEntity entity);

    List<ContentInfoVO> convertList(List<ContentInfoEntity> list);

}