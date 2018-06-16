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
                        <DatePicker type="date" v-model="selectDate" :options="options" :editable="false" style="width: 110px" @on-change="dateChangeHandler"></DatePicker>
                        <span>选择时间：</span>
                        <Select v-model="sTime" style="width: 80px" @on-change='handleStartTimeChange'>
                            <Option v-for="item in sTimes" :value="item">{{ item }}</Option>
                        </Select>
                        -
                        <Select v-model="eTime" style="width: 80px">
                            <Option v-for="item in eTimes" :value="item">{{ item }}</Option>
                        </Select>
                        <Button @click="getSeatInfo">查询</Button>
                    </div>
                </div>
                <div class="body">
                    <div class="seat" id="1" @mouseover="showSeatInfo"></div>
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
            options: {
                disabledDate(date) {
                    return (
                        (date && date.valueOf() < Date.now() - 86400000) ||
                        (date &&
                            date.valueOf() >
                                new Date().getTime() + 13 * 86400000)
                    );
                }
            },
            selectDate: new Date(),
            sTime: "",
            eTime: "",
            sTimes: [],
            eTimes: [],
            serverTime: "",
            seatInfo: []
        };
    },
    computed: {
        isCurrentDay() {
            return this.selectDate.toDateString() === new Date().toDateString()
                ? true
                : false;
        }
    },

    methods: {
        dateChangeHandler() {
            if (this.selectDate == "") {
                this.selectDate = new Date();
            }
            this.sTime = this.eTime = "";
            this.sTimes = [];
            this.eTimes = [];
            this.generateStartTimes(
                this.isCurrentDay,
                this.sTimes,
                this.serverTime
            );
            this.sTime = this.sTimes[0];
        },

        handleStartTimeChange() {
            this.eTimes = [];
            this.generateEndTimes(this.sTime, this.eTimes);
            this.eTime = this.eTimes[0];
        },
        generateStartTimes(isCurrentDay, timeArr, serverTime) {
            let sTime;
            if (isCurrentDay) {
                let hour = Number(serverTime.split(":")[0]);
                let minute = (parseInt(serverTime.split(":")[1] / 5) + 1) * 5;

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
            if (Number(sTime.split(":")[0]) < 21) {
                timeArr.push(sTime);
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
                    timeArr.push(timeOption);
                }
            }
        },
        generateEndTimes(sTime, eTimeArr) {
            let eTime;
            let sHour = Number(sTime.split(":")[0]);
            let sMinute = parseInt(sTime.split(":")[1]);
            let eHour = sHour + 1;
            let eMinute = sMinute;
            let eHourStr, eMinStr, timeOption;
            if (sHour < 21) {
                while (eHour <= 22) {
                    eHourStr =
                        eHour > 9 ? eHour.toString() : "0" + eHour.toString();
                    eMinStr =
                        eMinute > 9
                            ? eMinute.toString()
                            : "0" + eMinute.toString();
                    timeOption = eHourStr + ":" + eMinStr;
                    eTimeArr.push(timeOption);
                    eMinute += 5;
                    if (eMinute === 60) {
                        eHour += 1;
                        eMinute = 0;
                    }
                    if (eHour == 22 && eMinute == 5) {
                        break;
                    }
                    if (eHour - sHour === 4 && eMinute - sMinute === 5) {
                        break;
                    }
                }
            } else if (sHour == 21 && sMinute == 0) {
                eTimeArr.push("22:00");
            }
        },
        getSeatInfo() {
            if (this.sTime && this.eTime) {
                let that = this;
                that.seatInfo = [];
                let date = that.selectDate;
                let formatDate =
                    date.getFullYear() +
                    "-" +
                    (date.getMonth() + 1) +
                    "-" +
                    date.getDate();
                that.$ajax
                    .get(
                        "http://iyou.s1.natapp.cc/lidsLibrary//space/seatStatusTime?Date=" +
                            formatDate +
                            "&beginTime=" +
                            that.sTime +
                            "&endTime=" +
                            that.eTime
                    )
                    .then(function(response) {
                        let result = response.data.result;
                        let lenght = result.lenght;
                        for (let i = 0; i < length; i++) {
                            that.seatInfo.push({
                                id: result[i].id,
                                isOpne: result[i].isOpen,
                                isOccupied: result[i].isOccupied
                            });
                        }
                        console.log(that.seatInfo);
                    })
                    .catch(function(err) {
                        console.log(err);
                    });
            }
        },
        showSeatInfo(event) {
            let e = event || window.event;
            let num = e.target.id - 1;
            let seat = this.seatInfo[num];
            if (seat.isOpen === 0) {
                console.log("不开放");
            } else if (seat.isOccupied === 1) {
                console.log("忙碌");
            } else {
                console.log("空闲");
            }
        },

    },
    created() {
        let that = this;
        that.$ajax
            .get("http://iyou.s1.natapp.cc/lidsLibrary/info/serverTime")
            .then(function(response) {
                let hms = response.data.result.split(":");
                that.serverTime = hms[0] + ":" + hms[1];

                that.generateStartTimes(
                    that.isCurrentDay,
                    that.sTimes,
                    that.serverTime
                );
                that.sTime = that.sTimes[0];
                that.generateEndTimes(that.sTime,that.eTimes);
                that.eTime = that.eTimes[0];
                that.getSeatInfo();
            })
            .catch(function(err) {
                console.log(err);
            });
    }
};
</script>