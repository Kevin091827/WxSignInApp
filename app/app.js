//app.js
var mta= require('/utils/mta_analysis.js') 
App({
	onLaunch: function (options) {
		// wx.hideShareMenu()
		// 展示本地存储能力
		var logs = wx.getStorageSync('logs') || []
		logs.unshift(Date.now())
		wx.setStorageSync('logs', logs)

		// 登录
		wx.login({
			success: res => {
				// 发送 res.code 到后台换取 openId, sessionKey, unionId
			}
		})
		// 获取用户信息
		wx.getSetting({
			success: res => {
				if (res.authSetting['scope.userInfo']) {
					// 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
					wx.getUserInfo({
						success: res => {
							// 可以将 res 发送给后台解码出 unionId
							this.globalData.userInfo = res.userInfo

							// 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
							// 所以此处加入 callback 以防止这种情况
							if (this.userInfoReadyCallback) {
								this.userInfoReadyCallback(res)
							}
						}
					})
				}
			}
		})




		wx.getSystemInfo({
			success: (res) => {
				this.globalData.statusBarHeight = res.statusBarHeight
			}
		})
		// mta.App.init({     
		// 	"appID":"500682539",         
		// 	"eventID":"500682540",                                      
		// 	// "lauchOpts":options,           
		// 	// "statPullDownFresh": true ,      
		// 	//  "statShareApp": true ,         
		// 	// "statReachBottom": true,      
		// 	"autoReport": true ,      
		// 	"statParam": true ,      
		// 	"ignoreParams": []  
		// });


		mta.App.init({
			"appID":"500682539",
			"eventID":"500682540",
			"autoReport": true,
			"statParam": true,
			"ignoreParams": [],
			"statShareApp":true
	  });
			
	},
	 /**
   * 授权
   * scope.userInfo wx.getUserInfo  用户信息
      scope.userLocation    wx.getLocation, wx.chooseLocation   地理位置
      scope.address wx.chooseAddress    通讯地址
      scope.invoiceTitle    wx.chooseInvoiceTitle   发票抬头
      scope.werun   wx.getWeRunData 微信运动步数
      scope.record  wx.startRecord  录音功能
      scope.writePhotosAlbum    wx.saveImageToPhotosAlbum, wx.saveVideoToPhotosAlbum    保存到相册
      scope.camera      摄像头
   */
	checkAuthorize(scope) {
		wx.getSetting({
		  success: (res) => {
			console.log(res.authSetting[scope])
			if (!res.authSetting[scope]) {
			  wx.showModal({
				title: '用户未授权',
				content: '拒绝授权将不能体验小程序完整功能，点击确定开启授权',
				success: (res) => {
				  console.log(res)
				  if (res.confirm) {
					wx.openSetting({})
				  }
				}
			  })
			}
		  }
		})
	  },
	globalData: {
		userInfo: null,
		statusBarHeight: 0,
		code: null,

		// 重新授权后重新获取数据请求
		promiseQueue: [],
		exeQueue: true,
		page:null,
		isWaiting:false,
		//是否审核失败
		isReview:false
	}
})