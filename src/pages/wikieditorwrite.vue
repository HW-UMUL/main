<script> 
  import TextEditor from '@/pages/texteditor.vue';
import StarterKit from '@tiptap/starter-kit';
  
  export default {
    name: "NoticePost",
  
    components: { TextEditor },
  
    data() {
      return {
        editor: null,
      };
    },
  
    mounted() {
      this.editor = new Editor({
        content: '<p>running tiptap with vue.js</p>',
        extensions: [
          StarterKit,
        ]
      })
    }, 
    
    beforeUnmount() {
      this.editor.destroy();
    }
  }

  </script>

<template>
  <div>
      <text-editor v-model="content" />
      <button @click="saveHandler">저장하기</button>
  </div>
</template>
