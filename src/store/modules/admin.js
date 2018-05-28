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
                name: 'admin_index'
            }
        ], // 面包屑数组
        adminMenuList: [],
        accessList:[],
        limits:[],
        adminRouters: [
            otherRouter,
            ...appRouter
        ],
        adminAccount:'',
        pageOpenedList: [
            {
                title: '首页',
                path: '',
                name: 'admin_index'
            }
        ],
        cachePage: [],
        tagsList: [...otherRouter.children],
        dontCache: [] // 在这里定义你不想要缓存的页面的name属性值(参见路由配置router.js)
    },
    mutations: {
        setAdminAccount(state){
            state.adminAccount=sessionStorage.getItem('adminAccount');
        },
        setTagsList (state, list) {
            state.tagsList.push(...list);
        },
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
        closePage (state, name) {
            state.cachePage.forEach((item, index) => {
                if (item === name) {
                    state.cachePage.splice(index, 1);
                }
            });
        },
        initCachepage (state) {
            if (localStorage.cachePage) {
                state.cachePage = JSON.parse(localStorage.cachePage);
            }
        },
        removeTag (state, name) {
            state.pageOpenedList.map((item, index) => {
                if (item.name === name) {
                    state.pageOpenedList.splice(index, 1);
                }
            });
        },
        pageOpenedList (state, get) {
            let openedPage = state.pageOpenedList[get.index];
            if (get.argu) {
                openedPage.argu = get.argu;
            }
            if (get.query) {
                openedPage.query = get.query;
            }
            state.pageOpenedList.splice(get.index, 1, openedPage);
            localStorage.pageOpenedList = JSON.stringify(state.pageOpenedList);
        },
        clearAllTags (state) {
            state.pageOpenedList.splice(1);
            state.cachePage.length = 0;
            localStorage.pageOpenedList = JSON.stringify(state.pageOpenedList);
        },
        clearOtherTags (state, vm) {
            let currentName = vm.$route.name;
            let currentIndex = 0;
            state.pageOpenedList.forEach((item, index) => {
                if (item.name === currentName) {
                    currentIndex = index;
                }
            });
            if (currentIndex === 0) {
                state.pageOpenedList.splice(1);
            } else {
                state.pageOpenedList.splice(currentIndex + 1);
                state.pageOpenedList.splice(1, currentIndex - 1);
            }
            let newCachepage = state.cachePage.filter(item => {
                return item === currentName;
            });
            state.cachePage = newCachepage;
            localStorage.pageOpenedList = JSON.stringify(state.pageOpenedList);
        },
        setOpenedList (state) {
            state.pageOpenedList = localStorage.pageOpenedList ? JSON.parse(localStorage.pageOpenedList) : [otherRouter[0].children[0]];
        },
        setCurrentPageName (state, name) {
            state.currentPageName = name;
        },
        setAvator (state, path) {
            localStorage.avatorImgPath = path;
        },
        increateTag (state, tagObj) {
            if (!Util.oneOf(tagObj.name, state.dontCache)) {
                state.cachePage.push(tagObj.name);
                localStorage.cachePage = JSON.stringify(state.cachePage);
            }
            state.pageOpenedList.push(tagObj);
            localStorage.pageOpenedList = JSON.stringify(state.pageOpenedList);
        }
    },
};

export default appAdmin;
