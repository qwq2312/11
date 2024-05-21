package springboot.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
    import java.util.Date;

/**
* 帖子详情
*
*/

@Data
@TableName("posts")
public class PostsEntity {
        /**
        * 主键
        */
        @TableId
        private Long id;

        /**
        * 话题分类
        */
        private String categoryId;

        /**
        * 发帖地址
        */
        private String postingAddress;

        /**
        * 封面
        */
        private String picture;

        /**
        * 帖子标题
        */
        private String postTitle;

        /**
        * 内容
        */
        private String postContent;

        /**
        * 是否发布(Y/N)
        */
        private String isPublished;

        /**
        * 是否审核通过(Y/N)
        */
        private String isApproved;

        /**
        * 发帖人
        */
        private String userid;

        /**
        * 创建时间
        */
        @TableField(fill = FieldFill.INSERT)
        private Date addtime;

        /**
        * 发帖人名称
        */
        private String username;

}