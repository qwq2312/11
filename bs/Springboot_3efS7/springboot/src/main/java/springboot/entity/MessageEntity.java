package springboot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @since 1.0.0 2023-11-16
 */

@Data
@TableName("message")
public class MessageEntity {
	/**
	 * 消息ID
	 */
	@TableId
	private Long id;

	/**
	 * 发送方用户ID
	 */
	private Long fromId;

	/**
	 * 接收方用户ID
	 */
	private Long toId;

	/**
	 * 消息类型(0 ~ 255)，私聊(0)/群聊(1)消息
	 */
	private Integer type;

	/**
	 * 消息内容类型，文本(0)/图片(1)/文件(2)
	 */
	private Integer contentType;

	/**
	 * 消息内容ID
	 */
	private Long contentId;

	/**
	 * 消息发送时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date updateTime;

	private String username;
	private String touxiang;

}