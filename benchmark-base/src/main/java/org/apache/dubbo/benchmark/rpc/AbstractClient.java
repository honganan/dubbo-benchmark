package org.apache.dubbo.benchmark.rpc;

import org.apache.dubbo.benchmark.api.bean.Page;
import org.apache.dubbo.benchmark.api.bean.User;
import org.apache.dubbo.benchmark.api.service.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractClient {
    private final AtomicInteger counter = new AtomicInteger(0);

    protected abstract UserService getUserService();

    public boolean existUser() throws Exception {
        String email = String.valueOf(counter.getAndIncrement());
        return getUserService().existUser(email);
    }

    public boolean createUser() throws Exception {
        int id = counter.getAndIncrement();
        User user = new User();

        user.setId(id);
        user.setName(new String("Doug Lea"));
        user.setSex(1);
        user.setBirthday(LocalDate.of(1968, 12, 8));
        user.setEmail(new String("dong.lea@gmail.com"));
        user.setMobile(new String("18612345678"));
        user.setAddress(new String("北京市 中关村 中关村大街1号 鼎好大厦 1605"));
        user.setIcon(new String("https://www.baidu.com/img/bd_logo1.png"));
        user.setStatus(1);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(user.getCreateTime());

        List<Integer> permissions = new ArrayList<Integer>(
            Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 19, 88, 86, 89, 90, 91, 92));

        user.setPermissions(permissions);
        return getUserService().createUser(user);
    }

    public User getUser() throws Exception {
        int id = counter.getAndIncrement();
        return getUserService().getUser(id);
    }

    public Page<User> listUser() throws Exception {
        int pageNo = counter.getAndIncrement();
        return getUserService().listUser(pageNo);
    }

}

