<style lang="less">
</style>
<template>
    <div class="spaceInfo">
        <span>空间类型&nbsp;&nbsp;</span>
        <Select v-model="currentType" style="width:200px" @on-change='handleSelectChange'>
            <Option v-for="item in spaceList" :value="item.value" :key="item.value">{{ item.label }}</Option>
        </Select>
        <Row :gutter="10">
            <Col span="12">
                <Form ref="formCustom" :model="form" :label-width="80">
                    <Card>
                        <FormItem label="空间简介" prop="introduce">
                            <Input type="textarea" v-model="form.introduce"></Input>
                        </FormItem>
                    </Card>
                    <Card>
                        <h2>预约须知</h2>
                        <FormItem label="标题" prop="notice.title">
                            <Input type="text" v-model="form.notice.title"></Input>
                        </FormItem>
                        <FormItem label="前言" prop="notice.prolusion">
                            <Input type="textarea" v-model="form.notice.prolusion"></Input>
                        </FormItem>
                        <FormItem
                            v-for="(item, index) in form.notice.tips"
                            v-if="item.status"
                            :key="index"
                            :label="'规则 ' + item.index"
                            :prop="'notice.tips.' + index + '.text'"
                            :rules="{required: true, message: '规则 ' + item.index +' 不能为空', trigger: 'blur'}">
                            <Row>
                                <Col span="18">
                                    <Input type="textarea" v-model="item.text" placeholder="Enter something..."></Input>
                                </Col>
                                <Col span="4" offset="1">
                                    <Button type="ghost" @click="handleRemove(index)">Delete</Button>
                                </Col>
                            </Row>
                        </FormItem>
                        <FormItem>
                            <Row>
                                <Col span="12">
                                    <Button type="dashed" long @click="handleAdd" icon="plus-round">Add item</Button>
                                </Col>
                            </Row>
                        </FormItem>
                    </Card>
                </Form>
                
            </Col>
            <Col span="12"> 
                <Card class="attach">
                    <Upload action="/" accept="image/png, image/jpeg, image/gif, image/jpg"  :format="['jpg','jpeg','png']" :before-upload="handleAlbumUpload">
                        <span>相册展示&nbsp;&nbsp;</span>
                        <Button type="ghost" icon="ios-cloud-upload-outline">上传文件</Button>
                    </Upload>
                    <template v-for="file in albumFiles">
                        <div :key="file.name" class="attach-item">
                            <Tag type="dot" closable @on-close="handleAlbumDelete" :name="file.name">{{file.name}}</Tag>
                        </div>
                    </template>
                    <Upload action="/" accept="image/png, image/jpeg, image/gif, image/jpg" :format="['jpg','jpeg','png']" :before-upload="handleDeployUpload">
                        <span>硬件配置&nbsp;&nbsp;</span>
                        <Button type="ghost" icon="ios-cloud-upload-outline">上传文件</Button>
                    </Upload>
                    <template v-for="file in deployFiles">
                        <div :key="file.name" class="attach-item">
                            <Tag type="dot" closable @on-close="handleDeployDelete" :name="file.name">{{file.name}}</Tag>
                        </div>
                    </template>
                </Card>    
            </Col>
            <Button type="ghost" class="submit" @click="handleSubmit('formCustom')">提交</Button>
        </Row>
    </div>
</template>
<script>
import util from '../../../../../libs/util.js';
export default {
    name: 'spaceInfo',
    data () {
        return {
            currentType:'studyRoom',
            spaceList:[
                {
                    label:'研习间',
                    value:'studyRoom'
                },
                {
                    label:'座位',
                    value:'seat'
                },
            ],
            albumFiles:[],
            deployFiles:[],
            index: 1,
            form:{
                introduce:'',
                notice:{
                    tips:[{
                            text: '',
                            index: 1,
                            status: 1
                    }],
                    title:'',
                    prolusion:''
                },
            },
            ruleCustom:{
                introduce:[{
                    //validator: validateCard, trigger: 'blur' 
                }],
                notice:[{
                    //validator: validateName, trigger: 'blur' 
                }],
            }
        };
    },
    methods:{
        handleAlbumUpload(file){
            for(let i=0;i<this.albumFiles.length;i++){
                let item=this.albumFiles[i];
                if(item.name==file.name){
                    this.$Message.warning({
                        content: '此文件已存在上传列表中'
                    });
                    return false;
                }
            }
            this.albumFiles.push(file);
            return false;
        },
        handleDeployUpload(file){
             for(let i=0;i<this.deployFiles.length;i++){
                let item=this.deployFiles[i];
                if(item.name==file.name){
                    this.$Message.warning({
                        content: '此文件已存在上传列表中'
                    });
                    return false;
                }
            }
            this.deployFiles.push(file);
            return false;
        },
        handleAlbumDelete(event,name){
            for(let i=0;i<this.albumFiles.length;i++){
                let item=this.albumFiles[i];
                if(item.name==name){
                    this.albumFiles.splice(i,1);
                    break;
                }
            }
        },
        handleDeployDelete(event,name){
            for(let i=0;i<this.deployFiles.length;i++){
                let item=this.deployFiles[i];
                if(item.name==name){
                    this.deployFiles.splice(i,1);
                    break;
                }
            }
        },
        handleAdd () {
            this.index++;
            this.form.notice.tips.push({
                text: '',
                index: this.index,
                status: 1
            });
        },
        handleRemove (index) {
            this.form.notice.tips[index].status = 0;
        },
        handleSelectChange(){

        },
        handleSubmit(name){
            let that=this;
            this.$refs[name].validate((valid) => {
                if (valid) {
                    let form=new FormData();
                    form.append("introduce",this.form.introduce);
                    let notice={};
                    notice.title=this.form.notice.title;
                    notice.prolusion=this.form.notice.prolusion;
                    notice.tips=[];
                    for(let item of this.form.notice.tips){
                        if(item.status==1){
                            let temp={};
                            temp.text=item.text;
                            notice.tips.push(temp);
                        }
                    }
                    form.append("notice",JSON.stringify(notice));
                    if(this.albumFiles.length){
                        form.append('albumFiles',this.albumFiles);   
                    }  
                    if(this.deployFiles.length){
                        form.append('deployFiles',this.deployFiles);   
                    }  
                    that.$ajax
                        .post(
                            util.adminUrl+"/spaceNotice/?type="+this.currentType,
                            form,
                        ).then(function(response){
                            console.log(response.code);
                        })
                    this.$Message.success('Success!');
                } else {
                    this.$Message.error('Fail!');
                }
            })
        },
    },
    created(){
    },
    mounted(){
    }
};
</script>