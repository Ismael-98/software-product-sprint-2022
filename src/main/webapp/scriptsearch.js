google.charts.load('current', {'packages':['table']});
google.charts.setOnLoadCallback(drawTable);

/** Fetches foods and ingredients data and uses it to create a dataTable. */
function drawTable() {
  fetch('/foods-data').then(response => response.json())
  .then((foodsList) => {
    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Food Name');
    data.addColumn('string', 'Ingredients');
    
    Object.keys(foodsList).forEach((name) => {
      data.addRow([name, foodsList[name]]);
    });

    var table = new google.visualization.Table(document.getElementById('table_div'));
    table.draw(data, {showRowNumber: true, width: '100%', height: '100%'});    
  });
}

