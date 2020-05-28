var app = getApp();
const util = require('../../utils/util');
const { $Message } = require('../../dist/base/index');
let s

Page({

    /**
     * 页面的初始数据
     */
    data: {
        isFinishLoading: false,
        isLogin: false,
        showAddCourse: false,

        // 添加信息
        isAdd: true,
        courseName: '',
        courseCode: '',
        courseTime: '',
        coursePlace: '',
        type: 'student',

        courseWeek: '',
        courseStanza: '',
        stuGrade: '',
        stuMajor: '',
        stuClass: '',
        id: 0,

        isDelete: false,

        searchType: '',
        searchContent: '',
        typeList: [],
        typeIndex: 0,
        typeListArr: [],

        colorArrays: [ "#85B8CF", "#90C652", "#D8AA5A", "#FC9F9D", "#0A9A84", "#61BC69", "#12AEF3", "#E29AAD"],
        wlist: []
            // 星期  上课起点  上课长度
    //   { "xqj": 1, "skjc": 1, "skcd": 3, "kcmc": "高等数学@教A-301" },
    //   { "xqj": 1, "skjc": 5, "skcd": 3, "kcmc": "高等数学@教A-301" },
    //   { "xqj": 2, "skjc": 1, "skcd": 2,"kcmc":"高等数学@教A-301"},
    //   { "xqj": 2, "skjc": 8, "skcd": 2, "kcmc": "高等数学@教A-301" },
    //   { "xqj": 3, "skjc": 4, "skcd": 1, "kcmc": "高等数学@教A-301" },
    //   { "xqj": 3, "skjc": 8, "skcd": 1, "kcmc": "高等数学@教A-301" },
    //   { "xqj": 3, "skjc": 5, "skcd": 2, "kcmc": "高等数学@教A-301" },
    //   { "xqj": 4, "skjc": 2, "skcd": 3, "kcmc": "高等数学@教A-301" },
    //   { "xqj": 4, "skjc": 8, "skcd": 2, "kcmc": "高等数学@教A-301" },
    //   { "xqj": 5, "skjc": 1, "skcd": 2, "kcmc": "高等数学@教A-301" },
    //   { "xqj": 6, "skjc": 3, "skcd": 2, "kcmc": "高等数学@教A-301" },
    //   { "xqj": 7, "skjc": 5, "skcd": 3, "kcmc": "高等数学@教A-301" },]
    },

    // 搜索
    handleSearchBtn() {
        const selectParam = this.data.searchContent;
        const type = wx.getStorageSync('TYPE');
        const selectType = this.data.searchType.key;

        wx.showLoading({
            title: '搜索中...',
            mask: true
        })
        util.post(`${util.url}/course/search`, {
            selectParam,
            type,
            selectType
        }).then(res => {
            wx.hideLoading();
            if(res.data.status === 200) {
                const wlist = this.filterCurWeek(+res.data.oData.currentWeek, res.data.oData.list);
                this.makeWList(wlist);
                this.setData({
                    wlist
                })
            }
        })
    },
    handleGetSearchContent(e) {
        let searchContent;
        this.setData({
            searchContent: e.detail.value
        })
    },
    handleSearchTypeChange(e) {
        this.setData({
            searchType: this.data.typeList[e.detail.value]
        })
    },

    // 导入课程
    importCourse() {
        const openId = wx.getStorageSync('OPEN_ID');
        if(!openId) {  // 没授权            
            wx.navigateTo('../welcome/welcome');
            return ;
        }   
        const type = wx.getStorageSync('TYPE');
        if(wx.getStorageSync('IS_IMPORT_COURSE')) { // 已经导入
            this.getCourse();
            return ;
        }
        wx.showLoading({
            title: '导入课程中...'
        })
        // test
        util.post(`${util.url}/course/import`, {
            openId,
            type
        }).then(res => {
            wx.hideLoading();
            if(res.data.status === 200) {
                wx.showToast({
                    title: '导入成功',
                    duration: 2000
                })
                this.getCourse();
                wx.setStorageSync('IS_IMPORT_COURSE', true);
            } else {
                wx.showToast({
                    title: res.data.sData,
                    icon: 'none'
                })
                wx.setStorageSync('IS_IMPORT_COURSE', false);
            }
        })
    },

    getCourse() {
        wx.showLoading({
            title: '加载课程中...'
        })
        const type = wx.getStorageSync('TYPE') || '';
        const openId = wx.getStorageSync('OPEN_ID') || '';
        util.get(`${util.url}/course/select`, {
            type,
            openId
        }).then(res => {
            wx.hideLoading();
            if(res.data.status === 200) {
                let wlist = res.data.oData.list; 
                wlist = this.filterCurWeek(+res.data.oData.currentWeek, wlist);
                
                console.log(wlist)
                wlist = this.makeWList(wlist);
                console.log(wlist)
                this.setData({
                    wlist
                })
            } else {
                wx.showToast({
                    icon: 'none',
                    title: '数据获取失败'
                })
            }
        })
    },

    filterCurWeek(currentWeek = 1, list) {
        // TDOO 筛选出当前周的课程
        return list.filter(item => {
            console.log(item.courseTime, currentWeek, item.courseName)
            return (Number(item.courseWeek) === currentWeek);
        })
    },

    // wlist字段处理
    makeWList(list) {
        // "xqj": 1, "skjc": 1, "skcd": 3, "kcmc":
        return list.map(item => {
            item.kcmc = item.courseName;
            item.xqj = item.courseTime;
            item.skjc = Number(item.courseStanza[0]);
            item.skcd = item.courseStanza.length;
            return item;
        })
    },


    handleDeleteCourseClick() {
        const _this = this;
        wx.showModal({
            title: '您确定要删除吗',
            success(res) {
                if(res.confirm) {
                    util.post(`${util.url}/course/deleteCourseById`, {
                        id: _this.data.id                        
                    }).then(res => {
                        if(res.data.status === 200) {
                            wx.showToast({
                                title: '删除成功'
                            })
                            _this.getCourse();
                        }
                    })
                    _this.setData({
                        showAddCourse: false
                    })
                }
            }
        })
    },

    // 点击课程
    handleCourseClick(e) {
        // 先获取课程信息
        let courseIdx = e.currentTarget.dataset.index;
        const courseMsg = this.data.wlist[courseIdx];
        console.log(courseMsg)

        this.setData({
            isAdd: false,
            courseName: this.data.wlist[courseIdx].kcmc,
            courseCode: courseMsg.courseId || '',
            courseTime: courseMsg.courseTime || '',
            coursePlace: courseMsg.courseAddr || '',
            showAddCourse: true,
            id: courseMsg.id || '',
            courseWeek: courseMsg.courseWeek || '',
            courseStanza: courseMsg.courseStanza || '',
            stuGrade: courseMsg.stuGrade || '',
            stuMajor: courseMsg.stuMajor || '',
            stuClass: courseMsg.stuClass || '',
        })
    },
    
    handleOpenAddCourse() {
        this.setData({
            showAddCourse: true,
            courseName: '',
            courseCode: '',
            courseTime: '',
            coursePlace: '',
            isAdd: true
        })
    },

    handleCloseAddCourse() {
        this.setData({
            showAddCourse: false,
            courseName: '',
            courseCode: '',
            courseTime: '',
            coursePlace: ''
        })
    },


    handleAddCourse() {
        const _this = this;
        console.log(this.data.courseName
            ,this.data.courseCode
            ,this.data.courseTime
            ,this.data.coursePlace)
        // 判断信息是否填完整了
        if(!this.data.courseName
            || !this.data.courseCode
            || !this.data.courseTime
            || !this.data.coursePlace
            || !this.data.courseWeek
            || !this.data.courseStanza
            || !this.data.stuGrade
            || !this.data.stuMajor
            || !this.data.stuClass
        ) {
            $Message({
                content: '信息不完整',
                type: 'warning'
            });
        } else {
            // TODO 提交信息... 判断是修改还是add
            if(this.data.isAdd) {
                this.addCourseRequest()
                .then(isSucc => {
                    if(isSucc) {
                        wx.showToast({
                            title: '新增成功',
                            duration: 2000
                        })
                        this.getCourse();
                    } else {
                        wx.showToast({
                            icon: 'none',
                            title: '新增失败',
                            duration: 2000
                        })
                    }
                })
            } else {
                this.updataCourseRequest()
                .then(isSucc => {
                    if(isSucc) {
                        wx.showToast({
                            title: '更新成功',
                            duration: 2000
                        })
                    } else {
                        wx.showToast({
                            icon: 'none',
                            title: '更新失败',
                            duration: 2000
                        })
                    }
                })
            }

            this.setData({
                showAddCourse: false,
                courseName: '',
                courseCode: '',
                courseTime: '',
                coursePlace: ''
            })
        }
    },

    updataCourseRequest() {
        wx.showLoading({
            title: '修改课程中...'
        })
        const {
            courseName, 
            courseTime,
            coursePlace,
            courseId,
            courseWeek,
            courseStanza,
            id
        } = this.data;
        return util.post(`${util.url}/course/update`, {
            courseAddr:coursePlace,
            courseId,
            courseWeek,
            courseStanza,
            id,
            courseName, 
            courseTime
        }).then(res => {
            return res.data.status === 200;
        })
    },

    addCourseRequest() {
        wx.showLoading({
            title: '新增课程中...'
        })
        const type = wx.getStorageSync('TYPE');
        const openId = wx.getStorageSync('OPEN_ID');
        const {
            courseName,
            courseCode: courseId,
            courseTime,
            coursePlace: courseAddr,
            courseWeek,
            courseStanza,
            stuGrade,
            stuMajor,
            stuClass,
        } = this.data;
        return util.post(`${util.url}/course/add`, {
            type,
            courseName,
            courseId,
            courseTime,
            courseAddr,
            courseWeek,
            courseStanza,
            stuGrade,
            stuMajor,
            stuClass,
            openId
        }).then(res => {
            wx.hideLoading();
            return res.data.status === 200;
        })
    },
    
    // 输入操作
    handleGetClass(e) {
        let stuClass;
        if(stuClass = e.detail.value) {
            this.setData({
                stuClass
            })
        }
    },
    handleGetMajor(e) {
        let stuMajor;
        if(stuMajor = e.detail.value) {
            this.setData({
                stuMajor
            })
        }
    },
    handleGetGrade(e) {
        let stuGrade;
        if(stuGrade = e.detail.value) {
            this.setData({
                stuGrade
            })
        }
    },
    handleGetCourseStanza(e) {
        let courseStanza;
        if(courseStanza = e.detail.value) {
            this.setData({
                courseStanza
            })
        }
    },
    handleGetCourseWeek(e) {
        let courseWeek;
        if(courseWeek = e.detail.value) {
            this.setData({
                courseWeek
            })
        }
    },
    handleGetCourseName(e) {
        let courseName;
        if(courseName = e.detail.value) {
            this.setData({
                courseName
            })
        }
    },
    handleGetCourseCode(e) {
        let courseCode;
        if(courseCode = e.detail.value) {
            this.setData({
                courseCode
            })
        }
    },
    handleGetCourseTime(e) {
        let courseTime;
        if(courseTime = e.detail.value) {
            this.setData({
                courseTime
            })
        }
    },
    handleGetCoursePlace(e) {
        let coursePlace;
        if(coursePlace = e.detail.value) {
            this.setData({
                coursePlace
            })
        }
    },

    // 检验是否登录
    checkIsLogin() {
        let OPEN_ID = wx.getStorageSync('OPEN_ID');
        if(!OPEN_ID) {   // 没有授权 
            return false;
        }
        
        return true;
    },

    setSearchType() {
        const typeList = [{
            key: 'name',
            title: '课程名字'
        },{
            key: 'courseId',
            title: '课程ID'
        }, {
            key: 'time',
            title: '时间'
        }, {
            key: 'addr',
            title: '上课地点'
        }];
        if(wx.getStorageSync('TYPE') === 'student') {
            typeList.push({
                key: 'teaName',
                title: '教师名字'
            })
        }
        this.setData({
            typeList,
            typeListArr: typeList.map(item => item.title)
        })
    },


    handleToStudentList(){
        wx.navigateTo({
            url: `../studentList/studentList?id=${this.data.id}`
        })
    },
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options = {}) {
        this.setData({
            type: wx.getStorageSync('TYPE')
        })
        this.setSearchType();
        this.importCourse();
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
    // onShareAppMessage: function() {

    // }
})