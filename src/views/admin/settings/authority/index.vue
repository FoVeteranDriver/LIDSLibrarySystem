<style lang="less">
    @import "./index.less";
</style>
<template>
    <div class="authority-main">
        <Input v-model="searchKey" placeholder="账号/姓名/学工号搜索" size='large'>
            <Button slot="append" icon="ios-search"></Button>
        </Input>
        <Button type="ghost" class="addAdmin">新建管理员</Button>
    </div>
</template>

<script>
export default {
    name: 'home',
    data () {
        return {
            searchKey:''
        };
    },
    methods:{
        loadAdminList(){

        }
    }
};
</script>
