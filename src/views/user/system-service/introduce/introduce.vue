<style lang="less">
    @import "./introduce.less";
</style>
<template>
    <div class="introduce">
        <h1 class="title">Introduce</h1>
        <Row>
            <Col span="8">
                <p class="prolusion">
                    {{prolusion}}
                </p>
                <template v-for="(item,index) in tips.slice(0,3)">
                    <p class="tip">
                       {{index+1}}.&nbsp;&nbsp; {{item.text}}
                    </p>
                </template>
            </Col>
            <Col span="8">
                <div class="center-Pic">
                    <img :src="mainPic"/>
                </div>
            </Col>
            <Col span="8">
                <template v-for="(item,index) in tips.slice(3)">
                    <p class="tip">
                        {{index+4}}.&nbsp;&nbsp; {{item.text}}
                    </p>
                </template>
            </Col>
        </Row>
    </div>
</template>

<script>
export default {
    name: 'introduce',
    data(){
        return{
            tips:[{
                text:'使用信息共享空间需遵守国家法律及学校有关规章制度，信息共享空间仅提供本校师生学术研究所用，不得用于任何商业用途',
            },{
                text:'信息共享空间实行预约使用，用户通过图书馆信息共享空间网站进行空间预约。无预约用户在信息共享空间有待分配空间时在服务台现场预约。申请条件请阅各空间说明。'
            },{
                text:'进入信息共享空间，需至服务台刷卡登记，空间使用结束后如时间未到请刷卡退出。预约成功后如不能按时到场，请提前登陆系统删除预约，否则将作该帐户一周内不能进行预约的处罚。',
            },{
                text:'使用电脑请预约知识空间：100号以上普通PC；100号以下Mac电脑，需先到咨询台领取无线键鼠；自带电脑请预约学习空间：分为有显示器和无显示器座位，请根据个人需求进行选择；学习空间和知识空间在预约时间30分钟内刷卡选座（未到预约时间请勿刷卡以免删除预约记录），其他空间到咨询台登记使用。'
            },{
                text:'知识空间资源有限，仅用于电子资源检索，空间预约半小时内不登陆系统（输入学/工号和校园卡消费密码，初始身份证后六位数字）将产生违约；使用电子资源请遵守知识产权保护条例，严禁恶意下载。',
            },{
                text:'请爱护使用信息共享空间所有设施，规范使用设备、软件等，如发现信息共享空间设备故障或有其他疑问，请立即与工作人员联系，不得擅自处理，否则由此造成的机器损坏或软件破坏须做相应赔偿。'
            },{
                text:'请按照预约分配到的空间和设备进行使用，空间内请勿饮食，保持环境整洁。'
            }],
            mainPic:'./static/images/user/system-service/mainPic.jpg',
            prolusion:'信息共享空间面向全校师生开放，为您提供一个灵活多样的学习、工作环境，有利于释放您的创新能量'
        }
    }
};
</script>
