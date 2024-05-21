package springboot.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

/**
 * 关于我们
 *
 * @since 1.0.0 2023-11-04
 */

@Data
@TableName("systemintro")
public class SystemintroEntity {
	/**
	 * 主键
	 */
	@TableId
	private Long id;

	/**
	 * 创建时间
	 */
	private Date addtime;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 副标题
	 */
	private String subtitle;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 图片1
	 */
	private String picture1;

	/**
	 * 图片2
	 */
	private String picture2;

	/**
	 * 图片3
	 */
	private String picture3;

}