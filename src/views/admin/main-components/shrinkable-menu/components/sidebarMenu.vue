<style lang="less">
    @import '../styles/menu.less';
</style>

<template>
    <div class="admin-sideBar">
        <Tabs v-model="hotTab" :animated="false" @on-click='handleTabClick'>
            <template v-for="item in menuList">
                <TabPane 
                :label='item.title'
                :icon='item.icon'
                :key='item.name'>
                    <RadioGroup type="button" v-model="currentRoute" @on-change='changeMenu'>
                        <template v-for="child in item.children">
                            <Radio :label="child.name">
                                <Icon :type="child.icon"></Icon>
                                {{child.title.slice(0,2)}}
                                <br/>
                                {{child.title.slice(2)}}
                            </Radio>
                        </template>
                    </RadioGroup>
                </TabPane>
            </template>
        </Tabs>
    </div>       
</template>

<script>
export default {
    name: 'sidebarMenu',
    data(){
        return{
            currentRoute:'',
            hotTab:'-1',
            label:(h,params)=>{
                console.log(params);
                return h('div', [
                    h('span', '标签一'),
                    h('Badge', {
                        props: {
                            count: 3
                        }
                    })
                ])
            } 
        }
    },
    props: {
        menuList: Array,
        menuTheme: {
            type: String,
            default: 'dark'
        },
       
    },
    methods: {
        changeMenu (active) {
            this.$emit('on-change', this.currentRoute);
        },
        itemTitle (item) {
            if (typeof item.title === 'object') {
                return this.$t(item.title.i18n);
            } else {
                return item.title;
            }
        },
        handleTabClick(name){
            this.$emit('on-menuOpen',name);
        }
    },
    mounted(){
       
    }
};
</script>