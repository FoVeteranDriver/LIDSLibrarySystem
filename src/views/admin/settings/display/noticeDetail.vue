<style lang="less">
   //@import "./notice.less";
</style>
<template>
    <div class="admin-noticeDetail">
         <h2>通知详情</h2>
        <div class="header">
            <router-link :to="{name:'home'}">
                <div class="back">
                    <Icon type="ios-arrow-back" size=24 class="backIcon"></Icon>
                    <span class="backText">返回</span>
                </div>
            </router-link>
            <div class="title">{{ noticeTitle }}</div>
        </div>
        <div class="main">
            <div class="content">&nbsp;&nbsp;{{ noticeText }}</div>
            <div class="footer">
                <span class="publisher">{{publisher}}</span><br/>
                <span class="publish-time" v-if="createTime!==''">
                    {{createTime.year}}/{{createTime.month}}/{{createTime.day}}
                    &nbsp;
                    {{createTime.hour}}:{{createTime.minute}}
                </span>
                <img v-bind:src='imageFile'/>
            </div>
        </div>
    </div>
</template>
<script>
import util from '../../../../libs/util.js';
export default {
    name: 'noticeDetail',
    data () {
        return {
                noticeTitle:'',
                noticeText:'',
                createTime:'',
                publisher:'',
                imageFile:'',
                affixFiles:[]
        };
    },
    methods:{
        loadNoticeDetail(){
            let notice_id=this.$route.params.notice_id;
            let that=this;
            that.$ajax
                .get(
                    util.adminUrl+"/notice/?id="+notice_id
                )
                .then(function(response){
                    let data=response.data; 
                    if(data.code==0){
                        let notice=data.result;
                        that.noticeTitle=notice.noticeTitle;
                        that.noticeText=notice.noticeText;
                        that.publisher=notice.publisher;
                        //that.createTime=util.parseTimestamp(notice.createTime);
                        that.imageFile=notice.imageFile;
                        that.affixFiles=notice.affixFiles;
                    }else if(data.code==5){ //用户没有登录或者禁用cookie
                        
                    }
                })
                .catch(function(err){
                    console.log(err);
                });
        },
        disposeData(data){
            for(let item of data){
                let time=util.parseTimestamp(item.createTime);
                item.createTime=time.year+'.'+time.month+'.'+time.day;
            }
            this.noticeList=data;
        },
    },
    created(){
        this.loadNoticeDetail();
    }
};
</script>