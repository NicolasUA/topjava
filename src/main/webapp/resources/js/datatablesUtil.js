function makeEditable() {

    $('#add').click(function () {
        $('#id').val(0);
        $('#editRow').modal();
    });

    $('.delete').click(function () {
        deleteRow($(this).closest('tr').attr('id'));
    });

    $('#detailsForm').submit(function () {
        save();
        return false;
    });

    $('#filter').submit(function () {
        filterTable();
        return false;
    });

    $('.enable').click(function () {
        enable($(this), $(this).closest('tr').attr('id'));
    });

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(event, jqXHR, options, jsExc);
    });

    $('.date-picker').datetimepicker({
        timepicker: false,
        format: 'Y-m-d',
        lang:'ru'
    });

    $('.time-picker').datetimepicker({
        datepicker: false,
        format: 'H:i',
        lang:'ru'
    });

    $('.datetime-picker').datetimepicker({
        format: 'Y-m-d\\TH:i',
        lang:'ru'
    });
}

function deleteRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: 'DELETE',
        success: function () {
            updateTable();
            successNoty('Deleted');
        }
    });
}

function updateTable() {
    $.get(ajaxUrl, function (data) {
        oTable_datatable.clear().rows.add(data).draw();
    });
}

function filterTable() {
    var form = $('#filter');
    $.ajax({
        type: "POST",
        url: ajaxUrl + "filter",
        data: form.serialize(),
        success: successNoty('Filtered')
    }).done(function (data) {
        oTable_datatable.clear().rows.add(data).draw();
    });
}

function save() {
    var form = $('#detailsForm');
//    debugger;
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize(),
        success: function () {
            $('#editRow').modal('hide');
            updateTable();
            successNoty('Saved');
        }
    });
}

function enable(checkbox, id) {
    var enabled = checkbox.is(':checked');
    $.ajax({
        type: "POST",
        url: ajaxUrl + id + '/enable',
        data: 'enabled=' + enabled,
        success: function () {
            successNoty(enabled ? 'Enabled' : 'Disabled');
        }
    });
}

var failedNote;

function closeNote() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(text) {
    closeNote();
    noty({
        text: text,
        type: 'success',
        layout: 'bottomRight',
        timeout: true
    });
}

function failNoty(event, jqXHR, options, jsExc) {
    closeNote();
    failedNote = noty({
        text: 'Failed: ' + jqXHR.statusText + "<br>",
        type: 'error',
        layout: 'bottomRight'
    });
}
