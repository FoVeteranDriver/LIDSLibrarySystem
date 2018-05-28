<style lang="less">
@import "./main.less";
</style>
<template>
    <div>
        <loginModal :show="showLoginModal" @clickCancel="hideLogin"></loginModal>
        <div class="main" :class="{'main-hide-text': shrink}">
            <div class="sidebar-menu-con" :style="{width: shrink?'0.51rem':'2.1rem',minWidth:'19px', overflow: shrink ? 'visible' : 'auto'}">
                <shrinkable-menu :shrink="shrink" :menu-list="menuList">
                    <div slot="top" class="logo-con">
                        <div v-show="!shrink" class="max-con">
                            <img src="../../images/user/main-component/logo.png" key="max-logo" :style="{ height: '0.55rem', paddingLeft: '0.1rem'}" />
                            <hgroup class="hgroup">
                                <h2>图书馆</h2>
                                <h2>信息共享空间</h2>
                            </hgroup>
                        </div>
                        <img v-show="shrink" src="../../images/user/main-component/logo.png" key="logo" :style="{ height: '0.35rem',width:'0.35rem',marginTop:'0.08rem'}" />
                    </div>
                </shrinkable-menu>
            </div>
            <div class="main-header-con" :style="{paddingLeft: shrink?'0.51rem':'2.1rem'}">
                <div class="main-header">
                    <div class="navicon-con">
                        <Button :style="{transform: 'rotateZ(' + (this.shrink ? '-90' : '0') + 'deg)'}" type="text" @click="toggleClick">
                            <img src="../../images/user/main-component/navicon.png" class="navicon-con-btn" />
                        </Button>
                    </div>
                    <div class="header-avator-con">
                        <div class="login-con" v-show="!logged">
                            <div class="login-link">
                                <a @click="showLogin">登录</a><img src="../../images/user/home/login.png">
                            </div>
                            <div class="admin-link">
                                <router-link :to="{name:'admin_login'}">
                                    【管理端】<img src="../../images/user/home/admin.png">
                                </router-link>
                            </div> 
                        </div>
                        <div class="user-dropdown-menu-con" v-show="logged">
                            <div class="user-dropdown-innercon">
                                <Dropdown transfer trigger="click" class="user-right" @on-click="handleClickUserDropdown">
                                    <a href="javascript:void(0)">
                                        <img src="../../images/user/main-component/power.png" class="power" />
                                    </a>
                                    <DropdownMenu slot="list">
                                        <DropdownItem name="loginout" divided>退出登录</DropdownItem>
                                    </DropdownMenu>
                                </Dropdown>
                                <div class="user-right">
                                    <img src="../../images/user/main-component/user-avater.png" class="user-avater" />
                                    <span>Hi</span>
                                    <span class="main-user-name">{{userName}}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="single-page-con" :style="{left: shrink?'0.51rem':'2.1rem'}">
                <div class="single-page">
                    <router-view @needLogin="needLogin"></router-view>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import shrinkableMenu from "./main-components/shrinkable-menu/shrinkable-menu.vue";
import loginModal from "./common/login/login";
export default {
    components: {
        shrinkableMenu,
        loginModal
    },
    data() {
        return {
            shrink: false,
            showLoginModal: false
        };
    },
    computed: {
        menuList() {
            return this.$store.state.appUser.userMenuList;
        },
        logged() {
            return this.$store.state.logged;
        },
        userName() {
            return this.$store.state.username;
        }
    },
    methods: {
        shrinkTrigger() {
            var outerWidth = window.outerWidth;
            if (outerWidth < 960) {
                this.shrink = true;
            }
        },
        toggleClick() {
            this.shrink = !this.shrink;
        },
        handleClickUserDropdown(name) {
            let that = this;
            this.$ajax
                .get("http://iyou.s1.natapp.cc/lidsLibrary/user/logout")
                .then(function(response) {
                    if (response.data.code === 0) {
                        console.log("退出登录成功");
                        that.$store.commit("logout");
                    }else {
                        console.log("退出登录失败");
                        console.log(response.data);
                    }
                })
                .catch(function(err) {
                    console.log(err);
                });
        },
        showLogin(event) {
            event.preventDefault();
            this.showLoginModal = true;
            this.$store.commit("loginShow");
        },
        needLogin(){
            this.showLoginModal = true;
            this.$store.commit("loginShow");
        },
        hideLogin() {
            this.showLoginModal = false;
        }
    },
    mounted(){
        this.$store.commit("keepLogin");
    }
};
</script>
