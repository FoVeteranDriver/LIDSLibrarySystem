<style lang="less">
  @import './creditsTable.less';
</style>
<template>
    <div class="creditsTable">
        <div class="top">
            <div class="left"><span>总积分（学期）</span><span>{{totalCredits}}</span></div>
            <div class="right"><span>当前积分（学期）</span><span class="score">{{currentCredits}}</span></div>
        </div>
        <Table border :columns="creditsTable" :data='partialRecords' :show-header=false>
            <div slot="header" class="credits-header">
                <div>序号</div>
                <div>违约时间</div>
                <div>违约状态</div>
                <div>预约空间</div>
                <div>扣除积分</div>
            </div>
        </Table>
        <Page :total="totolSize" :pageSize='pageSize' :current=1 size="small" show-total @on-change='handlePageChange'></Page>
    </div>
</template>
<script>
    import util from '../../../libs/util.js';
    export default {
        name:'creditsTable',
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
                totalCredits:0,
                currentCredits:0,
                creditsList:[],
                creditsTable:[
                    {
                        title:'id',
                        key:'recordID',
                        width:80,
                    },
                    {
                        title:'违约时间',
                        key:'violateTime',
                        align:'left',
                        
                    },{
                        title:'违约状态',
                        key:'violateState',
                        align:'left',
                    
                    },{
                        title:'预约空间',
                        key:'spaceName',
                        align:'left',
                    },{
                        title:'扣除积分',
                        key:'deductedCredits',
                        align:'left',
                        width:150
                    }
                ]
            }
         
        },
        methods:{
            handlePageChange(currentPage){
                let index=(currentPage-1)*this.pageSize;
                this.partialRecords=this.creditsList.slice(index,index+this.pageSize);
            },
        },
        mounted(){
            for(let item of this.tableList){
                this.creditsList.push(item);
            }
            this.totolSize=this.creditsList.length;
            this.partialRecords=this.creditsList.slice(0,this.pageSize);
        }
    }
</script>