import ReadWiki from '@/wiki/readwiki.vue';
import UpdateWiki from '@/wiki/updatewiki.vue';
import ViewWikiLogs from '@/wiki/viewwikilogs.vue';
import { createRouter, createWebHistory } from 'vue-router';
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
        component: () => import('@/wiki/wikieditorwrite.vue'),
      },
      {
        path: 'wikiwrite',
        component: () => import('@/wiki/wikiwrite.vue'),
      },
      {
        path: 'wikilist',
        component: () => import('@/wiki/wikilist.vue'),
      },
      {
        path: '/readwiki/:id',
        name: 'readwiki',
        component: ReadWiki,
        props: true // URL 파라미터를 컴포넌트의 props로 전달합니다.
      }, 
      {
        path: '/updatewiki/:id',
        name: 'updatewiki',
        component: UpdateWiki,
        props: true // URL 파라미터를 컴포넌트의 props로 전달합니다.
      },
      {
        path: '/viewwikilogs/:id',
        name: 'viewwikilogs',
        component: ViewWikiLogs,
        props: true
      },
      {
        path: 'wikieditorwrite',
        component: () => import('@/wiki/wikieditorwrite.vue'),
      },
      {
        path: 'wikieditor',
        component: () => import('@/wiki/wikieditor.vue'),
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
        path: 'wikiwrite',
        component: () => import('@/wiki/wikiwrite.vue'),
      },
      {
        path: '/:pathMatch(.*)*',
        component: () => import('@/pages/[...error].vue'),
      },
      {
        path: 'wikilist',
        component: () => import('@/wiki/wikilist.vue'),
      },
      {
        path: 'wikieditorwrite',
        component: () => import('@/wiki/wikieditorwrite.vue'),
      },
      {
        path: 'wikieditor',
        component: () => import('@/wiki/wikieditor.vue'),
      }
    ],
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes
});


export default router;
