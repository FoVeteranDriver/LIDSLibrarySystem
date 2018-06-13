<style lang="less">
    @import "./authority.less";
</style>
<template>
    <div class="authority-main">
        <Input v-model="searchKey" placeholder="账号/姓名/学工号搜索" @on-change='handleSearchChange' size='large'>
            <Button slot="append" icon="ios-search" @click="handleSearch"></Button>
        </Input>
        <Button type="ghost" class="editAdmin custom-btn" @click="handleAdd">新建管理员</Button>
        <Table :columns="columns" stripe :data="adminList"></Table>
        <Modal
            class="confirmDialog2"
            v-model="dialogState"
            width="400"
            :loading=true
            @on-ok="deleteAdmin"
            @on-cancel='handleDeleteCancel'>
            <p>
                <Icon type="help-circled" color='#ff9900' size=32></Icon>
                <span>你确定要删除该管理员？</span>
            </p>
        </Modal>
    </div>
</template>

<script>
import util from '../../../../libs/util.js';
export default {
    name: 'authority',
    data () {
        return {
            dialogState:false,
            willDelete:'',
            searchKey:'',
            columns:[
                {
                    key: 'library_card_number',
                    title: '学工号',
                    width:200,
                },{
                    key: 'account',
                    title: '账号',
                    width:200,
                },{
                    key: 'type',
                    title: '类型',
                },{
                    title:'操作',
                    key:'action',
                    align:'center',
                    width:200,
                    render:(h,params)=>{
                        let hElem=[];
                        hElem.push( h('span',{
                            style: {
                                color:'#3f3140',
                                cursor:'pointer',
                                padding:'10px',
                            },
                            on: {
                                click: () => {
                                    this.handleEdit(params.row,params.index)
                                }
                            }
                        },'编辑'));
                        hElem.push( h('span',{
                            style: {
                                color:'#f78888',
                                cursor:'pointer',
                                padding:'10px',
                            },
                            on: {
                                click: () => {
                                    this.hanleRemoveClick(params.row,params.index);
                                }
                            }
                        },'删除'));
                        return hElem;
                    }
                }
            ],
            adminList:[],
        };
    },
    methods:{
        handleAdd(){
            this.$router.push({
                name: 'admin_edit'
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
                this.loadAdminList();
            }
        },
        handleEdit(row,index){
            this.$router.push({name:'admin_edit',params:{admin:row}});
        },
        disposeData(data){
            this.adminList=[];
            for(let item of data){
                let temp={};
                temp.id=item.id;
                temp.library_card_number=item.libraryCardNumber;
                temp.name=item.name;
                temp.account=item.account;
                temp.email=item.email;
                temp.phone=item.phone;
                temp.accessList=item.accessList;
                let type='';
                let children;
                let subItem=[];
                for(let i of item.accessList){
                    switch(i.name){
                        case 'admin-settings':
                            type+='设置（';
                            children=i.children;
                            subItem=[];
                            for(let c of children){
                                switch(c.name){
                                    case 'admin-authority':
                                        if(c.access!==0){
                                            subItem.push('权限管理');
                                        }      
                                        break;
                                    case 'admin-rules':
                                        if(c.access!==0){
                                            subItem.push('系统规则');
                                        }
                                        break;
                                    case 'admin-display':
                                        if(c.access!==0){
                                            subItem.push('通知展示');
                                        }
                                        break;
                                    case 'admin-space':
                                        if(c.access!==0){
                                            subItem.push('空间区域');
                                        }
                                        break;
                                }
                            }
                            type+=subItem.join('，');
                            type+='），';
                            break;
                        case 'admin-management':
                            type+='管理（';
                            children=i.children;
                            subItem=[];
                            for(let c of children){
                                switch(c.name){
                                    case 'admin-mSpace':
                                        if(c.access!==0){
                                            subItem.push('空间区域');
                                        }
                                        break;
                                    case 'admin-credits':
                                        if(c.access!==0){
                                            subItem.push('积分管理');
                                        }
                                        break;
                                    case 'admin-activity':
                                        if(c.access!==0){
                                            subItem.push('活动安排');
                                        }
                                        break;
                                }
                            }
                            type+=subItem.join('，');
                            type+='），';
                            break;
                        case 'admin-data':
                            type+='数据（';
                            children=i.children;
                            subItem=[];
                            for(let c of children){
                                switch(c.name){
                                    case 'admin-usage':
                                        if(c.access!==0){
                                            subItem.push('使用统计');
                                        }
                                        break;
                                    case 'admin-mCredits':
                                        if(c.access!==0){
                                            subItem.push('违约统计');
                                        }
                                        break;
                                    case 'admin-bookSheet':
                                        if(c.access!==0){
                                            subItem.push('预约统计');
                                        }
                                        break;
                                    case 'admin-userInfo':
                                        if(c.access!==0){
                                            subItem.push('用户统计');
                                        }
                                        break;
                                }
                            }
                            type+=subItem.join('，');
                            type+='），';
                            break;
                        default:
                            break;
                    }
                };
                temp.type=type;
                this.adminList.push(temp);
            }
        },
        loadAdminList(){
            let that=this;
            that.$ajax
                .get(
                    util.adminUrl+"/user/adminList/"
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
        hanleRemoveClick(row,index){
            this.dialogState=true;
            this.willDelete=row.account;
        },
        deleteAdmin(){
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
        }
    },
    created(){
        this.loadAdminList();
    }
};
</script>
