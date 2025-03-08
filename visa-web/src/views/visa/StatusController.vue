<script setup>
//TODO::访问数据库更新
import {onMounted, ref} from "vue";
import { ElTable } from 'element-plus'

const task=ref({ id:"",weightNum:7, name: "",level:1,des:""});
const subTask=ref({ id:"",weightNum:1, name: "",level:2,des:""});

const taskList = ref([
    { id:1,weightNum:1, name: "処理待ち",level:1,des:"description"},
    { id:2,weightNum:2, name: "資料準備中",level:1,des:"description"},
    { id:3,weightNum:3, name: "資料確認中(会社)",level:1,des:"description"},
    { id:4,weightNum:3, name: "追加資料必要(会社)",level:2,des:"description"},
    { id:5,weightNum:4, name: "審査完了(会社)",level:1,des:"description"},
    { id:6,weightNum:5, name: "申請待ち(入館)",level:1,des:"description"},
    { id:8,weightNum:5, name: "追加資料必要(入館)",level:2,des:"description"},
    { id:9,weightNum:5, name: "追加テスト(入館)",level:2,des:"description"},
    { id:7,weightNum:6, name: "完了",level:0,des:"description"},
  ]);
  const updateTaskList=()=>{
    //TODO::更新状态列表
  }
const singleTableRef = ref({});
const subSingleTableRef = ref();

  //子状态
  const taskTopList=ref([]);
  const taskSubList=ref([]);

  //选择主状态
  const currentSelection=ref({});
  const subCurrentSelection=ref({});
  const handleCurrentChange = (val) => {
    if(val===undefined||val===null){
      return;
    }
    currentSelection.value = val;
    organizeSubStatus(val.id)
  };
  const changeSubStatusTo=(weightNum,ope)=>{
    taskList.value.forEach((item,index)=>{
      if(item.level===2&&item.weightNum===weightNum){
        taskList.value[index].weightNum=weightNum+ope;
      }
    })
  }

const changeSubStatusToById=(id,ope)=>{
  taskList.value.forEach((item,index)=>{
    if(item.level===2&&item.id===id){
      taskList.value[index].weightNum=taskList.value[index].weightNum+ope;
    }
  })
}
  const subSpace=ref([]);
  const targetSpace=ref([]);
  const changeSubs=(weightNum1,weightNum2)=>{
    subSpace.value= [];
    targetSpace.value=[];
    if(weightNum1-weightNum2===0){
      return;
    }
    taskList.value.forEach((item,index)=>{
      if(item.level===2&&(item.weightNum===weightNum1)){
        subSpace.value.push(item.id);
      }else if(item.level===2&&(item.weightNum===weightNum2)){
        targetSpace.value.push(item.id);
      }
    })
    if(subSpace.value!={}){
      for (let i = 0; i < subSpace.value.length; i++) {
        if(subSpace.value[i]===undefined||subSpace.value[i]===null){break;}
        //changeSubStatusTo(getWeightById(subSpace.value[i]),weightNum2-weightNum1);
        changeSubStatusToById(subSpace.value[i],weightNum2-weightNum1)
      }
    }
    if(targetSpace.value!={}){
      for (let i = 0; i < targetSpace.value.length; i++) {
        if(targetSpace.value[i]===undefined||targetSpace.value[i]===null){break;}
        //changeSubStatusTo(getWeightById(targetSpace.value[i])+weightNum2-weightNum1,weightNum1-weightNum2);
        changeSubStatusToById(targetSpace.value[i],weightNum1-weightNum2)
      }
    }
  }
  const subhandleCurrentChange = (val) => {
    if(val===undefined||val===null){
      return;
    }
    subCurrentSelection.value = val;
  };
  const sortTaskListByWeightNum=()=>{
    taskList.value.sort((a, b) => a.weightNum - b.weightNum);
  }
  //向上移动
  const moveUp=()=>{
    if(currentSelection.value===undefined||currentSelection.value===null){
      return;
    }
    if(currentSelection.value.weightNum===1){
      return;
    }
    let thisId = currentSelection.value.id;

    let upId = findTopIdByWeight(currentSelection.value.weightNum-1);
    taskList.value.forEach((item,index)=>{
      if(item.id===thisId){
        //changeSubStatusTo(currentSelection.value.weightNum,'up');
        //changeSubStatusTo(currentSelection.value.weightNum,'down');
        changeSubs(currentSelection.value.weightNum,currentSelection.value.weightNum-1)
        taskList.value[index].weightNum =currentSelection.value.weightNum-1;
      }else if(item.id===upId){

        taskList.value[index].weightNum=currentSelection.value.weightNum;
      }
    })
    sortTaskListByWeightNum();
    organizeTopStatus();
    organizeSubStatus(currentSelection.value.id);
    subStatus.value={};
  }

  const moveDown=()=>{
    if(currentSelection.value===undefined||currentSelection.value===null){
      return;
    }
    if(currentSelection.value.weightNum<1){
      return;
    }
    let thisId = currentSelection.value.id;

    let downId = findTopIdByWeight(currentSelection.value.weightNum+1);
    taskList.value.forEach((item,index)=>{
      if(item.id===thisId){
        //changeSubStatusTo(currentSelection.value.weightNum,'down');
        //changeSubStatusTo(currentSelection.value.weightNum,'up');
        changeSubs(currentSelection.value.weightNum,currentSelection.value.weightNum+1);
        taskList.value[index].weightNum =currentSelection.value.weightNum+1;
      }else if(item.id===downId){
        taskList.value[index].weightNum=currentSelection.value.weightNum-1;

      }
    })
    taskList.value.sort((a, b) => a.weightNum - b.weightNum);
    organizeTopStatus();
    organizeSubStatus(currentSelection.value.id);
    subStatus.value={};
  }

  const findRowById=(list,id)=>{
    let result=null;
    for (let i = 0; i < list.length; i++) {
      if(list[i].id===id){
        result=i;
        break;
      }
    }
    return result;
  }
  const deleteSubStatus=(weightNum)=>{
    for (let i = 0; i < taskList.value.length; i++) {
      if(taskList.value[i].level===2&&taskList.value[i].weightNum===weightNum){
        taskList.value.splice(i,1);
      }
    }
  }
  const deleteSelection=(target)=>{
    if(currentSelection.value===undefined||currentSelection.value===null){
      alert(currentSelection.value)
      return;
    }
    //TODO::删除
    if (Object.keys(currentSelection.value).length === 0) {
      return;
    }
    if(taskTopList.value.length === 1) {
      alert("無効な操作")
      return;
    }
    let deleteId=currentSelection.value.id;
    deleteId=findRowById(taskTopList.value,deleteId);
    if(target==='up'){
      while (currentSelection.value.weightNum!==taskList.value[taskList.value.length-1].weightNum){
        moveDown();
      }
      for (let i = taskList.value.length - 1; i >= 0; i--) {
        if ((taskList.value[i].weightNum) === currentSelection.value.weightNum&&taskList.value[i].level===1) {
          deleteSubStatus(taskList.value[i].weightNum);
          taskList.value.splice(i, 1);
          break;
        }
      }
      singleTableRef.value.setCurrentRow(null);
      subCurrentSelection.value={};
      subStatus.value={};
      currentSelection.value=null;
      organizeTopStatus();
    }

  }
  const resetId=()=>{
    sortTaskListByWeightNum();
    for (let i = 0; i < taskList.value.length; i++) {
      taskList.value[i].id=i+1;
    }
  }
  const getMaxWeight=()=>{
    organizeTopStatus();
    return taskTopList.value.length+1;
  }
  const showAddNew=(mode)=>{
    task.value={ id:"",weightNum:7, name: "",level:1,des:""};
    subTask.value={ id:"",weightNum:7, name: "",level:2,des:""};

    if(mode!=='sub'){
      dialogVisible.value =!dialogVisible.value;
    }else{
      subTask.value.weightNum=currentSelection.value.weightNum;
      subDialogVisible.value =!subDialogVisible.value;
    }

  }
  const addNew=(mode)=>{
    if(mode!=='sub'){
      //TODO::新增
      let weightNum=task.value.weightNum;
      task.value.weightNum=getMaxWeight();
      resetId();
      task.value.id=taskList.value.length+1;
      taskList.value.push(task.value);
      organizeTopStatus();//2  7  6 2
      sortTaskListByWeightNum();
      singleTableRef.value.setCurrentRow(taskTopList.value[taskTopList.value.length-1]);
      for (let i =getMaxWeight()-1; i > weightNum; i--) {
        moveUp();
      }
      dialogVisible.value =!dialogVisible.value;


    }else{
      alert("asd")
      subTask.value.weightNum=Number(subTask.value.weightNum);
      taskList.value.push(subTask.value);
      resetId();
      sortTaskListByWeightNum();
      organizeSubStatus(currentSelection.value.id);
      subDialogVisible.value =!subDialogVisible.value;
      console.log(taskList.value)
    }
    task.value={};
    subTask.value={};
  }

import { ElMessageBox } from 'element-plus'

  const dialogVisible = ref(false)
  const subDialogVisible = ref(false)

  const handleClose = () => {
    dialogVisible.value = false;
    subDialogVisible.value=false;
    subEditorDialog.value=false;
    editorDialog.value=false;
  }
  const editorDialog=ref(false);
  const subEditorDialog=ref(false);
  const showEdit=(mode)=>{
    if(!currentSelection.value){
      return;
    }
    let row = findRowById(taskList.value,currentSelection.value.id);
    task.value=ref({ id:"",weightNum:7, name: "asd",level:1,des:"asd"});
    task.value.name=taskList.value[Number(row)].name;
    task.value.des=taskList.value[Number(row)].des;
    if(!row){return;}
    if(mode!=='sub'){
      editorDialog.value=!editorDialog.value;
    }else{
      subEditorDialog.value=!subEditorDialog.value;
    }
  }
  const edit=(mode)=>{
    let targetId;
    if(mode!=='sub'){
      targetId=currentSelection.value.id
    }else{
      targetId=subCurrentSelection.value.id;
    }
    //TODO::修改
    editorDialog.value=!editorDialog.value;
    let row = findRowById(taskList.value,targetId);
    taskList.value[Number(row)].des=task.value.des;
    taskList.value[Number(row)].name=task.value.name;
    organizeTopStatus();

  }


  onMounted(()=>{
    organizeTopStatus();
    organizeSubStatus(1);
  })
const organizeTopStatus=()=>{
    taskTopList.value=[];
    let space=taskList.value;
    for (let i = 0; i < space.length; i++) {
      if(space[i]===undefined||space[i]===null){break;}
      if (space[i].level === 1 || space[i].level === 0) {
        taskTopList.value.push(space[i]);
      }
    }
  };
  const organizeSubStatus=(id)=> {
    taskSubList.value = [];
    let space = taskList.value;
    for (let i = 0; i < space.length; i++) {
      if (space[i].level === 2 && space[i].weightNum === getWeightById(id)) {
        taskSubList.value.push(space[i]);
      }
    }
  }

  //查找组件
  const subStatus=ref({opeStatus:"なし"});
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
  const findTopIdByWeight=(weightNum)=>{
    let list = taskTopList.value;
    for (let i = 0; i < list.length; i++) {
      if(list[i].weightNum===weightNum){
        return list[i].id;
      }
    }
    return 0;
  }

  //主状态
</script>

<template>
  <!--主添加-->

  <el-dialog
      v-model="dialogVisible"
      title="Tips"
      width="500"
      :before-close="handleClose"
  >
    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item label="順番"><el-input v-model="task.weightNum" placeholder="順番"></el-input></el-form-item>
      <el-form-item label="状態"><el-input v-model="task.name" placeholder="順番"></el-input></el-form-item>
      <el-form-item label="説明"><el-input v-model="task.des" placeholder="順番"></el-input></el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button type="primary" @click="addNew">
          確認
        </el-button>
      </div>
    </template>
  </el-dialog>


  <!--次添加-->
  <el-dialog
      v-model="subDialogVisible"
      title="Tips"
      width="500"
      :before-close="handleClose"
  >
    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item label="順番"><el-input v-model="subTask.weightNum" placeholder="順番"></el-input></el-form-item>
      <el-form-item label="状態"><el-input v-model="subTask.name" placeholder="順番"></el-input></el-form-item>
      <el-form-item label="説明"><el-input v-model="subTask.des" placeholder="順番"></el-input></el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button type="primary" @click="addNew('sub')">
          確認
        </el-button>
      </div>
    </template>
  </el-dialog>






  <!--主修改-->
  <el-dialog
      v-model="editorDialog"
      title="Tips"
      width="500"
      :before-close="handleClose"
  >
    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item label="状態"><el-input v-model="task.name" placeholder="順番"></el-input></el-form-item>
      <el-form-item label="説明"><el-input v-model="task.des" placeholder="順番"></el-input></el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button type="primary" @click="edit">
          確認
        </el-button>
      </div>
    </template>
  </el-dialog>
  <!--次修改-->
  <el-dialog
      v-model="subEditorDialog"
      title="Tips"
      width="500"
      :before-close="handleClose"
  >
    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item label="状態"><el-input v-model="task.name" placeholder="順番"></el-input></el-form-item>
      <el-form-item label="説明"><el-input v-model="task.des" placeholder="順番"></el-input></el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button type="primary" @click="edit('sub')">
          確認
        </el-button>
      </div>
    </template>
  </el-dialog>


  <el-row :gutter="10">
    <!--主状态-->
   <el-col :span="12">
     <el-card class="box-card" body-style="padding:0" style="margin-bottom: 10px;" >
       <div slot="header" style="background-color:  rgba(49,52,59,0.8);position: relative; height: 60px;">

         <el-row :gutter="10">
          <el-col :span="9"><p style="margin:0;color: white;font-weight: bold; position: absolute;left: 30px;top: 20px;">アカウント関係</p></el-col>
          <el-col :span="3"><el-button style="float: right;margin-right: 5px;margin-top: 20px;" size="small" type="primary" plain @click="showAddNew()"><el-icon><Plus /></el-icon></el-button></el-col>
          <el-col :span="3"><el-button style="float: right;margin-right: 5px;margin-top: 20px;" size="small" type="primary" @click="showEdit()"><el-icon><EditPen /></el-icon></el-button></el-col>
          <el-col :span="3"><el-button style="float: right;margin-right: 5px;margin-top: 20px;" size="small" type="primary" @click="moveDown()"><el-icon><Bottom /></el-icon></el-button></el-col>
          <el-col :span="3"><el-button style="float: right;margin-right: 5px;margin-top: 20px;" size="small" type="primary" @click="moveUp()"><el-icon><Top /></el-icon></el-button></el-col>
          <el-col :span="3"><el-button style="float: right;margin-right: 5px;margin-top: 20px;" size="small" type="primary" @click="deleteSelection('up')"><el-icon><CircleCloseFilled /></el-icon></el-button></el-col>
         </el-row>
       </div>
       <div style="padding: 0;color: white;background-color:rgba(38,38,44,1);">
         <el-table
             ref="singleTableRef"
             :data="taskTopList"
             highlight-current-row="highlight-current-row"
             @current-change="handleCurrentChange"
             >
           <el-table-column
               align="center"
               prop="weightNum"
               label="順番"
           >
           </el-table-column>
           <el-table-column
               align="center"
               prop="name"
               label="名前"
           >
           </el-table-column>
           <el-table-column
               align="center"
               prop="des"
               label="説明">
           </el-table-column>
         </el-table>
       </div>
     </el-card>

   </el-col>
    <!--图片-->
    <el-col :span="1">

    </el-col>
    <!--子状态-->
   <el-col :span="11">
     <el-card class="box-card" body-style="padding:0" style="margin-bottom: 10px;" >
       <div slot="header" style="background-color:  rgba(49,52,59,0.8);position: relative; height: 60px;">
         <el-row :gutter="10">
           <el-col :span="15"><p style="margin:0;color: white;font-weight: bold; position: absolute;left: 30px;top: 20px;">アカウント関係</p></el-col>
           <el-col :span="3"><el-button style="float: right;margin-right: 5px;margin-top: 20px;" size="small" type="primary" @click="deleteSelection('sub')"><el-icon><CircleCloseFilled /></el-icon></el-button></el-col>
           <el-col :span="3"><el-button style="float: right;margin-right: 5px;margin-top: 20px;" size="small" type="primary" @click="showEdit()"><el-icon><EditPen /></el-icon></el-button></el-col>
           <el-col :span="3"><el-button style="float: right;margin-right: 5px;margin-top: 20px;" size="small" type="primary" @click="showAddNew('sub')"><el-icon><Plus /></el-icon></el-button></el-col>
         </el-row>
       </div>
       <div style="padding: 0;color: white;background-color:rgba(38,38,44,1);">
         <el-table
             :ref="subSingleTableRef"
             :data="taskSubList"
             highlight-current-row
             @current-change="subhandleCurrentChange(val)"
         >
           <el-table-column
               align="center"
               prop="weightNum"
               label="順番"
           >
           </el-table-column>
           <el-table-column
               align="center"
               prop="name"
               label="名前"
           >
           </el-table-column>
           <el-table-column
               align="center"
               prop="des"
               label="説明">
           </el-table-column>
         </el-table>
       </div>
     </el-card>
   </el-col>
  </el-row>
</template>

<style scoped>

</style>
