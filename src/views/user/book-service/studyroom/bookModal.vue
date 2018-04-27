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
                    <span class="right">{{ applyInfo.applySpace }}</span>
                </p>
                <p class="userName applyItem">
                    <span class="label">申请人员</span>
                    <span class="right">{{ applyInfo.applyUser }}</span>
                </p>
                <p class="theme applyItem">
                    <span class="label">主题</span>
                    <Input v-model="applyInfo.theme" class="right" ref="theme" :maxlength=30 ></Input>
                </p>
                <div class="member applyItem">
                    <span class="label">成员</span>
                    <Tooltip placement="right-end" always class="right">
                         <a href="javascript:">
                            <Select 
                                filterable 
                                remote
                                clearable
                                placeholder="校园卡号/读者证号/学号/工号搜索" 
                                :remote-method="loadMember" 
                                :loading="loadingMember" 
                                @on-change='handleMemberSelect'
                                style="width:250px">
                                <Option v-for="option in memberOptions" :value="JSON.stringify(option)":label="option.name" :key="option.id">
                                    {{ option.name }}
                                </Option>
                            </Select>
                        </a>
                        <div slot="content">
                            <Tag type="dot">{{applyInfo.applyUser}}</Tag>
                            <Tag v-for="item in memberGroup" :key="item.id" :name="item.id" closable type="dot" @on-close="handleDeleteMember">
                                {{ item.name }}
                            </Tag>
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
                        <Select v-model="timeObject.selectTime[0].eTime" style="width: 80px" @on-change='handleEndTimeChange'>
                            <Option v-for="item in endTime" :value="item" :key="item">{{ item }}</Option>
                        </Select>
                    </span>
                </p>
                <p class="applyNote applyItem">
                    <span class="noteTip">请输入需要备注的信息（45）</span>
                    <span class="label">申请说明</span>
                    <Input v-model="applyInfo.note" type="textarea" placeholder="Enter something..." class="right" @on-keyup='handleNoteChange'></Input>
                </p>
                <p class="isAgree"><Radio v-model="isAgree"></Radio><a target='_blank' @click="handleNoticeOpen">同意《预约须知》条例</a></p>
            </div>
            <div slot="footer" class="footer">
                <Button type="primary" @click="handleSubmit" class="submit">提交</Button>
                <Button type="ghost" @click="del" class="return">返回</Button>
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
            memberGroup:[],
            open_time:this.openTime,
            close_time:this.closeTime, 
            date:this.bookDate,
            isAgree:false,
            beginTime:[],
            endTime:[],
            memberOptions:[],
            applyInfo:{
                applySpace:this.spaceName,
                spaceId:this.spaceId,
                applyUser:this.bookUser,
                theme:'',
                note:''
            },
            timeObject:{
                outTime:[],
                occupyTime:[],
                selectTime:[]
            },
        }
    },
    props:{
            bookDate:{
                type:Number,
                required: true
            },
            openTime:{
                type:String,
                required:true
            },
            closeTime:{
                type:String,
                required:true
            },
            bookUser:{
                type:String,
                required:true
            },
            spaceName:{
                type:String,
                required:true
            },
            spaceId:{
                type:String,
                required:true
            },
            occupyTime:{   //每个元素是一个对象，每个对象拥有两个属性:sTime、eTime
                type:Array,
                required:true
            },
            selectTime:{      //两个属性：sTime、eTime
                type:Object,
                required:true
            }
    },
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
        dataInit(){
            if(this.open_time.length!==5){
                let temp=this.open_time.split(':');
                this.open_time=this.timeFormat(temp[0])+':'+this.timeFormat(temp[1]);
            }
            if(this.close_time.length!==5){
                let temp=this.close_time.split(':');
                this.close_time=this.timeFormat(temp[0])+':'+this.timeFormat(temp[1]);
            }
            this.timeObject.selectTime.push(this.selectTime);
            for(let item of this.occupyTime){
                this.timeObject.occupyTime.push(item);
            }
            for(let item in this.timeObject){
                let arr=this.timeObject[item];
                if(arr.length){
                    for(let time of arr){
                        for(let i in time){
                            if(time[i].length!==5){
                                let temp=time[i].split(':');
                                time[i]=this.timeFormat(temp[0])+':'+this.timeFormat(temp[1]);
                            }
                        }
                    }
                }
            }
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
                let occupyTime=this.timeObject.occupyTime;
                for(let i=0;i<occupyTime.length;i++){              //当天已过期时间不再显示预约信息
                    let item=occupyTime[i];
                    let sBool=this.compareTime(out.eTime.split(':'),item.sTime.split(':'));
                    let eBool=this.compareTime(item.eTime.split(':'),out.eTime.split(':'));
                    if(sBool&&eBool){
                        let temp=this.findTimePoint(this.parseTime(out.eTime));
                        temp=this.timeFormat(temp.hour)+':'+this.timeFormat(temp.minute);
                        if(temp!=item.eTime){    //他人预约时间部分过期，将开始时间截取到当前时刻点
                            item.sTime=temp;
                        }else{
                            occupyTime.splice(i,1);
                            i--;
                        }  
                    }else if(sBool&&!eBool){  //他人预约时间已完全过期
                        occupyTime.splice(i,1);
                        i--;
                    }
                }
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
            if(typeof num=='string'){
                if(parseInt(num)<10&&num.length<2){
                    num='0'+num;
                }
            }else if(typeof num=='number'){
                if(num<10){
                    num='0'+num;
                }
            }
            return num;
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
        timeWarning(){
            if(typeof this.timeObject.selectTime[0].eTime=='undefined'){
                this.$Message.error({
                        content: '缺少结束时间，请重新选择开始时间'
                });
            }
        },
        generateBeginTime(){
            let sPoint;
            let ePoint;
            if(this.isSameDay){
                let outTime=this.timeObject.outTime[0];
                sPoint=this.findTimePoint(this.parseTime(outTime.eTime));
                if(this.compareTime(outTime.eTime.split(':'),this.timeObject.selectTime[0].sTime.split(':'))){
                    this.timeObject.selectTime[0].sTime=this.timeFormat(sPoint.hour)+':'+this.timeFormat(sPoint.minute);
                }
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
            for(let item of occupyTime){  //除去他人已占用时间段
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
                this.timeWarning();
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
                if(!this.compareTime(sSplit,sNode)&&this.compareTime(sRange,sNode)){  //无法满足至少预约一小时的要求
                    this.endTime=[];
                    this.timeWarning()
                    return;
                }
                let sIndex=this.endTime.indexOf(sTime);
                if(sIndex!=-1){     //4小时时间段部分被他人占用，丢弃被他人占用时间以及后续时间
                    this.endTime.splice(sIndex+1,this.endTime.length-sIndex);
                }
            }
            return this.endTime[0];
        },
        handleNoticeOpen(){
            this.$Notice.open({
                    title: '信息共享空间须知',
                    desc: `&nbsp;&nbsp;信息共享空间面向全校师生开放，为您提供一个灵活多样的学习、工作环境，有利于释放您的创新能量。
                          <p>1.使用信息共享空间需遵守国家法律及学校有关规章制度，信息共享空间仅提供本校师生学术研究所用，不得用于任何商业用途。</p>
                          <p>2.信息共享空间实行预约使用，用户通过图书馆信息共享空间网站进行空间预约。无预约用户在信息共享空间有待分配空间时在服务台现场预约。申请条件请阅各空间说明。</p>
                          <p>3.进入信息共享空间，需至服务台刷卡登记，空间使用结束后如时间未到请刷卡退出。预约成功后如不能按时到场，请提前登陆系统删除预约，否则将作该帐户一周内不能进行预约的处罚。</p>
                          <p>4.使用电脑请预约知识空间：100号以上普通PC；100号以下Mac电脑，需先到咨询台领取无线键鼠；自带电脑请预约学习空间：分为有显示器和无显示器座位，请根据个人需求进行选择；学习空间和知识空间在预约时间30分钟内刷卡选座（未到预约时间请勿刷卡以免删除预约记录），其他空间到咨询台登记使用。</p>
                          <p>5.知识空间资源有限，仅用于电子资源检索，空间预约半小时内不登陆系统（输入学/工号和校园卡消费密码，初始身份证后六位数字）将产生违约；使用电子资源请遵守知识产权保护条例，严禁恶意下载。</p>
                          <p>6.请爱护使用信息共享空间所有设施，规范使用设备、软件等，如发现信息共享空间设备故障或有其他疑问，请立即与工作人员联系，不得擅自处理，否则由此造成的机器损坏或软件破坏须做相应赔偿。<p>
                          <p>7.请按照预约分配到的空间和设备进行使用，空间内请勿饮食，保持环境整洁。</p>`,
                    duration: 0
            });
        },
        handleNoteChange(){
            let note=this.applyInfo.note;
            if(note.length>45){
                note=note.slice(0,45);
                this.applyInfo.note=note;
            }
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
        handleEndTimeChange(value){
            if(value===''){
                delete this.timeObject.selectTime[0].eTime;
                this.timeWarning();
            }
            this.drawTimeAxis();
        },
        handleMemberSelect(value){
            if(value!==''){
                let member=JSON.parse(value);
                let doubleMember=this.memberGroup.filter(item=>{  
                    return item.id==member.id;
                });
                if(!doubleMember.length){        //不可重复选择同一个partner
                    this.memberGroup.push(member);
                }else{
                    this.$Message.warning({
                        content: '此成员已在列表中'
                    });
                }
            }
            
        },
        handleDeleteMember(event,name){
            let id=parseInt(name);
            for(let i=0;i<this.memberGroup.length;i++){
                let item=this.memberGroup[i];
                if(item.id==id){
                    this.memberGroup.splice(i,1);
                    break;
                }
            }
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
                        query='';
                        let data=response.data;
                        if(data.code==0){
                             that.loadingMember=false;
                             that.memberOptions=data.result;
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
                    if(i==0){
                        color[i]='#c9c9c9';
                    }else if(i==len-1){
                        color[i]=this.isSelectDone()?'#ffcc80':'#fab5b5';
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
        },
        handleSubmit(){
            let data={};
            data.spaceId=this.applyInfo.spaceId;
            data.application=this.applyInfo.theme;
            if(data.application==''){
                this.$Message.error({
                        content: '主题信息不可为空'
                });
                this.$refs.theme.focus();
                return;
            }
            let date=util.parseTimestamp(this.date);
            data.date=date.year+'-'+date.month+'-'+date.day;
            data.beginTime=this.timeObject.selectTime[0].sTime;
            data.endTime=this.timeObject.selectTime[0].eTime;
            if(typeof data.endTime=='undefined'){
                this.timeWarning();
                return;
            }
            data.note=this.applyInfo.note;
            let partners=[];
            for(let item of this.memberGroup){
                partners.push(item.id);
            }
            if(partners.length<3||partners.length>9){
                this.$Message.error({
                        content: '预约人数不符合限制要求（4-10）'
                });
                return;
            }
            data.partners=partners.join(":");
            if(!this.isAgree){
                this.$Message.warning({
                        content: '请阅读《预约须知》条例并勾选'
                });
                return;
            }
            let that=this;
            that.$Spin.show();
            that.$ajax
                .post(
                    util.baseurl+"/booking/addNewBooking/",
                    JSON.stringify(data),
                    {
                        headers: {"Content-Type": "application/json;charset=utf-8"}
                    }
                )
                .then(function(response){
                    let data=response.data;
                    that.$Spin.hide();
                    switch(data.code){
                        case 0:
                            that.state=false;
                            that.$Message.success({
                                content:'预约成功',
                                duration:8
                            });
                            break;
                        case 107:
                            that.$Message.warning({
                                content:'当前时段已被他人预约',
                                duration:8
                            });
                            break;
                        case 1:
                            that.$Message.warning({
                                content:'预约失败，请稍后重试',
                                duration:8
                            });
                            break;
                        case 400:
                            that.$Message.warning({
                                content:'参数有误，请认真检查后提交',
                                duration:8
                            });
                            break;
                        default:
                            that.$Message.warning({
                                content:'预约失败，请稍后重试',
                                duration:8
                            });
                            break;
                    }
                })
                .catch(function(err){
                    that.$Spin.hide();
                    that.$Message.warning({
                        content:'预约失败，请稍后重试',
                        duration:8
                    });
                    console.log(err);
                });
        }
    },
    created(){
        this.state=true;
        this.dataInit();
        
    },
    mounted(){
        this.timeInit();
        this.drawTimeAxis();
        this.generateBeginTime();
        this.generateEndTime();
    }
};
</script>
<style>
 
</style>
