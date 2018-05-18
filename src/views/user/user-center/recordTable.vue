<style lang="less">
   @import "./recordTable.less";
</style>
<template>
    <div class="recordTable">
        <Table border :columns="recordTable" :data='partialRecords' :show-header=false>
            <div slot="header" class="tableHeader">
                <div>订单生成时间</div>
                <div>预约对象</div>
                <div>组内成员</div>
            </div>
        </Table>
        <Page :total="totolSize" :pageSize='pageSize' :current=1 size="small" show-total @on-change='handlePageChange'></Page>
        <Modal
            class="confirmDialog"
            v-model="dialogState"
            width="400"
            :loading=true
            @on-ok="deleteRecord"
            @on-cancel='handleDeleteCancel'>
            <p>
                <Icon type="help-circled" color='#ff9900' size=32></Icon>
                <span>你确定要取消该项预定？</span>
            </p>
        </Modal>
    </div>
</template>
<script>
    import util from '../../../libs/util.js';
    export default {
        name:'recordTable',
        props:{
            isNewRecord:{       //是否查询新预约记录
                type:Boolean
            },
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
                newRecordID:-1,
                currentPage:1,
                recordList:[],
                recordTable:[
                    {
                        title:'id',
                        key:'recordID',
                        width:1,
                        render:(h,params)=>{
                            return h('span',{
                                style:{
                                    color:'transparent'
                                }
                            },'');
                        }
                    },
                    {
                        title:'订单生成时间',
                        key:'orderTime',
                        align:'left',
                        width:170
                    },{
                        title:'预约对象',
                        key:'orderSpace',
                        align:'left',
                        width:180
                    },{
                        title:'组内成员',
                        key:'members',
                        align:'left'
                    },{
                        title:'预约时间',
                        key:'bookTime',
                        align:'left',
                        render:(h,params)=>{
                            return h('div',[
                                h('div',[
                                    h('span',{
                                        style:'',
                                    },
                                    '开始:'
                                    ),h('span',{
                                        style:{
                                            color:'#feafaf',
                                            paddingLeft:'0.08rem'
                                        },
                                    },
                                    params.row.bookTime.sTime)
                                ]),
                                 h('div',[
                                    h('span',{
                                        style:'',
                                    },
                                    '结束:'
                                    ),h('span',{
                                        style:{
                                            color:'#feafaf',
                                            paddingLeft:'0.08rem'
                                        },
                                    },
                                    params.row.bookTime.eTime)
                                ])
                            ]);
                        }
                    },{
                        title:'预约状态',
                        key:'bookState',
                        align:'left',
                        render:(h,params)=>{
                            let bookState=params.row.bookState;
                            let hElem=[];
                            for(let item in bookState){
                                switch(item){
                                    case 'is_active':
                                        if(bookState[item]){
                                            hElem.push(h('span',{
                                                style:''
                                            },'预约成功,'));
                                        }else{
                                            hElem.push(h('span',{
                                                style:{
                                                    color:'#ff2525'
                                                }
                                            },'预约失败,'));;
                                        }
                                        break;
                                    case 'permission_status':
                                        if(bookState[item]){
                                            hElem.push(h('span',{
                                                style:''
                                            },'审核通过,'));
                                        }else{
                                            hElem.push(h('span',{
                                                style:{
                                                    color:'#ff2525'
                                                }
                                            },'审核未通过,'));;
                                        }
                                        break;
                                     case 'has_check_in':
                                        if(bookState[item]){
                                            hElem.push(h('span',{
                                                style:{

                                                }
                                            },'已生效,'));
                                        }else{
                                            hElem.push(h('span',{
                                                style:{
                                                    color:'#f78888'
                                                }
                                            },'未生效,'));;
                                        }
                                        break;
                                }
                            }
                            return h('div',hElem);
                        }
                    },{
                        title:'操作',
                        key:'action',
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
                this.currentPage=currentPage;
                let index=(currentPage-1)*this.pageSize
                this.partialRecords=this.recordList.slice(index,index+this.pageSize);
            },
            removeBookRecord(recordID,index){
                this.dialogState=true;
                this.newRecordID=recordID;
                this.recordIndex=index+this.pageSize*(this.currentPage-1);
            },
            deleteRecord(){
                if(this.newRecordID!=-1){
                    let that=this;
                    that.$ajax
                        .delete(
                            util.baseurl+"/user/deleteBookingRecord/?bookingRecordId="+that.newRecordID
                        )
                        .then(function(response){
                            let data=response.data;
                            if(data.code==0){
                                that.recordList.splice(that.recordIndex,1);
                                that.dialogState=false;
                                let index=(that.currentPage-1)*that.pageSize;
                                that.partialRecords=that.recordList.slice(index,index+that.pageSize);
                                that.totolSize=that.recordList.length;
                                that.$Message.success('预约已经取消');
                            }else{
                                that.$Modal.remove();
                                that.$Message.error('预约无法取消');
                            }
                            that.newRecordID=-1;
                        })
                        .catch(function(err){
                            console.log(err);
                        });
                }
            },
            handleDeleteCancel(){
                this.newRecordID=-1;
            }
        },
        mounted(){
            for(let item of this.tableList){
                this.recordList.push(item);
            }
            this.totolSize=this.recordList.length;
            this.partialRecords=this.recordList.slice(0,this.pageSize);
            if(this.isNewRecord==false){     //只有新预约记录才有删除按钮,其他一律隐藏该按钮
                this.recordTable.pop();
            }
        }
    }
</script>