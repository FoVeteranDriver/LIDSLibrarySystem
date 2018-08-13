<style lang="less">
    @import "./display.less";
</style>
<template>
    <div id="display" class="admin-display">
        <Row :gutter="24">
            <Col span="18">
                <div class="module">
                    <div class="home" v-show="vertical=='home'">
                        <Row>
                            <Col :xs="{ span: 11, offset: 1 }" :sm="{ span: 15, offset: 1 }" :md="{ span: 15, offset: 1 }" :lg="{ span: 15, offset: 1 }" class="left">
                                <h1 class="home-title">Home Page</h1>
                                <div class="display-area">
                                    <img :src="home.mainPic"/>
                                </div>
                                <div class="home-info">
                                    {{home.mainInfo}}
                                </div>
                            </Col>
                            <Col :xs="{span:10,offset:1}" :sm="{span:6,offset:1}" :md="{span:6,offset:1}" :lg="{span:6,offset:1}" class="right">
                                <div class="logo">
                                    <img :src="home.logo"/>
                                </div>
                                <div class="display-menu">
                                    <template v-for="item in home.menu">
                                        <div class="display-menu-item">
                                            <img :src="item.pic"/>
                                            <div class="inner-menu-item">
                                                <h3>{{item.title}}</h3>
                                                <p>{{item.text}}</p>
                                            </div>
                                        </div>
                                    </template>
                                </div>
                            </Col>
                        </Row>
                    </div>
                    <div class="usinghelp" v-show="vertical=='usinghelp'">
                        <h1 class="title">Introduce</h1>
                        <Row>
                            <Col span="8">
                                <p class="prolusion">
                                    {{usinghelp.prolusion}}
                                </p>
                                <template v-for="(item,index) in usinghelp.tips.slice(0,3)">
                                    <p class="tip">
                                    {{index+1}}.&nbsp;{{item.text}}
                                    </p>
                                </template>
                            </Col>
                            <Col span="8">
                                <div class="center-Pic">
                                    <img :src="usinghelp.mainPic"/>
                                </div>
                            </Col>
                            <Col span="8">
                                <template v-for="(item,index) in usinghelp.tips.slice(3)">
                                    <p class="tip">
                                        {{index+4}}.&nbsp;&nbsp; {{item.text}}
                                    </p>
                                </template>
                            </Col>
                        </Row>
                    </div>
                </div>
            </Col>
            <Col span="5" offset="1">
                <div class="right">
                    <div class="btn-item">
                        <Button class="custom-btn" @click="handleNoticeOpen">通知</Button>
                    </div>
                    <div class="btn-item">
                        <Button class="custom-btn" @click="handleItemsOpen">项目</Button>
                    </div>   
                    <RadioGroup v-model="vertical" vertical>
                        <Radio label="home">
                            <span>主页</span>
                        </Radio>
                        <Radio label="usinghelp"> 
                            <span>使用帮助</span>
                        </Radio>
                        <Radio label="studyRoom">   
                            <span>研习间</span>
                        </Radio>
                        <Radio label="seat">
                            <span>学习间</span>
                        </Radio>
                </RadioGroup>
                </div>
            </Col>
        </Row>
    </div>
</template>
<script>
import util from '../../../../libs/util.js';
export default {
    name: 'display',
    data(){
        return {
            vertical: 'home',
            home:{
                mainPic:'',
                mainInfo:'',
                logo:'./static/images/admin/settings/display/logo.png',
                menu:[{
                    pic:'./static/images/admin/settings/display/resource.png',
                    text:'',
                    title:'',
                },{
                    pic:'./static/images/admin/settings/display/shared.png',
                    text:'' ,
                    title:'',
                },{
                    pic:'./static/images/admin/settings/display/service.png',
                    text:'' ,
                    title:'',
                }]
            },
            usinghelp:{
                tips:[{
                    text:'使用信息共享空间需遵守国家法律及学校有关规章制度，信息共享空间仅提供本校师生学术研究所用，不得用于任何商业用途',
                },{
                    text:'信息共享空间实行预约使用，用户通过图书馆信息共享空间网站进行空间预约。无预约用户在信息共享空间有待分配空间时在服务台现场预约。申请条件请阅各空间说明。'
                },{
                    text:'进入信息共享空间，需至服务台刷卡登记，空间使用结束后如时间未到请刷卡退出。预约成功后如不能按时到场，请提前登陆系统删除预约，否则将作该帐户一周内不能进行预约的处罚。',
                },{
                    text:'使用电脑请预约知识空间：100号以上普通PC；100号以下Mac电脑，需先到咨询台领取无线键鼠；自带电脑请预约学习空间：分为有显示器和无显示器座位，请根据个人需求进行选择；学习空间和知识空间在预约时间30分钟内刷卡选座（未到预约时间请勿刷卡以免删除预约记录），其他空间到咨询台登记使用。'
                },{
                    text:'知识空间资源有限，仅用于电子资源检索，空间预约半小时内不登陆系统（输入学/工号和校园卡消费密码，初始身份证后六位数字）将产生违约；使用电子资源请遵守知识产权保护条例，严禁恶意下载。',
                },{
                    text:'请爱护使用信息共享空间所有设施，规范使用设备、软件等，如发现信息共享空间设备故障或有其他疑问，请立即与工作人员联系，不得擅自处理，否则由此造成的机器损坏或软件破坏须做相应赔偿。'
                },{
                    text:'请按照预约分配到的空间和设备进行使用，空间内请勿饮食，保持环境整洁。'
                }],
                mainPic:'./static/images/user/system-service/mainPic.jpg',
                prolusion:'信息共享空间面向全校师生开放，为您提供一个灵活多样的学习、工作环境，有利于释放您的创新能量'
            },

        }
    },
    mounted () {
        
    },
    beforeDestroy () {

    },
    created(){
        this.loadHomePage();
        this.loadUsingHelp();
    },
    methods: {
        loadHomePage(){
            let that = this;
            that.$ajax
                .get(
                    util.adminUrl+"/homePage/"
                )
                .then(function(response){
                    let data=response.data; 
                    if(data.code==0){
                        let result=data.result;
                        that.home.mainPic=result.mainPic;
                        that.home.mainInfo=result.mainInfo;
                        let index=0;
                        for(let item of result.menu){
                            that.home.menu[index].title=item.title;
                            that.home.menu[index].text=item.text;
                            index++;
                        }
                    }else if(data.code==5){ //用户没有登录或者禁用cookie
                        
                    }
                })
                .catch(function(err){
                    console.log(err);
                });
        },
        loadUsingHelp(){
            let that = this;
            that.$ajax
                .get(
                    util.adminUrl+"/usinghelp/"
                )
                .then(function(response){
                    let data=response.data; 
                    if(data.code==0){
                        let result=data.result;
                        that.usinghelp.mainPic=result.mainPic;
                        that.usinghelp.prolusion=result.prolusion;
                        that.usinghelp.tips=result.tips;
                    }else if(data.code==5){ //用户没有登录或者禁用cookie
                        
                    }
                })
                .catch(function(err){
                    console.log(err);
                });
        },
        handleNoticeOpen(){
            this.$router.push({name:'admin_notice'});
        },
        handleItemsOpen(){
            this.$router.push({name:'admin_infoEdit'});
        }
    },
};
</script>