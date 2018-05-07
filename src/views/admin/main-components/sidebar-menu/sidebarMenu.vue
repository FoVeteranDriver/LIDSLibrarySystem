<style lang="less">
    @import './menu.less';
</style>

<template>
    <div class="admin-sideBar">
        <slot name="top"></slot>
        <Tabs v-model="tab" :animated="false" @on-click='handleTabClick'>
            <template v-for="item in menuList">
                <TabPane  :label='item.title' :icon='item.icon' :key='item.path' :name='item.name'>
                    <RadioGroup type="button" v-model="route" @on-change='changeMenu'>
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
            tab:this.hotTab,
            route:this.currentRoute
        }
    },
    props: {
        menuList: Array,
        menuTheme: {
            type: String,
            default: 'dark'
        },
        hotTab:{
            type:String
        },
        currentRoute:{
            type:String
        },
        onMenuOpen:{
            type:Function
        }
    },
    methods: {
        changeMenu (active) {
            let willpush = true;
            if (this.beforePush !== undefined) {
                if (!this.beforePush(this.route)) {
                    willpush = false;
                }
            }
            if (willpush) {
                this.$router.push({
                    name: this.route
                });
            }
            this.$emit('on-change', this.route);
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