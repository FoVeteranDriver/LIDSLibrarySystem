import Main from '@/views/admin/Main.vue';

//所有管理员端的路由必须添加前缀admin_

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
export const otherRouter = {
    path: '/admin',
    name: 'otherRouter',
    component: Main,
    children: [
        { path: '/admin_index', title: '首页', name: 'admin_index', component: resolve => { require(['@/views/admin/admin-home/admin-home.vue'], resolve); } },
        { path: '/admin_center', title: '管理员中心', name: 'admin_center', component: resolve => { require(['@/views/admin/admin-center/admin_center.vue'], resolve); } },
        { path: '/admin_edit', title: '管理员编辑', name: 'admin_edit', component: resolve => { require(['@/views/admin/settings/authority/editAdmin.vue'], resolve); } },
        { path: '/admin_general_rules', title: '总体规则', name: 'admin_general_rules', component: resolve => { require(['@/views/admin/settings/rules/general-rules/generalRules.vue'], resolve); } },
        { path: '/admin_open_rules', title: '新建开放规则', name: 'admin_open_rules', component: resolve => { require(['@/views/admin/settings/rules/general-rules/openRules.vue'], resolve); } },
        { path: '/admin_close_rules', title: '新建不开放规则', name: 'admin_close_rules', component: resolve => { require(['@/views/admin/settings/rules/general-rules/closeRules.vue'], resolve); } },
        { path: '/admin_booking_rules', title: '预约规则', name: 'admin_booking_rules', component: resolve => { require(['@/views/admin/settings/rules/booking-rules/bookingRules.vue'], resolve); } },
        { path: '/admin_edit_booking_rule', title: '新建预约规则', name: 'admin_edit_booking_rule', component: resolve => { require(['@/views/admin/settings/rules/booking-rules/editBookingRule.vue'], resolve); } },
        { path: '/admin_points_rules', title: '积分规则', name: 'admin_points_rules', component: resolve => { require(['@/views/admin/settings/rules/points-rules/pointsRules.vue'], resolve); } },
        { path: '/admin_edit_points_rule', title: '新建积分规则', name: 'admin_edit_points_rule', component: resolve => { require(['@/views/admin/settings/rules/points-rules/editPointsRule.vue'], resolve); } },

        { path: '/general_rules', title: '总体规则', name: 'general_rules', component: resolve => { require(['@/views/admin/settings/rules/general-rules/generalRules.vue'], resolve); } },
        { path: '/open_rules', title: '新建开放规则', name: 'open_rules', component: resolve => { require(['@/views/admin/settings/rules/general-rules/openRules.vue'], resolve); } },
        { path: '/close_rules', title: '新建不开放规则', name: 'close_rules', component: resolve => { require(['@/views/admin/settings/rules/general-rules/closeRules.vue'], resolve); } },
        { path: '/booking_rules', title: '预约规则', name: 'booking_rules', component: resolve => { require(['@/views/admin/settings/rules/booking-rules/bookingRules.vue'], resolve); } },
        { path: '/edit_booking_rule', title: '新建预约规则', name: 'edit_booking_rule', component: resolve => { require(['@/views/admin/settings/rules/booking-rules/editBookingRule.vue'], resolve); } },
        { path: '/points_rules', title: '积分规则', name: 'points_rules', component: resolve => { require(['@/views/admin/settings/rules/points-rules/pointsRules.vue'], resolve); } },
        { path: '/edit_points_rule', title: '新建积分规则', name: 'edit_points_rule', component: resolve => { require(['@/views/admin/settings/rules/points-rules/editPointsRule.vue'], resolve); } },
        { path: '/admin_spaceState', title: '空间状况', name: 'admin_spaceState', component: resolve => { require(['@/views/admin/management/mSpace/spaceState.vue'], resolve); } },
        { path: '/admin_notice', title: '通知', name: 'admin_notice', component: resolve => { require(['@/views/admin/settings/display/notice.vue'], resolve); } },
        { path: '/admin_noticeEdit', title: '添加通知', name: 'admin_noticeEdit', component: resolve => { require(['@/views/admin/settings/display/editNotice.vue'], resolve); } },
        { path: '/admin_noticeDetail/:notice_id', title: '通知详情', name: 'admin_noticeDetail', component: resolve => { require(['@/views/admin/settings/display/noticeDetail.vue'], resolve); } },
        { path: '/admin_infoEdit', title: '修改信息', name: 'admin_infoEdit', component: resolve => { require(['@/views/admin/settings/display/editInfo.vue'], resolve); } },
    ]
};

// 作为Main组件的子页面展示并且在左侧菜单显示的路由写在appRouter里
export const appRouter = [{
        path: '/admin-settings',
        icon: 'ios-gear-outline',
        name: 'admin-settings',
        title: '设置',
        access: 1,
        component: Main,
        children: [{
                path: 'admin-authority',
                name: 'admin-authority',
                icon: 'locked',
                title: '权限管理',
                access: 1,
                component: resolve => { require(['@/views/admin/settings/authority/authority.vue'], resolve); }
            },
            {
                path: 'admin-rules',
                name: 'admin-rules',
                icon: 'ionic',
                title: '系统规则',
                access: 1,
                component: resolve => { require(['@/views/admin/settings/rules/rules.vue'], resolve); }
            },
            {
                path: 'admin-display',
                name: 'admin-display',
                icon: 'chatbubble-working',
                title: '通知展示',
                access: 1,
                component: resolve => { require(['@/views/admin/settings/display/display.vue'], resolve); }
            },
            {
                path: 'admin-space',
                name: 'admin-space',
                icon: 'cube',
                title: '空间区域',
                access: 1,
                component: resolve => { require(['@/views/admin/settings/space/space.vue'], resolve); }
            }
        ]
    },
    {
        path: '/admin-management',
        icon: 'ios-ionic-outline',
        name: 'admin-management',
        title: '管理',
        access: 1,
        component: Main,
        children: [{
                path: 'admin-mSpace',
                name: 'admin-mSpace',
                icon: 'cube',
                title: '空间区域',
                access: 1,
                component: resolve => { require(['@/views/admin/management/mSpace/mSpace.vue'], resolve); }
            },
            {
                path: 'admin-credits',
                name: 'admin-credits',
                icon: 'ios-list',
                title: '积分处罚',
                access: 1,
                component: resolve => { require(['@/views/admin/management/credit/credit.vue'], resolve); }
            },
            {
                path: 'admin-activity',
                name: 'admin-activity',
                icon: 'social-chrome-outline',
                title: '活动安排',
                access: 1,
                component: resolve => { require(['@/views/admin/management/activity/activity.vue'], resolve); }
            }
        ]
    },
    {
        path: '/admin-data',
        icon: 'ios-pie-outline',
        name: 'admin-data',
        title: '数据',
        access: 1,
        component: Main,
        children: [{
                path: 'admin-usage',
                name: 'admin-usage',
                icon: 'ios-analytics',
                title: '使用统计',
                component: resolve => { require(['@/views/admin/data/page1/page1.vue'], resolve); }
            },
            {
                path: 'admin-mCredits',
                name: 'admin-mCredits',
                icon: 'thumbsdown',
                title: '违约统计',
                component: resolve => { require(['@/views/admin/data/page2/page2.vue'], resolve); }
            },
            {
                path: 'admin-bookSheet',
                name: 'admin-bookSheet',
                icon: 'ios-recording',
                title: '预约统计',
                component: resolve => { require(['@/views/admin/data/page2/page2.vue'], resolve); }
            },
            {
                path: 'admin-userInfo',
                name: 'admin-userInfo',
                icon: 'android-contact',
                title: '用户统计',
                component: resolve => { require(['@/views/admin/data/page2/page2.vue'], resolve); }
            },
        ]
    },
]

// 所有上面定义的路由都要写在下面的routers里
export const adminRouters = [
    loginRouter,
    otherRouter,
    ...appRouter,
    page500,
    page403,
    page404
];