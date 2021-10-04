package com.bjpowernode.service.impl;

import com.bjpowernode.mapper.AdminMapper;
import com.bjpowernode.pojo.Admin;
import com.bjpowernode.pojo.AdminExample;
import com.bjpowernode.service.AdminService;
import com.bjpowernode.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    //业务逻辑层一定会用到数据库访问层
    @Autowired
    AdminMapper adminMapper;
    @Override
    public Admin login(String name, String pwd) {

        //根据传入的用户到数据库中查询相应的用户对象
        AdminExample example = new AdminExample();
        /*
        * 如何添加条件
        * select * from admin where a_name = "admin"
        * */
        //添加用户名a_name条件
        example.createCriteria().andANameEqualTo(name);
        List<Admin> list = adminMapper.selectByExample(example);
        //如果查询到用户，再进行密码的对比
        if (list.size()>0){
            Admin admin = list.get(0);
            //如果查询到用户，再进行密码的对比
            String miPwd = MD5Util.getMD5(pwd);
            if (miPwd.equals(admin.getaPass())){
                return admin;
            }
        }
        return null;
    }
}
