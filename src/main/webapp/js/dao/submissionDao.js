var app = app || {};

app.SubmissionDao = (function () {
    var HOSTNAME = '';
    
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
    
    return {
        getSubmissionsByTraineeId: getSubmissionsByTraineeId,
        getSubmissionsByTrainerId: getSubmissionsByTrainerId
    };
});