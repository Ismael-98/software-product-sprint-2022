google.charts.load('current', {'packages':['table']});
      google.charts.setOnLoadCallback(drawTable);

      function drawTable() {
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Food');
        data.addColumn('number', 'Calories');
        data.addColumn('string', 'Ingredients');
        data.addRows([
          ['Green Pasta',  {v: 800, f: '800'}, 'Pesto, Pasta'],
          ['Sour Cream & Onion Chips',   {v:450,   f: '450'},  'Sour Cream, Onions'],
          ['Ramen', {v: 400, f: '400'}, 'Eggs, Wheat, Soy Sauce'],
          ['Fried Chicken',   {v: 850,  f: '850'},  'Chicken, Flour, Paprika']
        ]);

        var table = new google.visualization.Table(document.getElementById('table_div'));

        table.draw(data, {showRowNumber: true, width: '100%', height: '100%'});
      }