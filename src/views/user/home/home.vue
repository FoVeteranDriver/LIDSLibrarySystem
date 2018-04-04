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
                    <Col :xs="{span:10,offset:1}" :sm="{span:6,offset:1}" :md="{span:6,offset:1}" :lg="{span:6,offset:1}"class="right">
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
                    <div class="heaer">
                        <h1>最新通知</h1>
                        <h3>Latest Notice</h3>
                    </div>
                    <div class="content">
                        <img src="../../../images/user/home/notice.png"/>
                        <h2>关于信息共享空间网上预约时间调整的通知</h2>
                        <p class="notice-tip"> 为合理利用图书馆资源，提升馆内座位使用效率，从2017年4月1日起，个人座位预约时间将由提前7日改为提前2日可预约，具体包括基础馆三楼信息共享</p>
                        <span class="publish-time">2018/03/19 13:59</span>
                    </div>
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
export default {
    name: 'home',
    data () {
        return {
          bookList:[
              {
                  "note":'自习',
                  "seatNUm":'F8-004',
                  "state":0,
                  "startTime":'6:42',
                  "endTime":'14:50',
                  "date":'-04-26'
              },
              {
                  "seatNUm":'F8-004',
                  "state":1,
                  "startTime":'6:42',
                  "endTime":'14:50',
                  "date":'-04-26'
              },
              {
                  "seatNUm":'F8-004',
                  "state":0,
                  "startTime":'6:42',
                  "endTime":'14:50',
                  "date":'-04-26'
              },
              {
                  "note":'自习',
                  "seatNUm":'F8-004',
                  "state":1,
                  "startTime":'6:42',
                  "endTime":'14:50',
                  "date":'-04-26'
              },
              {
                  "note":'自习',
                  "seatNUm":'F8-004',
                  "state":1,
                  "startTime":'6:42',
                  "endTime":'14:50',
                  "date":'-04-26'
              },
               {
                  "seatNUm":'F8-004',
                  "state":1,
                  "startTime":'6:42',
                  "endTime":'14:50',
                  "date":'-04-26'
              },
          ]
        };
    },
    mounted(){
        var original=this.$refs.original;
        var copy=this.$refs.copy;
        var carousel=this.$refs.carousel;
        var speed=100;
        copy.innerHTML=original.innerHTML;
        var marquee=function(){
            if(copy.offsetTop-carousel.scrollTop<=0){
                carousel.scrollTop-=copy.offsetHeight;
            }else{
                carousel.scrollTop+=2;
            }
            marId=setTimeout(marquee,speed);
        }
        var marId=setTimeout(marquee,speed);
        carousel.onmouseout=function(){marId=setTimeout(marquee,speed);}
        carousel.onmouseover=function(){clearTimeout(marId);}
    },
    methods:{
        
    }
};
</script>
