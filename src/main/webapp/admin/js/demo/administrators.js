$(document).ready(function () {
    var table = $('table#ms-admins').DataTable({
        dom: 'Bfrtip',
        buttons: [
            {'extend': 'copy', 'className': 'btn btn-info'},
            {'extend': 'csv', 'className': 'btn btn-info'},
            {'extend': 'excel', 'className': 'btn btn-info'},
            {'extend': 'print', 'className': 'btn btn-info'}
        ],
        'ajax': '/admin/dt/admins',
        'serverSide': true,
        columns: [
            {"data": 'id'},
            {"data": 'firstName'},
            {"data": 'lastName'},
            {"data": 'phoneNumber'},
            {"data": 'login'},
            {"data": 'address'},
            {"data": 'profit'},
            {"data": 'role'},
            {"data": 'created'}
        ]
    });
});
