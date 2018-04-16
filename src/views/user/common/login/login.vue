<style lang="less">
@import "./login.less";
</style>

<template>
    <div>
        <Modal v-model="show" :mask-closable="false" @on-cancel="cancel" class="login">
            <div slot="header" style="font-size:0.18rem;height:0.18rem;">
                <Tabs v-model="tabName">
                    <TabPane label="账号登录" name="accountTab">
                        <Input v-model="account" type="text" placeholder="读书证号" @on-focus="inputFocus">
                        <span slot="prepend">
                            <img src="../../../../images/user/login/account.png"></span>
                        </Input>
                        </br>
                        <Input v-model="password" type="password" placeholder="密码" autocomplete="new-password" @on-focus="inputFocus">
                        <span slot="prepend">
                            <img src="../../../../images/user/login/password.png">
                        </span>
                        </br>
                        </Input>
                        <p class="warning" v-show="warning1">{{warningTag}}，请重新输入</p>
                        <p class="warning" v-show="warning2">账号或密码不能为空，请重新输入</p>
                    </TabPane>
                    <TabPane label="扫码登录" name="scanTab">
                        <div class="QRcode">
                            <img :src="QRAddress">
                        </div>
                    </TabPane>
                </Tabs>
            </div>
            <div slot="footer">
                <a @click="login" v-show="!QRcode"><img src="../../../../images/user/login/login.png"></a>
                <p class="tip" v-show="QRcode">请使用已绑定图书证的微信账号登录</p>
            </div>
        </Modal>
    </div>
</template>


<script>
export default {
    name: "loginModal",
    props: ["show"],
    data() {
        return {
            tabName: "accountTab",
            account: "",
            password: "",
            warning1: false,
            warningTag: "",
            warning2: false,
            QRAddress: ""
        };
    },
    watch: {
        warning2:function(val){
            if(val === true){
                this.warning1 = false;
            }
        },
        tabName: function(newVal, oldVal) {
            if (newVal === "scanTab" && oldVal === "accountTab") {
                let that = this;
                that.$ajax
                    .get(
                        "http://iyou.s1.natapp.cc/lidsLibrary/showLoginQRCode",

                        //可改进，超时后做重新请求处理
                        { timeout: 5000 }
                    )
                    .then(function(response) {
                        let data = response.data;
                        if (data.code === 0) {
                            that.QRAddress = data.result.QRCodeUrl;
                            let uuid = data.result.uuid;
                            that.$ajax
                                .get(
                                    "http://iyou.s1.natapp.cc/lidsLibrary/user/checkScanEvent?uuid=" +
                                        uuid
                                )
                                .then(function(response) {
                                    let data = response.data;
                                    if (data.code === 0) {
                                        console.log("登录成功");
                                        that.$options.methods.successLog.call(that,data);
                                        that.$options.methods.cancel.call(that);
                                    } else {
                                        console.log("登录失败");
                                        console.log(data);
                                    }
                                })
                                .catch(function(err) {
                                    console.log(err);
                                });
                        }
                    });
            }
        }
    },
    computed: {
        QRcode: function() {
            if (this.tabName == "scanTab") {
                return true;
            } else {
                return false;
            }
        }
    },
    methods: {
        login(event) {
            event.preventDefault();
            if (this.account.trim() === "" || this.password.trim() === "") {
                this.warning2 = true;
                return;
            } else {
                let that = this;
                this.$ajax
                    .post(
                        "http://iyou.s1.natapp.cc/lidsLibrary/user/login",
                        {
                            libraryCardNumber: this.account,
                            password: this.password
                        },
                        {
                            timeout: 5000
                        }
                    )
                    .then(function(response) {
                        let data = response.data;
                        if (data.code === 0) {
                            console.log("登录成功");
                            that.$options.methods.successLog.call(that,data);
                            that.$options.methods.cancel.call(that);
                        } else {
                            console.log("登录失败");
                            console.log(data);
                            that.warning1 = true;
                            that.password = "";
                            that.warningTag = data.msg;
                        }
                    })
                    .catch(function(err) {
                        console.log(err);
                    });
            }
        },
        inputFocus() {
            this.warning1 = false;
            this.warning2 = false;
        },

        //成功登录触发store的mutation
        successLog(data) {
            this.$store.commit("writeUserName", data);
            this.$store.commit("login");
            this.$options.methods.cancel.call(this);
        },

        //触发父组件事件关闭窗口
        cancel() {
            this.$emit("clickCancel");
            this.tabName = "accountTab";
            this.account = "";
            this.password = "";
        }
    }
};
</script>