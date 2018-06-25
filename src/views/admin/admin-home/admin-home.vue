<style lang="less">
    @import "./admin-home.less";
</style>
<template>
    <div class="admin-home">
        <h1>Welcome!</h1>
        <div>
            如有疑问请阅读操作说明
        </div>
    </div>
</template>

<script>
export default {
    name: 'admin-home',
    data(){
        return{
            loginSum:10
        }
    },
    mounted(){
        // if(this.$store.state.appAdmin.adminAccount.indexOf('root')>-1){
        //     this.loginSum=1;
        // }
    }
};
</script>
