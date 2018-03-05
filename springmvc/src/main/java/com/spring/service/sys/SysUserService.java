package com.spring.service.sys;

import com.spring.dao.sys.SysUserDao;
import com.spring.domain.sys.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Date:        16:16 08/18/2017
 * Version:     1.0
 * Description: ${Description}
 */
@Service
public class SysUserService {

	@Value("${environment}")
	private String environment;
	@Autowired
	private SysUserDao sysUserDao;

	public List<SysUser> queryAllUsers() {
		return sysUserDao.queryAllUsers();
	}

	public SysUser getUserById(Long userId) {
		System.out.println("environment: " + environment);
		return sysUserDao.getById(userId);
	}
}
