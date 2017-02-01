'use strict';

var app = app || {};

app.Renderer = (function () {
	
	function render(selector, path, data) {
		$.get(path, function (template) {
			var html = Mustache.render(template, data);
			
            $(selector).html(html);
        });
	} 
	
	return {
		render: render
	};
}());