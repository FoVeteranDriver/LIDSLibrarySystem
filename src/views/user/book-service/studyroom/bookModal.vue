<style lang="less">
    @import "./bookModal.less";
</style>
<template>
    <div>
        <Modal v-model="state"  class="bookModal">
            <p slot="header" class="header">
                <span>研习间预约申请</span>
            </p>
            <div class="tips">
                <div>
                    <p class="left">当天开放：<span class="timeSlot">8:30-22:00</span></p>
                    <p class="center">允许时长：<span class="timeSlot">1小时-4小时</span></p>
                    <p class="right">预约属性：<span class="timeSlot">需审核</span></p>
                </div>
                <div>
                    <p class="left">预约至少提前：<span class="timeSlot">0</span></p>
                    <p class="center">迟到取消预约时长：<span class="timeSlot">30分钟</span></p>
                </div>
                <div>
                    <p class="left">预约最多提前：<span class="timeSlot">6天</span></p>
                    <p class="center">人数限制：<span class="timeSlot">4-10</span></p>
                </div>
            </div>
            <svg class="svgBar"></svg>
            <div class="applyInfo">
                <p><span>申请空间</span><span>{{applyInfo.applySpace}}</span></p>
                <p><span>申请人员</span><span>{{applyInfo.applyUser}}</span></p>
                <p><span>主题</span><Input v-model="applyInfo.theme"></Input></p>
                <div>
                    <span>成员</span>
                    <Tooltip placement="right-end" always>
                         <a href="javascript:">
                            <Input placeholder="校园卡号/读者证号/学号/工号搜索">
                            <span slot="prepend"> <Icon type="ios-plus-empty"></Icon></span>
                            </Input>
                        </a>
                        <div slot="content">
                            <p>Display multiple lines of information</p>
                        </div>
                    </Tooltip>         
                </div>
                <p><span>日期</span><span>{{getDate(date)}}</span></p>
                <p>
                    <span>时间</span>
                    <Select v-model="timeObject.selectTime.sTime" style="width: 80px">
                        <Option value="16:30">16:30</Option>
                        <Option value="17:00">17:00</Option>
                    </Select>
                    -
                     <Select v-model="timeObject.selectTime.eTime" style="width: 80px">
                        <Option value="17:30">17:30</Option>
                        <Option value="18:00">18:00</Option>
                    </Select>
                </p>
                <p>
                    <span>请输入需要备注的信息（45）</span>
                    <span>申请说明</span>
                    <Input v-model="applyInfo.note" type="textarea" placeholder="Enter something..."></Input>
                </p>
                <p><Radio v-model="isAgree">同意《预约须知》条例</Radio></p>
            </div>
            <div slot="footer" class="footer">
                <Button type="primary" @click="del">同意</Button>
                <Button type="ghost" @click="del">返回</Button>
            </div>
        </Modal>
    </div>   
</template>
<script>
import {event as currentEvent} from 'd3';
export default {
    name: 'bookModal',
    data(){
        return{
            state:false,
            open_time:'08:30',
            close_time:'22:00',
            date:1523419411525,
            isAgree:false,
            applyInfo:{applySpace:'合作研修室1',applyUser:'卓文君',theme:'',note:''},
            timeObject:{outTime:[],occupyTime:[{sTime:'13:00',eTime:'15:00'},{sTime:'20:00',eTime:'21:00'}],selectTime:[{sTime:'16:30',eTime:'17:30'}]},
        }
    },
    props:[
        'title'
    ],
    methods:{
        del(){
            this.state=!this.state;
        },
        parseTime(timeString){
            var time={};
            var temp=parseInt(timeString.replace(':',''));
            time.hour=parseInt(temp/100);
            time.minute=temp%100;
            return time;
        },
        isSameDay(date){
            var now=new Date();
            var nowYear=now.getFullYear();
            var nowMonth=now.getMonth();
            var nowDay=now.getDate();
            return date.getFullYear()==nowYear&&date.getMonth()==nowMonth&&date.getDate()==nowDay;
        },
        minuteFormat(date){
            var minute=date.getMinutes();
            return minute<10?'0'+minute:minute;
        },
        getDate(date){
            date=new Date(date);
            var year=date.getFullYear();
            var month=date.getMonth()+1<10?'0'+(date.getMonth()+1):date.getMonth()+1;
            var day=date.getDate()<10?'0'+date.getDate():date.getDate();
            return year+'.'+month+'.'+day;
        }
    },
    mounted(){
        this.state=true;
        var width = 620;  //画布的宽度
        var height = 70;   //画布的高度
        var innerWidth=580;  //刻度尺宽度
        var padding = {left:20, right:10, top:10, bottom:20}; //画布留白
        var startTime=parseInt(this.open_time.replace(':','')/100);   //当天开放时间，精确到时
        var endTime=parseInt(this.close_time.replace(':','')/100);     //当天结束时间，精确到时
        var date=new Date(this.date);     //预约时刻
        var bookYear=date.getFullYear();
        var bookMonth=date.getMonth();
        var bookDay=date.getDate();
        var isSameDay=false;  //是否预约当天
        var dataset=[];  //数据（表示矩形的开始坐标和结束坐标）
        var color=[];    //颜色数组
        var textSet=[];  //临界时间点（已过时除外）
        var that=this;
        //确认是否当天预约
        if(this.isSameDay(date)){
            isSameDay=true;
            var now=new Date();
            var out={};
            out.sTime=this.open_time;
            out.eTime=now.getHours()+':'+this.minuteFormat(now);
            this.timeObject.outTime.push(out);
        }else{
            delete this.timeObject.outTime;
        }
        for(var key of Object.keys(this.timeObject)){
            for(var i of this.timeObject[key]){
                i.sTime=this.parseTime(i.sTime);
                i.eTime=this.parseTime(i.eTime);
                var node={};
                node.s=new Date(bookYear,bookMonth,bookDay,i.sTime.hour,i.sTime.minute);
                node.e=new Date(bookYear,bookMonth,bookDay,i.eTime.hour,i.eTime.minute);
                dataset.push(node);
            }
        }
        color=new Array(dataset.length);
        if(isSameDay){
            for(let i=0,len=color.length;i<len;i++){
                if(i==len-1){
                    color[i]='#ffcc80';
                }else if(i==0){
                    color[i]='#c9c9c9';
                }else{
                    color[i]='#fab5b5';
                }    
            }
            for(let j=1,dlen=dataset.length;j<dlen;j++){
                textSet.push(dataset[j].s);
                textSet.push(dataset[j].e);
            }
        }else{
            for(let i=0,len=color.length;i<len;i++){
                if(i==len-1){
                    color[i]='#c9c9c9';
                }else{
                    color[i]='#fab5b5';
                }    
            }
            for(let j=0,dlen=dataset.length;j<dlen;j++){
                textSet.push(dataset[j].s);
                textSet.push(dataset[j].e);
            }
        }
  
        var linear=this.$d3.scaleTime()
            .domain([new Date(bookYear,bookMonth,bookDay,startTime),new Date(bookYear,bookMonth,bookDay,endTime)])
            .range([0,innerWidth]);
        var axis=this.$d3.axisBottom(linear)
            .ticks(14);
        var draw=document.getElementsByTagName('svg')[0];
        var svg = this.$d3.select(draw)     //选择文档中的body元素
            .attr("width", width)       //设定宽度
            .attr("height", height);    //设定高度
        var rectHeight = 12;   //每个矩形所占的像素高度(包括空白)
        svg.selectAll("rect")     //矩形条目
            .data(dataset)
            .enter()
            .append("rect")
            .attr("x",function(d,i){
                return linear(d.s)+padding.left;
            })
            .attr("y",height - padding.bottom-12)
            .attr("width",function(d){
                return linear(d.e)-linear(d.s);
            })
            .attr("height",rectHeight)
            .attr("fill",function(d,i){
                return color[i];
            });
        svg.append('g')           //坐标轴
            .attr("class","axis")
            .attr("transform","translate(" + padding.left + "," + (height - padding.bottom) + ")")
            .call(axis);
        svg.selectAll('.toolTip')     //气泡背景
            .data(textSet)
            .enter()
            .append('rect')
            .attr('class','toolTip')
            .attr('x',function(d,i){
                return linear(d)+padding.left/2-6;
            })
            .attr('y',height-padding.bottom-36)
            .attr("width",function(d){
                return 32;
            })
            .attr('rx',10)
            .attr('ry',20)
            .attr("height",20)
            .attr("fill",function(d,i){
                return '#666666';
            });
        svg.selectAll('.text')   //气泡文字
            .data(textSet)
            .enter()
            .append('text')
            .attr('class','text')
            .attr('x',function(d,i){
                return linear(d)+padding.left/2-4;
            })
            .attr('y',height-padding.bottom-30)
            .attr("dx",function(d){
                return 2;
            })
            .attr("dy",function(d){
                return 8;
            })
            .attr('textLength',28)
            .attr('fill','#fff')
            .text(function(d){
                return d.getHours()+':'+that.minuteFormat(d);
            });
    }
};
</script>
<style>
 
</style>
