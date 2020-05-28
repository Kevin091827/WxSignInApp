//请求之前对token的判断
function prejudge(app,reqFn){
  var token=wx.getStorageSync('token').split('.'),
      str=decode(token[1]),
      t_msg=JSON.parse(str.substr(0,str.length-1)),
      time_now = Math.round(new Date() / 1000),
      exp = t_msg.exp;
  if (!token||exp-time_now<600){
    console.log('1', !token, time_now-exp)
    wx.login({
      success(res) {
        if (res.code) {
          console.log(res.code)
          wx.setStorageSync('code', res.code)
          // 发起网络请求
          wx.request({
            url: app.globalData.ymUrl + '/api/login',
            header: {
              'content-type': 'application/json',
            },
            data: {
              code: res.code,
              userHead: wx.getStorageSync('avatarUrl')|| '',
              userName:'',
              userGender:'',
              userCity:'',
              userProvince:'',
            },
            success: function (e) {
              console.log('login', e)
              var token = e.data.oData.token
              wx.setStorageSync('token', token)
              wx.setStorageSync('session_key', e.data.oData.session_key)
              reqFn;
            },
            fail: function (e) {
              console.log('登陆失败', e)
            }
          })
        } else {
          console.log('登录失败！' + res.errMsg)
        }
      }
    })
  }
  else{
    reqFn;
  }
}
//解码函数
var _utf8_decode = function (utftext) {
  var string = "";
  var i = 0;
  var c = 0;
  var c2 = 0;
  var c3 = 0;
  while (i < utftext.length) {
    c = utftext.charCodeAt(i);
    if (c < 128) {
      string += String.fromCharCode(c);
      i++;
    } else if ((c > 191) && (c < 224)) {
      c2 = utftext.charCodeAt(i + 1);
      string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
      i += 2;
    } else {
      c2 = utftext.charCodeAt(i + 1);
      c3 = utftext.charCodeAt(i + 2);
      string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
      i += 3;
    }
  }
  return string;
}
function decode(input) {
  var _keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
  var output = "";
  var chr1, chr2, chr3;
  var enc1, enc2, enc3, enc4;
  var i = 0;
  input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
  while (i < input.length) {
    enc1 = _keyStr.indexOf(input.charAt(i++));
    enc2 = _keyStr.indexOf(input.charAt(i++));
    enc3 = _keyStr.indexOf(input.charAt(i++));
    enc4 = _keyStr.indexOf(input.charAt(i++));
    chr1 = (enc1 << 2) | (enc2 >> 4);
    chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
    chr3 = ((enc3 & 3) << 6) | enc4;
    output = output + String.fromCharCode(chr1);
    if (enc3 != 64) {
      output = output + String.fromCharCode(chr2);
    }
    if (enc4 != 64) {
      output = output + String.fromCharCode(chr3);
    }
  }
  output = _utf8_decode(output);
  return output;
}
module.exports = {
  prejudge:prejudge,
  decode:decode
}
