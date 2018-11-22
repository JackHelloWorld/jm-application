var contentVue;
$(function() {
	contentVue = new Vue({
		el: '#page-content',
		data: {
			showTag: 'activity',
			userInfo : eval('('+getData('login_user')+')'),
		},
		methods: {
			showTagClick: function(tag) {
				this.showTag = tag;
			}
		},
		mounted: function() {}
	});
});