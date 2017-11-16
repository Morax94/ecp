(function () {
    'use strict';

    angular
        .module('ewidencjaCzasuPracyApp')
        .controller('TimeshitController', TimeshitController);

    TimeshitController.$inject = ['$scope', 'Principal', 'LoginService', '$state'];

    function TimeshitController($scope, Principal, LoginService, $state) {
        var vm = this;

        vm.hours = initializeHours();
        vm.task = {
            startTime: "0",
            endTime: "0",
            name: "",
            date: new Date()
        }
        vm.summary = function getSummary(task) {
            var duration = task.endTime - task.startTime;
            return task.date.toLocaleDateString() + "\nName:\t" + task.name + "\nDuration:\t" + duration + "h";
        }

        function initializeHours() {
            let hoursArray = [];
            for (let i = 0; i < 24; i++) {
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
