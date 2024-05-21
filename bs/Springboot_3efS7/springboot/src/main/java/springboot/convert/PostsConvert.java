package springboot.convert;

import springboot.entity.PostsEntity;
import springboot.vo.PostsVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 帖子详情
*
*/
@Mapper
public interface PostsConvert {
    PostsConvert INSTANCE = Mappers.getMapper(PostsConvert.class);

    PostsEntity convert(PostsVO vo);

    PostsVO convert(PostsEntity entity);

    List<PostsVO> convertList(List<PostsEntity> list);

}