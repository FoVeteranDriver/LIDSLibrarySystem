<style lang="less">
    @import "./picShow.less";
</style>
<template>
    <div class="pic-main">
        <div class="ban" id="mainPic">
            <div class="min_pic">
                <div class="prev_btn1" id="prev_btn1"><img src="../../../../images/user/common/picShow/left.png"/></div>
                <div class="num clearfix" id="ban_num1">
                    <ul>
                        <template v-for="(item, index) in imageList">
                            <li><a href="javascript:;"><img :src="item.url" class="abbr"/></a></li>
                        </template>
                    </ul>
                </div>
                <div class="next_btn1" id="next_btn1"><img src="../../../../images/user/common/picShow/right.png"/></div>
            </div>
            <div class="bigPic">
                <div class="ban2" id="ban_pic1">
                    <div class="prev1" id="prev1" width="28" height="51"><img src="../../../../images/user/common/picShow/prev.png"/></div>
                    <div class="next1" id="next1" width="28" height="51"><img src="../../../../images/user/common/picShow/next.png"/></div>
                    <ul>
                        <template v-for="(item, index) in imageList">
                            <li><a href="javascript:;"><img :src="item.url" class="abbr-big"/></a></li>
                        </template>
                    </ul>
                </div>
            </div>
        </div>
        <div class="mhc"></div>
        <div class="pop_up" id="bullet">
            <div class="pop_up_xx"><img src="../../../../images/user/common/picShow/pop_xx.png"/></div>
            <div class="pop_up2" id="ban_pic2">
                <div class="prev1" id="prev2"><img src="../../../../images/user/common/picShow/prev.png"/></div>
                <div class="next1" id="next2"><img src="../../../../images/user/common/picShow/next.png"/></div>
                <ul>
                    <template v-for="(item, index) in imageList">
                        <li><a href="javascript:;"><img :src="item.url" class="abbr-big"/></a></li>
                    </template>
                </ul>
            </div>
        </div>
    </div>
</template>
<script>
   import {banqh} from '../../../../js/pic_tab.js';
   import util from '../../../../libs/util.js';
    export default {
        name:'picShow',
        props:{
            imageList:{
                type:Array,
                required: true
            },
        },
        mounted(){
            function init(){
                $('#mainPic').banqh({
                    box:"#mainPic",//总框架
                    pic:"#ban_pic1",//大图框架
                    pnum:"#ban_num1",//小图框架
                    prev_btn:"#prev_btn1",//小图左箭头
                    next_btn:"#next_btn1",//小图右箭头
                    pop_prev:"#prev2",//弹出框左箭头
                    pop_next:"#next2",//弹出框右箭头
                    prev:"#prev1",//大图左箭头
                    next:"#next1",//大图右箭头
                    pop_div:"#bullet",//弹出框框架
                    pop_pic:"#ban_pic2",//弹出框图片框架
                    pop_xx:".pop_up_xx",//关闭弹出框按钮
                    mhc:".mhc",//朦灰层
                    autoplay:true,//是否自动播放
                    interTime:5000,//图片自动切换间隔
                    delayTime:400,//切换一张图片时间
                    pop_delayTime:400,//弹出框切换一张图片时间
                    order:0,//当前显示的图片（从0开始）
                    picdire:true,//大图滚动方向（true为水平方向滚动）
                    mindire:true,//小图滚动方向（true为水平方向滚动）
                    min_picnum:5,//小图显示数量
                    pop_up:true//大图是否有弹出框
                });
            }
            window.addEventListener('resize',function(){
                util.throttle(init);
            },false);
            init();
        }
    }
</script>