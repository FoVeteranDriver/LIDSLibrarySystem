import {otherRouter, appRouter} from '@/router/userRouter';

const appUser = {
    state: {
        menuList: [],
        routers: [
            ...otherRouter,
            ...appRouter
        ]
    },
    mutations: {
        updateMenulist (state) {
            state.menuList = appRouter;
        }
    }
};

export default appUser;
