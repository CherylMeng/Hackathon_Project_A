/**
 * Copyright (c) 2014, 2017, Oracle and/or its affiliates.
 * The Universal Permissive License (UPL), Version 1.0
 */
/*
 * Your order ViewModel code goes here
 */
define(['ojs/ojcore',
    'knockout',
    'jquery',
    'promise',
    'ojs/ojrouter',
    'ojs/ojknockout',
    'ojs/ojtree',
    'ojs/ojmasonrylayout',
    'ojs/ojlistview',
    'ojs/ojmodel',
    'ojs/ojgauge',
    'ojs/ojbutton',
    'ojs/ojcheckboxset',
    'ojs/ojvalidation-number',
    'ojs/ojselectcombobox',
    'ojs/ojpagingcontrol',
    'ojs/ojcollectiontabledatasource',
    'ojs/ojpagingtabledatasource',
    'ojs/ojdialog',
    'ojs/ojtable',
    'ojs/ojinputnumber',
    'data/data',
    'ojs/ojfilmstrip'
],
        function (oj, ko, $) {

            dataListView = [];
            dataCollection = [];
            CART_ITEMS_ID = [];
            CART_ITEMS = [];
            ORDERS = [];
            function OrderViewModel() {
                var self = this;
                self.$tree = $("#catalog_tree");

//                localStorage.setItem('orders', "");

                self.getOrderTreeElementID = function () {
                    return "catalog_tree";
                }

                self.getProductViewlistElementID = function () {
                    return "products_view";
                }

                this.changeHandler = function (event, ui) {
                    if (ui.option === "selection") {

                        // read json data 

                        // render

                    }
                }


                // *******************************
                // Interface: get Product list given a selected
                // item in catalog tree
                // *******************************
                self.loadProductViewlist = function (sCatalogSelection) {
                    var aProductViewList;
                    switch (sCatalogSelection) {
                        case "News":
                            var sProduct1 = {catalogID: "news_1"};
                            var sProduct2 = {catalogID: "news_2"};
                            aProductViewList.append(sProduct1);
                            aProductViewList.append(sProduct2);
                            break;
                        case "Today":
                            var sProduct1 = {catalogID: "today_1"};
                            var sProduct2 = {catalogID: "today_2"};
                            var sProduct3 = {catalogID: "today_3"};
                            aProductViewList.append(sProduct1);
                            aProductViewList.append(sProduct2);
                            aProductViewList.append(sProduct3);
                            break;
                        default:
                            aProductViewList = [];
                    }
                    return aProductViewList;
                };


                self.productInMasonryLayout = ko.observableArray([]);

                // render: set productInMasonryLayout 
                self.renderProductViewlist = function (aProductViewlist) {
                    var $eleProductViewlist = document.getElementById(self.getProductViewlistElementID());
                    var aProductList = [];
                    if (aProductViewlist.length) {
                        var oProduct = {sizeClass: 'oj-masonrylayout-tile-1x1'};
                        for (i = 0; i < aProductViewlist.length; i++)
                        {
                            oProduct.name = aProductViewlist[i].catalogID;
                            aProductList.push(oProduct);
                        }
                    }
                    self.productInMasonryLayout(aProductList);
                    // ko.applyBindings(aProductList, $eleProductViewlist);
                };

                self._arrayToStr = function (arr) {
                    var s = "";
                    $.each(arr, function (i, val)
                    {
                        if (i) {
                            s += ", ";
                        }
                        s += $(arr[i]).attr("id");
                    });
                    return s;
                };

                // *****************************************
                // Interface: Get Catalog Tree, to be edit
                // *****************************************
                self.getCatalogJson = function (node, fn)       // get local json
                {
                    var data = [
                        {"title": "Computers And Laptops",
                            "attr": {"id": "1"},
                            "children": [{"title": "Tablet Computers",
                                    "attr": {"id": "7"}},
                                {"title": "Laptops",
                                    "attr": {"id": "8"}},
                                {"title": "All-in-one PCs",
                                    "attr": {"id": "9"}},
                                {"title": "Desktops",
                                    "attr": {"id": "10"}},
                                {"title": "Sheer PCs",
                                    "attr": {"id": "11"}}]
                        },
                        {"title": "Peripheral Products",
                            "attr": {"id": "2"},
                            "children": [{"title": "Mobile Hard Disk Drives",
                                    "attr": {"id": "12"}},
                                {"title": "Solid State Disks",
                                    "attr": {"id": "13"}},
                                {"title": "USB Disks",
                                    "attr": {"id": "14"}},
                                {"title": "Mouses",
                                    "attr": {"id": "15"}},
                                {"title": "Keyboards",
                                    "attr": {"id": "16"}},
                                {"title": "Mechanical Keyboards",
                                    "attr": {"id": "17"}},
                                {"title": "Laptop Backpacks",
                                    "attr": {"id": "18"}}]
                        },
                        {"title": "PC Components",
                            "attr": {"id": "3"},
                            "children": [{"title": "Monitors",
                                    "attr": {"id": "19"}},
                                {"title": "Motherboards",
                                    "attr": {"id": "20"}},
                                {"title": "Computer Radiators",
                                    "attr": {"id": "21"}},
                                {"title": "Video Cards",
                                    "attr": {"id": "22"}},
                                {"title": "Wires",
                                    "attr": {"id": "23"}},
                                {"title": "Memory Bars",
                                    "attr": {"id": "24"}},
                                {"title": "Adapters",
                                    "attr": {"id": "25"}}]
                        },
                        {"title": "Network Devices",
                            "attr": {"id": "4"},
                            "children": [{"title": "Routers",
                                    "attr": {"id": "26"}},
                                {"title": "Wireless APs",
                                    "attr": {"id": "27"}},
                                {"title": "Switches",
                                    "attr": {"id": "28"}},
                                {"title": "4G Network Services",
                                    "attr": {"id": "29"}},
                                {"title": "Network Cards",
                                    "attr": {"id": "30"}}]
                        },
                        {"title": "Office Facilities",
                            "attr": {"id": "5"},
                            "children": [{"title": "Printers",
                                    "attr": {"id": "31"}},
                                {"title": "Projectors",
                                    "attr": {"id": "32"}},
                                {"title": "Copiers",
                                    "attr": {"id": "33"}},
                                {"title": "Shredders",
                                    "attr": {"id": "34"}},
                                {"title": "Cash Registers",
                                    "attr": {"id": "35"}},
                                {"title": "Safe",
                                    "attr": {"id": "36"}},
                                {"title": "Attendance Machines",
                                    "attr": {"id": "37"}},
                                {"title": "Bookbinding Machines",
                                    "attr": {"id": "38"}}]
                        },
                        {"title": "Stationery Supplies",
                            "attr": {"id": "6"},
                            "children": [{"title": "Toner Cartridges",
                                    "attr": {"id": "39"}},
                                {"title": "Paper",
                                    "attr": {"id": "40"}},
                                {"title": "Pens",
                                    "attr": {"id": "41"}},
                                {"title": "Notecards",
                                    "attr": {"id": "42"}},
                                {"title": "Calculators",
                                    "attr": {"id": "43"}},
                                {"title": "Glues",
                                    "attr": {"id": "44"}},
                                {"title": "Clips",
                                    "attr": {"id": "45"}},
                                {"title": "Bookbinding Supplies",
                                    "attr": {"id": "46"}}]
                        }
                    ];
                    fn(data);  // pass to ojTree using supplied function
                };


                // Product Listview
                var criteriaMap = {};
                criteriaMap['lh'] = {key: 'PRICE', direction: 'ascending'};
                criteriaMap['hl'] = {key: 'PRICE', direction: 'descending'};

                var filters = ['lt30', '30to40', '40to50', 'gt50'];

                // filter: on price
                var filterFunc = {};
                filterFunc['lt30'] = function (model) {
                    return (parseFloat(model.get('PRICE')) < 30);
                };
                filterFunc['30to40'] = function (model) {
                    return (parseFloat(model.get('PRICE')) > 30 && parseFloat(model.get('PRICE')) < 40);
                };
                filterFunc['40to50'] = function (model) {
                    return (parseFloat(model.get('PRICE')) >= 40 && parseFloat(model.get('PRICE')) <= 50);
                };
                filterFunc['gt50'] = function (model) {
                    return (parseFloat(model.get('PRICE')) > 50);
                };

                var converterFactory = oj.Validation.converterFactory("number");
                var currencyOptions =
                        {
                            style: "currency",
                            currency: "USD",
                            currencyDisplay: "symbol"
                        };
                this.currencyConverter = converterFactory.createConverter(currencyOptions);

                var model = oj.Model.extend({
                    idAttribute: 'ID'
                });

                str = "appCode=00000000&serviceName=OrderController&operation=getItemList";
                  $.ajax({
                  	url : "controller",
                  	type : "post",
                  	data : str,
                  	dataType : "json",
                  	success : function(data) {
                  	}
                  });
                
                this.jsonUrl = 'js/data/TabletComputers.json';
                this.collection = new oj.Collection(null, {
                            url: this.jsonUrl,
                            model: model
                        });
                var originalCollection = this.collection;
                dataCollection = originalCollection;

                this.dataSource = ko.observable(new oj.PagingTableDataSource(new oj.CollectionTableDataSource(this.collection)));
                dataListView = this.dataSource();

                this.rawData = $.getJSON(this.jsonUrl, function (json) {
                    var array = [];
                    for (var key in json) {
                        if (json.hasOwnProperty(key)) {
                            var item = json[key];
                            array.push({
                                ID: item.ID,
                                TITLE: item.TITLE,
                                PRICE: item.PRICE,
                                NUMBER: 0
                            });
                        }
                    }
                });

                this.currentPrice = [];
                this.currentSort = ko.observable("default");


                this.handleSortCriteriaChanged = function (event, ui) {
                    if (ui.option != 'value' || (ui.value.length == 1 && ui.value[0] == 'default')) {
                        return;
                    }
                    var criteria = criteriaMap[ui.value];
                    self.dataSource().sort(criteria);
                };

                this.handleFilterChanged = function (event, ui) {
                    if (ui.option != 'value') {
                        return;
                    }
                    var value = ui.value;
                    if (!Array.isArray(value)) {
                        return;
                    }
                    var results = [];
                    var processed = false;

                    $.each(filters, function (index, filter) {
                        if (value.indexOf(filter) > -1) {
                            results = results.concat(originalCollection.filter(filterFunc[filter]));
                            processed = true;
                        }
                    });

                    if (processed) {
                        self.collection = new oj.Collection(results);
                    } else {
                        self.collection = originalCollection;
                    }
                    self.dataSource(new oj.PagingTableDataSource(new oj.CollectionTableDataSource(self.collection)));

                    if (self.currentSort() != "default") {
                        var criteria = criteriaMap[self.currentSort()];
                        self.dataSource().sort(criteria);
                    }
                };

                this.handleSelectCatalogInTree = function (event, ui) {
                    var $orderPaging = $("#paging");
                    var $orderContent = $("#listview");
                    if (ui.option === "selection") {
                        var sCatalog = ui.value[0].innerText.trim();
                        var sUrl = 'js/data/' + sCatalog + '.json';
                        var oModel = oj.Model.extend({idAttribute: 'ID'});
                        var oCollection = new oj.Collection(oModel, {
                            url: sUrl});
                        dataListView = new oj.PagingTableDataSource(new oj.CollectionTableDataSource(oCollection));

                    }
                }

                this.selectedProductInfos = ko.observable("(None selected yet)");

                // The array to store products in cart currently
                this.aSelectedProductsID = ko.observableArray([]);
                this.aSelectedProducts = ko.observableArray([]);
                this.onAddToCart = function () {
                    // get parent id, and push to aSelectedProducts
                    if (event) {
                        var aParents = $(event.target).parents();
                        var sAddedProductID = "";
                        for (i = 0; i < aParents.length; i++) {
                            if (aParents[i].className && aParents[i].className.includes("order_single_product_container")) {
                                sAddedProductID = aParents[i].id;
                                break;
                            }
                        }
                        if (sAddedProductID.length) {
                            // check duplicates
                            var bNotDuplicates = (CART_ITEMS_ID.indexOf(sAddedProductID) === -1);
                            if (bNotDuplicates) {
                                CART_ITEMS_ID.push(sAddedProductID);
                                this.selectedProductInfos(this.aSelectedProductsID().join());
                                var oSelectedProduct = $.grep(this.rawData.responseJSON, function (e) {
                                    return e.ID == sAddedProductID;
                                });
                                oSelectedProduct[0].NUMBER = 1;
                                CART_ITEMS.push(oSelectedProduct[0]);


                                // update the cart datasource (also global variables)
                                this.cartDataSource = new oj.ArrayTableDataSource(CART_ITEMS, {idAttribute: 'ID'});
//                                CART_ITEMS_ID = this.aSelectedProductsID();
//                                CART_ITEMS = this.aSelectedProducts();
                            } else {
                                // find the product in CART_ITEMS and plus 1
                                var idx = CART_ITEMS_ID.indexOf(sAddedProductID);
                                CART_ITEMS[idx].NUMBER = CART_ITEMS[idx].NUMBER + 1;
                            }

                        }
                    }
                };


                this.onPopCheckMyCart = function () {
                    // Pop Cart Dialog
                    var opt = {
                        autoOpen: false,
                        modal: false,
                        title: 'My Cart'
                    };
                    $("#order_my_cart_modelessDialog").ojDialog(opt);
                    if (event && event.type === "click") {
                        $("#order_my_cart_modelessDialog").ojDialog("open");

                        // render listview of aSelectedProducts
                        var oSelectedProduct;
                        if (CART_ITEMS.length) {
                            $("#order_my_cart_modelessDialog_listview").empty();
                            for (i = 0; i < CART_ITEMS.length; i++) {
                                oSelectedProduct = CART_ITEMS[i];
                                var $container = $("<li>").attr("id", "my_cart_dialog_product_" + oSelectedProduct.ID)
                                        .attr("role", "row")
                                        .addClass("my_cart_dialog_product_container oj-listview-focused-element oj-listview-item-element oj-listview-item oj-selected");
                                $("#order_my_cart_modelessDialog_listview").append($container);

                                var $scontainer = $("<div>").addClass("my_cart_dialog_single_product_container oj-listview-cell-element")
                                        .addClass("role", "gridcell");
                                $container.append($scontainer);

                                // id
                                var $tmp = $("<div>");
                                var $tmpSpan = $("<span>").addClass("my_cart_dialog_single_product_id display_line")
                                        .text("ID:  " + oSelectedProduct.ID);
                                $tmp.append($tmpSpan);
                                $scontainer.append($tmp);

                                // title
                                $tmp = $("<div>");
                                $tmpSpan = $("<span>").addClass("my_cart_dialog_single_product_title display_line")
                                        .text("NAME:  " + oSelectedProduct.TITLE);
                                $tmp.append($tmpSpan);
                                $scontainer.append($tmp);

                                // Delete button
                                $tmp = $("<div>");
                                $tmpSpan = document.createElement("input");
                                $tmpSpan.type = "button";
                                $tmpSpan.value = "delete";
                                $tmpSpan.onclick = self.onDeleteAnItemInCart;
                                $tmp.append($tmpSpan);
                                $scontainer.append($tmp);

                                // input number box
                                $tmp = $("<div>");
                                $tmpSpan = document.createElement("input");
                                $tmpSpan.id = "cart_inputnumber_" + oSelectedProduct.ID;
                                $tmpSpan.value = oSelectedProduct.NUMBER;
                                $tmp.append($tmpSpan);
                                $scontainer.append($tmp);

                                // add number 
                                $tmp = $("<div>");
                                $tmpSpan = document.createElement("input");
                                $tmpSpan.type = "button";
                                $tmpSpan.value = "+";
                                $tmpSpan.onclick = self.onAddNumber;
                                $tmp.append($tmpSpan);
                                $scontainer.append($tmp);
                                
                                // substract number
                                $tmp = $("<div>");
                                $tmpSpan = document.createElement("input");
                                $tmpSpan.type = "button";
                                $tmpSpan.value = "-";
                                $tmpSpan.onclick = self.onMinusNumber;
                                $tmp.append($tmpSpan);
                                $scontainer.append($tmp);

                            }
                        } else {
                            $("#order_my_cart_modelessDialog_message").innerHTML = "Cart is empty now.";
                        }
                    }
                }

                this.aSubmittedOrder = ko.observableArray([]);
                this.nOrderCounter = 0;
                this.onConfirmOrder = function () {
                    // render a record in History page;
                    if (CART_ITEMS_ID.length) {
                        // order name
                        var dt = new Date();
                        var sOrderName = "Submitted Order " + '-'
                                + dt.getDay().toString() + '-'
                                + dt.getHours().toString() + '-'
                                + dt.getMinutes().toString() + '-'
                                + dt.getSeconds().toString();
                        
                        // order content
                        var sContent = ""; 
                        for(i = 0; i<CART_ITEMS.length; i++){
                            sContent = sContent + "ID = " + CART_ITEMS[i].ID.toString() + ", ";
                            sContent = sContent + "Pieces = " + CART_ITEMS[i].NUMBER.toString() + "; "; 
                        }
                        var oData = {id: this.nOrderCounter, name: sOrderName, content: sContent, details: CART_ITEMS};

                        // save order
//                        var sOrder = localStorage.getItem("orders");
//                        if (sOrder === "") {
//                            sOrder = JSON.stringify(oData);
//                        } else {
//                            sOrder = sOrder + "," + JSON.stringify(oData);
//                        }
//                        localStorage.setItem("orders", sOrder);
                        ORDERS.push(oData);
                        
                        
                        // clear all the products
                        this.aSelectedProducts([]);
                        this.nOrderCounter = this.nOrderCounter + 1;
                        CART_ITEMS = [];
                        CART_ITEMS_ID = [];
                    }
                }
                this.onDeleteAnItemInCart = function () {
                    var aParents = $(event.target).parents();
                    var sToDeleteID = "";
                    for (i = 0; i < aParents.length; i++) {
                        if (aParents[i].id && aParents[i].id.includes("my_cart_dialog_product_")) {
                            // remove div
                            sToDeleteID = aParents[i].id.split("_").pop();
                            aParents[i].remove();
                            // remove product in aSelectedProducts
                            for (i = 0; i < CART_ITEMS.length; i++) {
                                if (CART_ITEMS[i].ID.toString() === sToDeleteID) {
                                    CART_ITEMS.splice(i, 1);
                                    CART_ITEMS_ID.splice(i, 1);
                                    break;
                                }
                            }

                            break;
                        }
                    }
                };

                this.onDeleteAllItemsInCart = function () {
                    var aContainers = document.getElementsByClassName("my_cart_dialog_product_container");
                    // delete div
                    $("#order_my_cart_modelessDialog_listview").empty();
                    
                    // delete div
                    CART_ITEMS = [];
                    CART_ITEMS_ID = [];
                };

                this.onAddNumber = function () {
                    // add in div
                    var aSiblings = $(event.target).parent().siblings();
                    var sAddedProductID = "";
                    for (i = 0; i < aSiblings.length; i++) {
                        if (aSiblings[i].children[0].id && aSiblings[i].children[0].id.includes("cart_inputnumber_")) {
                            sAddedProductID =  aSiblings[i].children[0].id.split("_").pop();
                            break;
                        }
                    }
                    var $input = document.getElementById("cart_inputnumber_" +sAddedProductID );
                    $input.value = (parseInt($input.value) +1).toString(); 
                    // add in CART_ITEMS
                    for (i = 0; i < CART_ITEMS.length; i++) {
                        if (CART_ITEMS[i].ID.toString() === sAddedProductID) {
                            CART_ITEMS[i].NUMBER = CART_ITEMS[i].NUMBER +1;
                            break;
                        }
                    }
                    
                };
                this.onMinusNumber = function () {
                    //  in div
                    var aSiblings = $(event.target).parent().siblings();
                    var sAddedProductID = "";
                    for (i = 0; i < aSiblings.length; i++) {
                        if (aSiblings[i].children[0].id && aSiblings[i].children[0].id.includes("cart_inputnumber_")) {
                            sAddedProductID =  aSiblings[i].children[0].id.split("_").pop();
                            break;
                        }
                    }
                    var $input = document.getElementById("cart_inputnumber_" +sAddedProductID );
                    $input.value = (parseInt($input.value) -1).toString(); 
                    // add in CART_ITEMS
                    for (i = 0; i < CART_ITEMS.length; i++) {
                        if (CART_ITEMS[i].ID.toString() === sAddedProductID) {
                            CART_ITEMS[i].NUMBER = CART_ITEMS[i].NUMBER +1;
                            break;
                        }
                    }
                    
                };


                // just for debug
                this.getCartJson = function (node, fn)       // get local json
                {
                    var data = [
                        {"ProductId": "101",
                            "ProductName": "name 1"
                        },
                        {"ProductId": "102",
                            "ProductName": "name 2"
                        }
                    ];
                    fn(data);
                };

                this.getDataSourceJson = function (node, fn)       // get local json
                {
                    var data = [
                        {"ProductId": "101",
                            "ProductName": "name 1"
                        },
                        {"ProductId": "102",
                            "ProductName": "name 2"
                        }
                    ];
                    fn(data);
                };

                this.cartCollection = ko.observableArray([]);

                // cartDataSource:  to render in cart table
                this.cartDataSource = ko.observableArray([]);
                
                self.popPictureDialog = function(){
                    $("#order_my_cart_pictureDialog").ojDialog('open');
                };
                self.closePictureDialog = function(){
                    $("#order_my_cart_pictureDialog").ojDialog('close');
                };
                
                self.chemicals = [
                    { name: 'Hydrogen' },
                    { name: 'Helium' },
                    { name: 'Lithium' },
                    { name: 'Beryllium' },
                    { name: 'Boron' },
                    { name: 'Carbon' },
                    { name: 'Nitrogen' },
                    { name: 'Oxygen' },
                    { name: 'Fluorine' },
                    { name: 'Neon' },
                    { name: 'Sodium' },
                    { name: 'Magnesium' }
                ];

                self.currDetailPage = ko.observable(0);

                handleDetailCreate = function()
                {
                  var filmStrip = $("#detailFilmStrip");
                  var pagingModel = filmStrip.ojFilmStrip("getPagingModel");
                  pagingModel.on("beforePage", handleDetailBeforePage);
                  handleDetailBeforePage();
                };

                handleDetailBeforePage = function(event)
                {
                  var filmStrip = $("#detailFilmStrip");
                  var pagingModel = filmStrip.ojFilmStrip("getPagingModel");
                  var pageCount = pagingModel.getPageCount();
                  var pageIndex = event ? event.page : 0;

                  //wrap in try..catch block because the detail filmstrip is 
                  //initialized first and the master filmstrip is not yet 
                  //available
                  try
                  {
                    var masterFilmStrip = $("#masterFilmStrip");
                    var masterPagingModel = 
                      masterFilmStrip.ojFilmStrip("getPagingModel");
                    var masterPage = masterPagingModel.getPage();
                    var masterItemsPerPage = 
                      masterFilmStrip.ojFilmStrip("getItemsPerPage");
                    var newMasterPage = 
                      Math.floor(pageIndex / masterItemsPerPage);
                    //change master page in detail's beforePage event so that 
                    //the master page change transitions at the same time as 
                    //the detail page
                    if (newMasterPage !== masterPage)
                      masterPagingModel.setPage(newMasterPage);
                  }
                  catch (e)
                  {
                    //do nothing
                  }
                };

                masterClick = function(data, event)
                {
                  //get the ko binding context for the item DOM element
                  var context = ko.contextFor(event.target);
                  //get the index of the item from the binding context
                  var index = context.$index();
                  //set currDetailPage in a timeout so that the page change 
                  //transitions smoothly, otherwise it seems that there's some 
                  //processing going on that interferes (maybe due to knockout)
                  setTimeout(function() {
                    self.currDetailPage(index);
                    }, 50);
                };

                getDetailItemInitialDisplay = function(index)
                {
                  return index < 1 ? '' : 'none';
                };

                getMasterItemInitialDisplay = function(index)
                {
                  return index < 3 ? '' : 'none';
                };

                getMasterItemLabelId = function(index)
                {
                  return 'masterLabel' + index;
                };

                isMasterItemSelected = function(index)
                {
                  return self.currDetailPage() == index;
                };

                getMasterItemLabelledBy = function(index)
                {
                  var labelledBy = getMasterItemLabelId(index);
                  if (isMasterItemSelected(index))
                    labelledBy += " masterItemSelectedLabel";
                  return labelledBy;
                };
            }





            /*
             * Returns a constructor for the ViewModel so that the ViewModel is constrcuted
             * each time the view is displayed.  Return an instance of the ViewModel if
             * only one instance of the ViewModel is needed.
             */
            return new OrderViewModel();
        }
);
