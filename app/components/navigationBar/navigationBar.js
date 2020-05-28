// components/navigationBar.js
const app = getApp()
 
Component({
 
 properties: {
  text: {
   type: String,
   value: '绑定企业'
  },

 },
 data: {
  statusBarHeight: app.globalData.statusBarHeight + 'px',
  navigationBarHeight: (app.globalData.statusBarHeight + 44) + 'px'
 },
 
 methods: {
  backHome: function () {

wx.switchTab({
    url: '/pages/index/index',
  })
  },

 }
})