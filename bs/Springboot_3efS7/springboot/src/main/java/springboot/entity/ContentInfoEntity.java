package springboot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 新闻表
 */

@Data
@TableName("content_info")
public class ContentInfoEntity {
	/**
	 * 主键
	 */
	@TableId
	private Long id;

	private String title;

	private String photoUrl;

	private Integer readcount;

	private Date createTime;

	private String source;

	private Integer commentCount;

	private Integer enable;

	private Integer sort;

	private String categoryId;

	private String content;

	private String desinfo;

	private String sourceUrl;

	private Integer likeCount;

	private Integer slider;

	private Long userId;

	private String processingTime;

	private Integer afterProcess;

}