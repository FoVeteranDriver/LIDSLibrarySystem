<style lang="less">
  //@import './creditsTable.less';
</style>
<template>
    <div class="admin-usingRoom">
        <Table :columns="table" :data='partialRecords'>
        </Table>
        <Page :total="totolSize" :pageSize='pageSize' :current=1 size="small" show-total @on-change='handlePageChange'></Page>
    </div>
</template>
<script>
    //import util from '../../../libs/util.js';
    export default {
        name:'usingRoom',
        props:{
            tableList:{
                type:Array
            },
        },
        data(){
            return{
                totolSize:0,
                pageSize:10,
                partialRecords:[],
                bookList:[],
                table:[
                    {
                        title:'id',
                        key:'recordID',
                        width:80,
                    },
                    {
                        title:'空间名称',
                        key:'spaceName',
                        align:'left',
                        
                    },{
                        title:'门禁状态',
                        key:'RKEState',
                        align:'left',
                    
                    },{
                        title:'使用状态',
                        key:'usingState',
                        align:'left',
                    },{
                        title:'使用者信息',
                        key:'userState',
                        align:'left',
                        width:150
                    },
                    {
                        title:'签到记录',
                        key:'signIn',
                        align:'left',
                        width:150
                    }
                ]
            }
         
        },
        methods:{
            handlePageChange(currentPage){
                let index=(currentPage-1)*this.pageSize;
                this.partialRecords=this.bookList.slice(index,index+this.pageSize);
            },
        },
        mounted(){
            for(let item of this.tableList){
                this.bookList.push(item);
            }
            this.totolSize=this.bookList.length;
            this.partialRecords=this.bookList.slice(0,this.pageSize);
        }
    }
</script>