<script setup>

import {onMounted, ref} from "vue";
import router from "@/router";
import {useRoute} from "vue-router";
import axios from "axios";
import {ElMessage} from "element-plus";
const index=ref(null)





//个人情报
const user=ref({
  phone:"1",
  address:"1",
  email:"1",
  name:"1",
  visaType:"1",
  visaImg:"1",
  visaEnd:"1",
  opeStatus:"1",
  updateTime:"1",
  createTime:"1",
  username:"1",
  lastLogin:"1",
  empId:"1",
  remaining:70
})
let id;
onMounted(()=>{
  const currentUrl=new URLSearchParams(window.location.search);
  index.value=currentUrl.get("id");

  const route = useRoute();  // 获取当前路由
  id = route.params.id;
  ElMessage.info(id);
  updateInfoView();
})

const userInfoView=ref([
  {name:"名前",des:"name",value:user.value.name},
  {name:"携帯番号",des:"phone",value:user.value.phone},
  {name:"住所",des:"address",value:user.value.address},
  {name:"メール",des:"email",value:user.value.email}
])
const dateInfoView=ref([
  {name:"アカウント名",des:"username",value:user.value.username},
  {name:"作成日",des:"createTime",value:user.value.createTime},
  {name:"更新日",des:"updateTime",value:user.value.updateTime},
  {name:"最終ログイン日",des:"lastLogin",value:user.value.lastLogin},
])
const visaInfoView=ref([
  {name:"ビザタイプ",des:"visaType",value:user.value.visaType},
  {name:"ビザ期限",des:"visaEnd",value:user.value.visaEnd},
  {name:"残り時間",des:"visaDuring",value:50}
])

const updateInfoView=()=>{

  axios.get("http://localhost:8080/emp/"+id+"/info").then((res)=>{
    if(res.data.code===2001){
      user.value=res.data.data;
      userInfoView.value=[
        {name:"名前",des:"name",value:user.value.name},
        {name:"携帯番号",des:"phone",value:user.value.phone},
        {name:"住所",des:"address",value:user.value.address},
        {name:"メール",des:"email",value:user.value.email}
      ];


      dateInfoView.value=[
        {name:"アカウント名",des:"username",value:user.value.username},
        {name:"作成日",des:"createTime",value:user.value.createTime},
        {name:"更新日",des:"updateTime",value:user.value.updateTime},
        {name:"最終ログイン日",des:"lastLogin",value:user.value.lastLogin},
      ];

      visaInfoView.value=[
        {name:"ビザタイプ",des:"visaType",value:user.value.visaType},
        {name:"ビザ期限",des:"visaEnd",value:user.value.visaEnd},
        {name:"残り時間",des:"visaDuring",value:user.value.remaining}
      ];

      if(user.value.visaImg===undefined||user.value.visaImg===null){
        alert("null");
        return;
      }
      urls.value=[];
      user.value.visaImg.toString().split(";").forEach((u,index)=>{

        urls.value.push("http://localhost:8080/"+u);
      })
    }
  })
}


//照片处理
const urls = ref([
  'https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg',
  'https://fuss10.elemecdn.com/1/34/19aa98b1fcb2781c4fba33d850549jpeg.jpeg',
  'https://fuss10.elemecdn.com/0/6f/e35ff375812e6b0020b6b4e8f9583jpeg.jpeg',
  'https://fuss10.elemecdn.com/9/bb/e27858e973f5d7d3904835f46abbdjpeg.jpeg',
  'https://fuss10.elemecdn.com/d/e6/c4d93a3805b3ce3f323f7974e6f78jpeg.jpeg',
  'https://fuss10.elemecdn.com/3/28/bbf893f792f03a54408b3b7a7ebf0jpeg.jpeg',
  'https://fuss10.elemecdn.com/2/11/6535bcfb26e4c79b48ddde44f4b6fjpeg.jpeg',
])

//编辑显示
const userInfoShow=ref(false);
const changeMode = (mode) => {
  userInfoShow.value = !userInfoShow.value;
  if(mode === "update"){
    console.log("更新");
    //TODO:更新员工基本信息
    user.value.name=userInfoView.value[0].name;
    user.value.phone=userInfoView.value[1].name;
    user.value.address=userInfoView.value[2].name;
    user.value.email=userInfoView.value[3].name;
  }
};
const print=()=>{
  //TODO:打印使用模板
  window.open("http://localhost:8080/pdf/emp/"+id+"/download");

}


</script>

<template>
  <div style="width:100%;min-height: 1200px;">
    <div style="min-height: 40px; ">
      <p style="font-size: 20px;margin-bottom: 5px;font-weight: bold;color: #333333;">個 人 情 報</p>

    </div>

    <div style="width:80%;margin:0 auto;">
      <el-card class="box-card" body-style="padding:0;" style="margin-bottom: 20px;">
        <div slot="header" style="background-color:  rgba(49,52,59,0.8);position: relative; height: 60px;">
          <p style="margin:0;color: white;font-weight: bold; position: absolute;left: 30px;top: 20px;">個人情報</p>
          <el-button type="info" v-if="!userInfoShow" style="float: right;margin: 15px 20px 0 0;width: 70px;" @click="changeMode('edit')">編集</el-button>
          <el-button type="info" v-if="!userInfoShow" style="float: right;margin: 15px 20px 0 0;width: 70px;" @click="print()">プリント</el-button>
          <el-button type="info" v-else style="float: right;margin: 15px 20px 0 0;width: 70px;" @click="changeMode('update')">确定</el-button>
        </div>
        <div style="padding: 20px;color: white;background-color:rgba(38,38,44,1);min-height: 140px;">
          <el-row  :gutter="20" v-for="u in userInfoView" style="margin-top: 10px;">
            <el-col :span="8" style="margin-top: 14px;" >
              <span style="color: #666;font-size: 14px;font-weight: bold;">{{u.name}}</span>
            </el-col>
            <el-col :span="16" style="">
              <el-input  v-if="userInfoShow" style="margin-top: 2px" v-model="u.value" placeholder="placeholder"></el-input>
              <div v-else  style="margin-top: 14px;color: white;font-size: 15px;font-weight: bold;" v-text="u.value"></div>
            </el-col>
          </el-row>
        </div>
      </el-card>


      <el-card class="box-card" body-style="padding:0;" style="margin-bottom: 20px;" >
        <div slot="header" style="background-color:  rgba(49,52,59,0.8);position: relative; height: 60px;">
          <p style="margin:0;color: white;font-weight: bold; position: absolute;left: 30px;top: 20px;">ビザ情報</p>
          <el-button type="primary" style="float: right;margin: 15px 20px 0 0;width: 70px;" @click="router.push('/visa/update?index='+index)">ビザ更新</el-button>
        </div>
        <div style="padding: 20px;color: white;background-color:rgba(38,38,44,1);min-height: 400px;">
          <el-row   :gutter="15" v-for="u in visaInfoView" style="margin-top: 14px;">
            <el-col :span="8" >
              <div style="color: #666; margin-top: 14px ;font-size: 14px;font-weight: bold;">{{u.name}}</div>
            </el-col>
            <el-col :span="16">
              <div style="margin-top: 14px ;color: white;font-size: 15px;font-weight: bold;" >{{u.value}}</div>
            </el-col>
          </el-row>

          <el-row  :gutter="15" style="margin-top: 14px;">
            <el-col :span="8" >
              <div style="margin-top: 14px ;color: #666;font-size: 14px;font-weight: bold;">ビザ写真</div>

            </el-col>
            <el-col :span="16" style="margin-top: 10px;">
              <!--照片处理-->
              <div class="demo-image__lazy" style="min-height: 275px;">
                <el-image style="margin-top: 14px ;" v-for="url in urls" :key="url" :src="url" lazy />
              </div>
            </el-col>
          </el-row>
        </div>
      </el-card>


      <el-card class="box-card" body-style="padding:0;" style="margin-bottom: 10px;" >
        <div slot="header" style="background-color:  rgba(49,52,59,0.8);position: relative; height: 60px;">
          <p style="margin:0;color: white;font-weight: bold; position: absolute;left: 30px;top: 20px;">アカウント関係</p>

        </div>
        <div style="padding: 20px;color: white;background-color:rgba(38,38,44,1);">
          <el-row  :gutter="15" v-for="u in dateInfoView" style="margin-top: 10px;">
            <el-col :span="8" >
              <span style="color: #666;font-size: 14px;font-weight: bold;">{{u.name}}</span>
            </el-col>
            <el-col :span="16">
              <span style="color: white;font-size: 15px;font-weight: bold;" >{{u.value}}</span>
            </el-col>
          </el-row>
        </div>
      </el-card>

    </div>



  </div>
</template>

<style scoped>
.demo-image__lazy {
  height: 200px;
  overflow-y: auto;
}
.demo-image__lazy .el-image {
  display: block;
  min-height: 100px;
  margin-bottom: 10px;
}
.demo-image__lazy .el-image:last-child {
  margin-bottom: 0;
  margin-top: 20px;
}
</style>