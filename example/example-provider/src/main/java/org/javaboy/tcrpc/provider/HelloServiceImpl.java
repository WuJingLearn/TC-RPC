package org.javaboy.tcrpc.provider;

import org.javaboy.tcrpc.pojo.RequestParam;
import org.javaboy.tcrpc.pojo.Response;
import org.javaboy.tcrpc.pojo.Student;
import org.javaboy.tcrpc.service.HelloService;
import org.javaboy.tcrpc.annotation.TCService;

/**
 * @author:majin.wj
 */
@TCService(interfaceClass = HelloService.class)
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String name, Integer age) {
        System.out.println("服务端执行结果执行。。。");
        return "sucess..";
    }

    @Override
    public Response<Student> testRequest(RequestParam param) {
        Student student = new Student();
        student.setAge(10);
        student.setName("2");
        return Response.of(student);
    }
}
