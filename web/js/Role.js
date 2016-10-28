/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this templte file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function () {
    
    /*var tel = document.getElementById("tel");
    tel.setCustomValidity(" El formato debe ser +99(99)9999-9999");*/
    
    
    $.ajax({
        url: 'GetRoles',
        type: 'GET',
        dataType: 'json'
    }).done(function (json) {
        if(json.code === 200)
            $.each($.parseJSON(json.msg), 
                function (i, row) {
                    $('<option></option>', {text: row.rolename}).attr('value', row.roleid).appendTo('#cbRoles');
                });
    });
    
    
    
    $('#frmRole').validate({
        responsive: true,
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
            if (element.parent('.input-group').length) {
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
        language: {
            url: "//cdn.datatables.net/plug-ins/1.10.12/i18n/Spanish.json"
        },
        ajax: {
            url: "GetRoles",
            dataSrc: function (json) {
                return $.parseJSON(json['msg']);
            }
        },
        columns: [
            {
                data: function (row) {
                    str = "<div align='right'>";
                    str += "$ " + row['roleid'] + ".00";
                    str += "</div>";
                    return str;
                }

            },
            {
                data: "rolename"
            },
            {
                data: function (row) {
                    str = "<div align='center'>";
                    str += "<button id='btnBorrar' class='btn btn-danger btn-xs' onclick='deleteRole(" + row['roleid'] + ")'><i class='glyphicon glyphicon-erase'></i></button>";
                    str += " <button id='btnEditar' class='btn btn-primary btn-xs' onclick='showRole(" + row['roleid'] + ",\"" + row['rolename'] + "\")'><i class='glyphicon glyphicon-edit'></i></button>";
                    str += "</div>";
                    //str+= "&nbsp;<button id='btnEditar' class='btn btn-success btn-xs' onclick='showRole("+row['roleid']+",\""+row['rolename']+"\")'><i class='glyphicon glyphicon-edit'></i></button>";
                    return str;
                }
            }
        ]
    });
    $('#frmEditRole').validate({
        rules: {
            rolename2: {
                minlength: 3,
                maxlength: 20,
                required: true
            }
        },
        messages: {
            rolename2: {
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
            if (element.parent('.input-group').length) {
                error.insertAfter(element.parent());
            } else {
                error.insertAfter(element);
            }
        },
        submitHandler: function (form) {
            updateRole();
            return false;
        }

    });

    $('#btnModificar').on('click', function () {
        $('#frmEditRole').submit();
    });

});

function newRole() {
    $.ajax(
            {
                url: "NewRole",
                type: "post",
                data: {rolename: $('#rolename').val()}
            }
    ).done(
            function (data) {
                if (data.code === 200) {

                    $.growl.notice({message: data.msg});
                    $('#tbRoles').dataTable().api().ajax.reload();
                    $('#rolename').val('');
                } else
                    $.growl.error({message: data.msg});
            }
    ).fail(
            function () {
                $.growl.error({message: "Algo va mal!!!"});
            }
    );
}

function updateRole() {
    $.ajax(
            {
                url: "UpdateRole",
                type: "post",
                data: {roleid: $('#roleid').val(), rolename: $('#rolename2').val()}
            }
    ).done(
            function (data) {
                if (data.code === 200) {

                    $.growl.notice({message: data.msg});
                    $('#tbRoles').dataTable().api().ajax.reload();
                    $('#rolename2').val('');
                    $('#modalRole').modal('toggle');
                } else
                    $.growl.error({message: data.msg});
            }
    ).fail(
            function () {
                $.growl.error({message: "Algo va mal!!!"});
            }
    );
}

function deleteRole(id) {
    $.ajax(
            {
                url: "DeleteRole",
                type: "post",
                data: {roleid: id}
            }
    ).done(
            function (data) {
                if (data.code === 200) {
                    $.growl.notice({message: data.msg});
                    $('#tbRoles').dataTable().api().ajax.reload();
                    $('#rolename').val('');
                } else
                    $.growl.error({message: data.msg});
            }
    ).fail(
            function () {
                $.growl.error({message: "Algo va mal!!!"});
            }
    );
}

function showRole(roleid, rolename) {
    $('#roleid').val(roleid);
    $('#rolename2').val(rolename);
    //$('#slslsls').va(skfmlksdmflkads)
    
    
    $("#modalRole").modal("show");
}
