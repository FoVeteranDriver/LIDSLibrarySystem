<style lang="less">
    @import "./spaceState.less";
</style>
<template>
    <div id="mSpace" class="admin-SpaceState">
        <div class="header">
            <div class="selectItem">
                <p>查询类型</p>
                <Select v-model="currentQuery" style="width:200px" @on-change='handleQueryChange' placeholder='请选择查询类型'>
                    <Option v-for="item in queryList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                </Select>
            </div>
            <div class="selectItem">
                <p>空间类型</p>
                <Select v-model="hotSpace" style="width:200px" @on-change='handleQueryChange' placeholder='请选择空间类型'>
                    <Option v-for="item in spaceType" :value="item.value" :key="item.value">{{ item.label }}</Option>
                </Select>
            </div>
            <div class="selectItem" v-show="currentQuery=='old'">
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
        </div>
        <div class="main">
            <usingRoom :tableList='roomTable' v-show="currentQuery=='old'&&hotSpace=='studyRoom'"></usingRoom>
        </div>
    </div>
</template>

<script>
    import usingRoom from './usingRoom.vue';
    export default {
        name: 'mSpace',
        components:{
            usingRoom
        },
        data(){
            return {
                currentQuery:'',
                hotSpace:'',
                dateRange:'',
                roomTable:[],
                queryList:[
                    {
                        label:'历史使用记录',
                        value:'old'
                    },
                    {
                        label:'使用状态',
                        value:'using'
                    },
                    {
                        label:'预约状态',
                        value:'booking'
                    }
                ],
                spaceType:[
                    {
                        label:'研习间',
                        value:'studyRoom'
                    },
                    {
                        label:'学习间',
                        value:'seat'
                    }
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
            handleSearchChange(){

            },
            handleBtnClick(){
                
            },
            handleSearch(){

            },
            handleQueryChange(){

            },
            handleDateChange(date,type){
                console.log(date);
                console.log(this.dateRange[0].getTime());
            }
        },
    };
</script>