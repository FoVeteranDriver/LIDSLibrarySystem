package com.lids.controller;

import com.lids.util.fileUtil;
import com.lids.vo.CommomDTO;
import com.lids.vo.ResultEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {

    @RequestMapping(value = "/uploadFile",method = RequestMethod.POST)
    @ResponseBody
    public CommomDTO fileUpload(@RequestParam("file") MultipartFile file){
        String fileUrl = fileUtil.saveFile(file);
        CommomDTO commomDTO = new CommomDTO();
        commomDTO.setInfo(ResultEnum.SUCCESS,fileUrl);
        return commomDTO;
    }

}
