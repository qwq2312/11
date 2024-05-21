package springboot.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
    import java.util.Date;

/**
* 收藏表
*
*/

@Data
@TableName("storeup")
public class StoreupEntity {
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
        * 用户id
        */
        private Long userid;

        /**
        * 关联id
        */
        private Long refid;

        /**
        * 表名
        */
        private String tablename;

        /**
        * 名称
        */
        private String name;

        /**
        * 图片
        */
        private String picture;

        /**
        * 类型(1:收藏,2:赞,3:踩,4:竞拍参与,5:关注)
        */
        private Integer type;

        /**
        * 推荐类型
        */
        private String inteltype;

        /**
        * 备注
        */
        private String remark;

}