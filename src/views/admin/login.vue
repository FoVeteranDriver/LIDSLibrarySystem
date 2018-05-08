<style lang="less">
    @import './login.less';
</style>

<template>
    <div class="login" @keydown.enter="handleSubmit">
        <div class="login-con">
            <div class="header">
                <img src=""/>
            </div>
            <Card :bordered="false">
                <p slot="title">
                    <Icon type="log-in"></Icon>
                    欢迎登录
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
                            <Button @click="handleSubmit" type="primary" long>登录</Button>
                        </FormItem>
                    </Form>
                </div>
            </Card>
        </div>
    </div>
</template>

<script>
import Cookies from 'js-cookie';
export default {
    data () {
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
                    { required: true, message: '密码不能为空', trigger: 'blur' }
                ]
            }
        };
    },
    methods: {
        handleSubmit () {
            this.$refs.loginForm.validate((valid) => {
                if (valid) {
                    Cookies.set('user', this.form.userName);
                    let accessList=[{
                        name:'admin-settings',
                        access:1,
                        children:[
                            {
                                name:'admin-authority',
                                access:1
                            },
                             {
                                name:'admin-rules',
                                access:1
                            },
                            {
                                name:'admin-display',
                                access:1,
                            },
                              {
                                name:'admin-space',
                                access:1,
                            }
                        ]
                    },{
                        name:'admin-management',
                        access:1,
                        children:[
                            {
                                name:'admin-mSpace',
                                access:1
                            },
                             {
                                name:'admin-credits',
                                access:1
                            },
                            {
                                name:'admin-activity',
                                access:1
                            }
                        ]
                    },{
                        name:'admin-data',
                        access:1,
                        children:[
                            {
                                name:'data1',
                                access:1
                            },
                             {
                                name:'data2',
                                access:1
                            }
                        ]
                    }];
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
                    sessionStorage.setItem('accessList',JSON.stringify(accessList));
                    sessionStorage.setItem('limits',JSON.stringify(limits));
                    this.$router.push({
                        name: 'admin_index'
                    });
                }
            });
        }
    }
};
</script>

<style>

</style>
