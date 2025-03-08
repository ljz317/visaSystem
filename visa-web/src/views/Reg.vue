<script setup>

import { UploadFilled } from '@element-plus/icons-vue'
import {ref} from "vue";
import axios from "axios";
import qs from "qs";
import {ElMessage} from "element-plus";
import { onMounted } from 'vue';
import router from "@/router";
onMounted(() => {
  console.log("页面已挂载，执行一些操作");
});

const types=ref([
  {type:"期限あり",value:"student"},
  {type: "期限なし",value:"teacher"},
])
const classes=ref([

])
const classs=ref("");

const user=ref({
  name:"",
  username:"",
  password:"",
  phone:"",
  address:"",
  type:"",
  email:"",
  salary:"",
  empCode:"",
  date:""
})
user.value.type='期限あり';
const showTeacher=ref(false);
const changed=()=>{
  if(user.value.type=="teacher"){
    showTeacher.value=true;
  }else{
    showTeacher.value=false;
  }
}

const reg = () => {
  console.log(user.value);
  let data = qs.stringify(user.value);
}
</script>

<template>

  <div  class="container">
    <div id="asd" style="width:100%;margin:0 auto;">
      <el-card style="max-width: 800px;margin:0 auto;min-width: 450px;">
        <template #header>
          <span style="font-weight: bold;color: #0aa1ed;font-size: 30px;">新規登録</span>
        </template>

        <el-form  label-position="left" label-width="130" style=" font-weight: bold;color:#333;">


          <el-form-item label="ユーザー名">
            <el-input v-model="user.username" placeholder="アカウント名"></el-input>
          </el-form-item>
          <el-form-item label="パスワード">
            <el-input show-password v-model="user.password" placeholder="パスワード"></el-input>
          </el-form-item>
          <el-form-item label="名前">
            <el-input v-model="user.name" placeholder="例：サカキ　エリコ"></el-input>
          </el-form-item>
          <el-form-item label="携帯番号">
            <el-input v-model="user.phone" placeholder="携帯番号"></el-input>
          </el-form-item>
          <el-form-item label="住所">
            <el-input v-model="user.address" placeholder="例：東京都千代田2-10-13 401"></el-input>
          </el-form-item>
          <el-form-item label="メール">
            <el-input v-model="user.email" placeholder="例：12345@abc.com"></el-input>
          </el-form-item>

          <el-form-item label="ビザタイプ">
            <el-select v-on:change="changed()" v-model="user.type" placeholder="ビザタイプ">
              <el-option
                  v-for="item in types"
                  :label="item.type"
                  :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>


          <el-form-item label="ビザ写真"  v-show="!showTeacher">
            <el-upload
                class="upload-demo"
                drag
                action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
                limit="2"
                multiple
            >
              <el-icon class="el-icon--upload"><upload-filled /></el-icon>
              <div>
                ビザ正面と裏側をアップロードしてください
              </div>
            </el-upload>
          </el-form-item>
          <el-form-item label="ビザ期限日"  v-show="!showTeacher">
            <el-date-picker
                v-model="user.date"
                type="date"
                placeholder="期限日"
                clearable
            />
          </el-form-item>

          <el-form-item label="admin code(任意)" v-show="showTeacher">
            <el-input v-model="user.salary" placeholder=""></el-input>
          </el-form-item>

        </el-form>
        <template #footer>
          <el-button style="margin:0 auto; width: 100px;" @click="reg()" type="success">注册</el-button>
        </template>



      </el-card>
    </div>
  </div>
</template>

<style>
.container {
  display: flex;
  justify-content: center;  /* 水平居中 */
  align-items: center;      /* 垂直居中 */
  height: 100vh;            /* 设置容器的高度为视口高度 */
}
</style>