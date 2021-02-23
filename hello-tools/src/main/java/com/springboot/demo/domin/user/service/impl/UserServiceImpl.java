package com.springboot.demo.domin.user.service.impl;

import org.springframework.stereotype.Service;
import com.springboot.demo.constant.Result;
import org.springframework.beans.factory.annotation.Autowired;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import com.springboot.demo.domin.user.entity.UserEntity;
import com.springboot.demo.domin.user.service.UserService;
import com.springboot.demo.domin.user.repository.UserRepository;

/**

/** null service impl
* @Author yyzh

* @Date Sat Feb 20 10:19:53 CST 2021
*/

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository rep;

	@Override
	public Result getOneById(Long id){
 		UserEntity one = rep.getOne(id);
		return Result.ok(one);
	}

	@Override
	public Result deleteById(Long id){
 		rep.deleteById(id);
		return Result.ok();
	}


	@Override
	public Result updateEntity(UserEntity entity){
	if(rep.getOne(entity.getId() ) ==null) return Result.failed(0,"璇ヨ褰曚笉瀛樺湪!");
 		rep.saveAndFlush(entity);
		return Result.ok();
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public Result saveOne(UserEntity entity){
 		UserEntity one = rep.save(entity);
		return Result.ok();
	}

	@Override
	public Result<Page<UserEntity>> getByPage(UserEntity entity, Integer page, Integer pageSize){
			Specification querySpecif =  new Specification() {
	            @Override
		           public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
		                List<Predicate> predicates = new ArrayList<>();

			if( entity.getId() != null ){
				predicates.add(criteriaBuilder.equal(root.get("id"),  entity.getId() ));
			}
			if(entity.getName().isEmpty() ){
				predicates.add(criteriaBuilder.equal(root.get("name"),  entity.getName() ));
			}
			if( entity.getSex() != null ){
				predicates.add(criteriaBuilder.equal(root.get("sex"),  entity.getSex() ));
			}
			if( entity.getBirthday() != null ){
				predicates.add(criteriaBuilder.equal(root.get("birthday"),  entity.getBirthday() ));
			}
			if( entity.getStatus() != null ){
				predicates.add(criteriaBuilder.equal(root.get("status"),  entity.getStatus() ));
			}
			if( entity.getCreateTime() != null ){
				predicates.add(criteriaBuilder.equal(root.get("create_time"),  entity.getCreateTime() ));
			}
			if(entity.getCreateBy().isEmpty() ){
				predicates.add(criteriaBuilder.equal(root.get("create_by"),  entity.getCreateBy() ));
			}
			if( entity.getUpdateTime() != null ){
				predicates.add(criteriaBuilder.equal(root.get("update_time"),  entity.getUpdateTime() ));
			}
			if(entity.getUpdateBy().isEmpty() ){
				predicates.add(criteriaBuilder.equal(root.get("update_by"),  entity.getUpdateBy() ));
			}
 		               return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		            }
	        };
	        Sort sort = new Sort(Sort.Direction.DESC,"id");
	        page =page==null? 0:page;   	        pageSize =pageSize==null? 0:pageSize;   	        PageRequest pageRequest = new PageRequest(page,pageSize, sort);
	Page<UserEntity> pages=rep.findAll(querySpecif,pageRequest);
	return Result.ok(pages);
	
	}
}
