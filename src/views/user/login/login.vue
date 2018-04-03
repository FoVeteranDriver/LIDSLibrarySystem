<style lang="less">
@import "./login.less";
</style>

<template>
    <div class="login">
        <template>
            <Modal v-model="show" @on-ok="logIn" @on-cancel="cancel">
                <div slot="header" style="font-size:0.18rem;height:0.18rem;">
                    <Tabs type="" v-model="tabName">
                        <TabPane label="账号登录" name="accountTab">
                            <Input v-model="username" placeholder="读书证号" autocomplete="new-password">
                            <span slot="prepend">
                                <img src="../../../images/user/login/account.png"></span>
                            </Input>
                            </br>
                            <Input v-model="password" type="password" placeholder="密码" autocomplete="new-password">
                            <span slot="prepend">
                                <img src="../../../images/user/login/password.png">
                            </span>
                            </br>
                            </Input>
                        </TabPane>
                        <TabPane label="扫码登录" name="scanTab" >
                            <div class="QRcode">
                                <img src="../../../images/user/login/QRcode.jpg">
                            </div>
                        </TabPane>
                    </Tabs>
                </div>
                <div slot="footer">
                    <a @click="login" href="javascript:void(0)" class="login-btn"  v-show="!QRcode"><img src="/src/images/user/login/login.png"></a>
                </div>

            </Modal>
        </template>
    </div>
</template>


<script>
export default {
  data() {
    return {
      show: true,
      tabName:"accountTab",
      username:"",
      password:""
    };
  },
  computed:{
      QRcode:function(){
          if (this.tabName == "accountTab"){
              return false;
          }else {
              return true;
          }
      }
  },
  methods: {
    logIn:function() {

    },
    changeQRcode:function(){
        this.QRcode = !this.QRcode
    }
  }
};
</script>