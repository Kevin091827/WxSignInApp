const url = 'ws://'

// 连接 websocket
const WebsocketInit = () => {
	wx.connectSocket({
		url,
		header: {
			'Content-Type': 'application/json'
		},
		method: 'GET',
		success(res) {
			console.log('websocket连接成功')
		},
		fail(res) {
			console.log(res)
		}
	})

	wx.onSocketOpen(function() {
	})

	wx.onSocketMessage(function (data) {
		console.log(data)
	})
}

// 发送数据
const sendMessage = (data) => {
	return new Promise((resolve, reject) => {
		wx.sendSocketMessage({
			data,
			success(res) {
				resolve(res)
			},
			fail(err) {
				reject(err)
			}
		})
	})
}

// 接收数据
const receiveMessage = () => {
	return new Promise((resolve) => {
		wx.onSocketMessage(function(res) {
			resolve(res)	
		})
	})
}




module.exports = {
	WebsocketInit,
	sendMessage,
	receiveMessage
}