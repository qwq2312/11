package springboot.convert;

import springboot.entity.CategoryInfoEntity;
import springboot.vo.CategoryInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 新闻分类
*
*/
@Mapper
public interface CategoryInfoConvert {
    CategoryInfoConvert INSTANCE = Mappers.getMapper(CategoryInfoConvert.class);

    CategoryInfoEntity convert(CategoryInfoVO vo);

    CategoryInfoVO convert(CategoryInfoEntity entity);

    List<CategoryInfoVO> convertList(List<CategoryInfoEntity> list);

}