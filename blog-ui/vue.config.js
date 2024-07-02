const { defineConfig } = require('@vue/cli-service');
const path = require('path');

module.exports = defineConfig({
  transpileDependencies: true,

  // 配置前端开发服务器
  devServer: {
    port: 8081, // 前端运行端口
    proxy: {
      '/dev-api': {
        target: 'http://localhost:8085', // 后端接口运行端口
        changeOrigin: true,
        pathRewrite: {
          '^/dev-api':''
        }
      }
    }
  },

  // 可选的其他配置
  configureWebpack: {
    resolve: {
      alias: {
        '@': path.resolve(__dirname, 'src')
      }
    }
  },

});
