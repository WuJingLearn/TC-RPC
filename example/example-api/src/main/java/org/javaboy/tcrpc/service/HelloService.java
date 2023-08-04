package org.javaboy.tcrpc.service;

import org.javaboy.tcrpc.pojo.RequestParam;
import org.javaboy.tcrpc.pojo.Response;
import org.javaboy.tcrpc.pojo.Student;

/**
 * @author:majin.wj
 */
public interface HelloService {

    String hello(String name, Integer age);

    Response<Student> testRequest(RequestParam param);
}
