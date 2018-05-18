import Vue from 'vue';
import iView from 'iview';
import { router } from './router/index';
import store from './store';
import App from './app.vue';
import 'iview/dist/styles/iview.css';
import axios from 'axios';
import $ from 'jquery';
import * as d3 from 'd3';
axios.defaults.withCredentials = true;
Vue.prototype.$ajax = axios;
Vue.prototype.$d3 = d3;
Vue.use(iView);
new Vue({
    el: '#app',
    router: router,
    store: store,
    render: h => h(App),
    mounted() {
        this.$store.commit('updateUserMenulist');
    }
});