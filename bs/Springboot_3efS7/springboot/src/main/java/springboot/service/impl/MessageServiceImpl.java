package springboot.service.impl;

import org.springframework.stereotype.Service;
import springboot.common.entity.Message;
import springboot.common.utils.Result;
import springboot.dao.MessageDao;
import springboot.service.ImageContentService;
import springboot.service.MessageService;
import springboot.service.SysAttachmentService;
import springboot.service.TextContentService;

import javax.annotation.Resource;
import java.util.*;

@Service
public class MessageServiceImpl implements MessageService {
	@Resource
	MessageDao messageDao;
	@Resource
	TextContentService textContentService;
	@Resource
	ImageContentService imageContentService;
	@Resource
	SysAttachmentService sysAttachmentService;

	@Override
	public Result loadMessage(Integer type, Date updateTime, Long toId, Integer count, Long userId) {
		List<Message> messages = this.messageDao.listMessage(type, updateTime, userId, toId, count);
		List<Map<String, Object>> collections = new ArrayList<>();
		for (Message message : messages) {
			Map<String, Object> dataMap = new HashMap<>();
			dataMap.put("id", message.getId());
			dataMap.put("fromId", message.getFromId());
			dataMap.put("name", message.getUsername());
			dataMap.put("avatarPath", message.getTouxiang());
			dataMap.put("updateTime", message.getUpdateTime());
			dataMap.put("contentType", message.getContentType());
			dataMap.put("content", message.getContentType() == 0 ?
					textContentService.getById(message.getContentId()).getContent()
					: (message.getContentType() == 1 ? imageContentService.getById(message.getContentId()).getPath()
					: sysAttachmentService.getById(message.getContentId()).getName()));
			dataMap.put("url", message.getContentType() == 2 ?
					sysAttachmentService.getById(message.getContentId()).getUrl() : null);
			collections.add(dataMap);
		}
		return Result.ok(collections);
	}
}
