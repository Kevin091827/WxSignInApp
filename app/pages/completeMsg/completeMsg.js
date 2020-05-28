// pages/completeMsg/completeMsg.js
const util = require('../../utils/util')

Page({

  /**
   * 页面的初始数据
   */
  data: {
    isStudent: true,
    schoolName: '',
    cerid: '',
    name: '',
    collage: '',
    major: '',
    grade: '',
    stuClass: '',
    phone: '',
    email: ''
  },

  getSchoolName: util.debounce(function(e){
    var val = e.detail.value;
    console.log(val)
    this.setData({
        schoolName: val
    })
  }),

  getPassword: util.debounce(function(e){
      var val = e.detail.value;
      console.log(val)
      this.setData({
          password: val
      })
  }),

  getCerid:util.debounce(function(e){
      var val = e.detail.value;
      console.log(val)
      this.setData({
          cerid: val
      })
  }),

    // name: '',
    // collage: '',
    // major: '',
    // grade: '',
    // stuClass: '',
    // phone: '',
    // mail: ''

  getName:util.debounce(function(e){
      var val = e.detail.value;
      console.log(val)
      this.setData({
          name: val
      })
  }),
  getCollage:util.debounce(function(e){
      var val = e.detail.value;
      console.log(val)
      this.setData({
          collage: val
      })
  }),
  getMajor:util.debounce(function(e){
      var val = e.detail.value;
      console.log(val)
      this.setData({
          major: val
      })
  }),
  getGrade:util.debounce(function(e){
      var val = e.detail.value;
      console.log(val)
      this.setData({
          grade: val
      })
  }),
  getClass:util.debounce(function(e){
      var val = e.detail.value;
      console.log(val)
      this.setData({
          stuClass: val
      })
  }),
  getPhone:util.debounce(function(e){
      var val = e.detail.value;
      console.log(val)
      this.setData({
          phone: val
      })
  }),
  getMail:util.debounce(function(e){
    var val = e.detail.value;
    console.log(val)
    this.setData({
        email: val
    })
  }),

  getCerid:util.debounce(function(e){
    var val = e.detail.value;
    console.log(val)
    this.setData({
        cerid: val
    })
  }),
  
  selectPos(e) {
    let isStudent = true;
    if(e.detail.value === 'teacher') {
        isStudent = false;
    } else {
        isStudent = true;
    }
    wx.setStorageSync('IS_STUDENT', isStudent)
    this.setData({
        isStudent
    })
  },

  // 提交
  delay:function(e){
      if(!wx.getStorageSync('OPEN_ID')) {
          return ;
      }
      var formId = e.detail.formId;
      this.setData({
          formId:formId
      })
      // 延迟一会后执行
      setTimeout(() => {
        this.formMes();
    }, 1000);
  },

  formMes: function() {
      const _this = this;
      var formId = _this.data.formId;
    
      const school = _this.data.schoolName,
          accountId = _this.data.cerid,
          type = _this.data.isStudent ? 'student' : 'teacher',
          collage = _this.data.collage,
          phone = _this.data.phone,
          email = _this.data.email,
          name = _this.data.name;
      if(email && !this.checkEmail(email)) {
        wx.showToast({
          icon: 'none',
          title: '邮箱格式不正确'
        })
        return ;
      } 
      if(phone && !this.checkMobile(phone)) {
        wx.showToast({
          icon: 'none',
          title: '电话格式不正确'
        })
        return ;
      }
      let grade = '', stuClass = '', major = '';
      if(_this.data.isStudent) {
        grade = _this.data.grade,
        stuClass = _this.data.stuClass,
        major = _this.data.major;
      }
      wx.showLoading({
          title: '提交信息中...'
      })
      util.post(`${util.url}/info/improveInfo`, {
          openId: wx.getStorageSync('OPEN_ID'),
          school,
          accountId,
          type,
          collage,
          phone,
          email,
          grade,
          stuClass,
          major,
          name
      }).then(res => {
          wx.hideLoading();
          
          if (res.data.status == 200) {
            wx.setStorageSync('SCHOOL', school);
            wx.setStorageSync('ACCOUNT_ID', accountId);
            wx.setStorageSync('TYPE', type);
            wx.showToast({
                title: '提交成功',
                icon: 'none',
                duration: 2000
            })
            // this.getMsg();
            wx.navigateBack();
          } else {
              wx.showToast({
                  title: '提交失败,请检查信息',
                  icon: 'none',
                  duration: 2000
              })
          }
      })

  },

  checkMobile(str) {
    var re = /^1\d{10}$/;
    if (re.test(str)) {
        return true;
    } else {
        return false;
    }
  },

  checkEmail(str){
    var re = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/
    if(re.test(str)){
      return true;
    }else{
      return false;
    }
  },

  getMsg() {
    const type = wx.getStorageSync('TYPE'),
      school = wx.getStorageSync('SCHOOL'),
      accountId = wx.getStorageSync('ACCOUNT_ID'),
      isStudent = wx.getStorageSync('IS_STUDENT');
    wx.showLoading({
      title: '加载中...'
    })

    this.setData({
      schoolName: school || '',
      cerid: accountId || '',
      isStudent
    })
    
    util.get(`${util.url}/info/selectInfo`, {
      type,
      school,
      accountId
    }).then(res => {
      wx.hideLoading();
      const data = res.data;
      if(data.status === 200) {
        this.setData({
          schoolName: data.oData.school || '',
          cerid: data.oData.accountId || '',
          name: data.oData.name || '',
          collage: data.oData.collage || '',
          major: data.oData.major || '',
          grade: data.oData.grade || '',
          stuClass: data.oData.stuClass || '',
          phone: data.oData.phone || '',
          email: data.oData.email || ''
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