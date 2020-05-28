// components/tabs-swiper/index.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    roomList: Array
  },

  /**
   * 组件的初始数据
   */
  data: {
    windowWidth: "", //窗口宽度
    currentTab: 0, //预设当前项的值
    scrollLeft: 0, //tab标题的滚动条位置,
    roomList: [0, 1, 2, 3, 4, 5]
  },

  /**
   * 组件的方法列表
   */
  methods: {
    // 滚动切换标签样式
    switchTab: function(e) {
      this.setData({
        currentTab: e.detail.current
      });
      this.checkCor();
      // 改变房型选择
      let room = this.data.roomList[e.detail.current].Rows[0];
      this.triggerEvent('select-room', room)
    },
    // 点击标题切换当前页时改变样式
    swichNav: function(e) {
      // 改变时即改变选择房型
      this.triggerEvent('select-room', e.target.dataset.room)
      
      const _this = this;
      var cur = e.target.dataset.current;
      //每个tab选项宽度占1/3
      var singleNavWidth = this.data.windowWidth / 3;
      //tab选项居中                            
      // this.setData({
      //   scrollLeft: (cur - 2) * singleNavWidth
      // }) 
      if (this.data.currentTaB == cur) {
        return false;
      } else {
        this.setData({
          currentTab: cur
        })
      }
      const query = wx.createSelectorQuery().in(this);
      let tab = query.select('.tab-item.active').boundingClientRect(function(res) {
        _this.setData({
          scrollLeft: cur * 90
        })
      }).exec()
    },
    //判断当前滚动超过一屏时，设置tab标题滚动条。
    checkCor: function() {
      // if (this.data.currentTab > 2) {
      //   this.setData({
      //     scrollLeft: 350
      //   })
      // } else {
      //   this.setData({
      //     scrollLeft: 0
      //   })
      // }
      const _this = this;
      let cur = this.data.currentTab;
      const query = wx.createSelectorQuery().in(this);
      let tab = query.select('.tab-item.active').boundingClientRect(function(res) {
        _this.setData({
          scrollLeft: cur * 90
        })
      }).exec()
    },
    // 打开详情面板
    handleToDetail(e) {
      this.triggerEvent('open-detail', e.currentTarget.dataset.room)
    },
    handleSelectRoom(e) {
      // this.triggerEvent('select-room', e.currentTarget.dataset.room)
      this.handleToDetail(e)
    }
  }
})