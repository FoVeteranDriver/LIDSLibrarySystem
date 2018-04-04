package com.lids.util;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

//生成二维码的工具
public class QrGenUtil {

    public static ByteArrayOutputStream createQrGen(String url) throws IOException {
        //如果有中文，可使用withCharset("UTF-8")方法

        //设置二维码url链接，图片宽度250*250，JPG类型
        return QRCode.from(url).withSize(250, 250).to(ImageType.JPG).stream();
    }


}
