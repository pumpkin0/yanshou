//页面的初始加载
$.ajax({
	type : "POST",
	url : "./news/queryAll.do",
	success : function(data) {
		//<input type="text" name="itemList[${s.index }].name" style="width:200px; height:18px" value="${item.itemName }"></input>
		var tbody = $("#tbody");
		for(var i = 0; i < data.length; i++){
			console.info(data[i].itemName);
			tbody.append("<tr><td><input type='text'  id='"+data[i].id+"name' style='width:400px' value="+data[i].name+"></input></td>" +
					"<td><input type='text' id='"+data[i].id+"url' style='width:500px' value="+data[i].url+"></input></td>"+
					"<td width='250px'>"+data[i].createDate.slice(0,10)+"</td>"+
					"<td width='40px'><a href='javascript:;' onclick=updateNew(\""+ data[i].id +"\")>修改</a></td>"+
					"<td width='40px'><a href='javascript:;' onclick=deleteNew(\""+ data[i].id +"\")>删除</a></td></tr>");
		}
	},
	dataType : "json"
});

function updateNew(id){
	var name= $('#'+id+'name').val();
	var url= $('#'+id+'url').val();
	var formData = new FormData();
	formData.append("id", id);
	formData.append("name", name);
	formData.append("url", url);
	
	var request = new XMLHttpRequest();
	request.open("POST","http://localhost:9501/news/update.do");
	request.send(formData);

	request.onload = function(data) {
		if (request.status == 200) {
			alert("更新成功");
		}else{
			alert("更新失败");
		}
	}
}


function deleteNew(id){
	var formData = new FormData();
	formData.append("id", id);
	var request = new XMLHttpRequest();
	request.open("POST","http://localhost:9501/news/delete.do");
	request.send(formData);

	request.onload = function(data) {
		if (request.status == 200) {
			location.reload();
		}else{
			alert("删除失败");
		}
	}
}

function newFormSubmit(){
	$.ajax({
        type: "POST",//方法类型
        dataType: "json",//预期服务器返回的数据类型
        url: "news/add.do" ,//url
        data: $('#newsId').serialize(),
        success: function (result) {
            if (result.flag == true) {
            	location.reload();
            };
        },
        error : function() {
            alert("添加失败");
        }
    });
	
}