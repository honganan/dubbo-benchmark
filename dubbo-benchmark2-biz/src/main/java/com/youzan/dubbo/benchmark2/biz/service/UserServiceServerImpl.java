package com.youzan.dubbo.benchmark2.biz.service;


import com.youzan.platform.demo.api.DemoService1;

import org.apache.dubbo.benchmark.api.bean.Page;
import org.apache.dubbo.benchmark.api.bean.User;
import org.apache.dubbo.benchmark.api.service.UserService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class UserServiceServerImpl implements UserService, ApplicationContextAware {

    private DemoService1 demoService;

    @Override
    public boolean existUser(String email) {
//        if (email == null || email.isEmpty()) {
//            return true;
//        }
//
//        if (email.charAt(email.length() - 1) < '5') {
//            return false;
//        }
//
//        return true;
        return demoService.existUser(email);
    }

    @Override
    public User getUser(long id) {
        return demoService.getUser(id);

//        User user = new User();
//
//        user.setId(id);
//        user.setName(new String("Doug Lea"));
//        user.setSex(1);
//        user.setBirthday(LocalDate.of(1968, 12, 8));
//        user.setEmail(new String("dong.lea@gmail.com"));
//        user.setMobile(new String("18612345678"));
//        user.setAddress(new String("北京市 中关村 中关村大街1号 鼎好大厦 1605"));
//        user.setIcon(new String("https://www.baidu.com/img/bd_logo1.png"));
//        user.setStatus(1);
//        user.setCreateTime(LocalDateTime.now());
//        user.setUpdateTime(user.getCreateTime());
//
//        List<Integer> permissions = new ArrayList<Integer>(
//                Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 19, 88, 86, 89, 90, 91, 92));
//
//        user.setPermissions(permissions);
//
//        return user;
    }

    @Override
    public Page<User> listUser(int pageNo) {
        return demoService.listUser(pageNo);
//
//        List<User> userList = new ArrayList<>(15);
//
//        for (int i = 0; i < 15; i++) {
//            User user = new User();
//
//            user.setId(i);
//            user.setName("Doug Lea" + i);
//            user.setSex(1);
//            user.setBirthday(LocalDate.of(1968, 12, 8));
//            user.setEmail("dong.lea@gmail.com" + i);
//            user.setMobile("18612345678" + i);
//            user.setAddress("北京市 中关村 中关村大街1号 鼎好大厦 1605" + i);
//            user.setIcon("https://www.baidu.com/img/bd_logo1.png" + i);
//            user.setStatus(1);
//            user.setCreateTime(LocalDateTime.now());
//            user.setUpdateTime(user.getCreateTime());
//
//            List<Integer> permissions = new ArrayList<Integer>(
//                    Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 19, 88, 86, 89, 90, 91, 92));
//            user.setPermissions(permissions);
//
//            userList.add(user);
//        }
//
//        Page<User> page = new Page<>();
//        page.setPageNo(pageNo);
//        page.setTotal(1000);
//        page.setResult(userList);
//
//        return page;
    }

    @Override
    public boolean createUser(User user) {
        return demoService.createUser(user);

//        if (user == null) {
//            return false;
//        }
//
//        return true;
    }

    public DemoService1 getDemoService() {
        return demoService;
    }

    public void setDemoService(DemoService1 demoService) {
        this.demoService = demoService;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        demoService = (DemoService1) applicationContext.getBean("demoService");
    }
}

