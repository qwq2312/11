package springboot.service;

import springboot.common.utils.Result;

import java.util.Date;

public interface MessageService {
	/**
	 * 加载历史消息
	 *
	 * @param type
	 * @param updateTime
	 * @param toId
	 * @param count
	 * @param userId
	 * @return
	 */
	Result loadMessage(Integer type, Date updateTime, Long toId, Integer count, Long userId);
}
