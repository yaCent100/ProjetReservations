$(document).ready(function() {
	console.log("Script is running!");

	$("div[id^='card-']").click(function() {
		console.log("Card clicked!");
		var url = $(this).find("a").attr("href");
		if (url) {
			window.location = url;
		}
	});

	$(".card-text").each(function() {
		var maxLength = 20;
		var myStr = $(this).text();
		if (myStr.length > maxLength) {
			$(this).text(myStr.substring(0, maxLength) + '...');
		}
	});
});
