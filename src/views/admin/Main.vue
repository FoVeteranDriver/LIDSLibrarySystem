<style lang="less">
    @import "./main.less";
</style>
<template>
    <div class="admin-main">
        <div class="sidebar-menu-con" :style="{width: '0.9rem', overflow: 'visible'}">
            <shrinkable-menu 
                :menu-list="menuList"
                @on-menuOpen='handleMenuOpen'>
                <div slot="top" class="logo-con">
                    <img src="../../images/admin/main-component/logo.png" key="max-logo" :style="{ height: '0.55rem', paddingLeft: '0.1rem'}" />
                </div>
            </shrinkable-menu>
            <div class="subMenu-con" :style="{display:isHidden?'none':'block'}"></div>
        </div>
        <div class="admin-main-header-con" :style="{paddingLeft:isHidden?'0.9rem':'2.1rem'}">
            <div class="admin-main-header">
                <div class="header-avator-con">
                    <div class="user-dropdown-menu-con">
                        <Row type="flex" justify="end" align="middle" class="user-dropdown-innercon">
                            <Dropdown transfer trigger="click" @on-click="handleClickUserDropdown">
                                <a href="javascript:void(0)">
                                    <span class="admin-main-user-name">{{ userName }}</span>
                                    <Icon type="arrow-down-b"></Icon>
                                </a>
                                <DropdownMenu slot="list">
                                    <DropdownItem name="loginout" divided>退出登录</DropdownItem>
                                </DropdownMenu>
                            </Dropdown>
                            <Avatar icon="person" style="background: #619fe7;margin-left:10px;"></Avatar>
                        </Row>
                    </div>
                </div>
            </div>
        </div>
        <!--<div class="tags-con">
                <tags-page-opened :pageTagsList="pageTagsList"></tags-page-opened>
        </div>-->
        <div class="single-page-con" :style="{left:isHidden?'0.9rem':'2.1rem'}">
            <div class="single-page">
                hdufhdyu
                <router-view></router-view>
            </div>
        </div>
    </div>
</template>
<script>
import Cookies from 'js-cookie';
import shrinkableMenu from './main-components/shrinkable-menu/shrinkable-menu.vue';
//import tagsPageOpened from './main-components/tags-page-opened.vue';
export default {
    components: {
        shrinkableMenu,
        //tagsPageOpened,
    },
    data () {
        return {
            userName: '',
            isHidden:true
        };
    },
    computed: {
        menuList () {
            return this.$store.state.appAdmin.adminMenuList;
        },
        cachePage () {
            return this.$store.state.app.cachePage;
        },
        //pageTagsList () {
           // return this.$store.state.app.pageOpenedList; // 打开的页面的页面对象
        //},
    },
    methods: {
        init () {
            this.userName = Cookies.get('user');
            this.$store.commit('updateAccessList');
            this.$store.commit('updateAdminMenulist');
        },
        handleClickUserDropdown (name) {
            this.$router.push({
                name: 'admin_login'
            });
        },
        handleMenuOpen(name){
            if(name!='-1'){
                this.isHidden=false;
            }else{
                this.isHidden=true;
            }
        }
    },
    mounted () {
        this.init();
    }
};
</script>
