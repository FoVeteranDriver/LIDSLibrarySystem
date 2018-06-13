<style lang="less">
   @import "./notice.less";
</style>
<template>
    <div class="admin-notice">
         <Input v-model="searchKey" placeholder="请输入名称关键字" @on-change='handleSearchChange' size='large'>
            <Button slot="append" class="custom-btn" icon="ios-search" @click="handleSearch"></Button>
        </Input>
        <Button class="custom-btn addNotice"  @click="handleAdd">发布通知</Button>
        <Table :columns="columns" stripe :data="noticeList" highlight-row @on-current-change='handleRowChange'></Table>
        <Modal
            class="confirmDialog2"
            v-model="dialogState"
            width="400"
            :loading=true
            @on-ok="deleteNotice"
            @on-cancel='handleDeleteCancel'>
            <p>
                <Icon type="help-circled" color='#ff9900' size=32></Icon>
                <span>你确定要删除该通知记录？</span>
            </p>
        </Modal>
    </div>
</template>
<script>
import util from '../../../../libs/util.js';
export default {
        name: 'notice',
           data () {
        return {
            dialogState:false,
            willDelete:'',
            searchKey:'',
            columns:[
                {
                    key: 'noticeTitle',
                    title: '通知名称',
                },{
                    title:'操作',
                    key:'action',
                    align:'center',
                    width:200,
                    render:(h,params)=>{
                        return  h('span',{
                            style: {
                                color:'#f78888',
                                cursor:'pointer',
                                padding:'10px',
                                fontSize:'0.14rem'
                            },
                            on: {
                                click: () => {
                                    this.hanleRemoveClick(params.row,params.index);
                                }
                            }
                        },'删除');
                    }
                }
            ],
            noticeList:[{
                id:'1',
                noticeTitle:'关于调整开放时间的通知'
            },{
                id:'2',
                noticeTitle:'关于调整开放时间的通知'
            }],
        };
    },
    methods:{
        handleAdd(){
            this.$router.push({
                name: 'admin_noticeEdit'
            });
        },
        handleSearch(){
            if(this.searchKey==''){
                this.$Message.warning({
                        content: '查询关键字不可为空'
                });
                return;
            }
            let that=this;
            that.$ajax
                .get(
                    util.adminUrl+"/user/searchAdmin/?key="+this.searchKey
                )
                .then(function(response){
                    let data=response.data; 
                    if(data.code==0){
                        that.disposeData(data.result);
                    }else if(data.code==5){ //用户没有登录或者禁用cookie
                    }
                })
                .catch(function(err){
                    console.log(err);
                });
        },
        handleSearchChange(){
            if(this.searchKey==''){
                this.loadNoticeList();
            }
        },
        handleEdit(row,index){
            this.$router.push({name:'notice_edit',params:{admin:row}});
        },
        loadNoticeList(){
            let that=this;
            that.$ajax
                .get(
                    util.adminUrl+"/user/adminList/"
                )
                .then(function(response){
                    let data=response.data; 
                    if(data.code==0){
                        that.adminList=data.result;
                    }else if(data.code==5){ //用户没有登录或者禁用cookie
                        
                    }
                })
                .catch(function(err){
                    console.log(err);
                });
        },
        hanleRemoveClick(row,index){
            this.dialogState=true;
            this.willDelete=row.id;
        },
        deleteNotice(){
            if(this.willDelete!==''){
                let that=this;
                that.$ajax
                    .delete(
                        util.adminUrl+"/user/deleteAdmin/?account="+this.willDelete
                    )
                    .then(function(response){
                        that.willDelete='';
                        that.dialogState=false;
                        let data=response.data; 
                        if(data.code==0){
                            that.$Message.success('删除成功');
                            that.loadAdminList();
                        }else if(data.code==400){ 
                            that.$Message.error('该账号不存在');
                            that.loadAdminList();
                        }else if(data.code==403){
                            that.$router.push({name:'error-403'});
                        }else if(data.code==5){
                            that.$Message.error('您尚未登录');
                            that.$store.commit('removeAdminAccount');
                            that.$router.push({name:'admin_login'});
                        }
                    })
                    .catch(function(err){
                        console.log(err);
                    });
            }
        },
        handleDeleteCancel(){
            this.dialogState=false;
            this.willDelete='';
        },
        handleRowChange(newVal,oldVal){
            console.log(newVal,oldVal);
        }
    },
    created(){
        this.loadNoticeList();
    }
};
</script>