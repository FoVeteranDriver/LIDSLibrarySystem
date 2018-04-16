<style lang="less">
    @import "./tab.less";
</style>
<template>
    <div>
        <Tabs :animated="false" class="main-tab">
            <div class="display-area">
                <img  v-bind:src='imgUrl'/>
            </div>
            <template v-for="(item, index) in tabList">
                <TabPane :label="item.label" class="tab-title">
                    <slot :name="item.state" class="tab-content"></slot>
                </TabPane>
            </template>
        </Tabs>
    </div>
</template>
<script>
    export default {
        name:'tab',
        props:{
            tabList:{
                type:Array,
                required: true
            },
            imgUrl:{
                type:String,
                required:true
            }
        }
    }
</script>