package springboot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 评论表
 *
 * @since 1.0.0 2023-11-07
 */

@Data
@TableName("discuss")
public class DiscussEntity {
	@TableId
	private Long id;

	/**
	 * 关联表id
	 */
	private Long refid;

	/**
	 * 用户id
	 */
	private Long userid;

	/**
	 * 头像
	 */
	private String avatarurl;

	/**
	 * 用户名
	 */
	private String nickname;

	/**
	 * 评论内容
	 */
	private String content;

	/**
	 * 父id
	 */
	private Long parentId;

	/**
	 * 点赞数量
	 */
	private Integer likesCount;

	/**
	 * 创建时间
	 */
	private Date createTime;

}