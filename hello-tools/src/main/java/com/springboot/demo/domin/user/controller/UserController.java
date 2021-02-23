package com.springboot.demo.domin.user.controller;

import com.springboot.demo.constant.Result;
import com.springboot.demo.annotation.sysLog.aspect.SysLog;
import com.springboot.demo.domin.base.controller.BaseController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import com.springboot.demo.domin.user.entity.UserEntity;
import com.springboot.demo.domin.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**

/** null controller
* @Author yyzh

* @Date Sat Feb 20 10:19:53 CST 2021
*/

@RestController
@RequestMapping("/user")
@Api(value = "userController")
@Slf4j
public class UserController extends BaseController {

	@Autowired
	private UserService service;

	@SysLog("根据ID获取{tableComment}唯一数据")
	@GetMapping(value = "/getOneById")
	@ApiOperation(value = "根据ID获取{tableComment}唯一数据")
	public Result getOneById(@RequestParam("id") Long id){
		return service.getOneById(id);
	}

	@SysLog("根据ID删除{tableComment}唯一数据")
	@GetMapping(value = "/deleteById")
	@ApiOperation(value = "根据ID删除{tableComment}唯一数据")
	public Result deleteById(@RequestParam("id") Long id){

		return service.deleteById(id);

	}

	@SysLog("新增{tableComment} 信息")
	@GetMapping(value = "/saveOne")
	@ApiOperation(value = "新增{tableComment} 信息")
	public Result saveOne(@RequestBody UserEntity entity){

		return service.saveOne(entity);

	}


	@SysLog("修改 {tableComment}信息")
	@GetMapping(value = "/updateEntity")
	@ApiOperation(value = "修改 {tableComment}信息")
	public Result updateEntity(@RequestBody UserEntity entity){

		return service.updateEntity(entity);

	}

	@SysLog("分页查找{tableComment}数据")
	@GetMapping(value = "/getByPage")
	@ApiOperation(value = "分页查找{tableComment}数据")
	public Result<Page<UserEntity>> getByPage(@RequestBody UserEntity entity,@RequestHeader("page") Integer page,@RequestHeader("pageSize") Integer pageSize){
		return service.getByPage(entity,page,pageSize);
	}
}
