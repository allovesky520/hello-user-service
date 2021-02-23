package com.springboot.demo.domin.user.entity;

import com.springboot.demo.domin.base.BaseEntity;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import java.util.Date;

import java.io.Serializable;

import javax.persistence.*;
import lombok.EqualsAndHashCode;

/**
/** null entity 
* @Author yyzh
* @Date 2021-02-20 10:19:53 

*/

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_user")
public class UserEntity extends BaseEntity implements Serializable {

	private static final long serialVersionUID =  1957306296920749502L;

	private Long id;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 性别，0：男性，1：女生
	 */
	private Integer sex;

	/**
	 * 出生日期
	 */
	private Date birthday;

}
