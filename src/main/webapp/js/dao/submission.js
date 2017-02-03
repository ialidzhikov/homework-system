'use strict';

var app = app || {};

app.SubmissionDao = (function () {
    var HOSTNAME = 'webapi/hmwsrest/v1/';
    
    function getSubmissions() {
        return $.ajax({
            method: 'GET',
            url: HOSTNAME + 'submission',
            dataType: 'json',
            contentType: 'application/json'
        });
    }
    
    function getSubmission(id) {
        return $.ajax({
            method: 'GET',
            url: HOSTNAME + 'submission/' + id,
            dataType: 'json',
            contentType: 'application/json'
        });
    }
    
    function addHomework(lectureId, file) {
    	var formData = new FormData();
    	formData.append('file', file);
    	return $.ajax({
    		method: 'POST',
    		url: HOSTNAME + 'submission/' + lectureId,
    		processData: false,  // tell jQuery not to process the data
    	    contentType: false,  // tell jQuery not to set contentType
    		data: formData,
    		enctype: 'multipart/form-data',
    		cache: false
    	});
    }
    
    function addSubmissionMark(id, mark) {
    	return $.ajax({
    		method: 'PUT',
    		url: HOSTNAME + 'submission/' + id,
    		dataType: 'json',
    		contentType: 'application/json',
    		data: JSON.stringify({ 
    			mark: mark
    		})
    	});
    }
    
    return {
        getSubmissions: getSubmissions,
        getSubmission: getSubmission,
        addHomework: addHomework,
        addSubmissionMark: addSubmissionMark
    };
}());