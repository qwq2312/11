package springboot.service;

import springboot.common.page.PageResult;
import springboot.common.utils.BaseService;
import springboot.vo.PostsVO;
import springboot.query.PostsQuery;
import springboot.entity.PostsEntity;

import java.util.List;

/**
* 帖子详情
*
*/
public interface PostsService extends BaseService<PostsEntity> {

    PageResult<PostsVO> page(PostsQuery query);

    List<PostsVO> queryList(PostsQuery query);

    void save(PostsVO vo);

    void update(PostsVO vo);

    void delete(List<Long> idList);

    void export(PostsQuery query);
}