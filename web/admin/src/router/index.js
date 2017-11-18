import Vue from 'vue'
import Router from 'vue-router'
const _import = require('./_import_' + process.env.NODE_ENV)
// in development env not use Lazy Loading,because Lazy Loading too many pages will cause webpack hot update too slow.so only in production use Lazy Loading

/* layout */
import Layout from '../views/layout/Layout'

Vue.use(Router)

 /**
  * icon : the icon show in the sidebar
  * hidden : if `hidden:true` will not show in the sidebar
  * redirect : if `redirect:noredirect` will not redirct in the levelbar
  * noDropdown : if `noDropdown:true` will not has submenu in the sidebar
  * meta : `{ role: ['admin'] }`  will control the page role
  **/
export const constantRouterMap = [
  { path: '/login', component: _import('login/index'), hidden: true },
  { path: '/404', component: _import('error/404'), hidden: true },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: '首页',
    hidden: true,
    children: [{ path: 'dashboard', component: _import('dashboard/index') }]
  }
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

export const asyncRouterMap = [
  {
    path: '/dashboard',
    component: Layout,
    redirect: '/dashboard',
    icon: 'zujian',
    noDropdown: true,
    children: [{ path: 'index', name: '仪表盘', component: _import('dashboard/index'), meta: {}}]
  },
  {
    path: '/monitor',
    component: Layout,
    redirect: 'noredirect',
    name: '系统监控',
    icon: 'tubiao',
    children: [
      { path: 'trans-monitor', name: '交易监控', component: _import('monitor/trans'), meta: { role: ['monitor'] }}
    ]
  },
  {
    path: '/system-admin',
    component: Layout,
    redirect: 'noredirect',
    name: '系统管理',
    icon: 'quanxian',
    children: [
      { path: 'group', name: '组织管理', component: _import('sys/group'), meta: { role: ['sys-auth'] }},
      { path: 'user', name: '用户管理', component: _import('sys/user'), meta: { role: ['sys-auth'] }},
      { path: 'role', name: '角色管理', component: _import('sys/role'), meta: { role: ['sys-auth'] }},
      { path: 'permission', name: '权限管理', component: _import('sys/permission'), meta: { role: ['sys-auth'] }}
    ]
  },
  { path: '*', redirect: '/404', hidden: true }
]
