$(function(){
	$("#sendBtn").click(send_letter);
	$(".close").click(delete_msg);
});

function send_letter() {
	$("#sendModal").modal("hide");

	// // 发送 AJAX 请求前，将 csrf 令牌设置到请求的消息头中
	// let token = $("meta[name='_csrf']").attr("content");
	// let header = $("meta[name='_csrf_header']").attr("content");
	// $(document).ajaxSend(function (e, xhr, options) {
	// 	xhr.setRequestHeader(header, token);
	// });

	let toName = $("#recipient-name").val();
	let content = $("#message-text").val();
	// 发送异步请求(POST)
	$.post (
		CONTEXT_PATH + "/letter/send",
		{"toName":toName,"content":content},
		function (data) {
			data = $.parseJSON(data);
			if (data.code === 0) {
				// 在提示框中显示返回的消息
				$("#hintBody").text("发送成功！");
			} else {
				$("#hintBody").text(data.msg);
			}
			// 显示提示框
			$("#hintModal").modal("show");
			// 2 秒后，自动隐藏提示框
			setTimeout(function(){
				$("#hintModal").modal("hide");
				// 成功后刷新页面
				location.reload();
			}, 2000);
		}
	);
}

function delete_msg() {
	// TODO 删除数据
	$(this).parents(".media").remove();
}