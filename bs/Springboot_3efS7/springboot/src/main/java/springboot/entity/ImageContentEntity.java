package springboot.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @since 1.0.0 2023-11-16
 */

@Data
@TableName("image_content")
public class ImageContentEntity {
	/**
	 * 图片消息ID
	 */
	@TableId
	private Long id;

	/**
	 * 图片存储路径
	 */
	private String path;

	/**
	 * 文件ID
	 */
	private Long fileId;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@TableField("create_time")
	private Date createTime;

	/**
	 * 更新时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@TableField("update_time")
	private Date updateTime;
}