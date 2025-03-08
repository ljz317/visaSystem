<script setup>


//个人情报
import {onMounted, onUnmounted, ref} from "vue";


//图片处理

import { Plus } from '@element-plus/icons-vue'
import {ElMessage, UploadProps, UploadUserFile} from 'element-plus'
import { Check } from '@element-plus/icons-vue'

//TODO::更新照片
const fileList = ref([
  {
    name: 'food.jpeg',
    url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100',
  },
  {
    name: 'food.jpeg',
    url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100',
  }
])

const dialogImageUrl = ref('')
const dialogVisible = ref(false)

const handleRemove= (uploadFile, uploadFiles) => {
  console.log(uploadFile, uploadFiles)
  //TODO::删除照片
}

const handlePictureCardPreview= (uploadFile) => {
  dialogImageUrl.value = uploadFile.url
  dialogVisible.value = true

}
//下载

const downloadImage = async () => {
  if (!dialogImageUrl.value) return;

  try {
    const response = await fetch(dialogImageUrl.value);
    const blob = await response.blob(); // 获取图片数据（Blob 格式）
    const url = URL.createObjectURL(blob);

    // 创建 <a> 标签下载文件
    const link = document.createElement("a");
    link.href = url;
    link.download = user.value.name+"/"+user.value.visaType+"/"+user.value.visaEnd+".jpg"; // 设定文件名
    document.body.appendChild(link);
    link.click(); // 触发下载
    document.body.removeChild(link);

    // 释放 URL 对象
    URL.revokeObjectURL(url);
  } catch (error) {
    console.error("下载失败:", error);
  }
};




//TODO::访问数据库更新
const user=ref({
  phone:"1",
  address:"1",
  email:"1",
  name:"1",
  visaType:"期限なし",
  visaImg:"1",
  visaEnd:"2025-05-14",
  opeStatus:7,
  updateTime:"1",
  createTime:"1",
  username:"1",
  lastLogin:"1",
  empId:"1"
});
//TODO::访问数据库更新
const taskList = ref([
  { id:1,weightNum:1, name: "処理待ち",level:1},
  { id:2,weightNum:2, name: "資料準備中",level:1},
  { id:3,weightNum:3, name: "資料確認中(会社)",level:1},
  { id:4,weightNum:3, name: "追加資料必要(会社)",level:2},
  { id:5,weightNum:4, name: "審査完了(会社)",level:1},
  { id:6,weightNum:5, name: "申請待ち(入館)",level:1},
  { id:8,weightNum:5, name: "追加資料必要(入館)",level:2},
  { id:9,weightNum:5, name: "追加テスト(入館)",level:2},
  { id:7,weightNum:6, name: "完了",level:0},
]);
const updateTaskList=()=>{
  //TODO::更新状态列表
}
const updateUser=()=>{
  //TODO::更新USER
}
const updatePicture=()=>{
  //TODO::更新照片
}
onMounted( () => {
  updateUser();
  updateTaskList();
  updatePicture();
})
//TODO::员工查看修改页
//TODO::管理コード初期化页
//TODO::状态管理




const visaTypes=ref([
  {id:1,name:"期限なし",des:"1"},
  {id:2,name:"期限あり",des:"1"}
]);
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

const getDaysRemaining=(targetDate)=> {
  console.log(targetDate)
  const now = new Date(); // 当前日期
  const target = new Date(targetDate); // 目标日期

  // 计算时间差（毫秒）
  const timeDiff = target - now;


  // 将毫秒转换为天（1 天 = 1000ms * 60s * 60min * 24h）
  visaInfoView.value[2].value=Math.ceil(timeDiff / (1000 * 60 * 60 * 24));
}

//数值恢复
const returnValue=()=>{
  userInfoView.value=[
    {name:"名前",des:"name",value:user.value.name},
    {name:"携帯番号",des:"phone",value:user.value.phone},
    {name:"住所",des:"address",value:user.value.address},
    {name:"メール",des:"email",value:user.value.email}
  ]
  visaInfoView.value=[
    {name:"ビザタイプ",des:"visaType",value:user.value.visaType},
    {name:"ビザ期限",des:"visaEnd",value:user.value.visaEnd},
    {name:"残り時間",des:"visaDuring",value:50}
  ]
}
//更新数据
const updateVisaInfo=()=>{
  user.value.visaType=visaInfoView.value[0].value;
  user.value.visaEnd=visaInfoView.value[1].value;
}

//流程处理
const progressValue=ref(0);
import { useTimeoutFn } from "@vueuse/core";


const taskTopList=ref([]);
const taskSubList=ref([]);
const organizeTopStatus=()=>{
  taskTopList.value=[];
  let space=taskList.value;
  for (let i = 0; i < space.length; i++) {
    if (space[i].level === 1 || space[i].level === 0) {
      taskTopList.value.push(space[i]);
    }
  }
};
const organizeSubStatus=()=> {
  taskSubList.value = [];
  let currentStatus = taskList.value;
  let space = taskList.value;
  for (let i = 0; i < space.length; i++) {
    if (space[i].level === 2 && space[i].weightNum === getWeightById(user.value.opeStatus)) {
      taskSubList.value.push(space[i]);
    }
  }
}

//更新进度
const subStatus=ref({opeStatus:"なし"});
const SubselectionChange=()=>{
  user.value.opeStatus=subStatus.value.opeStatus;
  updateSelection();
}
const selectionChange=()=>{
  subStatus.value={opeStatus:"なし"};
  updateSelection();
  organizeSubStatus();
}
const getWeightById=(id)=>{
  let list = taskList.value;
  let weight=0;
  for (let i = 0; i < list.length; i++) {
    if(list[i].id===id){
      weight=list[i].weightNum;
    }
  }
  return weight;
}
const updateSelection=()=>{
  tasks.value=[];
  viewList.value=[];
  console.log(user.value)
  taskList.value.forEach((item)=>{
    let status=item;
    console.log("updateSelection++++++"+item.weightNum)
    console.log("updateSelection++++++"+user.value.opeStatus)
    console.log("updateSelection++++++"+getWeightById(user.value.opeStatus))

    if(item.weightNum<getWeightById(user.value.opeStatus)){
      status.done=1;
      status.current=0;
    }else if(item.weightNum===getWeightById(user.value.opeStatus)&&item.id===user.value.opeStatus){
      status.done=0;
      status.current=1;
    }else if(item.weightNum===getWeightById(user.value.opeStatus)&&item.id!==user.value.opeStatus&&item.level===1){
      status.done=1;
      status.current=0;
    }else{
      status.done=0;
      status.current=0;
    }
    tasks.value.push(status);
    console.log(tasks.value);

  })
  findCurrent();
}
//切换下阶段

const findTopIdByWeight=(weightNum)=>{
  let list = taskList.value;
  for (let i = 0; i < list.length; i++) {
    if(list[i].weightNum===weightNum){
      return list[i].id;
    }
  }
}
const next=()=>{
  if( getWeightById(user.value.opeStatus)<=getWeightById(taskList.value.length)){
    user.value.opeStatus=findTopIdByWeight(getWeightById(user.value.opeStatus)+1);
    updateSelection();
    organizeSubStatus();
  }else{
    ElMessage.warning("次はありません")
  }
}
const back=()=>{
  if(user.value.opeStatus>1&&findTopIdByWeight(getWeightById(user.value.opeStatus))>1){
    user.value.opeStatus=findTopIdByWeight(getWeightById(user.value.opeStatus)-1);
    updateSelection();
    organizeSubStatus();
  }else{
    ElMessage.warning("前はありません")
  }
}



//const filterSelectorList =ref([]);
//const filterSelector=()=>{
//  taskList.value.forEach((item,index)=>{
//    if(item.level!==0){
//      filterSelectorList.value.push(item);
//    }
//  })
//  return filterSelectorList;
//}
const getCurrentTask=()=>{
  let currentTask=null;
  taskList.value.forEach((task)=>{
    if(user.value.opeStatus===task.id){
      currentTask=task.name;
    }
  })
  return currentTask;
}
onMounted(()=>{
  updateSelection();
  organizeTopStatus();
  organizeSubStatus();
})

//  const tasks = ref([
//    { weightNum: 1, name: "処理待ち", done: 1 ,current:0,level:1},
//    { weightNum: 2, name: "資料準備中", done: 1 ,current:0,level:1},
//    { weightNum: 3, name: "資料確認中(会社)", done: 0 ,current:1,level:1},
//    { weightNum: 3, name: "追加資料必要(会社)", done: 0 ,current:0,level:2},
//    { weightNum: 4, name: "審査完了(会社)", done:0 ,current:0,level:1},
//    { weightNum: 5, name: "申請待ち(入館)", done: 0 ,current:0},
//  ]);
const tasks = ref([]);

//找到当前做的事情
const viewList=ref([
]);
const findCurrent=()=>{
    viewList.value=[];
    let current=-1;
    let doneNum=0;
    let noCount=0;
    tasks.value.forEach((item,index)=>{
      if(item.done===1&&item.level!==0){
        doneNum++;
      }else if(item.level===0){
        noCount++;
      }
      if(item.current===1){
        console.log(index)
        current=index;
      }
    });
  if(current===-1){
    return;
  }
  let last=current-1;
  let next=current+1;
  if(last-1>-1){
    viewList.value.push(tasks.value[last-1]);
  }
  if(last!==-1){
    viewList.value.push(tasks.value[last]);
  }
  viewList.value.push(tasks.value[current]);
  if(next<=tasks.value.length-1){
    viewList.value.push(tasks.value[next]);
  }
  if(next+1<=tasks.value.length-1){
    viewList.value.push(tasks.value[next+1]);
  }
  console.log(viewList.value);
  let value = Math.floor((100/taskTopList.value.length)*doneNum);
  progressValue.value=value>100?100:value;

}


const newTask = ref("");

const addTask = () => {
  if (newTask.value.trim()) {
    tasks.value.push({ id: Date.now(), name: newTask.value, done: false });
    newTask.value = "";
  }
};

const toggleTask = (task) => {
  task.done = true;
  useTimeoutFn(() => {
    tasks.value = tasks.value.filter((t) => t.id !== task.id); // 2秒后删除任务
  }, 1000);
};


//  interval = setInterval(() => {
//    if(progressValue.value<100){
//      progressValue.value += 1;
//    }
//  }, 50); // 每 200 毫秒自增 1

const counter = ref(0);
let interval = null;

onMounted(() => {


});

//onUnmounted(() => {
//  clearInterval(interval); // 组件卸载时清除定时器
//});




//更新状态
const changeMode=ref(false);
const changeStatusMode=()=>{
  changeMode.value=!changeMode.value;
  subStatus.value={opeStatus:"なし"};
  organizeSubStatus();
}
const MoveStatus=(id)=>{
  user.value.opeStatus=id;
  updateSelection();
}
//全部通过
const finish=()=>{
  MoveStatus(findTopIdByWeight(6));
}
</script>

<template>
  <div style="min-height: 40px; ">
    <p style="font-size: 20px;margin-bottom: 5px;font-weight: bold;color: #333333;">Visa管理</p>
  </div>

  <div style="width:80%;margin:0 auto; min-height:500px">


    <el-card class="box-card" body-style="padding:0;" style="margin-bottom: 20px;">
      <div slot="header" style="background-color:  rgba(49,52,59,0.8);position: relative; height: 50px;">
        <p style="margin:0;color: white;font-weight: bold; position: absolute;left: 30px;top: 14px;">個人情報</p>
      </div>
      <div style="padding: 20px;color: white;background-color:rgba(38,38,44,1);min-height: 120px;">
        <el-row  :gutter="20" v-for="u in userInfoView" style="margin-top: 10px;">
          <el-col :span="8" style="" >
            <span style="color: #666;font-size: 14px;font-weight: bold;">{{u.name}}</span>
          </el-col>
          <el-col :span="16" style="">
            <div   style="color: greenyellow;font-size: 15px;font-weight: bold;" >{{u.value}}</div>
          </el-col>
        </el-row>
      </div>
    </el-card>

    <el-card class="box-card" body-style="padding:0;" style="margin-bottom: 20px;">
      <div slot="header" style="background-color:  rgba(49,52,59,0.8);position: relative; height: 50px;">
        <p style="margin:0;color: white;font-weight: bold; position: absolute;left: 30px;top: 14px;">個人情報</p>
        <el-button type="success" style="float: right;margin: 10px 20px 0 0;width: 70px;" @click="updateVisaInfo()">更新</el-button>
        <el-button type="info" style="float: right;margin: 10px 20px 0 0;width: 70px;" @click="returnValue()">戻り</el-button>
      </div>
      <div style="padding: 20px;color: white;background-color:rgba(38,38,44,1);min-height: 120px;">
        <el-form :model="form" label-width="90px">
          <el-form-item :label="visaInfoView[0].name">
            <el-select v-model="visaInfoView[0].value" placeholder="placeholder" style="max-width: 220px;">
              <el-option
                  v-for="item in visaTypes"
                  :label="item.name"
                  :value="item.name">
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item :label="visaInfoView[1].name">
            <el-date-picker
                :onchange="getDaysRemaining(user.visaEnd)"
                v-model="visaInfoView[1].value"
                type="date"
                placeholder="期限日"
                clearable
            />
          </el-form-item>

          <el-form-item :label="visaInfoView[2].name">
            <div style="margin-left: 40px; color: white;font-size: 15px;font-weight: bold;" v-text="visaInfoView[2].value"></div>
          </el-form-item>

          <!--图-->
          <el-form-item label="ビザ写真">
            <el-upload
                v-model:file-list="fileList"
                action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
                list-type="picture-card"
                :on-preview="handlePictureCardPreview"
                :on-remove="handleRemove"
            >
              <el-icon><Plus /></el-icon>
            </el-upload>

            <el-dialog v-model="dialogVisible" width="50%">
              <img :src="dialogImageUrl" alt="Preview Image" style="position: relative;max-width: 100%; height: auto; display: block; margin: 0 auto;" />
              <template #footer>
                <el-button type="primary" @click="downloadImage" style="position: absolute; right: 40px;bottom: 40px;"><el-icon style=""><Download/></el-icon></el-button>
              </template>
            </el-dialog>

          </el-form-item>

        </el-form>
      </div>
    </el-card>


    <!--流程处理-->
    <el-card class="box-card" body-style="padding:0;" style="margin-bottom: 20px;">
      <div slot="header" style="background-color:  rgba(49,52,59,0.8);position: relative; height: 50px;">
        <p style="margin:0;color: white;font-weight: bold; position: absolute;left: 30px;top: 14px;">状況処理</p>
        <el-button v-show="!changeMode" type="primary" style="float:right;margin-top: 10px;margin-right: 20px;width: 75px;" @click="changeStatusMode()">詳細設定</el-button>
        <el-button v-show="!changeMode" type="primary" style="float:right;margin-top: 10px;margin-right: 20px;width: 75px;" @click="next()">次に更新</el-button>
        <el-button v-show="!changeMode" type="primary" style="float:right;margin-top: 10px;margin-right: 10px;width: 75px;" @click="back()">前に移動</el-button>
        <el-button v-show="!changeMode" type="primary" style="float:right;margin-top: 10px;margin-right: 10px;width: 75px;" @click="finish()">すべて完了</el-button>
      </div>
      <div style="padding: 20px;color: white;background-color:rgba(38,38,44,1);min-height: 120px;">
        <el-row :gutter="10" style="margin-top: 20px;">
          <el-col :span="8" >
            <div style="margin-top: 20px;" class="demo-progress">
              <el-progress type="circle" :percentage="progressValue" status="success">
                <template #default="{ percentage }">
                  <el-button v-show="progressValue===100" type="success" :icon="Check" circle />
                  <span v-show="progressValue!==100" class="percentage-value">{{ progressValue }}%</span>
                </template>
              </el-progress>
            </div>
            <div style="margin-top: 15px;position: relative;left: -4px;"><span style="color: #666;font-weight: bold;">更新進み具合</span> </div>
          </el-col>
          <el-col :span="14" >
            <!--代办事项-->

            <div v-if="!changeMode" v-for="(item,index) in viewList" style="margin-top: 0;">
              <div  v-show="item.current!==1&&item.done&&false" style=" position: relative;left: -30px; color: green; position: relative;bottom: 0;font-size: 21px;font-weight: bold;" >
                <el-icon color="green" size="25" style="margin-right: 20px;position: relative;top: -5px;"><SuccessFilled/></el-icon>
                <span style="color: rgba(0,255,0,0.4);">{{item.name}}</span>
              </div>
              <div  v-if="item.current===1&&false" style=" color: cornflowerblue; position: relative;bottom: 0;font-size: 35px;font-weight: bold;" >
                <el-icon color="orange" size="30" style="margin-right: 15px;"><DArrowRight /></el-icon>
                {{item.name}}
              </div>
              <div  v-show="item.current!==1&&!item.done&&false" style=" position: relative;left: -30px; color: green; position: relative;bottom: 0;font-size: 21px;font-weight: bold;" >
                <el-icon color="green" size="25" style="margin-right: 20px;position: relative;top: 3px;"><Tickets /></el-icon>
                <span style="color: rgba(255,255,255,1);">{{item.name}}</span>
              </div>

              <el-row :gutter="10" v-show="item.current!==1&&item.done" style="margin-left: 20px;margin-top: 3px;">
                <el-col :span="4">
                  <el-icon  color="green" size="25" style="position: relative;top: -2px;"><SuccessFilled/></el-icon>
                </el-col>
                <el-col :span="20" align="left">
                  <span style="color: rgba(0,255,0,0.4);font-weight: bold;">{{item.name}}</span>
                </el-col>
              </el-row>

              <el-row :gutter="10" v-if="item.current===1" style="margin-left: 0;">
                <el-col :span="4">
                  <el-icon  color="cornflowerblue" size="25" style="margin-top: 5px;"><DArrowRight /></el-icon>
                </el-col>
                <el-col :span="20" align="left">
                  <span style="font-size: 25px;font-weight: bold;color: cornflowerblue;">{{item.name}}</span>
                </el-col>
              </el-row>

              <el-row :gutter="10" v-show="item.current!==1&&!item.done&&item.level!==2" style=" margin-top: 5px;margin-left: 20px;">
                <el-col :span="4">
                  <el-icon color="white" size="25" style=""><Tickets /></el-icon>
                </el-col>
                <el-col :span="20" align="left">
                  <span style="color: rgba(255,255,255,1);font-weight: bold;">{{item.name}}</span>
                </el-col>
              </el-row>

              <el-row :gutter="10" v-show="item.current!==1&&!item.done&&item.level===2" style=" margin-top: 5px;margin-left: 50px;">
                <el-col :span="4">
                  <el-icon color="white" size="25" style=""><DocumentAdd /></el-icon>
                </el-col>
                <el-col :span="20" align="left">
                  <span style="color: rgba(255,255,255,1);font-weight: bold;">{{item.name}}</span>
                </el-col>
              </el-row>
            </div>
            <!--可以更新状态-->
            <div v-else>

              <el-form ref="form" :model="form" label-width="80px" >
                <el-form-item class="updateLabel" label="現在状況" style="margin-bottom:4px;">
                  <el-row :gutter="10" style="margin-left: 0;">
                    <el-col :span="3">
                        <el-icon  color="cornflowerblue" size="25" style="margin-top: 3px;"><DArrowRight /></el-icon>
                    </el-col>
                    <el-col :span="21" align="left" style="">
                      <span style="margin-left: 10px ;font-size: 20px;font-weight: bold;color: cornflowerblue;">{{getCurrentTask()}}</span>
                    </el-col>
                  </el-row>
                </el-form-item>

                <el-form-item class="updateLabel" label="状況調整" style="margin-bottom:4px;">
                  <el-select @change="selectionChange()" v-model="user.opeStatus" placeholder="placeholder">
                    <el-option
                        v-for="item in taskTopList"
                        :label="item.name"
                        :value="item.id">
                    </el-option>
                  </el-select>
                </el-form-item>

                <el-form-item v-if="taskSubList.length>0" class="updateLabel" label="追加状況" style="margin-top: 0;">
                  <el-select @change="SubselectionChange()" v-model="subStatus.opeStatus" placeholder="未追加">
                    <el-option
                        v-for="item in taskSubList"
                        :label="item.name"
                        :value="item.id">
                    </el-option>
                  </el-select>
                </el-form-item>

              </el-form>
              <el-button type="success" @click="changeStatusMode()">更新</el-button>

            </div>

          </el-col>
        </el-row>
      </div>
    </el-card>
  </div>
</template>

<style scoped>

::v-deep(.el-form-item__label) {
  color: white !important;
  font-size: 14px;
  font-weight: bold;
}

::v-deep(.updateLabel .el-form-item__label) {
  color: white !important;
  font-size: 16px;
  font-weight: bold;
  margin-top: 2px;
}

.percentage-value {
  display: block;
  margin-top: 10px;
  font-size: 28px;
}
.percentage-label {
  display: block;
  margin-top: 10px;
  font-size: 12px;
}
.demo-progress .el-progress--line {
  margin-bottom: 15px;
  max-width: 600px;
}
.demo-progress .el-progress--circle {
  margin-right: 15px;
}





</style>