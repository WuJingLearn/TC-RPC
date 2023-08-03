package org.javaboy.tcrpc.client.request;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/**
 * @author:majin.wj
 */
@Builder
@Getter
public class Invocation {

    private String serviceKey;
    private String methodName;
    private Class<?>[] argTypes;
    private Object[] args;
    private Map<String, Object> extraDatas;

}
