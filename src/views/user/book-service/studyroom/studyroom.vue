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
                        <Button class="last-week disable" type="default" icon="arrow-left-b" @click="lastWeek"></Button>
                        <Button v-for="(item,index) in week" class="week-day" type="default" @click="getOrderInfo">
                            <span>{{item.month}}月{{item.day}}日</span>
                            </br>
                            <span>星期{{item.weekDay}}</span>
                        </Button>
                        <Button class="next-week" type="default" icon="arrow-right-b" @click="nextWeek"></Button>
                    </div>
                    <table class="orderTable" @mouseenter="tbMouseenter" @mousemove="tbMousemove" @mouseleave="tbMouseleave">
                        <tr class="orderTr" v-for="(room,roomIndex) in rooms" :key="roomIndex" @mousedown="mousedownHandler" @mouseup="mouseupHandler" @mousemove="mousemoveHandler" @mouseenter="mouseenterHandler" @mouseleave="mouseleaveHandler" @mouseclick="mouseclickHandler">
                            <th unselectable="on" onselectstart="return false;" style="-moz-user-select:none;">{{rooms[roomIndex].name}}</th>
                            <td unselectable="on" onselectstart="return false;" style="-moz-user-select:none;" v-for="(hour,hourIndex) in hours">
                                <div>{{hours[hourIndex]}}</div>
                            </td>
                        </tr>
                    </table>
                </div>
                <Button v-if="false" type="primary" @click="handleSelect">click me</Button>
                <bookModal v-if="showBookModal" :key="modalState" :bookDate="currentTime" :openTime="openTime" :closeTime="closeTime" :bookUser="username" :spaceName="selectRoom" :spaceId="selectRoomId" :occupyTime="occupations" :selectTime="selectTime"></bookModal>
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
            tabList: [
                { label: "预约状态", state: "aContent" },
                { label: "预约须知", state: "bContent" },
                { label: "相册展示", state: "cContent" },
                { label: "硬件配置", state: "dContent" }
            ],
            imgUrl: "./static/images/user/common/tab/example.png",
            imageList: [
                {
                    title: "pic1",
                    url: "./static/images/user/common/picShow/pic1.jpg"
                },
                {
                    title: "pic2",
                    url: "./static/images/user/common/picShow/pic2.jpg"
                },
                {
                    title: "pic3",
                    url: "./static/images/user/common/picShow/pic3.jpg"
                },
                {
                    title: "pic4",
                    url: "./static/images/user/common/picShow/pic4.jpg"
                },
                {
                    title: "pic5",
                    url: "./static/images/user/common/picShow/pic5.jpg"
                },
                {
                    title: "pic6",
                    url: "./static/images/user/common/picShow/pic6.jpg"
                },
                {
                    title: "pic7",
                    url: "./static/images/user/common/picShow/pic7.jpg"
                },
                {
                    title: "pic8",
                    url: "./static/images/user/common/picShow/pic8.jpg"
                }
            ],
            showBookModal: false,
            modalState: false,
            title: "welcome",
            hours: [
                "7:00",
                "8:00",
                "9:00",
                "10:00",
                "11:00",
                "12:00",
                "13:00",
                "14:00",
                "15:00",
                "16:00",
                "17:00",
                "18:00",
                "19:00",
                "20:00",
                "21:00"
            ], //所有研习间当天的开放时间，需要从后台获取（created时先请求当天）
            rooms: [],
            time: this.getCurrentTime().time, //用于头部时间展示（今天）
            date: this.getCurrentTime().date - 28800000, //当前周一时间，点击上/下周时更改
            dayTime: 86400000,
            draging: false,
            keepClick: false,
            tdWidth: 0,
            unitWidth: 0,
            startPosX: 0,
            endPosX: 0,
            posY: 0,
            selectTr: -1,
            REM: -1,
            tableLeft: -1,
            tableTop: -1,
            serverTime: "",
            validTime: "",
            offsetY: 0,
            clientY: 0,

            /*---------------------------需要传递给预定信息确认模态窗的数据-------------------------------*/
            currentTime: 0, //选中日期的时间戳
            startTime: "",
            endTime: ""
        };
    },
    computed: {
        //从store读取用户名
        username() {
            return this.$store.state.username;
        },
        //从store读取目前登录框显示状态
        loginShow() {
            return this.$store.state.loginShow;
        },
        //选中研习间的id
        selectRoomId() {
            if (this.selectTr === -1) {
                return "";
            } else {
                return this.rooms[this.selectTr].id.toString();
            }
        },
        //选中研习间的名称
        selectRoom() {
            if (this.selectTr === -1) {
                return "";
            } else {
                return this.rooms[this.selectTr].name;
            }
        },
        //选中研习间的开放时间
        openTime() {
            return this.rooms[this.selectTr].openTime + ":00";
        },
        closeTime() {
            return this.rooms[this.selectTr].closeTime + ":00";
        },
        //所选时间
        selectTime() {
            return {
                sTime: this.startTime,
                eTime: this.endTime
            };
        },
        //所选研习间被占用时间
        occupations() {
            let result = [];
            let index = -1;
            for (let i = 0; i < this.rooms.length; i++) {
                if (this.rooms[i].id === Number(this.selectRoomId)) {
                    index = i;
                }
            }
            let occupations = this.rooms[index].occupations;
            let singleOccupy = {};
            for (let i = 0; i < occupations.length; i++) {
                singleOccupy = {
                    sTime: occupations[i].sTime,
                    eTime: occupations[i].eTime
                };
                result.push(singleOccupy);
            }
            return result;
        },
        rootSize() {
            return this.$store.state.rootSize;
        },
        //获取一周的日期
        week() {
            let results = [];
            let weekDays = ["日", "一", "二", "三", "四", "五", "六"];
            for (let i = 0; i < 7; i++) {
                let nowDate = new Date(this.date + i * this.dayTime);
                let month = nowDate.getMonth() + 1;
                let day = nowDate.getDate();
                let weekDay = weekDays[nowDate.getDay()];
                let result = {
                    date: nowDate,
                    month: month,
                    day: day,
                    weekDay: weekDay
                };

                results.push(result);
            }
            return results;
        },

        //获取当前日期的字符串
        currntTimeStr() {
            let selectDate = new Date(this.date);
            let { year, month, day } = getYMD(selectDate);
            return year + "-" + month + "-" + day;
        }
    },
    methods: {
        //获取当前日期
        getCurrentTime() {
            let date = new Date(),
                zeroTime;
            let { year, month, day } = getYMD(date);

            //生成当前时间的字符串，并以0点初始化新的Date对象
            zeroTime = year + "-" + month + "-" + day;
            return {
                time: year + "." + month + "." + day,
                date: new Date(zeroTime).getTime()
            };
        },

        //获取当天所有研习间预定信息
        getOrderInfo(e) {
            let nowBtn = e.currentTarget;
            let offset = 0;
            let btnCon = nowBtn.parentNode;
            let btns = btnCon.children;
            for (let i = 1; i < 8; i++) {
                if (btns[i] === nowBtn) {
                    offset = i - 1;
                }
                btns[i].classList.remove("click");
            }
            nowBtn.classList.add("click");
            this.getOrderInfoAjax.call(this, offset);
        },
        //获取上一周的日期
        lastWeek(e) {
            let nowDate = new Date().getTime();
            let nextBtn = document.getElementsByClassName("next-week")[0];
            if (this.date > nowDate + 6 * this.dayTime) {
                this.date -= 7 * this.dayTime;
                nextBtn.classList.remove("disable");
                let btnCon = e.currentTarget.parentNode;
                let btns = btnCon.children;
                for (let i = 1; i < 8; i++) {
                    btns[i].classList.remove("click");
                }
                btns[1].classList.add("click");
                this.getOrderInfoAjax.call(this, 0);
            }
            if (this.date < nowDate + 6 * this.dayTime) {
                let btn = e.currentTarget;
                btn.classList.add("disable");
            }
        },
        //获取下一周的日期
        nextWeek(e) {
            let nowDate = new Date().getTime();
            let lastBtn = document.getElementsByClassName("last-week")[0];
            if (this.date < nowDate + 14 * this.dayTime) {
                this.date += 7 * this.dayTime;
                lastBtn.classList.remove("disable");
                let btnCon = e.currentTarget.parentNode;
                let btns = btnCon.children;
                for (let i = 1; i < 8; i++) {
                    btns[i].classList.remove("click");
                }
                btns[1].classList.add("click");
                this.getOrderInfoAjax.call(this, 0);
            }
            if (this.date > nowDate + 14 * this.dayTime) {
                let btn = e.currentTarget;
                btn.classList.add("disable");
            }
        },
        //移入表格创建tip提示框
        tbMouseenter(event) {
            if (document.getElementsByClassName("time-tip").length === 0) {
                let tip = document.createElement("div");
                tip.className = "time-tip";
                document.body.appendChild(tip);
            }
        },

        //跟随鼠标显示tip提示框
        tbMousemove(event) {
            let e = event || window.event;
            let tagName = e.target.tagName.toLowerCase();
            let tip = document.getElementsByClassName("time-tip")[0];
            if (
                tagName !== "th" &&
                tagName !== "tr" &&
                tagName !== "table" &&
                !e.target.classList.contains("selected-box")
            ) {
                tip.style.zIndex = 1000;
                let currentTd =
                    tagName === "td" ? e.target : e.target.parentNode;
                this.tdWidth = currentTd.offsetWidth;
                this.unitWidth = this.tdWidth / 12;
                if (!currentTd.classList.contains("past")) {
                    tip.style.left = e.clientX + "px";
                    if (this.draging) {
                        tip.style.top = this.clientY - this.offsetY - 20 + "px";
                    } else {
                        tip.style.top = e.clientY - e.offsetY - 20 + "px";
                        let hour = currentTd.innerText.split(":")[0];
                        let ratio = parseInt(e.offsetX / this.unitWidth);
                        let minute =
                            ratio * 5 > 9 ? ratio * 5 : "0" + ratio * 5;
                        if (
                            this.currentTime < new Date().getTime() &&
                            hour === this.serverTime.split(":")[0] &&
                            minute <= Number(this.serverTime.split(":")[1])
                        ) {
                            tip.innerText = "需晚于" + this.validTime;
                        } else {
                            let hour = currentTd.innerText.split(":")[0];
                            let ratio = parseInt(e.offsetX / this.unitWidth);
                            let minute =
                                ratio * 5 > 9 ? ratio * 5 : "0" + ratio * 5;
                            tip.innerText = hour + ":" + minute;
                        }
                    }
                } else {
                    tip.style.zIndex = 0;
                }
            }
        },

        //移出表格删除tip提示框
        tbMouseleave(event) {
            let tips = document.getElementsByClassName("time-tip");
            if (tips.length !== 0) {
                let tip = tips[0];
                document.body.removeChild(tip);
            }
            this.keepClick = false;
            let dragBoxs = document.getElementsByClassName("drag-box");
            let table = document.getElementsByClassName("orderTable")[0];
            if (dragBoxs.length !== 0) {
                let dragBox = dragBoxs[0];
                table.removeChild(dragBox);
            }
        },

        //按下鼠标键，计算预定起始时间
        mousedownHandler(event) {
            let e = event || window.event;
            let table = document.getElementsByClassName("orderTable")[0];
            this.tableLeft = table.getBoundingClientRect().left;
            this.tableTop = table.getBoundingClientRect().top; //获取表格相对于body的位置（单位为px)
            this.tableWidth = table.offsetWidth;
            let dragBox = document.getElementById("drag-box");
            if (dragBox) {
                table.removeChild(dragBox);
            }
            let tagName = e.target.tagName.toLowerCase();
            if (tagName !== "th") {
                this.REM = Number(
                    document.documentElement.style.fontSize.split("p")[0]
                );
                let currentTr = e.currentTarget;
                let startTd = tagName === "td" ? e.target : e.target.parentNode;
                this.tdWidth = startTd.offsetWidth;
                this.unitWidth = this.tdWidth / 12;
                if (!startTd.classList.contains("past")) {
                    let ratio = parseInt(e.offsetX / this.unitWidth);
                    let hour = startTd.innerText.split(":")[0];
                    let minute = ratio * 5 > 9 ? ratio * 5 : "0" + ratio * 5;
                    if (
                        hour == this.validTime.split(":")[0] &&
                        minute < this.validTime.split(":")[1]
                    ) {
                    } else {
                        this.keepClick = true; //处于点击状态
                        this.draging = true; //处于拖动状态
                        this.selectTr = currentTr.rowIndex; //保存所选行id
                        this.startPosX = e.clientX - this.tableLeft; //保存距离表格左端的rem数起始坐标
                        this.endPosX = e.clientX - this.tableLeft;
                        this.posY =
                            (e.clientY - e.offsetY - this.tableTop - 2) /
                            this.REM;
                        this.offsetY = e.offsetY;
                        this.clientY = e.clientY; // 用于时间提示窗口的定位
                        this.startTime = hour + ":" + minute;
                        console.log("研习间名称:" + this.selectRoom);
                        console.log("研习间id:" + this.selectRoomId);
                        console.log("起始时间:" + this.startTime);
                    }
                }
            }
        },

        //拖动选择时间，绘制填充
        mousemoveHandler(event) {
            let e = event || window.event;
            if (this.draging && this.keepClick) {
                let tagName = e.target.tagName.toLowerCase();
                if (tagName !== "th") {
                    let currentTd =
                        tagName === "td" ? e.target : e.target.parentNode;
                    if (!currentTd.classList.contains("past")) {
                        this.endPosX = e.clientX - this.tableLeft;
                        if (this.endPosX > this.startPosX) {
                            if (
                                !document.getElementsByClassName("drag-box")
                                    .length
                            ) {
                                let dragBox = this.paintDragBox(
                                    this.startPosX,
                                    this.posY
                                );
                                let that = this;
                                let table = document.getElementsByTagName(
                                    "table"
                                )[0];
                                let tr = table.getElementsByTagName("tr")[
                                    this.selectTr
                                ];
                                dragBox.addEventListener("mouseenter", function(
                                    e
                                ) {
                                    tr.classList.add("active");
                                    that.draging = true;
                                });
                                dragBox.addEventListener("mouseleave", function(
                                    e
                                ) {
                                    tr.classList.remove("active");
                                });
                                dragBox.addEventListener("mousemove", function(
                                    e
                                ) {
                                    if (that.draging && that.keepClick) {
                                        let tip = document.getElementsByClassName(
                                            "time-tip"
                                        )[0];
                                        tip.style.left = e.clientX + "px";
                                        that.endPosX =
                                            e.clientX - that.tableLeft + 1.5;
                                        let offset =
                                            that.endPosX - that.startPosX;
                                        this.style.width =
                                            offset / that.tableWidth * 100 +
                                            "%";
                                        console.log("move");
                                        let strs = that.startTime.split(":");
                                        let sMins =
                                            Number(strs[0]) * 60 +
                                            Number(strs[1]);
                                        let offsetMins =
                                            offset / that.unitWidth * 5;
                                        let eMins = sMins + offsetMins + 5;
                                        let hour = parseInt(eMins / 60);
                                        let min =
                                            parseInt((eMins - hour * 60) / 5) *
                                            5;
                                        if (min < 10) {
                                            min = "0" + min;
                                        }
                                        if (min === 60) {
                                            hour = hour + 1;
                                            min = "00";
                                        }
                                        that.endTime = hour + ":" + min;
                                        let eTimeStr = document.getElementsByClassName(
                                            "eTimeStr"
                                        )[0];
                                        eTimeStr.innerText = that.endTime;

                                        if (offsetMins < 60) {
                                            tip.innerText = "至少1小时";
                                        } else {
                                            tip.innerText = that.endTime;
                                        }
                                    }
                                });
                                dragBox.addEventListener("mouseup", function(
                                    e
                                ) {
                                    tr.classList.remove("active");
                                    let tips = document.getElementsByClassName(
                                        "time-tip"
                                    );
                                    tips[0].style.zIndex = 0;

                                    that.keepClick = false;
                                    that.draging = false;
                                    //当所选时间小于1小时时，自动取消所选区域
                                    let currentBox = document.getElementById(
                                        "drag-box"
                                    );
                                    document
                                        .getElementsByClassName("orderTable")[0]
                                        .removeChild(currentBox);
                                    console.log("结束时间:" + that.endTime);
                                    if (
                                        that.endPosX - that.startPosX >
                                        that.tdWidth
                                    ) {
                                        //用局部变量暂存然后初始化所有data，并清除所选区域
                                        if (!that.username) {
                                            console.log("未登录，请先登录");
                                            that.$emit("needLogin");
                                            setTimeout(that.loginState, 5000);
                                        } else {
                                            console.log(
                                                "已登录，请确认预定信息"
                                            );
                                            //弹出预约信息模态窗
                                            that.showBookModal = true;
                                            that.modalState = !this.modalState;
                                        }
                                    }
                                });
                                dragBox.addEventListener("mouseleave", function(
                                    e
                                ) {
                                    tr.classList.remove("active");
                                    that.draging = false;
                                });
                                document
                                    .getElementsByClassName("orderTable")[0]
                                    .appendChild(dragBox);
                            }
                            let sTimeStr = document.getElementsByClassName(
                                "sTimeStr"
                            )[0];
                            sTimeStr.innerText = this.startTime;
                            let eTimeStr = document.getElementsByClassName(
                                "eTimeStr"
                            )[0];
                            eTimeStr.innerText = this.endTime;

                            let dragBox = document.getElementById("drag-box");
                            let offset = this.endPosX - this.startPosX;
                            let totalHours = offset / this.tdWidth;
                            let tip = document.getElementsByClassName(
                                "time-tip"
                            )[0];

                            if (totalHours <= 4) {
                                dragBox.style.width =
                                    offset / this.tableWidth * 100 + "%";
                                let tagName = e.target.tagName.toLowerCase();
                                let currentTd =
                                    tagName === "td"
                                        ? e.target
                                        : e.target.parentNode;
                                let hour = currentTd.innerText.split(":")[0];
                                let ratio = parseInt(
                                    e.offsetX / this.unitWidth
                                );
                                let minute =
                                    ratio * 5 > 9 ? ratio * 5 : "0" + ratio * 5;
                                this.endTime = hour + ":" + minute;
                                if (totalHours < 1) {
                                    tip.innerText = "至少1小时";
                                } else {
                                    tip.innerText = this.endTime;
                                }
                            } else {
                                this.endTime =
                                    (
                                        Number(this.startTime.split(":")[0]) + 4
                                    ).toString() +
                                    ":" +
                                    this.startTime.split(":")[1];
                                tip.innerText = "最多4小时";
                                dragBox.style.width = "23.2%";
                            }
                        }
                    }
                }
            }
        },

        //鼠标移入所选行
        mouseenterHandler(event) {
            let e = event || window.event;
            let tr = e.currentTarget;
            if (tr.rowIndex === this.selectTr && this.keepClick) {
                this.draging = true;
            }
        },
        //鼠标移出所选行
        mouseleaveHandler(event) {
            let e = event || window.event;
            let tr = e.currentTarget;
            if (tr.rowIndex === this.selectTr && this.keepClick) {
                this.draging = false;
            }
        },

        //松开鼠标键，计算结束时间,判断是否登录
        mouseupHandler(event) {
            // console.log(this.endTime);
            let tips = document.getElementsByClassName("time-tip");
            tips[0].style.zIndex = 0;
            this.keepClick = false;
            this.draging = false;

            let dragBox = document.getElementById("drag-box");
            if (dragBox) {
                console.log(this.occupations);
                console.log("结束时间:" + this.endTime);
                //当所选时间小于1小时时，自动取消所选区域
                if (this.endPosX - this.startPosX > 12 * this.unitWidth) {
                    document
                        .getElementsByClassName("orderTable")[0]
                        .removeChild(dragBox);
                    //用局部变量暂存然后初始化所有data，并清除所选区域
                    if (!this.username) {
                        console.log("未登录，请先登录");
                        this.$emit("needLogin");
                        setTimeout(this.loginState, 5000);
                    } else {
                        console.log("已登录，请确认预定信息");
                        //弹出预约信息模态窗
                        this.showBookModal = true;
                        this.modalState = !this.modalState;
                    }
                } else {
                    document
                        .getElementsByClassName("orderTable")[0]
                        .removeChild(dragBox);
                }
            }
        },
        mouseclickHandler() {
            let dragBox = document.getElementById("drag-box");
            document.body.removeChild(dragBox);
        },

        //选择时间后循环询问是否已经登录
        loginState() {
            if (this.username) {
                this.showBookModal = true;
                this.modalState = !this.modalState;
                console.log("已登录，弹出预约信息确认窗口");
                return;
            } else {
                console.log("还未登录，继续等待");
                let that = this;
                if (that.loginShow) {
                    setTimeout(that.loginState, 1000);
                }
            }
        },

        //获取所选日期研习间预定信息的请求(根据按钮的偏移量)
        getOrderInfoAjax(offset) {
            let that = this;
            let nowDate = new Date(
                new Date(that.currntTimeStr).getTime() + offset * this.dayTime
            );
            let timeStr;
            let { year, month, day } = getYMD(nowDate);
            timeStr = year + "-" + month + "-" + day;
            this.currentTime = new Date(timeStr).getTime();
            console.log("所选日期的时间戳:" + this.currentTime); //当前选中天的时间戳
            that.$ajax
                .get(
                    "http://iyou.s1.natapp.cc/lidsLibrary/space/roomInfo?date=" +
                        timeStr
                )
                .then(function(response) {
                    let data = response.data.result;
                    that.serverTime = data.serverTime[0];
                    console.log(that.serverTime);
                    let serverTime = Number(data.serverTime[0].split(":")[0]);
                    let minutes =
                        (parseInt(data.serverTime[0].split(":")[1] / 5) + 1) *
                        5;

                    if (minutes === 60) {
                        serverTime = serverTime + 1;
                        minutes = 0;
                    }
                    if (minutes < 10) {
                        minutes = "0" + minutes;
                    }
                    that.validTime = serverTime + ":" + minutes;
                    let pastTdIndex = 15;
                    for (let i = 0; i < 15; i++) {
                        if (
                            that.hours[i].split(":")[0] ===
                            serverTime.toString()
                        ) {
                            pastTdIndex = i;
                            break;
                        }
                    }

                    //禁止选择当天当前时间之前的时间
                    let trs = document.getElementsByTagName("tr");
                    let length = trs.length;
                    for (let i = 0; i < length; i++) {
                        let tds = trs[i].getElementsByTagName("td");
                        for (let j = 0; j < tds.length; j++) {
                            tds[j].classList.remove("past");
                        }
                    }
                    if (new Date(timeStr).getTime() < new Date().getTime()) {
                        that.togglePast("add", trs, length, pastTdIndex);
                    } else {
                        that.togglePast("remove", trs, length, pastTdIndex);
                    }

                    //获取每间研习间的开放时间
                    let schedules = response.data.result.roomScheduler;
                    for (let i = 0; i < schedules.length; i++) {
                        that.rooms[i].openTime = schedules[i].begin_time.split(
                            ":"
                        )[0];
                        that.rooms[i].closeTime = schedules[i].end_time.split(
                            ":"
                        )[0];
                    }
                    for (let i = 0; i < that.rooms.length; i++) {
                        let sIndex = that.rooms[i].openTime - 7;
                        let eIndex = that.rooms[i].closeTime - 7;
                        let tds = trs[i].getElementsByTagName("td");
                        for (let j = 0; j < tds.length; j++) {
                            if (j < sIndex || j >= eIndex) {
                                tds[j].classList.add("past");
                            }
                        }
                    }

                    //存储每间研习间已预约时间段
                    let orders = data.roomBooking;
                    let roomNum = that.rooms.length;
                    let order = {};
                    let roomIndex, sTime, eTime, username, topic;
                    for (let j = 0; j < roomNum; j++) {
                        that.rooms[j].occupations = [];
                    }
                    for (let i = 0; i < orders.length; i++) {
                        for (let j = 0; j < roomNum; j++) {
                            if (that.rooms[j].id == orders[i].spaceId) {
                                roomIndex = j;
                            }
                        }
                        sTime = orders[i].beginTime;
                        eTime = orders[i].endTime;
                        username = orders[i].name;
                        topic = orders[i].application;
                        order = {
                            sTime: sTime,
                            eTime: eTime,
                            username: username,
                            topic: topic
                        };

                        that.rooms[roomIndex].occupations.push(order);
                    }

                    let sBoxes = document.getElementsByClassName(
                        "selected-box"
                    );
                    let orderTable = document.getElementsByClassName(
                        "orderTable"
                    )[0];
                    while (sBoxes.length > 0) {
                        orderTable.removeChild(sBoxes[0]);
                    }

                    //绘制每间研习间已预约时间
                    let occNum,
                        sTimeArr,
                        eTimeArr,
                        sMin,
                        eMin,
                        offsetMin,
                        sRatio,
                        totalRatio,
                        occName,
                        occTopic,
                        selectedBox;
                    for (let i = 0; i < roomNum; i++) {
                        if (that.rooms[i].occupations) {
                            let occs = that.rooms[i].occupations;
                            occNum = occs.length;
                            for (let j = 0; j < occNum; j++) {
                                sTimeArr = occs[j].sTime.split(":");
                                eTimeArr = occs[j].eTime.split(":");
                                sMin =
                                    Number(sTimeArr[0]) * 60 +
                                    Number(sTimeArr[1]);
                                eMin =
                                    Number(eTimeArr[0]) * 60 +
                                    Number(eTimeArr[1]);
                                offsetMin = eMin - sMin;
                                sRatio = (sMin - 420) / 60 * 5.8 + i*0.06 + 12;
                                totalRatio = offsetMin / 60 * 5.8;
                                occName = occs[j].username;
                                occTopic = occs[j].topic;
                                selectedBox = that.paintSelectedBox(
                                    i,
                                    sRatio,
                                    totalRatio,
                                    occName,
                                    occTopic
                                );
                                orderTable.appendChild(selectedBox);
                            }
                        }
                    }
                    console.log(that.rooms);
                })
                .catch(function(err) {
                    console.log(err);
                });
        },
        //绘制已选择的时间段的矩形
        paintSelectedBox(i, sRatio, totalRatio, occName, occTopic) {
            let selectedBox = document.createElement("div");
            selectedBox.className = "selected-box";
            selectedBox.style.position = "absolute";
            selectedBox.style.top = i * 0.42 + "rem";
            selectedBox.style.left = sRatio + "%";
            selectedBox.style.height = "0.39rem";
            selectedBox.style.width = totalRatio + "%";
            selectedBox.innerText = occName + "：" + occTopic;
            let table = document.getElementsByTagName("table")[0];
            let tr = table.getElementsByTagName("tr")[i];
            selectedBox.addEventListener("mouseenter", function(event) {
                tr.classList.add("active");
            });
            selectedBox.addEventListener("mouseleave", function(event) {
                tr.classList.remove("active");
            });
            return selectedBox;
        },
        //绘制正在选择的时间段的矩形
        paintDragBox(x, y) {
            let dragBox = document.createElement("div");
            dragBox.id = "drag-box";
            dragBox.className = "drag-box";
            dragBox.style.position = "absolute";
            dragBox.style.top = y + "rem";
            dragBox.style.left = x / this.tableWidth * 100 + "%";
            dragBox.style.height = "0.42rem";
            let sTimeStr = document.createElement("span");
            sTimeStr.className = "sTimeStr";
            dragBox.appendChild(sTimeStr);
            let br = document.createElement("br");
            dragBox.appendChild(br);
            let eTimeStr = document.createElement("span");
            eTimeStr.className = "eTimeStr";
            dragBox.appendChild(eTimeStr);
            return dragBox;
        },

        //添加/删除单元格"past"类
        togglePast(operation, trs, length, pastTdIndex) {
            for (let i = 0; i < length; i++) {
                let tds = trs[i].getElementsByTagName("td");
                let tdNum = tds.length;
                for (let j = 0; j < pastTdIndex; j++) {
                    let fun = "tds[j].classList." + operation + "('past')";
                    eval(fun);
                }
            }
        },
        //请求研习间的基础数据
        getRoomsInfo() {
            let that = this;
            that.$ajax
                .get("http://iyou.s1.natapp.cc/lidsLibrary/space/roomMap")
                .then(function(response) {
                    let resInfo = response.data;
                    let data = resInfo.result;
                    let length = data.length;
                    for (let i = 0; i < length; i++) {
                        that.rooms.push(data[i]);
                    }
                    that.getOrderInfoAjax.call(this, 0);
                })
                .catch(function(err) {
                    console.log(err);
                });
        }
    },
    //页面加载时请求研习间数据
    created() {
        this.getRoomsInfo();
    },
    mounted() {
        let firstDayBtn = document.getElementsByClassName("week-day")[0];
        firstDayBtn.classList.add("click");
    }
};

/*---------------------------------------组件通用外部方法------------------------------------------*/

//获取传入日期的年、月、日
function getYMD(date) {
    let year, month, day;
    year = date.getFullYear().toString();
    month =
        date.getMonth() + 1 > 9
            ? (date.getMonth() + 1).toString()
            : "0" + (date.getMonth() + 1).toString();
    day =
        date.getDate() > 9
            ? date.getDate().toString()
            : "0" + date.getDate().toString();
    return {
        year: year,
        month: month,
        day: day
    };
}
</script>
