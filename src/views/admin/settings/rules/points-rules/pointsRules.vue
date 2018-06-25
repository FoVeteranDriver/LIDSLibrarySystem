<style lang="less">
@import "pointsRules.less";
</style>

<template>
    <div class="rules-main">
        <Input v-model="searchKey" placeholder="请输入规则名称" @on-change='searchChangeHandler' size='large'>
        <Button slot="append" icon="ios-search" @click="searchHandler"></Button>
        </Input>

        <Button type="ghost" class="create-points-rule" @click="createPointsRule">新建规则</Button>


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
            rules:[],

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
        createPointsRule()
        {
             this.$router.push({
                name: 'admin_edit_points_rule'
            });
        },
        loadRuleList(){

        },
        displayRules(){

        },
        },
    created(){
        this.loadRuleList();
    }
};
</script>

<style>
</style>