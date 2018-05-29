import Main from '@/views/user/Main.vue';

// // 不作为Main组件的子页面展示的页面单独写，如下
//为空

// 作为Main组件的子页面展示但是不在左侧菜单显示的路由写在otherRouter里
export const otherRouter = {
    path: '/',
    name: 'otherRouter',
    component: Main,
    children: [
        { path: '/', title: { i18n: 'home' }, name: 'home', component: resolve => { require(['@/views/user/home/home.vue'], resolve); } },
        { path: 'noticeDetail', title: 'noticeDetail', name: 'noticeDetail', component: resolve => { require(['@/views/user/home/noticeDetail.vue'], resolve); } }
    ]
}

// 作为Main组件的子页面展示并且在左侧菜单显示的路由写在appRouter里
export const appRouter = [
    {
        path: '/home',
        icon: './static/images/user/main-component/home.png',
        title: '主页',
        name: 'home',
        component: Main,
        children: [
            { 
                path: 'index', 
                title: '主页', 
                icon: 'ios-home-outline', 
                name: 'home', 
                component: resolve => { require(['@/views/user/home/home.vue'], resolve); } 
            }
        ]
    },
    {
        path: '/systemService',
        icon: './static/images/user/main-component/sys-service.png',
        name: 'systemService',
        title: '系统服务',
        component: Main,
        children: [
            {
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
        icon: './static/images/user/main-component/book.png',
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
                path: 'learningroom',
                icon: 'ios-pie-outline',
                name: 'learningroom',
                title: '学习间预约',
                component: resolve => { require(['@/views/user/book-service/learningroom/learningroom.vue'], resolve); }
            }
        ]
    },
    {
        path: '/user',
        icon: './static/images/user/main-component/user-center.png',
        title: '个人中心',
        name: 'user',
        component: Main,
        children: [
            { path: 'user', title: '个人中心', icon: 'ios-person-outline', name: 'user_index', component: resolve => { require(['@/views/user/user-center/user.vue'], resolve); } }
        ]
    }
];

// 所有上面定义的路由都要写在下面的routers里
export const userRouters = [
    otherRouter,
    ...appRouter,
];