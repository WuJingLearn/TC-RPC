package org.javaboy.tcrpc.consumer;

import org.javaboy.tcrpc.pojo.RequestParam;
import org.javaboy.tcrpc.pojo.Response;
import org.javaboy.tcrpc.pojo.Student;
import org.javaboy.tcrpc.service.HelloService;
import org.javaboy.tcrpc.annotation.TCReference;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author:majin.wj
 */
@Component
public class RpcController {


    @TCReference
    HelloService helloService;


    @PostConstruct
    public void init() {
        String result = helloService.hello("zs", 10);
        System.out.println("call result:" + result);
        Response<Student> studentResponse = helloService.testRequest(new RequestParam());
        System.out.println("收到结果:"+studentResponse);

    }

}
