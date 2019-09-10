package org.apache.dubbo.benchmark.rpc;

import com.youzan.platform.demo.api.DemoService1;
import com.youzan.platform.demo.api.dto.ResultDTO;

import java.util.concurrent.atomic.AtomicLong;

public abstract class AbstractClient {
//    private final AtomicInteger counter = new AtomicInteger(0);
    private final AtomicLong counter = new AtomicLong(0L);
//    private final UserService _serviceUserService = new UserServiceServerImpl();

//    protected abstract UserService getUserService();
    protected abstract DemoService1 getDemoService();


    public ResultDTO findAuthorTopics() throws Exception {
        Long authorId = counter.incrementAndGet();
        return getDemoService().findAuthorTopics(authorId);
    }

    /*public boolean existUser() throws Exception {
        String email = String.valueOf(counter.getAndIncrement());
        return getUserService().existUser(email);
    }

    public boolean createUser() throws Exception {
        int id = counter.getAndIncrement();
        User user = _serviceUserService.getUser(id);
        return getUserService().createUser(user);
    }

    public User getUser() throws Exception {
        int id = counter.getAndIncrement();
        return getUserService().getUser(id);
    }

    public Page<User> listUser() throws Exception {
        int pageNo = counter.getAndIncrement();
        return getUserService().listUser(pageNo);
    }*/

}

