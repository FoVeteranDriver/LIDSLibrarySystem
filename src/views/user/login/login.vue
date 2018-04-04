<style lang="less">
@import "./login.less";
</style>

<template>
    <div class="login">
        <template>
            <Modal v-model="show">
                <div slot="header" style="font-size:0.18rem;height:0.18rem;">
                    <Tabs v-model="tabName">
                        <TabPane label="账号登录" name="accountTab">
                            <Input v-model="account" type="text" placeholder="读书证号" >
                            <span slot="prepend">
                                <img src="../../../images/user/login/account.png"></span>
                            </Input>
                            </br>
                            <Input v-model="password" type="text" placeholder="密码" onfocus="this.type='password'">
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
                    <a @click="login" class="login-btn"  v-show="!QRcode"><img src="/src/images/user/login/login.png"></a>
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
      account:"",
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
    login:function(event) {
        event.preventDefault();
        console.log(this.account + ' ' + this.password);
        this.$ajax.post('http://iyou.s1.natapp.cc/lidsLibrary/user/login',{
                libraryCardNumber:this.account,
                password:this.password
            }
        ).then(function (response) {
            console.log(response.data);
          }).catch(function(err){
              console.log(err);
          })
    },
    changeQRcode:function(){
        this.QRcode = !this.QRcode
    }
  }
};
</script>