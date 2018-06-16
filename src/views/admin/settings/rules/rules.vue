<style lang="less">
@import "rules.less";
</style>

<template>
    <div class="rules-main">
        <Input v-model="searchKey" placeholder="请输入规则名称" @on-change='searchChangeHandler' size='large'>
        <Button slot="append" icon="ios-search" @click="searchHandler"></Button>
        </Input>

        <Button type="ghost" class="general-rules" @click="generalRules">总体规则</Button>
        <Button type="ghost" class="booking-rules" @click="bookingRules">预约规则</Button>
        <Button type="ghost" class="points-rules" @click="pointsRules">积分规则</Button>

        <Table :columns="columns" :data="rules"></Table>
    </div>
</template>

<script>
import Cookies from "js-cookie";
import util from "../../../../libs/util.js";
export default {
    data() {
        return {
            columns: [
                {
                    title: "name",
                    key: "name"
                },
                {
                    title: "type",
                    key: "type"
                },
                {
                    title: "note",
                    key: "note"
                },
                {
                    title: "action",
                    key: "action",
                    align:'center',
                    render:(h,params)=>{
                        let hElem=[];
                        hElem.push( h('span',{
                            style: {
                                color:'#3f3140',
                                cursor:'pointer',
                                padding:'10px',
                            },
                            on: {
                                click: () => {
                                    this.handleEdit(params.row,params.index)
                                }
                            }
                        },'编辑'));
                        hElem.push( h('span',{
                            style: {
                                color:'#f78888',
                                cursor:'pointer',
                                padding:'10px',
                            },
                            on: {
                                click: () => {
                                }
                            }
                        },'删除'));
                        return hElem;
                    }
                }
            ],
            rules: [],

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
        generalRules() {
            this.$router.push({
                name: "general_rules"
            });
        },
        bookingRules() {
            this.$router.push({
                name: "booking_rules"
            });
        },
        pointsRules(){
            this.$router.push({
                name: "points_rules"
            });
        },
        loadRuleList(){
            let that = this;
                that.$ajax
                .get(
                    util.adminUrl+"/user/adminList/"
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

        },
    },
    created() {
        this.loadRuleList();
    }
};
</script>

<style>
</style>
