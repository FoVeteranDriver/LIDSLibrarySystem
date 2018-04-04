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
            <div slot="cContent">
                <picShow :imageList="imageList">
                </picShow>
            </div>
        </tab>
    </div>   
</template>

<script>
import tab from '../../common/tab/tab.vue';
import picShow from '../../common/picShow/picShow.vue';
export default {
    name: 'page',
    components: {
        tab,
        picShow
    },
    data(){
        return{
            tabList:[{label:'预约状态',state:'aContent'},{label:'预约须知',state:'bContent'},{label:'相册展示',state:'cContent'},{label:'硬件配置',state:'dContent'}],
            imgUrl:'./src/images/user/common/tab/example.png',
            imageList:[
                {title:'pic1',url:'./src/images/user/common/picShow/pic1.jpg'},
                {title:'pic2',url:'./src/images/user/common/picShow/pic2.jpg'},
                {title:'pic3',url:'./src/images/user/common/picShow/pic3.jpg'},
                {title:'pic4',url:'./src/images/user/common/picShow/pic4.jpg'},
                {title:'pic5',url:'./src/images/user/common/picShow/pic5.jpg'},
                {title:'pic6',url:'./src/images/user/common/picShow/pic6.jpg'},
                {title:'pic7',url:'./src/images/user/common/picShow/pic7.jpg'},
                {title:'pic8',url:'./src/images/user/common/picShow/pic8.jpg'},
            ]
        }
    }
};
</script>