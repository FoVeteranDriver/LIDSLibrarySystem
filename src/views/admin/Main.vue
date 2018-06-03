<style lang="less">
    @import "./main.less";
</style>
<template>
    <div class="admin-main">
        <div class="sidebar-menu-con" :style="{width: '0.9rem', overflow: 'visible'}">
            <sidebar-menu 
                :menu-list="menuList"
                :hot-tab="hotTab"
                :key="sidebarState"
                :current-route="currentRoute"
                @on-menuOpen='handleMenuOpen'>
                <div slot="top" class="logo-con">
                    <img src="../../images/admin/main-component/logo.png" key="max-logo" :style="{ height: '0.55rem', paddingLeft: '0.1rem'}" />
                </div>
            </sidebar-menu>
            <div class="subMenu-con" :style="{display:isHidden?'none':'block'}"></div>
        </div>
        <div class="admin-main-header-con" :style="{left:isHidden?'0.92rem':'2.1rem',paddingRight:isHidden?'0.9rem':'2.1rem'}">
            <div class="admin-main-header">
                <div class="navicon-con">
                    信息共享空间管理系统
                </div>
                <div class="header-avator-con">
                    <div class="user-dropdown-menu-con">
                        <div class="user-dropdown-innercon">
                            <Dropdown transfer trigger="click" class="user-right" @on-click="handleClickUserDropdown">
                                <a href="javascript:void(0)">
                                    <img src="../../images/admin/main-component/power.png" class="power" />
                                </a>
                                <DropdownMenu slot="list">
                                    <DropdownItem name="loginout" divided>退出登录</DropdownItem>
                                </DropdownMenu>
                            </Dropdown>
                            <div class="user-right">
                                <router-link :to="{name:'admin_center'}">
                                    <img src="../../images/admin/main-component/user-avater.png" class="user-avater" />
                                </router-link>
                                <span>Hi</span>
                                <span class="main-user-name">{{userName}}</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="tags-con"  :style="{left:isHidden?'0.92rem':'2.12rem',paddingRight:isHidden?'0.9rem':'2.1rem'}">
            <tags-page-opened :pageTagsList="pageTagsList">
            </tags-page-opened>
        </div>
        <div class="single-page-con" :style="{left:isHidden?'0.9rem':'2.1rem'}">
            <div class="single-page">
                <keep-alive :include="cachePage">
                    <router-view></router-view>
                </keep-alive>
            </div>
        </div>
    </div>
</template>
<script>
import Cookies from 'js-cookie';
import sidebarMenu from './main-components/sidebar-menu/sidebarMenu.vue';
import tagsPageOpened from './main-components/tags-page-opened.vue';
import util from '@/libs/util.js';
import {appRouter} from '@/router/adminRouter';
export default {
    components: {
        sidebarMenu,
        tagsPageOpened,
    },
    data () {
        return {
            userName: '',
            isHidden:true,
            hotTab:'-1',
            currentRoute:'',
            sidebarState:true
        };
    },
    computed: {
        menuList () {
            return this.$store.state.appAdmin.adminMenuList;
        },
        cachePage () {
            return this.$store.state.appAdmin.cachePage;
        },
        pageTagsList () {
            return this.$store.state.appAdmin.pageOpenedList; // 打开的页面的页面对象
        },
    },
    watch: {
        '$route' (to) {
            this.$store.commit('setCurrentPageName', to.name);
            let pathArr = util.setCurrentPath(this, to.name);
            if (pathArr.length > 2) {
               this.isHidden=false;
               this.hotTab=pathArr[1].name;
               this.currentRoute=pathArr[2].name;
            }else{
                this.isHidden=true;
                this.hotTab='-1';
                this.currentRoute='';
            }
            this.sidebarState=!this.sidebarState;
            this.checkTag(to.name);
            localStorage.currentPageName = to.name;
        },
    },
    methods: {
        init () {
            let pathArr = util.setCurrentPath(this, this.$route.name);
            this.$store.commit('updateAccessList');
            this.$store.commit('updateAdminMenulist');
            this.userName =this.$store.state.appAdmin.adminAccount;
            this.checkTag(this.$route.name);
        },
        handleClickUserDropdown (name) {
            let that=this;
            that.$ajax
                .get(
                    util.adminUrl+"/user/logout/"
                )
                .then(function(response){
                    let data=response.data; 
                    if(data.code==0){
                        that.$store.commit('removeAdminAccount');
                        that.$router.push({
                            name: 'admin_login'
                        });
                    }else if(data.code==5){ //用户没有登录或者禁用cookie
                        that.$router.push({
                            name: 'admin_login'
                        });
                    }
                })
                .catch(function(err){
                    console.log(err);
            });
        },
        handleMenuOpen(name){
            if(name!='-1'){
                this.isHidden=false;
            }else{
                this.isHidden=true;
            }
        },
        checkTag (name) {
            let openpageHasTag = this.pageTagsList.some(item => {
                if (item.name === name) {
                    return true;
                }
            });
            if (!openpageHasTag) { //  解决关闭当前标签后再点击回退按钮会退到当前页时没有标签的问题
                util.openNewPage(this, name, this.$route.params || {}, this.$route.query || {});
            }
        },
    },
    mounted () {
        this.init();
    },
    created () {
        let tagsList = [];
        appRouter.map((item) => {
            if (item.children.length <= 1) {
                tagsList.push(item.children[0]);
            } else {
                tagsList.push(...item.children);
            }
        });
        this.$store.commit('setAdminAccount');
        this.$store.commit('setTagsList', tagsList);
        // 显示打开的页面的列表
        this.$store.commit('setOpenedList');
        this.$store.commit('initCachepage');
    },
};
</script>
