<template>
    <div class="editAdmin">
       <Row :gutter="32">
        <Col span="12">
        <h3>权限管理</h3>
        <div class="rights-wrapper">
            <div class="rights-item">
                <Checkbox v-model="settings" size='large' @on-change="handleSettingsToggle">设置</Checkbox>
                <div  v-show="settings">
                        <Checkbox v-model="settingsGroup.authority" @on-change='handleAuthorityClick'>
                            <span>权限管理</span>
                            <CheckboxGroup v-model="rwGroup.authority" @on-change='handleRWClick1'>
                                <Checkbox label="read">
                                    <span>可读</span>
                                </Checkbox>
                                <br/>
                                <Checkbox label="write">
                                    <span>可写</span>
                                </Checkbox>
                            </CheckboxGroup>
                        </Checkbox>
                        <Checkbox v-model="settingsGroup.rules" @on-change='handleRulesClick'>
                            <span>系统规则</span>
                            <CheckboxGroup v-model="rwGroup.rules" @on-change='handleRWClick2'>
                                <Checkbox label="read">
                                    <span>可读</span>
                                </Checkbox>
                                 <br/>
                                <Checkbox label="write">
                                    <span>可写</span>
                                </Checkbox>
                            </CheckboxGroup>
                        </Checkbox>
                        <Checkbox v-model="settingsGroup.display" @on-change='handleDisplayClick'>
                            <span>通知展示</span>
                            <CheckboxGroup v-model="rwGroup.display" @on-change='handleRWClick3'>
                                <Checkbox label="read">
                                    <span>可读</span>
                                </Checkbox>
                                 <br/>
                                <Checkbox label="write">
                                    <span>可写</span>
                                </Checkbox>
                            </CheckboxGroup>
                        </Checkbox>
                        <Checkbox v-model="settingsGroup.space" @on-change='handleSpaceClick'>
                            <span>空间区域</span>
                            <CheckboxGroup v-model="rwGroup.space" @on-change='handleRWClick4'>
                                <Checkbox label="read">
                                    <span>可读</span>
                                </Checkbox>
                                 <br/>
                                <Checkbox label="write">
                                    <span>可写</span>
                                </Checkbox>
                            </CheckboxGroup>
                        </Checkbox>
                </div>
            </div>
            <div class="rights-item">
                <Checkbox v-model="management" size='large' @on-change="handleManagementToggle">管理</Checkbox>
                 <div  v-show="management">
                        <Checkbox v-model="managementGroup.mSpace" @on-change="handlemSpaceClick">
                            <span>空间区域</span>
                            <CheckboxGroup v-model="rwGroup.mSpace" @on-change='handleRWClick5'>
                                <Checkbox label="read">
                                    <span>可读</span>
                                </Checkbox>
                                <br/>
                                <Checkbox label="write">
                                    <span>可写</span>
                                </Checkbox>
                            </CheckboxGroup>
                        </Checkbox>
                        <Checkbox v-model="managementGroup.credits" @on-change="handleCreditsClick">
                            <span>积分处罚</span>
                            <CheckboxGroup v-model="rwGroup.credits" @on-change='handleRWClick6'>
                                <Checkbox label="read">
                                    <span>可读</span>
                                </Checkbox>
                                 <br/>
                                <Checkbox label="write">
                                    <span>可写</span>
                                </Checkbox>
                            </CheckboxGroup>
                        </Checkbox>
                        <Checkbox v-model="managementGroup.activity" @on-change="handleActivityClick">
                            <span>活动安排</span>
                            <CheckboxGroup v-model="rwGroup.activity" @on-change='handleRWClick7'>
                                <Checkbox label="read">
                                    <span>可读</span>
                                </Checkbox>
                                 <br/>
                                <Checkbox label="write">
                                    <span>可写</span>
                                </Checkbox>
                            </CheckboxGroup>
                        </Checkbox>
                </div>
            </div>
            <div class="rights-item">
                <Checkbox v-model="data" size='large' @on-change="handleDataToggle">数据</Checkbox>
                <div v-show="data">
                        <Checkbox v-model="dataGroup.data1" @on-change='handleData1Click'>
                            <span>数据展示1</span>
                            <CheckboxGroup v-model="rwGroup.data1" @on-change='handleRWClick8'>
                                <Checkbox label="read">
                                    <span>可读</span>
                                </Checkbox>
                                <br/>
                                <Checkbox label="write">
                                    <span>可写</span>
                                </Checkbox>
                            </CheckboxGroup>
                        </Checkbox>
                        <Checkbox v-model="dataGroup.data2" @on-change='handleData2Click'>
                            <span>数据展示2</span>
                            <CheckboxGroup v-model="rwGroup.data2" @on-change='handleRWClick9'>
                                <Checkbox label="read">
                                    <span>可读</span>
                                </Checkbox>
                                 <br/>
                                <Checkbox label="write">
                                    <span>可写</span>
                                </Checkbox>
                            </CheckboxGroup>
                        </Checkbox>
                </div>
            </div>
        </div>
        </Col>
        <Col span="12">
            <Form ref="formCustom" label-position="right" :model='form' :rules="ruleCustom" :label-width="80">
                <FormItem label="学工号" prop="library_card_number">
                    <Input type="text" v-model="form.library_card_number"></Input>
                </FormItem>
                <FormItem label="姓名" prop="name">
                    <Input type="text" v-model="form.name"></Input>
                </FormItem>
                <FormItem label="账号" prop="account">
                    <Input type="text" v-model="form.account" :disabled='isNew?false:true'></Input>
                </FormItem>
                <FormItem label="密码" prop="password">
                    <Input type="password" v-model="form.password" :disabled='isNew?false:true'></Input>
                </FormItem>
                <FormItem label="确认密码" prop="passwdCheck">
                    <Input type="password" v-model="form.passwdCheck" :disabled='isNew?false:true'></Input>
                </FormItem>
                <FormItem label="邮箱" prop="email">
                    <Input type="text" v-model="form.email"></Input>
                </FormItem>
                 <FormItem label="手机" prop="phone">
                    <Input type="text" v-model="form.phone"></Input>
                </FormItem>
            </Form>
            <Button type="ghost" @click="handleSubmit('formCustom')">Submit</Button>
        </Col>
    </Row>
    </div>
</template>

<script>
export default {
    name: 'editAdmin',
    data(){
        const validateCard=(rule, value, callback) => {
            let p=/^(\d)*$/;
            if(!p.test(value)){
                callback(new Error('学工号格式有误'));
            }
            callback();
        };
        const validateName=(rule, value, callback) => {
            callback();
        };
        const validateAccount=(rule, value, callback) => {
            let p=/^[\da-zA-Z][\da-zA-Z_]{3,}$/;
            if(!p.test(value)){
                callback(new Error('请输入4位以上由字母、数字或下划线组成的账号'));
            }
            callback();
        };
        const validatePassword=(rule, value, callback) => {
            let p=/^[0-9a-zA-Z]{6,}/;
                if(this.isNew){
                if(value==''){
                    callback(new Error('密码不能为空'));
                }
                if(!p.test(value)){
                    callback(new Error('请输入6位以上由字母、数字组成的密码'));
                }
                if(this.form.passwdCheck !== ''){
                    this.$refs.formCustom.validateField('passwdCheck');
                }
                callback();
            }else{
                callback();
            }
           
        };
        const validatePasswdCheck=(rule, value, callback) => {
            if(this.isNew){
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.form.password) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            }else{
                callback();
            }
         
        };
        const validateEmail=(rule, value, callback) => {
            if (value !== '') {
                let p=/^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
                if(!p.test(value)){
                    callback(new Error('邮箱格式有误!'));
                }
                callback();
            }else {
                callback();
            }
        };
        const validatePhone=(rule, value, callback) => {
            if (value !== '') {
                let p=/^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\d{8}$/;
                if(!p.test(value)){
                    callback(new Error('手机号码格式有误!'));
                }
                callback();
            }else {
                callback();
            }
        };
        return{
            isNew:true,
            settings:false,
            management:false,
            data:false,
            accessList:[],
            settingsGroup:{
                authority:false,
                rules:false,
                display:false,
                space:false,
            },
            managementGroup:{
                mSpace:false,
                credits:false,
                activity:false
            },
            dataGroup:{
                data1:false,
                data2:false
            },
            rwGroup:{
                authority:[],
                rules:[],
                display:[],
                space:[],
                mSpace:[],
                credits:[],
                activity:[],
                data1:[],
                data2:[]
            },
            form:{
                library_card_number:'',
                name:'',
                account:'',
                password:'',
                passwdCheck:'',
                email:'',
                phone:''
            },
            ruleCustom:{
                library_card_number:[{
                    validator: validateCard, trigger: 'blur' 
                }],
                name:[{
                    validator: validateName, trigger: 'blur' 
                }],
                account:[{
                    validator:validateAccount,trigger:'blur'
                }],
                password:[{
                    validator:validatePassword,trigger:'blur'
                }],
                passwdCheck:[{
                    validator:validatePasswdCheck,trigger:'blur'
                }],
                email:[{
                    validator:validateEmail,trigger:'blur'
                }],
                phone:[{
                    validator:validatePhone,trigger:'blur'
                }]
            }
        }
    },
    methods:{
        handleSettingsToggle(){
            if(!this.settings){
                this.settingsGroup.authority=false;
                this.settingsGroup.rules=false;
                this.settingsGroup.display=false;
                this.settingsGroup.space=false;
                this.rwGroup.authority=[];
                this.rwGroup.rules=[];
                this.rwGroup.display=[];
                this.rwGroup.space=[];
            }
        },
        handleManagementToggle(){
            if(!this.management){
                this.managementGroup.mSpace=false;
                this.managementGroup.credits=false;
                this.managementGroup.activity=false;
                this.rwGroup.mSpace=[];
                this.rwGroup.credits=[];
                this.rwGroup.activity=[];
            }
        },
        handleDataToggle(){
            if(!this.data){
                this.dataGroup.data1=false;
                this.dataGroup.data2=false;
                this.rwGroup.data1=[];
                this.rwGroup.data2=[];
            }
        },
        handleAuthorityClick(check){
            if(check){
                this.rwGroup.authority.push('read','write');
            }else{
                this.rwGroup.authority=[];
            }
        },
        handleRulesClick(check){
            if(check){
                this.rwGroup.rules.push('read','write');
            }else{
                this.rwGroup.rules=[];
            }
        },
        handleDisplayClick(check){
            if(check){
                this.rwGroup.display.push('read','write');
            }else{
                this.rwGroup.display=[];
            }
        },
        handleSpaceClick(check){
            if(check){
                this.rwGroup.space.push('read','write');
            }else{
                this.rwGroup.space=[];
            }
        },
        handlemSpaceClick(check){
            if(check){
                this.rwGroup.mSpace.push('read','write');
            }else{
                this.rwGroup.mSpace=[];
            }
        },
        handleCreditsClick(check){
            if(check){
                this.rwGroup.credits.push('read','write');
            }else{
                this.rwGroup.credits=[];
            }
        },
        handleActivityClick(check){
            if(check){
                this.rwGroup.activity.push('read','write');
            }else{
                this.rwGroup.activity=[];
            }
        },
        handleData1Click(check){
            if(check){
                this.rwGroup.data1.push('read','write');
            }else{
                this.rwGroup.data1=[];
            }
        },
        handleData2Click(check){
            if(check){
                this.rwGroup.data2.push('read','write');
            }else{
                this.rwGroup.data2=[];
            }
        },
        handleRWClick1(data){
            if(data.length==0){
                this.settingsGroup.authority=false;
            }else if(data.length==1){
                if(data[0]=='write'){   //不存在可写不可读的情况
                    if(this.settingsGroup.authority==true){ 
                        this.rwGroup.authority=[];
                        this.settingsGroup.authority=false;
                    }else{
                        this.settingsGroup.authority=true;
                        this.rwGroup.authority=['read','write'];
                    }
                   
                }else{
                    if(!this.settingsGroup.authority){
                        this.settingsGroup.authority=true;
                    }
                }       
            }else if(data.length==2){
                if(!this.settingsGroup.authority){
                    this.settingsGroup.authority=true;
                }
            }
        },
         handleRWClick2(data){
            if(data.length==0){
                this.settingsGroup.rules=false;
            }else if(data.length==1){
                if(data[0]=='write'){
                    if(this.settingsGroup.rules==true){
                        this.rwGroup.rules=[];
                        this.settingsGroup.rules=false;
                    }else{
                        this.settingsGroup.rules=true;
                        this.rwGroup.rules=['read','write'];
                    }
                   
                }else{
                    if(!this.settingsGroup.rules){
                        this.settingsGroup.rules=true;
                    }
                }       
            }else if(data.length==2){
                if(!this.settingsGroup.rules){
                    this.settingsGroup.rules=true;
                }
            }
        },
        handleRWClick3(data){
            if(data.length==0){
                this.settingsGroup.display=false;
            }else if(data.length==1){
                if(data[0]=='write'){
                    if(this.settingsGroup.display==true){
                        this.rwGroup.display=[];
                        this.settingsGroup.display=false;
                    }else{
                        this.settingsGroup.display=true;
                        this.rwGroup.display=['read','write'];
                    }
                   
                }else{
                    if(!this.settingsGroup.display){
                        this.settingsGroup.display=true;
                    }
                }       
            }else if(data.length==2){
                if(!this.settingsGroup.display){
                    this.settingsGroup.display=true;
                }
            }
        },
        handleRWClick4(data){
            if(data.length==0){
                this.settingsGroup.space=false;
            }else if(data.length==1){
                if(data[0]=='write'){
                    if(this.settingsGroup.space==true){
                        this.rwGroup.space=[];
                        this.settingsGroup.space=false;
                    }else{
                        this.settingsGroup.space=true;
                        this.rwGroup.space=['read','write'];
                    }
                   
                }else{
                    if(!this.settingsGroup.space){
                        this.settingsGroup.space=true;
                    }
                }       
            }else if(data.length==2){
                if(!this.settingsGroup.space){
                    this.settingsGroup.space=true;
                }
            }
        },
        handleRWClick5(data){
            if(data.length==0){
                this.managementGroup.mSpace=false;
            }else if(data.length==1){
                if(data[0]=='write'){
                    if(this.managementGroup.mSpace==true){
                        this.rwGroup.mSpace=[];
                        this.managementGroup.mSpace=false;
                    }else{
                        this.managementGroup.mSpace=true;
                        this.rwGroup.mSpace=['read','write'];
                    }
                   
                }else{
                    if(!this.managementGroup.mSpace){
                        this.managementGroup.mSpace=true;
                    }
                }       
            }else if(data.length==2){
                if(!this.managementGroup.mSpace){
                    this.managementGroup.mSpace=true;
                }
            }
        },
        handleRWClick6(data){
            if(data.length==0){
                this.managementGroup.credits=false;
            }else if(data.length==1){
                if(data[0]=='write'){
                    if(this.managementGroup.credits==true){
                        this.rwGroup.credits=[];
                        this.managementGroup.credits=false;
                    }else{
                        this.managementGroup.credits=true;
                        this.rwGroup.credits=['read','write'];
                    }
                   
                }else{
                    if(!this.managementGroup.credits){
                        this.managementGroup.credits=true;
                    }
                }       
            }else if(data.length==2){
                if(!this.managementGroup.credits){
                    this.managementGroup.credits=true;
                }
            }
        },
        handleRWClick7(data){
            if(data.length==0){
                this.managementGroup.activity=false;
            }else if(data.length==1){
                if(data[0]=='write'){
                    if(this.managementGroup.activity==true){
                        this.rwGroup.activity=[];
                        this.managementGroup.activity=false;
                    }else{
                        this.managementGroup.activity=true;
                        this.rwGroup.activity=['read','write'];
                    }
                   
                }else{
                    if(!this.managementGroup.activity){
                        this.managementGroup.activity=true;
                    }
                }       
            }else if(data.length==2){
                if(!this.managementGroup.activity){
                    this.managementGroup.activity=true;
                }
            }
        },
        handleRWClick8(data){
            if(data.length==0){
                this.dataGroup.data1=false;
            }else if(data.length==1){
                if(data[0]=='write'){
                    if(this.dataGroup.data1==true){
                        this.rwGroup.data1=[];
                        this.dataGroup.data1=false;
                    }else{
                        this.dataGroup.data1=true;
                        this.rwGroup.data1=['read','write'];
                    }
                   
                }else{
                    if(!this.dataGroup.data1){
                        this.dataGroup.data1=true;
                    }
                }       
            }else if(data.length==2){
                if(!this.dataGroup.data1){
                    this.dataGroup.data1=true;
                }
            }
        },
        handleRWClick9(data){
            if(data.length==0){
                this.dataGroup.data2=false;
            }else if(data.length==1){
                if(data[0]=='write'){
                    if(this.dataGroup.data2==true){
                        this.rwGroup.data2=[];
                        this.dataGroup.data2=false;
                    }else{
                        this.dataGroup.data2=true;
                        this.rwGroup.data2=['read','write'];
                    }
                   
                }else{
                    if(!this.dataGroup.data2){
                        this.dataGroup.data2=true;
                    }
                }       
            }else if(data.length==2){
                if(!this.dataGroup.data2){
                    this.dataGroup.data2=true;
                }
            }
        },
        initRW(name,access){
            if(access==2){
                this.rwGroup[name]=['read','write'];
            }else if(access==1){
                this.rwGroup[name]=['read'];
            }
        },
        handleSubmit(name){
            this.$refs[name].validate((valid) => {
                if (valid) {
                    if(this.settings){
                        
                    }
                    this.$Message.success('Success!');
                } else {
                    this.$Message.error('Fail!');
                }
            })
        }
    },
    created(){
        let admin=this.$route.params.admin;
        if(admin){
            console.log(admin);
            this.isNew=false;
            this.form.library_card_number=admin.library_card_number||'';
            this.form.name=admin.name||'';
            this.form.account=admin.account;
            this.form.email=admin.email||'';
            this.form.phone=admin.phone||'';
            this.accessList=admin.accessList;
            for(let item of this.accessList){
                switch(item.name){
                    case 'admin-settings':
                        this.settings=true;
                        for(let c of item.children){
                            let name=c.name.split('-')[1];
                            this.settingsGroup[name]=true;
                            this.initRW(name,c.access);
                        }
                        break;
                    case 'admin-management':
                        this.management=true;
                        for(let c of item.children){
                            let name=c.name.split('-')[1];
                            this.managementGroup[name]=true;
                            this.initRW(name,c.access);
                        }
                        break;
                    case 'admin-data':
                        this.data=true;
                        for(let c of item.children){
                            let name=c.name.split('-')[1];
                            this.dataGroup[name]=true;
                            this.initRW(name,c.access);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }
};
</script>
