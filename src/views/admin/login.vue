<style lang="less">
    @import './login.less';
</style>

<template>
    <div class="admin-login" @keydown.enter="handleSubmit">
        <div class="admin-login-con">
            <Card :bordered="false">
                <p slot="title">
                    <router-link :to="{name:'home'}">
                        <Icon type="log-in" color='black'></Icon>
                    </router-link>
                    <br/>
                    <img src="../../images/admin/login/logo.png"/>
                </p>
                <div class="form-con">
                    <Form ref="loginForm" :model="form" :rules="rules">
                        <FormItem prop="userName">
                            <Input v-model="form.userName" placeholder="请输入用户名">
                                <span slot="prepend">
                                    <Icon :size="16" type="person"></Icon>
                                </span>
                            </Input>
                        </FormItem>
                        <FormItem prop="password">
                            <Input type="password" v-model="form.password" placeholder="请输入密码">
                                <span slot="prepend">
                                    <Icon :size="14" type="locked"></Icon>
                                </span>
                            </Input>
                        </FormItem>
                        <FormItem>
                            <Button @click="handleSubmit" long>登录</Button>
                        </FormItem>
                    </Form>
                </div>
            </Card>
        </div>
    </div>
</template>

<script>
import Cookies from 'js-cookie';
import util from '../../libs/util.js';
export default {
    data () {
        const validPass=(rule,value,callback)=>{
            let p=/^[0-9a-zA-Z]{6,}/;
            if(value==''){
                callback(new Error('密码不能为空'));
            }
            if(!p.test(value)){
                callback(new Error('密码长度或格式有误'));
            }
            callback();
        };
        return {
            form: {
                userName: '',
                password: ''
            },
            rules: {
                userName: [
                    { required: true, message: '账号不能为空', trigger: 'blur' }
                ],
                password: [
                    { validator:validPass, trigger: 'blur' }
                ]
            }
        };
    },
    methods: {
        handleSubmit () {
            this.$refs.loginForm.validate((valid) => {
                if (valid) {
                    let that=this;
                    let postData={};
                    postData.account=this.form.userName;
                    postData.password=this.form.password;
                    that.$ajax
                        .post(
                            util.adminUrl+"/user/login/",
                            JSON.stringify(postData),
                            {
                                headers: {"Content-Type": "application/json;charset=utf-8"}
                            }
                        )
                        .then(function(response){
                            let data=response.data;
                            if(data.code==1){
                                that.$Message.error({
                                    content: '账号不存在'
                                });
                                return;
                            }else if(data.code==2){
                                that.$Message.error({
                                    content: '密码错误'
                                });
                                return;
                            }else if(data.code==0){
                                sessionStorage.setItem('adminAccount',data.result.account);
                                sessionStorage.setItem('accessList',JSON.stringify(data.result.accessList));
                                let accessList=data.result.accessList;
                                let limits=[];
                                accessList.forEach(item=>{
                                    if(item.access==0){
                                        limits.push(item.name);
                                    }
                                    if(item.children&&item.children.length){
                                        item.children.forEach(i=>{
                                            if(i.access==0){
                                                limits.push(i.name);
                                            }
                                        })
                                    }
                                });
                                sessionStorage.setItem('limits',JSON.stringify(limits));
                                that.$router.push({
                                    name: 'admin_index'
                                });
                            }else{
                                that.$Message.error({
                                    content: '登录失败，请稍后重试'
                                });
                                return;
                                // sessionStorage.setItem('adminName','hhh');
                                // let accessList=[{
                                //     name:'admin-settings',
                                //     access:1,
                                //     children:[
                                //         {
                                //             name:'admin-authority',
                                //             access:1
                                //         },
                                //          {
                                //             name:'admin-rules',
                                //             access:1
                                //         },
                                //         {
                                //             name:'admin-display',
                                //             access:1,
                                //         },
                                //           {
                                //             name:'admin-space',
                                //             access:1,
                                //         }
                                //     ]
                                // },{
                                //     name:'admin-management',
                                //     access:1,
                                //     children:[
                                //         {
                                //             name:'admin-mSpace',
                                //             access:1
                                //         },
                                //          {
                                //             name:'admin-credits',
                                //             access:1
                                //         },
                                //         {
                                //             name:'admin-activity',
                                //             access:1
                                //         }
                                //     ]
                                // },{
                                //     name:'admin-data',
                                //     access:1,
                                //     children:[
                                //         {
                                //             name:'data1',
                                //             access:1
                                //         },
                                //          {
                                //             name:'data2',
                                //             access:1
                                //         }
                                //     ]
                                // }];
                                // let limits=[];
                                // accessList.forEach(item=>{
                                //     if(item.access==0){
                                //         limits.push(item.name);
                                //     }
                                //     if(item.children&&item.children.length){
                                //         item.children.forEach(i=>{
                                //             if(i.access==0){
                                //                 limits.push(i.name);
                                //             }
                                //         })
                                //     }
                                // });
                                // sessionStorage.setItem('accessList',JSON.stringify(accessList));
                                // sessionStorage.setItem('limits',JSON.stringify(limits));
                                // that.$router.push({
                                //     name: 'admin_index'
                                // });
                            }
                        })
                        .catch(function(err){
                            console.log(err);
                        });
                }
            });
        }
    }
};
</script>

<style>

</style>
