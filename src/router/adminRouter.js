import Main from '@/views/admin/Main.vue';

// 不作为Main组件的子页面展示的页面单独写，如下
export const loginRouter = {
    path: '/admin_login',
    name: 'admin_login',
    meta: {
        title: 'Login - 登录'
    },
    component: resolve => { require(['@//views/admin/login.vue'], resolve); }
};

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
export const otherRouter = [{
    path: '/admin',
    name: 'otherRouter',
    component: Main,
    children: [
        { path: '/', title: { i18n: 'admin_index' }, name: 'admin_index', component: resolve => { require(['@/views/admin/settings/authority/index.vue'], resolve); } }
    ]
}];

// 作为Main组件的子页面展示并且在左侧菜单显示的路由写在appRouter里
export const appRouter = [ 
    {
        path: '/admin-settings',
        icon: './src/images/admin/main-component/sys-service.png',
        name: 'admin-settings',
        title: '设置',
        access:1,
        component: Main,
        children: [
            {
                path: 'admin-authority',
                name: 'admin-authority',
                icon: 'social-octocat',
                title: '权限管理',
                access: 1,
                component: resolve => { require(['@/views/admin/settings/authority/index.vue'], resolve); }
            },
            {
                path: 'admin-rules',
                name: 'admin-rules',
                icon: 'ios-alarm-outline',
                title: '系统规则',
                access: 1,
                component: resolve => { require(['@/views/user/system-service/notice/notice.vue'], resolve); }
            },
            {
                path: 'admin-display',
                name: 'admin-display',
                icon: 'ios-star-outline',
                title: '通知展示',
                access: 1,
                component: resolve => { require(['@/views/user/system-service/review/review.vue'], resolve); }
            },
            {
                path: 'admin-space',
                name: 'admin-space',
                icon: 'ios-star-outline',
                title: '空间区域',
                access: 1,
                component: resolve => { require(['@/views/user/system-service/review/review.vue'], resolve); }
            }
        ]
    },
    {
        path: '/admin-management',
        icon: './src/images/admin/main-component/sys-service.png',
        name: 'admin-management',
        title: '日常管理',
        access: 1,
        component: Main,
        children: [
            {
                path: 'daily1',
                name: 'daily1',
                icon: 'social-octocat',
                title: 'daily1',
                access: 1,
                component: resolve => { require(['@/views/user/system-service/introduce/introduce.vue'], resolve); }
            },
            {
                path: 'daily2',
                name: 'daily2',
                icon: 'ios-alarm-outline',
                title: 'daily2',
                access: 1,
                component: resolve => { require(['@/views/user/system-service/notice/notice.vue'], resolve); }
            },
        ]
    },
    {
        path: '/admin-data',
        icon: './src/images/admin/main-component/sys-service.png',
        name: 'admin-data',
        title: '数据',
        access: 1,
        component: Main,
        children: [
            {
                path: 'data1',
                name: 'data1',
                icon: 'social-octocat',
                title: 'data1',
                component: resolve => { require(['@/views/user/system-service/introduce/introduce.vue'], resolve); }
            },
            {
                path: 'data2',
                name: 'data2',
                icon: 'ios-alarm-outline',
                title: 'data2',
                component: resolve => { require(['@/views/user/system-service/notice/notice.vue'], resolve); }
            },
        ]
    },
]

// 所有上面定义的路由都要写在下面的routers里
export const adminRouters = [
    loginRouter,
    ...otherRouter,
    ...appRouter,
    page500,
    page403,
    page404
];