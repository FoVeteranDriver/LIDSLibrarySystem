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
                <p class="spaceName applyItem">
                    <span class="label">申请空间</span>
                    <span class="right">{{applyInfo.applySpace}}</span>
                </p>
                <p class="userName applyItem">
                    <span class="label">申请人员</span>
                    <span class="right">{{applyInfo.applyUser}}</span>
                </p>
                <p class="theme applyItem">
                    <span class="label">主题</span>
                    <Input v-model="applyInfo.theme" class="right" ></Input>
                </p>
                <div class="member applyItem">
                    <span class="label">成员</span>
                    <Tooltip placement="right-end" always class="right">
                         <a href="javascript:">
                            <Select 
                                filterable 
                                remote 
                                label-in-value
                                placeholder="校园卡号/读者证号/学号/工号搜索" 
                                :remote-method="loadMember" 
                                :loading="loadingMember" 
                                v-model="currentMember"
                                style="width:250px">
                                <Option v-for="(option,index) in memberOptions" :value="option.name":label="option" :key="option.id">{{ option.name }}</Option>
                            </Select>
                        </a>
                        <div slot="content">
                            <p>Display multiple lines of information</p>
                        </div>
                    </Tooltip>         
                </div>
                <p class="applyDate applyItem">
                    <span class="label">日期</span>
                    <span class="right">{{getDate(date)}}</span>
                </p>
                <p class="applyTime applyItem">
                    <span class="label">时间</span>
                    <span class="right">
                        <Select v-model="timeObject.selectTime[0].sTime" style="width: 80px" @on-change='handleStartTimeChange'>
                           <Option v-for="item in beginTime" :value="item" :key="item">{{ item }}</Option>
                        </Select>
                        -
                        <Select v-model="timeObject.selectTime[0].eTime" style="width: 80px" @on-change='handleEndtTimeChange'>
                            <Option v-for="item in endTime" :value="item" :key="item">{{ item }}</Option>
                        </Select>
                    </span>
                </p>
                <p class="applyNote applyItem">
                    <span class="noteTip">请输入需要备注的信息（45）</span>
                    <span class="label">申请说明</span>
                    <Input v-model="applyInfo.note" type="textarea" placeholder="Enter something..." class="right"></Input>
                </p>
                <p class="isAgree"><Radio v-model="isAgree">同意《预约须知》条例</Radio></p>
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
import util from '../../../../libs/util.js';
export default {
    name: 'bookModal',
    data(){
        return{
            state:false,
            isSameDay:false,  //是否预约当天
            loadingMember:false,
            currentMember:'',
            memberOptions:[],
            open_time:'08:30',
            close_time:'22:00', 
            date:1524105761475,
            isAgree:false,
            beginTime:[],
            endTime:[],
            applyInfo:{applySpace:'合作研修室1',applyUser:'卓文君',theme:'',note:''},
            timeObject:{outTime:[],occupyTime:[{sTime:'09:30',eTime:'13:00'},{sTime:'19:00',eTime:'20:00'}],selectTime:[{sTime:'17:30',eTime:'18:30'}]},
        }
    },
    props:[
        'title'
    ],
    methods:{
        del(){
            this.state=!this.state;
        },
        parseTime(timeString){       //timeString:形如'hh:mm'的字符串
            let time={};
            let temp=parseInt(timeString.replace(':',''));
            time.hour=parseInt(temp/100);
            time.minute=temp%100;
            return time;
        },
        timeInit(){
            //确认是否当天预约
            let date=new Date(this.date);
            let now=new Date();
            let nowYear=now.getFullYear();
            let nowMonth=now.getMonth();
            let nowDay=now.getDate();
            if(date.getFullYear()==nowYear&&date.getMonth()==nowMonth&&date.getDate()==nowDay){
                this.isSameDay=true;
            }
            if(this.isSameDay){
                let out={};
                out.sTime=this.open_time;
                out.eTime=now.getHours()+':'+this.timeFormat(now.getMinutes());
                this.timeObject.outTime.push(out);
                this.timeObject.occupyTime.forEach((item,index,arr)=>{
                    let sBool=this.compareTime(out.eTime.split(':'),item.sTime.split(':'));
                    let eBool=this.compareTime(item.eTime.split(':'),out.eTime.split(':'));
                    if(sBool&&eBool){
                        let temp=this.findTimePoint(this.parseTime(out.eTime));
                        temp=this.timeFormat(temp.hour)+':'+this.timeFormat(temp.minute);
                        if(temp!=item.eTime){
                            item.sTime=temp;
                        }else{
                            arr.splice(index,1);
                        }  
                    }else if(sBool&&!eBool){
                        arr.splice(index,1);
                    }
                });
            }else{
                delete this.timeObject.outTime;
            }
        },
        compareTime(arr1,arr2){       //每个数组长度为2，arr[0]表示时钟，arr[1]表示分钟
            arr1.map(item=>parseInt(item));
            arr2.map(item=>parseInt(item));
            if(arr1[0]>arr2[0]||arr1[0]==arr2[0]&&arr1[1]>arr2[1]){
                return true;
            }
            return false;
        },
        timeFormat(num){  //num代表时钟/分钟
            return num<10?'0'+num:num;
        },
        getDate(date){
            date=new Date(date);
            let year=date.getFullYear();
            let month=date.getMonth()+1<10?'0'+(date.getMonth()+1):date.getMonth()+1;
            let day=date.getDate()<10?'0'+date.getDate():date.getDate();
            return year+'.'+month+'.'+day;
        },
        findTimePoint(time){    //time:类型为对象，拥有两个属性hour、minute.
            if(time.minute%5){      //非5的整数倍
                time.minute+=(5-time.minute%5);
                if(time.minute>=60){
                    time.hour+=1;
                    time.minute-=60;
                }
            }
            return time;
        },
        isSelectDone(){
            var selectTime=this.timeObject.selectTime[0];
            return typeof selectTime.sTime!=='undefined'&&typeof selectTime.eTime!=='undefined';
        },
        generateBeginTime(){
            let sPoint;
            let ePoint;
            if(this.isSameDay){
                let outTime=this.timeObject.outTime[0];
                sPoint=this.findTimePoint(this.parseTime(outTime.eTime));
            }else{
                sPoint=this.findTimePoint(this.parseTime(this.open_time));
            }
            ePoint=this.findTimePoint(this.parseTime(this.close_time));
            while(sPoint.hour<ePoint.hour||sPoint.hour==ePoint.hour&&sPoint.minute<=ePoint.minute){
                this.beginTime.push(this.timeFormat(sPoint.hour)+':'+this.timeFormat(sPoint.minute));
                sPoint.minute+=5;
                if(sPoint.minute>=60){
                    sPoint.hour+=1;
                    sPoint.minute-=60;
                }    
            }
            let occupyTime=this.timeObject.occupyTime;
            for(let item of occupyTime){
                let sIndex=this.beginTime.indexOf(item.sTime);
                let eIndex=this.beginTime.indexOf(item.eTime);
                if(sIndex!==-1&&eIndex!==-1);
                    this.beginTime.splice(sIndex,eIndex-sIndex);
            }
        },
        generateEndTime(){
            this.endTime=[];
            let sSplit=this.timeObject.selectTime[0].sTime.split(':');
            let sRange=[];
            sRange[0]=parseInt(sSplit[0])+1;   //至少预约一个小时
            sRange[1]=parseInt(sSplit[1]);
            let closeTime=this.close_time.split(':');
            if(this.compareTime(sRange,closeTime)){      //超过关闭时间
                return;
            }
            closeTime.map(item=>parseInt(item));
            let eRange=[sRange[0]+3,sRange[1]];          //至多预约长达4小时
            if(eRange[0]>closeTime[0]||eRange[0]==closeTime[0]&&eRange[1]>closeTime[1]){
                eRange=[closeTime[0],closeTime[1]];
            }
            let sCopy=[sRange[0],sRange[1]];
            while(sCopy[0]<eRange[0]||sCopy[0]==eRange[0]&&sCopy[1]<=eRange[1]){
                this.endTime.push(this.timeFormat(sCopy[0])+':'+this.timeFormat(sCopy[1]));
                sCopy[1]+=5;
                if(sCopy[1]>=60){
                    sCopy[0]++;
                    sCopy[1]-=60;
                }
            }
            for(let item of this.timeObject.occupyTime){
                let sTime=item.sTime;
                let sNode=sTime.split(':');
                if(!this.compareTime(sSplit,sNode)&&this.compareTime(sRange,sNode)){
                    this.endTime=[];
                    return;
                }
                let sIndex=this.endTime.indexOf(sTime);
                if(sIndex!=-1){
                    this.endTime.splice(sIndex+1,this.endTime.length-sIndex);
                }
            }
            return this.endTime[0];
        },
        handleStartTimeChange(){
            let eTime=this.generateEndTime();
            if(typeof eTime!=='undefined'){
                this.timeObject.selectTime[0].eTime=eTime;
            }else{
                delete this.timeObject.selectTime[0].eTime;
            }
            this.drawTimeAxis();
        },
        handleEndtTimeChange(value){
            if(value===''){
                delete this.timeObject.selectTime[0].eTime;
            }
            this.drawTimeAxis();
        },
        loadMember(query){
            if(query!=''){
                let that=this;
                that.loadingMember=true;
                that.$ajax
                    .get(
                        util.baseurl+"/user/searchUser/?key="+query
                    )
                    .then(function(response){
                        let data=response.data;
                        if(data.code==0){
                             that.loadingMember=false;
                             that.memberOptions=data.result;
                            console.log(data.result);
                        }else{
                             that.loadingMember=false;
                        }
                    })
                    .catch(function(err){
                        console.log(err);
                    });
            }else{
                this.memberOptions=[];
            }
        },
        drawTimeAxis(){
            let width = 620;  //画布的宽度
            let height = 70;   //画布的高度
            let innerWidth=580;  //刻度尺宽度
            let padding = {left:20, right:10, top:10, bottom:20}; //画布留白
            let startTime=parseInt(this.open_time.replace(':','')/100);   //当天开放时间，精确到时
            let endTime=parseInt(this.close_time.replace(':','')/100);     //当天结束时间，精确到时
            let date=new Date(this.date);     //预约时刻
            let bookYear=date.getFullYear();
            let bookMonth=date.getMonth();
            let bookDay=date.getDate();
            let dataset=[];  //数据（表示矩形的开始坐标和结束坐标）
            let color=[];    //颜色数组
            let textSet=[];  //临界时间点（已过时除外）
            let that=this;
            let timeCopy=JSON.parse(JSON.stringify(this.timeObject));
            for(let key of Object.keys(timeCopy)){
                for(let i of timeCopy[key]){
                    if(typeof i.sTime!=='undefined'&&typeof i.eTime!='undefined'){
                        i.sTime=this.parseTime(i.sTime);
                        i.eTime=this.parseTime(i.eTime);
                        let node={};
                        node.s=new Date(bookYear,bookMonth,bookDay,i.sTime.hour,i.sTime.minute);
                        node.e=new Date(bookYear,bookMonth,bookDay,i.eTime.hour,i.eTime.minute);
                        dataset.push(node);
                    }
                }
            }
            color=new Array(dataset.length);
            if(this.isSameDay){
                for(let i=0,len=color.length;i<len;i++){
                    if(i==len-1){
                        color[i]=this.isSelectDone()?'#ffcc80':'#fab5b5';
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
                        color[i]=this.isSelectDone()?'#ffcc80':'#fab5b5';
                    }else{
                        color[i]='#fab5b5';
                    }    
                }
                for(let j=0,dlen=dataset.length;j<dlen;j++){
                    textSet.push(dataset[j].s);
                    textSet.push(dataset[j].e);
                }
            }
            let linear=this.$d3.scaleTime()
                .domain([new Date(bookYear,bookMonth,bookDay,startTime),new Date(bookYear,bookMonth,bookDay,endTime)])
                .range([0,innerWidth]);
            let axis=this.$d3.axisBottom(linear)
                .ticks(14);
            let draw=document.getElementsByTagName('svg')[0];
            let svg = this.$d3.select(draw)     //选择文档中的body元素
                .attr("width", width)       //设定宽度
                .attr("height", height);    //设定高度
            svg.selectAll("*").remove();
            let rectHeight = 12;   //每个矩形所占的像素高度
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
                    return linear(d)+padding.left/2-6;
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
                    return d.getHours()+':'+that.timeFormat(d.getMinutes());
                });
        }
    },
    mounted(){
        this.state=true;
        this.timeInit();
        this.drawTimeAxis();
        this.generateBeginTime();
        this.generateEndTime();
    }
};
</script>
<style>
 
</style>
