package springboot.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import springboot.common.utils.DateUtils;
    import java.util.Date;

/**
* 附件管理
* @since 1.0.0 2023-11-16
*/
@Data
@Schema(description = "附件管理")
public class SysAttachmentVO implements Serializable {
private static final long serialVersionUID = 1L;

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
        @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private Date createTime;


}