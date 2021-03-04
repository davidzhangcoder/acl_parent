package com.acl.service;


import com.acl.entity.SecurityUser;
import com.acl.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service( value="userDetailsService" )
public class LoginService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 判断用户名是否存在
        if (!"admin".equals(username) && !"sales".equals(username)) {
            throw new UsernameNotFoundException("用户名不存在!");
        }
        // 从数据库中获取的密码 atguigu 的密文
        String pwd = "d7b79bb6d6f77e6cbb5df2d0d2478361"; //MD5
        //String pwd = "$2a$10$2R/M6iU3mCZt3ByG7kwYTeeW0w7/UqdeXrb27zkBIizBvAven0/na"; //
        if("admin".equals(username)) {
//            return new User(username, pwd, AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_managerrole"));

            User curUser = new User(username, pwd);
            SecurityUser securityUser = new SecurityUser(curUser);

            List<String> authorities = new ArrayList<String>();
            authorities.add("admin");
            authorities.add("ROLE_managerrole");
            securityUser.setPermissionValueList(authorities);
            return securityUser;
        }
        else {
//            return new User(username, pwd, AuthorityUtils.commaSeparatedStringToAuthorityList("sales,"));
            User curUser = new User(username, pwd);
            SecurityUser securityUser = new SecurityUser(curUser);

            List<String> authorities = new ArrayList<String>();
            authorities.add("sales");
            securityUser.setPermissionValueList(authorities);
            return securityUser;

        }
    }
}