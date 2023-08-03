package org.javaboy.tcprc.service;

import org.javaboy.tcprc.pojo.RequestParam;
import org.javaboy.tcprc.pojo.Response;
import org.javaboy.tcprc.pojo.Student;

/**
 * @author:majin.wj
 */
public interface HelloService {

    String hello(String name, Integer age);

    Response<Student> testRequest(RequestParam param);
}
