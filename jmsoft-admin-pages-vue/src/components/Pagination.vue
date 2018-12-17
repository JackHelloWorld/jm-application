<template>
	<div id="Pagination">
		<div v-if="page.list != null && page.list.length > 0" class="el-pagination"><span class="el-pagination__total">共 {{ page.total }}
				条</span><span class="el-pagination__sizes">
				<div class="el-select el-select--mini">

					<div class="el-input el-input--mini el-input--suffix">
						<el-select v-model="queryData.pageSize" placeholder="请选择" @change="query()">
							<el-option v-for="item in sizeList" :key="item" :label="item+'条/页'" :value="item">
							</el-option>
						</el-select>
					</div>
				</div>
			</span>
			<button type="button" class="btn-prev" @click="clickNumber(page.prePage)" :disabled="page.isFirstPage"><i class="el-icon el-icon-arrow-left"></i></button>
			<ul class="el-pager">
				<li @click="clickNumber(num)" v-for="num in page.navigatepageNums" :class="'number '+(num == page.pageNum?'active':'')"
				 v-html="num"></li>
			</ul><button type="button" class="btn-next" @click="clickNumber(page.nextPage)" :disabled="page.isLastPage"><i class="el-icon el-icon-arrow-right"></i></button><span
			 class="el-pagination__jump">前往<div class="el-input el-pagination__editor is-in-pagination">
					<input type="number" @keydown="keydown($event)" @blur="query()" v-model="queryData.pageNumber" autocomplete="off"
					 min="1" class="el-input__inner">
				</div>页</span>
		</div>
	</div>
</template>

<script>
	export default {

		data() {
			return {
				sizeList: [10, 50, 100, 200]
			}
		},
		methods: {
			keydown(event) {
				if (event.keyCode == 13) {
					this.query();
				}
			},
			clickNumber(number) {
				if (number == this.queryData.pageNumber) return;
				this.queryData.pageNumber = number;
				this.query();
			},
			query() {

				if (this.queryData.pageNumber == null || this.queryData.pageNumber == '')
					return;

				if (this.queryData.pageNumber > this.page.pages) {
					this.queryData.pageNumber = this.page.pages;
				}


				this.This[this.queryMethod]();
			}
		},
		props: ['page', 'queryMethod', 'sizes', 'This', 'queryData'],
		mounted: function() {
			if (this.sizes && this.sizes.length > 0) {
				this.sizeList = this.sizes;
			}

		},
	}
</script>

<style scoped>
	#Pagination {
		text-align: right;
		padding-top: 15px;
	}
</style>
