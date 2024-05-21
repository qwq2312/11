package springboot.controller;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springboot.common.utils.GloabUtils;
import springboot.common.utils.Result;
import springboot.query.MessageLoadQuery;
import springboot.service.MessageService;

import java.util.Date;


@RestController
@RequestMapping("/message")
public class MessageController {
	@Autowired
	MessageService messageService;

	@ApiOperation(value = "Load Message")
	@PostMapping(value = "load")
	@ResponseBody
	public Result load(@RequestBody MessageLoadQuery messageLoadQuery) {
		Date updateTime = DateUtil.parse(messageLoadQuery.getUpdateTime());
		return messageService.loadMessage(messageLoadQuery.getType(), updateTime, messageLoadQuery.getToId(), messageLoadQuery.getCount(), GloabUtils.getUserId());
	}
}
