export const routes = [
  { path: '/', redirect: '/dashboard' },
  {
    path: '/',
    component: () => import('@/layouts/default.vue'),
    children: [
      {
        path: 'dashboard',
        component: () => import('@/pages/dashboard.vue'),
      },
      {
        path: 'wiki',
        component: () => import('@/pages/wiki.vue'),
      },
      {
        path: 'account-settings',
        component: () => import('@/pages/account-settings.vue'),
      },
      {
        path: 'writepost',
        component: () => import('@/pages/writepost.vue')
      },
      {
        path: 'writewiki',
        component: () => import('@/pages/writewiki.vue')
      },
      {
        path: 'table',
        component: () => import('@/pages/table.vue')
      },
      {
        path: 'createtable',
        component: () => import('@/pages/createtable.vue')
      },

      
      {
        path: 'chat',
        component: () => import('@/pages/chathome.vue')
      },
      {
        path: 'chatroom/:roomId',
        component: () => import('@/pages/chatroom.vue'),
        props: true
      },





      {
        path: '/search/:keyword',
        name: 'search',
        component: () => import('@/pages/dashboard.vue'),
        props: true
      },
      {
        path: '/search',
        component: () => import('@/pages/dashboard.vue'),
        props: true
      }
    ],
  },
  {
    path: '/',
    component: () => import('@/layouts/blank.vue'),
    children: [
      {
        path: 'login',
        component: () => import('@/pages/login.vue'),
      },
      {
        path: 'logout',
        component: () => import('@/pages/logout.vue'),
      },
      {
        path: 'register',
        component: () => import('@/pages/register.vue'),
      },
      {
        path: '/:pathMatch(.*)*',
        component: () => import('@/pages/[...error].vue'),
      },
    ],
  },
  // mypage
  {
    path: '/',
    component: () => import('@/layouts/mypage.vue'),
    children: [
      {
        path: 'mypage',
        component: () => import('@/pages/mypage.vue'),
      },
      {
        path: 'myinfo',
        component: () => import('@/pages/mypage.vue'),
      },
      {
        path: 'mytables',
        component: () => import('@/pages/mytables.vue'),
      },
      {
        path: 'myposts',
        component: () => import('@/pages/myposts.vue'),
      },
      {
        path: 'mywikis',
        component: () => import('@/pages/mywikis.vue'),
      },
      {
        path: 'myreplys',
        component: () => import('@/pages/myreplys.vue'),
      },
      {
        path: 'mylikes',
        component: () => import('@/pages/mylikes.vue'),
      },
      {
        path: 'mystars',
        component: () => import('@/pages/mystars.vue'),
      },

    ],
  },
]

export default routes