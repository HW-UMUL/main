<script setup>

// 년-월-일
const formatDate = function(value) {
    const date = new Date(value);
    const year = date.getFullYear().toString().slice(-2);
    const month = (date.getMonth() + 1);
    const day = date.getDate();
    const hour = date.getHours();
    const min = date.getMinutes();
    const sec = date.getSeconds();
    
    return `${year}-${month}-${day} ${hour}:${min}`;
}

const auth = inject('auth')
const serverAddress = inject('serverAddress')

const sortDirection = ref('desc')
const sortBy = ref('id')

function togglesort(sortType) {
  if (sortBy.value === sortType) {
    sortDirection.value = sortDirection.value === 'asc' ? 'desc' : 'asc'
  } else {
    sortBy.value = sortType
    sortDirection.value = 'asc'
  }
  issort();
}

function issort() {
  sortBy.value === 'id'
  sortBy.value === 'name'
  sortBy.value === 'date'

  const checkedTableIds = checkedTables.value.map((checked, index) => checked ? sortedTables.value[index].id : null);
  sortedTables.value.sort((a, b) => {
    const compareResult = sortDirection.value === 'asc' ? -1 : 1
    switch (sortBy.value) {
      case 'id':
        return compareResult * (a.id - b.id);
      case 'name':
        return compareResult * (a.name.localeCompare(b.name))
      case 'date':
        return compareResult * (new Date(a.date) - new Date(b.date))
      default:
        return 0;
    }
  })
  checkedTableIds.value = sortedTables.value.map(table => checkedTableIds.includes(table.id));
}

const sortedTables = ref([])
const tables = ref([])

  async function getTables(){

  const response = await fetch(
      `http://${serverAddress}/api/table/readtables`,
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
    console.error(error)
  } else{
    tables.value = await response.json()
  }
  sortedTables.value = [...tables.value];
  issort()
}

getTables()

// 삭제
async function delTable(tableId) {
  const response = await fetch(
    `http://${serverAddress}/api/table/delete/${tableId}`,
    {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json'
      },
      'Authorization': `Bearer ${auth}`,
      credentials: 'include'
    }
  )
  if(!response.ok) {
    alert(postId+"삭제 실패")
    console.error(error)
  } else {
    getTables()
  }
}

const checkedTables = ref([])
async function delcheckedtables() {
  const delTableList = [];

  checkedTables.value.forEach((checked, index) => {
    if (checked) {
      delTableList.push(sortedTables.value[index].id);
    }
  })
  if(delTableList.length === 0) {
    alert("삭제할 게시물을 선택하십시오.");
    return;
  }
  const confirmdel = confirm("삭제하시겠습니까?");
  if(!confirmdel) return;

  try {
    for(const tableId of delTableList) {
    await delTable(tableId);
  }
  checkedTables.value = [];
  alert("삭제되었습니다");
  } catch(error) {
    console.error(error);
    alert("삭제 실패");
  }
}

const allChecked = ref(false);

function selectAllCheckboxes() {
  checkedTables.value = sortedTables.value.map(() => allChecked.value);
}

function selectIndividualCheckbox(index) {
  checkedTables.value[index] = !checkedTables.value[index];
  allChecked.value = checkedTables.value.every(checked => checked);
}
</script>

<template>
<div style="margin: 10px;
  display: flex;
  justify-content: space-between">
  <span
    style="font-size: small;
    margin-left: 10px;">
    total : {{ tables.length }}
  </span>
  <VIconBtn
    style="margin-right: 10px;
    cursor: pointer"
    @click="delcheckedtables"
    >
    삭제
  </VIconBtn>
</div>
<VTable
density="comfortable"
fixed-header
height="440"
>
  <thead>
    <tr>
      <th class="text-center">
        No
        <VIcon size="12pt"
        :icon="sortBy === 'id' ? (sortDirection === 'asc' ? 'ri-arrow-up-s-line' : 'ri-arrow-down-s-line') : 'ri-subtract-line'"
          @click="togglesort('id')" />
      </th>
      <th class="text-center" >
        Name
        <VIcon size="12pt"
        :icon="sortBy === 'name' ? (sortDirection === 'asc' ? 'ri-arrow-up-s-line' : 'ri-arrow-down-s-line') : 'ri-subtract-line'"
          @click="togglesort('name')" />
      </th>
      <th class="text-center">
        Description
      </th>
      <th class="text-center">
        date
        <VIcon size="12pt"
        :icon="sortBy === 'date' ? (sortDirection === 'asc' ? 'ri-arrow-up-s-line' : 'ri-arrow-down-s-line') : 'ri-subtract-line'"
          @click="togglesort('date')" />
      </th>
      <th class="text-center">
        <input 
          type="checkbox"
          v-model="allChecked"
          @change="selectAllCheckboxes">
      </th>
    </tr>
  </thead>

  <tbody>
    <tr v-for="(item, index) in sortedTables" :key="index">
      <td class="text-center" style="width: 60pt">
        {{ item.id }}
      </td>
      <td class="text-center">
        {{ item.name }}
      </td>
      <td class="text-center">
        {{ item.description }}
      </td>
      <td class="text-center">
        {{ formatDate(item.date) }}
      </td>
      <td class="text-center" style="width: 50px;">
        <input 
          type="checkbox"
          :checked="checkedTables[index]"
          @change.stop="selectIndividualCheckbox(index)"
          >
      </td>
    </tr>
  </tbody>
</VTable>
</template>