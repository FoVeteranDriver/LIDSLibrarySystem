<style lang="less">
    @import './styles/menu.less';
</style>

<template>
    <div :style="{background: bgColor, color:'#825f5d'}" class="ivu-shrinkable-menu">
        <slot name="top"></slot>
        <sidebar-menu 
            :menu-theme="theme" 
            :menu-list="menuList" 
            :open-names="openNames"
            @on-change="handleChange"
            @on-menuOpen='handleMenuOpen'
        ></sidebar-menu>
    </div>
</template>

<script>
import sidebarMenu from './components/sidebarMenu.vue';
import util from '@/libs/util';
export default {
    name: 'shrinkableMenu',
    components: {
        sidebarMenu,
    },
    props: {
        menuList: {
            type: Array,
            required: true
        },
        theme: {
            type: String,
            default: 'dark',
            validator (val) {
                return util.oneOf(val, ['dark', 'light']);
            }
        },
        beforePush: {
            type: Function
        },
        openNames: {
            type: Array
        }
    },
    computed: {
        bgColor () {
            return '#fff';
        },
        shrinkIconColor () {
            return this.theme === 'dark' ? '#fff' : '#495060';
        }
    },
    methods: {
        handleChange (name) {
            let willpush = true;
            if (this.beforePush !== undefined) {
                if (!this.beforePush(name)) {
                    willpush = false;
                }
            }
            if (willpush) {
                this.$router.push({
                    name: name
                });
            }
            this.$emit('on-change', name);
        },
        handleMenuOpen(name){
            this.$emit('on-menuOpen', name);
        }
    }
};
</script>
