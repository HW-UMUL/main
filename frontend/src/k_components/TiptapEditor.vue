<script setup>
import Document from '@tiptap/extension-document';
import { Editor, EditorContent } from '@tiptap/vue-3';
import StarterKit from '@tiptap/starter-kit';
import { watch } from 'vue';
import { Color } from '@tiptap/extension-color';
import Paragraph from '@tiptap/extension-paragraph';
import Text from '@tiptap/extension-text';
import TextStyle from '@tiptap/extension-text-style';
import Image from '@tiptap/extension-image';

const props = defineProps ({
    modelValue: String
})
const emit = defineEmits(['update:modelValue'])
const editor = ref(null)

editor = new Editor({
  extensions: [
    Document,
    Paragraph,
    StarterKit,
    Text,
    TextStyle,
    Color,
    Image
    
  ],
  content: props.modelValue,
  onUpdate: () => {
    emit('update:modelValue', editor.getHTML())
  }
})

const addImage = () => {
  const url = window.prompt('URL')

  if (url) {
    editor.value.chain().focus().setImage({ src: url }).run()
  }
}

// onBeforeUnmount(() => {
//   editor.value.destroy()
// })


watch(() => props.modelValue, (value) => {
  if (editor && editor.getHTML() !== value) {
    editor.commands.setContent(value, false)
  }
})

</script>

<template>
    <div>
      <input
        type="color"
        @input="editor.chain().focus().setColor($event.target.value).run()"
        :value="editor.getAttributes('textStyle').color"
        class="mb-2"
      >
      <button class="btn" @click="editor.chain().focus().setColor('#F98181').run()" :class="{ 'is-active': editor.isActive('textStyle', { color: '#F98181' })}"
        style="color: #F98181">
        red
      </button>
      <button class="btn" @click="editor.chain().focus().setColor('#FBBC88').run()" :class="{ 'is-active': editor.isActive('textStyle', { color: '#FBBC88' })}"
        style="color: #FBBC88">
        orange
      </button>
      <button class="btn" @click="editor.chain().focus().setColor('#FAF594').run()" :class="{ 'is-active': editor.isActive('textStyle', { color: '#FAF594' })}"
        style="color: #FAF594">
        yellow
      </button>
      <button class="btn" @click="editor.chain().focus().setColor('#B9F18D').run()" :class="{ 'is-active': editor.isActive('textStyle', { color: '#B9F18D' })}"
        style="color: #B9F18D">
        green
      </button>
      <button class="btn" @click="editor.chain().focus().setColor('#70CFF8').run()" :class="{ 'is-active': editor.isActive('textStyle', { color: '#70CFF8' })}"
        style="color: #70CFF8">
        blue
      </button>
      <button class="btn" @click="editor.chain().focus().setColor('#94FADB').run()" :class="{ 'is-active': editor.isActive('textStyle', { color: '#94FADB' })}"
        style="color: #94FADB">
        teal
      </button>
      <button class="btn" @click="editor.chain().focus().setColor('#958DF1').run()" :class="{ 'is-active': editor.isActive('textStyle', { color: '#958DF1' })}"
        style="color: #958DF1">
        purple
      </button>
      <button class="btn" @click="editor.chain().focus().unsetColor().run()">
        unsetColor
      </button>
    </div>

    <div>
      <button @click="addImage">Image</button>
      <editor-content :editor="editor" />
    </div>
</template>

<style lang="scss">
.tiptap {
  > * + * {
    margin-top: 0.75em;
  }

  ul,
  ol {
    padding: 0 1rem;
  }

  h1,
  h2,
  h3,
  h4,
  h5,
  h6 {
    line-height: 1.1;
  }

  code {
    background-color: rgba(#616161, 0.1);
    color: #616161;
  }

  pre {
    background: #0D0D0D;
    color: #FFF;
    font-family: 'JetBrainsMono', monospace;
    padding: 0.75rem 1rem;
    border-radius: 0.5rem;

    code {
      color: inherit;
      padding: 0;
      background: none;
      font-size: 0.8rem;
    }
  }

  img {
    max-width: 100%;
    height: auto;
  }

  blockquote {
    padding-left: 1rem;
    border-left: 2px solid rgba(#0D0D0D, 0.1);
  }

  hr {
    border: none;
    border-top: 2px solid rgba(#0D0D0D, 0.1);
    margin: 2rem 0;
  }
}

.btn{
  margin-right: 10px;
  font-size: 12pt;
  font-weight: 700;
}
</style>