package springboot.service.impl;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import springboot.common.entity.Message;
import springboot.common.storage.service.StorageService;
import springboot.common.websocket.MessageType;
import springboot.common.websocket.ResponseJson;
import springboot.constants.Constants;
import springboot.dao.ImageContentDao;
import springboot.dao.MessageDao;
import springboot.dao.TextContentDao;
import springboot.entity.ImageContentEntity;
import springboot.entity.TextContentEntity;
import springboot.service.ChatService;
import springboot.service.SysAttachmentService;
import springboot.vo.SysAttachmentVO;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Map.Entry;

@Slf4j
@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {

	private final MessageDao messageDao;
	private final TextContentDao textContentDao;
	private final ImageContentDao imageContentDao;
	private final SysAttachmentService sysAttachmentService;
	private final StorageService storageService;

	@Override
	public void online(JSONObject param, ChannelHandlerContext ctx) {
		String id = param.get("id").toString();
		Constants.onlineUserMap.put(id, ctx);
		log.info(MessageFormat.format("id为 {0} 的用户登记到在线用户表，当前在线人数为：{1}"
				, id, Constants.onlineUserMap.size()));
	}

	@Override
	public void offline(ChannelHandlerContext ctx) {
		Iterator<Entry<String, ChannelHandlerContext>> iterator =
				Constants.onlineUserMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, ChannelHandlerContext> entry = iterator.next();
			if (entry.getValue() == ctx) {
				iterator.remove();
				log.info(MessageFormat.format("userId为 {0} 的用户已退出聊天，当前在线人数为：{1}"
						, entry.getKey(), Constants.onlineUserMap.size()));
				break;
			}
		}
	}

	@Override
	public void send(JSONObject jm, ChannelHandlerContext ctx, Integer type, Integer contentType) {
		Long fromId = Long.parseLong(jm.get("fromId").toString());
		Long toId = Long.parseLong(jm.get("toId").toString());

		storeMessage(jm, fromId, toId, type, contentType);

		if (type == 0) {
			// 拿到对方ID上下文
			ChannelHandlerContext toUserCtx = Constants.onlineUserMap.get(toId.toString());
			// 对方在线
			if (toUserCtx != null) {
				sendMessage(toUserCtx, jm.toJSONString());
			}
		}
	}

	@Override
	public void typeError(ChannelHandlerContext ctx) {
		String responseJson = new ResponseJson()
				.error("该类型不存在！")
				.toString();
		sendMessage(ctx, responseJson);
	}

	/**
	 * 消息持久化到数据库
	 *
	 * @param jm
	 * @param fromId
	 * @param toId
	 * @param type
	 * @param contentType
	 */
	private void storeMessage(JSONObject jm, Long fromId, Long toId, Integer type, Integer contentType) {
		String content = jm.get("content").toString();

		Message message = new Message(fromId, toId, type, contentType);
		if (contentType == MessageType.TEXT.getId()) {
			TextContentEntity textContent = new TextContentEntity();
			textContent.setContent(content);
			textContentDao.insert(textContent);
			message.setContentId(textContent.getId());
		} else if (contentType == MessageType.IMAGE.getId()) {
			ImageContentEntity imageContent = new ImageContentEntity();
			imageContent.setPath(content);
			imageContentDao.insert(imageContent);
			message.setContentId(imageContent.getId());
		} else if (contentType == MessageType.FILE.getId()) {
			String size = jm.get("size").toString();
			String url = jm.get("url").toString();
			SysAttachmentVO vo = new SysAttachmentVO();
			vo.setUrl(url);
			vo.setSize((Long) jm.get("size"));
			vo.setName(jm.get("name").toString());
			vo.setPlatform(storageService.properties.getConfig().getType().name());
			sysAttachmentService.save(vo);
			message.setContentId(vo.getId());
		}
		message.setTouxiang(jm.getString("avatarPath"));
		message.setUsername(jm.getString("name"));
		messageDao.insert(message);
		jm.put("id", message.getId());
	}


	private void sendMessage(ChannelHandlerContext ctx, String message) {
		ctx.channel().writeAndFlush(new TextWebSocketFrame(message));
	}

}
