package com.lids.controller;

import com.lids.common.BaseController;
import com.lids.util.TimeUtil;
import com.lids.vo.CommomDTO;
import com.lids.vo.ResultEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class InfoController extends BaseController {

    @RequestMapping("/info/login")
    @ResponseBody
    public CommomDTO loginInfo(){
        return new CommomDTO(ResultEnum.NO_LOGIN);
    }

    @RequestMapping("/info/unauth")
    @ResponseBody
    public CommomDTO unpermissionInfo(){
        return new CommomDTO(ResultEnum.NO_PERMISSION);
    }

    /**
     * 获取信息共享空间用户须知
     * @return
     */
    @RequestMapping("/info/bookingProvision")
    @ResponseBody
    public CommomDTO bookingProvision(){
        CommomDTO commomDTO = new CommomDTO();
        //TODO 从数据库获取须知条款
        String title = "信息共享空间须知";
        String text = " 信息共享空间面向全校师生开放，为您提供一个灵活多样的学习、工作环境，有利于释放您的创新能量。\n 1.使用信息共享空间需遵守国家法律及学校有关规章制度，信息共享空间仅提供本校师生学术研究所用，不得用于任何商业用途。\n" +
                "2.信息共享空间实行预约使用，用户通过图书馆信息共享空间网站进行空间预约。无预约用户在信息共享空间有待分配空间时在服务台现场预约。申请条件请阅各空间说明。\n" +
                "3.进入信息共享空间，需至服务台刷卡登记，空间使用结束后如时间未到请刷卡退出。预约成功后如不能按时到场，请提前登陆系统删除预约，否则将作该帐户一周内不能进行预约的处罚。\n" +
                "4.使用电脑请预约知识空间：100号以上普通PC；100号以下Mac电脑，需先到咨询台领取无线键鼠；自带电脑请预约学习空间：分为有显示器和无显示器座位，请根据个人需求进行选择；学习空间和知识空间在预约时间30分钟内刷卡选座（未到预约时间请勿刷卡以免删除预约记录），其他空间到咨询台登记使用。\n" +
                "5.知识空间资源有限，仅用于电子资源检索，空间预约半小时内不登陆系统（输入学/工号和校园卡消费密码，初始身份证后六位数字）将产生违约；使用电子资源请遵守知识产权保护条例，严禁恶意下载。\n" +
                "6.请爱护使用信息共享空间所有设施，规范使用设备、软件等，如发现信息共享空间设备故障或有其他疑问，请立即与工作人员联系，不得擅自处理，否则由此造成的机器损坏或软件破坏须做相应赔偿。";
        Map map = new HashMap();
        map.put("title",title);
        map.put("text",text);
        commomDTO.setInfo(ResultEnum.SUCCESS,map);
        return commomDTO;
    }


    /**
     * 返回当前的服务器时间
     * @return
     */
    @RequestMapping("/info/serverTime")
    @ResponseBody
    public CommomDTO serverTime(){
        Date time = new Date();
        String timeS = TimeUtil.formatTime(time.getTime());
        CommomDTO commomDTO = new CommomDTO();
        commomDTO.setInfo(ResultEnum.SUCCESS,timeS);
        return commomDTO;
    }

}
