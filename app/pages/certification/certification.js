// pages/certification/certification.js
var app = getApp()
const util = require('../../utils/util')
Page({

    /**
     * 页面的初始数据
     */
    data: {
        isStudent: true,  //学生 / 老师
        isCertify: false,
        schoolName: '',
        cerid: '',
        password: '',
        isFinishLoading: false
    },

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

    // 获取已认证的信息
    getCertificationMsg() {

    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options = {}) {
        wx.setStorageSync('IS_STUDENT', true)
        this.isLogin();
    },

    isFirstLogin() {
        wx.showLoading({
            title: '加载中...',
            mask: true
        })
        return util.get(`${util.url}/auth/isFirstLogin`, {
            openId: wx.getStorageSync('OPEN_ID') || ''
        })
            .then(res => {
                wx.hideLoading();
                if(res.data.oData && res.data.oData.msg !== 'false') {
                    // 第一次
                    return true ;
                } else if(res.data.oData && res.data.oData.msg === 'false') {
                    const type = res.data.oData.type;
                    const data = res.data.oData[type];
                    wx.setStorageSync('IS_CERTIFICATION', true)
                    wx.setStorageSync('SCHOOL', data.school);
                    wx.setStorageSync('ACCOUNT_ID', data.accountId);
                    wx.setStorageSync('TYPE', type);
                    wx.setStorageSync('IS_STUDENT', type === 'student');
                    console.log(data, data.accountId)
                    return false;
                }
                wx.showToast({
                    icon: 'none',
                    title: '请重新登录'
                })
                return true;
            })
    },

    isLogin() {  // 是否有授权
        return this.isFirstLogin()
            .then(isFirst => {
                if(isFirst && !wx.getStorageSync('OPEN_ID')) {
                    wx.navigateTo({
                        url: '../welcome/welcome'
                    })
                } else if(!isFirst) {
                    wx.switchTab({
                        url: '../index/index'
                    })
                }
            })
        // if(!wx.getStorageSync('OPEN_ID')) {
        //     wx.navigateTo({
        //         url: '../welcome/welcome'
        //     })
        // } else if(wx.getStorageSync('IS_CERTIFICATION')) {
        //     wx.switchTab({
        //         url: '../index/index'
        //     })
        // }
    },

    judge: function() {
        var _this = this
        const url = `${util.url}/guestImformation/checkGuestHadRegistration`

        wx.showLoading({
            title: '努力加载中~',
            mask: true
        });
        // 判断是否有认证了
        util.get(url, {
            unionId: wx.getStorageSync('unionId')
        }).then(res => {
            wx.hideLoading();
        
            _this.setData({
                isFinishLoading: true
            })
            if (res.statusCode === 200) {  // 有认证过
                wx.navigateTo({
                    url: '../index/index'
                })
            }

        })
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
      
        if (_this.data.schoolName == '' || _this.data.cerid == '' || _this.data.password == '') {
            wx.showToast({
                title: '请填写完整信息哦~',
                icon: 'none',
                duration: 2000
            })
        } else {
            wx.showLoading({
                title: '认证中...'
            })
            const school = _this.data.schoolName,
                accountId = _this.data.cerid,
                type = _this.data.isStudent ? 'student' : 'teacher';
            util.post(`${util.url}/auth/auth`, {
                openId: wx.getStorageSync('OPEN_ID'),
                school,
                accountId,
                pwd: _this.data.password,
                type
            }).then(res => {
                wx.hideLoading();
                
                console.log(res)
                if (res.data.status == 200) {
                    wx.setStorageSync('IS_CERTIFICATION', true)
                    wx.setStorageSync('SCHOOL', school);
                    wx.setStorageSync('ACCOUNT_ID', accountId);
                    wx.setStorageSync('TYPE', type);
                    wx.showToast({
                        title: '认证成功',
                        icon: 'none',
                        duration: 2000
                    })
                    wx.navigateTo({
                        url: '../completeMsg/completeMsg'
                    })
                } else {
                    wx.showToast({
                        title: '认证失败,请检查信息',
                        icon: 'none',
                        duration: 2000
                    })
                }
            })
        }

    },

    //获取手机号
    getPhoneNumber: function(e) {
        var _this = this;
        var options = _this.data.options
        var iv = e.detail.iv || ''
        var encryptedData = e.detail.encryptedData || ''

        wx.login({
            success: function(res) {
                if (res.code) {
                    app.globalData.code = res.code
                    const url = `${util.url}/api/getPhoneNumber`;

                    var code = res.code

                    console.log('code=', code)
                    // console.log('iv=', iv)
                    // console.log('encryptedData=', encryptedData)
                    util.get(url, {
                        iv: iv,
                        // sessionKey: wx.getStorageSync('session_key'),
                        encryptedData: encryptedData,
                        code: code,
                        // options
                    }).then((res) => {
                        console.log(res)
                        if (res.data.status == 200) {
                            //存入缓存即可
                            var phone = JSON.parse(res.data.oData.sData).phoneNumber
                            _this.setData({
                                guestMobile: phone
                            })
                            // var that = _this
                            // util.get(`${util.url}/guestImformation/checkPhoneCanBeReg`, {
                            //     phone: phone
                            // }).then(res => {
                            //     console.log(res)
                            //     if (res.data.status !== 200) {
                            //         that.setData({
                            //             isPhone: false
                            //         })
                            //     }
                            // })
                        } else {
                            wx.showToast({
                                title: '手机号获取失败',
                                icon: 'none'
                            })
                        }
                    })

                }

            }
        })
    },

    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function() {

    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function() {
        if(wx.getStorageSync('IS_CERTIFICATION')) {
            wx.switchTab({
                url: '../index/index'
            })
        }
    },

    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide: function() {

    },

    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload: function() {

    },

    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh: function() {

    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function() {

    },

    /**
     * 用户点击右上角分享
     */

})