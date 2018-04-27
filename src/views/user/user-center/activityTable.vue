<style lang="less">
  @import './activityTable.less';
</style>
<template>
    <div class="activityTable">
        <Table border :columns="activityTable" :data='partialRecords' :show-header=false>
            <div slot="header" class="activity-header">
                <div>活动名称</div>
                <div>主办单位</div>
                <div>联系人</div>
                <div>活动地点</div>
                <div>活动时间</div>
                <div>活动状态</div>
                <div>操作</div>
            </div>
        </Table>
        <Page :total="totolSize" :pageSize='pageSize' :current=1 size="small" show-total @on-change='handlePageChange'></Page>
    </div>
</template>
<script>
    import util from '../../../libs/util.js';
    export default {
        name:'activityTable',
        props:{
            tableList:{
                type:Array
            }
        },
        data(){
            return{
                totolSize:0,
                pageSize:2,
                partialRecords:[],
                dialogState:false,
                activityList:[],
                activityTable:[
                    {
                        title:'id',
                        key:'recordID',
                        width:1,
                    },
                    {
                        title:'活动名称',
                        key:'activityName',
                        align:'left',
                        width:170
                    },{
                        title:'主办单位',
                        key:'sponsor',
                        align:'left',
                        width:180
                    },{
                        title:'联系人',
                        key:'contacts',
                        align:'left',
                        width:120
                    },{
                        title:'活动地点',
                        key:'activitySite',
                        align:'left',
                    },{
                        title:'活动时间',
                        key:'activityTime',
                        align:'left',
                    },{
                        title:'活动状态',
                        key:'actionState',
                        align:'center',
                        width:100,
                    },
                    {
                        title:'操作',
                        key:'actionState',
                        align:'center',
                        width:80,
                        render:(h,params)=>{
                            return h('span',{
                                style: {
                                   color:'#f78888',
                                   cursor:'pointer'
                                },
                                on: {
                                    click: () => {
                                        this.removeBookRecord(params.row.recordID,params.index)
                                    }
                                }
                            },'删除');
                        }
                    }
                ]
            }
         
        },
        methods:{
            handlePageChange(currentPage){
                let index=(currentPage-1)*this.pageSize;
                this.partialRecords=this.activityList.slice(index,index+this.pageSize);
            },
        },
        mounted(){
            for(let item of this.tableList){
                this.activityList.push(item);
            }
            this.totolSize=this.activityList.length;
            this.partialRecords=this.activityList.slice(0,this.pageSize);
        }
    }
</script>