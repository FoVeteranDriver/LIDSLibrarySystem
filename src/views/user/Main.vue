<style lang="less">
    @import "./main.less";
</style>
<template>
    <div class="main" :class="{'main-hide-text': shrink}">
        <div class="sidebar-menu-con" :style="{width: shrink?'0.6rem':'2.1rem',minWidth:'19px', overflow: shrink ? 'visible' : 'auto'}">
            <shrinkable-menu 
                :shrink="shrink"
                :menu-list="menuList">
                <div slot="top" class="logo-con">
                    <div v-show="!shrink" class="max-con" >
                        <img  src="../../images/logo.png" key="max-logo" :style="{ height: '0.55rem', paddingLeft: '0.1rem'}"/>
                        <hgroup class="hgroup"><h2>图书馆</h2><h2>信息共享空间</h2></hgroup>
                    </div>
                    <img v-show="shrink" src="../../images/logo-min.jpg" key="min-logo" :style="{ height: '44px'}" />
                </div>
            </shrinkable-menu>
        </div>
        <div class="main-header-con" :style="{paddingLeft: shrink?'0.6rem':'2.1rem'}">
            <div class="main-header">
                <div class="navicon-con">
                    <Button :style="{transform: 'rotateZ(' + (this.shrink ? '-90' : '0') + 'deg)'}" type="text" @click="toggleClick">
                        <img src="../../images/navicon.png" class="navicon-con-btn"/>
                    </Button>
                </div>
                <div class="header-avator-con">
                    <div class="user-dropdown-menu-con">
                        <div class="user-dropdown-innercon">
                            <Dropdown transfer trigger="click" class="user-right" @on-click="handleClickUserDropdown">
                                <a href="javascript:void(0)">      
                                    <img src="../../images/power.png" class="power"/>
                                </a>
                                <DropdownMenu slot="list">
                                    <DropdownItem name="loginout" divided>退出登录</DropdownItem>
                                </DropdownMenu>
                            </Dropdown>
                            <div class="user-right">
                                <img src="../../images/user-avater.png" class="user-avater"/>
                                <span>Hi</span><span class="main-user-name">卓文卓文卓文卓文卓</span>
                            </div>
                         
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="single-page-con" :style="{left: shrink?'0.6rem':'2.1rem'}">
            <div class="single-page">
                <router-view></router-view>
            </div>
        </div>
    </div>
</template>
<script>
import Cookies from 'js-cookie';
import shrinkableMenu from './main-components/shrinkable-menu/shrinkable-menu.vue';
import utils from '../../libs/util.js';
export default {
    components: {
        shrinkableMenu
    },
    data () {
        return {
            shrink: false,
            userName: ''
        };
    },
    computed: {
        menuList () {
            return this.$store.state.app.menuList;
        }
    },
    methods: {
        init () {
            var self=this;
            this.userName = Cookies.get('user');
            // window.onresize=function(){
            //     this.throttle(this.shrinkTrigger,null);
            // }
        },
        shrinkTrigger(){
            var outerWidth=window.outerWidth;
            if(outerWidth<960){
                 this.shrink=true;
            }
        },
        throttle(fn,context){
             if(typeof fn.tId!=='undefined'){
                clearTimeout(fn.tId);
           }
            fn.tId=setTimeout(function(){
                fn.call(context);
            },100);
        },
        toggleClick () {
            this.shrink = !this.shrink;
        },
        handleClickUserDropdown (name) {
            this.$router.push({
                name: 'login'
            });
        }
    },
    mounted () {
        this.init();
    }
};
</script>
