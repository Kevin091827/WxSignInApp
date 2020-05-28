const util = require('../../utils/util')

Page({

  /**
   * 页面的初始数据
   */
  data: {
    isStudent: true,
    list: [],
    timer: null
  },

  getSignRecord() {
    const openId = wx.getStorageSync('OPEN_ID');
    const type = wx.getStorageSync('TYPE');
    util.get(`${util.url}/signIn/select`, {
      openId,
      type
    }).then(res => {
      if(res.data.status === 200) {
        const list = Array.isArray(res.data.oData[0]) ? res.data.oData[0] : res.data.oData;
        this.setData({
          list
        })        
      } else {
        wx.showToast({
          title: '加载出错了',
          icon: 'none'
        })
      }
    })
  },

  handleDeleteBtnClick(e) {
    wx.showLoading({
      title: '删除中...',
      mask: true
    })
    const id = e.currentTarget.dataset.id;
    util.get(`${util.url}/signIn/delete`, {
      id
    }).then(res => {
      wx.hideLoading();
      if(res.data.status === 200) {
        wx.showToast({
          title: '删除成功'
        })
      } else {
        wx.showToast({
          icon: 'none',
          title: '删除失败'
        })
      }
    })
  },

  handleExportBtnClick() {
    const teaId = wx.getStorageSync('ACCOUNT_ID');
    wx.showLoading({
      title: '导出中...'
    })
    wx.downloadFile({
      url: `${util.url}/signIn/export?teaId=${teaId}`,
      success (res) {
        wx.hideLoading();
        // 只要服务器有响应数据，就会把响应内容写入文件并进入 success 回调，业务需要自行判断是否下载到了想要的内容
        if (res.statusCode === 200) {
          wx.showLoading({
            title: '导出成功'
          })
          // TODO
          const filePath = res.tempFilePath
          wx.openDocument({
            filePath: filePath,
            success: function (res) {
              console.log('打开文档成功')
            }
          })
        }
      },
      fail() {
        wx.hideLoading();
        wx.showToast({
          title: '导出失败',
          icon: 'none'
        })
      }
    })
  },

  setTimer() {
    const timer = setInterval(() => {
      this.getSignRecord();
    }, 10000);
    this.setData({
      timer
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      isStudent: wx.getStorageSync('IS_STUDENT')
    })
    this.getSignRecord();
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
    this.setTimer();
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
    this.data.timer && clearInterval(this.data.timer);
    this.setData({
      timer: null
    })
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
    this.data.timer && clearInterval(this.data.timer);
    this.setData({
      timer: null
    })
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
  onShareAppMessage: function () {

  }
})