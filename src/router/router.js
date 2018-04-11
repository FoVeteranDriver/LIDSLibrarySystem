import Main from '@/views/user/Main.vue';

// 不作为Main组件的子页面展示的页面单独写，如下
export const page404 = {
    path: '/*',
    name: 'error-404',
    meta: {
        title: '404-页面不存在'
    },
    component: resolve => { require(['@/views/error-page/404.vue'], resolve); }
};

export const page403 = {
    path: '/403',
    meta: {
        title: '403-权限不足'
    },
    name: 'error-403',
    component: resolve => { require(['@//views/error-page/403.vue'], resolve); }
};

export const page500 = {
    path: '/500',
    meta: {
        title: '500-服务端错误'
    },
    name: 'error-500',
    component: resolve => { require(['@/views/error-page/500.vue'], resolve); }
};

// 作为Main组件的子页面展示但是不在左侧菜单显示的路由写在otherRouter里
export const otherRouter = {
    path: '/',
    name: 'otherRouter',
    component: Main,
    children: [
        { path: 'home', title: { i18n: 'home' }, name: 'home_index', component: resolve => { require(['@/views/user/home/home.vue'], resolve); } }
    ]
};

// 作为Main组件的子页面展示并且在左侧菜单显示的路由写在appRouter里
export const appRouter = [{
        path: '/home',
        icon: './src/images/user/main-component/home.png',
        title: '主页',
        name: 'home',
        component: Main,
        children: [
            { path: 'index', title: '主页', icon: 'ios-home-outline', name: 'home', component: resolve => { require(['@/views/user/home/home.vue'], resolve); } }
        ]
    },
    {
        path: '/systemService',
        icon: './src/images/user/main-component/sys-service.png',
        name: 'systemService',
        title: '系统服务',
        component: Main,
        children: [{
                path: 'introduce',
                name: 'introduce',
                icon: 'social-octocat',
                title: '使用帮助',
                component: resolve => { require(['@/views/user/system-service/introduce/introduce.vue'], resolve); }
            },
            {
                path: 'notice',
                name: 'notice',
                icon: 'ios-alarm-outline',
                title: '活动预告',
                component: resolve => { require(['@/views/user/system-service/notice/notice.vue'], resolve); }
            },
            {
                path: 'review',
                name: 'review',
                icon: 'ios-star-outline',
                title: '活动回顾',
                component: resolve => { require(['@/views/user/system-service/review/review.vue'], resolve); }
            }
        ]
    },
    {
        path: '/book',
        icon: './src/images/user/main-component/book.png',
        name: 'book',
        title: '预约服务',
        component: Main,
        children: [{
                path: 'studyroom',
                icon: 'ios-bookmarks-outline',
                name: 'studyroom',
                title: '研习间预约',
                component: resolve => { require(['@/views/user/book-service/studyroom/studyroom.vue'], resolve); }
            },
            {
                path: 'page3',
                icon: 'ios-pie-outline',
                name: 'page3',
                title: '学习间预约',
                component: resolve => { require(['@/views/user/book-service/learningroom/learningroom.vue'], resolve); }
            }
        ]
    },
    {
        path: '/user',
        icon: './src/images/user/main-component/user-center.png',
        title: '个人中心',
        name: 'user',
        component: Main,
        children: [
            { path: 'user', title: '个人中心', icon: 'ios-person-outline', name: 'user_index', component: resolve => { require(['@/views/user/user-center/user.vue'], resolve); } }
        ]
    }
];

// 所有上面定义的路由都要写在下面的routers里
export const routers = [
    otherRouter,
    ...appRouter,
    page500,
    page403,
    page404
];