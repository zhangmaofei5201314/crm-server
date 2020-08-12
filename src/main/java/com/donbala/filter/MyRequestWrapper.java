package com.donbala.filter;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * {\_/}
 * ( ^.^ )
 * / > @ zmf
 *
 * @date 2020/8/11
 */
//@Component
public class MyRequestWrapper extends HttpServletRequestWrapper {

    private  String body;

    public MyRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        try{
//            InputStream inputStream = request.getInputStream();
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                System.out.println("有流数据");
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    System.out.println(bytesRead);
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            }else {
                stringBuilder.append("");
            }


        }catch (IOException ex){
            throw ex;
        }finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                }catch (IOException ex){
                    throw ex;
                }
            }
        }


        body = stringBuilder.toString();

    }

    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
        ServletInputStream servletInputStream = new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }

            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
        return servletInputStream;
    }

    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    public String getBody() {
        return this.body;
    }
}
