import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import mixins from './mixin/index'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import md5 from 'js-md5';
import request from "@/axios/request";


Vue.prototype.request=request


Vue.prototype.$md5 = md5;


Vue.prototype.$bus = new Vue();

import "./assets/css/k.css";

Vue.use(ElementUI);
Vue.config.productionTip = false;
Vue.mixin(mixins);

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
