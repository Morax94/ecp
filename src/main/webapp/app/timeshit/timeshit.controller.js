(function () {
    'use strict';

    angular
        .module('ewidencjaCzasuPracyApp')
        .controller('TimeshitController', TimeshitController);

    TimeshitController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'Tasks', 'AlertService'];

    function TimeshitController($scope, Principal, LoginService, $state, Tasks, AlertService) {
        var vm = this;

        Principal.identity().then(function (account) {
            vm.userId = account.id;
        });

        vm.hours = initializeHours();
        vm.task = {
            startTime: "0",
            endTime: "0",
            name: "",
            date: new Date()
        }
        vm.isSaving = false;
        vm.summary = function getSummary(task) {
            var duration = task.endTime - task.startTime;
            return task.date.toLocaleDateString() + "\nName:\t" + task.name + "\nDuration:\t" + duration + "h";
        }

        vm.saveTask = saveTask;

        function saveTask() {
            var task = {};
            task.nameOfTask = vm.task.name;
            task.userId = vm.userId;
            task.startTime = new Date();
            task.startTime.setHours(vm.task.startTime);
            task.endTime = new Date();
            task.endTime.setHours(vm.task.endTime);
            vm.isSaving = true;
            Tasks.save(task, onSaveSuccess, onSaveError);
        }

        function onSaveSuccess(result) {
            vm.task = {
                startTime: "0",
                endTime: "0",
                name: "",
                date: new Date()
            }
            vm.isSaving = false;
        }

        function onSaveError(result) {
            vm.isSaving = false;
        }

        function initializeHours() {
            var hoursArray = [];
            for (var i = 0; i < 24; i++) {
                if (i < 10) {
                    hoursArray[i] = "0" + i;
                } else {
                    hoursArray[i] = "" + i;
                }
            }
            return hoursArray;
        }

        function getSummary() {
            var taskDuration = vm.task.endTime.idx - vm.task.startTime.idx;
            return vm.task.endTime;
        }

    }
})();
