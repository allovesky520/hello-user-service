package com.springboot.demo.domin.user.service;

import com.springboot.demo.constant.Result;
import org.springframework.data.domain.Page;
import com.springboot.demo.domin.user.entity.UserEntity;

/**

/** null service interface
* @Author yyzh

* @Date Sat Feb 20 10:19:53 CST 2021
*/

public interface UserService{

 /**
  * 根据ID获取{tableComment}唯一数据
  */
   Result getOneById(Long id);

 /**
  *  根据ID删除{tableComment}唯一数据
  */
   Result deleteById(Long id);

 /**
 *  新增{tableComment} 信息
 */
   Result saveOne(UserEntity entity);

 /**
 * 修改 {tableComment}信息 
 */
   Result updateEntity(UserEntity entity);

 /**
 * 分页查找{tableComment}数据
 */
   Result<Page<UserEntity>> getByPage(UserEntity entity, Integer page, Integer pageSize);
}
