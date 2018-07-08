//页面的初始加载
$.ajax({
	type : "POST",
	url : "./item/itemList.do",
	success : function(data) {
		console.info(data);
		var tbody = $("#tbody");
		// 清空表格  ,这儿的目的是 切换数据时用
		// tbody.html("");
		
		for(var i = 0; i < data.length; i++){
			console.info(data[i].itemName);
			//tbody.append("<tr><td width='60%'>"+"<a href='javascript:;' onclick='infoItem(\""+data[i].id+"\")'>"+data[i].itemName+"</a></td><td width='20%'>"+data[i].createDate.slice(0,9)+"</td><td>"+data[i].companyName+"</td></tr>");
			tbody.append("<tr><td width='60%'>"+"<a href='javascript:;' onclick='window.open(\"itemDesc.html#"+data[i].id+"\")'>"+data[i].itemName+"</a></td><td width='20%'>"+data[i].createDate.slice(0,9)+"</td><td>"+data[i].companyName+"</td></tr>");
		}
	},
	dataType : "json"
});

function infoItem(itemId){
	
	console.log(window.location.href);
	console.info(itemId);
	var formData = new FormData();
	formData.append("itemId", itemId);
	var request = new XMLHttpRequest();
	request.open("POST","http://localhost:9501/aa.do");
	request.send(formData);

	request.onload = function(data) {
		if (request.status == 200) {
			
		}else{
			console.info(data);
			var tempwindow=window.open('_blank'); // 先打开页面
			tempwindow.location='./itemDesc.html#id=1'; // 后更改页面地址
		}
	}
	
}

