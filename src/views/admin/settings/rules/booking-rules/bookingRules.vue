<style lang="less">
@import "bookingRules.less";
</style>

<template>
    <div class="rules-main">
        <Input v-model="searchKey" placeholder="请输入规则名称" @on-change='searchChangeHandler' size='large'>
        <Button slot="append" icon="ios-search" @click="searchHandler"></Button>
        </Input>

        <Button type="ghost" class="create-booking-rule"  @click="createBookingRule">新建规则</Button>

        <Table :columns="columns" :data="rules"></Table>
    </div>
</template>

<script>
import Cookies from "js-cookie";
import util from "../../../../../libs/util.js";
export default {
    data() {
        return {
            columns:[
                    {
                        title: '规则名称',
                        key: 'name',
                    },
                    {
                        title: '类型',
                        key: 'type'
                    },
                    {
                        title: '备注',
                        key: 'note'
                    },
                    {
                        title:'操作',
                        key: 'action'
                    }
                ],
            bookingRules:[],

            searchKey: ""
        };
    },
    methods: {
        searchChangeHandler(){
            if (this.searchKey.trim === "") {
                this.loadRuleList();
            }
        },
        searchHandler(){

        },
        createBookingRule(){
             this.$router.push({
                name: 'edit_booking_rule'
            });
        },
        loadRuleList(){
            let that = this;
                 that.$ajax
                .get(
                    util.adminUrl+"/bookingRule/editRule"
                )
                .then(function(response){
                    let data=response.data;
                    if(data.code==0){
                        that.displayRules(data.result);
                    }else if(data.code==5){ //用户没有登录或者禁用cookie

                    }
                })
                .catch(function(err){
                    console.log(err);
                });
        },
        displayRules(data){
            this.bookingRules = [];
        },
    },
    created(){
        this.loadRuleList();
    }
};
</script>

<style>
</style>