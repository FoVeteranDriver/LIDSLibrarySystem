<style lang="less">
   @import "./editInfo.less";
</style>
<template>
    <div class="admin-infoEdit">
        <Tabs type="card" :animated="false">
                <TabPane label="空间信息">
                    <space-info></space-info>
                </TabPane>
                <TabPane label="主页">
                    <home-info></home-info>
                </TabPane>
                <TabPane label="使用帮助">
                    <using-help></using-help>
                </TabPane>
                <TabPane label="活动预告">
                    标签4的内容
                </TabPane>
                <TabPane label="活动回顾">
                    标签5的内容
                </TabPane>
        </Tabs>
    </div>
</template>
<script>
import spaceInfo from './subItem/spaceInfo.vue';
import homeInfo from './subItem/homeInfo.vue';
import usingHelp from './subItem/usingHelp.vue';
export default {
        name: 'editInfo',
        components: {
            spaceInfo,
            homeInfo,
            usingHelp
        },
        data () {
            return {
              
            };
    },
    methods:{
      
    }
};
</script>