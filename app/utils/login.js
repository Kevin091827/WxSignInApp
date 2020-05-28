const util = require('./util.js')

const login = function (userinfo, iv, encryptedData) {
  // const session = wx.getStorageSync('session_key');
  wx.login({
    success: function(res) {
      console.log(res)
      const url = `${util.url}/wx/wxLogin`;
      if (res.code) {
        app.globalData.code = res.code
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
          success: handleLoginSucc
        })
      } else {
        console.log('登录失败！' + res.errorMsg)
      }
    }
  })

}

function handleLoginSucc(res) {
  console.log(res)
  if (res.data.status === 200) {
    console.log(res)
    const data = res.data.oData;
    const exp = getExpTime(data.token);
    // wx.setStorageSync('session_key', data.session_key.replace(/\"([^\"]*)\"/, '$1'))
    // wx.setStorageSync('open_id', data.open_id.replace(/\"([^\"]*)\"/, '$1'))
    wx.setStorageSync('session_key', data.session_key)
    wx.setStorageSync('open_id', data.openId)
    wx.setStorageSync('token', data.token)
    wx.setStorageSync('unionId', data.unionId)
    wx.setStorageSync('exp', exp)
    app.globalData.unionId = data.unionId
    console.log("登陆授权成功")
    
    back();
  }
}

function back () {
  var pages = getCurrentPages(); // 当前页面
  var beforePage = pages[pages.length - 2];
  console.log(pages)
  if (pages.length == 1) {
    wx.switchTab({
      url: '/pages/index/index'
    })
  } else {
    console.log(beforePage)
    wx.navigateBack({
      success: function () {
        beforePage.onLoad(); // 执行前一个页面的onLoad方法
      }
    });
  }
}

function getExpTime(str) {
  str = prejudge.decode(str);
  let expTime = str.slice(str.indexOf('exp":') + 5, str.indexOf('"iat') - 1);
  return expTime * 1000;
}

module.exports = login