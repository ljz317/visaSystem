import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/Login.vue'

const routes = [
  {
    path: '/login',
    name: 'login',
    component: () => import(/* webpackChunkName: "about" */ '../views/Login.vue')
  },
  {
    path: '/reg',
    name: 'reg',
    component: () => import(/* webpackChunkName: "about" */ '../views/Reg.vue')
  },
  {
    path: '/',
    name: '/login',
    component: () => import(/* webpackChunkName: "about" */ '../views/Login.vue')
  },
  {
    path: '/home',
    name: 'home',
    redirect: '/home/list',
    component: () => import('../views/Home.vue'),
    children: [
      {
        path: 'list',
        name: 'list',
        component: () => import(/* webpackChunkName: "about" */ '../views/homeview/EmpList.vue')
      },
      {
        path: '/:id/detail',
        name: 'detail',
        props: true, // 让路由参数作为 prop 传递给组件
        component: () => import(/* webpackChunkName: "about" */ '../views/emp/DetailEmp.vue')
      },
      {
        path: '/visa/update',
        name: 'update',
        component: () => import(/* webpackChunkName: "about" */ '../views/visa/VisaUpdate.vue')
      },
      {
        path: '/visa/status',
        name: 'status',
        component: () => import(/* webpackChunkName: "about" */ '../views/visa/StatusController.vue')
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
