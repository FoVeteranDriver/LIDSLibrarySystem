import Vue from 'vue';
import Vuex from 'vuex';

import app from './modules/app';
import user from './modules/user';

Vue.use(Vuex);

const store = new Vuex.Store({
    state: {
        logged: false,
        userInfo: {
            name: '',
        },
    },

    mutations: {
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