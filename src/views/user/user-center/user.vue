<style lang="less">
@import "./user.less";
</style>
<template>
    <div class="user-center">
        <h1>Personal Center</h1>
        <div>
            <div class="inline-item">{{userName}}</div>
            <div class="inline-item">{{userNumber}}</div>
        </div>
        <div>
            <div class="inline-item">{{instituteName}}</div>
            <div class="inline-item">{{userIdentity}}</div>
        </div>
        <Tabs type="card" :animated="false" class="user-tab">
            <TabPane label="个人预约">
                标签一的内容

            </TabPane>
            <TabPane label="活动维护">
                标签二的内容
            </TabPane>
            <TabPane label="信息管理">
                标签三的内容
            </TabPane>
            <TabPane label="积分管理">
                标签四的内容
            </TabPane>
        </Tabs> 
    </div>
    
</template>

<script>
export default {
    name: 'home',
    data () {
        return {
            userName:'卓文君',
            userNumber:'201730712386',
            instituteName:'软件学院',
            userIdentity:'2014级学生'
        };
    },
    mounted(){
    }
};
</script>
