<script setup>
import avatar1 from '@images/avatars/avatar-1.png';
import { useRouter } from "vue-router"

const serverAddress = inject('serverAddress')
const profileAddress = inject('profileAddress')


const auth = inject('auth')
const router = useRouter()

const emailKeyword = ref('')
const emails = ref([])

async function searchEmail(){

  const response = await fetch(
      `http://${serverAddress}/api/search/email/${emailKeyword.value}`,
      {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${auth}`,          
        },
        credentials: 'include'
      }
  )

  if(!response.ok) {
    alert("실패!")
  } else{
    emails.value = await response.json()
  }
}

async function checkFollow(email){

  const response = await fetch(
      `http://${serverAddress}/api/follow/check/${email}`,
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${auth}`,          
        },
        credentials: 'include'
      }
  )

  if(!response.ok) {
    alert("실패!")
  } else{
    getFollowers()
    getFollowees()
  }
}

const followers = ref([])
const followees = ref([])


async function getFollowers(){

  const response = await fetch(
      `http://${serverAddress}/api/follow/read/follower`,
      {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${auth}`,          
        },
        credentials: 'include'
      }
  )

  if(!response.ok) {
    alert("실패!")
  } else{
    followers.value = await response.json()
    settingFollowerEmails(followers)
  }
}

async function getFollowees(){

  const response = await fetch(
      `http://${serverAddress}/api/follow/read/followee`,
      {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${auth}`,          
        },
        credentials: 'include'
      }
  )

  if(!response.ok) {
    alert("실패!")
  } else{
    followees.value = await response.json()
    settingFolloweeEmails(followees)
  }
}

const switchContent = ref(true)

function isFollowed(email){
 
  if(followerEmails.value.includes(email)){
    return 'unFollow'
  }
  else{
    return 'Follow'
  }
}

getFollowees()
getFollowers()

//////////// 이메일 계산하기
function settingFollowerEmails(followers){
  followerEmails.value = []
  followers.value.forEach(function(item){
    followerEmails.value.push(item.follower.email)
  })
}
function settingFolloweeEmails(followees){
  followeeEmails.value = []
  followees.value.forEach(function(item){
    followeeEmails.value.push(item.followee.email)
  })  
}

const followerEmails = ref([])
const followeeEmails = ref([])

////////////////////////////////////////////////////

//////////// 다른 사람 페이지 

function moveToOther(userId){
  router.push({ path: `/user/${userId}` })
}


////////////////////////////////////////////////////

</script>

<template>
  <VRow>
    <VCol
      cols="12"
      md="8"
      class="mb-4"
    >
    <VRow style="height: 30px; margin-bottom: 20px;">
      <div class="selectFollowerFollowee" @click="switchContent=true">FOLLOWER</div>
      <div class="bar"></div>
      <div class="selectFollowerFollowee" @click="switchContent=false">FOLOWEE</div>
    </VRow>

    <VCard class="position-relative">
        <div v-if="switchContent" v-for="(item, index) in followers" :key="index">          
          <div class="d-flex">
            <div  @click="moveToOther(item.follower.id)">
              <img v-if="!item.follower.profile" class="follow-propile-img" :src="avatar1">
              <img v-else class="follow-propile-img" :src="profileAddress + item.follower.profile.storeFileName">
            </div>
            <div class="pa-2" style="margin-top:18px; vertical-align: center;">{{ item.follower.email }}</div>
            <VBtn class="ml-auto" style="margin-top: 18px; margin-right: 10px;"
            @click="checkFollow(item.follower.email)">
              UnFollow
            </VBtn>
          </div>
          <VDivider />
        </div>
        <div v-if="!switchContent" v-for="(item, index) in followees" :key="index">          
          <div class="d-flex">
            <div  @click="moveToOther(item.followee.id)">
              <img v-if="!item.followee.profile" class="follow-propile-img" :src="avatar1">
              <img v-else class="follow-propile-img" :src="profileAddress + item.followee.profile.storeFileName">
            </div>
            <div class="pa-2" style="margin-top:18px; vertical-align: center;">{{ item.followee.email }}</div>
            <VBtn class="ml-auto" style="margin-top: 18px; margin-right: 10px;"
            @click="checkFollow(item.followee.email)">
              {{ isFollowed(item.followee.email) }}
            </VBtn>
          </div>
          <VDivider />
        </div>
    </VCard>

 
    </VCol>  
    <VCol
      cols="12"
      md="4"
    >
      <!-- 검색 -->
      <VCard title="Search" class="position-relative">
        <VCardText>
                  <VTextField
                  v-model="emailKeyword"
                  placeholder="email"
                  label="email"
                  >
                    <IconBtn style="position:absolute; right:0;"
                    @click="searchEmail"
                    >
                      <VIcon icon="ri-search-line"/>
                    </IconBtn>
                </VTextField>
          <VDivider />

          <!-- 나중에 입력 출력 통일하고 하는게 좋을듯 함. -->
          
            <div v-for="(item, index) in emails" :key="index">
              {{ item }}
              <VBtn
              @click="checkFollow(item)">
                {{ isFollowed(item) }}
              </VBtn>
            </div>



        </VCardText>
      </VCard>
    </VCol>  

  </VRow>
</template>
<style lang="scss">
.selectFollowerFollowee{
  align-items: center;
  width: 49%;
  display: flex;
  justify-content: center;
  font-size: 20px; 
}

.bar {
    width: 1px; /* 바의 너비 */
    background-color: gray; /* 바의 배경색 */
}

.follow-propile-img {
  margin-top: 10px;
  margin-left: 10px;
   width: 50px;
   height: 50px;
  border-radius: 50%;
  object-fit: cover;
}
</style>
