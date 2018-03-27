<style lang="less">
    @import "./main.less";
</style>
<template>
    <div class="main" :class="{'main-hide-text': shrink}">
        <div class="sidebar-menu-con" :style="{width: shrink?'0.6rem':'2.1rem', overflow: shrink ? 'visible' : 'auto'}">
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
                        <Icon type="navicon" size="40"></Icon>
                    </Button>
                </div>
                <div class="header-avator-con">
                    <div class="user-dropdown-menu-con">
                        <div class="user-dropdown-innercon">
                            <Dropdown transfer trigger="click" class="user-right" @on-click="handleClickUserDropdown">
                                <a href="javascript:void(0)">      
                                    <Icon type="power" size="26" color="#f56c6c"></Icon>
                                </a>
                                <DropdownMenu slot="list">
                                    <DropdownItem name="loginout" divided>退出登录</DropdownItem>
                                </DropdownMenu>
                            </Dropdown>
                            <div class="user-right">
                                <Avatar icon="person" style="background: #f56c6c;" class="user-avater"></Avatar>
                                <span>Hi</span><span class="main-user-name">卓文</span>
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
            this.userName = Cookies.get('user');
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
