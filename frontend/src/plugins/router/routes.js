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
        path: 'account-settings',
        component: () => import('@/pages/account-settings.vue'),
      },
      {
        path: 'typography',
        component: () => import('@/pages/typography.vue'),
      },
      {
        path: 'icons',
        component: () => import('@/pages/icons.vue'),
      },
      {
        path: 'cards',
        component: () => import('@/pages/cards.vue'),
      },
      {
        path: 'tables',
        component: () => import('@/pages/tables.vue'),
      },
      {
        path: 'form-layouts',
        component: () => import('@/pages/form-layouts.vue'),
      },
      {
        path: 'wikieditorwrite',
        component: () => import('@/pages/wikieditorwrite.vue'),
      },
      {
        path: 'wikiwrite',
        component: () => import('@/pages/wikiwrite.vue'),
      },
      {
        path: 'wikilist',
        component: () => import('@/pages/wikilist.vue'),
      },
      {
        path: 'readwiki',
        component: () => import('@/pages/readwiki.vue'),
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
        path: 'register',
        component: () => import('@/pages/register.vue'),
      },
      {
        path: 'wikieditorwrite',
        component: () => import('@/pages/wikieditorwrite.vue'),
      },
      {
        path: 'wikiwrite',
        component: () => import('@/pages/wikiwrite.vue'),
      },
      {
        path: '/:pathMatch(.*)*',
        component: () => import('@/pages/[...error].vue'),
      },
      {
        path: 'wikilist',
        component: () => import('@/pages/wikilist.vue'),
      },
    ],
  },
]
