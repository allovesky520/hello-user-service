package com.springboot.demo.domin.user.repository;

import com.springboot.demo.domin.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
/** null repository
* @Author yyzh
* @Date 2021-02-20 10:19:53 

*/

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity>{}
