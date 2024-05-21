package springboot.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import springboot.common.entity.Message;
import springboot.common.utils.BaseDao;

import java.util.Date;
import java.util.List;

/**
 * @since 1.0.0 2023-11-16
 */
@Mapper
public interface MessageDao extends BaseDao<Message> {

	List<Message> listMessage(@Param("type") Integer type, @Param("updateTime") Date updateTime,
	                          @Param("fromId") Long fromId, @Param("toId") Long toId, @Param("count") Integer count);
}