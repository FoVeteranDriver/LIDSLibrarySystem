<style lang="less">
@import "openRule.less";
</style>

<template>
    <div class="open-rule-main">
        <p class="rule-name">规则名称
            <Input v-model="ruleName" size="large" placeholder="请输入规则名称"></Input>
        </p>
        <p class="rule-target">面向对象
            
        </p>
    </div>
</template>

<script>
import Cookies from "js-cookie";
import util from "../../../../libs/util.js";
export default {
    data() {
        return {
        };
    },
    methods: {
      
    },
    created(){

    }
};
</script>

<style>
</style>