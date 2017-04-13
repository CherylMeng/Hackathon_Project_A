/*
 * Your customer ViewModel code goes here
 */
define(['ojs/ojcore',
    'knockout',
    'jquery'],
        function (oj, ko, $) {
            function HistoryViewModel() {

                var self = this;

                this.aOrders = ko.observableArray(JSON.parse("[" + localStorage.getItem("orders") + "]"));
                this.dataSource = new oj.ArrayTableDataSource(this.aOrders, {idAttribute: "id"});
                this.content = ko.observable("");

                this.gotoList = function (event, ui) {
                    $("#history_listview").ojListView("option", "currentItem", null);
                    self.slide();
                };

                this.gotoContent = function (event, ui) {
                    self.aOrders = ko.observableArray(JSON.parse("[" + localStorage.getItem("orders") + "]"));
                    // refind id
                    var id = 0;
                    if (ui.item) {
                        var $ele = ui.item[0];
                        var aChildren = ui.item.parent().children();
                        for (i = 0; i < aChildren.length; i++) {
                            if ($ele === aChildren[i]) {
                                id = i;
                                break;
                            }
                        }

                        if (ui.option === 'currentItem' && ui.value != null)
                        {
                            var row = self.aOrders()[id];
                            self.content(row.content);
                            self.slide();
                        }
                    }
                };

                this.slide = function () {
                    $("#page1").toggleClass("demo-page1-hide");
                    $("#page2").toggleClass("demo-page2-hide");
                }

                this.onRefreshListView = function () {
                    this.aOrders(JSON.parse("[" + localStorage.getItem("orders") + "]"));
                    this.dataSource = new oj.ArrayTableDataSource(this.aOrders, {idAttribute: "id"});
                }

                this.onClearListView = function () {
                    // for debug only
//                    localStorage.setItem("orders", "");
//                    localStorage.setItem("counter", 1);
                }

            }

            return new HistoryViewModel();
        }
);
