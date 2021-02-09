package com.springboot.demo.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

@Data
@Slf4j
public class ValidateCodeUtil {

    private Random random = new Random();
    private String randString = "346789ABCDEFGHIJKMNPQRTUVWXY";

//    private int width = 80;
//    private int height = 26;
//    private int lineCount = 40;
//    private int codeCount = 4;
    private int width;
    private int height;
    private int lineCount;
    private int codeCount;


    private String code = null;
    private BufferedImage buffImg = null;


    public ValidateCodeUtil(int width, int height, int lineCount, int codeCount) {
        this.width = width;
        this.height = height;
        this.lineCount = lineCount;
        this.codeCount = codeCount;

        this.createCode();
    }

    private void createCode() {
        this.getRandcode();
    }

    /**
     * 获取图形byte，之后可选择将验证码以图片web展示或者附件文件流形式
     * @return
     */
    public byte[] getBuffImgBytes() {
        byte[] bytes;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(this.buffImg, "jpg", byteArrayOutputStream);
            bytes = byteArrayOutputStream.toByteArray();
        } catch (IOException var3) {
            log.warn("ValidataCode buffImg error:" + var3.getMessage());
            bytes = new byte[0];
        }

        return bytes;
    }

    /**
     * 初始化验证码
     */
    public void getRandcode() {
        BufferedImage image = new BufferedImage(this.width, this.height, 4);
        Graphics g = image.getGraphics();
        g.fillRect(0, 0, this.width, this.height);
        g.setFont(new Font("Times New Roman", 0, 18));
        g.setColor(this.getRandColor(110, 133));

        for(int i = 0; i <= this.lineCount; ++i) {
            this.drowLine(g);
        }

        String randomString = "";

        for(int i = 1; i <= this.codeCount; ++i) {
            randomString = this.drowString(g, randomString, i);
        }

        this.code = randomString;
        g.dispose();
        this.buffImg = image;
    }

    private Color getRandColor(int fc, int bc) {
        if (fc > 255) {
            fc = 255;
        }

        if (bc > 255) {
            bc = 255;
        }

        int r = fc + this.random.nextInt(bc - fc - 16);
        int g = fc + this.random.nextInt(bc - fc - 14);
        int b = fc + this.random.nextInt(bc - fc - 18);
        return new Color(r, g, b);
    }

    private void drowLine(Graphics g) {
        int x = this.random.nextInt(this.width);
        int y = this.random.nextInt(this.height);
        int xl = this.random.nextInt(13);
        int yl = this.random.nextInt(15);
        g.drawLine(x, y, x + xl, y + yl);
    }

    private String drowString(Graphics g, String randomString, int i) {
        g.setFont(this.getFont());
        g.setColor(new Color(this.random.nextInt(101), this.random.nextInt(111), this.random.nextInt(121)));
        String rand = String.valueOf(this.getRandomString(this.random.nextInt(this.randString.length())));
        randomString = randomString + rand;
        g.translate(this.random.nextInt(3), this.random.nextInt(3));
        g.drawString(rand, 13 * i, 20);
        return randomString;
    }

    /**
     * 先从配置文件获取字体
     * @return
     */
    private Font getFont() {
        return System.getProperty("ValidateCodeFont") != null ? new Font(System.getProperty("ValidateCodeFont"), 1, this.height - 3) : new Font("Fixedsys", 1, this.height - 3);
    }

    public String getRandomString(int num) {
        return String.valueOf(this.randString.charAt(num));
    }

    /**
     * 在web页面直接显示验证码图片
     * @param bytes
     */
    public static void buildImageRes(byte[] bytes){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)requestAttributes;
        HttpServletResponse httpServletResponse = servletRequestAttributes.getResponse();
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0L);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = null;
        try {
            responseOutputStream = httpServletResponse.getOutputStream();
            responseOutputStream.write(bytes);
            responseOutputStream.flush();
            responseOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
