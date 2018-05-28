<style lang="less">
@import "./learningroom.less";
</style>
<template>
    <div>
        <h1>学习间预约</h1>
        <tab :tabList="tabList" :imgUrl="imgUrl">
            <div slot="aContent" class="lOrderTab">
                <div class="header">
                    <div class="time">
                        <span>选择日期：</span>
                        <DatePicker type="date" v-model="selectDate" show-week-numbers :editable="false" style="width: 110px" @on-change="dateChangeHandler"></DatePicker>
                        <span>选择时间：</span>
                        <Select :value="sTimes[0]" style="width: 80px" @on-change='handleStartTimeChange'>
                            <Option v-for="item in sTimes" :value="item" :key="item">{{ item }}</Option>
                        </Select>
                        -
                        <Select :value="eTimes[0]" style="width: 80px" @on-change='handleEndTimeChange'>
                            <Option v-for="item in eTimes" :value="item" :key="item">{{ item }}</Option>
                        </Select>
                    </div>
                </div>
                <div class="body">

                </div>
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
export default {
    components: {
        tab,
        picShow
    },
    data() {
        return {
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
            selectDate: new Date(),
            sTimes: [],
            eTimes: [],
            serverTime:"",
        };
    },
    computed: {
        isCurrentDay() {
            return this.selectDate.toDateString() === new Date().toDateString()
                ? true
                : false;
        },
        sTime() {
            return this.sTimes[0];
        },
        eTime() {
            return this.eTimes[0];
        }
    },

    methods: {
        dateChangeHandler() {
            if (this.selectDate == "") {
                this.selectDate = new Date();
            }
        },
        generateStartTimes() {
            // let sTime;
            // console.log(this.serverTime);
            // if (this.isCurrentDay) {
            //     let hour = Number(this.serverTime.split(":")[0]);
            //     let minute =
            //         (parseInt(this.serverTime.split(":")[1] / 5) + 1) * 5;

            //     if (minute === 60) {
            //         hour += 1;
            //         minute = 0;
            //     }
            //     if (hour < 10) {
            //         hour = "0" + hour;
            //     }
            //     if (minute < 10) {
            //         minute = "0" + minute;
            //     }
            //     sTime = hour + ":" + minute;
            // } else {
            //     sTime = "08:00";
            // }
            // console.log(sTime);
            // if (Number(sTime.split(":")[0]) < 21) {
            //     this.sTimes.push(sTime);
            //     let currentHour = Number(sTime.split(":")[0]);
            //     let currentMin = Number(sTime.split(":")[1]);
            //     let hourStr, minStr, timeOption;
                // while (currentHour < 21) {
                //     currentMin += 5;
                //     if (currentMin === 60) {
                //         currentHour += 1;
                //         currentMin = 0;
                //     }
                //     hourStr =
                //         currentHour > 9
                //             ? currentHour.toString()
                //             : "0" + currentHour.toString();
                //     minStr =
                //         currentMin > 9
                //             ? currentMin.toString()
                //             : "0" + currentMin.toString();
                //     timeOption = hourStr + ":" + minStr;
                //     this.sTimes.push(timeOption);
                // }
            //     this.sTimes.push("21:00");
            // }
        },
        generateEndTimes() {
            // this.endTime = [];
            // let sSplit = this.timeObject.selectTime[0].sTime.split(":");
            // let sRange = [];
            // sRange[0] = parseInt(sSplit[0]) + 1; //至少预约一个小时
            // sRange[1] = parseInt(sSplit[1]);
            // let closeTime = this.close_time.split(":");
            // if (this.compareTime(sRange, closeTime)) {
            //     //超过关闭时间
            //     this.timeWarning();
            //     return;
            // }
            // closeTime.map(item => parseInt(item));
            // let eRange = [sRange[0] + 3, sRange[1]]; //至多预约长达4小时
            // if (
            //     eRange[0] > closeTime[0] ||
            //     (eRange[0] == closeTime[0] && eRange[1] > closeTime[1])
            // ) {
            //     eRange = [closeTime[0], closeTime[1]];
            // }
            // let sCopy = [sRange[0], sRange[1]];
            // while (
            //     sCopy[0] < eRange[0] ||
            //     (sCopy[0] == eRange[0] && sCopy[1] <= eRange[1])
            // ) {
            //     this.endTime.push(
            //         this.timeFormat(sCopy[0]) + ":" + this.timeFormat(sCopy[1])
            //     );
            //     sCopy[1] += 5;
            //     if (sCopy[1] >= 60) {
            //         sCopy[0]++;
            //         sCopy[1] -= 60;
            //     }
            // }
            // for (let item of this.timeObject.occupyTime) {
            //     let sTime = item.sTime;
            //     let sNode = sTime.split(":");
            //     if (
            //         !this.compareTime(sSplit, sNode) &&
            //         this.compareTime(sRange, sNode)
            //     ) {
            //         //无法满足至少预约一小时的要求
            //         this.endTime = [];
            //         this.timeWarning();
            //         return;
            //     }
            //     let sIndex = this.endTime.indexOf(sTime);
            //     if (sIndex != -1) {
            //         //4小时时间段部分被他人占用，丢弃被他人占用时间以及后续时间
            //         this.endTime.splice(
            //             sIndex + 1,
            //             this.endTime.length - sIndex
            //         );
            //     }
            // }
            // return this.endTime[0];
        },
        handleStartTimeChange() {},
        handleEndTimeChange() {}
    },
    created() {
        let that = this;
        that.$ajax
            .get("http://iyou.s1.natapp.cc/lidsLibrary/info/serverTime")
            .then(function(response) {
                let hms = response.data.result.split(":");
                that.serverTime = hms[0] + ":" + hms[1];
                console.log(that.serverTime);
                let sTime;
            console.log(that.serverTime);
            if (that.isCurrentDay) {
                let hour = Number(that.serverTime.split(":")[0]);
                let minute =
                    (parseInt(that.serverTime.split(":")[1] / 5) + 1) * 5;

                if (minute === 60) {
                    hour += 1;
                    minute = 0;
                }
                if (hour < 10) {
                    hour = "0" + hour;
                }
                if (minute < 10) {
                    minute = "0" + minute;
                }
                sTime = hour + ":" + minute;
            } else {
                sTime = "08:00";
            }
            console.log(sTime);
            if (Number(sTime.split(":")[0]) < 21) {
                that.sTimes.push(sTime);
                let currentHour = Number(sTime.split(":")[0]);
                let currentMin = Number(sTime.split(":")[1]);
                let hourStr, minStr, timeOption;
                while (currentHour < 21) {
                    currentMin += 5;
                    if (currentMin === 60) {
                        currentHour += 1;
                        currentMin = 0;
                    }
                    hourStr =
                        currentHour > 9
                            ? currentHour.toString()
                            : "0" + currentHour.toString();
                    minStr =
                        currentMin > 9
                            ? currentMin.toString()
                            : "0" + currentMin.toString();
                    timeOption = hourStr + ":" + minStr;
                    that.sTimes.push(timeOption);
                }
                that.sTimes.push("21:00");
            }
            })
            .catch(function(err) {
                console.log(err);
            });
    },
};
</script>