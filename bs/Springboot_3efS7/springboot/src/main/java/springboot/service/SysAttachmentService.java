package springboot.service;

import springboot.common.page.PageResult;
import springboot.common.utils.BaseService;
import springboot.vo.SysAttachmentVO;
import springboot.query.SysAttachmentQuery;
import springboot.entity.SysAttachmentEntity;

import java.util.List;

/**
* 附件管理
* @since 1.0.0 2023-11-16
*/
public interface SysAttachmentService extends BaseService<SysAttachmentEntity> {

    PageResult<SysAttachmentVO> page(SysAttachmentQuery query);

    List<SysAttachmentVO> queryList(SysAttachmentQuery query);

    void save(SysAttachmentVO vo);

    void update(SysAttachmentVO vo);

    void delete(List<Long> idList);

    void export(SysAttachmentQuery query);
}