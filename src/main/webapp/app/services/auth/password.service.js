(function() {
    'use strict';

    angular
        .module('ewidencjaCzasuPracyApp')
        .factory('Password', Password);

    Password.$inject = ['$resource'];

    function Password($resource) {
        var service = $resource('api/account/change-password', {}, {});

        return service;
    }
})();
