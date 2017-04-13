/**
 * Copyright (c) 2014, 2017, Oracle and/or its affiliates.
 * The Universal Permissive License (UPL), Version 1.0
 */
/*
 * Your dashboard ViewModel code goes here
 */
define(['ojs/ojcore', 'knockout', 'jquery', 'ojs/ojinputtext', 'ojs/ojselectcombobox', 'ojs/ojarraytabledatasource'],
 function(oj, ko, $) {
  
    function AddOfficeDepotViewModel() {
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
      
//      self.catalogRawData = ko.observable([{'root': {'id': 1, 'name': "Computer & Laptop", 'root': 0}, 'subCatalog': [{'id': 7, 'name': "XXX", 'root': 1}]}, 
//              {'root': {'id': 2, 'name': "Peripheral Products", 'root': 0}, 'subCatalog': [{'id': 14, 'name': "YYY", 'root': 2}]}]);
//      self.catalogs = ko.observableArray([]);
//      self.catalogRawData.subscribe(function(catalogRawDataList){
//          for(var i = 0; i < catalogRawDataList.length; ++i){
//              if(catalogRawDataList['root']){
//                self.catalogs.push({id: catalogRawDataList.root.id, name: catalogRawDataList.root.name});
//              }
//          }
//      });
//      self.selectedCatalog = ko.observable();
//      self.selectedCatalog.subscribe(function(selectedCatalogItem){
//          var currentCatalogItem = self.catalogRawData().find(function(catalogItemRawData){
//              return catalogItemRawData.root && catalogItemRawData.root.id === selectedCatalogItem.id;
//          });
//          if(!currentCatalogItem){
//              return;
//          }
//          self.subCatalogs.removeAll();
//          var currentSubCatalogRawDataList = currentCatalogItem.subCatalog;
//          for(var i = 0; i < currentSubCatalogRawDataList.length; ++i){
//                self.subCatalogs.push({id: currentSubCatalogRawDataList.id, name: currentSubCatalogRawDataList.name});
//          }
//      });
//      self.subCatalogs = ko.observableArray([]);
//      self.SKUTypes = ko.observableArray(["Usage", "Color"]);
//      self.currencys = ko.observableArray(["RMB", "USD"]);
//      
//      
//      self.selectedSubCatalog = ko.observable();
//      self.SKUSelectedList = [];
//      self.selectedCurrency = ko.observable();
//      
//      self.catalogID = ko.observable();
//      self.supplierID = ko.observable();
//      self.currencyID = ko.observable();
//      self.SKU = ko.observable();
//      self.brand = ko.observable();
      self.name = ko.observable();
      self.price = ko.observable();
      self.pictureList = ko.observableArray([]);;
      self.pictureListDataSource = new oj.ArrayTableDataSource(self.pictureList, {idAttribute: 'id'});
      
      self.fileSelected = function(data, event) {   //not in used
        // get selected file element
        var oFile = event.target.files[0];

        // filter for image files
        var rFilter = /^(image\/bmp|image\/gif|image\/jpeg|image\/png|image\/tiff)$/i;
        if (! rFilter.test(oFile.type)) {
            document.getElementById('error').style.display = 'block';
            return;
        }
        //file type checking and error message display todo
    };
    
    self.uploadHandler = function(){
        // get form data for POSTing
        //var vFD = document.getElementById('upload_form').getFormData(); // for FF3
        var oReader = new FileReader();
            oReader.onload = function(e){
                if(!self.pictureId){
                    self.pictureId = 1;
                }else{
                    self.pictureId++;
                }
                self.pictureList.push({data:e.target.result, id: 'picture-list-item' + self.pictureId});
            };
        var files = document.getElementById('picture').files; 
        if (files.length > 0) {  
            var fd = new FormData();  
            for (var i = 0; i < files.length; i++) {  
                fd.append("fileToUpload", files[i]);
                var oFile = files[i];
                oReader.readAsDataURL(oFile);
            }  
        }
    };
    
    self.shouldDisableSave = ko.computed(function(){
        return !self.name() || !self.price();
    });
    
    self.deletePictureHandler = function(data, event){
        self.pictureList.remove(function(item){
            return item.id === data.id;
        });
    };
    
    self.saveHandler = function(data, event){
        var pictureDataList = [];
        for (var i = 0; i < self.pictureList().length; i++) {  
                pictureDataList.push(encodeURI(self.pictureList()[i].data));
            }
        var dataToPost = {'name': self.name(),
                        'price': self.price(),
                        'picture': pictureDataList
        };
        str = "appCode=00000000&serviceName=ItemController&operation=addItem";
        console.log(dataToPost);
          $.ajax({
          	url : "controller",
          	type : "post",                
          	data : JSON.stringify(dataToPost),
          	dataType : "json",
          	success : function(data) {
          	  alert();
              ko.dataFor($("#navDrawer>div")[0]).router.go("homepage");
          	}
          });
    };
    
    self.cancelHandler = function(data, event){
        ko.dataFor($("#navDrawer>div")[0]).router.go("homepage");
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
    return new AddOfficeDepotViewModel();
  }
);
