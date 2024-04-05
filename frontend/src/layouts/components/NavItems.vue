<script setup>
import VerticalNavSectionTitle from '@/@layouts/components/VerticalNavSectionTitle.vue';
import VerticalNavGroup from '@layouts/components/VerticalNavGroup.vue';
import VerticalNavLink from '@layouts/components/VerticalNavLink.vue';

</script>

<template>
    <VerticalNavGroup
      :item="{
        title: 'My Page',
        icon: 'ri-user-smile-line'
      }"
    >
      <VerticalNavLink
      :item=" {
        title: 'Account Setting',
        to: '/mypage',
        icon: 'ri-home-line'      
      }"/>

      <VerticalNavLink
      :item=" {
        title: 'Post',
        to: '/myposts',
        icon: 'ri-chat-smile-line'      
      }"/>

      <VerticalNavLink
      :item=" {
        title: 'Wiki',
        to: '/mywikis',
        icon: 'ri-book-line'      
      }"/>

      <VerticalNavLink
      :item=" {
        title: 'Reply',
        to: '/myreplys',
        icon: 'ri-reply-line'      
      }"/>

      <VerticalNavLink
      :item=" {
        title: 'Star',
        to: '/mystars',
        icon: 'ri-star-line'      
      }"/>

      <VerticalNavLink
      :item=" {
        title: 'Like',
        to: '/mylikes',
        icon: 'ri-heart-line'      
      }"/>      

  </VerticalNavGroup>

  <VerticalNavLink
    :item=" {
      title: 'Post',
      to: '/dashboard',
      icon: 'ri-chat-smile-line'      
    }">    
  </VerticalNavLink>

  <VerticalNavLink
      :item="{
        title: 'Wiki',
        to: '/wiki',
        icon:'ri-book-line'
      }"
  />
  
  <VerticalNavLink
    :item="{
      title: 'Login',
      icon: 'ri-login-box-line',
      to: '/login',
    }"
  />
  <VerticalNavLink
    :item="{
      title: 'Logout',
      icon: 'ri-login-box-line',
      to: '/logout',
    }"
  />

  <VerticalNavLink
    :item="{
      title: 'Register',
      icon: 'ri-user-add-line',
      to: '/register',
    }"
  />

  <VerticalNavLink
    :item="{
      title: 'Table',
      icon: 'ri-group-line',
      to: '/table',
    }"
  /> 

  <VerticalNavLink
    :item="{
      title: 'Chat',
      icon: 'ri-chat-1-line',
      to: '/chat',
    }"
  /> 

  <VerticalNavLink
    :item="{
      title: 'Follow',
      icon: 'ri-user-heart-line',
      to: '/follow',
    }"
  /> 

</template>
