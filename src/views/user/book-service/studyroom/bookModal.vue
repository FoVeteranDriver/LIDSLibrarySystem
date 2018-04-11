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
            <div>hahaha</div>
            <div slot="footer" class="footer">
                <Button type="error" size="large" long  @click="del">Delete</Button>
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
            timeObject:{occupyTime:[{sTime:'17:00',eTime:'18:00'},{sTime:'19:00',eTime:'21:00'}],selectTime:[{sTime:'15:00',eTime:'16:30'}]},
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
        }
    },
    mounted(){
        this.state=true;
        var width = 620;  //画布的宽度
        var height = 100;   //画布的高度
        var innerWidth=580;  //刻度尺宽度
        var padding = {left:20, right:10, top:10, bottom:20};
        var startTime=parseInt(this.open_time.replace(':','')/100);
        var endTime=parseInt(this.close_time.replace(':','')/100);
        var date=new Date(this.date);
        var bookYear=date.getFullYear();
        var bookMonth=date.getMonth();
        var bookDay=date.getDate();
        var isSameDay=false;
        var that=this;
        //var color = this.$d3.scaleOrdinal(this.$d3.schemeCategory10);
        //确认预约是否是当天预约
        if(this.isSameDay(date)){
            isSameDay=true;
            var now=new Date();
            this.timeObject.outTime=[];
            var out={};
            out.sTime=this.open_time;
            out.eTime=now.getHours()+':'+this.minuteFormat(now);
            this.timeObject.outTime.push(out);
        }
        var dataset=[];//数据（表示矩形的宽度）
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
        var color=new Array(dataset.length);
        var textSet=[];
        if(isSameDay){
            for(let i=0,len=color.length;i<len;i++){
                if(i==len-2){
                    color[i]='#ffcc80';
                }else if(i==len-1){
                    color[i]='#c9c9c9';
                }else{
                    color[i]='#fab5b5';
                }    
            }
            for(let j=0,dlen=dataset.length-1;j<dlen;j++){
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
        svg.selectAll("rect")
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
        svg.append('g')
            .attr("class","axis")
            .attr("transform","translate(" + padding.left + "," + (height - padding.bottom) + ")")
            .call(axis);
        svg.selectAll('.toolTip')
            .data(textSet)
            .enter()
            .append('react')
            .attr('class','toolTip')
            .attr('x',function(d,i){
                return linear(d)+padding.left/2;
            })
            .attr('y',height-padding.bottom-36)
            .attr("dy",function(d){
                return 20;
            })
            .text(function(d){
                return d.getHours()+':'+that.minuteFormat(d);
            });
    }
};
</script>
<style>
 
</style>
