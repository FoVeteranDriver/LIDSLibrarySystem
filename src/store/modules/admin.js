import {otherRouter, appRouter} from '@/router/adminRouter';
import Util from '@/libs/util';
import Cookies from 'js-cookie';
import Vue from 'vue';
//import { stat } from 'fs';

const appAdmin = {
    state: {
        currentPath: [
            {
                title: '首页',
                path: '',
                name: 'home_index'
            }
        ], // 面包屑数组
        adminMenuList: [],
        accessList:[],
        limits:[],
        adminRouters: [
            ...otherRouter,
            ...appRouter
        ],
    },
    mutations: {
        updateAccessList(state){
            state.accessList=JSON.parse(sessionStorage.getItem('accessList'));
            state.limits=JSON.parse(sessionStorage.getItem('limits'));
        },
        updateAdminMenulist (state) {
           if(state.accessList&&state.accessList.length){
                let menuList = [];
                appRouter.forEach((item, index) => {
                    if (item.access !== undefined) {
                        let target=state.accessList.filter((i)=>{
                            return i.name==item.name
                        })[0];
                        if(target!== undefined){
                            let accessCode=target.access;
                            if (Util.showThisRoute(item.access, accessCode)) {
                                let len = menuList.push(JSON.parse(JSON.stringify(item)));
                                if (item.children.length > 1) {
                                    let subRoutes=target.children;
                                    let childrenArr = [];
                                    childrenArr = item.children.filter(child => {
                                        if (child.access !== undefined) {
                                            accessCode=subRoutes.filter((i)=>{
                                                return i.name==child.name;
                                            })[0].access;
                                            if (child.access === accessCode) {
                                                return child;
                                            }
                                        } else {
                                            return child;
                                        }
                                    });
                                    menuList[len - 1].children = childrenArr;
                                }
                            }
                        }else{
                            menuList.push(item);
                        }
                    } else {
                        let len = menuList.push(JSON.parse(JSON.stringify(item)));
                        if (item.children.length > 1) {
                            let childrenArr = [];
                            let target=state.accessList.filter((i)=>{
                                return i.name==item.name;
                            })[0];
                            if(target!=undefined){
                                let subRoutes=target.children;
                                childrenArr = item.children.filter(child => {
                                    if (child.access !== undefined) {
                                        let accessCode=subRoutes.filter((i)=>{
                                            return i.name==child.name;
                                        })[0].access;
                                        if (Util.showThisRoute(child.access, accessCode)) {
                                            return child;
                                        }
                                    } else {
                                        return child;
                                    }
                                });
                                if (childrenArr === undefined || childrenArr.length === 0) {
                                    menuList.splice(len - 1, 1);
                                } else {
                                    let handledItem = JSON.parse(JSON.stringify(menuList[len - 1]));
                                    handledItem.children = childrenArr;
                                    menuList.splice(len - 1, 1, handledItem);
                                }
                            }
                        }
                    }
                });
                state.adminMenuList = menuList;
           }else{
                state.adminMenuList=appRouter;
           }
           
        },
        setCurrentPath (state, pathArr) {
            state.currentPath = pathArr;
        },
    },
};

export default appAdmin;
