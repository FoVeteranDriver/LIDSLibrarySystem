<style lang="less">
    @import "./main.less";
</style>
<template>
    <div class="main" :class="{'main-hide-text': shrink}">
        <div class="sidebar-menu-con" :style="{width: shrink?'0.51rem':'2.1rem',minWidth:'19px', overflow: shrink ? 'visible' : 'auto'}">
            <shrinkable-menu 
                :shrink="shrink"
                :menu-list="menuList">
                <div slot="top" class="logo-con">
                    <div v-show="!shrink" class="max-con" >
                        <img  src="../../images/user/main-component/logo.png" key="max-logo" :style="{ height: '0.55rem', paddingLeft: '0.1rem'}"/>
                        <hgroup class="hgroup"><h2>图书馆</h2><h2>信息共享空间</h2></hgroup>
                    </div>
                    <img v-show="shrink" src="../../images/user/main-component/logo.png" key="logo" :style="{ height: '0.35rem',width:'0.35rem',marginTop:'0.08rem'}" />
                </div>
            </shrinkable-menu>
        </div>
        <div class="main-header-con" :style="{paddingLeft: shrink?'0.51rem':'2.1rem'}">
            <div class="main-header">
                <div class="navicon-con">
                    <Button :style="{transform: 'rotateZ(' + (this.shrink ? '-90' : '0') + 'deg)'}" type="text" @click="toggleClick">
                        <img src="../../images/user/main-component/navicon.png" class="navicon-con-btn"/>
                    </Button>
                </div>
                <div class="header-avator-con">
                    <div class="user-dropdown-menu-con">
                        <div class="user-dropdown-innercon">
                            <Dropdown transfer trigger="click" class="user-right" @on-click="handleClickUserDropdown">
                                <a href="javascript:void(0)">      
                                    <img src="../../images/user/main-component/power.png" class="power"/>
                                </a>
                                <DropdownMenu slot="list">
                                    <DropdownItem name="loginout" divided>退出登录</DropdownItem>
                                </DropdownMenu>
                            </Dropdown>
                            <div class="user-right">
                                <img src="../../images/user/main-component/user-avater.png" class="user-avater"/>
                                <span>Hi,</span><span class="main-user-name">刘润华说要请吃饭</span>
                            </div>
                         
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="single-page-con" :style="{left: shrink?'0.51rem':'2.1rem'}">
            <div class="single-page">
                <router-view></router-view>
            </div>
        </div>
    </div>
</template>
<script>
import Cookies from 'js-cookie';
import shrinkableMenu from './main-components/shrinkable-menu/shrinkable-menu.vue';
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
        },
        shrinkTrigger(){
            var outerWidth=window.outerWidth;
            if(outerWidth<960){
                 this.shrink=true;
            }
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
