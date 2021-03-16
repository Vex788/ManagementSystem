$(document).ready(function () {
    var clientsTable = $('#ms-clients').DataTable({
        select: { style: 'single' },
        dom: 'Bfrtip',
        buttons: [
            {'extend': 'copy', 'text': 'Копировать', 'className': 'btn btn-info', 'charset': 'utf-8'},
            {'extend': 'csv', 'className': 'btn btn-info', 'charset': 'utf-8', 'bom': true},
            {'extend': 'excel', 'className': 'btn btn-info', 'charset': 'utf-8'},
            {'extend': 'print', 'className': 'btn btn-info', 'charset': 'utf-8'},
            {
                'text': 'Добавить', 'className': 'btn btn-success', 'charset': 'utf-8',
                'attr': {
                    'id': 'add-btn',
                    'data-toggle': 'modal',
                    'data-target': '#ms-client-modal',
                    'data-whatever': 'Добавить клиента'
                }
            },
            {
                'text': 'Изменить', 'className': 'btn btn-warning', 'charset': 'utf-8',
                'attr': {
                    'id': 'edit-btn',
                    'data-toggle': 'modal',
                    'data-target': '#ms-client-modal',
                    'data-whatever': 'Редактирование'
                }
            },
            {'text': 'Удалить', 'className': 'btn btn-danger', 'charset': 'utf-8', 'attr': { 'id': 'remove-btn' }}
        ],
        'ajax': '/client/dt/clients',
        'serverSide': true,
        columns: [
            {"data": 'id'},
            {"data": 'firstName'},
            {"data": 'lastName'},
            {"data": 'phoneNumber'},
            {"data": 'login'},
            {"data": 'address'},
            {"data": 'numberOfPaidWorkouts'},
            {"data": 'subscription.name'},
            {"data": 'subscriptionUpdated'},
            {"data": 'created'}
        ]
    });

    // --- load subscriptions ---
    loadSubscriptions();

    // --- on selected table row event ---
    $('#ms-clients tbody').on('click', 'tr', function() {
        var rowData = clientsTable.row(this).data();

        $('#ms-client-id').val(rowData.id);
    });

    // --- on show modal window event ---
    $('#ms-client-modal').on('shown.bs.modal', function (e) {
        var operationType = $('#ms-client-operation-type').val();
        var clientId = $('#ms-client-id').val();

        switch (operationType) {
            case 'CREATE': {
                $('#save-btn').attr('onclick', 'add()');
                break;
            }
            case 'UPDATE': {
                if (clientId !== undefined && clientId !== null && clientId !== "") {
                    $('#save-btn').attr('onclick', 'edit(' + clientId + ')');
                } else {
                    alert('Для редактирования нужно выбрать клиента в таблице.');
                    $('#ms-client-modal').modal('hide');
                }
                break;
            }
        }
    });

    $('#ms-clients tbody').on('click', 'tr', function () {  // show selected row
        $('tr.selected').removeClass("selected");
        $(this).toggleClass('selected');
    });

    $('#add-btn').on('click', function () {
        $('#ms-client-operation-type').val('CREATE');
    });

    $('#edit-btn').on('click', function () {
        $('#ms-client-operation-type').val('UPDATE');
    });

    $('#remove-btn').on('click', function () {
        var clientId = $('#ms-client-id').val();

        if (clientId !== undefined && clientId !== null && clientId !== "") {
            if (confirm('Вы действительно хотите удалить клиента с ID: ' + clientId + '?')) {
                remove(clientId);
            }
        } else {
            alert('Для удаления нужно выбрать клиента в таблице.');
        }
    });

    $('#ms-client-modal').on('show.bs.modal', function (event) {
        var recipient = $(event.relatedTarget).data('whatever');
        $(this).find('.modal-title').text(recipient);
    });
});

/**
 * This method create a new client.
 */
function add() {

}

/**
 * This method edit target data.
 *
 * @param id
 */
function edit(id) {

}

/**
 * This method edit target data.
 *
 * @param id
 */
function remove(id) {

}

/**
 * This method load subscriptions and set to the select element.
 */
function loadSubscriptions() {
    $.getJSON('../subscription/get-all', function (response) {
//				console.log(response);
    }).always(function (response) {
        if (response != null && response.length > 0) {
            for (var i = 0; i < response.length; i++){
                $('#ms-client-subscriptions').append('<option value="' + response[i].id + '">' +
                    response[i].name + ' Цена: ' + response[i].cost + ' Дни: ' + response[i].daysCount +
                    '</option>');
            }
        } else {

        }
    });
}
