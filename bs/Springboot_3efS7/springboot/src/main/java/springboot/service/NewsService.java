package springboot.service;

import springboot.common.page.PageResult;
import springboot.common.utils.BaseService;
import springboot.vo.NewsVO;
import springboot.query.NewsQuery;
import springboot.entity.NewsEntity;

import java.util.List;

/**
* 公告信息
*
*/
public interface NewsService extends BaseService<NewsEntity> {

    PageResult<NewsVO> page(NewsQuery query);

    List<NewsVO> queryList(NewsQuery query);

    void save(NewsVO vo);

    void update(NewsVO vo);

    void delete(List<Long> idList);

    void export(NewsQuery query);
}