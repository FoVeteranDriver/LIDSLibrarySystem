import Vue from 'vue';
import User from '../views/user/user-center/user.vue';
describe('User',()=>{
    it('has a created hook',()=>{
        expect(typeof User.createded).toBe('function')
    })
})