//index.js
//获取应用实例
const app = getApp()
const util = require('../../utils/util')


Page({

    /**
     * 页面的初始数据
     */
    data: {
        isStudent: true,
        isSignining: false,
        type: '',

        showMsgModel: false,

        courseStanza: '',
        courseTime: '',
        courseName: '',
        coursePlace: '',

        signCode: '',

        id: '',

        signSucc: false,

        hadSignIn: [],
        noSignIn: []
    },

    handleCloseMsgModel() {
        this.setData({
            showMsgModel: false
        })
    },

    getSignCode(e) {
        const signCode = e.detail.value || '';
        this.setData({
            signCode
        })
    },

    signIn() {
        // 签到 / 发起签到
        const { signCode, type } = this.data;
        const _this = this;
        if(signCode.length !== 6) {
            wx.showToast({
                title: '请输入6位数',
                icon: 'none'
            })
            return ;
        }
        if(type === 'teacher') {
            // 发起
            this.setData({
                showMsgModel: true
            })
        } else {
            wx.showLoading({
                title: '正在签到',
                mask: true
            })
            // 直接签到
            wx.getLocation({
                type: 'wgs84',
                success (res) {
                    const latitude = res.latitude
                    const longitude = res.longitude
                    _this.stuSignInRequest(`维度：${latitude};经度：${longitude}`);
                }
            })
        }
    },

    judgeIsSigninging() {
        // 是否正在签到
        const id = wx.getStorageSync('CUR_SIGN_COURSE_ID');
        this.getSignStuList(id);
    },

    handleGetCourseName(e) {
        const courseName = e.detail.value || '';
        if(courseName) {
            this.setData({
                courseName
            })
        }
    },

    handleGetCourseTime(e) {
        const courseTime = e.detail.value || '';
        if(courseTime) {
            this.setData({
                courseTime
            })
        }
    },
    handleGetCoursePlace(e) {
        const coursePlace = e.detail.value || '';
        if(coursePlace) {
            this.setData({
                coursePlace
            })
        }
    },
    handleGetCourseStanza(e) {
        const courseStanza = e.detail.value || '';
        if(courseStanza) {
            this.setData({
                courseStanza
            })
        }
    },
    handleConfirmBtn() {
        wx.showLoading({
            title: '正在发起中...'
        })
        const _this = this;
        const {
            courseName,
            coursePlace,
            courseStanza,
            courseTime
        } = this.data;
        if(!courseName
            || !coursePlace
            || !courseStanza
            || !courseTime  
        ) {
            wx.showToast({
                title: '请填写完整信息哦~',
                icon: 'none'
            })
            return ;
        }
        util.get(`${util.url}/signIn/input`, {
            teaId: wx.getStorageSync('ACCOUNT_ID'),
            courseName,
            courseAddr: coursePlace,
            courseTime,
            courseStanza
        }).then(res => {
            if(res.data.status === 200) {
                const id = res.data.oData.id;
                _this.setData({
                    showMsgModel: false,
                    id
                })
                wx.setStorageSync('CUR_SIGN_COURSE_ID', id);
                _this.signInRequest(id);
            } else {
                wx.showToast({
                    title: res.data.sData,
                    icon: 'none'
                })
            }
        })
    },
    signInRequest(id = '') {
        // 填写信息后发起请求
        const _this = this;
        const signNum = this.data.signCode;
        util.get(`${util.url}/signIn/requestSignIn`, {
            id,
            signNum,
            openId: wx.getStorageSync('OPEN_ID') || ''
        }).then(res => {
            wx.hideLoading();
            if(res.data.status === 200) {
                _this.getSignStuList(id);
                wx.showToast({
                    icon: 'none',
                    title: '发起成功,签到时间为15分钟',
                    duration: 3000
                })
            } else {
                wx.showToast({
                    title: res.data.sData,
                    icon: 'none'
                })
            }
        })
    },
    stuSignInRequest(gpsInfo) {
        const stuId = wx.getStorageSync('ACCOUNT_ID');
        const signNum = this.data.signCode;
        const openId = wx.getStorageSync('OPEN_ID');
        util.get(`${util.url}/signIn/responseSignIn`, {
            stuId,
            signNum,
            openId,
            gpsInfo
        }).then(res => {
            wx.hideLoading();
            if(res.data.status === 200) {
                this.setData({
                    courseName: res.data.oData.courseName,
                    signSucc: true
                })
                wx.showToast({
                    title: '签到成功'
                })
            } else {
                wx.showToast({
                    icon: 'none',
                    title: res.data.sData
                })
            }
        })
    },

    getSignStuList(id = '') {
        // signNum id
        const signNum = this.data.signCode || '';
        const _this = this;
        if(!id && !signNum) return  ;
        wx.showLoading({
            title: '加载中...',
            mask: true
        })
        util.get(`${util.url}/signIn/signInNow`, {
            id,
            signNum
        }).then(res => {
            wx.hideLoading();
            console.log(res)
            if(res.data.status === 200) {
                const {hadSignIn, noSignIn} = res.data.oData;
                if((!hadSignIn || !hadSignIn.length) && (!noSignIn || !noSignIn.length)) {
                    _this.setData({
                        isSignining: false
                    });
                    return ;                    
                } 
                _this.setData({
                    hadSignIn: hadSignIn || [],
                    noSignIn: noSignIn || [],
                    isSignining: true
                })
            }
        })
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options = {}) {
        this.setData({
            isStudent: wx.getStorageSync('IS_STUDENT'),
            type: wx.getStorageSync('TYPE')
        })
        !wx.getStorageSync('IS_STUDENT') && this.judgeIsSigninging();
    },

    // 检验是否登录
    checkIsLogin() {
        let token = wx.getStorageSync('token');
        if(!token) {   // 没有授权  
            wx.navigateTo({
                url: '../welcome/welcome'
            }) 
            return false;
        }
        return true;
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
    onShareAppMessage: function () {
        
    }
})