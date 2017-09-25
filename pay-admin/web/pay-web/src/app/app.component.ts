import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'app';

  menuItems = [

    {
      name: "test",
      text: "测试",
      menus: [
        {
          name: 'dashboard',
          text: 'DashBoard',
          href: '/pages/dashboard',
        },
        {
          name: 'smarttables',
          text: 'SmartTables',
          href: '/pages/tables/smarttables',
        },
      ]
    },

    
    {
      name: "auth-admin",
      text: "权限管理",
      menus: [
        {
          name: 'user-admin',
          text: '用户管理',
          href: '#',
        },
        {
          name: 'group-admin',
          text: '用户组管理',
          href: '#',
        },
        {
          name: 'auth-admin',
          text: '权限管理',
          href: '#',
        },
      ]
    },

    {
      name: "mer-admin",
      text: "商户管理",
      menus: [
        {
          name: 'mer-admin',
          text: '商户管理',
          href: '#',
        },
        {
          name: 'ter-admin',
          text: '终端管理',
          href: '#',
        },
      ]
    },

    {
      name: "trans-admin",
      text: "交易管理",
      menus: [
        {
          name: 'trans-admin',
          text: '交易管理',
          href: '#',
        },
        
      ]
    },

    

    
  ];
  
}
