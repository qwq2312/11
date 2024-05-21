package springboot.service;

import springboot.common.page.PageResult;
import springboot.common.utils.BaseService;
import springboot.vo.AboutusVO;
import springboot.query.AboutusQuery;
import springboot.entity.AboutusEntity;

import java.util.List;

/**
* 关于我们
*
*/
public interface AboutusService extends BaseService<AboutusEntity> {

    PageResult<AboutusVO> page(AboutusQuery query);

    List<AboutusVO> queryList(AboutusQuery query);

    void save(AboutusVO vo);

    void update(AboutusVO vo);

    void delete(List<Long> idList);

    void export(AboutusQuery query);
}