<style lang="less">
    @import "./home.less";
</style>
<template>
    <div class="home-main">
        <Row>
            <Col :xs="{ span: 16, offset: 0 }" :lg="{ span: 17, offset: 0 }" class="main">
                <Row>
                    <Col :xs="{ span: 11, offset: 1 }" :sm="{ span: 15, offset: 1 }" :md="{ span: 15, offset: 1 }" :lg="{ span: 15, offset: 1 }" class="left">
                        <h1 class="home-title">Home Page</h1>
                        <div class="display-area">
                            <img src="../../../images/user/home/library.jpg"/>
                        </div>
                        <div class="home-info">
                            这是一种新型的空间，一种新型的学习模式，一种共享的交流社区。
                            配置计算机、网络、有线电视、投影仪等设施以及常用的专业软件，
                            打通了通信载体、内容分类、物理空间的界限，提供了资源的一站式服务，
                            读者在享受最新的阅读体验的同时可以对空间进行多信息的交互和共享。
                        </div>
                    </Col>
                    <Col :xs="{span:10,offset:1}" :sm="{span:6,offset:1}" :md="{span:6,offset:1}" :lg="{span:6,offset:1}" class="right">
                        <div class="logo">
                            <img src="../../../images/user/home/logo.png"/>
                        </div>
                        <div class="display-menu">
                            <div class="display-menu-item"> 
                                <img src="../../../images/user/home/resource.png"/>     
                                <div class="inner-menu-item">
                                    <h3>资源</h3>
                                    <p>大量设施完善管理有序的资源</p>
                                </div>
                            </div>
                             <div class="display-menu-item">
                                <img src="../../../images/user/home/shared.png"/>
                                <div class="inner-menu-item">
                                    <h3>共享</h3>
                                    <p>信息化的共享管理方式</p>
                                </div>
                            </div>
                            <div class="display-menu-item">
                                <img src="../../../images/user/home/service.png"/>
                                <div class="inner-menu-item">
                                    <h3>服务</h3>
                                    <p>为广大读者提供更多优质的服务</p>
                                </div>
                            </div>
                        </div>
                    </Col>
                </Row>
            </Col>
            <Col :xs="{ span: 7, offset: 1 }" :lg="{ span: 5, offset: 1 }" class="sidebar">
                <div class="notice">
                    <router-link :to="{name:'noticeDetail',params:{notice:notice}}">
                    <div class="heaer">
                        <h1>最新通知</h1>
                        <h3>Latest Notice</h3>
                    </div>
                    <div class="content">
                        <img src="../../../images/user/home/notice.png"/>
                        <h2>{{notice.noticeTitle}}</h2>
                        <p class="notice-tip">
                            {{noticeOmit}}
                            <span v-if="notice.noticeText.length>45">
                                ...<span class="detailIcon">[详情]</span>
                            </span>
                        </p>
                        <span class="publish-time" v-if="notice.publishTime!==''">
                            {{notice.publishTime.year}}/{{notice.publishTime.month}}/{{notice.publishTime.day}}
                            &nbsp;
                            {{notice.publishTime.hour}}:{{notice.publishTime.minute}}
                        </span>
                    </div>
                    </router-link>
                </div>
                <div class="bookList">
                    <div class="header">
                        <h1>今日预约</h1>
                        <h3>Today's Reservation</h3>
                    </div>
                    <div class="content" ref="carousel">
                        <div class="original" ref="original">
                            <template v-for="(item, index) in bookList">
                                <div v-if="index%2==0" class="even book-item">
                                    <div v-if="typeof item.note!=='undefined'"  class="remark">{{item.note}}</div>
                                    <div v-else class="noRemark"></div>
                                    <div class="seatNote">
                                        <span class="seatNum">{{item.seatNUm}}</span>
                                        <span class="bState state" v-if="item.state==0">预约成功</span>
                                        <span class="vState state" v-else>已生效</span>
                                    </div>
                                    <div class="noteTime">
                                        <Icon type="ios-clock-outline"></Icon>
                                        <span>{{item.startTime}}</span>
                                        <span>{{item.date}}</span>
                                        <span>{{item.endTime}}</span>
                                    </div>
                                </div>
                                <div v-else class="odd book-item">
                                    <div v-if="typeof item.note!=='undefined'" class="remark">{{item.note}}</div>
                                    <div v-else class="noRemark"></div>
                                    <div class="seatNote">
                                        <span class="seatNum">{{item.seatNUm}}</span>
                                         <span class="bState state" v-if="item.state==0">预约成功</span>
                                         <span class="vState state" v-else>已生效</span>
                                    </div>
                                    <div class="noteTime">
                                        <Icon type="ios-clock-outline"></Icon>
                                        <span>{{item.startTime}}</span>
                                        <span>{{item.date}}</span>
                                        <span>{{item.endTime}}</span>
                                    </div>
                                </div>
                            </template>
                         </div>
                         <div class="copy" ref="copy"></div>
                    </div>
                </div>
            </Col>
        </Row>
    </div>
</template>

<script>
import util from '../../../libs/util.js';
export default {
    name: 'home',
    data () {
        return {
          bookList:[],
          notice:{
              noticeTitle:'',
              noticeText:'',
              publishTime:''
          }
        };
    },
    mounted(){
        var original=this.$refs.original;
        var copy=this.$refs.copy;
        var carousel=this.$refs.carousel;
        var speed=50;
        var marquee=function(){
            if(copy.offsetTop-carousel.scrollTop<=0){
                carousel.scrollTop-=original.offsetHeight;
            }else{
                carousel.scrollTop+=2;
            }
            marId=setTimeout(marquee,speed);
        }
        var marId=setTimeout(marquee,speed);
        carousel.onmouseout=function(){marId=setTimeout(marquee,speed);}
        carousel.onmouseover=function(){clearTimeout(marId);}
        this.$nextTick(()=>{
            copy.innerHTML=original.innerHTML;
        });
        this.init();
    },
    methods:{
        init(){
            let that=this;
            that.$ajax
                .get(
                    util.baseurl+"/notice"
                )
                .then(function(response){
                    let data=response.data;
                    if(data.code==0){
                        that.notice.noticeTitle=data.result.noticeTitle;
                        that.notice.noticeText=data.result.noticeText;
                        that.notice.publishTime=util.parseTimestamp(data.result.createTime);
                    }else{
                        that.notice.noticeTitle='没有通知!';
                    }
                })
                .catch(function(err){
                    console.log(err);
                });
            that.getBookRecords();
            setTimeout(that.polling,30000);
        },
        getBookRecords(){
            let that=this;
            that.bookList=[];
            that.$ajax
                .get(
                    util.baseurl+"/booking/todayBookRecords"
                )
                .then(function(response){
                    let data=response.data;
                    if(data.code==0){
                        let dataList=data.result;
                        for(let item of dataList){
                            let temp={};
                            temp.note=item.application;
                            temp.seatNUm=item.name;
                            temp.state=(item.is_active&&!item.has_check_in)?0:1;
                            temp.startTime=item.begin_time.slice(0,5);
                            temp.endTime=item.end_time.slice(0,5);
                            temp.date=item.date.slice(4);
                            that.bookList.push(temp);
                        }
                        let len=that.bookList.length;
                        if(len==1){
                            that.bookList.push(that.bookList[0]);
                            that.bookList.push(that.bookList[0]);
                            len=that.bookList.length;
                        }else if(len==2){
                            that.bookList.push(that.bookList[0]);
                            that.bookList.push(that.bookList[1]);
                            len=that.bookList.length;
                        }
                        if(len>=3&&len<=5){    //数目在此范围内的预约条目会被加倍
                            let mirror=[];
                            for(let item of that.bookList){
                                mirror.push(item);
                            }
                            that.bookList=that.bookList.concat(mirror);
                        }
                        if(that.bookList.length%2!==0&&that.bookList.length>6){
                            that.bookList.pop();  //只有偶数条目才不会出现连续颜色块
                        }
                        that.scrollShow();
                    }
                })
                .catch(function(err){
                    console.log(err);
                });
        },
        scrollShow(){
            var original=this.$refs.original;
            var copy=this.$refs.copy;
            var carousel=this.$refs.carousel;
            this.$nextTick(()=>{
                if(original){
                    copy.innerHTML=original.innerHTML;
                    carousel.scrollTop=0;
                }
              
            });
        },
        polling(){
            this.getBookRecords();
            setTimeout(this.polling,30000);
        }
    },
     computed:{
        noticeOmit(){
            return this.notice.noticeText.slice(0,45);
        }
    }
};
</script>
