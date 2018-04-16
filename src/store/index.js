import Vue from 'vue';
import Vuex from 'vuex';

import app from './modules/app';
import user from './modules/user';

Vue.use(Vuex);

const store = new Vuex.Store({
    state: {
        logged: false, //登录状态
        userInfo: {
            name: '',
        },
    },

    mutations: {
        //获取用户名
        writeUserName(state, data) {
            state.userInfo.name = data.result.name;
        },
        login(state) {
            state.logged = true;
        },
        logout(state) {
            state.logged = false;
            state.userInfo.name = '';
        }
    },

    actions: {

    },

    modules: {
        app,
        user
    }
});

export default store;