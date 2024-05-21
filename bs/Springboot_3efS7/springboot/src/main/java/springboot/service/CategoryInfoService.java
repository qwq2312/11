package springboot.service;

import springboot.common.page.PageResult;
import springboot.common.utils.BaseService;
import springboot.vo.CategoryInfoVO;
import springboot.query.CategoryInfoQuery;
import springboot.entity.CategoryInfoEntity;

import java.util.List;

/**
* 新闻分类
*
*/
public interface CategoryInfoService extends BaseService<CategoryInfoEntity> {

    PageResult<CategoryInfoVO> page(CategoryInfoQuery query);

    List<CategoryInfoVO> queryList(CategoryInfoQuery query);

    void save(CategoryInfoVO vo);

    void update(CategoryInfoVO vo);

    void delete(List<Long> idList);

    void export(CategoryInfoQuery query);
}