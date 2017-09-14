function headerAlign(){
    var $table = $('table');
    var	$bodyCells = $table.find('tbody tr:first').children();
    var	colWidth;

    colWidth = $bodyCells.map(function() {
        return $(this).width();
    }).get();

    $table.find('thead tr').children().each(function(i, v) {
        $(v).width(colWidth[i]);
    });
}

function createUserBox(){
    $("#back_shadow").show();
    $("#entity_box").show();
    $("#boxType").val("create");
}

function editUserBox(){
    $("#back_shadow").show();
    $("#entity_box").show();
    $("#entity_box #loginInput").prop("readonly","readonly");
    $("#entity_box #loginInput").val($(".selected .userLogin").text());
    $("#entity_box #passwordInput").val($(".selected .userPassword").text());
    $("#entity_box #clientCheckBox").prop("checked",$(".selected .CLIENT").val());
    $("#entity_box #managerCheckBox").prop("checked", $(".selected .MANAGER").val());
    $("#entity_box #rootCheckBox").prop("checked", $(".selected .ROOT").val());
    $("#boxType").val("edit");

}

function boxCleaner(){
    $("#entity_box").hide();
    $("#back_shadow").hide();
    $("#entity_box input[type='text']").val("");
    $("#entity_box input[type='text']").removeAttr("readonly");
    $("#entity_box input[type='checkbox']").prop("checked",false);
    $("#entity_box input[type='checkbox']").val("");
    $("#boxType").val("");
}


function getValuesFromBox(){

    var login = $("#entity_box #loginInput").val();
    var password = $("#entity_box #passwordInput").val();
    var roles = "";
    if ($("#entity_box #clientCheckBox").prop("checked")){
        roles = '{"name":"CLIENT"}'
    }
    if ($("#entity_box #managerCheckBox").prop("checked")){
        if (roles != ""){
            roles += ',{"name":"MANAGER"}'
        }
        else
            roles += '{"name":"MANAGER"}';
    }
    if ($("#entity_box #rootCheckBox").prop("checked")){
        if (roles != ""){
            roles += ',{"name":"ROOT"}'
        }
        else
            roles += '{"name":"ROOT"}';
    }
    return jsonUser = '{"login":"' + login +
                     '", "password":"' + password +
                     '", "roles":[' + roles + ']}';
}


function createUser(){
    jQuery.support.cors = true;

    var toServer = getValuesFromBox();

    $.ajax ({
        type:'POST',
        url: 'http://localhost:8080/createUser',
        data: toServer,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (v) {
            alert("Запись для пользователя с логином " + v.login + " создана");
            var line = getLine(v);
            $("#entityList").prepend(line);
            $("tbody tr").removeClass("selected");
            $("#"+v.login).addClass("selected");
            $("#delete").removeAttr("disabled");
            $("#edit").removeAttr("disabled");
            boxCleaner();
            headerAlign();
        },
        error: function(jqXHR, textStatus, errorThrown){
            alert($(jqXHR).html() + " " +  textStatus + ": " + errorThrown);
        }
    })


}

function editUser(){
    jQuery.support.cors = true;

    var toServer = getValuesFromBox();

    $.ajax ({
        type:'POST',
        url: 'http://localhost:8080/editUser',
        data: toServer,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (v) {
            alert("Запись для пользователя с логином " + v.login + " изменена");
            var line = getLine(v);
            $("#" + v.login).replaceWith(line);
            $("#" + v.login).addClass("selected");
            $("#delete").removeAttr("disabled");
            $("#edit").removeAttr("disabled");
            boxCleaner();
            headerAlign();
        },
        error: function(jqXHR, textStatus, errorThrown){
            alert($(jqXHR).html() + " " +  textStatus + ": " + errorThrown);
        }
    })


}



function deleteUser(login){

    jQuery.support.cors = true;

    $.ajax ({
        type:'POST',
        url: 'http://localhost:8080/deleteUser',
        data: '{"login":"' + login + '"}',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (result) {
            alert(result.message);
            $("#entityList .selected").remove();
            $("#delete").prop("disabled","disabled");
            $("#edit").prop("disabled","disabled");
            headerAlign();
        },
        error: function(jqXHR, textStatus, errorThrown){
            alert($(jqXHR).html() + " " +  textStatus + ": " + errorThrown);
        }
    })

}

function getLine(v){
    trHTML = ""
    trHTML = '<tr id="' + v.login + '"><td class="first_element userLogin">' + v.login + '</td>' +
        '<td class="userPassword">' + v.password + '</td>';
    for (i=0; i < v.roles.length; i++){
        trHTML += '<input class="' + v.roles[i].name + '" type="hidden" value="' + v.roles[i].name + '" />';
    }
    trHTML += '</tr>';
    return trHTML;

}
function findAll(){

    jQuery.support.cors = true;

    $.ajax(
        {
            type: 'GET',
            url: 'http://localhost:8080/users',
            dataType: "json",
            success: function (result) {

                var trHTML = '';

                $.each(result, function(k, v) {
                    trHTML += getLine(v);
                });

                $('#entityList tbody').html(trHTML);
                headerAlign();

            },

            error: function(jqXHR, textStatus, errorThrown){
                alert($(jqXHR).html() + " " +  textStatus + ": " + errorThrown);
            }
        });
}

$(document).ready(function(){

    findAll();
    headerAlign();

    $("tbody").on("click", "tr", function(){
        if ($(this).hasClass("selected")){
            $(this).removeClass("selected");
            $("#delete").prop("disabled","disabled");
            $("#edit").prop("disabled","disabled");
        }
        else{
            $("tbody tr").removeClass("selected");
            $(this).addClass("selected");
            $("#delete").removeAttr("disabled");
            $("#edit").removeAttr("disabled");
        }
    })

    $("#delete").on("click", function(){
        deleteUser($(".selected .userLogin").text());
    })

    $("#edit").on("click", function(){
        editUserBox();
    })

    $("#create").on("click", function(){
        createUserBox();
    })

    $("#cancel").on("click", function(){
        boxCleaner();
    })

    $("#ok").on("click", function(){
        if ($("#boxType").val() == "create"){
            createUser();
        }
        else if ($("#boxType").val() == "edit"){
            editUser();
        }
    })

})