package com.spring.boot.study.common;


import org.springframework.mock.web.DelegatingServletInputStream;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * 使request inputStream 能多次读取
 */
public class RepeatedlyReadRequestWrapper extends HttpServletRequestWrapper {
    private byte[] buffer;

    public RepeatedlyReadRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(request.getInputStream());
        int len = request.getContentLength();
        if (len > 0) {
            buffer = new byte[len];
            bufferedInputStream.read(buffer);
        } else {
            buffer = new byte[0];
        }
        bufferedInputStream.close();
    }

    @Override
    public ServletInputStream getInputStream() {
        return new DelegatingServletInputStream(new ByteArrayInputStream(buffer));
    }
}
