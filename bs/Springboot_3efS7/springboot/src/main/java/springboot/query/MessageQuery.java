package springboot.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import springboot.common.query.Query;
import org.springframework.format.annotation.DateTimeFormat;

    import java.util.Date;

/**
* 查询
* @since 1.0.0 2023-11-16
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "查询")
public class MessageQuery extends Query {
    @Schema(description = "消息ID")
    private Long id;

    @Schema(description = "发送方用户ID")
    private Long fromId;

    @Schema(description = "接收方用户ID")
    private Long toId;

    @Schema(description = "消息类型(0 ~ 255)，私聊(0)/群聊(1)消息")
    private Integer type;

    @Schema(description = "消息内容类型，文本(0)/图片(1)/文件(2)")
    private Integer contentType;

    @Schema(description = "消息内容ID")
    private Long contentId;

    @Schema(description = "消息发送时间")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date[] createTime;

    @Schema(description = "更新时间")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date[] updateTime;

}