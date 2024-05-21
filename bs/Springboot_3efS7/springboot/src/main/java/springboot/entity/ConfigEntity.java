package springboot.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
    import java.util.Date;

/**
* 配置文件
*
*/

@Data
@TableName("config")
public class ConfigEntity {
        /**
        * 主键
        */
        @TableId
        private Long id;

        /**
        * 配置参数名称
        */
        private String name;

        /**
        * 配置参数值
        */
        private String value;

        /**
        * 创建时间
        */
        @TableField(fill = FieldFill.INSERT)
        private Date addtime;

}