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
import springboot.convert.SysAttachmentConvert;
import springboot.entity.SysAttachmentEntity;
import springboot.query.SysAttachmentQuery;
import springboot.service.SysAttachmentService;
import springboot.vo.SysAttachmentVO;

import javax.validation.Valid;
import java.util.List;

/**
 * 附件管理
 *
 * @since 1.0.0 2023-11-16
 */
@RestController
@RequestMapping("sys_attachment")
@Tag(name = "附件管理")
@AllArgsConstructor
public class SysAttachmentController {
	private final SysAttachmentService sysAttachmentService;

	@PostMapping("page")
	@Operation(summary = "分页")
	public Result<PageResult<SysAttachmentVO>> page(@RequestBody @Valid SysAttachmentQuery query) {
		PageResult<SysAttachmentVO> page = sysAttachmentService.page(query);
		return Result.ok(page);
	}

	@PostMapping("list")
	@Operation(summary = "列表")
	public Result<List<SysAttachmentVO>> list(@RequestBody @Valid SysAttachmentQuery query) {
		List<SysAttachmentVO> list = sysAttachmentService.queryList(query);
		return Result.ok(list);
	}

	@PostMapping("/info")
	@Operation(summary = "信息")
	public Result<SysAttachmentVO> get(@RequestSingleParam("id") Long id) {
		SysAttachmentEntity entity = sysAttachmentService.getById(id);
		return Result.ok(SysAttachmentConvert.INSTANCE.convert(entity));
	}

	@PostMapping("save")
	@Operation(summary = "保存")
	public Result<String> save(@RequestBody SysAttachmentVO vo) {
		sysAttachmentService.save(vo);
		return Result.ok();
	}

	@PostMapping("update")
	@Operation(summary = "修改")
	public Result<String> update(@RequestBody @Valid SysAttachmentVO vo) {
		sysAttachmentService.update(vo);
		return Result.ok();
	}

	@PostMapping("delete")
	@Operation(summary = "删除")
	public Result<String> delete(@RequestBody List<Long> idList) {
		sysAttachmentService.delete(idList);
		return Result.ok();
	}

	@PostMapping("export")
	@Operation(summary = "导出")
	public void export(@RequestBody @Valid SysAttachmentQuery query) {
		sysAttachmentService.export(query);
	}
}