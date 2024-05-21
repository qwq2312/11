package springboot.service;

import springboot.common.page.PageResult;
import springboot.common.utils.BaseService;
import springboot.vo.ImageContentVO;
import springboot.query.ImageContentQuery;
import springboot.entity.ImageContentEntity;

import java.util.List;

/**
* 
* @since 1.0.0 2023-11-16
*/
public interface ImageContentService extends BaseService<ImageContentEntity> {

    PageResult<ImageContentVO> page(ImageContentQuery query);

    List<ImageContentVO> queryList(ImageContentQuery query);

    void save(ImageContentVO vo);

    void update(ImageContentVO vo);

    void delete(List<Long> idList);

    void export(ImageContentQuery query);
}