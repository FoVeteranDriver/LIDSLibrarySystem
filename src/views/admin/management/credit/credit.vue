<style lang="less">
    //@import "./spaceState.less";
</style>
<template>
    <div id="credit" class="admin-credit">
        <div class="header">
            <div class="selectItem">
                <p>学工号</p>
                <Input v-model="cardNumber" placeholder="请输入学工号" size='large'>
                </Input>
            </div>
            <div class="selectItem">
                <p>日期范围</p>
                <DatePicker 
                type="daterange" 
                format="yyyy/MM/dd" 
                split-panels 
                placeholder="请选择日期范围" 
                style="width: 200px"
                :editable=false
                :options='dateOptions'
                v-model="dateRange"
                @on-change='handleDateChange'>
                </DatePicker>
            </div>
            <Button type="ghost" class="spaceState" @click="handleBtnClick">查询</Button>
            <Button @click="handleSelectAll(true)">批量删除违约</Button>
        </div>
        <div class="main">
            <Table ref="selection" :columns="table" :data='partialRecords'>
            </Table>
            <Page :total="totolSize" :pageSize='pageSize' :current=1 size="small" show-total @on-change='handlePageChange'></Page>
        </div>
    </div>
</template>

<script>
    export default {
        name: 'credit',
        data(){
            return {
                cardNumber:'',
                dateRange:'',
                totolSize:0,
                pageSize:10,
                partialRecords:[],
                creditList:[],
                table:[
                    {
                        type: 'selection',
                        width: 60,
                        align: 'center'
                    },
                    {
                        title:'id',
                        key:'id',
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
                        title:'违约人员',
                        key:'userName',
                        align:'left',
                        
                    },{
                        title:'违约对象',
                        key:'spaceName',
                        align:'left',
                    
                    },{
                        title:'违约类别',
                        key:'creditType',
                        align:'left',
                    },{
                        title:'违约时间',
                        key:'violateTime',
                        align:'left',
                        width:150
                    },
                    {
                        title:'预约目的',
                        key:'bookTheme',
                        align:'left',
                        width:150
                    },
                    {
                        title:'扣除积分',
                        key:'deductedCredits',
                        align:'left',
                        width:150
                    },
                    {
                        title:'剩余积分',
                        key:'leftCredits',
                        align:'left',
                        width:150
                    },
                    {
                        title:'处罚时间',
                        key:'punishTime',
                        align:'left',
                        width:150
                    },
                ],
                dateOptions:{
                    shortcuts:[
                        {
                            text: '1周',
                            value () {
                                const end = new Date();
                                const start = new Date();
                                start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                                return [start, end];
                            }
                        },
                        {
                            text: '1个月',
                            value () {
                                const end = new Date();
                                const start = new Date();
                                start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                                return [start, end];
                            }
                        },
                        {
                            text: '3个月',
                            value () {
                                const end = new Date();
                                const start = new Date();
                                start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                                return [start, end];
                            }
                        }
                    ]
                },
            }
        },
        mounted () {

        },
        beforeDestroy () {

        },
        methods: {
            handleBtnClick(){
                
            },
            handleDateChange(date,type){
                console.log(date);
                console.log(this.dateRange[0].getTime());
            },
            handlePageChange(currentPage){
                let index=(currentPage-1)*this.pageSize;
                this.partialRecords=this.creditList.slice(index,index+this.pageSize);
            },
            handleSelectAll(status) {
                this.$refs.selection.selectAll(status);
            }
        },
    };
</script>