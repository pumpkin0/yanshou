window.onload = function() {
	// 首先要获取当前的href值
	var url = window.location.href.split('#');
	console.log(url);// 打印出来是一个数组[‘http://localhost:63342/HTML_ExamplePractice/%E5%8D%9A%E5%AE%A2%E5%9B%AD%E6%8F%90%E5%89%8D%E7%BB%83%E4%B9%A0/index2.html’,'id1']
	goHash(url[1]);
}

function goHash(hash) {
	alert(hash);
}
 
 