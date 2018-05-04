import Vue from 'vue';
import iView from 'iview';
import Util from '../libs/util';
import VueRouter from 'vue-router';
import {userRouters} from './userRouter';
import {adminRouters} from './adminRouter';
import {otherRouter,appRouter} from './adminRouter';
import Cookies from 'js-cookie';
import store from '../store';
let routers=userRouters.concat(adminRouters);

Vue.use(VueRouter);

// 路由配置
const RouterConfig = {
    // mode: 'history',
    routes: routers
};

export const router = new VueRouter(RouterConfig);
router.beforeEach((to, from, next) => {
    iView.LoadingBar.start();
    Util.title(to.meta.title);
    if(to.name.indexOf('admin')>-1){
        if (!Cookies.get('user')&& to.name!=='admin_login') { // 判断是否已经登录且前往的页面不是登录页
            next({
                name: 'admin_login'
            });
        }else if (Cookies.get('user') && to.name === 'admin_login') { // 判断是否已经登录且前往的是登录页
            Util.title();
            next({
                name:'admin_index'
            });
        } else {
            const curRouterObj = Util.getRouterObjByName([...otherRouter, ...appRouter], to.name);
            const limits=store.state.appAdmin.limits;
            if (curRouterObj && curRouterObj.access !== undefined) { // 需要判断权限的路由
                let temp=[];
                if(limits.length){
                   temp=limits.filter(item=>{
                        return item==to.name;
                    });
                }
                if(temp.length){
                    next({
                        replace: true,
                        name: 'error-403'
                    });
                }else{
                    Util.toDefaultPage([...otherRouter, ...appRouter], to.name, router, next); // 如果在地址栏输入的是一级菜单则默认打开其第一个二级菜单的页面
                }
                // if (curRouterObj.access === parseInt(Cookies.get('access'))) {
                //     Util.toDefaultPage([...otherRouter, ...appRouter], to.name, router, next); // 如果在地址栏输入的是一级菜单则默认打开其第一个二级菜单的页面
                // } else {
                //     next({
                //         replace: true,
                //         name: 'error-403'
                //     });
                // }
            } else { // 没有配置权限的路由, 直接通过
                Util.toDefaultPage([...routers], to.name, router, next);
            }
        }
    }else{
        next();
    }
});

router.afterEach((to) => {
    iView.LoadingBar.finish();
    window.scrollTo(0, 0);
});
