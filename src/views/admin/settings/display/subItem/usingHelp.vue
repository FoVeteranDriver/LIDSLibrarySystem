<style lang="less">
  @import "./usingHelp.less";
</style>
<template>
    <div class="admin-usingHelp">
        <Row :gutter="10">
            <Col span="12">
                  <Card>
                   <Upload action="/" accept="image/png, image/jpeg, image/gif, image/jpg" :format="['jpg','jpeg','png']" :before-upload="handleFileUpload">
                        <span>图片上传&nbsp;&nbsp;</span>
                        <Button type="ghost" icon="ios-cloud-upload-outline">上传文件</Button>
                    </Upload>
                    <Tag v-if="imgFile.name" type="dot"  :name="imgFile.name">{{imgFile.name}}</Tag>
                </Card>
            </Col>
            <Col span="12"> 
                <Card>
                    <h2>文本编辑</h2>
                    <Form ref="formCustom" :model="form" :rules="ruleCustom" :label-width="80">
                        <FormItem label="空间介绍" prop="prolusion">
                            <Input type="textarea" :rows="4" v-model="form.prolusion"></Input>
                        </FormItem>
                        <FormItem
                            v-for="(item, index) in form.tips"
                            v-if="item.status"
                            :key="index"
                            :label="'Item ' + item.index"
                            :prop="'tips.' + index + '.text'"
                            :rules="{required: true, message: 'Item ' + item.index +' can not be empty', trigger: 'blur'}">
                            <Row>
                                <Col span="18">
                                    <Input type="textarea" v-model="item.text" placeholder="条例..."></Input>
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
                    </Form>
                </Card>    
            </Col>
            <Button type="ghost" class="submit" @click="handleSubmit('formCustom')">提交</Button>
        </Row>
    </div>
</template>
<script>
import util from '../../../../../libs/util.js';
export default {
        name: 'homeInfo',
           data () {
        return {
            index:1,
            imgFile:'',
            form:{
                prolusion:'',
                tips:[{
                    text:'',
                    index:1,
                    status:1
                }]
            },
            ruleCustom:{
                noticeTitle:[{
                    //validator: validateCard, trigger: 'blur' 
                }],
                noticeText:[{
                    //validator: validateName, trigger: 'blur' 
                }],
                publisher:[{
                    //validator: validateName, trigger: 'blur' 
                }],
            }
        };
    },
    methods:{
        handleFileUpload(file){
            this.imgFile=file;
        },
        handleAdd () {
            this.index++;
            this.form.tips.push({
                text: '',
                index: this.index,
                status: 1
            });
        },
        handleRemove (index) {
            this.form.tips[index].status = 0;
        },
        handleSubmit(name){
            let that=this;
            this.$refs[name].validate((valid) => {
                if (valid) {
                    let form=new FormData();
                    form.append("prolusion",this.form.prolusion);
                    if(this.imgFile){
                        form.append("mainPic",this.imgFile);
                    } 
                    let tips=[];
                    for(let item of this.form.tips){
                        if(item.status==1){
                            let temp={};
                            temp.text=item.text;
                            tips.push(temp);
                        }
                    }
                    form.append("tips",JSON.stringify(tips));
                    that.$ajax
                        .post(
                            util.adminUrl+"/usinghelp/",
                            form,
                        ).then(function(response){
                            console.log(response.code);
                        })
                }
            });
        }, 
    },
    created(){
    },
};
</script>
