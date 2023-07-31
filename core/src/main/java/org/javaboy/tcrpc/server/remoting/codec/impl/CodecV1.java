package org.javaboy.tcrpc.server.remoting.codec.impl;

import io.netty.buffer.ByteBuf;
import org.javaboy.tcrpc.exception.RpcException;
import org.javaboy.tcrpc.serializer.Serializer;
import org.javaboy.tcrpc.serializer.SerializerFactory;
import org.javaboy.tcrpc.server.remoting.codec.Codec;
import org.javaboy.tcrpc.server.remoting.codec.CodecUtil;
import org.javaboy.tcrpc.server.remoting.packet.Request;
import org.javaboy.tcrpc.server.remoting.packet.Header;
import org.javaboy.tcrpc.server.remoting.packet.Response;
import org.javaboy.tcrpc.server.remoting.packet.TCProtocol;

/**
 * @author:majin.wj
 */
public class CodecV1 implements Codec {

    public static byte type = 1;

    @Override
    public void encode(TCProtocol protocol, ByteBuf out) throws RpcException {
        Header header = protocol.getHeader();
        encodeHeader(header, out);
        // 临时占位
        int bodyIndex = out.writerIndex();
        out.writeLong(-1);
        int curIndex = out.writerIndex();
        Serializer serializer = SerializerFactory.get(header.getSerializeType());
        Object body = protocol.getBody();
        if (body instanceof Request) {
            Request request = (Request) body;
            encodeRequest(request, out, serializer);
        } else if (body instanceof Response) {
            Response response = (Response) body;
            encodeResponse(response, out, serializer);
        }
        long bodyLength = out.writerIndex() - curIndex;
        out.setLong(bodyIndex, bodyLength);
    }

    private void encodeHeader(Header header, ByteBuf out) {
        out.writeByte(header.getType());
        out.writeByte(header.getSerializeType());
        out.writeByte(header.getRequestType());
        out.writeLong(header.getId());
    }

    private void encodeRequest(Request request, ByteBuf out, Serializer serializer) {
        CodecUtil.writeString(request.getServiceKey(), out);
        CodecUtil.writeString(request.getMethodName(), out);
        Class<?>[] argTypes = request.getArgTypes();
        int length = argTypes.length;
        out.writeInt(length);
        for (Class<?> argType : argTypes) {
            CodecUtil.writeString(argType.getName(), out);
        }
        Object[] args = request.getArgs();
        for (int i = 0; i < args.length; i++) {
            byte[] data = serializer.serialize(args);
            CodecUtil.writeBytes(data, out);
        }
    }

    private void encodeResponse(Response response, ByteBuf out, Serializer serializer) {
        boolean success = response.isSuccess();
        out.writeBoolean(success);
        if (success) {
            String retTypeName = response.getRetType().getName();
            CodecUtil.writeString(retTypeName, out);
            byte[] data = serializer.serialize(response.getRetValue());
            out.writeBytes(data);
        } else {
            CodecUtil.writeString(response.getErrorMsg(), out);
        }
    }

    @Override
    public TCProtocol decode(ByteBuf buf) throws RpcException {
        TCProtocol protocol = new TCProtocol();
        Header header = decodeRequestHeader(buf);
        protocol.setHeader(header);
        Serializer serializer = SerializerFactory.get(header.getSerializeType());
        Object body = null;
        if (header.getRequestType() == TCProtocol.REQUEST) {
            body = decodeRequest(buf, serializer);
        } else if (header.getRequestType() == TCProtocol.RESPONSE) {
            body = decodeResponse(buf, serializer, ((int) header.getLength()));
        }
        protocol.setBody(body);
        return protocol;
    }

    private Header decodeRequestHeader(ByteBuf buf) {
        Header header = new Header();
        header.setType(buf.readByte());
        header.setSerializeType(buf.readByte());
        header.setRequestType(buf.readByte());
        header.setId(buf.readLong());
        header.setLength(buf.readLong());
        return header;
    }

    private Request decodeRequest(ByteBuf buf, Serializer serializer) {
        Request request = new Request();
        String serviceKey = CodecUtil.readString(buf);
        String methodName = CodecUtil.readString(buf);
        request.setServiceKey(serviceKey);
        request.setMethodName(methodName);
        int argsTypeLength = buf.readInt();
        Class<?>[] argsType = new Class[argsTypeLength];
        for (int i = 0; i < argsTypeLength; i++) {
            String argTypeName = null;
            try {
                argTypeName = CodecUtil.readString(buf);
                Class<?> type = Class.forName(argTypeName);
                argsType[i] = type;
            } catch (ClassNotFoundException e) {
                throw new RpcException("CodecV1 decode error,argType cant find argTypeName:" + argTypeName);
            }
        }
        request.setArgTypes(argsType);
        // 参数
        Object[] args = new Object[argsType.length];
        for (int i = 0; i < argsType.length; i++) {
            byte[] data = CodecUtil.readBytes(buf);
            Object arg = serializer.deSerialize(data, argsType[i]);
            args[i] = arg;
        }
        request.setArgs(args);
        return request;
    }

    private Response decodeResponse(ByteBuf buf, Serializer serializer, int bodyLength) {
        Response response = new Response();
        int bodyStart = buf.readerIndex();
        boolean success = buf.readBoolean();
        if (success) {
            String retTypeName = CodecUtil.readString(buf);
            Class<?> retType;
            try {
                retType = Class.forName(retTypeName);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            int bodySize = bodyLength - (buf.readerIndex() - bodyStart);
            byte[] data = new byte[bodySize];
            buf.readBytes(data);
            Object retValue = serializer.deSerialize(data, retType);
            response.setRetValue(retValue);
            response.setRetType(retType);
        } else {
            String errorMsg = CodecUtil.readString(buf);
            response.setErrorMsg(errorMsg);
        }
        return response;
    }


}
