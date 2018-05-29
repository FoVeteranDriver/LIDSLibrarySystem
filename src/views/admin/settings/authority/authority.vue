<style lang="less">
    @import "./authority.less";
</style>
<template>
    <div class="authority-main">
        <Input v-model="searchKey" placeholder="账号/姓名/学工号搜索" @on-change='handleSearchChange' size='large'>
            <Button slot="append" icon="ios-search" @click="handleSearch"></Button>
        </Input>
       
            <Button type="ghost" class="editAdmin" @click="handleAdd">新建管理员</Button>
      
        <Table :columns="columns" :data="adminList"></Table>
    </div>
</template>

<script>
import util from '../../../../libs/util.js';
export default {
    name: 'authority',
    data () {
        return {
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
                                    //this.removeBookRecord(params.row.recordID,params.index)
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
                temp.library_card_number=item.library_card_number;
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
                                        subItem.push('权限管理');
                                        break;
                                    case 'admin-rules':
                                        subItem.push('系统规则');
                                        break;
                                    case 'admin-display':
                                        subItem.push('通知展示');
                                        break;
                                    case 'admin-space':
                                        subItem.push('空间区域');
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
                                        subItem.push('空间区域');
                                        break;
                                    case 'admin-credits':
                                        subItem.push('积分管理');
                                        break;
                                    case 'admin-activity':
                                        subItem.push('活动安排');
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
                                    case 'admin-data1':
                                        subItem.push('数据展示1');
                                        break;
                                    case 'admin-data2':
                                        subItem.push('数据展示2');
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
        }
    },
    created(){
        this.loadAdminList();
    }
};
</script>
