<template >
  <div style="border:1px solid red;width: 100%;">
    <div  style="min-width: 1000px;max-width: 1000px;margin:0 auto;">
      <router-view/>
    </div>
  </div>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
.container {
  display: flex;
  justify-content: center;  /* 水平居中 */
  align-items: center;      /* 垂直居中 */
  height: 100vh;            /* 设置容器的高度为视口高度 */
}
</style>
<script setup lang="ts">
</script>