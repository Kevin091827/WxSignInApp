const util = require('../../utils/util')


Page({

  /**
   * 页面的初始数据
   */
  data: {
    stuList: [
      
    ],
    id: 0
  },

  getStuList() {
    wx.showLoading({
      title: '加载中...'
    })
    const id = this.data.id;
    const type = wx.getStorageSync('TYPE');
    util.get(`${util.url}/course/selectStuList`, {
      type,
      id
    }).then(res => {
      wx.hideLoading();
      if(res.data.status) {
        const stuList = res.data.oData;
        stuList.forEach(item => {
          item.class = `${item.grade} ${item.collage} ${item.stuClass}`
        })
        this.setData([
          stuList
        ])
      }
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      id: options.id
    })
    this.getStuList();
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
  onShareAppMessage: function () {

  }
})