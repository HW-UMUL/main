<script setup>
</script>

<template>
  <VCard class="position-relative">
    <VCardText>
      <div class="mb-2">
        <div class="post-details">
            <div class="post-header">
                <span class="circle"></span>
                <span class="post-name">이름</span>
                <span class="post-title">제목</span>
                <span class="post-date">날짜</span>
            </div>
            <hr/>
            <p class="post-content">본문</p>
            <hr/>
            <div class="post-interactions">
                <span class="post-interactions-item">좋아요 수</span>
                <span class="post-interactions-item">즐겨찾기 수</span>
            </div>
            <hr/>
            <div class="post-comments">
                <p>댓글</p>
            </div>
        </div>
    </div>
    </VCardText>
  </VCard>
</template>

<style lang="scss">
.v-card .trophy {
  position: absolute;
  inline-size: 5.188rem;
  inset-block-end: 1.25rem;
  inset-inline-end: 1.25rem;
}
.post-image {
    width: 100px;
    height: 100px;
    border-radius: 50%; /* 원형 이미지를 위한 border-radius */
    margin-bottom: 10px;
}
.post-title {
    font-weight: bold;
    margin-bottom: 5px;
}
.post-date {
    color: #666;
    margin-bottom: 10px;
}
.post-content {
    margin-bottom: 10px;
}
.post-interactions {
    display: flex;
    justify-content: space-between;
    width: 100%;
    margin-bottom: 10px;
}
.post-interactions-item {
    color: #666;
}
.post-comments {
    color: #333;
}

.circle {
    width: 20px;
    height: 20px;
    background-color: red;
    border-radius: 50%; /* 원 모양의 동그라미를 만들기 위해 반지름을 50%로 설정합니다. */

    margin-bottom: 5px;

    display: inline-block;
}
</style>
