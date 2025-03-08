<script setup>
import {onMounted, ref} from "vue";
import { checkBefore } from "@/views/js/CheckLogin";
import axios from "axios";
import {ElMessage} from "element-plus";
import qs from "qs";

const emp = ref([
  {
    id:null,
    name: 'Tom',
    remaining:500,
    visaEnd:"2016-05-03",
    opeStatus:"処理待ち",
  },{
    id:null,
    name: 'Tom',
    remaining:500,
    visaEnd:"2016-05-03",
    opeStatus:"処理待ち",
  },{
    id:null,
    name: 'Tom',
    remaining:500,
    visaEnd:"2016-05-03",
    opeStatus:"処理待ち",
  },{
    id:null,
    name: 'Tom',
    remaining:500,
    visaEnd:"2016-05-03",
    opeStatus:"処理待ち",
  },
])

onMounted(()=>{
  checkBefore();
  axios.get("http://localhost:8080/emp/list").then(res => {
    if(res.data.code == 2001){
      emp.value=res.data.data;
    }else{
      ElMessage.warning(res.data.msg)
    }
  })
})



//搜索模式
const lookType=ref([
  {name:"名前で検索"},
  {name:"時間で検索(何日以下)"}
]);
const selectedMode=ref("名前で検索");


//搜索框
const searchMes=ref("名前を入力してください");
const state = ref('')

const LinkItem =ref({
  value: "",
  link: ""
})

const links = ref([])

const loadTime = () => {
   return [
    { value: '200'},
    { value: '60'},
    { value: '100'},
  ]

}
let timeout;
const querySearchAsync = (queryString, cb) => {
  const results = queryString
      ? links.value.filter(createFilter(queryString))
      : links.value;
  clearTimeout(timeout);
  timeout = setTimeout(() => {
    cb(results);
  }, 300 * Math.random());
};

const createFilter = (queryString) => {
  return (restaurant) => {
    return restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0;
  }
}

const handleSelect = (item) => {

}
//更改模式
const modeChanged=()=>{
  if(selectedMode.value===lookType.value[0].name){
    axios.get("http://localhost:8080/emp/search/list").then(res => {
      if(res.data.code === 2001){
        links.value=res.data.data;
      }else{
        ElMessage.warning(res.data.msg)
      }
    })
    //links.value = loadTime();
  }else{
    links.value = loadTime();
  }
}
const loadList=()=>{
  axios.get("http://localhost:8080/emp/search/list").then(res => {
    if(res.data.code === 2001){
      links.value=res.data.data;
    }else{
      ElMessage.warning(res.data.msg)
    }
  })
}
//加载搜索名字
onMounted(() => {
  if(selectedMode.value===lookType.value[0].name){
    loadList();
    //links.value = loadTime();
  }else{
    links.value = loadTime();
  }
})

const addNewKey=()=>{
  for (let i = 0; i < links.value.length; i++) {
    if(links.value[i].value===state.value){
      return;
    }
  }
  links.value.push({value:state.value,link:''});
}
//开始搜索
const searchName=()=>{
  addNewKey();
  if(state.value===undefined||state.value===null){
    return;
  }
  axios.get("http://localhost:8080/emp/search/name?name="+state.value).then(res => {
    if(res.data.code === 2001){
      if(!Array.isArray(res.data.data)){
        emp.value=[];
        emp.value.push(res.data.data);
      }else{
        emp.value=res.data.data;
      }
    }else{
      ElMessage.warning(res.data.msg)
    }
  })
}
const searchTime=()=>{
  addNewKey();
  if(state.value===undefined||state.value===null){
    return;
  }
  if(state.value===""){
    state.value=0;
  }
  axios.get("http://localhost:8080/emp/search/time?day="+state.value).then(res => {
    if(res.data.code === 2001){
      if(res.data.data===null){
        emp.value=[];
        return;
      }
      if(!Array.isArray(res.data.data)){
        emp.value=[];
        emp.value.push(res.data.data);
      }else{
        emp.value=res.data.data;
      }
    }else{
      ElMessage.warning(res.data.msg)
    }
  })
}
const search=()=>{
  alert(state.value)
  if(lookType.value[0].name===selectedMode.value){
    searchName();
  }else if(lookType.value[1].name===selectedMode.value){
    searchTime();
  }
}

//高亮显示
const tableRowClassName = ({row, rowIndex}) => {
  if (row.remaining<100&&row.remaining!==0) {
    return 'success-row';
  } else if (row.remaining<80&&row.remaining!==0) {
    return 'warning-row';
  }
  return '';
};

//搜索框修改
const searchChange=()=>{
  if(state.value===""){
    loadList();
  }
}

//打印
const print=()=>{
  //TODO:::打印动态录入表格
  window.open("http://localhost:8080/pdf/emp/list/download");
}
</script>

<template>
  <!--头部搜索栏-->
  <div style="height: 50px;padding: 10px; width: 100%; border-bottom: 1px solid rgba(0,0,0,0.1);">
    <el-row :gutter="0">
      <el-col :span="10">
        <!--左面部分搜索模式-->
        <el-form ref="form" label-width="90">
          <el-form-item label="検索モード">
            <el-select v-model="selectedMode">
              <el-option
                  :onchange="modeChanged()"
                  v-for="item in lookType"
                  :label="item.name"
                  :value="item.name">
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
      </el-col>
      <el-col :span="14">
        <!--右面部分-->
        <el-form ref="form" label-width="40" style="width: 100%;">
          <el-form-item>
            <el-row :gutter="0">
              <el-col :span="16">
                <el-autocomplete
                    v-model="state"
                    :fetch-suggestions="querySearchAsync"
                    :placeholder="selectedMode"
                    @select="handleSelect"
                    @change="searchChange()"
                    style="min-width: 100px;"
                />
              </el-col>
              <el-col :span="4">
                <el-button type="primary" @click="search()" style="margin-left: 15px;">検索</el-button>
              </el-col>
              <el-col :span="4">
                <el-button type="info" @click="print()" style="margin-left: 15px;">プリント</el-button>
              </el-col>

            </el-row>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
  <div style="min-height: 500px;">
    <el-table :data="emp" style="text-align: center;width: 100%" :row-class-name="tableRowClassName">
      <el-table-column align="center" type="index" label="番号" min-width="90" />
      <el-table-column align="center" prop="name" label="名前" min-width="90" />
      <el-table-column align="center" prop="remaining" label="残り時間"/>
      <el-table-column align="center" prop="visaEnd" label="期限日" min-width="100" />
      <el-table-column align="center" prop="opeStatus" label="進捗状況" min-width="100" />
      <el-table-column align="center" label="操作"  min-width="150">
        <template #default="scope">
          <el-button type="primary"><router-link :to="'/'+scope.row.id+'/detail/'">詳細</router-link></el-button>
          <el-button type="primary"><router-link :to="'/'+scope.row.id+'/update/'">更新</router-link></el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

</template>

<style scoped>
.container {
  display: flex;
  justify-content: center;  /* 水平居中 */
  align-items: center;      /* 垂直居中 */
  height: 100vh;            /* 设置容器的高度为视口高度 */
}

  /* 使用 ::v-deep（Vue 3 语法） */
  ::v-deep(.el-table .warning-row) {
    --el-table-tr-bg-color: var(--el-color-warning-light-9);
  }

  ::v-deep(.el-table .success-row) {
    --el-table-tr-bg-color: var(--el-color-success-light-9);
  }
</style>