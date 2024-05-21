package springboot.controller;


import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.common.captcha.CaptchaController;
import springboot.common.page.PageResult;
import springboot.common.request.RequestSingleParam;
import springboot.common.utils.PwdUtil;
import springboot.common.utils.Result;
import springboot.common.utils.TokenEntity;
import springboot.constants.Constants;
import springboot.entity.UsersEntity;
import springboot.query.UsersQuery;
import springboot.service.UsersService;
import springboot.vo.SysAccountLoginVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 登录相关
 */
@RequestMapping("user")
@RestController
@AllArgsConstructor
public class UsersController {

	private final UsersService userService;
	private final CaptchaController captchaController;
	private static final String StpPre = "user";

	/**
	 * 账号密码登录
	 */
	@SaIgnore
	@PostMapping(value = "/login")
	public Result<TokenEntity> login(@RequestBody SysAccountLoginVO sysAccountLoginVO) {
		//验证码
		captchaController.validateCaptcha(sysAccountLoginVO.getCaptcha(), sysAccountLoginVO.getKey());
		UsersEntity user = userService.getByUsername(sysAccountLoginVO.getUsername());
		if (user == null || !user.getPassword().equals(PwdUtil.encrypt(sysAccountLoginVO.getPassword()))) {
			return Result.error("账号或密码不正确");
		}
		StpUtil.login(StpPre + user.getId());
		TokenEntity tokenEntity = new TokenEntity();
		tokenEntity.setUsername(user.getUsername());
		tokenEntity.setUserid(user.getId());
		tokenEntity.setRole(user.getRole());
		tokenEntity.setTablename("users");
		tokenEntity.setAddtime(user.getAddtime());
		tokenEntity.setAvatarUrl(user.getTouxiang());
		tokenEntity.setMoney(user.getMoney());
		tokenEntity.setToken(StpUtil.getTokenValue());
		StpUtil.getSession().set(Constants.CURRENT_USER, tokenEntity);
		return Result.ok(tokenEntity);
	}

	/**
	 * @return springboot.common.utils.Result<java.lang.Boolean>
	 * @title: sendEmail
	 * @Description //通过邮箱登录--发送邮件
	 * @Param [sysAccountLoginVO]
	 **/
	@SaIgnore
	@PostMapping(value = "/sendEmail")
	public Result<Boolean> sendEmail(@RequestBody SysAccountLoginVO sysAccountLoginVO) {
		UsersEntity user = userService.getByUsername(sysAccountLoginVO.getUsername());
		if (StrUtil.isBlank(user.getEmail())) {
			return Result.error("用户邮箱为空,无法登录");
		}
		//发送邮箱验证码
		captchaController.sendEmail(user.getEmail());
		return Result.ok(true);
	}

	/**
	 * @return springboot.common.utils.Result<springboot.common.utils.TokenEntity>
	 * @title: loginByEmail
	 * @Description //邮箱登录
	 * @Param [sysAccountLoginVO]
	 **/
	@SaIgnore
	@PostMapping(value = "/loginByEmail")
	public Result<TokenEntity> loginByEmail(@RequestBody SysAccountLoginVO sysAccountLoginVO) {
		//验证码
		UsersEntity user = userService.getByUsername(sysAccountLoginVO.getUsername());
		captchaController.validateEmailCaptcha(sysAccountLoginVO.getCaptcha(), user.getEmail());
		StpUtil.login(StpPre + user.getId());
		TokenEntity tokenEntity = new TokenEntity();
		tokenEntity.setUsername(user.getUsername());
		tokenEntity.setUserid(user.getId());
		tokenEntity.setRole(user.getRole());
		tokenEntity.setTablename("users");
		tokenEntity.setAddtime(user.getAddtime());
		tokenEntity.setAvatarUrl(user.getTouxiang());
		tokenEntity.setMoney(user.getMoney());
		tokenEntity.setToken(StpUtil.getTokenValue());
		StpUtil.getSession().set(Constants.CURRENT_USER, tokenEntity);
		return Result.ok(tokenEntity);
	}

	/**
	 * 注册
	 */
	@SaIgnore
	@PostMapping(value = "/register")
	public Result<Boolean> register(@RequestBody UsersEntity user) {
		if (userService.getOne(new QueryWrapper<UsersEntity>().eq("username", user.getUsername())) != null) {
			return Result.error("用户已存在");
		}
		user.setPassword(PwdUtil.encrypt(user.getPassword()));
		userService.save(user);
		return Result.ok(true);
	}


	/**
	 * 密码重置
	 */
	@SaIgnore
	@RequestMapping(value = "/resetPass")
	public Result resetPass(String username) {
		UsersEntity user = userService.getOne(new QueryWrapper<UsersEntity>().eq("username", username));
		if (user == null) {
			return Result.error("账号不存在");
		}
		user.setPassword(PwdUtil.encrypt(Constants.RESETPASS));
		userService.update(user, null);
		return Result.ok("密码已重置为：123456");
	}

	/**
	 * 列表
	 */
	@PostMapping("/page")
	public Result page(@RequestBody UsersQuery user) {
		PageResult<UsersEntity> page = userService.queryPage(user);
		return Result.ok(page);
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	public Result list(@RequestBody UsersEntity user) {
		QueryWrapper<UsersEntity> ew = new QueryWrapper<>(user);
		return Result.ok(userService.selectListView(ew));
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info")
	public Result<UsersEntity> info(@RequestSingleParam(value = "id") String id) {
		UsersEntity user = userService.getById(id);
		return Result.ok(user);
	}

	/**
	 * 获取用户的session用户信息
	 */
	@RequestMapping("/session")
	public Result getCurrUser(HttpServletRequest request) {
		return Result.ok(StpUtil.getSession().get(Constants.CURRENT_USER));
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Result save(@RequestBody UsersEntity user) {
		if (userService.getOne(new QueryWrapper<UsersEntity>().eq("username", user.getUsername())) != null) {
			return Result.error("用户已存在");
		}
		userService.save(user);
		return Result.ok(true);
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody UsersEntity user) {
		if (StrUtil.isNotBlank(user.getPassword())) {
			user.setPassword(PwdUtil.encrypt(user.getPassword()));
		}
		userService.updateById(user);//全部更新
		return Result.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public Result delete(@RequestBody List<Long> idList) {
		userService.removeByIds(idList);
		return Result.ok();
	}
}
