export default {
	
	pageUtils(page,data){
		
		if(data == null) return;
		
		if(page.list == null) page.list = [];
		if(! data.list) data.list=[];
		page.startRow = data.startRow;
		page.navigatepageNums = data.navigatepageNums;
		page.prePage = data.prePage;
		page.hasNextPage = data.hasNextPage;
		page.nextPage = data.nextPage;
		page.pageSize = data.pageSize;
		page.endRow = data.endRow;
		page.pageNum = data.pageNum;
		page.navigatePages = data.navigatePages;
		page.total = data.total;
		page.navigateFirstPage = data.navigateFirstPage;
		page.pages = data.pages;
		page.size = data.size;
		page.isLastPage = data.isLastPage;
		page.hasPreviousPage = data.hasPreviousPage;
		page.navigateLastPage = data.navigateLastPage;
		page.isFirstPage = data.isFirstPage;
		
		for(var i = 0;i<data.list.length;i++){
			page.list.push(data.list[i]);
		}
		
		
	},
	isNull(obj){
		return obj == null || obj == undefined || obj.toString().trim().length == 0
	}
	
	
}