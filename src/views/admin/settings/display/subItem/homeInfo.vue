<style lang="less">
   @import "./homeInfo.less";
</style>
<template>
    <div class="admin-homeInfo">
        <Row :gutter="10">
            <Col span="12">
                <Card>
                    <Row :gutter="10"  class="image-editor">
                        <Col span="14" class="image-editor-con1">
                            <div class="cropper">
                                <img id="cropimg2" alt="">
                            </div>
                        </Col>
                        <Col span="10" class="image-editor-con1">
                            <Row type="flex" justify="center" align="middle" class="image-editor-con1-preview-con">
                                <div id="iPreview2"></div>
                            </Row>
                            <div class="image-editor-con1-btn-con margin-top-10">
                                <input type="file" accept="image/png, image/jpeg, image/gif, image/jpg" @change="handleFileChange" id="fileinput1" class="fileinput" />
                                <label class="filelabel" for="fileinput1"><Icon type="image"></Icon>&nbsp;选择图片</label>
                                <span><Button @click="handlecrop" type="primary" icon="crop">确定</Button></span>
                            </div>
                            <Modal v-model="option.showCropedImage">
                                <p slot="header">预览裁剪之后的图片</p>
                                <img :src="option.cropedImg" alt="" v-if="option.showCropedImage" style="width: 100%;">
                            </Modal>
                        </Col>
                    </Row>
                </Card>
            </Col>
            <Col span="12"> 
                <Card>
                    <h2>文本编辑</h2>
                    <Form ref="formCustom" :model="form" :rules="ruleCustom" :label-width="80">
                        <FormItem label="空间介绍" prop="mainInfo">
                            <Input type="textarea" :rows="4" v-model="form.mainInfo"></Input>
                        </FormItem>
                        <FormItem
                            v-for="(item, index) in form.menu"
                            :key="index"
                            :label="'Item ' + item.index"
                            :prop="'menu.' + index + '.text'"
                            :rules="{required: true, message: 'Item ' + item.index +' can not be empty', trigger: 'blur'}">
                            <Row>
                                <Input type="text" v-model="item.title" placeholder="标题..."></Input>
                                <Input type="textarea" v-model="item.text" placeholder="简要介绍..."></Input>
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
import Cropper from 'cropperjs';
import '../cropper.min.css';
export default {
        name: 'homeInfo',
           data () {
        return {
            cropper: {},
            option: {
                showCropedImage: false,
                cropedImg: '',
                imgName:'',
            },
            files:[],
            form:{
                mainInfo:'',
                menu:[{
                    title:'',
                    text:'',
                    index:1
                },{
                    title:'',
                    text:'',
                    index:2
                },{
                    title:'',
                    text:'',
                    index:3
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
        handleFileChange (e) {
            let file = e.target.files[0];
            this.option.imgName=file.name.split('.')[0];
            let reader = new FileReader();
            reader.onload = () => {
                this.cropper.replace(reader.result);
                reader.onload = null;
            };
            reader.readAsDataURL(file);
        },
        handlecrop () {
            let file = this.cropper.getCroppedCanvas().toDataURL();
            this.option.cropedImg = file;
            this.option.showCropedImage = true;
        },
        handleSubmit(name){
            let that=this;
            this.$refs[name].validate((valid) => {
                if (valid) {
                    let form=new FormData();
                    form.append("mainInfo",this.form.mainInfo);
                    let menu=[];
                    for(let item of this.form.menu){
                        let temp={};
                        temp.title=item.title;
                        temp.text=item.text;
                        menu.push(temp);
                    }
                    form.append("menu",JSON.stringify(menu));
                    if(this.option.cropedImg){
                        let imgFile=this.base64ToBlob(this.option.cropedImg); 
                        form.append('mainPic',imgFile,this.option.imgName+'.png');      
                    }
                    that.$ajax
                        .post(
                            util.adminUrl+"/homePage/",
                            form,
                        ).then(function(response){
                            console.log(response.code);
                        })
                }
            });
        },
        base64ToBlob(urlData) {        //将base64图片转化为可以上传的file对象
            var arr = urlData.split(',');
            var mime = arr[0].match(/:(.*?);/)[1] || 'image/png';
            // 去掉url的头，并转化为byte
            var bytes = window.atob(arr[1]);
            // 处理异常,将ascii码小于0的转换为大于0
            var ab = new ArrayBuffer(bytes.length);
            // 生成视图（直接针对内存）：8位无符号整数，长度1个字节
            var ia = new Uint8Array(ab);
            
            for (var i = 0; i < bytes.length; i++) {
                ia[i] = bytes.charCodeAt(i);
            }
            return new Blob([ab], {
                type: mime
            });
        }
    
    },
    created(){
    },
    mounted(){
        let img = document.getElementById('cropimg2');
        this.cropper = new Cropper(img, {
            dragMode: 'move',
            preview: '#iPreview2',
            restore: false,
            center: false,
            highlight: false,
            cropBoxMovable: false,
            toggleDragModeOnDblclick: false
        });
    }
};
</script>