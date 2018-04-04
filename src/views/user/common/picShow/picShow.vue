<style lang="less">
    @import "./picShow.less";
</style>
<template>
    <div class="pic-main">
            <div>
                <template v-for="(item, index) in imageList">
                    <img :src="item.url" class="abbr"/>
                </template>
            </div>
            <div class="detail"></div>
    </div>
</template>
<script>
    export default {
        name:'picShow',
        props:{
            imageList:{
                type:Array,
                required: true
            }
        }
    }
</script>