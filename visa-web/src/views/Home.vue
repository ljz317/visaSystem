<script setup>
import { ref, onMounted, watch } from 'vue';
import { useRoute } from 'vue-router'; // 导入 useRoute

import { Document, Menu as IconMenu, Location, Setting } from '@element-plus/icons-vue';

const isCollapse = ref(false); // 控制面板的展开状态

// 侧边栏选择
const currentAside = ref("/home/list");

// 监听路由变化并根据不同的路径设置展开的面板
const route = useRoute();

// 监听路由变化
watch(() => route.path, (newPath) => {
  if (newPath === '/detail') {
    currentAside.value = '/detail'; // 如果路径是 /about，则展开
  }else if(newPath === '/home/list') {
    currentAside.value ="/home/list";
  }else if(newPath === '/visa/update') {
    currentAside.value ="/visa/update";
  }else if(newPath === '/visa/status') {
    currentAside.value ="/visa/status";
  }
}, { immediate: true }); // 添加 immediate: true，确保页面加载时立即执行一次

// 处理菜单打开和关闭
const handleOpen = (key, keyPath) => {
  console.log(key, keyPath);
};

const handleClose = (key, keyPath) => {
  console.log(key, keyPath);
};

onMounted(() => {
  // 组件挂载后执行的逻辑
});
</script>


<template>

  <div class="common-layout">
    <el-container>
      <!--头部-->
      <el-header  style="padding:0px; border-bottom: 2px solid #666; ">
        <el-card  style="padding:0;">
          <div style="height: 60px;background-color: white;">
            <p style="padding: 0;margin: 0;font-weight: bold;color: #666;font-size: 30px; float: left;">ビザ管理システム</p>
          </div>
        </el-card>
      </el-header>
      <hr>
      <el-container >
        <!--侧边栏-->
        <el-aside width="200px" style="padding:0;">
          <el-menu style="font-weight: bold;height: 100%;"
                   :default-active="currentAside"
                   router="true"
                   class="el-menu-vertical-demo"
                   :collapse="isCollapse"
                   @open="handleOpen"
                   @close="handleClose"
          >
            <el-menu-item index="/home/list" >
              <el-icon><icon-menu /></el-icon>
              <template #title><p style="color: #666;">社員一覧表</p></template>
            </el-menu-item>

            <el-menu-item index="/detail"  disabled>
              <el-icon><document/></el-icon>
              <template #title><p style="color: #666;">社員情報一覧</p></template>
            </el-menu-item>


            <el-sub-menu  index="/visa" style="color: #666">
              <template #title>
                <el-icon><location /></el-icon>
                <span>社員情報</span>
              </template>
              <el-menu-item-group title="ビザ関係">
                <el-menu-item index="/visa/update" disabled="true"><el-icon><document/></el-icon><span>ビザ更新</span></el-menu-item>

              </el-menu-item-group>
              <el-menu-item-group title="管理">
                <el-menu-item index="/"><span><el-icon><document/></el-icon>管理コード発行</span></el-menu-item>
                <el-menu-item index="/visa/status"><el-icon><document/></el-icon><span>状態管理</span></el-menu-item>
              </el-menu-item-group>
            </el-sub-menu>



            <el-menu-item index="3">
              <el-icon><setting /></el-icon>
              <template #title><router-link to="/" style="text-decoration:none;"><p style="color: #666;">個人設定</p></router-link></template>
            </el-menu-item>
          </el-menu>
        </el-aside>

        <!--主体-->
        <el-main style="padding:0;">
          <el-card  body-style="padding:0;width:100%;" >
            <router-view/>
          </el-card>
        </el-main>
      </el-container>
    </el-container>
  </div>






</template>

<style scoped>
  .el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 200px;
    min-height: 400px;
  }
  body{
    margin: 0;
    padding: 0;

  }
  body{
    background-color: #0aa1ed !important;
  }
  span{
    color: #666;
  }
</style>