<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  height: 100vh;
}

/* 确保所有表格都应用相同的样式 */
.el-table {
  --table-bg: rgba(255, 255, 255, 0.85);
  --table-header-bg: rgba(248, 250, 253, 0.95);
  --table-border: rgba(226, 232, 240, 0.6);
  --table-hover: rgba(66, 153, 225, 0.05);
  --table-text: #2d3748;
  --table-text-secondary: #718096;
  --backdrop-blur: 12px;
  --transition-normal: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  --transition-bounce: all 0.4s cubic-bezier(0.34, 1.56, 0.64, 1);
}

/* 深色主题变量 */
.dark-theme .el-table {
  --table-bg: rgba(26, 32, 44, 0.85);
  --table-header-bg: rgba(45, 55, 72, 0.95);
  --table-border: rgba(74, 85, 104, 0.6);
  --table-hover: rgba(66, 153, 225, 0.1);
  --table-text: #e2e8f0;
  --table-text-secondary: #a0aec0;
}
</style>
