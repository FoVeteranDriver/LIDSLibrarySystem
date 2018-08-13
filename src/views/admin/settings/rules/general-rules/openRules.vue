<style lang="less">
@import "openRules.less";
</style>

<template>
    <div class="open-rule-main">
        <p class="rule-name">规则名称
            &nbsp;
            <input v-model="rName" size="large" placeholder="请输入规则名称"></input>
        </p>
        <p class="rule-target">面向对象
            <CheckboxGroup v-model="rTarget">
                <Checkbox label="教工"></Checkbox>
                <Checkbox label="职工"></Checkbox>
                <Checkbox label="学生"></Checkbox>
            </CheckboxGroup>
        </p>
        <div class="rules">
            <div class="rule-number">
                <RadioGroup v-model="rNumber" @on-change="printNumber">
                    <Radio label="one">方案一</Radio>
                    <Radio label="two">方案二</Radio>
                </RadioGroup>
            </div>
            <div class="inner week">
                <p>周一</p>
                <p>周二</p>
                <p>周三</p>
                <p>周四</p>
                <p>周五</p>
                <p>周六</p>
                <p>周日</p>
            </div>
            <div class="inner rule1">
                <p><Select v-model="mon1s">
                            <Option v-for="item in sTimes" :value="item">{{ item }}</Option>
                        </Select>
</p>

                <!-- <p>
                    <TimePicker @on-change="onChange" v-model="mon1" format="HH:mm" type="timerange" placement="bottom-end" placeholder="请选择时间" :steps="[1,15]"></TimePicker>
                </p> -->
                <!-- <p> -->
                    <!-- <TimePicker v-model="tues1" format="HH:mm" type="timerange" placement="bottom-end" placeholder="请选择时间" :steps="[1,15]"></TimePicker>
                </p>
                <p>
                    <TimePicker v-model="wed1" format="HH:mm" type="timerange" placement="bottom-end" placeholder="请选择时间" :steps="[1,15]"></TimePicker>
                </p>
                <p>
                    <TimePicker v-model="thur1" format="HH:mm" type="timerange" placement="bottom-end" placeholder="请选择时间" :steps="[1,15]"></TimePicker>
                </p>
                <p>
                    <TimePicker v-model="fri1" format="HH:mm" type="timerange" placement="bottom-end" placeholder="请选择时间" :steps="[1,15]"></TimePicker>
                </p>
                <p>
                    <TimePicker v-model="sat1" format="HH:mm" type="timerange" placement="bottom-end" placeholder="请选择时间" :steps="[1,15]"></TimePicker>
                </p>
                <p>
                    <TimePicker v-model="sun1" format="HH:mm" type="timerange" placement="bottom-end" placeholder="请选择时间" :steps="[1,15]"></TimePicker>
                </p>
            </div>
            <div class="inner rule2">

                <p>
                    <TimePicker v-model="mon2" format="HH:mm" type="timerange" placement="bottom-end" placeholder="请选择时间" :steps="[1,15]"></TimePicker>
                </p>
                <p>
                    <TimePicker v-model="tues2" format="HH:mm" type="timerange" placement="bottom-end" placeholder="请选择时间" :steps="[1,15]"></TimePicker>
                </p>
                <p>
                    <TimePicker v-model="wed2" format="HH:mm" type="timerange" placement="bottom-end" placeholder="请选择时间" :steps="[1,15]"></TimePicker>
                </p>
                <p>
                    <TimePicker v-model="thur2" format="HH:mm" type="timerange" placement="bottom-end" placeholder="请选择时间" :steps="[1,15]"></TimePicker>
                </p>
                <p>
                    <TimePicker v-model="fri2" format="HH:mm" type="timerange" placement="bottom-end" placeholder="请选择时间" :steps="[1,15]"></TimePicker>
                </p>
                <p>
                    <TimePicker v-model="sat2" format="HH:mm" type="timerange" placement="bottom-end" placeholder="请选择时间" :steps="[1,15]"></TimePicker>
                </p>
                <p>
                    <TimePicker v-model="sun2" format="HH:mm" type="timerange" placement="bottom-end" placeholder="请选择时间" :steps="[1,15]"></TimePicker>
                </p>
            </div> -->
        </div>
    </div>
</template>

<script>
import Cookies from "js-cookie";
import util from "../../../../../libs/util.js";
export default {
    data() {
        return {
            rName: "",
            rTarget: [],
            rNumber:"",
            sTimes:computeTime(),
            mon1:[],
            // tues1:[],
            // wed1:[],
            // thur1:[],
            // fri1:[],
            // sat1:[],
            // sun1:[],
            // mon2:[],
            // tues2:[],
            // wed2:[],
            // thur2:[],
            // fri2:[],
            // sat2:[],
            // sun2:[]
        };
    },
    methods: {
        onChange(){
            console.log(this.mon1)
        },
        printNumber(){
            console.log(this.rNumber);
        },
        computeTime(){
            let times = [];
            let
        }
    },
    created() {}
};
</script>
