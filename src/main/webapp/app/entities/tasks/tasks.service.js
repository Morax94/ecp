(function() {
    'use strict';
    angular
        .module('ewidencjaCzasuPracyApp')
        .factory('Tasks', Tasks);

    Tasks.$inject = ['$resource', 'DateUtils'];

    function Tasks ($resource, DateUtils) {
        var resourceUrl =  'api/tasks/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.startTime = DateUtils.convertLocalDateFromServer(data.startTime);
                        data.endTime = DateUtils.convertLocalDateFromServer(data.endTime);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.startTime = DateUtils.convertLocalDateToServer(copy.startTime);
                    copy.endTime = DateUtils.convertLocalDateToServer(copy.endTime);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.startTime = DateUtils.convertLocalDateToServer(copy.startTime);
                    copy.endTime = DateUtils.convertLocalDateToServer(copy.endTime);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
