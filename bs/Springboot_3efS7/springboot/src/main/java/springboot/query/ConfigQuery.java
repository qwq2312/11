package springboot.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import springboot.common.query.Query;
import org.springframework.format.annotation.DateTimeFormat;

    import java.util.Date;

/**
* 配置文件查询
*
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "配置文件查询")
public class ConfigQuery extends Query {
    @Schema(description = "主键")
    private Long id;

    @Schema(description = "配置参数名称")
    private String name;

    @Schema(description = "配置参数值")
    private String value;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date[] addtime;

}