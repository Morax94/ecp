(function() {
    'use strict';

    angular
        .module('ewidencjaCzasuPracyApp')
        .controller('TasksDetailController', TasksDetailController);

    TasksDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Tasks', 'User'];

    function TasksDetailController($scope, $rootScope, $stateParams, previousState, entity, Tasks, User) {
        var vm = this;

        vm.tasks = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ewidencjaCzasuPracyApp:tasksUpdate', function(event, result) {
            vm.tasks = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
