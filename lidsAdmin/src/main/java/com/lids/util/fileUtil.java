package com.lids.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class fileUtil {

    public static String saveFile(MultipartFile file){
        // 判断文件是否为空
        String fileUrl = null;
        if (!file.isEmpty()) {
            try {

                // 文件保存路径
                String fileName = file.getOriginalFilename();

//                UUID uuid = UUID.randomUUID();
//                String contentType=file.getContentType();
//                String name=contentType.substring(contentType.indexOf("/")+1);
//                String fileName = uuid.toString() + "." + name;

//                String filePath = "I:\\other\\nginx-1.13.0\\html\\jpg\\"+fileName;

                String filePath = "C:\\nginx-1.13.0\\html\\jpg\\"+fileName;
                // 转存文件
                file.transferTo(new File(filePath));

                fileUrl = "http://iyou.s1.natapp.cc/"+"jpg/"+fileName;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fileUrl;
    }


}
