import Vue from 'vue';
import Vuex from 'vuex';

import appUser from './modules/user';

Vue.use(Vuex);

const store = new Vuex.Store({
    state: {
        logged: false, //登录状态
        username: "",
        loginShow: false,
        rootSize: -1
    },

    mutations: {
        //获取用户名
        writeUserName(state, data) {
            state.username = data.result.name;
            sessionStorage.username = data.result.name;
        },
        login(state) {
            state.logged = true;
            sessionStorage.logged = true;
        },
        logout(state) {
            state.logged = false;
            state.username = '';
            sessionStorage.username = "";
            sessionStorage.logged = false;
        },
        keepLogin(state) {
            if (sessionStorage.logged === "true") {
                state.logged = true;
                state.username = sessionStorage.username;
            }
        },
        loginShow(state) {
            state.loginShow = true;
        },
        loginClose(state) {
            state.loginShow = false;
        },
        storeREM(state, data) {
            state.rootSize = data;
        }
    },

    actions: {

    },

    modules: {
        appUser,
    }
});

export default store;