package springboot.service;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;


public interface ChatService {

	/**
	 * 处理用户上线
	 *
	 * @param param
	 * @param ctx
	 */
	void online(JSONObject param, ChannelHandlerContext ctx);

	/**
	 * 处理用户下线
	 *
	 * @param ctx
	 */
	void offline(ChannelHandlerContext ctx);

	/**
	 * @param jm
	 * @param ctx
	 * @param type
	 * @param contentType
	 */
	void send(JSONObject jm, ChannelHandlerContext ctx, Integer type, Integer contentType);

	/**
	 * 封装并发送“类型不存在”错误
	 *
	 * @param ctx
	 */
	void typeError(ChannelHandlerContext ctx);

}
