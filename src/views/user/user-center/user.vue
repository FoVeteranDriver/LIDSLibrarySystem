<style lang="less">
@import "./user.less";
</style>
<template>
    <div class="user-center">
        <div class="header">
            <h1>Personal Center</h1>
            <img src="../../../images/user/user-center/user-background.png" class="backImg"/>
        </div>
        <div class="outline-item top">
            <div class="inline-item">{{userName}}</div>
            <div class="inline-item">{{userNumber}}</div>
        </div>
        <div class="outline-item bottom">
            <div class="inline-item">{{instituteName}}</div>
            <div class="inline-item">{{userIdentity}}</div>
        </div>
        <Tabs type="card" :animated="false" class="user-tab">
            <TabPane label="个人预约">
                <Select v-model="currentQuery" style="width:200px" @on-change='handleQueryChange'>
                    <Option v-for="item in queryList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                </Select>
                <recordTable :tableList='recordTableList' :isNewRecord='isNewRecord' :key="recordTableState">
                </recordTable>
            </TabPane>
            <TabPane label="活动维护">
                <activityTable :tableList='activityTableList'>
                </activityTable>
            </TabPane>
            <TabPane label="信息管理">
               <div class="userInfo">
                   <div class="userItem">
                        <label for="phoneNumber">手机</label>
                        <Input v-model="phoneNumber"></Input>
                    </div>
                    <div class="userItem">
                    <label for="mailBox">邮箱</label>
                    <Input v-model="mailBox"></Input>
                    </div>
                    <Checkbox v-model="checkMessage" size="large">短信提醒</Checkbox>
                    <Button>保存</Button>
                </div>
            </TabPane>
            <TabPane label="积分管理">
                <creditsTable :tableList='creditsTableList'></creditsTable>
            </TabPane>
        </Tabs> 
    </div>
    
</template>

<script>
import recordTable from './recordTable.vue';
import activityTable from './activityTable.vue';
import creditsTable from './creditsTable.vue'
import util from '../../../libs/util.js';
export default {
    name: 'user-center',
    components:{
        recordTable,
        activityTable,
        creditsTable
    },
    data () {
        return {
            userNumber:'201730712386',
            instituteName:'软件学院',
            userIdentity:'2014级学生',
            phoneNumber:'',
            mailBox:'',
            checkMessage:true,
            queryList:[
                {
                    label:'新预约记录',
                    value:'newRecords'
                },
                {
                    label:'近三个月预约记录',
                    value:'latestRecords'
                },
                {
                    label:'近三个月违约记录',
                    value:'latestIllegalRecords'
                }
            ],
            currentQuery:'',
            isNewRecord:true,
            recordTableState:true,
            recordTableList:[
                //{
            //     recordID:0,
            //     orderTime:'2018-04-23 17:39',
            //     orderSpace:'研究空间RED(紫金港信息空间)',
            //     members:'卓文君1',
            //     bookTime:{sTime:'04-24 14:00',eTime:'04-24 16:00'},
            //     bookState:{'has_check_in':true,'is_active':false,'permission_status':true},
            // },{
            //     recordID:1,
            //     orderTime:'2018-04-23 17:39',
            //     orderSpace:'研究空间RED(紫金港信息空间)',
            //     members:'卓文君2',
            //     bookTime:{sTime:'04-24 14:00',eTime:'04-24 16:00'},
            //     bookState:{'has_check_in':true,'is_active':false,'permission_status':true},
            // },{
            //     recordID:2,
            //     orderTime:'2018-04-23 17:39',
            //     orderSpace:'研究空间RED(紫金港信息空间)',
            //     members:'卓文君3',
            //     bookTime:{sTime:'04-24 14:00',eTime:'04-24 16:00'},
            //     bookState:{'has_check_in':true,'is_active':false,'permission_status':true},
            // },{
            //     recordID:3,
            //     orderTime:'2018-04-23 17:39',
            //     orderSpace:'研究空间RED(紫金港信息空间)',
            //     members:'刘润华',
            //     bookTime:{sTime:'04-24 14:00',eTime:'04-24 16:00'},
            //     bookState:{'has_check_in':true,'is_active':false,'permission_status':true},
            //}
            ],
            activityTableList:[{
                recordID:1,
                activityName:'大学城高校图书馆共享会',
                sponsor:'华南理工大学教工办',
                contacts:'高于钦',
                activitySite:'华工图书馆二楼',
                activityTime:'2018-04-23 14:00-17:00',
                actionState:'已结束',
            }],
            creditsTableList:[{
                recordID:1,
                violateTime:'2018-04-25 14:00-15:00',
                violateState:'预约未签到',
                spaceName:'研习间1',
                deductedCredits:'100'
            }]
        }
    },
    mounted(){
        if(!this.username){
            this.$emit("needLogin");
        }else{
            this.currentQuery=this.queryList[0].value;
        }
    },
    methods:{
        handleQueryChange(){
            let that=this;
            that.$ajax
                .get(
                    util.baseurl+"/user/userBookingRecords/?key="+this.currentQuery
                )
                .then(function(response){
                    let data=response.data;
                    if(data.code==0){
                        that.recordTableList=data.result;
                    }
                })
                .catch(function(err){
                    console.log(err);
                });
            if(this.currentQuery==this.queryList[0].value){
                this.isNewRecord=true;
            }else{
                this.isNewRecord=false;
            }
            this.recordTableState=!this.recordTableState;
        }
    },
    computed:{
         //从store读取用户名
        userName() {
            return this.$store.state.username;
        },
    },
    watch:{
        userName:function(newValue,oldValue){
            if(newValue){
                this.currentQuery=this.queryList[0].value;
            }
        }
    }
};
</script>
