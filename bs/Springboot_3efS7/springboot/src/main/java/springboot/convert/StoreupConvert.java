package springboot.convert;

import springboot.entity.StoreupEntity;
import springboot.vo.StoreupVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 收藏表
*
*/
@Mapper
public interface StoreupConvert {
    StoreupConvert INSTANCE = Mappers.getMapper(StoreupConvert.class);

    StoreupEntity convert(StoreupVO vo);

    StoreupVO convert(StoreupEntity entity);

    List<StoreupVO> convertList(List<StoreupEntity> list);

}