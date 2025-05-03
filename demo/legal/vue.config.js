const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  css: {
    sourceMap: true// 检查在第几行
  },

  publicPath: '/',// 设置项目根目录 :80/index -> :80/app/index

  // devServer文档: https://webpack.docschina.org/configuration/dev-server/#devserverhost
  devServer: {
    open: true,// 自动打开浏览器
    port: 8080,
    //port: 7275,// 端口
    host: "127.0.0.1",
    //host: "172.16.1.101",// 解决开启VPN以后报错[WDS]socket.io报错
    // disableHostCheck: true ,// 跳过host检查解决vue内网穿透白屏
    // proxy: {
    //   "/api": {
    //     target: "http://116.1.203.43:8067",
    //     pathRewrite: { "^/api": "" }
    //   }
    // }

  },

  // 设置网页标题,配置完需要重启服务
  chainWebpack: config => {
    config.plugin('html').tap(args => {
      args[0].title = 'ai问答系统';
      return args;
    })
  }
})

