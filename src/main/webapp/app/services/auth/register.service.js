(function () {
    'use strict';

    angular
        .module('ewidencjaCzasuPracyApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
