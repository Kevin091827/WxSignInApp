const app = getApp()
const util = require('../../utils/util')
const prejudge = require('../../utils/prejudge.js')

Page({

  /**
   * 页面的初始数据
   */
  data: {
    imgUrl: 'https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=794344838,2354567795&fm=26&gp=0.jpg',
    options: {}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options = {}) {
    // if (options.page) {
    //   app.globalData.page = options.page
    // }

    // var _this = this;
    // let exp = wx.getStorageSync('exp') || 0,
    //   now = +new Date();
    // if ( wx.getStorageSync('token') && (exp - now <= 3 * 60 * 1000)) {
    //   wx.removeStorageSync('exp');
    //   wx.removeStorageSync('token');
    // } else if (wx.getStorageSync('token')) {
    //   wx.getSetting({
    //     success(res) {
    //       if (res.authSetting['scope.userInfo']) {
    //         // _this.relogin();
    //         wx.getUserInfo({
    //           success(res) {
    //             wx.setStorageSync('userInfo', res.userInfo)
    //             app.globalData.userInfo = res.userInfo
    //             wx.switchTab({
    //               url: '/pages/index/index'
    //             })
    //           }
    //         })
    //       }
    //     }
    //   })
    // }
    // this.setData({
    //   options: options || {}
    // })
  },
  //超时重新获取
  fTokenOverTime: function () {
    let exp = wx.getStorageSync('exp'),
      now = +new Date();
    // 过期时间与现在时间之差小于三分钟时重新登录

    if (exp - now <= 3 * 60 * 1000) {
      wx.removeStorageSync('exp');
      wx.removeStorageSync('token');
      // 重新登录 (主动获取token)
      fGetToken();
      return true
    }
    return false
  },

  back: function () {
    let options = this.data.options;
    var pages = getCurrentPages(); // 当前页面
    var beforePage = pages[pages.length - 2];
    
    if (pages.length == 1) {
      wx.switchTab({
        url: '/pages/index/index'
      })
    } else {
      var beforePage = pages[pages.length - 2];
      beforePage.setData({
        options
      })
      wx.navigateBack({
        success: function () {
          beforePage.onLoad(); // 执行前一个页面的onLoad方法
        }
      });
    }

  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  getUserInfo(e) {
    if (e.detail.userInfo) {
      wx.setStorageSync('userInfo', e.detail.userInfo)
      wx.setStorageSync('avatarUrl', e.detail.userInfo.avatarUrl)
      app.globalData.userInfo = e.detail.userInfo
      var iv = e.detail.iv || ''
      var encryptedData = e.detail.encryptedData || ''
      this.login(e.detail.userInfo, iv, encryptedData)
    }
  },

  login(userinfo, iv, encryptedData) {
    wx.showLoading({
      title: '登录中...',
      mask: true
    })

    const _this = this;
    // const session = wx.getStorageSync('session_key');
    wx.login({
      success: function (res) {
        const url = `${util.url}/wx/wxLogin`;
        if (res.code) {
          app.globalData.code = res.code
          console.log(res.code)
          wx.request({
            url,
            header: {
              "Content-Type": "json",
            },
            data: {
              code: res.code,
              iv: iv,
              encryptedData: encryptedData
            },
            method: 'GET',
            success: _this.handleLoginSucc
          })
        } else {
        }
      }
    })

  },

  getExpTime(str) {
    str = prejudge.decode(str);
    let expTime = str.slice(str.indexOf('exp":') + 5, str.indexOf('"iat') - 1);
    return expTime * 1000;
  },

  handleLoginSucc(res) {
    wx.hideLoading();

    var _this = this;
    if (res.data.status === 200) {
      wx.showToast({
        title: '登陆成功',
        duration: 2000
      })
      const data = res.data.oData;
      wx.setStorageSync('OPEN_ID', data.openId);
      _this.back();
    } else {
      wx.showToast({
        title: '登陆失败,请检查网络状况，稍后再试~',
        icon: 'none',
        duration: 2000
      })
    }
  },

  // 拒绝登录
  handleCancelBtnClick() {
    wx.showToast({
      icon: 'none',
      title: '授权后才能正常使用！'
    })
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