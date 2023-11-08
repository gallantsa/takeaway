<template>
	<view style="padding: 20rpx;">
		<view
			style="padding: 20rpx; margin: 80rpx 0; background-color: #fff; box-shadow: 0 2rpx 10rpx rgba(0, 0, 0, .1); border-radius: 10rpx;">
			<view style="margin: 50rpx 30rpx; font-size: 40rpx;">欢迎登录</view>
			<uni-forms ref="form" :modelValue="form">
				<uni-forms-item required>
					<uni-easyinput prefixIcon="person" v-model="form.username" placeholder="请输入账号" />
				</uni-forms-item>
				<uni-forms-item required>
					<uni-easyinput prefixIcon="locked" v-model="form.password" placeholder="请输入密码" />
				</uni-forms-item>
				<uni-forms-item>
					<button @click="login()"
						style="background-color: #ffd100; border-color: #ffd100; height: 70rpx; line-height: 70rpx;">登录</button>
				</uni-forms-item>
			</uni-forms>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				form: {
					role: 'USER'
				}
			}
		},
		methods: {
			login() {
				this.$request.post('/login', this.form).then(res => {
					if (res.code === '200') {
						uni.showToast({
							icon: 'success',
							title: '登录成功'
						})
						uni.setStorageSync('xm-user', res.data)

						// 跳转主页
						uni.switchTab({
							url: '/pages/index/index'
						})
					} else {
						uni.showToast({
							icon: 'error',
							title: res.msg
						})
					}
				})
			}
		}
	}
</script>

<style>

</style>