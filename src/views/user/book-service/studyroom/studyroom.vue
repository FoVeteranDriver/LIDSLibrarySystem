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
                    <table class="orderTable">
                        <tr v-for="(room,roomIndex) in rooms" :key="roomIndex" @mousedown="mousedownHandler" @mouseup="mouseupHandler" @mousemove="mousemoveHandler" @mouseenter="mouseenterHandler" @mouseleave="mouseleaveHandler">
                            <th unselectable="on" onselectstart="return false;" style="-moz-user-select:none;">{{rooms[roomIndex].name}}</th>
                            <td unselectable="on" onselectstart="return false;" style="-moz-user-select:none;" v-for="(hour,hourIndex) in hours">
                                <div>{{hours[hourIndex]}}</div>
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
            ///////////////////////////////////////////////////////////////////////////
            rooms: [
                {
                    name: "研修室1",
                    id: 1
                },
                {
                    name: "研修室2",
                    id: 2
                },
                {
                    name: "研修室3",
                    id: 3
                },
                {
                    name: "研修室4",
                    id: 4
                },
                {
                    name: "研修室5",
                    id: 5
                },
                {
                    name: "合作研修室1",
                    id: 6
                },
                {
                    name: "合作研修室2",
                    id: 7
                },
                {
                    name: "合作研修室3",
                    id: 8
                },
                {
                    name: "合作研修室4",
                    id: 9
                },
                {
                    name: "合作研修室5",
                    id: 10
                }
            ],
            //所有研习间名称及id，需要从后台获取（created时先请求当天）
            ///////////////////////////////////////////////////////////////////////////
            hours: [
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
            trHeight: 0,
            selectTr: -1,

            /*---------------------------需要传递给预定信息确认模态窗的数据-------------------------------*/
            currentTime: 0, //选中日期的时间戳
            startTime: "",
            endTime: "",
            // 还有用户名，在computed属性
        };
    },
    computed: {
        //从store读取用户名
        username() {
            return this.$store.state.username;
        },
        //选中研习间的id
        selectRoomId() {
            if (this.selectTr === -1) {
                return -1;
            } else {
                return this.rooms[this.selectTr].id;
            }
        },
        //选中研习间的名称
        selectRoom(){
            if (this.selectTr === -1) {
                return "";
            } else {
                return this.rooms[this.selectTr].name;
            }
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

        //弹出预约信息模态窗
        handleSelect() {
            this.showBookModal = true;
            this.modalState = !this.modalState;
        },

        //按下鼠标键，计算预定起始时间
        mousedownHandler(event) {
            let dragBox = document.getElementById("drag-box");
            if (dragBox) {
                document.body.removeChild(dragBox);
            }
            let e = event || window.event;
            let tagName = e.target.tagName.toLowerCase();
            if (tagName !== "th") {
                let currentTr = e.currentTarget;
                let startTd = tagName === "td" ? e.target : e.target.parentNode;
                let x = e.clientX;
                this.trHeight = startTd.offsetHeight;
                this.tdWidth = startTd.offsetWidth;
                this.unitWidth = this.tdWidth / 12; //保存单位长度
                this.keepClick = true;
                this.draging = true; //处于拖动状态
                this.selectTr = currentTr.rowIndex; //保存所选行id
                this.startPosX = e.clientX; //保存起始坐标
                this.posY = e.clientY - e.offsetY;
                let hour = startTd.innerText.split(":")[0];
                let ratio = parseInt(e.offsetX / this.unitWidth);
                let minute = ratio * 5 > 9 ? ratio * 5 : "0" + ratio * 5;
                let startTime = hour + ":" + minute;
                let dragBox = paintDragBox(
                    this.startPosX,
                    this.posY,
                    this.trHeight
                );
                let that = this;
                let table = document.getElementsByTagName("table")[0];
                let tr = table.getElementsByTagName("tr")[this.selectTr];
                dragBox.addEventListener("mouseenter", function(e) {
                    tr.classList.add("active");
                    that.draging = true;
                });
                dragBox.addEventListener("mousemove", function(e) {
                    if (that.draging && that.keepClick) {
                        that.endPosX = e.clientX;
                        this.style.width = that.endPosX - that.startPosX + "px";
                    }
                });
                dragBox.addEventListener("mouseleave", function(e) {
                    tr.classList.remove("active");
                    that.draging = false;
                });
                document.body.appendChild(dragBox);
                console.log(this.selectRoom);
                console.log(this.selectRoomId);
                // console.log("起始时间:" + startTime);
            }
        },

        //拖动选择时间，绘制填充
        mousemoveHandler(event) {
            let e = event || window.event;
            if (this.draging && this.keepClick) {
                this.endPosX = e.clientX;
                let dragBox = document.getElementById("drag-box");
                if (this.endPosX > this.startPosX) {
                    let offset = this.endPosX - this.startPosX;
                    let totalHours = offset / this.tdWidth;
                    if (totalHours <= 4) {
                        dragBox.style.width = offset + "px";
                        let tagName = e.target.tagName.toLowerCase();
                        let currentTd =
                            tagName === "td" ? e.target : e.target.parentNode;
                        let hour = currentTd.innerText.split(":")[0];
                        let ratio = parseInt(e.offsetX / this.unitWidth);
                        let minute =
                            ratio * 5 > 9 ? ratio * 5 : "0" + ratio * 5;
                        let currentTime = hour + ":" + minute;
                        // console.log("当前时间:" + currentTime);
                    } else {
                        dragBox.style.width = 4 * this.tdWidth;
                    }
                }
            }
        },

        //鼠标移入所选行或移入选择区域div
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
            this.keepClick = false;
            this.draging = false;
            if ((this.endPosX - this.startPosX) / this.unitWidth < 12) {
                let dragBox = document.getElementById("drag-box");
                document.body.removeChild(dragBox);
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
            console.log(this.currentTime); //当前选中天的时间戳
            that.$ajax
                .get(
                    "http://iyou.s1.natapp.cc/lidsLibrary//space/roomBookings?date=" +
                        timeStr
                )
                .then(function(response) {
                    let data = response.data;
                    console.log(data);
                    if (data.code === 0) {
                    } else {
                    }
                })
                .catch(function(err) {
                    console.log(err);
                });
        }
    },

    //页面加载时请求今天的数据
    created() {
        this.getOrderInfoAjax.call(this, 0);
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

//绘制矩形
function paintDragBox(x, y, h) {
    let dragBox = document.createElement("div");
    dragBox.id = "drag-box";
    dragBox.className = "drag-box";
    dragBox.style.position = "absolute";
    dragBox.style.top = y + "px";
    dragBox.style.left = x + "px";
    dragBox.style.height = h + "px";
    return dragBox;
}
</script>