package springboot.dao;

import springboot.common.utils.BaseDao;
import springboot.entity.SysAttachmentEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 附件管理
* @since 1.0.0 2023-11-16
*/
@Mapper
public interface SysAttachmentDao extends BaseDao<SysAttachmentEntity> {

}