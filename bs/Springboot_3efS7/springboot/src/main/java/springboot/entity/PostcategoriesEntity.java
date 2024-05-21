package springboot.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 帖子话题分类
 */

@Data
@TableName("postcategories")
public class PostcategoriesEntity {
	/**
	 * 主键
	 */
	@TableId
	private Long id;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private Date addtime;

	/**
	 * 创建人
	 */
	private Long userid;

	/**
	 * 创建人名字
	 */
	private String username;

	/**
	 * 分类
	 */
	private String categoryName;

}