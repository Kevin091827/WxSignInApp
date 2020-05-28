// pages/moreMsg/moreMsg.js
const util = require('../../utils/util');

Page({

  /**
   * 页面的初始数据
   */
  data: {
    msgList: [{
      title: '学校',
      content: '山东科技大学'
    }, {
      title: '学校',
      content: '山东科技大学'
    }, {
      title: '学校',
      content: '山东科技大学'
    }],
  },

  getMsg() {
    const type = wx.getStorageSync('TYPE'),
      school = wx.getStorageSync('SCHOOL'),
      accountId = wx.getStorageSync('ACCOUNT_ID');
    wx.showLoading({
      title: '加载中...'
    })
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
    
    util.get(`${util.url}/info/selectInfo`, {
      type,
      school,
      accountId
    }).then(res => {
      wx.hideLoading();
      let data = res.data;
      if(data.status === 200) {
        data = data.oData;
        data.collage && msgList.push({
          title: '学院',
          content: data.collage
        })
        data.phone && msgList.push({
          title: '电话',
          content: data.phone
        })
        data.email && msgList.push({
          title: '邮箱',
          content: data.email
        })
        this.setData({
          msgList
        })
      } 
    }) 
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getMsg();
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