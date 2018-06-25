<style lang="less">
   @import "./editInfo.less";
</style>
<template>
    <div class="admin-infoEdit">
        <Tabs type="card" :animated="false">
                <TabPane label="空间信息">
                    <home-info></home-info>
                </TabPane>
                <TabPane label="主页">
                    标签二的内
                </TabPane>
                <TabPane label="使用帮助">
                    标签三的内容
                </TabPane>
                <TabPane label="活动预告">
                    标签三的内容
                </TabPane>
                <TabPane label="活动回顾">
                    标签三的内容
                </TabPane>
        </Tabs>
    </div>
</template>
<script>
import homeInfo from './subItem/homeInfo.vue';
export default {
        name: 'editInfo',
        components: {
            homeInfo
        },
        data () {
            return {
            };
    },
    methods:{
    }
};
</script>