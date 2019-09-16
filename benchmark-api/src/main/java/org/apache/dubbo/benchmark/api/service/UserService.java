package org.apache.dubbo.benchmark.api.service;

import org.apache.dubbo.benchmark.api.bean.Page;
import org.apache.dubbo.benchmark.api.bean.User;

public interface UserService {
    public boolean existUser(String email);

    public boolean createUser(User user);

    public User getUser(long id);

    public Page<User> listUser(int pageNo);

}

