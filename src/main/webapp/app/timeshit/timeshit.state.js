(function() {
    'use strict';

    angular
        .module('ewidencjaCzasuPracyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('timeshit', {
            parent: 'app',
            url: '/timeshit',
            data: {
                authorities: ['ROLE_USER']
            },
            views: {
                'content@': {
                    templateUrl: 'app/timeshit/timeshit.html',
                    controller: 'TimeshitController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
