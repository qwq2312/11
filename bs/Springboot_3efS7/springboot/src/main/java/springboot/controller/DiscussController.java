package springboot.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.common.page.PageResult;
import springboot.common.request.RequestSingleParam;
import springboot.common.utils.Result;
import springboot.convert.DiscussConvert;
import springboot.entity.DiscussEntity;
import springboot.query.DiscussQuery;
import springboot.service.DiscussService;
import springboot.vo.DiscussVO;

import javax.validation.Valid;
import java.util.List;

/**
 * 评论表
 *
 * @since 1.0.0 2023-11-07
 */
@RestController
@RequestMapping("discuss")
@Tag(name = "评论表")
@AllArgsConstructor
public class DiscussController {
	private final DiscussService discussService;

	@PostMapping("page")
	@Operation(summary = "分页")
	public Result<PageResult<DiscussVO>> page(@RequestBody @Valid DiscussQuery query) {
		PageResult<DiscussVO> page = discussService.page(query);
		return Result.ok(page);
	}
	
	@PostMapping("list")
	@Operation(summary = "查询评论列表")
	public Result<List<DiscussVO>> list(@RequestBody @Valid DiscussQuery query) {
		List<DiscussVO> list = discussService.queryList(query);
		return Result.ok(list);
	}

	@PostMapping("like")
	@Operation(summary = "点赞")
	public Result<Boolean> like(@RequestSingleParam("id") Long id) {
		discussService.like(id);
		return Result.ok(true);
	}

	@PostMapping("/info")
	@Operation(summary = "信息")
	public Result<DiscussVO> get(@RequestSingleParam("id") Long id) {
		DiscussEntity entity = discussService.getById(id);
		return Result.ok(DiscussConvert.INSTANCE.convert(entity));
	}

	@PostMapping("save")
	@Operation(summary = "保存")
	public Result<String> save(@RequestBody DiscussVO vo) {
		discussService.save(vo);
		return Result.ok();
	}

	@PostMapping("update")
	@Operation(summary = "修改")
	public Result<String> update(@RequestBody @Valid DiscussVO vo) {
		discussService.update(vo);
		return Result.ok();
	}

	@PostMapping("delete")
	@Operation(summary = "删除")
	public Result<String> delete(@RequestBody List<Long> idList) {
		discussService.delete(idList);
		return Result.ok();
	}
}