/**
 * Copyright (c) 2014, 2017, Oracle and/or its affiliates.
 * The Universal Permissive License (UPL), Version 1.0
 */
/*
 * Your dashboard ViewModel code goes here
 */
define(['ojs/ojcore', 'knockout', 'jquery', 'ojs/ojarraytabledatasource', 'ojs/ojtable'],
 function(oj, ko, $) {
  
    function AccountViewModel() {
        var self = this;
      // Below are a subset of the ViewModel methods invoked by the ojModule binding
      // Please reference the ojModule jsDoc for additionaly available methods.

        self.roleName = ko.observable("Requestor");
        self.username = ko.observable("xxxx");
        self.notificationNum = ko.observable("1");
        self.toDoListNum = ko.observable("10");
        self.pageNavigationHandler = function(data, event){
            ko.dataFor($("#navDrawer>div")[0]).router.go("customers");
        };

        var deptArray = [{account: 1001, firstName: 'ADFPM 1001 neverending', lastName: 200, status: 300, actions: ['Approve', 'Refuse']},
          {account: 556, firstName: 'BB', lastName: 200, status: 300, actions: ['Approve', 'Refuse']},
          {account: 10, firstName: 'Administration', lastName: 200, status: 300, actions: ['Approve', 'Refuse']},
          {account: 20, firstName: 'Marketing', lastName: 200, status: 300, actions: ['Approve', 'Refuse']},
          {account: 30, firstName: 'Purchasing', lastName: 200, status: 300, actions: ['Approve', 'Refuse']},
          {account: 40, firstName: 'Human Resources1', lastName: 200, status: 300, actions: ['Approve', 'Refuse']},
          {account: 50, firstName: 'Administration2', lastName: 200, status: 300, actions: ['Approve', 'Refuse']},
          {account: 60, firstName: 'Marketing3', lastName: 200, status: 300, actions: ['Approve', 'Refuse']},
          {account: 70, firstName: 'Purchasing4', lastName: 200, status: 300, actions: ['Approve', 'Refuse']},
          {account: 80, firstName: 'Human Resources5', lastName: 200, status: 300, actions: ['Approve', 'Refuse']},
          {account: 90, firstName: 'Human Resources11', lastName: 200, status: 300, actions: ['Approve', 'Refuse']},
          {account: 100, firstName: 'Administration12', lastName: 200, status: 300, actions: ['Approve', 'Refuse']},
          {account: 110, firstName: 'Marketing13', lastName: 200, status: 300, actions: ['Approve', 'Refuse']},
          {account: 120, firstName: 'Purchasing14', lastName: 200, status: 300, actions: ['Approve', 'Refuse']},
          {account: 130, firstName: 'Human Resources15', lastName: 200, status: 300, actions: ['Approve', 'Refuse']}];
        self.datasource = new oj.ArrayTableDataSource(deptArray, {idAttribute: 'account'});
        
        self.disableHandler = function(data, event){
            debugger;
        };
        self.enableHandler = function(data, event){
            debugger;
        };
        self.approveHandler = function(data, event){
            debugger;
        };
        self.refuseHandler = function(data, event){
            debugger;
        };
      /**
       * Optional ViewModel method invoked when this ViewModel is about to be
       * used for the View transition.  The application can put data fetch logic
       * here that can return a Promise which will delay the handleAttached function
       * call below until the Promise is resolved.
       * @param {Object} info - An object with the following key-value pairs:
       * @param {Node} info.element - DOM element or where the binding is attached. This may be a 'virtual' element (comment node).
       * @param {Function} info.valueAccessor - The binding's value accessor.
       * @return {Promise|undefined} - If the callback returns a Promise, the next phase (attaching DOM) will be delayed until
       * the promise is resolved
       */
      self.handleActivated = function(info) {
        // Implement if needed
      };

      /**
       * Optional ViewModel method invoked after the View is inserted into the
       * document DOM.  The application can put logic that requires the DOM being
       * attached here.
       * @param {Object} info - An object with the following key-value pairs:
       * @param {Node} info.element - DOM element or where the binding is attached. This may be a 'virtual' element (comment node).
       * @param {Function} info.valueAccessor - The binding's value accessor.
       * @param {boolean} info.fromCache - A boolean indicating whether the module was retrieved from cache.
       */
      self.handleAttached = function(info) {
        // Implement if needed
      };


      /**
       * Optional ViewModel method invoked after the bindings are applied on this View. 
       * If the current View is retrieved from cache, the bindings will not be re-applied
       * and this callback will not be invoked.
       * @param {Object} info - An object with the following key-value pairs:
       * @param {Node} info.element - DOM element or where the binding is attached. This may be a 'virtual' element (comment node).
       * @param {Function} info.valueAccessor - The binding's value accessor.
       */
      self.handleBindingsApplied = function(info) {
        // Implement if needed
      };

      /*
       * Optional ViewModel method invoked after the View is removed from the
       * document DOM.
       * @param {Object} info - An object with the following key-value pairs:
       * @param {Node} info.element - DOM element or where the binding is attached. This may be a 'virtual' element (comment node).
       * @param {Function} info.valueAccessor - The binding's value accessor.
       * @param {Array} info.cachedNodes - An Array containing cached nodes for the View if the cache is enabled.
       */
      self.handleDetached = function(info) {
        // Implement if needed
      };
    }
        
    /*
     * Returns a constructor for the ViewModel so that the ViewModel is constrcuted
     * each time the view is displayed.  Return an instance of the ViewModel if
     * only one instance of the ViewModel is needed.
     */
    return new AccountViewModel();
  }
);
