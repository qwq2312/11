package springboot.controller;

import cn.dev33.satoken.annotation.SaIgnore;
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
import springboot.convert.SystemintroConvert;
import springboot.entity.SystemintroEntity;
import springboot.query.SystemintroQuery;
import springboot.service.SystemintroService;
import springboot.vo.SystemintroVO;

import javax.validation.Valid;
import java.util.List;

/**
 * 关于我们
 */
@RestController
@RequestMapping("systemintro")
@Tag(name = "关于我们")
@AllArgsConstructor
public class SystemintroController {
	private final SystemintroService systemintroService;

	@PostMapping("page")
	@Operation(summary = "分页")
	public Result<PageResult<SystemintroVO>> page(@RequestBody @Valid SystemintroQuery query) {
		PageResult<SystemintroVO> page = systemintroService.page(query);

		return Result.ok(page);
	}

	@PostMapping("/info")
	@Operation(summary = "信息")
	@SaIgnore
	public Result<SystemintroVO> get(@RequestSingleParam("id") Long id) {
		SystemintroEntity entity = systemintroService.getById(id);

		return Result.ok(SystemintroConvert.INSTANCE.convert(entity));
	}

	@PostMapping("save")
	@Operation(summary = "保存")
	public Result<String> save(@RequestBody SystemintroVO vo) {
		systemintroService.save(vo);

		return Result.ok();
	}

	@PostMapping("update")
	@Operation(summary = "修改")
	public Result<String> update(@RequestBody @Valid SystemintroVO vo) {
		systemintroService.update(vo);

		return Result.ok();
	}

	@PostMapping("delete")
	@Operation(summary = "删除")
	public Result<String> delete(@RequestBody List<Long> idList) {
		systemintroService.delete(idList);
		return Result.ok();
	}
}