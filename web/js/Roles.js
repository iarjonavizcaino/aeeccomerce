/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function() {
    $('#frmRole').validate({
        rules: {
            rolename: {
                minlength: 3,
                maxlength: 20,
                required: true
            }
        },
        messages: {
            rolename: {
                minlength: "Introduzca al menos 3 caracteres",
                maxlength: "Introduzca máximo 20 caracteres",
                required: "Capture el nombre del rol"
            }
        },
        highlight: function (element) {
            $(element).closest('.form-group').addClass('has-error');
        },
        unhighlight: function (element) {
            $(element).closest('.form-group').removeClass('has-error');
        },
        errorElement: 'span',
        errorClass: 'help-block',
        errorPlacement: function (error, element) {
            if(element.parent('.input-group').length) {
                error.insertAfter(element.parent());
            } else {
                error.insertAfter(element);
            }
        },
        submitHandler: function (form) {
            newRole();
            return false;
        }
    });
    $('#tbRoles').DataTable({
        ajax: {
            url: 'GetRoles',
            dataSrc: function(json) {
                console.log(json.msg);
                return $.parseJSON(json.msg);
            }
        },
        columns: [
            {
                data: 'roleid'
            },
            {
                data: 'rolename'
            },
            {
                data: function (row) {
                    str = "<button id='btnEliminar' class='btn btn-danger btn-xs' onclick='deleteRole("+row['roleid']+")'>Eliminar</button>";
                    return str;
                }
            }
            
        ]
    });
});

function newRole() {
    $.ajax({
        url: "NewRole",
        type: "post",
        data: $('#frmRole').serialize()
    }).done(function(json){
        if(json.code===200){
            $('#tbRoles').dataTable().api().ajax.reload();
            $('#rolename').val('');
            $.growl.notice({ message: json.msg });
        }
        else
            $.growl.error({ message: json.msg });
    }).fail(function (json){
        $.growl.error({ message: "No disponible" });
    });
}

function deleteRole(id) {
    $.ajax(
            {
                url: "DeleteRole",
                type: "post",
                data: {roleid: id}
            }
    ).done(
        function(data) {
            if(data.code === 200) {
                $.growl.notice({ message: data.msg });
                $('#tbRoles').dataTable().api().ajax.reload();
                $('#rolename').val('');
            }
            else
                $.growl.error({ message: data.msg });
        }
    ).fail(
        function() {
            $.growl.error({ message: "Algo va mal!!!" });
        }
    );
}