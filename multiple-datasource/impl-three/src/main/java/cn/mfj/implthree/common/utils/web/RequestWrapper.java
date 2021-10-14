package cn.mfj.implthree.common.utils.web;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * 重写HttpServletRequest，让其支持重复读取请求body内容
 * <br/>
 * Request的getReader、getInputStream是以流的形式读出去的，并且读一次就会空，
 * 需要将读取的信息暂存地方，流可以随时从暂存区再次读取。
 * <br/>
 *
 * @author favian.meng on 2021-10-14
 */
public class RequestWrapper extends HttpServletRequestWrapper {

    private final String body;

    /**
     * 将Request的流读取类成员body中暂存
     *
     * @param request 请求
     */
    public RequestWrapper(HttpServletRequest request) {
        super(request);
        StringBuilder stringBuilder = new StringBuilder();
        InputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
            if (inputStream != null) {
                byte[] byteBuffer = new byte[1024];
                int readLength;
                while ((readLength = inputStream.read(byteBuffer)) > 0) {
                    stringBuilder.append(new String(byteBuffer), 0, readLength);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        body = stringBuilder.toString();
    }

    @Override
    public ServletInputStream getInputStream() {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
        return new ServletInputStream() {
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
            public int read() {
                return byteArrayInputStream.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }
}
