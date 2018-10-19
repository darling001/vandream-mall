package com.vandream.mall.commons.utils;

import com.alibaba.fastjson.JSON;
import java.io.*;

/**
 * @author Li Jie
 */
public class DeepCopy<T> {
    public  <T> T cloneByStream(T t) throws Exception{
        ByteArrayOutputStream bout = new ByteArrayOutputStream();

        ObjectOutputStream oos = new ObjectOutputStream(bout);

        oos.writeObject(t);

        ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());

        ObjectInputStream ois = new ObjectInputStream(bin);

        return (T) ois.readObject();
    }

    public  <T> T cloneByJson(T t) {
        String tJson = JSON.toJSONString(t);
        return (T) JSON.parseObject(tJson, t.getClass());
    }
}
