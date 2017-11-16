(function() {
    'use strict';

    angular
        .module('ewidencjaCzasuPracyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tasks', {
            parent: 'entity',
            url: '/tasks',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Tasks'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tasks/tasks.html',
                    controller: 'TasksController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('tasks-detail', {
            parent: 'tasks',
            url: '/tasks/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Tasks'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tasks/tasks-detail.html',
                    controller: 'TasksDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Tasks', function($stateParams, Tasks) {
                    return Tasks.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tasks',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tasks-detail.edit', {
            parent: 'tasks-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tasks/tasks-dialog.html',
                    controller: 'TasksDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tasks', function(Tasks) {
                            return Tasks.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tasks.new', {
            parent: 'tasks',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tasks/tasks-dialog.html',
                    controller: 'TasksDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nameOfTask: null,
                                startTime: null,
                                endTime: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tasks', null, { reload: 'tasks' });
                }, function() {
                    $state.go('tasks');
                });
            }]
        })
        .state('tasks.edit', {
            parent: 'tasks',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tasks/tasks-dialog.html',
                    controller: 'TasksDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tasks', function(Tasks) {
                            return Tasks.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tasks', null, { reload: 'tasks' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tasks.delete', {
            parent: 'tasks',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tasks/tasks-delete-dialog.html',
                    controller: 'TasksDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tasks', function(Tasks) {
                            return Tasks.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tasks', null, { reload: 'tasks' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
