var app = app || {};

app.SubmissionDao = (function () {
    var HOSTNAME = 'webapi/hmwsrest/v1/';
    
    function getSubmissionsByTraineeId(traineeId) {
        return $.ajax({
            method: 'GET',
            url: HOSTNAME + 'submissions?query={"traineeId": "' + traineeId + '"}',
            dataType: 'json'
        });
    }
    
    function getSubmissionsByTrainerId(trainerId) {
        return $.ajax({
            method: 'GET',
            url: HOSTNAME + 'submissions?query={"trainerId": "' + trainerId + '"}',
            dataType: 'json'
        });
    }
    
    function addHomework(lectureId, file) {
    	var formData = new FormData(file);
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
    
    return {
        getSubmissionsByTraineeId: getSubmissionsByTraineeId,
        getSubmissionsByTrainerId: getSubmissionsByTrainerId,
        addHomework: addHomework
    };
}());