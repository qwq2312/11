/** * @ClassName MessageLoadQuery * @Description TODO * @Version 1.0 */package springboot.query;import io.swagger.v3.oas.annotations.media.Schema;import lombok.Data;import java.io.Serializable;@Datapublic class MessageLoadQuery implements Serializable {	@Schema(description = "接收方用户ID")	private Long toId;	@Schema(description = "消息类型(0 ~ 255)，私聊(0)/群聊(1)消息")	private Integer type;	private String updateTime;	private Integer count;}