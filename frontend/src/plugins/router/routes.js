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
]

export default routes