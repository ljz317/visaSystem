<script setup>
import {onMounted, ref} from "vue";
import axios from "axios";
import qs from "qs";
import {ElMessage} from "element-plus";

const loginItem=ref([
  {viewName:'アカウント',name:"username",value:"",id:0},
  {viewName:'パスワード',name:"password",value:"",id:1}
]);
const pdfInfo=ref({});
const downloadPdf=()=>{
  axios.get('http://localhost:8080/pdf/create?type=0', {
    responseType: 'blob'  // 设置返回类型为 Blob，用于处理二进制数据（如PDF）
  }).then(response => {
    const blob = response.data;  // 获取返回的 Blob 数据
    const link = document.createElement('a');  // 创建一个 <a> 标签用于下载
    link.href = window.URL.createObjectURL(blob);  // 创建下载链接
    link.download = 'contract.pdf';  // 指定下载的文件名
    link.click();  // 模拟点击下载
  }).catch(error => {
    console.error('Error downloading the PDF:', error);
  });
}
onMounted(()=>{
  if(localStorage.user){
    if(localStorage.user.isAdmin===1){
      document.location.href="http://localhost:8080/home";
    }
  }
})


//登录处理
const login=()=>{
  let user={username:loginItem.value[0].value,password:loginItem.value[1].value};
  console.log(user);
  var data = qs.stringify(user);
  axios.post('http://localhost:8080/user/login',data).then(response => {
    if(response.data.code === 2001){
      ElMessage.success(response.data.msg);
      console.log("response.data.data");
      console.log(response.data.data);
      localStorage.setItem("user",JSON.stringify(response.data.data));

      if(localStorage.user){
        if(JSON.parse(localStorage.getItem("user")).isAdmin===1){
          document.location.href="http://localhost:9090/home";
          ElMessage.success('admin '+"さん、ようこそ");
        }else{
          alert("您不是管理员")
        }
      }
    }else{
      ElMessage.warning(response.data.msg);
    }
  })
}
</script>
<style>
body{
  background-color: rgba(0,0,0,0.1);
}
.container {
  display: flex;
  justify-content: center;  /* 水平居中 */
  align-items: center;      /* 垂直居中 */
  height: 100vh;            /* 设置容器的高度为视口高度 */
}

</style>
<template>
  <div class="container">
    <div style=" margin:0 auto; width: 400px">
      <el-card class="box-card">
        <div style="border-bottom: 1px solid rgba(0,0,0,0.1);margin-bottom: 30px;padding:5px;">
          <span style="font-size: 18px ;font-weight: bold;color: #666;" >ビザ管理システム</span>
        </div>

        <div style="margin-top:20px;">
          <el-form :model="loginItem" label-width="100px">
            <el-form-item v-for="o in loginItem" :label="o.viewName+':'">
              <el-input v-model="o.value"  :placeholder="o.viewName+'を入力してください'"></el-input>
            </el-form-item>
          </el-form>
        </div>

        <div style="margin-top: 30px;">
          <el-button type="primary" @click="login()">ログイン</el-button>
          <router-link to="/reg"><p style="font-size: 10px;margin:10px 0 0 0;text-decoration: none;">立即注册</p></router-link>
          <el-button type="primary" @click="downloadPdf()">downloadpdf</el-button>
        </div>
      </el-card>
    </div>
  </div>

</template>
<style>

</style>
