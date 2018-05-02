import {otherRouter, appRouter} from '@/router/userRouter';

const appUser = {
    state: {
        userMenuList: [],
        userRouters: [
            ...otherRouter,
            ...appRouter
        ]
    },
    mutations: {
        updateUserMenulist (state) {
            state.userMenuList = appRouter;
        }
    }
};

export default appUser;
