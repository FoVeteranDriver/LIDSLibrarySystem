<template>
    <div>
        <h1>研习间预约</h1>
        <tab :tabList="tabList" :imgUrl="imgUrl">
            <div slot="aContent">
                hhhhhhhhh
            </div>
            <div slot="bContent">
                bbbbbbbbbbbb
            </div>
        </tab>
    </div>   
</template>

<script>
import tab from '../../common/tab/tab.vue';
export default {
    name: 'page',
    components: {
        tab
    },
    data(){
        return{
            tabList:[{label:'预约状态',state:'aContent'},{label:'预约须知',state:'bContent'},{label:'相册展示',state:'cContent'},{label:'硬件配置',state:'dContent'}],
            imgUrl:'./src/images/user/common/tab/example.png'
        }
    }
};
</script>