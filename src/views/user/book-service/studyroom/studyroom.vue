<style lang="less">
@import "./studyroom.less";
</style>
<template>
    <div>
        <h1>研习间预约</h1>
        <tab :tabList="tabList" :imgUrl="imgUrl">
            <div slot="aContent" class="orderTab">
                <div class="header">
                    <h2 class="time">TODAY {{time}}</h2>
                    <p class="guide">
                        <span>已预约</span>
                        <span>过期/关闭</span>
                        <span>拖拽选择时间</span>
                    </p>
                </div>
                <div class="body">
                    <div class="btn-group">
                        <Button class="last-week" type="default" icon="arrow-left-b"></Button>
                        <Button v-for="item in week" class="week-day" type="primary">
                            <p>{{item.month}}月{{item.day}}日</p>
                            <p>星期{{item.weekDay}}</p>
                        </Button>
                        <Button class="next-week" type="default" icon="arrow-right-b"></Button>
                    </div>
                    <table class="orderTable">
                        <tr v-for="(room,roomIndex) in rooms">
                            <th>{{rooms[roomIndex]}}</th>
                            <td v-for="(hour,hourIndex) in hours">
                                {{hours[hourIndex]}}
                            </td>
                        </tr>
                    </table>
                </div>
                <Button v-if="false" type="primary" @click="handleSelect">click me</Button>
                <bookModal :title="title" v-if="showBookModal" :key="modalState"></bookModal>
            </div>
            <div slot="bContent">
            </div>
            <div slot="cContent">
                <picShow :imageList="imageList">
                </picShow>
            </div>
        </tab>
    </div>
</template>

<script>
import tab from "../../common/tab/tab.vue";
import picShow from "../../common/picShow/picShow.vue";
import bookModal from "./bookModal.vue";
export default {
    name: "page",
    components: {
        tab,
        picShow,
        bookModal
    },
    data() {
        return {
            time: this.$options.methods.getTime(),
            week: this.$options.methods.getWeek(),
            tabList: [
                { label: "预约状态", state: "aContent" },
                { label: "预约须知", state: "bContent" },
                { label: "相册展示", state: "cContent" },
                { label: "硬件配置", state: "dContent" }
            ],
            imgUrl: "./src/images/user/common/tab/example.png",
            imageList: [
                {
                    title: "pic1",
                    url: "./src/images/user/common/picShow/pic1.jpg"
                },
                {
                    title: "pic2",
                    url: "./src/images/user/common/picShow/pic2.jpg"
                },
                {
                    title: "pic3",
                    url: "./src/images/user/common/picShow/pic3.jpg"
                },
                {
                    title: "pic4",
                    url: "./src/images/user/common/picShow/pic4.jpg"
                },
                {
                    title: "pic5",
                    url: "./src/images/user/common/picShow/pic5.jpg"
                },
                {
                    title: "pic6",
                    url: "./src/images/user/common/picShow/pic6.jpg"
                },
                {
                    title: "pic7",
                    url: "./src/images/user/common/picShow/pic7.jpg"
                },
                {
                    title: "pic8",
                    url: "./src/images/user/common/picShow/pic8.jpg"
                }
            ],
            showBookModal: false,
            modalState: false,
            title: "welcome",
            rooms:["研修室1","研修室2","研修室3","研修室4","研修室5","合作研修室1","合作研修室2","合作研修室3","合作研修室4","合作研修室5"],
            hours:["8:00","9:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00"],
        };
    },
    methods: {
        handleSelect() {
            this.showBookModal = true;
            this.modalState = !this.modalState;
        },

        //获取当前日期
        getTime() {
            let date = new Date(),
                year,
                month,
                day;
            year = date.getFullYear().toString();
            month =
                date.getMonth() + 1 > 9
                    ? (date.getMonth() + 1).toString()
                    : "0" + (date.getMonth() + 1).toString();
            day =
                date.getDate() > 9
                    ? date.getDate().toString()
                    : "0" + date.getDate().toString();
            return year + "." + month + "." + day;
        },

        //获取一周的日期和星期
        getWeek() {
            let date = new Date().getTime();
            let dayTime = 24 * 60 * 60 * 1000;
            let results = [];
            let weekDays = ["日","一", "二", "三", "四", "五", "六"];
            for (let i = 0; i < 7; i++) {
                let nowDate = new Date(date + i * dayTime);
                let month = nowDate.getMonth() + 1;
                let day = nowDate.getDate();
                let weekDay = weekDays[nowDate.getDay()];
                let result = {
                    month: month,
                    day: day,
                    weekDay: weekDay
                };
                results.push(result);
            }
            return results;
        }
    }
};
</script>