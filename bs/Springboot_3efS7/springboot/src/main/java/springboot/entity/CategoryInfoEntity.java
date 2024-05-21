package springboot.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
    import java.util.Date;

/**
* 新闻分类
*
*/

@Data
@TableName("category_info")
public class CategoryInfoEntity {
        @TableId
        private String id;

        /**
        * 名称
        */
        private String name;

        /**
        * 排序
        */
        private Integer paixu;

        /**
        * 查询类型
        */
        private String queryUrl;

        /**
        * 创建时间
        */
        @TableField(fill = FieldFill.INSERT)
        private Date addtime;

}