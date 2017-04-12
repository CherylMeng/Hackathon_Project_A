/*
 * Your customer ViewModel code goes here
 */
define(['ojs/ojcore',
    'knockout',
    'jquery'],
        function (oj, ko, $) {
            function HistoryViewModel() {

                this.aOrders = ko.observableArray(JSON.parse("[" + localStorage.getItem("orders") + "]"));
                this.dataSource = new oj.ArrayTableDataSource(this.aOrders, {idAttribute: "id"});
                this.content = ko.observable("");

                var self = this;
                this.gotoList = function (event, ui) {
                    $("#history_listview").ojListView("option", "currentItem", null);
                    self.slide();
                };

                this.gotoContent = function (event, ui) {
                    self.aOrders = ko.observableArray(JSON.parse("[" + localStorage.getItem("orders") + "]"));
                    if (ui.option === 'currentItem' && ui.value != null)
                    {
                        var row = self.aOrders()[ui.value];
                        self.content(row.content);
                        self.slide();
                    }
                };

                this.slide = function () {
                    $("#page1").toggleClass("demo-page1-hide");
                    $("#page2").toggleClass("demo-page2-hide");
                }

                this.onRefreshListView = function () {
//                    this.aOrders(JSON.parse("[" + localStorage.getItem("orders") + "]"));
                    $("#history_listview").ojListView("refresh");
                }

                this.onClearListView = function () {
                    this.aOrders(JSON.parse("[" + localStorage.getItem("orders") + "]"));
                }

            }

            return new HistoryViewModel();
        }
);
