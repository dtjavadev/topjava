const mealAjaxUrl = "profile/meals/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mealAjaxUrl,
    updateTable: function () {
        $.ajax({
            type: "GET",
            url: "profile/meals/filter",
            data: $("#filter").serialize()
        }).done(updateTableByData);
    }
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get("profile/meals/", updateTableByData);
}

$.ajaxSetup({
    converters: {
        "text json": function (stringData) {
            var json = JSON.parse(stringData);
            if (typeof json === "object") {
                $(json).each(function () {
                    if (this.hasOwnProperty("dateTime")) {
                        this.dateTime = this.dateTime.substr(0, 16).replace("T", " ");
                    }
                });
            }
            return json;
        }
    }
});

$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "ajax": {
                "url": mealAjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderEditBtn
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                $(row).attr("data-mealExcess", data.excess);
            }
        })
    );

    $("#startTime, #endTime").datetimepicker({
        datepicker: false,
        format: "H:i",
        lang: "ru"
    });

    $("#startDate, #endDate").datetimepicker({
        timepicker: false,
        format: "Y-m-d",
        lang: "ru"
    });

    $("#dateTime").datetimepicker({
        format: "Y-m-d H:i",
        lang: "ru"
    });
});
