package springboot.service;

import springboot.common.page.PageResult;
import springboot.common.utils.BaseService;
import springboot.vo.TextContentVO;
import springboot.query.TextContentQuery;
import springboot.entity.TextContentEntity;

import java.util.List;

/**
* 
* @since 1.0.0 2023-11-16
*/
public interface TextContentService extends BaseService<TextContentEntity> {

    PageResult<TextContentVO> page(TextContentQuery query);

    List<TextContentVO> queryList(TextContentQuery query);

    void save(TextContentVO vo);

    void update(TextContentVO vo);

    void delete(List<Long> idList);

    void export(TextContentQuery query);
}