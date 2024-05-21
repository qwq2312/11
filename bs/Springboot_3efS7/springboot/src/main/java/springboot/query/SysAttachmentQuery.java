package springboot.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import springboot.common.query.Query;
import org.springframework.format.annotation.DateTimeFormat;

    import java.util.Date;

/**
* 附件管理查询
* @since 1.0.0 2023-11-16
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "附件管理查询")
public class SysAttachmentQuery extends Query {
    @Schema(description = "id")
    private Long id;

    @Schema(description = "附件名称")
    private String name;

    @Schema(description = "附件地址")
    private String url;

    @Schema(description = "附件大小")
    private Long size;

    @Schema(description = "存储平台")
    private String platform;

    @Schema(description = "创建者")
    private Long creator;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date[] createTime;

}