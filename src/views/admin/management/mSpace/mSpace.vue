<style lang="less">
    @import "./mSpace.less";
</style>
<template>
    <div id="mSpace" class="admin-mSpace">
        <div class="header">
            <Input v-model="searchKey" placeholder="请输入区域名称" @on-change='handleSearchChange' size='large'>
                <Button slot="append" icon="ios-search" @click="handleSearch"></Button>
            </Input>
            <Button  class="spaceState custom-btn" @click="handleBtnClick">空间状况</Button>
        </div>
        <div class="main">
            <Table :columns="columns" :data="spaceList"></Table>
        </div>
    </div>
</template>

<script>
    export default {
        name: 'mSpace',
        data(){
            return {
                searchKey:'',
                columns:[
                    {
                        key: 'area',
                        title: '区域名称',
                        width:280,
                        align:'center'
                    },{
                        key: 'totalSpace',
                        title: '已有空间数',
                        width:280,
                        align:'center',
                        render:(h,params)=>{
                             return h('div',[
                                h('div',[
                                    h('span',{
                                        style:'',
                                    },
                                    '研习间:'
                                    ),h('span',{
                                        style:{
                                            color:'#feafaf',
                                            
                                        },
                                    },
                                    params.row.totalSpace.studyRoom)
                                ]),
                                 h('div',[
                                    h('span',{
                                        style:'',
                                    },
                                    '学习间:'
                                    ),h('span',{
                                        style:{
                                            color:'#feafaf',
                                            paddingLeft:'0.08rem'
                                        },
                                    },
                                    params.row.totalSpace.seating)
                                ])
                            ]);
                        }
                    },{
                        key: 'occupySpace',
                        title: '当前已使用空间',
                        width:280,
                        align:'center',
                        render:(h,params)=>{
                             return h('div',[
                                h('div',[
                                    h('span',{
                                        style:'',
                                    },
                                    '研习间:'
                                    ),h('span',{
                                        style:{
                                            color:'#feafaf',
                                            
                                        },
                                    },
                                    params.row.occupySpace.studyRoom)
                                ]),
                                 h('div',[
                                    h('span',{
                                        style:'',
                                    },
                                    '学习间:'
                                    ),h('span',{
                                        style:{
                                            color:'#feafaf',
                                           
                                        },
                                    },
                                    params.row.occupySpace.seating)
                                ])
                            ]);
                        }
                    },{
                        key: 'notes',
                        title: '备注',
                        align:'center'
                    }
                ],
                spaceList:[{
                    area:'合作室1',
                    totalSpace:{studyRoom:10,seating:7},
                    occupySpace:{studyRoom:5,seating:5},
                    notes:'无'
                }],
            }
        },
        mounted () {

        },
        beforeDestroy () {

        },
        methods: {
            handleSearchChange(){

            },
            handleBtnClick(){
                this.$router.push({
                    name: 'admin_spaceState'
                });
            },
            handleSearch(){

            }
        },
    };
</script>