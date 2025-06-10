import Vue from 'vue'
import VueRouter from 'vue-router'




const Home = () => import('@/views/home/Home')
const Login = () => import('@/views/login/Login')
const HomeView = () => import('@/views/home/HomeView')


const wenshu = () => import('@/views/wenshu/wenshu')

const yuce = () => import('@/views/yuce/yuce')


const wenda = () => import('@/views/wenda/wenda')
const history = () => import('@/views/wenda/history')
const history1 = () => import('@/views/wenshu/history1')

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: Login
  },
  {
    path: '/home',
    component: Home,
    children: [
      //首页
      { path: "/home", name: '0', redirect: '/homeview' },
      {
        name: '1',
        path: '/homeview',
        component: HomeView
      },

      {
        name: '12',
        path: '/wenshu',
        component: wenshu
      },
      {
        name: '13',
        path: '/yuce',
        component: yuce
      },
      {
        name: '14',
        path: '/wenda',
        component: wenda
      },
      {
        name: '15',
        path: '/lishi',
        component: history
      },
      {
        name: '16',
        path: '/lishi1',
        component: history1
      },

    ]
  },


]

const router = new VueRouter({
  mode: 'history',
  // base: process.env.BASE_URL,
  routes
})


export default router
