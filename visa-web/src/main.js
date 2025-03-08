import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';
import { createI18n } from 'vue-i18n';
import ja from 'element-plus/es/locale/lang/ja';
import * as ElementPlusIconsVue from '@element-plus/icons-vue';

// 定义语言包
const messages = {
    ja: {
        hello: 'こんにちは'
    }
};

// 创建 vue-i18n 实例
const i18n = createI18n({
    locale: 'ja', // 设置默认语言为日语
    messages
});

// 创建 Vue 应用
const app = createApp(App);

// 注册 Element Plus 图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component);
}

// 使用 Element Plus 并设置日语语言包
app.use(ElementPlus, { locale: ja })
    .use(store)
    .use(router)
    .use(i18n)
    .mount('#app');
