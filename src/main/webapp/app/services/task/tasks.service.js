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
                        data.startTime = DateUtils.convertDateTimeFromServer(data.startTime);
                        data.endTime = DateUtils.convertDateTimeFromServer(data.endTime);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
