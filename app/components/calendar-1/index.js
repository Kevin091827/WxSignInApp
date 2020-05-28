var Moment = require('../../utils/moment.js');
import util from '../../utils/util';

Component({
	/**
	 * 组件的属性列表
	 */
	properties: {
		price: Array
	},

	/**
	 * 组件的初始数据
	 */
	data: {
		isFinish: false, // 是否完整的点击了住店还有离店

		timer: null,
		today: {},
		isSelectCheckoutDate: true, // 是否选择了离店日期
		dayCounts: 2,
		DATE_LIST: [],
		DATE_YEAR: new Date().getFullYear(),
		DATE_MONTH: new Date().getMonth() + 1,
		DATE_DAY: new Date().getDate(),
		maxMonth: 7, //最多渲染月数
		dateList: [],
		systemInfo: {},
		weekStr: ['日', '一', '二', '三', '四', '五', '六'],
		checkInDate: Moment(new Date()).format('YYYY-MM-DD'),
		checkOutDate: Moment(new Date())
			.add(1, 'day')
			.format('YYYY-MM-DD'),
		markcheckInDate: false, //标记开始时间是否已经选择
		markcheckOutDate: false, //标记结束时间是否已经选择
		sFtv: [
			{
				month: 1,
				day: 1,
				name: '元旦'
			},
			{
				month: 2,
				day: 14,
				name: '情人节'
			},
			{
				month: 3,
				day: 8,
				name: '妇女节'
			},
			{
				month: 3,
				day: 12,
				name: '植树节'
			},
			{
				month: 3,
				day: 15,
				name: '消费者权益日'
			},
			{
				month: 4,
				day: 1,
				name: '愚人节'
			},
			{
				month: 5,
				day: 1,
				name: '劳动节'
			},
			{
				month: 5,
				day: 4,
				name: '青年节'
			},
			{
				month: 5,
				day: 12,
				name: '护士节'
			},
			{
				month: 6,
				day: 1,
				name: '儿童节'
			},
			{
				month: 7,
				day: 1,
				name: '建党节'
			},
			{
				month: 8,
				day: 1,
				name: '建军节'
			},
			{
				month: 9,
				day: 10,
				name: '教师节'
			},
			{
				month: 9,
				day: 28,
				name: '孔子诞辰'
			},
			{
				month: 10,
				day: 1,
				name: '国庆节'
			},
			{
				month: 10,
				day: 6,
				name: '老人节'
			},
			{
				month: 10,
				day: 24,
				name: '联合国日'
			},
			{
				month: 12,
				day: 24,
				name: '平安夜'
			},
			{
				month: 12,
				day: 25,
				name: '圣诞节'
			}
		]
	},

	methods: {
		init(options) {
			let todayD = new Date(),
				today = {};
			today.year = todayD.getFullYear();
			today.month = todayD.getMonth() + 1;
			today.day = todayD.getDate();
			this.setData({
				markcheckOutDate: false,
				markcheckInDate: false,
				today
			});
			// 页面初始化 options为页面跳转所带来的参数
			this.createDateListData();
			var _this = this;
			// 页面初始化 options为页面跳转所带来的参数

			var checkInDate = options.checkInDate
				? options.checkInDate
				: Moment(new Date()).format('YYYY-MM-DD');
			var checkOutDate = options.checkOutDate
				? options.checkOutDate
				: Moment(new Date())
						.add(1, 'day')
						.format('YYYY-MM-DD');
			wx.getSystemInfo({
				success: function(res) {
					_this.setData({
						systemInfo: res,
						checkInDate: checkInDate,
						checkOutDate: checkOutDate
					});
				}
			});
			this.selectDataMarkLine();
		},
		//选择的入住与离店时间段
		selectDataMarkLine: function() {
			// 房间价格
			let roomPrice = this.data.price;

			// 是否到入住日期
			let isEnterBeginDate = false;
			let dl = this.data.DATE_LIST;

			let dateList = this.data.dateList;
			// let { checkInDate, checkOutDate } = wx.getStorageSync("ROOM_SOURCE_DATE");
			let checkInDate = this.data.checkInDate;
			let checkOutDate = this.data.checkOutDate;

			let curreInid =
				checkInDate.substr(0, 4) +
				'-' +
				(checkInDate.substr(5, 2) < 10
					? checkInDate.substr(6, 1)
					: checkInDate.substr(5, 2)); //选择入住的id
			let curreOutid =
				checkOutDate.substr(0, 4) +
				'-' +
				(checkOutDate.substr(5, 2) < 10
					? checkOutDate.substr(6, 1)
					: checkOutDate.substr(5, 2)); //选择离店的id
			let dayIn =
				checkInDate.substr(8, 2) >= 10
					? checkInDate.substr(8, 2)
					: checkInDate.substr(9, 1); //选择入住的天id
			let dayOut =
				checkOutDate.substr(8, 2) >= 10
					? checkOutDate.substr(8, 2)
					: checkOutDate.substr(9, 1); //选择离店的天id
			let monthIn =
				checkInDate.substr(5, 2) >= 10
					? checkInDate.substr(5, 2)
					: checkInDate.substr(6, 1); //选择入店的月id
			let monthOut =
				checkOutDate.substr(5, 2) >= 10
					? checkOutDate.substr(5, 2)
					: checkOutDate.substr(6, 1); //选择离店的月id
			if (curreInid == curreOutid) {
				//入住与离店是当月的情况
				for (let i = 0; i < dateList.length; i++) {
					if (dateList[i].id == curreInid) {
						let days = dateList[i].days;
						for (let k = 0; k < days.length; k++) {
							if (days[k].day == dayOut) {
								isEnterBeginDate = false;
								days[k].class = days[k].class + ' dayout-bgitem';
							}
							if (days[k].day == dayIn) {
								isEnterBeginDate = true;
								days[k].class = days[k].class + ' dayin-bgitem';
							}
							if (days[k].day > dayIn && days[k].day < dayOut) {
								days[k].class = days[k].class + ' bgitem';
							}
							if (days[k].day == dayIn) {
								days[k].class = days[k].class + ' active';
								days[k].inday = true;
							}
							if (days[k].day == dayOut) {
								days[k].class = days[k].class + ' active';
								days[k].outday = true;
							}
						}
					}
				}
			} else {
				//跨月
				for (let j = 0; j < dateList.length; j++) {
					if (dateList[j].month == monthIn) {	
						console.log('入住开始的月份>>>', dateList[j].month)
						//入住的开始月份
						let days = dateList[j].days;
						for (let k = 0; k < days.length; k++) {
							if (days[k].day == dayIn) {
								isEnterBeginDate = true;
								days[k].class = days[k].class + ' dayin-bgitem';
							}
							if (days[k].day > dayIn) {
								days[k].class = days[k].class + ' bgitem';
							}
							if (days[k].day == dayIn) {
								days[k].class = days[k].class + ' active';
								days[k].inday = true;
							}

							// 加房间价格
							// if (isEnterBeginDate || days[k].day == dayOut) {
							// 	roomPrice.forEach((item, index) => {
							// 		if (days[k].day === item.PriceDate.slice(-2)) {
							// 			days[k].price = item.Price
							// 			dl[i].days[k].price = item.Price;
							// 		}
							// 	})
							// }
						}

						// 这个月加上房间价格
						// days.forEach((day, k) => {
						// 	roomPrice.forEach((item, index) => {
						// 		if (days[k].day === +item.PriceDate.slice(-2)) {
						// 			days[k].price = item.Price;
						// 			dl[j].days[k].price = item.Price;
						// 			if (!item.CanBooking) { // 不可预定
						// 				days[k].canbooking = false;
						// 			}
						// 		}
						// 	})
						// })
					} else {
						//入住跨月月份
						if (dateList[j].month < monthOut && dateList[j].month > monthIn) {
							console.log('入住跨月月份>>>', dateList[j].month)
							//离店中间的月份
							let days = dateList[j].days;
							for (let k = 0; k < days.length; k++) {
								days[k].class = days[k].class + ' bgitem';

								// 加房间价格
								// if (isEnterBeginDate || days[k].day == dayOut) {
								// 	roomPrice.forEach((item, index) => {
								// 		if (days[k].day === item.PriceDate.slice(-2)) {
								// 			days[k].price = item.Price
								// 			dl[i].days[k].price = item.Price;
								// 		}
								// 	})
								// }
							}

							// 这个月加上房间价格
							// days.forEach((day, k) => {
							// 	roomPrice.forEach((item, index) => {
							// 		console.log(days[k].day, +item.PriceDate.slice(-2))
							// 		if (days[k].day === +item.PriceDate.slice(-2)) {
							// 			days[k].price = item.Price;
							// 			dl[j].days[k].price = item.Price;
							// 			if (!item.CanBooking) { // 不可预定
							// 				days[k].canbooking = false;
							// 			}
							// 		}
							// 	})
							// })
						} else if (dateList[j].month == monthOut) {
							console.log('离店最后的月份>>>', dateList[j].month)
							//离店最后的月份
							let days = dateList[j].days;
							for (let k = 0; k < days.length; k++) {
								if (days[k].day == dayOut) {
									isEnterBeginDate = false;
									days[k].class = days[k].class + ' dayout-bgitem';
								}
								if (days[k].day < dayOut) {
									days[k].class = days[k].class + ' bgitem';
								}
								if (days[k].day == dayOut) {
									days[k].class = days[k].class + ' active';
									days[k].outday = true;
								}
								// 加房间价格
								// if (isEnterBeginDate || days[k].day == dayOut) {
								// 	roomPrice.forEach((item, index) => {
								// 		if (days[k].day === item.PriceDate.slice(-2)) {
								// 			days[k].price = item.Price
								// 			dl[i].days[k].price = item.Price;
								// 		}
								// 	})
								// }
							}

							// 这个月加上房间价格
							// days.forEach((day, k) => {
							// 	roomPrice.forEach((item, index) => {
							// 		console.log(days[k].day, +item.PriceDate.slice(-2))
							// 		if (days[k].day === +item.PriceDate.slice(-2)) {
							// 			days[k].price = item.Price;
							// 			dl[j].days[k].price = item.Price;
							// 			if (!item.CanBooking) { // 不可预定
							// 				days[k].canbooking = false;
							// 			}
							// 		}
							// 	})
							// })
						}
					}
					// 加房间价格
				}
			}

			const curDay = +checkInDate.split('-')[2];
			let crossDay = 0; // 跨月时下一个月需要展示价格的天数
			// 判断是否跨月
			if (curDay + 27 > this.getCountDays()) {
				crossDay = 27 - (this.getCountDays() - curDay);
			}

			let curMonthPrice, nextMonthPrice; // 入住当月与下个月需要展示的价格
			if (crossDay) {
				curMonthPrice = roomPrice.slice(0, roomPrice.length - crossDay);
				nextMonthPrice = roomPrice.slice(-crossDay);
			} else {
				curMonthPrice = roomPrice;
				nextMonthPrice = [];
			}

			// 加上选择的开始日期到以后28天的价格
			if (roomPrice[roomPrice.length - 1]) {
				for (let i = 0; i < dateList.length; i++) {
					const isCross =
						+roomPrice[roomPrice.length - 1].PriceDate.slice(5, 7) !== +monthIn;
					if (dateList[i].id == curreInid) {
						let days = dateList[i].days;
						// 这个月加上房间价格
						days.forEach((day, k) => {
							curMonthPrice.forEach((item, index) => {
								if (days[k].day === +item.PriceDate.slice(-2)) {
									days[k].price = item.Price;
									dl[i].days[k].price = item.Price;
									if (!item.CanBooking) {
										// 不可预定
										days[k].canbooking = false;
									}
								}
							});
						});
					} else if (
						isCross &&
						+dateList[i].id.slice(5) === +curreInid.slice(5) + 1
					) {
						// 跨月
						let days = dateList[i].days;
						// 这个月加上房间价格
						days.forEach((day, k) => {
							nextMonthPrice.forEach((item, index) => {
								if (days[k].day === +item.PriceDate.slice(-2)) {
									days[k].price = item.Price;
									dl[i].days[k].price = item.Price;
									if (!item.CanBooking) {
										// 不可预定
										days[k].canbooking = false;
									}
								}
							});
						});
					} else if (isCross && +dateList[i].id.slice(5) === 1) {
						// 跨年
						let days = dateList[i].days;
						// 这个月加上房间价格
						days.forEach((day, k) => {
							nextMonthPrice.forEach((item, index) => {
								if (days[k].day === +item.PriceDate.slice(-2)) {
									days[k].price = item.Price;
									dl[i].days[k].price = item.Price;
									if (!item.CanBooking) {
										// 不可预定
										days[k].canbooking = false;
									}
								}
							});
						});
					}
				}
			}

			this.setData({
				dateList: dateList
			});
		},

		// 获取本月天数
		getCountDays() {
			var curDate = new Date();
			/* 获取当前月份 */
			var curMonth = curDate.getMonth();
			/*  生成实际的月份: 由于curMonth会比实际月份小1, 故需加1 */
			curDate.setMonth(curMonth + 1);
			/* 将日期设置为0, 这里为什么要这样设置, 我不知道原因, 这是从网上学来的 */
			curDate.setDate(0);
			/* 返回当月的天数 */
			return curDate.getDate();
		},

		deepClone(obj) {
			let _obj = JSON.stringify(obj),
				objClone = JSON.parse(_obj);
			return objClone;
		},

		createDateListData: function() {
			let DATE_YEAR = this.data.DATE_YEAR;
			let DATE_MONTH = this.data.DATE_MONTH;
			let DATE_DAY = this.data.DATE_DAY;
			let dateList = [];
			var originDateList = [];
			var now = new Date();
			/*
			  设置日期为 年-月-01,否则可能会出现跨月的问题
			  比如：2017-01-31为now ,月份直接+1（now.setMonth(now.getMonth()+1)），则会直接跳到跳到2017-03-03月份.
			    原因是由于2月份没有31号，顺推下去变成了了03-03
			*/
			now = new Date(now.getFullYear(), now.getMonth(), 1);
			for (var i = 0; i < this.data.maxMonth; i++) {
				var momentDate = Moment(now).add(
					this.data.maxMonth - (this.data.maxMonth - i),
					'month'
				).date;
				var year = momentDate.getFullYear();
				var month = momentDate.getMonth() + 1;

				let days = [];
				var originDays = [];

				var totalDay = this.getTotalDayByMonth(year, month);
				var week = this.getWeek(year, month, 1);
				//-week是为了使当月第一天的日期可以正确的显示到对应的周几位置上，比如星期三(week = 2)，
				//则当月的1号是从列的第三个位置开始渲染的，前面会占用-2，-1，0的位置,从1开正常渲染
				for (var j = -week + 1; j <= totalDay; j++) {
					var tempWeek = -1;
					if (j > 0) tempWeek = this.getWeek(year, month, j);
					let clazz = '';
					if (tempWeek == 0 || tempWeek == 6) clazz = 'week';
					if (j < DATE_DAY && year == DATE_YEAR && month == DATE_MONTH) {
						//当天之前的日期不可用
						clazz = 'unavailable ' + clazz;
					} else {
						clazz = '' + clazz;
					}
					days.push({
						day: j,
						class: clazz
					});
				}
				var dateItem = {
					id: year + '-' + month,
					year: year,
					month: month,
					days: days
				};
				dateList.push(dateItem);
			}

			var sFtv = this.data.sFtv;
			// for (let i = 0; i < dateList.length; i++) { //加入公历节日
			// 	for (let k = 0; k < sFtv.length; k++) {
			// 		if (dateList[i].month == sFtv[k].month) {
			// 			let days = dateList[i].days;
			// 			for (let j = 0; j < days.length; j++) {
			// 				if (days[j].day == sFtv[k].day) {
			// 					days[j].daytext = sFtv[k].name
			// 				}
			// 			}
			// 		}
			// 	}
			// }
			let DATE_LIST = this.deepClone(dateList);
			this.setData({
				dateList,
				DATE_LIST
			});
		},

		/*
		 * 获取月的总天数
		 */
		getTotalDayByMonth: function(year, month) {
			month = parseInt(month, 10);
			var d = new Date(year, month, 0);
			return d.getDate();
		},
		/*
		 * 获取月的第一天是星期几
		 */
		getWeek: function(year, month, day) {
			var d = new Date(year, month - 1, day);
			return d.getDay();
		},
		/**
		 * 点击日期事件
		 */
		onPressDate: function(e) {
			let DATE_MONTH = this.data.DATE_MONTH;
			let DATE_DAY = this.data.DATE_DAY;
			let DATE_LIST = this.data.DATE_LIST;
			var { year, month, day } = e.currentTarget.dataset;

			if ((day < DATE_DAY && month == DATE_MONTH) || day <= 0) {
				return;
			}

			var tempMonth = month;
			var tempDay = day;

			if (month < 10) tempMonth = '0' + month;
			if (day < 10) tempDay = '0' + day;

			var date = year + '-' + tempMonth + '-' + tempDay;
			const self = this;
			//如果点击选择的日期A小于入住时间，或者是已经选择了离店时间后再次点击，则重新渲染入住时间为A
			if (
				(this.data.markcheckInDate &&
					Moment(date).before(this.data.checkInDate)) ||
				this.data.checkInDate === date ||
				this.data.markcheckOutDate
			) {
				this.setData({
					markcheckInDate: false,
					markcheckOutDate: false,
					dateList: this.deepClone(DATE_LIST),
					isFinish: false
				});
				wx.showToast({
					icon: 'none',
					title: '请选择离店日期'
				});
			}
			if (!this.data.markcheckInDate) {
				clearTimeout(this.data.timer);
				
				// 选择的是入住日期
				this.setData({
					checkInDate: date,
					markcheckInDate: true,
					dateList: this.deepClone(DATE_LIST),
					isFinish: false,
					timer: null
				});
			} else if (!this.data.markcheckOutDate) {
				// 选择的是离店日期
				// 总天数
				let dayCounts = util.countDate(
					new Date(this.data.checkInDate),
					new Date(date)
				);

				// 判断是否超过28天
				if(this.isOverMaxDay(date)) {
					wx.showToast({
						icon: 'none',
						title: '不能超过28天！'
					})
					this.setData({
						isFinish: false
					})
					return ;
				}

				this.setData({
					checkOutDate: date,
					markcheckOutDate: true,
					dayCounts,
					isFinish: true
				});

				this.renderCheckInToOut();
				
				// 延时3秒后直接关闭日历
				let timer = setTimeout(() => {
		          this.triggerEvent('close-calendar', {
		            checkInDate: this.data.checkInDate,
					checkOutDate: this.data.checkOutDate
		          });
				}, 3000)
				
				this.setData({
					timer
				})
			}

			this.renderPressStyle(year, month, day);
		},

		// 判断是否选择超过28一天
		isOverMaxDay(checkOutDate) {
			const checkInDateTimestamp = +new Date(this.data.checkInDate.replace(/-/g, '/')),
				checkOutDateTimestamp = +new Date(checkOutDate.replace(/-/g, '/'));

			return (checkOutDateTimestamp - checkInDateTimestamp) > (28 * 24 * 60 * 60 * 1000);
		},

		// 渲染入住到离店日期样式
		renderCheckInToOut() {
			let checkInDate = this.data.checkInDate,
				checkOutDate = this.data.checkOutDate;
			let dateList = this.data.dateList;
			// DATALIST

			let [inYear, monthIn, dayIn] = checkInDate
					.split('-')
					.map(item => Number(item)),
				[outYear, monthOut, dayOut] = checkOutDate
					.split('-')
					.map(item => Number(item));

			let curreInid = `${inYear}-${monthIn}`,
				isEnterBeginDate = false;

			if (monthIn == monthOut) {
				// 入住与离店是当月的情况
				for (let i = 0; i < dateList.length; i++) {
					if (dateList[i].id == curreInid) {
						// 到了入住的月份
						let days = dateList[i].days;
						for (let k = 0; k < days.length; k++) {
							if (days[k].day == dayOut) {
								isEnterBeginDate = false;
								days[k].class = days[k].class + ' dayout-bgitem';
							}
							if (days[k].day == dayIn) {
								isEnterBeginDate = true;
								days[k].class = days[k].class + ' dayin-bgitem';
							}
							if (days[k].day > dayIn && days[k].day < dayOut) {
								days[k].class = days[k].class + ' bgitem';
							}
							if (days[k].day == dayIn) {
								days[k].class = days[k].class + ' active';
								days[k].inday = true;
							}
							if (days[k].day == dayOut) {
								days[k].class = days[k].class + ' active';
								days[k].outday = true;
							}
						}
					}
				}
			} else {
				//跨月
				for (let j = 0; j < dateList.length; j++) {
					if (dateList[j].month == monthIn) {
						//入住的开始月份
						let days = dateList[j].days;
						for (let k = 0; k < days.length; k++) {
							if (days[k].day == dayIn) {
								isEnterBeginDate = true;
								days[k].class = days[k].class + ' dayin-bgitem';
							}
							if (days[k].day > dayIn) {
								days[k].class = days[k].class + ' bgitem';
							}
							if (days[k].day == dayIn) {
								days[k].class = days[k].class + ' active';
								days[k].inday = true;
							}
						}
					} else {
						//入住跨月月份
						if (dateList[j].month < monthOut) {
							//离店中间的月份
							let days = dateList[j].days;
							for (let k = 0; k < days.length; k++) {
								days[k].class = days[k].class + ' bgitem';
							}
						} else if (dateList[j].month == monthOut) {
							//离店最后的月份
							let days = dateList[j].days;
							for (let k = 0; k < days.length; k++) {
								if (days[k].day == dayOut) {
									isEnterBeginDate = false;
									days[k].class = days[k].class + ' dayout-bgitem';
								}
								if (days[k].day < dayOut) {
									days[k].class = days[k].class + ' bgitem';
								}
								if (days[k].day == dayOut) {
									days[k].class = days[k].class + ' active';
									days[k].outday = true;
								}
							}
						}
					}
				}
			}
		},

		renderPressStyle: function(year, month, day) {
			var dateList = this.data.dateList;
			//渲染点击样式
			for (var i = 0; i < dateList.length; i++) {
				var dateItem = dateList[i];
				var id = dateItem.id;
				if (id === year + '-' + month) {
					var days = dateItem.days;

					for (var j = 0; j < days.length; j++) {
						var tempDay = days[j].day;
						if (tempDay == day) {
							if (this.data.markcheckOutDate) {
								// 此时选择的是入住日期
								days[j].class = days[j].class + ' active';
								days[j].inday = false;
								days[j].outday = true;
								break;
							} else {
								// 选择的是退房日期
								days[j].class = days[j].class + ' active';
								days[j].outday = false;
								days[j].inday = true;
								break;
							}
						}
					}
					break;
				}
			}
			this.setData({
				dateList: dateList
			});
		},

		fCloseCalendar() {
			clearTimeout(this.data.timer);
			this.setData({
				timer: null
			})
			if(this.data.isFinish) {
				this.triggerEvent('close-calendar', {
					checkInDate: this.data.checkInDate,
					checkOutDate: this.data.checkOutDate,
					timer: this.data.timer
				}); // 修改：不自动关闭日历，点击关闭后传递日期
			} else {
				this.triggerEvent('close-calendar', {
					timer: this.data.timer
				})
			}
		}
	},

	attached() {
		// this.init({})
	}
});
