<style lang="less">
  @import './noticeDetail.less';
</style>
<template>
    <div class="noticeDetail">
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
                <span class="publisher">图书馆信息共享空间</span>
                <span class="publish-time" v-if="publishTime!==''">
                    {{publishTime.year}}/{{publishTime.month}}/{{publishTime.day}}
                    &nbsp;
                    {{publishTime.hour}}:{{publishTime.minute}}
                </span>
            </div>
        </div>
    </div>
</template>
<script>
    
    export default {
        name:'noticeDetail',
        props:{
        },
        data(){
            return{
                noticeTitle:'',
                noticeText:'',
                publishTime:''
            }
         
        },
        methods:{
        
        },
        created(){
           let notice=this.$route.params.notice;
           let key='notice';
           if(!notice){
               notice=sessionStorage.getItem(key);       //解决this.$route.params刷新会丢失的问题
               notice=JSON.parse(notice);
           }else{
               sessionStorage.setItem(key,JSON.stringify(notice));
           }
           if(notice){
                this.noticeTitle=notice.noticeTitle;
                this.noticeText=notice.noticeText;
                this.publishTime=notice.publishTime;
           }
        }
    }
</script>