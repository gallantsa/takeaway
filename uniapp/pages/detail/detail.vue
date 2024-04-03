<template>
	<view style="padding: 20rpx;">
		<!-- 商家信息 -->
		<view class="box" style="display: flex;">
			<view style="flex: 1; display: flex; flex-direction: column; justify-content: space-between;">
				<view style="font-size: 36rpx; font-weight: bold;">{{ business.name || '' }}</view>
				<view>
					<text style="padding-right: 10rpx; border-right: 2rpx solid #ccc;">平台配送</text>
					<text style="padding: 0 10rpx; border-right: 2rpx solid #ccc;">免配送费</text>
					<text style="padding: 0 10rpx;">30分钟送达</text>
				</view>
				<view>
					地址：{{ business.address || '' }}
				</view>
				<view>
					联系电话：{{ business.phone || '' }}
				</view>
			</view>

			<view style="width: 200rpx; height: 200rpx;">
				<image :src="business.avatar" style="width: 100%; height: 100%; border-radius: 50%;"></image>
			</view>
		</view>
		<!-- 商家信息结束 -->

		<!-- 商家的分类商品列表 -->
		<view style="margin: 20rpx 0;">
			<view
				style="background-color: #fff; display: flex; border-radius: 10rpx; overflow: hidden; margin-bottom: 10rpx;">
				<view style="padding: 10rpx; background-color: #ffd100;">全部商品</view>
				<view style="padding: 10rpx;">评价</view>
			</view>

			<!-- 分类商品展示 -->
			<view style="display: flex; background-color: #fff; border-radius: 10rpx; padding: 10rpx;">
				<view style="width: 160rpx; text-align: center;">
					<view v-for="item in categoryList" :key="item.id" class="category-item"
						:class="{ 'category-active' : item.id === activeCategoryId }" @click="loadGoods(item.id)">
						{{ item.name }}
					</view>
				</view>

				<view style="padding: 20rpx; min-height: 70vh">
					<view style="display: flex; grid-gap: 20rpx; margin-bottom: 20rpx;" v-for="item in goodsList"
						:key="item.id">
						<view style="width: 200rpx; height: 200rpx;">
							<image :src="item.img"
								style="width: 200rpx; height: 200rpx; border-radius: 10rpx; display: block;"></image>
						</view>
						<view style="flex: 1; display: flex; flex-direction: column; justify-content: space-between;">
							<view style="font-size: 32rpx; font-weight: bold;">{{ item.name }}</view>
							<view style="font-size: 24rpx;">{{ item.descr }}</view>
							<view>
								<text class="mini-btn">7折</text>
								<text style="font-size: 24rpx; margin-left: 10rpx;">已售 30</text>
							</view>
							<view>
								<text style="text-decoration: line-through;">￥{{ item.price }}</text>
								<text style="color: orangered; margin-left: 10rpx;">￥{{ item.money || 0 }}</text>
								<text class="mini-btn" style="margin-left: 5rpx;">到手价</text>
							</view>
							<view>
								<text class="mini-btn-fill">选购</text>
							</view>
						</view>
					</view>
				</view>
			</view>
		</view>
		<!-- 商家的分类商品列表结束 -->

	</view>
</template>

<script>
	export default {
		data() {
			return {
				businessId: 0,
				business: {},
				categoryList: [],
				activeCategoryId: 0,
				goodsList: []
			}
		},
		onLoad(option) {
			this.businessId = option.businessId
			this.load()
		},
		methods: {
			load() {
				this.$request.get('/business/selectById/' + this.businessId).then(res => {
					this.business = res.data || {}
					console.log(this.business)
				})

				this.$request.get('/category/selectAll', {
					businessId: this.businessId
				}).then(res => {
					this.categoryList = res.data || []
					this.activeCategoryId = this.categoryList.length > 0 ? this.categoryList[0].id : 0
					this.loadGoods(this.activeCategoryId)
				})
			},
			loadGoods(categoryId) {
				this.activeCategoryId = categoryId
				this.$request.get('/goods/selectAll', {
					categoryId: categoryId
				}).then(res => {
					this.goodsList = res.data || []
				})
			}
		}
	}
</script>

<style>
	.category-item {
		padding: 15rpx;
		background-color: #eee;
		border-bottom: 2rpx solid #fff;
	}

	.category-active {
		background-color: #fff;
		color: orange;
		border-left: 5rpx solid orange;
	}

	.mini-btn {
		border: 2rpx solid orangered;
		padding: 0 4rpx;
		font-size: 22rpx;
		color: orangered;
		border-radius: 5rpx;
		font-weight: bold;
	}

	.mini-btn-fill {
		background-color: orange;
		padding: 0 4rpx;
		color: #fff;
		font-size: 24rpx;
		border-radius: 5rpx;
	}
</style>