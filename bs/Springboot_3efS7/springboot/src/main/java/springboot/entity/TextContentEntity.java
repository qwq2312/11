package springboot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @since 1.0.0 2023-11-16
 */

@Data
@TableName("text_content")
public class TextContentEntity {
	/**
	 * 文本消息ID
	 */
	@TableId
	private Long id;

	/**
	 * 文本消息
	 */
	private String content;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date updateTime;

}