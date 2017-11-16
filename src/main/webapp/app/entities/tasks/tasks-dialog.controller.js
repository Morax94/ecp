(function() {
    'use strict';

    angular
        .module('ewidencjaCzasuPracyApp')
        .controller('TasksDialogController', TasksDialogController);

    TasksDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tasks', 'User'];

    function TasksDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Tasks, User) {
        var vm = this;

        vm.tasks = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tasks.id !== null) {
                Tasks.update(vm.tasks, onSaveSuccess, onSaveError);
            } else {
                Tasks.save(vm.tasks, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ewidencjaCzasuPracyApp:tasksUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.startTime = false;
        vm.datePickerOpenStatus.endTime = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
