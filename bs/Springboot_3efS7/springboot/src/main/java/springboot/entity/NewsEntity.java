package springboot.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
    import java.util.Date;

/**
* 公告信息
*
*/

@Data
@TableName("news")
public class NewsEntity {
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
        * 标题
        */
        private String title;

        /**
        * 简介
        */
        private String introduction;

        /**
        * 图片
        */
        private String picture;

        /**
        * 内容
        */
        private String content;

}