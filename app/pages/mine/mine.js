// pages/mine/mine.js
const app = getApp()
const util = require('../../utils/util')
Page({

  /**
   * 页面的初始数据
   */
  data: {
    msgList: [],
    
    userInfo: {
      avatarUrl: '../../static/images/logo1.png',
      nickName: '游客'
    },
    name: '',
    position: '',
    hasUserInfo: false,
    isCertify: false,
  },

  goCertification() {
    wx.navigateTo({
      url: '../certification/certification'
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options={}) {
    const type = wx.getStorageSync('TYPE'),
      school = wx.getStorageSync('SCHOOL'),
      accountId = wx.getStorageSync('ACCOUNT_ID'),
      name = wx.getStorageSync('NAME');
    const msgList = [{
      title: '身份',
      content: type === 'student' ? '学生' : '教师',
    }, {
      title: '学校',
      content: school,
    }, {
      title: type === 'student' ? '学号' : '工号',
      content: accountId,
    }];
    this.setData({
      msgList
    })

    console.log(app.globalData)
    let userInfo = app.globalData.userInfo || wx.getStorageSync('userInfo');
    if (userInfo) {
      this.setData({
        userInfo,
        hasUserInfo: true,
        name: name || userInfo.nickName
      })
    }
    if(wx.getStorageSync('IS_CERTIFICATION')) {
      this.setData({
        isCertify: true
      })
    }
    
  },

  // 检验是否登录
  checkIsLogin(e) {
    // let token = wx.getStorageSync('token');
    // if(!token) {   // 没有授权 
    //   wx.navigateTo({
    //       url: '../welcome/welcome'
    //   }) 
    //   return false;
    // }
    if(e && e.currentTarget.dataset.link) {
      wx.navigateTo({
        url: e.currentTarget.dataset.link
      }) 
    }
    return true;
  },

  judge: function () {
    var options = this.data.options
    var _this = this
   
    const url = `${util.url}/guestImformation/checkGuestHadRegistration`
    util.get(url, {
      unionId: wx.getStorageSync('unionId'),
      options
    }).then(res => {
      if (res.statusCode === 200) {
        if (res.data.oData.code == 201) {
          _this.setData({
            isCertify: false
          })
        } else {
          _this.setData({
            isCertify: true,
          })
          wx.setStorageSync('guestName', res.data.oData.userInfo.guestName)
          wx.setStorageSync('guestMobile', res.data.oData.userInfo.guestMobile)
          wx.setStorageSync('guestCerid', res.data.oData.userInfo.guestCerid)
          _this.getScore()
        }

      }

    })
  },

  getUserInfo: function (e) {
    if (e.detail.userInfo) {
      app.globalData.userInfo = e.detail.userInfo
      this.setData({
        userInfo: e.detail.userInfo,
        hasUserInfo: true
      })
      // this.judge()
    } else {
      this.setData({

        hasUserInfo: false
      })
    }

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  // onShareAppMessage: function () {

  // }
})