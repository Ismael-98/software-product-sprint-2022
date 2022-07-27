var table = $('#example').DataTable({
    columns: [{
        "title": "Food Name",
        "data": "Food Name"
    }, {
        "title": "Ingredients",
        "data": "Ingredients"
    }]
});
$("#populateTable").click(function() {
    console.log($("#csvImport").val());
    console.log($.csv.toObjects($("#csvImport").val()));
    table.rows.add($.csv.toObjects($("#csvImport").val())).draw();
});
